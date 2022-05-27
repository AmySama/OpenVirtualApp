package com.tencent.mapsdk.rastercore.tile;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import com.tencent.mapsdk.raster.model.Tile;
import com.tencent.mapsdk.raster.model.TileOverlayOptions;
import com.tencent.mapsdk.raster.model.TileProvider;
import com.tencent.mapsdk.rastercore.b.c;
import com.tencent.mapsdk.rastercore.c;
import com.tencent.mapsdk.rastercore.d.b;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.mapsdk.rastercore.e.a.f;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class a {
  private static final Comparator<a> l = new a((byte)0);
  
  private final int a;
  
  private final int b;
  
  private final int c;
  
  private MapTile.MapSource d;
  
  private final int e;
  
  private TileProvider f;
  
  private Bitmap g;
  
  private String h;
  
  private boolean i = true;
  
  private volatile boolean j = false;
  
  private f k;
  
  public a(TileProvider paramTileProvider, int paramInt1, int paramInt2, int paramInt3, int paramInt4, MapTile.MapSource paramMapSource, String paramString) {
    this.f = paramTileProvider;
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = paramInt3;
    this.h = paramString;
    this.d = paramMapSource;
    this.e = paramInt4;
  }
  
  public a(f paramf, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this(paramf.f(), paramInt1, paramInt2, paramInt3, paramInt4, MapTile.MapSource.CUSTOMER, paramf.g());
    this.k = paramf;
    this.i = paramf.h();
  }
  
  public static Comparator<a> k() {
    return l;
  }
  
  public final String a() {
    return this.h;
  }
  
  public final void a(Bitmap paramBitmap) {
    this.g = paramBitmap;
  }
  
  public final boolean a(Canvas paramCanvas) {
    f f1 = this.k;
    if (f1 != null && !f1.e())
      return false; 
    Bitmap bitmap = this.g;
    if (bitmap != null && !bitmap.isRecycled())
      try {
        paramCanvas.drawBitmap(this.g, 0.0F, 0.0F, null);
        return true;
      } catch (Exception exception) {
        Bitmap bitmap1 = this.g;
        if (bitmap1 != null && !bitmap1.isRecycled())
          this.g.recycle(); 
        this.g = null;
      }  
    return false;
  }
  
  public final boolean a(f paramf) {
    f f1 = this.k;
    return (f1 == null) ? false : f1.equals(paramf);
  }
  
  public final byte[] a(boolean paramBoolean, String paramString) {
    try {
      Tile tile = this.f.getTile(this.a, this.b, this.c, this.d, new Object[] { Integer.valueOf(this.e), paramString, Boolean.valueOf(paramBoolean) });
      return (tile != null) ? tile.getData() : null;
    } catch (Exception exception) {
      (new StringBuilder("get tile raises exception:")).append(exception.getMessage());
      return null;
    } 
  }
  
  public final int b() {
    return this.a;
  }
  
  public final int c() {
    return this.b;
  }
  
  public final int d() {
    return this.c;
  }
  
  public final TileProvider e() {
    return this.f;
  }
  
  public final Bitmap f() {
    return this.g;
  }
  
  public final boolean g() {
    return this.i;
  }
  
  public final void h() {
    this.j = true;
    Bitmap bitmap = this.g;
    if (bitmap != null && !bitmap.isRecycled())
      this.g.recycle(); 
    this.g = null;
  }
  
  public final boolean i() {
    return this.j;
  }
  
  public final float j() {
    f f1 = this.k;
    return (f1 != null) ? f1.a() : Float.NEGATIVE_INFINITY;
  }
  
  public final int l() {
    return this.e;
  }
  
  public final MapTile.MapSource m() {
    return this.d;
  }
  
  public final String toString() {
    StringBuilder stringBuilder = new StringBuilder(128);
    stringBuilder.append(this.a);
    stringBuilder.append("_");
    stringBuilder.append(this.b);
    stringBuilder.append("_");
    stringBuilder.append(this.c);
    stringBuilder.append("_");
    stringBuilder.append(this.d);
    stringBuilder.append("_");
    stringBuilder.append(this.f.getClass().getCanonicalName());
    return stringBuilder.toString();
  }
  
  static final class a implements Comparator<a> {
    private a() {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\tile\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */