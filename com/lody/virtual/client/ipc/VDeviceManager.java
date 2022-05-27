package com.lody.virtual.client.ipc;

import android.os.RemoteException;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.helper.utils.ReflectException;
import com.lody.virtual.remote.VDeviceConfig;
import com.lody.virtual.server.interfaces.IDeviceManager;
import java.util.Map;
import mirror.android.os.Build;

public class VDeviceManager {
  private static final VDeviceManager sInstance = new VDeviceManager();
  
  private IDeviceManager mService;
  
  public static VDeviceManager get() {
    return sInstance;
  }
  
  private Object getRemoteInterface() {
    return IDeviceManager.Stub.asInterface(ServiceManagerNative.getService("device"));
  }
  
  public void applyBuildProp(VDeviceConfig paramVDeviceConfig) {
    for (Map.Entry entry : paramVDeviceConfig.buildProp.entrySet()) {
      try {
        Reflect.on(Build.TYPE).set((String)entry.getKey(), entry.getValue());
      } catch (ReflectException reflectException) {
        reflectException.printStackTrace();
      } 
    } 
    if (paramVDeviceConfig.serial != null)
      Reflect.on(Build.TYPE).set("SERIAL", paramVDeviceConfig.serial); 
  }
  
  public VDeviceConfig getDeviceConfig(int paramInt) {
    try {
      return getService().getDeviceConfig(paramInt);
    } catch (RemoteException remoteException) {
      return (VDeviceConfig)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public IDeviceManager getService() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mService : Lcom/lody/virtual/server/interfaces/IDeviceManager;
    //   4: invokestatic isAlive : (Landroid/os/IInterface;)Z
    //   7: ifne -> 38
    //   10: aload_0
    //   11: monitorenter
    //   12: aload_0
    //   13: ldc com/lody/virtual/server/interfaces/IDeviceManager
    //   15: aload_0
    //   16: invokespecial getRemoteInterface : ()Ljava/lang/Object;
    //   19: invokestatic genProxy : (Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    //   22: checkcast com/lody/virtual/server/interfaces/IDeviceManager
    //   25: putfield mService : Lcom/lody/virtual/server/interfaces/IDeviceManager;
    //   28: aload_0
    //   29: monitorexit
    //   30: goto -> 38
    //   33: astore_1
    //   34: aload_0
    //   35: monitorexit
    //   36: aload_1
    //   37: athrow
    //   38: aload_0
    //   39: getfield mService : Lcom/lody/virtual/server/interfaces/IDeviceManager;
    //   42: areturn
    // Exception table:
    //   from	to	target	type
    //   12	30	33	finally
    //   34	36	33	finally
  }
  
  public boolean isEnable(int paramInt) {
    try {
      return getService().isEnable(paramInt);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public void setEnable(int paramInt, boolean paramBoolean) {
    try {
      getService().setEnable(paramInt, paramBoolean);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void updateDeviceConfig(int paramInt, VDeviceConfig paramVDeviceConfig) {
    try {
      getService().updateDeviceConfig(paramInt, paramVDeviceConfig);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\ipc\VDeviceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */