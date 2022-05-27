package com.lody.virtual.client.hook.proxies.isms;

import android.os.Build;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceSpecPkgMethodProxy;
import mirror.com.android.internal.telephony.ISms;

public class ISmsStub extends BinderInvocationProxy {
  public ISmsStub() {
    super(ISms.Stub.asInterface, "isms");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    if (Build.VERSION.SDK_INT >= 23) {
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("getAllMessagesFromIccEfForSubscriber", 1));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("updateMessageOnIccEfForSubscriber", 1));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("copyMessageToIccEfForSubscriber", 1));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("sendDataForSubscriber", 1));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("sendDataForSubscriberWithSelfPermissions", 1));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("sendTextForSubscriber", 1));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("sendTextForSubscriberWithSelfPermissions", 1));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("sendMultipartTextForSubscriber", 1));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("sendStoredText", 1));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("sendStoredMultipartText", 1));
    } else if (Build.VERSION.SDK_INT >= 21) {
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getAllMessagesFromIccEf"));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("getAllMessagesFromIccEfForSubscriber", 1));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("updateMessageOnIccEf"));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("updateMessageOnIccEfForSubscriber", 1));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("copyMessageToIccEf"));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("copyMessageToIccEfForSubscriber", 1));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("sendData"));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("sendDataForSubscriber", 1));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("sendText"));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("sendTextForSubscriber", 1));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("sendMultipartText"));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("sendMultipartTextForSubscriber", 1));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("sendStoredText", 1));
      addMethodProxy((MethodProxy)new ReplaceSpecPkgMethodProxy("sendStoredMultipartText", 1));
    } else if (Build.VERSION.SDK_INT >= 18) {
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getAllMessagesFromIccEf"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("updateMessageOnIccEf"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("copyMessageToIccEf"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("sendData"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("sendText"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("sendMultipartText"));
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\isms\ISmsStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */