package com.lody.virtual.client.hook.proxies.appops;

import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import mirror.com.android.internal.app.ISmtOpsService;

@Inject(MethodProxies.class)
public class SmtOpsManagerStub extends BinderInvocationProxy {
  public SmtOpsManagerStub() {
    super(ISmtOpsService.Stub.asInterface, "smtops");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\appops\SmtOpsManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */