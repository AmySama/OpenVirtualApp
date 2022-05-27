package com.android.dx.rop.cst;

import com.android.dx.rop.type.Type;
import com.android.dx.util.Hex;

public final class CstLong extends CstLiteral64 {
  public static final CstLong VALUE_0 = make(0L);
  
  public static final CstLong VALUE_1 = make(1L);
  
  private CstLong(long paramLong) {
    super(paramLong);
  }
  
  public static CstLong make(long paramLong) {
    return new CstLong(paramLong);
  }
  
  public Type getType() {
    return Type.LONG;
  }
  
  public long getValue() {
    return getLongBits();
  }
  
  public String toHuman() {
    return Long.toString(getLongBits());
  }
  
  public String toString() {
    long l = getLongBits();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("long{0x");
    stringBuilder.append(Hex.u8(l));
    stringBuilder.append(" / ");
    stringBuilder.append(l);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public String typeName() {
    return "long";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstLong.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */