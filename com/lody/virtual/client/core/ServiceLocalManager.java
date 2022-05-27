package com.lody.virtual.client.core;

import com.lody.virtual.client.hook.base.BinderInvocationStub;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocalManager {
  private static final Map<String, BinderInvocationStub> sCache = new HashMap<String, BinderInvocationStub>();
  
  public static void addService(String paramString, BinderInvocationStub paramBinderInvocationStub) {
    synchronized (sCache) {
      sCache.put(paramString, paramBinderInvocationStub);
      return;
    } 
  }
  
  public static BinderInvocationStub getService(String paramString) {
    synchronized (sCache) {
      return sCache.get(paramString);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\core\ServiceLocalManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */