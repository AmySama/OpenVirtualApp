package com.android.dx.rop.code;

import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeList;
import com.android.dx.util.IntList;

public final class SwitchInsn extends Insn {
  private final IntList cases;
  
  public SwitchInsn(Rop paramRop, SourcePosition paramSourcePosition, RegisterSpec paramRegisterSpec, RegisterSpecList paramRegisterSpecList, IntList paramIntList) {
    super(paramRop, paramSourcePosition, paramRegisterSpec, paramRegisterSpecList);
    if (paramRop.getBranchingness() == 5) {
      if (paramIntList != null) {
        this.cases = paramIntList;
        return;
      } 
      throw new NullPointerException("cases == null");
    } 
    throw new IllegalArgumentException("bogus branchingness");
  }
  
  public void accept(Insn.Visitor paramVisitor) {
    paramVisitor.visitSwitchInsn(this);
  }
  
  public boolean contentEquals(Insn paramInsn) {
    return false;
  }
  
  public IntList getCases() {
    return this.cases;
  }
  
  public TypeList getCatches() {
    return (TypeList)StdTypeList.EMPTY;
  }
  
  public String getInlineString() {
    return this.cases.toString();
  }
  
  public Insn withAddedCatch(Type paramType) {
    throw new UnsupportedOperationException("unsupported");
  }
  
  public Insn withNewRegisters(RegisterSpec paramRegisterSpec, RegisterSpecList paramRegisterSpecList) {
    return new SwitchInsn(getOpcode(), getPosition(), paramRegisterSpec, paramRegisterSpecList, this.cases);
  }
  
  public Insn withRegisterOffset(int paramInt) {
    return new SwitchInsn(getOpcode(), getPosition(), getResult().withOffset(paramInt), getSources().withOffset(paramInt), this.cases);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\SwitchInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */