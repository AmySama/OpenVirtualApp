package com.lody.virtual.client.core;

import com.lody.virtual.client.hook.proxies.oem.vivo.PhysicalFlingManagerStub;
import com.lody.virtual.client.hook.proxies.oem.vivo.PopupCameraManagerStub;
import com.lody.virtual.client.hook.proxies.oem.vivo.SuperResolutionManagerStub;
import com.lody.virtual.client.hook.proxies.oem.vivo.SystemDefenceManagerStub;
import com.lody.virtual.client.hook.proxies.oem.vivo.VivoPermissionServiceStub;
import com.lody.virtual.client.interfaces.IInjector;
import mirror.oem.vivo.IPhysicalFlingManagerStub;
import mirror.oem.vivo.IPopupCameraManager;
import mirror.oem.vivo.ISuperResolutionManager;
import mirror.oem.vivo.ISystemDefenceManager;
import mirror.oem.vivo.IVivoPermissonService;

public class OemInjectManager {
  private static void injectVivo(InvocationStubManager paramInvocationStubManager) {
    if (IPhysicalFlingManagerStub.TYPE != null)
      paramInvocationStubManager.addInjector((IInjector)new PhysicalFlingManagerStub()); 
    if (IPopupCameraManager.TYPE != null)
      paramInvocationStubManager.addInjector((IInjector)new PopupCameraManagerStub()); 
    if (ISuperResolutionManager.TYPE != null)
      paramInvocationStubManager.addInjector((IInjector)new SuperResolutionManagerStub()); 
    if (ISystemDefenceManager.TYPE != null)
      paramInvocationStubManager.addInjector((IInjector)new SystemDefenceManagerStub()); 
    if (IVivoPermissonService.TYPE != null)
      paramInvocationStubManager.addInjector((IInjector)new VivoPermissionServiceStub()); 
  }
  
  public static void oemInject(InvocationStubManager paramInvocationStubManager) {
    injectVivo(paramInvocationStubManager);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\core\OemInjectManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */