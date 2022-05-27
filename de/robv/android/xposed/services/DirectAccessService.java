package de.robv.android.xposed.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class DirectAccessService extends BaseService {
  public boolean checkFileAccess(String paramString, int paramInt) {
    File file = new File(paramString);
    return (paramInt == 0 && !file.exists()) ? false : (((paramInt & 0x4) != 0 && !file.canRead()) ? false : (((paramInt & 0x2) != 0 && !file.canWrite()) ? false : (!((paramInt & 0x1) != 0 && !file.canExecute()))));
  }
  
  public boolean checkFileExists(String paramString) {
    return (new File(paramString)).exists();
  }
  
  public FileResult getFileInputStream(String paramString, long paramLong1, long paramLong2) throws IOException {
    File file = new File(paramString);
    long l1 = file.length();
    long l2 = file.lastModified();
    return (paramLong1 == l1 && paramLong2 == l2) ? new FileResult(l1, l2) : new FileResult(new BufferedInputStream(new FileInputStream(paramString), 16384), l1, l2);
  }
  
  public InputStream getFileInputStream(String paramString) throws IOException {
    return new BufferedInputStream(new FileInputStream(paramString), 16384);
  }
  
  public boolean hasDirectFileAccess() {
    return true;
  }
  
  public FileResult readFile(String paramString, int paramInt1, int paramInt2, long paramLong1, long paramLong2) throws IOException {
    byte[] arrayOfByte;
    File file = new File(paramString);
    long l1 = file.length();
    long l2 = file.lastModified();
    if (paramLong1 == l1 && paramLong2 == l2)
      return new FileResult(l1, l2); 
    if (paramInt1 <= 0 && paramInt2 <= 0)
      return new FileResult(readFile(paramString), l1, l2); 
    if (paramInt1 <= 0 || paramInt1 < l1) {
      int i = paramInt1;
      if (paramInt1 < 0)
        i = 0; 
      if (paramInt2 <= 0 || (i + paramInt2) <= l1) {
        paramInt1 = paramInt2;
        if (paramInt2 <= 0)
          paramInt1 = (int)(l1 - i); 
        arrayOfByte = new byte[paramInt1];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.skip(i);
        fileInputStream.read(arrayOfByte);
        fileInputStream.close();
        return new FileResult(arrayOfByte, l1, l2);
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Length ");
      stringBuilder1.append(paramInt2);
      stringBuilder1.append(" is out of range for ");
      stringBuilder1.append((String)arrayOfByte);
      throw new IllegalArgumentException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Offset ");
    stringBuilder.append(paramInt1);
    stringBuilder.append(" is out of range for ");
    stringBuilder.append((String)arrayOfByte);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public FileResult readFile(String paramString, long paramLong1, long paramLong2) throws IOException {
    File file = new File(paramString);
    long l1 = file.length();
    long l2 = file.lastModified();
    return (paramLong1 == l1 && paramLong2 == l2) ? new FileResult(l1, l2) : new FileResult(readFile(paramString), l1, l2);
  }
  
  public byte[] readFile(String paramString) throws IOException {
    File file = new File(paramString);
    byte[] arrayOfByte = new byte[(int)file.length()];
    FileInputStream fileInputStream = new FileInputStream(file);
    fileInputStream.read(arrayOfByte);
    fileInputStream.close();
    return arrayOfByte;
  }
  
  public FileResult statFile(String paramString) throws IOException {
    File file = new File(paramString);
    return new FileResult(file.length(), file.lastModified());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\services\DirectAccessService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */