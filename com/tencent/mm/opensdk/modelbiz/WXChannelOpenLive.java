package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

public class WXChannelOpenLive {
  public static class Req extends BaseReq {
    private static final int LENGTH_LIMIT = 1024;
    
    private static final String TAG = "MicroMsg.SDK.WXChannelOpenLive.Req";
    
    public String feedID;
    
    public String nonceID;
    
    public boolean checkArgs() {
      if (b.b(this.feedID)) {
        String str = "feedID is null";
        Log.e("MicroMsg.SDK.WXChannelOpenLive.Req", str);
        return false;
      } 
      if (b.b(this.nonceID)) {
        String str = "nonceID is null";
        Log.e("MicroMsg.SDK.WXChannelOpenLive.Req", str);
        return false;
      } 
      if (this.feedID.length() > 1024) {
        String str = "feedID.length too long!";
        Log.e("MicroMsg.SDK.WXChannelOpenLive.Req", str);
        return false;
      } 
      if (this.nonceID.length() > 1024) {
        String str = "nonceID.length too long!";
        Log.e("MicroMsg.SDK.WXChannelOpenLive.Req", str);
        return false;
      } 
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.feedID = param1Bundle.getString("_wxapi_finder_feedID");
      this.nonceID = param1Bundle.getString("_wxapi_finder_nonceID");
    }
    
    public int getType() {
      return 35;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_finder_feedID", this.feedID);
      param1Bundle.putString("_wxapi_finder_nonceID", this.nonceID);
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
      return 35;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_finder_extMsg", this.extMsg);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\WXChannelOpenLive.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */