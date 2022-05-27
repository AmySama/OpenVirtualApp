package com.tencent.lbssearch.a.a;

import com.tencent.lbssearch.a.a.b.j;
import com.tencent.lbssearch.a.a.d.c;
import java.io.IOException;
import java.io.StringWriter;

public abstract class l {
  public Number b() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public String c() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public double d() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public float e() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public long f() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public int g() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public boolean h() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public boolean i() {
    return this instanceof i;
  }
  
  public boolean j() {
    return this instanceof o;
  }
  
  public boolean k() {
    return this instanceof q;
  }
  
  public boolean l() {
    return this instanceof n;
  }
  
  public o m() {
    if (j())
      return (o)this; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Not a JSON Object: ");
    stringBuilder.append(this);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public i n() {
    if (i())
      return (i)this; 
    throw new IllegalStateException("This is not a JSON Array.");
  }
  
  public q o() {
    if (k())
      return (q)this; 
    throw new IllegalStateException("This is not a JSON Primitive.");
  }
  
  Boolean p() {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public String toString() {
    try {
      StringWriter stringWriter = new StringWriter();
      this();
      c c = new c();
      this(stringWriter);
      c.b(true);
      j.a(this, c);
      return stringWriter.toString();
    } catch (IOException iOException) {
      throw new AssertionError(iOException);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\l.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */