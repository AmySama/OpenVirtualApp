package com.lody.virtual.client.hook.proxies.system;

import android.os.IInterface;
import com.android.internal.widget.ILockSettings;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import mirror.android.os.ServiceManager;

public class LockSettingsStub extends BinderInvocationProxy {
  private static final String SERVICE_NAME = "lock_settings";
  
  public LockSettingsStub() {
    super((IInterface)new EmptyLockSettings(), "lock_settings");
  }
  
  public void inject() throws Throwable {
    if (ServiceManager.checkService.call(new Object[] { "lock_settings" }) == null)
      super.inject(); 
  }
  
  static class EmptyLockSettings extends ILockSettings.Stub {
    public int[] getRecoverySecretTypes() {
      return new int[0];
    }
    
    public void setRecoverySecretTypes(int[] param1ArrayOfint) {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\system\LockSettingsStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */