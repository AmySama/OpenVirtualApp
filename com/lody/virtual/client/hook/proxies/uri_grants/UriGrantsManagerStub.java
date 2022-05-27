package com.lody.virtual.client.hook.proxies.uri_grants;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import mirror.android.app.IUriGrantsManager;

public class UriGrantsManagerStub extends BinderInvocationProxy {
  public UriGrantsManagerStub() {
    super(IUriGrantsManager.Stub.asInterface, "uri_grants");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getUriPermissions"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxie\\uri_grants\UriGrantsManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */