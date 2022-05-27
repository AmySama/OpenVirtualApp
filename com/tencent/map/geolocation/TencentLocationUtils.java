package com.tencent.map.geolocation;

import android.content.Context;
import c.t.m.g.b;
import c.t.m.g.ea;

public class TencentLocationUtils {
  private TencentLocationUtils() {
    throw new UnsupportedOperationException();
  }
  
  public static boolean contains(TencentLocation paramTencentLocation1, double paramDouble, TencentLocation paramTencentLocation2) {
    if (paramTencentLocation1 != null && paramTencentLocation2 != null)
      return (distanceBetween(paramTencentLocation1, paramTencentLocation2) <= paramDouble); 
    throw null;
  }
  
  public static double distanceBetween(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    return b.a.a(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
  }
  
  public static double distanceBetween(TencentLocation paramTencentLocation1, TencentLocation paramTencentLocation2) {
    if (paramTencentLocation1 != null && paramTencentLocation2 != null)
      return b.a.a(paramTencentLocation1.getLatitude(), paramTencentLocation1.getLongitude(), paramTencentLocation2.getLatitude(), paramTencentLocation2.getLongitude()); 
    throw null;
  }
  
  public static boolean isFromGps(TencentLocation paramTencentLocation) {
    return (paramTencentLocation == null) ? false : "gps".equals(paramTencentLocation.getProvider());
  }
  
  public static boolean isFromNetwork(TencentLocation paramTencentLocation) {
    return (paramTencentLocation == null) ? false : "network".equals(paramTencentLocation.getProvider());
  }
  
  public static boolean isSupportGps(Context paramContext) {
    if (paramContext != null) {
      ea.a();
      return ((ea.a(paramContext) & 0x10) == 0);
    } 
    throw null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\map\geolocation\TencentLocationUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */