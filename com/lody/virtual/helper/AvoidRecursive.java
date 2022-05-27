package com.lody.virtual.helper;

public class AvoidRecursive {
  private boolean mCalling = false;
  
  public boolean beginCall() {
    if (this.mCalling)
      return false; 
    this.mCalling = true;
    return true;
  }
  
  public void finishCall() {
    this.mCalling = false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\AvoidRecursive.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */