package com.android.dx.dex.code;

import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.rop.cst.Constant;
import com.android.dx.util.Hex;

public final class MultiCstInsn extends FixedSizeInsn {
  private static final int NOT_SET = -1;
  
  private int classIndex;
  
  private final Constant[] constants;
  
  private final int[] index;
  
  public MultiCstInsn(Dop paramDop, SourcePosition paramSourcePosition, RegisterSpecList paramRegisterSpecList, Constant[] paramArrayOfConstant) {
    super(paramDop, paramSourcePosition, paramRegisterSpecList);
    if (paramArrayOfConstant != null) {
      this.constants = paramArrayOfConstant;
      this.index = new int[paramArrayOfConstant.length];
      byte b = 0;
      while (true) {
        int[] arrayOfInt = this.index;
        if (b < arrayOfInt.length) {
          if (paramArrayOfConstant[b] != null) {
            arrayOfInt[b] = -1;
            b++;
            continue;
          } 
          throw new NullPointerException("constants[i] == null");
        } 
        this.classIndex = -1;
        return;
      } 
    } 
    throw new NullPointerException("constants == null");
  }
  
  private MultiCstInsn(Dop paramDop, SourcePosition paramSourcePosition, RegisterSpecList paramRegisterSpecList, Constant[] paramArrayOfConstant, int[] paramArrayOfint, int paramInt) {
    super(paramDop, paramSourcePosition, paramRegisterSpecList);
    this.constants = paramArrayOfConstant;
    this.index = paramArrayOfint;
    this.classIndex = paramInt;
  }
  
  protected String argString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < this.constants.length; b++) {
      if (stringBuilder.length() > 0)
        stringBuilder.append(", "); 
      stringBuilder.append(this.constants[b].toHuman());
    } 
    return stringBuilder.toString();
  }
  
  public String cstComment() {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < this.constants.length; b++) {
      if (!hasIndex(b))
        return ""; 
      if (b > 0)
        stringBuilder.append(", "); 
      stringBuilder.append(getConstant(b).typeName());
      stringBuilder.append('@');
      int i = getIndex(b);
      if (i < 65536) {
        stringBuilder.append(Hex.u2(i));
      } else {
        stringBuilder.append(Hex.u4(i));
      } 
    } 
    return stringBuilder.toString();
  }
  
  public String cstString() {
    return argString();
  }
  
  public int getClassIndex() {
    if (hasClassIndex())
      return this.classIndex; 
    throw new IllegalStateException("class index not yet set");
  }
  
  public Constant getConstant(int paramInt) {
    return this.constants[paramInt];
  }
  
  public int getIndex(int paramInt) {
    if (hasIndex(paramInt))
      return this.index[paramInt]; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("index not yet set for constant ");
    stringBuilder.append(paramInt);
    stringBuilder.append(" value = ");
    stringBuilder.append(this.constants[paramInt]);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public int getNumberOfConstants() {
    return this.constants.length;
  }
  
  public boolean hasClassIndex() {
    boolean bool;
    if (this.classIndex != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean hasIndex(int paramInt) {
    boolean bool;
    if (this.index[paramInt] != -1) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void setClassIndex(int paramInt) {
    if (paramInt >= 0) {
      if (!hasClassIndex()) {
        this.classIndex = paramInt;
        return;
      } 
      throw new IllegalStateException("class index already set");
    } 
    throw new IllegalArgumentException("index < 0");
  }
  
  public void setIndex(int paramInt1, int paramInt2) {
    if (paramInt2 >= 0) {
      if (!hasIndex(paramInt1)) {
        this.index[paramInt1] = paramInt2;
        return;
      } 
      throw new IllegalStateException("index already set");
    } 
    throw new IllegalArgumentException("index < 0");
  }
  
  public DalvInsn withOpcode(Dop paramDop) {
    return new MultiCstInsn(paramDop, getPosition(), getRegisters(), this.constants, this.index, this.classIndex);
  }
  
  public DalvInsn withRegisters(RegisterSpecList paramRegisterSpecList) {
    return new MultiCstInsn(getOpcode(), getPosition(), paramRegisterSpecList, this.constants, this.index, this.classIndex);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\MultiCstInsn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */