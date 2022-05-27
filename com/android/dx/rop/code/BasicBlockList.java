package com.android.dx.rop.code;

import com.android.dx.rop.type.StdTypeList;
import com.android.dx.util.Hex;
import com.android.dx.util.IntList;
import com.android.dx.util.LabeledList;

public final class BasicBlockList extends LabeledList {
  private int regCount = -1;
  
  public BasicBlockList(int paramInt) {
    super(paramInt);
  }
  
  private BasicBlockList(BasicBlockList paramBasicBlockList) {
    super(paramBasicBlockList);
  }
  
  public boolean catchesEqual(BasicBlock paramBasicBlock1, BasicBlock paramBasicBlock2) {
    if (!StdTypeList.equalContents(paramBasicBlock1.getExceptionHandlerTypes(), paramBasicBlock2.getExceptionHandlerTypes()))
      return false; 
    IntList intList1 = paramBasicBlock1.getSuccessors();
    IntList intList2 = paramBasicBlock2.getSuccessors();
    int i = intList1.size();
    int j = paramBasicBlock1.getPrimarySuccessor();
    int k = paramBasicBlock2.getPrimarySuccessor();
    if ((j == -1 || k == -1) && j != k)
      return false; 
    for (byte b = 0; b < i; b++) {
      int m = intList1.get(b);
      int n = intList2.get(b);
      if (m == j) {
        if (n != k)
          return false; 
      } else if (m != n) {
        return false;
      } 
    } 
    return true;
  }
  
  public void forEachInsn(Insn.Visitor paramVisitor) {
    int i = size();
    for (byte b = 0; b < i; b++)
      get(b).getInsns().forEach(paramVisitor); 
  }
  
  public BasicBlock get(int paramInt) {
    return (BasicBlock)get0(paramInt);
  }
  
  public int getEffectiveInstructionCount() {
    int i = size();
    byte b = 0;
    int j;
    for (j = 0; b < i; j = k) {
      BasicBlock basicBlock = (BasicBlock)getOrNull0(b);
      int k = j;
      if (basicBlock != null) {
        InsnList insnList = basicBlock.getInsns();
        int m = insnList.size();
        byte b1 = 0;
        while (true) {
          k = j;
          if (b1 < m) {
            k = j;
            if (insnList.get(b1).getOpcode().getOpcode() != 54)
              k = j + 1; 
            b1++;
            j = k;
            continue;
          } 
          break;
        } 
      } 
      b++;
    } 
    return j;
  }
  
  public int getInstructionCount() {
    int i = size();
    byte b = 0;
    int j;
    for (j = 0; b < i; j = k) {
      BasicBlock basicBlock = (BasicBlock)getOrNull0(b);
      int k = j;
      if (basicBlock != null)
        k = j + basicBlock.getInsns().size(); 
      b++;
    } 
    return j;
  }
  
  public BasicBlockList getMutableCopy() {
    return new BasicBlockList(this);
  }
  
  public int getRegCount() {
    if (this.regCount == -1) {
      RegCountVisitor regCountVisitor = new RegCountVisitor();
      forEachInsn(regCountVisitor);
      this.regCount = regCountVisitor.getRegCount();
    } 
    return this.regCount;
  }
  
  public BasicBlock labelToBlock(int paramInt) {
    int i = indexOfLabel(paramInt);
    if (i >= 0)
      return get(i); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("no such label: ");
    stringBuilder.append(Hex.u2(paramInt));
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public BasicBlock preferredSuccessorOf(BasicBlock paramBasicBlock) {
    int i = paramBasicBlock.getPrimarySuccessor();
    IntList intList = paramBasicBlock.getSuccessors();
    int j = intList.size();
    return (j != 0) ? ((j != 1) ? ((i != -1) ? labelToBlock(i) : labelToBlock(intList.get(0))) : labelToBlock(intList.get(0))) : null;
  }
  
  public void set(int paramInt, BasicBlock paramBasicBlock) {
    set(paramInt, paramBasicBlock);
    this.regCount = -1;
  }
  
  public BasicBlockList withRegisterOffset(int paramInt) {
    int i = size();
    BasicBlockList basicBlockList = new BasicBlockList(i);
    for (byte b = 0; b < i; b++) {
      BasicBlock basicBlock = (BasicBlock)get0(b);
      if (basicBlock != null)
        basicBlockList.set(b, basicBlock.withRegisterOffset(paramInt)); 
    } 
    if (isImmutable())
      basicBlockList.setImmutable(); 
    return basicBlockList;
  }
  
  private static class RegCountVisitor implements Insn.Visitor {
    private int regCount = 0;
    
    private void processReg(RegisterSpec param1RegisterSpec) {
      int i = param1RegisterSpec.getNextReg();
      if (i > this.regCount)
        this.regCount = i; 
    }
    
    private void visit(Insn param1Insn) {
      RegisterSpec registerSpec = param1Insn.getResult();
      if (registerSpec != null)
        processReg(registerSpec); 
      RegisterSpecList registerSpecList = param1Insn.getSources();
      int i = registerSpecList.size();
      for (byte b = 0; b < i; b++)
        processReg(registerSpecList.get(b)); 
    }
    
    public int getRegCount() {
      return this.regCount;
    }
    
    public void visitFillArrayDataInsn(FillArrayDataInsn param1FillArrayDataInsn) {
      visit(param1FillArrayDataInsn);
    }
    
    public void visitInvokePolymorphicInsn(InvokePolymorphicInsn param1InvokePolymorphicInsn) {
      visit(param1InvokePolymorphicInsn);
    }
    
    public void visitPlainCstInsn(PlainCstInsn param1PlainCstInsn) {
      visit(param1PlainCstInsn);
    }
    
    public void visitPlainInsn(PlainInsn param1PlainInsn) {
      visit(param1PlainInsn);
    }
    
    public void visitSwitchInsn(SwitchInsn param1SwitchInsn) {
      visit(param1SwitchInsn);
    }
    
    public void visitThrowingCstInsn(ThrowingCstInsn param1ThrowingCstInsn) {
      visit(param1ThrowingCstInsn);
    }
    
    public void visitThrowingInsn(ThrowingInsn param1ThrowingInsn) {
      visit(param1ThrowingInsn);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\BasicBlockList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */