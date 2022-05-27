package io.virtualapp.Utils;

import com.baidu.mapapi.model.LatLng;

public class GPSUtil {
  public static final String BAIDU_LBS_TYPE = "bd09ll";
  
  public static double a = 6378245.0D;
  
  public static double ee = 0.006693421622965943D;
  
  public static double pi = 3.141592653589793D;
  
  public static LatLng bd09_To_Gcj02(double paramDouble1, double paramDouble2) {
    paramDouble2 -= 0.0065D;
    double d = paramDouble1 - 0.006D;
    paramDouble1 = Math.sqrt(paramDouble2 * paramDouble2 + d * d) - Math.sin(pi * d) * 2.0E-5D;
    paramDouble2 = Math.atan2(d, paramDouble2) - Math.cos(paramDouble2 * pi) * 3.0E-6D;
    d = Math.cos(paramDouble2);
    return new LatLng(paramDouble1 * Math.sin(paramDouble2), d * paramDouble1);
  }
  
  public static LatLng bd09_To_Gps84(double paramDouble1, double paramDouble2) {
    LatLng latLng = bd09_To_Gcj02(paramDouble1, paramDouble2);
    return gcj_To_Gps84(latLng.latitude, latLng.longitude);
  }
  
  public static LatLng gcj02_To_Bd09(double paramDouble1, double paramDouble2) {
    double d = Math.sqrt(paramDouble2 * paramDouble2 + paramDouble1 * paramDouble1) + Math.sin(pi * paramDouble1) * 2.0E-5D;
    paramDouble2 = Math.atan2(paramDouble1, paramDouble2) + Math.cos(paramDouble2 * pi) * 3.0E-6D;
    paramDouble1 = Math.cos(paramDouble2);
    return new LatLng(d * Math.sin(paramDouble2) + 0.006D, paramDouble1 * d + 0.0065D);
  }
  
  public static LatLng gcj_To_Gps84(double paramDouble1, double paramDouble2) {
    LatLng latLng = transform(paramDouble1, paramDouble2);
    double d = latLng.longitude;
    return new LatLng(paramDouble1 * 2.0D - latLng.latitude, paramDouble2 * 2.0D - d);
  }
  
  public static LatLng gps84_To_Bd09(double paramDouble1, double paramDouble2) {
    LatLng latLng = new LatLng(paramDouble1, paramDouble2);
    try {
      null = gps84_To_Gcj02(latLng.latitude, latLng.longitude);
      return gcj02_To_Bd09(null.latitude, null.longitude);
    } catch (Exception exception) {
      return latLng;
    } 
  }
  
  public static LatLng gps84_To_Gcj02(double paramDouble1, double paramDouble2) {
    if (outOfChina(paramDouble1, paramDouble2))
      return null; 
    double d1 = paramDouble2 - 105.0D;
    double d2 = paramDouble1 - 35.0D;
    double d3 = transformLat(d1, d2);
    d1 = transformLon(d1, d2);
    d2 = paramDouble1 / 180.0D * pi;
    double d4 = Math.sin(d2);
    double d5 = 1.0D - ee * d4 * d4;
    d4 = Math.sqrt(d5);
    double d6 = a;
    return new LatLng(paramDouble1 + d3 * 180.0D / (1.0D - ee) * d6 / d5 * d4 * pi, paramDouble2 + d1 * 180.0D / d6 / d4 * Math.cos(d2) * pi);
  }
  
  public static boolean outOfChina(double paramDouble1, double paramDouble2) {
    return (paramDouble2 < 72.004D || paramDouble2 > 137.8347D || paramDouble1 < 0.8293D || paramDouble1 > 55.8271D);
  }
  
  public static LatLng transform(double paramDouble1, double paramDouble2) {
    if (outOfChina(paramDouble1, paramDouble2))
      return new LatLng(paramDouble1, paramDouble2); 
    double d1 = paramDouble2 - 105.0D;
    double d2 = paramDouble1 - 35.0D;
    double d3 = transformLat(d1, d2);
    d1 = transformLon(d1, d2);
    d2 = paramDouble1 / 180.0D * pi;
    double d4 = Math.sin(d2);
    double d5 = 1.0D - ee * d4 * d4;
    d4 = Math.sqrt(d5);
    double d6 = a;
    return new LatLng(paramDouble1 + d3 * 180.0D / (1.0D - ee) * d6 / d5 * d4 * pi, paramDouble2 + d1 * 180.0D / d6 / d4 * Math.cos(d2) * pi);
  }
  
  public static double transformLat(double paramDouble1, double paramDouble2) {
    double d = paramDouble1 * 2.0D;
    return -100.0D + d + paramDouble2 * 3.0D + paramDouble2 * 0.2D * paramDouble2 + 0.1D * paramDouble1 * paramDouble2 + Math.sqrt(Math.abs(paramDouble1)) * 0.2D + (Math.sin(paramDouble1 * 6.0D * pi) * 20.0D + Math.sin(d * pi) * 20.0D) * 2.0D / 3.0D + (Math.sin(pi * paramDouble2) * 20.0D + Math.sin(paramDouble2 / 3.0D * pi) * 40.0D) * 2.0D / 3.0D + (Math.sin(paramDouble2 / 12.0D * pi) * 160.0D + Math.sin(paramDouble2 * pi / 30.0D) * 320.0D) * 2.0D / 3.0D;
  }
  
  public static double transformLon(double paramDouble1, double paramDouble2) {
    double d = paramDouble1 * 0.1D;
    return paramDouble1 + 300.0D + paramDouble2 * 2.0D + d * paramDouble1 + d * paramDouble2 + Math.sqrt(Math.abs(paramDouble1)) * 0.1D + (Math.sin(6.0D * paramDouble1 * pi) * 20.0D + Math.sin(paramDouble1 * 2.0D * pi) * 20.0D) * 2.0D / 3.0D + (Math.sin(pi * paramDouble1) * 20.0D + Math.sin(paramDouble1 / 3.0D * pi) * 40.0D) * 2.0D / 3.0D + (Math.sin(paramDouble1 / 12.0D * pi) * 150.0D + Math.sin(paramDouble1 / 30.0D * pi) * 300.0D) * 2.0D / 3.0D;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\GPSUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */