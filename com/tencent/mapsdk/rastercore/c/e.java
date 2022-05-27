package com.tencent.mapsdk.rastercore.c;

import android.graphics.Point;
import android.graphics.PointF;

public final class e extends a {
  private float d;
  
  private Point e;
  
  public final void a(float paramFloat) {
    this.d = paramFloat;
  }
  
  public final void a(Point paramPoint) {
    this.e = paramPoint;
  }
  
  public final void a(com.tencent.mapsdk.rastercore.d.e parame) {
    if (this.e != null) {
      PointF pointF = new PointF(this.e.x, this.e.y);
      parame.c().a(this.d, pointF, this.a, this.c);
      return;
    } 
    parame.c().a(this.d, this.a, this.c);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\c\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */