package com.android.dx.dex.code.form;

import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.dex.code.MultiCstInsn;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.util.AnnotatedOutput;

public final class Form4rcc extends InsnFormat {
  public static final InsnFormat THE_ONE = new Form4rcc();
  
  public int codeSize() {
    return 4;
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
    boolean bool = paramDalvInsn instanceof MultiCstInsn;
    boolean bool1 = false;
    if (!bool)
      return false; 
    MultiCstInsn multiCstInsn = (MultiCstInsn)paramDalvInsn;
    int i = multiCstInsn.getIndex(0);
    int j = multiCstInsn.getIndex(1);
    bool = bool1;
    if (unsignedFitsInShort(i))
      if (!unsignedFitsInShort(j)) {
        bool = bool1;
      } else {
        if (!(multiCstInsn.getConstant(0) instanceof com.android.dx.rop.cst.CstMethodRef))
          return false; 
        if (!(multiCstInsn.getConstant(1) instanceof com.android.dx.rop.cst.CstProtoRef))
          return false; 
        RegisterSpecList registerSpecList = multiCstInsn.getRegisters();
        j = registerSpecList.size();
        if (j == 0)
          return true; 
        bool = bool1;
        if (unsignedFitsInByte(registerSpecList.getWordCount())) {
          bool = bool1;
          if (unsignedFitsInShort(j)) {
            bool = bool1;
            if (unsignedFitsInShort(registerSpecList.get(0).getReg())) {
              bool = bool1;
              if (isRegListSequential(registerSpecList))
                bool = true; 
            } 
          } 
        } 
      }  
    return bool;
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    MultiCstInsn multiCstInsn = (MultiCstInsn)paramDalvInsn;
    short s1 = 0;
    short s2 = (short)multiCstInsn.getIndex(0);
    short s3 = (short)multiCstInsn.getIndex(1);
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    short s4 = s1;
    if (registerSpecList.size() > 0) {
      s1 = (short)registerSpecList.get(0).getReg();
      s4 = s1;
    } 
    write(paramAnnotatedOutput, opcodeUnit(paramDalvInsn, registerSpecList.getWordCount()), s2, s4, s3);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\Form4rcc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */