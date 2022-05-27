package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;

public class WXStateJumpUrlInfo implements WXStateSceneDataObject.IWXStateJumpInfo {
  private static final int LENGTH_LIMIT = 10240;
  
  private static final String TAG = "MicroMsg.SDK.WXStateJumpUrlInfo";
  
  public String jumpUrl;
  
  public boolean checkArgs() {
    String str = this.jumpUrl;
    if (str == null || str.length() <= 0) {
      str = "checkArgs fail, jumpUrl is null";
      Log.e("MicroMsg.SDK.WXStateJumpUrlInfo", str);
      return false;
    } 
    if (this.jumpUrl.length() >= 10240) {
      str = "checkArgs fail, jumpUrl is invalid";
      Log.e("MicroMsg.SDK.WXStateJumpUrlInfo", str);
      return false;
    } 
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putString("wx_state_jump_url", this.jumpUrl);
  }
  
  public int type() {
    return 1;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.jumpUrl = paramBundle.getString("wx_state_jump_url", "");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXStateJumpUrlInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */