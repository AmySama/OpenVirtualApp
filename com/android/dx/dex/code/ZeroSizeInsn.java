package com.android.dx.dex.code;

import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.util.AnnotatedOutput;

public abstract class ZeroSizeInsn extends DalvInsn {
  public ZeroSizeInsn(SourcePosition paramSourcePosition) {
    super(Dops.SPECIAL_FORMAT, paramSourcePosition, RegisterSpecList.EMPTY);
  }
  
  public final int codeSize() {
    return 0;
  }
  
  public final DalvInsn withOpcode(Dop paramDop) {
    throw new RuntimeException("unsupported");
  }
  
  public DalvInsn withRegisterOffset(int paramInt) {
    return withRegisters(getRegisters().withOffset(paramInt));
  }
  
  public final void writeTo(AnnotatedOutput paramAnnotatedOutput) {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\ZeroSizeInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */