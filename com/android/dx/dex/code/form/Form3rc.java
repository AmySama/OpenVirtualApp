package com.android.dx.dex.code.form;

import com.android.dx.dex.code.CstInsn;
import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.cst.Constant;
import com.android.dx.util.AnnotatedOutput;

public final class Form3rc extends InsnFormat {
  public static final InsnFormat THE_ONE = new Form3rc();
  
  public int codeSize() {
    return 3;
  }
  
  public String insnArgString(DalvInsn paramDalvInsn) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(regRangeString(paramDalvInsn.getRegisters()));
    stringBuilder.append(", ");
    stringBuilder.append(paramDalvInsn.cstString());
    return stringBuilder.toString();
  }
  
  public String insnCommentString(DalvInsn paramDalvInsn, boolean paramBoolean) {
    return paramBoolean ? paramDalvInsn.cstComment() : "";
  }
  
  public boolean isCompatible(DalvInsn paramDalvInsn) {
    null = paramDalvInsn instanceof CstInsn;
    boolean bool = false;
    if (!null)
      return false; 
    CstInsn cstInsn = (CstInsn)paramDalvInsn;
    int i = cstInsn.getIndex();
    Constant constant = cstInsn.getConstant();
    if (!unsignedFitsInShort(i))
      return false; 
    if (!(constant instanceof com.android.dx.rop.cst.CstMethodRef) && !(constant instanceof com.android.dx.rop.cst.CstType) && !(constant instanceof com.android.dx.rop.cst.CstCallSiteRef))
      return false; 
    RegisterSpecList registerSpecList = cstInsn.getRegisters();
    registerSpecList.size();
    if (registerSpecList.size() != 0) {
      null = bool;
      if (isRegListSequential(registerSpecList)) {
        null = bool;
        if (unsignedFitsInShort(registerSpecList.get(0).getReg())) {
          null = bool;
          if (unsignedFitsInByte(registerSpecList.getWordCount()))
            return true; 
        } 
      } 
      return null;
    } 
    return true;
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    int i = ((CstInsn)paramDalvInsn).getIndex();
    int j = registerSpecList.size();
    int k = 0;
    if (j != 0)
      k = registerSpecList.get(0).getReg(); 
    write(paramAnnotatedOutput, opcodeUnit(paramDalvInsn, registerSpecList.getWordCount()), (short)i, (short)k);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\Form3rc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */