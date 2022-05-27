package com.tencent.mapsdk.raster.model;

import com.tencent.mapsdk.rastercore.f.a;

public final class VisibleRegion {
  private final LatLng farLeft;
  
  private final LatLng farRight;
  
  private final LatLngBounds latLngBounds;
  
  private final int mVersionCode;
  
  private final LatLng nearLeft;
  
  private final LatLng nearRight;
  
  protected VisibleRegion(int paramInt, LatLng paramLatLng1, LatLng paramLatLng2, LatLng paramLatLng3, LatLng paramLatLng4, LatLngBounds paramLatLngBounds) {
    this.mVersionCode = paramInt;
    this.nearLeft = paramLatLng1;
    this.nearRight = paramLatLng2;
    this.farLeft = paramLatLng3;
    this.farRight = paramLatLng4;
    this.latLngBounds = paramLatLngBounds;
  }
  
  public VisibleRegion(LatLng paramLatLng1, LatLng paramLatLng2, LatLng paramLatLng3, LatLng paramLatLng4, LatLngBounds paramLatLngBounds) {
    this(1, paramLatLng1, paramLatLng2, paramLatLng3, paramLatLng4, paramLatLngBounds);
  }
  
  public final boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof VisibleRegion))
      return false; 
    paramObject = paramObject;
    return (getNearLeft().equals(paramObject.getNearLeft()) && getNearRight().equals(paramObject.getNearRight()) && getFarLeft().equals(paramObject.getFarLeft()) && getFarRight().equals(paramObject.getFarRight()) && getLatLngBounds().equals(paramObject.getLatLngBounds()));
  }
  
  public final LatLng getFarLeft() {
    return this.farLeft;
  }
  
  public final LatLng getFarRight() {
    return this.farRight;
  }
  
  public final LatLngBounds getLatLngBounds() {
    return this.latLngBounds;
  }
  
  public final LatLng getNearLeft() {
    return this.nearLeft;
  }
  
  public final LatLng getNearRight() {
    return this.nearRight;
  }
  
  protected final int getVersionCode() {
    return this.mVersionCode;
  }
  
  public final int hashCode() {
    return a.a(new Object[] { getNearLeft(), getNearRight(), getFarLeft(), getFarRight(), getLatLngBounds() });
  }
  
  public final String toString() {
    return a.a(new String[] { a.a("nearLeft", getNearLeft()), a.a("nearRight", getNearRight()), a.a("farLeft", getFarLeft()), a.a("farRight", getFarRight()), a.a("latLngBounds", getLatLngBounds()) });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\VisibleRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */