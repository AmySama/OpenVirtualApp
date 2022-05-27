package com.lody.virtual.server;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.stub.KeepAliveService;
import com.lody.virtual.helper.compat.BundleCompat;
import com.lody.virtual.helper.compat.NotificationChannelCompat;
import com.lody.virtual.server.accounts.VAccountManagerService;
import com.lody.virtual.server.am.VActivityManagerService;
import com.lody.virtual.server.content.VContentService;
import com.lody.virtual.server.device.VDeviceManagerService;
import com.lody.virtual.server.fs.FileTransfer;
import com.lody.virtual.server.interfaces.IServiceFetcher;
import com.lody.virtual.server.job.VJobSchedulerService;
import com.lody.virtual.server.location.VirtualLocationService;
import com.lody.virtual.server.notification.VNotificationManagerService;
import com.lody.virtual.server.pm.VAppManagerService;
import com.lody.virtual.server.pm.VPackageManagerService;
import com.lody.virtual.server.pm.VUserManagerService;
import com.lody.virtual.server.vs.VirtualStorageService;

public final class BinderProvider extends ContentProvider {
  private static final String TAG = "BinderProvider";
  
  private static boolean sInitialized = false;
  
  public static boolean scanApps = true;
  
  private final ServiceFetcher mServiceFetcher = new ServiceFetcher();
  
  private void addService(String paramString, IBinder paramIBinder) {
    ServiceCache.addService(paramString, paramIBinder);
  }
  
  private boolean init() {
    if (sInitialized)
      return false; 
    Context context = getContext();
    if (context != null) {
      if (Build.VERSION.SDK_INT >= 26) {
        NotificationChannelCompat.checkOrCreateChannel(context, NotificationChannelCompat.DAEMON_ID, "daemon");
        NotificationChannelCompat.checkOrCreateChannel(context, NotificationChannelCompat.DEFAULT_ID, "default");
      } 
      try {
        Intent intent = new Intent();
        this(context, KeepAliveService.class);
        context.startService(intent);
      } catch (Exception exception) {
        exception.printStackTrace();
      } 
    } 
    if (!VirtualCore.get().isStartup())
      return false; 
    addService("file-transfer", (IBinder)FileTransfer.get());
    VPackageManagerService.systemReady();
    addService("package", (IBinder)VPackageManagerService.get());
    addService("activity", (IBinder)VActivityManagerService.get());
    addService("user", (IBinder)VUserManagerService.get());
    VAppManagerService.systemReady();
    addService("app", (IBinder)VAppManagerService.get());
    if (Build.VERSION.SDK_INT >= 21)
      addService("job", (IBinder)VJobSchedulerService.get()); 
    VNotificationManagerService.systemReady(context);
    addService("notification", (IBinder)VNotificationManagerService.get());
    VContentService.systemReady();
    addService("account", (IBinder)VAccountManagerService.get());
    addService("content", (IBinder)VContentService.get());
    addService("vs", (IBinder)VirtualStorageService.get());
    addService("device", (IBinder)VDeviceManagerService.get());
    addService("virtual-loc", (IBinder)VirtualLocationService.get());
    killAllProcess();
    sInitialized = true;
    if (scanApps)
      VAppManagerService.get().scanApps(); 
    VAccountManagerService.systemReady();
    return true;
  }
  
  private void killAllProcess() {
    try {
      int i = (getContext().getPackageManager().getApplicationInfo(getContext().getPackageName(), 0)).uid;
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append(getContext().getPackageName());
      stringBuilder.append(":p");
      String str = stringBuilder.toString();
    } finally {
      Exception exception = null;
    } 
  }
  
  public Bundle call(String paramString1, String paramString2, Bundle paramBundle) {
    if (!sInitialized)
      init(); 
    if ("@".equals(paramString1)) {
      Bundle bundle = new Bundle();
      BundleCompat.putBinder(bundle, "_VA_|_binder_", (IBinder)this.mServiceFetcher);
      return bundle;
    } 
    return null;
  }
  
  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString) {
    return 0;
  }
  
  public String getType(Uri paramUri) {
    return null;
  }
  
  public Uri insert(Uri paramUri, ContentValues paramContentValues) {
    return null;
  }
  
  public boolean onCreate() {
    return init();
  }
  
  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2) {
    return null;
  }
  
  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString) {
    return 0;
  }
  
  private static class ServiceFetcher extends IServiceFetcher.Stub {
    private ServiceFetcher() {}
    
    public void addService(String param1String, IBinder param1IBinder) throws RemoteException {
      if (param1String != null && param1IBinder != null)
        ServiceCache.addService(param1String, param1IBinder); 
    }
    
    public IBinder getService(String param1String) throws RemoteException {
      return (param1String != null) ? ServiceCache.getService(param1String) : null;
    }
    
    public void removeService(String param1String) throws RemoteException {
      if (param1String != null)
        ServiceCache.removeService(param1String); 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\BinderProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */