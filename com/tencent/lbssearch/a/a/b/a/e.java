package com.tencent.lbssearch.a.a.b.a;

import com.tencent.lbssearch.a.a.d.c;
import com.tencent.lbssearch.a.a.i;
import com.tencent.lbssearch.a.a.l;
import com.tencent.lbssearch.a.a.n;
import com.tencent.lbssearch.a.a.o;
import com.tencent.lbssearch.a.a.q;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public final class e extends c {
  private static final Writer a = new Writer() {
      public void close() throws IOException {
        throw new AssertionError();
      }
      
      public void flush() throws IOException {
        throw new AssertionError();
      }
      
      public void write(char[] param1ArrayOfchar, int param1Int1, int param1Int2) {
        throw new AssertionError();
      }
    };
  
  private static final q b = new q("closed");
  
  private final List<l> c = new ArrayList<l>();
  
  private String d;
  
  private l e = (l)n.a;
  
  public e() {
    super(a);
  }
  
  private void a(l paraml) {
    if (this.d != null) {
      if (!paraml.l() || h())
        ((o)i()).a(this.d, paraml); 
      this.d = null;
    } else if (this.c.isEmpty()) {
      this.e = paraml;
    } else {
      l l1 = i();
      if (l1 instanceof i) {
        ((i)l1).a(paraml);
        return;
      } 
      throw new IllegalStateException();
    } 
  }
  
  private l i() {
    List<l> list = this.c;
    return list.get(list.size() - 1);
  }
  
  public c a(long paramLong) throws IOException {
    a((l)new q(Long.valueOf(paramLong)));
    return this;
  }
  
  public c a(Number paramNumber) throws IOException {
    if (paramNumber == null)
      return f(); 
    if (!g()) {
      double d = paramNumber.doubleValue();
      if (Double.isNaN(d) || Double.isInfinite(d)) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("JSON forbids NaN and infinities: ");
        stringBuilder.append(paramNumber);
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
    } 
    a((l)new q(paramNumber));
    return this;
  }
  
  public c a(String paramString) throws IOException {
    if (!this.c.isEmpty() && this.d == null) {
      if (i() instanceof o) {
        this.d = paramString;
        return this;
      } 
      throw new IllegalStateException();
    } 
    throw new IllegalStateException();
  }
  
  public c a(boolean paramBoolean) throws IOException {
    a((l)new q(Boolean.valueOf(paramBoolean)));
    return this;
  }
  
  public l a() {
    if (this.c.isEmpty())
      return this.e; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected one JSON element but was ");
    stringBuilder.append(this.c);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public c b() throws IOException {
    i i = new i();
    a((l)i);
    this.c.add(i);
    return this;
  }
  
  public c b(String paramString) throws IOException {
    if (paramString == null)
      return f(); 
    a((l)new q(paramString));
    return this;
  }
  
  public c c() throws IOException {
    if (!this.c.isEmpty() && this.d == null) {
      if (i() instanceof i) {
        List<l> list = this.c;
        list.remove(list.size() - 1);
        return this;
      } 
      throw new IllegalStateException();
    } 
    throw new IllegalStateException();
  }
  
  public void close() throws IOException {
    if (this.c.isEmpty()) {
      this.c.add(b);
      return;
    } 
    throw new IOException("Incomplete document");
  }
  
  public c d() throws IOException {
    o o = new o();
    a((l)o);
    this.c.add(o);
    return this;
  }
  
  public c e() throws IOException {
    if (!this.c.isEmpty() && this.d == null) {
      if (i() instanceof o) {
        List<l> list = this.c;
        list.remove(list.size() - 1);
        return this;
      } 
      throw new IllegalStateException();
    } 
    throw new IllegalStateException();
  }
  
  public c f() throws IOException {
    a((l)n.a);
    return this;
  }
  
  public void flush() throws IOException {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\a\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */