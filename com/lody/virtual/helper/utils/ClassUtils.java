package com.lody.virtual.helper.utils;

public class ClassUtils {
  public static void fixArgs(Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject) {
    for (byte b = 0; b < paramArrayOfClass.length; b++) {
      if (paramArrayOfClass[b] == int.class && paramArrayOfObject[b] == null) {
        paramArrayOfObject[b] = Integer.valueOf(0);
      } else if (paramArrayOfClass[b] == boolean.class && paramArrayOfObject[b] == null) {
        paramArrayOfObject[b] = Boolean.valueOf(false);
      } 
    } 
  }
  
  public static boolean isClassExist(String paramString) {
    try {
      Class.forName(paramString);
      return true;
    } catch (ClassNotFoundException classNotFoundException) {
      return false;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\ClassUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */