package com.lody.virtual.client.hook.proxies.telephony;

import android.os.Build;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceSequencePkgMethodProxy;
import java.lang.reflect.Method;
import mirror.com.android.internal.telephony.ITelephonyRegistry;

public class TelephonyRegistryStub extends BinderInvocationProxy {
  public TelephonyRegistryStub() {
    super(ITelephonyRegistry.Stub.asInterface, "telephony.registry");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("addOnSubscriptionsChangedListener"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("addOnOpportunisticSubscriptionsChangedListener"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("removeOnSubscriptionsChangedListener"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("listen"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("listenWithEventList"));
    addMethodProxy((MethodProxy)new ReplaceSequencePkgMethodProxy("listenForSubscriber", 1) {
          public boolean beforeCall(Object param1Object, Method param1Method, Object... param1VarArgs) {
            if (Build.VERSION.SDK_INT >= 17 && isFakeLocationEnable())
              for (int i = param1VarArgs.length - 1; i > 0; i--) {
                if (param1VarArgs[i] instanceof Integer) {
                  param1VarArgs[i] = Integer.valueOf(((Integer)param1VarArgs[i]).intValue() ^ 0x400 ^ 0x10);
                  break;
                } 
              }  
            return super.beforeCall(param1Object, param1Method, param1VarArgs);
          }
        });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\telephony\TelephonyRegistryStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */