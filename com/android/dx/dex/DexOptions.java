package com.android.dx.dex;

import com.android.dex.DexFormat;
import java.io.PrintStream;

public final class DexOptions {
  public static final boolean ALIGN_64BIT_REGS_SUPPORT = true;
  
  public boolean ALIGN_64BIT_REGS_IN_OUTPUT_FINISHER = true;
  
  public boolean allowAllInterfaceMethodInvokes = false;
  
  public final PrintStream err = System.err;
  
  public boolean forceJumbo = false;
  
  public int minSdkVersion = 13;
  
  public DexOptions() {}
  
  public DexOptions(PrintStream paramPrintStream) {}
  
  public boolean apiIsSupported(int paramInt) {
    boolean bool;
    if (this.minSdkVersion >= paramInt) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public String getMagic() {
    return DexFormat.apiToMagic(this.minSdkVersion);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\DexOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */