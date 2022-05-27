package com.lody.virtual.client.ipc;

import android.app.Notification;
import android.os.RemoteException;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.server.interfaces.INotificationManager;
import com.lody.virtual.server.notification.NotificationCompat;

public class VNotificationManager {
  private static final VNotificationManager sInstance = new VNotificationManager();
  
  private final NotificationCompat mNotificationCompat = NotificationCompat.create();
  
  private INotificationManager mService;
  
  public static VNotificationManager get() {
    return sInstance;
  }
  
  private Object getRemoteInterface() {
    return INotificationManager.Stub.asInterface(ServiceManagerNative.getService("notification"));
  }
  
  public void addNotification(int paramInt1, String paramString1, String paramString2, int paramInt2) {
    try {
      getService().addNotification(paramInt1, paramString1, paramString2, paramInt2);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public boolean areNotificationsEnabledForPackage(String paramString, int paramInt) {
    try {
      return getService().areNotificationsEnabledForPackage(paramString, paramInt);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
      return true;
    } 
  }
  
  public void cancelAllNotification(String paramString, int paramInt) {
    try {
      getService().cancelAllNotification(paramString, paramInt);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public boolean dealNotification(int paramInt, Notification paramNotification, String paramString) {
    boolean bool = false;
    if (paramNotification == null)
      return false; 
    if (VirtualCore.get().getHostPkg().equals(paramString) || this.mNotificationCompat.dealNotification(paramInt, paramNotification, paramString))
      bool = true; 
    return bool;
  }
  
  public int dealNotificationId(int paramInt1, String paramString1, String paramString2, int paramInt2) {
    try {
      return getService().dealNotificationId(paramInt1, paramString1, paramString2, paramInt2);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
      return paramInt1;
    } 
  }
  
  public String dealNotificationTag(int paramInt1, String paramString1, String paramString2, int paramInt2) {
    try {
      return getService().dealNotificationTag(paramInt1, paramString1, paramString2, paramInt2);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
      return paramString2;
    } 
  }
  
  public INotificationManager getService() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mService : Lcom/lody/virtual/server/interfaces/INotificationManager;
    //   4: astore_1
    //   5: aload_1
    //   6: ifnull -> 16
    //   9: aload_1
    //   10: invokestatic isAlive : (Landroid/os/IInterface;)Z
    //   13: ifne -> 38
    //   16: ldc com/lody/virtual/client/ipc/VNotificationManager
    //   18: monitorenter
    //   19: aload_0
    //   20: ldc com/lody/virtual/server/interfaces/INotificationManager
    //   22: aload_0
    //   23: invokespecial getRemoteInterface : ()Ljava/lang/Object;
    //   26: invokestatic genProxy : (Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    //   29: checkcast com/lody/virtual/server/interfaces/INotificationManager
    //   32: putfield mService : Lcom/lody/virtual/server/interfaces/INotificationManager;
    //   35: ldc com/lody/virtual/client/ipc/VNotificationManager
    //   37: monitorexit
    //   38: aload_0
    //   39: getfield mService : Lcom/lody/virtual/server/interfaces/INotificationManager;
    //   42: areturn
    //   43: astore_1
    //   44: ldc com/lody/virtual/client/ipc/VNotificationManager
    //   46: monitorexit
    //   47: aload_1
    //   48: athrow
    // Exception table:
    //   from	to	target	type
    //   19	38	43	finally
    //   44	47	43	finally
  }
  
  public void setNotificationsEnabledForPackage(String paramString, boolean paramBoolean, int paramInt) {
    try {
      getService().setNotificationsEnabledForPackage(paramString, paramBoolean, paramInt);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\ipc\VNotificationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */