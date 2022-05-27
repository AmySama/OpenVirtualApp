package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;

public class WXChannelJumpUrlInfo extends WXChannelBaseJumpInfo {
  private static final int LENGTH_LIMIT = 10240;
  
  private static final String TAG = "MicroMsg.SDK.WXChannelJumpUrlInfo";
  
  public String url;
  
  public boolean checkArgs() {
    String str = this.url;
    if (str == null || str.length() <= 0) {
      str = "checkArgs fail, url is null";
      Log.e("MicroMsg.SDK.WXChannelJumpUrlInfo", str);
      return false;
    } 
    if (this.url.length() >= 10240) {
      str = "checkArgs fail, url is invalid";
      Log.e("MicroMsg.SDK.WXChannelJumpUrlInfo", str);
      return false;
    } 
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    super.serialize(paramBundle);
    paramBundle.putString("wx_channel_jump_url", this.url);
  }
  
  public int type() {
    return 2;
  }
  
  public void unserialize(Bundle paramBundle) {
    super.unserialize(paramBundle);
    this.url = paramBundle.getString("wx_channel_jump_url");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\WXChannelJumpUrlInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */