package com.lody.virtual.helper.utils;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {
  private static final char[] hexDigit = new char[] { 
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
      'A', 'B', 'C', 'D', 'E', 'F' };
  
  public static boolean load(Properties paramProperties, File paramFile) {
    if (paramProperties == null || paramFile == null || !paramFile.exists())
      return false; 
    Exception exception = null;
    File file = null;
    try {
      FileInputStream fileInputStream = new FileInputStream();
      return true;
    } catch (Exception exception1) {
    
    } finally {
      paramFile = file;
      FileUtils.closeQuietly((Closeable)paramFile);
    } 
    FileUtils.closeQuietly((Closeable)paramProperties);
  }
  
  public static boolean save(Map paramMap, File paramFile, String paramString) {
    if (paramMap != null && paramFile != null) {
      Exception exception = null;
      File file = null;
      try {
        if (paramFile.exists()) {
          paramFile.delete();
        } else {
          File file1 = paramFile.getParentFile();
          if (!file1.exists())
            file1.mkdirs(); 
          paramFile.createNewFile();
        } 
        FileOutputStream fileOutputStream = new FileOutputStream();
      } catch (Exception exception1) {
      
      } finally {
        paramFile = file;
        FileUtils.closeQuietly((Closeable)paramFile);
      } 
      FileUtils.closeQuietly((Closeable)paramMap);
    } 
    return false;
  }
  
  private static String saveConvert(String paramString, boolean paramBoolean1, int paramBoolean2) {
    int i = paramString.length();
    int j = i * 2;
    int k = j;
    if (j < 0)
      k = Integer.MAX_VALUE; 
    StringBuffer stringBuffer = new StringBuffer(k);
    for (k = 0; k < i; k++) {
      char c = paramString.charAt(k);
      if (c > '=' && c < '') {
        if (c == '\\') {
          stringBuffer.append('\\');
          stringBuffer.append('\\');
        } else {
          stringBuffer.append(c);
        } 
      } else if (c != '\t') {
        if (c != '\n') {
          if (c != '\f') {
            if (c != '\r') {
              if (c != ' ') {
                if (c != '!' && c != '#' && c != ':' && c != '=') {
                  if (c < ' ' || c > '~') {
                    j = 1;
                  } else {
                    j = 0;
                  } 
                  if ((j & paramBoolean2) != 0) {
                    stringBuffer.append('\\');
                    stringBuffer.append('u');
                    stringBuffer.append(toHex(c >> 12 & 0xF));
                    stringBuffer.append(toHex(c >> 8 & 0xF));
                    stringBuffer.append(toHex(c >> 4 & 0xF));
                    stringBuffer.append(toHex(c & 0xF));
                  } else {
                    stringBuffer.append(c);
                  } 
                } else {
                  stringBuffer.append('\\');
                  stringBuffer.append(c);
                } 
              } else {
                if (k == 0 || paramBoolean1)
                  stringBuffer.append('\\'); 
                stringBuffer.append(' ');
              } 
            } else {
              stringBuffer.append('\\');
              stringBuffer.append('r');
            } 
          } else {
            stringBuffer.append('\\');
            stringBuffer.append('f');
          } 
        } else {
          stringBuffer.append('\\');
          stringBuffer.append('n');
        } 
      } else {
        stringBuffer.append('\\');
        stringBuffer.append('t');
      } 
    } 
    return stringBuffer.toString();
  }
  
  private static void store(Map paramMap, OutputStream paramOutputStream, String paramString) throws IOException {
    store0(paramMap, new BufferedWriter(new OutputStreamWriter(paramOutputStream, "8859_1")), paramString, true);
  }
  
  private static void store0(Map paramMap, BufferedWriter paramBufferedWriter, String paramString, boolean paramBoolean) throws IOException {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual newLine : ()V
    //   4: aload_2
    //   5: ifnull -> 13
    //   8: aload_1
    //   9: aload_2
    //   10: invokestatic writeComments : (Ljava/io/BufferedWriter;Ljava/lang/String;)V
    //   13: aload_0
    //   14: monitorenter
    //   15: aload_0
    //   16: invokeinterface keySet : ()Ljava/util/Set;
    //   21: invokeinterface iterator : ()Ljava/util/Iterator;
    //   26: astore_2
    //   27: aload_2
    //   28: invokeinterface hasNext : ()Z
    //   33: ifeq -> 132
    //   36: aload_2
    //   37: invokeinterface next : ()Ljava/lang/Object;
    //   42: astore #4
    //   44: aload #4
    //   46: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   49: astore #5
    //   51: aload_0
    //   52: aload #4
    //   54: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   59: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   62: astore #4
    //   64: aload #5
    //   66: iconst_1
    //   67: iload_3
    //   68: invokestatic saveConvert : (Ljava/lang/String;ZZ)Ljava/lang/String;
    //   71: astore #5
    //   73: aload #4
    //   75: iconst_0
    //   76: iload_3
    //   77: invokestatic saveConvert : (Ljava/lang/String;ZZ)Ljava/lang/String;
    //   80: astore #4
    //   82: new java/lang/StringBuilder
    //   85: astore #6
    //   87: aload #6
    //   89: invokespecial <init> : ()V
    //   92: aload #6
    //   94: aload #5
    //   96: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: pop
    //   100: aload #6
    //   102: ldc '='
    //   104: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: pop
    //   108: aload #6
    //   110: aload #4
    //   112: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   115: pop
    //   116: aload_1
    //   117: aload #6
    //   119: invokevirtual toString : ()Ljava/lang/String;
    //   122: invokevirtual write : (Ljava/lang/String;)V
    //   125: aload_1
    //   126: invokevirtual newLine : ()V
    //   129: goto -> 27
    //   132: aload_0
    //   133: monitorexit
    //   134: aload_1
    //   135: invokevirtual flush : ()V
    //   138: return
    //   139: astore_1
    //   140: aload_0
    //   141: monitorexit
    //   142: aload_1
    //   143: athrow
    // Exception table:
    //   from	to	target	type
    //   15	27	139	finally
    //   27	129	139	finally
    //   132	134	139	finally
    //   140	142	139	finally
  }
  
  private static char toHex(int paramInt) {
    return hexDigit[paramInt & 0xF];
  }
  
  private static void writeComments(BufferedWriter paramBufferedWriter, String paramString) throws IOException {
    // Byte code:
    //   0: aload_0
    //   1: ldc '#'
    //   3: invokevirtual write : (Ljava/lang/String;)V
    //   6: aload_1
    //   7: invokevirtual length : ()I
    //   10: istore_2
    //   11: bipush #6
    //   13: newarray char
    //   15: astore_3
    //   16: iconst_0
    //   17: istore #4
    //   19: aload_3
    //   20: iconst_0
    //   21: bipush #92
    //   23: i2c
    //   24: castore
    //   25: aload_3
    //   26: iconst_1
    //   27: bipush #117
    //   29: i2c
    //   30: castore
    //   31: iconst_0
    //   32: istore #5
    //   34: iload #4
    //   36: iload_2
    //   37: if_icmpge -> 301
    //   40: aload_1
    //   41: iload #4
    //   43: invokevirtual charAt : (I)C
    //   46: istore #6
    //   48: iload #6
    //   50: sipush #255
    //   53: if_icmpgt -> 78
    //   56: iload #6
    //   58: bipush #10
    //   60: if_icmpeq -> 78
    //   63: iload #5
    //   65: istore #7
    //   67: iload #4
    //   69: istore #8
    //   71: iload #6
    //   73: bipush #13
    //   75: if_icmpne -> 288
    //   78: iload #5
    //   80: iload #4
    //   82: if_icmpeq -> 97
    //   85: aload_0
    //   86: aload_1
    //   87: iload #5
    //   89: iload #4
    //   91: invokevirtual substring : (II)Ljava/lang/String;
    //   94: invokevirtual write : (Ljava/lang/String;)V
    //   97: iload #6
    //   99: sipush #255
    //   102: if_icmple -> 172
    //   105: aload_3
    //   106: iconst_2
    //   107: iload #6
    //   109: bipush #12
    //   111: ishr
    //   112: bipush #15
    //   114: iand
    //   115: invokestatic toHex : (I)C
    //   118: castore
    //   119: aload_3
    //   120: iconst_3
    //   121: iload #6
    //   123: bipush #8
    //   125: ishr
    //   126: bipush #15
    //   128: iand
    //   129: invokestatic toHex : (I)C
    //   132: castore
    //   133: aload_3
    //   134: iconst_4
    //   135: iload #6
    //   137: iconst_4
    //   138: ishr
    //   139: bipush #15
    //   141: iand
    //   142: invokestatic toHex : (I)C
    //   145: castore
    //   146: aload_3
    //   147: iconst_5
    //   148: iload #6
    //   150: bipush #15
    //   152: iand
    //   153: invokestatic toHex : (I)C
    //   156: castore
    //   157: aload_0
    //   158: new java/lang/String
    //   161: dup
    //   162: aload_3
    //   163: invokespecial <init> : ([C)V
    //   166: invokevirtual write : (Ljava/lang/String;)V
    //   169: goto -> 278
    //   172: aload_0
    //   173: invokevirtual newLine : ()V
    //   176: iload #4
    //   178: istore #5
    //   180: iload #6
    //   182: bipush #13
    //   184: if_icmpne -> 224
    //   187: iload #4
    //   189: istore #5
    //   191: iload #4
    //   193: iload_2
    //   194: iconst_1
    //   195: isub
    //   196: if_icmpeq -> 224
    //   199: iload #4
    //   201: iconst_1
    //   202: iadd
    //   203: istore #7
    //   205: iload #4
    //   207: istore #5
    //   209: aload_1
    //   210: iload #7
    //   212: invokevirtual charAt : (I)C
    //   215: bipush #10
    //   217: if_icmpne -> 224
    //   220: iload #7
    //   222: istore #5
    //   224: iload #5
    //   226: iload_2
    //   227: iconst_1
    //   228: isub
    //   229: if_icmpeq -> 268
    //   232: iload #5
    //   234: iconst_1
    //   235: iadd
    //   236: istore #7
    //   238: iload #5
    //   240: istore #4
    //   242: aload_1
    //   243: iload #7
    //   245: invokevirtual charAt : (I)C
    //   248: bipush #35
    //   250: if_icmpeq -> 278
    //   253: iload #5
    //   255: istore #4
    //   257: aload_1
    //   258: iload #7
    //   260: invokevirtual charAt : (I)C
    //   263: bipush #33
    //   265: if_icmpeq -> 278
    //   268: aload_0
    //   269: ldc '#'
    //   271: invokevirtual write : (Ljava/lang/String;)V
    //   274: iload #5
    //   276: istore #4
    //   278: iload #4
    //   280: iconst_1
    //   281: iadd
    //   282: istore #7
    //   284: iload #4
    //   286: istore #8
    //   288: iload #8
    //   290: iconst_1
    //   291: iadd
    //   292: istore #4
    //   294: iload #7
    //   296: istore #5
    //   298: goto -> 34
    //   301: iload #5
    //   303: iload #4
    //   305: if_icmpeq -> 320
    //   308: aload_0
    //   309: aload_1
    //   310: iload #5
    //   312: iload #4
    //   314: invokevirtual substring : (II)Ljava/lang/String;
    //   317: invokevirtual write : (Ljava/lang/String;)V
    //   320: aload_0
    //   321: invokevirtual newLine : ()V
    //   324: return
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\PropertiesUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */