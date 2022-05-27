package com.android.dx.rop.code;

import com.android.dx.rop.type.TypeList;
import com.android.dx.util.Hex;
import com.android.dx.util.IntList;
import com.android.dx.util.LabeledItem;

public final class BasicBlock implements LabeledItem {
  private final InsnList insns;
  
  private final int label;
  
  private final int primarySuccessor;
  
  private final IntList successors;
  
  public BasicBlock(int paramInt1, InsnList paramInsnList, IntList paramIntList, int paramInt2) {
    if (paramInt1 >= 0)
      try {
        paramInsnList.throwIfMutable();
        int i = paramInsnList.size();
        if (i != 0) {
          StringBuilder stringBuilder;
          int j = i - 2;
          while (j >= 0) {
            if (paramInsnList.get(j).getOpcode().getBranchingness() == 1) {
              j--;
              continue;
            } 
            stringBuilder = new StringBuilder();
            stringBuilder.append("insns[");
            stringBuilder.append(j);
            stringBuilder.append("] is a branch or can throw");
            throw new IllegalArgumentException(stringBuilder.toString());
          } 
          if (stringBuilder.get(i - 1).getOpcode().getBranchingness() != 1)
            try {
              paramIntList.throwIfMutable();
              if (paramInt2 >= -1) {
                if (paramInt2 < 0 || paramIntList.contains(paramInt2)) {
                  this.label = paramInt1;
                  this.insns = (InsnList)stringBuilder;
                  this.successors = paramIntList;
                  this.primarySuccessor = paramInt2;
                  return;
                } 
                stringBuilder = new StringBuilder();
                stringBuilder.append("primarySuccessor ");
                stringBuilder.append(paramInt2);
                stringBuilder.append(" not in successors ");
                stringBuilder.append(paramIntList);
                throw new IllegalArgumentException(stringBuilder.toString());
              } 
              throw new IllegalArgumentException("primarySuccessor < -1");
            } catch (NullPointerException nullPointerException) {
              throw new NullPointerException("successors == null");
            }  
          throw new IllegalArgumentException("insns does not end with a branch or throwing instruction");
        } 
        throw new IllegalArgumentException("insns.size() == 0");
      } catch (NullPointerException nullPointerException) {
        throw new NullPointerException("insns == null");
      }  
    throw new IllegalArgumentException("label < 0");
  }
  
  public boolean canThrow() {
    return this.insns.getLast().canThrow();
  }
  
  public boolean equals(Object paramObject) {
    boolean bool;
    if (this == paramObject) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public TypeList getExceptionHandlerTypes() {
    return this.insns.getLast().getCatches();
  }
  
  public Insn getFirstInsn() {
    return this.insns.get(0);
  }
  
  public InsnList getInsns() {
    return this.insns;
  }
  
  public int getLabel() {
    return this.label;
  }
  
  public Insn getLastInsn() {
    return this.insns.getLast();
  }
  
  public int getPrimarySuccessor() {
    return this.primarySuccessor;
  }
  
  public int getSecondarySuccessor() {
    if (this.successors.size() == 2) {
      int i = this.successors.get(0);
      int j = i;
      if (i == this.primarySuccessor)
        j = this.successors.get(1); 
      return j;
    } 
    throw new UnsupportedOperationException("block doesn't have exactly two successors");
  }
  
  public IntList getSuccessors() {
    return this.successors;
  }
  
  public boolean hasExceptionHandlers() {
    boolean bool;
    if (this.insns.getLast().getCatches().size() != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int hashCode() {
    return System.identityHashCode(this);
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('{');
    stringBuilder.append(Hex.u2(this.label));
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public BasicBlock withRegisterOffset(int paramInt) {
    return new BasicBlock(this.label, this.insns.withRegisterOffset(paramInt), this.successors, this.primarySuccessor);
  }
  
  public static interface Visitor {
    void visitBlock(BasicBlock param1BasicBlock);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\BasicBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */