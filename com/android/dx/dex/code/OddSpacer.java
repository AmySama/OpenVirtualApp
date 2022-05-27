package com.android.dx.dex.code;

import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.util.AnnotatedOutput;

public final class OddSpacer extends VariableSizeInsn {
  public OddSpacer(SourcePosition paramSourcePosition) {
    super(paramSourcePosition, RegisterSpecList.EMPTY);
  }
  
  protected String argString() {
    return null;
  }
  
  public int codeSize() {
    return getAddress() & 0x1;
  }
  
  protected String listingString0(boolean paramBoolean) {
    return (codeSize() == 0) ? null : "nop // spacer";
  }
  
  public DalvInsn withRegisters(RegisterSpecList paramRegisterSpecList) {
    return new OddSpacer(getPosition());
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput) {
    if (codeSize() != 0)
      paramAnnotatedOutput.writeShort(InsnFormat.codeUnit(0, 0)); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\OddSpacer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */