package com.android.dx.ssa;

import com.android.dx.rop.code.BasicBlockList;
import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.PlainInsn;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.RopMethod;
import com.android.dx.rop.code.Rops;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.util.IntList;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public final class SsaMethod {
  private boolean backMode;
  
  private ArrayList<SsaBasicBlock> blocks;
  
  private int borrowedSpareRegisters;
  
  private SsaInsn[] definitionList;
  
  private int entryBlockIndex;
  
  private int exitBlockIndex;
  
  private final boolean isStatic;
  
  private int maxLabel;
  
  private final int paramWidth;
  
  private int registerCount;
  
  private int spareRegisterBase;
  
  private List<SsaInsn>[] unmodifiableUseList;
  
  private ArrayList<SsaInsn>[] useList;
  
  private SsaMethod(RopMethod paramRopMethod, int paramInt, boolean paramBoolean) {
    this.paramWidth = paramInt;
    this.isStatic = paramBoolean;
    this.backMode = false;
    this.maxLabel = paramRopMethod.getBlocks().getMaxLabel();
    paramInt = paramRopMethod.getBlocks().getRegCount();
    this.registerCount = paramInt;
    this.spareRegisterBase = paramInt;
  }
  
  static BitSet bitSetFromLabelList(BasicBlockList paramBasicBlockList, IntList paramIntList) {
    BitSet bitSet = new BitSet(paramBasicBlockList.size());
    int i = paramIntList.size();
    for (byte b = 0; b < i; b++)
      bitSet.set(paramBasicBlockList.indexOfLabel(paramIntList.get(b))); 
    return bitSet;
  }
  
  private void buildUseList() {
    if (!this.backMode) {
      this.useList = (ArrayList<SsaInsn>[])new ArrayList[this.registerCount];
      boolean bool = false;
      byte b;
      for (b = 0; b < this.registerCount; b++)
        this.useList[b] = new ArrayList<SsaInsn>(); 
      forEachInsn(new SsaInsn.Visitor() {
            private void addToUses(SsaInsn param1SsaInsn) {
              RegisterSpecList registerSpecList = param1SsaInsn.getSources();
              int i = registerSpecList.size();
              for (byte b = 0; b < i; b++)
                SsaMethod.this.useList[registerSpecList.get(b).getReg()].add(param1SsaInsn); 
            }
            
            public void visitMoveInsn(NormalSsaInsn param1NormalSsaInsn) {
              addToUses(param1NormalSsaInsn);
            }
            
            public void visitNonMoveInsn(NormalSsaInsn param1NormalSsaInsn) {
              addToUses(param1NormalSsaInsn);
            }
            
            public void visitPhiInsn(PhiInsn param1PhiInsn) {
              addToUses(param1PhiInsn);
            }
          });
      this.unmodifiableUseList = (List<SsaInsn>[])new List[this.registerCount];
      for (b = bool; b < this.registerCount; b++)
        this.unmodifiableUseList[b] = Collections.unmodifiableList(this.useList[b]); 
      return;
    } 
    throw new RuntimeException("No use list in back mode");
  }
  
  private void convertRopToSsaBlocks(RopMethod paramRopMethod) {
    int i = paramRopMethod.getBlocks().size();
    this.blocks = new ArrayList<SsaBasicBlock>(i + 2);
    int j;
    for (j = 0; j < i; j++) {
      SsaBasicBlock ssaBasicBlock = SsaBasicBlock.newFromRop(paramRopMethod, j, this);
      this.blocks.add(ssaBasicBlock);
    } 
    j = paramRopMethod.getBlocks().indexOfLabel(paramRopMethod.getFirstLabel());
    this.entryBlockIndex = ((SsaBasicBlock)this.blocks.get(j)).insertNewPredecessor().getIndex();
    this.exitBlockIndex = -1;
  }
  
  private static SsaInsn getGoto(SsaBasicBlock paramSsaBasicBlock) {
    return new NormalSsaInsn((Insn)new PlainInsn(Rops.GOTO, SourcePosition.NO_INFO, null, RegisterSpecList.EMPTY), paramSsaBasicBlock);
  }
  
  public static IntList indexListFromLabelList(BasicBlockList paramBasicBlockList, IntList paramIntList) {
    IntList intList = new IntList(paramIntList.size());
    int i = paramIntList.size();
    for (byte b = 0; b < i; b++)
      intList.add(paramBasicBlockList.indexOfLabel(paramIntList.get(b))); 
    return intList;
  }
  
  public static SsaMethod newFromRopMethod(RopMethod paramRopMethod, int paramInt, boolean paramBoolean) {
    SsaMethod ssaMethod = new SsaMethod(paramRopMethod, paramInt, paramBoolean);
    ssaMethod.convertRopToSsaBlocks(paramRopMethod);
    return ssaMethod;
  }
  
  private void removeFromUseList(SsaInsn paramSsaInsn, RegisterSpecList paramRegisterSpecList) {
    if (paramRegisterSpecList == null)
      return; 
    int i = paramRegisterSpecList.size();
    byte b = 0;
    while (b < i) {
      if (this.useList[paramRegisterSpecList.get(b).getReg()].remove(paramSsaInsn)) {
        b++;
        continue;
      } 
      throw new RuntimeException("use not found");
    } 
  }
  
  public int blockIndexToRopLabel(int paramInt) {
    return (paramInt < 0) ? -1 : ((SsaBasicBlock)this.blocks.get(paramInt)).getRopLabel();
  }
  
  public int borrowSpareRegister(int paramInt) {
    int i = this.spareRegisterBase;
    int j = this.borrowedSpareRegisters;
    i += j;
    this.borrowedSpareRegisters = j + paramInt;
    this.registerCount = Math.max(this.registerCount, paramInt + i);
    return i;
  }
  
  public BitSet computeReachability() {
    int i = this.blocks.size();
    BitSet bitSet1 = new BitSet(i);
    BitSet bitSet2 = new BitSet(i);
    bitSet1.set(getEntryBlock().getIndex());
    while (true) {
      i = bitSet1.nextSetBit(0);
      if (i != -1) {
        bitSet2.set(i);
        bitSet1.or(((SsaBasicBlock)this.blocks.get(i)).getSuccessors());
        bitSet1.andNot(bitSet2);
        continue;
      } 
      return bitSet2;
    } 
  }
  
  public void deleteInsns(Set<SsaInsn> paramSet) {
    for (SsaInsn ssaInsn : paramSet) {
      SsaBasicBlock ssaBasicBlock = ssaInsn.getBlock();
      ArrayList<SsaInsn> arrayList = ssaBasicBlock.getInsns();
      int i;
      for (i = arrayList.size() - 1; i >= 0; i--) {
        SsaInsn ssaInsn1 = arrayList.get(i);
        if (ssaInsn == ssaInsn1) {
          onInsnRemoved(ssaInsn1);
          arrayList.remove(i);
          break;
        } 
      } 
      i = arrayList.size();
      if (i == 0) {
        ssaInsn = null;
      } else {
        ssaInsn = arrayList.get(i - 1);
      } 
      if (ssaBasicBlock != getExitBlock() && (i == 0 || ssaInsn.getOriginalRopInsn() == null || ssaInsn.getOriginalRopInsn().getOpcode().getBranchingness() == 1)) {
        arrayList.add(SsaInsn.makeFromRop((Insn)new PlainInsn(Rops.GOTO, SourcePosition.NO_INFO, null, RegisterSpecList.EMPTY), ssaBasicBlock));
        BitSet bitSet = ssaBasicBlock.getSuccessors();
        for (i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1)) {
          if (i != ssaBasicBlock.getPrimarySuccessorIndex())
            ssaBasicBlock.removeSuccessor(i); 
        } 
      } 
    } 
  }
  
  public void forEachBlockDepthFirst(boolean paramBoolean, SsaBasicBlock.Visitor paramVisitor) {
    SsaBasicBlock ssaBasicBlock;
    BitSet bitSet = new BitSet(this.blocks.size());
    Stack<SsaBasicBlock> stack = new Stack();
    if (paramBoolean) {
      ssaBasicBlock = getExitBlock();
    } else {
      ssaBasicBlock = getEntryBlock();
    } 
    if (ssaBasicBlock == null)
      return; 
    stack.add(null);
    stack.add(ssaBasicBlock);
    while (stack.size() > 0) {
      SsaBasicBlock ssaBasicBlock1 = stack.pop();
      SsaBasicBlock ssaBasicBlock2 = stack.pop();
      if (!bitSet.get(ssaBasicBlock1.getIndex())) {
        BitSet bitSet1;
        if (paramBoolean) {
          bitSet1 = ssaBasicBlock1.getPredecessors();
        } else {
          bitSet1 = ssaBasicBlock1.getSuccessors();
        } 
        int i;
        for (i = bitSet1.nextSetBit(0); i >= 0; i = bitSet1.nextSetBit(i + 1)) {
          stack.add(ssaBasicBlock1);
          stack.add(this.blocks.get(i));
        } 
        bitSet.set(ssaBasicBlock1.getIndex());
        paramVisitor.visitBlock(ssaBasicBlock1, ssaBasicBlock2);
      } 
    } 
  }
  
  public void forEachBlockDepthFirstDom(SsaBasicBlock.Visitor paramVisitor) {
    BitSet bitSet = new BitSet(getBlocks().size());
    Stack<SsaBasicBlock> stack = new Stack();
    stack.add(getEntryBlock());
    while (stack.size() > 0) {
      SsaBasicBlock ssaBasicBlock = stack.pop();
      ArrayList<SsaBasicBlock> arrayList = ssaBasicBlock.getDomChildren();
      if (!bitSet.get(ssaBasicBlock.getIndex())) {
        for (int i = arrayList.size() - 1; i >= 0; i--)
          stack.add(arrayList.get(i)); 
        bitSet.set(ssaBasicBlock.getIndex());
        paramVisitor.visitBlock(ssaBasicBlock, null);
      } 
    } 
  }
  
  public void forEachInsn(SsaInsn.Visitor paramVisitor) {
    Iterator<SsaBasicBlock> iterator = this.blocks.iterator();
    while (iterator.hasNext())
      ((SsaBasicBlock)iterator.next()).forEachInsn(paramVisitor); 
  }
  
  public void forEachPhiInsn(PhiInsn.Visitor paramVisitor) {
    Iterator<SsaBasicBlock> iterator = this.blocks.iterator();
    while (iterator.hasNext())
      ((SsaBasicBlock)iterator.next()).forEachPhiInsn(paramVisitor); 
  }
  
  public ArrayList<SsaBasicBlock> getBlocks() {
    return this.blocks;
  }
  
  public SsaInsn getDefinitionForRegister(int paramInt) {
    if (!this.backMode) {
      SsaInsn[] arrayOfSsaInsn = this.definitionList;
      if (arrayOfSsaInsn != null)
        return arrayOfSsaInsn[paramInt]; 
      this.definitionList = new SsaInsn[getRegCount()];
      forEachInsn(new SsaInsn.Visitor() {
            public void visitMoveInsn(NormalSsaInsn param1NormalSsaInsn) {
              SsaMethod.this.definitionList[param1NormalSsaInsn.getResult().getReg()] = param1NormalSsaInsn;
            }
            
            public void visitNonMoveInsn(NormalSsaInsn param1NormalSsaInsn) {
              if (param1NormalSsaInsn.getResult() != null)
                SsaMethod.this.definitionList[param1NormalSsaInsn.getResult().getReg()] = param1NormalSsaInsn; 
            }
            
            public void visitPhiInsn(PhiInsn param1PhiInsn) {
              SsaMethod.this.definitionList[param1PhiInsn.getResult().getReg()] = param1PhiInsn;
            }
          });
      return this.definitionList[paramInt];
    } 
    throw new RuntimeException("No def list in back mode");
  }
  
  public SsaBasicBlock getEntryBlock() {
    return this.blocks.get(this.entryBlockIndex);
  }
  
  public int getEntryBlockIndex() {
    return this.entryBlockIndex;
  }
  
  public SsaBasicBlock getExitBlock() {
    SsaBasicBlock ssaBasicBlock;
    int i = this.exitBlockIndex;
    if (i < 0) {
      ssaBasicBlock = null;
    } else {
      ssaBasicBlock = this.blocks.get(i);
    } 
    return ssaBasicBlock;
  }
  
  public int getExitBlockIndex() {
    return this.exitBlockIndex;
  }
  
  public int getParamWidth() {
    return this.paramWidth;
  }
  
  public int getRegCount() {
    return this.registerCount;
  }
  
  public ArrayList<SsaInsn>[] getUseListCopy() {
    if (this.useList == null)
      buildUseList(); 
    ArrayList[] arrayOfArrayList = new ArrayList[this.registerCount];
    for (byte b = 0; b < this.registerCount; b++)
      arrayOfArrayList[b] = new ArrayList<SsaInsn>(this.useList[b]); 
    return (ArrayList<SsaInsn>[])arrayOfArrayList;
  }
  
  public List<SsaInsn> getUseListForRegister(int paramInt) {
    if (this.unmodifiableUseList == null)
      buildUseList(); 
    return this.unmodifiableUseList[paramInt];
  }
  
  public boolean isRegALocal(RegisterSpec paramRegisterSpec) {
    SsaInsn ssaInsn = getDefinitionForRegister(paramRegisterSpec.getReg());
    if (ssaInsn == null)
      return false; 
    if (ssaInsn.getLocalAssignment() != null)
      return true; 
    Iterator<SsaInsn> iterator = getUseListForRegister(paramRegisterSpec.getReg()).iterator();
    while (iterator.hasNext()) {
      Insn insn = ((SsaInsn)iterator.next()).getOriginalRopInsn();
      if (insn != null && insn.getOpcode().getOpcode() == 54)
        return true; 
    } 
    return false;
  }
  
  public boolean isStatic() {
    return this.isStatic;
  }
  
  void makeExitBlock() {
    if (this.exitBlockIndex < 0) {
      this.exitBlockIndex = this.blocks.size();
      int i = this.exitBlockIndex;
      int j = this.maxLabel;
      this.maxLabel = j + 1;
      SsaBasicBlock ssaBasicBlock = new SsaBasicBlock(i, j, this);
      this.blocks.add(ssaBasicBlock);
      Iterator<SsaBasicBlock> iterator = this.blocks.iterator();
      while (iterator.hasNext())
        ((SsaBasicBlock)iterator.next()).exitBlockFixup(ssaBasicBlock); 
      if (ssaBasicBlock.getPredecessors().cardinality() == 0) {
        this.blocks.remove(this.exitBlockIndex);
        this.exitBlockIndex = -1;
        this.maxLabel--;
      } 
      return;
    } 
    throw new RuntimeException("must be called at most once");
  }
  
  public SsaBasicBlock makeNewGotoBlock() {
    int i = this.blocks.size();
    int j = this.maxLabel;
    this.maxLabel = j + 1;
    SsaBasicBlock ssaBasicBlock = new SsaBasicBlock(i, j, this);
    ssaBasicBlock.getInsns().add(getGoto(ssaBasicBlock));
    this.blocks.add(ssaBasicBlock);
    return ssaBasicBlock;
  }
  
  public int makeNewSsaReg() {
    int i = this.registerCount;
    int j = i + 1;
    this.registerCount = j;
    this.spareRegisterBase = j;
    onInsnsChanged();
    return i;
  }
  
  public void mapRegisters(RegisterMapper paramRegisterMapper) {
    Iterator<SsaBasicBlock> iterator = getBlocks().iterator();
    while (iterator.hasNext()) {
      Iterator<SsaInsn> iterator1 = ((SsaBasicBlock)iterator.next()).getInsns().iterator();
      while (iterator1.hasNext())
        ((SsaInsn)iterator1.next()).mapRegisters(paramRegisterMapper); 
    } 
    int i = paramRegisterMapper.getNewRegisterCount();
    this.registerCount = i;
    this.spareRegisterBase = i;
  }
  
  void onInsnAdded(SsaInsn paramSsaInsn) {
    onSourcesChanged(paramSsaInsn, null);
    updateOneDefinition(paramSsaInsn, null);
  }
  
  void onInsnRemoved(SsaInsn paramSsaInsn) {
    if (this.useList != null)
      removeFromUseList(paramSsaInsn, paramSsaInsn.getSources()); 
    RegisterSpec registerSpec = paramSsaInsn.getResult();
    SsaInsn[] arrayOfSsaInsn = this.definitionList;
    if (arrayOfSsaInsn != null && registerSpec != null)
      arrayOfSsaInsn[registerSpec.getReg()] = null; 
  }
  
  public void onInsnsChanged() {
    this.definitionList = null;
    this.useList = null;
    this.unmodifiableUseList = null;
  }
  
  void onSourceChanged(SsaInsn paramSsaInsn, RegisterSpec paramRegisterSpec1, RegisterSpec paramRegisterSpec2) {
    if (this.useList == null)
      return; 
    if (paramRegisterSpec1 != null) {
      int j = paramRegisterSpec1.getReg();
      this.useList[j].remove(paramSsaInsn);
    } 
    int i = paramRegisterSpec2.getReg();
    ArrayList<SsaInsn>[] arrayOfArrayList = this.useList;
    if (arrayOfArrayList.length <= i) {
      this.useList = null;
      return;
    } 
    arrayOfArrayList[i].add(paramSsaInsn);
  }
  
  void onSourcesChanged(SsaInsn paramSsaInsn, RegisterSpecList paramRegisterSpecList) {
    if (this.useList == null)
      return; 
    if (paramRegisterSpecList != null)
      removeFromUseList(paramSsaInsn, paramRegisterSpecList); 
    paramRegisterSpecList = paramSsaInsn.getSources();
    int i = paramRegisterSpecList.size();
    for (byte b = 0; b < i; b++) {
      int j = paramRegisterSpecList.get(b).getReg();
      this.useList[j].add(paramSsaInsn);
    } 
  }
  
  public void returnSpareRegisters() {
    this.borrowedSpareRegisters = 0;
  }
  
  public void setBackMode() {
    this.backMode = true;
    this.useList = null;
    this.definitionList = null;
  }
  
  void setNewRegCount(int paramInt) {
    this.registerCount = paramInt;
    this.spareRegisterBase = paramInt;
    onInsnsChanged();
  }
  
  void updateOneDefinition(SsaInsn paramSsaInsn, RegisterSpec paramRegisterSpec) {
    if (this.definitionList == null)
      return; 
    if (paramRegisterSpec != null) {
      int i = paramRegisterSpec.getReg();
      this.definitionList[i] = null;
    } 
    paramRegisterSpec = paramSsaInsn.getResult();
    if (paramRegisterSpec != null) {
      int i = paramRegisterSpec.getReg();
      SsaInsn[] arrayOfSsaInsn = this.definitionList;
      if (arrayOfSsaInsn[i] == null) {
        arrayOfSsaInsn[paramRegisterSpec.getReg()] = paramSsaInsn;
      } else {
        throw new RuntimeException("Duplicate add of insn");
      } 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\SsaMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */