package com.lody.virtual.client.hook.proxies.service;

import android.os.IInterface;
import com.lody.virtual.client.core.ServiceLocalManager;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodInvocationProxy;
import com.lody.virtual.client.hook.base.MethodInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.helper.utils.VLog;
import java.lang.reflect.Method;
import mirror.android.os.ServiceManager;

public class ServiceManagerStub extends MethodInvocationProxy<MethodInvocationStub<IInterface>> {
  public ServiceManagerStub() {
    super(new MethodInvocationStub(ServiceManager.getIServiceManager.call(new Object[0])));
  }
  
  public void inject() {
    ServiceManager.sServiceManager.set(getInvocationStub().getProxyInterface());
  }
  
  public boolean isEnvBad() {
    boolean bool;
    if (ServiceManager.sServiceManager.get() != getInvocationStub().getProxyInterface()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new StaticMethodProxy("getService") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            String str = (String)param1VarArgs[0];
            BinderInvocationStub binderInvocationStub = ServiceLocalManager.getService(str);
            if (binderInvocationStub != null) {
              VLog.d("kk", "ServiceLocalManager.getService:%s->%s", new Object[] { str, binderInvocationStub });
              return binderInvocationStub;
            } 
            VLog.d("kk", "ServiceLocalManager.getService:%s no find", new Object[] { str });
            return super.call(param1Object, param1Method, param1VarArgs);
          }
        });
    addMethodProxy((MethodProxy)new StaticMethodProxy("checkService") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            String str = (String)param1VarArgs[0];
            BinderInvocationStub binderInvocationStub = ServiceLocalManager.getService(str);
            if (binderInvocationStub != null) {
              VLog.d("kk", "ServiceLocalManager.checkService:%s->%s", new Object[] { str, binderInvocationStub });
              return binderInvocationStub;
            } 
            VLog.d("kk", "ServiceLocalManager.checkService:%s no find", new Object[] { str });
            return super.call(param1Object, param1Method, param1VarArgs);
          }
        });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\service\ServiceManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */