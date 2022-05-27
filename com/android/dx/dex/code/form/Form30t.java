package com.android.dx.dex.code.form;

import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.dex.code.TargetInsn;
import com.android.dx.util.AnnotatedOutput;

public final class Form30t extends InsnFormat {
  public static final InsnFormat THE_ONE = new Form30t();
  
  public boolean branchFits(TargetInsn paramTargetInsn) {
    return true;
  }
  
  public int codeSize() {
    return 3;
  }
  
  public String insnArgString(DalvInsn paramDalvInsn) {
    return branchString(paramDalvInsn);
  }
  
  public String insnCommentString(DalvInsn paramDalvInsn, boolean paramBoolean) {
    return branchComment(paramDalvInsn);
  }
  
  public boolean isCompatible(DalvInsn paramDalvInsn) {
    return !(!(paramDalvInsn instanceof TargetInsn) || paramDalvInsn.getRegisters().size() != 0);
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    int i = ((TargetInsn)paramDalvInsn).getTargetOffset();
    write(paramAnnotatedOutput, opcodeUnit(paramDalvInsn, 0), i);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\Form30t.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */