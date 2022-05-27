package com.tencent.mapsdk.rastercore.c;

import com.tencent.mapsdk.rastercore.d.b;
import com.tencent.mapsdk.rastercore.d.e;

public final class d extends a {
  private float d;
  
  private float e;
  
  public final void a(float paramFloat) {
    this.d = paramFloat;
  }
  
  public final void a(e parame) {
    b b = parame.c();
    if (this.a) {
      b.a((int)-this.d, (int)-this.e, this.b, this.c);
      return;
    } 
    b.scrollBy((int)-this.d, (int)-this.e);
  }
  
  public final void b(float paramFloat) {
    this.e = paramFloat;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\c\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */