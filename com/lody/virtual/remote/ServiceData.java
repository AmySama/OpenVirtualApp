package com.lody.virtual.remote;

import android.app.IServiceConnection;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.Parcelable;
import com.lody.virtual.helper.compat.BundleCompat;

public class ServiceData {
  public static class ServiceBindData {
    public ComponentName component;
    
    public IServiceConnection connection;
    
    public int flags;
    
    public ServiceInfo info;
    
    public Intent intent;
    
    public int userId;
    
    public ServiceBindData(ComponentName param1ComponentName, ServiceInfo param1ServiceInfo, Intent param1Intent, int param1Int1, int param1Int2, IServiceConnection param1IServiceConnection) {
      this.component = param1ComponentName;
      this.info = param1ServiceInfo;
      this.intent = param1Intent;
      this.flags = param1Int1;
      this.userId = param1Int2;
      this.connection = param1IServiceConnection;
    }
    
    public ServiceBindData(Intent param1Intent) {
      ServiceInfo serviceInfo = (ServiceInfo)param1Intent.getParcelableExtra("info");
      this.info = serviceInfo;
      if (serviceInfo != null)
        this.component = new ComponentName(this.info.packageName, this.info.name); 
      this.intent = (Intent)param1Intent.getParcelableExtra("intent");
      this.flags = param1Intent.getIntExtra("flags", 0);
      this.userId = param1Intent.getIntExtra("user_id", 0);
      IBinder iBinder = BundleCompat.getBinder(param1Intent, "conn");
      if (iBinder != null)
        this.connection = IServiceConnection.Stub.asInterface(iBinder); 
    }
    
    public void saveToIntent(Intent param1Intent) {
      param1Intent.putExtra("info", (Parcelable)this.info);
      param1Intent.putExtra("intent", (Parcelable)this.intent);
      param1Intent.putExtra("flags", this.flags);
      param1Intent.putExtra("user_id", this.userId);
      IServiceConnection iServiceConnection = this.connection;
      if (iServiceConnection != null)
        BundleCompat.putBinder(param1Intent, "conn", iServiceConnection.asBinder()); 
    }
  }
  
  public static class ServiceStartData {
    public ComponentName component;
    
    public ServiceInfo info;
    
    public Intent intent;
    
    public int userId;
    
    public ServiceStartData(ComponentName param1ComponentName, ServiceInfo param1ServiceInfo, Intent param1Intent, int param1Int) {
      this.component = param1ComponentName;
      this.info = param1ServiceInfo;
      this.intent = param1Intent;
      this.userId = param1Int;
    }
    
    public ServiceStartData(Intent param1Intent) {
      String str = param1Intent.getType();
      if (str != null)
        this.component = ComponentName.unflattenFromString(str); 
      this.info = (ServiceInfo)param1Intent.getParcelableExtra("info");
      this.intent = (Intent)param1Intent.getParcelableExtra("intent");
      this.userId = param1Intent.getIntExtra("user_id", 0);
      if (this.info != null) {
        param1Intent = this.intent;
        if (param1Intent != null && this.component != null && param1Intent.getComponent() == null)
          this.intent.setComponent(this.component); 
      } 
    }
    
    public void saveToIntent(Intent param1Intent) {
      param1Intent.setAction("start_service");
      param1Intent.setType(this.component.flattenToString());
      param1Intent.putExtra("info", (Parcelable)this.info);
      param1Intent.putExtra("intent", (Parcelable)this.intent);
      param1Intent.putExtra("user_id", this.userId);
    }
  }
  
  public static class ServiceStopData {
    public ComponentName component;
    
    public int startId;
    
    public IBinder token;
    
    public int userId;
    
    public ServiceStopData(int param1Int1, ComponentName param1ComponentName, int param1Int2, IBinder param1IBinder) {
      this.userId = param1Int1;
      this.component = param1ComponentName;
      this.startId = param1Int2;
      this.token = param1IBinder;
    }
    
    public ServiceStopData(Intent param1Intent) {
      String str = param1Intent.getType();
      if (str != null)
        this.component = ComponentName.unflattenFromString(str); 
      this.userId = param1Intent.getIntExtra("user_id", 0);
      this.startId = param1Intent.getIntExtra("start_id", 0);
      this.token = BundleCompat.getBinder(param1Intent, "token");
    }
    
    public void saveToIntent(Intent param1Intent) {
      param1Intent.setAction("stop_service");
      param1Intent.setType(this.component.flattenToString());
      param1Intent.putExtra("user_id", this.userId);
      param1Intent.putExtra("start_id", this.startId);
      BundleCompat.putBinder(param1Intent, "token", this.token);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\ServiceData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */