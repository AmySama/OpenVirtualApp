package com.lody.virtual.client.hook.proxies.appops;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import mirror.oem.IFlymePermissionService;

public class FlymePermissionServiceStub extends BinderInvocationProxy {
  public FlymePermissionServiceStub() {
    super(IFlymePermissionService.Stub.TYPE, "flyme_permission");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("noteIntentOperation"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\appops\FlymePermissionServiceStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */