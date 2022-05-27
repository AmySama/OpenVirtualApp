package com.tencent.lbssearch.a.a.b.a;

import com.tencent.lbssearch.a.a.b.b;
import com.tencent.lbssearch.a.a.b.c;
import com.tencent.lbssearch.a.a.b.e;
import com.tencent.lbssearch.a.a.b.h;
import com.tencent.lbssearch.a.a.b.j;
import com.tencent.lbssearch.a.a.d.b;
import com.tencent.lbssearch.a.a.d.c;
import com.tencent.lbssearch.a.a.l;
import com.tencent.lbssearch.a.a.q;
import com.tencent.lbssearch.a.a.t;
import com.tencent.lbssearch.a.a.w;
import com.tencent.lbssearch.a.a.x;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public final class f implements x {
  private final c a;
  
  private final boolean b;
  
  public f(c paramc, boolean paramBoolean) {
    this.a = paramc;
    this.b = paramBoolean;
  }
  
  private w<?> a(com.tencent.lbssearch.a.a.f paramf, Type paramType) {
    return (paramType == boolean.class || paramType == Boolean.class) ? l.f : paramf.a(com.tencent.lbssearch.a.a.c.a.a(paramType));
  }
  
  public <T> w<T> a(com.tencent.lbssearch.a.a.f paramf, com.tencent.lbssearch.a.a.c.a<T> parama) {
    Type type = parama.b();
    if (!Map.class.isAssignableFrom(parama.a()))
      return null; 
    Type[] arrayOfType = b.b(type, b.e(type));
    w<?> w2 = a(paramf, arrayOfType[0]);
    w<?> w1 = paramf.a(com.tencent.lbssearch.a.a.c.a.a(arrayOfType[1]));
    h<? extends Map<?, ?>> h = this.a.a(parama);
    return (w)new a<Object, Object>(this, paramf, arrayOfType[0], w2, arrayOfType[1], w1, h);
  }
  
  private final class a<K, V> extends w<Map<K, V>> {
    private final com.tencent.lbssearch.a.a.f b;
    
    private final Type c;
    
    private final Type d;
    
    private final w<K> e;
    
    private final w<V> f;
    
    private final h<? extends Map<K, V>> g;
    
    public a(f this$0, com.tencent.lbssearch.a.a.f param1f, Type param1Type1, w<K> param1w, Type param1Type2, w<V> param1w1, h<? extends Map<K, V>> param1h) {
      this.b = param1f;
      this.c = param1Type1;
      this.d = param1Type2;
      this.e = new k<K>(param1f, param1w, param1Type1);
      this.f = new k<V>(param1f, param1w1, param1Type2);
      this.g = param1h;
    }
    
    private String a(l param1l) {
      q q;
      if (param1l.k()) {
        q = param1l.o();
        if (q.q())
          return String.valueOf(q.b()); 
        if (q.a())
          return Boolean.toString(q.h()); 
        if (q.r())
          return q.c(); 
        throw new AssertionError();
      } 
      if (q.l())
        return "null"; 
      throw new AssertionError();
    }
    
    public Map<K, V> a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
      StringBuilder stringBuilder;
      b b = param1a.f();
      if (b == b.i) {
        param1a.j();
        return null;
      } 
      Map<Object, Object> map = (Map)this.g.a();
      if (b == b.a) {
        param1a.a();
        while (param1a.e()) {
          param1a.a();
          Object object = this.e.b(param1a);
          if (map.put(object, this.f.b(param1a)) == null) {
            param1a.b();
            continue;
          } 
          stringBuilder = new StringBuilder();
          stringBuilder.append("duplicate key: ");
          stringBuilder.append(object);
          throw new t(stringBuilder.toString());
        } 
        stringBuilder.b();
      } else {
        stringBuilder.c();
        while (stringBuilder.e()) {
          e.a.a((com.tencent.lbssearch.a.a.d.a)stringBuilder);
          Object object = this.e.b((com.tencent.lbssearch.a.a.d.a)stringBuilder);
          if (map.put(object, this.f.b((com.tencent.lbssearch.a.a.d.a)stringBuilder)) == null)
            continue; 
          stringBuilder = new StringBuilder();
          stringBuilder.append("duplicate key: ");
          stringBuilder.append(object);
          throw new t(stringBuilder.toString());
        } 
        stringBuilder.d();
      } 
      return (Map)map;
    }
    
    public void a(c param1c, Map<K, V> param1Map) throws IOException {
      if (param1Map == null) {
        param1c.f();
        return;
      } 
      if (!f.a(this.a)) {
        param1c.d();
        for (Map.Entry<K, V> entry : param1Map.entrySet()) {
          param1c.a(String.valueOf(entry.getKey()));
          this.f.a(param1c, entry.getValue());
        } 
        param1c.e();
        return;
      } 
      ArrayList<l> arrayList = new ArrayList(entry.size());
      ArrayList arrayList1 = new ArrayList(entry.size());
      Iterator<Map.Entry> iterator = entry.entrySet().iterator();
      boolean bool1 = false;
      boolean bool2 = false;
      int i;
      for (i = 0; iterator.hasNext(); i |= b) {
        byte b;
        Map.Entry entry1 = iterator.next();
        l l = this.e.a(entry1.getKey());
        arrayList.add(l);
        arrayList1.add(entry1.getValue());
        if (l.i() || l.j()) {
          b = 1;
        } else {
          b = 0;
        } 
      } 
      if (i != 0) {
        param1c.b();
        for (i = bool2; i < arrayList.size(); i++) {
          param1c.b();
          j.a(arrayList.get(i), param1c);
          this.f.a(param1c, arrayList1.get(i));
          param1c.c();
        } 
        param1c.c();
      } else {
        param1c.d();
        for (i = bool1; i < arrayList.size(); i++) {
          param1c.a(a(arrayList.get(i)));
          this.f.a(param1c, arrayList1.get(i));
        } 
        param1c.e();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\a\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */