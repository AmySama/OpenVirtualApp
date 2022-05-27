package com.swift.sandhook.xposedcompat.utils;

import android.app.Application;
import java.lang.reflect.Method;

public class ApplicationUtils {
  static Application application;
  
  private static Class classActivityThread;
  
  private static Method currentApplicationMethod;
  
  public static Application currentApplication() {
    Application application = application;
    if (application != null)
      return application; 
    if (currentApplicationMethod == null)
      try {
        Class<?> clazz = Class.forName("android.app.ActivityThread");
        classActivityThread = clazz;
        currentApplicationMethod = clazz.getDeclaredMethod("currentApplication", new Class[0]);
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    Method method = currentApplicationMethod;
    if (method == null)
      return null; 
    try {
      application = (Application)method.invoke(null, new Object[0]);
    } catch (Exception exception) {}
    return application;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\xposedcompa\\utils\ApplicationUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */