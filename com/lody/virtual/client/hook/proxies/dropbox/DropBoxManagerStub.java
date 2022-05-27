package com.lody.virtual.client.hook.proxies.dropbox;

import android.os.DropBoxManager;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import mirror.android.os.DropBoxManager;
import mirror.com.android.internal.os.IDropBoxManagerService;

public class DropBoxManagerStub extends BinderInvocationProxy {
  public DropBoxManagerStub() {
    super(IDropBoxManagerService.Stub.asInterface, "dropbox");
  }
  
  public void inject() throws Throwable {
    super.inject();
    DropBoxManager dropBoxManager = (DropBoxManager)VirtualCore.get().getContext().getSystemService("dropbox");
    try {
      DropBoxManager.mService.set(dropBoxManager, ((BinderInvocationStub)getInvocationStub()).getProxyInterface());
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getNextEntry", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getNextEntryWithAttribution", null));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\dropbox\DropBoxManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */