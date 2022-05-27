package com.lody.virtual.client.hook.proxies.display;

import android.os.IInterface;
import com.lody.virtual.client.hook.base.MethodInvocationProxy;
import com.lody.virtual.client.hook.base.MethodInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import mirror.RefStaticMethod;
import mirror.android.hardware.display.DisplayManagerGlobal;

public class DisplayStub extends MethodInvocationProxy<MethodInvocationStub<IInterface>> {
  public DisplayStub() {
    super(new MethodInvocationStub(DisplayManagerGlobal.mDm.get(DisplayManagerGlobal.getInstance.call(new Object[0]))));
  }
  
  public void inject() throws Throwable {
    Object object = DisplayManagerGlobal.getInstance.call(new Object[0]);
    DisplayManagerGlobal.mDm.set(object, getInvocationStub().getProxyInterface());
  }
  
  public boolean isEnvBad() {
    RefStaticMethod refStaticMethod = DisplayManagerGlobal.getInstance;
    boolean bool = false;
    Object object = refStaticMethod.call(new Object[0]);
    if ((IInterface)DisplayManagerGlobal.mDm.get(object) != getInvocationStub().getProxyInterface())
      bool = true; 
    return bool;
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("createVirtualDisplay"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\display\DisplayStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */