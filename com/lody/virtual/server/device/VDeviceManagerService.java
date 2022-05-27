package com.lody.virtual.server.device;

import com.lody.virtual.helper.collection.SparseArray;
import com.lody.virtual.remote.VDeviceConfig;
import com.lody.virtual.server.interfaces.IDeviceManager;

public class VDeviceManagerService extends IDeviceManager.Stub {
  private static final VDeviceManagerService sInstance = new VDeviceManagerService();
  
  final SparseArray<VDeviceConfig> mDeviceConfigs = new SparseArray();
  
  private DeviceInfoPersistenceLayer mPersistenceLayer;
  
  private VDeviceManagerService() {
    DeviceInfoPersistenceLayer deviceInfoPersistenceLayer = new DeviceInfoPersistenceLayer(this);
    this.mPersistenceLayer = deviceInfoPersistenceLayer;
    deviceInfoPersistenceLayer.read();
    for (byte b = 0; b < this.mDeviceConfigs.size(); b++)
      VDeviceConfig.addToPool((VDeviceConfig)this.mDeviceConfigs.valueAt(b)); 
  }
  
  public static VDeviceManagerService get() {
    return sInstance;
  }
  
  public VDeviceConfig getDeviceConfig(int paramInt) {
    synchronized (this.mDeviceConfigs) {
      VDeviceConfig vDeviceConfig1 = (VDeviceConfig)this.mDeviceConfigs.get(paramInt);
      VDeviceConfig vDeviceConfig2 = vDeviceConfig1;
      if (vDeviceConfig1 == null) {
        vDeviceConfig2 = VDeviceConfig.random();
        this.mDeviceConfigs.put(paramInt, vDeviceConfig2);
        this.mPersistenceLayer.save();
      } 
      return vDeviceConfig2;
    } 
  }
  
  public boolean isEnable(int paramInt) {
    return (getDeviceConfig(paramInt)).enable;
  }
  
  public void setEnable(int paramInt, boolean paramBoolean) {
    synchronized (this.mDeviceConfigs) {
      VDeviceConfig vDeviceConfig1 = (VDeviceConfig)this.mDeviceConfigs.get(paramInt);
      VDeviceConfig vDeviceConfig2 = vDeviceConfig1;
      if (vDeviceConfig1 == null) {
        vDeviceConfig2 = VDeviceConfig.random();
        this.mDeviceConfigs.put(paramInt, vDeviceConfig2);
      } 
      vDeviceConfig2.enable = paramBoolean;
      this.mPersistenceLayer.save();
      return;
    } 
  }
  
  public void updateDeviceConfig(int paramInt, VDeviceConfig paramVDeviceConfig) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mDeviceConfigs : Lcom/lody/virtual/helper/collection/SparseArray;
    //   4: astore_3
    //   5: aload_3
    //   6: monitorenter
    //   7: aload_2
    //   8: ifnull -> 27
    //   11: aload_0
    //   12: getfield mDeviceConfigs : Lcom/lody/virtual/helper/collection/SparseArray;
    //   15: iload_1
    //   16: aload_2
    //   17: invokevirtual put : (ILjava/lang/Object;)V
    //   20: aload_0
    //   21: getfield mPersistenceLayer : Lcom/lody/virtual/server/device/DeviceInfoPersistenceLayer;
    //   24: invokevirtual save : ()V
    //   27: aload_3
    //   28: monitorexit
    //   29: return
    //   30: astore_2
    //   31: aload_3
    //   32: monitorexit
    //   33: aload_2
    //   34: athrow
    // Exception table:
    //   from	to	target	type
    //   11	27	30	finally
    //   27	29	30	finally
    //   31	33	30	finally
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\device\VDeviceManagerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */