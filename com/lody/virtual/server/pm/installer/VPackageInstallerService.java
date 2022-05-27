package com.lody.virtual.server.pm.installer;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.IPackageInstallerCallback;
import android.content.pm.IPackageInstallerSession;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.SparseArray;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.utils.Singleton;
import com.lody.virtual.os.VBinder;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.remote.VParceledListSlice;
import com.lody.virtual.server.IPackageInstaller;
import com.lody.virtual.server.pm.VAppManagerService;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

public class VPackageInstallerService extends IPackageInstaller.Stub {
  private static final long MAX_ACTIVE_SESSIONS = 1024L;
  
  private static final String TAG = "PackageInstaller";
  
  private static final Singleton<VPackageInstallerService> gDefault = new Singleton<VPackageInstallerService>() {
      protected VPackageInstallerService create() {
        return new VPackageInstallerService();
      }
    };
  
  private final Callbacks mCallbacks;
  
  private Context mContext = VirtualCore.get().getContext();
  
  private final Handler mInstallHandler;
  
  private final HandlerThread mInstallThread;
  
  private final InternalCallback mInternalCallback = new InternalCallback();
  
  private final Random mRandom = new SecureRandom();
  
  private final SparseArray<PackageInstallerSession> mSessions = new SparseArray();
  
  private VPackageInstallerService() {
    HandlerThread handlerThread = new HandlerThread("PackageInstaller");
    this.mInstallThread = handlerThread;
    handlerThread.start();
    this.mInstallHandler = new Handler(this.mInstallThread.getLooper());
    this.mCallbacks = new Callbacks(this.mInstallThread.getLooper());
  }
  
  private int allocateSessionIdLocked() {
    byte b = 0;
    while (true) {
      int i = this.mRandom.nextInt(2147483646) + 1;
      if (this.mSessions.get(i) == null)
        return i; 
      if (b < 32) {
        b++;
        continue;
      } 
      throw new IllegalStateException("Failed to allocate session ID");
    } 
  }
  
  private int createSessionInternal(SessionParams paramSessionParams, String paramString, int paramInt1, int paramInt2) throws IOException {
    synchronized (this.mSessions) {
      if (getSessionCount(this.mSessions, paramInt2) < 1024L) {
        int i = allocateSessionIdLocked();
        PackageInstallerSession packageInstallerSession = new PackageInstallerSession();
        this(this.mInternalCallback, this.mContext, this.mInstallHandler.getLooper(), paramString, i, paramInt1, paramInt2, paramSessionParams, VEnvironment.getPackageInstallerStageDir());
        synchronized (this.mSessions) {
          this.mSessions.put(i, packageInstallerSession);
          this.mCallbacks.notifySessionCreated(packageInstallerSession.sessionId, packageInstallerSession.userId);
          return i;
        } 
      } 
      IllegalStateException illegalStateException = new IllegalStateException();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("Too many active sessions for UID ");
      stringBuilder.append(paramInt2);
      this(stringBuilder.toString());
      throw illegalStateException;
    } 
  }
  
  public static VPackageInstallerService get() {
    return (VPackageInstallerService)gDefault.get();
  }
  
  private static int getSessionCount(SparseArray<PackageInstallerSession> paramSparseArray, int paramInt) {
    int i = paramSparseArray.size();
    byte b = 0;
    int j;
    for (j = 0; b < i; j = k) {
      int k = j;
      if (((PackageInstallerSession)paramSparseArray.valueAt(b)).installerUid == paramInt)
        k = j + 1; 
      b++;
    } 
    return j;
  }
  
  private boolean isCallingUidOwner(PackageInstallerSession paramPackageInstallerSession) {
    return true;
  }
  
  private IPackageInstallerSession openSessionInternal(int paramInt) throws IOException {
    synchronized (this.mSessions) {
      PackageInstallerSession packageInstallerSession = (PackageInstallerSession)this.mSessions.get(paramInt);
      if (packageInstallerSession != null && isCallingUidOwner(packageInstallerSession)) {
        packageInstallerSession.open();
        return (IPackageInstallerSession)packageInstallerSession;
      } 
      SecurityException securityException = new SecurityException();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("Caller has no access to session ");
      stringBuilder.append(paramInt);
      this(stringBuilder.toString());
      throw securityException;
    } 
  }
  
  public void abandonSession(int paramInt) {
    synchronized (this.mSessions) {
      PackageInstallerSession packageInstallerSession = (PackageInstallerSession)this.mSessions.get(paramInt);
      if (packageInstallerSession != null) {
        boolean bool = isCallingUidOwner(packageInstallerSession);
        if (bool) {
          try {
            packageInstallerSession.abandon();
          } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
          } 
          return;
        } 
      } 
      SecurityException securityException = new SecurityException();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("Caller has no access to session ");
      stringBuilder.append(paramInt);
      this(stringBuilder.toString());
      throw securityException;
    } 
  }
  
  public int createSession(SessionParams paramSessionParams, String paramString, int paramInt) {
    try {
      return createSessionInternal(paramSessionParams, paramString, paramInt, VBinder.getCallingUid());
    } catch (IOException iOException) {
      throw new IllegalStateException(iOException);
    } 
  }
  
  public VParceledListSlice getAllSessions(int paramInt) {
    // Byte code:
    //   0: new java/util/ArrayList
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore_2
    //   8: aload_0
    //   9: getfield mSessions : Landroid/util/SparseArray;
    //   12: astore_3
    //   13: aload_3
    //   14: monitorenter
    //   15: iconst_0
    //   16: istore #4
    //   18: iload #4
    //   20: aload_0
    //   21: getfield mSessions : Landroid/util/SparseArray;
    //   24: invokevirtual size : ()I
    //   27: if_icmpge -> 71
    //   30: aload_0
    //   31: getfield mSessions : Landroid/util/SparseArray;
    //   34: iload #4
    //   36: invokevirtual valueAt : (I)Ljava/lang/Object;
    //   39: checkcast com/lody/virtual/server/pm/installer/PackageInstallerSession
    //   42: astore #5
    //   44: aload #5
    //   46: getfield userId : I
    //   49: iload_1
    //   50: if_icmpne -> 65
    //   53: aload_2
    //   54: aload #5
    //   56: invokevirtual generateInfo : ()Lcom/lody/virtual/server/pm/installer/SessionInfo;
    //   59: invokeinterface add : (Ljava/lang/Object;)Z
    //   64: pop
    //   65: iinc #4, 1
    //   68: goto -> 18
    //   71: aload_3
    //   72: monitorexit
    //   73: new com/lody/virtual/remote/VParceledListSlice
    //   76: dup
    //   77: aload_2
    //   78: invokespecial <init> : (Ljava/util/List;)V
    //   81: areturn
    //   82: astore_2
    //   83: aload_3
    //   84: monitorexit
    //   85: aload_2
    //   86: athrow
    // Exception table:
    //   from	to	target	type
    //   18	65	82	finally
    //   71	73	82	finally
    //   83	85	82	finally
  }
  
  public VParceledListSlice getMySessions(String paramString, int paramInt) {
    // Byte code:
    //   0: new java/util/ArrayList
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore_3
    //   8: aload_0
    //   9: getfield mSessions : Landroid/util/SparseArray;
    //   12: astore #4
    //   14: aload #4
    //   16: monitorenter
    //   17: iconst_0
    //   18: istore #5
    //   20: iload #5
    //   22: aload_0
    //   23: getfield mSessions : Landroid/util/SparseArray;
    //   26: invokevirtual size : ()I
    //   29: if_icmpge -> 85
    //   32: aload_0
    //   33: getfield mSessions : Landroid/util/SparseArray;
    //   36: iload #5
    //   38: invokevirtual valueAt : (I)Ljava/lang/Object;
    //   41: checkcast com/lody/virtual/server/pm/installer/PackageInstallerSession
    //   44: astore #6
    //   46: aload #6
    //   48: getfield installerPackageName : Ljava/lang/String;
    //   51: aload_1
    //   52: invokestatic equals : (Ljava/lang/Object;Ljava/lang/Object;)Z
    //   55: ifeq -> 79
    //   58: aload #6
    //   60: getfield userId : I
    //   63: iload_2
    //   64: if_icmpne -> 79
    //   67: aload_3
    //   68: aload #6
    //   70: invokevirtual generateInfo : ()Lcom/lody/virtual/server/pm/installer/SessionInfo;
    //   73: invokeinterface add : (Ljava/lang/Object;)Z
    //   78: pop
    //   79: iinc #5, 1
    //   82: goto -> 20
    //   85: aload #4
    //   87: monitorexit
    //   88: new com/lody/virtual/remote/VParceledListSlice
    //   91: dup
    //   92: aload_3
    //   93: invokespecial <init> : (Ljava/util/List;)V
    //   96: areturn
    //   97: astore_1
    //   98: aload #4
    //   100: monitorexit
    //   101: aload_1
    //   102: athrow
    // Exception table:
    //   from	to	target	type
    //   20	79	97	finally
    //   85	88	97	finally
    //   98	101	97	finally
  }
  
  public SessionInfo getSessionInfo(int paramInt) {
    synchronized (this.mSessions) {
      PackageInstallerSession packageInstallerSession = (PackageInstallerSession)this.mSessions.get(paramInt);
      if (packageInstallerSession != null) {
        SessionInfo sessionInfo = packageInstallerSession.generateInfo();
      } else {
        packageInstallerSession = null;
      } 
      return (SessionInfo)packageInstallerSession;
    } 
  }
  
  public IPackageInstallerSession openSession(int paramInt) {
    try {
      return openSessionInternal(paramInt);
    } catch (IOException iOException) {
      throw new IllegalStateException(iOException);
    } 
  }
  
  public void registerCallback(IPackageInstallerCallback paramIPackageInstallerCallback, int paramInt) {
    this.mCallbacks.register(paramIPackageInstallerCallback, paramInt);
  }
  
  public void setPermissionsResult(int paramInt, boolean paramBoolean) {
    synchronized (this.mSessions) {
      PackageInstallerSession packageInstallerSession = (PackageInstallerSession)this.mSessions.get(paramInt);
      if (packageInstallerSession != null)
        packageInstallerSession.setPermissionsResult(paramBoolean); 
      return;
    } 
  }
  
  public void uninstall(String paramString1, String paramString2, int paramInt1, IntentSender paramIntentSender, int paramInt2) {
    boolean bool = VAppManagerService.get().uninstallPackage(paramString1);
    if (paramIntentSender != null) {
      Intent intent = new Intent();
      intent.putExtra("android.content.pm.extra.PACKAGE_NAME", paramString1);
      intent.putExtra("android.content.pm.extra.STATUS", bool ^ true);
      intent.putExtra("android.content.pm.extra.STATUS_MESSAGE", PackageHelper.deleteStatusToString(bool));
      if (bool) {
        paramInt1 = 1;
      } else {
        paramInt1 = -1;
      } 
      intent.putExtra("android.content.pm.extra.LEGACY_STATUS", paramInt1);
      try {
        paramIntentSender.sendIntent(this.mContext, 0, intent, null, null);
      } catch (android.content.IntentSender.SendIntentException sendIntentException) {
        sendIntentException.printStackTrace();
      } 
    } 
  }
  
  public void unregisterCallback(IPackageInstallerCallback paramIPackageInstallerCallback) {
    this.mCallbacks.unregister(paramIPackageInstallerCallback);
  }
  
  public void updateSessionAppIcon(int paramInt, Bitmap paramBitmap) {
    synchronized (this.mSessions) {
      PackageInstallerSession packageInstallerSession = (PackageInstallerSession)this.mSessions.get(paramInt);
      if (packageInstallerSession != null && isCallingUidOwner(packageInstallerSession)) {
        packageInstallerSession.params.appIcon = paramBitmap;
        packageInstallerSession.params.appIconLastModified = -1L;
        this.mInternalCallback.onSessionBadgingChanged(packageInstallerSession);
        return;
      } 
      SecurityException securityException = new SecurityException();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("Caller has no access to session ");
      stringBuilder.append(paramInt);
      this(stringBuilder.toString());
      throw securityException;
    } 
  }
  
  public void updateSessionAppLabel(int paramInt, String paramString) {
    synchronized (this.mSessions) {
      PackageInstallerSession packageInstallerSession = (PackageInstallerSession)this.mSessions.get(paramInt);
      if (packageInstallerSession != null && isCallingUidOwner(packageInstallerSession)) {
        packageInstallerSession.params.appLabel = paramString;
        this.mInternalCallback.onSessionBadgingChanged(packageInstallerSession);
        return;
      } 
      SecurityException securityException = new SecurityException();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("Caller has no access to session ");
      stringBuilder.append(paramInt);
      this(stringBuilder.toString());
      throw securityException;
    } 
  }
  
  private static class Callbacks extends Handler {
    private static final int MSG_SESSION_ACTIVE_CHANGED = 3;
    
    private static final int MSG_SESSION_BADGING_CHANGED = 2;
    
    private static final int MSG_SESSION_CREATED = 1;
    
    private static final int MSG_SESSION_FINISHED = 5;
    
    private static final int MSG_SESSION_PROGRESS_CHANGED = 4;
    
    private final RemoteCallbackList<IPackageInstallerCallback> mCallbacks = new RemoteCallbackList();
    
    public Callbacks(Looper param1Looper) {
      super(param1Looper);
    }
    
    private void invokeCallback(IPackageInstallerCallback param1IPackageInstallerCallback, Message param1Message) throws RemoteException {
      int i = param1Message.arg1;
      int j = param1Message.what;
      if (j != 1) {
        if (j != 2) {
          if (j != 3) {
            if (j != 4) {
              if (j == 5)
                param1IPackageInstallerCallback.onSessionFinished(i, ((Boolean)param1Message.obj).booleanValue()); 
            } else {
              param1IPackageInstallerCallback.onSessionProgressChanged(i, ((Float)param1Message.obj).floatValue());
            } 
          } else {
            param1IPackageInstallerCallback.onSessionActiveChanged(i, ((Boolean)param1Message.obj).booleanValue());
          } 
        } else {
          param1IPackageInstallerCallback.onSessionBadgingChanged(i);
        } 
      } else {
        param1IPackageInstallerCallback.onSessionCreated(i);
      } 
    }
    
    private void notifySessionActiveChanged(int param1Int1, int param1Int2, boolean param1Boolean) {
      obtainMessage(3, param1Int1, param1Int2, Boolean.valueOf(param1Boolean)).sendToTarget();
    }
    
    private void notifySessionBadgingChanged(int param1Int1, int param1Int2) {
      obtainMessage(2, param1Int1, param1Int2).sendToTarget();
    }
    
    private void notifySessionCreated(int param1Int1, int param1Int2) {
      obtainMessage(1, param1Int1, param1Int2).sendToTarget();
    }
    
    private void notifySessionProgressChanged(int param1Int1, int param1Int2, float param1Float) {
      obtainMessage(4, param1Int1, param1Int2, Float.valueOf(param1Float)).sendToTarget();
    }
    
    public void handleMessage(Message param1Message) {
      int i = param1Message.arg2;
      int j = this.mCallbacks.beginBroadcast();
      byte b = 0;
      while (true) {
        if (b < j) {
          IPackageInstallerCallback iPackageInstallerCallback = (IPackageInstallerCallback)this.mCallbacks.getBroadcastItem(b);
          if (i == ((VUserHandle)this.mCallbacks.getBroadcastCookie(b)).getIdentifier())
            try {
              invokeCallback(iPackageInstallerCallback, param1Message);
            } catch (RemoteException remoteException) {} 
          b++;
          continue;
        } 
        this.mCallbacks.finishBroadcast();
        return;
      } 
    }
    
    public void notifySessionFinished(int param1Int1, int param1Int2, boolean param1Boolean) {
      obtainMessage(5, param1Int1, param1Int2, Boolean.valueOf(param1Boolean)).sendToTarget();
    }
    
    public void register(IPackageInstallerCallback param1IPackageInstallerCallback, int param1Int) {
      this.mCallbacks.register((IInterface)param1IPackageInstallerCallback, new VUserHandle(param1Int));
    }
    
    public void unregister(IPackageInstallerCallback param1IPackageInstallerCallback) {
      this.mCallbacks.unregister((IInterface)param1IPackageInstallerCallback);
    }
  }
  
  class InternalCallback {
    public void onSessionActiveChanged(PackageInstallerSession param1PackageInstallerSession, boolean param1Boolean) {
      VPackageInstallerService.this.mCallbacks.notifySessionActiveChanged(param1PackageInstallerSession.sessionId, param1PackageInstallerSession.userId, param1Boolean);
    }
    
    public void onSessionBadgingChanged(PackageInstallerSession param1PackageInstallerSession) {
      VPackageInstallerService.this.mCallbacks.notifySessionBadgingChanged(param1PackageInstallerSession.sessionId, param1PackageInstallerSession.userId);
    }
    
    public void onSessionFinished(final PackageInstallerSession session, boolean param1Boolean) {
      VPackageInstallerService.this.mCallbacks.notifySessionFinished(session.sessionId, session.userId, param1Boolean);
      VPackageInstallerService.this.mInstallHandler.post(new Runnable() {
            public void run() {
              synchronized (VPackageInstallerService.this.mSessions) {
                VPackageInstallerService.this.mSessions.remove(session.sessionId);
                return;
              } 
            }
          });
    }
    
    public void onSessionPrepared(PackageInstallerSession param1PackageInstallerSession) {}
    
    public void onSessionProgressChanged(PackageInstallerSession param1PackageInstallerSession, float param1Float) {
      VPackageInstallerService.this.mCallbacks.notifySessionProgressChanged(param1PackageInstallerSession.sessionId, param1PackageInstallerSession.userId, param1Float);
    }
    
    public void onSessionSealedBlocking(PackageInstallerSession param1PackageInstallerSession) {}
  }
  
  class null implements Runnable {
    public void run() {
      synchronized (VPackageInstallerService.this.mSessions) {
        VPackageInstallerService.this.mSessions.remove(session.sessionId);
        return;
      } 
    }
  }
  
  static class PackageInstallObserverAdapter extends PackageInstallObserver {
    private final Context mContext;
    
    private final int mSessionId;
    
    private final IntentSender mTarget;
    
    private final int mUserId;
    
    PackageInstallObserverAdapter(Context param1Context, IntentSender param1IntentSender, int param1Int1, int param1Int2) {
      this.mContext = param1Context;
      this.mTarget = param1IntentSender;
      this.mSessionId = param1Int1;
      this.mUserId = param1Int2;
    }
    
    public void onPackageInstalled(String param1String1, int param1Int, String param1String2, Bundle param1Bundle) {
      Intent intent = new Intent();
      intent.putExtra("android.content.pm.extra.PACKAGE_NAME", param1String1);
      intent.putExtra("android.content.pm.extra.SESSION_ID", this.mSessionId);
      intent.putExtra("android.content.pm.extra.STATUS", PackageHelper.installStatusToPublicStatus(param1Int));
      intent.putExtra("android.content.pm.extra.STATUS_MESSAGE", PackageHelper.installStatusToString(param1Int, param1String2));
      intent.putExtra("android.content.pm.extra.LEGACY_STATUS", param1Int);
      if (param1Bundle != null) {
        param1String1 = param1Bundle.getString("android.content.pm.extra.FAILURE_EXISTING_PACKAGE");
        if (!TextUtils.isEmpty(param1String1))
          intent.putExtra("android.content.pm.extra.OTHER_PACKAGE_NAME", param1String1); 
      } 
      try {
        this.mTarget.sendIntent(this.mContext, 0, intent, null, null);
      } catch (android.content.IntentSender.SendIntentException sendIntentException) {}
    }
    
    public void onUserActionRequired(Intent param1Intent) {
      Intent intent = new Intent();
      intent.putExtra("android.content.pm.extra.SESSION_ID", this.mSessionId);
      intent.putExtra("android.content.pm.extra.STATUS", -1);
      intent.putExtra("android.intent.extra.INTENT", (Parcelable)param1Intent);
      try {
        this.mTarget.sendIntent(this.mContext, 0, intent, null, null);
      } catch (android.content.IntentSender.SendIntentException sendIntentException) {}
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\installer\VPackageInstallerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */