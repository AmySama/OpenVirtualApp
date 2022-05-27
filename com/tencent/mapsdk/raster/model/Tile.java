package com.tencent.mapsdk.raster.model;

public final class Tile {
  private final byte[] data;
  
  private final int x;
  
  private final int y;
  
  private final int zoom;
  
  public Tile(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfbyte) {
    this.x = paramInt1;
    this.y = paramInt2;
    this.zoom = paramInt3;
    this.data = paramArrayOfbyte;
  }
  
  public final byte[] getData() {
    return this.data;
  }
  
  public final int getX() {
    return this.x;
  }
  
  public final int getY() {
    return this.y;
  }
  
  public final int getZoom() {
    return this.zoom;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\Tile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */