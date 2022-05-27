package com.android.dx.dex.code.form;

import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.util.AnnotatedOutput;
import java.util.BitSet;

public final class Form22x extends InsnFormat {
  public static final InsnFormat THE_ONE = new Form22x();
  
  public int codeSize() {
    return 2;
  }
  
  public BitSet compatibleRegs(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    BitSet bitSet = new BitSet(2);
    bitSet.set(0, unsignedFitsInByte(registerSpecList.get(0).getReg()));
    bitSet.set(1, unsignedFitsInShort(registerSpecList.get(1).getReg()));
    return bitSet;
  }
  
  public String insnArgString(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(registerSpecList.get(0).regString());
    stringBuilder.append(", ");
    stringBuilder.append(registerSpecList.get(1).regString());
    return stringBuilder.toString();
  }
  
  public String insnCommentString(DalvInsn paramDalvInsn, boolean paramBoolean) {
    return "";
  }
  
  public boolean isCompatible(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    boolean bool = paramDalvInsn instanceof com.android.dx.dex.code.SimpleInsn;
    boolean bool1 = true;
    if (!bool || registerSpecList.size() != 2 || !unsignedFitsInByte(registerSpecList.get(0).getReg()) || !unsignedFitsInShort(registerSpecList.get(1).getReg()))
      bool1 = false; 
    return bool1;
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    write(paramAnnotatedOutput, opcodeUnit(paramDalvInsn, registerSpecList.get(0).getReg()), (short)registerSpecList.get(1).getReg());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\Form22x.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */