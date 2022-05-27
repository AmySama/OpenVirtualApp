package com.lody.virtual.helper.utils;

import android.os.Bundle;
import android.util.Log;
import java.util.Set;

public class VLog {
  public static boolean OPEN_LOG = true;
  
  public static final String TAG_DEFAULT = "VA";
  
  public static void d(String paramString1, String paramString2, Object... paramVarArgs) {
    if (OPEN_LOG)
      Log.d(paramString1, String.format(paramString2, paramVarArgs)); 
  }
  
  public static void e(String paramString1, String paramString2) {
    if (OPEN_LOG)
      Log.e(paramString1, paramString2); 
  }
  
  public static void e(String paramString1, String paramString2, Object... paramVarArgs) {
    if (OPEN_LOG)
      Log.e(paramString1, String.format(paramString2, paramVarArgs)); 
  }
  
  public static void e(String paramString, Throwable paramThrowable) {
    Log.e(paramString, getStackTraceString(paramThrowable));
  }
  
  public static String getStackTraceString(Throwable paramThrowable) {
    return Log.getStackTraceString(paramThrowable);
  }
  
  public static void i(String paramString1, String paramString2, Object... paramVarArgs) {
    if (OPEN_LOG)
      Log.i(paramString1, String.format(paramString2, paramVarArgs)); 
  }
  
  public static void logbug(String paramString1, String paramString2) {
    d(paramString1, paramString2, new Object[0]);
  }
  
  public static void printStackTrace(String paramString) {
    Log.e(paramString, getStackTraceString(new Exception()));
  }
  
  public static String toString(Bundle paramBundle) {
    if (paramBundle == null)
      return null; 
    if (Reflect.on(paramBundle).get("mParcelledData") != null) {
      Set set = paramBundle.keySet();
      StringBuilder stringBuilder = new StringBuilder("Bundle[");
      if (set != null)
        for (String str : set) {
          stringBuilder.append(str);
          stringBuilder.append("=");
          stringBuilder.append(paramBundle.get(str));
          stringBuilder.append(",");
        }  
      stringBuilder.append("]");
      return stringBuilder.toString();
    } 
    return paramBundle.toString();
  }
  
  public static void v(String paramString1, String paramString2) {
    if (OPEN_LOG)
      Log.v(paramString1, paramString2); 
  }
  
  public static void v(String paramString1, String paramString2, Object... paramVarArgs) {
    if (OPEN_LOG)
      Log.v(paramString1, String.format(paramString2, paramVarArgs)); 
  }
  
  public static void w(String paramString1, String paramString2, Object... paramVarArgs) {
    if (OPEN_LOG)
      Log.w(paramString1, String.format(paramString2, paramVarArgs)); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\VLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */