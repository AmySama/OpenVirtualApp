package com.tencent.mapsdk.rastercore.tile.b;

import com.tencent.mapsdk.raster.model.TileProvider;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.mapsdk.rastercore.tile.MapTile;
import java.util.Map;
import java.util.WeakHashMap;

public final class c {
  private static Map<MapTile.MapSource, TileProvider> a = new WeakHashMap<MapTile.MapSource, TileProvider>();
  
  public static TileProvider a(e parame, MapTile.MapSource paramMapSource) {
    e e1;
    boolean bool;
    if (parame.f().a() > 1) {
      bool = true;
    } else {
      bool = true;
    } 
    TileProvider tileProvider = a.get(paramMapSource);
    if (tileProvider != null)
      return tileProvider; 
    int i = null.a[paramMapSource.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          if (i != 4)
            return null; 
          f f = new f(bool);
        } else {
          d d = new d(bool);
        } 
      } else {
        b b = new b(bool);
      } 
    } else {
      e1 = new e(bool);
    } 
    a.put(paramMapSource, e1);
    return (TileProvider)e1;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\tile\b\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */