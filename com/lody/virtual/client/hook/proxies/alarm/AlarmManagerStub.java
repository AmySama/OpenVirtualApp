package com.lody.virtual.client.hook.proxies.alarm;

import android.app.AlarmManager;
import android.os.Build;
import android.os.WorkSource;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.helper.utils.ArrayUtils;
import java.lang.reflect.Method;
import mirror.android.app.AlarmManager;
import mirror.android.app.IAlarmManager;

public class AlarmManagerStub extends BinderInvocationProxy {
  public AlarmManagerStub() {
    super(IAlarmManager.Stub.asInterface, "alarm");
  }
  
  public void inject() throws Throwable {
    super.inject();
    AlarmManager alarmManager = (AlarmManager)VirtualCore.get().getContext().getSystemService("alarm");
    if (AlarmManager.mService != null)
      try {
        AlarmManager.mService.set(alarmManager, ((BinderInvocationStub)getInvocationStub()).getProxyInterface());
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy(new Set());
    addMethodProxy(new SetTime());
    addMethodProxy(new SetTimeZone());
  }
  
  private static class GetNextAlarmClock extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      replaceLastUserId(param1VarArgs);
      return super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getNextAlarmClock";
    }
  }
  
  private static class Set extends MethodProxy {
    private Set() {}
    
    public boolean beforeCall(Object param1Object, Method param1Method, Object... param1VarArgs) {
      if (Build.VERSION.SDK_INT >= 24 && param1VarArgs[0] instanceof String)
        param1VarArgs[0] = getHostPkg(); 
      int i = ArrayUtils.indexOfFirst(param1VarArgs, WorkSource.class);
      if (i >= 0)
        param1VarArgs[i] = null; 
      return true;
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      try {
        return super.call(param1Object, param1Method, param1VarArgs);
      } finally {
        param1Object = null;
        param1Object.printStackTrace();
      } 
    }
    
    public String getMethodName() {
      return "set";
    }
  }
  
  private static class SetTime extends MethodProxy {
    private SetTime() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return (Build.VERSION.SDK_INT >= 21) ? Boolean.valueOf(false) : null;
    }
    
    public String getMethodName() {
      return "setTime";
    }
  }
  
  private static class SetTimeZone extends MethodProxy {
    private SetTimeZone() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return null;
    }
    
    public String getMethodName() {
      return "setTimeZone";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\alarm\AlarmManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */