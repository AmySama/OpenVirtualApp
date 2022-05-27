package com.lody.virtual.client;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Application;
import android.app.Instrumentation;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.os.Binder;
import android.os.Build;
import android.os.ConditionVariable;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.StrictMode;
import android.security.net.config.ApplicationConfig;
import android.view.autofill.AutofillManager;
import com.lody.virtual.SandXposed;
import com.lody.virtual.client.core.CrashHandler;
import com.lody.virtual.client.core.SettingConfig;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.client.fixer.ContextFixer;
import com.lody.virtual.client.hook.instruments.InstrumentationVirtualApp;
import com.lody.virtual.client.hook.providers.ProviderHook;
import com.lody.virtual.client.hook.proxies.view.AutoFillManagerStub;
import com.lody.virtual.client.hook.secondary.ProxyServiceFactory;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.client.ipc.VDeviceManager;
import com.lody.virtual.client.ipc.VPackageManager;
import com.lody.virtual.client.ipc.VirtualStorageManager;
import com.lody.virtual.client.receiver.StaticReceiverSystem;
import com.lody.virtual.client.service.VServiceRuntime;
import com.lody.virtual.client.stub.StubManifest;
import com.lody.virtual.helper.ComposeClassLoader;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.compat.StorageManagerCompat;
import com.lody.virtual.helper.compat.StrictModeCompat;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.remote.ClientConfig;
import com.lody.virtual.remote.InstalledAppInfo;
import com.lody.virtual.remote.VDeviceConfig;
import com.lody.virtual.server.extension.VExtPackageAccessor;
import com.lody.virtual.server.secondary.FakeIdentityBinder;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mirror.RefObject;
import mirror.android.app.ActivityManagerNative;
import mirror.android.app.ActivityThread;
import mirror.android.app.ActivityThreadNMR1;
import mirror.android.app.ActivityThreadQ;
import mirror.android.app.AlarmManager;
import mirror.android.app.ContextImpl;
import mirror.android.app.ContextImplKitkat;
import mirror.android.app.IActivityManager;
import mirror.android.app.LoadedApk;
import mirror.android.app.LoadedApkKitkat;
import mirror.android.app.Service;
import mirror.android.content.BroadcastReceiver;
import mirror.android.content.ContentProviderHolderOreo;
import mirror.android.content.res.CompatibilityInfo;
import mirror.android.os.Message;
import mirror.android.providers.Settings;
import mirror.android.renderscript.RenderScriptCacheDir;
import mirror.android.view.DisplayAdjustments;
import mirror.android.view.HardwareRenderer;
import mirror.android.view.RenderScript;
import mirror.android.view.ThreadedRenderer;
import mirror.com.android.internal.content.ReferrerIntent;
import mirror.dalvik.system.VMRuntime;
import mirror.java.lang.ThreadGroup;
import mirror.java.lang.ThreadGroupN;

public final class VClient extends IVClient.Stub {
  private static boolean CheckJunitClazz = false;
  
  private static final int FINISH_ACTIVITY = 13;
  
  private static final int NEW_INTENT = 11;
  
  private static final int RECEIVER = 12;
  
  private static final String TAG = VClient.class.getSimpleName();
  
  private static final VClient gClient = new VClient();
  
  private ClientConfig clientConfig;
  
  private CrashHandler crashHandler;
  
  private final Map<String, Application> mAllApplications = new HashMap<String, Application>(1);
  
  private InstalledAppInfo mAppInfo;
  
  private AppBindData mBoundApplication;
  
  private Set<String> mExportedVApiPkgs = new HashSet<String>();
  
  private final H mH = new H();
  
  private Application mInitialApplication;
  
  private Instrumentation mInstrumentation;
  
  static {
    CheckJunitClazz = false;
  }
  
  private void bindApplicationMainThread(String paramString1, String paramString2, ConditionVariable paramConditionVariable) {
    Map<String, Application> map;
    String str;
    synchronized (this.mAllApplications) {
      VDeviceConfig vDeviceConfig;
      boolean bool;
      int i;
      if (this.mAllApplications.containsKey(paramString1))
        return; 
      if (VirtualCore.get().isExtHelperProcess())
        VExtPackageAccessor.syncPackages(); 
      if (this.mInitialApplication == null) {
        bool = true;
      } else {
        bool = false;
      } 
      Binder.clearCallingIdentity();
      if (paramString2 == null) {
        str = paramString1;
      } else {
        str = paramString2;
      } 
      try {
        setupUncaughtHandler();
      } finally {
        paramString2 = null;
      } 
      try {
        fixInstalledProviders();
      } finally {
        paramString2 = null;
      } 
      VDeviceManager.get().applyBuildProp(vDeviceConfig);
      ActivityThread.mInitialApplication.set(VirtualCore.mainThread(), null);
      AppBindData appBindData = new AppBindData();
      InstalledAppInfo installedAppInfo = VirtualCore.get().getInstalledAppInfo(paramString1, 0);
      if (installedAppInfo == null) {
        (new Exception("app not exist")).printStackTrace();
        Process.killProcess(0);
        System.exit(0);
      } 
      if (bool)
        this.mAppInfo = installedAppInfo; 
      appBindData.appInfo = VPackageManager.get().getApplicationInfo(paramString1, 0, i);
      appBindData.processName = str;
      appBindData.providers = VPackageManager.get().queryContentProviders(str, getVUid(), 128);
      Iterator<ProviderInfo> iterator = appBindData.providers.iterator();
      while (iterator.hasNext()) {
        if (!((ProviderInfo)iterator.next()).enabled)
          iterator.remove(); 
      } 
      boolean bool1 = VirtualCore.get().isExtPackage();
      VLog.i(TAG, "Binding application %s (%s [%d])", new Object[] { appBindData.appInfo.packageName, appBindData.processName, Integer.valueOf(Process.myPid()) });
      if (bool) {
        this.mBoundApplication = appBindData;
        VirtualRuntime.setupRuntime(appBindData.processName, appBindData.appInfo);
        this.mInstrumentation = (Instrumentation)ActivityThread.mInstrumentation.get(VirtualCore.mainThread());
        int j = appBindData.appInfo.targetSdkVersion;
        if (j < 9)
          StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder(StrictMode.getThreadPolicy())).permitNetwork().build()); 
        if (Build.VERSION.SDK_INT >= 24 && VirtualCore.get().getTargetSdkVersion() >= 24 && j < 24)
          StrictModeCompat.disableDeathOnFileUriExposure(); 
        if (j < 21)
          Message.updateCheckRecycle.call(new Object[] { Integer.valueOf(j) }); 
        AlarmManager alarmManager = (AlarmManager)VirtualCore.get().getContext().getSystemService("alarm");
        if (AlarmManager.mTargetSdkVersion != null)
          try {
            AlarmManager.mTargetSdkVersion.set(alarmManager, j);
          } catch (Exception exception) {
            exception.printStackTrace();
          }  
        if (bool1) {
          System.setProperty("java.io.tmpdir", (new File(VEnvironment.getDataUserPackageDirectoryExt(i, installedAppInfo.packageName), "cache")).getAbsolutePath());
        } else {
          System.setProperty("java.io.tmpdir", (new File(VEnvironment.getDataUserPackageDirectory(i, installedAppInfo.packageName), "cache")).getAbsolutePath());
        } 
        NativeEngine.launchEngine(paramString1);
        if (VirtualCore.getConfig().isEnableIORedirect())
          mountVirtualFS(installedAppInfo, bool1); 
      } 
      Object object = VirtualCore.mainThread();
      initDataStorage(bool1, i, paramString1);
      Context context = createPackageContext(appBindData.appInfo.packageName);
      if (bool) {
        File file;
        NativeEngine.startDexOverride();
        StaticReceiverSystem.get().attach(str, VirtualCore.get().getContext(), appBindData.appInfo, i);
        if (Build.VERSION.SDK_INT >= 23) {
          file = context.getCodeCacheDir();
        } else {
          file = context.getCacheDir();
        } 
        if (Build.VERSION.SDK_INT < 24) {
          if (HardwareRenderer.setupDiskCache != null)
            HardwareRenderer.setupDiskCache.call(new Object[] { file }); 
        } else if (ThreadedRenderer.setupDiskCache != null) {
          ThreadedRenderer.setupDiskCache.call(new Object[] { file });
        } 
        if (Build.VERSION.SDK_INT >= 23) {
          if (RenderScriptCacheDir.setupDiskCache != null)
            RenderScriptCacheDir.setupDiskCache.call(new Object[] { file }); 
        } else if (RenderScript.setupDiskCache != null) {
          RenderScript.setupDiskCache.call(new Object[] { file });
        } 
        this.mBoundApplication.info = ContextImpl.mPackageInfo.get(context);
        Object object1 = ActivityThread.mBoundApplication.get(object);
        ActivityThread.AppBindData.appInfo.set(object1, appBindData.appInfo);
        ActivityThread.AppBindData.processName.set(object1, appBindData.processName);
        ActivityThread.AppBindData.instrumentationName.set(object1, new ComponentName(appBindData.appInfo.packageName, Instrumentation.class.getName()));
        ActivityThread.AppBindData.info.set(object1, appBindData.info);
        ActivityThread.AppBindData.providers.set(object1, appBindData.providers);
        if (LoadedApk.mSecurityViolation != null)
          LoadedApk.mSecurityViolation.set(this.mBoundApplication.info, false); 
        VMRuntime.setTargetSdkVersion.call(VMRuntime.getRuntime.call(new Object[0]), new Object[] { Integer.valueOf(appBindData.appInfo.targetSdkVersion) });
        Configuration configuration = context.getResources().getConfiguration();
        if (CompatibilityInfo.ctor != null) {
          object1 = CompatibilityInfo.ctor.newInstance(new Object[] { appBindData.appInfo, Integer.valueOf(configuration.screenLayout), Integer.valueOf(configuration.smallestScreenWidthDp), Boolean.valueOf(false) });
        } else {
          object1 = null;
        } 
        if (CompatibilityInfo.ctorLG != null)
          object1 = CompatibilityInfo.ctorLG.newInstance(new Object[] { appBindData.appInfo, Integer.valueOf(configuration.screenLayout), Integer.valueOf(configuration.smallestScreenWidthDp), Boolean.valueOf(false), Integer.valueOf(0) }); 
        if (object1 != null) {
          if (Build.VERSION.SDK_INT < 24)
            DisplayAdjustments.setCompatibilityInfo.call(ContextImplKitkat.mDisplayAdjustments.get(context), new Object[] { object1 }); 
          DisplayAdjustments.setCompatibilityInfo.call(LoadedApkKitkat.mDisplayAdjustments.get(this.mBoundApplication.info), new Object[] { object1 });
        } 
        if (Build.VERSION.SDK_INT >= 30)
          ApplicationConfig.setDefaultInstance(null); 
        SandXposed.initForXposed(context, str);
        fixSystem();
        VirtualCore.get().getAppCallback().beforeStartApplication(paramString1, str, context);
        if (this.mExportedVApiPkgs.contains(paramString1) && LoadedApk.mClassLoader != null)
          LoadedApk.mClassLoader.set(appBindData.info, new ComposeClassLoader(VClient.class.getClassLoader(), (ClassLoader)LoadedApk.getClassLoader.call(appBindData.info, new Object[0]))); 
      } 
      if (CheckJunitClazz && BuildCompat.isR() && appBindData.appInfo.targetSdkVersion < 30) {
        ClassLoader classLoader = (ClassLoader)LoadedApk.getClassLoader.call(appBindData.info, new Object[0]);
        if (Build.VERSION.SDK_INT >= 30)
          Reflect.on(classLoader).set("parent", new ClassLoader() {
                protected Class<?> loadClass(String param1String, boolean param1Boolean) throws ClassNotFoundException {
                  return param1String.startsWith("junit") ? VClient.class.getClassLoader().loadClass(param1String) : super.loadClass(param1String, param1Boolean);
                }
              }); 
      } 
      try {
        Application application;
        if (VirtualCore.getConfig().resumeInstrumentationInMakeApplication(paramString1) && this.mInstrumentation instanceof InstrumentationVirtualApp) {
          InstrumentationVirtualApp instrumentationVirtualApp = (InstrumentationVirtualApp)this.mInstrumentation;
          this.mInstrumentation = instrumentationVirtualApp.getBaseInstrumentation();
          ActivityThread.mInstrumentation.set(VirtualCore.mainThread(), this.mInstrumentation);
          application = (Application)LoadedApk.makeApplication.call(appBindData.info, new Object[] { Boolean.valueOf(false), instrumentationVirtualApp });
          this.mInstrumentation = (Instrumentation)instrumentationVirtualApp;
          ActivityThread.mInstrumentation.set(VirtualCore.mainThread(), this.mInstrumentation);
        } else {
          application = (Application)LoadedApk.makeApplication.call(appBindData.info, new Object[] { Boolean.valueOf(false), null });
        } 
        ContextFixer.fixContext((Context)application, appBindData.appInfo.packageName);
        if (bool) {
          this.mInitialApplication = application;
          ActivityThread.mInitialApplication.set(object, application);
        } 
        if (LoadedApk.mApplication != null) {
          Object object1 = ContextImpl.mPackageInfo.get(context);
          if (object1 != null)
            LoadedApk.mApplication.set(object1, application); 
        } 
        if (Build.VERSION.SDK_INT >= 24 && "com.tencent.mm:recovery".equals(str))
          fixWeChatRecovery(this.mInitialApplication); 
        if ("com.android.vending".equals(paramString1))
          try {
            context.getSharedPreferences("vending_preferences", 0).edit().putBoolean("notify_updates", false).putBoolean("notify_updates_completion", false).apply();
            context.getSharedPreferences("finsky", 0).edit().putBoolean("auto_update_enabled", false).apply();
          } finally {
            context = null;
          }  
      } finally {
        paramString1 = null;
      } 
    } 
  }
  
  private static void clearContentProvider(Object paramObject) {
    if (BuildCompat.isOreo()) {
      paramObject = Settings.NameValueCacheOreo.mProviderHolder.get(paramObject);
      if (paramObject != null)
        Settings.ContentProviderHolder.mContentProvider.set(paramObject, null); 
    } else {
      Settings.NameValueCache.mContentProvider.set(paramObject, null);
    } 
  }
  
  private void clearSettingProvider() {
    Object object = Settings.System.sNameValueCache.get();
    if (object != null)
      clearContentProvider(object); 
    object = Settings.Secure.sNameValueCache.get();
    if (object != null)
      clearContentProvider(object); 
    if (Settings.Global.TYPE != null) {
      object = Settings.Global.sNameValueCache.get();
      if (object != null)
        clearContentProvider(object); 
    } 
  }
  
  private Context createPackageContext(String paramString) {
    try {
      return VirtualCore.get().getContext().createPackageContext(paramString, 3);
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      nameNotFoundException.printStackTrace();
      VirtualRuntime.crash((Throwable)nameNotFoundException);
      throw new RuntimeException();
    } 
  }
  
  private void fixInstalledProviders() {
    clearSettingProvider();
    Iterator<Map.Entry> iterator = ((Map)ActivityThread.mProviderMap.get(VirtualCore.mainThread())).entrySet().iterator();
    while (iterator.hasNext()) {
      Object object1 = ((Map.Entry)iterator.next()).getValue();
      if (BuildCompat.isOreo()) {
        IInterface iInterface1 = (IInterface)ActivityThread.ProviderClientRecordJB.mProvider.get(object1);
        Object object = ActivityThread.ProviderClientRecordJB.mHolder.get(object1);
        if (object == null)
          continue; 
        ProviderInfo providerInfo1 = (ProviderInfo)ContentProviderHolderOreo.info.get(object);
        if (!providerInfo1.authority.startsWith(StubManifest.STUB_CP_AUTHORITY)) {
          iInterface1 = ProviderHook.createProxy(true, providerInfo1.authority, iInterface1);
          ActivityThread.ProviderClientRecordJB.mProvider.set(object1, iInterface1);
          ContentProviderHolderOreo.provider.set(object, iInterface1);
        } 
        continue;
      } 
      IInterface iInterface = (IInterface)ActivityThread.ProviderClientRecordJB.mProvider.get(object1);
      Object object2 = ActivityThread.ProviderClientRecordJB.mHolder.get(object1);
      if (object2 == null)
        continue; 
      ProviderInfo providerInfo = (ProviderInfo)IActivityManager.ContentProviderHolder.info.get(object2);
      if (!providerInfo.authority.startsWith(StubManifest.STUB_CP_AUTHORITY)) {
        IInterface iInterface1 = ProviderHook.createProxy(true, providerInfo.authority, iInterface);
        ActivityThread.ProviderClientRecordJB.mProvider.set(object1, iInterface1);
        IActivityManager.ContentProviderHolder.provider.set(object2, iInterface1);
      } 
    } 
  }
  
  private void fixSystem() {
    if (BuildCompat.isS())
      try {
        Reflect.on(Canvas.class).call("setCompatibilityVersion", new Object[] { Integer.valueOf(26) });
      } catch (Exception exception) {} 
    if (BuildCompat.isQ() && BuildCompat.isEMUI())
      XposedBridge.hookAllMethods(AutofillManager.class, "notifyViewEntered", new XC_MethodHook() {
            protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) throws Throwable {
              super.beforeHookedMethod(param1MethodHookParam);
              AutoFillManagerStub.disableAutoFill(param1MethodHookParam.thisObject);
            }
          }); 
  }
  
  private void fixWeChatRecovery(Application paramApplication) {
    try {
      Field field = paramApplication.getClassLoader().loadClass("com.tencent.recovery.Recovery").getField("context");
      field.setAccessible(true);
      if (field.get(null) != null)
        return; 
    } finally {
      paramApplication = null;
    } 
  }
  
  private void forbidHost() {
    for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager)VirtualCore.get().getContext().getSystemService("activity")).getRunningAppProcesses()) {
      if (runningAppProcessInfo.pid != Process.myPid() && runningAppProcessInfo.uid == VirtualCore.get().myUid() && !VActivityManager.get().isAppPid(runningAppProcessInfo.pid) && (runningAppProcessInfo.processName.startsWith(StubManifest.PACKAGE_NAME) || (StubManifest.EXT_PACKAGE_NAME != null && runningAppProcessInfo.processName.startsWith(StubManifest.EXT_PACKAGE_NAME)))) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/proc/");
        stringBuilder.append(runningAppProcessInfo.pid);
        stringBuilder.append("/maps");
        NativeEngine.forbid(stringBuilder.toString(), true);
        stringBuilder = new StringBuilder();
        stringBuilder.append("/proc/");
        stringBuilder.append(runningAppProcessInfo.pid);
        stringBuilder.append("/cmdline");
        NativeEngine.forbid(stringBuilder.toString(), true);
      } 
    } 
  }
  
  public static VClient get() {
    return gClient;
  }
  
  private HashSet<String> getMountPoints() {
    HashSet<String> hashSet = new HashSet(3);
    hashSet.add("/mnt/sdcard/");
    hashSet.add("/sdcard/");
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("/storage/emulated/");
    stringBuilder.append(VUserHandle.realUserId());
    stringBuilder.append("/");
    hashSet.add(stringBuilder.toString());
    stringBuilder = new StringBuilder();
    stringBuilder.append("storage/emulated/");
    stringBuilder.append(VUserHandle.realUserId());
    stringBuilder.append("/");
    hashSet.add(stringBuilder.toString());
    String[] arrayOfString = StorageManagerCompat.getAllPoints(VirtualCore.get().getContext());
    if (arrayOfString != null)
      Collections.addAll(hashSet, arrayOfString); 
    return hashSet;
  }
  
  private void handleNewIntent(NewIntentData paramNewIntentData) {
    Intent intent;
    ComponentUtils.unpackFillIn(paramNewIntentData.intent, get().getClassLoader());
    if (Build.VERSION.SDK_INT >= 22) {
      intent = (Intent)ReferrerIntent.ctor.newInstance(new Object[] { paramNewIntentData.intent, paramNewIntentData.creator });
    } else {
      intent = paramNewIntentData.intent;
    } 
    if (ActivityThread.performNewIntents != null) {
      ActivityThread.performNewIntents.call(VirtualCore.mainThread(), new Object[] { paramNewIntentData.token, Collections.singletonList(intent) });
    } else if (ActivityThreadNMR1.performNewIntents != null) {
      ActivityThreadNMR1.performNewIntents.call(VirtualCore.mainThread(), new Object[] { paramNewIntentData.token, Collections.singletonList(intent), Boolean.valueOf(true) });
    } else if (BuildCompat.isS()) {
      paramNewIntentData = (NewIntentData)((Map)ActivityThread.mActivities.get(VirtualCore.mainThread())).get(paramNewIntentData.token);
      if (paramNewIntentData != null)
        ActivityThread.handleNewIntent(paramNewIntentData, Collections.singletonList(intent)); 
    } else {
      ActivityThreadQ.handleNewIntent.call(VirtualCore.mainThread(), new Object[] { paramNewIntentData.token, Collections.singletonList(intent) });
    } 
  }
  
  private void handleReceiver(ReceiverData paramReceiverData) {
    BroadcastReceiver.PendingResult pendingResult = paramReceiverData.pendingResult;
    try {
      Context context1 = this.mInitialApplication.getBaseContext();
      Context context2 = (Context)ContextImpl.getReceiverRestrictedContext.call(context1, new Object[0]);
      ContextFixer.fixContext(context2, paramReceiverData.component.getPackageName());
      String str = paramReceiverData.component.getClassName();
      ClassLoader classLoader = (ClassLoader)LoadedApk.getClassLoader.call(this.mBoundApplication.info, new Object[0]);
      BroadcastReceiver broadcastReceiver = (BroadcastReceiver)classLoader.loadClass(str).newInstance();
      BroadcastReceiver.setPendingResult.call(broadcastReceiver, new Object[] { pendingResult });
      paramReceiverData.intent.setExtrasClassLoader(context1.getClassLoader());
      ComponentUtils.unpackFillIn(paramReceiverData.intent, classLoader);
      if (paramReceiverData.intent.getComponent() == null)
        paramReceiverData.intent.setComponent(paramReceiverData.component); 
      FakeIdentityBinder.setSystemIdentity();
      broadcastReceiver.onReceive(context2, paramReceiverData.intent);
      if (BroadcastReceiver.getPendingResult.call(broadcastReceiver, new Object[0]) != null) {
        IBinder iBinder = (IBinder)BroadcastReceiver.PendingResult.mToken.get(pendingResult);
        if (!VActivityManager.get().broadcastFinish(iBinder))
          pendingResult.finish(); 
      } 
      return;
    } catch (Exception exception) {
      paramReceiverData.stacktrace.printStackTrace();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to start receiver ");
      stringBuilder.append(paramReceiverData.component);
      stringBuilder.append(": ");
      stringBuilder.append(exception.toString());
      throw new RuntimeException(stringBuilder.toString(), exception);
    } 
  }
  
  private void initDataStorage(boolean paramBoolean, int paramInt, String paramString) {
    if (paramBoolean) {
      FileUtils.ensureDirCreate(VEnvironment.getDataUserPackageDirectoryExt(paramInt, paramString));
      FileUtils.ensureDirCreate(VEnvironment.getDeDataUserPackageDirectoryExt(paramInt, paramString));
    } else {
      FileUtils.ensureDirCreate(VEnvironment.getDataUserPackageDirectory(paramInt, paramString));
      FileUtils.ensureDirCreate(VEnvironment.getDeDataUserPackageDirectory(paramInt, paramString));
    } 
  }
  
  private void installContentProviders(Context paramContext, List<ProviderInfo> paramList) {
    long l = Binder.clearCallingIdentity();
    Object object = VirtualCore.mainThread();
    try {
      for (ProviderInfo providerInfo : paramList) {
        try {
          ActivityThread.installProvider(object, paramContext, providerInfo, null);
        } finally {
          providerInfo = null;
        } 
      } 
      return;
    } finally {
      Binder.restoreCallingIdentity(l);
    } 
  }
  
  private void mountVirtualFS(InstalledAppInfo paramInstalledAppInfo, boolean paramBoolean) {
    String str2;
    String str3;
    String str1 = paramInstalledAppInfo.packageName;
    int i = VUserHandle.myUserId();
    if (paramBoolean) {
      str2 = VEnvironment.getDataUserPackageDirectoryExt(i, str1).getPath();
      str3 = VEnvironment.getDeDataUserPackageDirectoryExtRoot(i).getPath();
      str4 = VEnvironment.getDataAppLibDirectoryExt(str1).getAbsolutePath();
    } else {
      str2 = VEnvironment.getDataUserPackageDirectory(i, str1).getPath();
      str3 = VEnvironment.getDeDataUserPackageDirectoryRoot(i).getPath();
      str4 = VEnvironment.getDataAppLibDirectory(str1).getAbsolutePath();
    } 
    if ((getDeviceConfig()).enable) {
      File file = getDeviceConfig().getWifiFile(i, paramBoolean);
      if (file != null && file.exists()) {
        String str = file.getPath();
        NativeEngine.redirectFile("/sys/class/net/wlan0/address", str);
        NativeEngine.redirectFile("/sys/class/net/eth0/address", str);
        NativeEngine.redirectFile("/sys/class/net/wifi/address", str);
      } 
    } 
    forbidHost();
    NativeEngine.redirectDirectory("/tmp/", (new File(str2, "cache")).getAbsolutePath());
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("/data/data/");
    stringBuilder.append(str1);
    NativeEngine.redirectDirectory(stringBuilder.toString(), str2);
    stringBuilder = new StringBuilder();
    stringBuilder.append("/data/user/");
    stringBuilder.append(VUserHandle.realUserId());
    stringBuilder.append("/");
    stringBuilder.append(str1);
    NativeEngine.redirectDirectory(stringBuilder.toString(), str2);
    if (Build.VERSION.SDK_INT >= 24) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("/data/user_de/");
      stringBuilder1.append(VUserHandle.realUserId());
      stringBuilder1.append("/");
      NativeEngine.redirectDirectory(stringBuilder1.toString(), str3);
    } 
    NativeEngine.whitelist(str4);
    if (paramInstalledAppInfo.dynamic) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("/data/user/");
      stringBuilder1.append(VUserHandle.realUserId());
      stringBuilder1.append("/");
      stringBuilder1.append(str1);
      stringBuilder1.append("/lib/");
      NativeEngine.whitelist(stringBuilder1.toString());
    } else {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("/data/data/");
      stringBuilder1.append(str1);
      stringBuilder1.append("/lib/");
      NativeEngine.redirectDirectory(stringBuilder1.toString(), str4);
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("/data/user/");
      stringBuilder1.append(VUserHandle.realUserId());
      stringBuilder1.append("/");
      stringBuilder1.append(str1);
      stringBuilder1.append("/lib/");
      NativeEngine.redirectDirectory(stringBuilder1.toString(), str4);
    } 
    NativeEngine.redirectDirectory(VEnvironment.getUserAppLibDirectory(i, str1).getPath(), str4);
    VirtualStorageManager virtualStorageManager = VirtualStorageManager.get();
    String str4 = virtualStorageManager.getVirtualStorage(paramInstalledAppInfo.packageName, i);
    if (virtualStorageManager.isVirtualStorageEnable(paramInstalledAppInfo.packageName, i) && str4 != null) {
      File file = new File(str4);
      if (file.exists() || file.mkdirs()) {
        Iterator<String> iterator = getMountPoints().iterator();
        while (iterator.hasNext())
          NativeEngine.redirectDirectory(iterator.next(), str4); 
      } 
    } else {
      redirectSdcard(paramInstalledAppInfo);
    } 
    if (!paramInstalledAppInfo.dynamic && (new File(paramInstalledAppInfo.getApkPath(paramBoolean))).exists()) {
      NativeEngine.redirectFile(VEnvironment.getPackageFileStub(str1), paramInstalledAppInfo.getApkPath(paramBoolean));
      if (Build.VERSION.SDK_INT == 27)
        NativeEngine.addDexOverride(new DexOverride(VEnvironment.getPackageFileStub(str1), paramInstalledAppInfo.getApkPath(paramBoolean), null, null)); 
    } 
    if (VirtualCore.getConfig().isEnableIORedirect())
      NativeEngine.enableIORedirect(paramInstalledAppInfo); 
  }
  
  private void redirectSdcard(InstalledAppInfo paramInstalledAppInfo) {
    SettingConfig settingConfig = VirtualCore.getConfig();
    redirectSdcardAndroidData();
    if (BuildCompat.isR() && VirtualCore.get().getTargetSdkVersion() >= 30) {
      ApplicationInfo applicationInfo = paramInstalledAppInfo.getApplicationInfo(VUserHandle.myUserId());
      if (applicationInfo == null)
        return; 
      if (applicationInfo.targetSdkVersion < 30) {
        HashSet<String> hashSet = getMountPoints();
        Context context = VirtualCore.get().getContext();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(settingConfig.getVirtualSdcardAndroidDataName());
        stringBuilder.append("/");
        stringBuilder.append(VUserHandle.myUserId());
        stringBuilder.append("/");
        File file = context.getExternalFilesDir(stringBuilder.toString());
        if (!file.exists() && !file.mkdirs()) {
          String str = TAG;
          stringBuilder = new StringBuilder();
          stringBuilder.append("failed to create dir: ");
          stringBuilder.append(file);
          VLog.e(str, stringBuilder.toString());
        } 
        for (String str : hashSet) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append(str);
          stringBuilder1.append("/");
          NativeEngine.redirectDirectory((new File(stringBuilder1.toString())).getPath(), file.getPath());
        } 
        for (String str : hashSet) {
          try {
            String[] arrayOfString = (String[])Reflect.on(Environment.class).field("STANDARD_DIRECTORIES").get();
            int i = arrayOfString.length;
            for (byte b = 0; b < i; b++)
              NativeEngine.whitelist(NativeEngine.pathCat(str, arrayOfString[b])); 
          } catch (Exception exception) {
            exception.printStackTrace();
          } 
        } 
      } 
    } 
  }
  
  private void redirectSdcardAndroidData() {
    SettingConfig settingConfig = VirtualCore.getConfig();
    HashSet<String> hashSet = getMountPoints();
    Context context = VirtualCore.get().getContext();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(settingConfig.getVirtualSdcardAndroidDataName());
    stringBuilder.append("/");
    stringBuilder.append(VUserHandle.myUserId());
    stringBuilder.append("/Android/data/");
    File file = context.getExternalFilesDir(stringBuilder.toString());
    if (!file.exists() && !file.mkdirs()) {
      String str = TAG;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("failed to create dir: ");
      stringBuilder1.append(file);
      VLog.e(str, stringBuilder1.toString());
    } 
    for (String str : hashSet) {
      for (byte b = 0; b < 2; b++) {
        (new String[2])[0] = "/Android/data/";
        (new String[2])[1] = "/Android/media/";
        String str1 = (new String[2])[b];
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str);
        stringBuilder1.append(str1);
        NativeEngine.redirectDirectory((new File(stringBuilder1.toString())).getPath(), file.getPath());
      } 
    } 
  }
  
  private void sendMessage(int paramInt, Object paramObject) {
    Message message = Message.obtain();
    message.what = paramInt;
    message.obj = paramObject;
    this.mH.sendMessage(message);
  }
  
  private void setupUncaughtHandler() {
    ThreadGroup threadGroup;
    for (threadGroup = Thread.currentThread().getThreadGroup(); threadGroup.getParent() != null; threadGroup = threadGroup.getParent());
    RootThreadGroup rootThreadGroup = new RootThreadGroup(threadGroup);
    if (Build.VERSION.SDK_INT < 24) {
      synchronized ((List)ThreadGroup.groups.get(threadGroup)) {
        ArrayList arrayList = new ArrayList();
        this(null);
        arrayList.remove(rootThreadGroup);
        ThreadGroup.groups.set(rootThreadGroup, arrayList);
        null.clear();
        null.add((E)rootThreadGroup);
        ThreadGroup.groups.set(threadGroup, null);
        for (ThreadGroup threadGroup1 : arrayList) {
          if (threadGroup1 == rootThreadGroup)
            continue; 
          ThreadGroup.parent.set(threadGroup1, rootThreadGroup);
        } 
      } 
    } else {
      synchronized ((ThreadGroup[])ThreadGroupN.groups.get(threadGroup)) {
        ThreadGroup[] arrayOfThreadGroup = (ThreadGroup[])null.clone();
        ThreadGroupN.groups.set(rootThreadGroup, arrayOfThreadGroup);
        RefObject refObject = ThreadGroupN.groups;
        byte b = 0;
        refObject.set(threadGroup, new ThreadGroup[] { rootThreadGroup });
        int i = arrayOfThreadGroup.length;
        while (b < i) {
          ThreadGroup threadGroup1 = arrayOfThreadGroup[b];
          if (threadGroup1 != null && threadGroup1 != rootThreadGroup)
            ThreadGroupN.parent.set(threadGroup1, rootThreadGroup); 
          b++;
        } 
        ThreadGroupN.ngroups.set(threadGroup, Integer.valueOf(1));
        return;
      } 
    } 
  }
  
  public IBinder acquireProviderClient(ProviderInfo paramProviderInfo) {
    IBinder iBinder;
    String str1;
    String str2;
    bindApplication(paramProviderInfo.packageName, paramProviderInfo.processName);
    String[] arrayOfString = paramProviderInfo.authority.split(";");
    if (arrayOfString.length == 0) {
      str1 = paramProviderInfo.authority;
    } else {
      str1 = str1[0];
    } 
    ContentResolver contentResolver = VirtualCore.get().getContext().getContentResolver();
    ProviderInfo providerInfo = null;
    try {
      ContentProviderClient contentProviderClient = contentResolver.acquireUnstableContentProviderClient(str1);
    } finally {
      str1 = null;
      str1.printStackTrace();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("acquireProviderClient ");
    stringBuilder.append(paramProviderInfo);
    stringBuilder.append(" result: ");
    stringBuilder.append(str1);
    stringBuilder.append(" process: ");
    stringBuilder.append(VirtualRuntime.getProcessName());
    VLog.e(str2, stringBuilder.toString());
    paramProviderInfo = providerInfo;
    if (str1 != null)
      iBinder = str1.asBinder(); 
    return iBinder;
  }
  
  public void addExportedVApiPkg(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mExportedVApiPkgs : Ljava/util/Set;
    //   6: aload_1
    //   7: invokeinterface add : (Ljava/lang/Object;)Z
    //   12: pop
    //   13: aload_0
    //   14: monitorexit
    //   15: return
    //   16: astore_1
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_1
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	16	finally
  }
  
  public void bindApplication(final String packageName, final String processName) {
    Map<String, Application> map;
    final ConditionVariable cond;
    synchronized (this.mAllApplications) {
      if (this.mAllApplications.containsKey(packageName))
        return; 
      if (this.clientConfig != null) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
          conditionVariable = new ConditionVariable();
          VirtualRuntime.getUIHandler().post(new Runnable() {
                public void run() {
                  VClient.this.bindApplicationMainThread(packageName, processName, cond);
                  cond.open();
                }
              });
          conditionVariable.block();
        } else {
          bindApplicationMainThread(packageName, processName, null);
        } 
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unrecorded process: ");
      stringBuilder.append(processName);
      throw new RuntimeException(stringBuilder.toString());
    } 
  }
  
  public IBinder createProxyService(ComponentName paramComponentName, IBinder paramIBinder) {
    return ProxyServiceFactory.getProxyService((Context)getCurrentApplication(), paramComponentName, paramIBinder);
  }
  
  public Service createService(ServiceInfo paramServiceInfo, IBinder paramIBinder) {
    bindApplication(paramServiceInfo.packageName, paramServiceInfo.processName);
    ClassLoader classLoader = (ClassLoader)LoadedApk.getClassLoader.call(this.mBoundApplication.info, new Object[0]);
    try {
      Service service = (Service)classLoader.loadClass(paramServiceInfo.name).newInstance();
      try {
        Context context = VirtualCore.get().getContext().createPackageContext(paramServiceInfo.packageName, 3);
        ContextImpl.setOuterContext.call(context, new Object[] { service });
        Service.attach.call(service, new Object[] { context, VirtualCore.mainThread(), paramServiceInfo.name, paramIBinder, this.mInitialApplication, ActivityManagerNative.getDefault.call(new Object[0]) });
        ContextFixer.fixContext(context, paramServiceInfo.packageName);
        service.onCreate();
        return service;
      } catch (Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to create service ");
        stringBuilder.append(paramServiceInfo.name);
        stringBuilder.append(": ");
        stringBuilder.append(exception.toString());
        throw new RuntimeException(stringBuilder.toString(), exception);
      } 
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to instantiate service ");
      stringBuilder.append(paramServiceInfo.name);
      stringBuilder.append(": ");
      stringBuilder.append(exception.toString());
      throw new RuntimeException(stringBuilder.toString(), exception);
    } 
  }
  
  public void finishActivity(IBinder paramIBinder) {
    sendMessage(13, paramIBinder);
  }
  
  public boolean finishReceiver(IBinder paramIBinder) {
    return StaticReceiverSystem.get().broadcastFinish(paramIBinder);
  }
  
  public InstalledAppInfo getAppInfo() {
    return this.mAppInfo;
  }
  
  public IBinder getAppThread() {
    return (IBinder)ActivityThread.getApplicationThread.call(VirtualCore.mainThread(), new Object[0]);
  }
  
  public int getBaseVUid() {
    ClientConfig clientConfig = this.clientConfig;
    return (clientConfig == null) ? 0 : VUserHandle.getAppId(clientConfig.vuid);
  }
  
  public ClassLoader getClassLoader() {
    return (ClassLoader)LoadedApk.getClassLoader.call(this.mBoundApplication.info, new Object[0]);
  }
  
  public ClassLoader getClassLoader(ApplicationInfo paramApplicationInfo) {
    return createPackageContext(paramApplicationInfo.packageName).getClassLoader();
  }
  
  public ClientConfig getClientConfig() {
    return this.clientConfig;
  }
  
  public CrashHandler getCrashHandler() {
    return this.crashHandler;
  }
  
  public Application getCurrentApplication() {
    return this.mInitialApplication;
  }
  
  public ApplicationInfo getCurrentApplicationInfo() {
    AppBindData appBindData = this.mBoundApplication;
    if (appBindData != null) {
      ApplicationInfo applicationInfo = appBindData.appInfo;
    } else {
      appBindData = null;
    } 
    return (ApplicationInfo)appBindData;
  }
  
  public String getCurrentPackage() {
    String str;
    AppBindData appBindData = this.mBoundApplication;
    if (appBindData != null) {
      str = appBindData.appInfo.packageName;
    } else {
      str = VPackageManager.get().getNameForUid(getVUid());
    } 
    return str;
  }
  
  public String getDebugInfo() {
    return VirtualRuntime.getProcessName();
  }
  
  public VDeviceConfig getDeviceConfig() {
    return VDeviceManager.get().getDeviceConfig(VUserHandle.getUserId(getVUid()));
  }
  
  public List<ActivityManager.RunningServiceInfo> getServices() {
    return VServiceRuntime.getInstance().getServices();
  }
  
  public IBinder getToken() {
    ClientConfig clientConfig = this.clientConfig;
    return (clientConfig == null) ? null : clientConfig.token;
  }
  
  public int getVUid() {
    ClientConfig clientConfig = this.clientConfig;
    return (clientConfig == null) ? 0 : clientConfig.vuid;
  }
  
  public int getVUserHandle() {
    ClientConfig clientConfig = this.clientConfig;
    return (clientConfig == null) ? 0 : VUserHandle.getUserId(clientConfig.vuid);
  }
  
  public int getVpid() {
    ClientConfig clientConfig = this.clientConfig;
    return (clientConfig == null) ? 0 : clientConfig.vpid;
  }
  
  public void initProcess(ClientConfig paramClientConfig) {
    if (this.clientConfig == null) {
      this.clientConfig = paramClientConfig;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("reject init process ");
    stringBuilder.append(paramClientConfig.vpid);
    stringBuilder.append(" : ");
    stringBuilder.append(paramClientConfig.processName);
    stringBuilder.append(", this process is : ");
    stringBuilder.append(this.clientConfig.processName);
    throw new RuntimeException(stringBuilder.toString());
  }
  
  public boolean isAppRunning() {
    boolean bool;
    if (this.mInitialApplication != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isDynamicApp() {
    boolean bool;
    InstalledAppInfo installedAppInfo = getAppInfo();
    if (installedAppInfo != null && installedAppInfo.dynamic) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isProcessBound() {
    boolean bool;
    if (this.clientConfig != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void scheduleNewIntent(String paramString, IBinder paramIBinder, Intent paramIntent) {
    NewIntentData newIntentData = new NewIntentData();
    newIntentData.creator = paramString;
    newIntentData.token = paramIBinder;
    newIntentData.intent = paramIntent;
    sendMessage(11, newIntentData);
  }
  
  public void scheduleReceiver(String paramString, ComponentName paramComponentName, Intent paramIntent, BroadcastReceiver.PendingResult paramPendingResult) {
    ReceiverData receiverData = new ReceiverData();
    receiverData.pendingResult = paramPendingResult;
    receiverData.intent = paramIntent;
    receiverData.component = paramComponentName;
    receiverData.processName = paramString;
    receiverData.stacktrace = new Exception();
    sendMessage(12, receiverData);
  }
  
  public void setCrashHandler(CrashHandler paramCrashHandler) {
    this.crashHandler = paramCrashHandler;
  }
  
  private static final class AppBindData {
    ApplicationInfo appInfo;
    
    Object info;
    
    String processName;
    
    List<ProviderInfo> providers;
    
    private AppBindData() {}
  }
  
  private class H extends Handler {
    private H() {
      super(Looper.getMainLooper());
    }
    
    public void handleMessage(Message param1Message) {
      switch (param1Message.what) {
        default:
          return;
        case 13:
          VActivityManager.get().finishActivity((IBinder)param1Message.obj);
        case 12:
          VClient.this.handleReceiver((VClient.ReceiverData)param1Message.obj);
        case 11:
          break;
      } 
      VClient.this.handleNewIntent((VClient.NewIntentData)param1Message.obj);
    }
  }
  
  private static final class NewIntentData {
    String creator;
    
    Intent intent;
    
    IBinder token;
    
    private NewIntentData() {}
  }
  
  private static final class ReceiverData {
    ComponentName component;
    
    Intent intent;
    
    BroadcastReceiver.PendingResult pendingResult;
    
    String processName;
    
    Throwable stacktrace;
    
    private ReceiverData() {}
  }
  
  private static class RootThreadGroup extends ThreadGroup {
    RootThreadGroup(ThreadGroup param1ThreadGroup) {
      super(param1ThreadGroup, "VA");
    }
    
    public void uncaughtException(Thread param1Thread, Throwable param1Throwable) {
      CrashHandler crashHandler = VClient.gClient.crashHandler;
      if (crashHandler != null) {
        crashHandler.handleUncaughtException(param1Thread, param1Throwable);
      } else {
        VLog.e("uncaught", param1Throwable);
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\VClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */