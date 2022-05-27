package com.android.dex;

import com.android.dex.util.ByteInput;
import java.io.UTFDataFormatException;

public final class Mutf8 {
  private static long countBytes(String paramString, boolean paramBoolean) throws UTFDataFormatException {
    int i = paramString.length();
    long l = 0L;
    byte b = 0;
    while (b < i) {
      long l1;
      char c = paramString.charAt(b);
      if (c != '\000' && c <= '') {
        l1 = 1L;
      } else if (c <= '߿') {
        l1 = 2L;
      } else {
        l1 = 3L;
      } 
      l += l1;
      if (!paramBoolean || l <= 65535L) {
        b++;
        continue;
      } 
      throw new UTFDataFormatException("String more than 65535 UTF bytes long");
    } 
    return l;
  }
  
  public static String decode(ByteInput paramByteInput, char[] paramArrayOfchar) throws UTFDataFormatException {
    int i = 0;
    while (true) {
      char c = (char)(paramByteInput.readByte() & 0xFF);
      if (c == '\000')
        return new String(paramArrayOfchar, 0, i); 
      paramArrayOfchar[i] = (char)c;
      if (c < '') {
        i++;
        continue;
      } 
      if ((c & 0xE0) == 192) {
        int j = paramByteInput.readByte() & 0xFF;
        if ((j & 0xC0) == 128) {
          int k = i + 1;
          paramArrayOfchar[i] = (char)(char)((c & 0x1F) << 6 | j & 0x3F);
          i = k;
          continue;
        } 
        throw new UTFDataFormatException("bad second byte");
      } 
      if ((c & 0xF0) == 224) {
        int j = paramByteInput.readByte() & 0xFF;
        int k = paramByteInput.readByte() & 0xFF;
        if ((j & 0xC0) == 128) {
          if ((k & 0xC0) == 128) {
            int m = i + 1;
            paramArrayOfchar[i] = (char)(char)((c & 0xF) << 12 | (j & 0x3F) << 6 | k & 0x3F);
            i = m;
            continue;
          } 
          throw new UTFDataFormatException("bad second or third byte");
        } 
        continue;
      } 
      throw new UTFDataFormatException("bad byte");
    } 
  }
  
  public static void encode(byte[] paramArrayOfbyte, int paramInt, String paramString) {
    int i = paramString.length();
    for (byte b = 0; b < i; b++) {
      char c = paramString.charAt(b);
      if (c != '\000' && c <= '') {
        int j = paramInt + 1;
        paramArrayOfbyte[paramInt] = (byte)(byte)c;
        paramInt = j;
      } else if (c <= '߿') {
        int j = paramInt + 1;
        paramArrayOfbyte[paramInt] = (byte)(byte)(c >> 6 & 0x1F | 0xC0);
        paramInt = j + 1;
        paramArrayOfbyte[j] = (byte)(byte)(c & 0x3F | 0x80);
      } else {
        int k = paramInt + 1;
        paramArrayOfbyte[paramInt] = (byte)(byte)(c >> 12 & 0xF | 0xE0);
        int j = k + 1;
        paramArrayOfbyte[k] = (byte)(byte)(c >> 6 & 0x3F | 0x80);
        paramInt = j + 1;
        paramArrayOfbyte[j] = (byte)(byte)(c & 0x3F | 0x80);
      } 
    } 
  }
  
  public static byte[] encode(String paramString) throws UTFDataFormatException {
    byte[] arrayOfByte = new byte[(int)countBytes(paramString, true)];
    encode(arrayOfByte, 0, paramString);
    return arrayOfByte;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dex\Mutf8.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */