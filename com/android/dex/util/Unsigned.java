package com.android.dex.util;

public final class Unsigned {
  public static int compare(int paramInt1, int paramInt2) {
    if (paramInt1 == paramInt2)
      return 0; 
    if ((paramInt1 & 0xFFFFFFFFL) < (paramInt2 & 0xFFFFFFFFL)) {
      paramInt1 = -1;
    } else {
      paramInt1 = 1;
    } 
    return paramInt1;
  }
  
  public static int compare(short paramShort1, short paramShort2) {
    if (paramShort1 == paramShort2)
      return 0; 
    if ((paramShort1 & 0xFFFF) < (paramShort2 & 0xFFFF)) {
      paramShort1 = -1;
    } else {
      paramShort1 = 1;
    } 
    return paramShort1;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\de\\util\Unsigned.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */