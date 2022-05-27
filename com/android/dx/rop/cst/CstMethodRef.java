package com.android.dx.rop.cst;

public final class CstMethodRef extends CstBaseMethodRef {
  public CstMethodRef(CstType paramCstType, CstNat paramCstNat) {
    super(paramCstType, paramCstNat);
  }
  
  public String typeName() {
    return "method";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstMethodRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */