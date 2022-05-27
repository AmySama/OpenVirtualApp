package com.android.dx.rop.code;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;

public final class ThrowingCstInsn extends CstInsn {
  private final TypeList catches;
  
  public ThrowingCstInsn(Rop paramRop, SourcePosition paramSourcePosition, RegisterSpecList paramRegisterSpecList, TypeList paramTypeList, Constant paramConstant) {
    super(paramRop, paramSourcePosition, (RegisterSpec)null, paramRegisterSpecList, paramConstant);
    if (paramRop.getBranchingness() == 6) {
      if (paramTypeList != null) {
        this.catches = paramTypeList;
        return;
      } 
      throw new NullPointerException("catches == null");
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("opcode with invalid branchingness: ");
    stringBuilder.append(paramRop.getBranchingness());
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void accept(Insn.Visitor paramVisitor) {
    paramVisitor.visitThrowingCstInsn(this);
  }
  
  public TypeList getCatches() {
    return this.catches;
  }
  
  public String getInlineString() {
    Constant constant = getConstant();
    String str = constant.toHuman();
    if (constant instanceof CstString)
      str = ((CstString)constant).toQuoted(); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append(" ");
    stringBuilder.append(ThrowingInsn.toCatchString(this.catches));
    return stringBuilder.toString();
  }
  
  public Insn withAddedCatch(Type paramType) {
    return new ThrowingCstInsn(getOpcode(), getPosition(), getSources(), this.catches.withAddedType(paramType), getConstant());
  }
  
  public Insn withNewRegisters(RegisterSpec paramRegisterSpec, RegisterSpecList paramRegisterSpecList) {
    return new ThrowingCstInsn(getOpcode(), getPosition(), paramRegisterSpecList, this.catches, getConstant());
  }
  
  public Insn withRegisterOffset(int paramInt) {
    return new ThrowingCstInsn(getOpcode(), getPosition(), getSources().withOffset(paramInt), this.catches, getConstant());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\ThrowingCstInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */