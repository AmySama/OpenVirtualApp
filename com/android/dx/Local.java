package com.android.dx;

import com.android.dx.rop.code.RegisterSpec;
import com.android.dx.rop.type.TypeBearer;

public final class Local<T> {
  private final Code code;
  
  private int reg = -1;
  
  private RegisterSpec spec;
  
  final TypeId<T> type;
  
  private Local(Code paramCode, TypeId<T> paramTypeId) {
    this.code = paramCode;
    this.type = paramTypeId;
  }
  
  static <T> Local<T> get(Code paramCode, TypeId<T> paramTypeId) {
    return new Local<T>(paramCode, paramTypeId);
  }
  
  public TypeId getType() {
    return this.type;
  }
  
  int initialize(int paramInt) {
    this.reg = paramInt;
    this.spec = RegisterSpec.make(paramInt, (TypeBearer)this.type.ropType);
    return size();
  }
  
  int size() {
    return this.type.ropType.getCategory();
  }
  
  RegisterSpec spec() {
    if (this.spec == null) {
      this.code.initializeLocals();
      if (this.spec == null)
        throw new AssertionError(); 
    } 
    return this.spec;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("v");
    stringBuilder.append(this.reg);
    stringBuilder.append("(");
    stringBuilder.append(this.type);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\Local.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */