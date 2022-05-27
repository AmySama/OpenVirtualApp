package com.android.dx.dex.code.form;

import com.android.dx.dex.code.DalvInsn;
import com.android.dx.dex.code.InsnFormat;
import com.android.dx.dex.code.MultiCstInsn;
import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.util.AnnotatedOutput;
import java.util.BitSet;

public final class Form45cc extends InsnFormat {
  private static final int MAX_NUM_OPS = 5;
  
  public static final InsnFormat THE_ONE = new Form45cc();
  
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
    return 4;
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
    boolean bool = paramDalvInsn instanceof MultiCstInsn;
    boolean bool1 = false;
    if (!bool)
      return false; 
    MultiCstInsn multiCstInsn = (MultiCstInsn)paramDalvInsn;
    if (multiCstInsn.getNumberOfConstants() != 2)
      return false; 
    int i = multiCstInsn.getIndex(0);
    int j = multiCstInsn.getIndex(1);
    bool = bool1;
    if (unsignedFitsInShort(i))
      if (!unsignedFitsInShort(j)) {
        bool = bool1;
      } else {
        if (!(multiCstInsn.getConstant(0) instanceof com.android.dx.rop.cst.CstMethodRef))
          return false; 
        if (!(multiCstInsn.getConstant(1) instanceof com.android.dx.rop.cst.CstProtoRef))
          return false; 
        bool = bool1;
        if (wordCount(multiCstInsn.getRegisters()) >= 0)
          bool = true; 
      }  
    return bool;
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput, DalvInsn paramDalvInsn) {
    boolean bool1;
    boolean bool2;
    boolean bool3;
    boolean bool4;
    MultiCstInsn multiCstInsn = (MultiCstInsn)paramDalvInsn;
    int i = 0;
    short s1 = (short)multiCstInsn.getIndex(0);
    short s2 = (short)multiCstInsn.getIndex(1);
    RegisterSpecList registerSpecList = explicitize(paramDalvInsn.getRegisters());
    int j = registerSpecList.size();
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
      i = registerSpecList.get(4).getReg(); 
    write(paramAnnotatedOutput, opcodeUnit(paramDalvInsn, makeByte(i, j)), s1, codeUnit(bool1, bool2, bool3, bool4), s2);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\form\Form45cc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */