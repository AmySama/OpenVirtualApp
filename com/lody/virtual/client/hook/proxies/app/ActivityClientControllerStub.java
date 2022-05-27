package com.lody.virtual.client.hook.proxies.app;

import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.MethodInvocationProxy;
import com.lody.virtual.client.hook.base.MethodInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.client.hook.proxies.am.MethodProxies;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.os.VUserHandle;
import java.lang.reflect.Method;
import mirror.RefStaticMethod;
import mirror.android.app.ActivityClient;
import mirror.android.util.Singleton;

@Inject(MethodProxies.class)
public class ActivityClientControllerStub extends MethodInvocationProxy<MethodInvocationStub<IInterface>> {
  private static IInterface sActivityClientControllerProxy;
  
  public ActivityClientControllerStub() {
    super(new MethodInvocationStub(ActivityClient.getActivityClientController.call(new Object[0])));
  }
  
  public static IInterface getProxyInterface() {
    return sActivityClientControllerProxy;
  }
  
  public void inject() {
    if (ActivityClient.INTERFACE_SINGLETON != null) {
      if (ActivityClient.ActivityClientControllerSingleton.mKnownInstance != null)
        ActivityClient.ActivityClientControllerSingleton.mKnownInstance.set(ActivityClient.INTERFACE_SINGLETON.get(), getInvocationStub().getProxyInterface()); 
      Singleton.mInstance.set(ActivityClient.INTERFACE_SINGLETON.get(), getInvocationStub().getProxyInterface());
      sActivityClientControllerProxy = (IInterface)getInvocationStub().getProxyInterface();
    } 
  }
  
  public boolean isEnvBad() {
    RefStaticMethod refStaticMethod = ActivityClient.getActivityClientController;
    boolean bool = false;
    if (refStaticMethod.call(new Object[0]) != getInvocationStub().getProxyInterface())
      bool = true; 
    return bool;
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new StaticMethodProxy("activityDestroyed") {
          public Object afterCall(Object param1Object1, Method param1Method, Object[] param1ArrayOfObject, Object param1Object2) throws Throwable {
            IBinder iBinder = (IBinder)param1ArrayOfObject[0];
            VActivityManager.get().onActivityDestroy(iBinder);
            return super.afterCall(param1Object1, param1Method, param1ArrayOfObject, param1Object2);
          }
        });
    addMethodProxy((MethodProxy)new StaticMethodProxy("activityResumed") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            IBinder iBinder = (IBinder)param1VarArgs[0];
            VActivityManager.get().onActivityResumed(iBinder);
            return super.call(param1Object, param1Method, param1VarArgs);
          }
        });
    addMethodProxy((MethodProxy)new StaticMethodProxy("finishActivity") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            IBinder iBinder = (IBinder)param1VarArgs[0];
            Intent intent = (Intent)param1VarArgs[2];
            if (intent != null)
              param1VarArgs[2] = ComponentUtils.processOutsideIntent(VUserHandle.myUserId(), VirtualCore.get().isExtPackage(), intent); 
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
    if (BuildCompat.isSamsung())
      addMethodProxy((MethodProxy)new StaticMethodProxy("startAppLockService") {
            public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) {
              return Integer.valueOf(0);
            }
          }); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\app\ActivityClientControllerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */