package com.lody.virtual.client.hook.proxies.vibrator;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import java.lang.reflect.Method;
import mirror.RefStaticMethod;

public class VibratorStub extends BinderInvocationProxy {
  public VibratorStub() {
    super(refStaticMethod, str);
  }
  
  protected void onBindMethods() {
    addMethodProxy((MethodProxy)new VibrateMethodProxy("vibrateMagnitude"));
    addMethodProxy((MethodProxy)new VibrateMethodProxy("vibratePatternMagnitude"));
    addMethodProxy((MethodProxy)new VibrateMethodProxy("vibrate"));
    addMethodProxy((MethodProxy)new VibrateMethodProxy("vibratePattern"));
  }
  
  private static final class VibrateMethodProxy extends ReplaceCallingPkgMethodProxy {
    private VibrateMethodProxy(String param1String) {
      super(param1String);
    }
    
    public boolean beforeCall(Object param1Object, Method param1Method, Object... param1VarArgs) {
      if (param1VarArgs[0] instanceof Integer)
        param1VarArgs[0] = Integer.valueOf(getRealUid()); 
      return super.beforeCall(param1Object, param1Method, param1VarArgs);
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\vibrator\VibratorStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */