package com.lody.virtual.client.hook.proxies.phonesubinfo;

import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import mirror.com.android.internal.telephony.IPhoneSubInfo;

@Inject(MethodProxies.class)
public class PhoneSubInfoStub extends BinderInvocationProxy {
  public PhoneSubInfoStub() {
    super(IPhoneSubInfo.Stub.asInterface, "iphonesubinfo");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getNaiForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getDeviceSvn"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getDeviceSvnUsingSubId"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getSubscriberId"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getSubscriberIdForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getGroupIdLevel1"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getGroupIdLevel1ForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getLine1AlphaTag"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getLine1AlphaTagForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getMsisdn"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getMsisdnForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getVoiceMailNumber"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getVoiceMailNumberForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getVoiceMailAlphaTag"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getVoiceMailAlphaTagForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getLine1Number"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getLine1NumberForSubscriber"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\phonesubinfo\PhoneSubInfoStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */