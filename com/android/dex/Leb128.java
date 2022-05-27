package com.android.dex;

import com.android.dex.util.ByteInput;
import com.android.dex.util.ByteOutput;

public final class Leb128 {
  public static int readSignedLeb128(ByteInput paramByteInput) {
    int m;
    int n;
    int i1;
    int i = 0;
    int j = 0;
    int k = -1;
    while (true) {
      m = paramByteInput.readByte() & 0xFF;
      n = i | (m & 0x7F) << j * 7;
      i1 = k << 7;
      int i2 = j + 1;
      m &= 0x80;
      if (m == 128) {
        i = n;
        j = i2;
        k = i1;
        if (i2 >= 5)
          break; 
        continue;
      } 
      break;
    } 
    if (m != 128) {
      k = n;
      if ((i1 >> 1 & n) != 0)
        k = n | i1; 
      return k;
    } 
    throw new DexException("invalid LEB128 sequence");
  }
  
  public static int readUnsignedLeb128(ByteInput paramByteInput) {
    int k;
    int m;
    int i = 0;
    int j = 0;
    while (true) {
      k = paramByteInput.readByte() & 0xFF;
      m = i | (k & 0x7F) << j * 7;
      int n = j + 1;
      k &= 0x80;
      if (k == 128) {
        i = m;
        j = n;
        if (n >= 5)
          break; 
        continue;
      } 
      break;
    } 
    if (k != 128)
      return m; 
    throw new DexException("invalid LEB128 sequence");
  }
  
  public static int unsignedLeb128Size(int paramInt) {
    paramInt >>= 7;
    byte b;
    for (b = 0; paramInt != 0; b++)
      paramInt >>= 7; 
    return b + 1;
  }
  
  public static void writeSignedLeb128(ByteOutput paramByteOutput, int paramInt) {
    byte b;
    int i = paramInt >> 7;
    if ((Integer.MIN_VALUE & paramInt) == 0) {
      b = 0;
    } else {
      b = -1;
    } 
    int j = 1;
    while (true) {
      int k = paramInt;
      int m = i;
      if (j) {
        if (m != b || (m & 0x1) != (k >> 6 & 0x1)) {
          paramInt = 1;
        } else {
          paramInt = 0;
        } 
        if (paramInt != 0) {
          i = 128;
        } else {
          i = 0;
        } 
        paramByteOutput.writeByte((byte)(k & 0x7F | i));
        i = m >> 7;
        j = paramInt;
        paramInt = m;
        continue;
      } 
      break;
    } 
  }
  
  public static void writeUnsignedLeb128(ByteOutput paramByteOutput, int paramInt) {
    while (true) {
      int i = paramInt;
      paramInt = i >>> 7;
      if (paramInt != 0) {
        paramByteOutput.writeByte((byte)(i & 0x7F | 0x80));
        continue;
      } 
      paramByteOutput.writeByte((byte)(i & 0x7F));
      return;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dex\Leb128.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */