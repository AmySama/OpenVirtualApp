package com.lody.virtual.helper.compat;

import mirror.android.os.StrictMode;

public class StrictModeCompat {
  public static int DETECT_VM_FILE_URI_EXPOSURE;
  
  public static int PENALTY_DEATH_ON_FILE_URI_EXPOSURE;
  
  static {
    int i;
    if (StrictMode.DETECT_VM_FILE_URI_EXPOSURE == null) {
      i = 8192;
    } else {
      i = StrictMode.DETECT_VM_FILE_URI_EXPOSURE.get();
    } 
    DETECT_VM_FILE_URI_EXPOSURE = i;
    if (StrictMode.PENALTY_DEATH_ON_FILE_URI_EXPOSURE == null) {
      i = 67108864;
    } else {
      i = StrictMode.PENALTY_DEATH_ON_FILE_URI_EXPOSURE.get();
    } 
    PENALTY_DEATH_ON_FILE_URI_EXPOSURE = i;
  }
  
  public static boolean disableDeathOnFileUriExposure() {
    try {
      return true;
    } finally {
      Exception exception = null;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\StrictModeCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */