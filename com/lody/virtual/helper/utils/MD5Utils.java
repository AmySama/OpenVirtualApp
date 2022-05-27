package com.lody.virtual.helper.utils;

import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
  protected static char[] HEX_DIGITS = new char[] { 
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
      'a', 'b', 'c', 'd', 'e', 'f' };
  
  protected static MessageDigest MESSAGE_DIGEST_5 = null;
  
  static {
    try {
      MESSAGE_DIGEST_5 = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
      noSuchAlgorithmException.printStackTrace();
    } 
  }
  
  private static void appendHexPair(byte paramByte, StringBuffer paramStringBuffer) {
    char[] arrayOfChar = HEX_DIGITS;
    char c1 = arrayOfChar[(paramByte & 0xF0) >> 4];
    char c2 = arrayOfChar[paramByte & 0xF];
    paramStringBuffer.append(c1);
    paramStringBuffer.append(c2);
  }
  
  private static String bufferToHex(byte[] paramArrayOfbyte) {
    return bufferToHex(paramArrayOfbyte, 0, paramArrayOfbyte.length);
  }
  
  private static String bufferToHex(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    StringBuffer stringBuffer = new StringBuffer(paramInt2 * 2);
    for (int i = paramInt1; i < paramInt2 + paramInt1; i++)
      appendHexPair(paramArrayOfbyte[i], stringBuffer); 
    return stringBuffer.toString();
  }
  
  public static boolean compareFiles(File paramFile1, File paramFile2) throws IOException {
    return paramFile1.getAbsolutePath().equals(paramFile2.getAbsolutePath()) ? true : TextUtils.equals(getFileMD5String(paramFile1), getFileMD5String(paramFile2));
  }
  
  public static String getFileMD5String(File paramFile) throws IOException {
    FileInputStream fileInputStream = new FileInputStream(paramFile);
    byte[] arrayOfByte = new byte[1024];
    while (true) {
      int i = fileInputStream.read(arrayOfByte);
      if (i > 0) {
        MESSAGE_DIGEST_5.update(arrayOfByte, 0, i);
        continue;
      } 
      fileInputStream.close();
      return bufferToHex(MESSAGE_DIGEST_5.digest());
    } 
  }
  
  public static String getFileMD5String(InputStream paramInputStream) throws IOException {
    byte[] arrayOfByte = new byte[1024];
    while (true) {
      int i = paramInputStream.read(arrayOfByte);
      if (i > 0) {
        MESSAGE_DIGEST_5.update(arrayOfByte, 0, i);
        continue;
      } 
      paramInputStream.close();
      return bufferToHex(MESSAGE_DIGEST_5.digest());
    } 
  }
  
  public static String hashBase64(byte[] paramArrayOfbyte) {
    // Byte code:
    //   0: new java/io/ByteArrayInputStream
    //   3: dup
    //   4: aload_0
    //   5: invokespecial <init> : ([B)V
    //   8: astore_0
    //   9: ldc 'SHA-1'
    //   11: invokestatic getInstance : (Ljava/lang/String;)Ljava/security/MessageDigest;
    //   14: astore_1
    //   15: sipush #1024
    //   18: newarray byte
    //   20: astore_2
    //   21: aload_0
    //   22: aload_2
    //   23: invokevirtual read : ([B)I
    //   26: istore_3
    //   27: iload_3
    //   28: ifle -> 41
    //   31: aload_1
    //   32: aload_2
    //   33: iconst_0
    //   34: iload_3
    //   35: invokevirtual update : ([BII)V
    //   38: goto -> 21
    //   41: aload_0
    //   42: invokevirtual close : ()V
    //   45: aload_1
    //   46: invokevirtual digest : ()[B
    //   49: iconst_0
    //   50: invokestatic encodeToString : ([BI)Ljava/lang/String;
    //   53: areturn
    //   54: astore_1
    //   55: aload_0
    //   56: invokevirtual close : ()V
    //   59: aload_1
    //   60: athrow
    //   61: astore_1
    //   62: aload_0
    //   63: invokevirtual close : ()V
    //   66: aconst_null
    //   67: areturn
    //   68: astore_0
    //   69: goto -> 45
    //   72: astore_0
    //   73: goto -> 59
    //   76: astore_0
    //   77: goto -> 66
    // Exception table:
    //   from	to	target	type
    //   9	21	61	java/lang/Exception
    //   9	21	54	finally
    //   21	27	61	java/lang/Exception
    //   21	27	54	finally
    //   31	38	61	java/lang/Exception
    //   31	38	54	finally
    //   41	45	68	java/io/IOException
    //   55	59	72	java/io/IOException
    //   62	66	76	java/io/IOException
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\MD5Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */