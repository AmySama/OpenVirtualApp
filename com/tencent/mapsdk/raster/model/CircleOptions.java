package com.tencent.mapsdk.raster.model;

import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.os.Parcel;

public final class CircleOptions {
  private DashPathEffect dashPathEffect = null;
  
  private int fillColor = 0;
  
  private String id;
  
  private boolean isVisible = true;
  
  private LatLng point = null;
  
  private double radius = 0.0D;
  
  private int strokeColor = -16777216;
  
  private boolean strokeDash = false;
  
  private float strokeWidth = 10.0F;
  
  private float zIndex = 0.0F;
  
  public final CircleOptions center(LatLng paramLatLng) {
    this.point = paramLatLng;
    return this;
  }
  
  public final int describeContents() {
    return 0;
  }
  
  public final CircleOptions fillColor(int paramInt) {
    this.fillColor = paramInt;
    return this;
  }
  
  public final LatLng getCenter() {
    return this.point;
  }
  
  public final int getFillColor() {
    return this.fillColor;
  }
  
  public final double getRadius() {
    return this.radius;
  }
  
  public final int getStrokeColor() {
    return this.strokeColor;
  }
  
  public final boolean getStrokeDash() {
    return this.strokeDash;
  }
  
  public final DashPathEffect getStrokeDashPathEffect() {
    return this.dashPathEffect;
  }
  
  public final float getStrokeWidth() {
    return this.strokeWidth;
  }
  
  public final float getZIndex() {
    return this.zIndex;
  }
  
  public final boolean isVisible() {
    return this.isVisible;
  }
  
  public final CircleOptions radius(double paramDouble) {
    this.radius = paramDouble;
    return this;
  }
  
  public final CircleOptions strokeColor(int paramInt) {
    this.strokeColor = paramInt;
    return this;
  }
  
  public final CircleOptions strokeDash(boolean paramBoolean) {
    this.strokeDash = paramBoolean;
    return this;
  }
  
  public final CircleOptions strokeDashPathEffect(DashPathEffect paramDashPathEffect) {
    this.dashPathEffect = paramDashPathEffect;
    return this;
  }
  
  public final CircleOptions strokeWidth(float paramFloat) {
    this.strokeWidth = paramFloat;
    return this;
  }
  
  public final CircleOptions visible(boolean paramBoolean) {
    this.isVisible = paramBoolean;
    return this;
  }
  
  public final void writeToParcel(Parcel paramParcel, int paramInt) {
    Bundle bundle = new Bundle();
    LatLng latLng = this.point;
    if (latLng != null) {
      bundle.putDouble("lat", latLng.getLatitude());
      bundle.putDouble("lng", this.point.getLongitude());
    } 
    paramParcel.writeBundle(bundle);
    paramParcel.writeDouble(this.radius);
    paramParcel.writeFloat(this.strokeWidth);
    paramParcel.writeInt(this.strokeColor);
    paramParcel.writeInt(this.fillColor);
    paramParcel.writeFloat(this.zIndex);
    paramParcel.writeByte((byte)this.isVisible);
    paramParcel.writeString(this.id);
  }
  
  public final CircleOptions zIndex(float paramFloat) {
    this.zIndex = paramFloat;
    return this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\CircleOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */