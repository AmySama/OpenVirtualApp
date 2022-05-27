package com.tencent.mapsdk.rastercore.tile.b;

import com.tencent.mapsdk.rastercore.d.e;
import java.net.MalformedURLException;
import java.net.URL;

public class b extends a {
  private String[] a = new String[] { "https://s0.map.gtimg.com/oversea", "https://s1.map.gtimg.com/oversea", "https://s2.map.gtimg.com/oversea", "https://s3.map.gtimg.com/oversea" };
  
  public b(int paramInt) {
    super(paramInt);
  }
  
  public URL getTileUrl(int paramInt1, int paramInt2, int paramInt3, Object... paramVarArgs) {
    int i = e.s();
    String str1 = e.u();
    int j = e.t();
    int k = a(paramInt1 + paramInt2, this.a.length);
    String str2 = this.a[k];
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str2);
    stringBuilder.append("?z=");
    stringBuilder.append(paramInt3);
    stringBuilder.append("&x=");
    stringBuilder.append(paramInt1);
    stringBuilder.append("&y=");
    stringBuilder.append(paramInt2);
    stringBuilder.append("&styleid=");
    stringBuilder.append(j);
    stringBuilder.append("&version=");
    stringBuilder.append(i);
    stringBuilder.append("&ch=");
    stringBuilder.append(str1);
    str1 = stringBuilder.toString();
    try {
      return new URL(str1);
    } catch (MalformedURLException malformedURLException) {
      (new StringBuilder("Unable to new URL with ")).append(str1);
      return null;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\tile\b\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */