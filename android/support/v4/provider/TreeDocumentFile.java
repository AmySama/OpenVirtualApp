package android.support.v4.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.util.Log;
import java.util.ArrayList;

class TreeDocumentFile extends DocumentFile {
  private Context mContext;
  
  private Uri mUri;
  
  TreeDocumentFile(DocumentFile paramDocumentFile, Context paramContext, Uri paramUri) {
    super(paramDocumentFile);
    this.mContext = paramContext;
    this.mUri = paramUri;
  }
  
  private static void closeQuietly(AutoCloseable paramAutoCloseable) {
    if (paramAutoCloseable != null)
      try {
        paramAutoCloseable.close();
      } catch (RuntimeException runtimeException) {
        throw runtimeException;
      } catch (Exception exception) {} 
  }
  
  private static Uri createFile(Context paramContext, Uri paramUri, String paramString1, String paramString2) {
    try {
      return DocumentsContract.createDocument(paramContext.getContentResolver(), paramUri, paramString1, paramString2);
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public boolean canRead() {
    return DocumentsContractApi19.canRead(this.mContext, this.mUri);
  }
  
  public boolean canWrite() {
    return DocumentsContractApi19.canWrite(this.mContext, this.mUri);
  }
  
  public DocumentFile createDirectory(String paramString) {
    Uri uri = createFile(this.mContext, this.mUri, "vnd.android.document/directory", paramString);
    if (uri != null) {
      TreeDocumentFile treeDocumentFile = new TreeDocumentFile(this, this.mContext, uri);
    } else {
      uri = null;
    } 
    return (DocumentFile)uri;
  }
  
  public DocumentFile createFile(String paramString1, String paramString2) {
    Uri uri = createFile(this.mContext, this.mUri, paramString1, paramString2);
    if (uri != null) {
      TreeDocumentFile treeDocumentFile = new TreeDocumentFile(this, this.mContext, uri);
    } else {
      uri = null;
    } 
    return (DocumentFile)uri;
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
    Cursor cursor1;
    ContentResolver contentResolver = this.mContext.getContentResolver();
    Uri uri1 = this.mUri;
    Uri uri2 = DocumentsContract.buildChildDocumentsUriUsingTree(uri1, DocumentsContract.getDocumentId(uri1));
    ArrayList<Uri> arrayList = new ArrayList();
    byte b = 0;
    uri1 = null;
    Cursor cursor2 = null;
    try {
      String str;
      Cursor cursor = contentResolver.query(uri2, new String[] { "document_id" }, null, null, null);
      while (true) {
        Cursor cursor3 = cursor;
        cursor2 = cursor;
        cursor1 = cursor;
        if (cursor.moveToNext()) {
          cursor2 = cursor;
          cursor1 = cursor;
          str = cursor.getString(0);
          cursor2 = cursor;
          cursor1 = cursor;
          arrayList.add(DocumentsContract.buildDocumentUriUsingTree(this.mUri, str));
          continue;
        } 
        break;
      } 
      closeQuietly((AutoCloseable)str);
    } catch (Exception exception) {
      cursor2 = cursor1;
      StringBuilder stringBuilder = new StringBuilder();
      cursor2 = cursor1;
      this();
      cursor2 = cursor1;
      stringBuilder.append("Failed query: ");
      cursor2 = cursor1;
      stringBuilder.append(exception);
      cursor2 = cursor1;
      Log.w("DocumentFile", stringBuilder.toString());
      Cursor cursor = cursor1;
      closeQuietly((AutoCloseable)cursor);
    } finally {}
    Uri[] arrayOfUri = arrayList.<Uri>toArray(new Uri[arrayList.size()]);
    DocumentFile[] arrayOfDocumentFile = new DocumentFile[arrayOfUri.length];
    while (b < arrayOfUri.length) {
      arrayOfDocumentFile[b] = new TreeDocumentFile(this, this.mContext, arrayOfUri[b]);
      b++;
    } 
    return arrayOfDocumentFile;
  }
  
  public boolean renameTo(String paramString) {
    try {
      Uri uri = DocumentsContract.renameDocument(this.mContext.getContentResolver(), this.mUri, paramString);
      if (uri != null) {
        this.mUri = uri;
        return true;
      } 
    } catch (Exception exception) {}
    return false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\provider\TreeDocumentFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */