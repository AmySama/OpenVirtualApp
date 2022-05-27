package com.lody.virtual.client.hook.proxies.window;

import android.os.IInterface;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.proxies.window.session.WindowSessionPatch;
import java.lang.reflect.Method;

class MethodProxies {
  static abstract class BasePatchSession extends MethodProxy {
    private Object proxySession(IInterface param1IInterface) {
      return (new WindowSessionPatch(param1IInterface)).getInvocationStub().getProxyInterface();
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      Object object = param1Method.invoke(param1Object, param1VarArgs);
      param1Object = object;
      if (object instanceof IInterface)
        param1Object = proxySession((IInterface)object); 
      return param1Object;
    }
  }
  
  static class OpenSession extends BasePatchSession {
    public String getMethodName() {
      return "openSession";
    }
  }
  
  static class OverridePendingAppTransition extends BasePatchSession {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (param1VarArgs[0] instanceof String)
        param1VarArgs[0] = getHostPkg(); 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "overridePendingAppTransition";
    }
  }
  
  static class OverridePendingAppTransitionInPlace extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (param1VarArgs[0] instanceof String)
        param1VarArgs[0] = getHostPkg(); 
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "overridePendingAppTransitionInPlace";
    }
  }
  
  static class SetAppStartingWindow extends BasePatchSession {
    public String getMethodName() {
      return "setAppStartingWindow";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\window\MethodProxies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */