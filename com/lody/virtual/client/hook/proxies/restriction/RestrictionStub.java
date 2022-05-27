package com.lody.virtual.client.hook.proxies.restriction;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import mirror.android.content.IRestrictionsManager;

public class RestrictionStub extends BinderInvocationProxy {
  public RestrictionStub() {
    super(IRestrictionsManager.Stub.asInterface, "restrictions");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getApplicationRestrictions"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("notifyPermissionResponse"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("requestPermission"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\restriction\RestrictionStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */