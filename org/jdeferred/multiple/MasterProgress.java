package org.jdeferred.multiple;

public class MasterProgress {
  private final int done;
  
  private final int fail;
  
  private final int total;
  
  public MasterProgress(int paramInt1, int paramInt2, int paramInt3) {
    this.done = paramInt1;
    this.fail = paramInt2;
    this.total = paramInt3;
  }
  
  public int getDone() {
    return this.done;
  }
  
  public int getFail() {
    return this.fail;
  }
  
  public int getTotal() {
    return this.total;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("MasterProgress [done=");
    stringBuilder.append(this.done);
    stringBuilder.append(", fail=");
    stringBuilder.append(this.fail);
    stringBuilder.append(", total=");
    stringBuilder.append(this.total);
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\multiple\MasterProgress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */