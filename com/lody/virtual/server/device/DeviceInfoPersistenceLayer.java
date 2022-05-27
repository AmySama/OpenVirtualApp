package com.lody.virtual.server.device;

import android.os.Parcel;
import com.lody.virtual.helper.PersistenceLayer;
import com.lody.virtual.helper.collection.SparseArray;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.remote.VDeviceConfig;

public class DeviceInfoPersistenceLayer extends PersistenceLayer {
  private VDeviceManagerService mService;
  
  DeviceInfoPersistenceLayer(VDeviceManagerService paramVDeviceManagerService) {
    super(VEnvironment.getDeviceInfoFile());
    this.mService = paramVDeviceManagerService;
  }
  
  public int getCurrentVersion() {
    return 3;
  }
  
  public void onPersistenceFileDamage() {
    getPersistenceFile().delete();
  }
  
  public void readPersistenceData(Parcel paramParcel, int paramInt) {
    SparseArray<VDeviceConfig> sparseArray = this.mService.mDeviceConfigs;
    sparseArray.clear();
    for (paramInt = paramParcel.readInt(); paramInt > 0; paramInt--)
      sparseArray.put(paramParcel.readInt(), new VDeviceConfig(paramParcel)); 
  }
  
  public boolean verifyMagic(Parcel paramParcel) {
    return true;
  }
  
  public void writeMagic(Parcel paramParcel) {}
  
  public void writePersistenceData(Parcel paramParcel) {
    SparseArray<VDeviceConfig> sparseArray = this.mService.mDeviceConfigs;
    int i = sparseArray.size();
    paramParcel.writeInt(i);
    for (byte b = 0; b < i; b++) {
      int j = sparseArray.keyAt(b);
      VDeviceConfig vDeviceConfig = (VDeviceConfig)sparseArray.valueAt(b);
      paramParcel.writeInt(j);
      vDeviceConfig.writeToParcel(paramParcel, 0);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\device\DeviceInfoPersistenceLayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */