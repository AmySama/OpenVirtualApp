package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

public class WXFileObject implements WXMediaMessage.IMediaObject {
  private static final int CONTENT_LENGTH_LIMIT = 1920991232;
  
  private static final String TAG = "MicroMsg.SDK.WXFileObject";
  
  private int contentLengthLimit = 1920991232;
  
  public byte[] fileData;
  
  public String filePath;
  
  public WXFileObject() {
    this.fileData = null;
    this.filePath = null;
  }
  
  public WXFileObject(String paramString) {
    this.filePath = paramString;
  }
  
  public WXFileObject(byte[] paramArrayOfbyte) {
    this.fileData = paramArrayOfbyte;
  }
  
  private int getFileSize(String paramString) {
    return b.a(paramString);
  }
  
  public boolean checkArgs() {
    byte[] arrayOfByte = this.fileData;
    if (arrayOfByte == null || arrayOfByte.length == 0) {
      String str1 = this.filePath;
      if (str1 == null || str1.length() == 0) {
        str1 = "checkArgs fail, both arguments is null";
        Log.e("MicroMsg.SDK.WXFileObject", str1);
        return false;
      } 
    } 
    arrayOfByte = this.fileData;
    if (arrayOfByte != null && arrayOfByte.length > this.contentLengthLimit) {
      String str1 = "checkArgs fail, fileData is too large";
      Log.e("MicroMsg.SDK.WXFileObject", str1);
      return false;
    } 
    String str = this.filePath;
    if (str != null && getFileSize(str) > this.contentLengthLimit) {
      str = "checkArgs fail, fileSize is too large";
      Log.e("MicroMsg.SDK.WXFileObject", str);
      return false;
    } 
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putByteArray("_wxfileobject_fileData", this.fileData);
    paramBundle.putString("_wxfileobject_filePath", this.filePath);
  }
  
  public void setContentLengthLimit(int paramInt) {
    this.contentLengthLimit = paramInt;
  }
  
  public void setFileData(byte[] paramArrayOfbyte) {
    this.fileData = paramArrayOfbyte;
  }
  
  public void setFilePath(String paramString) {
    this.filePath = paramString;
  }
  
  public int type() {
    return 6;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.fileData = paramBundle.getByteArray("_wxfileobject_fileData");
    this.filePath = paramBundle.getString("_wxfileobject_filePath");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXFileObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */