package com.lody.virtual.client.hook.proxies.location;

import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.text.TextUtils;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.annotations.Inject;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodInvocationProxy;
import com.lody.virtual.client.hook.base.MethodInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceFirstPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastUserIdMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceSequencePkgMethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import com.lody.virtual.client.hook.base.StaticMethodProxy;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.helper.utils.ReflectException;
import java.lang.reflect.Method;
import mirror.android.location.GeocoderParams;
import mirror.android.location.GpsStatus;
import mirror.android.location.ILocationManager;
import mirror.android.location.LocationManager;
import mirror.android.os.ServiceManager;

@Inject(MethodProxies.class)
public class LocationManagerStub extends MethodInvocationProxy<BinderInvocationStub> {
  public LocationManagerStub() {
    super((MethodInvocationStub)new BinderInvocationStub(getInterface()));
  }
  
  private static IInterface getInterface() {
    IBinder iBinder = (IBinder)ServiceManager.getService.call(new Object[] { "location" });
    if (iBinder instanceof android.os.Binder)
      try {
        return (IInterface)Reflect.on(iBinder).get("mILocationManager");
      } catch (ReflectException reflectException) {
        reflectException.printStackTrace();
      }  
    return (IInterface)ILocationManager.Stub.asInterface.call(new Object[] { iBinder });
  }
  
  public void inject() {
    LocationManager locationManager = (LocationManager)getContext().getSystemService("location");
    Object object = LocationManager.mService.get(locationManager);
    if (object instanceof android.os.Binder)
      Reflect.on(object).set("mILocationManager", ((BinderInvocationStub)getInvocationStub()).getProxyInterface()); 
    LocationManager.mService.set(locationManager, ((BinderInvocationStub)getInvocationStub()).getProxyInterface());
    ((BinderInvocationStub)getInvocationStub()).replaceService("location");
  }
  
  public boolean isEnvBad() {
    return false;
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    if (Build.VERSION.SDK_INT >= 23) {
      addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("addTestProvider"));
      addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("removeTestProvider"));
      addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("setTestProviderLocation"));
      addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("clearTestProviderLocation"));
      addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("setTestProviderEnabled"));
      addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("clearTestProviderEnabled"));
      addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("setTestProviderStatus"));
      addMethodProxy((MethodProxy)new ReplaceLastPkgMethodProxy("clearTestProviderStatus"));
    } 
    if (Build.VERSION.SDK_INT >= 21) {
      addMethodProxy((MethodProxy)new FakeReplaceLastPkgMethodProxy("addGpsMeasurementListener", Boolean.valueOf(true)));
      addMethodProxy((MethodProxy)new FakeReplaceLastPkgMethodProxy("addGpsNavigationMessageListener", Boolean.valueOf(true)));
      addMethodProxy((MethodProxy)new FakeReplaceLastPkgMethodProxy("removeGpsMeasurementListener", Integer.valueOf(0)));
      addMethodProxy((MethodProxy)new FakeReplaceLastPkgMethodProxy("removeGpsNavigationMessageListener", Integer.valueOf(0)));
    } 
    if (Build.VERSION.SDK_INT >= 17) {
      addMethodProxy((MethodProxy)new FakeReplaceLastPkgMethodProxy("requestGeofence", Integer.valueOf(0)));
      addMethodProxy((MethodProxy)new FakeReplaceLastPkgMethodProxy("removeGeofence", Integer.valueOf(0)));
    } 
    if (Build.VERSION.SDK_INT <= 16) {
      addMethodProxy((MethodProxy)new MethodProxies.GetLastKnownLocation());
      addMethodProxy((MethodProxy)new FakeReplaceLastPkgMethodProxy("addProximityAlert", Integer.valueOf(0)));
    } 
    if (Build.VERSION.SDK_INT <= 16) {
      addMethodProxy((MethodProxy)new MethodProxies.RequestLocationUpdatesPI());
      addMethodProxy((MethodProxy)new MethodProxies.RemoveUpdatesPI());
    } 
    if (Build.VERSION.SDK_INT >= 16) {
      addMethodProxy((MethodProxy)new MethodProxies.RequestLocationUpdates());
      addMethodProxy((MethodProxy)new MethodProxies.RemoveUpdates());
    } 
    addMethodProxy(new MethodProxies.IsProviderEnabled());
    addMethodProxy(new MethodProxies.GetBestProvider());
    if (Build.VERSION.SDK_INT >= 17) {
      addMethodProxy((MethodProxy)new MethodProxies.GetLastLocation());
      addMethodProxy((MethodProxy)new MethodProxies.AddGpsStatusListener());
      addMethodProxy((MethodProxy)new MethodProxies.RemoveGpsStatusListener());
      addMethodProxy((MethodProxy)new FakeReplaceLastPkgMethodProxy("addNmeaListener", Integer.valueOf(0)));
      addMethodProxy((MethodProxy)new FakeReplaceLastPkgMethodProxy("removeNmeaListener", Integer.valueOf(0)));
    } 
    if (Build.VERSION.SDK_INT >= 24) {
      if (BuildCompat.isS()) {
        addMethodProxy((MethodProxy)new ReplaceFirstPkgMethodProxy("registerGnssStatusCallback"));
      } else {
        addMethodProxy((MethodProxy)new MethodProxies.RegisterGnssStatusCallback());
      } 
      addMethodProxy((MethodProxy)new MethodProxies.UnregisterGnssStatusCallback());
    } 
    addMethodProxy((MethodProxy)new ReplaceLastUserIdMethodProxy("isProviderEnabledForUser"));
    addMethodProxy((MethodProxy)new ReplaceLastUserIdMethodProxy("isLocationEnabledForUser"));
    if (BuildCompat.isQ()) {
      addMethodProxy((MethodProxy)new StaticMethodProxy("setLocationControllerExtraPackageEnabled") {
            public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
              return null;
            }
          });
      addMethodProxy((MethodProxy)new StaticMethodProxy("setExtraLocationControllerPackageEnabled") {
            public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
              return null;
            }
          });
      addMethodProxy((MethodProxy)new StaticMethodProxy("setExtraLocationControllerPackage") {
            public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
              return null;
            }
          });
    } 
    if (BuildCompat.isR())
      addMethodProxy((MethodProxy)new ResultStaticMethodProxy("setLocationEnabledForUser", null)); 
    if (BuildCompat.isS()) {
      addMethodProxy((MethodProxy)new ReplaceSequencePkgMethodProxy("registerLocationPendingIntent", 2));
      addMethodProxy((MethodProxy)new ReplaceSequencePkgMethodProxy("registerLocationPendingIntent", 2));
      addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("registerGnssNmeaCallback"));
      addMethodProxy((MethodProxy)new GetFromLocation("getFromLocationName"));
      addMethodProxy((MethodProxy)new GetFromLocation("getFromLocation"));
    } 
  }
  
  private static class FakeReplaceLastPkgMethodProxy extends ReplaceLastPkgMethodProxy {
    private Object mDefValue;
    
    private FakeReplaceLastPkgMethodProxy(String param1String, Object param1Object) {
      super(param1String);
      this.mDefValue = param1Object;
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return isFakeLocationEnable() ? this.mDefValue : super.call(param1Object, param1Method, param1VarArgs);
    }
  }
  
  private static class GetFromLocation extends StaticMethodProxy {
    public GetFromLocation(String param1String) {
      super(param1String);
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      int i = ArrayUtils.indexOf(param1VarArgs, GpsStatus.TYPE, 0);
      if (i >= 0) {
        String str1 = VirtualCore.get().getHostPkg();
        String str2 = GeocoderParams.mPackageName(param1VarArgs[i]);
        if (str2 != null && !TextUtils.equals(str1, str2))
          GeocoderParams.mPackageName(param1VarArgs[i], str1); 
        int j = GeocoderParams.mUid(param1VarArgs[i]);
        if (j > 0 && j != VirtualCore.get().myUid())
          GeocoderParams.mUid(param1VarArgs[i], VirtualCore.get().myUid()); 
      } 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\location\LocationManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */