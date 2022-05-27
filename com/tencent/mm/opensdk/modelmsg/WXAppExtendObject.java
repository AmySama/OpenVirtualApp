package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

public class WXAppExtendObject implements WXMediaMessage.IMediaObject {
  private static final int CONTENT_LENGTH_LIMIT = 10485760;
  
  private static final int EXTINFO_LENGTH_LIMIT = 2048;
  
  private static final int PATH_LENGTH_LIMIT = 10240;
  
  private static final String TAG = "MicroMsg.SDK.WXAppExtendObject";
  
  public String extInfo;
  
  public byte[] fileData;
  
  public String filePath;
  
  public WXAppExtendObject() {}
  
  public WXAppExtendObject(String paramString1, String paramString2) {
    this.extInfo = paramString1;
    this.filePath = paramString2;
  }
  
  public WXAppExtendObject(String paramString, byte[] paramArrayOfbyte) {
    this.extInfo = paramString;
    this.fileData = paramArrayOfbyte;
  }
  
  private int getFileSize(String paramString) {
    return b.a(paramString);
  }
  
  public boolean checkArgs() {
    String str = this.extInfo;
    if (str == null || str.length() == 0) {
      str = this.filePath;
      if (str == null || str.length() == 0) {
        byte[] arrayOfByte1 = this.fileData;
        if (arrayOfByte1 == null || arrayOfByte1.length == 0) {
          String str1 = "checkArgs fail, all arguments is null";
          Log.e("MicroMsg.SDK.WXAppExtendObject", str1);
          return false;
        } 
      } 
    } 
    str = this.extInfo;
    if (str != null && str.length() > 2048) {
      str = "checkArgs fail, extInfo is invalid";
      Log.e("MicroMsg.SDK.WXAppExtendObject", str);
      return false;
    } 
    str = this.filePath;
    if (str != null && str.length() > 10240) {
      str = "checkArgs fail, filePath is invalid";
      Log.e("MicroMsg.SDK.WXAppExtendObject", str);
      return false;
    } 
    str = this.filePath;
    if (str != null && getFileSize(str) > 10485760) {
      str = "checkArgs fail, fileSize is too large";
      Log.e("MicroMsg.SDK.WXAppExtendObject", str);
      return false;
    } 
    byte[] arrayOfByte = this.fileData;
    if (arrayOfByte != null && arrayOfByte.length > 10485760) {
      String str1 = "checkArgs fail, fileData is too large";
      Log.e("MicroMsg.SDK.WXAppExtendObject", str1);
      return false;
    } 
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putString("_wxappextendobject_extInfo", this.extInfo);
    paramBundle.putByteArray("_wxappextendobject_fileData", this.fileData);
    paramBundle.putString("_wxappextendobject_filePath", this.filePath);
  }
  
  public int type() {
    return 7;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.extInfo = paramBundle.getString("_wxappextendobject_extInfo");
    this.fileData = paramBundle.getByteArray("_wxappextendobject_fileData");
    this.filePath = paramBundle.getString("_wxappextendobject_filePath");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXAppExtendObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */