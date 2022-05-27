package com.tencent.mapsdk.rastercore.b;

public final class a {
  public static int a;
  
  private static double b = Math.log(2.0D);
  
  private int c;
  
  private double d;
  
  private double e;
  
  static {
    a = 5;
  }
  
  public a(double paramDouble) {
    this.e = paramDouble;
    f();
  }
  
  public a(int paramInt, double paramDouble) {
    this.c = paramInt;
    this.d = paramDouble;
    e();
  }
  
  public static double c(double paramDouble) {
    return 156543.0339D / Math.pow(2.0D, paramDouble);
  }
  
  private void e() {
    this.e = Math.log(this.d) / b + this.c;
    StringBuilder stringBuilder = new StringBuilder("refreshZoom--zoom=");
    stringBuilder.append(this.e);
    stringBuilder.append(";level=");
    stringBuilder.append(this.c);
    stringBuilder.append(";scale=");
    stringBuilder.append(this.d);
  }
  
  private void f() {
    double d = this.e;
    int i = (int)d;
    this.c = i;
    this.d = Math.pow(2.0D, d - i);
    StringBuilder stringBuilder = new StringBuilder("refreshLevel--zoom=");
    stringBuilder.append(this.e);
    stringBuilder.append(";level=");
    stringBuilder.append(this.c);
    stringBuilder.append(";scale=");
    stringBuilder.append(this.d);
  }
  
  public final int a() {
    return this.c;
  }
  
  public final void a(double paramDouble) {
    this.d = paramDouble;
    e();
    f();
  }
  
  public final void a(int paramInt) {
    this.c = 19;
    e();
  }
  
  public final double b() {
    return this.d;
  }
  
  public final void b(double paramDouble) {
    this.e = paramDouble;
    f();
  }
  
  public final double c() {
    return this.e;
  }
  
  public final double d() {
    return c(this.e);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\b\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */