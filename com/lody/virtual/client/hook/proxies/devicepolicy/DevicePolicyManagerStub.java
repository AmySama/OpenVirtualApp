package com.lody.virtual.client.hook.proxies.devicepolicy;

import android.content.ComponentName;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import java.lang.reflect.Method;
import mirror.android.app.admin.IDevicePolicyManager;

public class DevicePolicyManagerStub extends BinderInvocationProxy {
  public DevicePolicyManagerStub() {
    super(IDevicePolicyManager.Stub.asInterface, "device_policy");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy(new GetStorageEncryptionStatus());
    addMethodProxy(new GetDeviceOwnerComponent());
  }
  
  private static class GetDeviceOwnerComponent extends MethodProxy {
    private GetDeviceOwnerComponent() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return new ComponentName(getAppPkg(), "");
    }
    
    public String getMethodName() {
      return "getDeviceOwnerComponent";
    }
  }
  
  private static class GetStorageEncryptionStatus extends MethodProxy {
    private GetStorageEncryptionStatus() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1VarArgs[0] = VirtualCore.get().getHostPkg();
      replaceLastUserId(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getStorageEncryptionStatus";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\devicepolicy\DevicePolicyManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */