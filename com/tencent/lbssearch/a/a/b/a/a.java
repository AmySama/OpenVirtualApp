package com.tencent.lbssearch.a.a.b.a;

import com.tencent.lbssearch.a.a.b.b;
import com.tencent.lbssearch.a.a.d.b;
import com.tencent.lbssearch.a.a.d.c;
import com.tencent.lbssearch.a.a.f;
import com.tencent.lbssearch.a.a.w;
import com.tencent.lbssearch.a.a.x;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class a<E> extends w<Object> {
  public static final x a = new x() {
      public <T> w<T> a(f param1f, com.tencent.lbssearch.a.a.c.a<T> param1a) {
        Type type = param1a.b();
        if (!(type instanceof java.lang.reflect.GenericArrayType) && (!(type instanceof Class) || !((Class)type).isArray()))
          return null; 
        type = b.g(type);
        return new a(param1f, param1f.a(com.tencent.lbssearch.a.a.c.a.a(type)), b.e(type));
      }
    };
  
  private final f b;
  
  private final Class<E> c;
  
  private final w<E> d;
  
  public a(f paramf, w<E> paramw, Class<E> paramClass) {
    this.b = paramf;
    this.d = new k<E>(paramf, paramw, paramClass);
    this.c = paramClass;
  }
  
  public void a(c paramc, Object paramObject) throws IOException {
    if (paramObject == null) {
      paramc.f();
      return;
    } 
    paramc.b();
    byte b = 0;
    int i = Array.getLength(paramObject);
    while (b < i) {
      Object object = Array.get(paramObject, b);
      this.d.a(paramc, object);
      b++;
    } 
    paramc.c();
  }
  
  public Object b(com.tencent.lbssearch.a.a.d.a parama) throws IOException {
    if (parama.f() == b.i) {
      parama.j();
      return null;
    } 
    ArrayList<Object> arrayList = new ArrayList();
    parama.a();
    while (parama.e())
      arrayList.add(this.d.b(parama)); 
    parama.b();
    Object object = Array.newInstance(this.c, arrayList.size());
    for (byte b = 0; b < arrayList.size(); b++)
      Array.set(object, b, arrayList.get(b)); 
    return object;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */