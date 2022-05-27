package com.lody.virtual.client.hook.base;

import java.lang.reflect.Method;

public class ResultStaticMethodProxy extends StaticMethodProxy {
  Object mResult;
  
  public ResultStaticMethodProxy(String paramString, Object paramObject) {
    super(paramString);
    this.mResult = paramObject;
  }
  
  public Object call(Object paramObject, Method paramMethod, Object... paramVarArgs) throws Throwable {
    return this.mResult;
  }
  
  public Object getResult() {
    return this.mResult;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\base\ResultStaticMethodProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */