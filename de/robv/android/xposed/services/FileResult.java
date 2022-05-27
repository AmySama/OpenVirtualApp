package de.robv.android.xposed.services;

import java.io.InputStream;

public final class FileResult {
  public final byte[] content = null;
  
  public final long mtime;
  
  public final long size;
  
  public final InputStream stream = null;
  
  public FileResult(long paramLong1, long paramLong2) {
    this.size = paramLong1;
    this.mtime = paramLong2;
  }
  
  public FileResult(InputStream paramInputStream, long paramLong1, long paramLong2) {
    this.size = paramLong1;
    this.mtime = paramLong2;
  }
  
  public FileResult(byte[] paramArrayOfbyte, long paramLong1, long paramLong2) {
    this.size = paramLong1;
    this.mtime = paramLong2;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("{");
    if (this.content != null) {
      stringBuilder.append("content.length: ");
      stringBuilder.append(this.content.length);
      stringBuilder.append(", ");
    } 
    if (this.stream != null) {
      stringBuilder.append("stream: ");
      stringBuilder.append(this.stream.toString());
      stringBuilder.append(", ");
    } 
    stringBuilder.append("size: ");
    stringBuilder.append(this.size);
    stringBuilder.append(", mtime: ");
    stringBuilder.append(this.mtime);
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\services\FileResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */