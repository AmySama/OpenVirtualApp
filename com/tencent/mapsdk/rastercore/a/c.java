package com.tencent.mapsdk.rastercore.a;

import android.graphics.PointF;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.tencentmap.mapsdk.map.CancelableCallback;

public final class c extends a {
  private PointF d;
  
  private double e;
  
  private double f;
  
  public c(e parame, double paramDouble, PointF paramPointF, long paramLong, CancelableCallback paramCancelableCallback) {
    super(parame, paramLong, paramCancelableCallback);
    this.e = paramDouble;
    this.d = paramPointF;
  }
  
  protected final void a(float paramFloat) {
    this.b.a(this.f * paramFloat, this.d, false, null);
  }
  
  protected final void c() {
    double d = this.b.c();
    this.f = this.e - d;
    StringBuilder stringBuilder = new StringBuilder("newZoom:");
    stringBuilder.append(this.e);
    stringBuilder.append(",oldZoom=");
    stringBuilder.append(d);
  }
  
  protected final void d() {
    this.b.a(this.e, this.d, false, 0L, null);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */