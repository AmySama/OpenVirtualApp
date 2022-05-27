package com.lody.virtual.client.hook.proxies.appops;

import android.app.AppOpsManager;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import mirror.android.app.AppOpsManager;
import mirror.com.android.internal.app.IAppOpsService;

@Inject(MethodProxies.class)
public class AppOpsManagerStub extends BinderInvocationProxy {
  public AppOpsManagerStub() {
    super(IAppOpsService.Stub.asInterface, "appops");
  }
  
  public void inject() throws Throwable {
    super.inject();
    if (AppOpsManager.mService != null) {
      AppOpsManager appOpsManager = (AppOpsManager)VirtualCore.get().getContext().getSystemService("appops");
      try {
        AppOpsManager.mService.set(appOpsManager, ((BinderInvocationStub)getInvocationStub()).getProxyInterface());
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    } 
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\appops\AppOpsManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */