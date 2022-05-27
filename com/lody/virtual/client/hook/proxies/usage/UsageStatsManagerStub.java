package com.lody.virtual.client.hook.proxies.usage;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.client.ipc.VActivityManager;
import java.lang.reflect.Method;
import mirror.android.app.IUsageStatsManager;

public class UsageStatsManagerStub extends BinderInvocationProxy {
  public UsageStatsManagerStub() {
    super(IUsageStatsManager.Stub.asInterface, "usagestats");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getAppStandbyBucket"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("queryUsageStats"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("queryConfigurations"));
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("queryEvents"));
    addMethodProxy((MethodProxy)new StaticMethodProxy("setAppInactive") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) {
            boolean bool;
            if (param1VarArgs.length > 2) {
              bool = ((Integer)param1VarArgs[2]).intValue();
            } else {
              bool = false;
            } 
            VActivityManager.get().setAppInactive((String)param1VarArgs[0], ((Boolean)param1VarArgs[1]).booleanValue(), bool);
            return Integer.valueOf(0);
          }
        });
    addMethodProxy((MethodProxy)new StaticMethodProxy("isAppInactive") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) {
            boolean bool;
            if (param1VarArgs.length > 1) {
              bool = ((Integer)param1VarArgs[1]).intValue();
            } else {
              bool = false;
            } 
            return Boolean.valueOf(VActivityManager.get().isAppInactive((String)param1VarArgs[0], bool));
          }
        });
    addMethodProxy((MethodProxy)new ReplacePkgAndUserIdMethodProxy("whitelistAppTemporarily"));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("setAppStandbyBucket", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("setAppStandbyBuckets", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("registerAppUsageObserver", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("unregisterAppUsageObserver", null));
  }
  
  private class ReplacePkgAndUserIdMethodProxy extends ReplaceLastPkgMethodProxy {
    public ReplacePkgAndUserIdMethodProxy(String param1String) {
      super(param1String);
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (param1VarArgs[param1VarArgs.length - 1] instanceof Integer)
        param1VarArgs[param1VarArgs.length - 1] = Integer.valueOf(0); 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxie\\usage\UsageStatsManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */