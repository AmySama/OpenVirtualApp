package com.tencent.mapsdk.raster.model;

import android.view.View;
import android.view.animation.Animation;
import com.tencent.mapsdk.rastercore.e.a.c;

public final class Marker implements IOverlay {
  private c markerDelegate;
  
  public Marker(c paramc) {
    this.markerDelegate = paramc;
  }
  
  public final boolean equals(Object paramObject) {
    return (paramObject != null && paramObject instanceof Marker) ? this.markerDelegate.equals(((Marker)paramObject).markerDelegate) : false;
  }
  
  public final float getAlpha() {
    return this.markerDelegate.o();
  }
  
  public final String getId() {
    return this.markerDelegate.m();
  }
  
  public final View getMarkerView() {
    return this.markerDelegate.a();
  }
  
  public final LatLng getPosition() {
    return this.markerDelegate.l();
  }
  
  public final float getRotation() {
    return this.markerDelegate.j();
  }
  
  public final String getSnippet() {
    return this.markerDelegate.i();
  }
  
  public final Object getTag() {
    return this.markerDelegate.p();
  }
  
  public final String getTitle() {
    return this.markerDelegate.h();
  }
  
  public final int hashCode() {
    return this.markerDelegate.hashCode();
  }
  
  public final void hideInfoWindow() {
    this.markerDelegate.g();
  }
  
  public final boolean isDraggable() {
    return this.markerDelegate.d();
  }
  
  public final boolean isInfoWindowShown() {
    return this.markerDelegate.e();
  }
  
  public final boolean isVisible() {
    return this.markerDelegate.k();
  }
  
  public final void remove() {
    this.markerDelegate.b();
  }
  
  public final void set2Top() {
    this.markerDelegate.n();
  }
  
  public final void setAlpha(float paramFloat) {
    this.markerDelegate.b(paramFloat);
  }
  
  public final void setAnchor(float paramFloat1, float paramFloat2) {
    this.markerDelegate.a(paramFloat1, paramFloat2);
  }
  
  public final void setDraggable(boolean paramBoolean) {
    this.markerDelegate.b(paramBoolean);
  }
  
  public final void setIcon(BitmapDescriptor paramBitmapDescriptor) {
    this.markerDelegate.a(paramBitmapDescriptor);
  }
  
  public final void setInfoWindowHideAnimation(Animation paramAnimation) {
    this.markerDelegate.b(paramAnimation);
  }
  
  public final void setInfoWindowShowAnimation(Animation paramAnimation) {
    this.markerDelegate.a(paramAnimation);
  }
  
  public final void setMarkerView(View paramView) {
    this.markerDelegate.a(paramView);
  }
  
  public final void setPosition(LatLng paramLatLng) {
    this.markerDelegate.a(paramLatLng);
  }
  
  public final void setRotation(float paramFloat) {
    this.markerDelegate.a(paramFloat);
  }
  
  public final void setSnippet(String paramString) {
    this.markerDelegate.a(paramString);
  }
  
  public final void setTag(Object paramObject) {
    this.markerDelegate.a(paramObject);
  }
  
  public final void setTitle(String paramString) {
    this.markerDelegate.b(paramString);
  }
  
  public final void setVisible(boolean paramBoolean) {
    this.markerDelegate.a(paramBoolean);
  }
  
  public final void showInfoWindow() {
    this.markerDelegate.f();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\Marker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */