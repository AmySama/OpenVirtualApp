package com.lody.virtual.client.hook.proxies.window;

import android.os.Build;
import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import mirror.android.view.Display;
import mirror.android.view.IWindowManager;
import mirror.android.view.WindowManagerGlobal;
import mirror.com.android.internal.policy.PhoneWindow;

@Inject(MethodProxies.class)
public class WindowManagerStub extends BinderInvocationProxy {
  public WindowManagerStub() {
    super(IWindowManager.Stub.asInterface, "window");
  }
  
  public void inject() throws Throwable {
    super.inject();
    if (Build.VERSION.SDK_INT >= 17) {
      if (WindowManagerGlobal.sWindowManagerService != null)
        WindowManagerGlobal.sWindowManagerService.set(((BinderInvocationStub)getInvocationStub()).getProxyInterface()); 
    } else if (Display.sWindowManager != null) {
      Display.sWindowManager.set(((BinderInvocationStub)getInvocationStub()).getProxyInterface());
    } 
    if (PhoneWindow.TYPE != null)
      PhoneWindow.sWindowManager.set(((BinderInvocationStub)getInvocationStub()).getProxyInterface()); 
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new StaticMethodProxy("addAppToken"));
    addMethodProxy((MethodProxy)new StaticMethodProxy("setScreenCaptureDisabled"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\window\WindowManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */