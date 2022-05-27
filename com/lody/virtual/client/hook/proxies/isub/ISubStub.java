package com.lody.virtual.client.hook.proxies.isub;

import android.os.Build;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import mirror.com.android.internal.telephony.ISub;

public class ISubStub extends BinderInvocationProxy {
  public ISubStub() {
    super(ISub.Stub.asInterface, "isub");
  }
  
  protected void onBindMethods() {
    String str;
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getActiveSubInfoCount"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getSubscriptionProperty"));
    if (Build.VERSION.SDK_INT >= 24) {
      str = "getSimStateForSlotIdx";
    } else {
      str = "getSimStateForSubscriber";
    } 
    addMethodProxy((MethodProxy)new StaticMethodProxy(str));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getActiveSubscriptionInfo"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getActiveSubscriptionInfoForIccId"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getActiveSubscriptionInfoForSimSlotIndex"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getAllSubInfoList"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getAllSubInfoCount"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getActiveSubscriptionInfoList"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getAvailableSubscriptionInfoList"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getAccessibleSubscriptionInfoList"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("isActiveSubId"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getOpportunisticSubscriptions"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("createSubscriptionGroup"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("removeSubscriptionsFromGroup"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\isub\ISubStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */