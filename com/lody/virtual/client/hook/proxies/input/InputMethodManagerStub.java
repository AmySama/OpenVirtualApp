package com.lody.virtual.client.hook.proxies.input;

import android.os.IInterface;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastUserIdMethodProxy;
import mirror.com.android.internal.view.inputmethod.InputMethodManager;

@Inject(MethodProxies.class)
public class InputMethodManagerStub extends BinderInvocationProxy {
  public InputMethodManagerStub() {
    super((IInterface)InputMethodManager.mService.get(VirtualCore.get().getContext().getSystemService("input_method")), "input_method");
  }
  
  public void inject() throws Throwable {
    Object object = getContext().getSystemService("input_method");
    InputMethodManager.mService.set(object, ((BinderInvocationStub)getInvocationStub()).getProxyInterface());
    ((BinderInvocationStub)getInvocationStub()).replaceService("input_method");
  }
  
  public boolean isEnvBad() {
    boolean bool;
    Object object = getContext().getSystemService("input_method");
    if (InputMethodManager.mService.get(object) != ((BinderInvocationStub)getInvocationStub()).getBaseInterface()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceLastUserIdMethodProxy("getInputMethodList"));
    addMethodProxy((MethodProxy)new ReplaceLastUserIdMethodProxy("getEnabledInputMethodList"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\input\InputMethodManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */