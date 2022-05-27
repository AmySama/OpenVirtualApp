package com.lody.virtual.client.hook.base;

public class StaticMethodProxy extends MethodProxy {
  private String mName;
  
  public StaticMethodProxy(String paramString) {
    this.mName = paramString;
  }
  
  public String getMethodName() {
    return this.mName;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\base\StaticMethodProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */