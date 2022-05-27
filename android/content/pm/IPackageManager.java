package android.content.pm;

import android.content.ComponentName;
import android.content.Intent;
import android.os.RemoteException;

public interface IPackageManager {
  int checkPermission(String paramString1, String paramString2, int paramInt) throws RemoteException;
  
  ActivityInfo getActivityInfo(ComponentName paramComponentName, int paramInt1, int paramInt2) throws RemoteException;
  
  ApplicationInfo getApplicationInfo(String paramString, int paramInt1, int paramInt2) throws RemoteException;
  
  int[] getPackageGids(String paramString) throws RemoteException;
  
  PackageInfo getPackageInfo(String paramString, int paramInt1, int paramInt2) throws RemoteException;
  
  int getPackageUid(String paramString, int paramInt) throws RemoteException;
  
  String[] getPackagesForUid(int paramInt) throws RemoteException;
  
  ProviderInfo getProviderInfo(ComponentName paramComponentName, int paramInt1, int paramInt2) throws RemoteException;
  
  ActivityInfo getReceiverInfo(ComponentName paramComponentName, int paramInt1, int paramInt2) throws RemoteException;
  
  ServiceInfo getServiceInfo(ComponentName paramComponentName, int paramInt1, int paramInt2) throws RemoteException;
  
  ProviderInfo resolveContentProvider(String paramString, int paramInt1, int paramInt2) throws RemoteException;
  
  ResolveInfo resolveIntent(Intent paramIntent, String paramString, int paramInt1, int paramInt2) throws RemoteException;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\content\pm\IPackageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */