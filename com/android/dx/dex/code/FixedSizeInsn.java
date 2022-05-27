package com.android.dx.dex.code;

import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.util.AnnotatedOutput;

public abstract class FixedSizeInsn extends DalvInsn {
  public FixedSizeInsn(Dop paramDop, SourcePosition paramSourcePosition, RegisterSpecList paramRegisterSpecList) {
    super(paramDop, paramSourcePosition, paramRegisterSpecList);
  }
  
  public final int codeSize() {
    return getOpcode().getFormat().codeSize();
  }
  
  protected final String listingString0(boolean paramBoolean) {
    return getOpcode().getFormat().listingString(this, paramBoolean);
  }
  
  public final DalvInsn withRegisterOffset(int paramInt) {
    return withRegisters(getRegisters().withOffset(paramInt));
  }
  
  public final void writeTo(AnnotatedOutput paramAnnotatedOutput) {
    getOpcode().getFormat().writeTo(paramAnnotatedOutput, this);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\FixedSizeInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */