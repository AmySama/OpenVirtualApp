package com.tencent.tencentmap.mapsdk.map;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import com.tencent.mapsdk.raster.model.CameraPosition;
import com.tencent.mapsdk.raster.model.Circle;
import com.tencent.mapsdk.raster.model.CircleOptions;
import com.tencent.mapsdk.raster.model.GroundOverlay;
import com.tencent.mapsdk.raster.model.GroundOverlayOptions;
import com.tencent.mapsdk.raster.model.IOverlay;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.LatLngBounds;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.mapsdk.raster.model.Polygon;
import com.tencent.mapsdk.raster.model.PolygonOptions;
import com.tencent.mapsdk.raster.model.Polyline;
import com.tencent.mapsdk.raster.model.PolylineOptions;
import com.tencent.mapsdk.raster.model.QMapLanguage;
import com.tencent.mapsdk.raster.model.TileOverlay;
import com.tencent.mapsdk.raster.model.TileOverlayOptions;
import com.tencent.mapsdk.rastercore.c.a;
import com.tencent.mapsdk.rastercore.d.a;
import com.tencent.mapsdk.rastercore.d.c;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.mapsdk.rastercore.tile.MapTile;
import com.tencent.mapsdk.rastercore.tile.a.a;

public class TencentMap {
  private static OnErrorListener onErrorListener;
  
  private a contentLayer;
  
  private e mapContext;
  
  private c projection;
  
  public TencentMap(e parame) {
    this.mapContext = parame;
    this.contentLayer = parame.e();
    this.projection = parame.b();
  }
  
  private void changeCamera(CameraUpdate paramCameraUpdate, long paramLong, CancelableCallback paramCancelableCallback) {
    if (!this.mapContext.f().k() || paramLong < 0L)
      paramCameraUpdate.getCameraUpdateFactoryDelegate().a(false); 
    paramCameraUpdate.getCameraUpdateFactoryDelegate().a(paramCancelableCallback);
    paramCameraUpdate.getCameraUpdateFactoryDelegate().a(paramLong);
    this.mapContext.c().a(paramCameraUpdate.getCameraUpdateFactoryDelegate());
  }
  
  public static OnErrorListener getErrorListener() {
    return onErrorListener;
  }
  
  private boolean setBounds(LatLngBounds paramLatLngBounds) {
    return this.projection.a(paramLatLngBounds);
  }
  
  public static void setErrorListener(OnErrorListener paramOnErrorListener) {
    onErrorListener = paramOnErrorListener;
  }
  
  private void setMaxZoomLevel(int paramInt) {
    this.projection.c(paramInt);
  }
  
  private void setMinZoomLevel(int paramInt) {
    this.projection.d(paramInt);
  }
  
  public Circle addCircle(CircleOptions paramCircleOptions) {
    return new Circle(this.contentLayer.a(paramCircleOptions));
  }
  
  public GroundOverlay addGroundOverlay(GroundOverlayOptions paramGroundOverlayOptions) {
    return new GroundOverlay(this.contentLayer.a(paramGroundOverlayOptions));
  }
  
  public Marker addMarker(MarkerOptions paramMarkerOptions) {
    return new Marker(this.contentLayer.a(paramMarkerOptions));
  }
  
  public Polygon addPolygon(PolygonOptions paramPolygonOptions) {
    return new Polygon(this.contentLayer.a(paramPolygonOptions));
  }
  
  public Polyline addPolyline(PolylineOptions paramPolylineOptions) {
    return new Polyline(this.contentLayer.a(paramPolylineOptions));
  }
  
  public TileOverlay addTileOverlay(TileOverlayOptions paramTileOverlayOptions) {
    return new TileOverlay(this.mapContext.g().a(paramTileOverlayOptions));
  }
  
  public void animateCamera(CameraUpdate paramCameraUpdate) {
    changeCamera(paramCameraUpdate, 1000L, null);
  }
  
  public void animateCamera(CameraUpdate paramCameraUpdate, long paramLong, CancelableCallback paramCancelableCallback) {
    long l = paramLong;
    if (paramLong < 0L)
      l = 0L; 
    changeCamera(paramCameraUpdate, l, paramCancelableCallback);
  }
  
  public void animateCamera(CameraUpdate paramCameraUpdate, CancelableCallback paramCancelableCallback) {
    changeCamera(paramCameraUpdate, 1000L, paramCancelableCallback);
  }
  
  public void animateTo(LatLng paramLatLng) {
    changeCamera(CameraUpdateFactory.newLatLng(paramLatLng), 1000L, null);
  }
  
  public void animateTo(LatLng paramLatLng, long paramLong, CancelableCallback paramCancelableCallback) {
    changeCamera(CameraUpdateFactory.newLatLng(paramLatLng), paramLong, paramCancelableCallback);
  }
  
  public void animateTo(LatLng paramLatLng, final Runnable runAnimate) {
    changeCamera(CameraUpdateFactory.newLatLng(paramLatLng), 1000L, new CancelableCallback() {
          public void onCancel() {
            runAnimate.run();
          }
          
          public void onFinish() {
            runAnimate.run();
          }
        });
  }
  
  public final void clearAllOverlays() {
    this.contentLayer.a();
    this.mapContext.a(false, false);
  }
  
  public boolean clearCache() {
    return (a.a().a(MapTile.MapSource.BING) && a.a().a(MapTile.MapSource.TENCENT) && a.a().a(MapTile.MapSource.SATELLITE));
  }
  
  public LatLng getMapCenter() {
    return this.projection.d().getTarget();
  }
  
  public int getMaxZoomLevel() {
    return this.projection.i().a();
  }
  
  public int getMinZoomLevel() {
    return this.projection.j().a();
  }
  
  public void getScreenShot(OnScreenShotListener paramOnScreenShotListener) {
    this.mapContext.a(paramOnScreenShotListener);
  }
  
  public void getScreenShot(OnScreenShotListener paramOnScreenShotListener, Rect paramRect) {
    this.mapContext.a(paramOnScreenShotListener, paramRect);
  }
  
  public final String getVersion() {
    return "1.2.8";
  }
  
  public int getZoomLevel() {
    return (int)this.projection.d().getZoom();
  }
  
  public final boolean isAppKeyAvailable() {
    return e.q();
  }
  
  public final boolean isSatelliteEnabled() {
    return (this.mapContext.l() == 2);
  }
  
  public boolean isTrafficEnabled() {
    return this.mapContext.i().a();
  }
  
  public void moveCamera(CameraUpdate paramCameraUpdate) {
    if (paramCameraUpdate != null) {
      a a1 = paramCameraUpdate.getCameraUpdateFactoryDelegate();
      if (a1 != null) {
        a1.a(false);
        this.mapContext.c().a(a1);
      } 
    } 
  }
  
  public void removeOverlay(IOverlay paramIOverlay) {
    if (paramIOverlay == null)
      return; 
    paramIOverlay.remove();
  }
  
  public void scrollBy(float paramFloat1, float paramFloat2) {
    changeCamera(CameraUpdateFactory.scrollBy(paramFloat1, paramFloat2), 1000L, null);
  }
  
  public void scrollBy(float paramFloat1, float paramFloat2, long paramLong, CancelableCallback paramCancelableCallback) {
    changeCamera(CameraUpdateFactory.scrollBy(paramFloat1, paramFloat2), paramLong, paramCancelableCallback);
  }
  
  public void setCenter(LatLng paramLatLng) {
    changeCamera(CameraUpdateFactory.newLatLngZoom(paramLatLng, getZoomLevel()), 0L, null);
  }
  
  public void setInfoWindowAdapter(InfoWindowAdapter paramInfoWindowAdapter) {
    this.mapContext.h().a(paramInfoWindowAdapter);
  }
  
  public void setLanguage(QMapLanguage paramQMapLanguage) {
    e.a(QMapLanguage.getLanguageCode(paramQMapLanguage));
    this.mapContext.a(true, true);
  }
  
  public void setOnInfoWindowClickListener(OnInfoWindowClickListener paramOnInfoWindowClickListener) {
    this.mapContext.h().a(paramOnInfoWindowClickListener);
  }
  
  public void setOnMapCameraChangeListener(OnMapCameraChangeListener paramOnMapCameraChangeListener) {
    this.mapContext.h().a(paramOnMapCameraChangeListener);
  }
  
  public void setOnMapClickListener(OnMapClickListener paramOnMapClickListener) {
    this.mapContext.h().a(paramOnMapClickListener);
  }
  
  public void setOnMapLoadedListener(OnMapLoadedListener paramOnMapLoadedListener) {
    this.mapContext.c().a(paramOnMapLoadedListener);
  }
  
  public void setOnMapLongClickListener(OnMapLongClickListener paramOnMapLongClickListener) {
    this.mapContext.h().a(paramOnMapLongClickListener);
  }
  
  public void setOnMarkerClickListener(OnMarkerClickListener paramOnMarkerClickListener) {
    this.mapContext.h().a(paramOnMarkerClickListener);
  }
  
  public void setOnMarkerDraggedListener(OnMarkerDraggedListener paramOnMarkerDraggedListener) {
    this.mapContext.h().a(paramOnMarkerDraggedListener);
  }
  
  public void setSatelliteEnabled(boolean paramBoolean) {
    e e1;
    boolean bool;
    if (paramBoolean) {
      e1 = this.mapContext;
      bool = true;
    } else {
      e1 = this.mapContext;
      bool = true;
    } 
    e1.a(bool);
  }
  
  public void setTrafficEnabled(boolean paramBoolean) {
    this.mapContext.i().a(paramBoolean);
  }
  
  public void setZoom(int paramInt) {
    changeCamera(CameraUpdateFactory.zoomTo(paramInt), 0L, null);
  }
  
  public void stopAnimation() {
    this.mapContext.d().stopAnimation();
  }
  
  public void zoomIn() {
    changeCamera(CameraUpdateFactory.zoomIn(), 1000L, null);
  }
  
  public void zoomInFixing(int paramInt1, int paramInt2) {
    changeCamera(CameraUpdateFactory.zoomBy(1.0F, new Point(paramInt1, paramInt2)), 1000L, null);
  }
  
  public void zoomOut() {
    changeCamera(CameraUpdateFactory.zoomOut(), 1000L, null);
  }
  
  public void zoomOutFixing(int paramInt1, int paramInt2) {
    changeCamera(CameraUpdateFactory.zoomBy(-1.0F, new Point(paramInt1, paramInt2)), 1000L, null);
  }
  
  public void zoomToSpan(double paramDouble1, double paramDouble2) {
    double d1 = getMapCenter().getLatitude();
    paramDouble1 /= 2.0D;
    double d2 = getMapCenter().getLongitude();
    paramDouble2 /= 2.0D;
    zoomToSpan(new LatLng(d1 - paramDouble1, d2 + paramDouble2), new LatLng(getMapCenter().getLatitude() + paramDouble1, getMapCenter().getLongitude() - paramDouble2));
  }
  
  public void zoomToSpan(LatLng paramLatLng1, LatLng paramLatLng2) {
    LatLngBounds latLngBounds = (new LatLngBounds.Builder()).include(paramLatLng1).include(paramLatLng2).build();
    this.mapContext.c().a(CameraUpdateFactory.newLatLngBounds(latLngBounds, 10).getCameraUpdateFactoryDelegate());
  }
  
  public static interface InfoWindowAdapter {
    View getInfoWindow(Marker param1Marker);
    
    void onInfoWindowDettached(Marker param1Marker, View param1View);
  }
  
  public static interface OnErrorListener {
    void collectErrorInfo(String param1String);
  }
  
  public static interface OnInfoWindowClickListener {
    void onInfoWindowClick(Marker param1Marker);
  }
  
  public static interface OnMapCameraChangeListener {
    void onCameraChange(CameraPosition param1CameraPosition);
    
    void onCameraChangeFinish(CameraPosition param1CameraPosition);
  }
  
  public static interface OnMapClickListener {
    void onMapClick(LatLng param1LatLng);
  }
  
  public static interface OnMapLoadedListener {
    void onMapLoaded();
  }
  
  public static interface OnMapLongClickListener {
    void onMapLongClick(LatLng param1LatLng);
  }
  
  public static interface OnMarkerClickListener {
    boolean onMarkerClick(Marker param1Marker);
  }
  
  public static interface OnMarkerDraggedListener {
    void onMarkerDrag(Marker param1Marker);
    
    void onMarkerDragEnd(Marker param1Marker);
    
    void onMarkerDragStart(Marker param1Marker);
  }
  
  public static interface OnScreenShotListener {
    void onMapScreenShot(Bitmap param1Bitmap);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\tencentmap\mapsdk\map\TencentMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */