package com.tencent.lbssearch.a.a.b.a;

import com.tencent.lbssearch.a.a.c.a;
import com.tencent.lbssearch.a.a.d.a;
import com.tencent.lbssearch.a.a.d.b;
import com.tencent.lbssearch.a.a.f;
import com.tencent.lbssearch.a.a.t;
import com.tencent.lbssearch.a.a.w;
import com.tencent.lbssearch.a.a.x;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class c extends w<Date> {
  public static final x a = new x() {
      public <T> w<T> a(f param1f, a<T> param1a) {
        if (param1a.a() == Date.class) {
          c c = new c();
        } else {
          param1f = null;
        } 
        return (w<T>)param1f;
      }
    };
  
  private final DateFormat b = DateFormat.getDateTimeInstance(2, 2, Locale.US);
  
  private final DateFormat c = DateFormat.getDateTimeInstance(2, 2);
  
  private final DateFormat d = a();
  
  private static DateFormat a() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    return simpleDateFormat;
  }
  
  private Date a(String paramString) {
    /* monitor enter ThisExpression{ObjectType{com/tencent/lbssearch/a/a/b/a/c}} */
    try {
      Date date = this.c.parse(paramString);
      /* monitor exit ThisExpression{ObjectType{com/tencent/lbssearch/a/a/b/a/c}} */
      return date;
    } catch (ParseException parseException) {
      try {
        Date date = this.b.parse(paramString);
        /* monitor exit ThisExpression{ObjectType{com/tencent/lbssearch/a/a/b/a/c}} */
        return date;
      } catch (ParseException parseException1) {
        try {
          Date date = this.d.parse(paramString);
          /* monitor exit ThisExpression{ObjectType{com/tencent/lbssearch/a/a/b/a/c}} */
          return date;
        } catch (ParseException parseException2) {
          t t = new t();
          this(paramString, parseException2);
          throw t;
        } 
      } 
    } finally {}
    /* monitor exit ThisExpression{ObjectType{com/tencent/lbssearch/a/a/b/a/c}} */
    throw paramString;
  }
  
  public Date a(a parama) throws IOException {
    if (parama.f() == b.i) {
      parama.j();
      return null;
    } 
    return a(parama.h());
  }
  
  public void a(com.tencent.lbssearch.a.a.d.c paramc, Date paramDate) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_2
    //   3: ifnonnull -> 14
    //   6: aload_1
    //   7: invokevirtual f : ()Lcom/tencent/lbssearch/a/a/d/c;
    //   10: pop
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: aload_1
    //   15: aload_0
    //   16: getfield b : Ljava/text/DateFormat;
    //   19: aload_2
    //   20: invokevirtual format : (Ljava/util/Date;)Ljava/lang/String;
    //   23: invokevirtual b : (Ljava/lang/String;)Lcom/tencent/lbssearch/a/a/d/c;
    //   26: pop
    //   27: aload_0
    //   28: monitorexit
    //   29: return
    //   30: astore_1
    //   31: aload_0
    //   32: monitorexit
    //   33: aload_1
    //   34: athrow
    // Exception table:
    //   from	to	target	type
    //   6	11	30	finally
    //   14	27	30	finally
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */