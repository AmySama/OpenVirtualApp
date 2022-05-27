package com.swift.sandhook.xposedcompat.methodgen;

import android.text.TextUtils;
import com.android.dx.BinaryOp;
import com.android.dx.Code;
import com.android.dx.Comparison;
import com.android.dx.DexMaker;
import com.android.dx.FieldId;
import com.android.dx.Label;
import com.android.dx.Local;
import com.android.dx.MethodId;
import com.android.dx.TypeId;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.SandHookConfig;
import com.swift.sandhook.SandHookMethodResolver;
import com.swift.sandhook.wrapper.HookWrapper;
import com.swift.sandhook.xposedcompat.XposedCompat;
import com.swift.sandhook.xposedcompat.utils.DexLog;
import com.swift.sandhook.xposedcompat.utils.DexMakerUtils;
import dalvik.system.InMemoryDexClassLoader;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.Map;

public class HookerDexMaker implements HookMaker {
  private static final String CALLBACK_METHOD_NAME_AFTER = "callAfterHookedMethod";
  
  private static final String CALLBACK_METHOD_NAME_BEFORE = "callBeforeHookedMethod";
  
  private static final String CLASS_DESC_PREFIX = "L";
  
  private static final String CLASS_NAME_PREFIX = "SandHooker";
  
  private static final String FIELD_NAME_BACKUP_METHOD = "backupMethod";
  
  private static final String FIELD_NAME_HOOK_INFO = "additionalHookInfo";
  
  private static final String FIELD_NAME_METHOD = "method";
  
  public static final String METHOD_NAME_BACKUP = "backup";
  
  public static final String METHOD_NAME_CALL_BACKUP = "callBackup";
  
  public static final String METHOD_NAME_HOOK = "hook";
  
  public static final String METHOD_NAME_LOG = "printMethodHookIn";
  
  public static final String METHOD_NAME_SETUP = "setup";
  
  private static final String PARAMS_FIELD_NAME_ARGS = "args";
  
  private static final String PARAMS_FIELD_NAME_METHOD = "method";
  
  private static final String PARAMS_FIELD_NAME_THIS_OBJECT = "thisObject";
  
  private static final MethodId<XC_MethodHook, Void> callAfterCallbackMethodId;
  
  private static final MethodId<XC_MethodHook, Void> callBeforeCallbackMethodId;
  
  private static final TypeId<XC_MethodHook> callbackTypeId;
  
  private static final TypeId<XposedBridge.CopyOnWriteSortedSet> callbacksTypeId;
  
  private static final MethodId<XC_MethodHook.MethodHookParam, Object> getResultMethodId;
  
  private static final MethodId<XC_MethodHook.MethodHookParam, Throwable> getThrowableMethodId;
  
  private static final MethodId<XC_MethodHook.MethodHookParam, Boolean> hasThrowableMethodId;
  
  private static final TypeId<XposedBridge.AdditionalHookInfo> hookInfoTypeId;
  
  private static final MethodId<XposedBridge, Void> logThrowableMethodId;
  
  private static final TypeId<Member> memberTypeId;
  
  private static final TypeId<Method> methodTypeId;
  
  public static final TypeId<Object[]> objArrayTypeId = TypeId.get(Object[].class);
  
  private static final TypeId<XC_MethodHook.MethodHookParam> paramTypeId;
  
  private static final FieldId<XC_MethodHook.MethodHookParam, Boolean> returnEarlyFieldId;
  
  private static final MethodId<XC_MethodHook.MethodHookParam, Void> setResultMethodId;
  
  private static final MethodId<XC_MethodHook.MethodHookParam, Void> setThrowableMethodId;
  
  private static final TypeId<Throwable> throwableTypeId = TypeId.get(Throwable.class);
  
  private static final TypeId<XposedBridge> xposedBridgeTypeId;
  
  private Class<?>[] mActualParameterTypes;
  
  private ClassLoader mAppClassLoader;
  
  private Method mBackupMethod;
  
  private FieldId<?, Method> mBackupMethodFieldId;
  
  private MethodId<?, ?> mBackupMethodId;
  
  private Method mCallBackupMethod;
  
  private MethodId<?, ?> mCallBackupMethodId;
  
  private String mDexDirPath;
  
  private DexMaker mDexMaker;
  
  private boolean mHasThrowable;
  
  private Class<?> mHookClass;
  
  private XposedBridge.AdditionalHookInfo mHookInfo;
  
  private FieldId<?, XposedBridge.AdditionalHookInfo> mHookInfoFieldId;
  
  private Method mHookMethod;
  
  private MethodId<?, ?> mHookMethodId;
  
  private TypeId<?> mHookerTypeId;
  
  private boolean mIsStatic;
  
  private Member mMember;
  
  private FieldId<?, Member> mMethodFieldId;
  
  private TypeId<?>[] mParameterTypeIds;
  
  private MethodId<?, ?> mPrintLogMethodId;
  
  private Class<?> mReturnType;
  
  private TypeId<?> mReturnTypeId;
  
  private MethodId<?, ?> mSandHookCallOriginMethodId;
  
  static {
    memberTypeId = TypeId.get(Member.class);
    methodTypeId = TypeId.get(Method.class);
    callbackTypeId = TypeId.get(XC_MethodHook.class);
    hookInfoTypeId = TypeId.get(XposedBridge.AdditionalHookInfo.class);
    callbacksTypeId = TypeId.get(XposedBridge.CopyOnWriteSortedSet.class);
    TypeId<XC_MethodHook.MethodHookParam> typeId = TypeId.get(XC_MethodHook.MethodHookParam.class);
    paramTypeId = typeId;
    setResultMethodId = typeId.getMethod(TypeId.VOID, "setResult", new TypeId[] { TypeId.OBJECT });
    setThrowableMethodId = paramTypeId.getMethod(TypeId.VOID, "setThrowable", new TypeId[] { throwableTypeId });
    getResultMethodId = paramTypeId.getMethod(TypeId.OBJECT, "getResult", new TypeId[0]);
    getThrowableMethodId = paramTypeId.getMethod(throwableTypeId, "getThrowable", new TypeId[0]);
    hasThrowableMethodId = paramTypeId.getMethod(TypeId.BOOLEAN, "hasThrowable", new TypeId[0]);
    callAfterCallbackMethodId = callbackTypeId.getMethod(TypeId.VOID, "callAfterHookedMethod", new TypeId[] { paramTypeId });
    callBeforeCallbackMethodId = callbackTypeId.getMethod(TypeId.VOID, "callBeforeHookedMethod", new TypeId[] { paramTypeId });
    returnEarlyFieldId = paramTypeId.getField(TypeId.BOOLEAN, "returnEarly");
    typeId = TypeId.get(XposedBridge.class);
    xposedBridgeTypeId = (TypeId)typeId;
    logThrowableMethodId = typeId.getMethod(TypeId.VOID, "log", new TypeId[] { throwableTypeId });
  }
  
  private Local[] createParameterLocals(Code paramCode) {
    Local[] arrayOfLocal = new Local[this.mParameterTypeIds.length];
    byte b = 0;
    while (true) {
      TypeId<?>[] arrayOfTypeId = this.mParameterTypeIds;
      if (b < arrayOfTypeId.length) {
        arrayOfLocal[b] = paramCode.getParameter(b, arrayOfTypeId[b]);
        b++;
        continue;
      } 
      return arrayOfLocal;
    } 
  }
  
  private HookWrapper.HookEntity doMake(String paramString1, String paramString2) throws Exception {
    InMemoryDexClassLoader inMemoryDexClassLoader;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("L");
    stringBuilder1.append(paramString1);
    stringBuilder1.append(";");
    TypeId<?> typeId = TypeId.get(stringBuilder1.toString());
    this.mHookerTypeId = typeId;
    DexMaker dexMaker = this.mDexMaker;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString1);
    stringBuilder2.append(".generated");
    dexMaker.declare(typeId, stringBuilder2.toString(), 1, TypeId.OBJECT, new TypeId[0]);
    generateFields();
    generateSetupMethod();
    if (XposedCompat.retryWhenCallOriginError) {
      generateBackupAndCallOriginCheckMethod();
    } else {
      generateBackupMethod();
    } 
    generateCallBackupMethod();
    generateHookMethod();
    if (TextUtils.isEmpty(this.mDexDirPath)) {
      if (SandHookConfig.SDK_INT >= 26) {
        inMemoryDexClassLoader = new InMemoryDexClassLoader(ByteBuffer.wrap(this.mDexMaker.generate()), this.mAppClassLoader);
      } else {
        throw new IllegalArgumentException("dexDirPath should not be empty!!!");
      } 
    } else {
      try {
        dexMaker = this.mDexMaker;
        ClassLoader classLoader2 = this.mAppClassLoader;
        File file = new File();
        this(this.mDexDirPath);
        ClassLoader classLoader1 = dexMaker.generateAndLoad(classLoader2, file, (String)inMemoryDexClassLoader);
      } catch (IOException iOException) {
        if (SandHookConfig.SDK_INT >= 26) {
          InMemoryDexClassLoader inMemoryDexClassLoader1 = new InMemoryDexClassLoader(ByteBuffer.wrap(this.mDexMaker.generate()), this.mAppClassLoader);
        } else {
          iOException = null;
        } 
      } 
    } 
    return (iOException == null) ? null : loadHookerClass((ClassLoader)iOException, paramString1);
  }
  
  private void generateBackupAndCallOriginCheckMethod() {
    byte b1;
    this.mBackupMethodId = this.mHookerTypeId.getMethod(this.mReturnTypeId, "backup", (TypeId[])this.mParameterTypeIds);
    this.mSandHookCallOriginMethodId = TypeId.get(ErrorCatch.class).getMethod(TypeId.get(Object.class), "callOriginError", new TypeId[] { memberTypeId, methodTypeId, TypeId.get(Object.class), TypeId.get(Object[].class) });
    MethodId methodId = TypeId.get(DexLog.class).getMethod(TypeId.get(void.class), "printCallOriginError", new TypeId[] { methodTypeId });
    Code code = this.mDexMaker.declare(this.mBackupMethodId, 9);
    Local local1 = code.newLocal(memberTypeId);
    Local local2 = code.newLocal(methodTypeId);
    Local local3 = code.newLocal(TypeId.OBJECT);
    Local local4 = code.newLocal(objArrayTypeId);
    Local local5 = code.newLocal(TypeId.INT);
    Local local6 = code.newLocal(TypeId.INT);
    Local local7 = code.newLocal(TypeId.OBJECT);
    Label label = new Label();
    Local[] arrayOfLocal = createParameterLocals(code);
    Map map = DexMakerUtils.createResultLocals(code);
    code.addCatchClause(throwableTypeId, label);
    code.sget(this.mMethodFieldId, local1);
    code.invokeStatic(methodId, null, new Local[] { local1 });
    code.loadConstant(local4, null);
    code.loadConstant(local6, Integer.valueOf(0));
    code.sget(this.mBackupMethodFieldId, local2);
    int i = this.mParameterTypeIds.length;
    if (this.mIsStatic) {
      code.loadConstant(local3, null);
      b1 = 0;
    } else {
      code.move(local3, arrayOfLocal[0]);
      b1 = 1;
    } 
    code.loadConstant(local5, Integer.valueOf(i - b1));
    code.newArray(local4, local5);
    for (byte b2 = b1; b2 < i; b2++) {
      DexMakerUtils.autoBoxIfNecessary(code, local7, arrayOfLocal[b2]);
      code.loadConstant(local6, Integer.valueOf(b2 - b1));
      code.aput(local4, local6, local7);
    } 
    if (this.mReturnTypeId.equals(TypeId.VOID)) {
      code.invokeStatic(this.mSandHookCallOriginMethodId, null, new Local[] { local1, local2, local3, local4 });
      code.returnVoid();
    } else {
      code.invokeStatic(this.mSandHookCallOriginMethodId, local7, new Local[] { local1, local2, local3, local4 });
      local4 = (Local)map.get(DexMakerUtils.getObjTypeIdIfPrimitive(this.mReturnTypeId));
      code.cast(local4, local7);
      local7 = (Local)map.get(this.mReturnTypeId);
      DexMakerUtils.autoUnboxIfNecessary(code, local7, local4, map, true);
      code.returnValue(local7);
    } 
    code.mark(label);
    if (this.mReturnTypeId.equals(TypeId.VOID)) {
      code.returnVoid();
    } else {
      code.returnValue((Local)map.get(this.mReturnTypeId));
    } 
  }
  
  private void generateBackupMethod() {
    MethodId<?, ?> methodId = this.mHookerTypeId.getMethod(this.mReturnTypeId, "backup", (TypeId[])this.mParameterTypeIds);
    this.mBackupMethodId = methodId;
    Code code = this.mDexMaker.declare(methodId, 9);
    Local local = code.newLocal(memberTypeId);
    Map map = DexMakerUtils.createResultLocals(code);
    MethodId methodId1 = TypeId.get(DexLog.class).getMethod(TypeId.get(void.class), "printCallOriginError", new TypeId[] { memberTypeId });
    Label label = new Label();
    code.addCatchClause(throwableTypeId, label);
    code.sget(this.mMethodFieldId, local);
    code.invokeStatic(methodId1, null, new Local[] { local });
    code.mark(label);
    if (this.mReturnTypeId.equals(TypeId.VOID)) {
      code.returnVoid();
    } else {
      code.returnValue((Local)map.get(this.mReturnTypeId));
    } 
  }
  
  private void generateCallBackupMethod() {
    MethodId<?, ?> methodId = this.mHookerTypeId.getMethod(this.mReturnTypeId, "callBackup", (TypeId[])this.mParameterTypeIds);
    this.mCallBackupMethodId = methodId;
    Code code = this.mDexMaker.declare(methodId, 9);
    Local local1 = code.newLocal(memberTypeId);
    Local local2 = code.newLocal(methodTypeId);
    Local[] arrayOfLocal = createParameterLocals(code);
    Map map = DexMakerUtils.createResultLocals(code);
    code.sget(this.mMethodFieldId, local1);
    code.sget(this.mBackupMethodFieldId, local2);
    code.invokeStatic(TypeId.get(SandHook.class).getMethod(TypeId.get(void.class), "ensureBackupMethod", new TypeId[] { memberTypeId, methodTypeId }), null, new Local[] { local1, local2 });
    if (this.mReturnTypeId.equals(TypeId.VOID)) {
      code.invokeStatic(this.mBackupMethodId, null, arrayOfLocal);
      code.returnVoid();
    } else {
      local1 = (Local)map.get(this.mReturnTypeId);
      code.invokeStatic(this.mBackupMethodId, local1, arrayOfLocal);
      code.returnValue(local1);
    } 
  }
  
  private void generateFields() {
    this.mHookInfoFieldId = this.mHookerTypeId.getField(hookInfoTypeId, "additionalHookInfo");
    this.mMethodFieldId = this.mHookerTypeId.getField(memberTypeId, "method");
    this.mBackupMethodFieldId = this.mHookerTypeId.getField(methodTypeId, "backupMethod");
    this.mDexMaker.declare(this.mHookInfoFieldId, 8, null);
    this.mDexMaker.declare(this.mMethodFieldId, 8, null);
    this.mDexMaker.declare(this.mBackupMethodFieldId, 8, null);
  }
  
  private void generateHookMethod() {
    this.mHookMethodId = this.mHookerTypeId.getMethod(this.mReturnTypeId, "hook", (TypeId[])this.mParameterTypeIds);
    TypeId typeId1 = TypeId.get(DexLog.class);
    TypeId typeId2 = TypeId.get(void.class);
    TypeId typeId3 = TypeId.get(Member.class);
    Integer integer = Integer.valueOf(0);
    this.mPrintLogMethodId = typeId1.getMethod(typeId2, "printMethodHookIn", new TypeId[] { typeId3 });
    Code code = this.mDexMaker.declare(this.mHookMethodId, 9);
    Label label1 = new Label();
    Label label2 = new Label();
    Label label3 = new Label();
    Label label4 = new Label();
    Label label5 = new Label();
    Label label6 = new Label();
    Label label7 = new Label();
    Label label8 = new Label();
    Label label9 = new Label();
    Label label10 = new Label();
    Label label11 = new Label();
    Label label12 = new Label();
    Label label13 = new Label();
    Local local2 = code.newLocal(TypeId.BOOLEAN);
    Local local3 = code.newLocal(hookInfoTypeId);
    Local local4 = code.newLocal(callbacksTypeId);
    Local local5 = code.newLocal(objArrayTypeId);
    Local local6 = code.newLocal(TypeId.INT);
    Local local7 = code.newLocal(TypeId.OBJECT);
    Local local8 = code.newLocal(callbackTypeId);
    Local local9 = code.newLocal(TypeId.OBJECT);
    Local local10 = code.newLocal(TypeId.INT);
    Local local11 = code.newLocal(TypeId.OBJECT);
    Local local12 = code.newLocal(throwableTypeId);
    Local local13 = code.newLocal(paramTypeId);
    Local local14 = code.newLocal(memberTypeId);
    Local local15 = code.newLocal(TypeId.OBJECT);
    Local local16 = code.newLocal(objArrayTypeId);
    Local local17 = code.newLocal(TypeId.BOOLEAN);
    Local local18 = code.newLocal(TypeId.INT);
    Local local1 = code.newLocal(TypeId.INT);
    Local local19 = code.newLocal(TypeId.INT);
    Local local20 = code.newLocal(TypeId.OBJECT);
    Local local21 = code.newLocal(throwableTypeId);
    Local local22 = code.newLocal(TypeId.BOOLEAN);
    Local[] arrayOfLocal = createParameterLocals(code);
    Map map = DexMakerUtils.createResultLocals(code);
    code.loadConstant(local16, null);
    code.loadConstant(local1, integer);
    code.loadConstant(local10, Integer.valueOf(1));
    code.loadConstant(local6, integer);
    code.loadConstant(local11, null);
    code.sget(this.mMethodFieldId, local14);
    code.invokeStatic(this.mPrintLogMethodId, null, new Local[] { local14 });
    code.sget(xposedBridgeTypeId.getField(TypeId.BOOLEAN, "disableHooks"), local2);
    code.compareZ(Comparison.NE, label1, local2);
    code.sget(this.mHookInfoFieldId, local3);
    code.iget(hookInfoTypeId.getField(callbacksTypeId, "callbacks"), local4, local3);
    TypeId<XposedBridge.CopyOnWriteSortedSet> typeId4 = callbacksTypeId;
    TypeId<Object[]> typeId = objArrayTypeId;
    int i = 0;
    code.invokeVirtual(typeId4.getMethod(typeId, "getSnapshot", new TypeId[0]), local5, local4, new Local[0]);
    code.arrayLength(local6, local5);
    code.compareZ(Comparison.EQ, label1, local6);
    int j = this.mParameterTypeIds.length;
    if (this.mIsStatic) {
      code.loadConstant(local15, null);
    } else {
      code.move(local15, arrayOfLocal[0]);
      i = 1;
    } 
    code.loadConstant(local18, Integer.valueOf(j - i));
    code.newArray(local16, local18);
    int k;
    for (k = i; k < j; k++) {
      DexMakerUtils.autoBoxIfNecessary(code, local9, arrayOfLocal[k]);
      code.loadConstant(local1, Integer.valueOf(k - i));
      code.aput(local16, local1, local9);
    } 
    code.newInstance(local13, paramTypeId.getConstructor(new TypeId[0]), new Local[0]);
    code.iput(paramTypeId.getField(memberTypeId, "method"), local13, local14);
    code.iput(paramTypeId.getField(TypeId.OBJECT, "thisObject"), local13, local15);
    code.iput(paramTypeId.getField(objArrayTypeId, "args"), local13, local16);
    code.loadConstant(local19, integer);
    code.mark(label6);
    code.addCatchClause(throwableTypeId, label3);
    code.aget(local7, local5, local19);
    code.cast(local8, local7);
    code.invokeVirtual(callBeforeCallbackMethodId, null, local8, new Local[] { local13 });
    code.jump(label4);
    code.removeCatchClause(throwableTypeId);
    code.mark(label3);
    code.moveException(local12);
    code.invokeStatic(logThrowableMethodId, null, new Local[] { local12 });
    code.invokeVirtual(setResultMethodId, null, local13, new Local[] { local11 });
    code.loadConstant(local17, Boolean.valueOf(false));
    code.iput(returnEarlyFieldId, local13, local17);
    code.jump(label2);
    code.mark(label4);
    code.iget(returnEarlyFieldId, local17, local13);
    code.compareZ(Comparison.EQ, label2, local17);
    code.op(BinaryOp.ADD, local19, local19, local10);
    code.jump(label5);
    code.mark(label2);
    code.op(BinaryOp.ADD, local19, local19, local10);
    code.compare(Comparison.LT, label6, local19, local6);
    code.mark(label5);
    code.iget(returnEarlyFieldId, local17, local13);
    code.compareZ(Comparison.NE, label9, local17);
    code.addCatchClause(throwableTypeId, label8);
    i = this.mIsStatic ^ true;
    for (k = i; k < arrayOfLocal.length; k++) {
      code.loadConstant(local1, Integer.valueOf(k - i));
      code.aget(local9, local16, local1);
      DexMakerUtils.autoUnboxIfNecessary(code, arrayOfLocal[k], local9, map, true);
    } 
    if (this.mReturnTypeId.equals(TypeId.VOID)) {
      code.invokeStatic(this.mBackupMethodId, null, arrayOfLocal);
      code.invokeVirtual(setResultMethodId, null, local13, new Local[] { local11 });
    } else {
      local1 = (Local)map.get(this.mReturnTypeId);
      code.invokeStatic(this.mBackupMethodId, local1, arrayOfLocal);
      DexMakerUtils.autoBoxIfNecessary(code, local9, local1);
      code.invokeVirtual(setResultMethodId, null, local13, new Local[] { local9 });
    } 
    code.jump(label9);
    code.removeCatchClause(throwableTypeId);
    code.mark(label8);
    code.moveException(local12);
    code.invokeVirtual(setThrowableMethodId, null, local13, new Local[] { local12 });
    code.mark(label9);
    code.op(BinaryOp.SUBTRACT, local19, local19, local10);
    code.mark(label7);
    code.invokeVirtual(getResultMethodId, local20, local13, new Local[0]);
    code.invokeVirtual(getThrowableMethodId, local21, local13, new Local[0]);
    code.addCatchClause(throwableTypeId, label10);
    code.aget(local7, local5, local19);
    code.cast(local8, local7);
    code.invokeVirtual(callAfterCallbackMethodId, null, local8, new Local[] { local13 });
    code.jump(label11);
    code.removeCatchClause(throwableTypeId);
    code.mark(label10);
    code.moveException(local12);
    code.invokeStatic(logThrowableMethodId, null, new Local[] { local12 });
    code.compareZ(Comparison.EQ, label12, local21);
    code.invokeVirtual(setThrowableMethodId, null, local13, new Local[] { local21 });
    code.jump(label11);
    code.mark(label12);
    code.invokeVirtual(setResultMethodId, null, local13, new Local[] { local20 });
    code.mark(label11);
    code.op(BinaryOp.SUBTRACT, local19, local19, local10);
    code.compareZ(Comparison.GE, label7, local19);
    code.invokeVirtual(hasThrowableMethodId, local22, local13, new Local[0]);
    code.compareZ(Comparison.NE, label13, local22);
    if (this.mReturnTypeId.equals(TypeId.VOID)) {
      code.returnVoid();
    } else {
      code.invokeVirtual(getResultMethodId, local9, local13, new Local[0]);
      TypeId typeId5 = DexMakerUtils.getObjTypeIdIfPrimitive(this.mReturnTypeId);
      Map map1 = map;
      Local local = (Local)map1.get(typeId5);
      code.cast(local, local9);
      local9 = (Local)map1.get(this.mReturnTypeId);
      DexMakerUtils.autoUnboxIfNecessary(code, local9, local, map1, true);
      code.returnValue(local9);
    } 
    code.mark(label13);
    code.invokeVirtual(getThrowableMethodId, local12, local13, new Local[0]);
    code.throwValue(local12);
    code.mark(label1);
    if (this.mReturnTypeId.equals(TypeId.VOID)) {
      code.invokeStatic(this.mBackupMethodId, null, arrayOfLocal);
      code.returnVoid();
    } else {
      local1 = (Local)map.get(this.mReturnTypeId);
      code.invokeStatic(this.mBackupMethodId, local1, arrayOfLocal);
      code.returnValue(local1);
    } 
  }
  
  private void generateSetupMethod() {
    MethodId methodId = this.mHookerTypeId.getMethod(TypeId.VOID, "setup", new TypeId[] { memberTypeId, methodTypeId, hookInfoTypeId });
    Code code = this.mDexMaker.declare(methodId, 9);
    Local local1 = code.getParameter(0, memberTypeId);
    Local local2 = code.getParameter(1, methodTypeId);
    Local local3 = code.getParameter(2, hookInfoTypeId);
    code.sput(this.mMethodFieldId, local1);
    code.sput(this.mBackupMethodFieldId, local2);
    code.sput(this.mHookInfoFieldId, local3);
    code.returnVoid();
  }
  
  private String getClassName(Member paramMember) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("SandHooker_");
    stringBuilder.append(DexMakerUtils.MD5(paramMember.toString()));
    return stringBuilder.toString();
  }
  
  private static TypeId<?>[] getParameterTypeIds(Class<?>[] paramArrayOfClass, boolean paramBoolean) {
    int i = paramArrayOfClass.length;
    if (!paramBoolean)
      i++; 
    TypeId[] arrayOfTypeId = new TypeId[i];
    byte b = 0;
    if (!paramBoolean) {
      arrayOfTypeId[0] = TypeId.OBJECT;
      i = 1;
    } else {
      i = 0;
    } 
    while (b < paramArrayOfClass.length) {
      arrayOfTypeId[b + i] = TypeId.get(paramArrayOfClass[b]);
      b++;
    } 
    return (TypeId<?>[])arrayOfTypeId;
  }
  
  private static Class<?>[] getParameterTypes(Class<?>[] paramArrayOfClass, boolean paramBoolean) {
    if (paramBoolean)
      return paramArrayOfClass; 
    Class[] arrayOfClass = new Class[paramArrayOfClass.length + 1];
    arrayOfClass[0] = Object.class;
    System.arraycopy(paramArrayOfClass, 0, arrayOfClass, 1, paramArrayOfClass.length);
    return arrayOfClass;
  }
  
  private HookWrapper.HookEntity loadHookerClass(ClassLoader paramClassLoader, String paramString) throws Exception {
    Class<?> clazz = paramClassLoader.loadClass(paramString);
    this.mHookClass = clazz;
    this.mHookMethod = clazz.getMethod("hook", this.mActualParameterTypes);
    this.mBackupMethod = this.mHookClass.getMethod("backup", this.mActualParameterTypes);
    Method method = this.mHookClass.getMethod("callBackup", this.mActualParameterTypes);
    this.mCallBackupMethod = method;
    SandHook.resolveStaticMethod(method);
    SandHookMethodResolver.resolveMethod(this.mCallBackupMethod, this.mBackupMethod);
    SandHook.compileMethod(this.mCallBackupMethod);
    this.mHookClass.getMethod("setup", new Class[] { Member.class, Method.class, XposedBridge.AdditionalHookInfo.class }).invoke(null, new Object[] { this.mMember, this.mBackupMethod, this.mHookInfo });
    return new HookWrapper.HookEntity(this.mMember, this.mHookMethod, this.mBackupMethod);
  }
  
  public Method getBackupMethod() {
    return this.mBackupMethod;
  }
  
  public Method getCallBackupMethod() {
    return this.mCallBackupMethod;
  }
  
  public Class getHookClass() {
    return this.mHookClass;
  }
  
  public Method getHookMethod() {
    return this.mHookMethod;
  }
  
  public void start(Member paramMember, XposedBridge.AdditionalHookInfo paramAdditionalHookInfo, ClassLoader paramClassLoader, String paramString) throws Exception {
    HookWrapper.HookEntity hookEntity;
    boolean bool = paramMember instanceof Method;
    boolean bool1 = true;
    boolean bool2 = true;
    if (bool) {
      Method method = (Method)paramMember;
      this.mIsStatic = Modifier.isStatic(method.getModifiers());
      Class<?> clazz = method.getReturnType();
      this.mReturnType = clazz;
      if (clazz.equals(Void.class) || this.mReturnType.equals(void.class) || this.mReturnType.isPrimitive()) {
        this.mReturnTypeId = TypeId.get(this.mReturnType);
      } else {
        this.mReturnType = Object.class;
        this.mReturnTypeId = TypeId.OBJECT;
      } 
      this.mParameterTypeIds = getParameterTypeIds(method.getParameterTypes(), this.mIsStatic);
      this.mActualParameterTypes = getParameterTypes(method.getParameterTypes(), this.mIsStatic);
      if ((method.getExceptionTypes()).length <= 0)
        bool2 = false; 
      this.mHasThrowable = bool2;
    } else if (paramMember instanceof Constructor) {
      Constructor constructor = (Constructor)paramMember;
      this.mIsStatic = false;
      this.mReturnType = void.class;
      this.mReturnTypeId = TypeId.VOID;
      this.mParameterTypeIds = getParameterTypeIds(constructor.getParameterTypes(), this.mIsStatic);
      this.mActualParameterTypes = getParameterTypes(constructor.getParameterTypes(), this.mIsStatic);
      if ((constructor.getExceptionTypes()).length > 0) {
        bool2 = bool1;
      } else {
        bool2 = false;
      } 
      this.mHasThrowable = bool2;
    } else {
      if (!paramMember.getDeclaringClass().isInterface()) {
        if (Modifier.isAbstract(paramMember.getModifiers())) {
          StringBuilder stringBuilder3 = new StringBuilder();
          stringBuilder3.append("Cannot hook abstract methods: ");
          stringBuilder3.append(paramMember.toString());
          throw new IllegalArgumentException(stringBuilder3.toString());
        } 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Only methods and constructors can be hooked: ");
        stringBuilder.append(paramMember.toString());
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      stringBuilder2 = new StringBuilder();
      stringBuilder2.append("Cannot hook interfaces: ");
      stringBuilder2.append(paramMember.toString());
      throw new IllegalArgumentException(stringBuilder2.toString());
    } 
    this.mMember = paramMember;
    this.mHookInfo = (XposedBridge.AdditionalHookInfo)stringBuilder2;
    this.mDexDirPath = paramString;
    if (paramClassLoader == null || paramClassLoader.getClass().getName().equals("java.lang.BootClassLoader")) {
      this.mAppClassLoader = getClass().getClassLoader();
    } else {
      this.mAppClassLoader = paramClassLoader;
    } 
    this.mDexMaker = new DexMaker();
    String str = getClassName(this.mMember);
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(str);
    stringBuilder1.append(".jar");
    paramString = stringBuilder1.toString();
    StringBuilder stringBuilder2 = null;
    try {
      DexMaker dexMaker = this.mDexMaker;
      ClassLoader classLoader1 = this.mAppClassLoader;
      File file = new File();
      this(this.mDexDirPath);
      ClassLoader classLoader2 = dexMaker.loadClassDirect(classLoader1, file, paramString);
      StringBuilder stringBuilder = stringBuilder2;
    } finally {
      stringBuilder1 = null;
    } 
    stringBuilder2 = stringBuilder1;
    if (stringBuilder1 == null)
      hookEntity = doMake(str, paramString); 
    SandHook.hook(hookEntity);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\xposedcompat\methodgen\HookerDexMaker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */