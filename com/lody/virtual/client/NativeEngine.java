package com.lody.virtual.client;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.util.Pair;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.client.hiddenapibypass.HiddenApiBypass;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.client.natives.NativeMethods;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.remote.InstalledAppInfo;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NativeEngine {
  private static final String LIB_NAME = "v++";
  
  private static final List<Pair<String, String>> REDIRECT_LISTS;
  
  private static final String TAG = NativeEngine.class.getSimpleName();
  
  public static Field artMethodField;
  
  private static final List<DexOverride> sDexOverrides = new ArrayList<DexOverride>();
  
  private static boolean sEnabled;
  
  private static boolean sFlag = false;
  
  static {
    sEnabled = false;
    try {
      System.loadLibrary(VirtualRuntime.adjustLibName("v++"));
    } finally {
      Exception exception = null;
    } 
  }
  
  public static void addDexOverride(DexOverride paramDexOverride) {
    sDexOverrides.add(paramDexOverride);
  }
  
  public static void bypassHiddenAPIEnforcementPolicyIfNeeded() {
    if (BuildCompat.isR()) {
      HiddenApiBypass.setHiddenApiExemptions(new String[] { "L" });
    } else if (BuildCompat.isPie()) {
      try {
        Class clazz = (Class)Class.class.getDeclaredMethod("forName", new Class[] { String.class }).invoke(null, new Object[] { "dalvik.system.VMRuntime" });
        Method method1 = Class.class.getDeclaredMethod("getDeclaredMethod", new Class[] { String.class, Class[].class });
        Method method2 = (Method)method1.invoke(clazz, new Object[] { "getRuntime", new Class[0] });
        ((Method)method1.invoke(clazz, new Object[] { "setHiddenApiExemptions", { String[].class } })).invoke(method2.invoke(null, new Object[0]), new Object[] { { "L" } });
      } finally {
        Exception exception = null;
      } 
    } 
  }
  
  public static void enableIORedirect(InstalledAppInfo paramInstalledAppInfo) {
    if (sEnabled)
      return; 
    try {
      ApplicationInfo applicationInfo = VirtualCore.get().getHostPackageManager().getApplicationInfo(VirtualCore.getConfig().getMainPackageName(), 0);
      Collections.sort(REDIRECT_LISTS, new Comparator<Pair<String, String>>() {
            private int compare(int param1Int1, int param1Int2) {
              return Integer.compare(param1Int1, param1Int2);
            }
            
            public int compare(Pair<String, String> param1Pair1, Pair<String, String> param1Pair2) {
              String str = (String)param1Pair1.first;
              return compare(((String)param1Pair2.first).length(), str.length());
            }
          });
      for (Pair<String, String> pair : REDIRECT_LISTS) {
        try {
          nativeIORedirect((String)pair.first, (String)pair.second);
        } finally {
          pair = null;
        } 
      } 
      try {
        File file = new File();
        this(applicationInfo.nativeLibraryDir, "libv++.so");
        String str = file.getAbsolutePath();
        file = new File();
        this(applicationInfo.nativeLibraryDir, "libv++_ext.so");
        nativeEnableIORedirect(str, file.getAbsolutePath(), VEnvironment.getNativeCacheDir(VirtualCore.get().isExtPackage()).getPath(), Build.VERSION.SDK_INT, paramInstalledAppInfo.packageName, VirtualCore.get().getHostPkg());
      } finally {
        paramInstalledAppInfo = null;
      } 
      return;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      throw new RuntimeException(nameNotFoundException);
    } 
  }
  
  private static DexOverride findDexOverride(String paramString) {
    for (DexOverride dexOverride : sDexOverrides) {
      if (dexOverride.originDexPath.equals(paramString))
        return dexOverride; 
    } 
    return null;
  }
  
  public static void forbid(String paramString, boolean paramBoolean) {
    String str = paramString;
    if (!paramBoolean) {
      str = paramString;
      if (!paramString.endsWith("/")) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramString);
        stringBuilder.append("/");
        str = stringBuilder.toString();
      } 
    } 
    try {
      nativeIOForbid(str);
    } finally {
      paramString = null;
    } 
  }
  
  public static long getArtMethod(Member paramMember) {
    if (artMethodField == null)
      try {
        artMethodField = getField(Method.class, "artMethod");
      } catch (NoSuchFieldException noSuchFieldException) {} 
    Field field = artMethodField;
    if (field == null)
      return 0L; 
    try {
      return ((Long)field.get(paramMember)).longValue();
    } catch (IllegalAccessException illegalAccessException) {
      return 0L;
    } 
  }
  
  private static String getCanonicalPath(String paramString) {
    File file = new File(paramString);
    try {
      return file.getCanonicalPath();
    } catch (IOException iOException) {
      iOException.printStackTrace();
      return file.getAbsolutePath();
    } 
  }
  
  private static Field getField(Class<Object> paramClass, String paramString) throws NoSuchFieldException {
    while (paramClass != null && paramClass != Object.class) {
      try {
        Field field = paramClass.getDeclaredField(paramString);
        field.setAccessible(true);
        return field;
      } catch (Exception exception) {
        paramClass = (Class)paramClass.getSuperclass();
      } 
    } 
    throw new NoSuchFieldException(paramString);
  }
  
  public static String getRedirectedPath(String paramString) {
    try {
      return nativeGetRedirectedPath(paramString);
    } finally {
      Exception exception = null;
      VLog.e(TAG, VLog.getStackTraceString(exception));
    } 
  }
  
  public static void launchEngine(String paramString) {
    if (sFlag)
      return; 
    Method method1 = NativeMethods.gNativeMask;
    Method method2 = NativeMethods.gOpenDexFileNative;
    Method method3 = NativeMethods.gCameraNativeSetup;
    Method method4 = NativeMethods.gAudioRecordNativeCheckPermission;
    Method method5 = NativeMethods.gMediaRecorderNativeSetup;
    Method method6 = NativeMethods.gAudioRecordNativeSetup;
    Method method7 = NativeMethods.gNativeLoad;
    try {
      int i;
      String str = VirtualCore.get().getHostPkg();
      boolean bool = VirtualRuntime.isArt();
      if (BuildCompat.isR()) {
        i = 30;
      } else {
        i = Build.VERSION.SDK_INT;
      } 
      int j = NativeMethods.gCameraMethodType;
      int k = NativeMethods.gAudioRecordMethodType;
    } finally {
      paramString = null;
    } 
    sFlag = true;
  }
  
  private static native void nativeEnableIORedirect(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5);
  
  private static native String nativeGetRedirectedPath(String paramString);
  
  private static native void nativeIOForbid(String paramString);
  
  private static native void nativeIOReadOnly(String paramString);
  
  private static native void nativeIORedirect(String paramString1, String paramString2);
  
  private static native void nativeIOWhitelist(String paramString);
  
  private static native void nativeLaunchEngine(Object[] paramArrayOfObject, String paramString1, String paramString2, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3);
  
  private static native void nativeMark();
  
  private static native String nativeReverseRedirectedPath(String paramString);
  
  public static int onGetCallingUid(int paramInt) {
    if (VClient.get().getClientConfig() == null)
      return paramInt; 
    if (paramInt != VirtualCore.get().myUid() && paramInt != VirtualCore.get().remoteUid())
      return paramInt; 
    paramInt = Binder.getCallingPid();
    if (paramInt == 0)
      return BuildCompat.isS() ? VClient.get().getBaseVUid() : 9001; 
    if (paramInt == Process.myPid())
      return VClient.get().getBaseVUid(); 
    int i = VActivityManager.get().getUidByPid(paramInt);
    paramInt = i;
    if (i == 9000)
      paramInt = 1000; 
    return paramInt;
  }
  
  public static int onGetUid(int paramInt) {
    return (VClient.get().getClientConfig() == null) ? paramInt : VClient.get().getBaseVUid();
  }
  
  public static boolean onKillProcess(int paramInt1, int paramInt2) {
    VLog.e(TAG, "killProcess: pid = %d, signal = %d.", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) });
    if (paramInt1 == Process.myPid())
      VLog.e(TAG, VLog.getStackTraceString(new Throwable())); 
    return true;
  }
  
  public static void onOpenDexFileNative(String[] paramArrayOfString) {
    String str = paramArrayOfString[0];
    if (str != null) {
      DexOverride dexOverride = findDexOverride(getCanonicalPath(str));
      if (dexOverride != null) {
        String str1 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("override: ");
        stringBuilder.append(dexOverride.newOdexPath);
        VLog.e(str1, stringBuilder.toString());
        if (dexOverride.newDexPath != null)
          paramArrayOfString[0] = dexOverride.newDexPath; 
        String str2 = dexOverride.newDexPath;
        if (dexOverride.originOdexPath != null) {
          if (getCanonicalPath(str2).equals(dexOverride.originOdexPath))
            paramArrayOfString[1] = dexOverride.newOdexPath; 
        } else {
          paramArrayOfString[1] = dexOverride.newOdexPath;
        } 
      } 
    } 
    VLog.i(TAG, "OpenDexFileNative(\"%s\", \"%s\")", new Object[] { paramArrayOfString[0], paramArrayOfString[1] });
  }
  
  public static String pathCat(String paramString1, String paramString2) {
    String str = paramString1;
    if (!TextUtils.isEmpty(paramString2)) {
      str = paramString1;
      if (!paramString1.endsWith("/")) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(paramString1);
        stringBuilder1.append("/");
        str = stringBuilder1.toString();
      } 
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append(paramString2);
    return stringBuilder.toString();
  }
  
  public static void readOnly(String paramString) {
    String str = paramString;
    if (!paramString.endsWith("/")) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append("/");
      str = stringBuilder.toString();
    } 
    try {
      nativeIOReadOnly(str);
    } finally {
      paramString = null;
    } 
  }
  
  public static void readOnlyFile(String paramString) {
    try {
      nativeIOReadOnly(paramString);
    } finally {
      paramString = null;
    } 
  }
  
  public static void redirectDirectory(String paramString1, String paramString2) {
    String str1;
    String str2 = paramString1;
    if (!paramString1.endsWith("/")) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString1);
      stringBuilder.append("/");
      str2 = stringBuilder.toString();
    } 
    paramString1 = paramString2;
    if (!paramString2.endsWith("/")) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString2);
      stringBuilder.append("/");
      str1 = stringBuilder.toString();
    } 
    REDIRECT_LISTS.add(new Pair(str2, str1));
  }
  
  public static void redirectFile(String paramString1, String paramString2) {
    String str = paramString1;
    if (paramString1.endsWith("/"))
      str = paramString1.substring(0, paramString1.length() - 1); 
    paramString1 = paramString2;
    if (paramString2.endsWith("/"))
      paramString1 = paramString2.substring(0, paramString2.length() - 1); 
    REDIRECT_LISTS.add(new Pair(str, paramString1));
  }
  
  public static String reverseRedirectedPath(String paramString) {
    try {
      return nativeReverseRedirectedPath(paramString);
    } finally {
      Exception exception = null;
      VLog.e(TAG, VLog.getStackTraceString(exception));
    } 
  }
  
  public static void startDexOverride() {
    for (InstalledAppInfo installedAppInfo : VirtualCore.get().getInstalledApps(0)) {
      if (!installedAppInfo.dynamic)
        addDexOverride(new DexOverride(getCanonicalPath(installedAppInfo.getApkPath()), null, null, installedAppInfo.getOatPath())); 
    } 
  }
  
  public static void whitelist(String paramString) {
    String str = paramString;
    if (!paramString.endsWith("/")) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append("/");
      str = stringBuilder.toString();
    } 
    try {
      nativeIOWhitelist(str);
    } finally {
      paramString = null;
    } 
  }
  
  public static void whitelistFile(String paramString) {
    try {
      nativeIOWhitelist(paramString);
    } finally {
      paramString = null;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\NativeEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */