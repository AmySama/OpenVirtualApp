package com.android.dx.rop.cst;

import com.android.dx.rop.type.Type;

public final class CstKnownNull extends CstLiteralBits {
  public static final CstKnownNull THE_ONE = new CstKnownNull();
  
  protected int compareTo0(Constant paramConstant) {
    return 0;
  }
  
  public boolean equals(Object paramObject) {
    return paramObject instanceof CstKnownNull;
  }
  
  public boolean fitsInInt() {
    return true;
  }
  
  public int getIntBits() {
    return 0;
  }
  
  public long getLongBits() {
    return 0L;
  }
  
  public Type getType() {
    return Type.KNOWN_NULL;
  }
  
  public int hashCode() {
    return 1147565434;
  }
  
  public boolean isCategory2() {
    return false;
  }
  
  public String toHuman() {
    return "null";
  }
  
  public String toString() {
    return "known-null";
  }
  
  public String typeName() {
    return "known-null";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstKnownNull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */