package com.lody.virtual.client.hook.proxies.battery_stats;

import android.os.health.SystemHealthManager;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastUidMethodProxy;
import java.lang.reflect.Method;
import mirror.android.os.health.SystemHealthManager;
import mirror.com.android.internal.app.IBatteryStats;

public class BatteryStatsHub extends BinderInvocationProxy {
  private static final String SERVICE_NAME = "batterystats";
  
  public BatteryStatsHub() {
    super(IBatteryStats.Stub.asInterface, "batterystats");
  }
  
  public void inject() throws Throwable {
    super.inject();
    if (SystemHealthManager.mBatteryStats != null) {
      SystemHealthManager systemHealthManager = (SystemHealthManager)VirtualCore.get().getContext().getSystemService("systemhealth");
      SystemHealthManager.mBatteryStats.set(systemHealthManager, ((BinderInvocationStub)getInvocationStub()).getProxyInterface());
    } 
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceLastUidMethodProxy("takeUidSnapshot") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) {
            try {
              return super.call(param1Object, param1Method, param1VarArgs);
            } finally {
              param1Object = null;
            } 
          }
        });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\battery_stats\BatteryStatsHub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */