package com.tencent.lbssearch.a.a.d;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class c implements Closeable, Flushable {
  private static final String[] a = new String[128];
  
  private static final String[] b;
  
  private final Writer c;
  
  private int[] d = new int[32];
  
  private int e = 0;
  
  private String f;
  
  private String g;
  
  private boolean h;
  
  private boolean i;
  
  private String j;
  
  private boolean k;
  
  static {
    for (byte b = 0; b <= 31; b++) {
      a[b] = String.format("\\u%04x", new Object[] { Integer.valueOf(b) });
    } 
    String[] arrayOfString = a;
    arrayOfString[34] = "\\\"";
    arrayOfString[92] = "\\\\";
    arrayOfString[9] = "\\t";
    arrayOfString[8] = "\\b";
    arrayOfString[10] = "\\n";
    arrayOfString[13] = "\\r";
    arrayOfString[12] = "\\f";
    arrayOfString = (String[])arrayOfString.clone();
    b = arrayOfString;
    arrayOfString[60] = "\\u003c";
    arrayOfString[62] = "\\u003e";
    arrayOfString[38] = "\\u0026";
    arrayOfString[61] = "\\u003d";
    arrayOfString[39] = "\\u0027";
  }
  
  public c(Writer paramWriter) {
    a(6);
    this.g = ":";
    this.k = true;
    if (paramWriter != null) {
      this.c = paramWriter;
      return;
    } 
    throw new NullPointerException("out == null");
  }
  
  private int a() {
    int i = this.e;
    if (i != 0)
      return this.d[i - 1]; 
    throw new IllegalStateException("JsonWriter is closed.");
  }
  
  private c a(int paramInt1, int paramInt2, String paramString) throws IOException {
    int i = a();
    if (i == paramInt2 || i == paramInt1) {
      if (this.j == null) {
        this.e--;
        if (i == paramInt2)
          j(); 
        this.c.write(paramString);
        return this;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Dangling name: ");
      stringBuilder.append(this.j);
      throw new IllegalStateException(stringBuilder.toString());
    } 
    throw new IllegalStateException("Nesting problem.");
  }
  
  private c a(int paramInt, String paramString) throws IOException {
    c(true);
    a(paramInt);
    this.c.write(paramString);
    return this;
  }
  
  private void a(int paramInt) {
    int i = this.e;
    int[] arrayOfInt1 = this.d;
    if (i == arrayOfInt1.length) {
      int[] arrayOfInt = new int[i * 2];
      System.arraycopy(arrayOfInt1, 0, arrayOfInt, 0, i);
      this.d = arrayOfInt;
    } 
    int[] arrayOfInt2 = this.d;
    i = this.e;
    this.e = i + 1;
    arrayOfInt2[i] = paramInt;
  }
  
  private void b(int paramInt) {
    this.d[this.e - 1] = paramInt;
  }
  
  private void c(String paramString) throws IOException {
    String[] arrayOfString;
    Object object;
    if (this.i) {
      arrayOfString = b;
    } else {
      arrayOfString = a;
    } 
    this.c.write("\"");
    int i = paramString.length();
    byte b = 0;
    boolean bool = false;
    while (b < i) {
      String str;
      char c1 = paramString.charAt(b);
      if (c1 < '') {
        String str1 = arrayOfString[c1];
        str = str1;
        if (str1 == null) {
          Object object1 = object;
          continue;
        } 
      } else if (c1 == ' ') {
        str = "\\u2028";
      } else {
        Object object1 = object;
        if (c1 == ' ') {
          str = "\\u2029";
        } else {
          continue;
        } 
      } 
      if (object < b)
        this.c.write(paramString, object, b - object); 
      this.c.write(str);
      int j = b + 1;
      continue;
      b++;
      object = SYNTHETIC_LOCAL_VARIABLE_9;
    } 
    if (object < i)
      this.c.write(paramString, object, i - object); 
    this.c.write("\"");
  }
  
  private void c(boolean paramBoolean) throws IOException {
    int i = a();
    if (i != 1) {
      if (i != 2) {
        if (i != 4) {
          if (i != 6)
            if (i == 7) {
              if (!this.h)
                throw new IllegalStateException("JSON must have only one top-level value."); 
            } else {
              throw new IllegalStateException("Nesting problem.");
            }  
          if (this.h || paramBoolean) {
            b(7);
            return;
          } 
          throw new IllegalStateException("JSON must start with an array or an object.");
        } 
        this.c.append(this.g);
        b(5);
      } else {
        this.c.append(',');
        j();
      } 
    } else {
      b(2);
      j();
    } 
  }
  
  private void i() throws IOException {
    if (this.j != null) {
      k();
      c(this.j);
      this.j = null;
    } 
  }
  
  private void j() throws IOException {
    if (this.f == null)
      return; 
    this.c.write("\n");
    int i = this.e;
    for (byte b = 1; b < i; b++)
      this.c.write(this.f); 
  }
  
  private void k() throws IOException {
    int i = a();
    if (i == 5) {
      this.c.write(44);
    } else if (i != 3) {
      throw new IllegalStateException("Nesting problem.");
    } 
    j();
    b(4);
  }
  
  public c a(long paramLong) throws IOException {
    i();
    c(false);
    this.c.write(Long.toString(paramLong));
    return this;
  }
  
  public c a(Number paramNumber) throws IOException {
    if (paramNumber == null)
      return f(); 
    i();
    String str = paramNumber.toString();
    if (this.h || (!str.equals("-Infinity") && !str.equals("Infinity") && !str.equals("NaN"))) {
      c(false);
      this.c.append(str);
      return this;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Numeric values must be finite, but was ");
    stringBuilder.append(paramNumber);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public c a(String paramString) throws IOException {
    if (paramString != null) {
      if (this.j == null) {
        if (this.e != 0) {
          this.j = paramString;
          return this;
        } 
        throw new IllegalStateException("JsonWriter is closed.");
      } 
      throw new IllegalStateException();
    } 
    throw new NullPointerException("name == null");
  }
  
  public c a(boolean paramBoolean) throws IOException {
    String str;
    i();
    c(false);
    Writer writer = this.c;
    if (paramBoolean) {
      str = "true";
    } else {
      str = "false";
    } 
    writer.write(str);
    return this;
  }
  
  public c b() throws IOException {
    i();
    return a(1, "[");
  }
  
  public c b(String paramString) throws IOException {
    if (paramString == null)
      return f(); 
    i();
    c(false);
    c(paramString);
    return this;
  }
  
  public final void b(boolean paramBoolean) {
    this.h = paramBoolean;
  }
  
  public c c() throws IOException {
    return a(1, 2, "]");
  }
  
  public void close() throws IOException {
    this.c.close();
    int i = this.e;
    if (i <= 1 && (i != 1 || this.d[i - 1] == 7)) {
      this.e = 0;
      return;
    } 
    throw new IOException("Incomplete document");
  }
  
  public c d() throws IOException {
    i();
    return a(3, "{");
  }
  
  public c e() throws IOException {
    return a(3, 5, "}");
  }
  
  public c f() throws IOException {
    if (this.j != null)
      if (this.k) {
        i();
      } else {
        this.j = null;
        return this;
      }  
    c(false);
    this.c.write("null");
    return this;
  }
  
  public void flush() throws IOException {
    if (this.e != 0) {
      this.c.flush();
      return;
    } 
    throw new IllegalStateException("JsonWriter is closed.");
  }
  
  public boolean g() {
    return this.h;
  }
  
  public final boolean h() {
    return this.k;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\d\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */