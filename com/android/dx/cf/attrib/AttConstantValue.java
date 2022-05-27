package com.android.dx.cf.attrib;

import com.android.dx.rop.cst.TypedConstant;

public final class AttConstantValue extends BaseAttribute {
  public static final String ATTRIBUTE_NAME = "ConstantValue";
  
  private final TypedConstant constantValue;
  
  public AttConstantValue(TypedConstant paramTypedConstant) {
    super("ConstantValue");
    if (!(paramTypedConstant instanceof com.android.dx.rop.cst.CstString) && !(paramTypedConstant instanceof com.android.dx.rop.cst.CstInteger) && !(paramTypedConstant instanceof com.android.dx.rop.cst.CstLong) && !(paramTypedConstant instanceof com.android.dx.rop.cst.CstFloat) && !(paramTypedConstant instanceof com.android.dx.rop.cst.CstDouble)) {
      if (paramTypedConstant == null)
        throw new NullPointerException("constantValue == null"); 
      throw new IllegalArgumentException("bad type for constantValue");
    } 
    this.constantValue = paramTypedConstant;
  }
  
  public int byteLength() {
    return 8;
  }
  
  public TypedConstant getConstantValue() {
    return this.constantValue;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\AttConstantValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */