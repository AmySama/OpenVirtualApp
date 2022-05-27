package com.lody.virtual.client.hook.proxies.imms;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceSpecPkgMethodProxy;
import mirror.com.android.internal.telephony.IMms;

public class MmsStub extends BinderInvocationProxy {
  public MmsStub() {
    super(IMms.Stub.asInterface, "imms");
  }
  
  protected void onBindMethods() {
    addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("sendMessage", 1));
    addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("downloadMessage", 1));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("importTextMessage"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("importMultimediaMessage"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("deleteStoredMessage"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("deleteStoredConversation"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("updateStoredMessageStatus"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("archiveStoredConversation"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("addTextMessageDraft"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("addMultimediaMessageDraft"));
    addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("sendStoredMessage", 1));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("setAutoPersisting"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\imms\MmsStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */