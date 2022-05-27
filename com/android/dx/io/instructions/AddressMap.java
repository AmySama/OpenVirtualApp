package com.android.dx.io.instructions;

import java.util.HashMap;

public final class AddressMap {
  private final HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
  
  public int get(int paramInt) {
    Integer integer = this.map.get(Integer.valueOf(paramInt));
    if (integer == null) {
      paramInt = -1;
    } else {
      paramInt = integer.intValue();
    } 
    return paramInt;
  }
  
  public void put(int paramInt1, int paramInt2) {
    this.map.put(Integer.valueOf(paramInt1), Integer.valueOf(paramInt2));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\io\instructions\AddressMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */