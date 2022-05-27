package com.android.dx.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.util.AnnotatedOutput;

public final class HighRegisterPrefix extends VariableSizeInsn {
  private SimpleInsn[] insns;
  
  public HighRegisterPrefix(SourcePosition paramSourcePosition, RegisterSpecList paramRegisterSpecList) {
    super(paramSourcePosition, paramRegisterSpecList);
    if (paramRegisterSpecList.size() != 0) {
      this.insns = null;
      return;
    } 
    throw new IllegalArgumentException("registers.size() == 0");
  }
  
  private void calculateInsnsIfNecessary() {
    if (this.insns != null)
      return; 
    RegisterSpecList registerSpecList = getRegisters();
    int i = registerSpecList.size();
    this.insns = new SimpleInsn[i];
    byte b = 0;
    int j = 0;
    while (b < i) {
      RegisterSpec registerSpec = registerSpecList.get(b);
      this.insns[b] = moveInsnFor(registerSpec, j);
      j += registerSpec.getCategory();
      b++;
    } 
  }
  
  private static SimpleInsn moveInsnFor(RegisterSpec paramRegisterSpec, int paramInt) {
    return DalvInsn.makeMove(SourcePosition.NO_INFO, RegisterSpec.make(paramInt, (TypeBearer)paramRegisterSpec.getType()), paramRegisterSpec);
  }
  
  protected String argString() {
    return null;
  }
  
  public int codeSize() {
    calculateInsnsIfNecessary();
    SimpleInsn[] arrayOfSimpleInsn = this.insns;
    int i = arrayOfSimpleInsn.length;
    byte b = 0;
    int j = 0;
    while (b < i) {
      j += arrayOfSimpleInsn[b].codeSize();
      b++;
    } 
    return j;
  }
  
  protected String listingString0(boolean paramBoolean) {
    RegisterSpecList registerSpecList = getRegisters();
    int i = registerSpecList.size();
    StringBuilder stringBuilder = new StringBuilder(100);
    byte b = 0;
    int j = 0;
    while (b < i) {
      RegisterSpec registerSpec = registerSpecList.get(b);
      SimpleInsn simpleInsn = moveInsnFor(registerSpec, j);
      if (b != 0)
        stringBuilder.append('\n'); 
      stringBuilder.append(simpleInsn.listingString0(paramBoolean));
      j += registerSpec.getCategory();
      b++;
    } 
    return stringBuilder.toString();
  }
  
  public DalvInsn withRegisters(RegisterSpecList paramRegisterSpecList) {
    return new HighRegisterPrefix(getPosition(), paramRegisterSpecList);
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput) {
    calculateInsnsIfNecessary();
    SimpleInsn[] arrayOfSimpleInsn = this.insns;
    int i = arrayOfSimpleInsn.length;
    for (byte b = 0; b < i; b++)
      arrayOfSimpleInsn[b].writeTo(paramAnnotatedOutput); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\HighRegisterPrefix.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */