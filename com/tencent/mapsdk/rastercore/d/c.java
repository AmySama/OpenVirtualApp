package com.tencent.mapsdk.rastercore.d;

import android.graphics.PointF;
import com.tencent.mapsdk.raster.model.CameraPosition;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.LatLngBounds;
import com.tencent.mapsdk.rastercore.b.a;
import com.tencent.mapsdk.rastercore.b.b;
import com.tencent.mapsdk.rastercore.d;
import com.tencent.tencentmap.mapsdk.map.CancelableCallback;

public final class c {
  private static final b a = new b(new com.tencent.mapsdk.rastercore.b.c(-2.003750834E7D, -2.003750834E7D), new com.tencent.mapsdk.rastercore.b.c(2.003750834E7D, 2.003750834E7D));
  
  private e b;
  
  private b c;
  
  private a d;
  
  private a e;
  
  private boolean f = false;
  
  private b g;
  
  private double h = 0.0D;
  
  private double i = 0.0D;
  
  public c(e parame) {
    this.b = parame;
  }
  
  private double a(double paramDouble) {
    return this.c.d().d() * Math.cos(paramDouble * 0.017453292519943295D);
  }
  
  private double a(boolean paramBoolean) {
    LatLngBounds latLngBounds = c();
    if (paramBoolean) {
      double d3 = latLngBounds.getNortheast().getLongitude();
      double d4 = latLngBounds.getSouthwest().getLongitude();
      return Math.abs(d3 - d4);
    } 
    double d1 = latLngBounds.getNortheast().getLatitude();
    double d2 = latLngBounds.getSouthwest().getLatitude();
    return Math.abs(d1 - d2);
  }
  
  private com.tencent.mapsdk.rastercore.b.c[] k() {
    int i = this.c.getWidth();
    return new com.tencent.mapsdk.rastercore.b.c[] { d.a.a(new PointF(0.0F, this.c.getHeight()), this.c.b(), this.c.a(), this.c.d()), d.a.a(new PointF(i, 0.0F), this.c.b(), this.c.a(), this.c.d()) };
  }
  
  public final float a(double paramDouble1, double paramDouble2) {
    return (float)(paramDouble2 / a(paramDouble1));
  }
  
  public final float a(float paramFloat) {
    return (float)(paramFloat / a(0.0D));
  }
  
  public final PointF a(LatLng paramLatLng) {
    com.tencent.mapsdk.rastercore.b.c c2 = this.c.b();
    PointF pointF2 = this.c.a();
    a a1 = this.c.d();
    com.tencent.mapsdk.rastercore.b.c c1 = d.a.a(paramLatLng);
    double d1 = c1.b();
    double d2 = c2.b();
    double d3 = c1.a();
    double d4 = c2.a();
    d1 = (d1 - d2) / a1.d();
    d4 = (d3 - d4) / a1.d();
    PointF pointF1 = new PointF();
    pointF1.x = (float)(pointF2.x + d1);
    pointF1.y = (float)(pointF2.y - d4);
    return pointF1;
  }
  
  public final LatLng a(int paramInt1, int paramInt2) {
    return d.a.a(d.a.a(new PointF(paramInt1, paramInt2), this.c.b(), this.c.a(), this.c.d()));
  }
  
  public final a a(a parama) {
    boolean bool;
    a a1;
    if (this.b.f().a() >= 3 && this.b.f().b() > 1.0F) {
      bool = true;
    } else {
      bool = false;
    } 
    double d1 = parama.c();
    double d2 = this.e.a();
    double d3 = 0.0D;
    if (bool) {
      d4 = Math.log(1.3D) / Math.log(2.0D);
    } else {
      d4 = 0.0D;
    } 
    if (d1 < d2 + d4) {
      a a2 = new a(this.e.c());
      a1 = a2;
      if (bool) {
        a2.a(1.3D);
        a1 = a2;
      } 
    } else {
      a1 = parama;
    } 
    d1 = parama.c();
    d2 = this.d.c();
    double d4 = d3;
    if (bool)
      d4 = Math.log(1.3D) / Math.log(2.0D); 
    if (d1 > d2 + d4) {
      parama = new a(this.d.c());
      a1 = parama;
      if (bool) {
        parama.a(1.3D);
        a1 = parama;
      } 
    } 
    return a1;
  }
  
  public final void a() {
    this.g = a;
    this.d = new a(19.0D);
    this.e = new a(a.a);
    this.c = this.b.c();
  }
  
  protected final void a(int paramInt) {
    this.h = 0.0D;
  }
  
  public final void a(com.tencent.mapsdk.rastercore.b.c paramc) {
    if (this.g == null)
      return; 
    com.tencent.mapsdk.rastercore.b.c[] arrayOfC = k();
    com.tencent.mapsdk.rastercore.b.c c1 = this.g.a();
    com.tencent.mapsdk.rastercore.b.c c2 = this.g.b();
    double d1 = c1.a();
    double d2 = arrayOfC[0].a();
    double d3 = 0.0D;
    if (d1 > d2) {
      d2 = c1.a() - arrayOfC[0].a();
    } else {
      d2 = 0.0D;
    } 
    if (c1.b() > arrayOfC[0].b())
      d3 = c1.b() - arrayOfC[0].b(); 
    if (c2.a() < arrayOfC[1].a())
      d2 = c2.a() - arrayOfC[1].a(); 
    if (c2.b() < arrayOfC[1].b())
      d3 = c2.b() - arrayOfC[1].b(); 
    paramc.a(paramc.a() + d2);
    paramc.b(paramc.b() + d3);
  }
  
  public final boolean a(LatLngBounds paramLatLngBounds) {
    if (paramLatLngBounds == null) {
      this.g = a;
      this.e.b(a.a);
      this.f = false;
      return true;
    } 
    int i = this.c.getHeight();
    int j = this.c.getWidth();
    if (i == 0 || j == 0)
      return false; 
    LatLng latLng2 = paramLatLngBounds.getNortheast();
    LatLng latLng1 = paramLatLngBounds.getSouthwest();
    com.tencent.mapsdk.rastercore.b.c c2 = d.a.a(latLng2);
    com.tencent.mapsdk.rastercore.b.c c1 = d.a.a(latLng1);
    double d1 = c2.a() - c1.a();
    double d2 = c2.b() - c1.b();
    if (i * 1.0F / j > (float)(d1 / d2)) {
      d1 = i * a.c(19.0D) / d1;
    } else {
      d1 = j * a.c(19.0D) / d2;
    } 
    float f = (float)d1;
    if (f > 2.0F)
      return false; 
    this.e.a(19);
    this.e.a(f);
    this.c.d().b(this.e.c());
    this.g = new b(c1, c2);
    a(this.c.b());
    this.f = true;
    this.b.a(false, false);
    return true;
  }
  
  protected final void b(double paramDouble1, double paramDouble2) {
    if (paramDouble1 > 0.0D && paramDouble2 > 0.0D) {
      double d1 = a(true);
      double d2 = a(false);
      if (d1 == 0.0D && d2 == 0.0D) {
        this.h = paramDouble1;
        this.i = paramDouble2;
        return;
      } 
      paramDouble1 = Math.max(paramDouble1 / d2, paramDouble2 / d1);
      a a1 = this.c.d();
      a1.a(a1.b() * paramDouble1);
      a(a1);
      this.b.a(false, false);
    } 
  }
  
  protected final void b(int paramInt) {
    this.i = 0.0D;
  }
  
  public final com.tencent.mapsdk.rastercore.b.c[] b() {
    com.tencent.mapsdk.rastercore.b.c[] arrayOfC = new com.tencent.mapsdk.rastercore.b.c[8];
    float f1 = this.c.getWidth();
    float f2 = this.c.getHeight();
    PointF pointF1 = new PointF(0.0F, 0.0F);
    byte b1 = 0;
    float f3 = f1 / 2.0F;
    PointF pointF2 = new PointF(f3, 0.0F);
    PointF pointF3 = new PointF(f1, 0.0F);
    float f4 = f2 / 2.0F;
    PointF pointF4 = new PointF(f1, f4);
    PointF pointF5 = new PointF(f1, f2);
    PointF pointF6 = new PointF(f3, f2);
    PointF pointF7 = new PointF(0.0F, f2);
    PointF pointF8 = new PointF(0.0F, f4);
    while (b1 < 8) {
      (new PointF[8])[0] = pointF1;
      (new PointF[8])[1] = pointF2;
      (new PointF[8])[2] = pointF3;
      (new PointF[8])[3] = pointF4;
      (new PointF[8])[4] = pointF5;
      (new PointF[8])[5] = pointF6;
      (new PointF[8])[6] = pointF7;
      (new PointF[8])[7] = pointF8;
      arrayOfC[b1] = d.a.a((new PointF[8])[b1], this.c.b(), this.c.a(), this.c.d());
      b1++;
    } 
    return arrayOfC;
  }
  
  public final LatLngBounds c() {
    com.tencent.mapsdk.rastercore.b.c[] arrayOfC = k();
    return new LatLngBounds(d.a.a(arrayOfC[0]), d.a.a(arrayOfC[1]));
  }
  
  public final void c(int paramInt) {
    int i = paramInt;
    if (paramInt <= this.e.a())
      i = this.e.a(); 
    paramInt = i;
    if (i >= 19)
      paramInt = 19; 
    this.d.b(paramInt);
    if (this.c.d().c() >= this.d.c())
      this.c.b(this.d.c(), true, (CancelableCallback)null); 
  }
  
  public final CameraPosition d() {
    LatLng latLng = d.a.a(this.c.b());
    float f = this.c.d().a();
    return CameraPosition.builder().target(latLng).zoom(f).build();
  }
  
  public final void d(int paramInt) {
    int i = paramInt;
    if (this.f) {
      i = paramInt;
      if (paramInt <= this.e.c())
        i = this.e.a(); 
    } 
    paramInt = i;
    if (i <= a.a)
      paramInt = a.a; 
    i = paramInt;
    if (paramInt >= this.d.c())
      i = this.d.a(); 
    this.e.b(i);
    if (this.c.d().c() <= this.e.c())
      this.c.b(this.e.c(), true, (CancelableCallback)null); 
  }
  
  public final double e() {
    int i = this.c.d().a();
    double d1 = 0.1D;
    double d2 = 1.0D;
    if (i >= 7) {
      double d = 1.0D - Math.abs(this.c.b().a() / 2.003750834E7D);
      d2 = d;
      if (d < 0.1D)
        d2 = d1; 
    } 
    return this.c.d().d() * d2;
  }
  
  public final float f() {
    int i = this.c.getWidth();
    return (float)(d.a.a(a(0, 0), a(i, 0)) / i);
  }
  
  protected final double g() {
    return this.h;
  }
  
  protected final double h() {
    return this.i;
  }
  
  public final a i() {
    return this.d;
  }
  
  public final a j() {
    return this.e;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\d\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */