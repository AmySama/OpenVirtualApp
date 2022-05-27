package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;

public abstract class WXChannelBaseJumpInfo implements IWXChannelJumpInfo {
  private static final String TAG = "MicroMsg.SDK.WXChannelBaseJumpInfo";
  
  private static final int WORDING_LENGTH_LIMIT = 1024;
  
  public String extra;
  
  public String wording;
  
  public boolean checkArgs() {
    String str = this.wording;
    if (str != null && str.length() >= 1024) {
      Log.e("MicroMsg.SDK.WXChannelBaseJumpInfo", "checkArgs fail, wording is invalid");
      return false;
    } 
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putString("wx_channel_jump_base_wording", this.wording);
    paramBundle.putString("wx_channel_jump_base_extra", this.extra);
  }
  
  public void unserialize(Bundle paramBundle) {
    this.wording = paramBundle.getString("wx_channel_jump_base_wording");
    this.extra = paramBundle.getString("wx_channel_jump_base_extra");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\WXChannelBaseJumpInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */