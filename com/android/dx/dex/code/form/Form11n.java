package com.android.dx.dex.code.form;

import com.android.dx.dex.code.CstInsn;
import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstLiteralBits;
import com.android.dx.util.AnnotatedOutput;
import java.util.BitSet;

public final class Form11n extends InsnFormat {
  public static final InsnFormat THE_ONE = new Form11n();
  
  public int codeSize() {
    return 1;
  }
  
  public BitSet compatibleRegs(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    BitSet bitSet = new BitSet(1);
    bitSet.set(0, unsignedFitsInNibble(registerSpecList.get(0).getReg()));
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
    return literalBitsComment((CstLiteralBits)((CstInsn)paramDalvInsn).getConstant(), 4);
  }
  
  public boolean isCompatible(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    boolean bool = paramDalvInsn instanceof CstInsn;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (bool) {
      bool2 = bool1;
      if (registerSpecList.size() == 1)
        if (!unsignedFitsInNibble(registerSpecList.get(0).getReg())) {
          bool2 = bool1;
        } else {
          Constant constant = ((CstInsn)paramDalvInsn).getConstant();
          if (!(constant instanceof CstLiteralBits))
            return false; 
          CstLiteralBits cstLiteralBits = (CstLiteralBits)constant;
          bool2 = bool1;
          if (cstLiteralBits.fitsInInt()) {
            bool2 = bool1;
            if (signedFitsInNibble(cstLiteralBits.getIntBits()))
              bool2 = true; 
          } 
        }  
    } 
    return bool2;
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    int i = ((CstLiteralBits)((CstInsn)paramDalvInsn).getConstant()).getIntBits();
    write(paramAnnotatedOutput, opcodeUnit(paramDalvInsn, makeByte(registerSpecList.get(0).getReg(), i & 0xF)));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\Form11n.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */