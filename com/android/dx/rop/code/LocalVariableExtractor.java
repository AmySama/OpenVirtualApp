package com.android.dx.rop.code;

import com.android.dx.util.Bits;
import com.android.dx.util.IntList;

public final class LocalVariableExtractor {
  private final BasicBlockList blocks;
  
  private final RopMethod method;
  
  private final LocalVariableInfo resultInfo;
  
  private final int[] workSet;
  
  private LocalVariableExtractor(RopMethod paramRopMethod) {
    if (paramRopMethod != null) {
      BasicBlockList basicBlockList = paramRopMethod.getBlocks();
      int i = basicBlockList.getMaxLabel();
      this.method = paramRopMethod;
      this.blocks = basicBlockList;
      this.resultInfo = new LocalVariableInfo(paramRopMethod);
      this.workSet = Bits.makeBitSet(i);
      return;
    } 
    throw new NullPointerException("method == null");
  }
  
  private LocalVariableInfo doit() {
    for (int i = this.method.getFirstLabel(); i >= 0; i = Bits.findFirst(this.workSet, 0)) {
      Bits.clear(this.workSet, i);
      processBlock(i);
    } 
    this.resultInfo.setImmutable();
    return this.resultInfo;
  }
  
  public static LocalVariableInfo extract(RopMethod paramRopMethod) {
    return (new LocalVariableExtractor(paramRopMethod)).doit();
  }
  
  private void processBlock(int paramInt) {
    RegisterSpecSet registerSpecSet1 = this.resultInfo.mutableCopyOfStarts(paramInt);
    BasicBlock basicBlock = this.blocks.labelToBlock(paramInt);
    InsnList insnList = basicBlock.getInsns();
    int i = insnList.size();
    boolean bool = basicBlock.hasExceptionHandlers();
    int j = 0;
    if (bool && insnList.getLast().getResult() != null) {
      paramInt = 1;
    } else {
      paramInt = 0;
    } 
    RegisterSpecSet registerSpecSet2 = registerSpecSet1;
    int k = 0;
    while (k < i) {
      RegisterSpec registerSpec1;
      RegisterSpecSet registerSpecSet = registerSpecSet2;
      if (paramInt != 0) {
        registerSpecSet = registerSpecSet2;
        if (k == i - 1) {
          registerSpecSet2.setImmutable();
          registerSpecSet = registerSpecSet2.mutableCopy();
        } 
      } 
      Insn insn = insnList.get(k);
      RegisterSpec registerSpec2 = insn.getLocalAssignment();
      if (registerSpec2 == null) {
        registerSpec1 = insn.getResult();
        if (registerSpec1 != null && registerSpecSet.get(registerSpec1.getReg()) != null)
          registerSpecSet.remove(registerSpecSet.get(registerSpec1.getReg())); 
      } else {
        registerSpec2 = registerSpec2.withSimpleType();
        if (!registerSpec2.equals(registerSpecSet.get(registerSpec2))) {
          RegisterSpec registerSpec = registerSpecSet.localItemToSpec(registerSpec2.getLocalItem());
          if (registerSpec != null && registerSpec.getReg() != registerSpec2.getReg())
            registerSpecSet.remove(registerSpec); 
          this.resultInfo.addAssignment((Insn)registerSpec1, registerSpec2);
          registerSpecSet.put(registerSpec2);
        } 
      } 
      k++;
      registerSpecSet2 = registerSpecSet;
    } 
    registerSpecSet2.setImmutable();
    IntList intList = basicBlock.getSuccessors();
    i = intList.size();
    k = basicBlock.getPrimarySuccessor();
    for (paramInt = j; paramInt < i; paramInt++) {
      RegisterSpecSet registerSpecSet;
      j = intList.get(paramInt);
      if (j == k) {
        registerSpecSet = registerSpecSet2;
      } else {
        registerSpecSet = registerSpecSet1;
      } 
      if (this.resultInfo.mergeStarts(j, registerSpecSet))
        Bits.set(this.workSet, j); 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\LocalVariableExtractor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */