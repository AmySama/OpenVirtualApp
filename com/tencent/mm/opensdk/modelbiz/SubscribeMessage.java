package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.Log;

public final class SubscribeMessage {
  public static class Req extends BaseReq {
    private static final int LENGTH_LIMIT = 1024;
    
    private static final String TAG = "MicroMsg.SDK.SubscribeMessage.Req";
    
    public String reserved;
    
    public int scene;
    
    public String templateID;
    
    public Req() {}
    
    public Req(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      String str = this.templateID;
      if (str == null || str.length() == 0) {
        str = "checkArgs fail, templateID is null";
        Log.e("MicroMsg.SDK.SubscribeMessage.Req", str);
        return false;
      } 
      if (this.templateID.length() > 1024) {
        str = "checkArgs fail, templateID is too long";
        Log.e("MicroMsg.SDK.SubscribeMessage.Req", str);
        return false;
      } 
      str = this.reserved;
      if (str != null && str.length() > 1024) {
        str = "checkArgs fail, reserved is too long";
        Log.e("MicroMsg.SDK.SubscribeMessage.Req", str);
        return false;
      } 
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.scene = param1Bundle.getInt("_wxapi_subscribemessage_req_scene");
      this.templateID = param1Bundle.getString("_wxapi_subscribemessage_req_templateid");
      this.reserved = param1Bundle.getString("_wxapi_subscribemessage_req_reserved");
    }
    
    public int getType() {
      return 18;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putInt("_wxapi_subscribemessage_req_scene", this.scene);
      param1Bundle.putString("_wxapi_subscribemessage_req_templateid", this.templateID);
      param1Bundle.putString("_wxapi_subscribemessage_req_reserved", this.reserved);
    }
  }
  
  public static class Resp extends BaseResp {
    private static final String TAG = "MicroMsg.SDK.SubscribeMessage.Resp";
    
    public String action;
    
    public String reserved;
    
    public int scene;
    
    public String templateID;
    
    public Resp() {}
    
    public Resp(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.templateID = param1Bundle.getString("_wxapi_subscribemessage_resp_templateid");
      this.scene = param1Bundle.getInt("_wxapi_subscribemessage_resp_scene");
      this.action = param1Bundle.getString("_wxapi_subscribemessage_resp_action");
      this.reserved = param1Bundle.getString("_wxapi_subscribemessage_resp_reserved");
    }
    
    public int getType() {
      return 18;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_subscribemessage_resp_templateid", this.templateID);
      param1Bundle.putInt("_wxapi_subscribemessage_resp_scene", this.scene);
      param1Bundle.putString("_wxapi_subscribemessage_resp_action", this.action);
      param1Bundle.putString("_wxapi_subscribemessage_resp_reserved", this.reserved);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\SubscribeMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */