package com.tencent.mapsdk.raster.model;

import com.tencent.mapsdk.rastercore.f.a;
import java.util.Iterator;

public class LatLngBounds {
  private int mVersionCode;
  
  private LatLng northeast;
  
  private LatLng southwest;
  
  LatLngBounds(int paramInt, LatLng paramLatLng1, LatLng paramLatLng2) {
    Builder builder = (new Builder()).include(paramLatLng1).include(paramLatLng2);
    this.southwest = new LatLng(builder.mSouth, builder.mWest);
    this.northeast = new LatLng(builder.mNorth, builder.mEast);
    this.mVersionCode = paramInt;
  }
  
  public LatLngBounds(LatLng paramLatLng1, LatLng paramLatLng2) {
    this(1, paramLatLng1, paramLatLng2);
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  private boolean containsLatitude(double paramDouble) {
    return (this.southwest.getLatitude() <= paramDouble && paramDouble <= this.northeast.getLatitude());
  }
  
  private boolean containsLongitude(double paramDouble) {
    double d1 = this.southwest.getLongitude();
    double d2 = this.northeast.getLongitude();
    int i = this.southwest.getLongitude() cmp paramDouble;
    return (d1 <= d2) ? ((i <= 0 && paramDouble <= this.northeast.getLongitude())) : ((i <= 0 || paramDouble <= this.northeast.getLongitude()));
  }
  
  private boolean intersect(LatLngBounds paramLatLngBounds) {
    if (paramLatLngBounds != null) {
      LatLng latLng = paramLatLngBounds.northeast;
      if (latLng != null && paramLatLngBounds.southwest != null && this.northeast != null && this.southwest != null) {
        double d1 = latLng.getLongitude();
        double d2 = paramLatLngBounds.southwest.getLongitude();
        double d3 = this.northeast.getLongitude();
        double d4 = this.southwest.getLongitude();
        double d5 = this.northeast.getLongitude();
        double d6 = this.southwest.getLongitude();
        double d7 = paramLatLngBounds.northeast.getLongitude();
        double d8 = paramLatLngBounds.southwest.getLongitude();
        double d9 = paramLatLngBounds.northeast.getLatitude();
        double d10 = paramLatLngBounds.southwest.getLatitude();
        double d11 = this.northeast.getLatitude();
        double d12 = this.southwest.getLatitude();
        double d13 = this.northeast.getLatitude();
        double d14 = this.southwest.getLatitude();
        double d15 = paramLatLngBounds.northeast.getLatitude();
        double d16 = paramLatLngBounds.southwest.getLatitude();
        if (Math.abs(d1 + d2 - d3 - d4) < d5 - d6 + d7 - d8 && Math.abs(d9 + d10 - d11 - d12) < d13 - d14 + d15 - d16)
          return true; 
      } 
    } 
    return false;
  }
  
  private static double longitudeDistanceHeadingEast(double paramDouble1, double paramDouble2) {
    return (paramDouble2 - paramDouble1 + 360.0D) % 360.0D;
  }
  
  private static double longitudeDistanceHeadingWest(double paramDouble1, double paramDouble2) {
    return (paramDouble1 - paramDouble2 + 360.0D) % 360.0D;
  }
  
  public boolean contains(LatLng paramLatLng) {
    return (containsLatitude(paramLatLng.getLatitude()) && containsLongitude(paramLatLng.getLongitude()));
  }
  
  public boolean contains(LatLngBounds paramLatLngBounds) {
    boolean bool1 = false;
    if (paramLatLngBounds == null)
      return false; 
    boolean bool2 = bool1;
    if (contains(paramLatLngBounds.southwest)) {
      bool2 = bool1;
      if (contains(paramLatLngBounds.northeast))
        bool2 = true; 
    } 
    return bool2;
  }
  
  public final boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof LatLngBounds))
      return false; 
    paramObject = paramObject;
    return (this.southwest.equals(((LatLngBounds)paramObject).southwest) && this.northeast.equals(((LatLngBounds)paramObject).northeast));
  }
  
  public LatLng getNortheast() {
    return this.northeast;
  }
  
  public LatLng getSouthwest() {
    return this.southwest;
  }
  
  int getVersionCode() {
    return this.mVersionCode;
  }
  
  public final int hashCode() {
    return a.a(new Object[] { this.southwest, this.northeast });
  }
  
  public LatLngBounds including(LatLng paramLatLng) {
    double d1 = Math.min(this.southwest.getLatitude(), paramLatLng.getLatitude());
    double d2 = Math.max(this.northeast.getLatitude(), paramLatLng.getLatitude());
    double d3 = this.northeast.getLongitude();
    double d4 = this.southwest.getLongitude();
    double d5 = paramLatLng.getLongitude();
    double d6 = d3;
    double d7 = d4;
    if (!containsLongitude(d5))
      if (longitudeDistanceHeadingWest(d4, d5) < longitudeDistanceHeadingEast(d3, d5)) {
        d7 = d5;
        d6 = d3;
      } else {
        d6 = d5;
        d7 = d4;
      }  
    return new LatLngBounds(new LatLng(d1, d7), new LatLng(d2, d6));
  }
  
  public boolean intersects(LatLngBounds paramLatLngBounds) {
    return (paramLatLngBounds == null) ? false : ((intersect(paramLatLngBounds) || paramLatLngBounds.intersect(this)));
  }
  
  public final String toString() {
    return a.a(new String[] { a.a("southwest", this.southwest), a.a("northeast", this.northeast) });
  }
  
  public static final class Builder {
    private double mEast = Double.NaN;
    
    private double mNorth = Double.NEGATIVE_INFINITY;
    
    private double mSouth = Double.POSITIVE_INFINITY;
    
    private double mWest = Double.NaN;
    
    private boolean containsLongitude(double param1Double) {
      double d1 = this.mWest;
      double d2 = this.mEast;
      return (d1 <= d2) ? ((d1 <= param1Double && param1Double <= d2)) : ((d1 <= param1Double || param1Double <= d2));
    }
    
    public final LatLngBounds build() {
      return new LatLngBounds(new LatLng(this.mSouth, this.mWest), new LatLng(this.mNorth, this.mEast));
    }
    
    public final Builder include(LatLng param1LatLng) {
      this.mSouth = Math.min(this.mSouth, param1LatLng.getLatitude());
      this.mNorth = Math.max(this.mNorth, param1LatLng.getLatitude());
      double d = param1LatLng.getLongitude();
      if (Double.isNaN(this.mWest)) {
        this.mWest = d;
      } else if (!containsLongitude(d)) {
        if (LatLngBounds.longitudeDistanceHeadingWest(this.mWest, d) < LatLngBounds.longitudeDistanceHeadingEast(this.mEast, d)) {
          this.mWest = d;
          return this;
        } 
      } else {
        return this;
      } 
      this.mEast = d;
      return this;
    }
    
    public final Builder include(Iterable<LatLng> param1Iterable) {
      if (param1Iterable != null) {
        Iterator<LatLng> iterator = param1Iterable.iterator();
        while (iterator.hasNext())
          include(iterator.next()); 
      } 
      return this;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\LatLngBounds.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */