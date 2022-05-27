package com.android.dx.dex.code.form;

import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.util.AnnotatedOutput;

public final class SpecialFormat extends InsnFormat {
  public static final InsnFormat THE_ONE = new SpecialFormat();
  
  public int codeSize() {
    throw new RuntimeException("unsupported");
  }
  
  public String insnArgString(DalvInsn paramDalvInsn) {
    throw new RuntimeException("unsupported");
  }
  
  public String insnCommentString(DalvInsn paramDalvInsn, boolean paramBoolean) {
    throw new RuntimeException("unsupported");
  }
  
  public boolean isCompatible(DalvInsn paramDalvInsn) {
    return true;
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    throw new RuntimeException("unsupported");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\SpecialFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */