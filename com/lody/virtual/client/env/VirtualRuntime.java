package com.lody.virtual.client.env;

import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.utils.VLog;
import java.util.HashMap;
import java.util.Map;
import mirror.android.ddm.DdmHandleAppNameJBMR1;
import mirror.android.os.Process;
import mirror.dalvik.system.VMRuntime;

public class VirtualRuntime {
  private static final Map<String, String> ABI_TO_INSTRUCTION_SET_MAP;
  
  private static String sInitialPackageName;
  
  private static String sProcessName;
  
  private static final Handler sUIHandler = new Handler(Looper.getMainLooper());
  
  static {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>(16);
    ABI_TO_INSTRUCTION_SET_MAP = (Map)hashMap;
    hashMap.put("armeabi", "arm");
    ABI_TO_INSTRUCTION_SET_MAP.put("armeabi-v7a", "arm");
    ABI_TO_INSTRUCTION_SET_MAP.put("mips", "mips");
    ABI_TO_INSTRUCTION_SET_MAP.put("mips64", "mips64");
    ABI_TO_INSTRUCTION_SET_MAP.put("x86", "x86");
    ABI_TO_INSTRUCTION_SET_MAP.put("x86_64", "x86_64");
    ABI_TO_INSTRUCTION_SET_MAP.put("arm64-v8a", "arm64");
  }
  
  public static String adjustLibName(String paramString) {
    if (VirtualCore.get().isMainPackage())
      return paramString; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("_ext");
    return stringBuilder.toString();
  }
  
  public static <T> T crash(Throwable paramThrowable) throws RuntimeException {
    paramThrowable.printStackTrace();
    throw new RuntimeException("transact remote server failed", paramThrowable);
  }
  
  public static void exit() {
    VLog.d(VirtualRuntime.class.getSimpleName(), "Exit process : %s (%s).", new Object[] { getProcessName(), VirtualCore.get().getProcessName() });
    Process.killProcess(Process.myPid());
  }
  
  public static String getCurrentInstructionSet() {
    return (String)VMRuntime.getCurrentInstructionSet.call(new Object[0]);
  }
  
  public static String getInitialPackageName() {
    return sInitialPackageName;
  }
  
  public static String getInstructionSet(String paramString) {
    String str = ABI_TO_INSTRUCTION_SET_MAP.get(paramString);
    if (str != null)
      return str; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Unsupported ABI: ");
    stringBuilder.append(paramString);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public static String getProcessName() {
    return sProcessName;
  }
  
  public static Handler getUIHandler() {
    return sUIHandler;
  }
  
  public static boolean is64bit() {
    return (Build.VERSION.SDK_INT >= 23) ? Process.is64Bit() : ((Boolean)VMRuntime.is64Bit.call(VMRuntime.getRuntime.call(new Object[0]), new Object[0])).booleanValue();
  }
  
  public static boolean isArt() {
    return System.getProperty("java.vm.version").startsWith("2");
  }
  
  public static void setupRuntime(String paramString, ApplicationInfo paramApplicationInfo) {
    if (sProcessName != null)
      return; 
    sInitialPackageName = paramApplicationInfo.packageName;
    sProcessName = paramString;
    Process.setArgV0.call(new Object[] { paramString });
    DdmHandleAppNameJBMR1.setAppName.call(new Object[] { paramString, Integer.valueOf(0) });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\env\VirtualRuntime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */