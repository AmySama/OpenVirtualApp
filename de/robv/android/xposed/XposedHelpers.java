package de.robv.android.xposed;

import android.content.res.Resources;
import dalvik.system.DexFile;
import external.org.apache.commons.lang3.ClassUtils;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipFile;

public final class XposedHelpers {
  private static final WeakHashMap<Object, HashMap<String, Object>> additionalFields;
  
  private static final HashMap<String, Constructor<?>> constructorCache;
  
  private static final HashMap<String, Field> fieldCache = new HashMap<String, Field>();
  
  private static final HashMap<String, Method> methodCache = new HashMap<String, Method>();
  
  private static final HashMap<String, ThreadLocal<AtomicInteger>> sMethodDepth;
  
  static {
    constructorCache = new HashMap<String, Constructor<?>>();
    additionalFields = new WeakHashMap<Object, HashMap<String, Object>>();
    sMethodDepth = new HashMap<String, ThreadLocal<AtomicInteger>>();
  }
  
  public static byte[] assetAsByteArray(Resources paramResources, String paramString) throws IOException {
    return inputStreamToByteArray(paramResources.getAssets().open(paramString));
  }
  
  public static Object callMethod(Object paramObject, String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs) {
    try {
      return findMethodBestMatch(paramObject.getClass(), paramString, paramArrayOfClass, paramVarArgs).invoke(paramObject, paramVarArgs);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } catch (InvocationTargetException invocationTargetException) {
      throw new InvocationTargetError(invocationTargetException.getCause());
    } 
  }
  
  public static Object callMethod(Object paramObject, String paramString, Object... paramVarArgs) {
    try {
      return findMethodBestMatch(paramObject.getClass(), paramString, paramVarArgs).invoke(paramObject, paramVarArgs);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } catch (InvocationTargetException invocationTargetException) {
      throw new InvocationTargetError(invocationTargetException.getCause());
    } 
  }
  
  public static Object callStaticMethod(Class<?> paramClass, String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs) {
    try {
      return findMethodBestMatch(paramClass, paramString, paramArrayOfClass, paramVarArgs).invoke(null, paramVarArgs);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } catch (InvocationTargetException invocationTargetException) {
      throw new InvocationTargetError(invocationTargetException.getCause());
    } 
  }
  
  public static Object callStaticMethod(Class<?> paramClass, String paramString, Object... paramVarArgs) {
    try {
      return findMethodBestMatch(paramClass, paramString, paramVarArgs).invoke(null, paramVarArgs);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } catch (InvocationTargetException invocationTargetException) {
      throw new InvocationTargetError(invocationTargetException.getCause());
    } 
  }
  
  static void closeSilently(DexFile paramDexFile) {
    if (paramDexFile != null)
      try {
        paramDexFile.close();
      } catch (IOException iOException) {} 
  }
  
  static void closeSilently(Closeable paramCloseable) {
    if (paramCloseable != null)
      try {
        paramCloseable.close();
      } catch (IOException iOException) {} 
  }
  
  static void closeSilently(ZipFile paramZipFile) {
    if (paramZipFile != null)
      try {
        paramZipFile.close();
      } catch (IOException iOException) {} 
  }
  
  public static int decrementMethodDepth(String paramString) {
    return ((AtomicInteger)getMethodDepthCounter(paramString).get()).decrementAndGet();
  }
  
  static boolean fileContains(File paramFile, String paramString) throws IOException {
    String str = null;
    try {
      BufferedReader bufferedReader = new BufferedReader();
      FileReader fileReader = new FileReader();
      this(paramFile);
      this(fileReader);
    } finally {
      paramFile = null;
    } 
    closeSilently((Closeable)paramString);
    throw paramFile;
  }
  
  public static XC_MethodHook.Unhook findAndHookConstructor(Class<?> paramClass, Object... paramVarArgs) {
    if (paramVarArgs.length != 0 && paramVarArgs[paramVarArgs.length - 1] instanceof XC_MethodHook) {
      XC_MethodHook xC_MethodHook = (XC_MethodHook)paramVarArgs[paramVarArgs.length - 1];
      return XposedBridge.hookMethod(findConstructorExact(paramClass, getParameterClasses(paramClass.getClassLoader(), paramVarArgs)), xC_MethodHook);
    } 
    throw new IllegalArgumentException("no callback defined");
  }
  
  public static XC_MethodHook.Unhook findAndHookConstructor(String paramString, ClassLoader paramClassLoader, Object... paramVarArgs) {
    return findAndHookConstructor(findClass(paramString, paramClassLoader), paramVarArgs);
  }
  
  public static XC_MethodHook.Unhook findAndHookMethod(Class<?> paramClass, String paramString, Object... paramVarArgs) {
    if (paramVarArgs.length != 0 && paramVarArgs[paramVarArgs.length - 1] instanceof XC_MethodHook) {
      XC_MethodHook xC_MethodHook = (XC_MethodHook)paramVarArgs[paramVarArgs.length - 1];
      return XposedBridge.hookMethod(findMethodExact(paramClass, paramString, getParameterClasses(paramClass.getClassLoader(), paramVarArgs)), xC_MethodHook);
    } 
    throw new IllegalArgumentException("no callback defined");
  }
  
  public static XC_MethodHook.Unhook findAndHookMethod(String paramString1, ClassLoader paramClassLoader, String paramString2, Object... paramVarArgs) {
    return findAndHookMethod(findClass(paramString1, paramClassLoader), paramString2, paramVarArgs);
  }
  
  public static Class<?> findClass(String paramString, ClassLoader paramClassLoader) {
    ClassLoader classLoader = paramClassLoader;
    if (paramClassLoader == null)
      classLoader = XposedBridge.BOOTCLASSLOADER; 
    try {
      return ClassUtils.getClass(classLoader, paramString, false);
    } catch (ClassNotFoundException classNotFoundException) {
      throw new ClassNotFoundError(classNotFoundException);
    } 
  }
  
  public static Class<?> findClassIfExists(String paramString, ClassLoader paramClassLoader) {
    try {
      return findClass(paramString, paramClassLoader);
    } catch (ClassNotFoundError classNotFoundError) {
      return null;
    } 
  }
  
  public static Constructor<?> findConstructorBestMatch(Class<?> paramClass, Class<?>... paramVarArgs) {
    // Byte code:
    //   0: new java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore_2
    //   8: aload_2
    //   9: aload_0
    //   10: invokevirtual getName : ()Ljava/lang/String;
    //   13: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: pop
    //   17: aload_2
    //   18: aload_1
    //   19: invokestatic getParametersString : ([Ljava/lang/Class;)Ljava/lang/String;
    //   22: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   25: pop
    //   26: aload_2
    //   27: ldc '#bestmatch'
    //   29: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   32: pop
    //   33: aload_2
    //   34: invokevirtual toString : ()Ljava/lang/String;
    //   37: astore_3
    //   38: getstatic de/robv/android/xposed/XposedHelpers.constructorCache : Ljava/util/HashMap;
    //   41: aload_3
    //   42: invokevirtual containsKey : (Ljava/lang/Object;)Z
    //   45: ifeq -> 74
    //   48: getstatic de/robv/android/xposed/XposedHelpers.constructorCache : Ljava/util/HashMap;
    //   51: aload_3
    //   52: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   55: checkcast java/lang/reflect/Constructor
    //   58: astore_0
    //   59: aload_0
    //   60: ifnull -> 65
    //   63: aload_0
    //   64: areturn
    //   65: new java/lang/NoSuchMethodError
    //   68: dup
    //   69: aload_3
    //   70: invokespecial <init> : (Ljava/lang/String;)V
    //   73: athrow
    //   74: aload_0
    //   75: aload_1
    //   76: invokestatic findConstructorExact : (Ljava/lang/Class;[Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   79: astore_2
    //   80: getstatic de/robv/android/xposed/XposedHelpers.constructorCache : Ljava/util/HashMap;
    //   83: aload_3
    //   84: aload_2
    //   85: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   88: pop
    //   89: aload_2
    //   90: areturn
    //   91: astore_2
    //   92: aload_0
    //   93: invokevirtual getDeclaredConstructors : ()[Ljava/lang/reflect/Constructor;
    //   96: astore #4
    //   98: aload #4
    //   100: arraylength
    //   101: istore #5
    //   103: iconst_0
    //   104: istore #6
    //   106: aconst_null
    //   107: astore_0
    //   108: iload #6
    //   110: iload #5
    //   112: if_icmpge -> 170
    //   115: aload #4
    //   117: iload #6
    //   119: aaload
    //   120: astore #7
    //   122: aload_0
    //   123: astore_2
    //   124: aload_1
    //   125: aload #7
    //   127: invokevirtual getParameterTypes : ()[Ljava/lang/Class;
    //   130: iconst_1
    //   131: invokestatic isAssignable : ([Ljava/lang/Class;[Ljava/lang/Class;Z)Z
    //   134: ifeq -> 162
    //   137: aload_0
    //   138: ifnull -> 159
    //   141: aload_0
    //   142: astore_2
    //   143: aload #7
    //   145: invokevirtual getParameterTypes : ()[Ljava/lang/Class;
    //   148: aload_0
    //   149: invokevirtual getParameterTypes : ()[Ljava/lang/Class;
    //   152: aload_1
    //   153: invokestatic compareParameterTypes : ([Ljava/lang/Class;[Ljava/lang/Class;[Ljava/lang/Class;)I
    //   156: ifge -> 162
    //   159: aload #7
    //   161: astore_2
    //   162: iinc #6, 1
    //   165: aload_2
    //   166: astore_0
    //   167: goto -> 108
    //   170: aload_0
    //   171: ifnull -> 190
    //   174: aload_0
    //   175: iconst_1
    //   176: invokevirtual setAccessible : (Z)V
    //   179: getstatic de/robv/android/xposed/XposedHelpers.constructorCache : Ljava/util/HashMap;
    //   182: aload_3
    //   183: aload_0
    //   184: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   187: pop
    //   188: aload_0
    //   189: areturn
    //   190: new java/lang/NoSuchMethodError
    //   193: dup
    //   194: aload_3
    //   195: invokespecial <init> : (Ljava/lang/String;)V
    //   198: astore_0
    //   199: getstatic de/robv/android/xposed/XposedHelpers.constructorCache : Ljava/util/HashMap;
    //   202: aload_3
    //   203: aconst_null
    //   204: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   207: pop
    //   208: aload_0
    //   209: athrow
    // Exception table:
    //   from	to	target	type
    //   74	89	91	java/lang/NoSuchMethodError
  }
  
  public static Constructor<?> findConstructorBestMatch(Class<?> paramClass, Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject) {
    Class[] arrayOfClass = null;
    for (byte b = 0; b < paramArrayOfClass.length; b++) {
      if (paramArrayOfClass[b] == null) {
        Class[] arrayOfClass1 = arrayOfClass;
        if (arrayOfClass == null)
          arrayOfClass1 = getParameterTypes(paramArrayOfObject); 
        paramArrayOfClass[b] = arrayOfClass1[b];
        arrayOfClass = arrayOfClass1;
      } 
    } 
    return findConstructorBestMatch(paramClass, paramArrayOfClass);
  }
  
  public static Constructor<?> findConstructorBestMatch(Class<?> paramClass, Object... paramVarArgs) {
    return findConstructorBestMatch(paramClass, getParameterTypes(paramVarArgs));
  }
  
  public static Constructor<?> findConstructorExact(Class<?> paramClass, Class<?>... paramVarArgs) {
    Constructor<?> constructor;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramClass.getName());
    stringBuilder.append(getParametersString(paramVarArgs));
    stringBuilder.append("#exact");
    String str = stringBuilder.toString();
    if (constructorCache.containsKey(str)) {
      constructor = constructorCache.get(str);
      if (constructor != null)
        return constructor; 
      throw new NoSuchMethodError(str);
    } 
    try {
      constructor = constructor.getDeclaredConstructor(paramVarArgs);
      constructor.setAccessible(true);
      constructorCache.put(str, constructor);
      return constructor;
    } catch (NoSuchMethodException noSuchMethodException) {
      constructorCache.put(str, null);
      throw new NoSuchMethodError(str);
    } 
  }
  
  public static Constructor<?> findConstructorExact(Class<?> paramClass, Object... paramVarArgs) {
    return findConstructorExact(paramClass, getParameterClasses(paramClass.getClassLoader(), paramVarArgs));
  }
  
  public static Constructor<?> findConstructorExact(String paramString, ClassLoader paramClassLoader, Object... paramVarArgs) {
    return findConstructorExact(findClass(paramString, paramClassLoader), getParameterClasses(paramClassLoader, paramVarArgs));
  }
  
  public static Constructor<?> findConstructorExactIfExists(Class<?> paramClass, Object... paramVarArgs) {
    try {
      return findConstructorExact(paramClass, paramVarArgs);
    } catch (ClassNotFoundError|NoSuchMethodError classNotFoundError) {
      return null;
    } 
  }
  
  public static Constructor<?> findConstructorExactIfExists(String paramString, ClassLoader paramClassLoader, Object... paramVarArgs) {
    try {
      return findConstructorExact(paramString, paramClassLoader, paramVarArgs);
    } catch (ClassNotFoundError|NoSuchMethodError classNotFoundError) {
      return null;
    } 
  }
  
  public static Field findField(Class<?> paramClass, String paramString) {
    Field field;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramClass.getName());
    stringBuilder.append('#');
    stringBuilder.append(paramString);
    String str = stringBuilder.toString();
    if (fieldCache.containsKey(str)) {
      field = fieldCache.get(str);
      if (field != null)
        return field; 
      throw new NoSuchFieldError(str);
    } 
    try {
      field = findFieldRecursiveImpl((Class<?>)field, paramString);
      field.setAccessible(true);
      fieldCache.put(str, field);
      return field;
    } catch (NoSuchFieldException noSuchFieldException) {
      fieldCache.put(str, null);
      throw new NoSuchFieldError(str);
    } 
  }
  
  public static Field findFieldIfExists(Class<?> paramClass, String paramString) {
    try {
      return findField(paramClass, paramString);
    } catch (NoSuchFieldError noSuchFieldError) {
      return null;
    } 
  }
  
  private static Field findFieldRecursiveImpl(Class<?> paramClass, String paramString) throws NoSuchFieldException {
    try {
      return paramClass.getDeclaredField(paramString);
    } catch (NoSuchFieldException noSuchFieldException) {
      while (true) {
        paramClass = paramClass.getSuperclass();
        if (paramClass != null && !paramClass.equals(Object.class))
          try {
            return paramClass.getDeclaredField(paramString);
          } catch (NoSuchFieldException noSuchFieldException1) {
            continue;
          }  
        break;
      } 
      throw noSuchFieldException;
    } 
  }
  
  public static Field findFirstFieldByExactType(Class<?> paramClass1, Class<?> paramClass2) {
    Class<?> clazz = paramClass1;
    while (true) {
      for (Field field : clazz.getDeclaredFields()) {
        if (field.getType() == paramClass2) {
          field.setAccessible(true);
          return field;
        } 
      } 
      clazz = clazz.getSuperclass();
      if (clazz != null)
        continue; 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Field of type ");
      stringBuilder.append(paramClass2.getName());
      stringBuilder.append(" in class ");
      stringBuilder.append(paramClass1.getName());
      throw new NoSuchFieldError(stringBuilder.toString());
    } 
  }
  
  public static Method findMethodBestMatch(Class<?> paramClass, String paramString, Class<?>... paramVarArgs) {
    // Byte code:
    //   0: new java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore_3
    //   8: aload_3
    //   9: aload_0
    //   10: invokevirtual getName : ()Ljava/lang/String;
    //   13: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   16: pop
    //   17: aload_3
    //   18: bipush #35
    //   20: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   23: pop
    //   24: aload_3
    //   25: aload_1
    //   26: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   29: pop
    //   30: aload_3
    //   31: aload_2
    //   32: invokestatic getParametersString : ([Ljava/lang/Class;)Ljava/lang/String;
    //   35: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   38: pop
    //   39: aload_3
    //   40: ldc '#bestmatch'
    //   42: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload_3
    //   47: invokevirtual toString : ()Ljava/lang/String;
    //   50: astore #4
    //   52: getstatic de/robv/android/xposed/XposedHelpers.methodCache : Ljava/util/HashMap;
    //   55: aload #4
    //   57: invokevirtual containsKey : (Ljava/lang/Object;)Z
    //   60: ifeq -> 91
    //   63: getstatic de/robv/android/xposed/XposedHelpers.methodCache : Ljava/util/HashMap;
    //   66: aload #4
    //   68: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   71: checkcast java/lang/reflect/Method
    //   74: astore_0
    //   75: aload_0
    //   76: ifnull -> 81
    //   79: aload_0
    //   80: areturn
    //   81: new java/lang/NoSuchMethodError
    //   84: dup
    //   85: aload #4
    //   87: invokespecial <init> : (Ljava/lang/String;)V
    //   90: athrow
    //   91: aload_0
    //   92: aload_1
    //   93: aload_2
    //   94: invokestatic findMethodExact : (Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   97: astore_3
    //   98: getstatic de/robv/android/xposed/XposedHelpers.methodCache : Ljava/util/HashMap;
    //   101: aload #4
    //   103: aload_3
    //   104: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   107: pop
    //   108: aload_3
    //   109: areturn
    //   110: astore_3
    //   111: aconst_null
    //   112: astore_3
    //   113: iconst_1
    //   114: istore #5
    //   116: aload_0
    //   117: invokevirtual getDeclaredMethods : ()[Ljava/lang/reflect/Method;
    //   120: astore #6
    //   122: aload #6
    //   124: arraylength
    //   125: istore #7
    //   127: iconst_0
    //   128: istore #8
    //   130: iload #8
    //   132: iload #7
    //   134: if_icmpge -> 233
    //   137: aload #6
    //   139: iload #8
    //   141: aaload
    //   142: astore #9
    //   144: iload #5
    //   146: ifne -> 166
    //   149: aload #9
    //   151: invokevirtual getModifiers : ()I
    //   154: invokestatic isPrivate : (I)Z
    //   157: ifeq -> 166
    //   160: aload_3
    //   161: astore #10
    //   163: goto -> 224
    //   166: aload_3
    //   167: astore #10
    //   169: aload #9
    //   171: invokevirtual getName : ()Ljava/lang/String;
    //   174: aload_1
    //   175: invokevirtual equals : (Ljava/lang/Object;)Z
    //   178: ifeq -> 224
    //   181: aload_3
    //   182: astore #10
    //   184: aload_2
    //   185: aload #9
    //   187: invokevirtual getParameterTypes : ()[Ljava/lang/Class;
    //   190: iconst_1
    //   191: invokestatic isAssignable : ([Ljava/lang/Class;[Ljava/lang/Class;Z)Z
    //   194: ifeq -> 224
    //   197: aload_3
    //   198: ifnull -> 220
    //   201: aload_3
    //   202: astore #10
    //   204: aload #9
    //   206: invokevirtual getParameterTypes : ()[Ljava/lang/Class;
    //   209: aload_3
    //   210: invokevirtual getParameterTypes : ()[Ljava/lang/Class;
    //   213: aload_2
    //   214: invokestatic compareParameterTypes : ([Ljava/lang/Class;[Ljava/lang/Class;[Ljava/lang/Class;)I
    //   217: ifge -> 224
    //   220: aload #9
    //   222: astore #10
    //   224: iinc #8, 1
    //   227: aload #10
    //   229: astore_3
    //   230: goto -> 130
    //   233: aload_0
    //   234: invokevirtual getSuperclass : ()Ljava/lang/Class;
    //   237: astore_0
    //   238: aload_0
    //   239: ifnonnull -> 285
    //   242: aload_3
    //   243: ifnull -> 263
    //   246: aload_3
    //   247: iconst_1
    //   248: invokevirtual setAccessible : (Z)V
    //   251: getstatic de/robv/android/xposed/XposedHelpers.methodCache : Ljava/util/HashMap;
    //   254: aload #4
    //   256: aload_3
    //   257: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   260: pop
    //   261: aload_3
    //   262: areturn
    //   263: new java/lang/NoSuchMethodError
    //   266: dup
    //   267: aload #4
    //   269: invokespecial <init> : (Ljava/lang/String;)V
    //   272: astore_0
    //   273: getstatic de/robv/android/xposed/XposedHelpers.methodCache : Ljava/util/HashMap;
    //   276: aload #4
    //   278: aconst_null
    //   279: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   282: pop
    //   283: aload_0
    //   284: athrow
    //   285: iconst_0
    //   286: istore #5
    //   288: goto -> 116
    // Exception table:
    //   from	to	target	type
    //   91	108	110	java/lang/NoSuchMethodError
  }
  
  public static Method findMethodBestMatch(Class<?> paramClass, String paramString, Class<?>[] paramArrayOfClass, Object[] paramArrayOfObject) {
    Class[] arrayOfClass = null;
    byte b = 0;
    while (b < paramArrayOfClass.length) {
      Class[] arrayOfClass1;
      if (paramArrayOfClass[b] != null) {
        arrayOfClass1 = arrayOfClass;
      } else {
        arrayOfClass1 = arrayOfClass;
        if (arrayOfClass == null)
          arrayOfClass1 = getParameterTypes(paramArrayOfObject); 
        paramArrayOfClass[b] = arrayOfClass1[b];
      } 
      b++;
      arrayOfClass = arrayOfClass1;
    } 
    return findMethodBestMatch(paramClass, paramString, paramArrayOfClass);
  }
  
  public static Method findMethodBestMatch(Class<?> paramClass, String paramString, Object... paramVarArgs) {
    return findMethodBestMatch(paramClass, paramString, getParameterTypes(paramVarArgs));
  }
  
  public static Method findMethodExact(Class<?> paramClass, String paramString, Class<?>... paramVarArgs) {
    Method method;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramClass.getName());
    stringBuilder.append('#');
    stringBuilder.append(paramString);
    stringBuilder.append(getParametersString(paramVarArgs));
    stringBuilder.append("#exact");
    String str = stringBuilder.toString();
    if (methodCache.containsKey(str)) {
      method = methodCache.get(str);
      if (method != null)
        return method; 
      throw new NoSuchMethodError(str);
    } 
    try {
      method = method.getDeclaredMethod(paramString, paramVarArgs);
      method.setAccessible(true);
      methodCache.put(str, method);
      return method;
    } catch (NoSuchMethodException noSuchMethodException) {
      methodCache.put(str, null);
      throw new NoSuchMethodError(str);
    } 
  }
  
  public static Method findMethodExact(Class<?> paramClass, String paramString, Object... paramVarArgs) {
    return findMethodExact(paramClass, paramString, getParameterClasses(paramClass.getClassLoader(), paramVarArgs));
  }
  
  public static Method findMethodExact(String paramString1, ClassLoader paramClassLoader, String paramString2, Object... paramVarArgs) {
    return findMethodExact(findClass(paramString1, paramClassLoader), paramString2, getParameterClasses(paramClassLoader, paramVarArgs));
  }
  
  public static Method findMethodExactIfExists(Class<?> paramClass, String paramString, Object... paramVarArgs) {
    try {
      return findMethodExact(paramClass, paramString, paramVarArgs);
    } catch (ClassNotFoundError|NoSuchMethodError classNotFoundError) {
      return null;
    } 
  }
  
  public static Method findMethodExactIfExists(String paramString1, ClassLoader paramClassLoader, String paramString2, Object... paramVarArgs) {
    try {
      return findMethodExact(paramString1, paramClassLoader, paramString2, paramVarArgs);
    } catch (ClassNotFoundError|NoSuchMethodError classNotFoundError) {
      return null;
    } 
  }
  
  public static Method[] findMethodsByExactParameters(Class<?> paramClass1, Class<?> paramClass2, Class<?>... paramVarArgs) {
    LinkedList<Method> linkedList = new LinkedList();
    for (Method method : paramClass1.getDeclaredMethods()) {
      if (paramClass2 == null || paramClass2 == method.getReturnType()) {
        Class[] arrayOfClass = method.getParameterTypes();
        if (paramVarArgs.length == arrayOfClass.length) {
          byte b = 0;
          while (true) {
            if (b < paramVarArgs.length) {
              if (paramVarArgs[b] != arrayOfClass[b]) {
                b = 0;
                break;
              } 
              b++;
              continue;
            } 
            b = 1;
            break;
          } 
          if (b != 0) {
            method.setAccessible(true);
            linkedList.add(method);
          } 
        } 
      } 
    } 
    return linkedList.<Method>toArray(new Method[linkedList.size()]);
  }
  
  public static Object getAdditionalInstanceField(Object paramObject, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: ifnull -> 68
    //   4: aload_1
    //   5: ifnull -> 57
    //   8: getstatic de/robv/android/xposed/XposedHelpers.additionalFields : Ljava/util/WeakHashMap;
    //   11: astore_2
    //   12: aload_2
    //   13: monitorenter
    //   14: getstatic de/robv/android/xposed/XposedHelpers.additionalFields : Ljava/util/WeakHashMap;
    //   17: aload_0
    //   18: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   21: checkcast java/util/HashMap
    //   24: astore_0
    //   25: aload_0
    //   26: ifnonnull -> 33
    //   29: aload_2
    //   30: monitorexit
    //   31: aconst_null
    //   32: areturn
    //   33: aload_2
    //   34: monitorexit
    //   35: aload_0
    //   36: monitorenter
    //   37: aload_0
    //   38: aload_1
    //   39: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   42: astore_1
    //   43: aload_0
    //   44: monitorexit
    //   45: aload_1
    //   46: areturn
    //   47: astore_1
    //   48: aload_0
    //   49: monitorexit
    //   50: aload_1
    //   51: athrow
    //   52: astore_0
    //   53: aload_2
    //   54: monitorexit
    //   55: aload_0
    //   56: athrow
    //   57: new java/lang/NullPointerException
    //   60: dup
    //   61: ldc_w 'key must not be null'
    //   64: invokespecial <init> : (Ljava/lang/String;)V
    //   67: athrow
    //   68: new java/lang/NullPointerException
    //   71: dup
    //   72: ldc_w 'object must not be null'
    //   75: invokespecial <init> : (Ljava/lang/String;)V
    //   78: athrow
    // Exception table:
    //   from	to	target	type
    //   14	25	52	finally
    //   29	31	52	finally
    //   33	35	52	finally
    //   37	45	47	finally
    //   48	50	47	finally
    //   53	55	52	finally
  }
  
  public static Object getAdditionalStaticField(Class<?> paramClass, String paramString) {
    return getAdditionalInstanceField(paramClass, paramString);
  }
  
  public static Object getAdditionalStaticField(Object paramObject, String paramString) {
    return getAdditionalInstanceField(paramObject.getClass(), paramString);
  }
  
  public static boolean getBooleanField(Object paramObject, String paramString) {
    try {
      return findField(paramObject.getClass(), paramString).getBoolean(paramObject);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static byte getByteField(Object paramObject, String paramString) {
    try {
      return findField(paramObject.getClass(), paramString).getByte(paramObject);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static char getCharField(Object paramObject, String paramString) {
    try {
      return findField(paramObject.getClass(), paramString).getChar(paramObject);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static Class<?>[] getClassesAsArray(Class<?>... paramVarArgs) {
    return paramVarArgs;
  }
  
  public static double getDoubleField(Object paramObject, String paramString) {
    try {
      return findField(paramObject.getClass(), paramString).getDouble(paramObject);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static int getFirstParameterIndexByType(Member paramMember, Class<?> paramClass) {
    Class[] arrayOfClass;
    if (paramMember instanceof Method) {
      arrayOfClass = ((Method)paramMember).getParameterTypes();
    } else {
      arrayOfClass = ((Constructor)paramMember).getParameterTypes();
    } 
    for (byte b = 0; b < arrayOfClass.length; b++) {
      if (arrayOfClass[b] == paramClass)
        return b; 
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No parameter of type ");
    stringBuilder.append(paramClass);
    stringBuilder.append(" found in ");
    stringBuilder.append(paramMember);
    throw new NoSuchFieldError(stringBuilder.toString());
  }
  
  public static float getFloatField(Object paramObject, String paramString) {
    try {
      return findField(paramObject.getClass(), paramString).getFloat(paramObject);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static int getIntField(Object paramObject, String paramString) {
    try {
      return findField(paramObject.getClass(), paramString).getInt(paramObject);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static long getLongField(Object paramObject, String paramString) {
    try {
      return findField(paramObject.getClass(), paramString).getLong(paramObject);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static String getMD5Sum(String paramString) throws IOException {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      FileInputStream fileInputStream = new FileInputStream();
      this(paramString);
      byte[] arrayOfByte = new byte[8192];
      while (true) {
        int i = fileInputStream.read(arrayOfByte);
        if (i > 0) {
          messageDigest.update(arrayOfByte, 0, i);
          continue;
        } 
        fileInputStream.close();
        byte[] arrayOfByte1 = messageDigest.digest();
        BigInteger bigInteger = new BigInteger();
        this(1, arrayOfByte1);
        return bigInteger.toString(16);
      } 
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      return "";
    } 
  }
  
  public static int getMethodDepth(String paramString) {
    return ((AtomicInteger)getMethodDepthCounter(paramString).get()).get();
  }
  
  private static ThreadLocal<AtomicInteger> getMethodDepthCounter(String paramString) {
    synchronized (sMethodDepth) {
      ThreadLocal<AtomicInteger> threadLocal1 = sMethodDepth.get(paramString);
      ThreadLocal<AtomicInteger> threadLocal2 = threadLocal1;
      if (threadLocal1 == null) {
        threadLocal2 = new ThreadLocal<AtomicInteger>() {
            protected AtomicInteger initialValue() {
              return new AtomicInteger();
            }
          };
        super();
        sMethodDepth.put(paramString, threadLocal2);
      } 
      return threadLocal2;
    } 
  }
  
  public static Object getObjectField(Object paramObject, String paramString) {
    try {
      return findField(paramObject.getClass(), paramString).get(paramObject);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  static Method getOverriddenMethod(Method paramMethod) {
    int i = paramMethod.getModifiers();
    if (!Modifier.isStatic(i) && !Modifier.isPrivate(i)) {
      String str = paramMethod.getName();
      Class[] arrayOfClass = paramMethod.getParameterTypes();
      Class<?> clazz = paramMethod.getDeclaringClass().getSuperclass();
      while (clazz != null) {
        try {
          Method method = clazz.getDeclaredMethod(str, arrayOfClass);
          i = method.getModifiers();
          if (!Modifier.isPrivate(i)) {
            boolean bool = Modifier.isAbstract(i);
            if (!bool)
              return method; 
          } 
          return null;
        } catch (NoSuchMethodException noSuchMethodException) {
          clazz = clazz.getSuperclass();
        } 
      } 
    } 
    return null;
  }
  
  static Set<Method> getOverriddenMethods(Class<?> paramClass) {
    HashSet<Method> hashSet = new HashSet();
    Method[] arrayOfMethod = paramClass.getDeclaredMethods();
    int i = arrayOfMethod.length;
    for (byte b = 0; b < i; b++) {
      Method method = getOverriddenMethod(arrayOfMethod[b]);
      if (method != null)
        hashSet.add(method); 
    } 
    return hashSet;
  }
  
  private static Class<?>[] getParameterClasses(ClassLoader paramClassLoader, Object[] paramArrayOfObject) {
    int i = paramArrayOfObject.length - 1;
    Class[] arrayOfClass2 = null;
    while (i >= 0) {
      Object object = paramArrayOfObject[i];
      if (object != null) {
        if (!(object instanceof XC_MethodHook)) {
          Class[] arrayOfClass = arrayOfClass2;
          if (arrayOfClass2 == null)
            arrayOfClass = new Class[i + 1]; 
          if (object instanceof Class) {
            arrayOfClass[i] = (Class)object;
            arrayOfClass2 = arrayOfClass;
          } else if (object instanceof String) {
            arrayOfClass[i] = findClass((String)object, paramClassLoader);
            arrayOfClass2 = arrayOfClass;
          } else {
            throw new ClassNotFoundError("parameter type must either be specified as Class or String", null);
          } 
        } 
        i--;
        continue;
      } 
      throw new ClassNotFoundError("parameter type must not be null", null);
    } 
    Class[] arrayOfClass1 = arrayOfClass2;
    if (arrayOfClass2 == null)
      arrayOfClass1 = new Class[0]; 
    return arrayOfClass1;
  }
  
  public static int getParameterIndexByType(Member paramMember, Class<?> paramClass) {
    Class[] arrayOfClass;
    if (paramMember instanceof Method) {
      arrayOfClass = ((Method)paramMember).getParameterTypes();
    } else {
      arrayOfClass = ((Constructor)paramMember).getParameterTypes();
    } 
    byte b = 0;
    byte b1;
    for (b1 = -1; b < arrayOfClass.length; b1 = b2) {
      byte b2 = b1;
      if (arrayOfClass[b] == paramClass)
        if (b1 == -1) {
          b2 = b;
        } else {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("More than one parameter of type ");
          stringBuilder1.append(paramClass);
          stringBuilder1.append(" found in ");
          stringBuilder1.append(paramMember);
          throw new NoSuchFieldError(stringBuilder1.toString());
        }  
      b++;
    } 
    if (b1 != -1)
      return b1; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("No parameter of type ");
    stringBuilder.append(paramClass);
    stringBuilder.append(" found in ");
    stringBuilder.append(paramMember);
    throw new NoSuchFieldError(stringBuilder.toString());
  }
  
  public static Class<?>[] getParameterTypes(Object... paramVarArgs) {
    Class[] arrayOfClass = new Class[paramVarArgs.length];
    for (byte b = 0; b < paramVarArgs.length; b++) {
      Class clazz;
      if (paramVarArgs[b] != null) {
        clazz = paramVarArgs[b].getClass();
      } else {
        clazz = null;
      } 
      arrayOfClass[b] = clazz;
    } 
    return arrayOfClass;
  }
  
  private static String getParametersString(Class<?>... paramVarArgs) {
    StringBuilder stringBuilder = new StringBuilder("(");
    int i = paramVarArgs.length;
    boolean bool = true;
    for (byte b = 0; b < i; b++) {
      Class<?> clazz = paramVarArgs[b];
      if (bool) {
        bool = false;
      } else {
        stringBuilder.append(",");
      } 
      if (clazz != null) {
        stringBuilder.append(clazz.getCanonicalName());
      } else {
        stringBuilder.append("null");
      } 
    } 
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  public static short getShortField(Object paramObject, String paramString) {
    try {
      return findField(paramObject.getClass(), paramString).getShort(paramObject);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static boolean getStaticBooleanField(Class<?> paramClass, String paramString) {
    try {
      return findField(paramClass, paramString).getBoolean(null);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static byte getStaticByteField(Class<?> paramClass, String paramString) {
    try {
      return findField(paramClass, paramString).getByte(null);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static char getStaticCharField(Class<?> paramClass, String paramString) {
    try {
      return findField(paramClass, paramString).getChar(null);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static double getStaticDoubleField(Class<?> paramClass, String paramString) {
    try {
      return findField(paramClass, paramString).getDouble(null);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static float getStaticFloatField(Class<?> paramClass, String paramString) {
    try {
      return findField(paramClass, paramString).getFloat(null);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static int getStaticIntField(Class<?> paramClass, String paramString) {
    try {
      return findField(paramClass, paramString).getInt(null);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static long getStaticLongField(Class<?> paramClass, String paramString) {
    try {
      return findField(paramClass, paramString).getLong(null);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static Object getStaticObjectField(Class<?> paramClass, String paramString) {
    try {
      return findField(paramClass, paramString).get(null);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static short getStaticShortField(Class<?> paramClass, String paramString) {
    try {
      return findField(paramClass, paramString).getShort(null);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static Object getSurroundingThis(Object paramObject) {
    return getObjectField(paramObject, "this$0");
  }
  
  public static int incrementMethodDepth(String paramString) {
    return ((AtomicInteger)getMethodDepthCounter(paramString).get()).incrementAndGet();
  }
  
  static byte[] inputStreamToByteArray(InputStream paramInputStream) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte[1024];
    while (true) {
      int i = paramInputStream.read(arrayOfByte);
      if (i > 0) {
        byteArrayOutputStream.write(arrayOfByte, 0, i);
        continue;
      } 
      paramInputStream.close();
      return byteArrayOutputStream.toByteArray();
    } 
  }
  
  public static Object newInstance(Class<?> paramClass, Class<?>[] paramArrayOfClass, Object... paramVarArgs) {
    try {
      return findConstructorBestMatch(paramClass, paramArrayOfClass, paramVarArgs).newInstance(paramVarArgs);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } catch (InvocationTargetException invocationTargetException) {
      throw new InvocationTargetError(invocationTargetException.getCause());
    } catch (InstantiationException instantiationException) {
      throw new InstantiationError(instantiationException.getMessage());
    } 
  }
  
  public static Object newInstance(Class<?> paramClass, Object... paramVarArgs) {
    try {
      return findConstructorBestMatch(paramClass, paramVarArgs).newInstance(paramVarArgs);
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } catch (InvocationTargetException invocationTargetException) {
      throw new InvocationTargetError(invocationTargetException.getCause());
    } catch (InstantiationException instantiationException) {
      throw new InstantiationError(instantiationException.getMessage());
    } 
  }
  
  public static Object removeAdditionalInstanceField(Object paramObject, String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: ifnull -> 68
    //   4: aload_1
    //   5: ifnull -> 57
    //   8: getstatic de/robv/android/xposed/XposedHelpers.additionalFields : Ljava/util/WeakHashMap;
    //   11: astore_2
    //   12: aload_2
    //   13: monitorenter
    //   14: getstatic de/robv/android/xposed/XposedHelpers.additionalFields : Ljava/util/WeakHashMap;
    //   17: aload_0
    //   18: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   21: checkcast java/util/HashMap
    //   24: astore_0
    //   25: aload_0
    //   26: ifnonnull -> 33
    //   29: aload_2
    //   30: monitorexit
    //   31: aconst_null
    //   32: areturn
    //   33: aload_2
    //   34: monitorexit
    //   35: aload_0
    //   36: monitorenter
    //   37: aload_0
    //   38: aload_1
    //   39: invokevirtual remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   42: astore_1
    //   43: aload_0
    //   44: monitorexit
    //   45: aload_1
    //   46: areturn
    //   47: astore_1
    //   48: aload_0
    //   49: monitorexit
    //   50: aload_1
    //   51: athrow
    //   52: astore_0
    //   53: aload_2
    //   54: monitorexit
    //   55: aload_0
    //   56: athrow
    //   57: new java/lang/NullPointerException
    //   60: dup
    //   61: ldc_w 'key must not be null'
    //   64: invokespecial <init> : (Ljava/lang/String;)V
    //   67: athrow
    //   68: new java/lang/NullPointerException
    //   71: dup
    //   72: ldc_w 'object must not be null'
    //   75: invokespecial <init> : (Ljava/lang/String;)V
    //   78: athrow
    // Exception table:
    //   from	to	target	type
    //   14	25	52	finally
    //   29	31	52	finally
    //   33	35	52	finally
    //   37	45	47	finally
    //   48	50	47	finally
    //   53	55	52	finally
  }
  
  public static Object removeAdditionalStaticField(Class<?> paramClass, String paramString) {
    return removeAdditionalInstanceField(paramClass, paramString);
  }
  
  public static Object removeAdditionalStaticField(Object paramObject, String paramString) {
    return removeAdditionalInstanceField(paramObject.getClass(), paramString);
  }
  
  public static Object setAdditionalInstanceField(Object paramObject1, String paramString, Object paramObject2) {
    // Byte code:
    //   0: aload_0
    //   1: ifnull -> 95
    //   4: aload_1
    //   5: ifnull -> 84
    //   8: getstatic de/robv/android/xposed/XposedHelpers.additionalFields : Ljava/util/WeakHashMap;
    //   11: astore_3
    //   12: aload_3
    //   13: monitorenter
    //   14: getstatic de/robv/android/xposed/XposedHelpers.additionalFields : Ljava/util/WeakHashMap;
    //   17: aload_0
    //   18: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   21: checkcast java/util/HashMap
    //   24: astore #4
    //   26: aload #4
    //   28: astore #5
    //   30: aload #4
    //   32: ifnonnull -> 55
    //   35: new java/util/HashMap
    //   38: astore #5
    //   40: aload #5
    //   42: invokespecial <init> : ()V
    //   45: getstatic de/robv/android/xposed/XposedHelpers.additionalFields : Ljava/util/WeakHashMap;
    //   48: aload_0
    //   49: aload #5
    //   51: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   54: pop
    //   55: aload_3
    //   56: monitorexit
    //   57: aload #5
    //   59: monitorenter
    //   60: aload #5
    //   62: aload_1
    //   63: aload_2
    //   64: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   67: astore_0
    //   68: aload #5
    //   70: monitorexit
    //   71: aload_0
    //   72: areturn
    //   73: astore_0
    //   74: aload #5
    //   76: monitorexit
    //   77: aload_0
    //   78: athrow
    //   79: astore_0
    //   80: aload_3
    //   81: monitorexit
    //   82: aload_0
    //   83: athrow
    //   84: new java/lang/NullPointerException
    //   87: dup
    //   88: ldc_w 'key must not be null'
    //   91: invokespecial <init> : (Ljava/lang/String;)V
    //   94: athrow
    //   95: new java/lang/NullPointerException
    //   98: dup
    //   99: ldc_w 'object must not be null'
    //   102: invokespecial <init> : (Ljava/lang/String;)V
    //   105: athrow
    // Exception table:
    //   from	to	target	type
    //   14	26	79	finally
    //   35	55	79	finally
    //   55	57	79	finally
    //   60	71	73	finally
    //   74	77	73	finally
    //   80	82	79	finally
  }
  
  public static Object setAdditionalStaticField(Class<?> paramClass, String paramString, Object paramObject) {
    return setAdditionalInstanceField(paramClass, paramString, paramObject);
  }
  
  public static Object setAdditionalStaticField(Object paramObject1, String paramString, Object paramObject2) {
    return setAdditionalInstanceField(paramObject1.getClass(), paramString, paramObject2);
  }
  
  public static void setBooleanField(Object paramObject, String paramString, boolean paramBoolean) {
    try {
      findField(paramObject.getClass(), paramString).setBoolean(paramObject, paramBoolean);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setByteField(Object paramObject, String paramString, byte paramByte) {
    try {
      findField(paramObject.getClass(), paramString).setByte(paramObject, paramByte);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setCharField(Object paramObject, String paramString, char paramChar) {
    try {
      findField(paramObject.getClass(), paramString).setChar(paramObject, paramChar);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setDoubleField(Object paramObject, String paramString, double paramDouble) {
    try {
      findField(paramObject.getClass(), paramString).setDouble(paramObject, paramDouble);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setFloatField(Object paramObject, String paramString, float paramFloat) {
    try {
      findField(paramObject.getClass(), paramString).setFloat(paramObject, paramFloat);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setIntField(Object paramObject, String paramString, int paramInt) {
    try {
      findField(paramObject.getClass(), paramString).setInt(paramObject, paramInt);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setLongField(Object paramObject, String paramString, long paramLong) {
    try {
      findField(paramObject.getClass(), paramString).setLong(paramObject, paramLong);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setObjectField(Object paramObject1, String paramString, Object paramObject2) {
    try {
      findField(paramObject1.getClass(), paramString).set(paramObject1, paramObject2);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setShortField(Object paramObject, String paramString, short paramShort) {
    try {
      findField(paramObject.getClass(), paramString).setShort(paramObject, paramShort);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setStaticBooleanField(Class<?> paramClass, String paramString, boolean paramBoolean) {
    try {
      findField(paramClass, paramString).setBoolean(null, paramBoolean);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setStaticByteField(Class<?> paramClass, String paramString, byte paramByte) {
    try {
      findField(paramClass, paramString).setByte(null, paramByte);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setStaticCharField(Class<?> paramClass, String paramString, char paramChar) {
    try {
      findField(paramClass, paramString).setChar(null, paramChar);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setStaticDoubleField(Class<?> paramClass, String paramString, double paramDouble) {
    try {
      findField(paramClass, paramString).setDouble(null, paramDouble);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setStaticFloatField(Class<?> paramClass, String paramString, float paramFloat) {
    try {
      findField(paramClass, paramString).setFloat(null, paramFloat);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setStaticIntField(Class<?> paramClass, String paramString, int paramInt) {
    try {
      findField(paramClass, paramString).setInt(null, paramInt);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setStaticLongField(Class<?> paramClass, String paramString, long paramLong) {
    try {
      findField(paramClass, paramString).setLong(null, paramLong);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setStaticObjectField(Class<?> paramClass, String paramString, Object paramObject) {
    try {
      findField(paramClass, paramString).set(null, paramObject);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static void setStaticShortField(Class<?> paramClass, String paramString, short paramShort) {
    try {
      findField(paramClass, paramString).setShort(null, paramShort);
      return;
    } catch (IllegalAccessException illegalAccessException) {
      XposedBridge.log(illegalAccessException);
      throw new IllegalAccessError(illegalAccessException.getMessage());
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } 
  }
  
  public static final class ClassNotFoundError extends Error {
    private static final long serialVersionUID = -1070936889459514628L;
    
    public ClassNotFoundError(String param1String, Throwable param1Throwable) {
      super(param1String, param1Throwable);
    }
    
    public ClassNotFoundError(Throwable param1Throwable) {
      super(param1Throwable);
    }
  }
  
  public static final class InvocationTargetError extends Error {
    private static final long serialVersionUID = -1070936889459514628L;
    
    public InvocationTargetError(Throwable param1Throwable) {
      super(param1Throwable);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\XposedHelpers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */