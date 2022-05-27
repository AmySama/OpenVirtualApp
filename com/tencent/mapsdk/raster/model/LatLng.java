package com.tencent.mapsdk.raster.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class LatLng implements Cloneable {
  private static DecimalFormat df = new DecimalFormat("0.000000", new DecimalFormatSymbols(Locale.US));
  
  private final double latitude;
  
  private final double longitude;
  
  public LatLng(double paramDouble1, double paramDouble2) {
    if (-180.0D > paramDouble2 || paramDouble2 >= 180.0D)
      paramDouble2 = ((paramDouble2 - 180.0D) % 360.0D + 360.0D) % 360.0D - 180.0D; 
    this.longitude = parseDouble(paramDouble2);
    this.latitude = parseDouble(Math.max(-85.0D, Math.min(85.0D, paramDouble1)));
  }
  
  private static double parseDouble(double paramDouble) {
    return Double.parseDouble(df.format(paramDouble));
  }
  
  public final LatLng clone() {
    return new LatLng(this.latitude, this.longitude);
  }
  
  public final boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof LatLng))
      return false; 
    paramObject = paramObject;
    return (Double.doubleToLongBits(this.latitude) == Double.doubleToLongBits(((LatLng)paramObject).latitude) && Double.doubleToLongBits(this.longitude) == Double.doubleToLongBits(((LatLng)paramObject).longitude));
  }
  
  public final double getLatitude() {
    return this.latitude;
  }
  
  public final double getLongitude() {
    return this.longitude;
  }
  
  public final int hashCode() {
    long l = Double.doubleToLongBits(this.latitude);
    int i = (int)(l ^ l >>> 32L);
    l = Double.doubleToLongBits(this.longitude);
    return (i + 31) * 31 + (int)(l ^ l >>> 32L);
  }
  
  public final String toString() {
    StringBuilder stringBuilder = new StringBuilder("lat/lng: (");
    stringBuilder.append(this.latitude);
    stringBuilder.append(",");
    stringBuilder.append(this.longitude);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\LatLng.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */