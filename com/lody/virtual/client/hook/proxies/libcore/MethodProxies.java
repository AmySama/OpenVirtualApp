package com.lody.virtual.client.hook.proxies.libcore;

import com.lody.virtual.client.NativeEngine;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.helper.utils.Reflect;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import mirror.libcore.io.Os;

class MethodProxies {
  static class Fstat extends Stat {
    public Object afterCall(Object param1Object1, Method param1Method, Object[] param1ArrayOfObject, Object param1Object2) throws Throwable {
      if (param1Object2 != null) {
        param1Object1 = Reflect.on(param1Object2);
        if (((Integer)param1Object1.get("st_uid")).intValue() == VirtualCore.get().myUid())
          param1Object1.set("st_uid", Integer.valueOf(VClient.get().getVUid())); 
      } 
      return param1Object2;
    }
    
    public String getMethodName() {
      return "fstat";
    }
  }
  
  static class GetUid extends MethodProxy {
    public Object afterCall(Object param1Object1, Method param1Method, Object[] param1ArrayOfObject, Object param1Object2) throws Throwable {
      return Integer.valueOf(NativeEngine.onGetUid(((Integer)param1Object2).intValue()));
    }
    
    public String getMethodName() {
      return "getuid";
    }
  }
  
  static class Getpwnam extends MethodProxy {
    public Object afterCall(Object param1Object1, Method param1Method, Object[] param1ArrayOfObject, Object param1Object2) throws Throwable {
      if (param1Object2 != null) {
        param1Object1 = Reflect.on(param1Object2);
        if (((Integer)param1Object1.get("pw_uid")).intValue() == VirtualCore.get().myUid())
          param1Object1.set("pw_uid", Integer.valueOf(VClient.get().getVUid())); 
      } 
      return param1Object2;
    }
    
    public String getMethodName() {
      return "getpwnam";
    }
  }
  
  static class GetsockoptUcred extends MethodProxy {
    public Object afterCall(Object param1Object1, Method param1Method, Object[] param1ArrayOfObject, Object param1Object2) throws Throwable {
      if (param1Object2 != null) {
        param1Object1 = Reflect.on(param1Object2);
        if (((Integer)param1Object1.get("uid")).intValue() == VirtualCore.get().myUid())
          param1Object1.set("uid", Integer.valueOf(getBaseVUid())); 
      } 
      return param1Object2;
    }
    
    public String getMethodName() {
      return "getsockoptUcred";
    }
  }
  
  static class Lstat extends Stat {
    public Object afterCall(Object param1Object1, Method param1Method, Object[] param1ArrayOfObject, Object param1Object2) throws Throwable {
      if (param1Object2 != null) {
        param1Object1 = Reflect.on(param1Object2);
        if (((Integer)param1Object1.get("st_uid")).intValue() == VirtualCore.get().myUid())
          param1Object1.set("st_uid", Integer.valueOf(VClient.get().getVUid())); 
      } 
      return param1Object2;
    }
    
    public String getMethodName() {
      return "lstat";
    }
  }
  
  static class Stat extends MethodProxy {
    private static Field st_uid;
    
    static {
      try {
        Field field = Os.TYPE.getMethod("stat", new Class[] { String.class }).getReturnType().getDeclaredField("st_uid");
        st_uid = field;
        return;
      } finally {
        Exception exception = null;
      } 
    }
    
    public Object afterCall(Object param1Object1, Method param1Method, Object[] param1ArrayOfObject, Object param1Object2) throws Throwable {
      if (((Integer)st_uid.get(param1Object2)).intValue() == VirtualCore.get().myUid())
        st_uid.set(param1Object2, Integer.valueOf(getBaseVUid())); 
      return param1Object2;
    }
    
    public String getMethodName() {
      return "stat";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\libcore\MethodProxies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */