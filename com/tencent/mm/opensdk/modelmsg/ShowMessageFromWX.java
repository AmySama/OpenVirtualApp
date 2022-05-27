package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;

public class ShowMessageFromWX {
  public static class Req extends BaseReq {
    public String country;
    
    public String lang;
    
    public WXMediaMessage message;
    
    public Req() {}
    
    public Req(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      WXMediaMessage wXMediaMessage = this.message;
      return (wXMediaMessage == null) ? false : wXMediaMessage.checkArgs();
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.lang = param1Bundle.getString("_wxapi_showmessage_req_lang");
      this.country = param1Bundle.getString("_wxapi_showmessage_req_country");
      this.message = WXMediaMessage.Builder.fromBundle(param1Bundle);
    }
    
    public int getType() {
      return 4;
    }
    
    public void toBundle(Bundle param1Bundle) {
      Bundle bundle = WXMediaMessage.Builder.toBundle(this.message);
      super.toBundle(bundle);
      param1Bundle.putString("_wxapi_showmessage_req_lang", this.lang);
      param1Bundle.putString("_wxapi_showmessage_req_country", this.country);
      param1Bundle.putAll(bundle);
    }
  }
  
  public static class Resp extends BaseResp {
    public Resp() {}
    
    public Resp(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      return true;
    }
    
    public int getType() {
      return 4;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\ShowMessageFromWX.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */