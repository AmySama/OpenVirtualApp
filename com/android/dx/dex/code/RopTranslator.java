package com.android.dx.dex.code;

import com.android.dx.dex.DexOptions;
import com.android.dx.rop.code.BasicBlock;
import com.android.dx.rop.code.BasicBlockList;
import com.android.dx.rop.code.FillArrayDataInsn;
import com.android.dx.rop.code.Insn;
import com.android.dx.rop.code.InvokePolymorphicInsn;
import com.android.dx.rop.code.LocalVariableInfo;
import com.android.dx.rop.code.PlainCstInsn;
import com.android.dx.rop.code.PlainInsn;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.RegisterSpecSet;
import com.android.dx.rop.code.Rop;
import com.android.dx.rop.code.RopMethod;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.rop.code.SwitchInsn;
import com.android.dx.rop.code.ThrowingCstInsn;
import com.android.dx.rop.code.ThrowingInsn;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstInteger;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.util.Bits;
import com.android.dx.util.IntList;
import java.util.ArrayList;

public final class RopTranslator {
  private final BlockAddresses addresses;
  
  private final DexOptions dexOptions;
  
  private final LocalVariableInfo locals;
  
  private final RopMethod method;
  
  private int[] order;
  
  private final OutputCollector output;
  
  private final int paramSize;
  
  private final boolean paramsAreInOrder;
  
  private final int positionInfo;
  
  private final int regCount;
  
  private final TranslationVisitor translationVisitor;
  
  private RopTranslator(RopMethod paramRopMethod, int paramInt1, LocalVariableInfo paramLocalVariableInfo, int paramInt2, DexOptions paramDexOptions) {
    this.dexOptions = paramDexOptions;
    this.method = paramRopMethod;
    this.positionInfo = paramInt1;
    this.locals = paramLocalVariableInfo;
    this.addresses = new BlockAddresses(paramRopMethod);
    this.paramSize = paramInt2;
    this.order = null;
    this.paramsAreInOrder = calculateParamsAreInOrder(paramRopMethod, paramInt2);
    BasicBlockList basicBlockList = paramRopMethod.getBlocks();
    int i = basicBlockList.size();
    int j = i * 3;
    int k = basicBlockList.getInstructionCount() + j;
    paramInt1 = k;
    if (paramLocalVariableInfo != null)
      paramInt1 = k + i + paramLocalVariableInfo.getAssignmentCount(); 
    i = basicBlockList.getRegCount();
    if (this.paramsAreInOrder) {
      k = 0;
    } else {
      k = this.paramSize;
    } 
    k = i + k;
    this.regCount = k;
    OutputCollector outputCollector = new OutputCollector(paramDexOptions, paramInt1, j, k, paramInt2);
    this.output = outputCollector;
    if (paramLocalVariableInfo != null) {
      this.translationVisitor = new LocalVariableAwareTranslationVisitor(outputCollector, paramLocalVariableInfo);
    } else {
      this.translationVisitor = new TranslationVisitor(outputCollector);
    } 
  }
  
  private static boolean calculateParamsAreInOrder(RopMethod paramRopMethod, final int paramSize) {
    final boolean[] paramsAreInOrder = new boolean[1];
    arrayOfBoolean[0] = true;
    final int initialRegCount = paramRopMethod.getBlocks().getRegCount();
    paramRopMethod.getBlocks().forEachInsn((Insn.Visitor)new Insn.BaseVisitor() {
          public void visitPlainCstInsn(PlainCstInsn param1PlainCstInsn) {
            if (param1PlainCstInsn.getOpcode().getOpcode() == 3) {
              boolean bool;
              int i = ((CstInteger)param1PlainCstInsn.getConstant()).getValue();
              boolean[] arrayOfBoolean = paramsAreInOrder;
              if (arrayOfBoolean[0] && initialRegCount - paramSize + i == param1PlainCstInsn.getResult().getReg()) {
                bool = true;
              } else {
                bool = false;
              } 
              arrayOfBoolean[0] = bool;
            } 
          }
        });
    return arrayOfBoolean[0];
  }
  
  private static RegisterSpecList getRegs(Insn paramInsn) {
    return getRegs(paramInsn, paramInsn.getResult());
  }
  
  private static RegisterSpecList getRegs(Insn paramInsn, RegisterSpec paramRegisterSpec) {
    RegisterSpecList registerSpecList1 = paramInsn.getSources();
    RegisterSpecList registerSpecList2 = registerSpecList1;
    if (paramInsn.getOpcode().isCommutative()) {
      registerSpecList2 = registerSpecList1;
      if (registerSpecList1.size() == 2) {
        registerSpecList2 = registerSpecList1;
        if (paramRegisterSpec.getReg() == registerSpecList1.get(1).getReg())
          registerSpecList2 = RegisterSpecList.make(registerSpecList1.get(1), registerSpecList1.get(0)); 
      } 
    } 
    return (paramRegisterSpec == null) ? registerSpecList2 : registerSpecList2.withFirst(paramRegisterSpec);
  }
  
  private void outputBlock(BasicBlock paramBasicBlock, int paramInt) {
    CodeAddress codeAddress = this.addresses.getStart(paramBasicBlock);
    this.output.add(codeAddress);
    LocalVariableInfo localVariableInfo = this.locals;
    if (localVariableInfo != null) {
      RegisterSpecSet registerSpecSet = localVariableInfo.getStarts(paramBasicBlock);
      this.output.add(new LocalSnapshot(codeAddress.getPosition(), registerSpecSet));
    } 
    this.translationVisitor.setBlock(paramBasicBlock, this.addresses.getLast(paramBasicBlock));
    paramBasicBlock.getInsns().forEach(this.translationVisitor);
    this.output.add(this.addresses.getEnd(paramBasicBlock));
    int i = paramBasicBlock.getPrimarySuccessor();
    Insn insn = paramBasicBlock.getLastInsn();
    if (i >= 0 && i != paramInt)
      if (insn.getOpcode().getBranchingness() == 4 && paramBasicBlock.getSecondarySuccessor() == paramInt) {
        this.output.reverseBranch(1, this.addresses.getStart(i));
      } else {
        TargetInsn targetInsn = new TargetInsn(Dops.GOTO, insn.getPosition(), RegisterSpecList.EMPTY, this.addresses.getStart(i));
        this.output.add(targetInsn);
      }  
  }
  
  private void outputInstructions() {
    BasicBlockList basicBlockList = this.method.getBlocks();
    int[] arrayOfInt = this.order;
    int i = arrayOfInt.length;
    int j;
    for (j = 0; j < i; j = k) {
      int m;
      int k = j + 1;
      if (k == arrayOfInt.length) {
        m = -1;
      } else {
        m = arrayOfInt[k];
      } 
      outputBlock(basicBlockList.labelToBlock(arrayOfInt[j]), m);
    } 
  }
  
  private void pickOrder() {
    BasicBlockList basicBlockList = this.method.getBlocks();
    int i = basicBlockList.size();
    int j = basicBlockList.getMaxLabel();
    int[] arrayOfInt1 = Bits.makeBitSet(j);
    int[] arrayOfInt2 = Bits.makeBitSet(j);
    for (j = 0; j < i; j++)
      Bits.set(arrayOfInt1, basicBlockList.get(j).getLabel()); 
    int[] arrayOfInt3 = new int[i];
    int k = this.method.getFirstLabel();
    int m = 0;
    while (k != -1) {
      int n;
      label56: while (true) {
        IntList intList = this.method.labelToPredecessors(k);
        int i1 = intList.size();
        byte b = 0;
        while (true) {
          j = k;
          n = m;
          if (b < i1) {
            j = intList.get(b);
            if (Bits.get(arrayOfInt2, j)) {
              j = k;
              n = m;
              break;
            } 
            if (Bits.get(arrayOfInt1, j) && basicBlockList.labelToBlock(j).getPrimarySuccessor() == k) {
              Bits.set(arrayOfInt2, j);
              k = j;
              continue label56;
            } 
            b++;
            continue;
          } 
          break;
        } 
        break;
      } 
      label57: while (true) {
        m = n;
        if (j != -1) {
          Bits.clear(arrayOfInt1, j);
          Bits.clear(arrayOfInt2, j);
          arrayOfInt3[n] = j;
          m = n + 1;
          BasicBlock basicBlock1 = basicBlockList.labelToBlock(j);
          BasicBlock basicBlock2 = basicBlockList.preferredSuccessorOf(basicBlock1);
          if (basicBlock2 == null)
            break; 
          k = basicBlock2.getLabel();
          j = basicBlock1.getPrimarySuccessor();
          if (Bits.get(arrayOfInt1, k)) {
            j = k;
            n = m;
            continue;
          } 
          if (j != k && j >= 0 && Bits.get(arrayOfInt1, j)) {
            n = m;
            continue;
          } 
          IntList intList = basicBlock1.getSuccessors();
          n = intList.size();
          for (j = 0; j < n; j++) {
            k = intList.get(j);
            if (Bits.get(arrayOfInt1, k)) {
              j = k;
              n = m;
              continue label57;
            } 
          } 
          j = -1;
          n = m;
          continue;
        } 
        break;
      } 
      k = Bits.findFirst(arrayOfInt1, 0);
    } 
    if (m == i) {
      this.order = arrayOfInt3;
      return;
    } 
    throw new RuntimeException("shouldn't happen");
  }
  
  public static DalvCode translate(RopMethod paramRopMethod, int paramInt1, LocalVariableInfo paramLocalVariableInfo, int paramInt2, DexOptions paramDexOptions) {
    return (new RopTranslator(paramRopMethod, paramInt1, paramLocalVariableInfo, paramInt2, paramDexOptions)).translateAndGetResult();
  }
  
  private DalvCode translateAndGetResult() {
    pickOrder();
    outputInstructions();
    StdCatchBuilder stdCatchBuilder = new StdCatchBuilder(this.method, this.order, this.addresses);
    return new DalvCode(this.positionInfo, this.output.getFinisher(), stdCatchBuilder);
  }
  
  private class LocalVariableAwareTranslationVisitor extends TranslationVisitor {
    private final LocalVariableInfo locals;
    
    public LocalVariableAwareTranslationVisitor(OutputCollector param1OutputCollector, LocalVariableInfo param1LocalVariableInfo) {
      super(param1OutputCollector);
      this.locals = param1LocalVariableInfo;
    }
    
    public void addIntroductionIfNecessary(Insn param1Insn) {
      RegisterSpec registerSpec = this.locals.getAssignment(param1Insn);
      if (registerSpec != null)
        addOutput(new LocalStart(param1Insn.getPosition(), registerSpec)); 
    }
    
    public void visitPlainCstInsn(PlainCstInsn param1PlainCstInsn) {
      super.visitPlainCstInsn(param1PlainCstInsn);
      addIntroductionIfNecessary((Insn)param1PlainCstInsn);
    }
    
    public void visitPlainInsn(PlainInsn param1PlainInsn) {
      super.visitPlainInsn(param1PlainInsn);
      addIntroductionIfNecessary((Insn)param1PlainInsn);
    }
    
    public void visitSwitchInsn(SwitchInsn param1SwitchInsn) {
      super.visitSwitchInsn(param1SwitchInsn);
      addIntroductionIfNecessary((Insn)param1SwitchInsn);
    }
    
    public void visitThrowingCstInsn(ThrowingCstInsn param1ThrowingCstInsn) {
      super.visitThrowingCstInsn(param1ThrowingCstInsn);
      addIntroductionIfNecessary((Insn)param1ThrowingCstInsn);
    }
    
    public void visitThrowingInsn(ThrowingInsn param1ThrowingInsn) {
      super.visitThrowingInsn(param1ThrowingInsn);
      addIntroductionIfNecessary((Insn)param1ThrowingInsn);
    }
  }
  
  private class TranslationVisitor implements Insn.Visitor {
    private BasicBlock block;
    
    private CodeAddress lastAddress;
    
    private final OutputCollector output;
    
    public TranslationVisitor(OutputCollector param1OutputCollector) {
      this.output = param1OutputCollector;
    }
    
    private RegisterSpec getNextMoveResultPseudo() {
      int i = this.block.getPrimarySuccessor();
      if (i < 0)
        return null; 
      Insn insn = RopTranslator.this.method.getBlocks().labelToBlock(i).getInsns().get(0);
      return (insn.getOpcode().getOpcode() != 56) ? null : insn.getResult();
    }
    
    protected void addOutput(DalvInsn param1DalvInsn) {
      this.output.add(param1DalvInsn);
    }
    
    protected void addOutputSuffix(DalvInsn param1DalvInsn) {
      this.output.addSuffix(param1DalvInsn);
    }
    
    public void setBlock(BasicBlock param1BasicBlock, CodeAddress param1CodeAddress) {
      this.block = param1BasicBlock;
      this.lastAddress = param1CodeAddress;
    }
    
    public void visitFillArrayDataInsn(FillArrayDataInsn param1FillArrayDataInsn) {
      SourcePosition sourcePosition = param1FillArrayDataInsn.getPosition();
      Constant constant = param1FillArrayDataInsn.getConstant();
      ArrayList<Constant> arrayList = param1FillArrayDataInsn.getInitValues();
      if (param1FillArrayDataInsn.getOpcode().getBranchingness() == 1) {
        CodeAddress codeAddress = new CodeAddress(sourcePosition);
        ArrayData arrayData = new ArrayData(sourcePosition, this.lastAddress, arrayList, constant);
        TargetInsn targetInsn = new TargetInsn(Dops.FILL_ARRAY_DATA, sourcePosition, RopTranslator.getRegs((Insn)param1FillArrayDataInsn), codeAddress);
        addOutput(this.lastAddress);
        addOutput(targetInsn);
        addOutputSuffix(new OddSpacer(sourcePosition));
        addOutputSuffix(codeAddress);
        addOutputSuffix(arrayData);
        return;
      } 
      throw new RuntimeException("shouldn't happen");
    }
    
    public void visitInvokePolymorphicInsn(InvokePolymorphicInsn param1InvokePolymorphicInsn) {
      SourcePosition sourcePosition = param1InvokePolymorphicInsn.getPosition();
      Dop dop = RopToDop.dopFor((Insn)param1InvokePolymorphicInsn);
      Rop rop = param1InvokePolymorphicInsn.getOpcode();
      if (rop.getBranchingness() == 6) {
        if (rop.isCallLike()) {
          addOutput(this.lastAddress);
          addOutput(new MultiCstInsn(dop, sourcePosition, param1InvokePolymorphicInsn.getSources(), new Constant[] { (Constant)param1InvokePolymorphicInsn.getPolymorphicMethod(), (Constant)param1InvokePolymorphicInsn.getCallSiteProto() }));
          return;
        } 
        throw new RuntimeException("Expected call-like operation");
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Expected BRANCH_THROW got ");
      stringBuilder.append(rop.getBranchingness());
      throw new RuntimeException(stringBuilder.toString());
    }
    
    public void visitPlainCstInsn(PlainCstInsn param1PlainCstInsn) {
      SourcePosition sourcePosition = param1PlainCstInsn.getPosition();
      Dop dop = RopToDop.dopFor((Insn)param1PlainCstInsn);
      Rop rop = param1PlainCstInsn.getOpcode();
      int i = rop.getOpcode();
      if (rop.getBranchingness() == 1) {
        if (i == 3) {
          if (!RopTranslator.this.paramsAreInOrder) {
            RegisterSpec registerSpec = param1PlainCstInsn.getResult();
            i = ((CstInteger)param1PlainCstInsn.getConstant()).getValue();
            addOutput(new SimpleInsn(dop, sourcePosition, RegisterSpecList.make(registerSpec, RegisterSpec.make(RopTranslator.this.regCount - RopTranslator.this.paramSize + i, (TypeBearer)registerSpec.getType()))));
          } 
        } else {
          addOutput(new CstInsn(dop, sourcePosition, RopTranslator.getRegs((Insn)param1PlainCstInsn), param1PlainCstInsn.getConstant()));
        } 
        return;
      } 
      throw new RuntimeException("shouldn't happen");
    }
    
    public void visitPlainInsn(PlainInsn param1PlainInsn) {
      TargetInsn targetInsn;
      Rop rop = param1PlainInsn.getOpcode();
      if (rop.getOpcode() == 54)
        return; 
      if (rop.getOpcode() == 56)
        return; 
      SourcePosition sourcePosition = param1PlainInsn.getPosition();
      Dop dop = RopToDop.dopFor((Insn)param1PlainInsn);
      int i = rop.getBranchingness();
      if (i != 1 && i != 2)
        if (i != 3) {
          if (i != 4) {
            if (i != 6)
              throw new RuntimeException("shouldn't happen"); 
          } else {
            i = this.block.getSuccessors().get(1);
            targetInsn = new TargetInsn(dop, sourcePosition, RopTranslator.getRegs((Insn)param1PlainInsn), RopTranslator.this.addresses.getStart(i));
            addOutput(targetInsn);
          } 
        } else {
          return;
        }  
      SimpleInsn simpleInsn = new SimpleInsn(dop, sourcePosition, RopTranslator.getRegs((Insn)targetInsn));
      addOutput(simpleInsn);
    }
    
    public void visitSwitchInsn(SwitchInsn param1SwitchInsn) {
      SourcePosition sourcePosition = param1SwitchInsn.getPosition();
      IntList intList1 = param1SwitchInsn.getCases();
      IntList intList2 = this.block.getSuccessors();
      int i = intList1.size();
      int j = intList2.size();
      int k = this.block.getPrimarySuccessor();
      if (i == j - 1 && k == intList2.get(i)) {
        Dop dop;
        CodeAddress[] arrayOfCodeAddress = new CodeAddress[i];
        for (k = 0; k < i; k++) {
          j = intList2.get(k);
          arrayOfCodeAddress[k] = RopTranslator.this.addresses.getStart(j);
        } 
        CodeAddress codeAddress1 = new CodeAddress(sourcePosition);
        CodeAddress codeAddress2 = new CodeAddress(this.lastAddress.getPosition(), true);
        SwitchData switchData = new SwitchData(sourcePosition, codeAddress2, intList1, arrayOfCodeAddress);
        if (switchData.isPacked()) {
          dop = Dops.PACKED_SWITCH;
        } else {
          dop = Dops.SPARSE_SWITCH;
        } 
        TargetInsn targetInsn = new TargetInsn(dop, sourcePosition, RopTranslator.getRegs((Insn)param1SwitchInsn), codeAddress1);
        addOutput(codeAddress2);
        addOutput(targetInsn);
        addOutputSuffix(new OddSpacer(sourcePosition));
        addOutputSuffix(codeAddress1);
        addOutputSuffix(switchData);
        return;
      } 
      throw new RuntimeException("shouldn't happen");
    }
    
    public void visitThrowingCstInsn(ThrowingCstInsn param1ThrowingCstInsn) {
      StringBuilder stringBuilder2;
      SourcePosition sourcePosition = param1ThrowingCstInsn.getPosition();
      Dop dop = RopToDop.dopFor((Insn)param1ThrowingCstInsn);
      Rop rop = param1ThrowingCstInsn.getOpcode();
      Constant constant = param1ThrowingCstInsn.getConstant();
      if (rop.getBranchingness() == 6) {
        addOutput(this.lastAddress);
        if (rop.isCallLike()) {
          addOutput(new CstInsn(dop, sourcePosition, param1ThrowingCstInsn.getSources(), constant));
        } else {
          CstInsn cstInsn;
          boolean bool2;
          RegisterSpec registerSpec = getNextMoveResultPseudo();
          RegisterSpecList registerSpecList = RopTranslator.getRegs((Insn)param1ThrowingCstInsn, registerSpec);
          boolean bool = dop.hasResult();
          boolean bool1 = false;
          if (bool || rop.getOpcode() == 43) {
            bool2 = true;
          } else {
            bool2 = false;
          } 
          if (registerSpec != null)
            bool1 = true; 
          if (bool2 == bool1) {
            if (rop.getOpcode() == 41 && dop.getOpcode() != 35) {
              SimpleInsn simpleInsn = new SimpleInsn(dop, sourcePosition, registerSpecList);
            } else {
              cstInsn = new CstInsn(dop, sourcePosition, registerSpecList, constant);
            } 
            addOutput(cstInsn);
            return;
          } 
          stringBuilder2 = new StringBuilder();
          stringBuilder2.append("Insn with result/move-result-pseudo mismatch ");
          stringBuilder2.append(cstInsn);
          throw new RuntimeException(stringBuilder2.toString());
        } 
        return;
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Expected BRANCH_THROW got ");
      stringBuilder1.append(stringBuilder2.getBranchingness());
      throw new RuntimeException(stringBuilder1.toString());
    }
    
    public void visitThrowingInsn(ThrowingInsn param1ThrowingInsn) {
      SourcePosition sourcePosition = param1ThrowingInsn.getPosition();
      Dop dop = RopToDop.dopFor((Insn)param1ThrowingInsn);
      if (param1ThrowingInsn.getOpcode().getBranchingness() == 6) {
        boolean bool2;
        RegisterSpec registerSpec = getNextMoveResultPseudo();
        boolean bool1 = dop.hasResult();
        if (registerSpec != null) {
          bool2 = true;
        } else {
          bool2 = false;
        } 
        if (bool1 == bool2) {
          addOutput(this.lastAddress);
          addOutput(new SimpleInsn(dop, sourcePosition, RopTranslator.getRegs((Insn)param1ThrowingInsn, registerSpec)));
          return;
        } 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Insn with result/move-result-pseudo mismatch");
        stringBuilder.append(param1ThrowingInsn);
        throw new RuntimeException(stringBuilder.toString());
      } 
      throw new RuntimeException("shouldn't happen");
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\RopTranslator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */