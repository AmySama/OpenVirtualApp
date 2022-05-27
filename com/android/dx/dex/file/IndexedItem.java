package com.android.dx.dex.file;

public abstract class IndexedItem extends Item {
  private int index = -1;
  
  public final int getIndex() {
    int i = this.index;
    if (i >= 0)
      return i; 
    throw new RuntimeException("index not yet set");
  }
  
  public final boolean hasIndex() {
    boolean bool;
    if (this.index >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final String indexString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('[');
    stringBuilder.append(Integer.toHexString(this.index));
    stringBuilder.append(']');
    return stringBuilder.toString();
  }
  
  public final void setIndex(int paramInt) {
    if (this.index == -1) {
      this.index = paramInt;
      return;
    } 
    throw new RuntimeException("index already set");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\file\IndexedItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */