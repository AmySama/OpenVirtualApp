package com.android.dx.rop.cst;

public interface ConstantPool {
  Constant get(int paramInt);
  
  Constant get0Ok(int paramInt);
  
  Constant[] getEntries();
  
  Constant getOrNull(int paramInt);
  
  int size();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\ConstantPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */