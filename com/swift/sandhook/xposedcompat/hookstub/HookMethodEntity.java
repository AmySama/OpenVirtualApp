package com.swift.sandhook.xposedcompat.hookstub;

import com.swift.sandhook.SandHook;
import com.swift.sandhook.utils.ParamWrapper;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class HookMethodEntity {
  public Method backup;
  
  public Method hook;
  
  public boolean isStatic;
  
  public Member origin;
  
  public Class[] parType;
  
  public Class retType;
  
  public HookMethodEntity(Member paramMember, Method paramMethod1, Method paramMethod2) {
    this.origin = paramMember;
    this.hook = paramMethod1;
    this.backup = paramMethod2;
    this.isStatic = Modifier.isStatic(paramMember.getModifiers());
  }
  
  public Object getArg(int paramInt, long paramLong) {
    return ParamWrapper.addressToObject(this.parType[paramInt], paramLong);
  }
  
  public Object[] getArgs(long... paramVarArgs) {
    if (paramVarArgs == null || paramVarArgs.length == 0)
      return new Object[0]; 
    Class[] arrayOfClass = this.parType;
    if (arrayOfClass == null || arrayOfClass.length == 0)
      return new Object[0]; 
    int i = this.isStatic ^ true;
    Object[] arrayOfObject = new Object[arrayOfClass.length];
    for (int j = i; j < this.parType.length + i; j++) {
      int k = j - i;
      arrayOfObject[k] = getArg(k, paramVarArgs[j]);
    } 
    return arrayOfObject;
  }
  
  public long[] getArgsAddress(long[] paramArrayOflong, Object... paramVarArgs) {
    byte b1 = 0;
    if (paramArrayOflong == null || paramArrayOflong.length == 0)
      return new long[0]; 
    boolean bool = this.isStatic;
    byte b2 = 1;
    if (!bool) {
      long[] arrayOfLong = new long[paramArrayOflong.length + 1];
      arrayOfLong[0] = paramArrayOflong[0];
      paramArrayOflong = arrayOfLong;
    } else {
      paramArrayOflong = new long[paramArrayOflong.length];
      b2 = 0;
    } 
    while (true) {
      Class[] arrayOfClass = this.parType;
      if (b1 < arrayOfClass.length) {
        paramArrayOflong[b1 + b2] = ParamWrapper.objectToAddress(arrayOfClass[b1], paramVarArgs[b1]);
        b1++;
        continue;
      } 
      return paramArrayOflong;
    } 
  }
  
  public Object getResult(long paramLong) {
    return isVoid() ? null : ParamWrapper.addressToObject(this.retType, paramLong);
  }
  
  public long getResultAddress(Object paramObject) {
    return isVoid() ? 0L : ParamWrapper.objectToAddress(this.retType, paramObject);
  }
  
  public Object getThis(long paramLong) {
    return this.isStatic ? null : SandHook.getObject(paramLong);
  }
  
  public boolean isConstructor() {
    return this.origin instanceof java.lang.reflect.Constructor;
  }
  
  public boolean isVoid() {
    return (this.retType == null || void.class.equals(this.retType));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\xposedcompat\hookstub\HookMethodEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */