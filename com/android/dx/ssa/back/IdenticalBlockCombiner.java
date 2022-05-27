package com.android.dx.ssa.back;

import com.android.dx.rop.code.BasicBlock;
import com.android.dx.rop.code.BasicBlockList;
import com.android.dx.rop.code.RopMethod;
import com.android.dx.util.IntList;
import java.util.BitSet;

public class IdenticalBlockCombiner {
  private final BasicBlockList blocks;
  
  private final BasicBlockList newBlocks;
  
  private final RopMethod ropMethod;
  
  public IdenticalBlockCombiner(RopMethod paramRopMethod) {
    this.ropMethod = paramRopMethod;
    BasicBlockList basicBlockList = paramRopMethod.getBlocks();
    this.blocks = basicBlockList;
    this.newBlocks = basicBlockList.getMutableCopy();
  }
  
  private void combineBlocks(int paramInt, IntList paramIntList) {
    int i = paramIntList.size();
    for (byte b = 0; b < i; b++) {
      int j = paramIntList.get(b);
      BasicBlock basicBlock = this.blocks.labelToBlock(j);
      IntList intList = this.ropMethod.labelToPredecessors(basicBlock.getLabel());
      int k = intList.size();
      for (byte b1 = 0; b1 < k; b1++)
        replaceSucc(this.newBlocks.labelToBlock(intList.get(b1)), j, paramInt); 
    } 
  }
  
  private static boolean compareInsns(BasicBlock paramBasicBlock1, BasicBlock paramBasicBlock2) {
    return paramBasicBlock1.getInsns().contentEquals(paramBasicBlock2.getInsns());
  }
  
  private void replaceSucc(BasicBlock paramBasicBlock, int paramInt1, int paramInt2) {
    IntList intList = paramBasicBlock.getSuccessors().mutableCopy();
    intList.set(intList.indexOf(paramInt1), paramInt2);
    int i = paramBasicBlock.getPrimarySuccessor();
    if (i != paramInt1)
      paramInt2 = i; 
    intList.setImmutable();
    BasicBlock basicBlock = new BasicBlock(paramBasicBlock.getLabel(), paramBasicBlock.getInsns(), intList, paramInt2);
    BasicBlockList basicBlockList = this.newBlocks;
    basicBlockList.set(basicBlockList.indexOfLabel(paramBasicBlock.getLabel()), basicBlock);
  }
  
  public RopMethod process() {
    int i = this.blocks.size();
    BitSet bitSet = new BitSet(this.blocks.getMaxLabel());
    int j;
    for (j = 0; j < i; j++) {
      BasicBlock basicBlock = this.blocks.get(j);
      if (!bitSet.get(basicBlock.getLabel())) {
        IntList intList = this.ropMethod.labelToPredecessors(basicBlock.getLabel());
        int k = intList.size();
        for (byte b = 0; b < k; b++) {
          int m = intList.get(b);
          BasicBlock basicBlock1 = this.blocks.labelToBlock(m);
          if (!bitSet.get(m) && basicBlock1.getSuccessors().size() <= 1 && basicBlock1.getFirstInsn().getOpcode().getOpcode() != 55) {
            IntList intList1 = new IntList();
            for (int n = b + 1; n < k; n++) {
              int i1 = intList.get(n);
              BasicBlock basicBlock2 = this.blocks.labelToBlock(i1);
              if (basicBlock2.getSuccessors().size() == 1 && compareInsns(basicBlock1, basicBlock2)) {
                intList1.add(i1);
                bitSet.set(i1);
              } 
            } 
            combineBlocks(m, intList1);
          } 
        } 
      } 
    } 
    for (j = i - 1; j >= 0; j--) {
      if (bitSet.get(this.newBlocks.get(j).getLabel()))
        this.newBlocks.set(j, null); 
    } 
    this.newBlocks.shrinkToFit();
    this.newBlocks.setImmutable();
    return new RopMethod(this.newBlocks, this.ropMethod.getFirstLabel());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\back\IdenticalBlockCombiner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */