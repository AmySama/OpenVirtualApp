package com.tencent.mapsdk.rastercore.c;

import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.LatLngBounds;
import com.tencent.mapsdk.rastercore.b.a;
import com.tencent.mapsdk.rastercore.b.c;
import com.tencent.mapsdk.rastercore.d;
import com.tencent.mapsdk.rastercore.d.e;

public final class b extends a {
  private int d;
  
  private int e;
  
  private LatLngBounds f;
  
  private int g;
  
  public final void a(int paramInt) {
    this.d = paramInt;
  }
  
  public final void a(LatLngBounds paramLatLngBounds) {
    this.f = paramLatLngBounds;
  }
  
  public final void a(e parame) {
    com.tencent.mapsdk.rastercore.d.b b1 = parame.c();
    int i = this.e;
    int j = i;
    if (i == 0)
      j = b1.getHeight(); 
    this.e = j - this.g * 2;
    i = this.d;
    j = i;
    if (i == 0)
      j = b1.getWidth(); 
    j -= this.g * 2;
    this.d = j;
    if (this.e != 0 && j != 0) {
      double d3;
      LatLng latLng1 = this.f.getNortheast();
      LatLng latLng2 = this.f.getSouthwest();
      c c1 = d.a.a(latLng1);
      c c2 = d.a.a(latLng2);
      double d1 = c1.a() - c2.a();
      double d2 = c1.b() - c2.b();
      j = this.e;
      float f = j;
      i = this.d;
      if (f * 1.0F / i < (float)(d1 / d2)) {
        d3 = j * 156543.0339D / d1;
      } else {
        d3 = i * 156543.0339D / d2;
      } 
      f = (float)d3;
      a a1 = parame.b().a(new a(0, f));
      c2 = new c(c2.b() + d2 / 2.0D, c2.a() + d1 / 2.0D);
      if (this.a) {
        b1.a(c2, this.b, this.c);
      } else {
        b1.b(c2);
      } 
      b1.b(a1.c(), this.a, this.c);
    } 
  }
  
  public final void b(int paramInt) {
    this.e = paramInt;
  }
  
  public final void c(int paramInt) {
    this.g = paramInt;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\c\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */