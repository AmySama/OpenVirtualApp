package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;

public class WXChannelJumpMiniProgramInfo extends WXChannelBaseJumpInfo {
  private static final int LENGTH_LIMIT = 10240;
  
  private static final String TAG = "MicroMsg.SDK.WXChannelJumpMiniProgramInfo";
  
  public String path;
  
  public String username;
  
  public boolean checkArgs() {
    String str = this.username;
    if (str == null || str.length() <= 0) {
      str = "checkArgs fail, username is null";
      Log.e("MicroMsg.SDK.WXChannelJumpMiniProgramInfo", str);
      return false;
    } 
    str = this.path;
    if (str != null && str.length() >= 10240) {
      str = "checkArgs fail, path is invalid";
      Log.e("MicroMsg.SDK.WXChannelJumpMiniProgramInfo", str);
      return false;
    } 
    return super.checkArgs();
  }
  
  public void serialize(Bundle paramBundle) {
    super.serialize(paramBundle);
    paramBundle.putString("wx_channel_jump_mini_program_username", this.username);
    paramBundle.putString("wx_channel_jump_mini_program_path", this.path);
  }
  
  public int type() {
    return 1;
  }
  
  public void unserialize(Bundle paramBundle) {
    super.unserialize(paramBundle);
    this.username = paramBundle.getString("wx_channel_jump_mini_program_username");
    this.path = paramBundle.getString("wx_channel_jump_mini_program_path");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\WXChannelJumpMiniProgramInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */