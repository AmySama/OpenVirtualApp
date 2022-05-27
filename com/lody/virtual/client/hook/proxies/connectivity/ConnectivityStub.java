package com.lody.virtual.client.hook.proxies.connectivity;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceFirstPkgMethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import mirror.android.net.IConnectivityManager;

public class ConnectivityStub extends BinderInvocationProxy {
  public ConnectivityStub() {
    super(IConnectivityManager.Stub.asInterface, "connectivity");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("isTetheringSupported", Boolean.valueOf(true)));
    addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("requestNetwork"));
    addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("getNetworkCapabilities"));
    addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("listenForNetwork"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\connectivity\ConnectivityStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */