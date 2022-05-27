package com.android.dx.dex.cf;

import com.android.dx.dex.code.DalvCode;
import com.android.dx.rop.code.RopMethod;
import java.io.PrintStream;

public final class CodeStatistics {
  private static final boolean DEBUG = false;
  
  public int dexRunningDeltaInsns = 0;
  
  public int dexRunningDeltaRegisters = 0;
  
  public int dexRunningTotalInsns = 0;
  
  public int runningDeltaInsns = 0;
  
  public int runningDeltaRegisters = 0;
  
  public int runningOriginalBytes = 0;
  
  public int runningTotalInsns = 0;
  
  public void dumpStatistics(PrintStream paramPrintStream) {
    int i = this.runningDeltaInsns;
    int j = this.runningTotalInsns;
    int k = this.runningDeltaInsns;
    paramPrintStream.printf("Optimizer Delta Rop Insns: %d total: %d (%.2f%%) Delta Registers: %d\n", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Double.valueOf((k / (this.runningTotalInsns + Math.abs(k))) * 100.0D), Integer.valueOf(this.runningDeltaRegisters) });
    i = this.dexRunningDeltaInsns;
    k = this.dexRunningTotalInsns;
    j = this.dexRunningDeltaInsns;
    paramPrintStream.printf("Optimizer Delta Dex Insns: Insns: %d total: %d (%.2f%%) Delta Registers: %d\n", new Object[] { Integer.valueOf(i), Integer.valueOf(k), Double.valueOf((j / (this.dexRunningTotalInsns + Math.abs(j))) * 100.0D), Integer.valueOf(this.dexRunningDeltaRegisters) });
    paramPrintStream.printf("Original bytecode byte count: %d\n", new Object[] { Integer.valueOf(this.runningOriginalBytes) });
  }
  
  public void updateDexStatistics(DalvCode paramDalvCode1, DalvCode paramDalvCode2) {
    this.dexRunningDeltaInsns += paramDalvCode2.getInsns().codeSize() - paramDalvCode1.getInsns().codeSize();
    this.dexRunningDeltaRegisters += paramDalvCode2.getInsns().getRegistersSize() - paramDalvCode1.getInsns().getRegistersSize();
    this.dexRunningTotalInsns += paramDalvCode2.getInsns().codeSize();
  }
  
  public void updateOriginalByteCount(int paramInt) {
    this.runningOriginalBytes += paramInt;
  }
  
  public void updateRopStatistics(RopMethod paramRopMethod1, RopMethod paramRopMethod2) {
    int i = paramRopMethod1.getBlocks().getEffectiveInstructionCount();
    int j = paramRopMethod1.getBlocks().getRegCount();
    int k = paramRopMethod2.getBlocks().getEffectiveInstructionCount();
    this.runningDeltaInsns += k - i;
    this.runningDeltaRegisters += paramRopMethod2.getBlocks().getRegCount() - j;
    this.runningTotalInsns += k;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\cf\CodeStatistics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */