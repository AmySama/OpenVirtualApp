package com.swift.sandhook.xposedcompat.methodgen;

import android.text.TextUtils;
import com.android.dx.Code;
import com.android.dx.DexMaker;
import com.android.dx.FieldId;
import com.android.dx.Local;
import com.android.dx.MethodId;
import com.android.dx.TypeId;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.SandHookConfig;
import com.swift.sandhook.wrapper.HookWrapper;
import com.swift.sandhook.xposedcompat.hookstub.HookStubManager;
import com.swift.sandhook.xposedcompat.utils.DexMakerUtils;
import dalvik.system.InMemoryDexClassLoader;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.Map;

public class HookerDexMakerNew implements HookMaker {
  private static final String CLASS_DESC_PREFIX = "L";
  
  private static final String CLASS_NAME_PREFIX = "SandHookerNew";
  
  private static final String FIELD_NAME_BACKUP_METHOD = "backupMethod";
  
  private static final String FIELD_NAME_HOOK_INFO = "additionalHookInfo";
  
  private static final String FIELD_NAME_METHOD = "method";
  
  public static final String METHOD_NAME_BACKUP = "backup";
  
  public static final String METHOD_NAME_HOOK = "hook";
  
  private static final TypeId<XposedBridge.AdditionalHookInfo> hookInfoTypeId;
  
  private static final TypeId<Member> memberTypeId;
  
  private static final TypeId<Method> methodTypeId;
  
  public static final TypeId<Object[]> objArrayTypeId = TypeId.get(Object[].class);
  
  private Class<?>[] mActualParameterTypes;
  
  private ClassLoader mAppClassLoader;
  
  private Method mBackupMethod;
  
  private FieldId<?, Method> mBackupMethodFieldId;
  
  private MethodId<?, ?> mBackupMethodId;
  
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
  
  private Class<?> mReturnType;
  
  private TypeId<?> mReturnTypeId;
  
  private MethodId<?, ?> mSandHookBridgeMethodId;
  
  static {
    memberTypeId = TypeId.get(Member.class);
    methodTypeId = TypeId.get(Method.class);
    hookInfoTypeId = TypeId.get(XposedBridge.AdditionalHookInfo.class);
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
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("L");
    stringBuilder.append(paramString1);
    stringBuilder.append(";");
    TypeId<?> typeId = TypeId.get(stringBuilder.toString());
    this.mHookerTypeId = typeId;
    DexMaker dexMaker = this.mDexMaker;
    stringBuilder = new StringBuilder();
    stringBuilder.append(paramString1);
    stringBuilder.append(".generated");
    dexMaker.declare(typeId, stringBuilder.toString(), 1, TypeId.OBJECT, new TypeId[0]);
    generateFields();
    generateHookMethod();
    generateBackupMethod();
    if (TextUtils.isEmpty(this.mDexDirPath)) {
      if (SandHookConfig.SDK_INT >= 26) {
        inMemoryDexClassLoader = new InMemoryDexClassLoader(ByteBuffer.wrap(this.mDexMaker.generate()), this.mAppClassLoader);
      } else {
        throw new IllegalArgumentException("dexDirPath should not be empty!!!");
      } 
    } else {
      try {
        DexMaker dexMaker1 = this.mDexMaker;
        ClassLoader classLoader2 = this.mAppClassLoader;
        File file = new File();
        this(this.mDexDirPath);
        ClassLoader classLoader1 = dexMaker1.generateAndLoad(classLoader2, file, (String)inMemoryDexClassLoader);
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
  
  private void generateBackupMethod() {
    MethodId<?, ?> methodId = this.mHookerTypeId.getMethod(TypeId.VOID, "backup", new TypeId[0]);
    this.mBackupMethodId = methodId;
    this.mDexMaker.declare(methodId, 9).returnVoid();
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
    byte b1;
    this.mHookMethodId = this.mHookerTypeId.getMethod(this.mReturnTypeId, "hook", (TypeId[])this.mParameterTypeIds);
    this.mSandHookBridgeMethodId = TypeId.get(HookStubManager.class).getMethod(TypeId.get(Object.class), "hookBridge", new TypeId[] { memberTypeId, methodTypeId, hookInfoTypeId, TypeId.get(Object.class), TypeId.get(Object[].class) });
    Code code = this.mDexMaker.declare(this.mHookMethodId, 9);
    Local local1 = code.newLocal(memberTypeId);
    Local local2 = code.newLocal(methodTypeId);
    Local local3 = code.newLocal(hookInfoTypeId);
    Local local4 = code.newLocal(TypeId.OBJECT);
    Local local5 = code.newLocal(objArrayTypeId);
    Local local6 = code.newLocal(TypeId.INT);
    Local local7 = code.newLocal(TypeId.INT);
    Local local8 = code.newLocal(TypeId.OBJECT);
    Local[] arrayOfLocal = createParameterLocals(code);
    Map map = DexMakerUtils.createResultLocals(code);
    code.loadConstant(local5, null);
    code.loadConstant(local7, Integer.valueOf(0));
    code.sget(this.mMethodFieldId, local1);
    code.sget(this.mBackupMethodFieldId, local2);
    code.sget(this.mHookInfoFieldId, local3);
    int i = this.mParameterTypeIds.length;
    if (this.mIsStatic) {
      code.loadConstant(local4, null);
      b1 = 0;
    } else {
      code.move(local4, arrayOfLocal[0]);
      b1 = 1;
    } 
    code.loadConstant(local6, Integer.valueOf(i - b1));
    code.newArray(local5, local6);
    for (byte b2 = b1; b2 < i; b2++) {
      DexMakerUtils.autoBoxIfNecessary(code, local8, arrayOfLocal[b2]);
      code.loadConstant(local7, Integer.valueOf(b2 - b1));
      code.aput(local5, local7, local8);
    } 
    if (this.mReturnTypeId.equals(TypeId.VOID)) {
      code.invokeStatic(this.mSandHookBridgeMethodId, null, new Local[] { local1, local2, local3, local4, local5 });
      code.returnVoid();
    } else {
      code.invokeStatic(this.mSandHookBridgeMethodId, local8, new Local[] { local1, local2, local3, local4, local5 });
      local3 = (Local)map.get(DexMakerUtils.getObjTypeIdIfPrimitive(this.mReturnTypeId));
      code.cast(local3, local8);
      local8 = (Local)map.get(this.mReturnTypeId);
      DexMakerUtils.autoUnboxIfNecessary(code, local8, local3, map, true);
      code.returnValue(local8);
    } 
  }
  
  private String getClassName(Member paramMember) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("SandHookerNew_");
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
    this.mBackupMethod = this.mHookClass.getMethod("backup", new Class[0]);
    setup(this.mHookClass);
    return new HookWrapper.HookEntity(this.mMember, this.mHookMethod, this.mBackupMethod, false);
  }
  
  private void setup(Class paramClass) {
    XposedHelpers.setStaticObjectField(paramClass, "method", this.mMember);
    XposedHelpers.setStaticObjectField(paramClass, "backupMethod", this.mBackupMethod);
    XposedHelpers.setStaticObjectField(paramClass, "additionalHookInfo", this.mHookInfo);
  }
  
  public Method getBackupMethod() {
    return this.mBackupMethod;
  }
  
  public Method getCallBackupMethod() {
    return this.mBackupMethod;
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
      ClassLoader classLoader = this.mAppClassLoader;
      File file = new File();
      this(this.mDexDirPath);
      classLoader = dexMaker.loadClassDirect(classLoader, file, paramString);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\xposedcompat\methodgen\HookerDexMakerNew.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */