package com.android.dx.io.instructions;

public final class SparseSwitchPayloadDecodedInstruction extends DecodedInstruction {
  private final int[] keys;
  
  private final int[] targets;
  
  public SparseSwitchPayloadDecodedInstruction(InstructionCodec paramInstructionCodec, int paramInt, int[] paramArrayOfint1, int[] paramArrayOfint2) {
    super(paramInstructionCodec, paramInt, 0, null, 0, 0L);
    if (paramArrayOfint1.length == paramArrayOfint2.length) {
      this.keys = paramArrayOfint1;
      this.targets = paramArrayOfint2;
      return;
    } 
    throw new IllegalArgumentException("keys/targets length mismatch");
  }
  
  public int[] getKeys() {
    return this.keys;
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\io\instructions\SparseSwitchPayloadDecodedInstruction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */