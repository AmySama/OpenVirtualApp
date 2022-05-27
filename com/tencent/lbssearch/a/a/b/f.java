package com.tencent.lbssearch.a.a.b;

import java.math.BigInteger;

public final class f extends Number {
  private final String a;
  
  public f(String paramString) {
    this.a = paramString;
  }
  
  public double doubleValue() {
    return Double.parseDouble(this.a);
  }
  
  public float floatValue() {
    return Float.parseFloat(this.a);
  }
  
  public int intValue() {
    try {
      return Integer.parseInt(this.a);
    } catch (NumberFormatException numberFormatException) {
      try {
        long l = Long.parseLong(this.a);
        return (int)l;
      } catch (NumberFormatException numberFormatException1) {
        return (new BigInteger(this.a)).intValue();
      } 
    } 
  }
  
  public long longValue() {
    try {
      return Long.parseLong(this.a);
    } catch (NumberFormatException numberFormatException) {
      return (new BigInteger(this.a)).longValue();
    } 
  }
  
  public String toString() {
    return this.a;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */