package com.android.dx.ssa.back;

import com.android.dx.rop.code.BasicBlock;
import com.android.dx.rop.code.BasicBlockList;
import com.android.dx.rop.code.InsnList;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.Rop;
import com.android.dx.rop.code.RopMethod;
import com.android.dx.rop.code.Rops;
import com.android.dx.ssa.BasicRegisterMapper;
import com.android.dx.ssa.PhiInsn;
import com.android.dx.ssa.RegisterMapper;
import com.android.dx.ssa.SsaBasicBlock;
import com.android.dx.ssa.SsaInsn;
import com.android.dx.ssa.SsaMethod;
import com.android.dx.util.Hex;
import com.android.dx.util.IntList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Iterator;

public class SsaToRop {
  private static final boolean DEBUG = false;
  
  private final InterferenceGraph interference;
  
  private final boolean minimizeRegisters;
  
  private final SsaMethod ssaMeth;
  
  private SsaToRop(SsaMethod paramSsaMethod, boolean paramBoolean) {
    this.minimizeRegisters = paramBoolean;
    this.ssaMeth = paramSsaMethod;
    this.interference = LivenessAnalyzer.constructInterferenceGraph(paramSsaMethod);
  }
  
  private RopMethod convert() {
    FirstFitLocalCombiningAllocator firstFitLocalCombiningAllocator = new FirstFitLocalCombiningAllocator(this.ssaMeth, this.interference, this.minimizeRegisters);
    RegisterMapper registerMapper = firstFitLocalCombiningAllocator.allocateRegisters();
    this.ssaMeth.setBackMode();
    this.ssaMeth.mapRegisters(registerMapper);
    removePhiFunctions();
    if (firstFitLocalCombiningAllocator.wantsParamsMovedHigh())
      moveParametersToHighRegisters(); 
    removeEmptyGotos();
    BasicBlockList basicBlockList = convertBasicBlocks();
    SsaMethod ssaMethod = this.ssaMeth;
    return (new IdenticalBlockCombiner(new RopMethod(basicBlockList, ssaMethod.blockIndexToRopLabel(ssaMethod.getEntryBlockIndex())))).process();
  }
  
  private BasicBlock convertBasicBlock(SsaBasicBlock paramSsaBasicBlock) {
    StringBuilder stringBuilder;
    int j;
    IntList intList1 = paramSsaBasicBlock.getRopLabelSuccessorList();
    int i = paramSsaBasicBlock.getPrimarySuccessorRopLabel();
    SsaBasicBlock ssaBasicBlock = this.ssaMeth.getExitBlock();
    if (ssaBasicBlock == null) {
      j = -1;
    } else {
      j = ssaBasicBlock.getRopLabel();
    } 
    IntList intList2 = intList1;
    if (intList1.contains(j))
      if (intList1.size() <= 1) {
        intList2 = IntList.EMPTY;
        verifyValidExitPredecessor(paramSsaBasicBlock);
        i = -1;
      } else {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Exit predecessor must have no other successors");
        stringBuilder.append(Hex.u2(paramSsaBasicBlock.getRopLabel()));
        throw new RuntimeException(stringBuilder.toString());
      }  
    stringBuilder.setImmutable();
    return new BasicBlock(paramSsaBasicBlock.getRopLabel(), convertInsns(paramSsaBasicBlock.getInsns()), (IntList)stringBuilder, i);
  }
  
  private BasicBlockList convertBasicBlocks() {
    ArrayList arrayList = this.ssaMeth.getBlocks();
    SsaBasicBlock ssaBasicBlock = this.ssaMeth.getExitBlock();
    BitSet bitSet = this.ssaMeth.computeReachability();
    int i = bitSet.cardinality();
    int j = i;
    if (ssaBasicBlock != null) {
      j = i;
      if (bitSet.get(ssaBasicBlock.getIndex()))
        j = i - 1; 
    } 
    BasicBlockList basicBlockList = new BasicBlockList(j);
    j = 0;
    for (SsaBasicBlock ssaBasicBlock1 : arrayList) {
      if (bitSet.get(ssaBasicBlock1.getIndex()) && ssaBasicBlock1 != ssaBasicBlock) {
        basicBlockList.set(j, convertBasicBlock(ssaBasicBlock1));
        j++;
      } 
    } 
    if (ssaBasicBlock == null || ssaBasicBlock.getInsns().isEmpty())
      return basicBlockList; 
    throw new RuntimeException("Exit block must have no insns when leaving SSA form");
  }
  
  private InsnList convertInsns(ArrayList<SsaInsn> paramArrayList) {
    int i = paramArrayList.size();
    InsnList insnList = new InsnList(i);
    for (byte b = 0; b < i; b++)
      insnList.set(b, ((SsaInsn)paramArrayList.get(b)).toRopInsn()); 
    insnList.setImmutable();
    return insnList;
  }
  
  public static RopMethod convertToRopMethod(SsaMethod paramSsaMethod, boolean paramBoolean) {
    return (new SsaToRop(paramSsaMethod, paramBoolean)).convert();
  }
  
  private void moveParametersToHighRegisters() {
    int i = this.ssaMeth.getParamWidth();
    BasicRegisterMapper basicRegisterMapper = new BasicRegisterMapper(this.ssaMeth.getRegCount());
    int j = this.ssaMeth.getRegCount();
    for (byte b = 0; b < j; b++) {
      if (b < i) {
        basicRegisterMapper.addMapping(b, j - i + b, 1);
      } else {
        basicRegisterMapper.addMapping(b, b - i, 1);
      } 
    } 
    this.ssaMeth.mapRegisters((RegisterMapper)basicRegisterMapper);
  }
  
  private void removeEmptyGotos() {
    final ArrayList blocks = this.ssaMeth.getBlocks();
    this.ssaMeth.forEachBlockDepthFirst(false, new SsaBasicBlock.Visitor() {
          public void visitBlock(SsaBasicBlock param1SsaBasicBlock1, SsaBasicBlock param1SsaBasicBlock2) {
            ArrayList<SsaInsn> arrayList = param1SsaBasicBlock1.getInsns();
            if (arrayList.size() == 1 && ((SsaInsn)arrayList.get(0)).getOpcode() == Rops.GOTO) {
              BitSet bitSet = (BitSet)param1SsaBasicBlock1.getPredecessors().clone();
              for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1))
                ((SsaBasicBlock)blocks.get(i)).replaceSuccessor(param1SsaBasicBlock1.getIndex(), param1SsaBasicBlock1.getPrimarySuccessorIndex()); 
            } 
          }
        });
  }
  
  private void removePhiFunctions() {
    ArrayList<SsaBasicBlock> arrayList = this.ssaMeth.getBlocks();
    for (SsaBasicBlock ssaBasicBlock : arrayList) {
      ssaBasicBlock.forEachPhiInsn(new PhiVisitor(arrayList));
      ssaBasicBlock.removeAllPhiInsns();
    } 
    Iterator<SsaBasicBlock> iterator = arrayList.iterator();
    while (iterator.hasNext())
      ((SsaBasicBlock)iterator.next()).scheduleMovesFromPhis(); 
  }
  
  private void verifyValidExitPredecessor(SsaBasicBlock paramSsaBasicBlock) {
    ArrayList<SsaInsn> arrayList = paramSsaBasicBlock.getInsns();
    Rop rop = ((SsaInsn)arrayList.get(arrayList.size() - 1)).getOpcode();
    if (rop.getBranchingness() == 2 || rop == Rops.THROW)
      return; 
    throw new RuntimeException("Exit predecessor must end in valid exit statement.");
  }
  
  public int[] getRegistersByFrequency() {
    int i = this.ssaMeth.getRegCount();
    Integer[] arrayOfInteger = new Integer[i];
    boolean bool = false;
    byte b;
    for (b = 0; b < i; b++)
      arrayOfInteger[b] = Integer.valueOf(b); 
    Arrays.sort(arrayOfInteger, new Comparator<Integer>() {
          public int compare(Integer param1Integer1, Integer param1Integer2) {
            return SsaToRop.this.ssaMeth.getUseListForRegister(param1Integer2.intValue()).size() - SsaToRop.this.ssaMeth.getUseListForRegister(param1Integer1.intValue()).size();
          }
        });
    int[] arrayOfInt = new int[i];
    for (b = bool; b < i; b++)
      arrayOfInt[b] = arrayOfInteger[b].intValue(); 
    return arrayOfInt;
  }
  
  private static class PhiVisitor implements PhiInsn.Visitor {
    private final ArrayList<SsaBasicBlock> blocks;
    
    public PhiVisitor(ArrayList<SsaBasicBlock> param1ArrayList) {
      this.blocks = param1ArrayList;
    }
    
    public void visitPhiInsn(PhiInsn param1PhiInsn) {
      RegisterSpecList registerSpecList = param1PhiInsn.getSources();
      RegisterSpec registerSpec = param1PhiInsn.getResult();
      int i = registerSpecList.size();
      for (byte b = 0; b < i; b++) {
        RegisterSpec registerSpec1 = registerSpecList.get(b);
        ((SsaBasicBlock)this.blocks.get(param1PhiInsn.predBlockIndexForSourcesIndex(b))).addMoveToEnd(registerSpec, registerSpec1);
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\back\SsaToRop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */