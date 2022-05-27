package com.android.dx.ssa;

import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.LocalItem;
import com.android.dx.rop.code.PlainInsn;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.Rops;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.util.IntList;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class SsaRenamer implements Runnable {
  private static final boolean DEBUG = false;
  
  private int nextSsaReg;
  
  private final int ropRegCount;
  
  private final SsaMethod ssaMeth;
  
  private final ArrayList<LocalItem> ssaRegToLocalItems;
  
  private IntList ssaRegToRopReg;
  
  private final RegisterSpec[][] startsForBlocks;
  
  private int threshold;
  
  public SsaRenamer(SsaMethod paramSsaMethod) {
    int i = paramSsaMethod.getRegCount();
    this.ropRegCount = i;
    this.ssaMeth = paramSsaMethod;
    this.nextSsaReg = i;
    i = 0;
    this.threshold = 0;
    this.startsForBlocks = new RegisterSpec[paramSsaMethod.getBlocks().size()][];
    this.ssaRegToLocalItems = new ArrayList<LocalItem>();
    RegisterSpec[] arrayOfRegisterSpec = new RegisterSpec[this.ropRegCount];
    while (i < this.ropRegCount) {
      arrayOfRegisterSpec[i] = RegisterSpec.make(i, (TypeBearer)Type.VOID);
      i++;
    } 
    this.startsForBlocks[paramSsaMethod.getEntryBlockIndex()] = arrayOfRegisterSpec;
  }
  
  public SsaRenamer(SsaMethod paramSsaMethod, int paramInt) {
    this(paramSsaMethod);
    this.threshold = paramInt;
  }
  
  private static RegisterSpec[] dupArray(RegisterSpec[] paramArrayOfRegisterSpec) {
    RegisterSpec[] arrayOfRegisterSpec = new RegisterSpec[paramArrayOfRegisterSpec.length];
    System.arraycopy(paramArrayOfRegisterSpec, 0, arrayOfRegisterSpec, 0, paramArrayOfRegisterSpec.length);
    return arrayOfRegisterSpec;
  }
  
  private static boolean equalsHandlesNulls(Object paramObject1, Object paramObject2) {
    return (paramObject1 == paramObject2 || (paramObject1 != null && paramObject1.equals(paramObject2)));
  }
  
  private LocalItem getLocalForNewReg(int paramInt) {
    return (paramInt < this.ssaRegToLocalItems.size()) ? this.ssaRegToLocalItems.get(paramInt) : null;
  }
  
  private boolean isBelowThresholdRegister(int paramInt) {
    boolean bool;
    if (paramInt < this.threshold) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private boolean isVersionZeroRegister(int paramInt) {
    boolean bool;
    if (paramInt < this.ropRegCount) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private void setNameForSsaReg(RegisterSpec paramRegisterSpec) {
    int i = paramRegisterSpec.getReg();
    LocalItem localItem = paramRegisterSpec.getLocalItem();
    this.ssaRegToLocalItems.ensureCapacity(i + 1);
    while (this.ssaRegToLocalItems.size() <= i)
      this.ssaRegToLocalItems.add(null); 
    this.ssaRegToLocalItems.set(i, localItem);
  }
  
  public void run() {
    this.ssaMeth.forEachBlockDepthFirstDom(new SsaBasicBlock.Visitor() {
          public void visitBlock(SsaBasicBlock param1SsaBasicBlock1, SsaBasicBlock param1SsaBasicBlock2) {
            (new SsaRenamer.BlockRenamer(param1SsaBasicBlock1)).process();
          }
        });
    this.ssaMeth.setNewRegCount(this.nextSsaReg);
    this.ssaMeth.onInsnsChanged();
  }
  
  private class BlockRenamer implements SsaInsn.Visitor {
    private final SsaBasicBlock block;
    
    private final RegisterSpec[] currentMapping;
    
    private final HashMap<SsaInsn, SsaInsn> insnsToReplace;
    
    private final RenamingMapper mapper;
    
    private final HashSet<SsaInsn> movesToKeep;
    
    BlockRenamer(SsaBasicBlock param1SsaBasicBlock) {
      this.block = param1SsaBasicBlock;
      this.currentMapping = SsaRenamer.this.startsForBlocks[param1SsaBasicBlock.getIndex()];
      this.movesToKeep = new HashSet<SsaInsn>();
      this.insnsToReplace = new HashMap<SsaInsn, SsaInsn>();
      this.mapper = new RenamingMapper();
      SsaRenamer.this.startsForBlocks[param1SsaBasicBlock.getIndex()] = null;
    }
    
    private void addMapping(int param1Int, RegisterSpec param1RegisterSpec) {
      int i = param1RegisterSpec.getReg();
      LocalItem localItem = param1RegisterSpec.getLocalItem();
      RegisterSpec[] arrayOfRegisterSpec = this.currentMapping;
      arrayOfRegisterSpec[param1Int] = param1RegisterSpec;
      for (param1Int = arrayOfRegisterSpec.length - 1; param1Int >= 0; param1Int--) {
        if (i == this.currentMapping[param1Int].getReg())
          this.currentMapping[param1Int] = param1RegisterSpec; 
      } 
      if (localItem == null)
        return; 
      SsaRenamer.this.setNameForSsaReg(param1RegisterSpec);
      for (param1Int = this.currentMapping.length - 1; param1Int >= 0; param1Int--) {
        param1RegisterSpec = this.currentMapping[param1Int];
        if (i != param1RegisterSpec.getReg() && localItem.equals(param1RegisterSpec.getLocalItem()))
          this.currentMapping[param1Int] = param1RegisterSpec.withLocalItem(null); 
      } 
    }
    
    private void updateSuccessorPhis() {
      PhiInsn.Visitor visitor = new PhiInsn.Visitor() {
          public void visitPhiInsn(PhiInsn param2PhiInsn) {
            int i = param2PhiInsn.getRopResultReg();
            if (SsaRenamer.this.isBelowThresholdRegister(i))
              return; 
            RegisterSpec registerSpec = SsaRenamer.BlockRenamer.this.currentMapping[i];
            if (!SsaRenamer.this.isVersionZeroRegister(registerSpec.getReg()))
              param2PhiInsn.addPhiOperand(registerSpec, SsaRenamer.BlockRenamer.this.block); 
          }
        };
      BitSet bitSet = this.block.getSuccessors();
      for (int i = bitSet.nextSetBit(0); i >= 0; i = bitSet.nextSetBit(i + 1))
        ((SsaBasicBlock)SsaRenamer.this.ssaMeth.getBlocks().get(i)).forEachPhiInsn(visitor); 
    }
    
    public void process() {
      this.block.forEachInsn(this);
      updateSuccessorPhis();
      ArrayList<SsaInsn> arrayList = this.block.getInsns();
      int i = arrayList.size();
      boolean bool = true;
      while (--i >= 0) {
        SsaInsn ssaInsn1 = arrayList.get(i);
        SsaInsn ssaInsn2 = this.insnsToReplace.get(ssaInsn1);
        if (ssaInsn2 != null) {
          arrayList.set(i, ssaInsn2);
        } else if (ssaInsn1.isNormalMoveInsn() && !this.movesToKeep.contains(ssaInsn1)) {
          arrayList.remove(i);
        } 
        i--;
      } 
      Iterator<SsaBasicBlock> iterator = this.block.getDomChildren().iterator();
      i = bool;
      while (iterator.hasNext()) {
        SsaBasicBlock ssaBasicBlock = iterator.next();
        if (ssaBasicBlock != this.block) {
          RegisterSpec[] arrayOfRegisterSpec;
          if (i != 0) {
            arrayOfRegisterSpec = this.currentMapping;
          } else {
            arrayOfRegisterSpec = SsaRenamer.dupArray(this.currentMapping);
          } 
          SsaRenamer.this.startsForBlocks[ssaBasicBlock.getIndex()] = arrayOfRegisterSpec;
          i = 0;
        } 
      } 
    }
    
    void processResultReg(SsaInsn param1SsaInsn) {
      RegisterSpec registerSpec = param1SsaInsn.getResult();
      if (registerSpec == null)
        return; 
      int i = registerSpec.getReg();
      if (SsaRenamer.this.isBelowThresholdRegister(i))
        return; 
      param1SsaInsn.changeResultReg(SsaRenamer.this.nextSsaReg);
      addMapping(i, param1SsaInsn.getResult());
      SsaRenamer.access$108(SsaRenamer.this);
    }
    
    public void visitMoveInsn(NormalSsaInsn param1NormalSsaInsn) {
      RegisterSpec registerSpec1 = param1NormalSsaInsn.getResult();
      int i = registerSpec1.getReg();
      RegisterSpecList registerSpecList = param1NormalSsaInsn.getSources();
      boolean bool = false;
      int j = registerSpecList.get(0).getReg();
      param1NormalSsaInsn.mapSourceRegisters(this.mapper);
      int k = param1NormalSsaInsn.getSources().get(0).getReg();
      LocalItem localItem2 = this.currentMapping[j].getLocalItem();
      LocalItem localItem3 = registerSpec1.getLocalItem();
      LocalItem localItem1 = localItem3;
      if (localItem3 == null)
        localItem1 = localItem2; 
      localItem3 = SsaRenamer.this.getLocalForNewReg(k);
      if (localItem3 == null || localItem1 == null || localItem1.equals(localItem3))
        bool = true; 
      RegisterSpec registerSpec2 = RegisterSpec.makeLocalOptional(k, (TypeBearer)registerSpec1.getType(), localItem1);
      if (!Optimizer.getPreserveLocals() || (bool && SsaRenamer.equalsHandlesNulls(localItem1, localItem2) && SsaRenamer.this.threshold == 0)) {
        addMapping(i, registerSpec2);
        return;
      } 
      if (bool && localItem2 == null && SsaRenamer.this.threshold == 0) {
        RegisterSpecList registerSpecList1 = RegisterSpecList.make(RegisterSpec.make(registerSpec2.getReg(), (TypeBearer)registerSpec2.getType(), localItem1));
        SsaInsn ssaInsn = SsaInsn.makeFromRop((Insn)new PlainInsn(Rops.opMarkLocal((TypeBearer)registerSpec2), SourcePosition.NO_INFO, null, registerSpecList1), this.block);
        this.insnsToReplace.put(param1NormalSsaInsn, ssaInsn);
        addMapping(i, registerSpec2);
      } else {
        processResultReg(param1NormalSsaInsn);
        this.movesToKeep.add(param1NormalSsaInsn);
      } 
    }
    
    public void visitNonMoveInsn(NormalSsaInsn param1NormalSsaInsn) {
      param1NormalSsaInsn.mapSourceRegisters(this.mapper);
      processResultReg(param1NormalSsaInsn);
    }
    
    public void visitPhiInsn(PhiInsn param1PhiInsn) {
      processResultReg(param1PhiInsn);
    }
    
    private class RenamingMapper extends RegisterMapper {
      public int getNewRegisterCount() {
        return SsaRenamer.this.nextSsaReg;
      }
      
      public RegisterSpec map(RegisterSpec param2RegisterSpec) {
        if (param2RegisterSpec == null)
          return null; 
        int i = param2RegisterSpec.getReg();
        return param2RegisterSpec.withReg(SsaRenamer.BlockRenamer.this.currentMapping[i].getReg());
      }
    }
  }
  
  class null implements PhiInsn.Visitor {
    public void visitPhiInsn(PhiInsn param1PhiInsn) {
      int i = param1PhiInsn.getRopResultReg();
      if (SsaRenamer.this.isBelowThresholdRegister(i))
        return; 
      RegisterSpec registerSpec = this.this$1.currentMapping[i];
      if (!SsaRenamer.this.isVersionZeroRegister(registerSpec.getReg()))
        param1PhiInsn.addPhiOperand(registerSpec, this.this$1.block); 
    }
  }
  
  private class RenamingMapper extends RegisterMapper {
    public int getNewRegisterCount() {
      return SsaRenamer.this.nextSsaReg;
    }
    
    public RegisterSpec map(RegisterSpec param1RegisterSpec) {
      if (param1RegisterSpec == null)
        return null; 
      int i = param1RegisterSpec.getReg();
      return param1RegisterSpec.withReg(this.this$1.currentMapping[i].getReg());
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\SsaRenamer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */