package com.android.dx.dex.code.form;

import com.android.dx.dex.code.CstInsn;
import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.cst.CstLiteral64;
import com.android.dx.rop.cst.CstLiteralBits;
import com.android.dx.util.AnnotatedOutput;
import java.util.BitSet;

public final class Form51l extends InsnFormat {
  public static final InsnFormat THE_ONE = new Form51l();
  
  public int codeSize() {
    return 5;
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
    return literalBitsComment((CstLiteralBits)((CstInsn)paramDalvInsn).getConstant(), 64);
  }
  
  public boolean isCompatible(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    return (!(paramDalvInsn instanceof CstInsn) || registerSpecList.size() != 1 || !unsignedFitsInByte(registerSpecList.get(0).getReg())) ? false : (((CstInsn)paramDalvInsn).getConstant() instanceof CstLiteral64);
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    long l = ((CstLiteral64)((CstInsn)paramDalvInsn).getConstant()).getLongBits();
    write(paramAnnotatedOutput, opcodeUnit(paramDalvInsn, registerSpecList.get(0).getReg()), l);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\Form51l.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */