package com.lody.virtual.client.hook.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodBox {
  public final Object[] args;
  
  public final Method method;
  
  public final Object who;
  
  public MethodBox(Method paramMethod, Object paramObject, Object[] paramArrayOfObject) {
    this.method = paramMethod;
    this.who = paramObject;
    this.args = paramArrayOfObject;
  }
  
  public <T> T call() throws InvocationTargetException {
    try {
      return (T)this.method.invoke(this.who, this.args);
    } catch (IllegalAccessException illegalAccessException) {
      throw new RuntimeException(illegalAccessException);
    } 
  }
  
  public <T> T callSafe() {
    try {
      return (T)this.method.invoke(this.who, this.args);
    } catch (IllegalAccessException illegalAccessException) {
      illegalAccessException.printStackTrace();
    } catch (InvocationTargetException invocationTargetException) {
      invocationTargetException.printStackTrace();
    } 
    return null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\base\MethodBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */