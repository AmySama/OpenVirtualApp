package com.lody.virtual.client.hook.secondary;

import android.app.IServiceConnection;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.collection.ArrayMap;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.server.IBinderProxyService;
import mirror.android.app.ActivityThread;
import mirror.android.app.ContextImpl;
import mirror.android.app.IServiceConnectionO;
import mirror.android.app.LoadedApk;

public class ServiceConnectionProxy extends IServiceConnection.Stub {
  private static final ArrayMap<IBinder, ServiceConnectionProxy> sProxyMap = new ArrayMap();
  
  private IServiceConnection mConn;
  
  private ServiceConnectionProxy(IServiceConnection paramIServiceConnection) {
    this.mConn = paramIServiceConnection;
  }
  
  public static IServiceConnection getDispatcher(Context paramContext, ServiceConnection paramServiceConnection, int paramInt) {
    if (paramServiceConnection != null) {
      try {
        Object object1 = ActivityThread.currentActivityThread.call(new Object[0]);
        Object object2 = ContextImpl.mPackageInfo.get(VirtualCore.get().getContext());
        object1 = ActivityThread.getHandler.call(object1, new Object[0]);
        IServiceConnection iServiceConnection = (IServiceConnection)LoadedApk.getServiceDispatcher.call(object2, new Object[] { paramServiceConnection, paramContext, object1, Integer.valueOf(paramInt) });
      } catch (Exception exception) {
        Log.e("ConnectionDelegate", "getServiceDispatcher", exception);
        exception = null;
      } 
      if (exception != null)
        return (IServiceConnection)exception; 
      throw new RuntimeException("Not supported in system context");
    } 
    throw new IllegalArgumentException("connection is null");
  }
  
  public static ServiceConnectionProxy getOrCreateProxy(IServiceConnection paramIServiceConnection) {
    if (paramIServiceConnection instanceof ServiceConnectionProxy)
      return (ServiceConnectionProxy)paramIServiceConnection; 
    IBinder iBinder = paramIServiceConnection.asBinder();
    synchronized (sProxyMap) {
      ServiceConnectionProxy serviceConnectionProxy1 = (ServiceConnectionProxy)sProxyMap.get(iBinder);
      ServiceConnectionProxy serviceConnectionProxy2 = serviceConnectionProxy1;
      if (serviceConnectionProxy1 == null) {
        serviceConnectionProxy2 = new ServiceConnectionProxy();
        this(paramIServiceConnection);
        sProxyMap.put(iBinder, serviceConnectionProxy2);
      } 
      return serviceConnectionProxy2;
    } 
  }
  
  public static IServiceConnection removeDispatcher(Context paramContext, ServiceConnection paramServiceConnection) {
    try {
      Object object = ContextImpl.mPackageInfo.get(VirtualCore.get().getContext());
      IServiceConnection iServiceConnection = (IServiceConnection)LoadedApk.forgetServiceDispatcher.call(object, new Object[] { paramContext, paramServiceConnection });
    } catch (Exception exception) {
      Log.e("ConnectionDelegate", "forgetServiceDispatcher", exception);
      exception = null;
    } 
    return (IServiceConnection)exception;
  }
  
  public static ServiceConnectionProxy removeProxy(IServiceConnection paramIServiceConnection) {
    if (paramIServiceConnection == null)
      return null; 
    synchronized (sProxyMap) {
      return (ServiceConnectionProxy)sProxyMap.remove(paramIServiceConnection.asBinder());
    } 
  }
  
  public void connected(ComponentName paramComponentName, IBinder paramIBinder) throws RemoteException {
    connected(paramComponentName, paramIBinder, false);
  }
  
  public void connected(ComponentName paramComponentName, IBinder paramIBinder, boolean paramBoolean) throws RemoteException {
    if (paramIBinder == null)
      return; 
    IBinderProxyService iBinderProxyService = IBinderProxyService.Stub.asInterface(paramIBinder);
    if (iBinderProxyService != null) {
      ComponentName componentName = iBinderProxyService.getComponent();
      IBinder iBinder = iBinderProxyService.getService();
      paramComponentName = componentName;
      paramIBinder = iBinder;
      if (VirtualCore.get().isVAppProcess()) {
        IBinder iBinder1 = ProxyServiceFactory.getProxyService((Context)VClient.get().getCurrentApplication(), componentName, iBinder);
        paramComponentName = componentName;
        paramIBinder = iBinder;
        if (iBinder1 != null) {
          paramIBinder = iBinder1;
          paramComponentName = componentName;
        } 
      } 
    } 
    if (BuildCompat.isOreo()) {
      IServiceConnectionO.connected.call(this.mConn, new Object[] { paramComponentName, paramIBinder, Boolean.valueOf(paramBoolean) });
    } else {
      this.mConn.connected(paramComponentName, paramIBinder);
    } 
  }
  
  public IServiceConnection getBase() {
    return this.mConn;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\secondary\ServiceConnectionProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */