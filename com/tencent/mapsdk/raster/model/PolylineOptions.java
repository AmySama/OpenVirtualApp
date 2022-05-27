package com.tencent.mapsdk.raster.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PolylineOptions {
  private float arrowGap = 90.0F;
  
  private BitmapDescriptor arrowTexture;
  
  private int color = -16777216;
  
  private int edgeColor = -983041;
  
  private float edgeWidth = 0.0F;
  
  private boolean isDottedLine = false;
  
  private boolean isGeodesic = false;
  
  private boolean isVisible = true;
  
  private final List<LatLng> points = new ArrayList<LatLng>();
  
  private float width = 10.0F;
  
  private float zIndex = 0.0F;
  
  public final PolylineOptions add(LatLng paramLatLng) {
    this.points.add(paramLatLng);
    return this;
  }
  
  public final PolylineOptions add(LatLng... paramVarArgs) {
    this.points.addAll(Arrays.asList(paramVarArgs));
    return this;
  }
  
  public final PolylineOptions addAll(Iterable<LatLng> paramIterable) {
    for (LatLng latLng : paramIterable)
      this.points.add(latLng); 
    return this;
  }
  
  public final PolylineOptions arrowGap(float paramFloat) {
    if (paramFloat > 0.0F)
      this.arrowGap = paramFloat; 
    return this;
  }
  
  public final PolylineOptions arrowTexture(BitmapDescriptor paramBitmapDescriptor) {
    if (paramBitmapDescriptor != null)
      this.arrowTexture = paramBitmapDescriptor; 
    return this;
  }
  
  public final PolylineOptions color(int paramInt) {
    this.color = paramInt;
    return this;
  }
  
  public final PolylineOptions edgeColor(int paramInt) {
    this.edgeColor = paramInt;
    return this;
  }
  
  public final PolylineOptions edgeWidth(float paramFloat) {
    if (paramFloat > 0.0F)
      this.edgeWidth = paramFloat; 
    return this;
  }
  
  public final PolylineOptions geodesic(boolean paramBoolean) {
    this.isGeodesic = paramBoolean;
    return this;
  }
  
  public final float getArrowGap() {
    return this.arrowGap;
  }
  
  public final BitmapDescriptor getArrowTexture() {
    return this.arrowTexture;
  }
  
  public final int getColor() {
    return this.color;
  }
  
  public final int getEdgeColor() {
    return this.edgeColor;
  }
  
  public final float getEdgeWidth() {
    return this.edgeWidth;
  }
  
  public final List<LatLng> getPoints() {
    return this.points;
  }
  
  public final float getWidth() {
    return this.width;
  }
  
  public final float getZIndex() {
    return this.zIndex;
  }
  
  public final boolean isDottedLine() {
    return this.isDottedLine;
  }
  
  public final boolean isGeodesic() {
    return this.isGeodesic;
  }
  
  public final boolean isVisible() {
    return this.isVisible;
  }
  
  public final PolylineOptions setDottedLine(boolean paramBoolean) {
    this.isDottedLine = paramBoolean;
    return this;
  }
  
  public final PolylineOptions visible(boolean paramBoolean) {
    this.isVisible = paramBoolean;
    return this;
  }
  
  public final PolylineOptions width(float paramFloat) {
    this.width = paramFloat;
    return this;
  }
  
  public final PolylineOptions zIndex(float paramFloat) {
    this.zIndex = paramFloat;
    return this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\PolylineOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */