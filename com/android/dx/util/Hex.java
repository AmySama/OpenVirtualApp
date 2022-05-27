package com.android.dx.util;

public final class Hex {
  public static String dump(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    int i = paramInt1 + paramInt2;
    if ((paramInt1 | paramInt2 | i) >= 0 && i <= paramArrayOfbyte.length) {
      if (paramInt3 >= 0) {
        if (paramInt2 == 0)
          return ""; 
        StringBuilder stringBuilder1 = new StringBuilder(paramInt2 * 4 + 6);
        int j = 0;
        i = paramInt1;
        paramInt1 = j;
        while (paramInt2 > 0) {
          if (paramInt1 == 0) {
            String str;
            if (paramInt5 != 2) {
              if (paramInt5 != 4) {
                if (paramInt5 != 6) {
                  str = u4(paramInt3);
                } else {
                  str = u3(paramInt3);
                } 
              } else {
                str = u2(paramInt3);
              } 
            } else {
              str = u1(paramInt3);
            } 
            stringBuilder1.append(str);
            stringBuilder1.append(": ");
          } else if ((paramInt1 & 0x1) == 0) {
            stringBuilder1.append(' ');
          } 
          stringBuilder1.append(u1(paramArrayOfbyte[i]));
          paramInt3++;
          i++;
          j = paramInt1 + 1;
          paramInt1 = j;
          if (j == paramInt4) {
            stringBuilder1.append('\n');
            paramInt1 = 0;
          } 
          paramInt2--;
        } 
        if (paramInt1 != 0)
          stringBuilder1.append('\n'); 
        return stringBuilder1.toString();
      } 
      throw new IllegalArgumentException("outOffset < 0");
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("arr.length ");
    stringBuilder.append(paramArrayOfbyte.length);
    stringBuilder.append("; ");
    stringBuilder.append(paramInt1);
    stringBuilder.append("..!");
    stringBuilder.append(i);
    throw new IndexOutOfBoundsException(stringBuilder.toString());
  }
  
  public static String s1(int paramInt) {
    char[] arrayOfChar = new char[3];
    byte b = 0;
    if (paramInt < 0) {
      arrayOfChar[0] = (char)'-';
      paramInt = -paramInt;
    } else {
      arrayOfChar[0] = (char)'+';
    } 
    while (b < 2) {
      arrayOfChar[2 - b] = Character.forDigit(paramInt & 0xF, 16);
      paramInt >>= 4;
      b++;
    } 
    return new String(arrayOfChar);
  }
  
  public static String s2(int paramInt) {
    char[] arrayOfChar = new char[5];
    byte b = 0;
    if (paramInt < 0) {
      arrayOfChar[0] = (char)'-';
      paramInt = -paramInt;
    } else {
      arrayOfChar[0] = (char)'+';
    } 
    while (b < 4) {
      arrayOfChar[4 - b] = Character.forDigit(paramInt & 0xF, 16);
      paramInt >>= 4;
      b++;
    } 
    return new String(arrayOfChar);
  }
  
  public static String s4(int paramInt) {
    char[] arrayOfChar = new char[9];
    byte b = 0;
    if (paramInt < 0) {
      arrayOfChar[0] = (char)'-';
      paramInt = -paramInt;
    } else {
      arrayOfChar[0] = (char)'+';
    } 
    while (b < 8) {
      arrayOfChar[8 - b] = Character.forDigit(paramInt & 0xF, 16);
      paramInt >>= 4;
      b++;
    } 
    return new String(arrayOfChar);
  }
  
  public static String s8(long paramLong) {
    char[] arrayOfChar = new char[17];
    byte b = 0;
    if (paramLong < 0L) {
      arrayOfChar[0] = (char)'-';
      paramLong = -paramLong;
    } else {
      arrayOfChar[0] = (char)'+';
    } 
    while (b < 16) {
      arrayOfChar[16 - b] = Character.forDigit((int)paramLong & 0xF, 16);
      paramLong >>= 4L;
      b++;
    } 
    return new String(arrayOfChar);
  }
  
  public static String u1(int paramInt) {
    char[] arrayOfChar = new char[2];
    for (byte b = 0; b < 2; b++) {
      arrayOfChar[1 - b] = Character.forDigit(paramInt & 0xF, 16);
      paramInt >>= 4;
    } 
    return new String(arrayOfChar);
  }
  
  public static String u2(int paramInt) {
    char[] arrayOfChar = new char[4];
    for (byte b = 0; b < 4; b++) {
      arrayOfChar[3 - b] = Character.forDigit(paramInt & 0xF, 16);
      paramInt >>= 4;
    } 
    return new String(arrayOfChar);
  }
  
  public static String u2or4(int paramInt) {
    return (paramInt == (char)paramInt) ? u2(paramInt) : u4(paramInt);
  }
  
  public static String u3(int paramInt) {
    char[] arrayOfChar = new char[6];
    for (byte b = 0; b < 6; b++) {
      arrayOfChar[5 - b] = Character.forDigit(paramInt & 0xF, 16);
      paramInt >>= 4;
    } 
    return new String(arrayOfChar);
  }
  
  public static String u4(int paramInt) {
    char[] arrayOfChar = new char[8];
    boolean bool = false;
    int i = paramInt;
    for (paramInt = bool; paramInt < 8; paramInt++) {
      arrayOfChar[7 - paramInt] = Character.forDigit(i & 0xF, 16);
      i >>= 4;
    } 
    return new String(arrayOfChar);
  }
  
  public static String u8(long paramLong) {
    char[] arrayOfChar = new char[16];
    for (byte b = 0; b < 16; b++) {
      arrayOfChar[15 - b] = Character.forDigit((int)paramLong & 0xF, 16);
      paramLong >>= 4L;
    } 
    return new String(arrayOfChar);
  }
  
  public static String uNibble(int paramInt) {
    return new String(new char[] { Character.forDigit(paramInt & 0xF, 16) });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\d\\util\Hex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */