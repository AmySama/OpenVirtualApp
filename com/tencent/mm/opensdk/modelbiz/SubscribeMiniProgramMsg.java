package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.Log;

public final class SubscribeMiniProgramMsg {
  public static class Req extends BaseReq {
    private static final int LENGTH_LIMIT = 1024;
    
    private static final String TAG = "MicroMsg.SDK.SubscribeMessage.Req";
    
    public String miniProgramAppId;
    
    public Req() {}
    
    public Req(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      String str = this.miniProgramAppId;
      if (str == null || str.length() == 0) {
        Log.e("MicroMsg.SDK.SubscribeMessage.Req", "checkArgs fail, miniProgramAppId is null");
        return false;
      } 
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.miniProgramAppId = param1Bundle.getString("_wxapi_subscribeminiprogram_req_miniprogramappid");
    }
    
    public int getType() {
      return 23;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_subscribeminiprogram_req_miniprogramappid", this.miniProgramAppId);
    }
  }
  
  public static class Resp extends BaseResp {
    private static final String TAG = "MicroMsg.SDK.SubscribeMessage.Resp";
    
    public String nickname;
    
    public String unionId;
    
    public Resp() {}
    
    public Resp(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.unionId = param1Bundle.getString("_wxapi_subscribeminiprogram_resp_unionId");
      this.nickname = param1Bundle.getString("_wxapi_subscribeminiprogram_resp_nickname");
    }
    
    public int getType() {
      return 23;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_subscribeminiprogram_resp_unionId", this.unionId);
      param1Bundle.putString("_wxapi_subscribeminiprogram_resp_nickname", this.nickname);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\SubscribeMiniProgramMsg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */