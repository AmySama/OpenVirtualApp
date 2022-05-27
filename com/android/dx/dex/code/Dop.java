package com.android.dx.dex.code;

import com.android.dx.io.OpcodeInfo;
import com.android.dx.io.Opcodes;

public final class Dop {
  private final int family;
  
  private final InsnFormat format;
  
  private final boolean hasResult;
  
  private final int nextOpcode;
  
  private final int opcode;
  
  public Dop(int paramInt1, int paramInt2, int paramInt3, InsnFormat paramInsnFormat, boolean paramBoolean) {
    if (Opcodes.isValidShape(paramInt1)) {
      if (Opcodes.isValidShape(paramInt2)) {
        if (Opcodes.isValidShape(paramInt3)) {
          if (paramInsnFormat != null) {
            this.opcode = paramInt1;
            this.family = paramInt2;
            this.nextOpcode = paramInt3;
            this.format = paramInsnFormat;
            this.hasResult = paramBoolean;
            return;
          } 
          throw new NullPointerException("format == null");
        } 
        throw new IllegalArgumentException("bogus nextOpcode");
      } 
      throw new IllegalArgumentException("bogus family");
    } 
    throw new IllegalArgumentException("bogus opcode");
  }
  
  public int getFamily() {
    return this.family;
  }
  
  public InsnFormat getFormat() {
    return this.format;
  }
  
  public String getName() {
    return OpcodeInfo.getName(this.opcode);
  }
  
  public int getNextOpcode() {
    return this.nextOpcode;
  }
  
  public int getOpcode() {
    return this.opcode;
  }
  
  public Dop getOppositeTest() {
    StringBuilder stringBuilder;
    switch (this.opcode) {
      default:
        stringBuilder = new StringBuilder();
        stringBuilder.append("bogus opcode: ");
        stringBuilder.append(this);
        throw new IllegalArgumentException(stringBuilder.toString());
      case 61:
        return Dops.IF_GTZ;
      case 60:
        return Dops.IF_LEZ;
      case 59:
        return Dops.IF_LTZ;
      case 58:
        return Dops.IF_GEZ;
      case 57:
        return Dops.IF_EQZ;
      case 56:
        return Dops.IF_NEZ;
      case 55:
        return Dops.IF_GT;
      case 54:
        return Dops.IF_LE;
      case 53:
        return Dops.IF_LT;
      case 52:
        return Dops.IF_GE;
      case 51:
        return Dops.IF_EQ;
      case 50:
        break;
    } 
    return Dops.IF_NE;
  }
  
  public boolean hasResult() {
    return this.hasResult;
  }
  
  public String toString() {
    return getName();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\Dop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */