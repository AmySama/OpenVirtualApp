package com.lody.virtual.helper;

import com.lody.virtual.helper.utils.VLog;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ApkLibraryHelper {
  public static final String[] ABI_32BIT;
  
  public static final String[] ABI_64BIT;
  
  public static final String[] DEFAULT_SUPPORTED_ABI_32BIT = new String[] { "armeabi", "armeabi-v7a" };
  
  public static final String[] DEFAULT_SUPPORTED_ABI_64BIT = new String[] { "arm64-v8a" };
  
  public static final int INSTALL_FAILED_INVALID_APK = -2;
  
  public static final int INSTALL_FAILED_NO_MATCHING_ABIS = -113;
  
  public static final int INSTALL_SUCCEEDED = 1;
  
  public static final int NO_NATIVE_LIBRARIES = -114;
  
  private ZipFile apkFile;
  
  static {
    ABI_32BIT = new String[] { "armeabi", "armeabi-v7a", "x86" };
    ABI_64BIT = new String[] { "arm64-v8a", "x86_64" };
  }
  
  public ApkLibraryHelper(File paramFile) {
    try {
      ZipFile zipFile = new ZipFile();
      this(paramFile, 1);
      this.apkFile = zipFile;
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  public int copyNativeBinaries(File paramFile, String paramString) {
    ZipFile zipFile = this.apkFile;
    if (zipFile == null)
      return -2; 
    Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
    byte[] arrayOfByte = new byte[8192];
    while (enumeration.hasMoreElements()) {
      ZipEntry zipEntry = enumeration.nextElement();
      if (zipEntry.isDirectory())
        continue; 
      String str1 = zipEntry.getName();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("lib/");
      stringBuilder.append(paramString);
      stringBuilder.append("/");
      String str2 = stringBuilder.toString();
      if (str1.startsWith(str2)) {
        File file = new File(paramFile, str1.substring(str2.length()));
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Writing: ");
        stringBuilder1.append(file);
        VLog.e("ApkLibraryHelper", stringBuilder1.toString());
        try {
          file.createNewFile();
          BufferedOutputStream bufferedOutputStream = new BufferedOutputStream();
          FileOutputStream fileOutputStream = new FileOutputStream();
          this(file);
          this(fileOutputStream);
          InputStream inputStream = this.apkFile.getInputStream(zipEntry);
          while (true) {
            int i = inputStream.read(arrayOfByte, 0, 100);
            if (i > 0) {
              bufferedOutputStream.write(arrayOfByte, 0, i);
              continue;
            } 
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
          } 
        } catch (IOException iOException) {
          throw new RuntimeException(iOException);
        } 
      } 
    } 
    return 1;
  }
  
  public int findSupportedAbi(String[] paramArrayOfString) {
    ZipFile zipFile = this.apkFile;
    if (zipFile == null)
      return -2; 
    byte b = -114;
    Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
    label25: while (enumeration.hasMoreElements()) {
      byte b1 = -113;
      ZipEntry zipEntry = enumeration.nextElement();
      if (zipEntry.isDirectory()) {
        b = b1;
        continue;
      } 
      String str = zipEntry.getName();
      if (!str.startsWith("lib/")) {
        b = b1;
        continue;
      } 
      byte b2 = 0;
      while (true) {
        b = b1;
        if (b2 < paramArrayOfString.length) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("lib/");
          stringBuilder.append(paramArrayOfString[b2]);
          if (str.startsWith(stringBuilder.toString()))
            return b2; 
          b2++;
          continue;
        } 
        continue label25;
      } 
    } 
    return b;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\ApkLibraryHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */