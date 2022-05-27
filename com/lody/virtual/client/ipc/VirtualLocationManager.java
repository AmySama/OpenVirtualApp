package com.lody.virtual.client.ipc;

import android.os.RemoteException;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.remote.vloc.VCell;
import com.lody.virtual.remote.vloc.VLocation;
import com.lody.virtual.remote.vloc.VWifi;
import com.lody.virtual.server.interfaces.IVirtualLocationManager;
import java.util.List;

public class VirtualLocationManager {
  public static final int MODE_CLOSE = 0;
  
  public static final int MODE_USE_GLOBAL = 1;
  
  public static final int MODE_USE_SELF = 2;
  
  private static final VirtualLocationManager sInstance = new VirtualLocationManager();
  
  private IVirtualLocationManager mService;
  
  public static VirtualLocationManager get() {
    return sInstance;
  }
  
  private Object getRemoteInterface() {
    return IVirtualLocationManager.Stub.asInterface(ServiceManagerNative.getService("virtual-loc"));
  }
  
  public List<VCell> getAllCell(int paramInt, String paramString) {
    try {
      return getService().getAllCell(paramInt, paramString);
    } catch (RemoteException remoteException) {
      return (List<VCell>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public List<VWifi> getAllWifi(int paramInt, String paramString) {
    try {
      return getService().getAllWifi(paramInt, paramString);
    } catch (RemoteException remoteException) {
      return (List<VWifi>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public VCell getCell(int paramInt, String paramString) {
    try {
      return getService().getCell(paramInt, paramString);
    } catch (RemoteException remoteException) {
      return (VCell)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public VLocation getGlobalLocation() {
    try {
      return getService().getGlobalLocation();
    } catch (RemoteException remoteException) {
      return (VLocation)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public VLocation getLocation() {
    return getLocation(MethodProxy.getAppUserId(), MethodProxy.getAppPkg());
  }
  
  public VLocation getLocation(int paramInt, String paramString) {
    try {
      return getService().getLocation(paramInt, paramString);
    } catch (RemoteException remoteException) {
      return (VLocation)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public int getMode() {
    return getMode(MethodProxy.getAppUserId(), MethodProxy.getAppPkg());
  }
  
  public int getMode(int paramInt, String paramString) {
    try {
      return getService().getMode(paramInt, paramString);
    } catch (RemoteException remoteException) {
      return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
    } 
  }
  
  public List<VCell> getNeighboringCell(int paramInt, String paramString) {
    try {
      return getService().getNeighboringCell(paramInt, paramString);
    } catch (RemoteException remoteException) {
      return (List<VCell>)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public IVirtualLocationManager getService() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mService : Lcom/lody/virtual/server/interfaces/IVirtualLocationManager;
    //   4: astore_1
    //   5: aload_1
    //   6: ifnull -> 16
    //   9: aload_1
    //   10: invokestatic isAlive : (Landroid/os/IInterface;)Z
    //   13: ifne -> 36
    //   16: aload_0
    //   17: monitorenter
    //   18: aload_0
    //   19: ldc com/lody/virtual/server/interfaces/IVirtualLocationManager
    //   21: aload_0
    //   22: invokespecial getRemoteInterface : ()Ljava/lang/Object;
    //   25: invokestatic genProxy : (Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    //   28: checkcast com/lody/virtual/server/interfaces/IVirtualLocationManager
    //   31: putfield mService : Lcom/lody/virtual/server/interfaces/IVirtualLocationManager;
    //   34: aload_0
    //   35: monitorexit
    //   36: aload_0
    //   37: getfield mService : Lcom/lody/virtual/server/interfaces/IVirtualLocationManager;
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
  
  public boolean isUseVirtualLocation(int paramInt, String paramString) {
    boolean bool;
    if (getMode(paramInt, paramString) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void setAllCell(int paramInt, String paramString, List<VCell> paramList) {
    try {
      getService().setAllCell(paramInt, paramString, paramList);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void setAllWifi(int paramInt, String paramString, List<VWifi> paramList) {
    try {
      getService().setAllWifi(paramInt, paramString, paramList);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void setCell(int paramInt, String paramString, VCell paramVCell) {
    try {
      getService().setCell(paramInt, paramString, paramVCell);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void setGlobalAllCell(List<VCell> paramList) {
    try {
      getService().setGlobalAllCell(paramList);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void setGlobalCell(VCell paramVCell) {
    try {
      getService().setGlobalCell(paramVCell);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void setGlobalLocation(VLocation paramVLocation) {
    try {
      getService().setGlobalLocation(paramVLocation);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void setGlobalNeighboringCell(List<VCell> paramList) {
    try {
      getService().setGlobalNeighboringCell(paramList);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void setLocation(int paramInt, String paramString, VLocation paramVLocation) {
    try {
      getService().setLocation(paramInt, paramString, paramVLocation);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void setMode(int paramInt1, String paramString, int paramInt2) {
    try {
      getService().setMode(paramInt1, paramString, paramInt2);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void setNeighboringCell(int paramInt, String paramString, List<VCell> paramList) {
    try {
      getService().setNeighboringCell(paramInt, paramString, paramList);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\ipc\VirtualLocationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */