package com.lody.virtual.client.hook.proxies.telephony;

import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import mirror.com.android.internal.telephony.IHwTelephony;

@Inject(MethodProxies.class)
public class HwTelephonyStub extends BinderInvocationProxy {
  public HwTelephonyStub() {
    super(IHwTelephony.Stub.TYPE, "phone_huawei");
  }
  
  protected void onBindMethods() {
    addMethodProxy((MethodProxy)new GetUniqueDeviceId());
  }
  
  private static class GetUniqueDeviceId extends MethodProxies.GetDeviceId {
    private GetUniqueDeviceId() {}
    
    public String getMethodName() {
      return "getUniqueDeviceId";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\telephony\HwTelephonyStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */