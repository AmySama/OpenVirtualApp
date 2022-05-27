package com.lody.virtual.client.hook.proxies.context_hub;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import com.lody.virtual.helper.compat.BuildCompat;
import mirror.android.hardware.location.IContextHubService;

public class ContextHubServiceStub extends BinderInvocationProxy {
  public ContextHubServiceStub() {
    super(IContextHubService.Stub.asInterface, getServiceName());
  }
  
  private static String getServiceName() {
    String str;
    if (BuildCompat.isOreo()) {
      str = "contexthub";
    } else {
      str = "contexthub_service";
    } 
    return str;
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("registerCallback", Integer.valueOf(0)));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getContextHubInfo", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getContextHubHandles", new int[0]));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\context_hub\ContextHubServiceStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */