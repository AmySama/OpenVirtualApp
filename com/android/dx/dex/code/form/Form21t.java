package com.android.dx.dex.code.form;

import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.dex.code.TargetInsn;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.util.AnnotatedOutput;
import java.util.BitSet;

public final class Form21t extends InsnFormat {
  public static final InsnFormat THE_ONE = new Form21t();
  
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
  
  public BitSet compatibleRegs(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    BitSet bitSet = new BitSet(1);
    bitSet.set(0, unsignedFitsInByte(registerSpecList.get(0).getReg()));
    return bitSet;
  }
  
  public String insnArgString(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(registerSpecList.get(0).regString());
    stringBuilder.append(", ");
    stringBuilder.append(branchString(paramDalvInsn));
    return stringBuilder.toString();
  }
  
  public String insnCommentString(DalvInsn paramDalvInsn, boolean paramBoolean) {
    return branchComment(paramDalvInsn);
  }
  
  public boolean isCompatible(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    if (paramDalvInsn instanceof TargetInsn) {
      int i = registerSpecList.size();
      boolean bool = true;
      if (i == 1 && unsignedFitsInByte(registerSpecList.get(0).getReg())) {
        TargetInsn targetInsn = (TargetInsn)paramDalvInsn;
        if (targetInsn.hasTargetOffset())
          bool = branchFits(targetInsn); 
        return bool;
      } 
    } 
    return false;
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    int i = ((TargetInsn)paramDalvInsn).getTargetOffset();
    write(paramAnnotatedOutput, opcodeUnit(paramDalvInsn, registerSpecList.get(0).getReg()), (short)i);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\Form21t.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */