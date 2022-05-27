package com.tencent.mapsdk.rastercore.tile;

import android.graphics.Canvas;
import android.graphics.PointF;
import com.tencent.mapsdk.raster.model.TileProvider;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.mapsdk.rastercore.e.a.f;
import com.tencent.mapsdk.rastercore.tile.a.b;
import com.tencent.mapsdk.rastercore.tile.b.c;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class MapTile {
  private e a;
  
  private final int b;
  
  private final int c;
  
  private final int d;
  
  private final int e;
  
  private PointF f;
  
  private MapSource g = MapSource.TENCENT;
  
  private List<a> h = new ArrayList<a>();
  
  private List<a> i = new ArrayList<a>();
  
  public MapTile(e parame, int paramInt1, int paramInt2, int paramInt3, int paramInt4, MapSource paramMapSource, List<f> paramList) {
    this.a = parame;
    this.b = paramInt1;
    this.c = paramInt2;
    this.d = paramInt3;
    this.e = paramInt4;
    this.g = paramMapSource;
    TileProvider tileProvider = c.a(this.a, paramMapSource);
    paramInt2 = this.b;
    paramInt1 = this.c;
    paramInt4 = this.d;
    paramInt3 = this.e;
    a a = new a(tileProvider, paramInt2, paramInt1, paramInt4, paramInt3, paramMapSource, b.a(paramMapSource, paramInt3));
    if (this.h.size() > 0) {
      this.h.set(0, a);
      this.i.set(0, a);
    } else {
      this.h.add(a);
      this.i.add(a);
    } 
    if (paramList != null && !paramList.isEmpty()) {
      Iterator<f> iterator = paramList.iterator();
      while (iterator.hasNext())
        a(iterator.next()); 
    } 
  }
  
  public final int a() {
    return this.d;
  }
  
  public final void a(PointF paramPointF) {
    this.f = paramPointF;
  }
  
  public final void a(f paramf) {
    this.h.size();
    a a = new a(paramf, this.b, this.c, this.d, this.e);
    this.h.add(a);
    this.i.add(a);
  }
  
  public final boolean a(Canvas paramCanvas) {
    List<a> list = this.h;
    boolean bool1 = true;
    boolean bool2 = true;
    boolean bool3 = bool1;
    if (list != null) {
      bool3 = bool1;
      if (list.size() > 0) {
        Collections.sort(this.i, a.k());
        paramCanvas.save();
        paramCanvas.translate(this.f.x, this.f.y);
        Iterator<a> iterator = this.i.iterator();
        for (bool3 = bool2; iterator.hasNext(); bool3 &= ((a)iterator.next()).a(paramCanvas));
        paramCanvas.restore();
      } 
    } 
    return bool3;
  }
  
  public final boolean a(List<a> paramList) {
    Iterator<a> iterator2 = paramList.iterator();
    while (iterator2.hasNext()) {
      String str = ((a)iterator2.next()).a();
      if (str != null && str.contains("BingGrid") && !str.endsWith(e.u()))
        return true; 
    } 
    this.h.clear();
    this.h.addAll(paramList);
    this.i.clear();
    this.i.addAll(paramList);
    Iterator<a> iterator1 = this.h.iterator();
    while (iterator1.hasNext()) {
      if (((a)iterator1.next()).f() == null)
        return true; 
    } 
    return false;
  }
  
  public final List<a> b() {
    return new ArrayList<a>(this.h);
  }
  
  public final void b(f paramf) {
    Iterator<a> iterator = this.h.iterator();
    while (iterator.hasNext()) {
      a a = iterator.next();
      if (a.a(paramf)) {
        a.h();
        iterator.remove();
        break;
      } 
    } 
  }
  
  public final void c() {
    Iterator<a> iterator = this.h.iterator();
    while (iterator.hasNext())
      ((a)iterator.next()).h(); 
  }
  
  public final boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof MapTile))
      return false; 
    paramObject = paramObject;
    return (this.b == ((MapTile)paramObject).b && this.c == ((MapTile)paramObject).c && this.d == ((MapTile)paramObject).d && this.e == ((MapTile)paramObject).e);
  }
  
  public final int hashCode() {
    return this.b * 7 + this.c * 11 + this.d * 13;
  }
  
  public final String toString() {
    StringBuilder stringBuilder = new StringBuilder(128);
    stringBuilder.append("MapTile(");
    stringBuilder.append(this.b);
    stringBuilder.append(",");
    stringBuilder.append(this.c);
    stringBuilder.append(",");
    stringBuilder.append(this.d);
    stringBuilder.append(",");
    stringBuilder.append(this.g);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  public enum MapSource {
    BING, CUSTOMER, SATELLITE, TENCENT, TRAFFIC;
    
    static {
      MapSource mapSource = new MapSource("CUSTOMER", 4);
      CUSTOMER = mapSource;
      $VALUES = new MapSource[] { TENCENT, BING, SATELLITE, TRAFFIC, mapSource };
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\tile\MapTile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */