package com.lody.virtual.client.ipc;

import android.os.RemoteException;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.server.interfaces.IVirtualStorageService;

public class VirtualStorageManager {
  private static final VirtualStorageManager sInstance = new VirtualStorageManager();
  
  private IVirtualStorageService mService;
  
  public static VirtualStorageManager get() {
    return sInstance;
  }
  
  private Object getRemoteInterface() {
    return IVirtualStorageService.Stub.asInterface(ServiceManagerNative.getService("vs"));
  }
  
  public IVirtualStorageService getService() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mService : Lcom/lody/virtual/server/interfaces/IVirtualStorageService;
    //   4: astore_1
    //   5: aload_1
    //   6: ifnull -> 16
    //   9: aload_1
    //   10: invokestatic isAlive : (Landroid/os/IInterface;)Z
    //   13: ifne -> 36
    //   16: aload_0
    //   17: monitorenter
    //   18: aload_0
    //   19: ldc com/lody/virtual/server/interfaces/IVirtualStorageService
    //   21: aload_0
    //   22: invokespecial getRemoteInterface : ()Ljava/lang/Object;
    //   25: invokestatic genProxy : (Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    //   28: checkcast com/lody/virtual/server/interfaces/IVirtualStorageService
    //   31: putfield mService : Lcom/lody/virtual/server/interfaces/IVirtualStorageService;
    //   34: aload_0
    //   35: monitorexit
    //   36: aload_0
    //   37: getfield mService : Lcom/lody/virtual/server/interfaces/IVirtualStorageService;
    //   40: areturn
    //   41: astore_1
    //   42: aload_0
    //   43: monitorexit
    //   44: aload_1
    //   45: athrow
    // Exception table:
    //   from	to	target	type
    //   18	36	41	finally
    //   42	44	41	finally
  }
  
  public String getVirtualStorage(String paramString, int paramInt) {
    try {
      return getService().getVirtualStorage(paramString, paramInt);
    } catch (RemoteException remoteException) {
      return (String)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public boolean isVirtualStorageEnable(String paramString, int paramInt) {
    try {
      return getService().isVirtualStorageEnable(paramString, paramInt);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public void setVirtualStorage(String paramString1, int paramInt, String paramString2) {
    try {
      getService().setVirtualStorage(paramString1, paramInt, paramString2);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void setVirtualStorageState(String paramString, int paramInt, boolean paramBoolean) {
    try {
      getService().setVirtualStorageState(paramString, paramInt, paramBoolean);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\ipc\VirtualStorageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */