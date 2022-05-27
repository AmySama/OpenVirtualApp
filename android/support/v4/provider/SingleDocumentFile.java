package android.support.v4.provider;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;

class SingleDocumentFile extends DocumentFile {
  private Context mContext;
  
  private Uri mUri;
  
  SingleDocumentFile(DocumentFile paramDocumentFile, Context paramContext, Uri paramUri) {
    super(paramDocumentFile);
    this.mContext = paramContext;
    this.mUri = paramUri;
  }
  
  public boolean canRead() {
    return DocumentsContractApi19.canRead(this.mContext, this.mUri);
  }
  
  public boolean canWrite() {
    return DocumentsContractApi19.canWrite(this.mContext, this.mUri);
  }
  
  public DocumentFile createDirectory(String paramString) {
    throw new UnsupportedOperationException();
  }
  
  public DocumentFile createFile(String paramString1, String paramString2) {
    throw new UnsupportedOperationException();
  }
  
  public boolean delete() {
    try {
      return DocumentsContract.deleteDocument(this.mContext.getContentResolver(), this.mUri);
    } catch (Exception exception) {
      return false;
    } 
  }
  
  public boolean exists() {
    return DocumentsContractApi19.exists(this.mContext, this.mUri);
  }
  
  public String getName() {
    return DocumentsContractApi19.getName(this.mContext, this.mUri);
  }
  
  public String getType() {
    return DocumentsContractApi19.getType(this.mContext, this.mUri);
  }
  
  public Uri getUri() {
    return this.mUri;
  }
  
  public boolean isDirectory() {
    return DocumentsContractApi19.isDirectory(this.mContext, this.mUri);
  }
  
  public boolean isFile() {
    return DocumentsContractApi19.isFile(this.mContext, this.mUri);
  }
  
  public boolean isVirtual() {
    return DocumentsContractApi19.isVirtual(this.mContext, this.mUri);
  }
  
  public long lastModified() {
    return DocumentsContractApi19.lastModified(this.mContext, this.mUri);
  }
  
  public long length() {
    return DocumentsContractApi19.length(this.mContext, this.mUri);
  }
  
  public DocumentFile[] listFiles() {
    throw new UnsupportedOperationException();
  }
  
  public boolean renameTo(String paramString) {
    throw new UnsupportedOperationException();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\provider\SingleDocumentFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */