package com.lody.virtual.helper.compat;

import android.text.TextUtils;
import com.lody.virtual.helper.utils.Reflect;

public class SystemPropertiesCompat {
  public static String get(String paramString) {
    try {
      return (String)Reflect.on("android.os.SystemProperties").call("get", new Object[] { paramString }).get();
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
  
  public static String get(String paramString1, String paramString2) {
    try {
      return (String)Reflect.on("android.os.SystemProperties").call("get", new Object[] { paramString1, paramString2 }).get();
    } catch (Exception exception) {
      exception.printStackTrace();
      return paramString2;
    } 
  }
  
  public static int getInt(String paramString, int paramInt) {
    try {
      return ((Integer)Reflect.on("android.os.SystemProperties").call("getInt", new Object[] { paramString, Integer.valueOf(paramInt) }).get()).intValue();
    } catch (Exception exception) {
      exception.printStackTrace();
      return paramInt;
    } 
  }
  
  public static boolean isExist(String paramString) {
    return TextUtils.isEmpty(get(paramString)) ^ true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\SystemPropertiesCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */