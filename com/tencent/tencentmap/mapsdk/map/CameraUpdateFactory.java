package com.tencent.tencentmap.mapsdk.map;

import android.graphics.Point;
import com.tencent.mapsdk.raster.model.CameraPosition;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.LatLngBounds;
import com.tencent.mapsdk.rastercore.c.a;
import com.tencent.mapsdk.rastercore.c.d;
import com.tencent.mapsdk.rastercore.c.f;
import com.tencent.mapsdk.rastercore.c.g;
import com.tencent.mapsdk.rastercore.c.h;
import com.tencent.mapsdk.rastercore.d;

public final class CameraUpdateFactory {
  public static CameraUpdate newCameraPosition(CameraPosition paramCameraPosition) {
    return new CameraUpdate(d.a.a(paramCameraPosition));
  }
  
  public static CameraUpdate newLatLng(LatLng paramLatLng) {
    return new CameraUpdate(d.a.a(CameraPosition.builder().target(paramLatLng).build()));
  }
  
  public static CameraUpdate newLatLngBounds(LatLngBounds paramLatLngBounds, int paramInt) {
    return new CameraUpdate(d.a.a(paramLatLngBounds, 0, 0, paramInt));
  }
  
  public static CameraUpdate newLatLngBounds(LatLngBounds paramLatLngBounds, int paramInt1, int paramInt2, int paramInt3) {
    return new CameraUpdate(d.a.a(paramLatLngBounds, paramInt1, paramInt2, paramInt3));
  }
  
  public static CameraUpdate newLatLngZoom(LatLng paramLatLng, float paramFloat) {
    return new CameraUpdate(d.a.a(CameraPosition.builder().target(paramLatLng).zoom(paramFloat).build()));
  }
  
  public static CameraUpdate scrollBy(float paramFloat1, float paramFloat2) {
    d d = new d();
    d.a(paramFloat1);
    d.b(paramFloat2);
    return new CameraUpdate((a)d);
  }
  
  public static CameraUpdate zoomBy(float paramFloat) {
    return new CameraUpdate(d.a.a(paramFloat, null));
  }
  
  public static CameraUpdate zoomBy(float paramFloat, Point paramPoint) {
    return new CameraUpdate(d.a.a(paramFloat, paramPoint));
  }
  
  public static CameraUpdate zoomIn() {
    return new CameraUpdate((a)new f());
  }
  
  public static CameraUpdate zoomOut() {
    return new CameraUpdate((a)new g());
  }
  
  public static CameraUpdate zoomTo(float paramFloat) {
    h h = new h();
    h.a(paramFloat);
    return new CameraUpdate((a)h);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\tencentmap\mapsdk\map\CameraUpdateFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */