package com.lody.virtual.client.hook.proxies.system;

import android.os.Bundle;
import android.os.IInterface;
import android.os.ISystemUpdateManager;
import android.os.PersistableBundle;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import mirror.android.os.ServiceManager;

public class SystemUpdateStub extends BinderInvocationProxy {
  private static final String SERVICE_NAME = "system_update";
  
  public SystemUpdateStub() {
    super((IInterface)new EmptySystemUpdateManagerImpl(), "system_update");
  }
  
  public void inject() throws Throwable {
    if (ServiceManager.checkService.call(new Object[] { "system_update" }) == null)
      super.inject(); 
  }
  
  static class EmptySystemUpdateManagerImpl extends ISystemUpdateManager.Stub {
    public Bundle retrieveSystemUpdateInfo() {
      Bundle bundle = new Bundle();
      bundle.putInt("status", 0);
      return bundle;
    }
    
    public void updateSystemUpdateInfo(PersistableBundle param1PersistableBundle) {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\system\SystemUpdateStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */