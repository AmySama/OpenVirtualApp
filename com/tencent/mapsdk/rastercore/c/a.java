package com.tencent.mapsdk.rastercore.c;

import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.tencentmap.mapsdk.map.CancelableCallback;

public abstract class a {
  protected boolean a = true;
  
  protected long b = 1000L;
  
  protected CancelableCallback c = null;
  
  public final void a(long paramLong) {
    this.b = paramLong;
  }
  
  public abstract void a(e parame);
  
  public final void a(CancelableCallback paramCancelableCallback) {
    this.c = paramCancelableCallback;
  }
  
  public final void a(boolean paramBoolean) {
    this.a = false;
  }
  
  public boolean a() {
    return true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\c\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */