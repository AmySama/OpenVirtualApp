package com.android.dx.rop.cst;

import com.android.dx.rop.type.Type;
import com.android.dx.util.Hex;

public final class CstFloat extends CstLiteral32 {
  public static final CstFloat VALUE_0 = make(Float.floatToIntBits(0.0F));
  
  public static final CstFloat VALUE_1 = make(Float.floatToIntBits(1.0F));
  
  public static final CstFloat VALUE_2 = make(Float.floatToIntBits(2.0F));
  
  private CstFloat(int paramInt) {
    super(paramInt);
  }
  
  public static CstFloat make(int paramInt) {
    return new CstFloat(paramInt);
  }
  
  public Type getType() {
    return Type.FLOAT;
  }
  
  public float getValue() {
    return Float.intBitsToFloat(getIntBits());
  }
  
  public String toHuman() {
    return Float.toString(Float.intBitsToFloat(getIntBits()));
  }
  
  public String toString() {
    int i = getIntBits();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("float{0x");
    stringBuilder.append(Hex.u4(i));
    stringBuilder.append(" / ");
    stringBuilder.append(Float.intBitsToFloat(i));
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public String typeName() {
    return "float";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstFloat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */