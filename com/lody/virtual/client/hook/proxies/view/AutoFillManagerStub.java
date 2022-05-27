package com.lody.virtual.client.hook.proxies.view;

import android.content.ComponentName;
import android.util.Log;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastUserIdMethodProxy;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import com.lody.virtual.helper.utils.ArrayUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import mirror.android.view.IAutoFillManager;

public class AutoFillManagerStub extends BinderInvocationProxy {
  private static final String AUTO_FILL_NAME = "autofill";
  
  private static final String TAG = "AutoFillManagerStub";
  
  public AutoFillManagerStub() {
    super(IAutoFillManager.Stub.asInterface, "autofill");
  }
  
  public static void disableAutoFill(Object paramObject) {
    if (paramObject != null)
      try {
        Field field = paramObject.getClass().getDeclaredField("mService");
        return;
      } finally {
        paramObject = null;
        Log.e("AutoFillManagerStub", "AutoFillManagerStub inject error.", (Throwable)paramObject);
      }  
    paramObject = new NullPointerException();
    super("AutoFillManagerInstance is null.");
    throw paramObject;
  }
  
  public void inject() throws Throwable {
    super.inject();
    try {
      Object object = getContext().getSystemService("autofill");
      if (object != null) {
        Object object1 = ((BinderInvocationStub)getInvocationStub()).getProxyInterface();
        if (object1 != null) {
          Field field = object.getClass().getDeclaredField("mService");
          field.setAccessible(true);
          field.set(object, object1);
          addMethodProxy((MethodProxy)new ReplacePkgAndComponentProxy("startSession") {
                public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
                  replaceFirstUserId(param1VarArgs);
                  return super.call(param1Object, param1Method, param1VarArgs);
                }
              });
          addMethodProxy((MethodProxy)new ReplacePkgAndComponentProxy("updateOrRestartSession"));
          addMethodProxy((MethodProxy)new ReplaceLastUserIdMethodProxy("addClient"));
          addMethodProxy((MethodProxy)new ReplaceLastUserIdMethodProxy("removeClient"));
          addMethodProxy((MethodProxy)new ReplaceLastUserIdMethodProxy("updateSession"));
          addMethodProxy((MethodProxy)new ReplaceLastUserIdMethodProxy("finishSession"));
          addMethodProxy((MethodProxy)new ReplaceLastUserIdMethodProxy("cancelSession"));
          addMethodProxy((MethodProxy)new ReplaceLastUserIdMethodProxy("setAuthenticationResult"));
          addMethodProxy((MethodProxy)new ReplaceLastUserIdMethodProxy("setHasCallback"));
          addMethodProxy((MethodProxy)new ReplaceLastUserIdMethodProxy("disableOwnedAutofillServices"));
          return;
        } 
        object1 = new NullPointerException();
        super("AutoFillManagerProxy is null.");
        throw object1;
      } 
      NullPointerException nullPointerException = new NullPointerException();
      this("AutoFillManagerInstance is null.");
      throw nullPointerException;
    } finally {
      Exception exception = null;
      Log.e("AutoFillManagerStub", "AutoFillManagerStub inject error.", exception);
    } 
  }
  
  static class ReplacePkgAndComponentProxy extends ReplaceLastPkgMethodProxy {
    ReplacePkgAndComponentProxy(String param1String) {
      super(param1String);
    }
    
    private void replaceLastAppComponent(Object[] param1ArrayOfObject, String param1String) {
      int i = ArrayUtils.indexOfLast(param1ArrayOfObject, ComponentName.class);
      if (i != -1)
        param1ArrayOfObject[i] = new ComponentName(param1String, ((ComponentName)param1ArrayOfObject[i]).getClassName()); 
    }
    
    public boolean beforeCall(Object param1Object, Method param1Method, Object... param1VarArgs) {
      replaceLastAppComponent(param1VarArgs, getHostPkg());
      return super.beforeCall(param1Object, param1Method, param1VarArgs);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\view\AutoFillManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */