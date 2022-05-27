package com.android.dx.ssa;

import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;

public class DeadCodeRemover {
  private final int regCount;
  
  private final SsaMethod ssaMeth;
  
  private final ArrayList<SsaInsn>[] useList;
  
  private final BitSet worklist;
  
  private DeadCodeRemover(SsaMethod paramSsaMethod) {
    this.ssaMeth = paramSsaMethod;
    this.regCount = paramSsaMethod.getRegCount();
    this.worklist = new BitSet(this.regCount);
    this.useList = this.ssaMeth.getUseListCopy();
  }
  
  private static boolean hasSideEffect(SsaInsn paramSsaInsn) {
    return (paramSsaInsn == null) ? true : paramSsaInsn.hasSideEffect();
  }
  
  private boolean isCircularNoSideEffect(int paramInt, BitSet paramBitSet) {
    if (paramBitSet != null && paramBitSet.get(paramInt))
      return true; 
    Iterator<SsaInsn> iterator1 = this.useList[paramInt].iterator();
    while (iterator1.hasNext()) {
      if (hasSideEffect(iterator1.next()))
        return false; 
    } 
    BitSet bitSet = paramBitSet;
    if (paramBitSet == null)
      bitSet = new BitSet(this.regCount); 
    bitSet.set(paramInt);
    Iterator<SsaInsn> iterator2 = this.useList[paramInt].iterator();
    while (iterator2.hasNext()) {
      RegisterSpec registerSpec = ((SsaInsn)iterator2.next()).getResult();
      if (registerSpec == null || !isCircularNoSideEffect(registerSpec.getReg(), bitSet))
        return false; 
    } 
    return true;
  }
  
  public static void process(SsaMethod paramSsaMethod) {
    (new DeadCodeRemover(paramSsaMethod)).run();
  }
  
  private void pruneDeadInstructions() {
    HashSet<SsaInsn> hashSet = new HashSet();
    BitSet bitSet = this.ssaMeth.computeReachability();
    ArrayList<SsaBasicBlock> arrayList = this.ssaMeth.getBlocks();
    int i = 0;
    label31: while (true) {
      int j = bitSet.nextClearBit(i);
      if (j < arrayList.size()) {
        SsaBasicBlock ssaBasicBlock = arrayList.get(j);
        int k = j + 1;
        j = 0;
        while (true) {
          i = k;
          if (j < ssaBasicBlock.getInsns().size()) {
            ssaInsn = ssaBasicBlock.getInsns().get(j);
            RegisterSpecList registerSpecList = ssaInsn.getSources();
            int m = registerSpecList.size();
            if (m != 0)
              hashSet.add(ssaInsn); 
            for (i = 0; i < m; i++) {
              RegisterSpec registerSpec1 = registerSpecList.get(i);
              this.useList[registerSpec1.getReg()].remove(ssaInsn);
            } 
            RegisterSpec registerSpec = ssaInsn.getResult();
            if (registerSpec != null)
              for (SsaInsn ssaInsn : this.useList[registerSpec.getReg()]) {
                if (ssaInsn instanceof PhiInsn)
                  ((PhiInsn)ssaInsn).removePhiRegister(registerSpec); 
              }  
            j++;
            continue;
          } 
          continue label31;
        } 
        break;
      } 
      this.ssaMeth.deleteInsns(hashSet);
      return;
    } 
  }
  
  private void run() {
    pruneDeadInstructions();
    HashSet<SsaInsn> hashSet = new HashSet();
    this.ssaMeth.forEachInsn(new NoSideEffectVisitor(this.worklist));
    while (true) {
      BitSet bitSet = this.worklist;
      byte b = 0;
      int i = bitSet.nextSetBit(0);
      if (i >= 0) {
        this.worklist.clear(i);
        if (this.useList[i].size() == 0 || isCircularNoSideEffect(i, null)) {
          SsaInsn ssaInsn = this.ssaMeth.getDefinitionForRegister(i);
          if (hashSet.contains(ssaInsn))
            continue; 
          RegisterSpecList registerSpecList = ssaInsn.getSources();
          i = registerSpecList.size();
          while (b < i) {
            RegisterSpec registerSpec = registerSpecList.get(b);
            this.useList[registerSpec.getReg()].remove(ssaInsn);
            if (!hasSideEffect(this.ssaMeth.getDefinitionForRegister(registerSpec.getReg())))
              this.worklist.set(registerSpec.getReg()); 
            b++;
          } 
          hashSet.add(ssaInsn);
        } 
        continue;
      } 
      this.ssaMeth.deleteInsns(hashSet);
      return;
    } 
  }
  
  private static class NoSideEffectVisitor implements SsaInsn.Visitor {
    BitSet noSideEffectRegs;
    
    public NoSideEffectVisitor(BitSet param1BitSet) {
      this.noSideEffectRegs = param1BitSet;
    }
    
    public void visitMoveInsn(NormalSsaInsn param1NormalSsaInsn) {
      if (!DeadCodeRemover.hasSideEffect(param1NormalSsaInsn))
        this.noSideEffectRegs.set(param1NormalSsaInsn.getResult().getReg()); 
    }
    
    public void visitNonMoveInsn(NormalSsaInsn param1NormalSsaInsn) {
      RegisterSpec registerSpec = param1NormalSsaInsn.getResult();
      if (!DeadCodeRemover.hasSideEffect(param1NormalSsaInsn) && registerSpec != null)
        this.noSideEffectRegs.set(registerSpec.getReg()); 
    }
    
    public void visitPhiInsn(PhiInsn param1PhiInsn) {
      if (!DeadCodeRemover.hasSideEffect(param1PhiInsn))
        this.noSideEffectRegs.set(param1PhiInsn.getResult().getReg()); 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\DeadCodeRemover.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */