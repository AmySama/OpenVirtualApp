package com.lody.virtual.client.hook.proxies.fingerprint;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import mirror.android.hardware.fingerprint.IFingerprintService;

public class FingerprintManagerStub extends BinderInvocationProxy {
  public FingerprintManagerStub() {
    super(IFingerprintService.Stub.asInterface, "fingerprint");
  }
  
  protected void onBindMethods() {
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("isHardwareDetected"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("hasEnrolledFingerprints"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("authenticate"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("cancelAuthentication"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getEnrolledFingerprints"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getAuthenticatorId"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\fingerprint\FingerprintManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */