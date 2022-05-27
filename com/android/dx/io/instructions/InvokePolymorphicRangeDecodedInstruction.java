package com.android.dx.io.instructions;

import com.android.dx.io.IndexType;

public class InvokePolymorphicRangeDecodedInstruction extends DecodedInstruction {
  private final int c;
  
  private final int protoIndex;
  
  private final int registerCount;
  
  public InvokePolymorphicRangeDecodedInstruction(InstructionCodec paramInstructionCodec, int paramInt1, int paramInt2, IndexType paramIndexType, int paramInt3, int paramInt4, int paramInt5) {
    super(paramInstructionCodec, paramInt1, paramInt2, paramIndexType, 0, 0L);
    if (paramInt5 == (short)paramInt5) {
      this.c = paramInt3;
      this.registerCount = paramInt4;
      this.protoIndex = paramInt5;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("protoIndex doesn't fit in a short: ");
    stringBuilder.append(paramInt5);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public int getC() {
    return this.c;
  }
  
  public short getProtoIndex() {
    return (short)this.protoIndex;
  }
  
  public int getRegisterCount() {
    return this.registerCount;
  }
  
  public DecodedInstruction withIndex(int paramInt) {
    throw new UnsupportedOperationException("use withProtoIndex to update both the method and proto indices for invoke-polymorphic/range");
  }
  
  public DecodedInstruction withProtoIndex(int paramInt1, int paramInt2) {
    return new InvokePolymorphicRangeDecodedInstruction(getFormat(), getOpcode(), paramInt1, getIndexType(), this.c, this.registerCount, paramInt2);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\io\instructions\InvokePolymorphicRangeDecodedInstruction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */