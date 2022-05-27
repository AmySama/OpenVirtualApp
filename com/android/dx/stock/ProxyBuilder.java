package com.android.dx.stock;

import com.android.dx.Code;
import com.android.dx.Comparison;
import com.android.dx.DexMaker;
import com.android.dx.FieldId;
import com.android.dx.Label;
import com.android.dx.Local;
import com.android.dx.MethodId;
import com.android.dx.TypeId;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ProxyBuilder<T> {
  private static final String FIELD_NAME_HANDLER = "$__handler";
  
  private static final String FIELD_NAME_METHODS = "$__methodArray";
  
  private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_BOXED;
  
  private static final Map<Class<?>, MethodId<?, ?>> PRIMITIVE_TO_UNBOX_METHOD;
  
  private static final Map<TypeId<?>, MethodId<?, ?>> PRIMITIVE_TYPE_TO_UNBOX_METHOD;
  
  public static final int VERSION = 1;
  
  private static final Map<ProxiedClass<?>, Class<?>> generatedProxyClasses = Collections.synchronizedMap(new HashMap<ProxiedClass<?>, Class<?>>());
  
  private final Class<T> baseClass;
  
  private Class<?>[] constructorArgTypes = new Class[0];
  
  private Object[] constructorArgValues = new Object[0];
  
  private File dexCache;
  
  private InvocationHandler handler;
  
  private List<Class<?>> interfaces = new ArrayList<Class<?>>();
  
  private boolean markTrusted;
  
  private Method[] methods;
  
  private ClassLoader parentClassLoader = ProxyBuilder.class.getClassLoader();
  
  private boolean sharedClassLoader;
  
  static {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    PRIMITIVE_TO_BOXED = (Map)hashMap;
    hashMap.put(boolean.class, Boolean.class);
    PRIMITIVE_TO_BOXED.put(int.class, Integer.class);
    PRIMITIVE_TO_BOXED.put(byte.class, Byte.class);
    PRIMITIVE_TO_BOXED.put(long.class, Long.class);
    PRIMITIVE_TO_BOXED.put(short.class, Short.class);
    PRIMITIVE_TO_BOXED.put(float.class, Float.class);
    PRIMITIVE_TO_BOXED.put(double.class, Double.class);
    PRIMITIVE_TO_BOXED.put(char.class, Character.class);
    PRIMITIVE_TYPE_TO_UNBOX_METHOD = new HashMap<TypeId<?>, MethodId<?, ?>>();
    for (Map.Entry<Class<?>, Class<?>> entry : PRIMITIVE_TO_BOXED.entrySet()) {
      TypeId<?> typeId1 = TypeId.get((Class)entry.getKey());
      TypeId typeId = TypeId.get((Class)entry.getValue());
      MethodId<?, ?> methodId = typeId.getMethod(typeId, "valueOf", new TypeId[] { typeId1 });
      PRIMITIVE_TYPE_TO_UNBOX_METHOD.put(typeId1, methodId);
    } 
    hashMap = new HashMap<Object, Object>();
    hashMap.put(boolean.class, TypeId.get(Boolean.class).getMethod(TypeId.BOOLEAN, "booleanValue", new TypeId[0]));
    hashMap.put(int.class, TypeId.get(Integer.class).getMethod(TypeId.INT, "intValue", new TypeId[0]));
    hashMap.put(byte.class, TypeId.get(Byte.class).getMethod(TypeId.BYTE, "byteValue", new TypeId[0]));
    hashMap.put(long.class, TypeId.get(Long.class).getMethod(TypeId.LONG, "longValue", new TypeId[0]));
    hashMap.put(short.class, TypeId.get(Short.class).getMethod(TypeId.SHORT, "shortValue", new TypeId[0]));
    hashMap.put(float.class, TypeId.get(Float.class).getMethod(TypeId.FLOAT, "floatValue", new TypeId[0]));
    hashMap.put(double.class, TypeId.get(Double.class).getMethod(TypeId.DOUBLE, "doubleValue", new TypeId[0]));
    hashMap.put(char.class, TypeId.get(Character.class).getMethod(TypeId.CHAR, "charValue", new TypeId[0]));
    PRIMITIVE_TO_UNBOX_METHOD = (Map)hashMap;
  }
  
  private ProxyBuilder(Class<T> paramClass) {
    this.baseClass = paramClass;
  }
  
  private static Local<?> boxIfRequired(Code paramCode, Local<?> paramLocal, Local<Object> paramLocal1) {
    MethodId methodId = PRIMITIVE_TYPE_TO_UNBOX_METHOD.get(paramLocal.getType());
    if (methodId == null)
      return paramLocal; 
    paramCode.invokeStatic(methodId, paramLocal1, new Local[] { paramLocal });
    return paramLocal1;
  }
  
  public static Object callSuper(Object paramObject, Method paramMethod, Object... paramVarArgs) throws Throwable {
    try {
      return paramObject.getClass().getMethod(superMethodName(paramMethod), paramMethod.getParameterTypes()).invoke(paramObject, paramVarArgs);
    } catch (InvocationTargetException invocationTargetException) {
      throw invocationTargetException.getCause();
    } 
  }
  
  private static void check(boolean paramBoolean, String paramString) {
    if (paramBoolean)
      return; 
    throw new IllegalArgumentException(paramString);
  }
  
  private static TypeId<?>[] classArrayToTypeArray(Class<?>[] paramArrayOfClass) {
    TypeId[] arrayOfTypeId = new TypeId[paramArrayOfClass.length];
    for (byte b = 0; b < paramArrayOfClass.length; b++)
      arrayOfTypeId[b] = TypeId.get(paramArrayOfClass[b]); 
    return (TypeId<?>[])arrayOfTypeId;
  }
  
  public static <T> ProxyBuilder<T> forClass(Class<T> paramClass) {
    return new ProxyBuilder<T>(paramClass);
  }
  
  private static <T, G extends T> void generateCodeForAllMethods(DexMaker paramDexMaker, TypeId<G> paramTypeId, Method[] paramArrayOfMethod, TypeId<T> paramTypeId1) {
    TypeId<G> typeId = paramTypeId;
    TypeId typeId1 = TypeId.get(InvocationHandler.class);
    TypeId typeId2 = TypeId.get(Method[].class);
    FieldId fieldId1 = typeId.getField(typeId1, "$__handler");
    FieldId fieldId2 = typeId.getField(typeId2, "$__methodArray");
    TypeId typeId3 = TypeId.get(Method.class);
    TypeId typeId4 = TypeId.get(Object[].class);
    MethodId methodId = typeId1.getMethod(TypeId.OBJECT, "invoke", new TypeId[] { TypeId.OBJECT, typeId3, typeId4 });
    byte b = 0;
    while (true) {
      Method[] arrayOfMethod = paramArrayOfMethod;
      typeId = paramTypeId;
      if (b < arrayOfMethod.length) {
        Local<AbstractMethodError> local;
        Local[] arrayOfLocal;
        Local local14;
        Local<String> local15;
        Method method = arrayOfMethod[b];
        String str = method.getName();
        Class[] arrayOfClass = method.getParameterTypes();
        int i = arrayOfClass.length;
        TypeId[] arrayOfTypeId = new TypeId[i];
        int j;
        for (j = 0; j < i; j++)
          arrayOfTypeId[j] = TypeId.get(arrayOfClass[j]); 
        Class<?> clazz1 = method.getReturnType();
        TypeId typeId5 = TypeId.get(clazz1);
        MethodId methodId1 = typeId.getMethod(typeId5, str, arrayOfTypeId);
        TypeId typeId6 = TypeId.get(AbstractMethodError.class);
        Code code2 = paramDexMaker.declare(methodId1, 1);
        Local local2 = code2.getThis(typeId);
        Local local3 = code2.newLocal(typeId1);
        Local local4 = code2.newLocal(TypeId.OBJECT);
        Local local5 = code2.newLocal(TypeId.INT);
        Local local6 = code2.newLocal(typeId4);
        Local local7 = code2.newLocal(TypeId.INT);
        Local<Object> local8 = code2.newLocal(TypeId.OBJECT);
        Local local9 = code2.newLocal(typeId5);
        Local local10 = code2.newLocal(typeId2);
        Local local11 = code2.newLocal(typeId3);
        Local local12 = code2.newLocal(TypeId.INT);
        Class clazz = PRIMITIVE_TO_BOXED.get(clazz1);
        if (clazz != null) {
          Local local16 = code2.newLocal(TypeId.get(clazz));
        } else {
          methodId1 = null;
        } 
        Local local13 = code2.newLocal(typeId1);
        if ((method.getModifiers() & 0x400) == 0) {
          arrayOfLocal = new Local[arrayOfClass.length];
          local14 = code2.newLocal(typeId5);
          MethodId methodId2 = paramTypeId1.getMethod(typeId5, str, arrayOfTypeId);
          local15 = null;
          clazz = null;
        } else {
          local15 = code2.newLocal(TypeId.STRING);
          local = code2.newLocal(typeId6);
          local14 = null;
          arrayOfLocal = null;
          str = null;
        } 
        TypeId typeId7 = typeId5;
        code2.loadConstant(local12, Integer.valueOf(b));
        code2.sget(fieldId2, local10);
        code2.aget(local11, local10, local12);
        code2.loadConstant(local7, Integer.valueOf(i));
        code2.newArray(local6, local7);
        code2.iget(fieldId1, local3, local2);
        code2.loadConstant(local13, null);
        Label label = new Label();
        code2.compare(Comparison.EQ, label, local13, local3);
        j = i;
        i = 0;
        Local local1 = local5;
        while (i < j) {
          code2.loadConstant(local1, Integer.valueOf(i));
          code2.aput(local6, local1, boxIfRequired(code2, code2.getParameter(i, arrayOfTypeId[i]), local8));
          i++;
        } 
        code2.invokeInterface(methodId, local4, local3, new Local[] { local2, local11, local6 });
        generateCodeForReturnStatement(code2, clazz1, local4, local9, (Local)methodId1);
        code2.mark(label);
        if ((method.getModifiers() & 0x400) == 0) {
          for (j = 0; j < arrayOfLocal.length; j++)
            arrayOfLocal[j] = code2.getParameter(j, arrayOfTypeId[j]); 
          if (void.class.equals(clazz1)) {
            code2.invokeSuper((MethodId)str, null, local2, arrayOfLocal);
            code2.returnVoid();
          } else {
            invokeSuper((MethodId)str, code2, local2, arrayOfLocal, local14);
            code2.returnValue(local14);
          } 
        } else {
          throwAbstractMethodError(code2, method, local15, local);
        } 
        Code code1 = paramDexMaker.declare(paramTypeId.getMethod(typeId7, superMethodName(method), arrayOfTypeId), 1);
        if ((method.getModifiers() & 0x400) == 0) {
          Local local16 = code1.getThis(paramTypeId);
          i = arrayOfClass.length;
          Local[] arrayOfLocal1 = new Local[i];
          for (j = 0; j < i; j++)
            arrayOfLocal1[j] = code1.getParameter(j, arrayOfTypeId[j]); 
          if (void.class.equals(clazz1)) {
            code1.invokeSuper((MethodId)str, null, local16, arrayOfLocal1);
            code1.returnVoid();
          } else {
            Local local17 = code1.newLocal(typeId7);
            invokeSuper((MethodId)str, code1, local16, arrayOfLocal1, local17);
            code1.returnValue(local17);
          } 
        } else {
          throwAbstractMethodError(code1, method, code1.newLocal(TypeId.STRING), code1.newLocal(typeId6));
        } 
        b++;
        continue;
      } 
      break;
    } 
  }
  
  private static void generateCodeForReturnStatement(Code paramCode, Class<?> paramClass, Local paramLocal1, Local paramLocal2, Local paramLocal3) {
    if (PRIMITIVE_TO_UNBOX_METHOD.containsKey(paramClass)) {
      paramCode.cast(paramLocal3, paramLocal1);
      paramCode.invokeVirtual(getUnboxMethodForPrimitive(paramClass), paramLocal2, paramLocal3, new Local[0]);
      paramCode.returnValue(paramLocal2);
    } else if (void.class.equals(paramClass)) {
      paramCode.returnVoid();
    } else {
      paramCode.cast(paramLocal2, paramLocal1);
      paramCode.returnValue(paramLocal2);
    } 
  }
  
  private static <T, G extends T> void generateConstructorsAndFields(DexMaker paramDexMaker, TypeId<G> paramTypeId, TypeId<T> paramTypeId1, Class<T> paramClass) {
    TypeId typeId1 = TypeId.get(InvocationHandler.class);
    TypeId typeId2 = TypeId.get(Method[].class);
    paramDexMaker.declare(paramTypeId.getField(typeId1, "$__handler"), 2, null);
    paramDexMaker.declare(paramTypeId.getField(typeId2, "$__methodArray"), 10, null);
    for (Constructor<T> constructor : getConstructorsToOverwrite(paramClass)) {
      if (constructor.getModifiers() != 16) {
        TypeId[] arrayOfTypeId = (TypeId[])classArrayToTypeArray(constructor.getParameterTypes());
        Code code = paramDexMaker.declare(paramTypeId.getConstructor(arrayOfTypeId), 1);
        Local local = code.getThis(paramTypeId);
        int i = arrayOfTypeId.length;
        Local[] arrayOfLocal = new Local[i];
        for (byte b = 0; b < i; b++)
          arrayOfLocal[b] = code.getParameter(b, arrayOfTypeId[b]); 
        code.invokeDirect(paramTypeId1.getConstructor(arrayOfTypeId), null, local, arrayOfLocal);
        code.returnVoid();
      } 
    } 
  }
  
  private static <T> Constructor<T>[] getConstructorsToOverwrite(Class<T> paramClass) {
    return (Constructor[])paramClass.getDeclaredConstructors();
  }
  
  private TypeId<?>[] getInterfacesAsTypeIds() {
    TypeId[] arrayOfTypeId = new TypeId[this.interfaces.size()];
    Iterator<Class<?>> iterator = this.interfaces.iterator();
    for (byte b = 0; iterator.hasNext(); b++)
      arrayOfTypeId[b] = TypeId.get(iterator.next()); 
    return (TypeId<?>[])arrayOfTypeId;
  }
  
  public static InvocationHandler getInvocationHandler(Object paramObject) {
    try {
      Field field = paramObject.getClass().getDeclaredField("$__handler");
      field.setAccessible(true);
      return (InvocationHandler)field.get(paramObject);
    } catch (NoSuchFieldException noSuchFieldException) {
      throw new IllegalArgumentException("Not a valid proxy instance", noSuchFieldException);
    } catch (IllegalAccessException illegalAccessException) {
      throw new AssertionError(illegalAccessException);
    } 
  }
  
  private static <T> String getMethodNameForProxyOf(Class<T> paramClass, List<Class<?>> paramList) {
    String str = Integer.toHexString(paramList.hashCode());
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramClass.getName().replace(".", "/"));
    stringBuilder.append("_");
    stringBuilder.append(str);
    stringBuilder.append("_Proxy");
    return stringBuilder.toString();
  }
  
  private void getMethodsToProxy(Set<MethodSetEntry> paramSet1, Set<MethodSetEntry> paramSet2, Class<?> paramClass) {
    Method[] arrayOfMethod = paramClass.getDeclaredMethods();
    int i = arrayOfMethod.length;
    boolean bool = false;
    byte b;
    for (b = 0; b < i; b++) {
      MethodSetEntry methodSetEntry;
      Method method = arrayOfMethod[b];
      if ((method.getModifiers() & 0x10) != 0) {
        methodSetEntry = new MethodSetEntry(method);
        paramSet2.add(methodSetEntry);
        paramSet1.remove(methodSetEntry);
      } else if ((methodSetEntry.getModifiers() & 0x8) == 0 && (Modifier.isPublic(methodSetEntry.getModifiers()) || Modifier.isProtected(methodSetEntry.getModifiers()) || (this.sharedClassLoader && !Modifier.isPrivate(methodSetEntry.getModifiers()))) && (!methodSetEntry.getName().equals("finalize") || (methodSetEntry.getParameterTypes()).length != 0)) {
        methodSetEntry = new MethodSetEntry((Method)methodSetEntry);
        if (!paramSet2.contains(methodSetEntry))
          paramSet1.add(methodSetEntry); 
      } 
    } 
    if (paramClass.isInterface()) {
      Class[] arrayOfClass = paramClass.getInterfaces();
      i = arrayOfClass.length;
      for (b = bool; b < i; b++)
        getMethodsToProxy(paramSet1, paramSet2, arrayOfClass[b]); 
    } 
  }
  
  private Method[] getMethodsToProxyRecursive() {
    HashSet<MethodSetEntry> hashSet1 = new HashSet();
    HashSet<MethodSetEntry> hashSet2 = new HashSet();
    Class<T> clazz;
    for (clazz = this.baseClass; clazz != null; clazz = (Class)clazz.getSuperclass())
      getMethodsToProxy(hashSet1, hashSet2, clazz); 
    clazz = this.baseClass;
    while (true) {
      int i = 0;
      int j = 0;
      if (clazz != null) {
        Class[] arrayOfClass = clazz.getInterfaces();
        i = arrayOfClass.length;
        while (j < i) {
          getMethodsToProxy(hashSet1, hashSet2, arrayOfClass[j]);
          j++;
        } 
        clazz = (Class)clazz.getSuperclass();
        continue;
      } 
      Iterator<Class<?>> iterator1 = this.interfaces.iterator();
      while (iterator1.hasNext())
        getMethodsToProxy(hashSet1, hashSet2, iterator1.next()); 
      Method[] arrayOfMethod = new Method[hashSet1.size()];
      Iterator<MethodSetEntry> iterator = hashSet1.iterator();
      for (j = i; iterator.hasNext(); j++)
        arrayOfMethod[j] = ((MethodSetEntry)iterator.next()).originalMethod; 
      return arrayOfMethod;
    } 
  }
  
  private static MethodId<?, ?> getUnboxMethodForPrimitive(Class<?> paramClass) {
    return PRIMITIVE_TO_UNBOX_METHOD.get(paramClass);
  }
  
  private static void invokeSuper(MethodId paramMethodId, Code paramCode, Local paramLocal1, Local[] paramArrayOfLocal, Local paramLocal2) {
    paramCode.invokeSuper(paramMethodId, paramLocal2, paramLocal1, paramArrayOfLocal);
  }
  
  public static boolean isProxyClass(Class<?> paramClass) {
    try {
      paramClass.getDeclaredField("$__handler");
      return true;
    } catch (NoSuchFieldException noSuchFieldException) {
      return false;
    } 
  }
  
  private static RuntimeException launderCause(InvocationTargetException paramInvocationTargetException) {
    Throwable throwable = paramInvocationTargetException.getCause();
    if (!(throwable instanceof Error)) {
      if (throwable instanceof RuntimeException)
        throw (RuntimeException)throwable; 
      throw new UndeclaredThrowableException(throwable);
    } 
    throw (Error)throwable;
  }
  
  private Class<? extends T> loadClass(ClassLoader paramClassLoader, String paramString) throws ClassNotFoundException {
    return (Class)paramClassLoader.loadClass(paramString);
  }
  
  public static void setInvocationHandler(Object paramObject, InvocationHandler paramInvocationHandler) {
    try {
      Field field = paramObject.getClass().getDeclaredField("$__handler");
      field.setAccessible(true);
      field.set(paramObject, paramInvocationHandler);
      return;
    } catch (NoSuchFieldException noSuchFieldException) {
      throw new IllegalArgumentException("Not a valid proxy instance", noSuchFieldException);
    } catch (IllegalAccessException illegalAccessException) {
      throw new AssertionError(illegalAccessException);
    } 
  }
  
  private static void setMethodsStaticField(Class<?> paramClass, Method[] paramArrayOfMethod) {
    try {
      Field field = paramClass.getDeclaredField("$__methodArray");
      field.setAccessible(true);
      field.set(null, paramArrayOfMethod);
      return;
    } catch (NoSuchFieldException noSuchFieldException) {
      throw new AssertionError(noSuchFieldException);
    } catch (IllegalAccessException illegalAccessException) {
      throw new AssertionError(illegalAccessException);
    } 
  }
  
  private static String superMethodName(Method paramMethod) {
    String str = paramMethod.getReturnType().getName();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("super$");
    stringBuilder.append(paramMethod.getName());
    stringBuilder.append("$");
    stringBuilder.append(str.replace('.', '_').replace('[', '_').replace(';', '_'));
    return stringBuilder.toString();
  }
  
  private static void throwAbstractMethodError(Code paramCode, Method paramMethod, Local<String> paramLocal, Local<AbstractMethodError> paramLocal1) {
    MethodId methodId = TypeId.get(AbstractMethodError.class).getConstructor(new TypeId[] { TypeId.STRING });
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("'");
    stringBuilder.append(paramMethod);
    stringBuilder.append("' cannot be called");
    paramCode.loadConstant(paramLocal, stringBuilder.toString());
    paramCode.newInstance(paramLocal1, methodId, new Local[] { paramLocal });
    paramCode.throwValue(paramLocal1);
  }
  
  public T build() throws IOException {
    boolean bool2;
    InvocationHandler invocationHandler = this.handler;
    boolean bool1 = true;
    if (invocationHandler != null) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    check(bool2, "handler == null");
    if (this.constructorArgTypes.length == this.constructorArgValues.length) {
      bool2 = bool1;
    } else {
      bool2 = false;
    } 
    check(bool2, "constructorArgValues.length != constructorArgTypes.length");
    Class<? extends T> clazz = buildProxyClass();
    try {
      Constructor<? extends T> constructor = clazz.getConstructor(this.constructorArgTypes);
      try {
        constructor = (Constructor<? extends T>)constructor.newInstance(this.constructorArgValues);
        setInvocationHandler(constructor, this.handler);
        return (T)constructor;
      } catch (InstantiationException instantiationException) {
        throw new AssertionError(instantiationException);
      } catch (IllegalAccessException illegalAccessException) {
        throw new AssertionError(illegalAccessException);
      } catch (InvocationTargetException invocationTargetException) {
        throw launderCause(invocationTargetException);
      } 
    } catch (NoSuchMethodException noSuchMethodException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("No constructor for ");
      stringBuilder.append(this.baseClass.getName());
      stringBuilder.append(" with parameter types ");
      stringBuilder.append(Arrays.toString((Object[])this.constructorArgTypes));
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
  }
  
  public Class<? extends T> buildProxyClass() throws IOException {
    ClassLoader classLoader;
    if (this.sharedClassLoader) {
      classLoader = this.baseClass.getClassLoader();
    } else {
      classLoader = this.parentClassLoader;
    } 
    ProxiedClass<?> proxiedClass = new ProxiedClass(this.baseClass, this.interfaces, classLoader, this.sharedClassLoader);
    Class<? extends T> clazz = (Class)generatedProxyClasses.get(proxiedClass);
    if (clazz != null)
      return clazz; 
    DexMaker dexMaker = new DexMaker();
    String str = getMethodNameForProxyOf(this.baseClass, this.interfaces);
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("L");
    stringBuilder1.append(str);
    stringBuilder1.append(";");
    TypeId<T> typeId1 = TypeId.get(stringBuilder1.toString());
    TypeId<T> typeId2 = TypeId.get(this.baseClass);
    generateConstructorsAndFields(dexMaker, typeId1, typeId2, this.baseClass);
    Method[] arrayOfMethod2 = this.methods;
    Method[] arrayOfMethod1 = arrayOfMethod2;
    if (arrayOfMethod2 == null)
      arrayOfMethod1 = getMethodsToProxyRecursive(); 
    Arrays.sort(arrayOfMethod1, new Comparator<Method>() {
          public int compare(Method param1Method1, Method param1Method2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(param1Method1.getDeclaringClass());
            stringBuilder.append(param1Method1.getName());
            stringBuilder.append(Arrays.toString((Object[])param1Method1.getParameterTypes()));
            stringBuilder.append(param1Method1.getReturnType());
            String str = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(param1Method2.getDeclaringClass());
            stringBuilder.append(param1Method2.getName());
            stringBuilder.append(Arrays.toString((Object[])param1Method2.getParameterTypes()));
            stringBuilder.append(param1Method2.getReturnType());
            return str.compareTo(stringBuilder.toString());
          }
        });
    generateCodeForAllMethods(dexMaker, typeId1, arrayOfMethod1, typeId2);
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(str);
    stringBuilder2.append(".generated");
    dexMaker.declare(typeId1, stringBuilder2.toString(), 1, typeId2, (TypeId[])getInterfacesAsTypeIds());
    if (this.sharedClassLoader)
      dexMaker.setSharedClassLoader(classLoader); 
    if (this.markTrusted)
      dexMaker.markAsTrusted(); 
    if (this.sharedClassLoader) {
      classLoader = dexMaker.generateAndLoad(null, this.dexCache);
    } else {
      classLoader = dexMaker.generateAndLoad(this.parentClassLoader, this.dexCache);
    } 
    try {
      Class<? extends T> clazz1 = loadClass(classLoader, str);
      setMethodsStaticField(clazz1, arrayOfMethod1);
      generatedProxyClasses.put(proxiedClass, clazz1);
      return clazz1;
    } catch (IllegalAccessError illegalAccessError) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("cannot proxy inaccessible class ");
      stringBuilder.append(this.baseClass);
      throw new UnsupportedOperationException(stringBuilder.toString(), illegalAccessError);
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionError(classNotFoundException);
    } 
  }
  
  public ProxyBuilder<T> constructorArgTypes(Class<?>... paramVarArgs) {
    this.constructorArgTypes = paramVarArgs;
    return this;
  }
  
  public ProxyBuilder<T> constructorArgValues(Object... paramVarArgs) {
    this.constructorArgValues = paramVarArgs;
    return this;
  }
  
  public ProxyBuilder<T> dexCache(File paramFile) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("v");
    stringBuilder.append(Integer.toString(1));
    paramFile = new File(paramFile, stringBuilder.toString());
    this.dexCache = paramFile;
    paramFile.mkdir();
    return this;
  }
  
  public ProxyBuilder<T> handler(InvocationHandler paramInvocationHandler) {
    this.handler = paramInvocationHandler;
    return this;
  }
  
  public ProxyBuilder<T> implementing(Class<?>... paramVarArgs) {
    List<Class<?>> list = this.interfaces;
    int i = paramVarArgs.length;
    byte b = 0;
    while (b < i) {
      Class<?> clazz = paramVarArgs[b];
      if (clazz.isInterface()) {
        if (!list.contains(clazz))
          list.add(clazz); 
        b++;
        continue;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Not an interface: ");
      stringBuilder.append(clazz.getName());
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    return this;
  }
  
  public ProxyBuilder<T> markTrusted() {
    this.markTrusted = true;
    return this;
  }
  
  public ProxyBuilder<T> onlyMethods(Method[] paramArrayOfMethod) {
    this.methods = paramArrayOfMethod;
    return this;
  }
  
  public ProxyBuilder<T> parentClassLoader(ClassLoader paramClassLoader) {
    this.parentClassLoader = paramClassLoader;
    return this;
  }
  
  public ProxyBuilder<T> withSharedClassLoader() {
    this.sharedClassLoader = true;
    return this;
  }
  
  public static class MethodSetEntry {
    public final String name;
    
    public final Method originalMethod;
    
    public final Class<?>[] paramTypes;
    
    public final Class<?> returnType;
    
    public MethodSetEntry(Method param1Method) {
      this.originalMethod = param1Method;
      this.name = param1Method.getName();
      this.paramTypes = param1Method.getParameterTypes();
      this.returnType = param1Method.getReturnType();
    }
    
    public boolean equals(Object param1Object) {
      boolean bool = param1Object instanceof MethodSetEntry;
      boolean bool1 = false;
      boolean bool2 = bool1;
      if (bool) {
        param1Object = param1Object;
        bool2 = bool1;
        if (this.name.equals(((MethodSetEntry)param1Object).name)) {
          bool2 = bool1;
          if (this.returnType.equals(((MethodSetEntry)param1Object).returnType)) {
            bool2 = bool1;
            if (Arrays.equals((Object[])this.paramTypes, (Object[])((MethodSetEntry)param1Object).paramTypes))
              bool2 = true; 
          } 
        } 
      } 
      return bool2;
    }
    
    public int hashCode() {
      int i = 527 + this.name.hashCode() + 17;
      i += i * 31 + this.returnType.hashCode();
      return i + i * 31 + Arrays.hashCode((Object[])this.paramTypes);
    }
  }
  
  private static class ProxiedClass<U> {
    final Class<U> clazz;
    
    final List<Class<?>> interfaces;
    
    final ClassLoader requestedClassloader;
    
    final boolean sharedClassLoader;
    
    private ProxiedClass(Class<U> param1Class, List<Class<?>> param1List, ClassLoader param1ClassLoader, boolean param1Boolean) {
      this.clazz = param1Class;
      this.interfaces = new ArrayList<Class<?>>(param1List);
      this.requestedClassloader = param1ClassLoader;
      this.sharedClassLoader = param1Boolean;
    }
    
    public boolean equals(Object param1Object) {
      boolean bool = true;
      if (this == param1Object)
        return true; 
      if (param1Object == null || getClass() != param1Object.getClass())
        return false; 
      param1Object = param1Object;
      if (this.clazz != ((ProxiedClass)param1Object).clazz || !this.interfaces.equals(((ProxiedClass)param1Object).interfaces) || this.requestedClassloader != ((ProxiedClass)param1Object).requestedClassloader || this.sharedClassLoader != ((ProxiedClass)param1Object).sharedClassLoader)
        bool = false; 
      return bool;
    }
    
    public int hashCode() {
      return this.clazz.hashCode() + this.interfaces.hashCode() + this.requestedClassloader.hashCode() + this.sharedClassLoader;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\stock\ProxyBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */