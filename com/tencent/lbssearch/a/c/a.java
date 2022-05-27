package com.tencent.lbssearch.a.c;

import android.text.TextUtils;
import android.util.Log;

public class a {
  private static Boolean a = Boolean.valueOf(false);
  
  public static void a(String paramString) {
    a(null, paramString, 'e');
  }
  
  private static void a(String paramString1, String paramString2, char paramChar) {
    if ('e' != paramChar && !a.booleanValue())
      return; 
    String str = paramString1;
    if (TextUtils.isEmpty(paramString1))
      str = "js"; 
    if ('e' == paramChar) {
      Log.e(str, paramString2);
    } else if ('w' == paramChar) {
      Log.w(str, paramString2);
    } else if ('d' == paramChar) {
      Log.d(str, paramString2);
    } else if ('i' == paramChar) {
      Log.i(str, paramString2);
    } else {
      Log.v(str, paramString2);
    } 
  }
  
  public static void b(String paramString) {
    a(null, paramString, 'v');
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\c\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */