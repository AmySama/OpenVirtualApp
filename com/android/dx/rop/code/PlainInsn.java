package com.android.dx.rop.code;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.rop.type.TypeList;

public final class PlainInsn extends Insn {
  public PlainInsn(Rop paramRop, SourcePosition paramSourcePosition, RegisterSpec paramRegisterSpec1, RegisterSpec paramRegisterSpec2) {
    this(paramRop, paramSourcePosition, paramRegisterSpec1, RegisterSpecList.make(paramRegisterSpec2));
  }
  
  public PlainInsn(Rop paramRop, SourcePosition paramSourcePosition, RegisterSpec paramRegisterSpec, RegisterSpecList paramRegisterSpecList) {
    super(paramRop, paramSourcePosition, paramRegisterSpec, paramRegisterSpecList);
    int i = paramRop.getBranchingness();
    if (i != 5 && i != 6) {
      if (paramRegisterSpec == null || paramRop.getBranchingness() == 1)
        return; 
      throw new IllegalArgumentException("can't mix branchingness with result");
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("opcode with invalid branchingness: ");
    stringBuilder.append(paramRop.getBranchingness());
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void accept(Insn.Visitor paramVisitor) {
    paramVisitor.visitPlainInsn(this);
  }
  
  public TypeList getCatches() {
    return (TypeList)StdTypeList.EMPTY;
  }
  
  public Insn withAddedCatch(Type paramType) {
    throw new UnsupportedOperationException("unsupported");
  }
  
  public Insn withNewRegisters(RegisterSpec paramRegisterSpec, RegisterSpecList paramRegisterSpecList) {
    return new PlainInsn(getOpcode(), getPosition(), paramRegisterSpec, paramRegisterSpecList);
  }
  
  public Insn withRegisterOffset(int paramInt) {
    return new PlainInsn(getOpcode(), getPosition(), getResult().withOffset(paramInt), getSources().withOffset(paramInt));
  }
  
  public Insn withSourceLiteral() {
    RegisterSpecList registerSpecList1 = getSources();
    int i = registerSpecList1.size();
    if (i == 0)
      return this; 
    TypeBearer typeBearer = registerSpecList1.get(i - 1).getTypeBearer();
    if (!typeBearer.isConstant()) {
      typeBearer = registerSpecList1.get(0).getTypeBearer();
      if (i == 2 && typeBearer.isConstant()) {
        constant = (Constant)typeBearer;
        registerSpecList1 = registerSpecList1.withoutFirst();
        return new PlainCstInsn(Rops.ropFor(getOpcode().getOpcode(), getResult(), registerSpecList1, constant), getPosition(), getResult(), registerSpecList1, constant);
      } 
      return this;
    } 
    Constant constant = constant;
    RegisterSpecList registerSpecList2 = registerSpecList1.withoutLast();
    try {
      CstInteger cstInteger;
      int j = getOpcode().getOpcode();
      i = j;
      Constant constant1 = constant;
      if (j == 15) {
        i = j;
        constant1 = constant;
        if (constant instanceof CstInteger) {
          i = 14;
          cstInteger = CstInteger.make(-((CstInteger)constant).getValue());
        } 
      } 
      Rop rop = Rops.ropFor(i, getResult(), registerSpecList2, (Constant)cstInteger);
      return new PlainCstInsn(rop, getPosition(), getResult(), registerSpecList2, (Constant)cstInteger);
    } catch (IllegalArgumentException illegalArgumentException) {
      return this;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\PlainInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */