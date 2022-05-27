package com.lody.virtual.client.hook.proxies.atm;

import android.content.Intent;
import android.os.IBinder;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.os.VUserHandle;
import java.lang.reflect.Method;
import mirror.android.app.IActivityTaskManager;
import mirror.android.util.Singleton;

@Inject(MethodProxies.class)
public class ActivityTaskManagerStub extends BinderInvocationProxy {
  public ActivityTaskManagerStub() {
    super(IActivityTaskManager.Stub.asInterface, "activity_task");
    try {
      Object object = Reflect.on("android.app.ActivityTaskManager").field("IActivityTaskManagerSingleton").get();
      Singleton.mInstance.set(object, ((BinderInvocationStub)getInvocationStub()).getProxyInterface());
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\atm\ActivityTaskManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */