package android.support.v4.provider;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import java.io.File;

public abstract class DocumentFile {
  static final String TAG = "DocumentFile";
  
  private final DocumentFile mParent;
  
  DocumentFile(DocumentFile paramDocumentFile) {
    this.mParent = paramDocumentFile;
  }
  
  public static DocumentFile fromFile(File paramFile) {
    return new RawDocumentFile(null, paramFile);
  }
  
  public static DocumentFile fromSingleUri(Context paramContext, Uri paramUri) {
    return (Build.VERSION.SDK_INT >= 19) ? new SingleDocumentFile(null, paramContext, paramUri) : null;
  }
  
  public static DocumentFile fromTreeUri(Context paramContext, Uri paramUri) {
    return (Build.VERSION.SDK_INT >= 21) ? new TreeDocumentFile(null, paramContext, DocumentsContract.buildDocumentUriUsingTree(paramUri, DocumentsContract.getTreeDocumentId(paramUri))) : null;
  }
  
  public static boolean isDocumentUri(Context paramContext, Uri paramUri) {
    return (Build.VERSION.SDK_INT >= 19) ? DocumentsContractApi19.isDocumentUri(paramContext, paramUri) : false;
  }
  
  public abstract boolean canRead();
  
  public abstract boolean canWrite();
  
  public abstract DocumentFile createDirectory(String paramString);
  
  public abstract DocumentFile createFile(String paramString1, String paramString2);
  
  public abstract boolean delete();
  
  public abstract boolean exists();
  
  public DocumentFile findFile(String paramString) {
    for (DocumentFile documentFile : listFiles()) {
      if (paramString.equals(documentFile.getName()))
        return documentFile; 
    } 
    return null;
  }
  
  public abstract String getName();
  
  public DocumentFile getParentFile() {
    return this.mParent;
  }
  
  public abstract String getType();
  
  public abstract Uri getUri();
  
  public abstract boolean isDirectory();
  
  public abstract boolean isFile();
  
  public abstract boolean isVirtual();
  
  public abstract long lastModified();
  
  public abstract long length();
  
  public abstract DocumentFile[] listFiles();
  
  public abstract boolean renameTo(String paramString);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\provider\DocumentFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */