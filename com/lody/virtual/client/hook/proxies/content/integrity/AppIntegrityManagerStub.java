package com.lody.virtual.client.hook.proxies.content.integrity;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import com.lody.virtual.helper.compat.ParceledListSliceCompat;
import java.util.Collections;
import mirror.android.content.integrity.IAppIntegrityManager;

public class AppIntegrityManagerStub extends BinderInvocationProxy {
  private static final String SERVER_NAME = "app_integrity";
  
  public AppIntegrityManagerStub() {
    super(IAppIntegrityManager.Stub.asInterface, "app_integrity");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("updateRuleSet", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getCurrentRuleSetVersion", ""));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getCurrentRuleSetProvider", ""));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getCurrentRules", ParceledListSliceCompat.create(Collections.emptyList())));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getWhitelistedRuleProviders", Collections.emptyList()));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\content\integrity\AppIntegrityManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */