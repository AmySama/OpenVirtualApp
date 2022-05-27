package com.android.dx.rop.cst;

import com.android.dx.rop.type.Type;
import com.android.dx.util.Hex;

public final class CstInteger extends CstLiteral32 {
  public static final CstInteger VALUE_0;
  
  public static final CstInteger VALUE_1;
  
  public static final CstInteger VALUE_2;
  
  public static final CstInteger VALUE_3;
  
  public static final CstInteger VALUE_4;
  
  public static final CstInteger VALUE_5;
  
  public static final CstInteger VALUE_M1;
  
  private static final CstInteger[] cache = new CstInteger[511];
  
  static {
    VALUE_M1 = make(-1);
    VALUE_0 = make(0);
    VALUE_1 = make(1);
    VALUE_2 = make(2);
    VALUE_3 = make(3);
    VALUE_4 = make(4);
    VALUE_5 = make(5);
  }
  
  private CstInteger(int paramInt) {
    super(paramInt);
  }
  
  public static CstInteger make(int paramInt) {
    CstInteger[] arrayOfCstInteger = cache;
    int i = (Integer.MAX_VALUE & paramInt) % arrayOfCstInteger.length;
    CstInteger cstInteger = arrayOfCstInteger[i];
    if (cstInteger != null && cstInteger.getValue() == paramInt)
      return cstInteger; 
    cstInteger = new CstInteger(paramInt);
    cache[i] = cstInteger;
    return cstInteger;
  }
  
  public Type getType() {
    return Type.INT;
  }
  
  public int getValue() {
    return getIntBits();
  }
  
  public String toHuman() {
    return Integer.toString(getIntBits());
  }
  
  public String toString() {
    int i = getIntBits();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("int{0x");
    stringBuilder.append(Hex.u4(i));
    stringBuilder.append(" / ");
    stringBuilder.append(i);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public String typeName() {
    return "int";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstInteger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */