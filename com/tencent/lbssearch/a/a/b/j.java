package com.tencent.lbssearch.a.a.b;

import com.tencent.lbssearch.a.a.b.a.l;
import com.tencent.lbssearch.a.a.d.a;
import com.tencent.lbssearch.a.a.d.c;
import com.tencent.lbssearch.a.a.d.d;
import com.tencent.lbssearch.a.a.l;
import com.tencent.lbssearch.a.a.m;
import com.tencent.lbssearch.a.a.n;
import com.tencent.lbssearch.a.a.p;
import com.tencent.lbssearch.a.a.t;
import java.io.EOFException;
import java.io.IOException;

public final class j {
  public static l a(a parama) throws p {
    boolean bool;
    try {
      parama.f();
      bool = false;
      try {
        return (l)l.P.b(parama);
      } catch (EOFException eOFException) {}
    } catch (EOFException eOFException) {
      bool = true;
    } catch (d d) {
      throw new t(d);
    } catch (IOException iOException) {
      throw new m(iOException);
    } catch (NumberFormatException numberFormatException) {
      throw new t(numberFormatException);
    } 
    if (bool)
      return (l)n.a; 
    throw new t(numberFormatException);
  }
  
  public static void a(l paraml, c paramc) throws IOException {
    l.P.a(paramc, paraml);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */