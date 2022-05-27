package com.lody.virtual.client.hook.proxies.telecom;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import java.lang.reflect.Method;
import mirror.com.android.internal.telecom.ITelecomService;

public class TelecomManagerStub extends BinderInvocationProxy {
  public TelecomManagerStub() {
    super(ITelecomService.Stub.TYPE, "telecom");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new StaticMethodProxy("registerPhoneAccount") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            return Integer.valueOf(0);
          }
        });
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("showInCallScreen"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getDefaultOutgoingPhoneAccount"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getCallCapablePhoneAccounts"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getSelfManagedPhoneAccounts"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getPhoneAccountsSupportingScheme"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isVoiceMailNumber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getVoiceMailNumber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getLine1Number"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("silenceRinger"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isInCall"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isInManagedCall"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isRinging"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("acceptRingingCall"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("acceptRingingCallWithVideoState("));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("cancelMissedCallsNotification"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("handlePinMmi"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("handlePinMmiForPhoneAccount"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getAdnUriForPhoneAccount"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isTtySupported"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getCurrentTtyMode"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("placeCall"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\telecom\TelecomManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */