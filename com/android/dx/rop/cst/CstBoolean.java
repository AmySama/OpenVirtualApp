package com.android.dx.rop.cst;

import com.android.dx.rop.type.Type;

public final class CstBoolean extends CstLiteral32 {
  public static final CstBoolean VALUE_FALSE = new CstBoolean(false);
  
  public static final CstBoolean VALUE_TRUE = new CstBoolean(true);
  
  private CstBoolean(boolean paramBoolean) {
    super(paramBoolean);
  }
  
  public static CstBoolean make(int paramInt) {
    if (paramInt == 0)
      return VALUE_FALSE; 
    if (paramInt == 1)
      return VALUE_TRUE; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("bogus value: ");
    stringBuilder.append(paramInt);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public static CstBoolean make(boolean paramBoolean) {
    CstBoolean cstBoolean;
    if (paramBoolean) {
      cstBoolean = VALUE_TRUE;
    } else {
      cstBoolean = VALUE_FALSE;
    } 
    return cstBoolean;
  }
  
  public Type getType() {
    return Type.BOOLEAN;
  }
  
  public boolean getValue() {
    boolean bool;
    if (getIntBits() == 0) {
      bool = false;
    } else {
      bool = true;
    } 
    return bool;
  }
  
  public String toHuman() {
    String str;
    if (getValue()) {
      str = "true";
    } else {
      str = "false";
    } 
    return str;
  }
  
  public String toString() {
    String str;
    if (getValue()) {
      str = "boolean{true}";
    } else {
      str = "boolean{false}";
    } 
    return str;
  }
  
  public String typeName() {
    return "boolean";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstBoolean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */