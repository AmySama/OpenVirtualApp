package com.lody.virtual.client.hook.base;

import java.lang.reflect.Method;

public class ReplaceSpecPkgMethodProxy extends StaticMethodProxy {
  private int index;
  
  public ReplaceSpecPkgMethodProxy(String paramString, int paramInt) {
    super(paramString);
    this.index = paramInt;
  }
  
  public boolean beforeCall(Object paramObject, Method paramMethod, Object... paramVarArgs) {
    if (paramVarArgs != null) {
      int i = this.index;
      int j = i;
      if (i < 0)
        j = i + paramVarArgs.length; 
      if (j >= 0 && j < paramVarArgs.length && paramVarArgs[j] instanceof String)
        paramVarArgs[j] = getHostPkg(); 
    } 
    return super.beforeCall(paramObject, paramMethod, paramVarArgs);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\base\ReplaceSpecPkgMethodProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */