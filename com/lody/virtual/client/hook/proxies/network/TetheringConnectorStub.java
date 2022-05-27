package com.lody.virtual.client.hook.proxies.network;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.helper.utils.Reflect;
import java.lang.reflect.Method;
import mirror.android.net.ITetheringConnector;

public class TetheringConnectorStub extends BinderInvocationProxy {
  private static final String SERVER_NAME = "tethering";
  
  public TetheringConnectorStub() {
    super(ITetheringConnector.Stub.asInterface, "tethering");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new StaticMethodProxy("isTetheringSupported") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
            try {
              Reflect.on(param1VarArgs[2]).call("onResult", new Object[] { Integer.valueOf(3) });
              return null;
            } catch (Exception exception) {
              return super.call(param1Object, param1Method, param1VarArgs);
            } 
          }
        });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\network\TetheringConnectorStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */