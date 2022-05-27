package com.lody.virtual.client.hook.proxies.location;

import android.location.LocationRequest;
import android.os.Build;
import android.os.WorkSource;
import com.lody.virtual.client.hook.annotations.SkipInject;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceFirstPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceSequencePkgMethodProxy;
import com.lody.virtual.client.ipc.VLocationManager;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.Reflect;
import java.lang.reflect.Method;
import java.util.Arrays;
import mirror.android.location.LocationRequestL;

class MethodProxies {
  private static void fixLocationRequest(LocationRequest paramLocationRequest) {
    WorkSource workSource;
    if (paramLocationRequest == null)
      return; 
    if ("passive".equals(paramLocationRequest.getProvider()))
      paramLocationRequest.setProvider("gps"); 
    if (LocationRequestL.mHideFromAppOps != null)
      LocationRequestL.mHideFromAppOps.set(paramLocationRequest, false); 
    if (BuildCompat.isS() && LocationRequestL.mWorkSource != null) {
      workSource = (WorkSource)LocationRequestL.mWorkSource.get(paramLocationRequest);
      if (workSource != null)
        workSource.clear(); 
      return;
    } 
    if (LocationRequestL.mWorkSource != null)
      LocationRequestL.mWorkSource.set(workSource, null); 
  }
  
  @SkipInject
  static class AddGpsStatusListener extends ReplaceLastPkgMethodProxy {
    public AddGpsStatusListener() {
      super("addGpsStatusListener");
    }
    
    public AddGpsStatusListener(String param1String) {
      super(param1String);
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (isFakeLocationEnable()) {
        VLocationManager.get().addGpsStatusListener(param1VarArgs);
        return Boolean.valueOf(true);
      } 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
  
  @SkipInject
  static class GetBestProvider extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return isFakeLocationEnable() ? "gps" : super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getBestProvider";
    }
  }
  
  @SkipInject
  static class GetLastKnownLocation extends GetLastLocation {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (isFakeLocationEnable()) {
        param1Object = VLocationManager.get().getLocation(getAppPkg(), getAppUserId());
        return (param1Object != null) ? param1Object.toSysLocation() : null;
      } 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getLastKnownLocation";
    }
  }
  
  @SkipInject
  static class GetLastLocation extends ReplaceLastPkgMethodProxy {
    public GetLastLocation() {
      super("getLastLocation");
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (!(param1VarArgs[0] instanceof String))
        MethodProxies.fixLocationRequest((LocationRequest)param1VarArgs[0]); 
      if (isFakeLocationEnable()) {
        param1Object = VLocationManager.get().getLocation(getAppPkg(), getAppUserId());
        return (param1Object != null) ? param1Object.toSysLocation() : null;
      } 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
  
  @SkipInject
  static class IsProviderEnabled extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return (isFakeLocationEnable() && param1VarArgs[0] instanceof String) ? Boolean.valueOf(VLocationManager.get().isProviderEnabled((String)param1VarArgs[0])) : super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "isProviderEnabled";
    }
  }
  
  @SkipInject
  static class RegisterGnssStatusCallback extends AddGpsStatusListener {
    public RegisterGnssStatusCallback() {
      super("registerGnssStatusCallback");
    }
  }
  
  private static class RegisterLocationListener extends ReplaceSequencePkgMethodProxy {
    public RegisterLocationListener() {
      super("registerLocationListener", 2);
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (isFakeLocationEnable()) {
        VLocationManager.get().requestLocationUpdates(param1VarArgs);
        return Integer.valueOf(0);
      } 
      if ("passive".equals(param1VarArgs[0]))
        param1VarArgs[0] = "gps"; 
      MethodProxies.fixLocationRequest((LocationRequest)param1VarArgs[1]);
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
  
  @SkipInject
  static class RemoveGpsStatusListener extends ReplaceLastPkgMethodProxy {
    public RemoveGpsStatusListener() {
      super("removeGpsStatusListener");
    }
    
    public RemoveGpsStatusListener(String param1String) {
      super(param1String);
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (isFakeLocationEnable()) {
        VLocationManager.get().removeGpsStatusListener(param1VarArgs);
        return Integer.valueOf(0);
      } 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
  
  @SkipInject
  static class RemoveUpdates extends ReplaceLastPkgMethodProxy {
    public RemoveUpdates() {
      super("removeUpdates");
    }
    
    public RemoveUpdates(String param1String) {
      super(param1String);
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (isFakeLocationEnable()) {
        VLocationManager.get().removeUpdates(param1VarArgs);
        return Integer.valueOf(0);
      } 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
  
  @SkipInject
  static class RemoveUpdatesPI extends RemoveUpdates {
    public RemoveUpdatesPI() {
      super("removeUpdatesPI");
    }
  }
  
  @SkipInject
  static class RequestLocationUpdates extends ReplaceFirstPkgMethodProxy {
    public RequestLocationUpdates() {
      super("requestLocationUpdates");
    }
    
    public RequestLocationUpdates(String param1String) {
      super(param1String);
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (isFakeLocationEnable()) {
        VLocationManager.get().requestLocationUpdates(param1VarArgs);
        return Integer.valueOf(0);
      } 
      if (Build.VERSION.SDK_INT > 16)
        MethodProxies.fixLocationRequest((LocationRequest)param1VarArgs[0]); 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
  
  @SkipInject
  static class RequestLocationUpdatesPI extends RequestLocationUpdates {
    public RequestLocationUpdatesPI() {
      super("requestLocationUpdatesPI");
    }
  }
  
  @SkipInject
  static class UnregisterGnssStatusCallback extends RemoveGpsStatusListener {
    public UnregisterGnssStatusCallback() {
      super("unregisterGnssStatusCallback");
    }
  }
  
  private static class getAllProviders extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return isFakeLocationEnable() ? Arrays.asList(new String[] { "gps", "network" }) : super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getAllProviders";
    }
  }
  
  static class getProviderProperties extends MethodProxy {
    public Object afterCall(Object param1Object1, Method param1Method, Object[] param1ArrayOfObject, Object param1Object2) throws Throwable {
      if (isFakeLocationEnable())
        try {
          Reflect.on(param1Object2).set("mRequiresNetwork", Boolean.valueOf(false));
          Reflect.on(param1Object2).set("mRequiresCell", Boolean.valueOf(false));
        } finally {
          param1Object1 = null;
        }  
      return super.afterCall(param1Object1, param1Method, param1ArrayOfObject, param1Object2);
    }
    
    public String getMethodName() {
      return "getProviderProperties";
    }
  }
  
  static class locationCallbackFinished extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "locationCallbackFinished";
    }
  }
  
  static class sendExtraCommand extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return isFakeLocationEnable() ? Boolean.valueOf(true) : super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "sendExtraCommand";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\location\MethodProxies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */