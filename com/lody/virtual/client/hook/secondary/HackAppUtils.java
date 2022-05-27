package com.lody.virtual.client.hook.secondary;

import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.helper.utils.ReflectException;

public class HackAppUtils {
  public static void enableQQLogOutput(String paramString, ClassLoader paramClassLoader) {
    if ("com.tencent.mobileqq".equals(paramString))
      try {
        Reflect.on("com.tencent.qphone.base.util.QLog", paramClassLoader).set("UIN_REPORTLOG_LEVEL", Integer.valueOf(100));
      } catch (ReflectException reflectException) {} 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\secondary\HackAppUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */