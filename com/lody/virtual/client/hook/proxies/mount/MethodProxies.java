package com.lody.virtual.client.hook.proxies.mount;

import android.os.Build;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import java.io.File;
import java.lang.reflect.Method;

class MethodProxies {
  static class GetVolumeList extends MethodProxy {
    public Object afterCall(Object param1Object1, Method param1Method, Object[] param1ArrayOfObject, Object param1Object2) throws Throwable {
      return param1Object2;
    }
    
    public boolean beforeCall(Object param1Object, Method param1Method, Object... param1VarArgs) {
      if (param1VarArgs == null || param1VarArgs.length == 0)
        return super.beforeCall(param1Object, param1Method, param1VarArgs); 
      if (param1VarArgs[0] instanceof Integer)
        param1VarArgs[0] = Integer.valueOf(getRealUid()); 
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      return super.beforeCall(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getVolumeList";
    }
  }
  
  static class Mkdirs extends MethodProxy {
    public boolean beforeCall(Object param1Object, Method param1Method, Object... param1VarArgs) {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      return super.beforeCall(param1Object, param1Method, param1VarArgs);
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (Build.VERSION.SDK_INT < 19)
        return super.call(param1Object, param1Method, param1VarArgs); 
      if (param1VarArgs.length == 1) {
        param1Object = param1VarArgs[0];
      } else {
        param1Object = param1VarArgs[1];
      } 
      param1Object = new File((String)param1Object);
      return (!param1Object.exists() && !param1Object.mkdirs()) ? Integer.valueOf(-1) : Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "mkdirs";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\mount\MethodProxies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */