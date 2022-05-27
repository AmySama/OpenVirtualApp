package android.arch.lifecycle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ClassesInfoCache {
  private static final int CALL_TYPE_NO_ARG = 0;
  
  private static final int CALL_TYPE_PROVIDER = 1;
  
  private static final int CALL_TYPE_PROVIDER_WITH_EVENT = 2;
  
  static ClassesInfoCache sInstance = new ClassesInfoCache();
  
  private final Map<Class, CallbackInfo> mCallbackMap = (Map)new HashMap<Class<?>, CallbackInfo>();
  
  private final Map<Class, Boolean> mHasLifecycleMethods = (Map)new HashMap<Class<?>, Boolean>();
  
  private CallbackInfo createInfo(Class paramClass, Method[] paramArrayOfMethod) {
    Class clazz = paramClass.getSuperclass();
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    if (clazz != null) {
      CallbackInfo callbackInfo1 = getInfo(clazz);
      if (callbackInfo1 != null)
        hashMap.putAll(callbackInfo1.mHandlerToEvent); 
    } 
    Class[] arrayOfClass = paramClass.getInterfaces();
    int i = arrayOfClass.length;
    byte b;
    for (b = 0; b < i; b++) {
      for (Map.Entry<MethodReference, Lifecycle.Event> entry : (getInfo(arrayOfClass[b])).mHandlerToEvent.entrySet())
        verifyAndPutHandler((Map)hashMap, (MethodReference)entry.getKey(), (Lifecycle.Event)entry.getValue(), paramClass); 
    } 
    if (paramArrayOfMethod == null)
      paramArrayOfMethod = getDeclaredMethods(paramClass); 
    int j = paramArrayOfMethod.length;
    i = 0;
    boolean bool = false;
    while (i < j) {
      Method method = paramArrayOfMethod[i];
      OnLifecycleEvent onLifecycleEvent = method.<OnLifecycleEvent>getAnnotation(OnLifecycleEvent.class);
      if (onLifecycleEvent != null) {
        Class[] arrayOfClass1 = method.getParameterTypes();
        if (arrayOfClass1.length > 0) {
          if (arrayOfClass1[0].isAssignableFrom(LifecycleOwner.class)) {
            b = 1;
          } else {
            throw new IllegalArgumentException("invalid parameter type. Must be one and instanceof LifecycleOwner");
          } 
        } else {
          b = 0;
        } 
        Lifecycle.Event event = onLifecycleEvent.value();
        if (arrayOfClass1.length > 1)
          if (arrayOfClass1[1].isAssignableFrom(Lifecycle.Event.class)) {
            if (event == Lifecycle.Event.ON_ANY) {
              b = 2;
            } else {
              throw new IllegalArgumentException("Second arg is supported only for ON_ANY value");
            } 
          } else {
            throw new IllegalArgumentException("invalid parameter type. second arg must be an event");
          }  
        if (arrayOfClass1.length <= 2) {
          verifyAndPutHandler((Map)hashMap, new MethodReference(b, method), event, paramClass);
          bool = true;
        } else {
          throw new IllegalArgumentException("cannot have more than 2 params");
        } 
      } 
      i++;
    } 
    CallbackInfo callbackInfo = new CallbackInfo((Map)hashMap);
    this.mCallbackMap.put(paramClass, callbackInfo);
    this.mHasLifecycleMethods.put(paramClass, Boolean.valueOf(bool));
    return callbackInfo;
  }
  
  private Method[] getDeclaredMethods(Class paramClass) {
    try {
      return paramClass.getDeclaredMethods();
    } catch (NoClassDefFoundError noClassDefFoundError) {
      throw new IllegalArgumentException("The observer class has some methods that use newer APIs which are not available in the current OS version. Lifecycles cannot access even other methods so you should make sure that your observer classes only access framework classes that are available in your min API level OR use lifecycle:compiler annotation processor.", noClassDefFoundError);
    } 
  }
  
  private void verifyAndPutHandler(Map<MethodReference, Lifecycle.Event> paramMap, MethodReference paramMethodReference, Lifecycle.Event paramEvent, Class paramClass) {
    Lifecycle.Event event = paramMap.get(paramMethodReference);
    if (event == null || paramEvent == event) {
      if (event == null)
        paramMap.put(paramMethodReference, paramEvent); 
      return;
    } 
    Method method = paramMethodReference.mMethod;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Method ");
    stringBuilder.append(method.getName());
    stringBuilder.append(" in ");
    stringBuilder.append(paramClass.getName());
    stringBuilder.append(" already declared with different @OnLifecycleEvent value: previous value ");
    stringBuilder.append(event);
    stringBuilder.append(", new value ");
    stringBuilder.append(paramEvent);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  CallbackInfo getInfo(Class paramClass) {
    CallbackInfo callbackInfo = this.mCallbackMap.get(paramClass);
    return (callbackInfo != null) ? callbackInfo : createInfo(paramClass, null);
  }
  
  boolean hasLifecycleMethods(Class paramClass) {
    if (this.mHasLifecycleMethods.containsKey(paramClass))
      return ((Boolean)this.mHasLifecycleMethods.get(paramClass)).booleanValue(); 
    Method[] arrayOfMethod = getDeclaredMethods(paramClass);
    int i = arrayOfMethod.length;
    for (byte b = 0; b < i; b++) {
      if ((OnLifecycleEvent)arrayOfMethod[b].<OnLifecycleEvent>getAnnotation(OnLifecycleEvent.class) != null) {
        createInfo(paramClass, arrayOfMethod);
        return true;
      } 
    } 
    this.mHasLifecycleMethods.put(paramClass, Boolean.valueOf(false));
    return false;
  }
  
  static class CallbackInfo {
    final Map<Lifecycle.Event, List<ClassesInfoCache.MethodReference>> mEventToHandlers;
    
    final Map<ClassesInfoCache.MethodReference, Lifecycle.Event> mHandlerToEvent;
    
    CallbackInfo(Map<ClassesInfoCache.MethodReference, Lifecycle.Event> param1Map) {
      this.mHandlerToEvent = param1Map;
      this.mEventToHandlers = new HashMap<Lifecycle.Event, List<ClassesInfoCache.MethodReference>>();
      for (Map.Entry<ClassesInfoCache.MethodReference, Lifecycle.Event> entry : param1Map.entrySet()) {
        Lifecycle.Event event = (Lifecycle.Event)entry.getValue();
        List<ClassesInfoCache.MethodReference> list2 = this.mEventToHandlers.get(event);
        List<ClassesInfoCache.MethodReference> list1 = list2;
        if (list2 == null) {
          list1 = new ArrayList();
          this.mEventToHandlers.put(event, list1);
        } 
        list1.add((ClassesInfoCache.MethodReference)entry.getKey());
      } 
    }
    
    private static void invokeMethodsForEvent(List<ClassesInfoCache.MethodReference> param1List, LifecycleOwner param1LifecycleOwner, Lifecycle.Event param1Event, Object param1Object) {
      if (param1List != null)
        for (int i = param1List.size() - 1; i >= 0; i--)
          ((ClassesInfoCache.MethodReference)param1List.get(i)).invokeCallback(param1LifecycleOwner, param1Event, param1Object);  
    }
    
    void invokeCallbacks(LifecycleOwner param1LifecycleOwner, Lifecycle.Event param1Event, Object param1Object) {
      invokeMethodsForEvent(this.mEventToHandlers.get(param1Event), param1LifecycleOwner, param1Event, param1Object);
      invokeMethodsForEvent(this.mEventToHandlers.get(Lifecycle.Event.ON_ANY), param1LifecycleOwner, param1Event, param1Object);
    }
  }
  
  static class MethodReference {
    final int mCallType;
    
    final Method mMethod;
    
    MethodReference(int param1Int, Method param1Method) {
      this.mCallType = param1Int;
      this.mMethod = param1Method;
      param1Method.setAccessible(true);
    }
    
    public boolean equals(Object param1Object) {
      boolean bool = true;
      if (this == param1Object)
        return true; 
      if (param1Object == null || getClass() != param1Object.getClass())
        return false; 
      param1Object = param1Object;
      if (this.mCallType != ((MethodReference)param1Object).mCallType || !this.mMethod.getName().equals(((MethodReference)param1Object).mMethod.getName()))
        bool = false; 
      return bool;
    }
    
    public int hashCode() {
      return this.mCallType * 31 + this.mMethod.getName().hashCode();
    }
    
    void invokeCallback(LifecycleOwner param1LifecycleOwner, Lifecycle.Event param1Event, Object param1Object) {
      try {
        int i = this.mCallType;
        if (i != 0) {
          if (i != 1) {
            if (i == 2)
              this.mMethod.invoke(param1Object, new Object[] { param1LifecycleOwner, param1Event }); 
          } else {
            this.mMethod.invoke(param1Object, new Object[] { param1LifecycleOwner });
          } 
        } else {
          this.mMethod.invoke(param1Object, new Object[0]);
        } 
        return;
      } catch (InvocationTargetException invocationTargetException) {
        throw new RuntimeException("Failed to call observer method", invocationTargetException.getCause());
      } catch (IllegalAccessException illegalAccessException) {
        throw new RuntimeException(illegalAccessException);
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\ClassesInfoCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */