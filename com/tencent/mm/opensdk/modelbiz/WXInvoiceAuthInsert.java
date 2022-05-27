package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

public class WXInvoiceAuthInsert {
  public static final class Req extends BaseReq {
    private static final String TAG = "MicroMsg.SDK.WXInvoiceAuthInsert.Req";
    
    private static final int URL_LENGTH_LIMIT = 10240;
    
    public String url;
    
    public boolean checkArgs() {
      if (b.b(this.url)) {
        Log.i("MicroMsg.SDK.WXInvoiceAuthInsert.Req", "url should not be empty");
        return false;
      } 
      if (this.url.length() > 10240) {
        Log.e("MicroMsg.SDK.WXInvoiceAuthInsert.Req", "url must be in 10k");
        return false;
      } 
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.url = param1Bundle.getString("_wxapi_invoice_auth_insert_req_url");
    }
    
    public int getType() {
      return 20;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      param1Bundle.putString("_wxapi_invoice_auth_insert_req_url", this.url);
    }
  }
  
  public static final class Resp extends BaseResp {
    public String wxOrderId;
    
    public boolean checkArgs() {
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.wxOrderId = param1Bundle.getString("_wxapi_invoice_auth_insert_order_id");
    }
    
    public int getType() {
      return 20;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      param1Bundle.putString("_wxapi_invoice_auth_insert_order_id", this.wxOrderId);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\WXInvoiceAuthInsert.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */