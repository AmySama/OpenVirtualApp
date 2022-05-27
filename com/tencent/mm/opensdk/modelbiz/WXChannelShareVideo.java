package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

public class WXChannelShareVideo {
  public static class Req extends BaseReq {
    private static final int LENGTH_LIMIT = 1024;
    
    private static final String TAG = "MicroMsg.SDK.WXChannelShareVideo.Req";
    
    private static final String WX_CHANNEL_SHARE_VIDEO_JUMP_INFO_KEY_IDENTIFIER = "_wxapi_channel_share_video_jump_info_identifier";
    
    public String extData;
    
    public IWXChannelJumpInfo jumpInfo;
    
    public String videoPath;
    
    public boolean checkArgs() {
      if (b.b(this.videoPath)) {
        String str = "videoPath is null";
        Log.e("MicroMsg.SDK.WXChannelShareVideo.Req", str);
        return false;
      } 
      IWXChannelJumpInfo iWXChannelJumpInfo = this.jumpInfo;
      if (iWXChannelJumpInfo != null && !iWXChannelJumpInfo.checkArgs()) {
        String str = "checkArgs fail, jumpInfo is invalid";
        Log.e("MicroMsg.SDK.WXChannelShareVideo.Req", str);
        return false;
      } 
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.videoPath = param1Bundle.getString("_wxapi_finder_share_video_path");
      this.extData = param1Bundle.getString("_wxapi_finder_share_video_extData");
      String str = param1Bundle.getString("_wxapi_channel_share_video_jump_info_identifier");
      if (str != null)
        try {
          IWXChannelJumpInfo iWXChannelJumpInfo = (IWXChannelJumpInfo)Class.forName(str).newInstance();
          this.jumpInfo = iWXChannelJumpInfo;
          iWXChannelJumpInfo.unserialize(param1Bundle);
        } catch (Exception exception) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("get WXChannelJumpInfo from bundle failed: unknown ident ");
          stringBuilder.append(str);
          stringBuilder.append(", ex = ");
          stringBuilder.append(exception.getMessage());
          Log.e("MicroMsg.SDK.WXChannelShareVideo.Req", stringBuilder.toString());
        }  
    }
    
    public int getType() {
      return 33;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_finder_share_video_path", this.videoPath);
      param1Bundle.putString("_wxapi_finder_share_video_extData", this.extData);
      IWXChannelJumpInfo iWXChannelJumpInfo = this.jumpInfo;
      if (iWXChannelJumpInfo != null) {
        param1Bundle.putString("_wxapi_channel_share_video_jump_info_identifier", iWXChannelJumpInfo.getClass().getName());
        this.jumpInfo.serialize(param1Bundle);
      } 
    }
  }
  
  public static class Resp extends BaseResp {
    public String extMsg;
    
    public Resp() {}
    
    public Resp(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.extMsg = param1Bundle.getString("_wxapi_finder_extMsg");
    }
    
    public int getType() {
      return 33;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_finder_extMsg", this.extMsg);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\WXChannelShareVideo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */