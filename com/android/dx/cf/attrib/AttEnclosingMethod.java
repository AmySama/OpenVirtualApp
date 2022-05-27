package com.android.dx.cf.attrib;

import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstType;

public final class AttEnclosingMethod extends BaseAttribute {
  public static final String ATTRIBUTE_NAME = "EnclosingMethod";
  
  private final CstNat method;
  
  private final CstType type;
  
  public AttEnclosingMethod(CstType paramCstType, CstNat paramCstNat) {
    super("EnclosingMethod");
    if (paramCstType != null) {
      this.type = paramCstType;
      this.method = paramCstNat;
      return;
    } 
    throw new NullPointerException("type == null");
  }
  
  public int byteLength() {
    return 10;
  }
  
  public CstType getEnclosingClass() {
    return this.type;
  }
  
  public CstNat getMethod() {
    return this.method;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\AttEnclosingMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */