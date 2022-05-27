package com.tencent.lbssearch.a.a.b.a;

import com.tencent.lbssearch.a.a.b.c;
import com.tencent.lbssearch.a.a.b.d;
import com.tencent.lbssearch.a.a.b.i;
import com.tencent.lbssearch.a.a.d.c;
import com.tencent.lbssearch.a.a.e;
import com.tencent.lbssearch.a.a.f;
import com.tencent.lbssearch.a.a.t;
import com.tencent.lbssearch.a.a.w;
import com.tencent.lbssearch.a.a.x;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public final class h implements x {
  private final c a;
  
  private final e b;
  
  private final d c;
  
  public h(c paramc, e parame, d paramd) {
    this.a = paramc;
    this.b = parame;
    this.c = paramd;
  }
  
  private b a(f paramf, Field paramField, String paramString, com.tencent.lbssearch.a.a.c.a<?> parama, boolean paramBoolean1, boolean paramBoolean2) {
    return new b(this, paramString, paramBoolean1, paramBoolean2, paramf, parama, paramField, i.a(parama.a())) {
        final w<?> a = this.b.a(this.c);
        
        void a(com.tencent.lbssearch.a.a.d.a param1a, Object param1Object) throws IOException, IllegalAccessException {
          Object object = this.a.b(param1a);
          if (object != null || !this.e)
            this.d.set(param1Object, object); 
        }
        
        void a(c param1c, Object param1Object) throws IOException, IllegalAccessException {
          param1Object = this.d.get(param1Object);
          (new k(this.b, this.a, this.c.b())).a(param1c, param1Object);
        }
      };
  }
  
  private String a(Field paramField) {
    String str;
    com.tencent.lbssearch.a.a.a.b b = paramField.<com.tencent.lbssearch.a.a.a.b>getAnnotation(com.tencent.lbssearch.a.a.a.b.class);
    if (b == null) {
      str = this.b.a(paramField);
    } else {
      str = b.a();
    } 
    return str;
  }
  
  private Map<String, b> a(f paramf, com.tencent.lbssearch.a.a.c.a<?> parama, Class<?> paramClass) {
    LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<Object, Object>();
    if (paramClass.isInterface())
      return (Map)linkedHashMap; 
    Type type = parama.b();
    while (paramClass != Object.class) {
      for (Field field : paramClass.getDeclaredFields()) {
        boolean bool1 = a(field, true);
        boolean bool2 = a(field, false);
        if (bool1 || bool2) {
          field.setAccessible(true);
          Type type1 = com.tencent.lbssearch.a.a.b.b.a(parama.b(), paramClass, field.getGenericType());
          b b = a(paramf, field, a(field), com.tencent.lbssearch.a.a.c.a.a(type1), bool1, bool2);
          b = (b)linkedHashMap.put(b.g, b);
          if (b != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(type);
            stringBuilder.append(" declares multiple JSON fields named ");
            stringBuilder.append(b.g);
            throw new IllegalArgumentException(stringBuilder.toString());
          } 
        } 
      } 
      parama = com.tencent.lbssearch.a.a.c.a.a(com.tencent.lbssearch.a.a.b.b.a(parama.b(), paramClass, paramClass.getGenericSuperclass()));
      paramClass = parama.a();
    } 
    return (Map)linkedHashMap;
  }
  
  public <T> w<T> a(f paramf, com.tencent.lbssearch.a.a.c.a<T> parama) {
    Class<?> clazz = parama.a();
    return !Object.class.isAssignableFrom(clazz) ? null : new a<T>(this.a.a(parama), a(paramf, parama, clazz));
  }
  
  public boolean a(Field paramField, boolean paramBoolean) {
    if (!this.c.a(paramField.getType(), paramBoolean) && !this.c.a(paramField, paramBoolean)) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    } 
    return paramBoolean;
  }
  
  public final class a<T> extends w<T> {
    private final com.tencent.lbssearch.a.a.b.h<T> b;
    
    private final Map<String, h.b> c;
    
    private a(h this$0, com.tencent.lbssearch.a.a.b.h<T> param1h, Map<String, h.b> param1Map) {
      this.b = param1h;
      this.c = param1Map;
    }
    
    public void a(c param1c, T param1T) throws IOException {
      if (param1T == null) {
        param1c.f();
        return;
      } 
      param1c.d();
      try {
        for (h.b b : this.c.values()) {
          if (b.h) {
            param1c.a(b.g);
            b.a(param1c, param1T);
          } 
        } 
        param1c.e();
        return;
      } catch (IllegalAccessException illegalAccessException) {
        throw new AssertionError();
      } 
    }
    
    public T b(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
      if (param1a.f() == com.tencent.lbssearch.a.a.d.b.i) {
        param1a.j();
        return null;
      } 
      Object object = this.b.a();
      try {
        param1a.c();
        while (param1a.e()) {
          String str = param1a.g();
          h.b b = this.c.get(str);
          if (b == null || !b.i) {
            param1a.n();
            continue;
          } 
          b.a(param1a, object);
        } 
        param1a.d();
        return (T)object;
      } catch (IllegalStateException illegalStateException) {
        throw new t(illegalStateException);
      } catch (IllegalAccessException illegalAccessException) {
        throw new AssertionError(illegalAccessException);
      } 
    }
  }
  
  static abstract class b {
    final String g;
    
    final boolean h;
    
    final boolean i;
    
    protected b(String param1String, boolean param1Boolean1, boolean param1Boolean2) {
      this.g = param1String;
      this.h = param1Boolean1;
      this.i = param1Boolean2;
    }
    
    abstract void a(com.tencent.lbssearch.a.a.d.a param1a, Object param1Object) throws IOException, IllegalAccessException;
    
    abstract void a(c param1c, Object param1Object) throws IOException, IllegalAccessException;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\a\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */