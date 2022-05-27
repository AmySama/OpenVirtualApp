package com.tencent.mapsdk.rastercore.b;

public final class b {
  private c a;
  
  private c b;
  
  public b(c paramc1, c paramc2) {
    a a = (new a()).a(paramc1).a(paramc2);
    this.a = new c(a.a(a), a.b(a));
    this.b = new c(a.c(a), a.d(a));
  }
  
  public final c a() {
    return this.a;
  }
  
  public final c b() {
    return this.b;
  }
  
  public final boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (!(paramObject instanceof b))
      return false; 
    paramObject = paramObject;
    return (this.a.equals(((b)paramObject).a) && this.b.equals(((b)paramObject).b));
  }
  
  public final int hashCode() {
    return com.tencent.mapsdk.rastercore.f.a.a(new Object[] { this.a, this.b });
  }
  
  public final String toString() {
    return com.tencent.mapsdk.rastercore.f.a.a(new String[] { com.tencent.mapsdk.rastercore.f.a.a("southwest", this.a), com.tencent.mapsdk.rastercore.f.a.a("northeast", this.b) });
  }
  
  public static final class a {
    private double a = Double.MAX_VALUE;
    
    private double b = -1.7976931348623157E308D;
    
    private double c = Double.MAX_VALUE;
    
    private double d = -1.7976931348623157E308D;
    
    public final a a(c param1c) {
      this.a = Math.min(this.a, param1c.a());
      this.b = Math.max(this.b, param1c.a());
      this.d = Math.max(this.d, param1c.b());
      this.c = Math.min(this.c, param1c.b());
      return this;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\b\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */