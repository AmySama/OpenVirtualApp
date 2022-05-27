package com.tencent.mapsdk.rastercore.tile.b;

import java.net.MalformedURLException;
import java.net.URL;

public final class d extends a {
  private String[] a = new String[] { "https://p0.map.gtimg.com/sateTiles", "https://p1.map.gtimg.com/sateTiles", "https://p2.map.gtimg.com/sateTiles", "https://p3.map.gtimg.com/sateTiles" };
  
  public d(int paramInt) {
    super(paramInt);
  }
  
  public final URL getTileUrl(int paramInt1, int paramInt2, int paramInt3, Object... paramVarArgs) {
    paramInt2 = (int)(Math.pow(2.0D, paramInt3) - paramInt2 - 1.0D);
    int i = a(paramInt1 + paramInt2, 4);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.a[i]);
    stringBuilder.append('/');
    stringBuilder.append(paramInt3);
    stringBuilder.append('/');
    stringBuilder.append(paramInt1 >> 4);
    stringBuilder.append('/');
    stringBuilder.append(paramInt2 >> 4);
    stringBuilder.append('/');
    stringBuilder.append(paramInt1);
    stringBuilder.append('_');
    stringBuilder.append(paramInt2);
    stringBuilder.append(".jpg");
    String str = stringBuilder.toString();
    try {
      return new URL(str);
    } catch (MalformedURLException malformedURLException) {
      (new StringBuilder("Unable to new URL with ")).append(str);
      return null;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\tile\b\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */