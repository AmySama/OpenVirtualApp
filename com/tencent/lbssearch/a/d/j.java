package com.tencent.lbssearch.a.d;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

public abstract class j<T> implements Comparable<j<T>> {
  private final r.a a;
  
  private final int b;
  
  private final String c;
  
  private final int d;
  
  private final l.a e;
  
  private Integer f;
  
  private k g;
  
  private boolean h;
  
  private boolean i;
  
  private boolean j;
  
  private n k;
  
  public j(int paramInt, String paramString, l.a parama) {
    r.a a1;
    if (r.a.a) {
      a1 = new r.a();
    } else {
      a1 = null;
    } 
    this.a = a1;
    this.h = false;
    this.i = false;
    this.j = false;
    this.b = paramInt;
    this.c = paramString;
    this.e = parama;
    a(new c());
    this.d = c(paramString);
  }
  
  private byte[] a(Map<String, String> paramMap, String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    try {
      for (Map.Entry<String, String> entry : paramMap.entrySet()) {
        stringBuilder.append(URLEncoder.encode((String)entry.getKey(), paramString));
        stringBuilder.append('=');
        stringBuilder.append(URLEncoder.encode((String)entry.getValue(), paramString));
        stringBuilder.append('&');
      } 
      return stringBuilder.toString().getBytes(paramString);
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("Encoding not supported: ");
      stringBuilder.append(paramString);
      throw new RuntimeException(stringBuilder.toString(), unsupportedEncodingException);
    } 
  }
  
  private static int c(String paramString) {
    if (!TextUtils.isEmpty(paramString)) {
      Uri uri = Uri.parse(paramString);
      if (uri != null) {
        String str = uri.getHost();
        if (str != null)
          return str.hashCode(); 
      } 
    } 
    return 0;
  }
  
  public int a() {
    return this.b;
  }
  
  public int a(j<T> paramj) {
    int i;
    a a1 = o();
    a a2 = paramj.o();
    if (a1 == a2) {
      i = this.f.intValue() - paramj.f.intValue();
    } else {
      i = a2.ordinal() - a1.ordinal();
    } 
    return i;
  }
  
  public final j<?> a(int paramInt) {
    this.f = Integer.valueOf(paramInt);
    return this;
  }
  
  public j<?> a(k paramk) {
    this.g = paramk;
    return this;
  }
  
  public j<?> a(n paramn) {
    this.k = paramn;
    return this;
  }
  
  protected abstract l<T> a(h paramh);
  
  protected q a(q paramq) {
    return paramq;
  }
  
  protected abstract void a(T paramT);
  
  public void a(String paramString) {
    if (r.a.a)
      this.a.a(paramString, Thread.currentThread().getId()); 
  }
  
  public int b() {
    return this.d;
  }
  
  public void b(q paramq) {
    l.a a1 = this.e;
    if (a1 != null)
      a1.a(paramq); 
  }
  
  void b(String paramString) {
    k k1 = this.g;
    if (k1 != null)
      k1.b(this); 
    if (r.a.a) {
      long l = Thread.currentThread().getId();
      if (Looper.myLooper() != Looper.getMainLooper()) {
        (new Handler(Looper.getMainLooper())).post(new Runnable(this, paramString, l) {
              public void run() {
                j.b(this.c).a(this.a, this.b);
                j.b(this.c).a(toString());
              }
            });
        return;
      } 
      this.a.a(paramString, l);
      this.a.a(toString());
    } 
  }
  
  public String c() {
    return this.c;
  }
  
  public boolean d() {
    return this.h;
  }
  
  public Map<String, String> e() throws a {
    return Collections.emptyMap();
  }
  
  @Deprecated
  protected Map<String, String> f() throws a {
    return j();
  }
  
  @Deprecated
  protected String g() {
    return k();
  }
  
  @Deprecated
  public String h() {
    return l();
  }
  
  @Deprecated
  public byte[] i() throws a {
    Map<String, String> map = f();
    return (map != null && map.size() > 0) ? a(map, g()) : null;
  }
  
  protected Map<String, String> j() throws a {
    return null;
  }
  
  protected String k() {
    return "UTF-8";
  }
  
  public String l() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("application/x-www-form-urlencoded; charset=");
    stringBuilder.append(k());
    return stringBuilder.toString();
  }
  
  public byte[] m() throws a {
    Map<String, String> map = j();
    return (map != null && map.size() > 0) ? a(map, k()) : null;
  }
  
  public final boolean n() {
    return this.j;
  }
  
  public a o() {
    return a.b;
  }
  
  public final int p() {
    return this.k.a();
  }
  
  public n q() {
    return this.k;
  }
  
  public void r() {
    this.i = true;
  }
  
  public boolean s() {
    return this.i;
  }
  
  public String toString() {
    String str1;
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("0x");
    stringBuilder1.append(Integer.toHexString(b()));
    String str2 = stringBuilder1.toString();
    StringBuilder stringBuilder2 = new StringBuilder();
    if (this.h) {
      str1 = "[X] ";
    } else {
      str1 = "[ ] ";
    } 
    stringBuilder2.append(str1);
    stringBuilder2.append(c());
    stringBuilder2.append(" ");
    stringBuilder2.append(str2);
    stringBuilder2.append(" ");
    stringBuilder2.append(o());
    stringBuilder2.append(" ");
    stringBuilder2.append(this.f);
    return stringBuilder2.toString();
  }
  
  public enum a {
    a, b, c, d;
    
    static {
      a a1 = new a("IMMEDIATE", 3);
      d = a1;
      e = new a[] { a, b, c, a1 };
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\d\j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */