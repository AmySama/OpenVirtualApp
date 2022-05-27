package com.lody.virtual.client.hook.proxies.dev_identifiers_policy;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import mirror.android.os.IDeviceIdentifiersPolicyService;

public class DeviceIdentifiersPolicyServiceHub extends BinderInvocationProxy {
  public DeviceIdentifiersPolicyServiceHub() {
    super(IDeviceIdentifiersPolicyService.Stub.asInterface, "device_identifiers");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getSerialForPackage"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\dev_identifiers_policy\DeviceIdentifiersPolicyServiceHub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */