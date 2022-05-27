package com.lody.virtual.client.ipc;

import android.os.IBinder;
import android.os.RemoteException;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.server.ServiceCache;
import com.lody.virtual.server.interfaces.IServiceFetcher;

public class ServiceManagerNative {
  public static final String ACCOUNT = "account";
  
  public static final String ACTIVITY = "activity";
  
  public static final String APP = "app";
  
  public static final String CONTENT = "content";
  
  public static final String DEVICE = "device";
  
  public static final String FILE_TRANSFER = "file-transfer";
  
  public static final String JOB = "job";
  
  public static final String NOTIFICATION = "notification";
  
  public static final String PACKAGE = "package";
  
  private static final String TAG = ServiceManagerNative.class.getSimpleName();
  
  public static final String USER = "user";
  
  public static final String VIRTUAL_LOC = "virtual-loc";
  
  public static final String VS = "vs";
  
  private static IServiceFetcher sFetcher;
  
  public static void addService(String paramString, IBinder paramIBinder) {
    IServiceFetcher iServiceFetcher = getServiceFetcher();
    if (iServiceFetcher != null)
      try {
        iServiceFetcher.addService(paramString, paramIBinder);
      } catch (RemoteException remoteException) {
        remoteException.printStackTrace();
      }  
  }
  
  public static void clearServerFetcher() {
    sFetcher = null;
  }
  
  public static void ensureServerStarted() {
    (new ProviderCall.Builder(VirtualCore.get().getContext(), getAuthority())).methodName("ensure_created").callSafely();
  }
  
  private static String getAuthority() {
    return VirtualCore.getConfig().getBinderProviderAuthority();
  }
  
  public static IBinder getService(String paramString) {
    if (VirtualCore.get().isServerProcess())
      return ServiceCache.getService(paramString); 
    IServiceFetcher iServiceFetcher = getServiceFetcher();
    if (iServiceFetcher != null)
      try {
        return iServiceFetcher.getService(paramString);
      } catch (RemoteException remoteException) {
        remoteException.printStackTrace();
      }  
    VLog.e(TAG, "GetService(%s) return null.", new Object[] { paramString });
    return null;
  }
  
  private static IServiceFetcher getServiceFetcher() {
    // Byte code:
    //   0: getstatic com/lody/virtual/client/ipc/ServiceManagerNative.sFetcher : Lcom/lody/virtual/server/interfaces/IServiceFetcher;
    //   3: astore_0
    //   4: aload_0
    //   5: ifnull -> 22
    //   8: aload_0
    //   9: invokeinterface asBinder : ()Landroid/os/IBinder;
    //   14: invokeinterface isBinderAlive : ()Z
    //   19: ifne -> 79
    //   22: ldc com/lody/virtual/client/ipc/ServiceManagerNative
    //   24: monitorenter
    //   25: invokestatic get : ()Lcom/lody/virtual/client/core/VirtualCore;
    //   28: invokevirtual getContext : ()Landroid/content/Context;
    //   31: astore_0
    //   32: new com/lody/virtual/client/ipc/ProviderCall$Builder
    //   35: astore_1
    //   36: aload_1
    //   37: aload_0
    //   38: invokestatic getAuthority : ()Ljava/lang/String;
    //   41: invokespecial <init> : (Landroid/content/Context;Ljava/lang/String;)V
    //   44: aload_1
    //   45: ldc '@'
    //   47: invokevirtual methodName : (Ljava/lang/String;)Lcom/lody/virtual/client/ipc/ProviderCall$Builder;
    //   50: invokevirtual callSafely : ()Landroid/os/Bundle;
    //   53: astore_0
    //   54: aload_0
    //   55: ifnull -> 76
    //   58: aload_0
    //   59: ldc '_VA_|_binder_'
    //   61: invokestatic getBinder : (Landroid/os/Bundle;Ljava/lang/String;)Landroid/os/IBinder;
    //   64: astore_0
    //   65: aload_0
    //   66: invokestatic linkBinderDied : (Landroid/os/IBinder;)V
    //   69: aload_0
    //   70: invokestatic asInterface : (Landroid/os/IBinder;)Lcom/lody/virtual/server/interfaces/IServiceFetcher;
    //   73: putstatic com/lody/virtual/client/ipc/ServiceManagerNative.sFetcher : Lcom/lody/virtual/server/interfaces/IServiceFetcher;
    //   76: ldc com/lody/virtual/client/ipc/ServiceManagerNative
    //   78: monitorexit
    //   79: getstatic com/lody/virtual/client/ipc/ServiceManagerNative.sFetcher : Lcom/lody/virtual/server/interfaces/IServiceFetcher;
    //   82: areturn
    //   83: astore_0
    //   84: ldc com/lody/virtual/client/ipc/ServiceManagerNative
    //   86: monitorexit
    //   87: aload_0
    //   88: athrow
    // Exception table:
    //   from	to	target	type
    //   25	54	83	finally
    //   58	76	83	finally
    //   76	79	83	finally
    //   84	87	83	finally
  }
  
  private static void linkBinderDied(final IBinder binder) {
    IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        public void binderDied() {
          binder.unlinkToDeath(this, 0);
        }
      };
    try {
      binder.linkToDeath(deathRecipient, 0);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public static void linkToDeath(IBinder.DeathRecipient paramDeathRecipient) {
    try {
      getServiceFetcher().asBinder().linkToDeath(paramDeathRecipient, 0);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public static void removeService(String paramString) {
    IServiceFetcher iServiceFetcher = getServiceFetcher();
    if (iServiceFetcher != null)
      try {
        iServiceFetcher.removeService(paramString);
      } catch (RemoteException remoteException) {
        remoteException.printStackTrace();
      }  
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\ipc\ServiceManagerNative.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */