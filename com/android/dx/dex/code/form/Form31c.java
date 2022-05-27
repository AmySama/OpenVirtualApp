package com.android.dx.dex.code.form;

import com.android.dx.dex.code.CstInsn;
import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.cst.Constant;
import com.android.dx.util.AnnotatedOutput;
import java.util.BitSet;

public final class Form31c extends InsnFormat {
  public static final InsnFormat THE_ONE = new Form31c();
  
  public int codeSize() {
    return 3;
  }
  
  public BitSet compatibleRegs(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    int i = registerSpecList.size();
    BitSet bitSet = new BitSet(i);
    boolean bool = unsignedFitsInByte(registerSpecList.get(0).getReg());
    if (i == 1) {
      bitSet.set(0, bool);
    } else if (registerSpecList.get(0).getReg() == registerSpecList.get(1).getReg()) {
      bitSet.set(0, bool);
      bitSet.set(1, bool);
    } 
    return bitSet;
  }
  
  public String insnArgString(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(registerSpecList.get(0).regString());
    stringBuilder.append(", ");
    stringBuilder.append(paramDalvInsn.cstString());
    return stringBuilder.toString();
  }
  
  public String insnCommentString(DalvInsn paramDalvInsn, boolean paramBoolean) {
    return paramBoolean ? paramDalvInsn.cstComment() : "";
  }
  
  public boolean isCompatible(DalvInsn paramDalvInsn) {
    RegisterSpec registerSpec;
    boolean bool = paramDalvInsn instanceof CstInsn;
    boolean bool1 = false;
    if (!bool)
      return false; 
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    int i = registerSpecList.size();
    if (i != 1) {
      if (i != 2)
        return false; 
      RegisterSpec registerSpec1 = registerSpecList.get(0);
      registerSpec = registerSpec1;
      if (registerSpec1.getReg() != registerSpecList.get(1).getReg())
        return false; 
    } else {
      registerSpec = registerSpecList.get(0);
    } 
    if (!unsignedFitsInByte(registerSpec.getReg()))
      return false; 
    Constant constant = ((CstInsn)paramDalvInsn).getConstant();
    if (constant instanceof com.android.dx.rop.cst.CstType || constant instanceof com.android.dx.rop.cst.CstFieldRef || constant instanceof com.android.dx.rop.cst.CstString)
      bool1 = true; 
    return bool1;
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    int i = ((CstInsn)paramDalvInsn).getIndex();
    write(paramAnnotatedOutput, opcodeUnit(paramDalvInsn, registerSpecList.get(0).getReg()), i);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\Form31c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */