package com.lody.virtual.client.hook.base;

import java.lang.reflect.Method;

public class ReplaceLastUserIdMethodProxy extends StaticMethodProxy {
  public ReplaceLastUserIdMethodProxy(String paramString) {
    super(paramString);
  }
  
  public boolean beforeCall(Object paramObject, Method paramMethod, Object... paramVarArgs) {
    replaceLastUserId(paramVarArgs);
    return super.beforeCall(paramObject, paramMethod, paramVarArgs);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\base\ReplaceLastUserIdMethodProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */