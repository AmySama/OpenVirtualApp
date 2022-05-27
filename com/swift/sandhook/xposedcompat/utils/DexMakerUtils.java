package com.swift.sandhook.xposedcompat.utils;

import com.android.dx.Code;
import com.android.dx.Local;
import com.android.dx.TypeId;
import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.PlainInsn;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.Rops;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class DexMakerUtils {
  private static volatile Method addInstMethod;
  
  private static volatile Method specMethod;
  
  public static String MD5(String paramString) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.update(paramString.getBytes());
      BigInteger bigInteger = new BigInteger();
      this(1, messageDigest.digest());
      return bigInteger.toString(32);
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      noSuchAlgorithmException.printStackTrace();
      return paramString;
    } 
  }
  
  public static void addInstruction(Code paramCode, Insn paramInsn) {
    if (addInstMethod == null)
      try {
        addInstMethod = Code.class.getDeclaredMethod("addInstruction", new Class[] { Insn.class });
        addInstMethod.setAccessible(true);
      } catch (NoSuchMethodException noSuchMethodException) {
        noSuchMethodException.printStackTrace();
      }  
    try {
      addInstMethod.invoke(paramCode, new Object[] { paramInsn });
    } catch (IllegalAccessException illegalAccessException) {
      illegalAccessException.printStackTrace();
    } catch (InvocationTargetException invocationTargetException) {}
  }
  
  public static void autoBoxIfNecessary(Code paramCode, Local<Object> paramLocal, Local paramLocal1) {
    TypeId typeId = paramLocal1.getType();
    if (typeId.equals(TypeId.BOOLEAN)) {
      typeId = TypeId.get(Boolean.class);
      paramCode.invokeStatic(typeId.getMethod(typeId, "valueOf", new TypeId[] { TypeId.BOOLEAN }), paramLocal, new Local[] { paramLocal1 });
    } else if (typeId.equals(TypeId.BYTE)) {
      typeId = TypeId.get(Byte.class);
      paramCode.invokeStatic(typeId.getMethod(typeId, "valueOf", new TypeId[] { TypeId.BYTE }), paramLocal, new Local[] { paramLocal1 });
    } else if (typeId.equals(TypeId.CHAR)) {
      typeId = TypeId.get(Character.class);
      paramCode.invokeStatic(typeId.getMethod(typeId, "valueOf", new TypeId[] { TypeId.CHAR }), paramLocal, new Local[] { paramLocal1 });
    } else if (typeId.equals(TypeId.DOUBLE)) {
      typeId = TypeId.get(Double.class);
      paramCode.invokeStatic(typeId.getMethod(typeId, "valueOf", new TypeId[] { TypeId.DOUBLE }), paramLocal, new Local[] { paramLocal1 });
    } else if (typeId.equals(TypeId.FLOAT)) {
      typeId = TypeId.get(Float.class);
      paramCode.invokeStatic(typeId.getMethod(typeId, "valueOf", new TypeId[] { TypeId.FLOAT }), paramLocal, new Local[] { paramLocal1 });
    } else if (typeId.equals(TypeId.INT)) {
      typeId = TypeId.get(Integer.class);
      paramCode.invokeStatic(typeId.getMethod(typeId, "valueOf", new TypeId[] { TypeId.INT }), paramLocal, new Local[] { paramLocal1 });
    } else if (typeId.equals(TypeId.LONG)) {
      typeId = TypeId.get(Long.class);
      paramCode.invokeStatic(typeId.getMethod(typeId, "valueOf", new TypeId[] { TypeId.LONG }), paramLocal, new Local[] { paramLocal1 });
    } else if (typeId.equals(TypeId.SHORT)) {
      typeId = TypeId.get(Short.class);
      paramCode.invokeStatic(typeId.getMethod(typeId, "valueOf", new TypeId[] { TypeId.SHORT }), paramLocal, new Local[] { paramLocal1 });
    } else if (typeId.equals(TypeId.VOID)) {
      paramCode.loadConstant(paramLocal, null);
    } else {
      paramCode.move(paramLocal, paramLocal1);
    } 
  }
  
  public static void autoUnboxIfNecessary(Code paramCode, Local paramLocal1, Local paramLocal2, Map<TypeId, Local> paramMap, boolean paramBoolean) {
    Local local;
    TypeId typeId = paramLocal1.getType();
    if (typeId.equals(TypeId.BOOLEAN)) {
      typeId = TypeId.get("Ljava/lang/Boolean;");
      local = paramMap.get(typeId);
      paramCode.cast(local, paramLocal2);
      paramCode.invokeVirtual(typeId.getMethod(TypeId.BOOLEAN, "booleanValue", new TypeId[0]), paramLocal1, local, new Local[0]);
    } else if (typeId.equals(TypeId.BYTE)) {
      typeId = TypeId.get("Ljava/lang/Byte;");
      local = (Local)local.get(typeId);
      paramCode.cast(local, paramLocal2);
      paramCode.invokeVirtual(typeId.getMethod(TypeId.BYTE, "byteValue", new TypeId[0]), paramLocal1, local, new Local[0]);
    } else if (typeId.equals(TypeId.CHAR)) {
      typeId = TypeId.get("Ljava/lang/Character;");
      local = (Local)local.get(typeId);
      paramCode.cast(local, paramLocal2);
      paramCode.invokeVirtual(typeId.getMethod(TypeId.CHAR, "charValue", new TypeId[0]), paramLocal1, local, new Local[0]);
    } else if (typeId.equals(TypeId.DOUBLE)) {
      typeId = TypeId.get("Ljava/lang/Double;");
      local = (Local)local.get(typeId);
      paramCode.cast(local, paramLocal2);
      paramCode.invokeVirtual(typeId.getMethod(TypeId.DOUBLE, "doubleValue", new TypeId[0]), paramLocal1, local, new Local[0]);
    } else if (typeId.equals(TypeId.FLOAT)) {
      typeId = TypeId.get("Ljava/lang/Float;");
      local = (Local)local.get(typeId);
      paramCode.cast(local, paramLocal2);
      paramCode.invokeVirtual(typeId.getMethod(TypeId.FLOAT, "floatValue", new TypeId[0]), paramLocal1, local, new Local[0]);
    } else if (typeId.equals(TypeId.INT)) {
      typeId = TypeId.get("Ljava/lang/Integer;");
      local = (Local)local.get(typeId);
      paramCode.cast(local, paramLocal2);
      paramCode.invokeVirtual(typeId.getMethod(TypeId.INT, "intValue", new TypeId[0]), paramLocal1, local, new Local[0]);
    } else if (typeId.equals(TypeId.LONG)) {
      typeId = TypeId.get("Ljava/lang/Long;");
      local = (Local)local.get(typeId);
      paramCode.cast(local, paramLocal2);
      paramCode.invokeVirtual(typeId.getMethod(TypeId.LONG, "longValue", new TypeId[0]), paramLocal1, local, new Local[0]);
    } else if (typeId.equals(TypeId.SHORT)) {
      typeId = TypeId.get("Ljava/lang/Short;");
      local = (Local)local.get(typeId);
      paramCode.cast(local, paramLocal2);
      paramCode.invokeVirtual(typeId.getMethod(TypeId.SHORT, "shortValue", new TypeId[0]), paramLocal1, local, new Local[0]);
    } else if (typeId.equals(TypeId.VOID)) {
      paramCode.loadConstant(paramLocal1, null);
    } else if (paramBoolean) {
      paramCode.cast(paramLocal1, paramLocal2);
    } else {
      paramCode.move(paramLocal1, paramLocal2);
    } 
  }
  
  public static Map<TypeId, Local> createResultLocals(Code paramCode) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    Local local1 = paramCode.newLocal(TypeId.BOOLEAN);
    Local local2 = paramCode.newLocal(TypeId.BYTE);
    Local local3 = paramCode.newLocal(TypeId.CHAR);
    Local local4 = paramCode.newLocal(TypeId.DOUBLE);
    Local local5 = paramCode.newLocal(TypeId.FLOAT);
    Local local6 = paramCode.newLocal(TypeId.INT);
    Local local7 = paramCode.newLocal(TypeId.LONG);
    Local local8 = paramCode.newLocal(TypeId.SHORT);
    Local local9 = paramCode.newLocal(TypeId.VOID);
    Local local10 = paramCode.newLocal(TypeId.OBJECT);
    Local local11 = paramCode.newLocal(TypeId.get("Ljava/lang/Boolean;"));
    Local local12 = paramCode.newLocal(TypeId.get("Ljava/lang/Byte;"));
    Local local13 = paramCode.newLocal(TypeId.get("Ljava/lang/Character;"));
    Local local14 = paramCode.newLocal(TypeId.get("Ljava/lang/Double;"));
    Local local15 = paramCode.newLocal(TypeId.get("Ljava/lang/Float;"));
    Local local16 = paramCode.newLocal(TypeId.get("Ljava/lang/Integer;"));
    Local local17 = paramCode.newLocal(TypeId.get("Ljava/lang/Long;"));
    Local local18 = paramCode.newLocal(TypeId.get("Ljava/lang/Short;"));
    Local local19 = paramCode.newLocal(TypeId.get("Ljava/lang/Void;"));
    paramCode.loadConstant(local1, Boolean.valueOf(false));
    paramCode.loadConstant(local2, Byte.valueOf((byte)0));
    paramCode.loadConstant(local3, Character.valueOf(false));
    paramCode.loadConstant(local4, Double.valueOf(0.0D));
    paramCode.loadConstant(local5, Float.valueOf(0.0F));
    paramCode.loadConstant(local6, Integer.valueOf(0));
    paramCode.loadConstant(local7, Long.valueOf(0L));
    paramCode.loadConstant(local8, Short.valueOf((short)0));
    paramCode.loadConstant(local9, null);
    paramCode.loadConstant(local10, null);
    paramCode.loadConstant(local11, null);
    paramCode.loadConstant(local12, null);
    paramCode.loadConstant(local13, null);
    paramCode.loadConstant(local14, null);
    paramCode.loadConstant(local15, null);
    paramCode.loadConstant(local16, null);
    paramCode.loadConstant(local17, null);
    paramCode.loadConstant(local18, null);
    paramCode.loadConstant(local19, null);
    hashMap.put(TypeId.BOOLEAN, local1);
    hashMap.put(TypeId.BYTE, local2);
    hashMap.put(TypeId.CHAR, local3);
    hashMap.put(TypeId.DOUBLE, local4);
    hashMap.put(TypeId.FLOAT, local5);
    hashMap.put(TypeId.INT, local6);
    hashMap.put(TypeId.LONG, local7);
    hashMap.put(TypeId.SHORT, local8);
    hashMap.put(TypeId.VOID, local9);
    hashMap.put(TypeId.OBJECT, local10);
    hashMap.put(TypeId.get("Ljava/lang/Boolean;"), local11);
    hashMap.put(TypeId.get("Ljava/lang/Byte;"), local12);
    hashMap.put(TypeId.get("Ljava/lang/Character;"), local13);
    hashMap.put(TypeId.get("Ljava/lang/Double;"), local14);
    hashMap.put(TypeId.get("Ljava/lang/Float;"), local15);
    hashMap.put(TypeId.get("Ljava/lang/Integer;"), local16);
    hashMap.put(TypeId.get("Ljava/lang/Long;"), local17);
    hashMap.put(TypeId.get("Ljava/lang/Short;"), local18);
    hashMap.put(TypeId.get("Ljava/lang/Void;"), local19);
    return (Map)hashMap;
  }
  
  public static TypeId getObjTypeIdIfPrimitive(TypeId paramTypeId) {
    if (paramTypeId.equals(TypeId.BOOLEAN))
      return TypeId.get("Ljava/lang/Boolean;"); 
    if (paramTypeId.equals(TypeId.BYTE))
      return TypeId.get("Ljava/lang/Byte;"); 
    if (paramTypeId.equals(TypeId.CHAR))
      return TypeId.get("Ljava/lang/Character;"); 
    if (paramTypeId.equals(TypeId.DOUBLE))
      return TypeId.get("Ljava/lang/Double;"); 
    if (paramTypeId.equals(TypeId.FLOAT))
      return TypeId.get("Ljava/lang/Float;"); 
    if (paramTypeId.equals(TypeId.INT))
      return TypeId.get("Ljava/lang/Integer;"); 
    if (paramTypeId.equals(TypeId.LONG))
      return TypeId.get("Ljava/lang/Long;"); 
    if (paramTypeId.equals(TypeId.SHORT))
      return TypeId.get("Ljava/lang/Short;"); 
    TypeId typeId = paramTypeId;
    if (paramTypeId.equals(TypeId.VOID))
      typeId = TypeId.get("Ljava/lang/Void;"); 
    return typeId;
  }
  
  public static void moveException(Code paramCode, Local<?> paramLocal) {
    addInstruction(paramCode, (Insn)new PlainInsn(Rops.opMoveException((TypeBearer)Type.THROWABLE), SourcePosition.NO_INFO, spec(paramLocal), RegisterSpecList.EMPTY));
  }
  
  public static void returnRightValue(Code paramCode, Class<?> paramClass, Map<Class, Local> paramMap) {
    paramCode.returnValue(paramMap.get(paramClass));
  }
  
  public static RegisterSpec spec(Local<?> paramLocal) {
    if (specMethod == null)
      try {
        specMethod = Local.class.getDeclaredMethod("spec", new Class[0]);
        specMethod.setAccessible(true);
      } catch (NoSuchMethodException noSuchMethodException) {
        noSuchMethodException.printStackTrace();
      }  
    try {
      return (RegisterSpec)specMethod.invoke(paramLocal, new Object[0]);
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    invocationTargetException.printStackTrace();
    return null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\xposedcompa\\utils\DexMakerUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */