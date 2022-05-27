package com.lody.virtual.client.hook.proxies.notification;

import android.os.Build;
import android.os.IInterface;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.Constants;
import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.MethodInvocationProxy;
import com.lody.virtual.client.hook.base.MethodInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgAndLastUserIdMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import com.lody.virtual.helper.compat.BuildCompat;
import java.lang.reflect.Method;
import mirror.RefStaticMethod;
import mirror.android.app.NotificationManager;
import mirror.android.widget.Toast;

@Inject(MethodProxies.class)
public class NotificationManagerStub extends MethodInvocationProxy<MethodInvocationStub<IInterface>> {
  public NotificationManagerStub() {
    super(new MethodInvocationStub(NotificationManager.getService.call(new Object[0])));
  }
  
  public void inject() throws Throwable {
    NotificationManager.sService.set(getInvocationStub().getProxyInterface());
    Toast.sService.set(getInvocationStub().getProxyInterface());
  }
  
  public boolean isEnvBad() {
    RefStaticMethod refStaticMethod = NotificationManager.getService;
    boolean bool = false;
    if (refStaticMethod.call(new Object[0]) != getInvocationStub().getProxyInterface())
      bool = true; 
    return bool;
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("enqueueToast"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("enqueueToastForLog"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("enqueueToastEx"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("cancelToast"));
    if (Build.VERSION.SDK_INT >= 24) {
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("removeAutomaticZenRules"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getImportance"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("areNotificationsEnabled"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("setNotificationPolicy"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getNotificationPolicy"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("setNotificationPolicyAccessGranted"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isNotificationPolicyAccessGranted"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("isNotificationPolicyAccessGrantedForPackage"));
    } 
    if ("samsung".equalsIgnoreCase(Build.BRAND) || "samsung".equalsIgnoreCase(Build.MANUFACTURER))
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("removeEdgeNotification")); 
    if (BuildCompat.isOreo()) {
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("createNotificationChannelGroups"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getNotificationChannelGroups"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("deleteNotificationChannelGroup"));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("createNotificationChannels"));
      if (BuildCompat.isQ()) {
        addMethodProxy(new MethodProxy() {
              public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
                param1VarArgs[0] = VirtualCore.get().getHostPkg();
                param1VarArgs[1] = VirtualCore.get().getHostPkg();
                replaceLastUserId(param1VarArgs);
                return super.call(param1Object, param1Method, param1VarArgs);
              }
              
              public String getMethodName() {
                return "getNotificationChannels";
              }
            });
      } else {
        addMethodProxy((MethodProxy)new ReplaceCallingPkgAndLastUserIdMethodProxy("getNotificationChannels"));
      } 
      if (BuildCompat.isQ()) {
        addMethodProxy(new MethodProxy() {
              public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
                param1VarArgs[0] = VirtualCore.get().getHostPkg();
                param1VarArgs[2] = VirtualCore.get().getHostPkg();
                replaceLastUserId(param1VarArgs);
                return super.call(param1Object, param1Method, param1VarArgs);
              }
              
              public String getMethodName() {
                return "getNotificationChannel";
              }
            });
        addMethodProxy((MethodProxy)new ResultStaticMethodProxy("setNotificationDelegate", null));
        addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getNotificationDelegate", null));
        addMethodProxy((MethodProxy)new ResultStaticMethodProxy("canNotifyAsPackage", Boolean.valueOf(false)));
      } else {
        addMethodProxy((MethodProxy)new ReplaceCallingPkgAndLastUserIdMethodProxy("getNotificationChannel"));
      } 
      addMethodProxy(new MethodProxy() {
            public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
              MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
              if (param1VarArgs != null && param1VarArgs.length >= 2 && param1VarArgs[1] instanceof String && Constants.NOTIFICATION_DAEMON_CHANNEL.equals(param1VarArgs[1]))
                return null; 
              try {
                return super.call(param1Object, param1Method, param1VarArgs);
              } catch (Exception exception) {
                exception.printStackTrace();
                return null;
              } 
            }
            
            public String getMethodName() {
              return "deleteNotificationChannel";
            }
          });
    } 
    if (BuildCompat.isPie())
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getNotificationChannelGroup")); 
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("setInterruptionFilter"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getPackageImportance"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("shouldGroupPkg"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getBubblePreferenceForPackage"));
    addMethodProxy((MethodProxy)new StaticMethodProxy("getConversationNotificationChannel") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            param1VarArgs[0] = VirtualCore.get().getHostPkg();
            param1VarArgs[2] = VirtualCore.get().getHostPkg();
            return super.call(param1Object, param1Method, param1VarArgs);
          }
        });
    addMethodProxy((MethodProxy)new StaticMethodProxy("getConversationNotificationChannel") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            param1VarArgs[0] = VirtualCore.get().getHostPkg();
            param1VarArgs[2] = VirtualCore.get().getHostPkg();
            return super.call(param1Object, param1Method, param1VarArgs);
          }
        });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\notification\NotificationManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */