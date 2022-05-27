package com.tencent.tencentmap.mapsdk.map;

import android.graphics.Rect;
import com.tencent.mapsdk.raster.model.GeoPoint;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.rastercore.f.a;

public class MapController {
  private boolean animateEable = true;
  
  private MapView mapView;
  
  public MapController(MapView paramMapView) {
    this.mapView = paramMapView;
  }
  
  public void animateTo(GeoPoint paramGeoPoint) {
    this.mapView.getMap().animateTo(a.a(paramGeoPoint));
  }
  
  public void animateTo(GeoPoint paramGeoPoint, long paramLong, CancelableCallback paramCancelableCallback) {
    this.mapView.getMap().animateTo(a.a(paramGeoPoint), paramLong, paramCancelableCallback);
  }
  
  public void animateTo(GeoPoint paramGeoPoint, Runnable paramRunnable) {
    this.mapView.getMap().animateTo(a.a(paramGeoPoint), paramRunnable);
  }
  
  public void animateTo(LatLng paramLatLng, long paramLong, CancelableCallback paramCancelableCallback) {
    this.mapView.getMap().animateTo(paramLatLng, paramLong, paramCancelableCallback);
  }
  
  public Projection getProjection() {
    return this.mapView.getProjection();
  }
  
  public void getScreenShot(TencentMap.OnScreenShotListener paramOnScreenShotListener) {
    this.mapView.getMap().getScreenShot(paramOnScreenShotListener);
  }
  
  public void getScreenShot(TencentMap.OnScreenShotListener paramOnScreenShotListener, Rect paramRect) {
    this.mapView.getMap().getScreenShot(paramOnScreenShotListener, paramRect);
  }
  
  public boolean isAnimateEable() {
    return this.animateEable;
  }
  
  public void scrollBy(float paramFloat1, float paramFloat2) {
    this.mapView.getMap().scrollBy(paramFloat1, paramFloat2);
  }
  
  public void scrollBy(float paramFloat1, float paramFloat2, long paramLong, CancelableCallback paramCancelableCallback) {
    this.mapView.getMap().scrollBy(paramFloat1, paramFloat2, paramLong, paramCancelableCallback);
  }
  
  public void setAnimateEable(boolean paramBoolean) {
    this.animateEable = paramBoolean;
  }
  
  public void setCenter(GeoPoint paramGeoPoint) {
    this.mapView.getMap().setCenter(a.a(paramGeoPoint));
  }
  
  public void setInfoWindowAdapter(TencentMap.InfoWindowAdapter paramInfoWindowAdapter) {
    this.mapView.getMap().setInfoWindowAdapter(paramInfoWindowAdapter);
  }
  
  public void setOnInfoWindowClickListener(TencentMap.OnInfoWindowClickListener paramOnInfoWindowClickListener) {
    this.mapView.getMap().setOnInfoWindowClickListener(paramOnInfoWindowClickListener);
  }
  
  public void setOnMapCameraChangeListener(TencentMap.OnMapCameraChangeListener paramOnMapCameraChangeListener) {
    this.mapView.getMap().setOnMapCameraChangeListener(paramOnMapCameraChangeListener);
  }
  
  public void setOnMapClickListener(TencentMap.OnMapClickListener paramOnMapClickListener) {
    this.mapView.getMap().setOnMapClickListener(paramOnMapClickListener);
  }
  
  public void setOnMapHitListener(final OnMapHitListener listener) {
    this.mapView.getMap().setOnMapClickListener(new TencentMap.OnMapClickListener() {
          public void onMapClick(LatLng param1LatLng) {
            listener.onMapClick(param1LatLng);
          }
        });
  }
  
  public void setOnMapLoadedListener(final OnLoadedListener listener) {
    this.mapView.getMap().setOnMapLoadedListener(new TencentMap.OnMapLoadedListener() {
          public void onMapLoaded() {
            listener.onMapLoaded();
          }
        });
  }
  
  public void setOnMapLoadedListener(TencentMap.OnMapLoadedListener paramOnMapLoadedListener) {
    this.mapView.getMap().setOnMapLoadedListener(paramOnMapLoadedListener);
  }
  
  public void setOnMapLongClickLisener(TencentMap.OnMapLongClickListener paramOnMapLongClickListener) {
    this.mapView.getMap().setOnMapLongClickListener(paramOnMapLongClickListener);
  }
  
  public void setOnMapPressClickLisener(final OnMapLongPressListener listener) {
    this.mapView.getMap().setOnMapLongClickListener(new TencentMap.OnMapLongClickListener() {
          public void onMapLongClick(LatLng param1LatLng) {
            listener.onMapLongPress(param1LatLng);
          }
        });
  }
  
  public void setOnMarkerClickListener(final OnMarkerPressListener listener) {
    this.mapView.getMap().setOnMarkerClickListener(new TencentMap.OnMarkerClickListener() {
          public boolean onMarkerClick(Marker param1Marker) {
            listener.onMarkerPressed(param1Marker);
            return false;
          }
        });
  }
  
  public void setOnMarkerClickListener(TencentMap.OnMarkerClickListener paramOnMarkerClickListener) {
    this.mapView.getMap().setOnMarkerClickListener(paramOnMarkerClickListener);
  }
  
  public void setOnMarkerDragListener(TencentMap.OnMarkerDraggedListener paramOnMarkerDraggedListener) {
    this.mapView.getMap().setOnMarkerDraggedListener(paramOnMarkerDraggedListener);
  }
  
  public void setZoom(int paramInt) {
    this.mapView.getMap().setZoom(paramInt);
  }
  
  public void stopAnimation() {
    this.mapView.stopAnimation();
  }
  
  public void zoomIn() {
    this.mapView.getMap().zoomIn();
  }
  
  public void zoomInFixing(int paramInt1, int paramInt2) {
    this.mapView.getMap().zoomInFixing(paramInt1, paramInt2);
  }
  
  public void zoomOut() {
    this.mapView.getMap().zoomOut();
  }
  
  public void zoomOutFixing(int paramInt1, int paramInt2) {
    this.mapView.getMap().zoomOutFixing(paramInt1, paramInt2);
  }
  
  public void zoomToSpan(int paramInt1, int paramInt2) {
    this.mapView.getMap().zoomToSpan(paramInt1 / 1000000.0D, paramInt2 / 1000000.0D);
  }
  
  public void zoomToSpan(GeoPoint paramGeoPoint1, GeoPoint paramGeoPoint2) {
    this.mapView.getMap().zoomToSpan(a.a(paramGeoPoint1), a.a(paramGeoPoint2));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\tencentmap\mapsdk\map\MapController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */