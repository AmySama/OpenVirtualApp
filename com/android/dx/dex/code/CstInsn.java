package com.android.dx.dex.code;

import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstString;
import com.android.dx.util.Hex;

public final class CstInsn extends FixedSizeInsn {
  private int classIndex;
  
  private final Constant constant;
  
  private int index;
  
  public CstInsn(Dop paramDop, SourcePosition paramSourcePosition, RegisterSpecList paramRegisterSpecList, Constant paramConstant) {
    super(paramDop, paramSourcePosition, paramRegisterSpecList);
    if (paramConstant != null) {
      this.constant = paramConstant;
      this.index = -1;
      this.classIndex = -1;
      return;
    } 
    throw new NullPointerException("constant == null");
  }
  
  protected String argString() {
    return this.constant.toHuman();
  }
  
  public String cstComment() {
    if (!hasIndex())
      return ""; 
    StringBuilder stringBuilder = new StringBuilder(20);
    stringBuilder.append(getConstant().typeName());
    stringBuilder.append('@');
    int i = this.index;
    if (i < 65536) {
      stringBuilder.append(Hex.u2(i));
    } else {
      stringBuilder.append(Hex.u4(i));
    } 
    return stringBuilder.toString();
  }
  
  public String cstString() {
    Constant constant = this.constant;
    return (constant instanceof CstString) ? ((CstString)constant).toQuoted() : constant.toHuman();
  }
  
  public int getClassIndex() {
    int i = this.classIndex;
    if (i >= 0)
      return i; 
    throw new IllegalStateException("class index not yet set");
  }
  
  public Constant getConstant() {
    return this.constant;
  }
  
  public int getIndex() {
    int i = this.index;
    if (i >= 0)
      return i; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("index not yet set for ");
    stringBuilder.append(this.constant);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public boolean hasClassIndex() {
    boolean bool;
    if (this.classIndex >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean hasIndex() {
    boolean bool;
    if (this.index >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void setClassIndex(int paramInt) {
    if (paramInt >= 0) {
      if (this.classIndex < 0) {
        this.classIndex = paramInt;
        return;
      } 
      throw new IllegalStateException("class index already set");
    } 
    throw new IllegalArgumentException("index < 0");
  }
  
  public void setIndex(int paramInt) {
    if (paramInt >= 0) {
      if (this.index < 0) {
        this.index = paramInt;
        return;
      } 
      throw new IllegalStateException("index already set");
    } 
    throw new IllegalArgumentException("index < 0");
  }
  
  public DalvInsn withOpcode(Dop paramDop) {
    CstInsn cstInsn = new CstInsn(paramDop, getPosition(), getRegisters(), this.constant);
    int i = this.index;
    if (i >= 0)
      cstInsn.setIndex(i); 
    i = this.classIndex;
    if (i >= 0)
      cstInsn.setClassIndex(i); 
    return cstInsn;
  }
  
  public DalvInsn withRegisters(RegisterSpecList paramRegisterSpecList) {
    CstInsn cstInsn = new CstInsn(getOpcode(), getPosition(), paramRegisterSpecList, this.constant);
    int i = this.index;
    if (i >= 0)
      cstInsn.setIndex(i); 
    i = this.classIndex;
    if (i >= 0)
      cstInsn.setClassIndex(i); 
    return cstInsn;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\CstInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */