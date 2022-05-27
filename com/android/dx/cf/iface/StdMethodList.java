package com.android.dx.cf.iface;

import com.android.dx.util.FixedSizeList;

public final class StdMethodList extends FixedSizeList implements MethodList {
  public StdMethodList(int paramInt) {
    super(paramInt);
  }
  
  public Method get(int paramInt) {
    return (Method)get0(paramInt);
  }
  
  public void set(int paramInt, Method paramMethod) {
    set0(paramInt, paramMethod);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\iface\StdMethodList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */