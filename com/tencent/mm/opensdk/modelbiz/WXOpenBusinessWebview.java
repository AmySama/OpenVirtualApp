package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import java.util.HashMap;

public class WXOpenBusinessWebview {
  public static class Req extends BaseReq {
    public int businessType;
    
    public HashMap<String, String> queryInfo;
    
    public boolean checkArgs() {
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.queryInfo = (HashMap<String, String>)param1Bundle.getSerializable("_wxapi_open_business_webview_query_info");
      this.businessType = param1Bundle.getInt("_wxapi_open_business_webview_query_type", 0);
    }
    
    public int getType() {
      return 25;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putSerializable("_wxapi_open_business_webview_query_info", this.queryInfo);
      param1Bundle.putInt("_wxapi_open_business_webview_query_type", this.businessType);
    }
  }
  
  public static class Resp extends BaseResp {
    public int businessType;
    
    public String resultInfo;
    
    public Resp() {}
    
    public Resp(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.resultInfo = param1Bundle.getString("_wxapi_open_business_webview_result");
      this.businessType = param1Bundle.getInt("_wxapi_open_business_webview_query_type", 0);
    }
    
    public int getType() {
      return 25;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_open_business_webview_result", this.resultInfo);
      param1Bundle.putInt("_wxapi_open_business_webview_query_type", this.businessType);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\WXOpenBusinessWebview.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */