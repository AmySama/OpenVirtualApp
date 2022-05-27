package com.android.dx.rop.cst;

public abstract class CstLiteral64 extends CstLiteralBits {
  private final long bits;
  
  CstLiteral64(long paramLong) {
    this.bits = paramLong;
  }
  
  protected int compareTo0(Constant paramConstant) {
    long l1 = ((CstLiteral64)paramConstant).bits;
    long l2 = this.bits;
    return (l2 < l1) ? -1 : ((l2 > l1) ? 1 : 0);
  }
  
  public final boolean equals(Object paramObject) {
    boolean bool;
    if (paramObject != null && getClass() == paramObject.getClass() && this.bits == ((CstLiteral64)paramObject).bits) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final boolean fitsInInt() {
    boolean bool;
    long l = this.bits;
    if ((int)l == l) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final int getIntBits() {
    return (int)this.bits;
  }
  
  public final long getLongBits() {
    return this.bits;
  }
  
  public final int hashCode() {
    long l = this.bits;
    return (int)l ^ (int)(l >> 32L);
  }
  
  public final boolean isCategory2() {
    return true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstLiteral64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */