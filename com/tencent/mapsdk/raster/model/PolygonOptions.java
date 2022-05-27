package com.tencent.mapsdk.raster.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PolygonOptions {
  private int fillColor = -16777216;
  
  private boolean isVisible = true;
  
  private final List<LatLng> points = new ArrayList<LatLng>();
  
  private int strokeColor = -16777216;
  
  private float strokeWidth = 10.0F;
  
  private float zIndex = 0.0F;
  
  public final PolygonOptions add(LatLng paramLatLng) {
    this.points.add(paramLatLng);
    return this;
  }
  
  public final PolygonOptions add(LatLng... paramVarArgs) {
    this.points.addAll(Arrays.asList(paramVarArgs));
    return this;
  }
  
  public final PolygonOptions addAll(Iterable<LatLng> paramIterable) {
    for (LatLng latLng : paramIterable)
      this.points.add(latLng); 
    return this;
  }
  
  public final PolygonOptions fillColor(int paramInt) {
    this.fillColor = paramInt;
    return this;
  }
  
  public final int getFillColor() {
    return this.fillColor;
  }
  
  public final List<LatLng> getPoints() {
    return this.points;
  }
  
  public final int getStrokeColor() {
    return this.strokeColor;
  }
  
  public final float getStrokeWidth() {
    return this.strokeWidth;
  }
  
  public final float getZIndex() {
    return this.zIndex;
  }
  
  public final boolean isVisible() {
    return this.isVisible;
  }
  
  public final PolygonOptions strokeColor(int paramInt) {
    this.strokeColor = paramInt;
    return this;
  }
  
  public final PolygonOptions strokeWidth(float paramFloat) {
    this.strokeWidth = paramFloat;
    return this;
  }
  
  public final PolygonOptions visible(boolean paramBoolean) {
    this.isVisible = paramBoolean;
    return this;
  }
  
  public final PolygonOptions zIndex(float paramFloat) {
    this.zIndex = paramFloat;
    return this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\PolygonOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */