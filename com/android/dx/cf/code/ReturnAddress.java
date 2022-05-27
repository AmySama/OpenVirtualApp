package com.android.dx.cf.code;

import com.android.dx.rop.type.Type;
import com.android.dx.rop.type.TypeBearer;
import com.android.dx.util.Hex;

public final class ReturnAddress implements TypeBearer {
  private final int subroutineAddress;
  
  public ReturnAddress(int paramInt) {
    if (paramInt >= 0) {
      this.subroutineAddress = paramInt;
      return;
    } 
    throw new IllegalArgumentException("subroutineAddress < 0");
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = paramObject instanceof ReturnAddress;
    boolean bool1 = false;
    if (!bool)
      return false; 
    if (this.subroutineAddress == ((ReturnAddress)paramObject).subroutineAddress)
      bool1 = true; 
    return bool1;
  }
  
  public int getBasicFrameType() {
    return Type.RETURN_ADDRESS.getBasicFrameType();
  }
  
  public int getBasicType() {
    return Type.RETURN_ADDRESS.getBasicType();
  }
  
  public TypeBearer getFrameType() {
    return this;
  }
  
  public int getSubroutineAddress() {
    return this.subroutineAddress;
  }
  
  public Type getType() {
    return Type.RETURN_ADDRESS;
  }
  
  public int hashCode() {
    return this.subroutineAddress;
  }
  
  public boolean isConstant() {
    return false;
  }
  
  public String toHuman() {
    return toString();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<addr:");
    stringBuilder.append(Hex.u2(this.subroutineAddress));
    stringBuilder.append(">");
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\ReturnAddress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */