package com.tencent.lbssearch.a.a.b.a;

import com.tencent.lbssearch.a.a.d.a;
import com.tencent.lbssearch.a.a.d.b;
import com.tencent.lbssearch.a.a.i;
import com.tencent.lbssearch.a.a.l;
import com.tencent.lbssearch.a.a.o;
import com.tencent.lbssearch.a.a.q;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class d extends a {
  private static final Reader a = new Reader() {
      public void close() throws IOException {
        throw new AssertionError();
      }
      
      public int read(char[] param1ArrayOfchar, int param1Int1, int param1Int2) throws IOException {
        throw new AssertionError();
      }
    };
  
  private static final Object b = new Object();
  
  private final List<Object> c;
  
  public d(l paraml) {
    super(a);
    ArrayList<Object> arrayList = new ArrayList();
    this.c = arrayList;
    arrayList.add(paraml);
  }
  
  private void a(b paramb) throws IOException {
    if (f() == paramb)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected ");
    stringBuilder.append(paramb);
    stringBuilder.append(" but was ");
    stringBuilder.append(f());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  private Object q() {
    List<Object> list = this.c;
    return list.get(list.size() - 1);
  }
  
  private Object r() {
    List<Object> list = this.c;
    return list.remove(list.size() - 1);
  }
  
  public void a() throws IOException {
    a(b.a);
    i i = (i)q();
    this.c.add(i.iterator());
  }
  
  public void b() throws IOException {
    a(b.b);
    r();
    r();
  }
  
  public void c() throws IOException {
    a(b.c);
    o o = (o)q();
    this.c.add(o.a().iterator());
  }
  
  public void close() throws IOException {
    this.c.clear();
    this.c.add(b);
  }
  
  public void d() throws IOException {
    a(b.d);
    r();
    r();
  }
  
  public boolean e() throws IOException {
    boolean bool;
    b b = f();
    if (b != b.d && b != b.b) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public b f() throws IOException {
    if (this.c.isEmpty())
      return b.j; 
    Object object = q();
    if (object instanceof Iterator) {
      b b;
      List<Object> list = this.c;
      boolean bool = list.get(list.size() - 2) instanceof o;
      Iterator iterator = (Iterator)object;
      if (iterator.hasNext()) {
        if (bool)
          return b.e; 
        this.c.add(iterator.next());
        return f();
      } 
      if (bool) {
        b = b.d;
      } else {
        b = b.b;
      } 
      return b;
    } 
    if (object instanceof o)
      return b.c; 
    if (object instanceof i)
      return b.a; 
    if (object instanceof q) {
      q q = (q)object;
      if (q.r())
        return b.f; 
      if (q.a())
        return b.h; 
      if (q.q())
        return b.g; 
      throw new AssertionError();
    } 
    if (object instanceof com.tencent.lbssearch.a.a.n)
      return b.i; 
    if (object == b)
      throw new IllegalStateException("JsonReader is closed"); 
    throw new AssertionError();
  }
  
  public String g() throws IOException {
    a(b.e);
    Map.Entry entry = ((Iterator<Map.Entry>)q()).next();
    this.c.add(entry.getValue());
    return (String)entry.getKey();
  }
  
  public String h() throws IOException {
    b b = f();
    if (b == b.f || b == b.g)
      return ((q)r()).c(); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected ");
    stringBuilder.append(b.f);
    stringBuilder.append(" but was ");
    stringBuilder.append(b);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public boolean i() throws IOException {
    a(b.h);
    return ((q)r()).h();
  }
  
  public void j() throws IOException {
    a(b.i);
    r();
  }
  
  public double k() throws IOException {
    b b = f();
    if (b == b.g || b == b.f) {
      double d1 = ((q)q()).d();
      if (p() || (!Double.isNaN(d1) && !Double.isInfinite(d1))) {
        r();
        return d1;
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("JSON forbids NaN and infinities: ");
      stringBuilder1.append(d1);
      throw new NumberFormatException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected ");
    stringBuilder.append(b.g);
    stringBuilder.append(" but was ");
    stringBuilder.append(b);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public long l() throws IOException {
    b b = f();
    if (b == b.g || b == b.f) {
      long l = ((q)q()).f();
      r();
      return l;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected ");
    stringBuilder.append(b.g);
    stringBuilder.append(" but was ");
    stringBuilder.append(b);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public int m() throws IOException {
    b b = f();
    if (b == b.g || b == b.f) {
      int i = ((q)q()).g();
      r();
      return i;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected ");
    stringBuilder.append(b.g);
    stringBuilder.append(" but was ");
    stringBuilder.append(b);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void n() throws IOException {
    if (f() == b.e) {
      g();
    } else {
      r();
    } 
  }
  
  public void o() throws IOException {
    a(b.e);
    Map.Entry entry = ((Iterator<Map.Entry>)q()).next();
    this.c.add(entry.getValue());
    this.c.add(new q((String)entry.getKey()));
  }
  
  public String toString() {
    return getClass().getSimpleName();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\a\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */