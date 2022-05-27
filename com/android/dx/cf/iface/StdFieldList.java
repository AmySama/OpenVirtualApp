package com.android.dx.cf.iface;

import com.android.dx.util.FixedSizeList;

public final class StdFieldList extends FixedSizeList implements FieldList {
  public StdFieldList(int paramInt) {
    super(paramInt);
  }
  
  public Field get(int paramInt) {
    return (Field)get0(paramInt);
  }
  
  public void set(int paramInt, Field paramField) {
    set0(paramInt, paramField);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\iface\StdFieldList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */