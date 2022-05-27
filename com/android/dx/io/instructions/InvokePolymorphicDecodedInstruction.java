package com.android.dx.io.instructions;

import com.android.dx.io.IndexType;

public class InvokePolymorphicDecodedInstruction extends DecodedInstruction {
  private final int protoIndex;
  
  private final int[] registers;
  
  public InvokePolymorphicDecodedInstruction(InstructionCodec paramInstructionCodec, int paramInt1, int paramInt2, IndexType paramIndexType, int paramInt3, int[] paramArrayOfint) {
    super(paramInstructionCodec, paramInt1, paramInt2, paramIndexType, 0, 0L);
    if (paramInt3 == (short)paramInt3) {
      this.protoIndex = paramInt3;
      this.registers = paramArrayOfint;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("protoIndex doesn't fit in a short: ");
    stringBuilder.append(paramInt3);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public int getC() {
    int[] arrayOfInt = this.registers;
    int i = arrayOfInt.length;
    int j = 0;
    if (i > 0)
      j = arrayOfInt[0]; 
    return j;
  }
  
  public int getD() {
    boolean bool;
    int[] arrayOfInt = this.registers;
    if (arrayOfInt.length > 1) {
      bool = arrayOfInt[1];
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int getE() {
    boolean bool;
    int[] arrayOfInt = this.registers;
    if (arrayOfInt.length > 2) {
      bool = arrayOfInt[2];
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int getF() {
    boolean bool;
    int[] arrayOfInt = this.registers;
    if (arrayOfInt.length > 3) {
      bool = arrayOfInt[3];
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int getG() {
    boolean bool;
    int[] arrayOfInt = this.registers;
    if (arrayOfInt.length > 4) {
      bool = arrayOfInt[4];
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public short getProtoIndex() {
    return (short)this.protoIndex;
  }
  
  public int getRegisterCount() {
    return this.registers.length;
  }
  
  public DecodedInstruction withIndex(int paramInt) {
    throw new UnsupportedOperationException("use withProtoIndex to update both the method and proto indices for invoke-polymorphic");
  }
  
  public DecodedInstruction withProtoIndex(int paramInt1, int paramInt2) {
    return new InvokePolymorphicDecodedInstruction(getFormat(), getOpcode(), paramInt1, getIndexType(), paramInt2, this.registers);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\io\instructions\InvokePolymorphicDecodedInstruction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */