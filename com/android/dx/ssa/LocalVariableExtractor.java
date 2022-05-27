package com.android.dx.ssa;

import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecSet;
import com.android.dx.util.IntList;
import java.util.ArrayList;
import java.util.BitSet;

public class LocalVariableExtractor {
  private final ArrayList<SsaBasicBlock> blocks;
  
  private final SsaMethod method;
  
  private final LocalVariableInfo resultInfo;
  
  private final BitSet workSet;
  
  private LocalVariableExtractor(SsaMethod paramSsaMethod) {
    if (paramSsaMethod != null) {
      ArrayList<SsaBasicBlock> arrayList = paramSsaMethod.getBlocks();
      this.method = paramSsaMethod;
      this.blocks = arrayList;
      this.resultInfo = new LocalVariableInfo(paramSsaMethod);
      this.workSet = new BitSet(arrayList.size());
      return;
    } 
    throw new NullPointerException("method == null");
  }
  
  private LocalVariableInfo doit() {
    if (this.method.getRegCount() > 0)
      for (int i = this.method.getEntryBlockIndex(); i >= 0; i = this.workSet.nextSetBit(0)) {
        this.workSet.clear(i);
        processBlock(i);
      }  
    this.resultInfo.setImmutable();
    return this.resultInfo;
  }
  
  public static LocalVariableInfo extract(SsaMethod paramSsaMethod) {
    return (new LocalVariableExtractor(paramSsaMethod)).doit();
  }
  
  private void processBlock(int paramInt) {
    RegisterSpecSet registerSpecSet1 = this.resultInfo.mutableCopyOfStarts(paramInt);
    SsaBasicBlock ssaBasicBlock = this.blocks.get(paramInt);
    ArrayList<SsaInsn> arrayList = ssaBasicBlock.getInsns();
    int i = arrayList.size();
    if (paramInt == this.method.getExitBlockIndex())
      return; 
    int j = i - 1;
    SsaInsn ssaInsn = arrayList.get(j);
    paramInt = ssaInsn.getOriginalRopInsn().getCatches().size();
    int k = 0;
    int m = 1;
    if (paramInt != 0) {
      paramInt = 1;
    } else {
      paramInt = 0;
    } 
    if (paramInt != 0 && ssaInsn.getResult() != null) {
      paramInt = m;
    } else {
      paramInt = 0;
    } 
    RegisterSpecSet registerSpecSet2 = registerSpecSet1;
    m = 0;
    while (m < i) {
      RegisterSpec registerSpec1;
      RegisterSpecSet registerSpecSet = registerSpecSet2;
      if (paramInt != 0) {
        registerSpecSet = registerSpecSet2;
        if (m == j) {
          registerSpecSet2.setImmutable();
          registerSpecSet = registerSpecSet2.mutableCopy();
        } 
      } 
      SsaInsn ssaInsn1 = arrayList.get(m);
      RegisterSpec registerSpec2 = ssaInsn1.getLocalAssignment();
      if (registerSpec2 == null) {
        registerSpec1 = ssaInsn1.getResult();
        if (registerSpec1 != null && registerSpecSet.get(registerSpec1.getReg()) != null)
          registerSpecSet.remove(registerSpecSet.get(registerSpec1.getReg())); 
      } else {
        registerSpec2 = registerSpec2.withSimpleType();
        if (!registerSpec2.equals(registerSpecSet.get(registerSpec2))) {
          RegisterSpec registerSpec = registerSpecSet.localItemToSpec(registerSpec2.getLocalItem());
          if (registerSpec != null && registerSpec.getReg() != registerSpec2.getReg())
            registerSpecSet.remove(registerSpec); 
          this.resultInfo.addAssignment((SsaInsn)registerSpec1, registerSpec2);
          registerSpecSet.put(registerSpec2);
        } 
      } 
      m++;
      registerSpecSet2 = registerSpecSet;
    } 
    registerSpecSet2.setImmutable();
    IntList intList = ssaBasicBlock.getSuccessorList();
    i = intList.size();
    m = ssaBasicBlock.getPrimarySuccessorIndex();
    for (paramInt = k; paramInt < i; paramInt++) {
      RegisterSpecSet registerSpecSet;
      k = intList.get(paramInt);
      if (k == m) {
        registerSpecSet = registerSpecSet2;
      } else {
        registerSpecSet = registerSpecSet1;
      } 
      if (this.resultInfo.mergeStarts(k, registerSpecSet))
        this.workSet.set(k); 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\LocalVariableExtractor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */