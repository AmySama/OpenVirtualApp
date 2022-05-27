package com.android.dx.dex.code.form;

import com.android.dx.dex.code.CstInsn;
import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstLiteralBits;
import com.android.dx.util.AnnotatedOutput;
import java.util.BitSet;

public final class Form31i extends InsnFormat {
  public static final InsnFormat THE_ONE = new Form31i();
  
  public int codeSize() {
    return 3;
  }
  
  public BitSet compatibleRegs(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    BitSet bitSet = new BitSet(1);
    bitSet.set(0, unsignedFitsInByte(registerSpecList.get(0).getReg()));
    return bitSet;
  }
  
  public String insnArgString(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    CstLiteralBits cstLiteralBits = (CstLiteralBits)((CstInsn)paramDalvInsn).getConstant();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(registerSpecList.get(0).regString());
    stringBuilder.append(", ");
    stringBuilder.append(literalBitsString(cstLiteralBits));
    return stringBuilder.toString();
  }
  
  public String insnCommentString(DalvInsn paramDalvInsn, boolean paramBoolean) {
    return literalBitsComment((CstLiteralBits)((CstInsn)paramDalvInsn).getConstant(), 32);
  }
  
  public boolean isCompatible(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    if (!(paramDalvInsn instanceof CstInsn) || registerSpecList.size() != 1 || !unsignedFitsInByte(registerSpecList.get(0).getReg()))
      return false; 
    Constant constant = ((CstInsn)paramDalvInsn).getConstant();
    return !(constant instanceof CstLiteralBits) ? false : ((CstLiteralBits)constant).fitsInInt();
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    int i = ((CstLiteralBits)((CstInsn)paramDalvInsn).getConstant()).getIntBits();
    write(paramAnnotatedOutput, opcodeUnit(paramDalvInsn, registerSpecList.get(0).getReg()), i);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\Form31i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */