package com.tencent.mapsdk.rastercore.e;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.PointF;
import com.tencent.mapsdk.raster.model.CircleOptions;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.rastercore.d;
import com.tencent.mapsdk.rastercore.d.e;

public class a implements b {
  private LatLng a = null;
  
  private double b = 0.0D;
  
  private float c = 10.0F;
  
  private int d = -16777216;
  
  private int e = 0;
  
  private float f = 0.0F;
  
  private boolean g = true;
  
  private boolean h = false;
  
  private DashPathEffect i = null;
  
  private DashPathEffect j;
  
  private String k;
  
  private e l;
  
  private com.tencent.mapsdk.rastercore.d.a m;
  
  public a(e parame, CircleOptions paramCircleOptions) {
    this.l = parame;
    this.m = parame.e();
    this.k = getId();
    this.e = paramCircleOptions.getFillColor();
    this.a = paramCircleOptions.getCenter();
    this.g = paramCircleOptions.isVisible();
    this.c = paramCircleOptions.getStrokeWidth();
    this.f = paramCircleOptions.getZIndex();
    this.d = paramCircleOptions.getStrokeColor();
    this.b = paramCircleOptions.getRadius();
    this.h = paramCircleOptions.getStrokeDash();
    this.i = paramCircleOptions.getStrokeDashPathEffect();
    float f = this.c;
    this.j = new DashPathEffect(new float[] { f, f }, 0.0F);
  }
  
  public LatLng a() {
    return this.a;
  }
  
  public void a(double paramDouble) {
    this.b = paramDouble;
    this.l.a(false, false);
  }
  
  public void a(float paramFloat) {
    this.c = paramFloat;
    paramFloat = this.c;
    this.j = new DashPathEffect(new float[] { paramFloat, paramFloat }, 0.0F);
    this.l.a(false, false);
  }
  
  public void a(int paramInt) {
    this.d = paramInt;
    this.l.a(false, false);
  }
  
  public void a(DashPathEffect paramDashPathEffect) {
    this.i = paramDashPathEffect;
  }
  
  public void a(LatLng paramLatLng) {
    this.a = paramLatLng;
    this.l.a(false, false);
  }
  
  public void a(boolean paramBoolean) {
    this.h = paramBoolean;
  }
  
  public double b() {
    return this.b;
  }
  
  public void b(int paramInt) {
    this.e = paramInt;
    this.l.a(false, false);
  }
  
  public boolean b(LatLng paramLatLng) {
    return (this.b >= d.a.a(this.a, paramLatLng));
  }
  
  public float c() {
    return this.c;
  }
  
  public boolean checkInBounds() {
    return true;
  }
  
  public int d() {
    return this.d;
  }
  
  public void destroy() {
    this.a = null;
  }
  
  public void draw(Canvas paramCanvas) {
    if (a() != null && this.b > 0.0D && isVisible()) {
      float f = this.l.b().a(this.a.getLatitude(), (float)b());
      PointF pointF = this.l.b().a(this.a);
      Paint paint = new Paint();
      paint.setColor(e());
      paint.setAntiAlias(true);
      paint.setStyle(Paint.Style.FILL);
      paramCanvas.drawCircle(pointF.x, pointF.y, f, paint);
      if (!com.tencent.mapsdk.rastercore.f.a.a(c(), 0.0F)) {
        if (this.h) {
          DashPathEffect dashPathEffect1 = this.i;
          DashPathEffect dashPathEffect2 = dashPathEffect1;
          if (dashPathEffect1 == null)
            dashPathEffect2 = this.j; 
          paint.setPathEffect((PathEffect)dashPathEffect2);
        } 
        paint.setColor(d());
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(c());
        paramCanvas.drawCircle(pointF.x, pointF.y, f, paint);
      } 
    } 
  }
  
  public int e() {
    return this.e;
  }
  
  public boolean equalsRemote(b paramb) {
    return (equals(paramb) || paramb.getId().equals(getId()));
  }
  
  public boolean f() {
    return this.h;
  }
  
  public DashPathEffect g() {
    return this.i;
  }
  
  public String getId() {
    if (this.k == null)
      this.k = com.tencent.mapsdk.rastercore.d.a.a("Circle"); 
    return this.k;
  }
  
  public float getZIndex() {
    return this.f;
  }
  
  public int hashCodeRemote() {
    return 0;
  }
  
  public boolean isVisible() {
    return this.g;
  }
  
  public void remove() {
    this.m.b(getId());
  }
  
  public void setVisible(boolean paramBoolean) {
    this.g = paramBoolean;
    this.l.a(false, false);
  }
  
  public void setZIndex(float paramFloat) {
    this.f = paramFloat;
    this.m.c();
    this.l.a(false, false);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\e\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */