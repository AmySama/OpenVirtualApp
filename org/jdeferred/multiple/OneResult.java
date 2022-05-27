package org.jdeferred.multiple;

import org.jdeferred.Promise;

public class OneResult {
  private final int index;
  
  private final Promise promise;
  
  private final Object result;
  
  public OneResult(int paramInt, Promise paramPromise, Object paramObject) {
    this.index = paramInt;
    this.promise = paramPromise;
    this.result = paramObject;
  }
  
  public int getIndex() {
    return this.index;
  }
  
  public Promise getPromise() {
    return this.promise;
  }
  
  public Object getResult() {
    return this.result;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("OneResult [index=");
    stringBuilder.append(this.index);
    stringBuilder.append(", promise=");
    stringBuilder.append(this.promise);
    stringBuilder.append(", result=");
    stringBuilder.append(this.result);
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\multiple\OneResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */