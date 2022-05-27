package com.lody.virtual.client.hook.proxies.device;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import mirror.android.os.IDeviceIdleController;

public class DeviceIdleControllerStub extends BinderInvocationProxy {
  public DeviceIdleControllerStub() {
    super(IDeviceIdleController.Stub.asInterface, "deviceidle");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("addPowerSaveWhitelistApp"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("removePowerSaveWhitelistApp"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("removeSystemPowerWhitelistApp"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("restoreSystemPowerWhitelistApp"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isPowerSaveWhitelistExceptIdleApp"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isPowerSaveWhitelistApp"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\device\DeviceIdleControllerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */