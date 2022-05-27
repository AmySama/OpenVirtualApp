package com.lody.virtual.server.vs;

import android.os.Parcel;
import android.util.SparseArray;
import com.lody.virtual.helper.PersistenceLayer;
import com.lody.virtual.os.VEnvironment;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class VSPersistenceLayer extends PersistenceLayer {
  private static final int CURRENT_VERSION = 1;
  
  private static final char[] MAGIC = new char[] { 'v', 's', 'a' };
  
  private final VirtualStorageService mService;
  
  VSPersistenceLayer(VirtualStorageService paramVirtualStorageService) {
    super(VEnvironment.getVSConfigFile());
    this.mService = paramVirtualStorageService;
  }
  
  public int getCurrentVersion() {
    return 1;
  }
  
  public void onPersistenceFileDamage() {}
  
  public void readPersistenceData(Parcel paramParcel, int paramInt) {
    SparseArray<HashMap<String, VSConfig>> sparseArray = this.mService.getConfigs();
    for (paramInt = paramParcel.readInt(); paramInt > 0; paramInt--)
      sparseArray.put(paramParcel.readInt(), paramParcel.readHashMap(VSConfig.class.getClassLoader())); 
  }
  
  public boolean verifyMagic(Parcel paramParcel) {
    return Arrays.equals(paramParcel.createCharArray(), MAGIC);
  }
  
  public void writeMagic(Parcel paramParcel) {
    paramParcel.writeCharArray(MAGIC);
  }
  
  public void writePersistenceData(Parcel paramParcel) {
    SparseArray<HashMap<String, VSConfig>> sparseArray = this.mService.getConfigs();
    int i = sparseArray.size();
    paramParcel.writeInt(i);
    while (true) {
      int j = i - 1;
      if (i > 0) {
        i = sparseArray.keyAt(j);
        Map map = (Map)sparseArray.valueAt(j);
        paramParcel.writeInt(i);
        paramParcel.writeMap(map);
        i = j;
        continue;
      } 
      break;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\vs\VSPersistenceLayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */