package com.swift.sandhook.xposedcompat.utils;

import android.util.Log;
import com.swift.sandhook.HookLog;
import java.lang.reflect.Member;

public class DexLog {
  public static boolean DEBUG = HookLog.DEBUG;
  
  public static final String TAG = "SandXposed";
  
  public static int d(String paramString) {
    return Log.d("SandXposed", paramString);
  }
  
  public static int e(String paramString) {
    return Log.e("SandXposed", paramString);
  }
  
  public static int e(String paramString, Throwable paramThrowable) {
    return Log.e("SandXposed", paramString, paramThrowable);
  }
  
  public static int i(String paramString) {
    return Log.i("SandXposed", paramString);
  }
  
  public static void printCallOriginError(Member paramMember) {
    if (paramMember != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("method <");
      stringBuilder.append(paramMember.toString());
      stringBuilder.append("> call origin error!");
      Log.e("SandXposed", stringBuilder.toString());
    } 
  }
  
  public static void printMethodHookIn(Member paramMember) {
    if (DEBUG && paramMember != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("method <");
      stringBuilder.append(paramMember.toString());
      stringBuilder.append("> hook in");
      Log.d("SandXposed", stringBuilder.toString());
    } 
  }
  
  public static int v(String paramString) {
    return Log.v("SandXposed", paramString);
  }
  
  public static int w(String paramString) {
    return Log.w("SandXposed", paramString);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\xposedcompa\\utils\DexLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */