package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

public class WXChannelOpenProfile {
  public static class Req extends BaseReq {
    private static final int LENGTH_LIMIT = 1024;
    
    private static final String TAG = "MicroMsg.SDK.WXChannelOpenProfile.Req";
    
    public String userName;
    
    public boolean checkArgs() {
      if (b.b(this.userName)) {
        String str = "userName is null";
        Log.e("MicroMsg.SDK.WXChannelOpenProfile.Req", str);
        return false;
      } 
      if (this.userName.length() > 1024) {
        String str = "userName.length too long";
        Log.e("MicroMsg.SDK.WXChannelOpenProfile.Req", str);
        return false;
      } 
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.userName = param1Bundle.getString("_wxapi_finder_userName");
    }
    
    public int getType() {
      return 34;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_finder_userName", this.userName);
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
      return 34;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_finder_extMsg", this.extMsg);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\WXChannelOpenProfile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */