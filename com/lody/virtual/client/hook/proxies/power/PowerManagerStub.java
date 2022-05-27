package com.lody.virtual.client.hook.proxies.power;

import android.os.Build;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceSequencePkgMethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import mirror.android.os.IPowerManager;

public class PowerManagerStub extends BinderInvocationProxy {
  public PowerManagerStub() {
    super(IPowerManager.Stub.asInterface, "power");
  }
  
  private Object onHandleError(InvocationTargetException paramInvocationTargetException) throws Throwable {
    if (paramInvocationTargetException.getCause() instanceof SecurityException)
      return Integer.valueOf(0); 
    throw paramInvocationTargetException.getCause();
  }
  
  private void replaceWorkSource(Object[] paramArrayOfObject) {
    for (byte b = 0; b < paramArrayOfObject.length; b++) {
      if (paramArrayOfObject[b] instanceof android.os.WorkSource) {
        paramArrayOfObject[b] = null;
        break;
      } 
    } 
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("wakeUp"));
    addMethodProxy((MethodProxy)new ReplaceSequencePkgMethodProxy("acquireWakeLock", 2) {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            PowerManagerStub.this.replaceWorkSource(param1VarArgs);
            try {
              return super.call(param1Object, param1Method, param1VarArgs);
            } catch (InvocationTargetException invocationTargetException) {
              return PowerManagerStub.this.onHandleError(invocationTargetException);
            } 
          }
        });
    addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("acquireWakeLockWithUid") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            PowerManagerStub.this.replaceWorkSource(param1VarArgs);
            try {
              return super.call(param1Object, param1Method, param1VarArgs);
            } catch (InvocationTargetException invocationTargetException) {
              return PowerManagerStub.this.onHandleError(invocationTargetException);
            } 
          }
        });
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("updateWakeLockWorkSource", Integer.valueOf(0)) {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            PowerManagerStub.this.replaceWorkSource(param1VarArgs);
            return super.call(param1Object, param1Method, param1VarArgs);
          }
        });
    if (Build.MANUFACTURER.equalsIgnoreCase("FUJITSU"))
      addMethodProxy((MethodProxy)new StaticMethodProxy("acquireWakeLockWithLogging") {
            public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
              if (param1VarArgs[3] instanceof String && isAppPkg((String)param1VarArgs[3]))
                param1VarArgs[3] = getHostPkg(); 
              PowerManagerStub.this.replaceWorkSource(param1VarArgs);
              return super.call(param1Object, param1Method, param1VarArgs);
            }
          }); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\power\PowerManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */