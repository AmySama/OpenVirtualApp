package com.tencent.lbssearch.a.d.a;

import com.tencent.lbssearch.a.d.h;
import com.tencent.lbssearch.a.d.j;
import com.tencent.lbssearch.a.d.l;
import java.io.UnsupportedEncodingException;

public class g extends j<String> {
  private final l.b<String> a;
  
  public g(int paramInt, String paramString, l.b<String> paramb, l.a parama) {
    super(paramInt, paramString, parama);
    this.a = paramb;
  }
  
  public g(String paramString, l.b<String> paramb, l.a parama) {
    this(0, paramString, paramb, parama);
  }
  
  protected l<String> a(h paramh) {
    String str;
    try {
      String str1 = new String();
      this(paramh.b, c.a(paramh.c));
      str = str1;
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      str = new String(((h)str).b);
    } 
    return l.a(str);
  }
  
  protected void c(String paramString) {
    this.a.a(paramString);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\d\a\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */