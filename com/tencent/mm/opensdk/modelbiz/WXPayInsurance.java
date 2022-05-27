package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

public class WXPayInsurance {
  public static final class Req extends BaseReq {
    private static final String TAG = "MicroMsg.SDK.WXPayInsurance.Req";
    
    private static final int URL_LENGTH_LIMIT = 10240;
    
    public String url;
    
    public boolean checkArgs() {
      if (b.b(this.url)) {
        Log.i("MicroMsg.SDK.WXPayInsurance.Req", "url should not be empty");
        return false;
      } 
      if (this.url.length() > 10240) {
        Log.e("MicroMsg.SDK.WXPayInsurance.Req", "url must be in 10k");
        return false;
      } 
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.url = param1Bundle.getString("_wxapi_pay_insourance_req_url");
    }
    
    public int getType() {
      return 22;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      param1Bundle.putString("_wxapi_pay_insourance_req_url", this.url);
    }
  }
  
  public static final class Resp extends BaseResp {
    public String wxOrderId;
    
    public boolean checkArgs() {
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.wxOrderId = param1Bundle.getString("_wxapi_pay_insourance_order_id");
    }
    
    public int getType() {
      return 22;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      param1Bundle.putString("_wxapi_pay_insourance_order_id", this.wxOrderId);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\WXPayInsurance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */