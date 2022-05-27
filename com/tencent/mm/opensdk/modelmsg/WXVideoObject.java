package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;

public class WXVideoObject implements WXMediaMessage.IMediaObject {
  private static final int LENGTH_LIMIT = 10240;
  
  private static final String TAG = "MicroMsg.SDK.WXVideoObject";
  
  public String videoLowBandUrl;
  
  public String videoUrl;
  
  public boolean checkArgs() {
    String str = this.videoUrl;
    if (str == null || str.length() == 0) {
      str = this.videoLowBandUrl;
      if (str == null || str.length() == 0) {
        str = "both arguments are null";
        Log.e("MicroMsg.SDK.WXVideoObject", str);
        return false;
      } 
    } 
    str = this.videoUrl;
    if (str != null && str.length() > 10240) {
      str = "checkArgs fail, videoUrl is too long";
      Log.e("MicroMsg.SDK.WXVideoObject", str);
      return false;
    } 
    str = this.videoLowBandUrl;
    if (str != null && str.length() > 10240) {
      str = "checkArgs fail, videoLowBandUrl is too long";
      Log.e("MicroMsg.SDK.WXVideoObject", str);
      return false;
    } 
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putString("_wxvideoobject_videoUrl", this.videoUrl);
    paramBundle.putString("_wxvideoobject_videoLowBandUrl", this.videoLowBandUrl);
  }
  
  public int type() {
    return 4;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.videoUrl = paramBundle.getString("_wxvideoobject_videoUrl");
    this.videoLowBandUrl = paramBundle.getString("_wxvideoobject_videoLowBandUrl");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXVideoObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */