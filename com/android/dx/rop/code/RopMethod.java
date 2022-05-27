package com.android.dx.rop.code;

import com.android.dx.util.Hex;
import com.android.dx.util.IntList;

public final class RopMethod {
  private final BasicBlockList blocks;
  
  private IntList exitPredecessors;
  
  private final int firstLabel;
  
  private IntList[] predecessors;
  
  public RopMethod(BasicBlockList paramBasicBlockList, int paramInt) {
    if (paramBasicBlockList != null) {
      if (paramInt >= 0) {
        this.blocks = paramBasicBlockList;
        this.firstLabel = paramInt;
        this.predecessors = null;
        this.exitPredecessors = null;
        return;
      } 
      throw new IllegalArgumentException("firstLabel < 0");
    } 
    throw new NullPointerException("blocks == null");
  }
  
  private void calcPredecessors() {
    byte b2;
    int i = this.blocks.getMaxLabel();
    IntList[] arrayOfIntList = new IntList[i];
    IntList intList = new IntList(10);
    int j = this.blocks.size();
    byte b1 = 0;
    int k = 0;
    while (true) {
      b2 = b1;
      if (k < j) {
        BasicBlock basicBlock = this.blocks.get(k);
        int m = basicBlock.getLabel();
        IntList intList1 = basicBlock.getSuccessors();
        int n = intList1.size();
        if (n == 0) {
          intList.add(m);
        } else {
          for (b2 = 0; b2 < n; b2++) {
            int i1 = intList1.get(b2);
            IntList intList3 = arrayOfIntList[i1];
            IntList intList2 = intList3;
            if (intList3 == null) {
              intList2 = new IntList(10);
              arrayOfIntList[i1] = intList2;
            } 
            intList2.add(m);
          } 
        } 
        k++;
        continue;
      } 
      break;
    } 
    while (b2 < i) {
      IntList intList1 = arrayOfIntList[b2];
      if (intList1 != null) {
        intList1.sort();
        intList1.setImmutable();
      } 
      b2++;
    } 
    intList.sort();
    intList.setImmutable();
    k = this.firstLabel;
    if (arrayOfIntList[k] == null)
      arrayOfIntList[k] = IntList.EMPTY; 
    this.predecessors = arrayOfIntList;
    this.exitPredecessors = intList;
  }
  
  public BasicBlockList getBlocks() {
    return this.blocks;
  }
  
  public IntList getExitPredecessors() {
    if (this.exitPredecessors == null)
      calcPredecessors(); 
    return this.exitPredecessors;
  }
  
  public int getFirstLabel() {
    return this.firstLabel;
  }
  
  public IntList labelToPredecessors(int paramInt) {
    if (this.exitPredecessors == null)
      calcPredecessors(); 
    IntList intList = this.predecessors[paramInt];
    if (intList != null)
      return intList; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("no such block: ");
    stringBuilder.append(Hex.u2(paramInt));
    throw new RuntimeException(stringBuilder.toString());
  }
  
  public RopMethod withRegisterOffset(int paramInt) {
    RopMethod ropMethod = new RopMethod(this.blocks.withRegisterOffset(paramInt), this.firstLabel);
    IntList intList = this.exitPredecessors;
    if (intList != null) {
      ropMethod.exitPredecessors = intList;
      ropMethod.predecessors = this.predecessors;
    } 
    return ropMethod;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\RopMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */