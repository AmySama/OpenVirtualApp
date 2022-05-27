package com.android.dx.rop.code;

import com.android.dx.rop.cst.Constant;

public abstract class CstInsn extends Insn {
  private final Constant cst;
  
  public CstInsn(Rop paramRop, SourcePosition paramSourcePosition, RegisterSpec paramRegisterSpec, RegisterSpecList paramRegisterSpecList, Constant paramConstant) {
    super(paramRop, paramSourcePosition, paramRegisterSpec, paramRegisterSpecList);
    if (paramConstant != null) {
      this.cst = paramConstant;
      return;
    } 
    throw new NullPointerException("cst == null");
  }
  
  public boolean contentEquals(Insn paramInsn) {
    boolean bool;
    if (super.contentEquals(paramInsn) && this.cst.equals(((CstInsn)paramInsn).getConstant())) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public Constant getConstant() {
    return this.cst;
  }
  
  public String getInlineString() {
    return this.cst.toHuman();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\CstInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */