package com.tencent.lbssearch.a.a;

import com.tencent.lbssearch.a.a.b.a;
import com.tencent.lbssearch.a.a.b.j;
import com.tencent.lbssearch.a.a.c.a;
import com.tencent.lbssearch.a.a.d.a;
import com.tencent.lbssearch.a.a.d.c;
import java.io.IOException;

final class v<T> extends w<T> {
  private final s<T> a;
  
  private final k<T> b;
  
  private final f c;
  
  private final a<T> d;
  
  private final x e;
  
  private w<T> f;
  
  private v(s<T> params, k<T> paramk, f paramf, a<T> parama, x paramx) {
    this.a = params;
    this.b = paramk;
    this.c = paramf;
    this.d = parama;
    this.e = paramx;
  }
  
  private w<T> a() {
    w<T> w1 = this.f;
    if (w1 == null) {
      w1 = this.c.a(this.e, this.d);
      this.f = w1;
    } 
    return w1;
  }
  
  public static x a(a<?> parama, Object paramObject) {
    return new a(paramObject, parama, false, null);
  }
  
  public static x b(a<?> parama, Object paramObject) {
    boolean bool;
    if (parama.b() == parama.a()) {
      bool = true;
    } else {
      bool = false;
    } 
    return new a(paramObject, parama, bool, null);
  }
  
  public void a(c paramc, T paramT) throws IOException {
    s<T> s1 = this.a;
    if (s1 == null) {
      a().a(paramc, paramT);
      return;
    } 
    if (paramT == null) {
      paramc.f();
      return;
    } 
    j.a(s1.a(paramT, this.d.b(), this.c.b), paramc);
  }
  
  public T b(a parama) throws IOException {
    if (this.b == null)
      return a().b(parama); 
    l l = j.a(parama);
    return l.l() ? null : this.b.b(l, this.d.b(), this.c.a);
  }
  
  private static class a implements x {
    private final a<?> a;
    
    private final boolean b;
    
    private final Class<?> c;
    
    private final s<?> d;
    
    private final k<?> e;
    
    private a(Object param1Object, a<?> param1a, boolean param1Boolean, Class<?> param1Class) {
      k<?> k1;
      boolean bool = param1Object instanceof s;
      s s1 = null;
      if (bool) {
        s2 = (s)param1Object;
      } else {
        s2 = null;
      } 
      this.d = s2;
      s s2 = s1;
      if (param1Object instanceof k)
        k1 = (k)param1Object; 
      this.e = k1;
      if (this.d != null || k1 != null) {
        bool = true;
      } else {
        bool = false;
      } 
      a.a(bool);
      this.a = param1a;
      this.b = param1Boolean;
      this.c = param1Class;
    }
    
    public <T> w<T> a(f param1f, a<T> param1a) {
      boolean bool;
      a<?> a1 = this.a;
      if (a1 != null) {
        if (a1.equals(param1a) || (this.b && this.a.b() == param1a.a())) {
          bool = true;
        } else {
          bool = false;
        } 
      } else {
        bool = this.c.isAssignableFrom(param1a.a());
      } 
      if (bool) {
        v v = new v(this.d, this.e, param1f, param1a, this);
      } else {
        param1f = null;
      } 
      return (w<T>)param1f;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\v.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */