package com.tencent.mm.opensdk.diffdev.a;

public enum d {
  b(402),
  c(403),
  d(404),
  e(405),
  f(408),
  g(408);
  
  private int a;
  
  static {
    d d1 = new d("UUID_ERROR", 5, 500);
    g = d1;
    h = new d[] { b, c, d, e, f, d1 };
  }
  
  d(int paramInt1) {
    this.a = paramInt1;
  }
  
  public int a() {
    return this.a;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("UUIDStatusCode:");
    stringBuilder.append(this.a);
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\diffdev\a\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */