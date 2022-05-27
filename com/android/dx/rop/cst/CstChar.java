package com.android.dx.rop.cst;

import com.android.dx.rop.type.Type;
import com.android.dx.util.Hex;

public final class CstChar extends CstLiteral32 {
  public static final CstChar VALUE_0 = make(false);
  
  private CstChar(char paramChar) {
    super(paramChar);
  }
  
  public static CstChar make(char paramChar) {
    return new CstChar(paramChar);
  }
  
  public static CstChar make(int paramInt) {
    char c = (char)paramInt;
    if (c == paramInt)
      return make(c); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("bogus char value: ");
    stringBuilder.append(paramInt);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public Type getType() {
    return Type.CHAR;
  }
  
  public char getValue() {
    return (char)getIntBits();
  }
  
  public String toHuman() {
    return Integer.toString(getIntBits());
  }
  
  public String toString() {
    int i = getIntBits();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("char{0x");
    stringBuilder.append(Hex.u2(i));
    stringBuilder.append(" / ");
    stringBuilder.append(i);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public String typeName() {
    return "char";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstChar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */