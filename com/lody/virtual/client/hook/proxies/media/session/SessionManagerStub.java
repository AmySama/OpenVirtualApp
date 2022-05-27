package com.lody.virtual.client.hook.proxies.media.session;

import android.os.IInterface;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import mirror.android.media.session.ISessionManager;

public class SessionManagerStub extends BinderInvocationProxy {
  public SessionManagerStub() {
    super(ISessionManager.Stub.asInterface, "media_session");
  }
  
  private static Object CreateProxy(IInterface paramIInterface, InvocationHandler paramInvocationHandler) {
    return Proxy.newProxyInstance(paramIInterface.getClass().getClassLoader(), paramIInterface.getClass().getInterfaces(), paramInvocationHandler);
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("createSession") {
          public Object call(final Object ISession, Method param1Method, Object... param1VarArgs) throws Throwable {
            replaceLastUserId(param1VarArgs);
            ISession = super.call(ISession, param1Method, param1VarArgs);
            return SessionManagerStub.CreateProxy((IInterface)ISession, new InvocationHandler() {
                  public Object invoke(final Object controller, Method param2Method, Object[] param2ArrayOfObject) throws Throwable {
                    if ("getController".equals(param2Method.getName())) {
                      controller = param2Method.invoke(ISession, param2ArrayOfObject);
                      return SessionManagerStub.CreateProxy((IInterface)controller, new InvocationHandler() {
                            public Object invoke(Object param3Object, Method param3Method, Object[] param3ArrayOfObject) throws Throwable {
                              if ("setVolumeTo".equals(param3Method.getName())) {
                                MethodParameterUtils.replaceFirstAppPkg(param3ArrayOfObject);
                                return param3Method.invoke(controller, param3ArrayOfObject);
                              } 
                              if ("adjustVolume".equals(param3Method.getName())) {
                                MethodParameterUtils.replaceFirstAppPkg(param3ArrayOfObject);
                                return param3Method.invoke(controller, param3ArrayOfObject);
                              } 
                              if ("createSession".equals(param3Method.getName()) || "getSessions".equals(param3Method.getName()) || "getSession2Tokens".equals(param3Method.getName()) || "addSessionsListener".equals(param3Method.getName()) || "addSession2TokensListener".equals(param3Method.getName()))
                                MethodProxy.replaceLastUserId(param3ArrayOfObject); 
                              return param3Method.invoke(controller, param3ArrayOfObject);
                            }
                          });
                    } 
                    return param2Method.invoke(ISession, param2ArrayOfObject);
                  }
                });
          }
        });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\media\session\SessionManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */