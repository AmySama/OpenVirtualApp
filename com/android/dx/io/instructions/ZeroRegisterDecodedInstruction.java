package com.android.dx.io.instructions;

import com.android.dx.io.IndexType;

public final class ZeroRegisterDecodedInstruction extends DecodedInstruction {
  public ZeroRegisterDecodedInstruction(InstructionCodec paramInstructionCodec, int paramInt1, int paramInt2, IndexType paramIndexType, int paramInt3, long paramLong) {
    super(paramInstructionCodec, paramInt1, paramInt2, paramIndexType, paramInt3, paramLong);
  }
  
  public int getRegisterCount() {
    return 0;
  }
  
  public DecodedInstruction withIndex(int paramInt) {
    return new ZeroRegisterDecodedInstruction(getFormat(), getOpcode(), paramInt, getIndexType(), getTarget(), getLiteral());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\io\instructions\ZeroRegisterDecodedInstruction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */