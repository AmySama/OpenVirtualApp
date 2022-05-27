package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;

public class WXStateJumpChannelProfileInfo implements WXStateSceneDataObject.IWXStateJumpInfo {
  private static final String TAG = "MicroMsg.SDK.WXStateJumpUrlInfo";
  
  private static final int USERNAME_LENGTH_LIMIT = 1024;
  
  public String username;
  
  public boolean checkArgs() {
    String str = this.username;
    if (str == null || str.length() <= 0) {
      str = "checkArgs fail, username is null";
      Log.e("MicroMsg.SDK.WXStateJumpUrlInfo", str);
      return false;
    } 
    if (this.username.length() >= 1024) {
      str = "checkArgs fail, username length exceed limit";
      Log.e("MicroMsg.SDK.WXStateJumpUrlInfo", str);
      return false;
    } 
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putString("wx_state_jump_channel_profile_username", this.username);
  }
  
  public int type() {
    return 3;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.username = paramBundle.getString("wx_state_jump_channel_profile_username", "");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXStateJumpChannelProfileInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */