package com.android.dx.util;

public class MutabilityControl {
  private boolean mutable = true;
  
  public MutabilityControl() {}
  
  public MutabilityControl(boolean paramBoolean) {}
  
  public final boolean isImmutable() {
    return this.mutable ^ true;
  }
  
  public final boolean isMutable() {
    return this.mutable;
  }
  
  public void setImmutable() {
    this.mutable = false;
  }
  
  public final void throwIfImmutable() {
    if (this.mutable)
      return; 
    throw new MutabilityException("immutable instance");
  }
  
  public final void throwIfMutable() {
    if (!this.mutable)
      return; 
    throw new MutabilityException("mutable instance");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\d\\util\MutabilityControl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */