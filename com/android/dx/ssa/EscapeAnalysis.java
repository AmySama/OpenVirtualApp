package com.android.dx.ssa;

import com.android.dx.rop.code.Exceptions;
import com.android.dx.rop.code.FillArrayDataInsn;
import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.PlainCstInsn;
import com.android.dx.rop.code.PlainInsn;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.Rop;
import com.android.dx.rop.code.Rops;
import com.android.dx.rop.code.ThrowingCstInsn;
import com.android.dx.rop.code.ThrowingInsn;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstLiteralBits;
import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.cst.TypedConstant;
import com.android.dx.rop.cst.Zeroes;
import com.android.dx.rop.type.StdTypeList;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.rop.type.TypeList;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;

public class EscapeAnalysis {
  private final ArrayList<EscapeSet> latticeValues;
  
  private final int regCount;
  
  private final SsaMethod ssaMeth;
  
  private EscapeAnalysis(SsaMethod paramSsaMethod) {
    this.ssaMeth = paramSsaMethod;
    this.regCount = paramSsaMethod.getRegCount();
    this.latticeValues = new ArrayList<EscapeSet>();
  }
  
  private void addEdge(EscapeSet paramEscapeSet1, EscapeSet paramEscapeSet2) {
    if (!paramEscapeSet2.parentSets.contains(paramEscapeSet1))
      paramEscapeSet2.parentSets.add(paramEscapeSet1); 
    if (!paramEscapeSet1.childSets.contains(paramEscapeSet2))
      paramEscapeSet1.childSets.add(paramEscapeSet2); 
  }
  
  private int findSetIndex(RegisterSpec paramRegisterSpec) {
    byte b;
    for (b = 0; b < this.latticeValues.size(); b++) {
      if (((EscapeSet)this.latticeValues.get(b)).regSet.get(paramRegisterSpec.getReg()))
        return b; 
    } 
    return b;
  }
  
  private SsaInsn getInsnForMove(SsaInsn paramSsaInsn) {
    int i = paramSsaInsn.getBlock().getPredecessors().nextSetBit(0);
    ArrayList<SsaInsn> arrayList = ((SsaBasicBlock)this.ssaMeth.getBlocks().get(i)).getInsns();
    return arrayList.get(arrayList.size() - 1);
  }
  
  private SsaInsn getMoveForInsn(SsaInsn paramSsaInsn) {
    int i = paramSsaInsn.getBlock().getSuccessors().nextSetBit(0);
    return ((SsaBasicBlock)this.ssaMeth.getBlocks().get(i)).getInsns().get(0);
  }
  
  private void insertExceptionThrow(SsaInsn paramSsaInsn, RegisterSpec paramRegisterSpec, HashSet<SsaInsn> paramHashSet) {
    CstType cstType = new CstType(Exceptions.TYPE_ArrayIndexOutOfBoundsException);
    insertThrowingInsnBefore(paramSsaInsn, RegisterSpecList.EMPTY, null, 40, (Constant)cstType);
    SsaBasicBlock ssaBasicBlock1 = paramSsaInsn.getBlock();
    SsaBasicBlock ssaBasicBlock3 = ssaBasicBlock1.insertNewSuccessor(ssaBasicBlock1.getPrimarySuccessor());
    SsaInsn ssaInsn2 = ssaBasicBlock3.getInsns().get(0);
    RegisterSpec registerSpec = RegisterSpec.make(this.ssaMeth.makeNewSsaReg(), (TypeBearer)cstType);
    insertPlainInsnBefore(ssaInsn2, RegisterSpecList.EMPTY, registerSpec, 56, null);
    ssaBasicBlock3 = ssaBasicBlock3.insertNewSuccessor(ssaBasicBlock3.getPrimarySuccessor());
    ssaInsn2 = ssaBasicBlock3.getInsns().get(0);
    CstMethodRef cstMethodRef = new CstMethodRef(cstType, new CstNat(new CstString("<init>"), new CstString("(I)V")));
    insertThrowingInsnBefore(ssaInsn2, RegisterSpecList.make(registerSpec, paramRegisterSpec), null, 52, (Constant)cstMethodRef);
    paramHashSet.add(ssaInsn2);
    SsaBasicBlock ssaBasicBlock2 = ssaBasicBlock3.insertNewSuccessor(ssaBasicBlock3.getPrimarySuccessor());
    SsaInsn ssaInsn1 = ssaBasicBlock2.getInsns().get(0);
    insertThrowingInsnBefore(ssaInsn1, RegisterSpecList.make(registerSpec), null, 35, null);
    ssaBasicBlock2.replaceSuccessor(ssaBasicBlock2.getPrimarySuccessorIndex(), this.ssaMeth.getExitBlock().getIndex());
    paramHashSet.add(ssaInsn1);
  }
  
  private void insertPlainInsnBefore(SsaInsn paramSsaInsn, RegisterSpecList paramRegisterSpecList, RegisterSpec paramRegisterSpec, int paramInt, Constant paramConstant) {
    PlainInsn plainInsn;
    PlainCstInsn plainCstInsn;
    Rop rop;
    Insn insn = paramSsaInsn.getOriginalRopInsn();
    if (paramInt == 56) {
      rop = Rops.opMoveResultPseudo((TypeBearer)paramRegisterSpec.getType());
    } else {
      rop = Rops.ropFor(paramInt, (TypeBearer)paramRegisterSpec, (TypeList)paramRegisterSpecList, paramConstant);
    } 
    if (paramConstant == null) {
      plainInsn = new PlainInsn(rop, insn.getPosition(), paramRegisterSpec, paramRegisterSpecList);
    } else {
      plainCstInsn = new PlainCstInsn(rop, insn.getPosition(), paramRegisterSpec, (RegisterSpecList)plainInsn, paramConstant);
    } 
    NormalSsaInsn normalSsaInsn = new NormalSsaInsn((Insn)plainCstInsn, paramSsaInsn.getBlock());
    ArrayList<SsaInsn> arrayList = paramSsaInsn.getBlock().getInsns();
    arrayList.add(arrayList.lastIndexOf(paramSsaInsn), normalSsaInsn);
    this.ssaMeth.onInsnAdded(normalSsaInsn);
  }
  
  private void insertThrowingInsnBefore(SsaInsn paramSsaInsn, RegisterSpecList paramRegisterSpecList, RegisterSpec paramRegisterSpec, int paramInt, Constant paramConstant) {
    ThrowingInsn throwingInsn;
    ThrowingCstInsn throwingCstInsn;
    Insn insn = paramSsaInsn.getOriginalRopInsn();
    Rop rop = Rops.ropFor(paramInt, (TypeBearer)paramRegisterSpec, (TypeList)paramRegisterSpecList, paramConstant);
    if (paramConstant == null) {
      throwingInsn = new ThrowingInsn(rop, insn.getPosition(), paramRegisterSpecList, (TypeList)StdTypeList.EMPTY);
    } else {
      throwingCstInsn = new ThrowingCstInsn(rop, insn.getPosition(), (RegisterSpecList)throwingInsn, (TypeList)StdTypeList.EMPTY, paramConstant);
    } 
    NormalSsaInsn normalSsaInsn = new NormalSsaInsn((Insn)throwingCstInsn, paramSsaInsn.getBlock());
    ArrayList<SsaInsn> arrayList = paramSsaInsn.getBlock().getInsns();
    arrayList.add(arrayList.lastIndexOf(paramSsaInsn), normalSsaInsn);
    this.ssaMeth.onInsnAdded(normalSsaInsn);
  }
  
  private void movePropagate() {
    for (byte b = 0; b < this.ssaMeth.getRegCount(); b++) {
      SsaInsn ssaInsn = this.ssaMeth.getDefinitionForRegister(b);
      if (ssaInsn != null && ssaInsn.getOpcode() != null && ssaInsn.getOpcode().getOpcode() == 2) {
        ArrayList[] arrayOfArrayList = (ArrayList[])this.ssaMeth.getUseListCopy();
        final RegisterSpec source = ssaInsn.getSources().get(0);
        final RegisterSpec result = ssaInsn.getResult();
        if (registerSpec2.getReg() >= this.regCount || registerSpec1.getReg() >= this.regCount) {
          RegisterMapper registerMapper = new RegisterMapper() {
              public int getNewRegisterCount() {
                return EscapeAnalysis.this.ssaMeth.getRegCount();
              }
              
              public RegisterSpec map(RegisterSpec param1RegisterSpec) {
                RegisterSpec registerSpec = param1RegisterSpec;
                if (param1RegisterSpec.getReg() == result.getReg())
                  registerSpec = source; 
                return registerSpec;
              }
            };
          Iterator<?> iterator = arrayOfArrayList[registerSpec1.getReg()].iterator();
          while (iterator.hasNext())
            ((SsaInsn)iterator.next()).mapSourceRegisters(registerMapper); 
        } 
      } 
    } 
  }
  
  public static void process(SsaMethod paramSsaMethod) {
    (new EscapeAnalysis(paramSsaMethod)).run();
  }
  
  private void processInsn(SsaInsn paramSsaInsn) {
    int i = paramSsaInsn.getOpcode().getOpcode();
    RegisterSpec registerSpec = paramSsaInsn.getResult();
    if (i == 56 && registerSpec.getTypeBearer().getBasicType() == 9) {
      processRegister(registerSpec, processMoveResultPseudoInsn(paramSsaInsn));
    } else if (i == 3 && registerSpec.getTypeBearer().getBasicType() == 9) {
      EscapeSet escapeSet = new EscapeSet(registerSpec.getReg(), this.regCount, EscapeState.NONE);
      this.latticeValues.add(escapeSet);
      processRegister(registerSpec, escapeSet);
    } else if (i == 55 && registerSpec.getTypeBearer().getBasicType() == 9) {
      EscapeSet escapeSet = new EscapeSet(registerSpec.getReg(), this.regCount, EscapeState.NONE);
      this.latticeValues.add(escapeSet);
      processRegister(registerSpec, escapeSet);
    } 
  }
  
  private EscapeSet processMoveResultPseudoInsn(SsaInsn paramSsaInsn) {
    RegisterSpec registerSpec = paramSsaInsn.getResult();
    paramSsaInsn = getInsnForMove(paramSsaInsn);
    int i = paramSsaInsn.getOpcode().getOpcode();
    if (i != 5)
      if (i != 38 && i != 45) {
        if (i != 46) {
          EscapeSet escapeSet2;
          RegisterSpec registerSpec1;
          EscapeSet escapeSet1;
          switch (i) {
            default:
              return null;
            case 41:
            case 42:
              if (paramSsaInsn.getSources().get(0).getTypeBearer().isConstant()) {
                escapeSet2 = new EscapeSet(registerSpec.getReg(), this.regCount, EscapeState.NONE);
                escapeSet2.replaceableArray = true;
              } else {
                escapeSet2 = new EscapeSet(registerSpec.getReg(), this.regCount, EscapeState.GLOBAL);
              } 
              this.latticeValues.add(escapeSet2);
              return escapeSet2;
            case 43:
              registerSpec1 = escapeSet2.getSources().get(0);
              i = findSetIndex(registerSpec1);
              if (i != this.latticeValues.size()) {
                escapeSet1 = this.latticeValues.get(i);
                escapeSet1.regSet.set(registerSpec.getReg());
                return escapeSet1;
              } 
              if (escapeSet1.getType() == Type.KNOWN_NULL) {
                escapeSet1 = new EscapeSet(registerSpec.getReg(), this.regCount, EscapeState.NONE);
              } else {
                escapeSet1 = new EscapeSet(registerSpec.getReg(), this.regCount, EscapeState.GLOBAL);
              } 
              this.latticeValues.add(escapeSet1);
              return escapeSet1;
            case 40:
              break;
          } 
        } else {
          EscapeSet escapeSet1 = new EscapeSet(registerSpec.getReg(), this.regCount, EscapeState.GLOBAL);
          this.latticeValues.add(escapeSet1);
          return escapeSet1;
        } 
      } else {
      
      }  
    EscapeSet escapeSet = new EscapeSet(registerSpec.getReg(), this.regCount, EscapeState.NONE);
    this.latticeValues.add(escapeSet);
    return escapeSet;
  }
  
  private void processPhiUse(SsaInsn paramSsaInsn, EscapeSet paramEscapeSet, ArrayList<RegisterSpec> paramArrayList) {
    EscapeSet escapeSet;
    int i = findSetIndex(paramSsaInsn.getResult());
    if (i != this.latticeValues.size()) {
      escapeSet = this.latticeValues.get(i);
      if (escapeSet != paramEscapeSet) {
        paramEscapeSet.replaceableArray = false;
        paramEscapeSet.regSet.or(escapeSet.regSet);
        if (paramEscapeSet.escape.compareTo(escapeSet.escape) < 0)
          paramEscapeSet.escape = escapeSet.escape; 
        replaceNode(paramEscapeSet, escapeSet);
        this.latticeValues.remove(i);
      } 
    } else {
      paramEscapeSet.regSet.set(escapeSet.getResult().getReg());
      paramArrayList.add(escapeSet.getResult());
    } 
  }
  
  private void processRegister(RegisterSpec paramRegisterSpec, EscapeSet paramEscapeSet) {
    ArrayList<RegisterSpec> arrayList = new ArrayList();
    arrayList.add(paramRegisterSpec);
    while (!arrayList.isEmpty()) {
      paramRegisterSpec = arrayList.remove(arrayList.size() - 1);
      for (SsaInsn ssaInsn : this.ssaMeth.getUseListForRegister(paramRegisterSpec.getReg())) {
        if (ssaInsn.getOpcode() == null) {
          processPhiUse(ssaInsn, paramEscapeSet, arrayList);
          continue;
        } 
        processUse(paramRegisterSpec, ssaInsn, paramEscapeSet, arrayList);
      } 
    } 
  }
  
  private void processUse(RegisterSpec paramRegisterSpec, SsaInsn paramSsaInsn, EscapeSet paramEscapeSet, ArrayList<RegisterSpec> paramArrayList) {
    RegisterSpecList registerSpecList;
    int i = paramSsaInsn.getOpcode().getOpcode();
    if (i != 2) {
      if (i != 33 && i != 35)
        if (i != 43 && i != 7 && i != 8) {
          if (i != 38) {
            if (i != 39) {
              switch (i) {
                default:
                  return;
                case 48:
                  paramEscapeSet.escape = EscapeState.GLOBAL;
                case 47:
                  if (paramSsaInsn.getSources().get(0).getTypeBearer().getBasicType() == 9) {
                    paramEscapeSet.replaceableArray = false;
                    registerSpecList = paramSsaInsn.getSources();
                    if (registerSpecList.get(0).getReg() == paramRegisterSpec.getReg()) {
                      i = findSetIndex(registerSpecList.get(1));
                      if (i != this.latticeValues.size()) {
                        EscapeSet escapeSet = this.latticeValues.get(i);
                        addEdge(escapeSet, paramEscapeSet);
                        if (paramEscapeSet.escape.compareTo(escapeSet.escape) < 0)
                          paramEscapeSet.escape = escapeSet.escape; 
                      } 
                    } else {
                      i = findSetIndex(registerSpecList.get(0));
                      if (i != this.latticeValues.size()) {
                        EscapeSet escapeSet = this.latticeValues.get(i);
                        addEdge(paramEscapeSet, escapeSet);
                        if (escapeSet.escape.compareTo(paramEscapeSet.escape) < 0)
                          escapeSet.escape = paramEscapeSet.escape; 
                      } 
                    } 
                  } 
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                  break;
              } 
            } else if (!registerSpecList.getSources().get(2).getTypeBearer().isConstant()) {
              paramEscapeSet.replaceableArray = false;
            } 
          } else if (!registerSpecList.getSources().get(1).getTypeBearer().isConstant()) {
            paramEscapeSet.replaceableArray = false;
          } 
        } else if (paramEscapeSet.escape.compareTo(EscapeState.METHOD) < 0) {
          paramEscapeSet.escape = EscapeState.METHOD;
        }  
      paramEscapeSet.escape = EscapeState.INTER;
    } 
    paramEscapeSet.regSet.set(registerSpecList.getResult().getReg());
    paramArrayList.add(registerSpecList.getResult());
  }
  
  private void replaceDef(SsaInsn paramSsaInsn1, SsaInsn paramSsaInsn2, int paramInt, ArrayList<RegisterSpec> paramArrayList) {
    Type type = paramSsaInsn1.getResult().getType();
    for (byte b = 0; b < paramInt; b++) {
      Constant constant = Zeroes.zeroFor(type.getComponentType());
      TypedConstant typedConstant = (TypedConstant)constant;
      RegisterSpec registerSpec = RegisterSpec.make(this.ssaMeth.makeNewSsaReg(), (TypeBearer)typedConstant);
      paramArrayList.add(registerSpec);
      insertPlainInsnBefore(paramSsaInsn1, RegisterSpecList.EMPTY, registerSpec, 5, constant);
    } 
  }
  
  private void replaceNode(EscapeSet paramEscapeSet1, EscapeSet paramEscapeSet2) {
    for (EscapeSet escapeSet : paramEscapeSet2.parentSets) {
      escapeSet.childSets.remove(paramEscapeSet2);
      escapeSet.childSets.add(paramEscapeSet1);
      paramEscapeSet1.parentSets.add(escapeSet);
    } 
    for (EscapeSet escapeSet : paramEscapeSet2.childSets) {
      escapeSet.parentSets.remove(paramEscapeSet2);
      escapeSet.parentSets.add(paramEscapeSet1);
      paramEscapeSet1.childSets.add(escapeSet);
    } 
  }
  
  private void replaceUse(SsaInsn paramSsaInsn1, SsaInsn paramSsaInsn2, ArrayList<RegisterSpec> paramArrayList, HashSet<SsaInsn> paramHashSet) {
    RegisterSpec registerSpec;
    ArrayList<TypeBearer> arrayList;
    RegisterSpec<SsaInsn> registerSpec1;
    int i = paramArrayList.size();
    int j = paramSsaInsn1.getOpcode().getOpcode();
    int k = 0;
    if (j != 34) {
      RegisterSpec<RegisterSpec> registerSpec2;
      if (j != 57) {
        if (j != 38) {
          if (j == 39) {
            RegisterSpec registerSpec3;
            RegisterSpecList registerSpecList = paramSsaInsn1.getSources();
            k = ((CstLiteralBits)registerSpecList.get(2).getTypeBearer()).getIntBits();
            if (k < i) {
              registerSpec3 = registerSpecList.get(0);
              registerSpec1 = registerSpec3.withReg(((RegisterSpec)paramArrayList.get(k)).getReg());
              insertPlainInsnBefore(paramSsaInsn1, RegisterSpecList.make(registerSpec3), registerSpec1, 2, null);
              paramArrayList.set(k, registerSpec1.withSimpleType());
            } else {
              insertExceptionThrow(paramSsaInsn1, registerSpec3.get(2), (HashSet<SsaInsn>)registerSpec1);
            } 
          } 
        } else {
          paramSsaInsn2 = getMoveForInsn(paramSsaInsn1);
          RegisterSpecList registerSpecList = paramSsaInsn1.getSources();
          k = ((CstLiteralBits)registerSpecList.get(1).getTypeBearer()).getIntBits();
          if (k < i) {
            registerSpec2 = paramArrayList.get(k);
            registerSpec = registerSpec2.withReg(paramSsaInsn2.getResult().getReg());
            insertPlainInsnBefore(paramSsaInsn2, RegisterSpecList.make(registerSpec2), registerSpec, 2, null);
          } else {
            insertExceptionThrow(paramSsaInsn2, registerSpec.get(1), (HashSet<SsaInsn>)registerSpec1);
            registerSpec1.add(paramSsaInsn2.getBlock().getInsns().get(2));
          } 
          registerSpec1.add(paramSsaInsn2);
        } 
      } else {
        arrayList = ((FillArrayDataInsn)registerSpec.getOriginalRopInsn()).getInitValues();
        while (k < i) {
          registerSpec1 = RegisterSpec.make(((RegisterSpec)registerSpec2.get(k)).getReg(), arrayList.get(k));
          insertPlainInsnBefore((SsaInsn)registerSpec, RegisterSpecList.EMPTY, registerSpec1, 5, (Constant)arrayList.get(k));
          registerSpec2.set(k, registerSpec1);
          k++;
        } 
      } 
    } else {
      TypeBearer typeBearer = arrayList.getSources().get(0).getTypeBearer();
      SsaInsn ssaInsn = getMoveForInsn((SsaInsn)registerSpec);
      insertPlainInsnBefore(ssaInsn, RegisterSpecList.EMPTY, ssaInsn.getResult(), 5, (Constant)typeBearer);
      registerSpec1.add(ssaInsn);
    } 
  }
  
  private void run() {
    this.ssaMeth.forEachBlockDepthFirstDom(new SsaBasicBlock.Visitor() {
          public void visitBlock(SsaBasicBlock param1SsaBasicBlock1, SsaBasicBlock param1SsaBasicBlock2) {
            param1SsaBasicBlock1.forEachInsn(new SsaInsn.Visitor() {
                  public void visitMoveInsn(NormalSsaInsn param2NormalSsaInsn) {}
                  
                  public void visitNonMoveInsn(NormalSsaInsn param2NormalSsaInsn) {
                    EscapeAnalysis.this.processInsn(param2NormalSsaInsn);
                  }
                  
                  public void visitPhiInsn(PhiInsn param2PhiInsn) {}
                });
          }
        });
    for (EscapeSet escapeSet : this.latticeValues) {
      if (escapeSet.escape != EscapeState.NONE)
        for (EscapeSet escapeSet1 : escapeSet.childSets) {
          if (escapeSet.escape.compareTo(escapeSet1.escape) > 0)
            escapeSet1.escape = escapeSet.escape; 
        }  
    } 
    scalarReplacement();
  }
  
  private void scalarReplacement() {
    for (EscapeSet escapeSet : this.latticeValues) {
      if (!escapeSet.replaceableArray || escapeSet.escape != EscapeState.NONE)
        continue; 
      int i = escapeSet.regSet.nextSetBit(0);
      ssaInsn1 = this.ssaMeth.getDefinitionForRegister(i);
      SsaInsn ssaInsn2 = getInsnForMove(ssaInsn1);
      int j = ((CstLiteralBits)ssaInsn2.getSources().get(0).getTypeBearer()).getIntBits();
      ArrayList<RegisterSpec> arrayList = new ArrayList(j);
      HashSet<SsaInsn> hashSet = new HashSet();
      replaceDef(ssaInsn1, ssaInsn2, j, arrayList);
      hashSet.add(ssaInsn2);
      hashSet.add(ssaInsn1);
      for (SsaInsn ssaInsn1 : this.ssaMeth.getUseListForRegister(i)) {
        replaceUse(ssaInsn1, ssaInsn2, arrayList, hashSet);
        hashSet.add(ssaInsn1);
      } 
      this.ssaMeth.deleteInsns(hashSet);
      this.ssaMeth.onInsnsChanged();
      SsaConverter.updateSsaMethod(this.ssaMeth, this.regCount);
      movePropagate();
    } 
  }
  
  static class EscapeSet {
    ArrayList<EscapeSet> childSets;
    
    EscapeAnalysis.EscapeState escape;
    
    ArrayList<EscapeSet> parentSets;
    
    BitSet regSet;
    
    boolean replaceableArray;
    
    EscapeSet(int param1Int1, int param1Int2, EscapeAnalysis.EscapeState param1EscapeState) {
      BitSet bitSet = new BitSet(param1Int2);
      this.regSet = bitSet;
      bitSet.set(param1Int1);
      this.escape = param1EscapeState;
      this.childSets = new ArrayList<EscapeSet>();
      this.parentSets = new ArrayList<EscapeSet>();
      this.replaceableArray = false;
    }
  }
  
  public enum EscapeState {
    GLOBAL, INTER, METHOD, NONE, TOP;
    
    static {
      METHOD = new EscapeState("METHOD", 2);
      INTER = new EscapeState("INTER", 3);
      EscapeState escapeState = new EscapeState("GLOBAL", 4);
      GLOBAL = escapeState;
      $VALUES = new EscapeState[] { TOP, NONE, METHOD, INTER, escapeState };
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\EscapeAnalysis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */