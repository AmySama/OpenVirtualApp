package com.lody.virtual.client.hook.proxies.oem.vivo;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceFirstPkgMethodProxy;
import mirror.oem.vivo.IPhysicalFlingManagerStub;

public class PhysicalFlingManagerStub extends BinderInvocationProxy {
  private static final String SERVER_NAME = "physical_fling_service";
  
  public PhysicalFlingManagerStub() {
    super(IPhysicalFlingManagerStub.Stub.TYPE, "physical_fling_service");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("isSupportPhysicalFling"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\oem\vivo\PhysicalFlingManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */