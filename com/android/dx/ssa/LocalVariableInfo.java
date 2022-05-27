package com.android.dx.ssa;

import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecSet;
import com.android.dx.util.MutabilityControl;
import java.util.ArrayList;
import java.util.HashMap;

public class LocalVariableInfo extends MutabilityControl {
  private final RegisterSpecSet[] blockStarts;
  
  private final RegisterSpecSet emptySet;
  
  private final HashMap<SsaInsn, RegisterSpec> insnAssignments;
  
  private final int regCount;
  
  public LocalVariableInfo(SsaMethod paramSsaMethod) {
    if (paramSsaMethod != null) {
      ArrayList<SsaBasicBlock> arrayList = paramSsaMethod.getBlocks();
      this.regCount = paramSsaMethod.getRegCount();
      this.emptySet = new RegisterSpecSet(this.regCount);
      this.blockStarts = new RegisterSpecSet[arrayList.size()];
      this.insnAssignments = new HashMap<SsaInsn, RegisterSpec>();
      this.emptySet.setImmutable();
      return;
    } 
    throw new NullPointerException("method == null");
  }
  
  private RegisterSpecSet getStarts0(int paramInt) {
    try {
      return this.blockStarts[paramInt];
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
      throw new IllegalArgumentException("bogus index");
    } 
  }
  
  public void addAssignment(SsaInsn paramSsaInsn, RegisterSpec paramRegisterSpec) {
    throwIfImmutable();
    if (paramSsaInsn != null) {
      if (paramRegisterSpec != null) {
        this.insnAssignments.put(paramSsaInsn, paramRegisterSpec);
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
  
  public RegisterSpec getAssignment(SsaInsn paramSsaInsn) {
    return this.insnAssignments.get(paramSsaInsn);
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
  
  public RegisterSpecSet getStarts(SsaBasicBlock paramSsaBasicBlock) {
    return getStarts(paramSsaBasicBlock.getIndex());
  }
  
  public boolean mergeStarts(int paramInt, RegisterSpecSet paramRegisterSpecSet) {
    RegisterSpecSet registerSpecSet1 = getStarts0(paramInt);
    if (registerSpecSet1 == null) {
      setStarts(paramInt, paramRegisterSpecSet);
      return true;
    } 
    RegisterSpecSet registerSpecSet2 = registerSpecSet1.mutableCopy();
    registerSpecSet2.intersect(paramRegisterSpecSet, true);
    if (registerSpecSet1.equals(registerSpecSet2))
      return false; 
    registerSpecSet2.setImmutable();
    setStarts(paramInt, registerSpecSet2);
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
        throw new IllegalArgumentException("bogus index");
      }  
    throw new NullPointerException("specs == null");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\ssa\LocalVariableInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */