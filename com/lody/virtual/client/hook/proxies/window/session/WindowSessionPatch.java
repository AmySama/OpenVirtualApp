package com.lody.virtual.client.hook.proxies.window.session;

import android.os.IInterface;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.MethodInvocationProxy;
import com.lody.virtual.client.hook.base.MethodInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import java.lang.reflect.Method;
import mirror.android.view.WindowManagerGlobal;

public class WindowSessionPatch extends MethodInvocationProxy<MethodInvocationStub<IInterface>> {
  private static final int ADD_PERMISSION_DENIED;
  
  static {
    byte b;
    if (WindowManagerGlobal.ADD_PERMISSION_DENIED != null) {
      b = WindowManagerGlobal.ADD_PERMISSION_DENIED.get();
    } else {
      b = -8;
    } 
    ADD_PERMISSION_DENIED = b;
  }
  
  public WindowSessionPatch(IInterface paramIInterface) {
    super(new MethodInvocationStub(paramIInterface));
  }
  
  public void inject() throws Throwable {}
  
  public boolean isEnvBad() {
    boolean bool;
    if (getInvocationStub().getProxyInterface() != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void onBindMethods() {
    addMethodProxy((MethodProxy)new BaseMethodProxy("add"));
    addMethodProxy((MethodProxy)new AddToDisplay("addToDisplayAsUser"));
    addMethodProxy((MethodProxy)new AddToDisplay("addToDisplay"));
    addMethodProxy((MethodProxy)new BaseMethodProxy("addToDisplayWithoutInputChannel"));
    addMethodProxy((MethodProxy)new BaseMethodProxy("addWithoutInputChannel"));
    addMethodProxy((MethodProxy)new BaseMethodProxy("relayout"));
  }
  
  class AddToDisplay extends BaseMethodProxy {
    public AddToDisplay(String param1String) {
      super(param1String);
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return (isDrawOverlays() && VirtualCore.getConfig().isDisableDrawOverlays(getAppPkg())) ? Integer.valueOf(WindowSessionPatch.ADD_PERMISSION_DENIED) : super.call(param1Object, param1Method, param1VarArgs);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\window\session\WindowSessionPatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */