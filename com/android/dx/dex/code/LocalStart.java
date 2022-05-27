package com.android.dx.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.ssa.RegisterMapper;

public final class LocalStart extends ZeroSizeInsn {
  private final RegisterSpec local;
  
  public LocalStart(SourcePosition paramSourcePosition, RegisterSpec paramRegisterSpec) {
    super(paramSourcePosition);
    if (paramRegisterSpec != null) {
      this.local = paramRegisterSpec;
      return;
    } 
    throw new NullPointerException("local == null");
  }
  
  public static String localString(RegisterSpec paramRegisterSpec) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramRegisterSpec.regString());
    stringBuilder.append(' ');
    stringBuilder.append(paramRegisterSpec.getLocalItem().toString());
    stringBuilder.append(": ");
    stringBuilder.append(paramRegisterSpec.getTypeBearer().toHuman());
    return stringBuilder.toString();
  }
  
  protected String argString() {
    return this.local.toString();
  }
  
  public RegisterSpec getLocal() {
    return this.local;
  }
  
  protected String listingString0(boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("local-start ");
    stringBuilder.append(localString(this.local));
    return stringBuilder.toString();
  }
  
  public DalvInsn withMapper(RegisterMapper paramRegisterMapper) {
    return new LocalStart(getPosition(), paramRegisterMapper.map(this.local));
  }
  
  public DalvInsn withRegisterOffset(int paramInt) {
    return new LocalStart(getPosition(), this.local.withOffset(paramInt));
  }
  
  public DalvInsn withRegisters(RegisterSpecList paramRegisterSpecList) {
    return new LocalStart(getPosition(), this.local);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\LocalStart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */