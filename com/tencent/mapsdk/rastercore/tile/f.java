package com.tencent.mapsdk.rastercore.tile;

import android.os.Handler;
import com.tencent.mapsdk.raster.model.TileOverlayOptions;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.mapsdk.rastercore.tile.b.c;

public final class f {
  private boolean a = false;
  
  private com.tencent.mapsdk.rastercore.e.a.f b;
  
  private e c;
  
  private Handler d = new Handler();
  
  private a e;
  
  private float f = 0.0F;
  
  public f(e parame) {
    this.c = parame;
  }
  
  public final void a(boolean paramBoolean) {
    if (paramBoolean && this.a && this.b != null)
      return; 
    if (paramBoolean) {
      TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
      tileOverlayOptions.tileProvider(c.a(this.c, MapTile.MapSource.TRAFFIC)).diskCacheEnabled(false).visible(true).zIndex(0.0F);
      this.b = this.c.g().a(tileOverlayOptions);
      a a1 = new a(this, 60000);
      this.e = a1;
      this.d.post(a1);
    } else {
      com.tencent.mapsdk.rastercore.e.a.f f1 = this.b;
      if (f1 == null)
        return; 
      f1.b();
      this.b = null;
      this.e.a(false);
      this.d.removeCallbacks(this.e);
      this.e = null;
    } 
    this.a = paramBoolean;
  }
  
  public final boolean a() {
    return this.a;
  }
  
  final class a implements Runnable {
    private boolean a = true;
    
    private int b = 60000;
    
    public a(f this$0, int param1Int) {}
    
    public final void a(boolean param1Boolean) {
      this.a = false;
    }
    
    public final void run() {
      f.a(this.c).a(false, false);
      if (this.a)
        f.b(this.c).postDelayed(this, this.b); 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\tile\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */