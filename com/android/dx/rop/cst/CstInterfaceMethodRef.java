package com.android.dx.rop.cst;

public final class CstInterfaceMethodRef extends CstBaseMethodRef {
  private CstMethodRef methodRef = null;
  
  public CstInterfaceMethodRef(CstType paramCstType, CstNat paramCstNat) {
    super(paramCstType, paramCstNat);
  }
  
  public CstMethodRef toMethodRef() {
    if (this.methodRef == null)
      this.methodRef = new CstMethodRef(getDefiningClass(), getNat()); 
    return this.methodRef;
  }
  
  public String typeName() {
    return "ifaceMethod";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstInterfaceMethodRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */