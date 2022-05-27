package com.android.dx.rop.code;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;

public final class PlainCstInsn extends CstInsn {
  public PlainCstInsn(Rop paramRop, SourcePosition paramSourcePosition, RegisterSpec paramRegisterSpec, RegisterSpecList paramRegisterSpecList, Constant paramConstant) {
    super(paramRop, paramSourcePosition, paramRegisterSpec, paramRegisterSpecList, paramConstant);
    if (paramRop.getBranchingness() == 1)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("opcode with invalid branchingness: ");
    stringBuilder.append(paramRop.getBranchingness());
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void accept(Insn.Visitor paramVisitor) {
    paramVisitor.visitPlainCstInsn(this);
  }
  
  public TypeList getCatches() {
    return (TypeList)StdTypeList.EMPTY;
  }
  
  public Insn withAddedCatch(Type paramType) {
    throw new UnsupportedOperationException("unsupported");
  }
  
  public Insn withNewRegisters(RegisterSpec paramRegisterSpec, RegisterSpecList paramRegisterSpecList) {
    return new PlainCstInsn(getOpcode(), getPosition(), paramRegisterSpec, paramRegisterSpecList, getConstant());
  }
  
  public Insn withRegisterOffset(int paramInt) {
    return new PlainCstInsn(getOpcode(), getPosition(), getResult().withOffset(paramInt), getSources().withOffset(paramInt), getConstant());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\PlainCstInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */