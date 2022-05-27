package com.android.dx.dex.code;

import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.code.RegisterSpecList;
import com.android.dx.rop.code.RegisterSpecSet;
import com.android.dx.rop.code.SourcePosition;
import com.android.dx.ssa.RegisterMapper;

public final class LocalSnapshot extends ZeroSizeInsn {
  private final RegisterSpecSet locals;
  
  public LocalSnapshot(SourcePosition paramSourcePosition, RegisterSpecSet paramRegisterSpecSet) {
    super(paramSourcePosition);
    if (paramRegisterSpecSet != null) {
      this.locals = paramRegisterSpecSet;
      return;
    } 
    throw new NullPointerException("locals == null");
  }
  
  protected String argString() {
    return this.locals.toString();
  }
  
  public RegisterSpecSet getLocals() {
    return this.locals;
  }
  
  protected String listingString0(boolean paramBoolean) {
    int i = this.locals.size();
    int j = this.locals.getMaxSize();
    StringBuilder stringBuilder = new StringBuilder(i * 40 + 100);
    stringBuilder.append("local-snapshot");
    for (i = 0; i < j; i++) {
      RegisterSpec registerSpec = this.locals.get(i);
      if (registerSpec != null) {
        stringBuilder.append("\n  ");
        stringBuilder.append(LocalStart.localString(registerSpec));
      } 
    } 
    return stringBuilder.toString();
  }
  
  public DalvInsn withMapper(RegisterMapper paramRegisterMapper) {
    return new LocalSnapshot(getPosition(), paramRegisterMapper.map(this.locals));
  }
  
  public DalvInsn withRegisterOffset(int paramInt) {
    return new LocalSnapshot(getPosition(), this.locals.withOffset(paramInt));
  }
  
  public DalvInsn withRegisters(RegisterSpecList paramRegisterSpecList) {
    return new LocalSnapshot(getPosition(), this.locals);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\LocalSnapshot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */