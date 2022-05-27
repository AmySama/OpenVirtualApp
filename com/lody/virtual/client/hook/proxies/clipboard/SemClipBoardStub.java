package com.lody.virtual.client.hook.proxies.clipboard;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import mirror.android.sec.clipboard.IClipboardService;

public class SemClipBoardStub extends BinderInvocationProxy {
  public SemClipBoardStub() {
    super(IClipboardService.Stub.asInterface, "semclipboard");
  }
  
  public void inject() throws Throwable {
    super.inject();
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getClipData"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\clipboard\SemClipBoardStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */