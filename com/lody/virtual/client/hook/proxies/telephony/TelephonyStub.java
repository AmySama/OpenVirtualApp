package com.lody.virtual.client.hook.proxies.telephony;

import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import com.lody.virtual.helper.compat.BuildCompat;
import mirror.com.android.internal.telephony.ITelephony;

public class TelephonyStub extends BinderInvocationProxy {
  public TelephonyStub() {
    super(ITelephony.Stub.asInterface, "phone");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getLine1NumberForDisplay"));
    addMethodProxy((MethodProxy)new MethodProxies.GetCellLocation());
    addMethodProxy((MethodProxy)new MethodProxies.GetAllCellInfoUsingSubId());
    addMethodProxy((MethodProxy)new MethodProxies.GetAllCellInfo());
    addMethodProxy((MethodProxy)new MethodProxies.GetNeighboringCellInfo());
    addMethodProxy((MethodProxy)new MethodProxies.GetDeviceId());
    addMethodProxy((MethodProxy)new MethodProxies.GetImeiForSlot());
    addMethodProxy((MethodProxy)new MethodProxies.GetMeidForSlot());
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("call"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("isSimPinEnabled"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getCdmaEriIconIndex"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getCdmaEriIconIndexForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getCdmaEriIconMode"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getCdmaEriIconModeForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getCdmaEriText"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getCdmaEriTextForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getNetworkTypeForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getDataNetworkType"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getDataNetworkTypeForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getVoiceNetworkTypeForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getLteOnCdmaMode"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getLteOnCdmaModeForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getCalculatedPreferredNetworkType"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getPcscfAddress"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getLine1AlphaTagForDisplay"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getMergedSubscriberIds"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getRadioAccessFamily"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isVideoCallingEnabled"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getDeviceSoftwareVersionForSlot"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getServiceStateForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getVisualVoicemailPackageName"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("enableVisualVoicemailSmsFilter"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("disableVisualVoicemailSmsFilter"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getVisualVoicemailSmsFilterSettings"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("sendVisualVoicemailSmsForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getVoiceActivationState"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getDataActivationState"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getVoiceMailAlphaTagForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("sendDialerSpecialCode"));
    if (BuildCompat.isOreo()) {
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("setVoicemailVibrationEnabled"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("setVoicemailRingtoneUri"));
    } 
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isOffhook"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("isOffhookForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isRinging"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("isRingingForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isIdle"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("isIdleForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isRadioOn"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("isRadioOnForSubscriber"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getClientRequestStats"));
    if (!VirtualCore.get().isSystemApp()) {
      addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getVisualVoicemailSettings", null));
      addMethodProxy((MethodProxy)new ResultStaticMethodProxy("setDataEnabled", Integer.valueOf(0)));
      addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getDataEnabled", Boolean.valueOf(false)));
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\telephony\TelephonyStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */