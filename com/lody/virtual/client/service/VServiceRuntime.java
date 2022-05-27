package com.lody.virtual.client.service;

import android.app.ActivityManager;
import android.app.IServiceConnection;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.SystemClock;
import com.lody.virtual.client.VClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VServiceRuntime {
  private static final VServiceRuntime sInstance = new VServiceRuntime();
  
  private final Map<ComponentName, ServiceRecord> mComponentToServiceRecords = new HashMap<ComponentName, ServiceRecord>();
  
  private RemoteCallbackList<IServiceConnection> mConnectionCallbackList = new RemoteCallbackList<IServiceConnection>() {
      public void onCallbackDied(final IServiceConnection callback) {
        VServiceRuntime.this.mHandler.post(new Runnable() {
              public void run() {
                VServiceRuntime.this.handleConnectionDied(callback);
              }
            });
      }
    };
  
  private Handler mHandler = new Handler(Looper.getMainLooper());
  
  private Service mShadowService;
  
  public static VServiceRuntime getInstance() {
    return sInstance;
  }
  
  private void handleConnectionDied(IServiceConnection paramIServiceConnection) {
    synchronized (this.mComponentToServiceRecords) {
      Iterator iterator = this.mComponentToServiceRecords.values().iterator();
      while (iterator.hasNext()) {
        Iterator<ServiceBindRecord> iterator1 = ((ServiceRecord)iterator.next()).bindings.iterator();
        while (iterator1.hasNext())
          ((ServiceBindRecord)iterator1.next()).connections.remove(paramIServiceConnection.asBinder()); 
      } 
      trimService();
      return;
    } 
  }
  
  private void trimService() {
    synchronized (this.mComponentToServiceRecords) {
      for (ServiceRecord serviceRecord : this.mComponentToServiceRecords.values()) {
        if (serviceRecord.service != null && !serviceRecord.started && serviceRecord.getClientCount() <= 0 && serviceRecord.getConnectionCount() <= 0) {
          serviceRecord.service.onDestroy();
          serviceRecord.service = null;
          this.mComponentToServiceRecords.remove(serviceRecord.component);
        } 
      } 
      return;
    } 
  }
  
  public ServiceRecord getServiceRecord(ComponentName paramComponentName, boolean paramBoolean) {
    synchronized (this.mComponentToServiceRecords) {
      ServiceRecord serviceRecord1 = this.mComponentToServiceRecords.get(paramComponentName);
      ServiceRecord serviceRecord2 = serviceRecord1;
      if (serviceRecord1 == null) {
        serviceRecord2 = serviceRecord1;
        if (paramBoolean) {
          serviceRecord2 = new ServiceRecord();
          this(this);
          serviceRecord2.component = paramComponentName;
          serviceRecord2.lastActivityTime = SystemClock.uptimeMillis();
          serviceRecord2.activeSince = SystemClock.elapsedRealtime();
          this.mComponentToServiceRecords.put(paramComponentName, serviceRecord2);
        } 
      } 
      return serviceRecord2;
    } 
  }
  
  public List<ActivityManager.RunningServiceInfo> getServices() {
    ArrayList<ActivityManager.RunningServiceInfo> arrayList = new ArrayList(this.mComponentToServiceRecords.size());
    synchronized (this.mComponentToServiceRecords) {
      for (ServiceRecord serviceRecord : this.mComponentToServiceRecords.values()) {
        ActivityManager.RunningServiceInfo runningServiceInfo = new ActivityManager.RunningServiceInfo();
        this();
        runningServiceInfo.pid = Process.myPid();
        runningServiceInfo.uid = VClient.get().getVUid();
        runningServiceInfo.activeSince = serviceRecord.activeSince;
        runningServiceInfo.lastActivityTime = serviceRecord.lastActivityTime;
        runningServiceInfo.clientCount = serviceRecord.getClientCount();
        runningServiceInfo.service = serviceRecord.component;
        runningServiceInfo.started = serviceRecord.started;
        runningServiceInfo.process = (VClient.get().getClientConfig()).processName;
        arrayList.add(runningServiceInfo);
      } 
      return arrayList;
    } 
  }
  
  public void setShadowService(Service paramService) {
    this.mShadowService = paramService;
  }
  
  public enum RebindStatus {
    NotRebind, NotYetBound, Rebind;
    
    static {
      RebindStatus rebindStatus = new RebindStatus("NotRebind", 2);
      NotRebind = rebindStatus;
      $VALUES = new RebindStatus[] { NotYetBound, Rebind, rebindStatus };
    }
  }
  
  public static class ServiceBindRecord {
    public IBinder binder;
    
    public final Set<IBinder> connections = new HashSet<IBinder>();
    
    public Intent intent;
    
    public VServiceRuntime.RebindStatus rebindStatus;
    
    public int getConnectionCount() {
      return this.connections.size();
    }
  }
  
  public class ServiceRecord extends Binder {
    public long activeSince;
    
    public final List<VServiceRuntime.ServiceBindRecord> bindings = new ArrayList<VServiceRuntime.ServiceBindRecord>();
    
    public ComponentName component;
    
    public boolean foreground;
    
    public long lastActivityTime;
    
    public Service service;
    
    public int startId;
    
    public boolean started;
    
    public int getClientCount() {
      return this.bindings.size();
    }
    
    int getConnectionCount() {
      Iterator<VServiceRuntime.ServiceBindRecord> iterator = this.bindings.iterator();
      int i;
      for (i = 0; iterator.hasNext(); i += ((VServiceRuntime.ServiceBindRecord)iterator.next()).getConnectionCount());
      return i;
    }
    
    public IBinder onBind(IServiceConnection param1IServiceConnection, Intent param1Intent) {
      this.lastActivityTime = SystemClock.uptimeMillis();
      VServiceRuntime.this.mConnectionCallbackList.register((IInterface)param1IServiceConnection);
      synchronized (VServiceRuntime.this.mComponentToServiceRecords) {
        for (VServiceRuntime.ServiceBindRecord serviceBindRecord1 : this.bindings) {
          if (serviceBindRecord1.intent.filterEquals(param1Intent)) {
            if (serviceBindRecord1.connections.isEmpty() && serviceBindRecord1.rebindStatus == VServiceRuntime.RebindStatus.Rebind)
              this.service.onRebind(param1Intent); 
            serviceBindRecord1.connections.add(param1IServiceConnection.asBinder());
            iBinder = serviceBindRecord1.binder;
            return iBinder;
          } 
        } 
        VServiceRuntime.ServiceBindRecord serviceBindRecord = new VServiceRuntime.ServiceBindRecord();
        this();
        serviceBindRecord.intent = param1Intent;
        serviceBindRecord.connections.add(iBinder.asBinder());
        serviceBindRecord.binder = this.service.onBind(param1Intent);
        this.bindings.add(serviceBindRecord);
        IBinder iBinder = serviceBindRecord.binder;
        return iBinder;
      } 
    }
    
    public void onUnbind(IServiceConnection param1IServiceConnection, Intent param1Intent) {
      synchronized (VServiceRuntime.this.mComponentToServiceRecords) {
        for (VServiceRuntime.ServiceBindRecord serviceBindRecord : this.bindings) {
          if (serviceBindRecord.intent.filterEquals(param1Intent)) {
            if (serviceBindRecord.connections.remove(param1IServiceConnection.asBinder())) {
              if (serviceBindRecord.connections.isEmpty() && serviceBindRecord.rebindStatus != VServiceRuntime.RebindStatus.NotRebind) {
                VServiceRuntime.RebindStatus rebindStatus;
                if (this.service.onUnbind(param1Intent)) {
                  rebindStatus = VServiceRuntime.RebindStatus.Rebind;
                } else {
                  rebindStatus = VServiceRuntime.RebindStatus.NotRebind;
                } 
                serviceBindRecord.rebindStatus = rebindStatus;
              } 
              stopServiceIfNecessary(-1, false);
            } 
            break;
          } 
        } 
        return;
      } 
    }
    
    public void stopServiceIfNecessary(int param1Int, boolean param1Boolean) {
      if (param1Boolean) {
        if (param1Int != -1 && param1Int != this.startId)
          return; 
        this.started = false;
      } 
      if (this.service != null && !this.started && getConnectionCount() <= 0) {
        this.service.onDestroy();
        this.service = null;
        synchronized (VServiceRuntime.this.mComponentToServiceRecords) {
          VServiceRuntime.this.mComponentToServiceRecords.remove(this.component);
          if (VServiceRuntime.this.mComponentToServiceRecords.isEmpty())
            VServiceRuntime.this.mShadowService.stopSelf(); 
        } 
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\service\VServiceRuntime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */