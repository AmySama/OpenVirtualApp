package com.tencent.mapsdk.rastercore.a;

import com.tencent.mapsdk.rastercore.b.c;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.tencentmap.mapsdk.map.CancelableCallback;

public final class b extends a {
  private c d;
  
  private c e;
  
  private double f;
  
  private double g;
  
  public b(e parame, c paramc, long paramLong, CancelableCallback paramCancelableCallback) {
    super(parame, paramLong, paramCancelableCallback);
    this.e = paramc;
  }
  
  protected final void a(float paramFloat) {
    double d1 = this.f;
    double d2 = paramFloat;
    double d3 = this.g;
    c c1 = this.d;
    c1.b(c1.b() + d1 * d2);
    c1 = this.d;
    c1.a(c1.a() + d3 * d2);
    this.b.b(this.d);
  }
  
  protected final void c() {
    this.d = this.b.b();
    this.f = this.e.b() - this.d.b();
    this.g = this.e.a() - this.d.a();
  }
  
  protected final void d() {
    this.b.b(this.e);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */