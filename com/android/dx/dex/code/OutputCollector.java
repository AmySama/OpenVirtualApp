package com.android.dx.dex.code;

import com.android.dx.dex.DexOptions;
import java.util.ArrayList;

public final class OutputCollector {
  private final OutputFinisher finisher;
  
  private ArrayList<DalvInsn> suffix;
  
  public OutputCollector(DexOptions paramDexOptions, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.finisher = new OutputFinisher(paramDexOptions, paramInt1, paramInt3, paramInt4);
    this.suffix = new ArrayList<DalvInsn>(paramInt2);
  }
  
  private void appendSuffixToOutput() {
    int i = this.suffix.size();
    for (byte b = 0; b < i; b++)
      this.finisher.add(this.suffix.get(b)); 
    this.suffix = null;
  }
  
  public void add(DalvInsn paramDalvInsn) {
    this.finisher.add(paramDalvInsn);
  }
  
  public void addSuffix(DalvInsn paramDalvInsn) {
    this.suffix.add(paramDalvInsn);
  }
  
  public OutputFinisher getFinisher() {
    if (this.suffix != null) {
      appendSuffixToOutput();
      return this.finisher;
    } 
    throw new UnsupportedOperationException("already processed");
  }
  
  public void reverseBranch(int paramInt, CodeAddress paramCodeAddress) {
    this.finisher.reverseBranch(paramInt, paramCodeAddress);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\OutputCollector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */