package com.lody.virtual.client.hook.proxies.accessibility;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import java.lang.reflect.Method;
import mirror.android.view.accessibility.IAccessibilityManager;

public class AccessibilityManagerStub extends BinderInvocationProxy {
  public AccessibilityManagerStub() {
    super(IAccessibilityManager.Stub.TYPE, "accessibility");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceLastUserIdProxy("addClient"));
    addMethodProxy((MethodProxy)new ReplaceLastUserIdProxy("sendAccessibilityEvent"));
    addMethodProxy((MethodProxy)new ReplaceLastUserIdProxy("getInstalledAccessibilityServiceList"));
    addMethodProxy((MethodProxy)new ReplaceLastUserIdProxy("getEnabledAccessibilityServiceList"));
    addMethodProxy((MethodProxy)new ReplaceLastUserIdProxy("getWindowToken"));
    addMethodProxy((MethodProxy)new ReplaceLastUserIdProxy("interrupt"));
    addMethodProxy((MethodProxy)new ReplaceLastUserIdProxy("addAccessibilityInteractionConnection"));
  }
  
  private static class ReplaceLastUserIdProxy extends StaticMethodProxy {
    public ReplaceLastUserIdProxy(String param1String) {
      super(param1String);
    }
    
    public boolean beforeCall(Object param1Object, Method param1Method, Object... param1VarArgs) {
      int i = param1VarArgs.length - 1;
      if (i >= 0 && param1VarArgs[i] instanceof Integer)
        param1VarArgs[i] = Integer.valueOf(getRealUserId()); 
      return super.beforeCall(param1Object, param1Method, param1VarArgs);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\accessibility\AccessibilityManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */