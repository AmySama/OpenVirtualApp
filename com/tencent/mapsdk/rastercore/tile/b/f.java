package com.tencent.mapsdk.rastercore.tile.b;

import java.net.MalformedURLException;
import java.net.URL;

public final class f extends a {
  private static final String[] a = new String[] { "https://rtt2.map.qq.com", "https://rtt2a.map.qq.com", "https://rtt2b.map.qq.com", "https://rtt2c.map.qq.com" };
  
  public f(int paramInt) {
    super(paramInt);
  }
  
  public final URL getTileUrl(int paramInt1, int paramInt2, int paramInt3, Object... paramVarArgs) {
    if (paramInt3 < 10)
      return null; 
    String[] arrayOfString = a;
    String str = arrayOfString[(paramInt1 + paramInt2) % arrayOfString.length];
    paramInt2 = (int)(Math.pow(2.0D, paramInt3) - paramInt2 - 1.0D);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append("/rtt/?z=");
    stringBuilder.append(paramInt3);
    stringBuilder.append("&x=");
    stringBuilder.append(paramInt1);
    stringBuilder.append("&y=");
    stringBuilder.append(paramInt2);
    stringBuilder.append("&ref=android2DSdk&timeKey=");
    stringBuilder.append(System.currentTimeMillis());
    str = stringBuilder.toString();
    try {
      return new URL(str);
    } catch (MalformedURLException malformedURLException) {
      getClass().getSimpleName();
      StringBuilder stringBuilder1 = new StringBuilder("Can not convert ");
      stringBuilder1.append(str);
      stringBuilder1.append(" to URL.");
      return null;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\tile\b\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */