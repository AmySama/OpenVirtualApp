package com.android.dx.dex.code;

import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.SourcePosition;

public abstract class VariableSizeInsn extends DalvInsn {
  public VariableSizeInsn(SourcePosition paramSourcePosition, RegisterSpecList paramRegisterSpecList) {
    super(Dops.SPECIAL_FORMAT, paramSourcePosition, paramRegisterSpecList);
  }
  
  public final DalvInsn withOpcode(Dop paramDop) {
    throw new RuntimeException("unsupported");
  }
  
  public final DalvInsn withRegisterOffset(int paramInt) {
    return withRegisters(getRegisters().withOffset(paramInt));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\VariableSizeInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */