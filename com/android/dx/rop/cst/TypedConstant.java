package com.android.dx.rop.cst;

import com.android.dx.rop.type.TypeBearer;

public abstract class TypedConstant extends Constant implements TypeBearer {
  public final int getBasicFrameType() {
    return getType().getBasicFrameType();
  }
  
  public final int getBasicType() {
    return getType().getBasicType();
  }
  
  public final TypeBearer getFrameType() {
    return this;
  }
  
  public final boolean isConstant() {
    return true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\TypedConstant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */