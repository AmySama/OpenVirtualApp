package com.swift.sandhook;

import android.util.Log;

public class ClassNeverCall {
  private void neverCall() {}
  
  private void neverCall2() {
    Log.e("ClassNeverCall", "ClassNeverCall2");
  }
  
  private native void neverCallNative();
  
  private native void neverCallNative2();
  
  private static void neverCallStatic() {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\ClassNeverCall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */