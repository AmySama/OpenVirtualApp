package com.tencent.lbssearch.a.a.b.a;

import com.tencent.lbssearch.a.a.c.a;
import com.tencent.lbssearch.a.a.d.a;
import com.tencent.lbssearch.a.a.d.c;
import com.tencent.lbssearch.a.a.f;
import com.tencent.lbssearch.a.a.w;
import java.io.IOException;
import java.lang.reflect.Type;

final class k<T> extends w<T> {
  private final f a;
  
  private final w<T> b;
  
  private final Type c;
  
  k(f paramf, w<T> paramw, Type paramType) {
    this.a = paramf;
    this.b = paramw;
    this.c = paramType;
  }
  
  private Type a(Type<?> paramType, Object paramObject) {
    null = paramType;
    if (paramObject != null) {
      if (paramType != Object.class && !(paramType instanceof java.lang.reflect.TypeVariable)) {
        null = paramType;
        return (paramType instanceof Class) ? paramObject.getClass() : null;
      } 
    } else {
      return null;
    } 
    return paramObject.getClass();
  }
  
  public void a(c paramc, T paramT) throws IOException {
    w<T> w1 = this.b;
    Type type = a(this.c, paramT);
    if (type != this.c) {
      w1 = this.a.a(a.a(type));
      if (w1 instanceof h.a) {
        w<T> w2 = this.b;
        if (!(w2 instanceof h.a))
          w1 = w2; 
      } 
    } 
    w1.a(paramc, paramT);
  }
  
  public T b(a parama) throws IOException {
    return (T)this.b.b(parama);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\a\k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */