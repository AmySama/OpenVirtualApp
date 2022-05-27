package com.tencent.lbssearch.a.a.b.a;

import com.tencent.lbssearch.a.a.c.a;
import com.tencent.lbssearch.a.a.d.a;
import com.tencent.lbssearch.a.a.d.b;
import com.tencent.lbssearch.a.a.d.c;
import com.tencent.lbssearch.a.a.f;
import com.tencent.lbssearch.a.a.w;
import com.tencent.lbssearch.a.a.x;
import java.io.IOException;
import java.util.ArrayList;

public final class g extends w<Object> {
  public static final x a = new x() {
      public <T> w<T> a(f param1f, a<T> param1a) {
        return (param1a.a() == Object.class) ? new g(param1f) : null;
      }
    };
  
  private final f b;
  
  private g(f paramf) {
    this.b = paramf;
  }
  
  public void a(c paramc, Object paramObject) throws IOException {
    if (paramObject == null) {
      paramc.f();
      return;
    } 
    w w1 = this.b.a(paramObject.getClass());
    if (w1 instanceof g) {
      paramc.d();
      paramc.e();
      return;
    } 
    w1.a(paramc, paramObject);
  }
  
  public Object b(a parama) throws IOException {
    com.tencent.lbssearch.a.a.b.g<String, Object> g1;
    b b = parama.f();
    switch (null.a[b.ordinal()]) {
      default:
        throw new IllegalStateException();
      case 6:
        parama.j();
        return null;
      case 5:
        return Boolean.valueOf(parama.i());
      case 4:
        return Double.valueOf(parama.k());
      case 3:
        return parama.h();
      case 2:
        g1 = new com.tencent.lbssearch.a.a.b.g();
        parama.c();
        while (parama.e())
          g1.put(parama.g(), b(parama)); 
        parama.d();
        return g1;
      case 1:
        break;
    } 
    ArrayList<Object> arrayList = new ArrayList();
    parama.a();
    while (parama.e())
      arrayList.add(b(parama)); 
    parama.b();
    return arrayList;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\a\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */