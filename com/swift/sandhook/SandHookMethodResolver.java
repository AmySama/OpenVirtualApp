package com.swift.sandhook;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class SandHookMethodResolver {
  public static Field artMethodField;
  
  public static boolean canResolvedInJava;
  
  public static Field dexCacheField;
  
  public static int dexMethodIndex;
  
  public static Field dexMethodIndexField;
  
  public static long entryPointFromCompiledCode;
  
  public static long entryPointFromInterpreter;
  
  public static Field fieldEntryPointFromCompiledCode;
  
  public static Field fieldEntryPointFromInterpreter;
  
  public static boolean isArtMethod;
  
  public static long resolvedMethodsAddress;
  
  public static Field resolvedMethodsField;
  
  public static Object testArtMethod;
  
  public static Method testMethod;
  
  private static void checkSupport() {
    try {
      Field field = SandHook.getField(Method.class, "artMethod");
      artMethodField = field;
      testArtMethod = field.get(testMethod);
      if (SandHook.hasJavaArtMethod() && testArtMethod.getClass() == SandHook.artMethodClass) {
        checkSupportForArtMethod();
        isArtMethod = true;
      } else if (testArtMethod instanceof Long) {
        checkSupportForArtMethodId();
        isArtMethod = false;
      } else {
        canResolvedInJava = false;
      } 
    } catch (Exception exception) {}
  }
  
  private static void checkSupportForArtMethod() throws Exception {
    // Byte code:
    //   0: getstatic com/swift/sandhook/SandHook.artMethodClass : Ljava/lang/Class;
    //   3: ldc 'dexMethodIndex'
    //   5: invokestatic getField : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   8: putstatic com/swift/sandhook/SandHookMethodResolver.dexMethodIndexField : Ljava/lang/reflect/Field;
    //   11: goto -> 26
    //   14: astore_0
    //   15: getstatic com/swift/sandhook/SandHook.artMethodClass : Ljava/lang/Class;
    //   18: ldc 'methodDexIndex'
    //   20: invokestatic getField : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   23: putstatic com/swift/sandhook/SandHookMethodResolver.dexMethodIndexField : Ljava/lang/reflect/Field;
    //   26: ldc java/lang/Class
    //   28: ldc 'dexCache'
    //   30: invokestatic getField : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   33: astore_0
    //   34: aload_0
    //   35: putstatic com/swift/sandhook/SandHookMethodResolver.dexCacheField : Ljava/lang/reflect/Field;
    //   38: aload_0
    //   39: getstatic com/swift/sandhook/SandHookMethodResolver.testMethod : Ljava/lang/reflect/Method;
    //   42: invokevirtual getDeclaringClass : ()Ljava/lang/Class;
    //   45: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   48: astore_0
    //   49: aload_0
    //   50: invokevirtual getClass : ()Ljava/lang/Class;
    //   53: ldc 'resolvedMethods'
    //   55: invokestatic getField : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   58: astore_1
    //   59: aload_1
    //   60: putstatic com/swift/sandhook/SandHookMethodResolver.resolvedMethodsField : Ljava/lang/reflect/Field;
    //   63: aload_1
    //   64: aload_0
    //   65: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   68: instanceof [Ljava/lang/Object;
    //   71: ifeq -> 78
    //   74: iconst_1
    //   75: putstatic com/swift/sandhook/SandHookMethodResolver.canResolvedInJava : Z
    //   78: getstatic com/swift/sandhook/SandHookMethodResolver.dexMethodIndexField : Ljava/lang/reflect/Field;
    //   81: getstatic com/swift/sandhook/SandHookMethodResolver.testArtMethod : Ljava/lang/Object;
    //   84: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   87: checkcast java/lang/Integer
    //   90: invokevirtual intValue : ()I
    //   93: putstatic com/swift/sandhook/SandHookMethodResolver.dexMethodIndex : I
    //   96: getstatic com/swift/sandhook/SandHook.artMethodClass : Ljava/lang/Class;
    //   99: ldc 'entryPointFromQuickCompiledCode'
    //   101: invokestatic getField : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   104: putstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromCompiledCode : Ljava/lang/reflect/Field;
    //   107: goto -> 122
    //   110: astore_0
    //   111: getstatic com/swift/sandhook/SandHook.artMethodClass : Ljava/lang/Class;
    //   114: ldc 'entryPointFromCompiledCode'
    //   116: invokestatic getField : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   119: putstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromCompiledCode : Ljava/lang/reflect/Field;
    //   122: getstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromCompiledCode : Ljava/lang/reflect/Field;
    //   125: invokevirtual getType : ()Ljava/lang/Class;
    //   128: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
    //   131: if_acmpne -> 150
    //   134: getstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromCompiledCode : Ljava/lang/reflect/Field;
    //   137: getstatic com/swift/sandhook/SandHookMethodResolver.testArtMethod : Ljava/lang/Object;
    //   140: invokevirtual getInt : (Ljava/lang/Object;)I
    //   143: i2l
    //   144: putstatic com/swift/sandhook/SandHookMethodResolver.entryPointFromCompiledCode : J
    //   147: goto -> 174
    //   150: getstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromCompiledCode : Ljava/lang/reflect/Field;
    //   153: invokevirtual getType : ()Ljava/lang/Class;
    //   156: getstatic java/lang/Long.TYPE : Ljava/lang/Class;
    //   159: if_acmpne -> 174
    //   162: getstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromCompiledCode : Ljava/lang/reflect/Field;
    //   165: getstatic com/swift/sandhook/SandHookMethodResolver.testArtMethod : Ljava/lang/Object;
    //   168: invokevirtual getLong : (Ljava/lang/Object;)J
    //   171: putstatic com/swift/sandhook/SandHookMethodResolver.entryPointFromCompiledCode : J
    //   174: getstatic com/swift/sandhook/SandHook.artMethodClass : Ljava/lang/Class;
    //   177: ldc 'entryPointFromInterpreter'
    //   179: invokestatic getField : (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   182: astore_0
    //   183: aload_0
    //   184: putstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromInterpreter : Ljava/lang/reflect/Field;
    //   187: aload_0
    //   188: invokevirtual getType : ()Ljava/lang/Class;
    //   191: getstatic java/lang/Integer.TYPE : Ljava/lang/Class;
    //   194: if_acmpne -> 213
    //   197: getstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromInterpreter : Ljava/lang/reflect/Field;
    //   200: getstatic com/swift/sandhook/SandHookMethodResolver.testArtMethod : Ljava/lang/Object;
    //   203: invokevirtual getInt : (Ljava/lang/Object;)I
    //   206: i2l
    //   207: putstatic com/swift/sandhook/SandHookMethodResolver.entryPointFromInterpreter : J
    //   210: goto -> 237
    //   213: getstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromCompiledCode : Ljava/lang/reflect/Field;
    //   216: invokevirtual getType : ()Ljava/lang/Class;
    //   219: getstatic java/lang/Long.TYPE : Ljava/lang/Class;
    //   222: if_acmpne -> 237
    //   225: getstatic com/swift/sandhook/SandHookMethodResolver.fieldEntryPointFromInterpreter : Ljava/lang/reflect/Field;
    //   228: getstatic com/swift/sandhook/SandHookMethodResolver.testArtMethod : Ljava/lang/Object;
    //   231: invokevirtual getLong : (Ljava/lang/Object;)J
    //   234: putstatic com/swift/sandhook/SandHookMethodResolver.entryPointFromInterpreter : J
    //   237: return
    //   238: astore_0
    //   239: goto -> 96
    //   242: astore_0
    //   243: goto -> 237
    // Exception table:
    //   from	to	target	type
    //   0	11	14	java/lang/NoSuchFieldException
    //   78	96	238	finally
    //   96	107	110	finally
    //   111	122	242	finally
    //   122	147	242	finally
    //   150	174	242	finally
    //   174	210	242	finally
    //   213	237	242	finally
  }
  
  private static void checkSupportForArtMethodId() throws Exception {
    Field field = SandHook.getField(Method.class, "dexMethodIndex");
    dexMethodIndexField = field;
    dexMethodIndex = ((Integer)field.get(testMethod)).intValue();
    field = SandHook.getField(Class.class, "dexCache");
    dexCacheField = field;
    Object object2 = field.get(testMethod.getDeclaringClass());
    field = SandHook.getField(object2.getClass(), "resolvedMethods");
    resolvedMethodsField = field;
    Object object1 = field.get(object2);
    if (object1 instanceof Long) {
      canResolvedInJava = false;
      resolvedMethodsAddress = ((Long)object1).longValue();
    } else if (object1 instanceof long[]) {
      canResolvedInJava = true;
    } else if (object1 instanceof int[]) {
      canResolvedInJava = true;
    } 
  }
  
  public static long getArtMethod(Member paramMember) {
    Field field = artMethodField;
    if (field == null)
      return 0L; 
    try {
      return ((Long)field.get(paramMember)).longValue();
    } catch (IllegalAccessException illegalAccessException) {
      return 0L;
    } 
  }
  
  public static void init() {
    testMethod = SandHook.testOffsetMethod1;
    checkSupport();
  }
  
  private static void resolveInJava(Method paramMethod1, Method paramMethod2) throws Exception {
    Object object2;
    Object object1 = dexCacheField.get(paramMethod1.getDeclaringClass());
    if (isArtMethod) {
      object2 = artMethodField.get(paramMethod2);
      int i = ((Integer)dexMethodIndexField.get(object2)).intValue();
      ((Object[])resolvedMethodsField.get(object1))[i] = object2;
    } else {
      int i = ((Integer)dexMethodIndexField.get(object2)).intValue();
      object1 = resolvedMethodsField.get(object1);
      if (object1 instanceof long[]) {
        long l = ((Long)artMethodField.get(object2)).longValue();
        ((long[])object1)[i] = l;
      } else {
        if (object1 instanceof int[]) {
          int j = Long.valueOf(((Long)artMethodField.get(object2)).longValue()).intValue();
          ((int[])object1)[i] = j;
          return;
        } 
        throw new UnsupportedOperationException("un support");
      } 
    } 
  }
  
  private static void resolveInNative(Method paramMethod1, Method paramMethod2) {
    SandHook.ensureMethodCached(paramMethod1, paramMethod2);
  }
  
  public static void resolveMethod(Method paramMethod1, Method paramMethod2) {
    if (canResolvedInJava && artMethodField != null) {
      try {
        resolveInJava(paramMethod1, paramMethod2);
      } catch (Exception exception) {
        resolveInNative(paramMethod1, paramMethod2);
      } 
    } else {
      resolveInNative(paramMethod1, paramMethod2);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\SandHookMethodResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */