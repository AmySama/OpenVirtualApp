package com.android.dx.ssa;

import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RopMethod;
import com.android.dx.util.IntIterator;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;

public class SsaConverter {
  public static final boolean DEBUG = false;
  
  public static SsaMethod convertToSsaMethod(RopMethod paramRopMethod, int paramInt, boolean paramBoolean) {
    SsaMethod ssaMethod = SsaMethod.newFromRopMethod(paramRopMethod, paramInt, paramBoolean);
    edgeSplit(ssaMethod);
    placePhiFunctions(ssaMethod, LocalVariableExtractor.extract(ssaMethod), 0);
    (new SsaRenamer(ssaMethod)).run();
    ssaMethod.makeExitBlock();
    return ssaMethod;
  }
  
  private static void edgeSplit(SsaMethod paramSsaMethod) {
    edgeSplitPredecessors(paramSsaMethod);
    edgeSplitMoveExceptionsAndResults(paramSsaMethod);
    edgeSplitSuccessors(paramSsaMethod);
  }
  
  private static void edgeSplitMoveExceptionsAndResults(SsaMethod paramSsaMethod) {
    ArrayList<SsaBasicBlock> arrayList = paramSsaMethod.getBlocks();
    for (int i = arrayList.size() - 1; i >= 0; i--) {
      SsaBasicBlock ssaBasicBlock = arrayList.get(i);
      if (!ssaBasicBlock.isExitBlock() && ssaBasicBlock.getPredecessors().cardinality() > 1 && ((SsaInsn)ssaBasicBlock.getInsns().get(0)).isMoveException()) {
        BitSet bitSet = (BitSet)ssaBasicBlock.getPredecessors().clone();
        int j;
        for (j = bitSet.nextSetBit(0); j >= 0; j = bitSet.nextSetBit(j + 1))
          ((SsaBasicBlock)arrayList.get(j)).insertNewSuccessor(ssaBasicBlock).getInsns().add(0, ((SsaInsn)ssaBasicBlock.getInsns().get(0)).clone()); 
        ssaBasicBlock.getInsns().remove(0);
      } 
    } 
  }
  
  private static void edgeSplitPredecessors(SsaMethod paramSsaMethod) {
    ArrayList<SsaBasicBlock> arrayList = paramSsaMethod.getBlocks();
    for (int i = arrayList.size() - 1; i >= 0; i--) {
      SsaBasicBlock ssaBasicBlock = arrayList.get(i);
      if (nodeNeedsUniquePredecessor(ssaBasicBlock))
        ssaBasicBlock.insertNewPredecessor(); 
    } 
  }
  
  private static void edgeSplitSuccessors(SsaMethod paramSsaMethod) {
    ArrayList<SsaBasicBlock> arrayList = paramSsaMethod.getBlocks();
    for (int i = arrayList.size() - 1; i >= 0; i--) {
      SsaBasicBlock ssaBasicBlock = arrayList.get(i);
      BitSet bitSet = (BitSet)ssaBasicBlock.getSuccessors().clone();
      int j;
      for (j = bitSet.nextSetBit(0); j >= 0; j = bitSet.nextSetBit(j + 1)) {
        SsaBasicBlock ssaBasicBlock1 = arrayList.get(j);
        if (needsNewSuccessor(ssaBasicBlock, ssaBasicBlock1))
          ssaBasicBlock.insertNewSuccessor(ssaBasicBlock1); 
      } 
    } 
  }
  
  private static boolean needsNewSuccessor(SsaBasicBlock paramSsaBasicBlock1, SsaBasicBlock paramSsaBasicBlock2) {
    ArrayList<SsaInsn> arrayList = paramSsaBasicBlock1.getInsns();
    int i = arrayList.size();
    boolean bool = true;
    SsaInsn ssaInsn = arrayList.get(i - 1);
    if (paramSsaBasicBlock1.getSuccessors().cardinality() > 1 && paramSsaBasicBlock2.getPredecessors().cardinality() > 1)
      return true; 
    if ((ssaInsn.getResult() == null && ssaInsn.getSources().size() <= 0) || paramSsaBasicBlock2.getPredecessors().cardinality() <= 1)
      bool = false; 
    return bool;
  }
  
  private static boolean nodeNeedsUniquePredecessor(SsaBasicBlock paramSsaBasicBlock) {
    int i = paramSsaBasicBlock.getPredecessors().cardinality();
    int j = paramSsaBasicBlock.getSuccessors().cardinality();
    boolean bool = true;
    if (i <= 1 || j <= 1)
      bool = false; 
    return bool;
  }
  
  private static void placePhiFunctions(SsaMethod paramSsaMethod, LocalVariableInfo paramLocalVariableInfo, int paramInt) {
    ArrayList<SsaBasicBlock> arrayList = paramSsaMethod.getBlocks();
    int i = arrayList.size();
    int j = paramSsaMethod.getRegCount() - paramInt;
    DomFront.DomInfo[] arrayOfDomInfo = (new DomFront(paramSsaMethod)).run();
    BitSet[] arrayOfBitSet1 = new BitSet[j];
    BitSet[] arrayOfBitSet2 = new BitSet[j];
    byte b;
    for (b = 0; b < j; b++) {
      arrayOfBitSet1[b] = new BitSet(i);
      arrayOfBitSet2[b] = new BitSet(i);
    } 
    i = arrayList.size();
    for (b = 0; b < i; b++) {
      Iterator<SsaInsn> iterator = ((SsaBasicBlock)arrayList.get(b)).getInsns().iterator();
      while (iterator.hasNext()) {
        RegisterSpec registerSpec = ((SsaInsn)iterator.next()).getResult();
        if (registerSpec != null && registerSpec.getReg() - paramInt >= 0)
          arrayOfBitSet1[registerSpec.getReg() - paramInt].set(b); 
      } 
    } 
    b = 0;
    while (b < j) {
      BitSet bitSet = (BitSet)arrayOfBitSet1[b].clone();
      while (true) {
        i = bitSet.nextSetBit(0);
        if (i >= 0) {
          bitSet.clear(i);
          IntIterator intIterator = (arrayOfDomInfo[i]).dominanceFrontiers.iterator();
          while (intIterator.hasNext()) {
            i = intIterator.next();
            if (!arrayOfBitSet2[b].get(i)) {
              arrayOfBitSet2[b].set(i);
              int k = b + paramInt;
              RegisterSpec registerSpec = paramLocalVariableInfo.getStarts(i).get(k);
              if (registerSpec == null) {
                ((SsaBasicBlock)arrayList.get(i)).addPhiInsnForReg(k);
              } else {
                ((SsaBasicBlock)arrayList.get(i)).addPhiInsnForReg(registerSpec);
              } 
              if (!arrayOfBitSet1[b].get(i))
                bitSet.set(i); 
            } 
          } 
          continue;
        } 
        b++;
      } 
    } 
  }
  
  public static SsaMethod testEdgeSplit(RopMethod paramRopMethod, int paramInt, boolean paramBoolean) {
    SsaMethod ssaMethod = SsaMethod.newFromRopMethod(paramRopMethod, paramInt, paramBoolean);
    edgeSplit(ssaMethod);
    return ssaMethod;
  }
  
  public static SsaMethod testPhiPlacement(RopMethod paramRopMethod, int paramInt, boolean paramBoolean) {
    SsaMethod ssaMethod = SsaMethod.newFromRopMethod(paramRopMethod, paramInt, paramBoolean);
    edgeSplit(ssaMethod);
    placePhiFunctions(ssaMethod, LocalVariableExtractor.extract(ssaMethod), 0);
    return ssaMethod;
  }
  
  public static void updateSsaMethod(SsaMethod paramSsaMethod, int paramInt) {
    placePhiFunctions(paramSsaMethod, LocalVariableExtractor.extract(paramSsaMethod), paramInt);
    (new SsaRenamer(paramSsaMethod, paramInt)).run();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\SsaConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */