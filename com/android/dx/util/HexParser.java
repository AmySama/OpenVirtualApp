package com.android.dx.util;

public final class HexParser {
  public static byte[] parse(String paramString) {
    int i = paramString.length();
    int j = i / 2;
    byte[] arrayOfByte2 = new byte[j];
    int k = 0;
    int m = 0;
    while (k < i) {
      String str1;
      int n = paramString.indexOf('\n', k);
      int i1 = n;
      if (n < 0)
        i1 = i; 
      n = paramString.indexOf('#', k);
      if (n >= 0 && n < i1) {
        str1 = paramString.substring(k, n);
      } else {
        str1 = paramString.substring(k, i1);
      } 
      n = str1.indexOf(':');
      String str2 = str1;
      if (n != -1) {
        k = str1.indexOf('"');
        if (k != -1 && k < n) {
          str2 = str1;
        } else {
          String str = str1.substring(0, n).trim();
          str2 = str1.substring(n + 1);
          if (Integer.parseInt(str, 16) != m) {
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("bogus offset marker: ");
            stringBuilder1.append(str);
            throw new RuntimeException(stringBuilder1.toString());
          } 
        } 
      } 
      int i2 = str2.length();
      byte b = 0;
      int i3 = -1;
      k = 0;
      n = m;
      while (true) {
        if (b < i2) {
          char c = str2.charAt(b);
          if (k != 0) {
            if (c == '"') {
              k = 0;
              m = i3;
            } else {
              arrayOfByte2[n] = (byte)(byte)c;
              n++;
              m = i3;
            } 
          } else {
            if (c > ' ')
              if (c == '"') {
                if (i3 == -1) {
                  k = 1;
                } else {
                  StringBuilder stringBuilder1 = new StringBuilder();
                  stringBuilder1.append("spare digit around offset ");
                  stringBuilder1.append(Hex.u4(n));
                  throw new RuntimeException(stringBuilder1.toString());
                } 
              } else {
                m = Character.digit(c, 16);
                if (m != -1) {
                  if (i3 != -1) {
                    arrayOfByte2[n] = (byte)(byte)(i3 << 4 | m);
                    n++;
                    m = -1;
                  } 
                } else {
                  StringBuilder stringBuilder1 = new StringBuilder();
                  stringBuilder1.append("bogus digit character: \"");
                  stringBuilder1.append(c);
                  stringBuilder1.append("\"");
                  throw new RuntimeException(stringBuilder1.toString());
                } 
                b++;
                i3 = m;
              }  
            m = i3;
          } 
        } else {
          break;
        } 
        b++;
        i3 = m;
      } 
      if (i3 == -1) {
        if (k == 0) {
          k = i1 + 1;
          m = n;
          continue;
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("unterminated quote around offset ");
        stringBuilder1.append(Hex.u4(n));
        throw new RuntimeException(stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("spare digit around offset ");
      stringBuilder.append(Hex.u4(n));
      throw new RuntimeException(stringBuilder.toString());
    } 
    byte[] arrayOfByte1 = arrayOfByte2;
    if (m < j) {
      arrayOfByte1 = new byte[m];
      System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, m);
    } 
    return arrayOfByte1;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\d\\util\HexParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */