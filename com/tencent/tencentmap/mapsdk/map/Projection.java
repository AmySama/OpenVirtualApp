package com.tencent.tencentmap.mapsdk.map;

import android.graphics.Point;
import android.graphics.PointF;
import com.tencent.mapsdk.raster.model.GeoPoint;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.LatLngBounds;
import com.tencent.mapsdk.raster.model.VisibleRegion;
import com.tencent.mapsdk.rastercore.d;
import com.tencent.mapsdk.rastercore.d.c;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.mapsdk.rastercore.f.a;

public class Projection {
  private e mapContext;
  
  private c projection;
  
  public Projection(e parame) {
    this.mapContext = parame;
    this.projection = parame.b();
  }
  
  public double distanceBetween(GeoPoint paramGeoPoint1, GeoPoint paramGeoPoint2) {
    return distanceBetween(a.a(paramGeoPoint1), a.a(paramGeoPoint2));
  }
  
  public double distanceBetween(LatLng paramLatLng1, LatLng paramLatLng2) {
    return d.a.a(paramLatLng1, paramLatLng2);
  }
  
  public GeoPoint fromPixels(int paramInt1, int paramInt2) {
    return a.a(this.projection.a(paramInt1, paramInt2));
  }
  
  public LatLng fromScreenLocation(Point paramPoint) {
    return this.projection.a(paramPoint.x, paramPoint.y);
  }
  
  public int getLatitudeSpan() {
    LatLngBounds latLngBounds = getVisibleRegion().getLatLngBounds();
    LatLng latLng = latLngBounds.getSouthwest();
    return (int)(latLngBounds.getNortheast().getLatitude() * 1000000.0D - latLng.getLatitude() * 1000000.0D);
  }
  
  public int getLongitudeSpan() {
    LatLngBounds latLngBounds = getVisibleRegion().getLatLngBounds();
    LatLng latLng = latLngBounds.getSouthwest();
    return (int)(latLngBounds.getNortheast().getLongitude() * 1000000.0D - latLng.getLongitude() * 1000000.0D);
  }
  
  public float getScalePerPixel() {
    return this.projection.f();
  }
  
  public VisibleRegion getVisibleRegion() {
    int i = this.mapContext.c().getWidth();
    int j = this.mapContext.c().getHeight();
    LatLng latLng1 = fromScreenLocation(new Point(0, 0));
    LatLng latLng2 = fromScreenLocation(new Point(i, 0));
    LatLng latLng3 = fromScreenLocation(new Point(0, j));
    LatLng latLng4 = fromScreenLocation(new Point(i, j));
    return new VisibleRegion(latLng3, latLng4, latLng1, latLng2, LatLngBounds.builder().include(latLng3).include(latLng4).include(latLng1).include(latLng2).build());
  }
  
  public float metersToEquatorPixels(float paramFloat) {
    return this.projection.a(paramFloat);
  }
  
  public float metersToPixels(double paramDouble1, double paramDouble2) {
    return this.projection.a(paramDouble1, paramDouble2);
  }
  
  public Point toPixels(GeoPoint paramGeoPoint, Point paramPoint) {
    Point point = paramPoint;
    if (paramPoint == null)
      point = new Point(); 
    PointF pointF = this.projection.a(a.a(paramGeoPoint));
    point.x = (int)pointF.x;
    point.y = (int)pointF.y;
    return point;
  }
  
  public Point toScreenLocation(LatLng paramLatLng) {
    PointF pointF = this.projection.a(paramLatLng);
    return new Point((int)pointF.x, (int)pointF.y);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\tencentmap\mapsdk\map\Projection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */