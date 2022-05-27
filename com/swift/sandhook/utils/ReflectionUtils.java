package com.swift.sandhook.utils;

import android.util.Log;
import java.lang.reflect.Method;

public class ReflectionUtils {
  static Method addWhiteListMethod;
  
  public static Method forNameMethod;
  
  public static Method getMethodMethod;
  
  static Object vmRuntime;
  
  static Class vmRuntimeClass;
  
  static {
    try {
      getMethodMethod = Class.class.getDeclaredMethod("getDeclaredMethod", new Class[] { String.class, Class[].class });
      Method method = Class.class.getDeclaredMethod("forName", new Class[] { String.class });
      forNameMethod = method;
      Class clazz = (Class)method.invoke(null, new Object[] { "dalvik.system.VMRuntime" });
      vmRuntimeClass = clazz;
      addWhiteListMethod = (Method)getMethodMethod.invoke(clazz, new Object[] { "setHiddenApiExemptions", { String[].class } });
      vmRuntime = ((Method)getMethodMethod.invoke(vmRuntimeClass, new Object[] { "getRuntime", null })).invoke(null, new Object[0]);
    } catch (Exception exception) {
      Log.e("ReflectionUtils", "error get methods", exception);
    } 
  }
  
  public static void addReflectionWhiteList(String... paramVarArgs) throws Throwable {
    addWhiteListMethod.invoke(vmRuntime, new Object[] { paramVarArgs });
  }
  
  public static boolean passApiCheck() {
    try {
      return true;
    } finally {
      Exception exception = null;
      exception.printStackTrace();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhoo\\utils\ReflectionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */