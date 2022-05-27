package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;

public class WXLaunchWxaRedirectingPage {
  public static final String URI_PATH = "launchWxaOpenApiRedirectingPage";
  
  static interface ConstantsWxaRedirectingPage {
    public static final String KEY_CALLBACK_ACTIVITY = "_launch_wx_wxa_redirecting_page_callback_activity";
    
    public static final String KEY_CALLBACK_MSG = "_launch_wx_wxa_redirecting_page_callback_msg";
    
    public static final String KEY_INVOKE_TICKET = "_launch_wx_wxa_redirecting_page_invoke_ticket";
  }
  
  public static final class Req extends BaseReq {
    private static final String TAG = "MicroMsg.SDK.WXLaunchWxaRedirectingPage.Req";
    
    public String callbackActivity;
    
    public String invokeTicket;
    
    public boolean checkArgs() {
      return TextUtils.isEmpty(this.invokeTicket) ^ true;
    }
    
    public void fromArray(String[] param1ArrayOfString) {
      this.invokeTicket = param1ArrayOfString[0];
      this.callbackActivity = param1ArrayOfString[1];
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.invokeTicket = param1Bundle.getString("_launch_wx_wxa_redirecting_page_invoke_ticket");
      this.callbackActivity = param1Bundle.getString("_launch_wx_wxa_redirecting_page_callback_activity");
    }
    
    public int getType() {
      return 30;
    }
    
    public String[] toArray() {
      return new String[] { this.invokeTicket, this.callbackActivity };
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_launch_wx_wxa_redirecting_page_invoke_ticket", this.invokeTicket);
      param1Bundle.putString("_launch_wx_wxa_redirecting_page_callback_activity", this.callbackActivity);
    }
  }
  
  public static final class Resp extends BaseResp {
    private static final String TAG = "MicroMsg.SDK.WXLaunchWxaFRedirectingPage.Resp";
    
    public String callbackActivity;
    
    public String invokeTicket;
    
    public Resp(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.invokeTicket = param1Bundle.getString("_launch_wx_wxa_redirecting_page_invoke_ticket");
      this.callbackActivity = param1Bundle.getString("_launch_wx_wxa_redirecting_page_callback_activity");
    }
    
    public int getType() {
      return 30;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_launch_wx_wxa_redirecting_page_invoke_ticket", this.invokeTicket);
      param1Bundle.putString("_launch_wx_wxa_redirecting_page_callback_activity", this.callbackActivity);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\WXLaunchWxaRedirectingPage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */