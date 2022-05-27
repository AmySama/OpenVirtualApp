package com.android.dx.rop.type;

import com.android.dx.util.ToHuman;

public interface TypeBearer extends ToHuman {
  int getBasicFrameType();
  
  int getBasicType();
  
  TypeBearer getFrameType();
  
  Type getType();
  
  boolean isConstant();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\type\TypeBearer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */