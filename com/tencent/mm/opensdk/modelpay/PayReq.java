package com.tencent.mm.opensdk.modelpay;

import android.os.Bundle;
import com.tencent.mm.opensdk.channel.a.a;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.utils.Log;

public class PayReq extends BaseReq {
  private static final int EXTDATA_MAX_LENGTH = 1024;
  
  private static final String TAG = "MicroMsg.PaySdk.PayReq";
  
  public String appId;
  
  public String extData;
  
  public String nonceStr;
  
  public Options options;
  
  public String packageValue;
  
  public String partnerId;
  
  public String prepayId;
  
  public String sign;
  
  public String signType;
  
  public String timeStamp;
  
  public boolean checkArgs() {
    String str = this.appId;
    if (str == null || str.length() == 0) {
      str = "checkArgs fail, invalid appId";
      Log.e("MicroMsg.PaySdk.PayReq", str);
      return false;
    } 
    str = this.partnerId;
    if (str == null || str.length() == 0) {
      str = "checkArgs fail, invalid partnerId";
      Log.e("MicroMsg.PaySdk.PayReq", str);
      return false;
    } 
    str = this.prepayId;
    if (str == null || str.length() == 0) {
      str = "checkArgs fail, invalid prepayId";
      Log.e("MicroMsg.PaySdk.PayReq", str);
      return false;
    } 
    str = this.nonceStr;
    if (str == null || str.length() == 0) {
      str = "checkArgs fail, invalid nonceStr";
      Log.e("MicroMsg.PaySdk.PayReq", str);
      return false;
    } 
    str = this.timeStamp;
    if (str == null || str.length() == 0) {
      str = "checkArgs fail, invalid timeStamp";
      Log.e("MicroMsg.PaySdk.PayReq", str);
      return false;
    } 
    str = this.packageValue;
    if (str == null || str.length() == 0) {
      str = "checkArgs fail, invalid packageValue";
      Log.e("MicroMsg.PaySdk.PayReq", str);
      return false;
    } 
    str = this.sign;
    if (str == null || str.length() == 0) {
      str = "checkArgs fail, invalid sign";
      Log.e("MicroMsg.PaySdk.PayReq", str);
      return false;
    } 
    str = this.extData;
    if (str != null && str.length() > 1024) {
      str = "checkArgs fail, extData length too long";
      Log.e("MicroMsg.PaySdk.PayReq", str);
      return false;
    } 
    return true;
  }
  
  public void fromBundle(Bundle paramBundle) {
    super.fromBundle(paramBundle);
    this.appId = a.a(paramBundle, "_wxapi_payreq_appid");
    this.partnerId = a.a(paramBundle, "_wxapi_payreq_partnerid");
    this.prepayId = a.a(paramBundle, "_wxapi_payreq_prepayid");
    this.nonceStr = a.a(paramBundle, "_wxapi_payreq_noncestr");
    this.timeStamp = a.a(paramBundle, "_wxapi_payreq_timestamp");
    this.packageValue = a.a(paramBundle, "_wxapi_payreq_packagevalue");
    this.sign = a.a(paramBundle, "_wxapi_payreq_sign");
    this.extData = a.a(paramBundle, "_wxapi_payreq_extdata");
    this.signType = a.a(paramBundle, "_wxapi_payreq_sign_type");
    Options options = new Options();
    this.options = options;
    options.fromBundle(paramBundle);
  }
  
  public int getType() {
    return 5;
  }
  
  public void toBundle(Bundle paramBundle) {
    super.toBundle(paramBundle);
    paramBundle.putString("_wxapi_payreq_appid", this.appId);
    paramBundle.putString("_wxapi_payreq_partnerid", this.partnerId);
    paramBundle.putString("_wxapi_payreq_prepayid", this.prepayId);
    paramBundle.putString("_wxapi_payreq_noncestr", this.nonceStr);
    paramBundle.putString("_wxapi_payreq_timestamp", this.timeStamp);
    paramBundle.putString("_wxapi_payreq_packagevalue", this.packageValue);
    paramBundle.putString("_wxapi_payreq_sign", this.sign);
    paramBundle.putString("_wxapi_payreq_extdata", this.extData);
    paramBundle.putString("_wxapi_payreq_sign_type", this.signType);
    Options options = this.options;
    if (options != null)
      options.toBundle(paramBundle); 
  }
  
  public static class Options {
    public static final int INVALID_FLAGS = -1;
    
    public String callbackClassName;
    
    public int callbackFlags = -1;
    
    public void fromBundle(Bundle param1Bundle) {
      this.callbackClassName = a.a(param1Bundle, "_wxapi_payoptions_callback_classname");
      this.callbackFlags = a.a(param1Bundle, "_wxapi_payoptions_callback_flags", -1);
    }
    
    public void toBundle(Bundle param1Bundle) {
      param1Bundle.putString("_wxapi_payoptions_callback_classname", this.callbackClassName);
      param1Bundle.putInt("_wxapi_payoptions_callback_flags", this.callbackFlags);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelpay\PayReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */