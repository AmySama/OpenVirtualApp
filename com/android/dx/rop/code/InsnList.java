package com.android.dx.rop.code;

import com.android.dx.util.FixedSizeList;

public final class InsnList extends FixedSizeList {
  public InsnList(int paramInt) {
    super(paramInt);
  }
  
  public boolean contentEquals(InsnList paramInsnList) {
    if (paramInsnList == null)
      return false; 
    int i = size();
    if (i != paramInsnList.size())
      return false; 
    for (byte b = 0; b < i; b++) {
      if (!get(b).contentEquals(paramInsnList.get(b)))
        return false; 
    } 
    return true;
  }
  
  public void forEach(Insn.Visitor paramVisitor) {
    int i = size();
    for (byte b = 0; b < i; b++)
      get(b).accept(paramVisitor); 
  }
  
  public Insn get(int paramInt) {
    return (Insn)get0(paramInt);
  }
  
  public Insn getLast() {
    return get(size() - 1);
  }
  
  public void set(int paramInt, Insn paramInsn) {
    set0(paramInt, paramInsn);
  }
  
  public InsnList withRegisterOffset(int paramInt) {
    int i = size();
    InsnList insnList = new InsnList(i);
    for (byte b = 0; b < i; b++) {
      Insn insn = (Insn)get0(b);
      if (insn != null)
        insnList.set0(b, insn.withRegisterOffset(paramInt)); 
    } 
    if (isImmutable())
      insnList.setImmutable(); 
    return insnList;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\InsnList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */