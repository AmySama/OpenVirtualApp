package org.jdeferred.multiple;

import org.jdeferred.Promise;

public class OneProgress extends MasterProgress {
  private final int index;
  
  private final Object progress;
  
  private final Promise promise;
  
  public OneProgress(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Promise paramPromise, Object paramObject) {
    super(paramInt1, paramInt2, paramInt3);
    this.index = paramInt4;
    this.promise = paramPromise;
    this.progress = paramObject;
  }
  
  public int getIndex() {
    return this.index;
  }
  
  public Object getProgress() {
    return this.progress;
  }
  
  public Promise getPromise() {
    return this.promise;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("OneProgress [index=");
    stringBuilder.append(this.index);
    stringBuilder.append(", promise=");
    stringBuilder.append(this.promise);
    stringBuilder.append(", progress=");
    stringBuilder.append(this.progress);
    stringBuilder.append(", getDone()=");
    stringBuilder.append(getDone());
    stringBuilder.append(", getFail()=");
    stringBuilder.append(getFail());
    stringBuilder.append(", getTotal()=");
    stringBuilder.append(getTotal());
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\multiple\OneProgress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */