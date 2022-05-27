package com.swift.sandhook;

import com.swift.sandhook.utils.ReflectionUtils;
import com.swift.sandhook.utils.Unsafe;
import com.swift.sandhook.wrapper.HookErrorException;
import com.swift.sandhook.wrapper.HookWrapper;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SandHook {
  public static Class artMethodClass;
  
  static Map<Method, HookWrapper.HookEntity> globalBackupMap;
  
  static Map<Member, HookWrapper.HookEntity> globalHookEntityMap = new ConcurrentHashMap<Member, HookWrapper.HookEntity>();
  
  private static HookModeCallBack hookModeCallBack;
  
  private static HookResultCallBack hookResultCallBack;
  
  public static Field nativePeerField;
  
  public static int testAccessFlag;
  
  public static Object testOffsetArtMethod1;
  
  public static Object testOffsetArtMethod2;
  
  public static Method testOffsetMethod1;
  
  public static Method testOffsetMethod2;
  
  static {
    globalBackupMap = new ConcurrentHashMap<Method, HookWrapper.HookEntity>();
    init();
  }
  
  public static native void MakeInitializedClassVisibilyInitialized(long paramLong);
  
  public static void addHookClass(ClassLoader paramClassLoader, Class... paramVarArgs) throws HookErrorException {
    HookWrapper.addHookClass(paramClassLoader, paramVarArgs);
  }
  
  public static void addHookClass(Class... paramVarArgs) throws HookErrorException {
    HookWrapper.addHookClass(paramVarArgs);
  }
  
  public static native void addPendingHookNative(Member paramMember);
  
  public static final Object callOriginByBackup(Method paramMethod, Object paramObject, Object... paramVarArgs) throws Throwable {
    HookWrapper.HookEntity hookEntity = globalBackupMap.get(paramMethod);
    return (hookEntity == null) ? null : callOriginMethod(hookEntity.backupIsStub, hookEntity.target, paramMethod, paramObject, paramVarArgs);
  }
  
  public static final Object callOriginMethod(Member paramMember, Object paramObject, Object... paramVarArgs) throws Throwable {
    HookWrapper.HookEntity hookEntity = globalHookEntityMap.get(paramMember);
    return (hookEntity == null || hookEntity.backup == null) ? null : callOriginMethod(hookEntity.backupIsStub, paramMember, hookEntity.backup, paramObject, paramVarArgs);
  }
  
  public static final Object callOriginMethod(Member paramMember, Method paramMethod, Object paramObject, Object[] paramArrayOfObject) throws Throwable {
    return callOriginMethod(true, paramMember, paramMethod, paramObject, paramArrayOfObject);
  }
  
  public static final Object callOriginMethod(boolean paramBoolean, Member paramMember, Method paramMethod, Object paramObject, Object[] paramArrayOfObject) throws Throwable {
    if (!paramBoolean && SandHookConfig.SDK_INT >= 24) {
      paramMember.getDeclaringClass();
      ensureDeclareClass(paramMember, paramMethod);
    } 
    if (Modifier.isStatic(paramMember.getModifiers()))
      try {
        return paramMethod.invoke(null, paramArrayOfObject);
      } catch (InvocationTargetException invocationTargetException) {
        if (invocationTargetException.getCause() != null)
          throw invocationTargetException.getCause(); 
        throw invocationTargetException;
      }  
    try {
      return paramMethod.invoke(paramObject, paramArrayOfObject);
    } catch (InvocationTargetException invocationTargetException) {
      if (invocationTargetException.getCause() != null)
        throw invocationTargetException.getCause(); 
      throw invocationTargetException;
    } 
  }
  
  public static native boolean canGetObject();
  
  public static boolean canGetObjectAddress() {
    return Unsafe.support();
  }
  
  public static native boolean compileMethod(Member paramMember);
  
  public static native boolean deCompileMethod(Member paramMember, boolean paramBoolean);
  
  public static native boolean disableDex2oatInline(boolean paramBoolean);
  
  public static native boolean disableVMInline();
  
  public static final void ensureBackupMethod(Method paramMethod) {
    if (SandHookConfig.SDK_INT < 24)
      return; 
    HookWrapper.HookEntity hookEntity = globalBackupMap.get(paramMethod);
    if (hookEntity != null)
      ensureDeclareClass(hookEntity.target, paramMethod); 
  }
  
  public static native void ensureDeclareClass(Member paramMember, Method paramMethod);
  
  public static native void ensureMethodCached(Method paramMethod1, Method paramMethod2);
  
  public static long getArtMethod(Member paramMember) {
    return SandHookMethodResolver.getArtMethod(paramMember);
  }
  
  private static Object[] getFakeArgs(Method paramMethod) {
    Class[] arrayOfClass = paramMethod.getParameterTypes();
    return (arrayOfClass == null || arrayOfClass.length == 0) ? new Object[] { new Object() } : null;
  }
  
  public static Field getField(Class<Object> paramClass, String paramString) throws NoSuchFieldException {
    while (paramClass != null && paramClass != Object.class) {
      try {
        Field field = paramClass.getDeclaredField(paramString);
        field.setAccessible(true);
        return field;
      } catch (Exception exception) {
        paramClass = (Class)paramClass.getSuperclass();
      } 
    } 
    throw new NoSuchFieldException(paramString);
  }
  
  public static Object getJavaMethod(String paramString1, String paramString2) {
    if (paramString1 == null)
      return null; 
    try {
      Class<?> clazz = Class.forName(paramString1);
      return clazz.getDeclaredMethod(paramString2, new Class[0]);
    } catch (ClassNotFoundException classNotFoundException) {
      return null;
    } 
  }
  
  public static Object getObject(long paramLong) {
    return (paramLong == 0L) ? null : getObjectNative(getThreadId(), paramLong);
  }
  
  public static long getObjectAddress(Object paramObject) {
    return Unsafe.getObjectAddress(paramObject);
  }
  
  public static native Object getObjectNative(long paramLong1, long paramLong2);
  
  public static long getThreadId() {
    Field field = nativePeerField;
    if (field == null)
      return 0L; 
    try {
      return (field.getType() == int.class) ? nativePeerField.getInt(Thread.currentThread()) : nativePeerField.getLong(Thread.currentThread());
    } catch (IllegalAccessException illegalAccessException) {
      return 0L;
    } 
  }
  
  public static boolean hasJavaArtMethod() {
    if (SandHookConfig.SDK_INT >= 26)
      return false; 
    if (artMethodClass != null)
      return true; 
    try {
      ClassLoader classLoader = SandHookConfig.initClassLoader;
      if (classLoader == null) {
        artMethodClass = Class.forName("java.lang.reflect.ArtMethod");
      } else {
        artMethodClass = Class.forName("java.lang.reflect.ArtMethod", true, SandHookConfig.initClassLoader);
      } 
      return true;
    } catch (ClassNotFoundException classNotFoundException) {
      return false;
    } 
  }
  
  public static void hook(HookWrapper.HookEntity paramHookEntity) throws HookErrorException {
    // Byte code:
    //   0: ldc com/swift/sandhook/SandHook
    //   2: monitorenter
    //   3: aload_0
    //   4: ifnull -> 572
    //   7: aload_0
    //   8: getfield target : Ljava/lang/reflect/Member;
    //   11: astore_1
    //   12: aload_0
    //   13: getfield hook : Ljava/lang/reflect/Method;
    //   16: astore_2
    //   17: aload_0
    //   18: getfield backup : Ljava/lang/reflect/Method;
    //   21: astore_3
    //   22: aload_1
    //   23: ifnull -> 555
    //   26: aload_2
    //   27: ifnull -> 555
    //   30: getstatic com/swift/sandhook/SandHook.globalHookEntityMap : Ljava/util/Map;
    //   33: aload_0
    //   34: getfield target : Ljava/lang/reflect/Member;
    //   37: invokeinterface containsKey : (Ljava/lang/Object;)Z
    //   42: ifne -> 505
    //   45: aload_1
    //   46: invokestatic canNotHook : (Ljava/lang/reflect/Member;)Z
    //   49: ifne -> 455
    //   52: getstatic com/swift/sandhook/SandHookConfig.delayHook : Z
    //   55: ifeq -> 82
    //   58: invokestatic canWork : ()Z
    //   61: ifeq -> 82
    //   64: aload_0
    //   65: getfield target : Ljava/lang/reflect/Member;
    //   68: invokestatic isStaticAndNoInited : (Ljava/lang/reflect/Member;)Z
    //   71: ifeq -> 82
    //   74: aload_0
    //   75: invokestatic addPendingHook : (Lcom/swift/sandhook/wrapper/HookWrapper$HookEntity;)V
    //   78: ldc com/swift/sandhook/SandHook
    //   80: monitorexit
    //   81: return
    //   82: aload_0
    //   83: getfield initClass : Z
    //   86: ifeq -> 100
    //   89: aload_1
    //   90: invokestatic resolveStaticMethod : (Ljava/lang/reflect/Member;)Z
    //   93: pop
    //   94: invokestatic getThreadId : ()J
    //   97: invokestatic MakeInitializedClassVisibilyInitialized : (J)V
    //   100: aload_3
    //   101: invokestatic resolveStaticMethod : (Ljava/lang/reflect/Member;)Z
    //   104: pop
    //   105: aload_3
    //   106: ifnull -> 121
    //   109: aload_0
    //   110: getfield resolveDexCache : Z
    //   113: ifeq -> 121
    //   116: aload_2
    //   117: aload_3
    //   118: invokestatic resolveMethod : (Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V
    //   121: aload_1
    //   122: instanceof java/lang/reflect/Method
    //   125: ifeq -> 136
    //   128: aload_1
    //   129: checkcast java/lang/reflect/Method
    //   132: iconst_1
    //   133: invokevirtual setAccessible : (Z)V
    //   136: getstatic com/swift/sandhook/SandHook.hookModeCallBack : Lcom/swift/sandhook/SandHook$HookModeCallBack;
    //   139: astore #4
    //   141: iconst_0
    //   142: istore #5
    //   144: aload #4
    //   146: ifnull -> 163
    //   149: getstatic com/swift/sandhook/SandHook.hookModeCallBack : Lcom/swift/sandhook/SandHook$HookModeCallBack;
    //   152: aload_1
    //   153: invokeinterface hookMode : (Ljava/lang/reflect/Member;)I
    //   158: istore #6
    //   160: goto -> 166
    //   163: iconst_0
    //   164: istore #6
    //   166: getstatic com/swift/sandhook/SandHook.globalHookEntityMap : Ljava/util/Map;
    //   169: aload_0
    //   170: getfield target : Ljava/lang/reflect/Member;
    //   173: aload_0
    //   174: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   179: pop
    //   180: iload #6
    //   182: ifeq -> 198
    //   185: aload_1
    //   186: aload_2
    //   187: aload_3
    //   188: iload #6
    //   190: invokestatic hookMethod : (Ljava/lang/reflect/Member;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;I)I
    //   193: istore #6
    //   195: goto -> 240
    //   198: aload_2
    //   199: ldc_w com/swift/sandhook/annotation/HookMode
    //   202: invokevirtual getAnnotation : (Ljava/lang/Class;)Ljava/lang/annotation/Annotation;
    //   205: checkcast com/swift/sandhook/annotation/HookMode
    //   208: astore #4
    //   210: aload #4
    //   212: ifnonnull -> 221
    //   215: iconst_0
    //   216: istore #6
    //   218: goto -> 230
    //   221: aload #4
    //   223: invokeinterface value : ()I
    //   228: istore #6
    //   230: aload_1
    //   231: aload_2
    //   232: aload_3
    //   233: iload #6
    //   235: invokestatic hookMethod : (Ljava/lang/reflect/Member;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;I)I
    //   238: istore #6
    //   240: iload #6
    //   242: ifle -> 254
    //   245: aload_3
    //   246: ifnull -> 254
    //   249: aload_3
    //   250: iconst_1
    //   251: invokevirtual setAccessible : (Z)V
    //   254: aload_0
    //   255: iload #6
    //   257: putfield hookMode : I
    //   260: getstatic com/swift/sandhook/SandHook.hookResultCallBack : Lcom/swift/sandhook/SandHook$HookResultCallBack;
    //   263: ifnull -> 287
    //   266: getstatic com/swift/sandhook/SandHook.hookResultCallBack : Lcom/swift/sandhook/SandHook$HookResultCallBack;
    //   269: astore_1
    //   270: iload #6
    //   272: ifle -> 278
    //   275: iconst_1
    //   276: istore #5
    //   278: aload_1
    //   279: iload #5
    //   281: aload_0
    //   282: invokeinterface hookResult : (ZLcom/swift/sandhook/wrapper/HookWrapper$HookEntity;)V
    //   287: iload #6
    //   289: iflt -> 392
    //   292: aload_0
    //   293: getfield backup : Ljava/lang/reflect/Method;
    //   296: ifnull -> 313
    //   299: getstatic com/swift/sandhook/SandHook.globalBackupMap : Ljava/util/Map;
    //   302: aload_0
    //   303: getfield backup : Ljava/lang/reflect/Method;
    //   306: aload_0
    //   307: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   312: pop
    //   313: new java/lang/StringBuilder
    //   316: astore_1
    //   317: aload_1
    //   318: invokespecial <init> : ()V
    //   321: aload_1
    //   322: ldc_w 'method <'
    //   325: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   328: pop
    //   329: aload_1
    //   330: aload_0
    //   331: getfield target : Ljava/lang/reflect/Member;
    //   334: invokevirtual toString : ()Ljava/lang/String;
    //   337: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   340: pop
    //   341: aload_1
    //   342: ldc_w '> hook <'
    //   345: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   348: pop
    //   349: iload #6
    //   351: iconst_1
    //   352: if_icmpne -> 362
    //   355: ldc_w 'inline'
    //   358: astore_0
    //   359: goto -> 366
    //   362: ldc_w 'replacement'
    //   365: astore_0
    //   366: aload_1
    //   367: aload_0
    //   368: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   371: pop
    //   372: aload_1
    //   373: ldc_w '> success!'
    //   376: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   379: pop
    //   380: aload_1
    //   381: invokevirtual toString : ()Ljava/lang/String;
    //   384: invokestatic d : (Ljava/lang/String;)I
    //   387: pop
    //   388: ldc com/swift/sandhook/SandHook
    //   390: monitorexit
    //   391: return
    //   392: getstatic com/swift/sandhook/SandHook.globalHookEntityMap : Ljava/util/Map;
    //   395: aload_0
    //   396: getfield target : Ljava/lang/reflect/Member;
    //   399: invokeinterface remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   404: pop
    //   405: new com/swift/sandhook/wrapper/HookErrorException
    //   408: astore_2
    //   409: new java/lang/StringBuilder
    //   412: astore_1
    //   413: aload_1
    //   414: invokespecial <init> : ()V
    //   417: aload_1
    //   418: ldc_w 'hook method <'
    //   421: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   424: pop
    //   425: aload_1
    //   426: aload_0
    //   427: getfield target : Ljava/lang/reflect/Member;
    //   430: invokevirtual toString : ()Ljava/lang/String;
    //   433: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   436: pop
    //   437: aload_1
    //   438: ldc_w '> error in native!'
    //   441: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   444: pop
    //   445: aload_2
    //   446: aload_1
    //   447: invokevirtual toString : ()Ljava/lang/String;
    //   450: invokespecial <init> : (Ljava/lang/String;)V
    //   453: aload_2
    //   454: athrow
    //   455: new com/swift/sandhook/wrapper/HookErrorException
    //   458: astore_1
    //   459: new java/lang/StringBuilder
    //   462: astore_2
    //   463: aload_2
    //   464: invokespecial <init> : ()V
    //   467: aload_2
    //   468: ldc_w 'method <'
    //   471: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   474: pop
    //   475: aload_2
    //   476: aload_0
    //   477: getfield target : Ljava/lang/reflect/Member;
    //   480: invokevirtual toString : ()Ljava/lang/String;
    //   483: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   486: pop
    //   487: aload_2
    //   488: ldc_w '> can not hook, because of in blacklist!'
    //   491: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   494: pop
    //   495: aload_1
    //   496: aload_2
    //   497: invokevirtual toString : ()Ljava/lang/String;
    //   500: invokespecial <init> : (Ljava/lang/String;)V
    //   503: aload_1
    //   504: athrow
    //   505: new com/swift/sandhook/wrapper/HookErrorException
    //   508: astore_2
    //   509: new java/lang/StringBuilder
    //   512: astore_1
    //   513: aload_1
    //   514: invokespecial <init> : ()V
    //   517: aload_1
    //   518: ldc_w 'method <'
    //   521: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   524: pop
    //   525: aload_1
    //   526: aload_0
    //   527: getfield target : Ljava/lang/reflect/Member;
    //   530: invokevirtual toString : ()Ljava/lang/String;
    //   533: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   536: pop
    //   537: aload_1
    //   538: ldc_w '> has been hooked!'
    //   541: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   544: pop
    //   545: aload_2
    //   546: aload_1
    //   547: invokevirtual toString : ()Ljava/lang/String;
    //   550: invokespecial <init> : (Ljava/lang/String;)V
    //   553: aload_2
    //   554: athrow
    //   555: new com/swift/sandhook/wrapper/HookErrorException
    //   558: astore_0
    //   559: aload_0
    //   560: ldc_w 'null input'
    //   563: invokespecial <init> : (Ljava/lang/String;)V
    //   566: aload_0
    //   567: athrow
    //   568: astore_0
    //   569: goto -> 585
    //   572: new com/swift/sandhook/wrapper/HookErrorException
    //   575: astore_0
    //   576: aload_0
    //   577: ldc_w 'null hook entity'
    //   580: invokespecial <init> : (Ljava/lang/String;)V
    //   583: aload_0
    //   584: athrow
    //   585: ldc com/swift/sandhook/SandHook
    //   587: monitorexit
    //   588: aload_0
    //   589: athrow
    // Exception table:
    //   from	to	target	type
    //   7	22	568	finally
    //   30	78	568	finally
    //   82	100	568	finally
    //   100	105	568	finally
    //   109	121	568	finally
    //   121	136	568	finally
    //   136	141	568	finally
    //   149	160	568	finally
    //   166	180	568	finally
    //   185	195	568	finally
    //   198	210	568	finally
    //   221	230	568	finally
    //   230	240	568	finally
    //   249	254	568	finally
    //   254	270	568	finally
    //   278	287	568	finally
    //   292	313	568	finally
    //   313	349	568	finally
    //   366	388	568	finally
    //   392	455	568	finally
    //   455	505	568	finally
    //   505	555	568	finally
    //   555	568	568	finally
    //   572	585	568	finally
  }
  
  private static native int hookMethod(Member paramMember, Method paramMethod1, Method paramMethod2, int paramInt);
  
  private static boolean init() {
    initTestOffset();
    initThreadPeer();
    SandHookMethodResolver.init();
    return initNative(SandHookConfig.SDK_INT, SandHookConfig.DEBUG);
  }
  
  public static native boolean initForPendingHook();
  
  private static native boolean initNative(int paramInt, boolean paramBoolean);
  
  private static void initTestAccessFlag() {
    if (hasJavaArtMethod()) {
      try {
        loadArtMethod();
        testAccessFlag = ((Integer)getField(artMethodClass, "accessFlags").get(testOffsetArtMethod1)).intValue();
      } catch (Exception exception) {}
    } else {
      testAccessFlag = ((Integer)getField(Method.class, "accessFlags").get(testOffsetMethod1)).intValue();
    } 
  }
  
  private static void initTestOffset() {
    ArtMethodSizeTest.method1();
    ArtMethodSizeTest.method2();
    try {
      testOffsetMethod1 = ArtMethodSizeTest.class.getDeclaredMethod("method1", new Class[0]);
      testOffsetMethod2 = ArtMethodSizeTest.class.getDeclaredMethod("method2", new Class[0]);
      initTestAccessFlag();
      return;
    } catch (NoSuchMethodException noSuchMethodException) {
      throw new RuntimeException("SandHook init error", noSuchMethodException);
    } 
  }
  
  private static void initThreadPeer() {
    try {
      nativePeerField = getField(Thread.class, "nativePeer");
    } catch (NoSuchFieldException noSuchFieldException) {}
  }
  
  public static native boolean is64Bit();
  
  private static void loadArtMethod() {
    try {
      Field field = getField(Method.class, "artMethod");
      testOffsetArtMethod1 = field.get(testOffsetMethod1);
      testOffsetArtMethod2 = field.get(testOffsetMethod2);
    } catch (IllegalAccessException illegalAccessException) {
      illegalAccessException.printStackTrace();
    } catch (NoSuchFieldException noSuchFieldException) {
      noSuchFieldException.printStackTrace();
    } 
  }
  
  public static boolean passApiCheck() {
    return ReflectionUtils.passApiCheck();
  }
  
  public static boolean resolveStaticMethod(Member paramMember) {
    if (paramMember == null)
      return true; 
    try {
      if (paramMember instanceof Method && Modifier.isStatic(paramMember.getModifiers())) {
        ((Method)paramMember).setAccessible(true);
        Method method = (Method)paramMember;
        Object object = new Object();
        this();
        method.invoke(object, getFakeArgs((Method)paramMember));
      } 
    } catch (ExceptionInInitializerError exceptionInInitializerError) {
      return false;
    } finally {}
    return true;
  }
  
  public static native void setHookMode(int paramInt);
  
  public static void setHookModeCallBack(HookModeCallBack paramHookModeCallBack) {
    hookModeCallBack = paramHookModeCallBack;
  }
  
  public static void setHookResultCallBack(HookResultCallBack paramHookResultCallBack) {
    hookResultCallBack = paramHookResultCallBack;
  }
  
  public static native void setInlineSafeCheck(boolean paramBoolean);
  
  public static native boolean setNativeEntry(Member paramMember1, Member paramMember2, long paramLong);
  
  public static native void skipAllSafeCheck(boolean paramBoolean);
  
  public static boolean tryDisableProfile(String paramString) {
    if (SandHookConfig.SDK_INT < 24)
      return false; 
    try {
      File file = new File();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("/data/misc/profiles/cur/");
      stringBuilder.append(SandHookConfig.curUser);
      stringBuilder.append("/");
      stringBuilder.append(paramString);
      stringBuilder.append("/primary.prof");
      this(stringBuilder.toString());
      boolean bool = file.getParentFile().exists();
      if (!bool)
        return false; 
    } finally {
      paramString = null;
    } 
  }
  
  @FunctionalInterface
  public static interface HookModeCallBack {
    int hookMode(Member param1Member);
  }
  
  @FunctionalInterface
  public static interface HookResultCallBack {
    void hookResult(boolean param1Boolean, HookWrapper.HookEntity param1HookEntity);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\SandHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */