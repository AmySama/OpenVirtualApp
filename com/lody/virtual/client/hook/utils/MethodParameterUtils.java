package com.lody.virtual.client.hook.utils;

import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.utils.ArrayUtils;
import java.util.Arrays;
import java.util.HashSet;

public class MethodParameterUtils {
  public static Class<?>[] getAllInterface(Class paramClass) {
    HashSet<Class<?>> hashSet = new HashSet();
    getAllInterfaces(paramClass, hashSet);
    Class[] arrayOfClass = new Class[hashSet.size()];
    hashSet.toArray((Class<?>[][])arrayOfClass);
    return arrayOfClass;
  }
  
  public static void getAllInterfaces(Class<Object> paramClass, HashSet<Class<?>> paramHashSet) {
    Class[] arrayOfClass = paramClass.getInterfaces();
    if (arrayOfClass.length != 0)
      paramHashSet.addAll(Arrays.asList(arrayOfClass)); 
    if (paramClass.getSuperclass() != Object.class)
      getAllInterfaces(paramClass.getSuperclass(), paramHashSet); 
  }
  
  public static <T> T getFirstParam(Object[] paramArrayOfObject, Class<T> paramClass) {
    if (paramArrayOfObject == null)
      return null; 
    int i = ArrayUtils.indexOfFirst(paramArrayOfObject, paramClass);
    return (T)((i != -1) ? paramArrayOfObject[i] : null);
  }
  
  public static int getIndex(Object[] paramArrayOfObject, Class<?> paramClass) {
    for (byte b = 0; b < paramArrayOfObject.length; b++) {
      Object object = paramArrayOfObject[b];
      if (object != null && object.getClass() == paramClass)
        return b; 
      if (paramClass.isInstance(object))
        return b; 
    } 
    return -1;
  }
  
  public static int getParamsIndex(Class[] paramArrayOfClass, Class<?> paramClass) {
    for (byte b = 0; b < paramArrayOfClass.length; b++) {
      if (paramArrayOfClass[b].equals(paramClass))
        return b; 
    } 
    return -1;
  }
  
  public static String replaceFirstAppPkg(Object[] paramArrayOfObject) {
    if (paramArrayOfObject == null)
      return null; 
    for (byte b = 0; b < paramArrayOfObject.length; b++) {
      if (paramArrayOfObject[b] instanceof String) {
        String str = (String)paramArrayOfObject[b];
        if (VirtualCore.get().isAppInstalled(str)) {
          paramArrayOfObject[b] = VirtualCore.get().getHostPkg();
          return str;
        } 
      } 
    } 
    return null;
  }
  
  public static String replaceLastAppPkg(Object[] paramArrayOfObject) {
    int i = ArrayUtils.indexOfLast(paramArrayOfObject, String.class);
    if (i != -1) {
      String str = (String)paramArrayOfObject[i];
      paramArrayOfObject[i] = VirtualCore.get().getHostPkg();
      return str;
    } 
    return null;
  }
  
  public static String replaceSequenceAppPkg(Object[] paramArrayOfObject, int paramInt) {
    paramInt = ArrayUtils.indexOf(paramArrayOfObject, String.class, paramInt);
    if (paramInt != -1) {
      String str = (String)paramArrayOfObject[paramInt];
      paramArrayOfObject[paramInt] = VirtualCore.get().getHostPkg();
      return str;
    } 
    return null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hoo\\utils\MethodParameterUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */