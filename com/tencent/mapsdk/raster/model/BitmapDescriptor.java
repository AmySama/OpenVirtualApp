package com.tencent.mapsdk.raster.model;

import android.graphics.Bitmap;

public final class BitmapDescriptor implements Cloneable {
  int h = 0;
  
  Bitmap mBitmap;
  
  int w = 0;
  
  public BitmapDescriptor(Bitmap paramBitmap) {
    if (paramBitmap != null) {
      this.w = paramBitmap.getWidth();
      this.h = paramBitmap.getHeight();
      this.mBitmap = paramBitmap;
    } 
  }
  
  private BitmapDescriptor(Bitmap paramBitmap, int paramInt1, int paramInt2) {
    this.w = paramInt1;
    this.h = paramInt2;
    this.mBitmap = paramBitmap;
  }
  
  public final BitmapDescriptor clone() {
    return new BitmapDescriptor(Bitmap.createBitmap(this.mBitmap), this.w, this.h);
  }
  
  public final Bitmap getBitmap() {
    return this.mBitmap;
  }
  
  public final int getHeight() {
    return this.h;
  }
  
  public final int getWidth() {
    return this.w;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\raster\model\BitmapDescriptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */