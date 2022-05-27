package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

public class WXEmojiObject implements WXMediaMessage.IMediaObject {
  private static final int CONTENT_LENGTH_LIMIT = 10485760;
  
  private static final String TAG = "MicroMsg.SDK.WXEmojiObject";
  
  public byte[] emojiData;
  
  public String emojiPath;
  
  public WXEmojiObject() {
    this.emojiData = null;
    this.emojiPath = null;
  }
  
  public WXEmojiObject(String paramString) {
    this.emojiPath = paramString;
  }
  
  public WXEmojiObject(byte[] paramArrayOfbyte) {
    this.emojiData = paramArrayOfbyte;
  }
  
  private int getFileSize(String paramString) {
    return b.a(paramString);
  }
  
  public boolean checkArgs() {
    byte[] arrayOfByte = this.emojiData;
    if (arrayOfByte == null || arrayOfByte.length == 0) {
      String str1 = this.emojiPath;
      if (str1 == null || str1.length() == 0) {
        str1 = "checkArgs fail, both arguments is null";
        Log.e("MicroMsg.SDK.WXEmojiObject", str1);
        return false;
      } 
    } 
    arrayOfByte = this.emojiData;
    if (arrayOfByte != null && arrayOfByte.length > 10485760) {
      String str1 = "checkArgs fail, emojiData is too large";
      Log.e("MicroMsg.SDK.WXEmojiObject", str1);
      return false;
    } 
    String str = this.emojiPath;
    if (str != null && getFileSize(str) > 10485760) {
      str = "checkArgs fail, emojiSize is too large";
      Log.e("MicroMsg.SDK.WXEmojiObject", str);
      return false;
    } 
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putByteArray("_wxemojiobject_emojiData", this.emojiData);
    paramBundle.putString("_wxemojiobject_emojiPath", this.emojiPath);
  }
  
  public void setEmojiData(byte[] paramArrayOfbyte) {
    this.emojiData = paramArrayOfbyte;
  }
  
  public void setEmojiPath(String paramString) {
    this.emojiPath = paramString;
  }
  
  public int type() {
    return 8;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.emojiData = paramBundle.getByteArray("_wxemojiobject_emojiData");
    this.emojiPath = paramBundle.getString("_wxemojiobject_emojiPath");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXEmojiObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */