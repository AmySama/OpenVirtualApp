package com.swift.sandhook.xposedcompat.hookstub;

import android.util.Log;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.SandHookMethodResolver;
import com.swift.sandhook.xposedcompat.XposedCompat;
import com.swift.sandhook.xposedcompat.utils.DexLog;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

public class HookStubManager {
  static {
    if (is64Bit) {
      Class<MethodHookerStubs64> clazz1 = MethodHookerStubs64.class;
    } else {
      clazz = MethodHookerStubs32.class;
    } 
    stubSizes = (int[])XposedHelpers.getStaticObjectField(clazz, "stubSizes");
    Boolean bool = (Boolean)XposedHelpers.getStaticObjectField(clazz, "hasStubBackup");
    if (bool != null && bool.booleanValue() && !XposedCompat.useNewCallBackup) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    hasStubBackup = bool1;
    int[] arrayOfInt = stubSizes;
    if (arrayOfInt != null && arrayOfInt.length > 0) {
      int i = arrayOfInt.length - 1;
      MAX_STUB_ARGS = i;
      curUseStubIndexes = new AtomicInteger[i + 1];
      for (i = 0; i < MAX_STUB_ARGS + 1; i++) {
        curUseStubIndexes[i] = new AtomicInteger(0);
        ALL_STUB += stubSizes[i];
      } 
      i = ALL_STUB;
      originMethods = new Member[i];
      hookMethodEntities = new HookMethodEntity[i];
      additionalHookInfos = new XposedBridge.AdditionalHookInfo[i];
    } 
  }
  
  public static final long callOrigin(HookMethodEntity paramHookMethodEntity, Member paramMember, Object paramObject, Object[] paramArrayOfObject) throws Throwable {
    return paramHookMethodEntity.getResultAddress(SandHook.callOriginMethod(paramMember, paramHookMethodEntity.backup, paramObject, paramArrayOfObject));
  }
  
  public static String getBackupMethodName(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("stub_backup_");
    stringBuilder.append(paramInt);
    return stringBuilder.toString();
  }
  
  public static String getCallOriginClassName(int paramInt1, int paramInt2) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("call_origin_");
    stringBuilder.append(paramInt1);
    stringBuilder.append("_");
    stringBuilder.append(paramInt2);
    return stringBuilder.toString();
  }
  
  public static Method getCallOriginMethod(int paramInt1, int paramInt2) {
    Class<MethodHookerStubs32> clazz;
    if (is64Bit) {
      Class<MethodHookerStubs64> clazz1 = MethodHookerStubs64.class;
    } else {
      clazz = MethodHookerStubs32.class;
    } 
    String str1 = clazz.getName();
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(str1);
    stringBuilder2.append("$");
    String str2 = stringBuilder2.toString();
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(str2);
    stringBuilder1.append(getCallOriginClassName(paramInt1, paramInt2));
    str2 = stringBuilder1.toString();
    try {
      return Class.forName(str2, true, clazz.getClassLoader()).getDeclaredMethod("call", new Class[] { long[].class });
    } finally {
      clazz = null;
      Log.e("HookStubManager", "load call origin class error!", (Throwable)clazz);
    } 
  }
  
  public static Class[] getFindMethodParTypes(boolean paramBoolean, int paramInt) {
    if (paramInt == 0)
      return null; 
    Class[] arrayOfClass = new Class[paramInt];
    byte b1 = 0;
    byte b2 = 0;
    if (paramBoolean) {
      for (b1 = b2; b1 < paramInt; b1++)
        arrayOfClass[b1] = long.class; 
    } else {
      while (b1 < paramInt) {
        arrayOfClass[b1] = int.class;
        b1++;
      } 
    } 
    return arrayOfClass;
  }
  
  public static HookMethodEntity getHookMethodEntity(Member paramMember, XposedBridge.AdditionalHookInfo paramAdditionalHookInfo) {
    // Byte code:
    //   0: invokestatic support : ()Z
    //   3: ifne -> 8
    //   6: aconst_null
    //   7: areturn
    //   8: aload_0
    //   9: invokeinterface getModifiers : ()I
    //   14: invokestatic isStatic : (I)Z
    //   17: istore_2
    //   18: aload_0
    //   19: instanceof java/lang/reflect/Method
    //   22: ifeq -> 44
    //   25: aload_0
    //   26: checkcast java/lang/reflect/Method
    //   29: astore_3
    //   30: aload_3
    //   31: invokevirtual getReturnType : ()Ljava/lang/Class;
    //   34: astore #4
    //   36: aload_3
    //   37: invokevirtual getParameterTypes : ()[Ljava/lang/Class;
    //   40: astore_3
    //   41: goto -> 66
    //   44: aload_0
    //   45: instanceof java/lang/reflect/Constructor
    //   48: ifeq -> 336
    //   51: aload_0
    //   52: checkcast java/lang/reflect/Constructor
    //   55: astore_3
    //   56: getstatic java/lang/Void.TYPE : Ljava/lang/Class;
    //   59: astore #4
    //   61: aload_3
    //   62: invokevirtual getParameterTypes : ()[Ljava/lang/Class;
    //   65: astore_3
    //   66: aload #4
    //   68: invokestatic support : (Ljava/lang/Class;)Z
    //   71: ifne -> 76
    //   74: aconst_null
    //   75: areturn
    //   76: iload_2
    //   77: iconst_1
    //   78: ixor
    //   79: istore #5
    //   81: iconst_0
    //   82: istore #6
    //   84: aload_3
    //   85: ifnull -> 156
    //   88: iload #5
    //   90: aload_3
    //   91: arraylength
    //   92: iadd
    //   93: istore #7
    //   95: iload #7
    //   97: getstatic com/swift/sandhook/xposedcompat/hookstub/HookStubManager.MAX_STUB_ARGS : I
    //   100: if_icmple -> 105
    //   103: aconst_null
    //   104: areturn
    //   105: getstatic com/swift/sandhook/xposedcompat/hookstub/HookStubManager.is64Bit : Z
    //   108: ifeq -> 120
    //   111: iload #7
    //   113: bipush #7
    //   115: if_icmple -> 120
    //   118: aconst_null
    //   119: areturn
    //   120: aload_3
    //   121: arraylength
    //   122: istore #8
    //   124: iload #7
    //   126: istore #5
    //   128: aload_3
    //   129: astore #9
    //   131: iload #6
    //   133: iload #8
    //   135: if_icmpge -> 162
    //   138: aload_3
    //   139: iload #6
    //   141: aaload
    //   142: invokestatic support : (Ljava/lang/Class;)Z
    //   145: ifne -> 150
    //   148: aconst_null
    //   149: areturn
    //   150: iinc #6, 1
    //   153: goto -> 124
    //   156: iconst_0
    //   157: anewarray java/lang/Class
    //   160: astore #9
    //   162: ldc com/swift/sandhook/xposedcompat/hookstub/HookStubManager
    //   164: monitorenter
    //   165: getstatic com/swift/sandhook/xposedcompat/hookstub/HookStubManager.is64Bit : Z
    //   168: iload #5
    //   170: invokestatic getStubMethodPair : (ZI)Lcom/swift/sandhook/xposedcompat/hookstub/HookStubManager$StubMethodsInfo;
    //   173: astore #10
    //   175: aload #10
    //   177: ifnonnull -> 185
    //   180: ldc com/swift/sandhook/xposedcompat/hookstub/HookStubManager
    //   182: monitorexit
    //   183: aconst_null
    //   184: areturn
    //   185: new com/swift/sandhook/xposedcompat/hookstub/HookMethodEntity
    //   188: astore_3
    //   189: aload_3
    //   190: aload_0
    //   191: aload #10
    //   193: getfield hook : Ljava/lang/reflect/Method;
    //   196: aload #10
    //   198: getfield backup : Ljava/lang/reflect/Method;
    //   201: invokespecial <init> : (Ljava/lang/reflect/Member;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V
    //   204: aload_3
    //   205: aload #4
    //   207: putfield retType : Ljava/lang/Class;
    //   210: aload_3
    //   211: aload #9
    //   213: putfield parType : [Ljava/lang/Class;
    //   216: getstatic com/swift/sandhook/xposedcompat/hookstub/HookStubManager.hasStubBackup : Z
    //   219: ifeq -> 289
    //   222: aload_3
    //   223: getfield backup : Ljava/lang/reflect/Method;
    //   226: aload #10
    //   228: getfield args : I
    //   231: aload #10
    //   233: getfield index : I
    //   236: invokestatic tryCompileAndResolveCallOriginMethod : (Ljava/lang/reflect/Method;II)Z
    //   239: ifne -> 289
    //   242: new java/lang/StringBuilder
    //   245: astore_0
    //   246: aload_0
    //   247: invokespecial <init> : ()V
    //   250: aload_0
    //   251: ldc 'internal stub <'
    //   253: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   256: pop
    //   257: aload_0
    //   258: aload_3
    //   259: getfield hook : Ljava/lang/reflect/Method;
    //   262: invokevirtual getName : ()Ljava/lang/String;
    //   265: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   268: pop
    //   269: aload_0
    //   270: ldc '> call origin compile failure, skip use internal stub'
    //   272: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   275: pop
    //   276: aload_0
    //   277: invokevirtual toString : ()Ljava/lang/String;
    //   280: invokestatic w : (Ljava/lang/String;)I
    //   283: pop
    //   284: ldc com/swift/sandhook/xposedcompat/hookstub/HookStubManager
    //   286: monitorexit
    //   287: aconst_null
    //   288: areturn
    //   289: aload #10
    //   291: getfield args : I
    //   294: aload #10
    //   296: getfield index : I
    //   299: invokestatic getMethodId : (II)I
    //   302: istore #6
    //   304: getstatic com/swift/sandhook/xposedcompat/hookstub/HookStubManager.originMethods : [Ljava/lang/reflect/Member;
    //   307: iload #6
    //   309: aload_0
    //   310: aastore
    //   311: getstatic com/swift/sandhook/xposedcompat/hookstub/HookStubManager.hookMethodEntities : [Lcom/swift/sandhook/xposedcompat/hookstub/HookMethodEntity;
    //   314: iload #6
    //   316: aload_3
    //   317: aastore
    //   318: getstatic com/swift/sandhook/xposedcompat/hookstub/HookStubManager.additionalHookInfos : [Lde/robv/android/xposed/XposedBridge$AdditionalHookInfo;
    //   321: iload #6
    //   323: aload_1
    //   324: aastore
    //   325: ldc com/swift/sandhook/xposedcompat/hookstub/HookStubManager
    //   327: monitorexit
    //   328: aload_3
    //   329: areturn
    //   330: astore_0
    //   331: ldc com/swift/sandhook/xposedcompat/hookstub/HookStubManager
    //   333: monitorexit
    //   334: aload_0
    //   335: athrow
    //   336: aconst_null
    //   337: areturn
    // Exception table:
    //   from	to	target	type
    //   165	175	330	finally
    //   180	183	330	finally
    //   185	287	330	finally
    //   289	328	330	finally
    //   331	334	330	finally
  }
  
  public static String getHookMethodName(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("stub_hook_");
    stringBuilder.append(paramInt);
    return stringBuilder.toString();
  }
  
  public static int getMatchStubArgsCount(int paramInt) {
    while (paramInt <= MAX_STUB_ARGS) {
      if (curUseStubIndexes[paramInt].get() < stubSizes[paramInt])
        return paramInt; 
      paramInt++;
    } 
    return -1;
  }
  
  public static int getMethodId(int paramInt1, int paramInt2) {
    boolean bool = false;
    int i = paramInt2;
    for (paramInt2 = bool; paramInt2 < paramInt1; paramInt2++)
      i += stubSizes[paramInt2]; 
    return i;
  }
  
  private static StubMethodsInfo getStubMethodPair(boolean paramBoolean, int paramInt) {
    // Byte code:
    //   0: ldc com/swift/sandhook/xposedcompat/hookstub/HookStubManager
    //   2: monitorenter
    //   3: iload_1
    //   4: invokestatic getMatchStubArgsCount : (I)I
    //   7: istore_2
    //   8: iload_2
    //   9: ifge -> 17
    //   12: ldc com/swift/sandhook/xposedcompat/hookstub/HookStubManager
    //   14: monitorexit
    //   15: aconst_null
    //   16: areturn
    //   17: getstatic com/swift/sandhook/xposedcompat/hookstub/HookStubManager.curUseStubIndexes : [Ljava/util/concurrent/atomic/AtomicInteger;
    //   20: iload_2
    //   21: aaload
    //   22: invokevirtual getAndIncrement : ()I
    //   25: istore_1
    //   26: iload_0
    //   27: iload_2
    //   28: invokestatic getFindMethodParTypes : (ZI)[Ljava/lang/Class;
    //   31: astore_3
    //   32: iload_0
    //   33: ifeq -> 107
    //   36: ldc com/swift/sandhook/xposedcompat/hookstub/MethodHookerStubs64
    //   38: iload_1
    //   39: invokestatic getHookMethodName : (I)Ljava/lang/String;
    //   42: aload_3
    //   43: invokevirtual getDeclaredMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   46: astore #4
    //   48: getstatic com/swift/sandhook/xposedcompat/hookstub/HookStubManager.hasStubBackup : Z
    //   51: ifeq -> 68
    //   54: ldc com/swift/sandhook/xposedcompat/hookstub/MethodHookerStubs64
    //   56: iload_1
    //   57: invokestatic getBackupMethodName : (I)Ljava/lang/String;
    //   60: aload_3
    //   61: invokevirtual getDeclaredMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   64: astore_3
    //   65: goto -> 72
    //   68: invokestatic getStubMethod : ()Ljava/lang/reflect/Method;
    //   71: astore_3
    //   72: aload #4
    //   74: ifnull -> 102
    //   77: aload_3
    //   78: ifnonnull -> 84
    //   81: goto -> 102
    //   84: new com/swift/sandhook/xposedcompat/hookstub/HookStubManager$StubMethodsInfo
    //   87: dup
    //   88: iload_2
    //   89: iload_1
    //   90: aload #4
    //   92: aload_3
    //   93: invokespecial <init> : (IILjava/lang/reflect/Method;Ljava/lang/reflect/Method;)V
    //   96: astore_3
    //   97: ldc com/swift/sandhook/xposedcompat/hookstub/HookStubManager
    //   99: monitorexit
    //   100: aload_3
    //   101: areturn
    //   102: ldc com/swift/sandhook/xposedcompat/hookstub/HookStubManager
    //   104: monitorexit
    //   105: aconst_null
    //   106: areturn
    //   107: ldc com/swift/sandhook/xposedcompat/hookstub/MethodHookerStubs32
    //   109: iload_1
    //   110: invokestatic getHookMethodName : (I)Ljava/lang/String;
    //   113: aload_3
    //   114: invokevirtual getDeclaredMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   117: astore #4
    //   119: getstatic com/swift/sandhook/xposedcompat/hookstub/HookStubManager.hasStubBackup : Z
    //   122: ifeq -> 139
    //   125: ldc com/swift/sandhook/xposedcompat/hookstub/MethodHookerStubs32
    //   127: iload_1
    //   128: invokestatic getBackupMethodName : (I)Ljava/lang/String;
    //   131: aload_3
    //   132: invokevirtual getDeclaredMethod : (Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
    //   135: astore_3
    //   136: goto -> 143
    //   139: invokestatic getStubMethod : ()Ljava/lang/reflect/Method;
    //   142: astore_3
    //   143: aload #4
    //   145: ifnull -> 173
    //   148: aload_3
    //   149: ifnonnull -> 155
    //   152: goto -> 173
    //   155: new com/swift/sandhook/xposedcompat/hookstub/HookStubManager$StubMethodsInfo
    //   158: dup
    //   159: iload_2
    //   160: iload_1
    //   161: aload #4
    //   163: aload_3
    //   164: invokespecial <init> : (IILjava/lang/reflect/Method;Ljava/lang/reflect/Method;)V
    //   167: astore_3
    //   168: ldc com/swift/sandhook/xposedcompat/hookstub/HookStubManager
    //   170: monitorexit
    //   171: aload_3
    //   172: areturn
    //   173: ldc com/swift/sandhook/xposedcompat/hookstub/HookStubManager
    //   175: monitorexit
    //   176: aconst_null
    //   177: areturn
    //   178: astore_3
    //   179: ldc com/swift/sandhook/xposedcompat/hookstub/HookStubManager
    //   181: monitorexit
    //   182: aconst_null
    //   183: areturn
    //   184: astore_3
    //   185: ldc com/swift/sandhook/xposedcompat/hookstub/HookStubManager
    //   187: monitorexit
    //   188: aload_3
    //   189: athrow
    // Exception table:
    //   from	to	target	type
    //   3	8	184	finally
    //   17	32	184	finally
    //   36	65	178	finally
    //   68	72	178	finally
    //   84	97	178	finally
    //   107	136	178	finally
    //   139	143	178	finally
    //   155	168	178	finally
  }
  
  private static boolean hasArgs(long... paramVarArgs) {
    boolean bool;
    if (paramVarArgs != null && paramVarArgs.length > 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static long hookBridge(int paramInt, CallOriginCallBack paramCallOriginCallBack, long... paramVarArgs) throws Throwable {
    Object[] arrayOfObject1;
    Object[] arrayOfObject2;
    int i;
    Member member = originMethods[paramInt];
    HookMethodEntity hookMethodEntity = hookMethodEntities[paramInt];
    if (hasArgs(paramVarArgs)) {
      arrayOfObject1 = (Object[])hookMethodEntity.getThis(paramVarArgs[0]);
      arrayOfObject2 = hookMethodEntity.getArgs(paramVarArgs);
    } else {
      arrayOfObject1 = null;
      arrayOfObject2 = arrayOfObject1;
    } 
    if (XposedBridge.disableHooks)
      return hasStubBackup ? paramCallOriginCallBack.call(paramVarArgs) : callOrigin(hookMethodEntity, member, arrayOfObject1, arrayOfObject2); 
    DexLog.printMethodHookIn(member);
    Object[] arrayOfObject3 = (additionalHookInfos[paramInt]).callbacks.getSnapshot();
    if (arrayOfObject3 == null || arrayOfObject3.length == 0)
      return hasStubBackup ? paramCallOriginCallBack.call(paramVarArgs) : callOrigin(hookMethodEntity, member, arrayOfObject1, arrayOfObject2); 
    XC_MethodHook.MethodHookParam methodHookParam = new XC_MethodHook.MethodHookParam();
    methodHookParam.method = member;
    methodHookParam.thisObject = arrayOfObject1;
    methodHookParam.args = arrayOfObject2;
    paramInt = 0;
    do {
      try {
      
      } finally {
        arrayOfObject2 = null;
        methodHookParam.setResult(null);
      } 
      i = paramInt + 1;
      paramInt = i;
    } while (i < arrayOfObject3.length);
    if (!methodHookParam.returnEarly)
      try {
      
      } finally {
        paramCallOriginCallBack = null;
        XposedBridge.log((Throwable)paramCallOriginCallBack);
      }  
    paramInt = i - 1;
    while (true) {
      Object object = methodHookParam.getResult();
      Throwable throwable = methodHookParam.getThrowable();
      try {
        ((XC_MethodHook)arrayOfObject3[paramInt]).callAfterHookedMethod(methodHookParam);
      } finally {
        paramVarArgs = null;
        XposedBridge.log((Throwable)paramVarArgs);
      } 
      if (i < 0) {
        if (!methodHookParam.hasThrowable())
          return hookMethodEntity.getResultAddress(methodHookParam.getResult()); 
        throw methodHookParam.getThrowable();
      } 
    } 
  }
  
  public static Object hookBridge(Member paramMember, Method paramMethod, XposedBridge.AdditionalHookInfo paramAdditionalHookInfo, Object paramObject, Object... paramVarArgs) throws Throwable {
    int j;
    if (XposedBridge.disableHooks)
      return SandHook.callOriginMethod(paramMember, paramMethod, paramObject, paramVarArgs); 
    DexLog.printMethodHookIn(paramMember);
    Object[] arrayOfObject = paramAdditionalHookInfo.callbacks.getSnapshot();
    if (arrayOfObject == null || arrayOfObject.length == 0)
      return SandHook.callOriginMethod(paramMember, paramMethod, paramObject, paramVarArgs); 
    XC_MethodHook.MethodHookParam methodHookParam = new XC_MethodHook.MethodHookParam();
    methodHookParam.method = paramMember;
    methodHookParam.thisObject = paramObject;
    methodHookParam.args = paramVarArgs;
    int i = 0;
    do {
      try {
      
      } finally {
        paramVarArgs = null;
        methodHookParam.setResult(null);
      } 
      j = i + 1;
      i = j;
    } while (j < arrayOfObject.length);
    if (!methodHookParam.returnEarly)
      try {
        methodHookParam.setResult(SandHook.callOriginMethod(paramMember, paramMethod, paramObject, methodHookParam.args));
      } finally {
        paramMember = null;
        XposedBridge.log((Throwable)paramMember);
      }  
    j--;
    while (true) {
      paramObject = methodHookParam.getResult();
      Throwable throwable = methodHookParam.getThrowable();
      try {
        ((XC_MethodHook)arrayOfObject[j]).callAfterHookedMethod(methodHookParam);
      } finally {
        paramMember = null;
        XposedBridge.log((Throwable)paramMember);
      } 
      if (i < 0) {
        if (!methodHookParam.hasThrowable())
          return methodHookParam.getResult(); 
        throw methodHookParam.getThrowable();
      } 
    } 
  }
  
  public static boolean support() {
    boolean bool;
    if (MAX_STUB_ARGS > 0 && SandHook.canGetObject() && SandHook.canGetObjectAddress()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean tryCompileAndResolveCallOriginMethod(Method paramMethod, int paramInt1, int paramInt2) {
    Method method = getCallOriginMethod(paramInt1, paramInt2);
    if (method != null) {
      SandHookMethodResolver.resolveMethod(method, paramMethod);
      return SandHook.compileMethod(method);
    } 
    return false;
  }
  
  static {
    Class<MethodHookerStubs32> clazz;
    boolean bool1;
  }
  
  public static int ALL_STUB = 0;
  
  public static final int MAX_64_ARGS = 7;
  
  public static int MAX_STUB_ARGS;
  
  public static XposedBridge.AdditionalHookInfo[] additionalHookInfos;
  
  public static AtomicInteger[] curUseStubIndexes;
  
  public static boolean hasStubBackup;
  
  public static HookMethodEntity[] hookMethodEntities;
  
  public static volatile boolean is64Bit = SandHook.is64Bit();
  
  public static Member[] originMethods;
  
  public static int[] stubSizes;
  
  static class StubMethodsInfo {
    int args = 0;
    
    Method backup;
    
    Method hook;
    
    int index = 0;
    
    public StubMethodsInfo(int param1Int1, int param1Int2, Method param1Method1, Method param1Method2) {
      this.args = param1Int1;
      this.index = param1Int2;
      this.hook = param1Method1;
      this.backup = param1Method2;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\xposedcompat\hookstub\HookStubManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */