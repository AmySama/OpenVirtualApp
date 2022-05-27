package com.tencent.mapsdk.raster.model;

public final class TileOverlayOptions {
  private boolean diskCacheEnabled = true;
  
  private TileProvider tileProvider;
  
  private boolean visible = true;
  
  private float zIndex = 1.0F;
  
  public final TileOverlayOptions diskCacheEnabled(boolean paramBoolean) {
    this.diskCacheEnabled = paramBoolean;
    return this;
  }
  
  public final boolean getDiskCacheEnabled() {
    return this.diskCacheEnabled;
  }
  
  public final TileProvider getTileProvider() {
    return this.tileProvider;
  }
  
  public final float getZIndex() {
    return this.zIndex;
  }
  
  public final boolean isVisible() {
    return this.visible;
  }
  
  public final TileOverlayOptions tileProvider(TileProvider paramTileProvider) {
    this.tileProvider = paramTileProvider;
    return this;
  }
  
  public final TileOverlayOptions visible(boolean paramBoolean) {
    this.visible = paramBoolean;
    return this;
  }
  
  public final TileOverlayOptions zIndex(float paramFloat) {
    this.zIndex = paramFloat;
    return this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\TileOverlayOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */