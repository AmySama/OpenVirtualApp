package com.android.dx.cf.attrib;

import com.android.dx.cf.code.LineNumberList;
import com.android.dx.util.MutabilityException;

public final class AttLineNumberTable extends BaseAttribute {
  public static final String ATTRIBUTE_NAME = "LineNumberTable";
  
  private final LineNumberList lineNumbers;
  
  public AttLineNumberTable(LineNumberList paramLineNumberList) {
    super("LineNumberTable");
    try {
      boolean bool = paramLineNumberList.isMutable();
      if (!bool) {
        this.lineNumbers = paramLineNumberList;
        return;
      } 
      MutabilityException mutabilityException = new MutabilityException();
      this("lineNumbers.isMutable()");
      throw mutabilityException;
    } catch (NullPointerException nullPointerException) {
      throw new NullPointerException("lineNumbers == null");
    } 
  }
  
  public int byteLength() {
    return this.lineNumbers.size() * 4 + 8;
  }
  
  public LineNumberList getLineNumbers() {
    return this.lineNumbers;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\attrib\AttLineNumberTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */