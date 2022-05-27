package com.android.dx.ssa;

import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.LocalItem;
import com.android.dx.rop.code.PlainCstInsn;
import com.android.dx.rop.code.PlainInsn;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.Rop;
import com.android.dx.rop.code.Rops;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.rop.code.ThrowingCstInsn;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.TypedConstant;
import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.rop.type.TypeList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ConstCollector {
  private static final boolean COLLECT_ONE_LOCAL = false;
  
  private static final boolean COLLECT_STRINGS = false;
  
  private static final int MAX_COLLECTED_CONSTANTS = 5;
  
  private final SsaMethod ssaMeth;
  
  private ConstCollector(SsaMethod paramSsaMethod) {
    this.ssaMeth = paramSsaMethod;
  }
  
  private void fixLocalAssignment(RegisterSpec paramRegisterSpec1, RegisterSpec paramRegisterSpec2) {
    for (SsaInsn ssaInsn1 : this.ssaMeth.getUseListForRegister(paramRegisterSpec1.getReg())) {
      RegisterSpec registerSpec = ssaInsn1.getLocalAssignment();
      if (registerSpec == null || ssaInsn1.getResult() == null)
        continue; 
      LocalItem localItem = registerSpec.getLocalItem();
      ssaInsn1.setResultLocal(null);
      paramRegisterSpec2 = paramRegisterSpec2.withLocalItem(localItem);
      SsaInsn ssaInsn2 = SsaInsn.makeFromRop((Insn)new PlainInsn(Rops.opMarkLocal((TypeBearer)paramRegisterSpec2), SourcePosition.NO_INFO, null, RegisterSpecList.make(paramRegisterSpec2)), ssaInsn1.getBlock());
      ArrayList<SsaInsn> arrayList = ssaInsn1.getBlock().getInsns();
      arrayList.add(arrayList.indexOf(ssaInsn1) + 1, ssaInsn2);
    } 
  }
  
  private ArrayList<TypedConstant> getConstsSortedByCountUse() {
    int i = this.ssaMeth.getRegCount();
    final HashMap<Object, Object> countUses = new HashMap<Object, Object>();
    new HashSet();
    for (byte b = 0; b < i; b++) {
      SsaInsn ssaInsn = this.ssaMeth.getDefinitionForRegister(b);
      if (ssaInsn != null && ssaInsn.getOpcode() != null) {
        RegisterSpec registerSpec = ssaInsn.getResult();
        TypeBearer typeBearer = registerSpec.getTypeBearer();
        if (typeBearer.isConstant()) {
          TypedConstant typedConstant = (TypedConstant)typeBearer;
          SsaInsn ssaInsn1 = ssaInsn;
          if (ssaInsn.getOpcode().getOpcode() == 56) {
            int j = ssaInsn.getBlock().getPredecessors().nextSetBit(0);
            ArrayList<SsaInsn> arrayList1 = ((SsaBasicBlock)this.ssaMeth.getBlocks().get(j)).getInsns();
            ssaInsn1 = arrayList1.get(arrayList1.size() - 1);
          } 
          if (ssaInsn1.canThrow()) {
            boolean bool = typedConstant instanceof com.android.dx.rop.cst.CstString;
          } else if (!this.ssaMeth.isRegALocal(registerSpec)) {
            Integer integer = (Integer)hashMap.get(typedConstant);
            if (integer == null) {
              hashMap.put(typedConstant, Integer.valueOf(1));
            } else {
              hashMap.put(typedConstant, Integer.valueOf(integer.intValue() + 1));
            } 
          } 
        } 
      } 
    } 
    ArrayList<?> arrayList = new ArrayList();
    for (Map.Entry<Object, Object> entry : hashMap.entrySet()) {
      if (((Integer)entry.getValue()).intValue() > 1)
        arrayList.add(entry.getKey()); 
    } 
    Collections.sort(arrayList, new Comparator<Constant>() {
          public int compare(Constant param1Constant1, Constant param1Constant2) {
            int i = ((Integer)countUses.get(param1Constant2)).intValue() - ((Integer)countUses.get(param1Constant1)).intValue();
            int j = i;
            if (i == 0)
              j = param1Constant1.compareTo(param1Constant2); 
            return j;
          }
          
          public boolean equals(Object param1Object) {
            boolean bool;
            if (param1Object == this) {
              bool = true;
            } else {
              bool = false;
            } 
            return bool;
          }
        });
    return (ArrayList)arrayList;
  }
  
  public static void process(SsaMethod paramSsaMethod) {
    (new ConstCollector(paramSsaMethod)).run();
  }
  
  private void run() {
    int i = this.ssaMeth.getRegCount();
    ArrayList<TypedConstant> arrayList = getConstsSortedByCountUse();
    int j = Math.min(arrayList.size(), 5);
    SsaBasicBlock ssaBasicBlock = this.ssaMeth.getEntryBlock();
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>(j);
    for (byte b = 0; b < j; b++) {
      TypedConstant typedConstant = arrayList.get(b);
      RegisterSpec registerSpec = RegisterSpec.make(this.ssaMeth.makeNewSsaReg(), (TypeBearer)typedConstant);
      Rop rop = Rops.opConst((TypeBearer)typedConstant);
      if (rop.getBranchingness() == 1) {
        ssaBasicBlock.addInsnToHead((Insn)new PlainCstInsn(Rops.opConst((TypeBearer)typedConstant), SourcePosition.NO_INFO, registerSpec, RegisterSpecList.EMPTY, (Constant)typedConstant));
      } else {
        SsaBasicBlock ssaBasicBlock1 = this.ssaMeth.getEntryBlock();
        SsaBasicBlock ssaBasicBlock2 = ssaBasicBlock1.getPrimarySuccessor();
        ssaBasicBlock1 = ssaBasicBlock1.insertNewSuccessor(ssaBasicBlock2);
        ssaBasicBlock1.replaceLastInsn((Insn)new ThrowingCstInsn(rop, SourcePosition.NO_INFO, RegisterSpecList.EMPTY, (TypeList)StdTypeList.EMPTY, (Constant)typedConstant));
        ssaBasicBlock1.insertNewSuccessor(ssaBasicBlock2).addInsnToHead((Insn)new PlainInsn(Rops.opMoveResultPseudo(registerSpec.getTypeBearer()), SourcePosition.NO_INFO, registerSpec, RegisterSpecList.EMPTY));
      } 
      hashMap.put(typedConstant, registerSpec);
    } 
    updateConstUses((HashMap)hashMap, i);
  }
  
  private void updateConstUses(HashMap<TypedConstant, RegisterSpec> paramHashMap, int paramInt) {
    new HashSet();
    ArrayList[] arrayOfArrayList = (ArrayList[])this.ssaMeth.getUseListCopy();
    for (byte b = 0; b < paramInt; b++) {
      SsaInsn ssaInsn = this.ssaMeth.getDefinitionForRegister(b);
      if (ssaInsn != null) {
        final RegisterSpec origReg = ssaInsn.getResult();
        TypeBearer typeBearer = ssaInsn.getResult().getTypeBearer();
        if (typeBearer.isConstant()) {
          final RegisterSpec newReg = paramHashMap.get(typeBearer);
          if (registerSpec1 != null && !this.ssaMeth.isRegALocal(registerSpec)) {
            RegisterMapper registerMapper = new RegisterMapper() {
                public int getNewRegisterCount() {
                  return ConstCollector.this.ssaMeth.getRegCount();
                }
                
                public RegisterSpec map(RegisterSpec param1RegisterSpec) {
                  RegisterSpec registerSpec = param1RegisterSpec;
                  if (param1RegisterSpec.getReg() == origReg.getReg())
                    registerSpec = newReg.withLocalItem(param1RegisterSpec.getLocalItem()); 
                  return registerSpec;
                }
              };
            for (SsaInsn ssaInsn1 : arrayOfArrayList[registerSpec.getReg()]) {
              if (ssaInsn1.canThrow() && ssaInsn1.getBlock().getSuccessors().cardinality() > 1)
                continue; 
              ssaInsn1.mapSourceRegisters(registerMapper);
            } 
          } 
        } 
      } 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\ConstCollector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */