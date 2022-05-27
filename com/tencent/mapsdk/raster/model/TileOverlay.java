package com.tencent.mapsdk.raster.model;

import com.tencent.mapsdk.rastercore.e.a.f;

public final class TileOverlay implements IOverlay {
  private f delegate;
  
  public TileOverlay(f paramf) {
    this.delegate = paramf;
  }
  
  public final void clearTileCache() {
    f.c();
  }
  
  public final boolean equals(Object paramObject) {
    if (paramObject == null)
      return false; 
    if (!(paramObject instanceof TileOverlay))
      return false; 
    paramObject = paramObject;
    return this.delegate.equals(((TileOverlay)paramObject).delegate);
  }
  
  public final String getId() {
    return this.delegate.d();
  }
  
  public final int hashCode() {
    return this.delegate.hashCode();
  }
  
  public final boolean isVisible() {
    return this.delegate.e();
  }
  
  public final void remove() {
    this.delegate.b();
  }
  
  public final void setVisible(boolean paramBoolean) {
    this.delegate.a(paramBoolean);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\TileOverlay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */