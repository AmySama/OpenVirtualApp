package com.tencent.mapsdk.rastercore.e.a;

import com.tencent.mapsdk.raster.model.TileOverlayOptions;
import com.tencent.mapsdk.raster.model.TileProvider;
import com.tencent.mapsdk.rastercore.d.e;
import java.io.File;

public final class f {
  private static int h;
  
  private e a;
  
  private final String b;
  
  private boolean c = true;
  
  private float d = Float.NEGATIVE_INFINITY;
  
  private boolean e = true;
  
  private TileProvider f;
  
  private String g;
  
  public f(e parame, TileOverlayOptions paramTileOverlayOptions) {
    StringBuilder stringBuilder2 = new StringBuilder("TileOverlay_");
    int i = h;
    h = i + 1;
    stringBuilder2.append(i);
    this.b = stringBuilder2.toString();
    this.a = parame;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(e.a().getPackageName());
    stringBuilder1.append(File.separator);
    stringBuilder1.append(this.b);
    this.g = stringBuilder1.toString();
    this.e = paramTileOverlayOptions.getDiskCacheEnabled();
    this.f = paramTileOverlayOptions.getTileProvider();
    this.d = paramTileOverlayOptions.getZIndex();
    this.c = paramTileOverlayOptions.isVisible();
  }
  
  public static void c() {}
  
  public final float a() {
    return this.d;
  }
  
  public final void a(boolean paramBoolean) {
    this.c = paramBoolean;
    this.a.a(false, false);
  }
  
  public final void b() {
    this.a.g().a(this);
  }
  
  public final String d() {
    return this.b;
  }
  
  public final boolean e() {
    return this.c;
  }
  
  public final boolean equals(Object paramObject) {
    if (paramObject != null && paramObject instanceof f) {
      paramObject = paramObject;
      if (this.b.equals(((f)paramObject).b))
        return true; 
    } 
    return false;
  }
  
  public final TileProvider f() {
    return this.f;
  }
  
  public final String g() {
    return this.g;
  }
  
  public final boolean h() {
    return this.e;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\e\a\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */