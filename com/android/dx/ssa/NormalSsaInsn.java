package com.android.dx.ssa;

import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.Rop;

public final class NormalSsaInsn extends SsaInsn implements Cloneable {
  private Insn insn;
  
  NormalSsaInsn(Insn paramInsn, SsaBasicBlock paramSsaBasicBlock) {
    super(paramInsn.getResult(), paramSsaBasicBlock);
    this.insn = paramInsn;
  }
  
  public void accept(SsaInsn.Visitor paramVisitor) {
    if (isNormalMoveInsn()) {
      paramVisitor.visitMoveInsn(this);
    } else {
      paramVisitor.visitNonMoveInsn(this);
    } 
  }
  
  public boolean canThrow() {
    return this.insn.canThrow();
  }
  
  public final void changeOneSource(int paramInt, RegisterSpec paramRegisterSpec) {
    RegisterSpecList registerSpecList1 = this.insn.getSources();
    int i = registerSpecList1.size();
    RegisterSpecList registerSpecList2 = new RegisterSpecList(i);
    for (byte b = 0; b < i; b++) {
      RegisterSpec registerSpec1;
      if (b == paramInt) {
        registerSpec1 = paramRegisterSpec;
      } else {
        registerSpec1 = registerSpecList1.get(b);
      } 
      registerSpecList2.set(b, registerSpec1);
    } 
    registerSpecList2.setImmutable();
    RegisterSpec registerSpec = registerSpecList1.get(paramInt);
    if (registerSpec.getReg() != paramRegisterSpec.getReg())
      getBlock().getParent().onSourceChanged(this, registerSpec, paramRegisterSpec); 
    this.insn = this.insn.withNewRegisters(getResult(), registerSpecList2);
  }
  
  public NormalSsaInsn clone() {
    return (NormalSsaInsn)super.clone();
  }
  
  public RegisterSpec getLocalAssignment() {
    RegisterSpec registerSpec;
    if (this.insn.getOpcode().getOpcode() == 54) {
      registerSpec = this.insn.getSources().get(0);
    } else {
      registerSpec = getResult();
    } 
    return (registerSpec == null) ? null : ((registerSpec.getLocalItem() == null) ? null : registerSpec);
  }
  
  public Rop getOpcode() {
    return this.insn.getOpcode();
  }
  
  public Insn getOriginalRopInsn() {
    return this.insn;
  }
  
  public RegisterSpecList getSources() {
    return this.insn.getSources();
  }
  
  public boolean hasSideEffect() {
    boolean bool;
    Rop rop = getOpcode();
    if (rop.getBranchingness() != 1)
      return true; 
    if (Optimizer.getPreserveLocals() && getLocalAssignment() != null) {
      bool = true;
    } else {
      bool = false;
    } 
    int i = rop.getOpcode();
    return (i != 2 && i != 5 && i != 55) ? true : bool;
  }
  
  public boolean isMoveException() {
    boolean bool;
    if (this.insn.getOpcode().getOpcode() == 4) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isNormalMoveInsn() {
    boolean bool;
    if (this.insn.getOpcode().getOpcode() == 2) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isPhiOrMove() {
    return isNormalMoveInsn();
  }
  
  public final void mapSourceRegisters(RegisterMapper paramRegisterMapper) {
    RegisterSpecList registerSpecList2 = this.insn.getSources();
    RegisterSpecList registerSpecList1 = paramRegisterMapper.map(registerSpecList2);
    if (registerSpecList1 != registerSpecList2) {
      this.insn = this.insn.withNewRegisters(getResult(), registerSpecList1);
      getBlock().getParent().onSourcesChanged(this, registerSpecList2);
    } 
  }
  
  public final void setNewSources(RegisterSpecList paramRegisterSpecList) {
    if (this.insn.getSources().size() == paramRegisterSpecList.size()) {
      this.insn = this.insn.withNewRegisters(getResult(), paramRegisterSpecList);
      return;
    } 
    throw new RuntimeException("Sources counts don't match");
  }
  
  public String toHuman() {
    return toRopInsn().toHuman();
  }
  
  public Insn toRopInsn() {
    return this.insn.withNewRegisters(getResult(), this.insn.getSources());
  }
  
  public void upgradeToLiteral() {
    RegisterSpecList registerSpecList = this.insn.getSources();
    this.insn = this.insn.withSourceLiteral();
    getBlock().getParent().onSourcesChanged(this, registerSpecList);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\NormalSsaInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */