package com.tencent.mapsdk.raster.model;

import com.tencent.mapsdk.rastercore.e.b;
import com.tencent.mapsdk.rastercore.e.c;
import java.util.List;

public final class Polygon implements IOverlay {
  private c polygonDelegate;
  
  public Polygon(c paramc) {
    this.polygonDelegate = paramc;
  }
  
  public final boolean contains(LatLng paramLatLng) {
    return this.polygonDelegate.a(paramLatLng);
  }
  
  public final boolean equals(Object paramObject) {
    return !(paramObject instanceof Polygon) ? false : this.polygonDelegate.equalsRemote((b)((Polygon)paramObject).polygonDelegate);
  }
  
  public final int getFillColor() {
    return this.polygonDelegate.b();
  }
  
  public final String getId() {
    return this.polygonDelegate.getId();
  }
  
  public final List<LatLng> getPoints() {
    return this.polygonDelegate.c();
  }
  
  public final int getStrokeColor() {
    return this.polygonDelegate.d();
  }
  
  public final float getStrokeWidth() {
    return this.polygonDelegate.a();
  }
  
  public final float getZIndex() {
    return this.polygonDelegate.getZIndex();
  }
  
  public final int hashCode() {
    return this.polygonDelegate.hashCodeRemote();
  }
  
  public final boolean isVisible() {
    return this.polygonDelegate.isVisible();
  }
  
  public final void remove() {
    this.polygonDelegate.remove();
  }
  
  public final void setFillColor(int paramInt) {
    this.polygonDelegate.a(paramInt);
  }
  
  public final void setPoints(List<LatLng> paramList) {
    this.polygonDelegate.a(paramList);
  }
  
  public final void setStrokeColor(int paramInt) {
    this.polygonDelegate.b(paramInt);
  }
  
  public final void setStrokeWidth(float paramFloat) {
    this.polygonDelegate.a(paramFloat);
  }
  
  public final void setVisible(boolean paramBoolean) {
    this.polygonDelegate.setVisible(paramBoolean);
  }
  
  public final void setZIndex(float paramFloat) {
    this.polygonDelegate.setZIndex(paramFloat);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\Polygon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */