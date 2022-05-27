package de.robv.android.xposed.services;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public abstract class BaseService {
  public static final int F_OK = 0;
  
  public static final int R_OK = 4;
  
  public static final int W_OK = 2;
  
  public static final int X_OK = 1;
  
  static void ensureAbsolutePath(String paramString) {
    if (paramString.startsWith("/"))
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Only absolute filenames are allowed: ");
    stringBuilder.append(paramString);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  static void throwCommonIOException(int paramInt, String paramString1, String paramString2, String paramString3) throws IOException {
    String str;
    if (paramInt != 1)
      if (paramInt != 2) {
        if (paramInt != 12) {
          if (paramInt != 13) {
            if (paramInt != 21) {
              if (paramString1 == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error ");
                stringBuilder.append(paramInt);
                stringBuilder.append(paramString3);
                stringBuilder.append(paramString2);
                str = stringBuilder.toString();
              } 
              throw new IOException(str);
            } 
            if (str == null) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Is a directory: ");
              stringBuilder.append(paramString2);
              str = stringBuilder.toString();
            } 
            throw new FileNotFoundException(str);
          } 
        } else {
          throw new OutOfMemoryError(str);
        } 
      } else {
        if (str == null) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("No such file or directory: ");
          stringBuilder.append(paramString2);
          str = stringBuilder.toString();
        } 
        throw new FileNotFoundException(str);
      }  
    if (str == null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Permission denied: ");
      stringBuilder.append(paramString2);
      str = stringBuilder.toString();
    } 
    throw new FileNotFoundException(str);
  }
  
  public abstract boolean checkFileAccess(String paramString, int paramInt);
  
  public boolean checkFileExists(String paramString) {
    return checkFileAccess(paramString, 0);
  }
  
  public FileResult getFileInputStream(String paramString, long paramLong1, long paramLong2) throws IOException {
    FileResult fileResult = readFile(paramString, paramLong1, paramLong2);
    return (fileResult.content == null) ? fileResult : new FileResult(new ByteArrayInputStream(fileResult.content), fileResult.size, fileResult.mtime);
  }
  
  public InputStream getFileInputStream(String paramString) throws IOException {
    return new ByteArrayInputStream(readFile(paramString));
  }
  
  public long getFileModificationTime(String paramString) throws IOException {
    return (statFile(paramString)).mtime;
  }
  
  public long getFileSize(String paramString) throws IOException {
    return (statFile(paramString)).size;
  }
  
  public boolean hasDirectFileAccess() {
    return false;
  }
  
  public abstract FileResult readFile(String paramString, int paramInt1, int paramInt2, long paramLong1, long paramLong2) throws IOException;
  
  public abstract FileResult readFile(String paramString, long paramLong1, long paramLong2) throws IOException;
  
  public abstract byte[] readFile(String paramString) throws IOException;
  
  public abstract FileResult statFile(String paramString) throws IOException;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\services\BaseService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */