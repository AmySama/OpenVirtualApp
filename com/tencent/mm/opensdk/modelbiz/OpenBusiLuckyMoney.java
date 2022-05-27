package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;

public class OpenBusiLuckyMoney {
  public static class Req extends BaseReq {
    private static final int MAX_URL_LENGHT = 10240;
    
    public String appId;
    
    public String nonceStr;
    
    public String packageExt;
    
    public String signType;
    
    public String signature;
    
    public String timeStamp;
    
    public boolean checkArgs() {
      String str = this.appId;
      if (str != null && str.length() > 0) {
        str = this.timeStamp;
        if (str != null && str.length() > 0) {
          str = this.nonceStr;
          if (str != null && str.length() > 0) {
            str = this.signType;
            if (str != null && str.length() > 0) {
              str = this.signature;
              if (str != null && str.length() > 0)
                return true; 
            } 
          } 
        } 
      } 
      return false;
    }
    
    public int getType() {
      return 13;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_open_busi_lucky_money_appid", this.appId);
      param1Bundle.putString("_wxapi_open_busi_lucky_money_timeStamp", this.timeStamp);
      param1Bundle.putString("_wxapi_open_busi_lucky_money_nonceStr", this.nonceStr);
      param1Bundle.putString("_wxapi_open_busi_lucky_money_signType", this.signType);
      param1Bundle.putString("_wxapi_open_busi_lucky_money_signature", this.signature);
      param1Bundle.putString("_wxapi_open_busi_lucky_money_package", this.packageExt);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\OpenBusiLuckyMoney.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */