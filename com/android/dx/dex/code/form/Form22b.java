package com.android.dx.dex.code.form;

import com.android.dx.dex.code.CstInsn;
import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstLiteralBits;
import com.android.dx.util.AnnotatedOutput;
import java.util.BitSet;

public final class Form22b extends InsnFormat {
  public static final InsnFormat THE_ONE = new Form22b();
  
  public int codeSize() {
    return 2;
  }
  
  public BitSet compatibleRegs(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    BitSet bitSet = new BitSet(2);
    bitSet.set(0, unsignedFitsInByte(registerSpecList.get(0).getReg()));
    bitSet.set(1, unsignedFitsInByte(registerSpecList.get(1).getReg()));
    return bitSet;
  }
  
  public String insnArgString(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    CstLiteralBits cstLiteralBits = (CstLiteralBits)((CstInsn)paramDalvInsn).getConstant();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(registerSpecList.get(0).regString());
    stringBuilder.append(", ");
    stringBuilder.append(registerSpecList.get(1).regString());
    stringBuilder.append(", ");
    stringBuilder.append(literalBitsString(cstLiteralBits));
    return stringBuilder.toString();
  }
  
  public String insnCommentString(DalvInsn paramDalvInsn, boolean paramBoolean) {
    return literalBitsComment((CstLiteralBits)((CstInsn)paramDalvInsn).getConstant(), 8);
  }
  
  public boolean isCompatible(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    boolean bool = paramDalvInsn instanceof CstInsn;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (bool) {
      bool2 = bool1;
      if (registerSpecList.size() == 2) {
        bool2 = bool1;
        if (unsignedFitsInByte(registerSpecList.get(0).getReg()))
          if (!unsignedFitsInByte(registerSpecList.get(1).getReg())) {
            bool2 = bool1;
          } else {
            Constant constant = ((CstInsn)paramDalvInsn).getConstant();
            if (!(constant instanceof CstLiteralBits))
              return false; 
            CstLiteralBits cstLiteralBits = (CstLiteralBits)constant;
            bool2 = bool1;
            if (cstLiteralBits.fitsInInt()) {
              bool2 = bool1;
              if (signedFitsInByte(cstLiteralBits.getIntBits()))
                bool2 = true; 
            } 
          }  
      } 
    } 
    return bool2;
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    int i = ((CstLiteralBits)((CstInsn)paramDalvInsn).getConstant()).getIntBits();
    write(paramAnnotatedOutput, opcodeUnit(paramDalvInsn, registerSpecList.get(0).getReg()), codeUnit(registerSpecList.get(1).getReg(), i & 0xFF));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\Form22b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */