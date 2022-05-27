package com.android.dx.rop.cst;

import com.android.dx.rop.type.Type;
import com.android.dx.util.Hex;

public final class CstByte extends CstLiteral32 {
  public static final CstByte VALUE_0 = make((byte)0);
  
  private CstByte(byte paramByte) {
    super(paramByte);
  }
  
  public static CstByte make(byte paramByte) {
    return new CstByte(paramByte);
  }
  
  public static CstByte make(int paramInt) {
    byte b = (byte)paramInt;
    if (b == paramInt)
      return make(b); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("bogus byte value: ");
    stringBuilder.append(paramInt);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public Type getType() {
    return Type.BYTE;
  }
  
  public byte getValue() {
    return (byte)getIntBits();
  }
  
  public String toHuman() {
    return Integer.toString(getIntBits());
  }
  
  public String toString() {
    int i = getIntBits();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("byte{0x");
    stringBuilder.append(Hex.u1(i));
    stringBuilder.append(" / ");
    stringBuilder.append(i);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public String typeName() {
    return "byte";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstByte.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */