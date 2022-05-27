package com.tencent.lbssearch.a.d;

public class l<T> {
  public final T a = null;
  
  public final q b;
  
  public boolean c = false;
  
  private l(q paramq) {
    this.b = paramq;
  }
  
  private l(T paramT) {
    this.b = null;
  }
  
  public static <T> l<T> a(q paramq) {
    return new l<T>(paramq);
  }
  
  public static <T> l<T> a(T paramT) {
    return new l<T>(paramT);
  }
  
  public boolean a() {
    boolean bool;
    if (this.b == null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static interface a {
    void a(q param1q);
  }
  
  public static interface b<T> {
    void a(T param1T);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\d\l.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */