package com.android.dx.rop.cst;

import com.android.dx.rop.type.Type;
import com.android.dx.util.ByteArray;
import com.android.dx.util.Hex;

public final class CstString extends TypedConstant {
  public static final CstString EMPTY_STRING = new CstString("");
  
  private final ByteArray bytes;
  
  private final String string;
  
  public CstString(ByteArray paramByteArray) {
    if (paramByteArray != null) {
      this.bytes = paramByteArray;
      this.string = utf8BytesToString(paramByteArray).intern();
      return;
    } 
    throw new NullPointerException("bytes == null");
  }
  
  public CstString(String paramString) {
    if (paramString != null) {
      this.string = paramString.intern();
      this.bytes = new ByteArray(stringToUtf8Bytes(paramString));
      return;
    } 
    throw new NullPointerException("string == null");
  }
  
  public static byte[] stringToUtf8Bytes(String paramString) {
    int i = paramString.length();
    byte[] arrayOfByte2 = new byte[i * 3];
    byte b1 = 0;
    byte b2 = 0;
    while (b1 < i) {
      char c = paramString.charAt(b1);
      if (c != '\000' && c < '') {
        arrayOfByte2[b2] = (byte)(byte)c;
        b2++;
      } else if (c < 'ࠀ') {
        arrayOfByte2[b2] = (byte)(byte)(c >> 6 & 0x1F | 0xC0);
        arrayOfByte2[b2 + 1] = (byte)(byte)(c & 0x3F | 0x80);
        b2 += 2;
      } else {
        arrayOfByte2[b2] = (byte)(byte)(c >> 12 & 0xF | 0xE0);
        arrayOfByte2[b2 + 1] = (byte)(byte)(c >> 6 & 0x3F | 0x80);
        arrayOfByte2[b2 + 2] = (byte)(byte)(c & 0x3F | 0x80);
        b2 += 3;
      } 
      b1++;
    } 
    byte[] arrayOfByte1 = new byte[b2];
    System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, b2);
    return arrayOfByte1;
  }
  
  private static String throwBadUtf8(int paramInt1, int paramInt2) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("bad utf-8 byte ");
    stringBuilder.append(Hex.u1(paramInt1));
    stringBuilder.append(" at offset ");
    stringBuilder.append(Hex.u4(paramInt2));
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public static String utf8BytesToString(ByteArray paramByteArray) {
    int i = paramByteArray.size();
    char[] arrayOfChar = new char[i];
    byte b1 = 0;
    byte b2 = 0;
    while (i > 0) {
      int k;
      int m;
      int n;
      int i1;
      int j = paramByteArray.getUnsignedByte(b2);
      switch (j >> 4) {
        default:
          return throwBadUtf8(j, b2);
        case 14:
          i -= 3;
          if (i < 0)
            return throwBadUtf8(j, b2); 
          k = b2 + 1;
          m = paramByteArray.getUnsignedByte(k);
          n = m & 0xC0;
          if (n != 128)
            return throwBadUtf8(m, k); 
          k = b2 + 2;
          i1 = paramByteArray.getUnsignedByte(k);
          if (n != 128)
            return throwBadUtf8(i1, k); 
          j = (j & 0xF) << 12 | (m & 0x3F) << 6 | i1 & 0x3F;
          if (j < 2048)
            return throwBadUtf8(i1, k); 
          j = (char)j;
          b2 += 3;
          break;
        case 12:
        case 13:
          i -= 2;
          if (i < 0)
            return throwBadUtf8(j, b2); 
          n = b2 + 1;
          k = paramByteArray.getUnsignedByte(n);
          if ((k & 0xC0) != 128)
            return throwBadUtf8(k, n); 
          j = (j & 0x1F) << 6 | k & 0x3F;
          if (j != 0 && j < 128)
            return throwBadUtf8(k, n); 
          j = (char)j;
          b2 += 2;
          break;
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
          i--;
          if (j == 0)
            return throwBadUtf8(j, b2); 
          j = (char)j;
          b2++;
          break;
      } 
      arrayOfChar[b1] = (char)j;
      b1++;
    } 
    return new String(arrayOfChar, 0, b1);
  }
  
  protected int compareTo0(Constant paramConstant) {
    return this.string.compareTo(((CstString)paramConstant).string);
  }
  
  public boolean equals(Object paramObject) {
    return !(paramObject instanceof CstString) ? false : this.string.equals(((CstString)paramObject).string);
  }
  
  public ByteArray getBytes() {
    return this.bytes;
  }
  
  public String getString() {
    return this.string;
  }
  
  public Type getType() {
    return Type.STRING;
  }
  
  public int getUtf16Size() {
    return this.string.length();
  }
  
  public int getUtf8Size() {
    return this.bytes.size();
  }
  
  public int hashCode() {
    return this.string.hashCode();
  }
  
  public boolean isCategory2() {
    return false;
  }
  
  public String toHuman() {
    int i = this.string.length();
    StringBuilder stringBuilder = new StringBuilder(i * 3 / 2);
    for (byte b = 0; b < i; b++) {
      char c = this.string.charAt(b);
      if (c >= ' ' && c < '') {
        if (c == '\'' || c == '"' || c == '\\')
          stringBuilder.append('\\'); 
        stringBuilder.append(c);
        continue;
      } 
      if (c <= '') {
        if (c != '\t') {
          if (c != '\n') {
            if (c != '\r') {
              byte b1;
              Object object;
              if (b < i - 1) {
                b1 = this.string.charAt(b + 1);
              } else {
                b1 = 0;
              } 
              if (b1 >= 48 && b1 <= 55) {
                b1 = 1;
              } else {
                b1 = 0;
              } 
              stringBuilder.append('\\');
              byte b2 = 6;
              while (true) {
                b2 -= 3;
                object = SYNTHETIC_LOCAL_VARIABLE_8;
              } 
              if (object == null)
                stringBuilder.append('0'); 
              continue;
            } 
            stringBuilder.append("\\r");
            continue;
          } 
          stringBuilder.append("\\n");
          continue;
        } 
        stringBuilder.append("\\t");
        continue;
      } 
      stringBuilder.append("\\u");
      stringBuilder.append(Character.forDigit(c >> 12, 16));
      stringBuilder.append(Character.forDigit(c >> 8 & 0xF, 16));
      stringBuilder.append(Character.forDigit(c >> 4 & 0xF, 16));
      stringBuilder.append(Character.forDigit(c & 0xF, 16));
      continue;
    } 
    return stringBuilder.toString();
  }
  
  public String toQuoted() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('"');
    stringBuilder.append(toHuman());
    stringBuilder.append('"');
    return stringBuilder.toString();
  }
  
  public String toQuoted(int paramInt) {
    String str2;
    String str1 = toHuman();
    if (str1.length() <= paramInt - 2) {
      str2 = "";
    } else {
      str1 = str1.substring(0, paramInt - 5);
      str2 = "...";
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('"');
    stringBuilder.append(str1);
    stringBuilder.append(str2);
    stringBuilder.append('"');
    return stringBuilder.toString();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("string{\"");
    stringBuilder.append(toHuman());
    stringBuilder.append("\"}");
    return stringBuilder.toString();
  }
  
  public String typeName() {
    return "utf8";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstString.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */