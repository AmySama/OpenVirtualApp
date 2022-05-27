package com.android.dx.util;

public final class Bits {
  public static boolean anyInRange(int[] paramArrayOfint, int paramInt1, int paramInt2) {
    boolean bool;
    paramInt1 = findFirst(paramArrayOfint, paramInt1);
    if (paramInt1 >= 0 && paramInt1 < paramInt2) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static int bitCount(int[] paramArrayOfint) {
    int i = paramArrayOfint.length;
    byte b = 0;
    int j = 0;
    while (b < i) {
      j += Integer.bitCount(paramArrayOfint[b]);
      b++;
    } 
    return j;
  }
  
  public static void clear(int[] paramArrayOfint, int paramInt) {
    int i = paramInt >> 5;
    paramArrayOfint[i] = 1 << (paramInt & 0x1F) & paramArrayOfint[i];
  }
  
  public static int findFirst(int paramInt1, int paramInt2) {
    paramInt2 = Integer.numberOfTrailingZeros(paramInt1 & (1 << paramInt2) - 1);
    paramInt1 = paramInt2;
    if (paramInt2 == 32)
      paramInt1 = -1; 
    return paramInt1;
  }
  
  public static int findFirst(int[] paramArrayOfint, int paramInt) {
    int i = paramArrayOfint.length;
    int j = paramInt & 0x1F;
    for (paramInt >>= 5; paramInt < i; paramInt++) {
      int k = paramArrayOfint[paramInt];
      if (k != 0) {
        j = findFirst(k, j);
        if (j >= 0)
          return (paramInt << 5) + j; 
      } 
      j = 0;
    } 
    return -1;
  }
  
  public static boolean get(int[] paramArrayOfint, int paramInt) {
    boolean bool = true;
    if ((paramArrayOfint[paramInt >> 5] & 1 << (paramInt & 0x1F)) == 0)
      bool = false; 
    return bool;
  }
  
  public static int getMax(int[] paramArrayOfint) {
    return paramArrayOfint.length * 32;
  }
  
  public static boolean isEmpty(int[] paramArrayOfint) {
    int i = paramArrayOfint.length;
    for (byte b = 0; b < i; b++) {
      if (paramArrayOfint[b] != 0)
        return false; 
    } 
    return true;
  }
  
  public static int[] makeBitSet(int paramInt) {
    return new int[paramInt + 31 >> 5];
  }
  
  public static void or(int[] paramArrayOfint1, int[] paramArrayOfint2) {
    for (byte b = 0; b < paramArrayOfint2.length; b++)
      paramArrayOfint1[b] = paramArrayOfint1[b] | paramArrayOfint2[b]; 
  }
  
  public static void set(int[] paramArrayOfint, int paramInt) {
    int i = paramInt >> 5;
    paramArrayOfint[i] = 1 << (paramInt & 0x1F) | paramArrayOfint[i];
  }
  
  public static void set(int[] paramArrayOfint, int paramInt, boolean paramBoolean) {
    int i = paramInt >> 5;
    paramInt = 1 << (paramInt & 0x1F);
    if (paramBoolean) {
      paramArrayOfint[i] = paramInt | paramArrayOfint[i];
    } else {
      paramArrayOfint[i] = paramInt & paramArrayOfint[i];
    } 
  }
  
  public static String toHuman(int[] paramArrayOfint) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('{');
    int i = paramArrayOfint.length;
    byte b = 0;
    boolean bool;
    for (bool = false; b < i * 32; bool = bool1) {
      boolean bool1 = bool;
      if (get(paramArrayOfint, b)) {
        if (bool)
          stringBuilder.append(','); 
        stringBuilder.append(b);
        bool1 = true;
      } 
      b++;
    } 
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\d\\util\Bits.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */