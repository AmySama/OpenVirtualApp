package com.lody.virtual.client.ipc;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.RemoteException;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.remote.ReceiverInfo;
import com.lody.virtual.server.IPackageInstaller;
import com.lody.virtual.server.interfaces.IPackageManager;
import java.util.List;

public class VPackageManager {
  private static final VPackageManager sMgr = new VPackageManager();
  
  private IPackageManager mService;
  
  public static VPackageManager get() {
    return sMgr;
  }
  
  private Object getRemoteInterface() {
    return IPackageManager.Stub.asInterface(ServiceManagerNative.getService("package"));
  }
  
  public boolean activitySupportsIntent(ComponentName paramComponentName, Intent paramIntent, String paramString) {
    try {
      return getService().activitySupportsIntent(paramComponentName, paramIntent, paramString);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public int checkPermission(String paramString1, String paramString2, int paramInt) {
    try {
      return getService().checkPermission(VirtualCore.get().isExtPackage(), paramString1, paramString2, paramInt);
    } catch (RemoteException remoteException) {
      return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
    } 
  }
  
  public int checkSignatures(String paramString1, String paramString2) {
    try {
      return getService().checkSignatures(paramString1, paramString2);
    } catch (RemoteException remoteException) {
      return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
    } 
  }
  
  public ActivityInfo getActivityInfo(ComponentName paramComponentName, int paramInt1, int paramInt2) {
    try {
      return getService().getActivityInfo(paramComponentName, paramInt1, paramInt2);
    } catch (RemoteException remoteException) {
      return (ActivityInfo)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public List<PermissionGroupInfo> getAllPermissionGroups(int paramInt) {
    try {
      return getService().getAllPermissionGroups(paramInt);
    } catch (RemoteException remoteException) {
      return (List<PermissionGroupInfo>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public ApplicationInfo getApplicationInfo(String paramString, int paramInt1, int paramInt2) {
    try {
      return getService().getApplicationInfo(paramString, paramInt1, paramInt2);
    } catch (RemoteException remoteException) {
      return (ApplicationInfo)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public int getComponentEnabledSetting(ComponentName paramComponentName, int paramInt) {
    try {
      return getService().getComponentEnabledSetting(paramComponentName, paramInt);
    } catch (RemoteException remoteException) {
      return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
    } 
  }
  
  public String[] getDangerousPermissions(String paramString) {
    try {
      return getService().getDangerousPermissions(paramString);
    } catch (RemoteException remoteException) {
      return (String[])VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public List<ApplicationInfo> getInstalledApplications(int paramInt1, int paramInt2) {
    try {
      return getService().getInstalledApplications(paramInt1, paramInt2).getList();
    } catch (RemoteException remoteException) {
      return (List<ApplicationInfo>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public List<PackageInfo> getInstalledPackages(int paramInt1, int paramInt2) {
    try {
      return getService().getInstalledPackages(paramInt1, paramInt2).getList();
    } catch (RemoteException remoteException) {
      return (List<PackageInfo>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public String getNameForUid(int paramInt) {
    try {
      return getService().getNameForUid(paramInt);
    } catch (RemoteException remoteException) {
      return (String)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public PackageInfo getPackageInfo(String paramString, int paramInt1, int paramInt2) {
    try {
      return getService().getPackageInfo(paramString, paramInt1, paramInt2);
    } catch (RemoteException remoteException) {
      return (PackageInfo)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public IPackageInstaller getPackageInstaller() {
    try {
      return IPackageInstaller.Stub.asInterface(getService().getPackageInstaller());
    } catch (RemoteException remoteException) {
      return (IPackageInstaller)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public int getPackageUid(String paramString, int paramInt) {
    try {
      return getService().getPackageUid(paramString, paramInt);
    } catch (RemoteException remoteException) {
      return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
    } 
  }
  
  public String[] getPackagesForUid(int paramInt) {
    try {
      return getService().getPackagesForUid(paramInt);
    } catch (RemoteException remoteException) {
      return (String[])VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public PermissionGroupInfo getPermissionGroupInfo(String paramString, int paramInt) {
    try {
      return getService().getPermissionGroupInfo(paramString, paramInt);
    } catch (RemoteException remoteException) {
      return (PermissionGroupInfo)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public PermissionInfo getPermissionInfo(String paramString, int paramInt) {
    try {
      return getService().getPermissionInfo(paramString, paramInt);
    } catch (RemoteException remoteException) {
      return (PermissionInfo)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public ProviderInfo getProviderInfo(ComponentName paramComponentName, int paramInt1, int paramInt2) {
    try {
      return getService().getProviderInfo(paramComponentName, paramInt1, paramInt2);
    } catch (RemoteException remoteException) {
      return (ProviderInfo)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public ActivityInfo getReceiverInfo(ComponentName paramComponentName, int paramInt1, int paramInt2) {
    try {
      return getService().getReceiverInfo(paramComponentName, paramInt1, paramInt2);
    } catch (RemoteException remoteException) {
      return (ActivityInfo)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public List<ReceiverInfo> getReceiverInfos(String paramString1, String paramString2, int paramInt) {
    try {
      return getService().getReceiverInfos(paramString1, paramString2, paramInt);
    } catch (RemoteException remoteException) {
      return (List<ReceiverInfo>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public IPackageManager getService() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mService : Lcom/lody/virtual/server/interfaces/IPackageManager;
    //   4: invokestatic isAlive : (Landroid/os/IInterface;)Z
    //   7: ifne -> 41
    //   10: ldc com/lody/virtual/client/ipc/VPackageManager
    //   12: monitorenter
    //   13: aload_0
    //   14: ldc com/lody/virtual/server/interfaces/IPackageManager
    //   16: aload_0
    //   17: invokespecial getRemoteInterface : ()Ljava/lang/Object;
    //   20: invokestatic genProxy : (Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    //   23: checkcast com/lody/virtual/server/interfaces/IPackageManager
    //   26: putfield mService : Lcom/lody/virtual/server/interfaces/IPackageManager;
    //   29: ldc com/lody/virtual/client/ipc/VPackageManager
    //   31: monitorexit
    //   32: goto -> 41
    //   35: astore_1
    //   36: ldc com/lody/virtual/client/ipc/VPackageManager
    //   38: monitorexit
    //   39: aload_1
    //   40: athrow
    //   41: aload_0
    //   42: getfield mService : Lcom/lody/virtual/server/interfaces/IPackageManager;
    //   45: areturn
    // Exception table:
    //   from	to	target	type
    //   13	32	35	finally
    //   36	39	35	finally
  }
  
  public ServiceInfo getServiceInfo(ComponentName paramComponentName, int paramInt1, int paramInt2) {
    try {
      return getService().getServiceInfo(paramComponentName, paramInt1, paramInt2);
    } catch (RemoteException remoteException) {
      return (ServiceInfo)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public List<ProviderInfo> queryContentProviders(String paramString, int paramInt1, int paramInt2) {
    try {
      return getService().queryContentProviders(paramString, paramInt1, paramInt2).getList();
    } catch (RemoteException remoteException) {
      return (List<ProviderInfo>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public List<ResolveInfo> queryIntentActivities(Intent paramIntent, String paramString, int paramInt1, int paramInt2) {
    try {
      return getService().queryIntentActivities(paramIntent, paramString, paramInt1, paramInt2);
    } catch (RemoteException remoteException) {
      return (List<ResolveInfo>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public List<ResolveInfo> queryIntentContentProviders(Intent paramIntent, String paramString, int paramInt1, int paramInt2) {
    try {
      return getService().queryIntentContentProviders(paramIntent, paramString, paramInt1, paramInt2);
    } catch (RemoteException remoteException) {
      return (List<ResolveInfo>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public List<ResolveInfo> queryIntentReceivers(Intent paramIntent, String paramString, int paramInt1, int paramInt2) {
    try {
      return getService().queryIntentReceivers(paramIntent, paramString, paramInt1, paramInt2);
    } catch (RemoteException remoteException) {
      return (List<ResolveInfo>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public List<ResolveInfo> queryIntentServices(Intent paramIntent, String paramString, int paramInt1, int paramInt2) {
    try {
      return getService().queryIntentServices(paramIntent, paramString, paramInt1, paramInt2);
    } catch (RemoteException remoteException) {
      return (List<ResolveInfo>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public List<PermissionInfo> queryPermissionsByGroup(String paramString, int paramInt) {
    try {
      return getService().queryPermissionsByGroup(paramString, paramInt);
    } catch (RemoteException remoteException) {
      return (List<PermissionInfo>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public List<String> querySharedPackages(String paramString) {
    try {
      return getService().querySharedPackages(paramString);
    } catch (RemoteException remoteException) {
      return (List<String>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public ProviderInfo resolveContentProvider(String paramString, int paramInt1, int paramInt2) {
    try {
      return getService().resolveContentProvider(paramString, paramInt1, paramInt2);
    } catch (RemoteException remoteException) {
      return (ProviderInfo)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public ResolveInfo resolveIntent(Intent paramIntent, String paramString, int paramInt1, int paramInt2) {
    try {
      return getService().resolveIntent(paramIntent, paramString, paramInt1, paramInt2);
    } catch (RemoteException remoteException) {
      return (ResolveInfo)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public ResolveInfo resolveService(Intent paramIntent, String paramString, int paramInt1, int paramInt2) {
    try {
      return getService().resolveService(paramIntent, paramString, paramInt1, paramInt2);
    } catch (RemoteException remoteException) {
      return (ResolveInfo)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void setComponentEnabledSetting(ComponentName paramComponentName, int paramInt1, int paramInt2, int paramInt3) {
    try {
      getService().setComponentEnabledSetting(paramComponentName, paramInt1, paramInt2, paramInt3);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\ipc\VPackageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */