package com.android.dx.dex.code.form;

import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.util.AnnotatedOutput;

public final class Form10x extends InsnFormat {
  public static final InsnFormat THE_ONE = new Form10x();
  
  public int codeSize() {
    return 1;
  }
  
  public String insnArgString(DalvInsn paramDalvInsn) {
    return "";
  }
  
  public String insnCommentString(DalvInsn paramDalvInsn, boolean paramBoolean) {
    return "";
  }
  
  public boolean isCompatible(DalvInsn paramDalvInsn) {
    boolean bool;
    if (paramDalvInsn instanceof com.android.dx.dex.code.SimpleInsn && paramDalvInsn.getRegisters().size() == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    write(paramAnnotatedOutput, opcodeUnit(paramDalvInsn, 0));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\Form10x.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */