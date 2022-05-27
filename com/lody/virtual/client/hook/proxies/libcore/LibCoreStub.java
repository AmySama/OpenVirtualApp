package com.lody.virtual.client.hook.proxies.libcore;

import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.MethodInvocationProxy;
import com.lody.virtual.client.hook.base.MethodInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceUidMethodProxy;
import mirror.libcore.io.ForwardingOs;
import mirror.libcore.io.Libcore;

@Inject(MethodProxies.class)
public class LibCoreStub extends MethodInvocationProxy<MethodInvocationStub<Object>> {
  public LibCoreStub() {
    super(new MethodInvocationStub(getOs()));
  }
  
  private static Object getOs() {
    Object object1 = Libcore.os.get();
    Object object2 = object1;
    if (ForwardingOs.os != null) {
      Object object = ForwardingOs.os.get(object1);
      object2 = object1;
      if (object != null)
        object2 = object; 
    } 
    return object2;
  }
  
  public void inject() {
    Libcore.os.set(getInvocationStub().getProxyInterface());
  }
  
  public boolean isEnvBad() {
    boolean bool;
    if (getOs() != getInvocationStub().getProxyInterface()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceUidMethodProxy("chown", 1));
    addMethodProxy((MethodProxy)new ReplaceUidMethodProxy("fchown", 1));
    addMethodProxy((MethodProxy)new ReplaceUidMethodProxy("getpwuid", 0));
    addMethodProxy((MethodProxy)new ReplaceUidMethodProxy("lchown", 1));
    addMethodProxy((MethodProxy)new ReplaceUidMethodProxy("setuid", 0));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\libcore\LibCoreStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */