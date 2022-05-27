package com.tencent.lbssearch.a.a;

import com.tencent.lbssearch.a.a.b.a.e;
import com.tencent.lbssearch.a.a.d.a;
import com.tencent.lbssearch.a.a.d.c;
import java.io.IOException;

public abstract class w<T> {
  public final l a(T paramT) {
    try {
      e e = new e();
      this();
      a((c)e, paramT);
      return e.a();
    } catch (IOException iOException) {
      throw new m(iOException);
    } 
  }
  
  public abstract void a(c paramc, T paramT) throws IOException;
  
  public abstract T b(a parama) throws IOException;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\w.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */