package com.tencent.mapsdk.raster.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.tencent.mapsdk.rastercore.f.a;

public final class CameraPosition implements Parcelable {
  public static final Parcelable.Creator<CameraPosition> CREATOR = new Parcelable.Creator<CameraPosition>() {
      public final CameraPosition createFromParcel(Parcel param1Parcel) {
        float f1 = param1Parcel.readFloat();
        float f2 = param1Parcel.readFloat();
        float f3 = param1Parcel.readFloat();
        return new CameraPosition(new LatLng(f1, f2), f3);
      }
      
      public final CameraPosition[] newArray(int param1Int) {
        return new CameraPosition[param1Int];
      }
    };
  
  private static final int EMPTY_ZOOMLEVEL = -1;
  
  private final LatLng target;
  
  private float zoom = -1.0F;
  
  public CameraPosition(LatLng paramLatLng, float paramFloat) {
    this.target = paramLatLng;
    this.zoom = paramFloat;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public static Builder builder(CameraPosition paramCameraPosition) {
    return new Builder(paramCameraPosition);
  }
  
  public static final CameraPosition fromLatLngZoom(LatLng paramLatLng, float paramFloat) {
    return new CameraPosition(paramLatLng, paramFloat);
  }
  
  public final int describeContents() {
    return 0;
  }
  
  public final boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof CameraPosition))
      return false; 
    paramObject = paramObject;
    return (getTarget().equals(paramObject.getTarget()) && Float.floatToIntBits(getZoom()) == Float.floatToIntBits(paramObject.getZoom()));
  }
  
  public final LatLng getTarget() {
    return this.target;
  }
  
  public final float getZoom() {
    return this.zoom;
  }
  
  public final int hashCode() {
    return super.hashCode();
  }
  
  public final String toString() {
    return a.a(new String[] { a.a("target", getTarget()), a.a("zoom", Float.valueOf(getZoom())) });
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeFloat((float)getTarget().getLatitude());
    paramParcel.writeFloat((float)getTarget().getLongitude());
    paramParcel.writeFloat(getZoom());
  }
  
  public static final class Builder {
    private LatLng target;
    
    private float zoom = -1.0F;
    
    public Builder() {}
    
    public Builder(CameraPosition param1CameraPosition) {
      target(param1CameraPosition.getTarget()).zoom(param1CameraPosition.getZoom());
    }
    
    public final CameraPosition build() {
      return new CameraPosition(this.target, this.zoom);
    }
    
    public final Builder target(LatLng param1LatLng) {
      this.target = param1LatLng;
      return this;
    }
    
    public final Builder zoom(float param1Float) {
      this.zoom = param1Float;
      return this;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\CameraPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */