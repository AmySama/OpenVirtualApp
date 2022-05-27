package com.tencent.lbssearch.a.a;

import com.tencent.lbssearch.a.a.b.a;
import com.tencent.lbssearch.a.a.b.f;

public final class q extends l {
  private static final Class<?>[] a = new Class[] { 
      int.class, long.class, short.class, float.class, double.class, byte.class, boolean.class, char.class, Integer.class, Long.class, 
      Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class };
  
  private Object b;
  
  public q(Boolean paramBoolean) {
    a(paramBoolean);
  }
  
  public q(Number paramNumber) {
    a(paramNumber);
  }
  
  public q(String paramString) {
    a(paramString);
  }
  
  private static boolean a(q paramq) {
    Object object = paramq.b;
    boolean bool = object instanceof Number;
    boolean bool1 = false;
    null = bool1;
    if (bool) {
      object = object;
      if (!(object instanceof java.math.BigInteger) && !(object instanceof Long) && !(object instanceof Integer) && !(object instanceof Short)) {
        null = bool1;
        return (object instanceof Byte) ? true : null;
      } 
    } else {
      return null;
    } 
    return true;
  }
  
  private static boolean b(Object paramObject) {
    if (paramObject instanceof String)
      return true; 
    Class<?> clazz = paramObject.getClass();
    paramObject = a;
    int i = paramObject.length;
    for (byte b = 0; b < i; b++) {
      if (paramObject[b].isAssignableFrom(clazz))
        return true; 
    } 
    return false;
  }
  
  void a(Object paramObject) {
    if (paramObject instanceof Character) {
      this.b = String.valueOf(((Character)paramObject).charValue());
    } else {
      boolean bool;
      if (paramObject instanceof Number || b(paramObject)) {
        bool = true;
      } else {
        bool = false;
      } 
      a.a(bool);
      this.b = paramObject;
    } 
  }
  
  public boolean a() {
    return this.b instanceof Boolean;
  }
  
  public Number b() {
    Object object = this.b;
    if (object instanceof String) {
      object = new f((String)this.b);
    } else {
      object = object;
    } 
    return (Number)object;
  }
  
  public String c() {
    return q() ? b().toString() : (a() ? p().toString() : (String)this.b);
  }
  
  public double d() {
    double d;
    if (q()) {
      d = b().doubleValue();
    } else {
      d = Double.parseDouble(c());
    } 
    return d;
  }
  
  public float e() {
    float f;
    if (q()) {
      f = b().floatValue();
    } else {
      f = Float.parseFloat(c());
    } 
    return f;
  }
  
  public boolean equals(Object paramObject) {
    boolean bool1 = true;
    boolean bool2 = true;
    boolean bool3 = true;
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    paramObject = paramObject;
    if (this.b == null) {
      if (((q)paramObject).b != null)
        bool3 = false; 
      return bool3;
    } 
    if (a(this) && a((q)paramObject)) {
      if (b().longValue() == paramObject.b().longValue()) {
        bool3 = bool1;
      } else {
        bool3 = false;
      } 
      return bool3;
    } 
    if (this.b instanceof Number && ((q)paramObject).b instanceof Number) {
      double d1 = b().doubleValue();
      double d2 = paramObject.b().doubleValue();
      bool3 = bool2;
      if (d1 != d2)
        if (Double.isNaN(d1) && Double.isNaN(d2)) {
          bool3 = bool2;
        } else {
          bool3 = false;
        }  
      return bool3;
    } 
    return this.b.equals(((q)paramObject).b);
  }
  
  public long f() {
    long l1;
    if (q()) {
      l1 = b().longValue();
    } else {
      l1 = Long.parseLong(c());
    } 
    return l1;
  }
  
  public int g() {
    int i;
    if (q()) {
      i = b().intValue();
    } else {
      i = Integer.parseInt(c());
    } 
    return i;
  }
  
  public boolean h() {
    return a() ? p().booleanValue() : Boolean.parseBoolean(c());
  }
  
  public int hashCode() {
    if (this.b == null)
      return 31; 
    if (a(this)) {
      long l1 = b().longValue();
      return (int)(l1 >>> 32L ^ l1);
    } 
    Object object = this.b;
    if (object instanceof Number) {
      long l1 = Double.doubleToLongBits(b().doubleValue());
      return (int)(l1 >>> 32L ^ l1);
    } 
    return object.hashCode();
  }
  
  Boolean p() {
    return (Boolean)this.b;
  }
  
  public boolean q() {
    return this.b instanceof Number;
  }
  
  public boolean r() {
    return this.b instanceof String;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\q.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */