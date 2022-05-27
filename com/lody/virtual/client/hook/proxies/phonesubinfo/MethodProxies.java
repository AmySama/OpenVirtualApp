package com.lody.virtual.client.hook.proxies.phonesubinfo;

import android.text.TextUtils;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.remote.VDeviceConfig;
import java.lang.reflect.Method;

class MethodProxies {
  static class GetDeviceId extends ReplaceLastPkgMethodProxy {
    public GetDeviceId() {
      super("getDeviceId");
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      VDeviceConfig vDeviceConfig = getDeviceConfig();
      if (vDeviceConfig.enable) {
        String str = vDeviceConfig.deviceId;
        if (!TextUtils.isEmpty(str))
          return str; 
      } 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
  
  static class GetDeviceIdForPhone extends GetDeviceId {
    public String getMethodName() {
      return "getDeviceIdForPhone";
    }
  }
  
  static class GetDeviceIdForSubscriber extends GetDeviceId {
    public String getMethodName() {
      return "getDeviceIdForSubscriber";
    }
  }
  
  static class GetIccSerialNumber extends ReplaceLastPkgMethodProxy {
    public GetIccSerialNumber() {
      super("getIccSerialNumber");
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if ((getDeviceConfig()).enable) {
        String str = (getDeviceConfig()).iccId;
        if (!TextUtils.isEmpty(str))
          return str; 
      } 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
  
  static class GetIccSerialNumberForSubscriber extends GetIccSerialNumber {
    public String getMethodName() {
      return "getIccSerialNumberForSubscriber";
    }
  }
  
  static class GetImeiForSubscriber extends GetDeviceId {
    public String getMethodName() {
      return "getImeiForSubscriber";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\phonesubinfo\MethodProxies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */