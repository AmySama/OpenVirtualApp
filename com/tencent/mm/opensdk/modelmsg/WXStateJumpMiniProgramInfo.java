package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;

public class WXStateJumpMiniProgramInfo implements WXStateSceneDataObject.IWXStateJumpInfo {
  private static final String TAG = "MicroMsg.SDK.WXStateJumpUrlInfo";
  
  public int miniProgramType;
  
  public String path;
  
  public String username;
  
  public boolean checkArgs() {
    String str = this.username;
    if (str == null || str.length() <= 0) {
      Log.e("MicroMsg.SDK.WXStateJumpUrlInfo", "checkArgs fail, username is null");
      return false;
    } 
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putString("wx_state_jump_mini_program_username", this.username);
    paramBundle.putString("wx_state_jump_mini_program_path", this.path);
    paramBundle.putInt("wx_state_jump_mini_program_type", this.miniProgramType);
  }
  
  public int type() {
    return 2;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.username = paramBundle.getString("wx_state_jump_mini_program_username", "");
    this.path = paramBundle.getString("wx_state_jump_mini_program_path", "");
    this.miniProgramType = paramBundle.getInt("wx_state_jump_mini_program_type", 0);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXStateJumpMiniProgramInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */