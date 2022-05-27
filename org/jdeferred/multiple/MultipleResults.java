package org.jdeferred.multiple;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MultipleResults implements Iterable<OneResult> {
  private final List<OneResult> results;
  
  public MultipleResults(int paramInt) {
    this.results = new CopyOnWriteArrayList<OneResult>(new OneResult[paramInt]);
  }
  
  public OneResult get(int paramInt) {
    return this.results.get(paramInt);
  }
  
  public Iterator<OneResult> iterator() {
    return this.results.iterator();
  }
  
  protected void set(int paramInt, OneResult paramOneResult) {
    this.results.set(paramInt, paramOneResult);
  }
  
  public int size() {
    return this.results.size();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("MultipleResults [results=");
    stringBuilder.append(this.results);
    stringBuilder.append("]");
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\multiple\MultipleResults.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */