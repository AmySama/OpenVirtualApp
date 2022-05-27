package com.android.dx.rop.cst;

public abstract class CstLiteralBits extends TypedConstant {
  public boolean fitsIn16Bits() {
    boolean bool = fitsInInt();
    boolean bool1 = false;
    if (!bool)
      return false; 
    int i = getIntBits();
    if ((short)i == i)
      bool1 = true; 
    return bool1;
  }
  
  public boolean fitsIn8Bits() {
    boolean bool = fitsInInt();
    boolean bool1 = false;
    if (!bool)
      return false; 
    int i = getIntBits();
    if ((byte)i == i)
      bool1 = true; 
    return bool1;
  }
  
  public abstract boolean fitsInInt();
  
  public abstract int getIntBits();
  
  public abstract long getLongBits();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstLiteralBits.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */