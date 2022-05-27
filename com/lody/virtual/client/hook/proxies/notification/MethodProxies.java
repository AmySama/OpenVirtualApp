package com.lody.virtual.client.hook.proxies.notification;

import android.app.Notification;
import android.os.Build;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import com.lody.virtual.client.ipc.VNotificationManager;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.ArrayUtils;
import java.lang.reflect.Method;

class MethodProxies {
  static class AreNotificationsEnabledForPackage extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      String str = (String)param1VarArgs[0];
      return getHostPkg().equals(str) ? param1Method.invoke(param1Object, param1VarArgs) : Boolean.valueOf(VNotificationManager.get().areNotificationsEnabledForPackage(str, getAppUserId()));
    }
    
    public String getMethodName() {
      return "areNotificationsEnabledForPackage";
    }
  }
  
  static class CancelAllNotifications extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      String str = MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      if (VirtualCore.get().isAppInstalled(str)) {
        VNotificationManager.get().cancelAllNotification(str, getAppUserId());
        return Integer.valueOf(0);
      } 
      replaceLastUserId(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "cancelAllNotifications";
    }
  }
  
  static class CancelNotificationWithTag extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      byte b2;
      boolean bool = BuildCompat.isR();
      byte b1 = 2;
      if (bool) {
        b2 = 3;
      } else {
        b2 = 2;
      } 
      if (!BuildCompat.isR())
        b1 = 1; 
      String str1 = MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      replaceLastUserId(param1VarArgs);
      if (getHostPkg().equals(str1))
        return param1Method.invoke(param1Object, param1VarArgs); 
      String str2 = (String)param1VarArgs[b1];
      int i = ((Integer)param1VarArgs[b2]).intValue();
      i = VNotificationManager.get().dealNotificationId(i, str1, str2, getAppUserId());
      param1VarArgs[b1] = VNotificationManager.get().dealNotificationTag(i, str1, str2, getAppUserId());
      param1VarArgs[b2] = Integer.valueOf(i);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "cancelNotificationWithTag";
    }
  }
  
  static class EnqueueNotification extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      String str = (String)param1VarArgs[0];
      replaceLastUserId(param1VarArgs);
      if (getHostPkg().equals(str))
        return param1Method.invoke(param1Object, param1VarArgs); 
      int i = ArrayUtils.indexOfFirst(param1VarArgs, Notification.class);
      int j = ArrayUtils.indexOfFirst(param1VarArgs, Integer.class);
      int k = ((Integer)param1VarArgs[j]).intValue();
      k = VNotificationManager.get().dealNotificationId(k, str, null, getAppUserId());
      param1VarArgs[j] = Integer.valueOf(k);
      Notification notification = (Notification)param1VarArgs[i];
      if (!VNotificationManager.get().dealNotification(k, notification, str))
        return Integer.valueOf(0); 
      VNotificationManager.get().addNotification(k, null, str, getAppUserId());
      param1VarArgs[0] = getHostPkg();
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "enqueueNotification";
    }
  }
  
  static class EnqueueNotificationWithTag extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      boolean bool;
      String str1 = (String)param1VarArgs[0];
      replaceLastUserId(param1VarArgs);
      if (getHostPkg().equals(str1))
        return param1Method.invoke(param1Object, param1VarArgs); 
      int i = ArrayUtils.indexOfFirst(param1VarArgs, Notification.class);
      int j = ArrayUtils.indexOfFirst(param1VarArgs, Integer.class);
      if (Build.VERSION.SDK_INT >= 18) {
        bool = true;
      } else {
        bool = true;
      } 
      int k = ((Integer)param1VarArgs[j]).intValue();
      String str2 = (String)param1VarArgs[bool];
      k = VNotificationManager.get().dealNotificationId(k, str1, str2, getAppUserId());
      String str3 = VNotificationManager.get().dealNotificationTag(k, str1, str2, getAppUserId());
      param1VarArgs[j] = Integer.valueOf(k);
      param1VarArgs[bool] = str3;
      Notification notification = (Notification)param1VarArgs[i];
      if (!VNotificationManager.get().dealNotification(k, notification, str1))
        return Integer.valueOf(0); 
      VNotificationManager.get().addNotification(k, str3, str1, getAppUserId());
      param1VarArgs[0] = getHostPkg();
      if (Build.VERSION.SDK_INT >= 18 && param1VarArgs[1] instanceof String)
        param1VarArgs[1] = getHostPkg(); 
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "enqueueNotificationWithTag";
    }
  }
  
  static class EnqueueNotificationWithTagPriority extends EnqueueNotificationWithTag {
    public String getMethodName() {
      return "enqueueNotificationWithTagPriority";
    }
  }
  
  static class GetAppActiveNotifications extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1VarArgs[0] = getHostPkg();
      replaceLastUserId(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getAppActiveNotifications";
    }
  }
  
  static class SetNotificationsEnabledForPackage extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      String str = (String)param1VarArgs[0];
      if (getHostPkg().equals(str))
        return param1Method.invoke(param1Object, param1VarArgs); 
      boolean bool = ((Boolean)param1VarArgs[ArrayUtils.indexOfFirst(param1VarArgs, Boolean.class)]).booleanValue();
      VNotificationManager.get().setNotificationsEnabledForPackage(str, bool, getAppUserId());
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "setNotificationsEnabledForPackage";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\notification\MethodProxies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */