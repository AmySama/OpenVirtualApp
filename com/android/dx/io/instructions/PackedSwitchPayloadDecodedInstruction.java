package com.android.dx.io.instructions;

public final class PackedSwitchPayloadDecodedInstruction extends DecodedInstruction {
  private final int firstKey;
  
  private final int[] targets;
  
  public PackedSwitchPayloadDecodedInstruction(InstructionCodec paramInstructionCodec, int paramInt1, int paramInt2, int[] paramArrayOfint) {
    super(paramInstructionCodec, paramInt1, 0, null, 0, 0L);
    this.firstKey = paramInt2;
    this.targets = paramArrayOfint;
  }
  
  public int getFirstKey() {
    return this.firstKey;
  }
  
  public int getRegisterCount() {
    return 0;
  }
  
  public int[] getTargets() {
    return this.targets;
  }
  
  public DecodedInstruction withIndex(int paramInt) {
    throw new UnsupportedOperationException("no index in instruction");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\io\instructions\PackedSwitchPayloadDecodedInstruction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */