package android.support.v4.provider;

import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class RawDocumentFile extends DocumentFile {
  private File mFile;
  
  RawDocumentFile(DocumentFile paramDocumentFile, File paramFile) {
    super(paramDocumentFile);
    this.mFile = paramFile;
  }
  
  private static boolean deleteContents(File paramFile) {
    File[] arrayOfFile = paramFile.listFiles();
    boolean bool1 = true;
    boolean bool2 = true;
    if (arrayOfFile != null) {
      int i = arrayOfFile.length;
      byte b = 0;
      while (true) {
        bool1 = bool2;
        if (b < i) {
          File file = arrayOfFile[b];
          bool1 = bool2;
          if (file.isDirectory())
            bool1 = bool2 & deleteContents(file); 
          bool2 = bool1;
          if (!file.delete()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to delete ");
            stringBuilder.append(file);
            Log.w("DocumentFile", stringBuilder.toString());
            bool2 = false;
          } 
          b++;
          continue;
        } 
        break;
      } 
    } 
    return bool1;
  }
  
  private static String getTypeForName(String paramString) {
    int i = paramString.lastIndexOf('.');
    if (i >= 0) {
      paramString = paramString.substring(i + 1).toLowerCase();
      paramString = MimeTypeMap.getSingleton().getMimeTypeFromExtension(paramString);
      if (paramString != null)
        return paramString; 
    } 
    return "application/octet-stream";
  }
  
  public boolean canRead() {
    return this.mFile.canRead();
  }
  
  public boolean canWrite() {
    return this.mFile.canWrite();
  }
  
  public DocumentFile createDirectory(String paramString) {
    File file = new File(this.mFile, paramString);
    return (file.isDirectory() || file.mkdir()) ? new RawDocumentFile(this, file) : null;
  }
  
  public DocumentFile createFile(String paramString1, String paramString2) {
    String str1;
    String str2 = MimeTypeMap.getSingleton().getExtensionFromMimeType(paramString1);
    paramString1 = paramString2;
    if (str2 != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString2);
      stringBuilder.append(".");
      stringBuilder.append(str2);
      str1 = stringBuilder.toString();
    } 
    File file = new File(this.mFile, str1);
    try {
      file.createNewFile();
      return new RawDocumentFile(this, file);
    } catch (IOException iOException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Failed to createFile: ");
      stringBuilder.append(iOException);
      Log.w("DocumentFile", stringBuilder.toString());
      return null;
    } 
  }
  
  public boolean delete() {
    deleteContents(this.mFile);
    return this.mFile.delete();
  }
  
  public boolean exists() {
    return this.mFile.exists();
  }
  
  public String getName() {
    return this.mFile.getName();
  }
  
  public String getType() {
    return this.mFile.isDirectory() ? null : getTypeForName(this.mFile.getName());
  }
  
  public Uri getUri() {
    return Uri.fromFile(this.mFile);
  }
  
  public boolean isDirectory() {
    return this.mFile.isDirectory();
  }
  
  public boolean isFile() {
    return this.mFile.isFile();
  }
  
  public boolean isVirtual() {
    return false;
  }
  
  public long lastModified() {
    return this.mFile.lastModified();
  }
  
  public long length() {
    return this.mFile.length();
  }
  
  public DocumentFile[] listFiles() {
    ArrayList<RawDocumentFile> arrayList = new ArrayList();
    File[] arrayOfFile = this.mFile.listFiles();
    if (arrayOfFile != null) {
      int i = arrayOfFile.length;
      for (byte b = 0; b < i; b++)
        arrayList.add(new RawDocumentFile(this, arrayOfFile[b])); 
    } 
    return arrayList.<DocumentFile>toArray(new DocumentFile[arrayList.size()]);
  }
  
  public boolean renameTo(String paramString) {
    File file = new File(this.mFile.getParentFile(), paramString);
    if (this.mFile.renameTo(file)) {
      this.mFile = file;
      return true;
    } 
    return false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\provider\RawDocumentFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */