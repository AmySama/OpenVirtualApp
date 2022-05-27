package com.android.dx.rop.code;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;
import java.util.ArrayList;

public final class FillArrayDataInsn extends Insn {
  private final Constant arrayType;
  
  private final ArrayList<Constant> initValues;
  
  public FillArrayDataInsn(Rop paramRop, SourcePosition paramSourcePosition, RegisterSpecList paramRegisterSpecList, ArrayList<Constant> paramArrayList, Constant paramConstant) {
    super(paramRop, paramSourcePosition, null, paramRegisterSpecList);
    if (paramRop.getBranchingness() == 1) {
      this.initValues = paramArrayList;
      this.arrayType = paramConstant;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("opcode with invalid branchingness: ");
    stringBuilder.append(paramRop.getBranchingness());
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void accept(Insn.Visitor paramVisitor) {
    paramVisitor.visitFillArrayDataInsn(this);
  }
  
  public TypeList getCatches() {
    return (TypeList)StdTypeList.EMPTY;
  }
  
  public Constant getConstant() {
    return this.arrayType;
  }
  
  public ArrayList<Constant> getInitValues() {
    return this.initValues;
  }
  
  public Insn withAddedCatch(Type paramType) {
    throw new UnsupportedOperationException("unsupported");
  }
  
  public Insn withNewRegisters(RegisterSpec paramRegisterSpec, RegisterSpecList paramRegisterSpecList) {
    return new FillArrayDataInsn(getOpcode(), getPosition(), paramRegisterSpecList, this.initValues, this.arrayType);
  }
  
  public Insn withRegisterOffset(int paramInt) {
    return new FillArrayDataInsn(getOpcode(), getPosition(), getSources().withOffset(paramInt), this.initValues, this.arrayType);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\FillArrayDataInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */