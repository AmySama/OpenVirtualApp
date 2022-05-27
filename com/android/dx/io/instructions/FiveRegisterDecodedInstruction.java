package com.android.dx.io.instructions;

import com.android.dx.io.IndexType;

public final class FiveRegisterDecodedInstruction extends DecodedInstruction {
  private final int a;
  
  private final int b;
  
  private final int c;
  
  private final int d;
  
  private final int e;
  
  public FiveRegisterDecodedInstruction(InstructionCodec paramInstructionCodec, int paramInt1, int paramInt2, IndexType paramIndexType, int paramInt3, long paramLong, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
    super(paramInstructionCodec, paramInt1, paramInt2, paramIndexType, paramInt3, paramLong);
    this.a = paramInt4;
    this.b = paramInt5;
    this.c = paramInt6;
    this.d = paramInt7;
    this.e = paramInt8;
  }
  
  public int getA() {
    return this.a;
  }
  
  public int getB() {
    return this.b;
  }
  
  public int getC() {
    return this.c;
  }
  
  public int getD() {
    return this.d;
  }
  
  public int getE() {
    return this.e;
  }
  
  public int getRegisterCount() {
    return 5;
  }
  
  public DecodedInstruction withIndex(int paramInt) {
    return new FiveRegisterDecodedInstruction(getFormat(), getOpcode(), paramInt, getIndexType(), getTarget(), getLiteral(), this.a, this.b, this.c, this.d, this.e);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\io\instructions\FiveRegisterDecodedInstruction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */