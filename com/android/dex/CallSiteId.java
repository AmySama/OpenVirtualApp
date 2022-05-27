package com.android.dex;

import com.android.dex.util.Unsigned;

public class CallSiteId implements Comparable<CallSiteId> {
  private final Dex dex;
  
  private final int offset;
  
  public CallSiteId(Dex paramDex, int paramInt) {
    this.dex = paramDex;
    this.offset = paramInt;
  }
  
  public int compareTo(CallSiteId paramCallSiteId) {
    return Unsigned.compare(this.offset, paramCallSiteId.offset);
  }
  
  public int getCallSiteOffset() {
    return this.offset;
  }
  
  public String toString() {
    Dex dex = this.dex;
    return (dex == null) ? String.valueOf(this.offset) : ((ProtoId)dex.protoIds().get(this.offset)).toString();
  }
  
  public void writeTo(Dex.Section paramSection) {
    paramSection.writeInt(this.offset);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dex\CallSiteId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */