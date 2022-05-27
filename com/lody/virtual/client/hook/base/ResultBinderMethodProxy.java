package com.lody.virtual.client.hook.base;

import android.os.IInterface;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public abstract class ResultBinderMethodProxy extends AutoResultStaticMethodProxy {
  public ResultBinderMethodProxy(String paramString) {
    super(paramString);
  }
  
  public Object call(Object paramObject, Method paramMethod, Object... paramVarArgs) throws Throwable {
    paramObject = super.call(paramObject, paramMethod, paramVarArgs);
    return newProxyInstance((IInterface)paramObject, createProxy((IInterface)paramObject));
  }
  
  public abstract InvocationHandler createProxy(IInterface paramIInterface);
  
  public Object newProxyInstance(IInterface paramIInterface, InvocationHandler paramInvocationHandler) {
    return Proxy.newProxyInstance(paramIInterface.getClass().getClassLoader(), paramIInterface.getClass().getInterfaces(), paramInvocationHandler);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\base\ResultBinderMethodProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */