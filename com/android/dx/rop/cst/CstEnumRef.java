package com.android.dx.rop.cst;

import com.android.dx.rop.type.Type;

public final class CstEnumRef extends CstMemberRef {
  private CstFieldRef fieldRef = null;
  
  public CstEnumRef(CstNat paramCstNat) {
    super(new CstType(paramCstNat.getFieldType()), paramCstNat);
  }
  
  public CstFieldRef getFieldRef() {
    if (this.fieldRef == null)
      this.fieldRef = new CstFieldRef(getDefiningClass(), getNat()); 
    return this.fieldRef;
  }
  
  public Type getType() {
    return getDefiningClass().getClassType();
  }
  
  public String typeName() {
    return "enum";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstEnumRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */