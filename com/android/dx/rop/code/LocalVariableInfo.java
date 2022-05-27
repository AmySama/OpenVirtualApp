package com.android.dx.rop.code;

import com.android.dx.util.MutabilityControl;
import java.util.HashMap;

public final class LocalVariableInfo extends MutabilityControl {
  private final RegisterSpecSet[] blockStarts;
  
  private final RegisterSpecSet emptySet;
  
  private final HashMap<Insn, RegisterSpec> insnAssignments;
  
  private final int regCount;
  
  public LocalVariableInfo(RopMethod paramRopMethod) {
    if (paramRopMethod != null) {
      BasicBlockList basicBlockList = paramRopMethod.getBlocks();
      int i = basicBlockList.getMaxLabel();
      this.regCount = basicBlockList.getRegCount();
      this.emptySet = new RegisterSpecSet(this.regCount);
      this.blockStarts = new RegisterSpecSet[i];
      this.insnAssignments = new HashMap<Insn, RegisterSpec>(basicBlockList.getInstructionCount());
      this.emptySet.setImmutable();
      return;
    } 
    throw new NullPointerException("method == null");
  }
  
  private RegisterSpecSet getStarts0(int paramInt) {
    try {
      return this.blockStarts[paramInt];
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      throw new IllegalArgumentException("bogus label");
    } 
  }
  
  public void addAssignment(Insn paramInsn, RegisterSpec paramRegisterSpec) {
    throwIfImmutable();
    if (paramInsn != null) {
      if (paramRegisterSpec != null) {
        this.insnAssignments.put(paramInsn, paramRegisterSpec);
        return;
      } 
      throw new NullPointerException("spec == null");
    } 
    throw new NullPointerException("insn == null");
  }
  
  public void debugDump() {
    byte b = 0;
    while (true) {
      RegisterSpecSet[] arrayOfRegisterSpecSet = this.blockStarts;
      if (b < arrayOfRegisterSpecSet.length) {
        if (arrayOfRegisterSpecSet[b] != null)
          if (arrayOfRegisterSpecSet[b] == this.emptySet) {
            System.out.printf("%04x: empty set\n", new Object[] { Integer.valueOf(b) });
          } else {
            System.out.printf("%04x: %s\n", new Object[] { Integer.valueOf(b), this.blockStarts[b] });
          }  
        b++;
        continue;
      } 
      break;
    } 
  }
  
  public RegisterSpec getAssignment(Insn paramInsn) {
    return this.insnAssignments.get(paramInsn);
  }
  
  public int getAssignmentCount() {
    return this.insnAssignments.size();
  }
  
  public RegisterSpecSet getStarts(int paramInt) {
    RegisterSpecSet registerSpecSet = getStarts0(paramInt);
    if (registerSpecSet == null)
      registerSpecSet = this.emptySet; 
    return registerSpecSet;
  }
  
  public RegisterSpecSet getStarts(BasicBlock paramBasicBlock) {
    return getStarts(paramBasicBlock.getLabel());
  }
  
  public boolean mergeStarts(int paramInt, RegisterSpecSet paramRegisterSpecSet) {
    RegisterSpecSet registerSpecSet1 = getStarts0(paramInt);
    if (registerSpecSet1 == null) {
      setStarts(paramInt, paramRegisterSpecSet);
      return true;
    } 
    RegisterSpecSet registerSpecSet2 = registerSpecSet1.mutableCopy();
    if (registerSpecSet1.size() != 0) {
      registerSpecSet2.intersect(paramRegisterSpecSet, true);
      paramRegisterSpecSet = registerSpecSet2;
    } else {
      paramRegisterSpecSet = paramRegisterSpecSet.mutableCopy();
    } 
    if (registerSpecSet1.equals(paramRegisterSpecSet))
      return false; 
    paramRegisterSpecSet.setImmutable();
    setStarts(paramInt, paramRegisterSpecSet);
    return true;
  }
  
  public RegisterSpecSet mutableCopyOfStarts(int paramInt) {
    RegisterSpecSet registerSpecSet = getStarts0(paramInt);
    if (registerSpecSet != null) {
      registerSpecSet = registerSpecSet.mutableCopy();
    } else {
      registerSpecSet = new RegisterSpecSet(this.regCount);
    } 
    return registerSpecSet;
  }
  
  public void setStarts(int paramInt, RegisterSpecSet paramRegisterSpecSet) {
    throwIfImmutable();
    if (paramRegisterSpecSet != null)
      try {
        this.blockStarts[paramInt] = paramRegisterSpecSet;
        return;
      } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        throw new IllegalArgumentException("bogus label");
      }  
    throw new NullPointerException("specs == null");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\LocalVariableInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */