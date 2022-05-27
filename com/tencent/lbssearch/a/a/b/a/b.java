package com.tencent.lbssearch.a.a.b.a;

import com.tencent.lbssearch.a.a.b.c;
import com.tencent.lbssearch.a.a.b.h;
import com.tencent.lbssearch.a.a.d.c;
import com.tencent.lbssearch.a.a.f;
import com.tencent.lbssearch.a.a.w;
import com.tencent.lbssearch.a.a.x;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

public final class b implements x {
  private final c a;
  
  public b(c paramc) {
    this.a = paramc;
  }
  
  public <T> w<T> a(f paramf, com.tencent.lbssearch.a.a.c.a<T> parama) {
    Type type = parama.b();
    Class<?> clazz = parama.a();
    if (!Collection.class.isAssignableFrom(clazz))
      return null; 
    type = com.tencent.lbssearch.a.a.b.b.a(type, clazz);
    return new a(this, paramf, type, paramf.a(com.tencent.lbssearch.a.a.c.a.a(type)), this.a.a(parama));
  }
  
  private final class a<E> extends w<Collection<E>> {
    private final f b;
    
    private final Type c;
    
    private final w<E> d;
    
    private final h<? extends Collection<E>> e;
    
    public a(b this$0, f param1f, Type param1Type, w<E> param1w, h<? extends Collection<E>> param1h) {
      this.b = param1f;
      this.c = param1Type;
      this.d = new k<E>(param1f, param1w, param1Type);
      this.e = param1h;
    }
    
    public Collection<E> a(com.tencent.lbssearch.a.a.d.a param1a) throws IOException {
      if (param1a.f() == com.tencent.lbssearch.a.a.d.b.i) {
        param1a.j();
        return null;
      } 
      Collection<Object> collection = (Collection)this.e.a();
      param1a.a();
      while (param1a.e())
        collection.add(this.d.b(param1a)); 
      param1a.b();
      return (Collection)collection;
    }
    
    public void a(c param1c, Collection<E> param1Collection) throws IOException {
      // Byte code:
      //   0: aload_2
      //   1: ifnonnull -> 10
      //   4: aload_1
      //   5: invokevirtual f : ()Lcom/tencent/lbssearch/a/a/d/c;
      //   8: pop
      //   9: return
      //   10: aload_1
      //   11: invokevirtual b : ()Lcom/tencent/lbssearch/a/a/d/c;
      //   14: pop
      //   15: aload_2
      //   16: invokeinterface iterator : ()Ljava/util/Iterator;
      //   21: astore_2
      //   22: aload_2
      //   23: invokeinterface hasNext : ()Z
      //   28: ifeq -> 50
      //   31: aload_2
      //   32: invokeinterface next : ()Ljava/lang/Object;
      //   37: astore_3
      //   38: aload_0
      //   39: getfield d : Lcom/tencent/lbssearch/a/a/w;
      //   42: aload_1
      //   43: aload_3
      //   44: invokevirtual a : (Lcom/tencent/lbssearch/a/a/d/c;Ljava/lang/Object;)V
      //   47: goto -> 22
      //   50: aload_1
      //   51: invokevirtual c : ()Lcom/tencent/lbssearch/a/a/d/c;
      //   54: pop
      //   55: return
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */