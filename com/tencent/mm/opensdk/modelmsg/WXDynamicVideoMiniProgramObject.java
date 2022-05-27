package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

public class WXDynamicVideoMiniProgramObject extends WXMiniProgramObject {
  private static final String TAG = "MicroMsg.SDK.WXDynamicVideoMiniProgramObject";
  
  public String appThumbUrl;
  
  public String videoSource;
  
  public boolean checkArgs() {
    if (b.b(this.webpageUrl)) {
      String str = "webPageUrl is null";
      Log.e("MicroMsg.SDK.WXDynamicVideoMiniProgramObject", str);
      return false;
    } 
    if (b.b(this.userName)) {
      String str = "userName is null";
      Log.e("MicroMsg.SDK.WXDynamicVideoMiniProgramObject", str);
      return false;
    } 
    int i = this.miniprogramType;
    if (i < 0 || i > 2) {
      String str = "miniprogram type should between MINIPTOGRAM_TYPE_RELEASE and MINIPROGRAM_TYPE_PREVIEW";
      Log.e("MicroMsg.SDK.WXDynamicVideoMiniProgramObject", str);
      return false;
    } 
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putString("_wxminiprogram_webpageurl", this.webpageUrl);
    paramBundle.putString("_wxminiprogram_username", this.userName);
    paramBundle.putString("_wxminiprogram_path", this.path);
    paramBundle.putString("_wxminiprogram_videoSource", this.videoSource);
    paramBundle.putString("_wxminiprogram_appThumbUrl", this.appThumbUrl);
    paramBundle.putBoolean("_wxminiprogram_withsharetiket", this.withShareTicket);
    paramBundle.putInt("_wxminiprogram_type", this.miniprogramType);
    paramBundle.putInt("_wxminiprogram_disableforward", this.disableforward);
  }
  
  public int type() {
    return 46;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.webpageUrl = paramBundle.getString("_wxminiprogram_webpageurl");
    this.userName = paramBundle.getString("_wxminiprogram_username");
    this.path = paramBundle.getString("_wxminiprogram_path");
    this.videoSource = paramBundle.getString("_wxminiprogram_videoSource");
    this.appThumbUrl = paramBundle.getString("_wxminiprogram_appThumbUrl");
    this.withShareTicket = paramBundle.getBoolean("_wxminiprogram_withsharetiket");
    this.miniprogramType = paramBundle.getInt("_wxminiprogram_type");
    this.disableforward = paramBundle.getInt("_wxminiprogram_disableforward");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXDynamicVideoMiniProgramObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */