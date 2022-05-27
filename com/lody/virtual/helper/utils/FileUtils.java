package com.lody.virtual.helper.utils;

import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.system.Os;
import android.text.TextUtils;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class FileUtils {
  public static String buildValidExtFilename(String paramString) {
    if (TextUtils.isEmpty(paramString) || ".".equals(paramString) || "..".equals(paramString))
      return "(invalid)"; 
    StringBuilder stringBuilder = new StringBuilder(paramString.length());
    for (byte b = 0; b < paramString.length(); b++) {
      char c = paramString.charAt(b);
      if (isValidExtFilenameChar(c)) {
        stringBuilder.append(c);
      } else {
        stringBuilder.append('_');
      } 
    } 
    return stringBuilder.toString();
  }
  
  public static boolean canRead(String paramString) {
    return (new File(paramString)).canRead();
  }
  
  public static File changeExt(File paramFile, String paramString) {
    String str1;
    String str2 = paramFile.getAbsolutePath();
    if (!getFilenameExt(str2).equals(paramString)) {
      int i = str2.lastIndexOf(".");
      if (i > 0) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str2.substring(0, i + 1));
        stringBuilder.append(paramString);
        str1 = stringBuilder.toString();
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append(".");
        stringBuilder.append(paramString);
        str1 = stringBuilder.toString();
      } 
      return new File(str1);
    } 
    return (File)str1;
  }
  
  public static void chmod(String paramString, int paramInt) {
    String str1;
    if (Build.VERSION.SDK_INT >= 21)
      try {
        Os.chmod(paramString, paramInt);
        return;
      } catch (Exception exception) {} 
    if ((new File(paramString)).isDirectory()) {
      str1 = "chmod  -R ";
    } else {
      str1 = "chmod ";
    } 
    String str2 = String.format("%o", new Object[] { Integer.valueOf(paramInt) });
    try {
      Runtime runtime = Runtime.getRuntime();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append(str1);
      stringBuilder.append(str2);
      stringBuilder.append(" ");
      stringBuilder.append(paramString);
      runtime.exec(stringBuilder.toString()).waitFor();
    } catch (InterruptedException interruptedException) {
      interruptedException.printStackTrace();
    } catch (IOException iOException) {}
  }
  
  public static void closeQuietly(Closeable paramCloseable) {
    if (paramCloseable != null)
      try {
        paramCloseable.close();
      } catch (Exception exception) {} 
  }
  
  public static void copyFile(File paramFile1, File paramFile2) throws IOException {
    FileOutputStream fileOutputStream2;
    if (paramFile1.getCanonicalPath().equals(paramFile2.getCanonicalPath()))
      return; 
    FileOutputStream fileOutputStream1 = null;
    try {
      FileInputStream fileInputStream = new FileInputStream();
    } finally {
      paramFile1 = null;
      paramFile2 = null;
    } 
    closeQuietly(fileOutputStream2);
    closeQuietly((Closeable)paramFile2);
    throw paramFile1;
  }
  
  public static void copyFileFromAssets(Context paramContext, String paramString, File paramFile) throws IOException {
    File file = null;
    try {
      InputStream inputStream2 = paramContext.getResources().getAssets().open(paramString);
    } finally {
      paramContext = null;
      paramString = null;
    } 
    closeQuietly((Closeable)paramFile);
    closeQuietly((Closeable)paramString);
    throw paramContext;
  }
  
  public static void copyTo(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(paramOutputStream);
    byte[] arrayOfByte = new byte[4096];
    while (true) {
      int i = paramInputStream.read(arrayOfByte, 0, 4096);
      if (i > 0) {
        bufferedOutputStream.write(arrayOfByte, 0, i);
        continue;
      } 
      bufferedOutputStream.flush();
      return;
    } 
  }
  
  public static void createSymlink(String paramString1, String paramString2) throws Exception {
    Os.symlink(paramString1, paramString2);
  }
  
  public static void deleteDir(File paramFile) {
    if (paramFile.isDirectory()) {
      boolean bool;
      byte b = 0;
      try {
        bool = isSymlink(paramFile);
      } catch (Exception exception) {
        bool = false;
      } 
      if (!bool) {
        String[] arrayOfString = paramFile.list();
        if (arrayOfString != null) {
          int i = arrayOfString.length;
          while (b < i) {
            deleteDir(new File(paramFile, arrayOfString[b]));
            b++;
          } 
        } 
      } 
    } 
    paramFile.delete();
  }
  
  public static void deleteDir(String paramString) {
    deleteDir(new File(paramString));
  }
  
  public static boolean ensureDirCreate(File paramFile) {
    return (paramFile.exists() || paramFile.mkdirs());
  }
  
  public static boolean ensureDirCreate(File... paramVarArgs) {
    int i = paramVarArgs.length;
    boolean bool = true;
    for (byte b = 0; b < i; b++) {
      if (!ensureDirCreate(paramVarArgs[b]))
        bool = false; 
    } 
    return bool;
  }
  
  public static long fileSize(String paramString) {
    File file = new File(paramString);
    return !file.exists() ? 0L : file.length();
  }
  
  public static String getFilenameExt(String paramString) {
    int i = paramString.lastIndexOf('.');
    return (i == -1) ? "" : paramString.substring(i + 1);
  }
  
  public static boolean isExist(String paramString) {
    return (new File(paramString)).exists();
  }
  
  public static boolean isSymlink(File paramFile) throws IOException {
    if (paramFile != null) {
      if (paramFile.getParent() != null)
        paramFile = new File(paramFile.getParentFile().getCanonicalFile(), paramFile.getName()); 
      return paramFile.getCanonicalFile().equals(paramFile.getAbsoluteFile()) ^ true;
    } 
    throw new NullPointerException("File must not be null");
  }
  
  public static boolean isValidExtFilename(String paramString) {
    boolean bool;
    if (paramString != null && paramString.equals(buildValidExtFilename(paramString))) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private static boolean isValidExtFilenameChar(char paramChar) {
    return (paramChar != '\000' && paramChar != '/');
  }
  
  public static void linkDir(String paramString1, String paramString2) {
    File file2 = new File(paramString1);
    File file1 = new File(paramString2);
    if (!file1.exists())
      file1.mkdirs(); 
    File[] arrayOfFile = file2.listFiles();
    if (!ArrayUtils.isEmpty(arrayOfFile)) {
      int i = arrayOfFile.length;
      for (byte b = 0; b < i; b++) {
        File file = arrayOfFile[b];
        try {
          Runtime runtime = Runtime.getRuntime();
          File file3 = file.getAbsoluteFile();
          File file4 = new File();
          this(file1, file.getName());
          runtime.exec(String.format("ln -s %s %s", new Object[] { file3, file4.getAbsolutePath() })).waitFor();
        } catch (Exception exception) {
          exception.printStackTrace();
        } 
      } 
    } 
  }
  
  public static int peekInt(byte[] paramArrayOfbyte, int paramInt, ByteOrder paramByteOrder) {
    int i;
    if (paramByteOrder == ByteOrder.BIG_ENDIAN) {
      i = paramInt + 1;
      int j = i + 1;
      i = paramArrayOfbyte[i];
      i = (paramArrayOfbyte[paramInt] & 0xFF) << 24 | (i & 0xFF) << 16 | (paramArrayOfbyte[j] & 0xFF) << 8;
      paramInt = paramArrayOfbyte[j + 1] & 0xFF;
    } else {
      i = paramInt + 1;
      int j = i + 1;
      i = paramArrayOfbyte[i];
      i = paramArrayOfbyte[paramInt] & 0xFF | (i & 0xFF) << 8 | (paramArrayOfbyte[j] & 0xFF) << 16;
      paramInt = (paramArrayOfbyte[j + 1] & 0xFF) << 24;
    } 
    return paramInt | i;
  }
  
  public static String readToString(String paramString) throws IOException {
    FileInputStream fileInputStream = new FileInputStream(paramString);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    while (true) {
      int i = fileInputStream.read();
      if (i != -1) {
        byteArrayOutputStream.write(i);
        continue;
      } 
      return byteArrayOutputStream.toString();
    } 
  }
  
  public static byte[] toByteArray(InputStream paramInputStream) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte[100];
    while (true) {
      int i = paramInputStream.read(arrayOfByte, 0, 100);
      if (i > 0) {
        byteArrayOutputStream.write(arrayOfByte, 0, i);
        continue;
      } 
      return byteArrayOutputStream.toByteArray();
    } 
  }
  
  public static void writeParcelToFile(Parcel paramParcel, File paramFile) throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
    fileOutputStream.write(paramParcel.marshall());
    fileOutputStream.close();
  }
  
  public static void writeToFile(InputStream paramInputStream, File paramFile) throws IOException {
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(paramFile));
    try {
      byte[] arrayOfByte = new byte[1024];
      while (true) {
        int i = paramInputStream.read(arrayOfByte, 0, 1024);
        if (i != -1) {
          bufferedOutputStream.write(arrayOfByte, 0, i);
          continue;
        } 
        bufferedOutputStream.close();
        closeQuietly(bufferedOutputStream);
        return;
      } 
    } catch (IOException iOException) {
      throw iOException;
    } finally {}
    closeQuietly(bufferedOutputStream);
    throw paramInputStream;
  }
  
  public static void writeToFile(byte[] paramArrayOfbyte, File paramFile) throws IOException {
    FileOutputStream fileOutputStream;
    File file2;
    FileChannel fileChannel = null;
    File file1 = null;
    try {
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream();
      this(paramArrayOfbyte);
    } finally {
      paramArrayOfbyte = null;
      paramFile = null;
    } 
    if (fileOutputStream != null)
      fileOutputStream.close(); 
    if (file2 != null)
      file2.close(); 
    if (paramFile != null)
      paramFile.close(); 
    throw paramArrayOfbyte;
  }
  
  public static interface FileMode {
    public static final int MODE_755 = 493;
    
    public static final int MODE_IRGRP = 32;
    
    public static final int MODE_IROTH = 4;
    
    public static final int MODE_IRUSR = 256;
    
    public static final int MODE_ISGID = 1024;
    
    public static final int MODE_ISUID = 2048;
    
    public static final int MODE_ISVTX = 512;
    
    public static final int MODE_IWGRP = 16;
    
    public static final int MODE_IWOTH = 2;
    
    public static final int MODE_IWUSR = 128;
    
    public static final int MODE_IXGRP = 8;
    
    public static final int MODE_IXOTH = 1;
    
    public static final int MODE_IXUSR = 64;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\FileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */