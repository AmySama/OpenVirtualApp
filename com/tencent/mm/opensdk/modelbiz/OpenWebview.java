package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import java.net.URLEncoder;

public class OpenWebview {
  public static class Req extends BaseReq {
    private static final int MAX_URL_LENGHT = 10240;
    
    public String url;
    
    public boolean checkArgs() {
      String str = this.url;
      return (str == null || str.length() < 0) ? false : (!(this.url.length() > 10240));
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.url = param1Bundle.getString("_wxapi_jump_to_webview_url");
    }
    
    public int getType() {
      return 12;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_jump_to_webview_url", URLEncoder.encode(this.url));
    }
  }
  
  public static class Resp extends BaseResp {
    public String result;
    
    public Resp() {}
    
    public Resp(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.result = param1Bundle.getString("_wxapi_open_webview_result");
    }
    
    public int getType() {
      return 12;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_open_webview_result", this.result);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\OpenWebview.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */