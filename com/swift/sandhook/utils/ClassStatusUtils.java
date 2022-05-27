package com.swift.sandhook.utils;

import com.swift.sandhook.SandHookConfig;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

public class ClassStatusUtils {
  static Field fieldStatusOfClass;
  
  static {
    try {
      Field field = Class.class.getDeclaredField("status");
      fieldStatusOfClass = field;
      field.setAccessible(true);
    } catch (NoSuchFieldException noSuchFieldException) {}
  }
  
  public static int getClassStatus(Class paramClass, boolean paramBoolean) {
    int i = 0;
    if (paramClass == null)
      return 0; 
    try {
      int k = fieldStatusOfClass.getInt(paramClass);
      i = k;
    } finally {}
    int j = i;
    if (paramBoolean)
      j = (int)(toUnsignedLong(i) >> 28L); 
    return j;
  }
  
  public static boolean isInitialized(Class paramClass) {
    Field field = fieldStatusOfClass;
    boolean bool1 = true;
    boolean bool2 = true;
    boolean bool3 = true;
    if (field == null)
      return true; 
    if (SandHookConfig.SDK_INT >= 28) {
      if (getClassStatus(paramClass, true) < 14)
        bool3 = false; 
      return bool3;
    } 
    if (SandHookConfig.SDK_INT == 27) {
      if (getClassStatus(paramClass, false) == 11) {
        bool3 = bool1;
      } else {
        bool3 = false;
      } 
      return bool3;
    } 
    if (getClassStatus(paramClass, false) == 10) {
      bool3 = bool2;
    } else {
      bool3 = false;
    } 
    return bool3;
  }
  
  public static boolean isStaticAndNoInited(Member paramMember) {
    boolean bool = paramMember instanceof java.lang.reflect.Method;
    boolean bool1 = false;
    if (!bool)
      return false; 
    Class<?> clazz = paramMember.getDeclaringClass();
    bool = bool1;
    if (Modifier.isStatic(paramMember.getModifiers())) {
      bool = bool1;
      if (!isInitialized(clazz))
        bool = true; 
    } 
    return bool;
  }
  
  public static long toUnsignedLong(int paramInt) {
    return paramInt & 0xFFFFFFFFL;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhoo\\utils\ClassStatusUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */