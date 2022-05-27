package com.android.dx.dex.code.form;

import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.dex.code.TargetInsn;
import com.android.dx.util.AnnotatedOutput;

public final class Form20t extends InsnFormat {
  public static final InsnFormat THE_ONE = new Form20t();
  
  public boolean branchFits(TargetInsn paramTargetInsn) {
    boolean bool;
    int i = paramTargetInsn.getTargetOffset();
    if (i != 0 && signedFitsInShort(i)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int codeSize() {
    return 2;
  }
  
  public String insnArgString(DalvInsn paramDalvInsn) {
    return branchString(paramDalvInsn);
  }
  
  public String insnCommentString(DalvInsn paramDalvInsn, boolean paramBoolean) {
    return branchComment(paramDalvInsn);
  }
  
  public boolean isCompatible(DalvInsn paramDalvInsn) {
    boolean bool;
    if (!(paramDalvInsn instanceof TargetInsn) || paramDalvInsn.getRegisters().size() != 0)
      return false; 
    TargetInsn targetInsn = (TargetInsn)paramDalvInsn;
    if (targetInsn.hasTargetOffset()) {
      bool = branchFits(targetInsn);
    } else {
      bool = true;
    } 
    return bool;
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    int i = ((TargetInsn)paramDalvInsn).getTargetOffset();
    write(paramAnnotatedOutput, opcodeUnit(paramDalvInsn, 0), (short)i);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\Form20t.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */