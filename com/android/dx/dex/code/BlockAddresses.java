package com.android.dx.dex.code;

import com.android.dx.rop.code.BasicBlock;
import com.android.dx.rop.code.BasicBlockList;
import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.RopMethod;
import com.android.dx.rop.code.SourcePosition;

public final class BlockAddresses {
  private final CodeAddress[] ends;
  
  private final CodeAddress[] lasts;
  
  private final CodeAddress[] starts;
  
  public BlockAddresses(RopMethod paramRopMethod) {
    int i = paramRopMethod.getBlocks().getMaxLabel();
    this.starts = new CodeAddress[i];
    this.lasts = new CodeAddress[i];
    this.ends = new CodeAddress[i];
    setupArrays(paramRopMethod);
  }
  
  private void setupArrays(RopMethod paramRopMethod) {
    BasicBlockList basicBlockList = paramRopMethod.getBlocks();
    int i = basicBlockList.size();
    for (byte b = 0; b < i; b++) {
      BasicBlock basicBlock = basicBlockList.get(b);
      int j = basicBlock.getLabel();
      Insn insn = basicBlock.getInsns().get(0);
      this.starts[j] = new CodeAddress(insn.getPosition());
      SourcePosition sourcePosition = basicBlock.getLastInsn().getPosition();
      this.lasts[j] = new CodeAddress(sourcePosition);
      this.ends[j] = new CodeAddress(sourcePosition);
    } 
  }
  
  public CodeAddress getEnd(int paramInt) {
    return this.ends[paramInt];
  }
  
  public CodeAddress getEnd(BasicBlock paramBasicBlock) {
    return this.ends[paramBasicBlock.getLabel()];
  }
  
  public CodeAddress getLast(int paramInt) {
    return this.lasts[paramInt];
  }
  
  public CodeAddress getLast(BasicBlock paramBasicBlock) {
    return this.lasts[paramBasicBlock.getLabel()];
  }
  
  public CodeAddress getStart(int paramInt) {
    return this.starts[paramInt];
  }
  
  public CodeAddress getStart(BasicBlock paramBasicBlock) {
    return this.starts[paramBasicBlock.getLabel()];
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\BlockAddresses.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */