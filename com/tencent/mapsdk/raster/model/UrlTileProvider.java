package com.tencent.mapsdk.raster.model;

import com.tencent.mapsdk.rastercore.tile.MapTile;
import com.tencent.mapsdk.rastercore.tile.c;
import java.net.URL;

public abstract class UrlTileProvider implements TileProvider {
  public final Tile getTile(int paramInt1, int paramInt2, int paramInt3, MapTile.MapSource paramMapSource, Object... paramVarArgs) {
    URL uRL = getTileUrl(paramInt1, paramInt2, paramInt3, paramVarArgs);
    return (uRL == null) ? null : new Tile(paramInt1, paramInt2, paramInt3, c.a(uRL));
  }
  
  public abstract URL getTileUrl(int paramInt1, int paramInt2, int paramInt3, Object... paramVarArgs);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\UrlTileProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */