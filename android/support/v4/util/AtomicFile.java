package android.support.v4.util;

import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AtomicFile {
  private final File mBackupName;
  
  private final File mBaseName;
  
  public AtomicFile(File paramFile) {
    this.mBaseName = paramFile;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramFile.getPath());
    stringBuilder.append(".bak");
    this.mBackupName = new File(stringBuilder.toString());
  }
  
  private static boolean sync(FileOutputStream paramFileOutputStream) {
    try {
      paramFileOutputStream.getFD().sync();
      return true;
    } catch (IOException iOException) {
      return false;
    } 
  }
  
  public void delete() {
    this.mBaseName.delete();
    this.mBackupName.delete();
  }
  
  public void failWrite(FileOutputStream paramFileOutputStream) {
    if (paramFileOutputStream != null) {
      sync(paramFileOutputStream);
      try {
        paramFileOutputStream.close();
        this.mBaseName.delete();
        this.mBackupName.renameTo(this.mBaseName);
      } catch (IOException iOException) {
        Log.w("AtomicFile", "failWrite: Got exception:", iOException);
      } 
    } 
  }
  
  public void finishWrite(FileOutputStream paramFileOutputStream) {
    if (paramFileOutputStream != null) {
      sync(paramFileOutputStream);
      try {
        paramFileOutputStream.close();
        this.mBackupName.delete();
      } catch (IOException iOException) {
        Log.w("AtomicFile", "finishWrite: Got exception:", iOException);
      } 
    } 
  }
  
  public File getBaseFile() {
    return this.mBaseName;
  }
  
  public FileInputStream openRead() throws FileNotFoundException {
    if (this.mBackupName.exists()) {
      this.mBaseName.delete();
      this.mBackupName.renameTo(this.mBaseName);
    } 
    return new FileInputStream(this.mBaseName);
  }
  
  public byte[] readFully() throws IOException {
    FileInputStream fileInputStream = openRead();
    try {
      byte[] arrayOfByte = new byte[fileInputStream.available()];
      int i = 0;
      while (true) {
        int j = fileInputStream.read(arrayOfByte, i, arrayOfByte.length - i);
        if (j <= 0)
          return arrayOfByte; 
        j = i + j;
        int k = fileInputStream.available();
        i = j;
        if (k > arrayOfByte.length - j) {
          byte[] arrayOfByte1 = new byte[k + j];
          System.arraycopy(arrayOfByte, 0, arrayOfByte1, 0, j);
          arrayOfByte = arrayOfByte1;
          i = j;
        } 
      } 
    } finally {
      fileInputStream.close();
    } 
  }
  
  public FileOutputStream startWrite() throws IOException {
    StringBuilder stringBuilder;
    if (this.mBaseName.exists())
      if (!this.mBackupName.exists()) {
        if (!this.mBaseName.renameTo(this.mBackupName)) {
          stringBuilder = new StringBuilder();
          stringBuilder.append("Couldn't rename file ");
          stringBuilder.append(this.mBaseName);
          stringBuilder.append(" to backup file ");
          stringBuilder.append(this.mBackupName);
          Log.w("AtomicFile", stringBuilder.toString());
        } 
      } else {
        this.mBaseName.delete();
      }  
    try {
      FileOutputStream fileOutputStream = new FileOutputStream();
      this(this.mBaseName);
    } catch (FileNotFoundException fileNotFoundException) {
      if (this.mBaseName.getParentFile().mkdirs())
        try {
          return new FileOutputStream(this.mBaseName);
        } catch (FileNotFoundException fileNotFoundException1) {
          stringBuilder = new StringBuilder();
          stringBuilder.append("Couldn't create ");
          stringBuilder.append(this.mBaseName);
          throw new IOException(stringBuilder.toString());
        }  
    } 
    return (FileOutputStream)stringBuilder;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v\\util\AtomicFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */