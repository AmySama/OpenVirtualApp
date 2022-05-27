package com.android.dx.ssa;

import com.android.dx.rop.code.BasicBlock;
import com.android.dx.rop.code.BasicBlockList;
import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.InsnList;
import com.android.dx.rop.code.PlainInsn;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.RopMethod;
import com.android.dx.rop.code.Rops;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.util.Hex;
import com.android.dx.util.IntList;
import com.android.dx.util.IntSet;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class SsaBasicBlock {
  public static final Comparator<SsaBasicBlock> LABEL_COMPARATOR = new LabelComparator();
  
  private final ArrayList<SsaBasicBlock> domChildren;
  
  private final int index;
  
  private final ArrayList<SsaInsn> insns;
  
  private IntSet liveIn;
  
  private IntSet liveOut;
  
  private int movesFromPhisAtBeginning = 0;
  
  private int movesFromPhisAtEnd = 0;
  
  private final SsaMethod parent;
  
  private BitSet predecessors;
  
  private int primarySuccessor = -1;
  
  private final int ropLabel;
  
  private IntList successorList;
  
  private BitSet successors;
  
  public SsaBasicBlock(int paramInt1, int paramInt2, SsaMethod paramSsaMethod) {
    this.parent = paramSsaMethod;
    this.index = paramInt1;
    this.insns = new ArrayList<SsaInsn>();
    this.ropLabel = paramInt2;
    this.predecessors = new BitSet(paramSsaMethod.getBlocks().size());
    this.successors = new BitSet(paramSsaMethod.getBlocks().size());
    this.successorList = new IntList();
    this.domChildren = new ArrayList<SsaBasicBlock>();
  }
  
  private static boolean checkRegUsed(BitSet paramBitSet, RegisterSpec paramRegisterSpec) {
    int i = paramRegisterSpec.getReg();
    int j = paramRegisterSpec.getCategory();
    boolean bool = paramBitSet.get(i);
    boolean bool1 = true;
    boolean bool2 = bool1;
    if (!bool)
      if (j == 2 && paramBitSet.get(i + 1)) {
        bool2 = bool1;
      } else {
        bool2 = false;
      }  
    return bool2;
  }
  
  private int getCountPhiInsns() {
    int i = this.insns.size();
    byte b;
    for (b = 0; b < i && (SsaInsn)this.insns.get(b) instanceof PhiInsn; b++);
    return b;
  }
  
  public static SsaBasicBlock newFromRop(RopMethod paramRopMethod, int paramInt, SsaMethod paramSsaMethod) {
    BasicBlockList basicBlockList = paramRopMethod.getBlocks();
    BasicBlock basicBlock = basicBlockList.get(paramInt);
    SsaBasicBlock ssaBasicBlock = new SsaBasicBlock(paramInt, basicBlock.getLabel(), paramSsaMethod);
    InsnList insnList = basicBlock.getInsns();
    ssaBasicBlock.insns.ensureCapacity(insnList.size());
    int i = insnList.size();
    for (paramInt = 0; paramInt < i; paramInt++)
      ssaBasicBlock.insns.add(new NormalSsaInsn(insnList.get(paramInt), ssaBasicBlock)); 
    ssaBasicBlock.predecessors = SsaMethod.bitSetFromLabelList(basicBlockList, paramRopMethod.labelToPredecessors(basicBlock.getLabel()));
    ssaBasicBlock.successors = SsaMethod.bitSetFromLabelList(basicBlockList, basicBlock.getSuccessors());
    IntList intList = SsaMethod.indexListFromLabelList(basicBlockList, basicBlock.getSuccessors());
    ssaBasicBlock.successorList = intList;
    if (intList.size() != 0) {
      paramInt = basicBlock.getPrimarySuccessor();
      if (paramInt < 0) {
        paramInt = -1;
      } else {
        paramInt = basicBlockList.indexOfLabel(paramInt);
      } 
      ssaBasicBlock.primarySuccessor = paramInt;
    } 
    return ssaBasicBlock;
  }
  
  private void scheduleUseBeforeAssigned(List<SsaInsn> paramList) {
    BitSet bitSet1 = new BitSet(this.parent.getRegCount());
    BitSet bitSet2 = new BitSet(this.parent.getRegCount());
    int i = paramList.size();
    int j = 0;
    while (j < i) {
      int k;
      for (k = j; k < i; k++) {
        setRegsUsed(bitSet1, ((SsaInsn)paramList.get(k)).getSources().get(0));
        setRegsUsed(bitSet2, ((SsaInsn)paramList.get(k)).getResult());
      } 
      int m = j;
      for (k = m; m < i; k = n) {
        int n = k;
        if (!checkRegUsed(bitSet1, ((SsaInsn)paramList.get(m)).getResult())) {
          Collections.swap(paramList, m, k);
          n = k + 1;
        } 
        m++;
      } 
      if (j == k) {
        SsaInsn ssaInsn1 = null;
        j = k;
        while (true) {
          ssaInsn2 = ssaInsn1;
          if (j < i) {
            ssaInsn2 = paramList.get(j);
            if (checkRegUsed(bitSet1, ssaInsn2.getResult()) && checkRegUsed(bitSet2, ssaInsn2.getSources().get(0))) {
              Collections.swap(paramList, k, j);
              break;
            } 
            j++;
            continue;
          } 
          break;
        } 
        RegisterSpec registerSpec1 = ssaInsn2.getResult();
        RegisterSpec registerSpec2 = registerSpec1.withReg(this.parent.borrowSpareRegister(registerSpec1.getCategory()));
        SsaInsn ssaInsn2 = new NormalSsaInsn((Insn)new PlainInsn(Rops.opMove((TypeBearer)registerSpec1.getType()), SourcePosition.NO_INFO, registerSpec2, ssaInsn2.getSources()), this);
        j = k + 1;
        paramList.add(k, ssaInsn2);
        RegisterSpecList registerSpecList = RegisterSpecList.make(registerSpec2);
        paramList.set(j, new NormalSsaInsn((Insn)new PlainInsn(Rops.opMove((TypeBearer)registerSpec1.getType()), SourcePosition.NO_INFO, registerSpec1, registerSpecList), this));
        i = paramList.size();
      } else {
        j = k;
      } 
      bitSet1.clear();
      bitSet2.clear();
    } 
  }
  
  private static void setRegsUsed(BitSet paramBitSet, RegisterSpec paramRegisterSpec) {
    paramBitSet.set(paramRegisterSpec.getReg());
    if (paramRegisterSpec.getCategory() > 1)
      paramBitSet.set(paramRegisterSpec.getReg() + 1); 
  }
  
  public void addDomChild(SsaBasicBlock paramSsaBasicBlock) {
    this.domChildren.add(paramSsaBasicBlock);
  }
  
  public void addInsnToHead(Insn paramInsn) {
    SsaInsn ssaInsn = SsaInsn.makeFromRop(paramInsn, this);
    this.insns.add(getCountPhiInsns(), ssaInsn);
    this.parent.onInsnAdded(ssaInsn);
  }
  
  public void addLiveIn(int paramInt) {
    if (this.liveIn == null)
      this.liveIn = SetFactory.makeLivenessSet(this.parent.getRegCount()); 
    this.liveIn.add(paramInt);
  }
  
  public void addLiveOut(int paramInt) {
    if (this.liveOut == null)
      this.liveOut = SetFactory.makeLivenessSet(this.parent.getRegCount()); 
    this.liveOut.add(paramInt);
  }
  
  public void addMoveToBeginning(RegisterSpec paramRegisterSpec1, RegisterSpec paramRegisterSpec2) {
    if (paramRegisterSpec1.getReg() == paramRegisterSpec2.getReg())
      return; 
    RegisterSpecList registerSpecList = RegisterSpecList.make(paramRegisterSpec2);
    NormalSsaInsn normalSsaInsn = new NormalSsaInsn((Insn)new PlainInsn(Rops.opMove((TypeBearer)paramRegisterSpec1.getType()), SourcePosition.NO_INFO, paramRegisterSpec1, registerSpecList), this);
    this.insns.add(getCountPhiInsns(), normalSsaInsn);
    this.movesFromPhisAtBeginning++;
  }
  
  public void addMoveToEnd(RegisterSpec paramRegisterSpec1, RegisterSpec paramRegisterSpec2) {
    if (this.successors.cardinality() <= 1) {
      if (paramRegisterSpec1.getReg() == paramRegisterSpec2.getReg())
        return; 
      ArrayList<SsaInsn> arrayList2 = this.insns;
      NormalSsaInsn normalSsaInsn2 = (NormalSsaInsn)arrayList2.get(arrayList2.size() - 1);
      if (normalSsaInsn2.getResult() != null || normalSsaInsn2.getSources().size() > 0) {
        int i;
        for (i = this.successors.nextSetBit(0); i >= 0; i = this.successors.nextSetBit(i + 1))
          ((SsaBasicBlock)this.parent.getBlocks().get(i)).addMoveToBeginning(paramRegisterSpec1, paramRegisterSpec2); 
        return;
      } 
      RegisterSpecList registerSpecList = RegisterSpecList.make(paramRegisterSpec2);
      NormalSsaInsn normalSsaInsn1 = new NormalSsaInsn((Insn)new PlainInsn(Rops.opMove((TypeBearer)paramRegisterSpec1.getType()), SourcePosition.NO_INFO, paramRegisterSpec1, registerSpecList), this);
      ArrayList<SsaInsn> arrayList1 = this.insns;
      arrayList1.add(arrayList1.size() - 1, normalSsaInsn1);
      this.movesFromPhisAtEnd++;
      return;
    } 
    throw new IllegalStateException("Inserting a move to a block with multiple successors");
  }
  
  public void addPhiInsnForReg(int paramInt) {
    this.insns.add(0, new PhiInsn(paramInt, this));
  }
  
  public void addPhiInsnForReg(RegisterSpec paramRegisterSpec) {
    this.insns.add(0, new PhiInsn(paramRegisterSpec, this));
  }
  
  public void exitBlockFixup(SsaBasicBlock paramSsaBasicBlock) {
    if (this == paramSsaBasicBlock)
      return; 
    if (this.successorList.size() == 0) {
      this.successors.set(paramSsaBasicBlock.index);
      this.successorList.add(paramSsaBasicBlock.index);
      this.primarySuccessor = paramSsaBasicBlock.index;
      paramSsaBasicBlock.predecessors.set(this.index);
    } 
  }
  
  public void forEachInsn(SsaInsn.Visitor paramVisitor) {
    int i = this.insns.size();
    for (byte b = 0; b < i; b++)
      ((SsaInsn)this.insns.get(b)).accept(paramVisitor); 
  }
  
  public void forEachPhiInsn(PhiInsn.Visitor paramVisitor) {
    int i = this.insns.size();
    byte b = 0;
    while (b < i) {
      SsaInsn ssaInsn = this.insns.get(b);
      if (ssaInsn instanceof PhiInsn) {
        paramVisitor.visitPhiInsn((PhiInsn)ssaInsn);
        b++;
      } 
    } 
  }
  
  public ArrayList<SsaBasicBlock> getDomChildren() {
    return this.domChildren;
  }
  
  public int getIndex() {
    return this.index;
  }
  
  public ArrayList<SsaInsn> getInsns() {
    return this.insns;
  }
  
  public IntSet getLiveInRegs() {
    if (this.liveIn == null)
      this.liveIn = SetFactory.makeLivenessSet(this.parent.getRegCount()); 
    return this.liveIn;
  }
  
  public IntSet getLiveOutRegs() {
    if (this.liveOut == null)
      this.liveOut = SetFactory.makeLivenessSet(this.parent.getRegCount()); 
    return this.liveOut;
  }
  
  public SsaMethod getParent() {
    return this.parent;
  }
  
  public List<SsaInsn> getPhiInsns() {
    return this.insns.subList(0, getCountPhiInsns());
  }
  
  public BitSet getPredecessors() {
    return this.predecessors;
  }
  
  public SsaBasicBlock getPrimarySuccessor() {
    return (this.primarySuccessor < 0) ? null : this.parent.getBlocks().get(this.primarySuccessor);
  }
  
  public int getPrimarySuccessorIndex() {
    return this.primarySuccessor;
  }
  
  public int getPrimarySuccessorRopLabel() {
    return this.parent.blockIndexToRopLabel(this.primarySuccessor);
  }
  
  public int getRopLabel() {
    return this.ropLabel;
  }
  
  public String getRopLabelString() {
    return Hex.u2(this.ropLabel);
  }
  
  public IntList getRopLabelSuccessorList() {
    IntList intList = new IntList(this.successorList.size());
    int i = this.successorList.size();
    for (byte b = 0; b < i; b++)
      intList.add(this.parent.blockIndexToRopLabel(this.successorList.get(b))); 
    return intList;
  }
  
  public IntList getSuccessorList() {
    return this.successorList;
  }
  
  public BitSet getSuccessors() {
    return this.successors;
  }
  
  public SsaBasicBlock insertNewPredecessor() {
    SsaBasicBlock ssaBasicBlock = this.parent.makeNewGotoBlock();
    ssaBasicBlock.predecessors = this.predecessors;
    ssaBasicBlock.successors.set(this.index);
    ssaBasicBlock.successorList.add(this.index);
    ssaBasicBlock.primarySuccessor = this.index;
    BitSet bitSet = new BitSet(this.parent.getBlocks().size());
    this.predecessors = bitSet;
    bitSet.set(ssaBasicBlock.index);
    for (int i = ssaBasicBlock.predecessors.nextSetBit(0); i >= 0; i = ssaBasicBlock.predecessors.nextSetBit(i + 1))
      ((SsaBasicBlock)this.parent.getBlocks().get(i)).replaceSuccessor(this.index, ssaBasicBlock.index); 
    return ssaBasicBlock;
  }
  
  public SsaBasicBlock insertNewSuccessor(SsaBasicBlock paramSsaBasicBlock) {
    SsaBasicBlock ssaBasicBlock = this.parent.makeNewGotoBlock();
    if (this.successors.get(paramSsaBasicBlock.index)) {
      ssaBasicBlock.predecessors.set(this.index);
      ssaBasicBlock.successors.set(paramSsaBasicBlock.index);
      ssaBasicBlock.successorList.add(paramSsaBasicBlock.index);
      ssaBasicBlock.primarySuccessor = paramSsaBasicBlock.index;
      for (int i = this.successorList.size() - 1; i >= 0; i--) {
        if (this.successorList.get(i) == paramSsaBasicBlock.index)
          this.successorList.set(i, ssaBasicBlock.index); 
      } 
      if (this.primarySuccessor == paramSsaBasicBlock.index)
        this.primarySuccessor = ssaBasicBlock.index; 
      this.successors.clear(paramSsaBasicBlock.index);
      this.successors.set(ssaBasicBlock.index);
      paramSsaBasicBlock.predecessors.set(ssaBasicBlock.index);
      paramSsaBasicBlock.predecessors.set(this.index, this.successors.get(paramSsaBasicBlock.index));
      return ssaBasicBlock;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Block ");
    stringBuilder.append(paramSsaBasicBlock.getRopLabelString());
    stringBuilder.append(" not successor of ");
    stringBuilder.append(getRopLabelString());
    throw new RuntimeException(stringBuilder.toString());
  }
  
  public boolean isExitBlock() {
    boolean bool;
    if (this.index == this.parent.getExitBlockIndex()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void removeAllPhiInsns() {
    this.insns.subList(0, getCountPhiInsns()).clear();
  }
  
  public void removeSuccessor(int paramInt) {
    int i = this.successorList.size() - 1;
    int j = 0;
    while (i >= 0) {
      if (this.successorList.get(i) == paramInt) {
        j = i;
      } else {
        this.primarySuccessor = this.successorList.get(i);
      } 
      i--;
    } 
    this.successorList.removeIndex(j);
    this.successors.clear(paramInt);
    ((SsaBasicBlock)this.parent.getBlocks().get(paramInt)).predecessors.clear(this.index);
  }
  
  public void replaceLastInsn(Insn paramInsn) {
    if (paramInsn.getOpcode().getBranchingness() != 1) {
      ArrayList<SsaInsn> arrayList1 = this.insns;
      SsaInsn ssaInsn2 = arrayList1.get(arrayList1.size() - 1);
      SsaInsn ssaInsn1 = SsaInsn.makeFromRop(paramInsn, this);
      ArrayList<SsaInsn> arrayList2 = this.insns;
      arrayList2.set(arrayList2.size() - 1, ssaInsn1);
      this.parent.onInsnRemoved(ssaInsn2);
      this.parent.onInsnAdded(ssaInsn1);
      return;
    } 
    throw new IllegalArgumentException("last insn must branch");
  }
  
  public void replaceSuccessor(int paramInt1, int paramInt2) {
    if (paramInt1 == paramInt2)
      return; 
    this.successors.set(paramInt2);
    if (this.primarySuccessor == paramInt1)
      this.primarySuccessor = paramInt2; 
    for (int i = this.successorList.size() - 1; i >= 0; i--) {
      if (this.successorList.get(i) == paramInt1)
        this.successorList.set(i, paramInt2); 
    } 
    this.successors.clear(paramInt1);
    ((SsaBasicBlock)this.parent.getBlocks().get(paramInt2)).predecessors.set(this.index);
    ((SsaBasicBlock)this.parent.getBlocks().get(paramInt1)).predecessors.clear(this.index);
  }
  
  public void scheduleMovesFromPhis() {
    int i = this.movesFromPhisAtBeginning;
    if (i > 1) {
      scheduleUseBeforeAssigned(this.insns.subList(0, i));
      if (((SsaInsn)this.insns.get(this.movesFromPhisAtBeginning)).isMoveException())
        throw new RuntimeException("Unexpected: moves from phis before move-exception"); 
    } 
    if (this.movesFromPhisAtEnd > 1) {
      ArrayList<SsaInsn> arrayList = this.insns;
      scheduleUseBeforeAssigned(arrayList.subList(arrayList.size() - this.movesFromPhisAtEnd - 1, this.insns.size() - 1));
    } 
    this.parent.returnSpareRegisters();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{");
    stringBuilder.append(this.index);
    stringBuilder.append(":");
    stringBuilder.append(Hex.u2(this.ropLabel));
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public static final class LabelComparator implements Comparator<SsaBasicBlock> {
    public int compare(SsaBasicBlock param1SsaBasicBlock1, SsaBasicBlock param1SsaBasicBlock2) {
      int i = param1SsaBasicBlock1.ropLabel;
      int j = param1SsaBasicBlock2.ropLabel;
      return (i < j) ? -1 : ((i > j) ? 1 : 0);
    }
  }
  
  public static interface Visitor {
    void visitBlock(SsaBasicBlock param1SsaBasicBlock1, SsaBasicBlock param1SsaBasicBlock2);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\SsaBasicBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */