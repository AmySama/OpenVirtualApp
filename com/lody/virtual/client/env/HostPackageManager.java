package com.lody.virtual.client.env;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import com.lody.virtual.os.VUserHandle;
import mirror.android.app.ActivityThread;

public abstract class HostPackageManager {
  private static HostPackageManager sInstance;
  
  public static HostPackageManager get() {
    return sInstance;
  }
  
  public static HostPackageManager init() {
    HostPackageManagerImpl hostPackageManagerImpl = new HostPackageManagerImpl();
    sInstance = hostPackageManagerImpl;
    return hostPackageManagerImpl;
  }
  
  public abstract int checkPermission(String paramString1, String paramString2);
  
  public abstract ApplicationInfo getApplicationInfo(String paramString, int paramInt) throws PackageManager.NameNotFoundException;
  
  public abstract PackageInfo getPackageInfo(String paramString, int paramInt) throws PackageManager.NameNotFoundException;
  
  public abstract String[] getPackagesForUid(int paramInt);
  
  public abstract ResolveInfo resolveActivity(Intent paramIntent, int paramInt);
  
  public abstract ProviderInfo resolveContentProvider(String paramString, int paramInt);
  
  private static class HostPackageManagerImpl extends HostPackageManager {
    private IPackageManager mService = (IPackageManager)ActivityThread.getPackageManager.call(new Object[0]);
    
    public int checkPermission(String param1String1, String param1String2) {
      try {
        return this.mService.checkPermission(param1String1, param1String2, VUserHandle.realUserId());
      } catch (RemoteException remoteException) {
        throw new RuntimeException(remoteException);
      } 
    }
    
    public ApplicationInfo getApplicationInfo(String param1String, int param1Int) throws PackageManager.NameNotFoundException {
      try {
        ApplicationInfo applicationInfo = this.mService.getApplicationInfo(param1String, param1Int, VUserHandle.realUserId());
        if (applicationInfo != null)
          return applicationInfo; 
        throw new PackageManager.NameNotFoundException(param1String);
      } catch (RemoteException remoteException) {
        throw new RuntimeException(remoteException);
      } 
    }
    
    public PackageInfo getPackageInfo(String param1String, int param1Int) throws PackageManager.NameNotFoundException {
      try {
        PackageInfo packageInfo = this.mService.getPackageInfo(param1String, param1Int, VUserHandle.realUserId());
        if (packageInfo != null)
          return packageInfo; 
        throw new PackageManager.NameNotFoundException(param1String);
      } catch (RemoteException remoteException) {
        throw new RuntimeException(remoteException);
      } 
    }
    
    public String[] getPackagesForUid(int param1Int) {
      try {
        return this.mService.getPackagesForUid(param1Int);
      } catch (RemoteException remoteException) {
        throw new RuntimeException(remoteException);
      } 
    }
    
    public ResolveInfo resolveActivity(Intent param1Intent, int param1Int) {
      try {
        return this.mService.resolveIntent(param1Intent, null, param1Int, VUserHandle.realUserId());
      } catch (RemoteException remoteException) {
        throw new RuntimeException(remoteException);
      } 
    }
    
    public ProviderInfo resolveContentProvider(String param1String, int param1Int) {
      try {
        return this.mService.resolveContentProvider(param1String, param1Int, VUserHandle.realUserId());
      } catch (RemoteException remoteException) {
        throw new RuntimeException(remoteException);
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\env\HostPackageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */