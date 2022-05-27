package de.robv.android.xposed;

import android.util.Log;
import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class XposedInit {
  private static final String INSTANT_RUN_CLASS = "com.android.tools.fd.runtime.BootstrapApplication";
  
  private static final String TAG = "SandXposed";
  
  private static volatile AtomicBoolean bootstrapHooked = new AtomicBoolean(false);
  
  private static boolean disableResources = true;
  
  private static volatile AtomicBoolean modulesLoaded = new AtomicBoolean(false);
  
  static void hookResources() throws Throwable {}
  
  public static void loadModule(String paramString1, String paramString2, String paramString3, ClassLoader paramClassLoader) {
    if (!(new File(paramString1)).exists()) {
      Log.e("SandXposed", "  File does not exist");
      return;
    } 
    try {
      InputStream inputStream1;
      InputStream inputStream2;
      DexFile dexFile = new DexFile(paramString1);
      if (dexFile.loadClass("com.android.tools.fd.runtime.BootstrapApplication", paramClassLoader) != null) {
        Log.e("SandXposed", "  Cannot load module, please disable \"Instant Run\" in Android Studio.");
        XposedHelpers.closeSilently(dexFile);
        return;
      } 
      if (dexFile.loadClass(XposedBridge.class.getName(), paramClassLoader) != null) {
        Log.e("SandXposed", "  Cannot load module:");
        Log.e("SandXposed", "  The Xposed API classes are compiled into the module's APK.");
        Log.e("SandXposed", "  This may cause strange issues and must be fixed by the module developer.");
        Log.e("SandXposed", "  For details, see: http://api.xposed.info/using.html");
        XposedHelpers.closeSilently(dexFile);
        return;
      } 
      XposedHelpers.closeSilently(dexFile);
      ZipEntry zipEntry = null;
      try {
        ZipFile zipFile = new ZipFile();
        this(paramString1);
        try {
          zipEntry = zipFile.getEntry("assets/xposed_init");
          if (zipEntry == null) {
            Log.e("SandXposed", "  assets/xposed_init not found in the APK");
            XposedHelpers.closeSilently(zipFile);
            return;
          } 
          inputStream2 = zipFile.getInputStream(zipEntry);
          DexClassLoader dexClassLoader = new DexClassLoader(paramString1, paramString2, paramString3, paramClassLoader);
          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream2));
          try {
            while (true) {
              String str = bufferedReader.readLine();
              if (str != null) {
                str = str.trim();
                if (!str.isEmpty()) {
                  boolean bool = str.startsWith("#");
                  if (bool)
                    continue; 
                  try {
                    StringBuilder stringBuilder = new StringBuilder();
                    this();
                    stringBuilder.append("  Loading class ");
                    stringBuilder.append(str);
                    Log.i("SandXposed", stringBuilder.toString());
                    Class<?> clazz = dexClassLoader.loadClass(str);
                    if (!IXposedMod.class.isAssignableFrom(clazz)) {
                      Log.e("SandXposed", "    This class doesn't implement any sub-interface of IXposedMod, skipping it");
                      continue;
                    } 
                    if (disableResources && IXposedHookInitPackageResources.class.isAssignableFrom(clazz)) {
                      Log.e("SandXposed", "    This class requires resource-related hooks (which are disabled), skipping it.");
                      continue;
                    } 
                    clazz = (Class<?>)clazz.newInstance();
                    if (clazz instanceof IXposedHookZygoteInit) {
                      IXposedHookZygoteInit.StartupParam startupParam = new IXposedHookZygoteInit.StartupParam();
                      this();
                      startupParam.modulePath = paramString1;
                      startupParam.startsSystemServer = false;
                      ((IXposedHookZygoteInit)clazz).initZygote(startupParam);
                    } 
                    if (clazz instanceof IXposedHookLoadPackage) {
                      IXposedHookLoadPackage.Wrapper wrapper = new IXposedHookLoadPackage.Wrapper();
                      this((IXposedHookLoadPackage)clazz);
                      XposedBridge.hookLoadPackage(wrapper);
                    } 
                    if (!(clazz instanceof IXposedHookInitPackageResources))
                      continue; 
                    UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
                    this("can not hook resource!");
                    throw unsupportedOperationException;
                  } finally {
                    Exception exception = null;
                    StringBuilder stringBuilder = new StringBuilder();
                    this();
                    stringBuilder.append("    Failed to load class ");
                    stringBuilder.append(str);
                    Log.e("SandXposed", stringBuilder.toString(), exception);
                  } 
                } 
                continue;
              } 
              break;
            } 
            XposedHelpers.closeSilently(inputStream2);
            XposedHelpers.closeSilently(zipFile);
          } catch (IOException iOException1) {
            StringBuilder stringBuilder = new StringBuilder();
            this();
            stringBuilder.append("  Failed to load module from ");
            stringBuilder.append(paramString1);
            Log.e("SandXposed", stringBuilder.toString(), iOException1);
            XposedHelpers.closeSilently(inputStream2);
            XposedHelpers.closeSilently(zipFile);
          } finally {}
          return;
        } catch (IOException null) {
          ZipFile zipFile1 = zipFile;
        } 
      } catch (IOException iOException) {
        inputStream1 = inputStream2;
      } 
      Log.e("SandXposed", "  Cannot read assets/xposed_init in the APK", iOException);
      XposedHelpers.closeSilently((ZipFile)inputStream1);
      return;
    } catch (IOException iOException) {
      Log.e("SandXposed", "  Cannot load module", iOException);
      return;
    } 
  }
  
  private static boolean needsToCloseFilesForFork() {
    return false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\XposedInit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */