package com.swift.sandhook.xposedcompat;

import android.app.Application;
import android.content.Context;
import com.swift.sandhook.xposedcompat.classloaders.ProxyClassLoader;
import com.swift.sandhook.xposedcompat.methodgen.DynamicBridge;
import com.swift.sandhook.xposedcompat.utils.ApplicationUtils;
import com.swift.sandhook.xposedcompat.utils.DexMakerUtils;
import com.swift.sandhook.xposedcompat.utils.FileUtils;
import com.swift.sandhook.xposedcompat.utils.ProcessUtils;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedInit;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XCallback;
import java.io.File;

public class XposedCompat {
  public static File cacheDir;
  
  public static volatile ClassLoader classLoader;
  
  public static Context context;
  
  public static boolean isFirstApplication = false;
  
  public static String packageName;
  
  public static String processName;
  
  public static volatile boolean retryWhenCallOriginError = false;
  
  private static ClassLoader sandHookXposedClassLoader;
  
  public static volatile boolean useInternalStub = true;
  
  public static volatile boolean useNewCallBackup = true;
  
  public static void addXposedModuleCallback(IXposedHookLoadPackage paramIXposedHookLoadPackage) {
    XposedBridge.hookLoadPackage((XC_LoadPackage)new IXposedHookLoadPackage.Wrapper(paramIXposedHookLoadPackage));
  }
  
  public static void callXposedModuleInit() throws Throwable {
    XC_LoadPackage.LoadPackageParam loadPackageParam = new XC_LoadPackage.LoadPackageParam(XposedBridge.sLoadedPackageCallbacks);
    Application application = ApplicationUtils.currentApplication();
    if (application != null) {
      if (loadPackageParam.packageName == null)
        loadPackageParam.packageName = application.getPackageName(); 
      if (loadPackageParam.processName == null)
        loadPackageParam.processName = ProcessUtils.getProcessName((Context)application); 
      if (loadPackageParam.classLoader == null)
        loadPackageParam.classLoader = application.getClassLoader(); 
      if (loadPackageParam.appInfo == null)
        loadPackageParam.appInfo = application.getApplicationInfo(); 
      if (cacheDir == null)
        application.getCacheDir(); 
    } 
    XC_LoadPackage.callAll((XCallback.Param)loadPackageParam);
  }
  
  public static boolean clearCache() {
    try {
      FileUtils.delete(getCacheDir());
      return true;
    } finally {
      Exception exception = null;
    } 
  }
  
  public static void clearOatCache() {
    DynamicBridge.clearOatFile();
  }
  
  public static File getCacheDir() {
    if (cacheDir == null) {
      if (context == null)
        context = (Context)ApplicationUtils.currentApplication(); 
      if (context != null) {
        File file = context.getCacheDir();
        String str = processName;
        if (str == null)
          str = ProcessUtils.getProcessName(context); 
        cacheDir = new File(file, DexMakerUtils.MD5(str));
      } 
    } 
    return cacheDir;
  }
  
  public static ClassLoader getSandHookXposedClassLoader(ClassLoader paramClassLoader1, ClassLoader paramClassLoader2) {
    ClassLoader classLoader = sandHookXposedClassLoader;
    if (classLoader != null)
      return classLoader; 
    ProxyClassLoader proxyClassLoader = new ProxyClassLoader(paramClassLoader2, paramClassLoader1);
    sandHookXposedClassLoader = (ClassLoader)proxyClassLoader;
    return (ClassLoader)proxyClassLoader;
  }
  
  public static void loadModule(String paramString1, String paramString2, String paramString3, ClassLoader paramClassLoader) {
    XposedInit.loadModule(paramString1, paramString2, paramString3, paramClassLoader);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\xposedcompat\XposedCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */