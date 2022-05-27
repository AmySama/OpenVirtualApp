package com.android.dx.rop.cst;

import com.android.dx.rop.type.Type;
import com.android.dx.util.Hex;

public final class CstDouble extends CstLiteral64 {
  public static final CstDouble VALUE_0 = new CstDouble(Double.doubleToLongBits(0.0D));
  
  public static final CstDouble VALUE_1 = new CstDouble(Double.doubleToLongBits(1.0D));
  
  private CstDouble(long paramLong) {
    super(paramLong);
  }
  
  public static CstDouble make(long paramLong) {
    return new CstDouble(paramLong);
  }
  
  public Type getType() {
    return Type.DOUBLE;
  }
  
  public double getValue() {
    return Double.longBitsToDouble(getLongBits());
  }
  
  public String toHuman() {
    return Double.toString(Double.longBitsToDouble(getLongBits()));
  }
  
  public String toString() {
    long l = getLongBits();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("double{0x");
    stringBuilder.append(Hex.u8(l));
    stringBuilder.append(" / ");
    stringBuilder.append(Double.longBitsToDouble(l));
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public String typeName() {
    return "double";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstDouble.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */