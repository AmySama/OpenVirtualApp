package com.lody.virtual.client.hook.proxies.cross_profile;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import mirror.android.content.pm.ICrossProfileApps;

public class CrossProfileAppsStub extends BinderInvocationProxy {
  public CrossProfileAppsStub() {
    super(ICrossProfileApps.Stub.asInterface, "crossprofileapps");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getTargetUserProfiles"));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("startActivityAsUser", null));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\cross_profile\CrossProfileAppsStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */