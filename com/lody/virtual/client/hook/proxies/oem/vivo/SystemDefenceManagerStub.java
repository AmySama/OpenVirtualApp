package com.lody.virtual.client.hook.proxies.oem.vivo;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceFirstPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import mirror.oem.vivo.ISystemDefenceManager;

public class SystemDefenceManagerStub extends BinderInvocationProxy {
  private static final String SERVER_NAME = "system_defence_service";
  
  public SystemDefenceManagerStub() {
    super(ISystemDefenceManager.Stub.TYPE, "system_defence_service");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("checkTransitionTimoutErrorDefence"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("checkSkipKilledByRemoveTask"));
    addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("checkSmallIconNULLPackage"));
    addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("checkDelayUpdate"));
    addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("onSetActivityResumed"));
    addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("checkReinstallPacakge"));
    addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("reportFgCrashData"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\oem\vivo\SystemDefenceManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */