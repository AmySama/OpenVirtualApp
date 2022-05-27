package com.tencent.map.geolocation.fence;

import com.tencent.map.geolocation.TencentLocation;
import java.util.List;
import java.util.Map;

public interface TxGeofenceManagerState {
  void add(int paramInt, TencentLocation paramTencentLocation);
  
  long getLastInterval();
  
  TencentLocation getLastLocation();
  
  long getLastLocationTime();
  
  Map<String, String> getLastSummary();
  
  String getLocationTimes();
  
  List<TencentLocation> getLocations();
  
  long getNextLocationTime();
  
  double getSpeed();
  
  List<Map<String, String>> getSummary();
  
  void reset();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\map\geolocation\fence\TxGeofenceManagerState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */