package com.tencent.lbssearch.a.a;

import com.tencent.lbssearch.a.a.b.a.a;
import com.tencent.lbssearch.a.a.b.a.b;
import com.tencent.lbssearch.a.a.b.a.c;
import com.tencent.lbssearch.a.a.b.a.d;
import com.tencent.lbssearch.a.a.b.a.f;
import com.tencent.lbssearch.a.a.b.a.g;
import com.tencent.lbssearch.a.a.b.a.h;
import com.tencent.lbssearch.a.a.b.a.i;
import com.tencent.lbssearch.a.a.b.a.j;
import com.tencent.lbssearch.a.a.b.a.l;
import com.tencent.lbssearch.a.a.b.c;
import com.tencent.lbssearch.a.a.b.d;
import com.tencent.lbssearch.a.a.b.i;
import com.tencent.lbssearch.a.a.c.a;
import com.tencent.lbssearch.a.a.d.a;
import com.tencent.lbssearch.a.a.d.b;
import com.tencent.lbssearch.a.a.d.c;
import com.tencent.lbssearch.a.a.d.d;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class f {
  final j a = new j(this) {
      public <T> T a(l param1l, Type param1Type) throws p {
        return this.a.a(param1l, param1Type);
      }
    };
  
  final r b = new r(this) {
    
    };
  
  private final ThreadLocal<Map<a<?>, a<?>>> c = new ThreadLocal<Map<a<?>, a<?>>>();
  
  private final Map<a<?>, w<?>> d = Collections.synchronizedMap(new HashMap<a<?>, w<?>>());
  
  private final List<x> e;
  
  private final c f;
  
  private final boolean g;
  
  private final boolean h;
  
  private final boolean i;
  
  private final boolean j;
  
  public f() {
    this(d.a, d.a, Collections.emptyMap(), false, false, false, true, false, false, u.a, Collections.emptyList());
  }
  
  f(d paramd, e parame, Map<Type, h<?>> paramMap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, u paramu, List<x> paramList) {
    this.f = new c(paramMap);
    this.g = paramBoolean1;
    this.i = paramBoolean3;
    this.h = paramBoolean4;
    this.j = paramBoolean5;
    ArrayList<x> arrayList = new ArrayList();
    arrayList.add(l.Q);
    arrayList.add(g.a);
    arrayList.add(paramd);
    arrayList.addAll(paramList);
    arrayList.add(l.x);
    arrayList.add(l.m);
    arrayList.add(l.g);
    arrayList.add(l.i);
    arrayList.add(l.k);
    arrayList.add(l.a(long.class, Long.class, a(paramu)));
    arrayList.add(l.a(double.class, Double.class, a(paramBoolean6)));
    arrayList.add(l.a(float.class, Float.class, b(paramBoolean6)));
    arrayList.add(l.r);
    arrayList.add(l.t);
    arrayList.add(l.z);
    arrayList.add(l.B);
    arrayList.add(l.a(BigDecimal.class, l.v));
    arrayList.add(l.a(BigInteger.class, l.w));
    arrayList.add(l.D);
    arrayList.add(l.F);
    arrayList.add(l.J);
    arrayList.add(l.O);
    arrayList.add(l.H);
    arrayList.add(l.d);
    arrayList.add(c.a);
    arrayList.add(l.M);
    arrayList.add(j.a);
    arrayList.add(i.a);
    arrayList.add(l.K);
    arrayList.add(a.a);
    arrayList.add(l.R);
    arrayList.add(l.b);
    arrayList.add(new b(this.f));
    arrayList.add(new f(this.f, paramBoolean2));
    arrayList.add(new h(this.f, parame, paramd));
    this.e = Collections.unmodifiableList(arrayList);
  }
  
  private w<Number> a(u paramu) {
    return (paramu == u.a) ? l.n : new w<Number>(this) {
        public Number a(a param1a) throws IOException {
          if (param1a.f() == b.i) {
            param1a.j();
            return null;
          } 
          return Long.valueOf(param1a.l());
        }
        
        public void a(c param1c, Number param1Number) throws IOException {
          if (param1Number == null) {
            param1c.f();
            return;
          } 
          param1c.b(param1Number.toString());
        }
      };
  }
  
  private w<Number> a(boolean paramBoolean) {
    return paramBoolean ? l.p : new w<Number>(this) {
        public Double a(a param1a) throws IOException {
          if (param1a.f() == b.i) {
            param1a.j();
            return null;
          } 
          return Double.valueOf(param1a.k());
        }
        
        public void a(c param1c, Number param1Number) throws IOException {
          if (param1Number == null) {
            param1c.f();
            return;
          } 
          double d = param1Number.doubleValue();
          f.a(this.a, d);
          param1c.a(param1Number);
        }
      };
  }
  
  private void a(double paramDouble) {
    if (!Double.isNaN(paramDouble) && !Double.isInfinite(paramDouble))
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramDouble);
    stringBuilder.append(" is not a valid double value as per JSON specification. To override this");
    stringBuilder.append(" behavior, use GsonBuilder.serializeSpecialDoubleValues() method.");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  private static void a(Object paramObject, a parama) {
    if (paramObject != null)
      try {
        if (parama.f() != b.j) {
          paramObject = new m();
          super("JSON document was not fully consumed.");
          throw paramObject;
        } 
      } catch (d d) {
        throw new t(d);
      } catch (IOException iOException) {
        throw new m(iOException);
      }  
  }
  
  private w<Number> b(boolean paramBoolean) {
    return paramBoolean ? l.o : new w<Number>(this) {
        public Float a(a param1a) throws IOException {
          if (param1a.f() == b.i) {
            param1a.j();
            return null;
          } 
          return Float.valueOf((float)param1a.k());
        }
        
        public void a(c param1c, Number param1Number) throws IOException {
          if (param1Number == null) {
            param1c.f();
            return;
          } 
          float f1 = param1Number.floatValue();
          f.a(this.a, f1);
          param1c.a(param1Number);
        }
      };
  }
  
  public <T> w<T> a(a<T> parama) {
    w<T> w = (w)this.d.get(parama);
    if (w != null)
      return w; 
    Map<Object, Object> map2 = (Map)this.c.get();
    boolean bool = false;
    Map<Object, Object> map1 = map2;
    if (map2 == null) {
      map1 = new HashMap<Object, Object>();
      this.c.set(map1);
      bool = true;
    } 
    null = (a)map1.get(parama);
    if (null != null)
      return null; 
    try {
      a<T> a1 = new a();
      this();
      map1.put(parama, a1);
      Iterator<x> iterator = this.e.iterator();
      while (iterator.hasNext()) {
        w<T> w1 = ((x)iterator.next()).a(this, parama);
        if (w1 != null) {
          a1.a(w1);
          this.d.put(parama, w1);
          return w1;
        } 
      } 
      IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("GSON cannot handle ");
      stringBuilder.append(parama);
      this(stringBuilder.toString());
      throw illegalArgumentException;
    } finally {
      map1.remove(parama);
      if (bool)
        this.c.remove(); 
    } 
  }
  
  public <T> w<T> a(x paramx, a<T> parama) {
    Iterator<x> iterator = this.e.iterator();
    boolean bool = false;
    while (iterator.hasNext()) {
      x x1 = iterator.next();
      if (!bool) {
        if (x1 == paramx)
          bool = true; 
        continue;
      } 
      w<T> w = x1.a(this, parama);
      if (w != null)
        return w; 
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("GSON cannot serialize ");
    stringBuilder.append(parama);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public <T> w<T> a(Class<T> paramClass) {
    return a(a.b(paramClass));
  }
  
  public <T> T a(a parama, Type paramType) throws m, t {
    boolean bool = parama.p();
    boolean bool1 = true;
    parama.a(true);
    try {
      parama.f();
      bool1 = false;
      paramType = a(a.a(paramType)).b(parama);
      parama.a(bool);
      return (T)paramType;
    } catch (EOFException eOFException) {
      if (bool1) {
        parama.a(bool);
        return null;
      } 
      t t = new t();
      this(eOFException);
      throw t;
    } catch (IllegalStateException illegalStateException) {
      t t = new t();
      this(illegalStateException);
      throw t;
    } catch (IOException iOException) {
      t t = new t();
      this(iOException);
      throw t;
    } finally {}
    parama.a(bool);
    throw paramType;
  }
  
  public <T> T a(l paraml, Type paramType) throws t {
    return (paraml == null) ? null : a((a)new d(paraml), paramType);
  }
  
  public <T> T a(Reader paramReader, Type paramType) throws m, t {
    a a = new a(paramReader);
    paramType = a(a, paramType);
    a(paramType, a);
    return (T)paramType;
  }
  
  public <T> T a(String paramString, Class<T> paramClass) throws t {
    paramString = a(paramString, paramClass);
    return i.a(paramClass).cast(paramString);
  }
  
  public <T> T a(String paramString, Type paramType) throws t {
    return (paramString == null) ? null : a(new StringReader(paramString), paramType);
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("{serializeNulls:");
    stringBuilder.append(this.g);
    stringBuilder.append("factories:");
    stringBuilder.append(this.e);
    stringBuilder.append(",instanceCreators:");
    stringBuilder.append(this.f);
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  static class a<T> extends w<T> {
    private w<T> a;
    
    public void a(c param1c, T param1T) throws IOException {
      w<T> w1 = this.a;
      if (w1 != null) {
        w1.a(param1c, param1T);
        return;
      } 
      throw new IllegalStateException();
    }
    
    public void a(w<T> param1w) {
      if (this.a == null) {
        this.a = param1w;
        return;
      } 
      throw new AssertionError();
    }
    
    public T b(a param1a) throws IOException {
      w<T> w1 = this.a;
      if (w1 != null)
        return w1.b(param1a); 
      throw new IllegalStateException();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */