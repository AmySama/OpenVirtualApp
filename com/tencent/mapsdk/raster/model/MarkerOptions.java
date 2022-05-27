package com.tencent.mapsdk.raster.model;

import android.view.View;
import android.view.animation.Animation;

public final class MarkerOptions {
  private float alpha = 1.0F;
  
  private float anchorU = 0.5F;
  
  private float anchorV = 1.0F;
  
  private BitmapDescriptor bitmapDescriptor;
  
  private Animation hidingAnination;
  
  protected String id;
  
  private Animation infoWindowHideAnimation;
  
  private Animation infoWindowShowAnimation;
  
  private boolean isDraggable = false;
  
  private boolean isGps = false;
  
  private boolean isVisible = true;
  
  private LatLng latLng;
  
  private View markerView;
  
  private float rotation = 0.0F;
  
  private Animation showingAnination;
  
  private String snippet;
  
  private Object tag;
  
  private String title;
  
  public final MarkerOptions alpha(float paramFloat) {
    this.alpha = paramFloat;
    return this;
  }
  
  public final MarkerOptions anchor(float paramFloat1, float paramFloat2) {
    this.anchorU = paramFloat1;
    this.anchorV = paramFloat2;
    return this;
  }
  
  public final MarkerOptions draggable(boolean paramBoolean) {
    this.isDraggable = paramBoolean;
    return this;
  }
  
  public final float getAlpha() {
    return this.alpha;
  }
  
  public final float getAnchorU() {
    return this.anchorU;
  }
  
  public final float getAnchorV() {
    return this.anchorV;
  }
  
  public final Animation getHidingAnination() {
    return this.hidingAnination;
  }
  
  public final BitmapDescriptor getIcon() {
    return this.bitmapDescriptor;
  }
  
  public final Animation getInfoWindowHideAnimation() {
    return this.infoWindowHideAnimation;
  }
  
  public final Animation getInfoWindowShowAnimation() {
    return this.infoWindowShowAnimation;
  }
  
  public final View getMarkerView() {
    return this.markerView;
  }
  
  public final LatLng getPosition() {
    return this.latLng;
  }
  
  public final float getRotation() {
    return this.rotation;
  }
  
  public final Animation getShowingAnination() {
    return this.showingAnination;
  }
  
  public final String getSnippet() {
    return this.snippet;
  }
  
  public final Object getTag() {
    return this.tag;
  }
  
  public final String getTitle() {
    return this.title;
  }
  
  public final MarkerOptions icon(BitmapDescriptor paramBitmapDescriptor) {
    this.bitmapDescriptor = paramBitmapDescriptor;
    return this;
  }
  
  public final MarkerOptions infoWindowHideAnimation(Animation paramAnimation) {
    this.infoWindowHideAnimation = paramAnimation;
    return this;
  }
  
  public final MarkerOptions infoWindowShowAnimation(Animation paramAnimation) {
    this.infoWindowShowAnimation = paramAnimation;
    return this;
  }
  
  public final boolean isDraggable() {
    return this.isDraggable;
  }
  
  public final boolean isGps() {
    return this.isGps;
  }
  
  public final boolean isVisible() {
    return this.isVisible;
  }
  
  public final MarkerOptions markerView(View paramView) {
    this.markerView = paramView;
    return this;
  }
  
  public final MarkerOptions position(LatLng paramLatLng) {
    this.latLng = paramLatLng;
    return this;
  }
  
  public final MarkerOptions rotation(float paramFloat) {
    this.rotation = paramFloat;
    return this;
  }
  
  public final MarkerOptions setHidingAnination(Animation paramAnimation) {
    this.hidingAnination = paramAnimation;
    return this;
  }
  
  public final MarkerOptions setShowingAnination(Animation paramAnimation) {
    this.showingAnination = paramAnimation;
    return this;
  }
  
  public final MarkerOptions snippet(String paramString) {
    this.snippet = paramString;
    return this;
  }
  
  public final MarkerOptions tag(Object paramObject) {
    this.tag = paramObject;
    return this;
  }
  
  public final MarkerOptions title(String paramString) {
    this.title = paramString;
    return this;
  }
  
  public final MarkerOptions visible(boolean paramBoolean) {
    this.isVisible = paramBoolean;
    return this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\MarkerOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */