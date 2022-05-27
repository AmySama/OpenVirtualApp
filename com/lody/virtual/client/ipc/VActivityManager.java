package com.lody.virtual.client.ipc;

import android.app.Activity;
import android.app.IServiceConnection;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.client.hook.secondary.ServiceConnectionProxy;
import com.lody.virtual.client.stub.IntentBuilder;
import com.lody.virtual.helper.compat.ActivityManagerCompat;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.remote.AppTaskInfo;
import com.lody.virtual.remote.BadgerInfo;
import com.lody.virtual.remote.ClientConfig;
import com.lody.virtual.remote.IntentSenderData;
import com.lody.virtual.remote.VParceledListSlice;
import com.lody.virtual.server.IBinderProxyService;
import com.lody.virtual.server.interfaces.IActivityManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import mirror.android.app.Activity;
import mirror.android.app.ActivityThread;
import mirror.android.content.ContentProviderNative;

public class VActivityManager {
  private static final VActivityManager sAM = new VActivityManager();
  
  private static final Map<ServiceConnection, ServiceConnectionDelegate> sServiceConnectionDelegates = new HashMap<ServiceConnection, ServiceConnectionDelegate>();
  
  private IActivityManager mService;
  
  public static VActivityManager get() {
    return sAM;
  }
  
  private Object getRemoteInterface() {
    return IActivityManager.Stub.asInterface(ServiceManagerNative.getService("activity"));
  }
  
  public IInterface acquireProviderClient(int paramInt, ProviderInfo paramProviderInfo) throws RemoteException {
    IBinder iBinder = getService().acquireProviderClient(paramInt, paramProviderInfo);
    return (iBinder != null) ? (IInterface)ContentProviderNative.asInterface.call(new Object[] { iBinder }) : null;
  }
  
  public void addOrUpdateIntentSender(IntentSenderData paramIntentSenderData) throws RemoteException {
    getService().addOrUpdateIntentSender(paramIntentSenderData, VUserHandle.myUserId());
  }
  
  public void appDoneExecuting(String paramString) {
    try {
      getService().appDoneExecuting(paramString, VUserHandle.myUserId());
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public boolean bindService(Context paramContext, Intent paramIntent, ServiceConnection paramServiceConnection, int paramInt1, int paramInt2) {
    if (VirtualCore.get().isServerProcess()) {
      paramIntent.putExtra("_VA_|_user_id_", paramInt2);
      return paramContext.bindService(paramIntent, paramServiceConnection, paramInt1);
    } 
    ServiceConnectionDelegate serviceConnectionDelegate = getDelegate(paramServiceConnection);
    ServiceInfo serviceInfo = VirtualCore.get().resolveServiceInfo(paramIntent, paramInt2);
    if (serviceInfo != null) {
      ClientConfig clientConfig = get().initProcess(serviceInfo.packageName, serviceInfo.processName, paramInt2);
      IServiceConnection iServiceConnection = ServiceConnectionProxy.getDispatcher(paramContext, serviceConnectionDelegate, paramInt1);
      return paramContext.bindService(IntentBuilder.createBindProxyServiceIntent(clientConfig.vpid, clientConfig.isExt, serviceInfo, paramIntent, paramInt1, paramInt2, iServiceConnection), serviceConnectionDelegate, paramInt1);
    } 
    return false;
  }
  
  public boolean broadcastFinish(IBinder paramIBinder) {
    try {
      return getService().broadcastFinish(paramIBinder);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public int checkPermission(String paramString, int paramInt1, int paramInt2) {
    try {
      return getService().checkPermission(VirtualCore.get().isExtPackage(), paramString, paramInt1, paramInt2);
    } catch (RemoteException remoteException) {
      return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
    } 
  }
  
  public Activity findActivityByToken(IBinder paramIBinder) {
    paramIBinder = (IBinder)((Map)ActivityThread.mActivities.get(VirtualCore.mainThread())).get(paramIBinder);
    return (paramIBinder != null) ? (Activity)ActivityThread.ActivityClientRecord.activity.get(paramIBinder) : null;
  }
  
  public void finishActivity(IBinder paramIBinder) {
    Activity activity1 = findActivityByToken(paramIBinder);
    Activity activity2 = activity1;
    if (activity1 == null) {
      VLog.e("VActivityManager", "finishActivity fail : activity = null");
      return;
    } 
    while (true) {
      activity1 = (Activity)Activity.mParent.get(activity2);
      if (activity1 == null) {
        ActivityManagerCompat.finishActivity(paramIBinder, Activity.mResultCode.get(activity2), (Intent)Activity.mResultData.get(activity2));
        Activity.mFinished.set(activity2, true);
        return;
      } 
      activity2 = activity1;
    } 
  }
  
  public boolean finishActivityAffinity(int paramInt, IBinder paramIBinder) {
    try {
      return getService().finishActivityAffinity(paramInt, paramIBinder);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public ComponentName getActivityForToken(IBinder paramIBinder) {
    try {
      return getService().getActivityClassForToken(VUserHandle.myUserId(), paramIBinder);
    } catch (RemoteException remoteException) {
      return (ComponentName)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public int getAppPid(String paramString1, int paramInt, String paramString2) {
    try {
      return getService().getAppPid(paramString1, paramInt, paramString2);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
      return -1;
    } 
  }
  
  public String getAppProcessName(int paramInt) {
    try {
      return getService().getAppProcessName(paramInt);
    } catch (RemoteException remoteException) {
      return (String)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public ComponentName getCallingActivity(IBinder paramIBinder) {
    try {
      return getService().getCallingActivity(VUserHandle.myUserId(), paramIBinder);
    } catch (RemoteException remoteException) {
      return (ComponentName)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public String getCallingPackage(IBinder paramIBinder) {
    try {
      return getService().getCallingPackage(VUserHandle.myUserId(), paramIBinder);
    } catch (RemoteException remoteException) {
      return (String)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public ServiceConnectionDelegate getDelegate(ServiceConnection paramServiceConnection) {
    ServiceConnectionDelegate serviceConnectionDelegate1 = sServiceConnectionDelegates.get(paramServiceConnection);
    ServiceConnectionDelegate serviceConnectionDelegate2 = serviceConnectionDelegate1;
    if (serviceConnectionDelegate1 == null) {
      serviceConnectionDelegate2 = new ServiceConnectionDelegate(paramServiceConnection);
      sServiceConnectionDelegates.put(paramServiceConnection, serviceConnectionDelegate2);
    } 
    return serviceConnectionDelegate2;
  }
  
  public String getInitialPackage(int paramInt) {
    try {
      return getService().getInitialPackage(paramInt);
    } catch (RemoteException remoteException) {
      return (String)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public IntentSenderData getIntentSender(IBinder paramIBinder) {
    try {
      return getService().getIntentSender(paramIBinder);
    } catch (RemoteException remoteException) {
      return (IntentSenderData)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public String getPackageForToken(IBinder paramIBinder) {
    try {
      return getService().getPackageForToken(VUserHandle.myUserId(), paramIBinder);
    } catch (RemoteException remoteException) {
      return (String)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public List<String> getProcessPkgList(int paramInt) {
    try {
      return getService().getProcessPkgList(paramInt);
    } catch (RemoteException remoteException) {
      return (List<String>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public IActivityManager getService() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mService : Lcom/lody/virtual/server/interfaces/IActivityManager;
    //   4: invokestatic isAlive : (Landroid/os/IInterface;)Z
    //   7: ifne -> 41
    //   10: ldc com/lody/virtual/client/ipc/VActivityManager
    //   12: monitorenter
    //   13: aload_0
    //   14: ldc com/lody/virtual/server/interfaces/IActivityManager
    //   16: aload_0
    //   17: invokespecial getRemoteInterface : ()Ljava/lang/Object;
    //   20: invokestatic genProxy : (Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    //   23: checkcast com/lody/virtual/server/interfaces/IActivityManager
    //   26: putfield mService : Lcom/lody/virtual/server/interfaces/IActivityManager;
    //   29: ldc com/lody/virtual/client/ipc/VActivityManager
    //   31: monitorexit
    //   32: goto -> 41
    //   35: astore_1
    //   36: ldc com/lody/virtual/client/ipc/VActivityManager
    //   38: monitorexit
    //   39: aload_1
    //   40: athrow
    //   41: aload_0
    //   42: getfield mService : Lcom/lody/virtual/server/interfaces/IActivityManager;
    //   45: areturn
    // Exception table:
    //   from	to	target	type
    //   13	32	35	finally
    //   36	39	35	finally
  }
  
  public VParceledListSlice getServices(String paramString, int paramInt1, int paramInt2) {
    try {
      return getService().getServices(paramString, paramInt1, paramInt2, VUserHandle.myUserId());
    } catch (RemoteException remoteException) {
      return (VParceledListSlice)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public int getSystemPid() {
    try {
      return getService().getSystemPid();
    } catch (RemoteException remoteException) {
      return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
    } 
  }
  
  public int getSystemUid() {
    try {
      return getService().getSystemUid();
    } catch (RemoteException remoteException) {
      return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
    } 
  }
  
  public AppTaskInfo getTaskInfo(int paramInt) {
    try {
      return getService().getTaskInfo(paramInt);
    } catch (RemoteException remoteException) {
      return (AppTaskInfo)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public int getUid() {
    return VClient.get().getVUid();
  }
  
  public int getUidByPid(int paramInt) {
    try {
      return getService().getUidByPid(paramInt);
    } catch (RemoteException remoteException) {
      return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
    } 
  }
  
  public void handleDownloadCompleteIntent(Intent paramIntent) {
    try {
      getService().handleDownloadCompleteIntent(paramIntent);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public ClientConfig initProcess(String paramString1, String paramString2, int paramInt) {
    try {
      return getService().initProcess(paramString1, paramString2, paramInt);
    } catch (RemoteException remoteException) {
      return (ClientConfig)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public boolean isAppInactive(String paramString, int paramInt) {
    try {
      return getService().isAppInactive(paramString, paramInt);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public boolean isAppPid(int paramInt) {
    try {
      return getService().isAppPid(paramInt);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public boolean isAppProcess(String paramString) {
    try {
      return getService().isAppProcess(paramString);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public boolean isAppRunning(String paramString, int paramInt, boolean paramBoolean) {
    try {
      return getService().isAppRunning(paramString, paramInt, paramBoolean);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public void killAllApps() {
    try {
      getService().killAllApps();
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public void killAppByPkg(String paramString, int paramInt) {
    try {
      getService().killAppByPkg(paramString, paramInt);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public void killApplicationProcess(String paramString, int paramInt) {
    try {
      getService().killApplicationProcess(paramString, paramInt);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public boolean launchApp(int paramInt, String paramString) {
    return launchApp(paramInt, paramString, true);
  }
  
  public boolean launchApp(int paramInt, String paramString, boolean paramBoolean) {
    // Byte code:
    //   0: invokestatic get : ()Lcom/lody/virtual/client/core/VirtualCore;
    //   3: aload_2
    //   4: invokevirtual isRunInExtProcess : (Ljava/lang/String;)Z
    //   7: istore #4
    //   9: iconst_0
    //   10: istore #5
    //   12: iload #4
    //   14: ifeq -> 25
    //   17: invokestatic hasExtPackageBootPermission : ()Z
    //   20: ifne -> 25
    //   23: iconst_0
    //   24: ireturn
    //   25: invokestatic get : ()Lcom/lody/virtual/client/core/VirtualCore;
    //   28: invokevirtual getContext : ()Landroid/content/Context;
    //   31: astore #6
    //   33: invokestatic get : ()Lcom/lody/virtual/client/ipc/VPackageManager;
    //   36: astore #7
    //   38: new android/content/Intent
    //   41: dup
    //   42: ldc_w 'android.intent.action.MAIN'
    //   45: invokespecial <init> : (Ljava/lang/String;)V
    //   48: astore #8
    //   50: aload #8
    //   52: ldc_w 'android.intent.category.INFO'
    //   55: invokevirtual addCategory : (Ljava/lang/String;)Landroid/content/Intent;
    //   58: pop
    //   59: aload #8
    //   61: aload_2
    //   62: invokevirtual setPackage : (Ljava/lang/String;)Landroid/content/Intent;
    //   65: pop
    //   66: aload #7
    //   68: aload #8
    //   70: aload #8
    //   72: aload #6
    //   74: invokevirtual resolveType : (Landroid/content/Context;)Ljava/lang/String;
    //   77: iconst_0
    //   78: iload_1
    //   79: invokevirtual queryIntentActivities : (Landroid/content/Intent;Ljava/lang/String;II)Ljava/util/List;
    //   82: astore #9
    //   84: aload #9
    //   86: ifnull -> 103
    //   89: aload #9
    //   91: astore #10
    //   93: aload #9
    //   95: invokeinterface size : ()I
    //   100: ifgt -> 145
    //   103: aload #8
    //   105: ldc_w 'android.intent.category.INFO'
    //   108: invokevirtual removeCategory : (Ljava/lang/String;)V
    //   111: aload #8
    //   113: ldc_w 'android.intent.category.LAUNCHER'
    //   116: invokevirtual addCategory : (Ljava/lang/String;)Landroid/content/Intent;
    //   119: pop
    //   120: aload #8
    //   122: aload_2
    //   123: invokevirtual setPackage : (Ljava/lang/String;)Landroid/content/Intent;
    //   126: pop
    //   127: aload #7
    //   129: aload #8
    //   131: aload #8
    //   133: aload #6
    //   135: invokevirtual resolveType : (Landroid/content/Context;)Ljava/lang/String;
    //   138: iconst_0
    //   139: iload_1
    //   140: invokevirtual queryIntentActivities : (Landroid/content/Intent;Ljava/lang/String;II)Ljava/util/List;
    //   143: astore #10
    //   145: iload #5
    //   147: istore #4
    //   149: aload #10
    //   151: ifnull -> 293
    //   154: aload #10
    //   156: invokeinterface size : ()I
    //   161: ifgt -> 171
    //   164: iload #5
    //   166: istore #4
    //   168: goto -> 293
    //   171: aload #10
    //   173: iconst_0
    //   174: invokeinterface get : (I)Ljava/lang/Object;
    //   179: checkcast android/content/pm/ResolveInfo
    //   182: getfield activityInfo : Landroid/content/pm/ActivityInfo;
    //   185: astore_2
    //   186: new android/content/Intent
    //   189: dup
    //   190: aload #8
    //   192: invokespecial <init> : (Landroid/content/Intent;)V
    //   195: astore #10
    //   197: aload #10
    //   199: ldc_w 268435456
    //   202: invokevirtual setFlags : (I)Landroid/content/Intent;
    //   205: pop
    //   206: aload #10
    //   208: aload_2
    //   209: getfield packageName : Ljava/lang/String;
    //   212: aload_2
    //   213: getfield name : Ljava/lang/String;
    //   216: invokevirtual setClassName : (Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   219: pop
    //   220: iconst_1
    //   221: istore #4
    //   223: iload_3
    //   224: ifeq -> 283
    //   227: invokestatic get : ()Lcom/lody/virtual/client/ipc/VActivityManager;
    //   230: aload_2
    //   231: getfield packageName : Ljava/lang/String;
    //   234: iload_1
    //   235: iconst_1
    //   236: invokevirtual isAppRunning : (Ljava/lang/String;IZ)Z
    //   239: ifeq -> 245
    //   242: goto -> 283
    //   245: aload #10
    //   247: ldc_w 65536
    //   250: invokevirtual addFlags : (I)Landroid/content/Intent;
    //   253: pop
    //   254: iload_1
    //   255: aload_2
    //   256: invokestatic previewActivity : (ILandroid/content/pm/ActivityInfo;)V
    //   259: invokestatic getUIHandler : ()Landroid/os/Handler;
    //   262: new com/lody/virtual/client/ipc/VActivityManager$1
    //   265: dup
    //   266: aload_0
    //   267: aload #10
    //   269: iload_1
    //   270: invokespecial <init> : (Lcom/lody/virtual/client/ipc/VActivityManager;Landroid/content/Intent;I)V
    //   273: ldc2_w 400
    //   276: invokevirtual postDelayed : (Ljava/lang/Runnable;J)Z
    //   279: pop
    //   280: goto -> 293
    //   283: invokestatic get : ()Lcom/lody/virtual/client/ipc/VActivityManager;
    //   286: aload #10
    //   288: iload_1
    //   289: invokevirtual startActivity : (Landroid/content/Intent;I)I
    //   292: pop
    //   293: iload #4
    //   295: ireturn
  }
  
  public void notifyBadgerChange(BadgerInfo paramBadgerInfo) {
    try {
      getService().notifyBadgerChange(paramBadgerInfo);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void onActivityCreate(IBinder paramIBinder1, IBinder paramIBinder2, int paramInt) {
    try {
      getService().onActivityCreated(paramIBinder1, paramIBinder2, paramInt);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public boolean onActivityDestroy(IBinder paramIBinder) {
    try {
      return getService().onActivityDestroyed(VUserHandle.myUserId(), paramIBinder);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public void onActivityResumed(IBinder paramIBinder) {
    try {
      getService().onActivityResumed(VUserHandle.myUserId(), paramIBinder);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public void onFinishActivity(IBinder paramIBinder) {
    try {
      getService().onActivityFinish(VUserHandle.myUserId(), paramIBinder);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void processRestarted(String paramString1, String paramString2, int paramInt) {
    try {
      getService().processRestarted(paramString1, paramString2, paramInt);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public ServiceConnection removeDelegate(ServiceConnection paramServiceConnection) {
    Iterator<ServiceConnectionDelegate> iterator = sServiceConnectionDelegates.values().iterator();
    while (iterator.hasNext()) {
      ServiceConnectionDelegate serviceConnectionDelegate = iterator.next();
      if (paramServiceConnection == serviceConnectionDelegate) {
        iterator.remove();
        return serviceConnectionDelegate;
      } 
    } 
    return paramServiceConnection;
  }
  
  public void removeIntentSender(IBinder paramIBinder) throws RemoteException {
    getService().removeIntentSender(paramIBinder);
  }
  
  public void sendActivityResult(IBinder paramIBinder, String paramString, int paramInt1, Intent paramIntent, int paramInt2) {
    if (findActivityByToken(paramIBinder) != null) {
      Object object = VirtualCore.mainThread();
      ActivityThread.sendActivityResult.call(object, new Object[] { paramIBinder, paramString, Integer.valueOf(paramInt1), paramIntent, Integer.valueOf(paramInt2) });
    } 
  }
  
  public void sendBroadcast(Intent paramIntent, int paramInt) {
    paramIntent = ComponentUtils.proxyBroadcastIntent(paramIntent, paramInt);
    if (paramIntent != null)
      VirtualCore.get().getContext().sendBroadcast(paramIntent); 
  }
  
  public void sendCancelActivityResult(IBinder paramIBinder, String paramString, int paramInt) {
    sendActivityResult(paramIBinder, paramString, paramInt, null, 0);
  }
  
  public void setAppInactive(String paramString, boolean paramBoolean, int paramInt) {
    try {
      getService().setAppInactive(paramString, paramBoolean, paramInt);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public int startActivities(Intent[] paramArrayOfIntent, String[] paramArrayOfString, IBinder paramIBinder, Bundle paramBundle, String paramString, int paramInt) {
    try {
      return getService().startActivities(paramArrayOfIntent, paramArrayOfString, paramIBinder, paramBundle, paramString, paramInt);
    } catch (RemoteException remoteException) {
      return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
    } 
  }
  
  public int startActivity(Intent paramIntent, int paramInt) {
    if (paramInt < 0)
      return ActivityManagerCompat.START_NOT_CURRENT_USER_ACTIVITY; 
    ActivityInfo activityInfo = VirtualCore.get().resolveActivityInfo(paramIntent, paramInt);
    return (activityInfo == null) ? ActivityManagerCompat.START_INTENT_NOT_RESOLVED : startActivity(paramIntent, activityInfo, null, null, null, -1, null, paramInt);
  }
  
  public int startActivity(Intent paramIntent, ActivityInfo paramActivityInfo, IBinder paramIBinder, Bundle paramBundle, String paramString1, int paramInt1, String paramString2, int paramInt2) {
    if (paramActivityInfo == null) {
      paramActivityInfo = VirtualCore.get().resolveActivityInfo(paramIntent, paramInt2);
      if (paramActivityInfo == null)
        return ActivityManagerCompat.START_INTENT_NOT_RESOLVED; 
    } 
    try {
      return getService().startActivity(paramIntent, paramActivityInfo, paramIBinder, paramBundle, paramString1, paramInt1, paramString2, paramInt2);
    } catch (RemoteException remoteException) {
      return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
    } 
  }
  
  public int startActivityFromHistory(Intent paramIntent) {
    try {
      return getService().startActivityFromHistory(paramIntent);
    } catch (RemoteException remoteException) {
      return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
    } 
  }
  
  public ComponentName startService(Context paramContext, Intent paramIntent, int paramInt) {
    if (VirtualCore.get().isServerProcess()) {
      paramIntent.putExtra("_VA_|_user_id_", paramInt);
      return paramContext.startService(paramIntent);
    } 
    ServiceInfo serviceInfo = VirtualCore.get().resolveServiceInfo(paramIntent, paramInt);
    if (serviceInfo != null) {
      ClientConfig clientConfig = get().initProcess(serviceInfo.packageName, serviceInfo.processName, paramInt);
      return paramContext.startService(IntentBuilder.createStartProxyServiceIntent(clientConfig.vpid, clientConfig.isExt, serviceInfo, paramIntent, paramInt));
    } 
    return null;
  }
  
  public void unbindService(Context paramContext, ServiceConnection paramServiceConnection) {
    paramContext.unbindService(removeDelegate(paramServiceConnection));
  }
  
  private static class ServiceConnectionDelegate implements ServiceConnection {
    private ServiceConnection mBase;
    
    public ServiceConnectionDelegate(ServiceConnection param1ServiceConnection) {
      this.mBase = param1ServiceConnection;
    }
    
    public void onServiceConnected(ComponentName param1ComponentName, IBinder param1IBinder) {
      IBinderProxyService iBinderProxyService = IBinderProxyService.Stub.asInterface(param1IBinder);
      if (iBinderProxyService != null) {
        try {
          this.mBase.onServiceConnected(iBinderProxyService.getComponent(), iBinderProxyService.getService());
        } catch (RemoteException remoteException) {
          remoteException.printStackTrace();
        } 
      } else {
        this.mBase.onServiceConnected((ComponentName)remoteException, param1IBinder);
      } 
    }
    
    public void onServiceDisconnected(ComponentName param1ComponentName) {
      this.mBase.onServiceDisconnected(param1ComponentName);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\ipc\VActivityManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */