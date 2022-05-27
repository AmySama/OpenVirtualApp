package com.android.dx.dex.code.form;

import com.android.dx.dex.code.CstInsn;
import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.util.AnnotatedOutput;
import java.util.BitSet;

public final class Form35c extends InsnFormat {
  private static final int MAX_NUM_OPS = 5;
  
  public static final InsnFormat THE_ONE = new Form35c();
  
  private static RegisterSpecList explicitize(RegisterSpecList paramRegisterSpecList) {
    int i = wordCount(paramRegisterSpecList);
    int j = paramRegisterSpecList.size();
    if (i == j)
      return paramRegisterSpecList; 
    RegisterSpecList registerSpecList = new RegisterSpecList(i);
    byte b = 0;
    i = 0;
    while (b < j) {
      RegisterSpec registerSpec = paramRegisterSpecList.get(b);
      registerSpecList.set(i, registerSpec);
      if (registerSpec.getCategory() == 2) {
        registerSpecList.set(i + 1, RegisterSpec.make(registerSpec.getReg() + 1, (TypeBearer)Type.VOID));
        i += 2;
      } else {
        i++;
      } 
      b++;
    } 
    registerSpecList.setImmutable();
    return registerSpecList;
  }
  
  private static int wordCount(RegisterSpecList paramRegisterSpecList) {
    int i = paramRegisterSpecList.size();
    byte b = -1;
    if (i > 5)
      return -1; 
    int j = 0;
    int k = 0;
    while (j < i) {
      RegisterSpec registerSpec = paramRegisterSpecList.get(j);
      k += registerSpec.getCategory();
      if (!unsignedFitsInNibble(registerSpec.getReg() + registerSpec.getCategory() - 1))
        return -1; 
      j++;
    } 
    j = b;
    if (k <= 5)
      j = k; 
    return j;
  }
  
  public int codeSize() {
    return 3;
  }
  
  public BitSet compatibleRegs(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = paramDalvInsn.getRegisters();
    int i = registerSpecList.size();
    BitSet bitSet = new BitSet(i);
    for (byte b = 0; b < i; b++) {
      RegisterSpec registerSpec = registerSpecList.get(b);
      bitSet.set(b, unsignedFitsInNibble(registerSpec.getReg() + registerSpec.getCategory() - 1));
    } 
    return bitSet;
  }
  
  public String insnArgString(DalvInsn paramDalvInsn) {
    RegisterSpecList registerSpecList = explicitize(paramDalvInsn.getRegisters());
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(regListString(registerSpecList));
    stringBuilder.append(", ");
    stringBuilder.append(paramDalvInsn.cstString());
    return stringBuilder.toString();
  }
  
  public String insnCommentString(DalvInsn paramDalvInsn, boolean paramBoolean) {
    return paramBoolean ? paramDalvInsn.cstComment() : "";
  }
  
  public boolean isCompatible(DalvInsn paramDalvInsn) {
    boolean bool = paramDalvInsn instanceof CstInsn;
    boolean bool1 = false;
    if (!bool)
      return false; 
    CstInsn cstInsn = (CstInsn)paramDalvInsn;
    if (!unsignedFitsInShort(cstInsn.getIndex()))
      return false; 
    Constant constant = cstInsn.getConstant();
    if (!(constant instanceof com.android.dx.rop.cst.CstMethodRef) && !(constant instanceof com.android.dx.rop.cst.CstType) && !(constant instanceof com.android.dx.rop.cst.CstCallSiteRef))
      return false; 
    if (wordCount(cstInsn.getRegisters()) >= 0)
      bool1 = true; 
    return bool1;
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    boolean bool1;
    boolean bool2;
    boolean bool3;
    boolean bool4;
    int i = ((CstInsn)paramDalvInsn).getIndex();
    RegisterSpecList registerSpecList = explicitize(paramDalvInsn.getRegisters());
    int j = registerSpecList.size();
    int k = 0;
    if (j > 0) {
      bool1 = registerSpecList.get(0).getReg();
    } else {
      bool1 = false;
    } 
    if (j > 1) {
      bool2 = registerSpecList.get(1).getReg();
    } else {
      bool2 = false;
    } 
    if (j > 2) {
      bool3 = registerSpecList.get(2).getReg();
    } else {
      bool3 = false;
    } 
    if (j > 3) {
      bool4 = registerSpecList.get(3).getReg();
    } else {
      bool4 = false;
    } 
    if (j > 4)
      k = registerSpecList.get(4).getReg(); 
    write(paramAnnotatedOutput, opcodeUnit(paramDalvInsn, makeByte(k, j)), (short)i, codeUnit(bool1, bool2, bool3, bool4));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\Form35c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */