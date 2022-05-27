package com.lody.virtual.client.hook.proxies.oem.vivo;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceFirstPkgMethodProxy;
import mirror.oem.vivo.ISuperResolutionManager;

public class SuperResolutionManagerStub extends BinderInvocationProxy {
  private static final String SERVER_NAME = "SuperResolutionManager";
  
  public SuperResolutionManagerStub() {
    super(ISuperResolutionManager.Stub.TYPE, "SuperResolutionManager");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("registerPackageSettingStateChangeListener"));
    addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("unRegisterPackageSettingStateChangeListener"));
    addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("registerSuperResolutionStateChange"));
    addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("unRegisterSuperResolutionStateChange"));
    addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("getPackageSettingState"));
    addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("putPackageSettingState"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\oem\vivo\SuperResolutionManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */