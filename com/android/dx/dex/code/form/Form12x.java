package com.android.dx.dex.code.form;

import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.util.AnnotatedOutput;
import java.util.BitSet;

public final class Form12x extends InsnFormat {
  public static final InsnFormat THE_ONE = new Form12x();
  
  public int codeSize() {
    return 1;
  }
  
  public BitSet compatibleRegs(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    BitSet bitSet = new BitSet(2);
    int i = registerSpecList.get(0).getReg();
    int j = registerSpecList.get(1).getReg();
    int k = registerSpecList.size();
    if (k != 2) {
      if (k == 3) {
        if (i != j) {
          bitSet.set(0, false);
          bitSet.set(1, false);
        } else {
          boolean bool = unsignedFitsInNibble(j);
          bitSet.set(0, bool);
          bitSet.set(1, bool);
        } 
        bitSet.set(2, unsignedFitsInNibble(registerSpecList.get(2).getReg()));
      } else {
        throw new AssertionError();
      } 
    } else {
      bitSet.set(0, unsignedFitsInNibble(i));
      bitSet.set(1, unsignedFitsInNibble(j));
    } 
    return bitSet;
  }
  
  public String insnArgString(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    int i = registerSpecList.size();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(registerSpecList.get(i - 2).regString());
    stringBuilder.append(", ");
    stringBuilder.append(registerSpecList.get(i - 1).regString());
    return stringBuilder.toString();
  }
  
  public String insnCommentString(DalvInsn paramDalvInsn, boolean paramBoolean) {
    return "";
  }
  
  public boolean isCompatible(DalvInsn paramDalvInsn) {
    RegisterSpec registerSpec1;
    RegisterSpec registerSpec2;
    boolean bool = paramDalvInsn instanceof com.android.dx.dex.code.SimpleInsn;
    boolean bool1 = false;
    if (!bool)
      return false; 
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    int i = registerSpecList.size();
    if (i != 2) {
      if (i != 3)
        return false; 
      RegisterSpec registerSpec = registerSpecList.get(1);
      registerSpec1 = registerSpecList.get(2);
      registerSpec2 = registerSpec;
      if (registerSpec.getReg() != registerSpecList.get(0).getReg())
        return false; 
    } else {
      registerSpec2 = registerSpecList.get(0);
      registerSpec1 = registerSpecList.get(1);
    } 
    bool = bool1;
    if (unsignedFitsInNibble(registerSpec2.getReg())) {
      bool = bool1;
      if (unsignedFitsInNibble(registerSpec1.getReg()))
        bool = true; 
    } 
    return bool;
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    int i = registerSpecList.size();
    write(paramAnnotatedOutput, opcodeUnit(paramDalvInsn, makeByte(registerSpecList.get(i - 2).getReg(), registerSpecList.get(i - 1).getReg())));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\Form12x.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */