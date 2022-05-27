package com.tencent.mapsdk.raster.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GeoPoint implements Parcelable {
  public static final Parcelable.Creator<GeoPoint> CREATOR = new Parcelable.Creator<GeoPoint>() {
      public final GeoPoint createFromParcel(Parcel param1Parcel) {
        return new GeoPoint(param1Parcel);
      }
      
      public final GeoPoint[] newArray(int param1Int) {
        return new GeoPoint[param1Int];
      }
    };
  
  private int e6Lat = 0;
  
  private int e6Lon = 0;
  
  public GeoPoint(int paramInt1, int paramInt2) {
    this.e6Lat = paramInt1;
    this.e6Lon = paramInt2;
  }
  
  private GeoPoint(Parcel paramParcel) {
    this.e6Lat = paramParcel.readInt();
    this.e6Lon = paramParcel.readInt();
  }
  
  public GeoPoint Copy() {
    return new GeoPoint(this.e6Lat, this.e6Lon);
  }
  
  public int describeContents() {
    return 0;
  }
  
  public final boolean equals(Object paramObject) {
    if (paramObject == null)
      return false; 
    if (paramObject.getClass() != getClass())
      return false; 
    paramObject = paramObject;
    return (this.e6Lat == ((GeoPoint)paramObject).e6Lat && this.e6Lon == ((GeoPoint)paramObject).e6Lon);
  }
  
  public int getLatitudeE6() {
    return this.e6Lat;
  }
  
  public int getLongitudeE6() {
    return this.e6Lon;
  }
  
  public final int hashCode() {
    return this.e6Lon * 7 + this.e6Lat * 11;
  }
  
  public void setLatitudeE6(int paramInt) {
    this.e6Lat = paramInt;
  }
  
  public void setLongitudeE6(int paramInt) {
    this.e6Lon = paramInt;
  }
  
  public final String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.e6Lat);
    stringBuilder.append(",");
    stringBuilder.append(this.e6Lon);
    return stringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.e6Lat);
    paramParcel.writeInt(this.e6Lon);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\GeoPoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */