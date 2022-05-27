package com.android.dx.dex.code;

import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstLiteral32;
import com.android.dx.rop.cst.CstLiteral64;
import com.android.dx.rop.cst.CstType;
import com.android.dx.util.AnnotatedOutput;
import com.android.dx.util.Hex;
import java.util.ArrayList;

public final class ArrayData extends VariableSizeInsn {
  private final Constant arrayType;
  
  private final int elemWidth;
  
  private final int initLength;
  
  private final CodeAddress user;
  
  private final ArrayList<Constant> values;
  
  public ArrayData(SourcePosition paramSourcePosition, CodeAddress paramCodeAddress, ArrayList<Constant> paramArrayList, Constant paramConstant) {
    super(paramSourcePosition, RegisterSpecList.EMPTY);
    if (paramCodeAddress != null) {
      if (paramArrayList != null) {
        if (paramArrayList.size() > 0) {
          this.arrayType = paramConstant;
          if (paramConstant == CstType.BYTE_ARRAY || paramConstant == CstType.BOOLEAN_ARRAY) {
            this.elemWidth = 1;
          } else if (paramConstant == CstType.SHORT_ARRAY || paramConstant == CstType.CHAR_ARRAY) {
            this.elemWidth = 2;
          } else if (paramConstant == CstType.INT_ARRAY || paramConstant == CstType.FLOAT_ARRAY) {
            this.elemWidth = 4;
          } else if (paramConstant == CstType.LONG_ARRAY || paramConstant == CstType.DOUBLE_ARRAY) {
            this.elemWidth = 8;
          } else {
            throw new IllegalArgumentException("Unexpected constant type");
          } 
          this.user = paramCodeAddress;
          this.values = paramArrayList;
          this.initLength = paramArrayList.size();
          return;
        } 
        throw new IllegalArgumentException("Illegal number of init values");
      } 
      throw new NullPointerException("values == null");
    } 
    throw new NullPointerException("user == null");
  }
  
  protected String argString() {
    StringBuilder stringBuilder = new StringBuilder(100);
    int i = this.values.size();
    for (byte b = 0; b < i; b++) {
      stringBuilder.append("\n    ");
      stringBuilder.append(b);
      stringBuilder.append(": ");
      stringBuilder.append(((Constant)this.values.get(b)).toHuman());
    } 
    return stringBuilder.toString();
  }
  
  public int codeSize() {
    return (this.initLength * this.elemWidth + 1) / 2 + 4;
  }
  
  protected String listingString0(boolean paramBoolean) {
    int i = this.user.getAddress();
    StringBuilder stringBuilder = new StringBuilder(100);
    int j = this.values.size();
    stringBuilder.append("fill-array-data-payload // for fill-array-data @ ");
    stringBuilder.append(Hex.u2(i));
    for (i = 0; i < j; i++) {
      stringBuilder.append("\n  ");
      stringBuilder.append(i);
      stringBuilder.append(": ");
      stringBuilder.append(((Constant)this.values.get(i)).toHuman());
    } 
    return stringBuilder.toString();
  }
  
  public DalvInsn withRegisters(RegisterSpecList paramRegisterSpecList) {
    return new ArrayData(getPosition(), this.user, this.values, this.arrayType);
  }
  
  public void writeTo(AnnotatedOutput paramAnnotatedOutput) {
    int i = this.values.size();
    paramAnnotatedOutput.writeShort(768);
    paramAnnotatedOutput.writeShort(this.elemWidth);
    paramAnnotatedOutput.writeInt(this.initLength);
    int j = this.elemWidth;
    if (j != 1) {
      if (j != 2) {
        if (j != 4) {
          if (j == 8)
            for (j = 0; j < i; j++)
              paramAnnotatedOutput.writeLong(((CstLiteral64)this.values.get(j)).getLongBits());  
        } else {
          for (j = 0; j < i; j++)
            paramAnnotatedOutput.writeInt(((CstLiteral32)this.values.get(j)).getIntBits()); 
        } 
      } else {
        for (j = 0; j < i; j++)
          paramAnnotatedOutput.writeShort((short)((CstLiteral32)this.values.get(j)).getIntBits()); 
      } 
    } else {
      for (j = 0; j < i; j++)
        paramAnnotatedOutput.writeByte((byte)((CstLiteral32)this.values.get(j)).getIntBits()); 
    } 
    if (this.elemWidth == 1 && i % 2 != 0)
      paramAnnotatedOutput.writeByte(0); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\ArrayData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */