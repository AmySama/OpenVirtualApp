package com.android.dex;

public final class Code {
  private final CatchHandler[] catchHandlers;
  
  private final int debugInfoOffset;
  
  private final int insSize;
  
  private final short[] instructions;
  
  private final int outsSize;
  
  private final int registersSize;
  
  private final Try[] tries;
  
  public Code(int paramInt1, int paramInt2, int paramInt3, int paramInt4, short[] paramArrayOfshort, Try[] paramArrayOfTry, CatchHandler[] paramArrayOfCatchHandler) {
    this.registersSize = paramInt1;
    this.insSize = paramInt2;
    this.outsSize = paramInt3;
    this.debugInfoOffset = paramInt4;
    this.instructions = paramArrayOfshort;
    this.tries = paramArrayOfTry;
    this.catchHandlers = paramArrayOfCatchHandler;
  }
  
  public CatchHandler[] getCatchHandlers() {
    return this.catchHandlers;
  }
  
  public int getDebugInfoOffset() {
    return this.debugInfoOffset;
  }
  
  public int getInsSize() {
    return this.insSize;
  }
  
  public short[] getInstructions() {
    return this.instructions;
  }
  
  public int getOutsSize() {
    return this.outsSize;
  }
  
  public int getRegistersSize() {
    return this.registersSize;
  }
  
  public Try[] getTries() {
    return this.tries;
  }
  
  public static class CatchHandler {
    final int[] addresses;
    
    final int catchAllAddress;
    
    final int offset;
    
    final int[] typeIndexes;
    
    public CatchHandler(int[] param1ArrayOfint1, int[] param1ArrayOfint2, int param1Int1, int param1Int2) {
      this.typeIndexes = param1ArrayOfint1;
      this.addresses = param1ArrayOfint2;
      this.catchAllAddress = param1Int1;
      this.offset = param1Int2;
    }
    
    public int[] getAddresses() {
      return this.addresses;
    }
    
    public int getCatchAllAddress() {
      return this.catchAllAddress;
    }
    
    public int getOffset() {
      return this.offset;
    }
    
    public int[] getTypeIndexes() {
      return this.typeIndexes;
    }
  }
  
  public static class Try {
    final int catchHandlerIndex;
    
    final int instructionCount;
    
    final int startAddress;
    
    Try(int param1Int1, int param1Int2, int param1Int3) {
      this.startAddress = param1Int1;
      this.instructionCount = param1Int2;
      this.catchHandlerIndex = param1Int3;
    }
    
    public int getCatchHandlerIndex() {
      return this.catchHandlerIndex;
    }
    
    public int getInstructionCount() {
      return this.instructionCount;
    }
    
    public int getStartAddress() {
      return this.startAddress;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dex\Code.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */