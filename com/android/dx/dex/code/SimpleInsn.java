package com.android.dx.dex.code;

import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.SourcePosition;

public final class SimpleInsn extends FixedSizeInsn {
  public SimpleInsn(Dop paramDop, SourcePosition paramSourcePosition, RegisterSpecList paramRegisterSpecList) {
    super(paramDop, paramSourcePosition, paramRegisterSpecList);
  }
  
  protected String argString() {
    return null;
  }
  
  public DalvInsn withOpcode(Dop paramDop) {
    return new SimpleInsn(paramDop, getPosition(), getRegisters());
  }
  
  public DalvInsn withRegisters(RegisterSpecList paramRegisterSpecList) {
    return new SimpleInsn(getOpcode(), getPosition(), paramRegisterSpecList);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\SimpleInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */