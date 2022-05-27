package com.tencent.mm.opensdk.utils;

public class Log {
  private static ILog logImpl;
  
  public static void d(String paramString1, String paramString2) {
    ILog iLog = logImpl;
    if (iLog == null) {
      android.util.Log.d(paramString1, paramString2);
    } else {
      iLog.d(paramString1, paramString2);
    } 
  }
  
  public static void e(String paramString1, String paramString2) {
    ILog iLog = logImpl;
    if (iLog == null) {
      android.util.Log.e(paramString1, paramString2);
    } else {
      iLog.e(paramString1, paramString2);
    } 
  }
  
  public static void i(String paramString1, String paramString2) {
    ILog iLog = logImpl;
    if (iLog == null) {
      android.util.Log.i(paramString1, paramString2);
    } else {
      iLog.i(paramString1, paramString2);
    } 
  }
  
  public static void setLogImpl(ILog paramILog) {
    logImpl = paramILog;
  }
  
  public static void v(String paramString1, String paramString2) {
    ILog iLog = logImpl;
    if (iLog == null) {
      android.util.Log.v(paramString1, paramString2);
    } else {
      iLog.v(paramString1, paramString2);
    } 
  }
  
  public static void w(String paramString1, String paramString2) {
    ILog iLog = logImpl;
    if (iLog == null) {
      android.util.Log.w(paramString1, paramString2);
    } else {
      iLog.w(paramString1, paramString2);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensd\\utils\Log.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */