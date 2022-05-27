package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.channel.a.a;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.Log;

public final class SendAuth {
  public static class Options {
    public static final int INVALID_FLAGS = -1;
    
    public String callbackClassName;
    
    public int callbackFlags = -1;
    
    public void fromBundle(Bundle param1Bundle) {
      this.callbackClassName = a.a(param1Bundle, "_wxapi_sendauth_options_callback_classname");
      this.callbackFlags = a.a(param1Bundle, "_wxapi_sendauth_options_callback_flags", -1);
    }
    
    public void toBundle(Bundle param1Bundle) {
      param1Bundle.putString("_wxapi_sendauth_options_callback_classname", this.callbackClassName);
      param1Bundle.putInt("_wxapi_sendauth_options_callback_flags", this.callbackFlags);
    }
  }
  
  public static class Req extends BaseReq {
    private static final int LENGTH_LIMIT = 1024;
    
    private static final String TAG = "MicroMsg.SDK.SendAuth.Req";
    
    public String extData;
    
    public SendAuth.Options options;
    
    public String scope;
    
    public String state;
    
    public Req() {}
    
    public Req(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      String str = this.scope;
      if (str == null || str.length() == 0 || this.scope.length() > 1024) {
        str = "checkArgs fail, scope is invalid";
        Log.e("MicroMsg.SDK.SendAuth.Req", str);
        return false;
      } 
      str = this.state;
      if (str != null && str.length() > 1024) {
        str = "checkArgs fail, state is invalid";
        Log.e("MicroMsg.SDK.SendAuth.Req", str);
        return false;
      } 
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.scope = param1Bundle.getString("_wxapi_sendauth_req_scope");
      this.state = param1Bundle.getString("_wxapi_sendauth_req_state");
      this.extData = param1Bundle.getString("_wxapi_sendauth_req_ext_data");
      SendAuth.Options options = new SendAuth.Options();
      this.options = options;
      options.fromBundle(param1Bundle);
    }
    
    public int getType() {
      return 1;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_sendauth_req_scope", this.scope);
      param1Bundle.putString("_wxapi_sendauth_req_state", this.state);
      param1Bundle.putString("_wxapi_sendauth_req_ext_data", this.extData);
      SendAuth.Options options = this.options;
      if (options != null)
        options.toBundle(param1Bundle); 
    }
  }
  
  public static class Resp extends BaseResp {
    public static final int ERR_SCOPE_SNSAPI_WXAAPP_INFO_CAN_ONLY_AUTHORIZED_SEPARATELY = -1000;
    
    private static final int LENGTH_LIMIT = 1024;
    
    private static final String TAG = "MicroMsg.SDK.SendAuth.Resp";
    
    public boolean authResult = false;
    
    public String code;
    
    public String country;
    
    public String lang;
    
    public String state;
    
    public String url;
    
    public Resp() {}
    
    public Resp(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      String str = this.state;
      if (str != null && str.length() > 1024) {
        Log.e("MicroMsg.SDK.SendAuth.Resp", "checkArgs fail, state is invalid");
        return false;
      } 
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.code = param1Bundle.getString("_wxapi_sendauth_resp_token");
      this.state = param1Bundle.getString("_wxapi_sendauth_resp_state");
      this.url = param1Bundle.getString("_wxapi_sendauth_resp_url");
      this.lang = param1Bundle.getString("_wxapi_sendauth_resp_lang");
      this.country = param1Bundle.getString("_wxapi_sendauth_resp_country");
      this.authResult = param1Bundle.getBoolean("_wxapi_sendauth_resp_auth_result");
    }
    
    public int getType() {
      return 1;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_sendauth_resp_token", this.code);
      param1Bundle.putString("_wxapi_sendauth_resp_state", this.state);
      param1Bundle.putString("_wxapi_sendauth_resp_url", this.url);
      param1Bundle.putString("_wxapi_sendauth_resp_lang", this.lang);
      param1Bundle.putString("_wxapi_sendauth_resp_country", this.country);
      param1Bundle.putBoolean("_wxapi_sendauth_resp_auth_result", this.authResult);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\SendAuth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */