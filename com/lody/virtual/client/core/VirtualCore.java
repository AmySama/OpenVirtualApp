package com.lody.virtual.client.core;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.ConditionVariable;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import com.lody.virtual.R;
import com.lody.virtual.SandXposed;
import com.lody.virtual.client.NativeEngine;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.env.Constants;
import com.lody.virtual.client.env.HostPackageManager;
import com.lody.virtual.client.env.LocalPackageCache;
import com.lody.virtual.client.env.SpecialComponentList;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.client.hook.delegate.TaskDescriptionDelegate;
import com.lody.virtual.client.ipc.ServiceManagerNative;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.client.ipc.VPackageManager;
import com.lody.virtual.client.stub.StubManifest;
import com.lody.virtual.helper.utils.BitmapUtils;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.remote.InstalledAppInfo;
import com.lody.virtual.remote.VAppInstallerParams;
import com.lody.virtual.remote.VAppInstallerResult;
import com.lody.virtual.server.BinderProvider;
import com.lody.virtual.server.extension.VExtPackageAccessor;
import com.lody.virtual.server.interfaces.IAppManager;
import com.lody.virtual.server.interfaces.IPackageObserver;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mirror.android.app.ActivityThread;
import mirror.android.content.res.AssetManager;

public final class VirtualCore {
  public static final int GET_HIDDEN_APP = 1;
  
  private static final String TAG = VirtualCore.class.getSimpleName();
  
  private static VirtualCore gCore = new VirtualCore();
  
  private Context context;
  
  private HostPackageManager hostPackageManager;
  
  private String hostPkgName;
  
  private boolean isMainPackage;
  
  private boolean isStartUp;
  
  private AppCallback mAppCallback;
  
  private AppRequestListener mAppRequestListener;
  
  private SettingConfig mConfig;
  
  private final BroadcastReceiver mDownloadCompleteReceiver = new BroadcastReceiver() {
      public void onReceive(Context param1Context, Intent param1Intent) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("receive download completed brodcast: ");
        stringBuilder.append(param1Intent);
        VLog.w("DownloadManager", stringBuilder.toString(), new Object[0]);
        if ("android.intent.action.DOWNLOAD_COMPLETE".equals(param1Intent.getAction()))
          VActivityManager.get().handleDownloadCompleteIntent(param1Intent); 
      }
    };
  
  private PackageInfo mHostPkgInfo;
  
  private ConditionVariable mInitLock;
  
  private IAppManager mService;
  
  private TaskDescriptionDelegate mTaskDescriptionDelegate;
  
  private String mainProcessName;
  
  private Object mainThread;
  
  private final int myUid = Process.myUid();
  
  private String processName;
  
  private ProcessType processType;
  
  private int remoteUid = -1;
  
  boolean scanned = false;
  
  private void detectProcessType() {
    this.hostPkgName = (this.context.getApplicationInfo()).packageName;
    this.mainProcessName = (this.context.getApplicationInfo()).processName;
    String str = getProcessName(this.context);
    this.processName = str;
    if (str.equals(this.mainProcessName)) {
      this.processType = ProcessType.Main;
    } else if (this.processName.endsWith(Constants.SERVER_PROCESS_NAME)) {
      this.processType = ProcessType.Server;
    } else if (this.processName.endsWith(Constants.HELPER_PROCESS_NAME)) {
      this.processType = ProcessType.Helper;
    } else if (VActivityManager.get().isAppProcess(this.processName)) {
      this.processType = ProcessType.VAppClient;
    } else {
      this.processType = ProcessType.CHILD;
    } 
  }
  
  public static VirtualCore get() {
    return gCore;
  }
  
  public static SettingConfig getConfig() {
    return (get()).mConfig;
  }
  
  public static PackageManager getPM() {
    return get().getPackageManager();
  }
  
  private static String getProcessName(Context paramContext) {
    // Byte code:
    //   0: invokestatic myPid : ()I
    //   3: istore_1
    //   4: aload_0
    //   5: ldc 'activity'
    //   7: invokevirtual getSystemService : (Ljava/lang/String;)Ljava/lang/Object;
    //   10: checkcast android/app/ActivityManager
    //   13: invokevirtual getRunningAppProcesses : ()Ljava/util/List;
    //   16: invokeinterface iterator : ()Ljava/util/Iterator;
    //   21: astore_0
    //   22: aload_0
    //   23: invokeinterface hasNext : ()Z
    //   28: ifeq -> 57
    //   31: aload_0
    //   32: invokeinterface next : ()Ljava/lang/Object;
    //   37: checkcast android/app/ActivityManager$RunningAppProcessInfo
    //   40: astore_2
    //   41: aload_2
    //   42: getfield pid : I
    //   45: iload_1
    //   46: if_icmpne -> 22
    //   49: aload_2
    //   50: getfield processName : Ljava/lang/String;
    //   53: astore_0
    //   54: goto -> 59
    //   57: aconst_null
    //   58: astore_0
    //   59: aload_0
    //   60: ifnull -> 65
    //   63: aload_0
    //   64: areturn
    //   65: new java/lang/RuntimeException
    //   68: dup
    //   69: ldc 'processName = null'
    //   71: invokespecial <init> : (Ljava/lang/String;)V
    //   74: athrow
  }
  
  private IAppManager getService() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mService : Lcom/lody/virtual/server/interfaces/IAppManager;
    //   4: invokestatic isAlive : (Landroid/os/IInterface;)Z
    //   7: ifne -> 38
    //   10: aload_0
    //   11: monitorenter
    //   12: aload_0
    //   13: ldc com/lody/virtual/server/interfaces/IAppManager
    //   15: aload_0
    //   16: invokespecial getStubInterface : ()Ljava/lang/Object;
    //   19: invokestatic genProxy : (Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    //   22: checkcast com/lody/virtual/server/interfaces/IAppManager
    //   25: putfield mService : Lcom/lody/virtual/server/interfaces/IAppManager;
    //   28: aload_0
    //   29: monitorexit
    //   30: goto -> 38
    //   33: astore_1
    //   34: aload_0
    //   35: monitorexit
    //   36: aload_1
    //   37: athrow
    //   38: aload_0
    //   39: getfield mService : Lcom/lody/virtual/server/interfaces/IAppManager;
    //   42: areturn
    // Exception table:
    //   from	to	target	type
    //   12	30	33	finally
    //   34	36	33	finally
  }
  
  private Object getStubInterface() {
    return IAppManager.Stub.asInterface(ServiceManagerNative.getService("app"));
  }
  
  public static Object mainThread() {
    return (get()).mainThread;
  }
  
  public boolean checkSelfPermission(String paramString, boolean paramBoolean) {
    boolean bool1 = true;
    boolean bool2 = true;
    if (paramBoolean) {
      if (this.hostPackageManager.checkPermission(paramString, StubManifest.EXT_PACKAGE_NAME) == 0) {
        paramBoolean = bool2;
      } else {
        paramBoolean = false;
      } 
      return paramBoolean;
    } 
    if (this.hostPackageManager.checkPermission(paramString, StubManifest.PACKAGE_NAME) == 0) {
      paramBoolean = bool1;
    } else {
      paramBoolean = false;
    } 
    return paramBoolean;
  }
  
  public boolean cleanPackageData(String paramString, int paramInt) {
    try {
      return getService().cleanPackageData(paramString, paramInt);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public boolean createShortcut(int paramInt, String paramString, Intent paramIntent, OnEmitShortcutListener paramOnEmitShortcutListener) {
    InstalledAppInfo installedAppInfo = getInstalledAppInfo(paramString, 0);
    if (installedAppInfo == null)
      return false; 
    ApplicationInfo applicationInfo = installedAppInfo.getApplicationInfo(paramInt);
    PackageManager packageManager = this.context.getPackageManager();
    try {
      String str1 = applicationInfo.loadLabel(packageManager).toString();
      Bitmap bitmap2 = BitmapUtils.drawableToBitmap(applicationInfo.loadIcon(packageManager));
      Bitmap bitmap1 = bitmap2;
      String str2 = str1;
      if (paramOnEmitShortcutListener != null) {
        str2 = paramOnEmitShortcutListener.getName(str1);
        if (str2 != null)
          str1 = str2; 
        Bitmap bitmap = paramOnEmitShortcutListener.getIcon(bitmap2);
        bitmap1 = bitmap2;
        str2 = str1;
        if (bitmap != null) {
          bitmap1 = bitmap;
          str2 = str1;
        } 
      } 
      Intent intent = getLaunchIntent(paramString, paramInt);
      if (intent == null)
        return false; 
      paramIntent = wrapperShortcutIntent(intent, paramIntent, paramString, paramInt);
      return true;
    } finally {
      paramString = null;
    } 
  }
  
  public boolean createShortcut(int paramInt, String paramString, OnEmitShortcutListener paramOnEmitShortcutListener) {
    return createShortcut(paramInt, paramString, null, paramOnEmitShortcutListener);
  }
  
  public AppCallback getAppCallback() {
    AppCallback appCallback1 = this.mAppCallback;
    AppCallback appCallback2 = appCallback1;
    if (appCallback1 == null)
      appCallback2 = AppCallback.EMPTY; 
    return appCallback2;
  }
  
  public int getAppPid(String paramString1, int paramInt, String paramString2) {
    return VActivityManager.get().getAppPid(paramString1, paramInt, paramString2);
  }
  
  public AppRequestListener getAppRequestListener() {
    return this.mAppRequestListener;
  }
  
  public Context getContext() {
    return this.context;
  }
  
  public String getEngineProcessName() {
    return this.context.getString(R.string.server_process_name);
  }
  
  public int[] getGids() {
    return this.mHostPkgInfo.gids;
  }
  
  public ApplicationInfo getHostApplicationInfo() {
    return this.mHostPkgInfo.applicationInfo;
  }
  
  public HostPackageManager getHostPackageManager() {
    return this.hostPackageManager;
  }
  
  public String getHostPkg() {
    return this.hostPkgName;
  }
  
  public ConditionVariable getInitLock() {
    return this.mInitLock;
  }
  
  public int getInstalledAppCount() {
    try {
      return getService().getInstalledAppCount();
    } catch (RemoteException remoteException) {
      return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
    } 
  }
  
  public InstalledAppInfo getInstalledAppInfo(String paramString, int paramInt) {
    try {
      return getService().getInstalledAppInfo(paramString, paramInt);
    } catch (RemoteException remoteException) {
      return (InstalledAppInfo)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public List<InstalledAppInfo> getInstalledApps(int paramInt) {
    try {
      return getService().getInstalledApps(paramInt);
    } catch (RemoteException remoteException) {
      return (List<InstalledAppInfo>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public List<InstalledAppInfo> getInstalledAppsAsUser(int paramInt1, int paramInt2) {
    try {
      return getService().getInstalledAppsAsUser(paramInt1, paramInt2);
    } catch (RemoteException remoteException) {
      return (List<InstalledAppInfo>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public List<String> getInstalledSplitNames(String paramString) {
    try {
      return getService().getInstalledSplitNames(paramString);
    } catch (RemoteException remoteException) {
      return (List<String>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public Intent getLaunchIntent(String paramString, int paramInt) {
    // Byte code:
    //   0: invokestatic get : ()Lcom/lody/virtual/client/ipc/VPackageManager;
    //   3: astore_3
    //   4: new android/content/Intent
    //   7: dup
    //   8: ldc_w 'android.intent.action.MAIN'
    //   11: invokespecial <init> : (Ljava/lang/String;)V
    //   14: astore #4
    //   16: aload #4
    //   18: ldc_w 'android.intent.category.INFO'
    //   21: invokevirtual addCategory : (Ljava/lang/String;)Landroid/content/Intent;
    //   24: pop
    //   25: aload #4
    //   27: aload_1
    //   28: invokevirtual setPackage : (Ljava/lang/String;)Landroid/content/Intent;
    //   31: pop
    //   32: aload_3
    //   33: aload #4
    //   35: aload #4
    //   37: aload_0
    //   38: getfield context : Landroid/content/Context;
    //   41: invokevirtual resolveType : (Landroid/content/Context;)Ljava/lang/String;
    //   44: iconst_0
    //   45: iload_2
    //   46: invokevirtual queryIntentActivities : (Landroid/content/Intent;Ljava/lang/String;II)Ljava/util/List;
    //   49: astore #5
    //   51: aload #5
    //   53: ifnull -> 70
    //   56: aload #5
    //   58: astore #6
    //   60: aload #5
    //   62: invokeinterface size : ()I
    //   67: ifgt -> 113
    //   70: aload #4
    //   72: ldc_w 'android.intent.category.INFO'
    //   75: invokevirtual removeCategory : (Ljava/lang/String;)V
    //   78: aload #4
    //   80: ldc_w 'android.intent.category.LAUNCHER'
    //   83: invokevirtual addCategory : (Ljava/lang/String;)Landroid/content/Intent;
    //   86: pop
    //   87: aload #4
    //   89: aload_1
    //   90: invokevirtual setPackage : (Ljava/lang/String;)Landroid/content/Intent;
    //   93: pop
    //   94: aload_3
    //   95: aload #4
    //   97: aload #4
    //   99: aload_0
    //   100: getfield context : Landroid/content/Context;
    //   103: invokevirtual resolveType : (Landroid/content/Context;)Ljava/lang/String;
    //   106: iconst_0
    //   107: iload_2
    //   108: invokevirtual queryIntentActivities : (Landroid/content/Intent;Ljava/lang/String;II)Ljava/util/List;
    //   111: astore #6
    //   113: aload #6
    //   115: ifnull -> 190
    //   118: aload #6
    //   120: invokeinterface size : ()I
    //   125: ifgt -> 131
    //   128: goto -> 190
    //   131: new android/content/Intent
    //   134: dup
    //   135: aload #4
    //   137: invokespecial <init> : (Landroid/content/Intent;)V
    //   140: astore_1
    //   141: aload_1
    //   142: ldc_w 268435456
    //   145: invokevirtual setFlags : (I)Landroid/content/Intent;
    //   148: pop
    //   149: aload_1
    //   150: aload #6
    //   152: iconst_0
    //   153: invokeinterface get : (I)Ljava/lang/Object;
    //   158: checkcast android/content/pm/ResolveInfo
    //   161: getfield activityInfo : Landroid/content/pm/ActivityInfo;
    //   164: getfield packageName : Ljava/lang/String;
    //   167: aload #6
    //   169: iconst_0
    //   170: invokeinterface get : (I)Ljava/lang/Object;
    //   175: checkcast android/content/pm/ResolveInfo
    //   178: getfield activityInfo : Landroid/content/pm/ActivityInfo;
    //   181: getfield name : Ljava/lang/String;
    //   184: invokevirtual setClassName : (Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   187: pop
    //   188: aload_1
    //   189: areturn
    //   190: aconst_null
    //   191: areturn
  }
  
  public String getMainProcessName() {
    return this.mainProcessName;
  }
  
  public int[] getPackageInstalledUsers(String paramString) {
    try {
      return getService().getPackageInstalledUsers(paramString);
    } catch (RemoteException remoteException) {
      return (int[])VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public PackageManager getPackageManager() {
    return this.context.getPackageManager();
  }
  
  public ActivityManager.RunningAppProcessInfo getProccessInfo(String paramString, int paramInt) {
    for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : getRunningAppProcessesEx()) {
      if (runningAppProcessInfo.processName.equals(paramString) && runningAppProcessInfo.uid == paramInt)
        return runningAppProcessInfo; 
    } 
    return null;
  }
  
  public String getProcessName() {
    return this.processName;
  }
  
  public List<ActivityManager.RecentTaskInfo> getRecentTasksEx(int paramInt1, int paramInt2) {
    ArrayList<ActivityManager.RecentTaskInfo> arrayList = new ArrayList(((ActivityManager)this.context.getSystemService("activity")).getRecentTasks(paramInt1, paramInt2));
    arrayList.addAll(VExtPackageAccessor.getRecentTasks(paramInt1, paramInt2));
    return arrayList;
  }
  
  public Resources getResources(String paramString) throws Resources.NotFoundException {
    AssetManager assetManager;
    InstalledAppInfo installedAppInfo = getInstalledAppInfo(paramString, 0);
    if (installedAppInfo != null) {
      assetManager = (AssetManager)AssetManager.ctor.newInstance();
      AssetManager.addAssetPath.call(assetManager, new Object[] { installedAppInfo.getApkPath() });
      Resources resources = this.context.getResources();
      return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
    } 
    throw new Resources.NotFoundException(assetManager);
  }
  
  public List<ActivityManager.RunningAppProcessInfo> getRunningAppProcessesEx() {
    ArrayList<ActivityManager.RunningAppProcessInfo> arrayList = new ArrayList(((ActivityManager)this.context.getSystemService("activity")).getRunningAppProcesses());
    arrayList.addAll(VExtPackageAccessor.getRunningAppProcesses());
    return arrayList;
  }
  
  public List<ActivityManager.RunningTaskInfo> getRunningTasksEx(int paramInt) {
    ArrayList<ActivityManager.RunningTaskInfo> arrayList = new ArrayList(((ActivityManager)this.context.getSystemService("activity")).getRunningTasks(paramInt));
    arrayList.addAll(VExtPackageAccessor.getRunningTasks(paramInt));
    return arrayList;
  }
  
  public int getTargetSdkVersion() {
    return (this.context.getApplicationInfo()).targetSdkVersion;
  }
  
  public TaskDescriptionDelegate getTaskDescriptionDelegate() {
    return this.mTaskDescriptionDelegate;
  }
  
  public int getUidForSharedUser(String paramString) {
    try {
      return getService().getUidForSharedUser(paramString);
    } catch (RemoteException remoteException) {
      return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
    } 
  }
  
  public void initialize(VirtualInitializer paramVirtualInitializer) {
    if (paramVirtualInitializer != null) {
      int i = null.$SwitchMap$com$lody$virtual$client$core$VirtualCore$ProcessType[this.processType.ordinal()];
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i == 4)
              paramVirtualInitializer.onChildProcess(); 
          } else {
            paramVirtualInitializer.onServerProcess();
          } 
        } else {
          if (Build.VERSION.SDK_INT >= 21)
            SandXposed.init(); 
          paramVirtualInitializer.onVirtualProcess();
        } 
      } else {
        paramVirtualInitializer.onMainProcess();
      } 
      return;
    } 
    throw new IllegalStateException("Initializer = NULL");
  }
  
  public VAppInstallerResult installPackage(Uri paramUri, VAppInstallerParams paramVAppInstallerParams) {
    try {
      return getService().installPackage(paramUri, paramVAppInstallerParams);
    } catch (RemoteException remoteException) {
      return (VAppInstallerResult)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public boolean installPackageAsUser(int paramInt, String paramString) {
    try {
      return getService().installPackageAsUser(paramInt, paramString);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public boolean isAppInstalled(String paramString) {
    try {
      return getService().isAppInstalled(paramString);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public boolean isAppInstalledAsUser(int paramInt, String paramString) {
    try {
      return getService().isAppInstalledAsUser(paramInt, paramString);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public boolean isAppRunning(String paramString, int paramInt, boolean paramBoolean) {
    return VActivityManager.get().isAppRunning(paramString, paramInt, paramBoolean);
  }
  
  public boolean isChildProcess() {
    boolean bool;
    if (ProcessType.CHILD == this.processType) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isEngineLaunched() {
    if (isExtPackage())
      return true; 
    if (!BinderProvider.scanApps)
      scanApps(); 
    ActivityManager activityManager = (ActivityManager)this.context.getSystemService("activity");
    String str = getEngineProcessName();
    Iterator iterator = activityManager.getRunningAppProcesses().iterator();
    while (iterator.hasNext()) {
      if (((ActivityManager.RunningAppProcessInfo)iterator.next()).processName.endsWith(str))
        return true; 
    } 
    return false;
  }
  
  public boolean isExtHelperProcess() {
    boolean bool;
    if (ProcessType.Helper == this.processType) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isExtPackage() {
    return this.isMainPackage ^ true;
  }
  
  public boolean isExtPackageInstalled() {
    return isOutsideInstalled(StubManifest.EXT_PACKAGE_NAME);
  }
  
  public boolean isMainPackage() {
    return this.isMainPackage;
  }
  
  public boolean isMainProcess() {
    boolean bool;
    if (ProcessType.Main == this.processType) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isOutsideInstalled(String paramString) {
    if (paramString == null)
      return false; 
    try {
      this.hostPackageManager.getApplicationInfo(paramString, 0);
      return true;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      return false;
    } 
  }
  
  public boolean isPackageLaunchable(String paramString) {
    boolean bool1 = false;
    InstalledAppInfo installedAppInfo = getInstalledAppInfo(paramString, 0);
    boolean bool2 = bool1;
    if (installedAppInfo != null) {
      bool2 = bool1;
      if (getLaunchIntent(paramString, installedAppInfo.getInstalledUsers()[0]) != null)
        bool2 = true; 
    } 
    return bool2;
  }
  
  public boolean isPackageLaunched(int paramInt, String paramString) {
    try {
      return getService().isPackageLaunched(paramInt, paramString);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public boolean isRunInExtProcess(String paramString) {
    try {
      return getService().isRunInExtProcess(paramString);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public boolean isServerProcess() {
    boolean bool;
    if (ProcessType.Server == this.processType) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isStartup() {
    return this.isStartUp;
  }
  
  public boolean isSystemApp() {
    ApplicationInfo applicationInfo = getContext().getApplicationInfo();
    int i = applicationInfo.flags;
    boolean bool1 = true;
    boolean bool2 = bool1;
    if ((i & 0x1) == 0)
      if ((applicationInfo.flags & 0x80) != 0) {
        bool2 = bool1;
      } else {
        bool2 = false;
      }  
    return bool2;
  }
  
  public boolean isVAppProcess() {
    boolean bool;
    if (ProcessType.VAppClient == this.processType) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void killAllApps() {
    VActivityManager.get().killAllApps();
  }
  
  public void killApp(String paramString, int paramInt) {
    VActivityManager.get().killAppByPkg(paramString, paramInt);
  }
  
  public int myUid() {
    return this.myUid;
  }
  
  public int myUserId() {
    return VUserHandle.getUserId(this.myUid);
  }
  
  public void registerObserver(IPackageObserver paramIPackageObserver) {
    try {
      getService().registerObserver(paramIPackageObserver);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public int remoteUid() {
    return this.remoteUid;
  }
  
  public boolean removeShortcut(int paramInt, String paramString, Intent paramIntent, OnEmitShortcutListener paramOnEmitShortcutListener) {
    InstalledAppInfo installedAppInfo = getInstalledAppInfo(paramString, 0);
    if (installedAppInfo == null)
      return false; 
    ApplicationInfo applicationInfo = installedAppInfo.getApplicationInfo(paramInt);
    PackageManager packageManager = this.context.getPackageManager();
    try {
      String str2 = applicationInfo.loadLabel(packageManager).toString();
      String str1 = str2;
      if (paramOnEmitShortcutListener != null) {
        String str = paramOnEmitShortcutListener.getName(str2);
        str1 = str2;
        if (str != null)
          str1 = str; 
      } 
      Intent intent = getLaunchIntent(paramString, paramInt);
      if (intent == null)
        return false; 
      paramIntent = wrapperShortcutIntent(intent, paramIntent, paramString, paramInt);
      return true;
    } finally {
      paramString = null;
    } 
  }
  
  public ActivityInfo resolveActivityInfo(ComponentName paramComponentName, int paramInt) {
    return VPackageManager.get().getActivityInfo(paramComponentName, 0, paramInt);
  }
  
  public ActivityInfo resolveActivityInfo(Intent paramIntent, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokestatic shouldBlockIntent : (Landroid/content/Intent;)Z
    //   6: istore_3
    //   7: aconst_null
    //   8: astore #4
    //   10: iload_3
    //   11: ifeq -> 18
    //   14: aload_0
    //   15: monitorexit
    //   16: aconst_null
    //   17: areturn
    //   18: aload_1
    //   19: invokevirtual getComponent : ()Landroid/content/ComponentName;
    //   22: ifnonnull -> 86
    //   25: invokestatic get : ()Lcom/lody/virtual/client/ipc/VPackageManager;
    //   28: aload_1
    //   29: aload_1
    //   30: invokevirtual getType : ()Ljava/lang/String;
    //   33: iconst_0
    //   34: iload_2
    //   35: invokevirtual resolveIntent : (Landroid/content/Intent;Ljava/lang/String;II)Landroid/content/pm/ResolveInfo;
    //   38: astore #5
    //   40: aload #4
    //   42: astore #6
    //   44: aload #5
    //   46: ifnull -> 97
    //   49: aload #4
    //   51: astore #6
    //   53: aload #5
    //   55: getfield activityInfo : Landroid/content/pm/ActivityInfo;
    //   58: ifnull -> 97
    //   61: aload #5
    //   63: getfield activityInfo : Landroid/content/pm/ActivityInfo;
    //   66: astore #6
    //   68: aload_1
    //   69: aload #6
    //   71: getfield packageName : Ljava/lang/String;
    //   74: aload #6
    //   76: getfield name : Ljava/lang/String;
    //   79: invokevirtual setClassName : (Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   82: pop
    //   83: goto -> 97
    //   86: aload_0
    //   87: aload_1
    //   88: invokevirtual getComponent : ()Landroid/content/ComponentName;
    //   91: iload_2
    //   92: invokevirtual resolveActivityInfo : (Landroid/content/ComponentName;I)Landroid/content/pm/ActivityInfo;
    //   95: astore #6
    //   97: aload_0
    //   98: monitorexit
    //   99: aload #6
    //   101: areturn
    //   102: astore_1
    //   103: aload_0
    //   104: monitorexit
    //   105: aload_1
    //   106: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	102	finally
    //   18	40	102	finally
    //   53	83	102	finally
    //   86	97	102	finally
  }
  
  public ServiceInfo resolveServiceInfo(Intent paramIntent, int paramInt) {
    ServiceInfo serviceInfo;
    boolean bool = SpecialComponentList.shouldBlockIntent(paramIntent);
    Intent intent = null;
    if (bool)
      return null; 
    ResolveInfo resolveInfo = VPackageManager.get().resolveService(paramIntent, paramIntent.getType(), 0, paramInt);
    paramIntent = intent;
    if (resolveInfo != null)
      serviceInfo = resolveInfo.serviceInfo; 
    return serviceInfo;
  }
  
  public void scanApps() {
    if (this.scanned)
      return; 
    try {
      getService().scanApps();
      this.scanned = true;
    } catch (RemoteException remoteException) {}
  }
  
  public void setAppCallback(AppCallback paramAppCallback) {
    this.mAppCallback = paramAppCallback;
  }
  
  public void setAppRequestListener(AppRequestListener paramAppRequestListener) {
    this.mAppRequestListener = paramAppRequestListener;
  }
  
  public void setCrashHandler(CrashHandler paramCrashHandler) {
    VClient.get().setCrashHandler(paramCrashHandler);
  }
  
  public void setPackageHidden(int paramInt, String paramString, boolean paramBoolean) {
    try {
      getService().setPackageHidden(paramInt, paramString, paramBoolean);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public void setTaskDescriptionDelegate(TaskDescriptionDelegate paramTaskDescriptionDelegate) {
    this.mTaskDescriptionDelegate = paramTaskDescriptionDelegate;
  }
  
  public void startup(Context paramContext, SettingConfig paramSettingConfig) throws Throwable {
    if (!this.isStartUp) {
      if (Looper.myLooper() == Looper.getMainLooper()) {
        InvocationStubManager invocationStubManager;
        IntentFilter intentFilter;
        if (paramContext.getPackageName().equals(paramSettingConfig.getMainPackageName()) || paramContext.getPackageName().equals(paramSettingConfig.getExtPackageName())) {
          this.mInitLock = new ConditionVariable();
          this.mConfig = paramSettingConfig;
          String str3 = paramSettingConfig.getMainPackageName();
          String str2 = paramSettingConfig.getExtPackageName();
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append(str3);
          stringBuilder1.append(Constants.ACTION_SHORTCUT);
          Constants.ACTION_SHORTCUT = stringBuilder1.toString();
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append(str3);
          stringBuilder1.append(Constants.ACTION_BADGER_CHANGE);
          Constants.ACTION_BADGER_CHANGE = stringBuilder1.toString();
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append(str3);
          stringBuilder1.append(".virtual_stub_");
          StubManifest.STUB_CP_AUTHORITY = stringBuilder1.toString();
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append(str3);
          stringBuilder1.append(".provider_proxy");
          StubManifest.PROXY_CP_AUTHORITY = stringBuilder1.toString();
          File file = paramContext.getExternalFilesDir(paramSettingConfig.getVirtualSdcardAndroidDataName());
          if (!file.exists())
            file.mkdirs(); 
          String str1 = str2;
          if (str2 == null)
            str1 = "NO_EXT"; 
          StubManifest.PACKAGE_NAME = str3;
          StubManifest.EXT_PACKAGE_NAME = str1;
          StubManifest.EXT_STUB_CP_AUTHORITY = "com.smallyin.moreopen.no.virtual_stub_ext_";
          StubManifest.EXT_PROXY_CP_AUTHORITY = "com.smallyin.moreopen.no.provider_proxy_ext";
          this.context = paramContext;
          this.isMainPackage = paramContext.getPackageName().equals(StubManifest.PACKAGE_NAME);
          NativeEngine.bypassHiddenAPIEnforcementPolicyIfNeeded();
          HostPackageManager hostPackageManager = HostPackageManager.init();
          this.hostPackageManager = hostPackageManager;
          this.mHostPkgInfo = hostPackageManager.getPackageInfo(str3, 256);
          detectProcessType();
          if (isVAppProcess()) {
            this.mainThread = ActivityThread.currentActivityThread.call(new Object[0]);
            LocalPackageCache.init();
          } 
          if (isExtPackage()) {
            try {
              ApplicationInfo applicationInfo = getHostPackageManager().getApplicationInfo(str3, 0);
              if (applicationInfo != null)
                this.remoteUid = applicationInfo.uid; 
            } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {}
            VLog.e(TAG, "===========  Extension Package(%s) ===========", new Object[] { this.processType.name() });
          } else {
            try {
              ApplicationInfo applicationInfo = getHostPackageManager().getApplicationInfo((String)nameNotFoundException, 0);
              if (applicationInfo != null)
                this.remoteUid = applicationInfo.uid; 
            } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException1) {}
          } 
          if (isVAppProcess() || isExtHelperProcess())
            ServiceManagerNative.linkToDeath(new IBinder.DeathRecipient() {
                  public void binderDied() {
                    String str = VirtualCore.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Server was dead, kill process: ");
                    stringBuilder.append(VirtualCore.this.processType.name());
                    VLog.e(str, stringBuilder.toString());
                    Process.killProcess(Process.myPid());
                  }
                }); 
          if (isServerProcess() || isExtHelperProcess()) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Listening DownloadManager action  in process: ");
            stringBuilder2.append(this.processType);
            VLog.w("DownloadManager", stringBuilder2.toString(), new Object[0]);
            intentFilter = new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE");
          } 
          invocationStubManager = InvocationStubManager.getInstance();
          invocationStubManager.init();
          invocationStubManager.injectAll();
          this.isStartUp = true;
          this.mInitLock.open();
          return;
        } 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Neither the main package nor the extension package, you seem to have configured the wrong package name, expected ");
        stringBuilder.append(intentFilter.getMainPackageName());
        stringBuilder.append(" or ");
        stringBuilder.append(intentFilter.getExtPackageName());
        stringBuilder.append(", but got ");
        stringBuilder.append(invocationStubManager.getPackageName());
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      throw new IllegalStateException("VirtualCore.startup() must called in main thread.");
    } 
  }
  
  public boolean uninstallPackage(String paramString) {
    try {
      return getService().uninstallPackage(paramString);
    } catch (RemoteException remoteException) {
      return false;
    } 
  }
  
  public boolean uninstallPackageAsUser(String paramString, int paramInt) {
    try {
      return getService().uninstallPackageAsUser(paramString, paramInt);
    } catch (RemoteException remoteException) {
      return false;
    } 
  }
  
  public void unregisterObserver(IPackageObserver paramIPackageObserver) {
    try {
      getService().unregisterObserver(paramIPackageObserver);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void waitForEngine() {
    ServiceManagerNative.ensureServerStarted();
  }
  
  public void waitStartup() {
    if (Looper.myLooper() == Looper.getMainLooper())
      return; 
    ConditionVariable conditionVariable = this.mInitLock;
    if (conditionVariable != null)
      conditionVariable.block(); 
  }
  
  public Intent wrapperShortcutIntent(Intent paramIntent1, Intent paramIntent2, String paramString, int paramInt) {
    Intent intent = new Intent();
    intent.addCategory("android.intent.category.DEFAULT");
    intent.setAction(Constants.ACTION_SHORTCUT);
    intent.setPackage(getHostPkg());
    if (paramIntent2 != null)
      intent.putExtra("_VA_|_splash_", paramIntent2.toUri(0)); 
    intent.putExtra("_VA_|_pkg_", paramString);
    intent.putExtra("_VA_|_uri_", paramIntent1.toUri(0));
    intent.putExtra("_VA_|_user_id_", paramInt);
    return intent;
  }
  
  public static interface AppRequestListener {
    void onRequestInstall(String param1String);
    
    void onRequestUninstall(String param1String);
  }
  
  public static interface OnEmitShortcutListener {
    Bitmap getIcon(Bitmap param1Bitmap);
    
    String getName(String param1String);
  }
  
  public static abstract class PackageObserver extends IPackageObserver.Stub {}
  
  private enum ProcessType {
    CHILD, Helper, Main, Server, VAppClient;
    
    static {
      Helper = new ProcessType("Helper", 3);
      ProcessType processType = new ProcessType("CHILD", 4);
      CHILD = processType;
      $VALUES = new ProcessType[] { Server, VAppClient, Main, Helper, processType };
    }
  }
  
  public static abstract class VirtualInitializer {
    public void onChildProcess() {}
    
    public void onMainProcess() {}
    
    public void onServerProcess() {}
    
    public void onVirtualProcess() {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\core\VirtualCore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */