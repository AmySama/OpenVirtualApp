package com.tencent.mapsdk.raster.model;

import com.tencent.mapsdk.rastercore.tile.MapTile;

public interface TileProvider {
  public static final Tile NO_TILE = new Tile(-1, -1, -1, null);
  
  Tile getTile(int paramInt1, int paramInt2, int paramInt3, MapTile.MapSource paramMapSource, Object... paramVarArgs);
  
  int getTileHeight();
  
  int getTileWidth();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\TileProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */