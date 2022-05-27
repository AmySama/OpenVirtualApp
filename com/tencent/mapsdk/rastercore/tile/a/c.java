package com.tencent.mapsdk.rastercore.tile.a;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public final class c {
  private String a = "md5";
  
  private byte[] b;
  
  private int c = -1;
  
  public c(byte[] paramArrayOfbyte, int paramInt) {
    this.b = paramArrayOfbyte;
    this.c = paramInt;
  }
  
  public c(byte[] paramArrayOfbyte, int paramInt, String paramString) {
    this.b = paramArrayOfbyte;
    this.c = paramInt;
    this.a = paramString;
  }
  
  public final String a() {
    return this.a;
  }
  
  public final Bitmap b() {
    byte[] arrayOfByte = this.b;
    return (arrayOfByte == null) ? null : BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
  }
  
  public final byte[] c() {
    return this.b;
  }
  
  public final int d() {
    return this.c;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\tile\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */