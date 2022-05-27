package com.lody.virtual.client.hook.base;

import java.lang.reflect.Method;

public class ReplaceUidMethodProxy extends StaticMethodProxy {
  private final int index;
  
  public ReplaceUidMethodProxy(String paramString, int paramInt) {
    super(paramString);
    this.index = paramInt;
  }
  
  public boolean beforeCall(Object paramObject, Method paramMethod, Object... paramVarArgs) {
    int i = ((Integer)paramVarArgs[this.index]).intValue();
    if (i == getVUid() || i == getBaseVUid())
      paramVarArgs[this.index] = Integer.valueOf(getRealUid()); 
    return super.beforeCall(paramObject, paramMethod, paramVarArgs);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\base\ReplaceUidMethodProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */