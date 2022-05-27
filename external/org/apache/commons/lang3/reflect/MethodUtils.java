package external.org.apache.commons.lang3.reflect;

import external.org.apache.commons.lang3.ArrayUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodUtils {
  public static Method getAccessibleMethod(Class<?> paramClass, String paramString, Class<?>... paramVarArgs) {
    try {
      return getAccessibleMethod(paramClass.getMethod(paramString, paramVarArgs));
    } catch (NoSuchMethodException noSuchMethodException) {
      return null;
    } 
  }
  
  public static Method getAccessibleMethod(Method paramMethod) {
    if (!MemberUtils.isAccessible(paramMethod))
      return null; 
    Class<?> clazz = paramMethod.getDeclaringClass();
    if (Modifier.isPublic(clazz.getModifiers()))
      return paramMethod; 
    String str = paramMethod.getName();
    Class[] arrayOfClass = paramMethod.getParameterTypes();
    Method method = getAccessibleMethodFromInterfaceNest(clazz, str, arrayOfClass);
    paramMethod = method;
    if (method == null)
      paramMethod = getAccessibleMethodFromSuperclass(clazz, str, arrayOfClass); 
    return paramMethod;
  }
  
  private static Method getAccessibleMethodFromInterfaceNest(Class<?> paramClass, String paramString, Class<?>... paramVarArgs) {
    Method method;
    Class<?> clazz1 = null;
    Class<?> clazz2 = paramClass;
    paramClass = clazz1;
    while (clazz2 != null) {
      Method method1;
      Class[] arrayOfClass = clazz2.getInterfaces();
      byte b = 0;
      while (true) {
        clazz1 = paramClass;
        if (b < arrayOfClass.length) {
          if (Modifier.isPublic(arrayOfClass[b].getModifiers())) {
            try {
              Method method3 = arrayOfClass[b].getDeclaredMethod(paramString, paramVarArgs);
              method2 = method3;
            } catch (NoSuchMethodException noSuchMethodException) {}
            if (method2 != null) {
              Method method3 = method2;
              break;
            } 
            method1 = getAccessibleMethodFromInterfaceNest(arrayOfClass[b], paramString, paramVarArgs);
            Method method2 = method1;
            if (method1 != null)
              break; 
          } 
          b++;
          continue;
        } 
        break;
      } 
      clazz2 = clazz2.getSuperclass();
      method = method1;
    } 
    return method;
  }
  
  private static Method getAccessibleMethodFromSuperclass(Class<?> paramClass, String paramString, Class<?>... paramVarArgs) {
    paramClass = paramClass.getSuperclass();
    while (paramClass != null) {
      if (Modifier.isPublic(paramClass.getModifiers()))
        try {
          return paramClass.getMethod(paramString, paramVarArgs);
        } catch (NoSuchMethodException noSuchMethodException) {
          return null;
        }  
      Class clazz = noSuchMethodException.getSuperclass();
    } 
    return null;
  }
  
  public static Method getMatchingAccessibleMethod(Class<?> paramClass, String paramString, Class<?>... paramVarArgs) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: invokevirtual getMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   6: astore_3
    //   7: aload_3
    //   8: invokestatic setAccessibleWorkaround : (Ljava/lang/reflect/AccessibleObject;)V
    //   11: aload_3
    //   12: areturn
    //   13: astore_3
    //   14: aconst_null
    //   15: astore_3
    //   16: aload_0
    //   17: invokevirtual getMethods : ()[Ljava/lang/reflect/Method;
    //   20: astore #4
    //   22: aload #4
    //   24: arraylength
    //   25: istore #5
    //   27: iconst_0
    //   28: istore #6
    //   30: aload_3
    //   31: astore_0
    //   32: iload #6
    //   34: iload #5
    //   36: if_icmpge -> 122
    //   39: aload #4
    //   41: iload #6
    //   43: aaload
    //   44: astore #7
    //   46: aload_0
    //   47: astore_3
    //   48: aload #7
    //   50: invokevirtual getName : ()Ljava/lang/String;
    //   53: aload_1
    //   54: invokevirtual equals : (Ljava/lang/Object;)Z
    //   57: ifeq -> 114
    //   60: aload_0
    //   61: astore_3
    //   62: aload_2
    //   63: aload #7
    //   65: invokevirtual getParameterTypes : ()[Ljava/lang/Class;
    //   68: iconst_1
    //   69: invokestatic isAssignable : ([Ljava/lang/Class;[Ljava/lang/Class;Z)Z
    //   72: ifeq -> 114
    //   75: aload #7
    //   77: invokestatic getAccessibleMethod : (Ljava/lang/reflect/Method;)Ljava/lang/reflect/Method;
    //   80: astore #7
    //   82: aload_0
    //   83: astore_3
    //   84: aload #7
    //   86: ifnull -> 114
    //   89: aload_0
    //   90: ifnull -> 111
    //   93: aload_0
    //   94: astore_3
    //   95: aload #7
    //   97: invokevirtual getParameterTypes : ()[Ljava/lang/Class;
    //   100: aload_0
    //   101: invokevirtual getParameterTypes : ()[Ljava/lang/Class;
    //   104: aload_2
    //   105: invokestatic compareParameterTypes : ([Ljava/lang/Class;[Ljava/lang/Class;[Ljava/lang/Class;)I
    //   108: ifge -> 114
    //   111: aload #7
    //   113: astore_3
    //   114: iinc #6, 1
    //   117: aload_3
    //   118: astore_0
    //   119: goto -> 32
    //   122: aload_0
    //   123: ifnull -> 130
    //   126: aload_0
    //   127: invokestatic setAccessibleWorkaround : (Ljava/lang/reflect/AccessibleObject;)V
    //   130: aload_0
    //   131: areturn
    // Exception table:
    //   from	to	target	type
    //   0	11	13	java/lang/NoSuchMethodException
  }
  
  public static Object invokeExactMethod(Object paramObject, String paramString, Object... paramVarArgs) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Object[] arrayOfObject = paramVarArgs;
    if (paramVarArgs == null)
      arrayOfObject = ArrayUtils.EMPTY_OBJECT_ARRAY; 
    int i = arrayOfObject.length;
    Class[] arrayOfClass = new Class[i];
    for (byte b = 0; b < i; b++)
      arrayOfClass[b] = arrayOfObject[b].getClass(); 
    return invokeExactMethod(paramObject, paramString, arrayOfObject, arrayOfClass);
  }
  
  public static Object invokeExactMethod(Object paramObject, String paramString, Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Object[] arrayOfObject = paramArrayOfObject;
    if (paramArrayOfObject == null)
      arrayOfObject = ArrayUtils.EMPTY_OBJECT_ARRAY; 
    Class<?>[] arrayOfClass = paramArrayOfClass;
    if (paramArrayOfClass == null)
      arrayOfClass = ArrayUtils.EMPTY_CLASS_ARRAY; 
    Method method = getAccessibleMethod(paramObject.getClass(), paramString, arrayOfClass);
    if (method != null)
      return method.invoke(paramObject, arrayOfObject); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No such accessible method: ");
    stringBuilder.append(paramString);
    stringBuilder.append("() on object: ");
    stringBuilder.append(paramObject.getClass().getName());
    throw new NoSuchMethodException(stringBuilder.toString());
  }
  
  public static Object invokeExactStaticMethod(Class<?> paramClass, String paramString, Object... paramVarArgs) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Object[] arrayOfObject = paramVarArgs;
    if (paramVarArgs == null)
      arrayOfObject = ArrayUtils.EMPTY_OBJECT_ARRAY; 
    int i = arrayOfObject.length;
    Class[] arrayOfClass = new Class[i];
    for (byte b = 0; b < i; b++)
      arrayOfClass[b] = arrayOfObject[b].getClass(); 
    return invokeExactStaticMethod(paramClass, paramString, arrayOfObject, arrayOfClass);
  }
  
  public static Object invokeExactStaticMethod(Class<?> paramClass, String paramString, Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Object[] arrayOfObject = paramArrayOfObject;
    if (paramArrayOfObject == null)
      arrayOfObject = ArrayUtils.EMPTY_OBJECT_ARRAY; 
    Class<?>[] arrayOfClass = paramArrayOfClass;
    if (paramArrayOfClass == null)
      arrayOfClass = ArrayUtils.EMPTY_CLASS_ARRAY; 
    Method method = getAccessibleMethod(paramClass, paramString, arrayOfClass);
    if (method != null)
      return method.invoke(null, arrayOfObject); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No such accessible method: ");
    stringBuilder.append(paramString);
    stringBuilder.append("() on class: ");
    stringBuilder.append(paramClass.getName());
    throw new NoSuchMethodException(stringBuilder.toString());
  }
  
  public static Object invokeMethod(Object paramObject, String paramString, Object... paramVarArgs) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Object[] arrayOfObject = paramVarArgs;
    if (paramVarArgs == null)
      arrayOfObject = ArrayUtils.EMPTY_OBJECT_ARRAY; 
    int i = arrayOfObject.length;
    Class[] arrayOfClass = new Class[i];
    for (byte b = 0; b < i; b++)
      arrayOfClass[b] = arrayOfObject[b].getClass(); 
    return invokeMethod(paramObject, paramString, arrayOfObject, arrayOfClass);
  }
  
  public static Object invokeMethod(Object paramObject, String paramString, Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Class<?>[] arrayOfClass = paramArrayOfClass;
    if (paramArrayOfClass == null)
      arrayOfClass = ArrayUtils.EMPTY_CLASS_ARRAY; 
    Object[] arrayOfObject = paramArrayOfObject;
    if (paramArrayOfObject == null)
      arrayOfObject = ArrayUtils.EMPTY_OBJECT_ARRAY; 
    Method method = getMatchingAccessibleMethod(paramObject.getClass(), paramString, arrayOfClass);
    if (method != null)
      return method.invoke(paramObject, arrayOfObject); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No such accessible method: ");
    stringBuilder.append(paramString);
    stringBuilder.append("() on object: ");
    stringBuilder.append(paramObject.getClass().getName());
    throw new NoSuchMethodException(stringBuilder.toString());
  }
  
  public static Object invokeStaticMethod(Class<?> paramClass, String paramString, Object... paramVarArgs) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Object[] arrayOfObject = paramVarArgs;
    if (paramVarArgs == null)
      arrayOfObject = ArrayUtils.EMPTY_OBJECT_ARRAY; 
    int i = arrayOfObject.length;
    Class[] arrayOfClass = new Class[i];
    for (byte b = 0; b < i; b++)
      arrayOfClass[b] = arrayOfObject[b].getClass(); 
    return invokeStaticMethod(paramClass, paramString, arrayOfObject, arrayOfClass);
  }
  
  public static Object invokeStaticMethod(Class<?> paramClass, String paramString, Object[] paramArrayOfObject, Class<?>[] paramArrayOfClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Class<?>[] arrayOfClass = paramArrayOfClass;
    if (paramArrayOfClass == null)
      arrayOfClass = ArrayUtils.EMPTY_CLASS_ARRAY; 
    Object[] arrayOfObject = paramArrayOfObject;
    if (paramArrayOfObject == null)
      arrayOfObject = ArrayUtils.EMPTY_OBJECT_ARRAY; 
    Method method = getMatchingAccessibleMethod(paramClass, paramString, arrayOfClass);
    if (method != null)
      return method.invoke(null, arrayOfObject); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No such accessible method: ");
    stringBuilder.append(paramString);
    stringBuilder.append("() on class: ");
    stringBuilder.append(paramClass.getName());
    throw new NoSuchMethodException(stringBuilder.toString());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\reflect\MethodUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */