package com.android.dx.dex.code.form;

import com.android.dx.dex.code.CstInsn;
import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.cst.Constant;
import com.android.dx.util.AnnotatedOutput;
import java.util.BitSet;

public final class Form22c extends InsnFormat {
  public static final InsnFormat THE_ONE = new Form22c();
  
  public int codeSize() {
    return 2;
  }
  
  public BitSet compatibleRegs(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    BitSet bitSet = new BitSet(2);
    bitSet.set(0, unsignedFitsInNibble(registerSpecList.get(0).getReg()));
    bitSet.set(1, unsignedFitsInNibble(registerSpecList.get(1).getReg()));
    return bitSet;
  }
  
  public String insnArgString(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(registerSpecList.get(0).regString());
    stringBuilder.append(", ");
    stringBuilder.append(registerSpecList.get(1).regString());
    stringBuilder.append(", ");
    stringBuilder.append(paramDalvInsn.cstString());
    return stringBuilder.toString();
  }
  
  public String insnCommentString(DalvInsn paramDalvInsn, boolean paramBoolean) {
    return paramBoolean ? paramDalvInsn.cstComment() : "";
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
        if (unsignedFitsInNibble(registerSpecList.get(0).getReg()))
          if (!unsignedFitsInNibble(registerSpecList.get(1).getReg())) {
            bool2 = bool1;
          } else {
            CstInsn cstInsn = (CstInsn)paramDalvInsn;
            if (!unsignedFitsInShort(cstInsn.getIndex()))
              return false; 
            Constant constant = cstInsn.getConstant();
            if (!(constant instanceof com.android.dx.rop.cst.CstType)) {
              bool2 = bool1;
              if (constant instanceof com.android.dx.rop.cst.CstFieldRef)
                bool2 = true; 
              return bool2;
            } 
            bool2 = true;
          }  
      } 
    } 
    return bool2;
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    int i = ((CstInsn)paramDalvInsn).getIndex();
    write(paramAnnotatedOutput, opcodeUnit(paramDalvInsn, makeByte(registerSpecList.get(0).getReg(), registerSpecList.get(1).getReg())), (short)i);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\Form22c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */