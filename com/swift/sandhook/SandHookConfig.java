package com.swift.sandhook;

import android.os.Build;

public class SandHookConfig {
  static {
    DEBUG = true;
    if (SDK_INT < 29) {
      bool = true;
    } else {
      bool = false;
    } 
    compiler = bool;
    curUser = 0;
    delayHook = true;
  }
  
  static {
    boolean bool;
  }
  
  public static volatile boolean DEBUG;
  
  public static volatile int SDK_INT = Build.VERSION.SDK_INT;
  
  public static volatile boolean compiler;
  
  public static volatile int curUser;
  
  public static volatile boolean delayHook;
  
  public static volatile ClassLoader initClassLoader;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\SandHookConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */