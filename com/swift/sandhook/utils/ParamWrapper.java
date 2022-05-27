package com.swift.sandhook.utils;

import com.swift.sandhook.SandHook;

public class ParamWrapper {
  private static boolean is64Bit = SandHook.is64Bit();
  
  public static Object addressToObject(Class paramClass, long paramLong) {
    return is64Bit ? addressToObject64(paramClass, paramLong) : addressToObject32(paramClass, (int)paramLong);
  }
  
  public static Object addressToObject32(Class<int> paramClass, int paramInt) {
    if (paramClass == null)
      return null; 
    if (paramClass.isPrimitive()) {
      if (paramClass == int.class)
        return Integer.valueOf(paramInt); 
      if (paramClass == short.class)
        return Short.valueOf((short)paramInt); 
      if (paramClass == byte.class)
        return Byte.valueOf((byte)paramInt); 
      if (paramClass == char.class)
        return Character.valueOf((char)paramInt); 
      if (paramClass == boolean.class) {
        boolean bool;
        if (paramInt != 0) {
          bool = true;
        } else {
          bool = false;
        } 
        return Boolean.valueOf(bool);
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("unknown type: ");
      stringBuilder.append(paramClass.toString());
      throw new RuntimeException(stringBuilder.toString());
    } 
    return SandHook.getObject(paramInt);
  }
  
  public static Object addressToObject64(Class<int> paramClass, long paramLong) {
    if (paramClass == null)
      return null; 
    if (paramClass.isPrimitive()) {
      if (paramClass == int.class)
        return Integer.valueOf((int)paramLong); 
      if (paramClass == long.class)
        return Long.valueOf(paramLong); 
      if (paramClass == short.class)
        return Short.valueOf((short)(int)paramLong); 
      if (paramClass == byte.class)
        return Byte.valueOf((byte)(int)paramLong); 
      if (paramClass == char.class)
        return Character.valueOf((char)(int)paramLong); 
      if (paramClass == boolean.class) {
        boolean bool;
        if (paramLong != 0L) {
          bool = true;
        } else {
          bool = false;
        } 
        return Boolean.valueOf(bool);
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("unknown type: ");
      stringBuilder.append(paramClass.toString());
      throw new RuntimeException(stringBuilder.toString());
    } 
    return SandHook.getObject(paramLong);
  }
  
  public static long objectToAddress(Class paramClass, Object paramObject) {
    return is64Bit ? objectToAddress64(paramClass, paramObject) : objectToAddress32(paramClass, paramObject);
  }
  
  public static int objectToAddress32(Class<int> paramClass, Object paramObject) {
    if (paramObject == null)
      return 0; 
    if (paramClass.isPrimitive()) {
      if (paramClass == int.class)
        return ((Integer)paramObject).intValue(); 
      if (paramClass == short.class)
        return ((Short)paramObject).shortValue(); 
      if (paramClass == byte.class)
        return ((Byte)paramObject).byteValue(); 
      if (paramClass == char.class)
        return ((Character)paramObject).charValue(); 
      if (paramClass == boolean.class)
        return Boolean.TRUE.equals(paramObject); 
      paramObject = new StringBuilder();
      paramObject.append("unknown type: ");
      paramObject.append(paramClass.toString());
      throw new RuntimeException(paramObject.toString());
    } 
    return (int)SandHook.getObjectAddress(paramObject);
  }
  
  public static long objectToAddress64(Class<int> paramClass, Object paramObject) {
    long l = 0L;
    if (paramObject == null)
      return 0L; 
    if (paramClass.isPrimitive()) {
      if (paramClass == int.class)
        return ((Integer)paramObject).intValue(); 
      if (paramClass == long.class)
        return ((Long)paramObject).longValue(); 
      if (paramClass == short.class)
        return ((Short)paramObject).shortValue(); 
      if (paramClass == byte.class)
        return ((Byte)paramObject).byteValue(); 
      if (paramClass == char.class)
        return ((Character)paramObject).charValue(); 
      if (paramClass == boolean.class) {
        if (Boolean.TRUE.equals(paramObject))
          l = 1L; 
        return l;
      } 
      paramObject = new StringBuilder();
      paramObject.append("unknown type: ");
      paramObject.append(paramClass.toString());
      throw new RuntimeException(paramObject.toString());
    } 
    return SandHook.getObjectAddress(paramObject);
  }
  
  public static boolean support(Class<float> paramClass) {
    boolean bool = is64Bit;
    boolean bool1 = true;
    boolean bool2 = true;
    if (bool) {
      if (paramClass == float.class || paramClass == double.class)
        bool2 = false; 
      return bool2;
    } 
    if (paramClass != float.class && paramClass != double.class && paramClass != long.class) {
      bool2 = bool1;
    } else {
      bool2 = false;
    } 
    return bool2;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhoo\\utils\ParamWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */