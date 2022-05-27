package com.lody.virtual.client.hook.proxies.clipboard;

import android.content.ClipboardManager;
import android.os.Build;
import android.os.IInterface;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgAndLastUserIdMethodProxy;
import mirror.android.content.ClipboardManager;
import mirror.android.content.ClipboardManagerOreo;

public class ClipBoardStub extends BinderInvocationProxy {
  public ClipBoardStub() {
    super(getInterface(), "clipboard");
  }
  
  private static IInterface getInterface() {
    if (ClipboardManager.getService != null)
      return (IInterface)ClipboardManager.getService.call(new Object[0]); 
    if (ClipboardManagerOreo.mService != null) {
      ClipboardManager clipboardManager = (ClipboardManager)VirtualCore.get().getContext().getSystemService("clipboard");
      return (IInterface)ClipboardManagerOreo.mService.get(clipboardManager);
    } 
    return (ClipboardManagerOreo.sService != null) ? (IInterface)ClipboardManagerOreo.sService.get() : null;
  }
  
  public void inject() throws Throwable {
    super.inject();
    if (ClipboardManagerOreo.mService != null) {
      ClipboardManager clipboardManager = (ClipboardManager)VirtualCore.get().getContext().getSystemService("clipboard");
      ClipboardManagerOreo.mService.set(clipboardManager, ((BinderInvocationStub)getInvocationStub()).getProxyInterface());
    } else if (ClipboardManagerOreo.sService != null) {
      ClipboardManagerOreo.sService.set(((BinderInvocationStub)getInvocationStub()).getProxyInterface());
    } 
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgAndLastUserIdMethodProxy("getPrimaryClip"));
    if (Build.VERSION.SDK_INT > 17) {
      addMethodProxy((MethodProxy)new ReplaceCallingPkgAndLastUserIdMethodProxy("setPrimaryClip"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgAndLastUserIdMethodProxy("getPrimaryClipDescription"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgAndLastUserIdMethodProxy("hasPrimaryClip"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgAndLastUserIdMethodProxy("addPrimaryClipChangedListener"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgAndLastUserIdMethodProxy("removePrimaryClipChangedListener"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgAndLastUserIdMethodProxy("hasClipboardText"));
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\clipboard\ClipBoardStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */