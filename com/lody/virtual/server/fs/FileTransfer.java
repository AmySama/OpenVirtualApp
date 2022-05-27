package com.lody.virtual.server.fs;

import android.os.ParcelFileDescriptor;
import com.lody.virtual.remote.FileInfo;
import java.io.File;

public class FileTransfer extends IFileTransfer.Stub {
  private static final FileTransfer sInstance = new FileTransfer();
  
  public static FileTransfer get() {
    return sInstance;
  }
  
  public FileInfo[] listFiles(String paramString) {
    File[] arrayOfFile = (new File(paramString)).listFiles();
    if (arrayOfFile == null)
      return null; 
    FileInfo[] arrayOfFileInfo = new FileInfo[arrayOfFile.length];
    for (byte b = 0; b < arrayOfFile.length; b++)
      arrayOfFileInfo[b] = new FileInfo(arrayOfFile[b]); 
    return arrayOfFileInfo;
  }
  
  public ParcelFileDescriptor openFile(String paramString) {
    try {
      File file = new File();
      this(paramString);
      return ParcelFileDescriptor.open(file, 268435456);
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\fs\FileTransfer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */