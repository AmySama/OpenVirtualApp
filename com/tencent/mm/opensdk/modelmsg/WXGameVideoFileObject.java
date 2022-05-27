package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

public class WXGameVideoFileObject implements WXMediaMessage.IMediaObject {
  private static final int FILE_SIZE_LIMIT = 104857600;
  
  private static final String TAG = "MicroMsg.SDK.WXGameVideoFileObject";
  
  private static final int URL_LENGTH_LIMIT = 10240;
  
  public String filePath = null;
  
  public String thumbUrl = null;
  
  public String videoUrl = null;
  
  public WXGameVideoFileObject() {}
  
  public WXGameVideoFileObject(String paramString1, String paramString2, String paramString3) {}
  
  private int getFileSize(String paramString) {
    return b.a(paramString);
  }
  
  public boolean checkArgs() {
    String str = this.filePath;
    if (str == null || str.length() == 0) {
      str = "checkArgs fail, filePath is null";
      Log.e("MicroMsg.SDK.WXGameVideoFileObject", str);
      return false;
    } 
    if (getFileSize(this.filePath) > 104857600) {
      str = "checkArgs fail, video file size is too large";
      Log.e("MicroMsg.SDK.WXGameVideoFileObject", str);
      return false;
    } 
    str = this.videoUrl;
    if (str != null && str.length() > 10240) {
      str = "checkArgs fail, videoUrl is too long";
      Log.e("MicroMsg.SDK.WXGameVideoFileObject", str);
      return false;
    } 
    str = this.thumbUrl;
    if (str != null && str.length() > 10240) {
      str = "checkArgs fail, thumbUrl is too long";
      Log.e("MicroMsg.SDK.WXGameVideoFileObject", str);
      return false;
    } 
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putString("_wxvideofileobject_filePath", this.filePath);
    paramBundle.putString("_wxvideofileobject_cdnUrl", this.videoUrl);
    paramBundle.putString("_wxvideofileobject_thumbUrl", this.thumbUrl);
  }
  
  public int type() {
    return 39;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.filePath = paramBundle.getString("_wxvideofileobject_filePath");
    this.videoUrl = paramBundle.getString("_wxvideofileobject_cdnUrl");
    this.thumbUrl = paramBundle.getString("_wxvideofileobject_thumbUrl");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXGameVideoFileObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */