package com.tencent.lbssearch.a.a.d;

import com.tencent.lbssearch.a.a.b.a.d;
import com.tencent.lbssearch.a.a.b.e;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

public class a implements Closeable {
  private static final char[] a = ")]}'\n".toCharArray();
  
  private static final e m = new e();
  
  private final Reader b;
  
  private boolean c = false;
  
  private final char[] d = new char[1024];
  
  private int e = 0;
  
  private int f = 0;
  
  private int g = 0;
  
  private int h = 0;
  
  private int i = 0;
  
  private long j;
  
  private int k;
  
  private String l;
  
  private int[] n;
  
  private int o;
  
  static {
    e.a = new e() {
        public void a(a param1a) throws IOException {
          if (param1a instanceof d) {
            ((d)param1a).o();
            return;
          } 
          int i = a.a(param1a);
          int j = i;
          if (i == 0)
            j = a.b(param1a); 
          if (j == 13) {
            a.a(param1a, 9);
          } else if (j == 12) {
            a.a(param1a, 8);
          } else {
            if (j == 14) {
              a.a(param1a, 10);
              return;
            } 
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected a name but was ");
            stringBuilder.append(param1a.f());
            stringBuilder.append(" ");
            stringBuilder.append(" at line ");
            stringBuilder.append(a.c(param1a));
            stringBuilder.append(" column ");
            stringBuilder.append(a.d(param1a));
            throw new IllegalStateException(stringBuilder.toString());
          } 
        }
      };
  }
  
  public a(Reader paramReader) {
    int[] arrayOfInt = new int[32];
    this.n = arrayOfInt;
    this.o = 0;
    this.o = 0 + 1;
    arrayOfInt[0] = 6;
    if (paramReader != null) {
      this.b = paramReader;
      return;
    } 
    throw new NullPointerException("in == null");
  }
  
  private void a(int paramInt) {
    int i = this.o;
    int[] arrayOfInt = this.n;
    if (i == arrayOfInt.length) {
      int[] arrayOfInt1 = new int[i * 2];
      System.arraycopy(arrayOfInt, 0, arrayOfInt1, 0, i);
      this.n = arrayOfInt1;
    } 
    arrayOfInt = this.n;
    i = this.o;
    this.o = i + 1;
    arrayOfInt[i] = paramInt;
  }
  
  private boolean a(char paramChar) throws IOException {
    if (paramChar != '\t' && paramChar != '\n' && paramChar != '\f' && paramChar != '\r' && paramChar != ' ')
      if (paramChar != '#') {
        if (paramChar != ',')
          if (paramChar != '/' && paramChar != '=') {
            if (paramChar != '{' && paramChar != '}' && paramChar != ':')
              if (paramChar != ';') {
                switch (paramChar) {
                  default:
                    return true;
                  case '\\':
                    w();
                    break;
                  case '[':
                  case ']':
                    break;
                } 
                return false;
              }  
            return false;
          }  
        return false;
      }  
    return false;
  }
  
  private boolean a(String paramString) throws IOException {
    label19: while (true) {
      int i = this.e;
      int j = paramString.length();
      int k = this.f;
      byte b = 0;
      if (i + j <= k || b(paramString.length())) {
        char[] arrayOfChar = this.d;
        k = this.e;
        if (arrayOfChar[k] == '\n') {
          this.g++;
          this.h = k + 1;
          continue;
        } 
        while (b < paramString.length()) {
          if (this.d[this.e + b] != paramString.charAt(b)) {
            this.e++;
            continue label19;
          } 
          b++;
        } 
        return true;
      } 
      return false;
    } 
  }
  
  private int b(boolean paramBoolean) throws IOException {
    char[] arrayOfChar = this.d;
    int i = this.e;
    int j = this.f;
    while (true) {
      StringBuilder stringBuilder1;
      StringBuilder stringBuilder3;
      int k = i;
      int m = j;
      if (i == j) {
        this.e = i;
        if (!b(1)) {
          if (!paramBoolean)
            return -1; 
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append("End of input at line ");
          stringBuilder1.append(u());
          stringBuilder1.append(" column ");
          stringBuilder1.append(v());
          throw new EOFException(stringBuilder1.toString());
        } 
        k = this.e;
        m = this.f;
      } 
      i = k + 1;
      StringBuilder stringBuilder2 = stringBuilder1[k];
      if (stringBuilder2 == 10) {
        this.g++;
        this.h = i;
      } else if (stringBuilder2 != 32 && stringBuilder2 != 13 && stringBuilder2 != 9) {
        int n;
        if (stringBuilder2 == 47) {
          this.e = i;
          if (i == m) {
            this.e = i - 1;
            boolean bool = b(2);
            this.e++;
            if (!bool)
              return stringBuilder2; 
          } 
          w();
          i = this.e;
          stringBuilder3 = stringBuilder1[i];
          if (stringBuilder3 != 42) {
            if (stringBuilder3 != 47)
              return stringBuilder2; 
            this.e = i + 1;
            x();
            i = this.e;
            n = this.f;
            continue;
          } 
          this.e = i + 1;
          if (a("*/")) {
            i = this.e + 2;
            n = this.f;
            continue;
          } 
          throw b("Unterminated comment");
        } 
        if (n == 35) {
          this.e = i;
          w();
          x();
          i = this.e;
          n = this.f;
          continue;
        } 
        this.e = i;
        return n;
      } 
      stringBuilder2 = stringBuilder3;
    } 
  }
  
  private IOException b(String paramString) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append(" at line ");
    stringBuilder.append(u());
    stringBuilder.append(" column ");
    stringBuilder.append(v());
    throw new d(stringBuilder.toString());
  }
  
  private String b(char paramChar) throws IOException {
    char[] arrayOfChar = this.d;
    StringBuilder stringBuilder = null;
    int i = 0;
    while (true) {
      StringBuilder stringBuilder1;
      int j = this.e;
      int k = this.f;
      label33: while (true) {
        int m = j;
        while (true) {
          int n = m;
          if (n < k) {
            m = n + 1;
            n = arrayOfChar[n];
            if (n == paramChar) {
              this.e = m;
              if (stringBuilder == null)
                return m.a(arrayOfChar, j, m - j - 1, i); 
              stringBuilder.append(arrayOfChar, j, m - j - 1);
              return stringBuilder.toString();
            } 
            if (n == 92) {
              this.e = m;
              StringBuilder stringBuilder2 = stringBuilder;
              if (stringBuilder == null)
                stringBuilder2 = new StringBuilder(); 
              stringBuilder2.append(arrayOfChar, j, m - j - 1);
              stringBuilder2.append(y());
              j = this.e;
              k = this.f;
              stringBuilder = stringBuilder2;
              continue label33;
            } 
            if (n == 10) {
              i = i * 31 + n;
              this.g++;
              this.h = m;
              continue;
            } 
            i = i * 31 + n;
            continue;
          } 
          stringBuilder1 = stringBuilder;
          if (stringBuilder == null)
            stringBuilder1 = new StringBuilder(); 
          stringBuilder1.append(arrayOfChar, j, n - j);
          this.e = n;
          if (b(1))
            break; 
          throw b("Unterminated string");
        } 
        break;
      } 
      stringBuilder = stringBuilder1;
    } 
  }
  
  private boolean b(int paramInt) throws IOException {
    char[] arrayOfChar = this.d;
    int i = this.h;
    int j = this.e;
    this.h = i - j;
    i = this.f;
    if (i != j) {
      i -= j;
      this.f = i;
      System.arraycopy(arrayOfChar, j, arrayOfChar, 0, i);
    } else {
      this.f = 0;
    } 
    this.e = 0;
    while (true) {
      Reader reader = this.b;
      j = this.f;
      j = reader.read(arrayOfChar, j, arrayOfChar.length - j);
      if (j != -1) {
        int k = this.f + j;
        this.f = k;
        j = paramInt;
        if (this.g == 0) {
          i = this.h;
          j = paramInt;
          if (i == 0) {
            j = paramInt;
            if (k > 0) {
              j = paramInt;
              if (arrayOfChar[0] == '﻿') {
                this.e++;
                this.h = i + 1;
                j = paramInt + 1;
              } 
            } 
          } 
        } 
        paramInt = j;
        if (this.f >= j)
          return true; 
        continue;
      } 
      return false;
    } 
  }
  
  private void c(char paramChar) throws IOException {
    char[] arrayOfChar = this.d;
    while (true) {
      int i = this.e;
      int j = this.f;
      while (i < j) {
        int k = i + 1;
        i = arrayOfChar[i];
        if (i == paramChar) {
          this.e = k;
          return;
        } 
        if (i == 92) {
          this.e = k;
          y();
          i = this.e;
          j = this.f;
          continue;
        } 
        if (i == 10) {
          this.g++;
          this.h = k;
        } 
        i = k;
      } 
      this.e = i;
      if (b(1))
        continue; 
      throw b("Unterminated string");
    } 
  }
  
  private int o() throws IOException {
    int[] arrayOfInt = this.n;
    int i = this.o;
    int j = arrayOfInt[i - 1];
    if (j == 1) {
      arrayOfInt[i - 1] = 2;
    } else if (j == 2) {
      i = b(true);
      if (i != 44) {
        if (i != 59) {
          if (i == 93) {
            this.i = 4;
            return 4;
          } 
          throw b("Unterminated array");
        } 
        w();
      } 
    } else {
      if (j == 3 || j == 5) {
        this.n[this.o - 1] = 4;
        if (j == 5) {
          i = b(true);
          if (i != 44) {
            if (i != 59) {
              if (i == 125) {
                this.i = 2;
                return 2;
              } 
              throw b("Unterminated object");
            } 
            w();
          } 
        } 
        i = b(true);
        if (i != 34) {
          if (i != 39) {
            if (i != 125) {
              w();
              this.e--;
              if (a((char)i)) {
                this.i = 14;
                return 14;
              } 
              throw b("Expected name");
            } 
            if (j != 5) {
              this.i = 2;
              return 2;
            } 
            throw b("Expected name");
          } 
          w();
          this.i = 12;
          return 12;
        } 
        this.i = 13;
        return 13;
      } 
      if (j == 4) {
        arrayOfInt[i - 1] = 5;
        i = b(true);
        if (i != 58)
          if (i == 61) {
            w();
            if (this.e < this.f || b(1)) {
              char[] arrayOfChar = this.d;
              i = this.e;
              if (arrayOfChar[i] == '>')
                this.e = i + 1; 
            } 
          } else {
            throw b("Expected ':'");
          }  
      } else if (j == 6) {
        if (this.c)
          z(); 
        this.n[this.o - 1] = 7;
      } else if (j == 7) {
        if (b(false) == -1) {
          this.i = 17;
          return 17;
        } 
        w();
        this.e--;
      } else if (j == 8) {
        throw new IllegalStateException("JsonReader is closed");
      } 
    } 
    i = b(true);
    if (i != 34) {
      if (i != 39) {
        if (i != 44 && i != 59)
          if (i != 91) {
            if (i != 93) {
              if (i != 123) {
                this.e--;
                if (this.o == 1)
                  w(); 
                j = q();
                if (j != 0)
                  return j; 
                j = r();
                if (j != 0)
                  return j; 
                if (a(this.d[this.e])) {
                  w();
                  this.i = 10;
                  return 10;
                } 
                throw b("Expected value");
              } 
              this.i = 1;
              return 1;
            } 
            if (j == 1) {
              this.i = 4;
              return 4;
            } 
          } else {
            this.i = 3;
            return 3;
          }  
        if (j == 1 || j == 2) {
          w();
          this.e--;
          this.i = 7;
          return 7;
        } 
        throw b("Unexpected value");
      } 
      w();
      this.i = 8;
      return 8;
    } 
    if (this.o == 1)
      w(); 
    this.i = 9;
    return 9;
  }
  
  private int q() throws IOException {
    String str1;
    String str2;
    char c = this.d[this.e];
    if (c == 't' || c == 'T') {
      c = '\005';
      str1 = "true";
      str2 = "TRUE";
    } else if (c == 'f' || c == 'F') {
      c = '\006';
      str1 = "false";
      str2 = "FALSE";
    } else if (c == 'n' || c == 'N') {
      c = '\007';
      str1 = "null";
      str2 = "NULL";
    } else {
      return 0;
    } 
    int i = str1.length();
    for (byte b = 1; b < i; b++) {
      if (this.e + b >= this.f && !b(b + 1))
        return 0; 
      char c1 = this.d[this.e + b];
      if (c1 != str1.charAt(b) && c1 != str2.charAt(b))
        return 0; 
    } 
    if ((this.e + i < this.f || b(i + 1)) && a(this.d[this.e + i]))
      return 0; 
    this.e += i;
    this.i = c;
    return c;
  }
  
  private int r() throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: getfield d : [C
    //   4: astore_1
    //   5: aload_0
    //   6: getfield e : I
    //   9: istore_2
    //   10: aload_0
    //   11: getfield f : I
    //   14: istore_3
    //   15: iconst_0
    //   16: istore #4
    //   18: iconst_0
    //   19: istore #5
    //   21: iconst_1
    //   22: istore #6
    //   24: lconst_0
    //   25: lstore #7
    //   27: iconst_0
    //   28: istore #9
    //   30: iload_2
    //   31: istore #10
    //   33: iload_3
    //   34: istore #11
    //   36: iload_2
    //   37: iload #4
    //   39: iadd
    //   40: iload_3
    //   41: if_icmpne -> 79
    //   44: iload #4
    //   46: aload_1
    //   47: arraylength
    //   48: if_icmpne -> 53
    //   51: iconst_0
    //   52: ireturn
    //   53: aload_0
    //   54: iload #4
    //   56: iconst_1
    //   57: iadd
    //   58: invokespecial b : (I)Z
    //   61: ifne -> 67
    //   64: goto -> 301
    //   67: aload_0
    //   68: getfield e : I
    //   71: istore #10
    //   73: aload_0
    //   74: getfield f : I
    //   77: istore #11
    //   79: aload_1
    //   80: iload #10
    //   82: iload #4
    //   84: iadd
    //   85: caload
    //   86: istore #12
    //   88: iload #12
    //   90: bipush #43
    //   92: if_icmpeq -> 468
    //   95: iload #12
    //   97: bipush #69
    //   99: if_icmpeq -> 445
    //   102: iload #12
    //   104: bipush #101
    //   106: if_icmpeq -> 445
    //   109: iload #12
    //   111: bipush #45
    //   113: if_icmpeq -> 420
    //   116: iload #12
    //   118: bipush #46
    //   120: if_icmpeq -> 406
    //   123: iload #12
    //   125: bipush #48
    //   127: if_icmplt -> 292
    //   130: iload #12
    //   132: bipush #57
    //   134: if_icmple -> 140
    //   137: goto -> 292
    //   140: iload #5
    //   142: iconst_1
    //   143: if_icmpeq -> 277
    //   146: iload #5
    //   148: ifne -> 154
    //   151: goto -> 277
    //   154: iload #5
    //   156: iconst_2
    //   157: if_icmpne -> 225
    //   160: lload #7
    //   162: lconst_0
    //   163: lcmp
    //   164: ifne -> 169
    //   167: iconst_0
    //   168: ireturn
    //   169: ldc2_w 10
    //   172: lload #7
    //   174: lmul
    //   175: iload #12
    //   177: bipush #48
    //   179: isub
    //   180: i2l
    //   181: lsub
    //   182: lstore #13
    //   184: lload #7
    //   186: ldc2_w -922337203685477580
    //   189: lcmp
    //   190: istore_3
    //   191: iload_3
    //   192: ifgt -> 215
    //   195: iload_3
    //   196: ifne -> 210
    //   199: lload #13
    //   201: lload #7
    //   203: lcmp
    //   204: ifge -> 210
    //   207: goto -> 215
    //   210: iconst_0
    //   211: istore_3
    //   212: goto -> 217
    //   215: iconst_1
    //   216: istore_3
    //   217: iload #6
    //   219: iload_3
    //   220: iand
    //   221: istore_3
    //   222: goto -> 260
    //   225: iload #5
    //   227: iconst_3
    //   228: if_icmpne -> 237
    //   231: iconst_4
    //   232: istore #5
    //   234: goto -> 478
    //   237: iload #5
    //   239: iconst_5
    //   240: if_icmpeq -> 270
    //   243: iload #6
    //   245: istore_3
    //   246: lload #7
    //   248: lstore #13
    //   250: iload #5
    //   252: bipush #6
    //   254: if_icmpne -> 260
    //   257: goto -> 270
    //   260: iload_3
    //   261: istore #6
    //   263: lload #13
    //   265: lstore #7
    //   267: goto -> 478
    //   270: bipush #7
    //   272: istore #5
    //   274: goto -> 478
    //   277: iload #12
    //   279: bipush #48
    //   281: isub
    //   282: ineg
    //   283: i2l
    //   284: lstore #7
    //   286: iconst_2
    //   287: istore #5
    //   289: goto -> 478
    //   292: aload_0
    //   293: iload #12
    //   295: invokespecial a : (C)Z
    //   298: ifne -> 404
    //   301: iload #5
    //   303: iconst_2
    //   304: if_icmpne -> 365
    //   307: iload #6
    //   309: ifeq -> 365
    //   312: lload #7
    //   314: ldc2_w -9223372036854775808
    //   317: lcmp
    //   318: ifne -> 326
    //   321: iload #9
    //   323: ifeq -> 365
    //   326: iload #9
    //   328: ifeq -> 334
    //   331: goto -> 339
    //   334: lload #7
    //   336: lneg
    //   337: lstore #7
    //   339: aload_0
    //   340: lload #7
    //   342: putfield j : J
    //   345: aload_0
    //   346: aload_0
    //   347: getfield e : I
    //   350: iload #4
    //   352: iadd
    //   353: putfield e : I
    //   356: aload_0
    //   357: bipush #15
    //   359: putfield i : I
    //   362: bipush #15
    //   364: ireturn
    //   365: iload #5
    //   367: iconst_2
    //   368: if_icmpeq -> 389
    //   371: iload #5
    //   373: iconst_4
    //   374: if_icmpeq -> 389
    //   377: iload #5
    //   379: bipush #7
    //   381: if_icmpne -> 387
    //   384: goto -> 389
    //   387: iconst_0
    //   388: ireturn
    //   389: aload_0
    //   390: iload #4
    //   392: putfield k : I
    //   395: aload_0
    //   396: bipush #16
    //   398: putfield i : I
    //   401: bipush #16
    //   403: ireturn
    //   404: iconst_0
    //   405: ireturn
    //   406: iload #5
    //   408: iconst_2
    //   409: if_icmpne -> 418
    //   412: iconst_3
    //   413: istore #5
    //   415: goto -> 478
    //   418: iconst_0
    //   419: ireturn
    //   420: iload #5
    //   422: ifne -> 434
    //   425: iconst_1
    //   426: istore #5
    //   428: iconst_1
    //   429: istore #9
    //   431: goto -> 478
    //   434: iload #5
    //   436: iconst_5
    //   437: if_icmpne -> 443
    //   440: goto -> 474
    //   443: iconst_0
    //   444: ireturn
    //   445: iload #5
    //   447: iconst_2
    //   448: if_icmpeq -> 462
    //   451: iload #5
    //   453: iconst_4
    //   454: if_icmpne -> 460
    //   457: goto -> 462
    //   460: iconst_0
    //   461: ireturn
    //   462: iconst_5
    //   463: istore #5
    //   465: goto -> 478
    //   468: iload #5
    //   470: iconst_5
    //   471: if_icmpne -> 490
    //   474: bipush #6
    //   476: istore #5
    //   478: iinc #4, 1
    //   481: iload #10
    //   483: istore_2
    //   484: iload #11
    //   486: istore_3
    //   487: goto -> 30
    //   490: iconst_0
    //   491: ireturn
  }
  
  private String s() throws IOException {
    String str;
    byte b;
    boolean bool = false;
    StringBuilder stringBuilder = null;
    while (true) {
      b = 0;
      while (true) {
        int i = this.e;
        if (i + b < this.f) {
          i = this.d[i + b];
          if (i != 9 && i != 10 && i != 12 && i != 13 && i != 32)
            if (i != 35) {
              if (i != 44)
                if (i != 47 && i != 61) {
                  if (i != 123 && i != 125 && i != 58)
                    if (i != 59) {
                      switch (i) {
                        case 92:
                          w();
                          break;
                        case 91:
                        case 93:
                          break;
                      } 
                      continue;
                    }  
                  break;
                }  
              break;
            }  
          break;
        } 
        if (b < this.d.length) {
          if (b(b + 1))
            continue; 
          break;
        } 
        StringBuilder stringBuilder1 = stringBuilder;
        if (stringBuilder == null)
          stringBuilder1 = new StringBuilder(); 
        stringBuilder1.append(this.d, this.e, b);
        this.e += b;
        stringBuilder = stringBuilder1;
        if (!b(1)) {
          stringBuilder = stringBuilder1;
          b = bool;
          break;
        } 
      } 
      break;
    } 
    if (stringBuilder == null) {
      str = new String(this.d, this.e, b);
    } else {
      str.append(this.d, this.e, b);
      str = str.toString();
    } 
    this.e += b;
    return str;
  }
  
  private void t() throws IOException {
    do {
      byte b = 0;
      while (true) {
        int i = this.e;
        if (i + b < this.f) {
          i = this.d[i + b];
          if (i != 9 && i != 10 && i != 12 && i != 13 && i != 32)
            if (i != 35) {
              if (i != 44)
                if (i != 47 && i != 61) {
                  if (i != 123 && i != 125 && i != 58)
                    if (i != 59) {
                      switch (i) {
                        default:
                          b++;
                          continue;
                        case 92:
                          w();
                          break;
                        case 91:
                        case 93:
                          break;
                      } 
                    } else {
                    
                    }  
                } else {
                
                }  
            } else {
            
            }  
          this.e += b;
          return;
        } 
        this.e = i + b;
        break;
      } 
    } while (b(1));
  }
  
  private int u() {
    return this.g + 1;
  }
  
  private int v() {
    return this.e - this.h + 1;
  }
  
  private void w() throws IOException {
    if (this.c)
      return; 
    throw b("Use JsonReader.setLenient(true) to accept malformed JSON");
  }
  
  private void x() throws IOException {
    while (this.e < this.f || b(1)) {
      char[] arrayOfChar = this.d;
      int i = this.e;
      int j = i + 1;
      this.e = j;
      i = arrayOfChar[i];
      if (i == 10) {
        this.g++;
        this.h = j;
        break;
      } 
      if (i == 13)
        break; 
    } 
  }
  
  private char y() throws IOException {
    if (this.e != this.f || b(1)) {
      int j;
      char c;
      char[] arrayOfChar = this.d;
      int i = this.e;
      int k = i + 1;
      this.e = k;
      int m = arrayOfChar[i];
      if (m != 10) {
        if (m != 98) {
          if (m != 102) {
            if (m != 110) {
              if (m != 114) {
                if (m != 116) {
                  if (m == 117) {
                    if (k + 4 <= this.f || b(4)) {
                      int n = 0;
                      i = this.e;
                      k = i;
                      m = n;
                      while (true) {
                        n = k;
                        if (n < i + 4) {
                          char c1 = this.d[n];
                          char c2 = (char)(m << 4);
                          if (c1 >= '0' && c1 <= '9') {
                            c1 -= '0';
                          } else {
                            if (c1 >= 'a' && c1 <= 'f') {
                              c1 -= 'a';
                            } else if (c1 >= 'A') {
                              if (c1 <= 'F') {
                                c1 -= 'A';
                              } else {
                                continue;
                              } 
                            } else {
                              StringBuilder stringBuilder = new StringBuilder();
                              stringBuilder.append("\\u");
                              stringBuilder.append(new String(this.d, this.e, 4));
                              throw new NumberFormatException(stringBuilder.toString());
                            } 
                            c1 += '\n';
                          } 
                          c2 = (char)(c2 + c1);
                          j = n + 1;
                          c = c2;
                          continue;
                        } 
                        this.e += 4;
                        return c;
                      } 
                    } 
                    throw b("Unterminated escape sequence");
                  } 
                } else {
                  return '\t';
                } 
              } else {
                return '\r';
              } 
            } else {
              return '\n';
            } 
          } else {
            return '\f';
          } 
        } else {
          return '\b';
        } 
      } else {
        this.g++;
        this.h = j;
      } 
      return c;
    } 
    throw b("Unterminated escape sequence");
  }
  
  private void z() throws IOException {
    b(true);
    int i = this.e - 1;
    this.e = i;
    char[] arrayOfChar = a;
    if (i + arrayOfChar.length > this.f && !b(arrayOfChar.length))
      return; 
    i = 0;
    while (true) {
      arrayOfChar = a;
      if (i < arrayOfChar.length) {
        if (this.d[this.e + i] != arrayOfChar[i])
          return; 
        i++;
        continue;
      } 
      this.e += arrayOfChar.length;
      return;
    } 
  }
  
  public void a() throws IOException {
    int i = this.i;
    int j = i;
    if (i == 0)
      j = o(); 
    if (j == 3) {
      a(1);
      this.i = 0;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected BEGIN_ARRAY but was ");
    stringBuilder.append(f());
    stringBuilder.append(" at line ");
    stringBuilder.append(u());
    stringBuilder.append(" column ");
    stringBuilder.append(v());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public final void a(boolean paramBoolean) {
    this.c = paramBoolean;
  }
  
  public void b() throws IOException {
    int i = this.i;
    int j = i;
    if (i == 0)
      j = o(); 
    if (j == 4) {
      this.o--;
      this.i = 0;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected END_ARRAY but was ");
    stringBuilder.append(f());
    stringBuilder.append(" at line ");
    stringBuilder.append(u());
    stringBuilder.append(" column ");
    stringBuilder.append(v());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void c() throws IOException {
    int i = this.i;
    int j = i;
    if (i == 0)
      j = o(); 
    if (j == 1) {
      a(3);
      this.i = 0;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected BEGIN_OBJECT but was ");
    stringBuilder.append(f());
    stringBuilder.append(" at line ");
    stringBuilder.append(u());
    stringBuilder.append(" column ");
    stringBuilder.append(v());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void close() throws IOException {
    this.i = 0;
    this.n[0] = 8;
    this.o = 1;
    this.b.close();
  }
  
  public void d() throws IOException {
    int i = this.i;
    int j = i;
    if (i == 0)
      j = o(); 
    if (j == 2) {
      this.o--;
      this.i = 0;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected END_OBJECT but was ");
    stringBuilder.append(f());
    stringBuilder.append(" at line ");
    stringBuilder.append(u());
    stringBuilder.append(" column ");
    stringBuilder.append(v());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public boolean e() throws IOException {
    boolean bool;
    int i = this.i;
    int j = i;
    if (i == 0)
      j = o(); 
    if (j != 2 && j != 4) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public b f() throws IOException {
    int i = this.i;
    int j = i;
    if (i == 0)
      j = o(); 
    switch (j) {
      default:
        throw new AssertionError();
      case 17:
        return b.j;
      case 15:
      case 16:
        return b.g;
      case 12:
      case 13:
      case 14:
        return b.e;
      case 8:
      case 9:
      case 10:
      case 11:
        return b.f;
      case 7:
        return b.i;
      case 5:
      case 6:
        return b.h;
      case 4:
        return b.b;
      case 3:
        return b.a;
      case 2:
        return b.d;
      case 1:
        break;
    } 
    return b.c;
  }
  
  public String g() throws IOException {
    StringBuilder stringBuilder;
    int i = this.i;
    int j = i;
    if (i == 0)
      j = o(); 
    if (j == 14) {
      String str = s();
    } else if (j == 12) {
      String str = b('\'');
    } else {
      if (j == 13) {
        String str = b('"');
        this.i = 0;
        return str;
      } 
      stringBuilder = new StringBuilder();
      stringBuilder.append("Expected a name but was ");
      stringBuilder.append(f());
      stringBuilder.append(" at line ");
      stringBuilder.append(u());
      stringBuilder.append(" column ");
      stringBuilder.append(v());
      throw new IllegalStateException(stringBuilder.toString());
    } 
    this.i = 0;
    return (String)stringBuilder;
  }
  
  public String h() throws IOException {
    StringBuilder stringBuilder;
    int i = this.i;
    int j = i;
    if (i == 0)
      j = o(); 
    if (j == 10) {
      String str = s();
    } else if (j == 8) {
      String str = b('\'');
    } else if (j == 9) {
      String str = b('"');
    } else if (j == 11) {
      String str = this.l;
      this.l = null;
    } else if (j == 15) {
      String str = Long.toString(this.j);
    } else {
      if (j == 16) {
        String str = new String(this.d, this.e, this.k);
        this.e += this.k;
        this.i = 0;
        return str;
      } 
      stringBuilder = new StringBuilder();
      stringBuilder.append("Expected a string but was ");
      stringBuilder.append(f());
      stringBuilder.append(" at line ");
      stringBuilder.append(u());
      stringBuilder.append(" column ");
      stringBuilder.append(v());
      throw new IllegalStateException(stringBuilder.toString());
    } 
    this.i = 0;
    return (String)stringBuilder;
  }
  
  public boolean i() throws IOException {
    int i = this.i;
    int j = i;
    if (i == 0)
      j = o(); 
    if (j == 5) {
      this.i = 0;
      return true;
    } 
    if (j == 6) {
      this.i = 0;
      return false;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected a boolean but was ");
    stringBuilder.append(f());
    stringBuilder.append(" at line ");
    stringBuilder.append(u());
    stringBuilder.append(" column ");
    stringBuilder.append(v());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void j() throws IOException {
    int i = this.i;
    int j = i;
    if (i == 0)
      j = o(); 
    if (j == 7) {
      this.i = 0;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected null but was ");
    stringBuilder.append(f());
    stringBuilder.append(" at line ");
    stringBuilder.append(u());
    stringBuilder.append(" column ");
    stringBuilder.append(v());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public double k() throws IOException {
    int i = this.i;
    int j = i;
    if (i == 0)
      j = o(); 
    if (j == 15) {
      this.i = 0;
      return this.j;
    } 
    if (j == 16) {
      this.l = new String(this.d, this.e, this.k);
      this.e += this.k;
    } else if (j == 8 || j == 9) {
      int k;
      if (j == 8) {
        j = 39;
        k = j;
      } else {
        j = 34;
        k = j;
      } 
      this.l = b(k);
    } else if (j == 10) {
      this.l = s();
    } else if (j != 11) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Expected a double but was ");
      stringBuilder1.append(f());
      stringBuilder1.append(" at line ");
      stringBuilder1.append(u());
      stringBuilder1.append(" column ");
      stringBuilder1.append(v());
      throw new IllegalStateException(stringBuilder1.toString());
    } 
    this.i = 11;
    double d = Double.parseDouble(this.l);
    if (this.c || (!Double.isNaN(d) && !Double.isInfinite(d))) {
      this.l = null;
      this.i = 0;
      return d;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("JSON forbids NaN and infinities: ");
    stringBuilder.append(d);
    stringBuilder.append(" at line ");
    stringBuilder.append(u());
    stringBuilder.append(" column ");
    stringBuilder.append(v());
    throw new d(stringBuilder.toString());
  }
  
  public long l() throws IOException {
    int i = this.i;
    int j = i;
    if (i == 0)
      j = o(); 
    if (j == 15) {
      this.i = 0;
      return this.j;
    } 
    if (j == 16) {
      this.l = new String(this.d, this.e, this.k);
      this.e += this.k;
    } else if (j == 8 || j == 9) {
      int k;
      if (j == 8) {
        j = 39;
        k = j;
      } else {
        j = 34;
        k = j;
      } 
      String str = b(k);
      this.l = str;
      try {
        long l1 = Long.parseLong(str);
        this.i = 0;
        return l1;
      } catch (NumberFormatException numberFormatException) {}
    } else {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Expected a long but was ");
      stringBuilder1.append(f());
      stringBuilder1.append(" at line ");
      stringBuilder1.append(u());
      stringBuilder1.append(" column ");
      stringBuilder1.append(v());
      throw new IllegalStateException(stringBuilder1.toString());
    } 
    this.i = 11;
    double d = Double.parseDouble(this.l);
    long l = (long)d;
    if (l == d) {
      this.l = null;
      this.i = 0;
      return l;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected a long but was ");
    stringBuilder.append(this.l);
    stringBuilder.append(" at line ");
    stringBuilder.append(u());
    stringBuilder.append(" column ");
    stringBuilder.append(v());
    throw new NumberFormatException(stringBuilder.toString());
  }
  
  public int m() throws IOException {
    int i = this.i;
    int j = i;
    if (i == 0)
      j = o(); 
    if (j == 15) {
      long l = this.j;
      j = (int)l;
      if (l == j) {
        this.i = 0;
        return j;
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Expected an int but was ");
      stringBuilder1.append(this.j);
      stringBuilder1.append(" at line ");
      stringBuilder1.append(u());
      stringBuilder1.append(" column ");
      stringBuilder1.append(v());
      throw new NumberFormatException(stringBuilder1.toString());
    } 
    if (j == 16) {
      this.l = new String(this.d, this.e, this.k);
      this.e += this.k;
    } else if (j == 8 || j == 9) {
      int k;
      if (j == 8) {
        j = 39;
        k = j;
      } else {
        j = 34;
        k = j;
      } 
      String str = b(k);
      this.l = str;
      try {
        j = Integer.parseInt(str);
        this.i = 0;
        return j;
      } catch (NumberFormatException numberFormatException) {}
    } else {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Expected an int but was ");
      stringBuilder1.append(f());
      stringBuilder1.append(" at line ");
      stringBuilder1.append(u());
      stringBuilder1.append(" column ");
      stringBuilder1.append(v());
      throw new IllegalStateException(stringBuilder1.toString());
    } 
    this.i = 11;
    double d = Double.parseDouble(this.l);
    j = (int)d;
    if (j == d) {
      this.l = null;
      this.i = 0;
      return j;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected an int but was ");
    stringBuilder.append(this.l);
    stringBuilder.append(" at line ");
    stringBuilder.append(u());
    stringBuilder.append(" column ");
    stringBuilder.append(v());
    throw new NumberFormatException(stringBuilder.toString());
  }
  
  public void n() throws IOException {
    for (int i = 0;; i = j) {
      int j = this.i;
      int k = j;
      if (j == 0)
        k = o(); 
      if (k == 3) {
        a(1);
      } else if (k == 1) {
        a(3);
      } else {
        if (k == 4) {
          this.o--;
        } else if (k == 2) {
          this.o--;
        } else {
          if (k == 14 || k == 10) {
            t();
            j = i;
          } else if (k == 8 || k == 12) {
            c('\'');
            j = i;
          } else if (k == 9 || k == 13) {
            c('"');
            j = i;
          } else {
            j = i;
            if (k == 16) {
              this.e += this.k;
              j = i;
            } 
          } 
          this.i = 0;
          i = j;
        } 
        j = i - 1;
        this.i = 0;
        i = j;
      } 
      j = i + 1;
      this.i = 0;
    } 
  }
  
  public final boolean p() {
    return this.c;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getClass().getSimpleName());
    stringBuilder.append(" at line ");
    stringBuilder.append(u());
    stringBuilder.append(" column ");
    stringBuilder.append(v());
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\d\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */