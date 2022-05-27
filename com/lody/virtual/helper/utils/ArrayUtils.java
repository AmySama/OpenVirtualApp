package com.lody.virtual.helper.utils;

import com.lody.virtual.helper.compat.ObjectsCompat;
import java.util.Arrays;

public class ArrayUtils {
  public static void checkOffsetAndCount(int paramInt1, int paramInt2, int paramInt3) throws ArrayIndexOutOfBoundsException {
    if ((paramInt2 | paramInt3) >= 0 && paramInt2 <= paramInt1 && paramInt1 - paramInt2 >= paramInt3)
      return; 
    throw new ArrayIndexOutOfBoundsException(paramInt2);
  }
  
  public static boolean contains(int[] paramArrayOfint, int paramInt) {
    if (paramArrayOfint == null)
      return false; 
    int i = paramArrayOfint.length;
    for (byte b = 0; b < i; b++) {
      if (paramArrayOfint[b] == paramInt)
        return true; 
    } 
    return false;
  }
  
  public static <T> boolean contains(T[] paramArrayOfT, T paramT) {
    boolean bool;
    if (indexOf(paramArrayOfT, paramT) != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static <T> T getFirst(Object[] paramArrayOfObject, Class<?> paramClass) {
    int i = indexOfFirst(paramArrayOfObject, paramClass);
    return (T)((i != -1) ? paramArrayOfObject[i] : null);
  }
  
  public static int indexOf(Object[] paramArrayOfObject, Class<?> paramClass, int paramInt) {
    if (!isEmpty(paramArrayOfObject)) {
      int i = paramArrayOfObject.length;
      byte b = 0;
      byte b1 = -1;
      while (b < i) {
        Object object = paramArrayOfObject[b];
        b1++;
        int j = paramInt;
        if (object != null) {
          j = paramInt;
          if (object.getClass() == paramClass) {
            j = --paramInt;
            if (paramInt <= 0)
              return b1; 
          } 
        } 
        b++;
        paramInt = j;
      } 
    } 
    return -1;
  }
  
  public static <T> int indexOf(T[] paramArrayOfT, T paramT) {
    if (paramArrayOfT == null)
      return -1; 
    for (byte b = 0; b < paramArrayOfT.length; b++) {
      if (ObjectsCompat.equals(paramArrayOfT[b], paramT))
        return b; 
    } 
    return -1;
  }
  
  public static int indexOfFirst(Object[] paramArrayOfObject, Class<?> paramClass) {
    if (!isEmpty(paramArrayOfObject)) {
      int i = paramArrayOfObject.length;
      byte b = 0;
      byte b1 = -1;
      while (b < i) {
        Object object = paramArrayOfObject[b];
        b1++;
        if (object != null && paramClass == object.getClass())
          return b1; 
        b++;
      } 
    } 
    return -1;
  }
  
  public static int indexOfLast(Object[] paramArrayOfObject, Class<?> paramClass) {
    if (!isEmpty(paramArrayOfObject))
      for (int i = paramArrayOfObject.length; i > 0; i--) {
        int j = i - 1;
        Object object = paramArrayOfObject[j];
        if (object != null && object.getClass() == paramClass)
          return j; 
      }  
    return -1;
  }
  
  public static int indexOfObject(Object[] paramArrayOfObject, Class<?> paramClass, int paramInt) {
    if (paramArrayOfObject == null)
      return -1; 
    while (paramInt < paramArrayOfObject.length) {
      if (paramClass.isInstance(paramArrayOfObject[paramInt]))
        return paramInt; 
      paramInt++;
    } 
    return -1;
  }
  
  public static <T> boolean isEmpty(T[] paramArrayOfT) {
    return (paramArrayOfT == null || paramArrayOfT.length == 0);
  }
  
  public static int protoIndexOf(Class<?>[] paramArrayOfClass, Class<?> paramClass) {
    if (paramArrayOfClass == null)
      return -1; 
    for (byte b = 0; b < paramArrayOfClass.length; b++) {
      if (paramArrayOfClass[b] == paramClass)
        return b; 
    } 
    return -1;
  }
  
  public static int protoIndexOf(Class<?>[] paramArrayOfClass, Class<?> paramClass, int paramInt) {
    if (paramArrayOfClass == null)
      return -1; 
    while (paramInt < paramArrayOfClass.length) {
      if (paramClass == paramArrayOfClass[paramInt])
        return paramInt; 
      paramInt++;
    } 
    return -1;
  }
  
  public static Object[] push(Object[] paramArrayOfObject, Object paramObject) {
    Object[] arrayOfObject = new Object[paramArrayOfObject.length + 1];
    System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, paramArrayOfObject.length);
    arrayOfObject[paramArrayOfObject.length] = paramObject;
    return arrayOfObject;
  }
  
  public static <T> T[] trimToSize(T[] paramArrayOfT, int paramInt) {
    return (paramArrayOfT == null || paramInt == 0) ? null : ((paramArrayOfT.length == paramInt) ? paramArrayOfT : Arrays.copyOf(paramArrayOfT, paramInt));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\ArrayUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */