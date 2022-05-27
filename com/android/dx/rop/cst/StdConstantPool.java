package com.android.dx.rop.cst;

import com.android.dex.util.ExceptionWithContext;
import com.android.dx.util.Hex;
import com.android.dx.util.MutabilityControl;

public final class StdConstantPool extends MutabilityControl implements ConstantPool {
  private final Constant[] entries;
  
  public StdConstantPool(int paramInt) {
    super(bool);
    boolean bool;
    if (paramInt >= 1) {
      this.entries = new Constant[paramInt];
      return;
    } 
    throw new IllegalArgumentException("size < 1");
  }
  
  private static Constant throwInvalid(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("invalid constant pool index ");
    stringBuilder.append(Hex.u2(paramInt));
    throw new ExceptionWithContext(stringBuilder.toString());
  }
  
  public Constant get(int paramInt) {
    try {
      Constant constant = this.entries[paramInt];
      if (constant == null)
        throwInvalid(paramInt); 
      return constant;
    } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
      return throwInvalid(paramInt);
    } 
  }
  
  public Constant get0Ok(int paramInt) {
    return (paramInt == 0) ? null : get(paramInt);
  }
  
  public Constant[] getEntries() {
    return this.entries;
  }
  
  public Constant getOrNull(int paramInt) {
    try {
      return this.entries[paramInt];
    } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
      return throwInvalid(paramInt);
    } 
  }
  
  public void set(int paramInt, Constant paramConstant) {
    int i;
    throwIfImmutable();
    if (paramConstant != null && paramConstant.isCategory2()) {
      i = 1;
    } else {
      i = 0;
    } 
    if (paramInt >= 1) {
      if (i) {
        Constant[] arrayOfConstant = this.entries;
        if (paramInt != arrayOfConstant.length - 1) {
          arrayOfConstant[paramInt + 1] = null;
        } else {
          throw new IllegalArgumentException("(n == size - 1) && cst.isCategory2()");
        } 
      } 
      if (paramConstant != null) {
        Constant[] arrayOfConstant = this.entries;
        if (arrayOfConstant[paramInt] == null) {
          i = paramInt - 1;
          Constant constant = arrayOfConstant[i];
          if (constant != null && constant.isCategory2())
            this.entries[i] = null; 
        } 
      } 
      this.entries[paramInt] = paramConstant;
      return;
    } 
    throw new IllegalArgumentException("n < 1");
  }
  
  public int size() {
    return this.entries.length;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\StdConstantPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */