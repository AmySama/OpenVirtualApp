package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;

public class WXStateSceneDataObject implements SendMessageToWX.IWXSceneDataObject {
  private static final int LENGTH_LIMIT = 10240;
  
  private static final String TAG = "MicroMsg.SDK.WXStateSceneDataObject";
  
  private static final String WX_STATE_JUMP_INFO_KEY_IDENTIFIER = "_wxapi_scene_data_state_jump_info_identifier";
  
  public String stateId;
  
  public IWXStateJumpInfo stateJumpInfo;
  
  public String stateTitle;
  
  public String token;
  
  public boolean checkArgs() {
    String str = this.stateId;
    if (str != null && str.length() > 10240) {
      Log.e("MicroMsg.SDK.WXStateSceneDataObject", "checkArgs fail, stateId is invalid");
      return false;
    } 
    str = this.stateTitle;
    if (str != null && str.length() > 10240) {
      Log.e("MicroMsg.SDK.WXStateSceneDataObject", "checkArgs fail, stateId is invalid");
      return false;
    } 
    str = this.token;
    if (str != null && str.length() > 10240) {
      Log.e("MicroMsg.SDK.WXStateSceneDataObject", "checkArgs fail, stateId is invalid");
      return false;
    } 
    IWXStateJumpInfo iWXStateJumpInfo = this.stateJumpInfo;
    if (iWXStateJumpInfo == null) {
      Log.e("MicroMsg.SDK.WXStateSceneDataObject", "checkArgs fail, statsJumpInfo is null");
      return false;
    } 
    return iWXStateJumpInfo.checkArgs();
  }
  
  public int getJumpType() {
    boolean bool;
    IWXStateJumpInfo iWXStateJumpInfo = this.stateJumpInfo;
    if (iWXStateJumpInfo != null) {
      bool = iWXStateJumpInfo.type();
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putString("_wxapi_scene_data_state_id", this.stateId);
    paramBundle.putString("_wxapi_scene_data_state_title", this.stateTitle);
    paramBundle.putString("_wxapi_scene_data_state_token", this.token);
    IWXStateJumpInfo iWXStateJumpInfo = this.stateJumpInfo;
    if (iWXStateJumpInfo != null) {
      paramBundle.putString("_wxapi_scene_data_state_jump_info_identifier", iWXStateJumpInfo.getClass().getName());
      this.stateJumpInfo.serialize(paramBundle);
    } 
  }
  
  public void unserialize(Bundle paramBundle) {
    this.stateId = paramBundle.getString("_wxapi_scene_data_state_id");
    this.stateTitle = paramBundle.getString("_wxapi_scene_data_state_title");
    this.token = paramBundle.getString("_wxapi_scene_data_state_token");
    String str = paramBundle.getString("_wxapi_scene_data_state_jump_info_identifier");
    if (str != null)
      try {
        IWXStateJumpInfo iWXStateJumpInfo = (IWXStateJumpInfo)Class.forName(str).newInstance();
        this.stateJumpInfo = iWXStateJumpInfo;
        iWXStateJumpInfo.unserialize(paramBundle);
      } catch (Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("get WXSceneDataObject from bundle failed: unknown ident ");
        stringBuilder.append(str);
        stringBuilder.append(", ex = ");
        stringBuilder.append(exception.getMessage());
        Log.e("MicroMsg.SDK.WXStateSceneDataObject", stringBuilder.toString());
      }  
  }
  
  public static interface IWXStateJumpInfo {
    public static final int WX_STATE_JUMP_TYPE_CHANNEL_PROFILE = 3;
    
    public static final int WX_STATE_JUMP_TYPE_MINI_PROGRAM = 2;
    
    public static final int WX_STATE_JUMP_TYPE_UNKNOWN = 0;
    
    public static final int WX_STATE_JUMP_TYPE_URL = 1;
    
    boolean checkArgs();
    
    void serialize(Bundle param1Bundle);
    
    int type();
    
    void unserialize(Bundle param1Bundle);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXStateSceneDataObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */