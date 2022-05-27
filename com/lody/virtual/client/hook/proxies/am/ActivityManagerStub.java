package com.lody.virtual.client.hook.proxies.am;

import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodInvocationProxy;
import com.lody.virtual.client.hook.base.MethodInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.compat.BuildCompat;
import java.lang.reflect.Method;
import java.util.Map;
import mirror.RefStaticMethod;
import mirror.android.app.ActivityManagerNative;
import mirror.android.app.ActivityManagerOreo;
import mirror.android.app.IActivityManager;
import mirror.android.os.ServiceManager;
import mirror.android.util.Singleton;

@Inject(MethodProxies.class)
public class ActivityManagerStub extends MethodInvocationProxy<MethodInvocationStub<IInterface>> {
  public ActivityManagerStub() {
    super(new MethodInvocationStub(ActivityManagerNative.getDefault.call(new Object[0])));
  }
  
  public void inject() {
    if (BuildCompat.isOreo()) {
      Object object = ActivityManagerOreo.IActivityManagerSingleton.get();
      Singleton.mInstance.set(object, getInvocationStub().getProxyInterface());
    } else if (ActivityManagerNative.gDefault.type() == IActivityManager.TYPE) {
      ActivityManagerNative.gDefault.set(getInvocationStub().getProxyInterface());
    } else if (ActivityManagerNative.gDefault.type() == Singleton.TYPE) {
      Object object = ActivityManagerNative.gDefault.get();
      Singleton.mInstance.set(object, getInvocationStub().getProxyInterface());
    } 
    BinderInvocationStub binderInvocationStub = new BinderInvocationStub((IInterface)getInvocationStub().getBaseInterface());
    binderInvocationStub.copyMethodProxies(getInvocationStub());
    ((Map<String, BinderInvocationStub>)ServiceManager.sCache.get()).put("activity", binderInvocationStub);
  }
  
  public boolean isEnvBad() {
    RefStaticMethod refStaticMethod = ActivityManagerNative.getDefault;
    boolean bool = false;
    if (refStaticMethod.call(new Object[0]) != getInvocationStub().getProxyInterface())
      bool = true; 
    return bool;
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    if (VirtualCore.get().isVAppProcess()) {
      addMethodProxy((MethodProxy)new StaticMethodProxy("setRequestedOrientation") {
            public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
              try {
                return super.call(param1Object, param1Method, param1VarArgs);
              } finally {
                param1Object = null;
                param1Object.printStackTrace();
              } 
            }
          });
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getHistoricalProcessExitReasons"));
      addMethodProxy((MethodProxy)new ResultStaticMethodProxy("registerUidObserver", Integer.valueOf(0)));
      addMethodProxy((MethodProxy)new ResultStaticMethodProxy("unregisterUidObserver", Integer.valueOf(0)));
      addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("getAppStartMode"));
      addMethodProxy((MethodProxy)new ResultStaticMethodProxy("updateConfiguration", Integer.valueOf(0)));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("setAppLockedVerifying"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("reportJunkFromApp"));
      addMethodProxy((MethodProxy)new StaticMethodProxy("activityResumed") {
            public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
              IBinder iBinder = (IBinder)param1VarArgs[0];
              VActivityManager.get().onActivityResumed(iBinder);
              return super.call(param1Object, param1Method, param1VarArgs);
            }
          });
      addMethodProxy((MethodProxy)new StaticMethodProxy("activityDestroyed") {
            public Object afterCall(Object param1Object1, Method param1Method, Object[] param1ArrayOfObject, Object param1Object2) throws Throwable {
              IBinder iBinder = (IBinder)param1ArrayOfObject[0];
              VActivityManager.get().onActivityDestroy(iBinder);
              return super.afterCall(param1Object1, param1Method, param1ArrayOfObject, param1Object2);
            }
          });
      addMethodProxy((MethodProxy)new StaticMethodProxy("checkUriPermission") {
            public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
              byte b = 0;
              if (param1VarArgs[0] instanceof android.net.Uri && param1VarArgs[0].toString().equals("content://telephony/carriers/preferapn")) {
                if (!VirtualCore.get().checkSelfPermission("Manifest.permission.WRITE_APN_SETTINGS", VirtualCore.get().isExtPackage()))
                  b = -1; 
                return Integer.valueOf(b);
              } 
              return Integer.valueOf(0);
            }
          });
      addMethodProxy((MethodProxy)new StaticMethodProxy("finishActivity") {
            public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
              IBinder iBinder = (IBinder)param1VarArgs[0];
              VActivityManager.get().onFinishActivity(iBinder);
              return super.call(param1Object, param1Method, param1VarArgs);
            }
            
            public boolean isEnable() {
              return isAppProcess();
            }
          });
      addMethodProxy((MethodProxy)new StaticMethodProxy("finishActivityAffinity") {
            public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) {
              param1Object = param1VarArgs[0];
              return Boolean.valueOf(VActivityManager.get().finishActivityAffinity(getAppUserId(), (IBinder)param1Object));
            }
            
            public boolean isEnable() {
              return isAppProcess();
            }
          });
    } 
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getIntentSenderWithFeature"));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\am\ActivityManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */