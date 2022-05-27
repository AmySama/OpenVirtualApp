package com.lody.virtual.client.stub;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.service.VServiceRuntime;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.remote.ClientConfig;
import com.lody.virtual.remote.ServiceData;
import com.lody.virtual.server.IBinderProxyService;
import com.lody.virtual.server.secondary.FakeIdentityBinder;
import java.util.HashMap;
import java.util.Map;

public class ShadowServiceImpl extends Service {
  private static final String TAG = ShadowServiceImpl.class.getSimpleName();
  
  private static final Map<String, IBindServiceProxy> sBinderServiceProxies;
  
  private final VServiceRuntime mRuntime = VServiceRuntime.getInstance();
  
  static {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    sBinderServiceProxies = (Map)hashMap;
    hashMap.put("android.accounts.IAccountAuthenticator", new IBindServiceProxy() {
          public Binder createProxy(Binder param1Binder) {
            return (Binder)new FakeIdentityBinder(param1Binder, 1000, Process.myPid());
          }
        });
  }
  
  public IBinder onBind(Intent paramIntent) {
    StringBuilder stringBuilder;
    Binder binder;
    ServiceData.ServiceBindData serviceBindData = new ServiceData.ServiceBindData(paramIntent);
    ClientConfig clientConfig = VClient.get().getClientConfig();
    if (clientConfig == null) {
      String str = TAG;
      stringBuilder = new StringBuilder();
      stringBuilder.append("restart service process: ");
      stringBuilder.append(serviceBindData.info.processName);
      VLog.e(str, stringBuilder.toString());
      return null;
    } 
    if (!serviceBindData.info.processName.equals(((ClientConfig)stringBuilder).processName))
      return null; 
    if (serviceBindData.intent == null)
      return null; 
    if (serviceBindData.userId != VUserHandle.myUserId())
      return null; 
    if (serviceBindData.connection == null)
      return null; 
    VServiceRuntime.ServiceRecord serviceRecord = this.mRuntime.getServiceRecord(serviceBindData.component, true);
    if (serviceRecord.service == null) {
      if ((serviceBindData.flags & 0x1) == 0)
        return null; 
      serviceRecord.service = VClient.get().createService(serviceBindData.info, (IBinder)serviceRecord);
    } 
    serviceBindData.intent.setExtrasClassLoader(serviceRecord.service.getClassLoader());
    IBinder iBinder2 = serviceRecord.onBind(serviceBindData.connection, serviceBindData.intent);
    IBinder iBinder1 = iBinder2;
    if (iBinder2 instanceof Binder) {
      iBinder1 = iBinder2;
      try {
        String str = iBinder2.getInterfaceDescriptor();
        iBinder1 = iBinder2;
        IBindServiceProxy iBindServiceProxy = sBinderServiceProxies.get(str);
        iBinder1 = iBinder2;
        if (iBindServiceProxy != null) {
          iBinder1 = iBinder2;
          Binder binder1 = iBindServiceProxy.createProxy((Binder)iBinder2);
          binder = binder1;
          StringBuilder stringBuilder1 = new StringBuilder();
          binder = binder1;
          this();
          binder = binder1;
          stringBuilder1.append("proxy binder ");
          binder = binder1;
          stringBuilder1.append(str);
          binder = binder1;
          stringBuilder1.append(" for service: ");
          binder = binder1;
          stringBuilder1.append(serviceBindData.component);
          binder = binder1;
          VLog.e("ServiceRuntime", stringBuilder1.toString());
          binder = binder1;
        } 
      } catch (RemoteException remoteException) {
        remoteException.printStackTrace();
      } 
    } 
    return (IBinder)new BinderProxy(serviceBindData.component, (IBinder)binder);
  }
  
  public void onCreate() {
    this.mRuntime.setShadowService(this);
  }
  
  public void onDestroy() {
    this.mRuntime.setShadowService(null);
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
    VServiceRuntime.ServiceRecord serviceRecord1;
    StringBuilder stringBuilder2;
    VServiceRuntime.ServiceRecord serviceRecord2;
    if (paramIntent == null)
      return 2; 
    String str = paramIntent.getAction();
    if (str == null)
      return 2; 
    if (str.equals("start_service")) {
      StringBuilder stringBuilder;
      ServiceData.ServiceStartData serviceStartData = new ServiceData.ServiceStartData(paramIntent);
      if (serviceStartData.intent == null) {
        String str1 = TAG;
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("invalid start service intent: ");
        stringBuilder2.append(paramIntent);
        VLog.e(str1, stringBuilder2.toString());
        return 2;
      } 
      ClientConfig clientConfig = VClient.get().getClientConfig();
      if (clientConfig == null) {
        String str1 = TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("restart service process: ");
        stringBuilder.append(((ServiceData.ServiceStartData)stringBuilder2).info.processName);
        VLog.e(str1, stringBuilder.toString());
        return 2;
      } 
      if (!((ServiceData.ServiceStartData)stringBuilder2).info.processName.equals(((ClientConfig)stringBuilder).processName))
        return 2; 
      if (((ServiceData.ServiceStartData)stringBuilder2).userId != VUserHandle.myUserId())
        return 2; 
      serviceRecord1 = this.mRuntime.getServiceRecord(((ServiceData.ServiceStartData)stringBuilder2).component, true);
      if (serviceRecord1.service == null)
        serviceRecord1.service = VClient.get().createService(((ServiceData.ServiceStartData)stringBuilder2).info, (IBinder)serviceRecord1); 
      serviceRecord1.lastActivityTime = SystemClock.uptimeMillis();
      serviceRecord1.started = true;
      serviceRecord1.startId++;
      ((ServiceData.ServiceStartData)stringBuilder2).intent.setExtrasClassLoader(serviceRecord1.service.getClassLoader());
      ComponentUtils.unpackFillIn(((ServiceData.ServiceStartData)stringBuilder2).intent, serviceRecord1.service.getClassLoader());
      paramInt2 = serviceRecord1.service.onStartCommand(((ServiceData.ServiceStartData)stringBuilder2).intent, paramInt1, serviceRecord1.startId);
      paramInt1 = paramInt2;
      if (paramInt2 == 1)
        paramInt1 = 3; 
      return paramInt1;
    } 
    if (stringBuilder2.equals("stop_service")) {
      ServiceData.ServiceStopData serviceStopData = new ServiceData.ServiceStopData((Intent)serviceRecord1);
      serviceRecord1 = null;
      if (serviceStopData.token instanceof VServiceRuntime.ServiceRecord)
        serviceRecord1 = (VServiceRuntime.ServiceRecord)serviceStopData.token; 
      serviceRecord2 = serviceRecord1;
      if (serviceRecord1 == null)
        serviceRecord2 = this.mRuntime.getServiceRecord(serviceStopData.component, false); 
      if (serviceRecord2 == null)
        return 2; 
      serviceRecord2.stopServiceIfNecessary(serviceStopData.startId, true);
      return 2;
    } 
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("unknown action: ");
    stringBuilder1.append((String)serviceRecord2);
    throw new RuntimeException(stringBuilder1.toString());
  }
  
  public boolean onUnbind(Intent paramIntent) {
    ServiceData.ServiceBindData serviceBindData = new ServiceData.ServiceBindData(paramIntent);
    if (serviceBindData.intent == null)
      return false; 
    if (serviceBindData.userId != VUserHandle.myUserId())
      return false; 
    if (serviceBindData.connection == null)
      return false; 
    VServiceRuntime.ServiceRecord serviceRecord = this.mRuntime.getServiceRecord(serviceBindData.component, false);
    if (serviceRecord != null && serviceRecord.service != null) {
      serviceBindData.intent.setExtrasClassLoader(serviceRecord.service.getClassLoader());
      serviceRecord.onUnbind(serviceBindData.connection, serviceBindData.intent);
    } 
    return false;
  }
  
  static class BinderProxy extends IBinderProxyService.Stub {
    private IBinder binder;
    
    private ComponentName component;
    
    public BinderProxy(ComponentName param1ComponentName, IBinder param1IBinder) {
      this.component = param1ComponentName;
      this.binder = param1IBinder;
    }
    
    public ComponentName getComponent() {
      return this.component;
    }
    
    public IBinder getService() {
      return this.binder;
    }
  }
  
  static interface IBindServiceProxy {
    Binder createProxy(Binder param1Binder);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\ShadowServiceImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */