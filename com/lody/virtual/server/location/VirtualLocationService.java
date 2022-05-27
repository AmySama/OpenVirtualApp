package com.lody.virtual.server.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.lody.virtual.helper.PersistenceLayer;
import com.lody.virtual.helper.collection.SparseArray;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.remote.vloc.VCell;
import com.lody.virtual.remote.vloc.VLocation;
import com.lody.virtual.remote.vloc.VWifi;
import com.lody.virtual.server.interfaces.IVirtualLocationManager;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VirtualLocationService extends IVirtualLocationManager.Stub {
  private static final VirtualLocationService sInstance = new VirtualLocationService();
  
  private final VLocConfig mGlobalConfig = new VLocConfig();
  
  private final SparseArray<Map<String, VLocConfig>> mLocConfigs = new SparseArray();
  
  private final PersistenceLayer mPersistenceLayer;
  
  private VirtualLocationService() {
    PersistenceLayer persistenceLayer = new PersistenceLayer(VEnvironment.getVirtualLocationFile()) {
        public int getCurrentVersion() {
          return 1;
        }
        
        public void readPersistenceData(Parcel param1Parcel, int param1Int) {
          VirtualLocationService.this.mGlobalConfig.set(new VirtualLocationService.VLocConfig(param1Parcel));
          VirtualLocationService.this.mLocConfigs.clear();
          for (param1Int = param1Parcel.readInt(); param1Int > 0; param1Int--) {
            int i = param1Parcel.readInt();
            HashMap hashMap = param1Parcel.readHashMap(getClass().getClassLoader());
            VirtualLocationService.this.mLocConfigs.put(i, hashMap);
          } 
        }
        
        public void writePersistenceData(Parcel param1Parcel) {
          VirtualLocationService.VLocConfig vLocConfig = VirtualLocationService.this.mGlobalConfig;
          byte b = 0;
          vLocConfig.writeToParcel(param1Parcel, 0);
          param1Parcel.writeInt(VirtualLocationService.this.mLocConfigs.size());
          while (b < VirtualLocationService.this.mLocConfigs.size()) {
            int i = VirtualLocationService.this.mLocConfigs.keyAt(b);
            Map map = (Map)VirtualLocationService.this.mLocConfigs.valueAt(b);
            param1Parcel.writeInt(i);
            param1Parcel.writeMap(map);
            b++;
          } 
        }
      };
    this.mPersistenceLayer = persistenceLayer;
    persistenceLayer.read();
  }
  
  public static VirtualLocationService get() {
    return sInstance;
  }
  
  private VLocConfig getOrCreateConfig(int paramInt, String paramString) {
    Map<Object, Object> map1 = (Map)this.mLocConfigs.get(paramInt);
    Map<Object, Object> map2 = map1;
    if (map1 == null) {
      map2 = new HashMap<Object, Object>();
      this.mLocConfigs.put(paramInt, map2);
    } 
    VLocConfig vLocConfig2 = (VLocConfig)map2.get(paramString);
    VLocConfig vLocConfig1 = vLocConfig2;
    if (vLocConfig2 == null) {
      vLocConfig1 = new VLocConfig();
      vLocConfig1.mode = 0;
      map2.put(paramString, vLocConfig1);
    } 
    return vLocConfig1;
  }
  
  public List<VCell> getAllCell(int paramInt, String paramString) {
    VLocConfig vLocConfig = getOrCreateConfig(paramInt, paramString);
    this.mPersistenceLayer.save();
    paramInt = vLocConfig.mode;
    return (paramInt != 1) ? ((paramInt != 2) ? null : vLocConfig.allCell) : this.mGlobalConfig.allCell;
  }
  
  public List<VWifi> getAllWifi(int paramInt, String paramString) {
    VLocConfig vLocConfig = getOrCreateConfig(paramInt, paramString);
    this.mPersistenceLayer.save();
    return vLocConfig.allWIfi;
  }
  
  public VCell getCell(int paramInt, String paramString) {
    VLocConfig vLocConfig = getOrCreateConfig(paramInt, paramString);
    this.mPersistenceLayer.save();
    paramInt = vLocConfig.mode;
    return (paramInt != 1) ? ((paramInt != 2) ? null : vLocConfig.cell) : this.mGlobalConfig.cell;
  }
  
  public VLocation getGlobalLocation() {
    return this.mGlobalConfig.location;
  }
  
  public VLocation getLocation(int paramInt, String paramString) {
    VLocConfig vLocConfig = getOrCreateConfig(paramInt, paramString);
    this.mPersistenceLayer.save();
    paramInt = vLocConfig.mode;
    return (paramInt != 1) ? ((paramInt != 2) ? null : vLocConfig.location) : this.mGlobalConfig.location;
  }
  
  public int getMode(int paramInt, String paramString) {
    synchronized (this.mLocConfigs) {
      VLocConfig vLocConfig = getOrCreateConfig(paramInt, paramString);
      this.mPersistenceLayer.save();
      paramInt = vLocConfig.mode;
      return paramInt;
    } 
  }
  
  public List<VCell> getNeighboringCell(int paramInt, String paramString) {
    VLocConfig vLocConfig = getOrCreateConfig(paramInt, paramString);
    this.mPersistenceLayer.save();
    paramInt = vLocConfig.mode;
    return (paramInt != 1) ? ((paramInt != 2) ? null : vLocConfig.neighboringCell) : this.mGlobalConfig.neighboringCell;
  }
  
  public void setAllCell(int paramInt, String paramString, List<VCell> paramList) {
    (getOrCreateConfig(paramInt, paramString)).allCell = paramList;
    this.mPersistenceLayer.save();
  }
  
  public void setAllWifi(int paramInt, String paramString, List<VWifi> paramList) {
    (getOrCreateConfig(paramInt, paramString)).allWIfi = paramList;
    this.mPersistenceLayer.save();
  }
  
  public void setCell(int paramInt, String paramString, VCell paramVCell) {
    (getOrCreateConfig(paramInt, paramString)).cell = paramVCell;
    this.mPersistenceLayer.save();
  }
  
  public void setGlobalAllCell(List<VCell> paramList) {
    this.mGlobalConfig.allCell = paramList;
    this.mPersistenceLayer.save();
  }
  
  public void setGlobalCell(VCell paramVCell) {
    this.mGlobalConfig.cell = paramVCell;
    this.mPersistenceLayer.save();
  }
  
  public void setGlobalLocation(VLocation paramVLocation) {
    this.mGlobalConfig.location = paramVLocation;
  }
  
  public void setGlobalNeighboringCell(List<VCell> paramList) {
    this.mGlobalConfig.neighboringCell = paramList;
    this.mPersistenceLayer.save();
  }
  
  public void setLocation(int paramInt, String paramString, VLocation paramVLocation) {
    (getOrCreateConfig(paramInt, paramString)).location = paramVLocation;
    this.mPersistenceLayer.save();
  }
  
  public void setMode(int paramInt1, String paramString, int paramInt2) {
    synchronized (this.mLocConfigs) {
      (getOrCreateConfig(paramInt1, paramString)).mode = paramInt2;
      this.mPersistenceLayer.save();
      return;
    } 
  }
  
  public void setNeighboringCell(int paramInt, String paramString, List<VCell> paramList) {
    (getOrCreateConfig(paramInt, paramString)).neighboringCell = paramList;
    this.mPersistenceLayer.save();
  }
  
  private static class VLocConfig implements Parcelable {
    public static final Parcelable.Creator<VLocConfig> CREATOR = new Parcelable.Creator<VLocConfig>() {
        public VirtualLocationService.VLocConfig createFromParcel(Parcel param2Parcel) {
          return new VirtualLocationService.VLocConfig(param2Parcel);
        }
        
        public VirtualLocationService.VLocConfig[] newArray(int param2Int) {
          return new VirtualLocationService.VLocConfig[param2Int];
        }
      };
    
    List<VCell> allCell;
    
    List<VWifi> allWIfi;
    
    VCell cell;
    
    VLocation location;
    
    int mode;
    
    List<VCell> neighboringCell;
    
    VLocConfig() {}
    
    VLocConfig(Parcel param1Parcel) {
      this.mode = param1Parcel.readInt();
      this.cell = (VCell)param1Parcel.readParcelable(VCell.class.getClassLoader());
      this.allCell = param1Parcel.createTypedArrayList(VCell.CREATOR);
      this.neighboringCell = param1Parcel.createTypedArrayList(VCell.CREATOR);
      this.allWIfi = param1Parcel.createTypedArrayList(VWifi.CREATOR);
      this.location = (VLocation)param1Parcel.readParcelable(VLocation.class.getClassLoader());
    }
    
    public int describeContents() {
      return 0;
    }
    
    public void set(VLocConfig param1VLocConfig) {
      this.mode = param1VLocConfig.mode;
      this.cell = param1VLocConfig.cell;
      this.allCell = param1VLocConfig.allCell;
      this.neighboringCell = param1VLocConfig.neighboringCell;
      this.location = param1VLocConfig.location;
      this.allWIfi = param1VLocConfig.allWIfi;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeInt(this.mode);
      param1Parcel.writeParcelable((Parcelable)this.cell, param1Int);
      param1Parcel.writeTypedList(this.allCell);
      param1Parcel.writeTypedList(this.neighboringCell);
      param1Parcel.writeTypedList(this.allWIfi);
      param1Parcel.writeParcelable((Parcelable)this.location, param1Int);
    }
  }
  
  class null implements Parcelable.Creator<VLocConfig> {
    public VirtualLocationService.VLocConfig createFromParcel(Parcel param1Parcel) {
      return new VirtualLocationService.VLocConfig(param1Parcel);
    }
    
    public VirtualLocationService.VLocConfig[] newArray(int param1Int) {
      return new VirtualLocationService.VLocConfig[param1Int];
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\location\VirtualLocationService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */