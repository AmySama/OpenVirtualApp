package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.Log;

public final class GetMessageFromWX {
  public static class Req extends BaseReq {
    public String country;
    
    public String lang;
    
    public String username;
    
    public Req() {}
    
    public Req(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.lang = param1Bundle.getString("_wxapi_getmessage_req_lang");
      this.country = param1Bundle.getString("_wxapi_getmessage_req_country");
    }
    
    public int getType() {
      return 3;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_getmessage_req_lang", this.lang);
      param1Bundle.putString("_wxapi_getmessage_req_country", this.country);
    }
  }
  
  public static class Resp extends BaseResp {
    private static final String TAG = "MicroMsg.SDK.GetMessageFromWX.Resp";
    
    public WXMediaMessage message;
    
    public Resp() {}
    
    public Resp(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      WXMediaMessage wXMediaMessage = this.message;
      if (wXMediaMessage == null) {
        Log.e("MicroMsg.SDK.GetMessageFromWX.Resp", "checkArgs fail, message is null");
        return false;
      } 
      return wXMediaMessage.checkArgs();
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.message = WXMediaMessage.Builder.fromBundle(param1Bundle);
    }
    
    public int getType() {
      return 3;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putAll(WXMediaMessage.Builder.toBundle(this.message));
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\GetMessageFromWX.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */