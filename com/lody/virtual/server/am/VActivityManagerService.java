package com.lody.virtual.server.am;

import android.app.ActivityManager;
import android.app.IStopUserCallback;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import com.lody.virtual.client.IVClient;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.Constants;
import com.lody.virtual.client.env.SpecialComponentList;
import com.lody.virtual.client.ipc.ProviderCall;
import com.lody.virtual.client.stub.StubManifest;
import com.lody.virtual.helper.compat.ApplicationThreadCompat;
import com.lody.virtual.helper.compat.BundleCompat;
import com.lody.virtual.helper.compat.PermissionCompat;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.helper.utils.Singleton;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VBinder;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.remote.AppTaskInfo;
import com.lody.virtual.remote.BadgerInfo;
import com.lody.virtual.remote.ClientConfig;
import com.lody.virtual.remote.IntentSenderData;
import com.lody.virtual.remote.VParceledListSlice;
import com.lody.virtual.server.extension.VExtPackageAccessor;
import com.lody.virtual.server.interfaces.IActivityManager;
import com.lody.virtual.server.pm.PackageSetting;
import com.lody.virtual.server.pm.VPackageManagerService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VActivityManagerService extends IActivityManager.Stub {
  private static final String TAG;
  
  private static final Singleton<VActivityManagerService> sService = new Singleton<VActivityManagerService>() {
      protected VActivityManagerService create() {
        return new VActivityManagerService();
      }
    };
  
  private final ActivityStack mActivityStack = new ActivityStack(this);
  
  private final Handler mHandler;
  
  private final Map<IBinder, IntentSenderData> mIntentSenderMap = new HashMap<IBinder, IntentSenderData>();
  
  private final List<ProcessRecord> mPidsSelfLocked = new ArrayList<ProcessRecord>();
  
  private boolean mResult;
  
  private final Map<String, Boolean> sIdeMap = new HashMap<String, Boolean>();
  
  static {
    TAG = VActivityManagerService.class.getSimpleName();
  }
  
  private VActivityManagerService() {
    Handler handler = new Handler();
    this.mHandler = handler;
    handler.postDelayed(new Runnable() {
          public void run() {
            synchronized (VActivityManagerService.this.mIntentSenderMap) {
              Iterator<IntentSenderData> iterator = VActivityManagerService.this.mIntentSenderMap.values().iterator();
              while (iterator.hasNext()) {
                PendingIntent pendingIntent = ((IntentSenderData)iterator.next()).getPendingIntent();
                if (pendingIntent == null || pendingIntent.getTargetPackage() == null)
                  iterator.remove(); 
              } 
              VActivityManagerService.this.mHandler.postDelayed(this, 300000L);
              return;
            } 
          }
        }300000L);
  }
  
  public static VActivityManagerService get() {
    return (VActivityManagerService)sService.get();
  }
  
  private String getProcessName(int paramInt) {
    for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : VirtualCore.get().getRunningAppProcessesEx()) {
      if (runningAppProcessInfo.pid == paramInt)
        return runningAppProcessInfo.processName; 
    } 
    return null;
  }
  
  private boolean initProcessLocked(ProcessRecord paramProcessRecord) {
    requestPermissionIfNeed(paramProcessRecord);
    Bundle bundle = new Bundle();
    bundle.putParcelable("_VA_|_client_config_", (Parcelable)paramProcessRecord.getClientConfig());
    bundle = ProviderCall.callSafely(paramProcessRecord.getProviderAuthority(), "_VA_|_init_process_", null, bundle, 0);
    if (bundle == null)
      return false; 
    paramProcessRecord.pid = bundle.getInt("_VA_|_pid_");
    IBinder iBinder = BundleCompat.getBinder(bundle, "_VA_|_client_");
    IVClient iVClient = IVClient.Stub.asInterface(iBinder);
    if (iVClient == null) {
      paramProcessRecord.kill();
      return false;
    } 
    try {
      IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
          public void binderDied() {
            clientBinder.unlinkToDeath(this, 0);
            VActivityManagerService.this.onProcessDied(app);
          }
        };
      super(this, iBinder, paramProcessRecord);
      iBinder.linkToDeath(deathRecipient, 0);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
    paramProcessRecord.client = iVClient;
    try {
      paramProcessRecord.appThread = ApplicationThreadCompat.asInterface(iVClient.getAppThread());
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
    String str = TAG;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("start new process : ");
    stringBuilder.append(paramProcessRecord.processName);
    stringBuilder.append(" pid: ");
    stringBuilder.append(paramProcessRecord.pid);
    VLog.w(str, stringBuilder.toString(), new Object[0]);
    return true;
  }
  
  private void onProcessDied(ProcessRecord paramProcessRecord) {
    if (paramProcessRecord != null)
      synchronized (this.mPidsSelfLocked) {
        this.mPidsSelfLocked.remove(paramProcessRecord);
        processDied(paramProcessRecord);
      }  
  }
  
  private int parseVPid(String paramString) {
    String str;
    if (paramString == null)
      return -1; 
    if (paramString.startsWith(StubManifest.EXT_PACKAGE_NAME)) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(StubManifest.EXT_PACKAGE_NAME);
      stringBuilder.append(":p");
      str = stringBuilder.toString();
    } else if (paramString.startsWith(StubManifest.PACKAGE_NAME)) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(VirtualCore.get().getHostPkg());
      stringBuilder.append(":p");
      str = stringBuilder.toString();
    } else {
      return -1;
    } 
    if (paramString.startsWith(str))
      try {
        return Integer.parseInt(paramString.substring(str.length()));
      } catch (NumberFormatException numberFormatException) {} 
    return -1;
  }
  
  private void processDied(ProcessRecord paramProcessRecord) {
    this.mActivityStack.processDied(paramProcessRecord);
  }
  
  private void requestPermissionIfNeed(ProcessRecord paramProcessRecord) {
    if (PermissionCompat.isCheckPermissionRequired(paramProcessRecord.info)) {
      String[] arrayOfString = VPackageManagerService.get().getDangerousPermissions(paramProcessRecord.info.packageName);
      if (!PermissionCompat.checkPermissions(arrayOfString, paramProcessRecord.isExt)) {
        ConditionVariable conditionVariable = new ConditionVariable();
        startRequestPermissions(paramProcessRecord.isExt, arrayOfString, conditionVariable);
        conditionVariable.block();
      } 
    } 
  }
  
  private void runProcessGC() {
    if (get().getFreeStubCount() < 10)
      killAllApps(); 
  }
  
  private void sendFirstLaunchBroadcast(PackageSetting paramPackageSetting, int paramInt) {
    Intent intent = new Intent("android.intent.action.PACKAGE_FIRST_LAUNCH", Uri.fromParts("package", paramPackageSetting.packageName, null));
    intent.setPackage(paramPackageSetting.packageName);
    intent.putExtra("android.intent.extra.UID", VUserHandle.getUid(paramPackageSetting.appId, paramInt));
    intent.putExtra("android.intent.extra.user_handle", paramInt);
    sendBroadcastAsUser(intent, new VUserHandle(paramInt));
  }
  
  private void startRequestPermissions(boolean paramBoolean, String[] paramArrayOfString, final ConditionVariable permissionLock) {
    PermissionCompat.startRequestPermissions(VirtualCore.get().getContext(), paramBoolean, paramArrayOfString, new PermissionCompat.CallBack() {
          public boolean onResult(int param1Int, String[] param1ArrayOfString, int[] param1ArrayOfint) {
            try {
              VActivityManagerService.access$302(VActivityManagerService.this, PermissionCompat.isRequestGranted(param1ArrayOfint));
              return VActivityManagerService.this.mResult;
            } finally {
              permissionLock.open();
            } 
          }
        });
  }
  
  public IBinder acquireProviderClient(int paramInt, ProviderInfo paramProviderInfo) {
    // Byte code:
    //   0: aload_2
    //   1: getfield processName : Ljava/lang/String;
    //   4: astore_3
    //   5: aload_0
    //   6: monitorenter
    //   7: aload_0
    //   8: aload_3
    //   9: iload_1
    //   10: aload_2
    //   11: getfield packageName : Ljava/lang/String;
    //   14: iconst_m1
    //   15: invokevirtual startProcessIfNeeded : (Ljava/lang/String;ILjava/lang/String;I)Lcom/lody/virtual/server/am/ProcessRecord;
    //   18: astore_3
    //   19: aload_0
    //   20: monitorexit
    //   21: aload_3
    //   22: ifnull -> 43
    //   25: aload_3
    //   26: getfield client : Lcom/lody/virtual/client/IVClient;
    //   29: aload_2
    //   30: invokeinterface acquireProviderClient : (Landroid/content/pm/ProviderInfo;)Landroid/os/IBinder;
    //   35: astore_2
    //   36: aload_2
    //   37: areturn
    //   38: astore_2
    //   39: aload_2
    //   40: invokevirtual printStackTrace : ()V
    //   43: aconst_null
    //   44: areturn
    //   45: astore_2
    //   46: aload_0
    //   47: monitorexit
    //   48: aload_2
    //   49: athrow
    // Exception table:
    //   from	to	target	type
    //   7	21	45	finally
    //   25	36	38	android/os/RemoteException
    //   46	48	45	finally
  }
  
  public void addOrUpdateIntentSender(IntentSenderData paramIntentSenderData, int paramInt) {
    if (paramIntentSenderData == null || paramIntentSenderData.token == null)
      return; 
    synchronized (this.mIntentSenderMap) {
      IntentSenderData intentSenderData = this.mIntentSenderMap.get(paramIntentSenderData.token);
      if (intentSenderData == null) {
        this.mIntentSenderMap.put(paramIntentSenderData.token, paramIntentSenderData);
      } else {
        intentSenderData.update(paramIntentSenderData);
      } 
      return;
    } 
  }
  
  public void appDoneExecuting(String paramString, int paramInt) {
    ProcessRecord processRecord = findProcessLocked(VBinder.getCallingPid());
    if (processRecord != null)
      processRecord.pkgList.add(paramString); 
  }
  
  public boolean broadcastFinish(IBinder paramIBinder) throws RemoteException {
    synchronized (this.mPidsSelfLocked) {
      for (ProcessRecord processRecord : this.mPidsSelfLocked) {
        if (processRecord.client != null && processRecord.client.finishReceiver(paramIBinder))
          return true; 
      } 
      return false;
    } 
  }
  
  public int checkPermission(boolean paramBoolean, String paramString, int paramInt1, int paramInt2) {
    return (paramString == null) ? -1 : ("android.permission.ACCOUNT_MANAGER".equals(paramString) ? 0 : ("android.permission.RECEIVE_BOOT_COMPLETED".equals(paramString) ? 0 : ("android.permission.BACKUP".equals(paramString) ? 0 : (("android.permission.INTERACT_ACROSS_USERS".equals(paramString) || "android.permission.INTERACT_ACROSS_USERS_FULL".equals(paramString)) ? -1 : ((paramInt2 == 0) ? 0 : VPackageManagerService.get().checkUidPermission(paramBoolean, paramString, paramInt2))))));
  }
  
  public void dump() {}
  
  public ProcessRecord findProcessLocked(int paramInt) {
    for (ProcessRecord processRecord : this.mPidsSelfLocked) {
      if (processRecord.pid == paramInt)
        return processRecord; 
    } 
    return null;
  }
  
  public ProcessRecord findProcessLocked(String paramString, int paramInt) {
    for (ProcessRecord processRecord : this.mPidsSelfLocked) {
      if (processRecord.processName.equals(paramString) && processRecord.userId == paramInt)
        return processRecord; 
    } 
    return null;
  }
  
  public boolean finishActivityAffinity(int paramInt, IBinder paramIBinder) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mActivityStack : Lcom/lody/virtual/server/am/ActivityStack;
    //   6: iload_1
    //   7: aload_2
    //   8: invokevirtual finishActivityAffinity : (ILandroid/os/IBinder;)Z
    //   11: istore_3
    //   12: aload_0
    //   13: monitorexit
    //   14: iload_3
    //   15: ireturn
    //   16: astore_2
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_2
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   2	14	16	finally
    //   17	19	16	finally
  }
  
  public ComponentName getActivityClassForToken(int paramInt, IBinder paramIBinder) {
    return this.mActivityStack.getActivityClassForToken(paramInt, paramIBinder);
  }
  
  public int getAppPid(String paramString1, int paramInt, String paramString2) {
    synchronized (this.mPidsSelfLocked) {
      Object object;
      int i = this.mPidsSelfLocked.size();
      while (true) {
        int j = object - 1;
        if (object > null) {
          ProcessRecord processRecord = this.mPidsSelfLocked.get(j);
          if (processRecord.userId != paramInt)
            continue; 
          boolean bool = processRecord.info.packageName.equals(paramString1);
          if (!bool)
            continue; 
          try {
            if (processRecord.client.isAppRunning() && processRecord.info.processName.equals(paramString2)) {
              paramInt = processRecord.pid;
              return paramInt;
            } 
            break;
          } catch (Exception exception) {
            exception.printStackTrace();
          } 
          return -1;
        } 
        break;
        object = SYNTHETIC_LOCAL_VARIABLE_6;
      } 
      return -1;
    } 
  }
  
  public String getAppProcessName(int paramInt) {
    synchronized (this.mPidsSelfLocked) {
      ProcessRecord processRecord = findProcessLocked(paramInt);
      if (processRecord != null)
        return processRecord.processName; 
      return null;
    } 
  }
  
  public ComponentName getCallingActivity(int paramInt, IBinder paramIBinder) {
    return this.mActivityStack.getCallingActivity(paramInt, paramIBinder);
  }
  
  public String getCallingPackage(int paramInt, IBinder paramIBinder) {
    return this.mActivityStack.getCallingPackage(paramInt, paramIBinder);
  }
  
  public int getFreeStubCount() {
    return StubManifest.STUB_COUNT - this.mPidsSelfLocked.size();
  }
  
  public String getInitialPackage(int paramInt) {
    synchronized (this.mPidsSelfLocked) {
      ProcessRecord processRecord = findProcessLocked(paramInt);
      if (processRecord != null)
        return processRecord.info.packageName; 
      return null;
    } 
  }
  
  public IntentSenderData getIntentSender(IBinder paramIBinder) {
    if (paramIBinder != null)
      synchronized (this.mIntentSenderMap) {
        return this.mIntentSenderMap.get(paramIBinder);
      }  
    return null;
  }
  
  public String getPackageForToken(int paramInt, IBinder paramIBinder) {
    return this.mActivityStack.getPackageForToken(paramInt, paramIBinder);
  }
  
  public List<String> getProcessPkgList(int paramInt) {
    synchronized (this.mPidsSelfLocked) {
      ProcessRecord processRecord = findProcessLocked(paramInt);
      if (processRecord != null) {
        ArrayList<String> arrayList = new ArrayList();
        this((Collection)processRecord.pkgList);
        return arrayList;
      } 
      return Collections.emptyList();
    } 
  }
  
  public VParceledListSlice<ActivityManager.RunningServiceInfo> getServices(String paramString, int paramInt1, int paramInt2, int paramInt3) {
    ArrayList arrayList = new ArrayList();
    synchronized (this.mPidsSelfLocked) {
      for (ProcessRecord processRecord : this.mPidsSelfLocked) {
        if (processRecord.pkgList.contains(paramString)) {
          boolean bool = processRecord.client.asBinder().isBinderAlive();
          if (bool)
            try {
              arrayList.addAll(processRecord.client.getServices());
            } catch (RemoteException remoteException) {
              remoteException.printStackTrace();
            }  
        } 
      } 
      List list = arrayList;
      if (arrayList.size() > paramInt1)
        list = arrayList.subList(0, paramInt1); 
      return new VParceledListSlice(list);
    } 
  }
  
  public int getSystemPid() {
    return Process.myPid();
  }
  
  public int getSystemUid() {
    return Process.myUid();
  }
  
  public AppTaskInfo getTaskInfo(int paramInt) {
    return this.mActivityStack.getTaskInfo(paramInt);
  }
  
  public int getUidByPid(int paramInt) {
    synchronized (this.mPidsSelfLocked) {
      ProcessRecord processRecord = findProcessLocked(paramInt);
      if (processRecord != null) {
        paramInt = processRecord.vuid;
        return paramInt;
      } 
      return (paramInt == Process.myPid()) ? 1000 : 9000;
    } 
  }
  
  public void handleDownloadCompleteIntent(Intent paramIntent) {
    paramIntent.setPackage(null);
    paramIntent.setComponent(null);
    paramIntent = ComponentUtils.proxyBroadcastIntent(paramIntent, -1);
    VirtualCore.get().getContext().sendBroadcast(paramIntent);
  }
  
  public ClientConfig initProcess(String paramString1, String paramString2, int paramInt) {
    ProcessRecord processRecord = startProcessIfNeeded(paramString2, paramInt, paramString1, -1);
    return (processRecord != null) ? processRecord.getClientConfig() : null;
  }
  
  public boolean isAppInactive(String paramString, int paramInt) {
    synchronized (this.sIdeMap) {
      boolean bool1;
      Map<String, Boolean> map = this.sIdeMap;
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append(paramString);
      stringBuilder.append("@");
      stringBuilder.append(paramInt);
      Boolean bool = map.get(stringBuilder.toString());
      if (bool != null && !bool.booleanValue()) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      return bool1;
    } 
  }
  
  public boolean isAppPid(int paramInt) {
    synchronized (this.mPidsSelfLocked) {
      boolean bool;
      if (findProcessLocked(paramInt) != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    } 
  }
  
  public boolean isAppProcess(String paramString) {
    boolean bool;
    if (parseVPid(paramString) != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isAppRunning(String paramString, int paramInt, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mPidsSelfLocked : Ljava/util/List;
    //   4: astore #4
    //   6: aload #4
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield mPidsSelfLocked : Ljava/util/List;
    //   13: invokeinterface size : ()I
    //   18: istore #5
    //   20: iload #5
    //   22: iconst_1
    //   23: isub
    //   24: istore #6
    //   26: iload #5
    //   28: ifle -> 126
    //   31: aload_0
    //   32: getfield mPidsSelfLocked : Ljava/util/List;
    //   35: iload #6
    //   37: invokeinterface get : (I)Ljava/lang/Object;
    //   42: checkcast com/lody/virtual/server/am/ProcessRecord
    //   45: astore #7
    //   47: aload #7
    //   49: getfield userId : I
    //   52: iload_2
    //   53: if_icmpeq -> 59
    //   56: goto -> 100
    //   59: aload #7
    //   61: getfield info : Landroid/content/pm/ApplicationInfo;
    //   64: getfield packageName : Ljava/lang/String;
    //   67: aload_1
    //   68: invokevirtual equals : (Ljava/lang/Object;)Z
    //   71: ifne -> 77
    //   74: goto -> 100
    //   77: iload_3
    //   78: ifeq -> 107
    //   81: aload #7
    //   83: getfield info : Landroid/content/pm/ApplicationInfo;
    //   86: getfield processName : Ljava/lang/String;
    //   89: aload_1
    //   90: invokevirtual equals : (Ljava/lang/Object;)Z
    //   93: istore #8
    //   95: iload #8
    //   97: ifne -> 107
    //   100: iload #6
    //   102: istore #5
    //   104: goto -> 20
    //   107: aload #7
    //   109: getfield client : Lcom/lody/virtual/client/IVClient;
    //   112: invokeinterface isAppRunning : ()Z
    //   117: istore_3
    //   118: goto -> 128
    //   121: astore_1
    //   122: aload_1
    //   123: invokevirtual printStackTrace : ()V
    //   126: iconst_0
    //   127: istore_3
    //   128: aload #4
    //   130: monitorexit
    //   131: iload_3
    //   132: ireturn
    //   133: astore_1
    //   134: aload #4
    //   136: monitorexit
    //   137: aload_1
    //   138: athrow
    // Exception table:
    //   from	to	target	type
    //   9	20	133	finally
    //   31	56	133	finally
    //   59	74	133	finally
    //   81	95	133	finally
    //   107	118	121	java/lang/Exception
    //   107	118	133	finally
    //   122	126	133	finally
    //   128	131	133	finally
    //   134	137	133	finally
  }
  
  public void killAllApps() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mPidsSelfLocked : Ljava/util/List;
    //   4: astore_1
    //   5: aload_1
    //   6: monitorenter
    //   7: iconst_0
    //   8: istore_2
    //   9: iload_2
    //   10: aload_0
    //   11: getfield mPidsSelfLocked : Ljava/util/List;
    //   14: invokeinterface size : ()I
    //   19: if_icmpge -> 44
    //   22: aload_0
    //   23: getfield mPidsSelfLocked : Ljava/util/List;
    //   26: iload_2
    //   27: invokeinterface get : (I)Ljava/lang/Object;
    //   32: checkcast com/lody/virtual/server/am/ProcessRecord
    //   35: invokevirtual kill : ()V
    //   38: iinc #2, 1
    //   41: goto -> 9
    //   44: aload_1
    //   45: monitorexit
    //   46: return
    //   47: astore_3
    //   48: aload_1
    //   49: monitorexit
    //   50: aload_3
    //   51: athrow
    // Exception table:
    //   from	to	target	type
    //   9	38	47	finally
    //   44	46	47	finally
    //   48	50	47	finally
  }
  
  public void killAppByPkg(String paramString, int paramInt) {
    synchronized (this.mPidsSelfLocked) {
      for (ProcessRecord processRecord : this.mPidsSelfLocked) {
        if ((paramInt == -1 || processRecord.userId == paramInt) && processRecord.pkgList.contains(paramString))
          processRecord.kill(); 
      } 
      return;
    } 
  }
  
  public void killApplicationProcess(String paramString, int paramInt) {
    synchronized (this.mPidsSelfLocked) {
      for (ProcessRecord processRecord : this.mPidsSelfLocked) {
        if (processRecord.vuid == paramInt) {
          if (processRecord.isExt) {
            VExtPackageAccessor.forceStop(new int[] { processRecord.pid });
            continue;
          } 
          processRecord.kill();
        } 
      } 
      return;
    } 
  }
  
  public void notifyBadgerChange(BadgerInfo paramBadgerInfo) {
    Intent intent = new Intent(Constants.ACTION_BADGER_CHANGE);
    intent.putExtra("userId", paramBadgerInfo.userId);
    intent.putExtra("packageName", paramBadgerInfo.packageName);
    intent.putExtra("badgerCount", paramBadgerInfo.badgerCount);
    VirtualCore.get().getContext().sendBroadcast(intent);
  }
  
  public void onActivityCreated(IBinder paramIBinder1, IBinder paramIBinder2, int paramInt) {
    int i = Binder.getCallingPid();
    synchronized (this.mPidsSelfLocked) {
      ProcessRecord processRecord = findProcessLocked(i);
      if (processRecord != null)
        this.mActivityStack.onActivityCreated(processRecord, paramIBinder2, paramInt, (ActivityRecord)paramIBinder1); 
      return;
    } 
  }
  
  public boolean onActivityDestroyed(int paramInt, IBinder paramIBinder) {
    boolean bool;
    if (this.mActivityStack.onActivityDestroyed(paramInt, paramIBinder) != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void onActivityFinish(int paramInt, IBinder paramIBinder) {
    this.mActivityStack.onActivityFinish(paramInt, paramIBinder);
  }
  
  public void onActivityResumed(int paramInt, IBinder paramIBinder) {
    this.mActivityStack.onActivityResumed(paramInt, paramIBinder);
  }
  
  public void processRestarted(String paramString1, String paramString2, int paramInt) {
    List<ProcessRecord> list;
    String str;
    int i = VBinder.getCallingPid();
    synchronized (this.mPidsSelfLocked) {
      ProcessRecord processRecord = findProcessLocked(i);
      if (processRecord == null) {
        str = getProcessName(i);
        if (str == null)
          return; 
        i = parseVPid(str);
        if (i != -1)
          startProcessIfNeeded(paramString2, paramInt, paramString1, i); 
      } 
      return;
    } 
  }
  
  public int queryFreeStubProcess(boolean paramBoolean, Set<Integer> paramSet) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mPidsSelfLocked : Ljava/util/List;
    //   4: astore_3
    //   5: aload_3
    //   6: monitorenter
    //   7: iconst_0
    //   8: istore #4
    //   10: iload #4
    //   12: getstatic com/lody/virtual/client/stub/StubManifest.STUB_COUNT : I
    //   15: if_icmpge -> 135
    //   18: aload_0
    //   19: getfield mPidsSelfLocked : Ljava/util/List;
    //   22: invokeinterface size : ()I
    //   27: istore #5
    //   29: iload #5
    //   31: iconst_1
    //   32: isub
    //   33: istore #6
    //   35: iconst_1
    //   36: istore #7
    //   38: iload #5
    //   40: ifle -> 116
    //   43: aload_0
    //   44: getfield mPidsSelfLocked : Ljava/util/List;
    //   47: iload #6
    //   49: invokeinterface get : (I)Ljava/lang/Object;
    //   54: checkcast com/lody/virtual/server/am/ProcessRecord
    //   57: astore #8
    //   59: aload_2
    //   60: aload #8
    //   62: getfield vpid : I
    //   65: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   68: invokeinterface contains : (Ljava/lang/Object;)Z
    //   73: ifeq -> 83
    //   76: iload #7
    //   78: istore #5
    //   80: goto -> 119
    //   83: aload #8
    //   85: getfield vpid : I
    //   88: iload #4
    //   90: if_icmpne -> 109
    //   93: aload #8
    //   95: getfield isExt : Z
    //   98: iload_1
    //   99: if_icmpne -> 109
    //   102: iload #7
    //   104: istore #5
    //   106: goto -> 119
    //   109: iload #6
    //   111: istore #5
    //   113: goto -> 29
    //   116: iconst_0
    //   117: istore #5
    //   119: iload #5
    //   121: ifeq -> 130
    //   124: iinc #4, 1
    //   127: goto -> 10
    //   130: aload_3
    //   131: monitorexit
    //   132: iload #4
    //   134: ireturn
    //   135: aload_3
    //   136: monitorexit
    //   137: iconst_m1
    //   138: ireturn
    //   139: astore_2
    //   140: aload_3
    //   141: monitorexit
    //   142: aload_2
    //   143: athrow
    // Exception table:
    //   from	to	target	type
    //   10	29	139	finally
    //   43	76	139	finally
    //   83	102	139	finally
    //   130	132	139	finally
    //   135	137	139	finally
    //   140	142	139	finally
  }
  
  public void removeIntentSender(IBinder paramIBinder) {
    if (paramIBinder != null)
      synchronized (this.mIntentSenderMap) {
        this.mIntentSenderMap.remove(paramIBinder);
      }  
  }
  
  public void sendBroadcastAsUser(Intent paramIntent, VUserHandle paramVUserHandle) {
    SpecialComponentList.protectIntent(paramIntent);
    Context context = VirtualCore.get().getContext();
    if (paramVUserHandle != null)
      paramIntent.putExtra("_VA_|_user_id_", paramVUserHandle.getIdentifier()); 
    context.sendBroadcast(paramIntent);
  }
  
  public void sendBroadcastAsUser(Intent paramIntent, VUserHandle paramVUserHandle, String paramString) {
    SpecialComponentList.protectIntent(paramIntent);
    Context context = VirtualCore.get().getContext();
    if (paramVUserHandle != null)
      paramIntent.putExtra("_VA_|_user_id_", paramVUserHandle.getIdentifier()); 
    context.sendBroadcast(paramIntent);
  }
  
  public void sendOrderedBroadcastAsUser(Intent paramIntent, VUserHandle paramVUserHandle, String paramString1, BroadcastReceiver paramBroadcastReceiver, Handler paramHandler, int paramInt, String paramString2, Bundle paramBundle) {
    Context context = VirtualCore.get().getContext();
    if (paramVUserHandle != null)
      paramIntent.putExtra("_VA_|_user_id_", paramVUserHandle.getIdentifier()); 
    context.sendOrderedBroadcast(paramIntent, null, paramBroadcastReceiver, paramHandler, paramInt, paramString2, paramBundle);
  }
  
  public void setAppInactive(String paramString, boolean paramBoolean, int paramInt) {
    synchronized (this.sIdeMap) {
      Map<String, Boolean> map = this.sIdeMap;
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append(paramString);
      stringBuilder.append("@");
      stringBuilder.append(paramInt);
      map.put(stringBuilder.toString(), Boolean.valueOf(paramBoolean));
      return;
    } 
  }
  
  public int startActivities(Intent[] paramArrayOfIntent, String[] paramArrayOfString, IBinder paramIBinder, Bundle paramBundle, String paramString, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: arraylength
    //   4: anewarray android/content/pm/ActivityInfo
    //   7: astore_2
    //   8: iconst_0
    //   9: istore #7
    //   11: iload #7
    //   13: aload_1
    //   14: arraylength
    //   15: if_icmpge -> 59
    //   18: invokestatic get : ()Lcom/lody/virtual/client/core/VirtualCore;
    //   21: aload_1
    //   22: iload #7
    //   24: aaload
    //   25: iload #6
    //   27: invokevirtual resolveActivityInfo : (Landroid/content/Intent;I)Landroid/content/pm/ActivityInfo;
    //   30: astore #5
    //   32: aload #5
    //   34: ifnonnull -> 47
    //   37: getstatic com/lody/virtual/helper/compat/ActivityManagerCompat.START_INTENT_NOT_RESOLVED : I
    //   40: istore #6
    //   42: aload_0
    //   43: monitorexit
    //   44: iload #6
    //   46: ireturn
    //   47: aload_2
    //   48: iload #7
    //   50: aload #5
    //   52: aastore
    //   53: iinc #7, 1
    //   56: goto -> 11
    //   59: aload_0
    //   60: getfield mActivityStack : Lcom/lody/virtual/server/am/ActivityStack;
    //   63: iload #6
    //   65: aload_1
    //   66: aload_2
    //   67: aload_3
    //   68: aload #4
    //   70: invokevirtual startActivitiesLocked : (I[Landroid/content/Intent;[Landroid/content/pm/ActivityInfo;Landroid/os/IBinder;Landroid/os/Bundle;)I
    //   73: istore #6
    //   75: aload_0
    //   76: monitorexit
    //   77: iload #6
    //   79: ireturn
    //   80: astore_1
    //   81: aload_0
    //   82: monitorexit
    //   83: aload_1
    //   84: athrow
    // Exception table:
    //   from	to	target	type
    //   2	8	80	finally
    //   11	32	80	finally
    //   37	44	80	finally
    //   59	77	80	finally
    //   81	83	80	finally
  }
  
  public int startActivity(Intent paramIntent, ActivityInfo paramActivityInfo, IBinder paramIBinder, Bundle paramBundle, String paramString1, int paramInt1, String paramString2, int paramInt2) {
    /* monitor enter ThisExpression{ObjectType{com/lody/virtual/server/am/VActivityManagerService}} */
    try {
    
    } finally {
      paramIntent = null;
      RuntimeException runtimeException = new RuntimeException();
      this((Throwable)paramIntent);
    } 
    /* monitor exit ThisExpression{ObjectType{com/lody/virtual/server/am/VActivityManagerService}} */
    throw paramIntent;
  }
  
  public int startActivityFromHistory(Intent paramIntent) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mActivityStack : Lcom/lody/virtual/server/am/ActivityStack;
    //   6: aload_1
    //   7: invokevirtual startActivityFromHistoryLocked : (Landroid/content/Intent;)I
    //   10: istore_2
    //   11: aload_0
    //   12: monitorexit
    //   13: iload_2
    //   14: ireturn
    //   15: astore_1
    //   16: aload_0
    //   17: monitorexit
    //   18: aload_1
    //   19: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	15	finally
    //   16	18	15	finally
  }
  
  ProcessRecord startProcessIfNeeded(String paramString1, int paramInt1, String paramString2, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial runProcessGC : ()V
    //   4: aload_3
    //   5: invokestatic getSetting : (Ljava/lang/String;)Lcom/lody/virtual/server/pm/PackageSetting;
    //   8: astore #5
    //   10: aload #5
    //   12: invokevirtual isRunInExtProcess : ()Z
    //   15: istore #6
    //   17: iload #6
    //   19: ifeq -> 69
    //   22: invokestatic get : ()Lcom/lody/virtual/client/core/VirtualCore;
    //   25: invokevirtual isExtPackageInstalled : ()Z
    //   28: ifne -> 69
    //   31: getstatic com/lody/virtual/server/am/VActivityManagerService.TAG : Ljava/lang/String;
    //   34: astore #7
    //   36: new java/lang/StringBuilder
    //   39: dup
    //   40: invokespecial <init> : ()V
    //   43: astore_1
    //   44: aload_1
    //   45: ldc_w 'startProcessIfNeeded failed due to ext package not install...packageName:'
    //   48: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   51: pop
    //   52: aload_1
    //   53: aload_3
    //   54: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   57: pop
    //   58: aload #7
    //   60: aload_1
    //   61: invokevirtual toString : ()Ljava/lang/String;
    //   64: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   67: aconst_null
    //   68: areturn
    //   69: invokestatic get : ()Lcom/lody/virtual/server/pm/VPackageManagerService;
    //   72: aload_3
    //   73: iconst_0
    //   74: iload_2
    //   75: invokevirtual getApplicationInfo : (Ljava/lang/String;II)Landroid/content/pm/ApplicationInfo;
    //   78: astore #7
    //   80: aload #5
    //   82: ifnull -> 401
    //   85: aload #7
    //   87: ifnonnull -> 93
    //   90: goto -> 401
    //   93: aload #5
    //   95: iload_2
    //   96: invokevirtual isLaunched : (I)Z
    //   99: ifne -> 115
    //   102: aload #5
    //   104: iload_2
    //   105: iconst_1
    //   106: invokevirtual setLaunched : (IZ)V
    //   109: invokestatic get : ()Lcom/lody/virtual/server/pm/VAppManagerService;
    //   112: invokevirtual savePersistenceData : ()V
    //   115: iload_2
    //   116: aload #5
    //   118: getfield appId : I
    //   121: invokestatic getUid : (II)I
    //   124: istore #8
    //   126: aload_0
    //   127: monitorenter
    //   128: iload #4
    //   130: iconst_m1
    //   131: if_icmpeq -> 192
    //   134: new com/lody/virtual/server/am/ProcessRecord
    //   137: astore_3
    //   138: aload_3
    //   139: aload #7
    //   141: aload_1
    //   142: iload #8
    //   144: iload #4
    //   146: iload #6
    //   148: invokespecial <init> : (Landroid/content/pm/ApplicationInfo;Ljava/lang/String;IIZ)V
    //   151: aload_0
    //   152: aload_3
    //   153: invokespecial initProcessLocked : (Lcom/lody/virtual/server/am/ProcessRecord;)Z
    //   156: ifeq -> 188
    //   159: aload_0
    //   160: getfield mPidsSelfLocked : Ljava/util/List;
    //   163: astore_1
    //   164: aload_1
    //   165: monitorenter
    //   166: aload_0
    //   167: getfield mPidsSelfLocked : Ljava/util/List;
    //   170: aload_3
    //   171: invokeinterface add : (Ljava/lang/Object;)Z
    //   176: pop
    //   177: aload_1
    //   178: monitorexit
    //   179: aload_0
    //   180: monitorexit
    //   181: aload_3
    //   182: areturn
    //   183: astore_3
    //   184: aload_1
    //   185: monitorexit
    //   186: aload_3
    //   187: athrow
    //   188: aload_0
    //   189: monitorexit
    //   190: aconst_null
    //   191: areturn
    //   192: aload_0
    //   193: getfield mPidsSelfLocked : Ljava/util/List;
    //   196: astore_3
    //   197: aload_3
    //   198: monitorenter
    //   199: aload_0
    //   200: aload_1
    //   201: iload_2
    //   202: invokevirtual findProcessLocked : (Ljava/lang/String;I)Lcom/lody/virtual/server/am/ProcessRecord;
    //   205: astore #5
    //   207: aload_3
    //   208: monitorexit
    //   209: aload #5
    //   211: ifnull -> 219
    //   214: aload_0
    //   215: monitorexit
    //   216: aload #5
    //   218: areturn
    //   219: aload_1
    //   220: ldc_w 'com.google.android.gms.persistent'
    //   223: invokevirtual equals : (Ljava/lang/Object;)Z
    //   226: ifeq -> 259
    //   229: new android/content/Intent
    //   232: astore_3
    //   233: aload_3
    //   234: ldc_w 'android.intent.action.GMS_INITIALIZED'
    //   237: invokespecial <init> : (Ljava/lang/String;)V
    //   240: aload_3
    //   241: ldc_w 'android.intent.extra.user_handle'
    //   244: iload_2
    //   245: invokevirtual putExtra : (Ljava/lang/String;I)Landroid/content/Intent;
    //   248: pop
    //   249: invokestatic get : ()Lcom/lody/virtual/client/core/VirtualCore;
    //   252: invokevirtual getContext : ()Landroid/content/Context;
    //   255: aload_3
    //   256: invokevirtual sendBroadcast : (Landroid/content/Intent;)V
    //   259: new java/util/HashSet
    //   262: astore #5
    //   264: iconst_3
    //   265: istore_2
    //   266: aload #5
    //   268: iconst_3
    //   269: invokespecial <init> : (I)V
    //   272: iload_2
    //   273: ifle -> 387
    //   276: aload_0
    //   277: iload #6
    //   279: aload #5
    //   281: invokevirtual queryFreeStubProcess : (ZLjava/util/Set;)I
    //   284: istore #4
    //   286: iload #4
    //   288: iconst_m1
    //   289: if_icmpne -> 314
    //   292: aload_0
    //   293: invokevirtual killAllApps : ()V
    //   296: getstatic com/lody/virtual/server/am/VActivityManagerService.TAG : Ljava/lang/String;
    //   299: ldc_w 'no free vpid, run GC now...'
    //   302: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   305: ldc2_w 500
    //   308: invokestatic sleep : (J)V
    //   311: goto -> 381
    //   314: new com/lody/virtual/server/am/ProcessRecord
    //   317: astore_3
    //   318: aload_3
    //   319: aload #7
    //   321: aload_1
    //   322: iload #8
    //   324: iload #4
    //   326: iload #6
    //   328: invokespecial <init> : (Landroid/content/pm/ApplicationInfo;Ljava/lang/String;IIZ)V
    //   331: aload_0
    //   332: aload_3
    //   333: invokespecial initProcessLocked : (Lcom/lody/virtual/server/am/ProcessRecord;)Z
    //   336: ifeq -> 368
    //   339: aload_0
    //   340: getfield mPidsSelfLocked : Ljava/util/List;
    //   343: astore_1
    //   344: aload_1
    //   345: monitorenter
    //   346: aload_0
    //   347: getfield mPidsSelfLocked : Ljava/util/List;
    //   350: aload_3
    //   351: invokeinterface add : (Ljava/lang/Object;)Z
    //   356: pop
    //   357: aload_1
    //   358: monitorexit
    //   359: aload_0
    //   360: monitorexit
    //   361: aload_3
    //   362: areturn
    //   363: astore_3
    //   364: aload_1
    //   365: monitorexit
    //   366: aload_3
    //   367: athrow
    //   368: aload #5
    //   370: iload #4
    //   372: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   375: invokeinterface add : (Ljava/lang/Object;)Z
    //   380: pop
    //   381: iinc #2, -1
    //   384: goto -> 272
    //   387: aload_0
    //   388: monitorexit
    //   389: aconst_null
    //   390: areturn
    //   391: astore_1
    //   392: aload_3
    //   393: monitorexit
    //   394: aload_1
    //   395: athrow
    //   396: astore_1
    //   397: aload_0
    //   398: monitorexit
    //   399: aload_1
    //   400: athrow
    //   401: getstatic com/lody/virtual/server/am/VActivityManagerService.TAG : Ljava/lang/String;
    //   404: astore_1
    //   405: new java/lang/StringBuilder
    //   408: dup
    //   409: invokespecial <init> : ()V
    //   412: astore #7
    //   414: aload #7
    //   416: ldc_w 'startProcessIfNeeded failed due to app not install...packageName:'
    //   419: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   422: pop
    //   423: aload #7
    //   425: aload_3
    //   426: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   429: pop
    //   430: aload_1
    //   431: aload #7
    //   433: invokevirtual toString : ()Ljava/lang/String;
    //   436: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   439: aconst_null
    //   440: areturn
    // Exception table:
    //   from	to	target	type
    //   134	166	396	finally
    //   166	179	183	finally
    //   179	181	396	finally
    //   184	186	183	finally
    //   186	188	396	finally
    //   188	190	396	finally
    //   192	199	396	finally
    //   199	209	391	finally
    //   214	216	396	finally
    //   219	259	396	finally
    //   259	264	396	finally
    //   266	272	396	finally
    //   276	286	396	finally
    //   292	311	396	finally
    //   314	346	396	finally
    //   346	359	363	finally
    //   359	361	396	finally
    //   364	366	363	finally
    //   366	368	396	finally
    //   368	381	396	finally
    //   387	389	396	finally
    //   392	394	391	finally
    //   394	396	396	finally
    //   397	399	396	finally
  }
  
  public int stopUser(int paramInt, IStopUserCallback.Stub paramStub) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mPidsSelfLocked : Ljava/util/List;
    //   4: astore_3
    //   5: aload_3
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield mPidsSelfLocked : Ljava/util/List;
    //   11: invokeinterface size : ()I
    //   16: istore #4
    //   18: iload #4
    //   20: iconst_1
    //   21: isub
    //   22: istore #5
    //   24: iload #4
    //   26: ifle -> 66
    //   29: aload_0
    //   30: getfield mPidsSelfLocked : Ljava/util/List;
    //   33: iload #5
    //   35: invokeinterface get : (I)Ljava/lang/Object;
    //   40: checkcast com/lody/virtual/server/am/ProcessRecord
    //   43: astore #6
    //   45: aload #6
    //   47: getfield userId : I
    //   50: iload_1
    //   51: if_icmpne -> 59
    //   54: aload #6
    //   56: invokevirtual kill : ()V
    //   59: iload #5
    //   61: istore #4
    //   63: goto -> 18
    //   66: aload_3
    //   67: monitorexit
    //   68: aload_2
    //   69: iload_1
    //   70: invokevirtual userStopped : (I)V
    //   73: goto -> 81
    //   76: astore_2
    //   77: aload_2
    //   78: invokevirtual printStackTrace : ()V
    //   81: iconst_0
    //   82: ireturn
    //   83: astore_2
    //   84: aload_3
    //   85: monitorexit
    //   86: aload_2
    //   87: athrow
    // Exception table:
    //   from	to	target	type
    //   7	18	83	finally
    //   29	59	83	finally
    //   66	68	83	finally
    //   68	73	76	android/os/RemoteException
    //   84	86	83	finally
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\am\VActivityManagerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */