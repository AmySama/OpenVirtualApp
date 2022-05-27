package com.lody.virtual.client.hook.proxies.role;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import mirror.android.role.IRoleManager;

public class RoleManagerStub extends BinderInvocationProxy {
  public RoleManagerStub() {
    super(IRoleManager.Stub.TYPE, "role");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isRoleHeld"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\role\RoleManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */