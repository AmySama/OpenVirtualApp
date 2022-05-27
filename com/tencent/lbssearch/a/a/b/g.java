package com.tencent.lbssearch.a.a.b;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class g<K extends Comparable<K>, V> extends AbstractMap<K, V> implements Serializable {
  private int a = 0;
  
  private c<K, V> b;
  
  private c<K, V> c;
  
  private c<K, V> d;
  
  private void a(c<K, V> paramc) {
    c.a(paramc, true);
    while (paramc != null && paramc != this.b && c.e(c.d(paramc)) == true) {
      if (c.d(paramc) == d(b(b(paramc)))) {
        c<Comparable, ?> c3 = e(b(b(paramc)));
        if (c(c3) == true) {
          a(b(paramc), false);
          a(c3, false);
          a(b(b(paramc)), true);
          paramc = b(b(paramc));
          continue;
        } 
        c3 = paramc;
        if (paramc == e(b(paramc))) {
          c3 = b(paramc);
          g((c)c3);
        } 
        a(b(c3), false);
        a(b(b(c3)), true);
        h(b(b((c)c3)));
        c1 = c3;
        continue;
      } 
      c<Comparable, ?> c2 = d(b(b(c1)));
      if (c(c2) == true) {
        a(b(c1), false);
        a(c2, false);
        a(b(b(c1)), true);
        c1 = b(b(c1));
        continue;
      } 
      c2 = c1;
      if (c1 == d(b(c1))) {
        c2 = b(c1);
        h((c)c2);
      } 
      a(b(c2), false);
      a(b(b(c2)), true);
      g(b(b((c)c2)));
      c<Comparable, ?> c1 = c2;
    } 
    c.a(this.b, false);
  }
  
  private static <K extends Comparable<K>, V> void a(c<K, V> paramc, boolean paramBoolean) {
    if (paramc != null)
      c.a(paramc, paramBoolean); 
  }
  
  private static <K extends Comparable<K>, V> c<K, V> b(c<K, V> paramc) {
    if (paramc != null) {
      paramc = c.d(paramc);
    } else {
      paramc = null;
    } 
    return paramc;
  }
  
  private V b(K paramK, V paramV) {
    for (c<K, V> c1 = this.b;; c1 = c2) {
      c<K, V> c2;
      int i = paramK.compareTo(c.a(c1));
      if (i < 0) {
        c2 = c.b(c1);
      } else if (i > 0) {
        c2 = c.c(c1);
      } else {
        paramK = (K)c1.getValue();
        c1.setValue(paramV);
        return (V)paramK;
      } 
      if (c2 == null) {
        this.a++;
        c<K, V> c3 = new c<K, V>(c1, this.d, paramK, paramV);
        if (i < 0) {
          c.a(c1, c3);
        } else if (i > 0) {
          c.b(c1, c3);
        } 
        c.c(this.d, c3);
        this.d = c3;
        a(c3);
        return null;
      } 
    } 
  }
  
  private c<K, V> c(K paramK) {
    if (paramK != null) {
      c<K, V> c1 = this.b;
      while (c1 != null) {
        int i = paramK.compareTo(c.a(c1));
        if (i < 0) {
          c1 = c.b(c1);
          continue;
        } 
        if (i > 0) {
          c1 = c.c(c1);
          continue;
        } 
        return c1;
      } 
    } 
    return null;
  }
  
  private static <K extends Comparable<K>, V> boolean c(c<K, V> paramc) {
    boolean bool;
    if (paramc != null) {
      bool = c.e(paramc);
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private static <K extends Comparable<K>, V> c<K, V> d(c<K, V> paramc) {
    if (paramc != null) {
      paramc = c.b(paramc);
    } else {
      paramc = null;
    } 
    return paramc;
  }
  
  private static <K extends Comparable<K>, V> c<K, V> e(c<K, V> paramc) {
    if (paramc != null) {
      paramc = c.c(paramc);
    } else {
      paramc = null;
    } 
    return paramc;
  }
  
  private static <K extends Comparable<K>, V> c<K, V> f(c<K, V> paramc) {
    if (paramc == null)
      return null; 
    if (c.c(paramc) != null) {
      for (paramc = c.c(paramc); c.b(paramc) != null; paramc = c.b(paramc));
      return paramc;
    } 
    c<K, V> c1 = c.d(paramc);
    c<K, V> c2 = paramc;
    paramc = c1;
    while (true) {
      c1 = c2;
      c2 = paramc;
      if (c2 != null && c1 == c.c(c2)) {
        paramc = c.d(c2);
        continue;
      } 
      break;
    } 
    return c2;
  }
  
  private void g(c<K, V> paramc) {
    if (paramc != null) {
      c<K, V> c1 = c.c(paramc);
      c.b(paramc, c.b(c1));
      if (c.b(c1) != null)
        c.d(c.b(c1), paramc); 
      c.d(c1, c.d(paramc));
      if (c.d(paramc) == null) {
        this.b = c1;
      } else if (c.b(c.d(paramc)) == paramc) {
        c.a(c.d(paramc), c1);
      } else {
        c.b(c.d(paramc), c1);
      } 
      c.a(c1, paramc);
      c.d(paramc, c1);
    } 
  }
  
  private void h(c<K, V> paramc) {
    if (paramc != null) {
      c<K, V> c1 = c.b(paramc);
      c.a(paramc, c.c(c1));
      if (c.c(c1) != null)
        c.d(c.c(c1), paramc); 
      c.d(c1, c.d(paramc));
      if (c.d(paramc) == null) {
        this.b = c1;
      } else if (c.c(c.d(paramc)) == paramc) {
        c.b(c.d(paramc), c1);
      } else {
        c.a(c.d(paramc), c1);
      } 
      c.b(c1, paramc);
      c.d(paramc, c1);
    } 
  }
  
  private void i(c<K, V> paramc) {
    c<K, V> c1 = paramc;
    if (c.b(paramc) != null) {
      c1 = paramc;
      if (c.c(paramc) != null) {
        c1 = f(paramc);
        c.a(paramc, c.a(c1));
        c.a(paramc, c.f(c1));
      } 
    } 
    if (c.b(c1) != null) {
      paramc = c.b(c1);
    } else {
      paramc = c.c(c1);
    } 
    if (paramc != null) {
      c.d(paramc, c.d(c1));
      if (c.d(c1) == null) {
        this.b = paramc;
      } else if (c1 == c.b(c.d(c1))) {
        c.a(c.d(c1), paramc);
      } else {
        c.b(c.d(c1), paramc);
      } 
      c.a(c1, (c)null);
      c.b(c1, null);
      c.d(c1, null);
      if (!c.e(c1))
        k(paramc); 
    } else if (c.d(c1) == null) {
      this.b = null;
    } else {
      if (!c.e(c1))
        k(c1); 
      if (c.d(c1) != null) {
        if (c1 == c.b(c.d(c1))) {
          c.a(c.d(c1), (c)null);
        } else if (c1 == c.c(c.d(c1))) {
          c.b(c.d(c1), null);
        } 
        c.d(c1, null);
      } 
    } 
  }
  
  private void j(c<K, V> paramc) {
    if (paramc == this.c)
      this.c = c.g(paramc); 
    if (paramc == this.d)
      this.d = c.h(paramc); 
    c c1 = c.h(paramc);
    paramc = c.g(paramc);
    if (c1 != null)
      c.c(c1, paramc); 
    if (paramc != null)
      c.e(paramc, c1); 
  }
  
  private void k(c<K, V> paramc) {
    while (paramc != this.b && !c(paramc)) {
      if (paramc == d(b(paramc))) {
        c<Comparable, ?> c3 = e(b(paramc));
        c<Comparable, ?> c4 = c3;
        if (c(c3) == true) {
          a(c3, false);
          a(b(paramc), true);
          g(b(paramc));
          c4 = e(b(paramc));
        } 
        if (!c(d(c4)) && !c(e(c4))) {
          a(c4, true);
          paramc = b(paramc);
          continue;
        } 
        c3 = c4;
        if (!c(e(c4))) {
          a(d(c4), false);
          a(c4, true);
          h((c)c4);
          c3 = e(b(paramc));
        } 
        a(c3, c(b(paramc)));
        a(b(paramc), false);
        a(e(c3), false);
        g(b(paramc));
        paramc = this.b;
        continue;
      } 
      c<Comparable, ?> c1 = d(b(paramc));
      c<Comparable, ?> c2 = c1;
      if (c(c1) == true) {
        a(c1, false);
        a(b(paramc), true);
        h(b(paramc));
        c2 = d(b(paramc));
      } 
      if (!c(e(c2)) && !c(d(c2))) {
        a(c2, true);
        paramc = b(paramc);
        continue;
      } 
      c1 = c2;
      if (!c(d(c2))) {
        a(e(c2), false);
        a(c2, true);
        g((c)c2);
        c1 = d(b(paramc));
      } 
      a(c1, c(b(paramc)));
      a(b(paramc), false);
      a(d(c1), false);
      h(b(paramc));
      paramc = this.b;
    } 
    a(paramc, false);
  }
  
  public V a(K paramK) {
    c<K, V> c1 = c(paramK);
    if (c1 == null) {
      c1 = null;
    } else {
      c1 = (c<K, V>)c1.getValue();
    } 
    return (V)c1;
  }
  
  public V a(K paramK, V paramV) {
    c<K, V> c1;
    a.a(paramK);
    if (this.b == null) {
      c1 = new c<K, V>(null, null, paramK, paramV);
      this.b = c1;
      this.c = c1;
      this.d = c1;
      this.a++;
      return null;
    } 
    return b((K)c1, paramV);
  }
  
  public V b(K paramK) {
    c<K, V> c1 = c(paramK);
    if (c1 == null)
      return null; 
    this.a--;
    V v = c1.getValue();
    j(c1);
    i(c1);
    return v;
  }
  
  public Set<Map.Entry<K, V>> entrySet() {
    return new a(this);
  }
  
  public int size() {
    return this.a;
  }
  
  class a extends AbstractSet<Map.Entry<K, V>> {
    a(g this$0) {}
    
    public Iterator<Map.Entry<K, V>> iterator() {
      g g1 = this.a;
      return new g.b(g.a(g1));
    }
    
    public int size() {
      return this.a.size();
    }
  }
  
  private class b implements Iterator<Map.Entry<K, V>> {
    private g.c<K, V> b;
    
    private b(g this$0, g.c<K, V> param1c) {
      this.b = param1c;
    }
    
    public Map.Entry<K, V> a() {
      g.c<K, V> c1 = this.b;
      this.b = g.c.g(c1);
      return c1;
    }
    
    public boolean hasNext() {
      boolean bool;
      if (this.b != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public final void remove() {
      this.a.b(this.b.a());
    }
  }
  
  private static class c<K extends Comparable<K>, V> implements Map.Entry<K, V> {
    private K a;
    
    private V b;
    
    private c<K, V> c;
    
    private c<K, V> d;
    
    private c<K, V> e;
    
    private boolean f = false;
    
    private c<K, V> g;
    
    private c<K, V> h;
    
    c(c<K, V> param1c1, c<K, V> param1c2, K param1K, V param1V) {
      this.c = param1c1;
      this.h = param1c2;
      this.a = param1K;
      this.b = param1V;
    }
    
    public K a() {
      return this.a;
    }
    
    public final boolean equals(Object param1Object) {
      boolean bool = param1Object instanceof Map.Entry;
      boolean bool1 = false;
      if (!bool)
        return false; 
      Map.Entry entry = (Map.Entry)param1Object;
      param1Object = entry.getValue();
      bool = bool1;
      if (this.a.equals(entry.getKey())) {
        V v = this.b;
        if (v == null) {
          bool = bool1;
          if (param1Object == null)
            return true; 
        } else {
          bool = bool1;
          if (v.equals(param1Object))
            return true; 
        } 
      } 
      return bool;
    }
    
    public V getValue() {
      return this.b;
    }
    
    public final int hashCode() {
      int j;
      int i = this.a.hashCode();
      V v = this.b;
      if (v == null) {
        j = 0;
      } else {
        j = v.hashCode();
      } 
      return i ^ j;
    }
    
    public V setValue(V param1V) {
      V v = this.b;
      this.b = param1V;
      return v;
    }
    
    public final String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.a);
      stringBuilder.append("=");
      stringBuilder.append(this.b);
      return stringBuilder.toString();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */