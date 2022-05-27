package com.android.dx.rop.cst;

import com.android.dx.rop.type.Type;
import com.android.dx.util.Hex;

public final class CstShort extends CstLiteral32 {
  public static final CstShort VALUE_0 = make((short)0);
  
  private CstShort(short paramShort) {
    super(paramShort);
  }
  
  public static CstShort make(int paramInt) {
    short s = (short)paramInt;
    if (s == paramInt)
      return make(s); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("bogus short value: ");
    stringBuilder.append(paramInt);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public static CstShort make(short paramShort) {
    return new CstShort(paramShort);
  }
  
  public Type getType() {
    return Type.SHORT;
  }
  
  public short getValue() {
    return (short)getIntBits();
  }
  
  public String toHuman() {
    return Integer.toString(getIntBits());
  }
  
  public String toString() {
    int i = getIntBits();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("short{0x");
    stringBuilder.append(Hex.u2(i));
    stringBuilder.append(" / ");
    stringBuilder.append(i);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public String typeName() {
    return "short";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstShort.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */