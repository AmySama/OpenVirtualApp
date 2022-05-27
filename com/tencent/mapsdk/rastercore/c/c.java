package com.tencent.mapsdk.rastercore.c;

import com.tencent.mapsdk.raster.model.CameraPosition;
import com.tencent.mapsdk.rastercore.d;
import com.tencent.mapsdk.rastercore.d.b;
import com.tencent.mapsdk.rastercore.d.e;

public final class c extends a {
  private CameraPosition d;
  
  public final void a(CameraPosition paramCameraPosition) {
    this.d = paramCameraPosition;
  }
  
  public final void a(e parame) {
    b b = parame.c();
    if (this.a) {
      b.a(d.a.a(this.d.getTarget()), this.b, this.c);
    } else {
      b.b(d.a.a(this.d.getTarget()));
    } 
    if (this.d.getZoom() > 0.0F)
      b.b(this.d.getZoom(), false, this.c); 
  }
  
  public final boolean a() {
    return false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\c\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */