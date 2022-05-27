package com.lody.virtual.helper;

import android.util.SparseBooleanArray;

public class MultiAvoidRecursive {
  private SparseBooleanArray mCallings;
  
  public MultiAvoidRecursive() {
    this(7);
  }
  
  public MultiAvoidRecursive(int paramInt) {
    this.mCallings = new SparseBooleanArray(paramInt);
  }
  
  public boolean beginCall(int paramInt) {
    synchronized (this.mCallings) {
      if (this.mCallings.get(paramInt))
        return false; 
      this.mCallings.put(paramInt, true);
      return true;
    } 
  }
  
  public void finishCall(int paramInt) {
    synchronized (this.mCallings) {
      this.mCallings.put(paramInt, false);
      return;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\MultiAvoidRecursive.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */