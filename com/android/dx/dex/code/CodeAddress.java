package com.android.dx.dex.code;

import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.SourcePosition;

public final class CodeAddress extends ZeroSizeInsn {
  private final boolean bindsClosely;
  
  public CodeAddress(SourcePosition paramSourcePosition) {
    this(paramSourcePosition, false);
  }
  
  public CodeAddress(SourcePosition paramSourcePosition, boolean paramBoolean) {
    super(paramSourcePosition);
    this.bindsClosely = paramBoolean;
  }
  
  protected String argString() {
    return null;
  }
  
  public boolean getBindsClosely() {
    return this.bindsClosely;
  }
  
  protected String listingString0(boolean paramBoolean) {
    return "code-address";
  }
  
  public final DalvInsn withRegisters(RegisterSpecList paramRegisterSpecList) {
    return new CodeAddress(getPosition());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\CodeAddress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */