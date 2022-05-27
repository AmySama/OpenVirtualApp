package com.tencent.tencentmap.mapsdk.map;

import android.graphics.Canvas;
import android.view.MotionEvent;
import com.tencent.mapsdk.raster.model.GeoPoint;
import com.tencent.mapsdk.rastercore.d.a;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.mapsdk.rastercore.e.b;

public class Overlay implements b {
  protected static final float SHADOW_X_SKEW = -0.89F;
  
  protected static final float SHADOW_Y_SCALE = 0.5F;
  
  protected a contentLayer;
  
  protected String id = getId();
  
  protected boolean isVisible = true;
  
  protected e mapContext;
  
  protected MapView mapView;
  
  protected float zIndex = 0.0F;
  
  public boolean checkInBounds() {
    return false;
  }
  
  public void destroy() {}
  
  public void draw(Canvas paramCanvas) {
    draw(paramCanvas, this.mapView);
  }
  
  protected void draw(Canvas paramCanvas, MapView paramMapView) {}
  
  public boolean equalsRemote(b paramb) {
    return (equals(paramb) || paramb.getId().equals(getId()));
  }
  
  public String getId() {
    if (this.id == null)
      this.id = a.a("Overlay"); 
    return this.id;
  }
  
  public float getZIndex() {
    return this.zIndex;
  }
  
  public int hashCodeRemote() {
    return 0;
  }
  
  public void init(MapView paramMapView) {
    this.mapView = paramMapView;
    e e1 = paramMapView.getMapContext();
    this.mapContext = e1;
    this.contentLayer = e1.e();
  }
  
  public boolean isVisible() {
    return this.isVisible;
  }
  
  public void onEmptyTap(GeoPoint paramGeoPoint) {}
  
  public boolean onLongPress(GeoPoint paramGeoPoint, MotionEvent paramMotionEvent, MapView paramMapView) {
    return false;
  }
  
  public void onRemoveOverlay() {}
  
  public boolean onTap(GeoPoint paramGeoPoint, MapView paramMapView) {
    return false;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent, MapView paramMapView) {
    return false;
  }
  
  public void remove() {
    this.contentLayer.b(getId());
    onRemoveOverlay();
  }
  
  public void setVisible(boolean paramBoolean) {
    this.isVisible = paramBoolean;
    this.mapContext.a(false, false);
  }
  
  public void setZIndex(float paramFloat) {
    this.zIndex = paramFloat;
    this.contentLayer.c();
    this.mapContext.a(false, false);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\tencentmap\mapsdk\map\Overlay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */