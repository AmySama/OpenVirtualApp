package com.android.dx.dex.code;

import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.SourcePosition;

public final class TargetInsn extends FixedSizeInsn {
  private CodeAddress target;
  
  public TargetInsn(Dop paramDop, SourcePosition paramSourcePosition, RegisterSpecList paramRegisterSpecList, CodeAddress paramCodeAddress) {
    super(paramDop, paramSourcePosition, paramRegisterSpecList);
    if (paramCodeAddress != null) {
      this.target = paramCodeAddress;
      return;
    } 
    throw new NullPointerException("target == null");
  }
  
  protected String argString() {
    CodeAddress codeAddress = this.target;
    return (codeAddress == null) ? "????" : codeAddress.identifierString();
  }
  
  public CodeAddress getTarget() {
    return this.target;
  }
  
  public int getTargetAddress() {
    return this.target.getAddress();
  }
  
  public int getTargetOffset() {
    return this.target.getAddress() - getAddress();
  }
  
  public boolean hasTargetOffset() {
    boolean bool;
    if (hasAddress() && this.target.hasAddress()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public TargetInsn withNewTargetAndReversed(CodeAddress paramCodeAddress) {
    return new TargetInsn(getOpcode().getOppositeTest(), getPosition(), getRegisters(), paramCodeAddress);
  }
  
  public DalvInsn withOpcode(Dop paramDop) {
    return new TargetInsn(paramDop, getPosition(), getRegisters(), this.target);
  }
  
  public DalvInsn withRegisters(RegisterSpecList paramRegisterSpecList) {
    return new TargetInsn(getOpcode(), getPosition(), paramRegisterSpecList, this.target);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\TargetInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */