package com.lody.virtual.client.hook.base;

import android.content.Context;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.annotations.LogInvocation;
import com.lody.virtual.client.hook.annotations.SkipInject;
import com.lody.virtual.client.interfaces.IInjector;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public abstract class MethodInvocationProxy<T extends MethodInvocationStub> implements IInjector {
  protected T mInvocationStub;
  
  public MethodInvocationProxy(T paramT) {
    this.mInvocationStub = paramT;
    onBindMethods();
    afterHookApply(paramT);
    LogInvocation logInvocation = getClass().<LogInvocation>getAnnotation(LogInvocation.class);
    if (logInvocation != null)
      paramT.setInvocationLoggingCondition(logInvocation.value()); 
  }
  
  private void addMethodProxy(Class<?> paramClass) {
    try {
      return;
    } finally {
      Exception exception = null;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to instance Hook : ");
      stringBuilder.append(paramClass);
      stringBuilder.append(" : ");
      stringBuilder.append(exception.getMessage());
    } 
  }
  
  public MethodProxy addMethodProxy(MethodProxy paramMethodProxy) {
    return this.mInvocationStub.addMethodProxy(paramMethodProxy);
  }
  
  protected void afterHookApply(T paramT) {}
  
  public Context getContext() {
    return VirtualCore.get().getContext();
  }
  
  public T getInvocationStub() {
    return this.mInvocationStub;
  }
  
  public abstract void inject() throws Throwable;
  
  protected void onBindMethods() {
    if (this.mInvocationStub == null)
      return; 
    Inject inject = getClass().<Inject>getAnnotation(Inject.class);
    if (inject != null) {
      Class clazz = inject.value();
      Class[] arrayOfClass = clazz.getDeclaredClasses();
      int i = arrayOfClass.length;
      boolean bool = false;
      byte b;
      for (b = 0; b < i; b++) {
        Class<?> clazz1 = arrayOfClass[b];
        if (!Modifier.isAbstract(clazz1.getModifiers()) && MethodProxy.class.isAssignableFrom(clazz1) && clazz1.getAnnotation(SkipInject.class) == null)
          addMethodProxy(clazz1); 
      } 
      Method[] arrayOfMethod = clazz.getMethods();
      i = arrayOfMethod.length;
      for (b = bool; b < i; b++) {
        Method method = arrayOfMethod[b];
        if (Modifier.isStatic(method.getModifiers()) && method.getAnnotation(SkipInject.class) == null)
          addMethodProxy(new DirectCallMethodProxy(method)); 
      } 
    } 
  }
  
  public void setDefaultMethodProxy(MethodProxy paramMethodProxy) {
    this.mInvocationStub.setDefaultMethodProxy(paramMethodProxy);
  }
  
  private static final class DirectCallMethodProxy extends StaticMethodProxy {
    private Method directCallMethod;
    
    public DirectCallMethodProxy(Method param1Method) {
      super(param1Method.getName());
      this.directCallMethod = param1Method;
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return this.directCallMethod.invoke(null, new Object[] { param1Object, param1Method, param1VarArgs });
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\base\MethodInvocationProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */