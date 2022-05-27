package com.lody.virtual.client.hook.proxies.network;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceUidMethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import java.lang.reflect.Method;
import mirror.android.os.INetworkManagementService;

public class NetworkManagementStub extends BinderInvocationProxy {
  public NetworkManagementStub() {
    super(INetworkManagementService.Stub.asInterface, "network_management");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceUidMethodProxy("setUidCleartextNetworkPolicy", 0));
    addMethodProxy((MethodProxy)new ReplaceUidMethodProxy("setUidMeteredNetworkBlacklist", 0));
    addMethodProxy((MethodProxy)new ReplaceUidMethodProxy("setUidMeteredNetworkWhitelist", 0));
    addMethodProxy((MethodProxy)new StaticMethodProxy("getNetworkStatsUidDetail") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            if (((Integer)param1VarArgs[0]).intValue() == getVUid())
              param1VarArgs[0] = Integer.valueOf(getRealUid()); 
            return super.call(param1Object, param1Method, param1VarArgs);
          }
        });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\network\NetworkManagementStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */