package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.Log;

public class ChooseCardFromWXCardPackage {
  private static final String TAG = "MicroMsg.ChooseCardFromWXCardPackage";
  
  public static class Req extends BaseReq {
    public String appId;
    
    public String canMultiSelect;
    
    public String cardId;
    
    public String cardSign;
    
    public String cardType;
    
    public String locationId;
    
    public String nonceStr;
    
    public String signType;
    
    public String timeStamp;
    
    public boolean checkArgs() {
      String str = this.appId;
      if (str != null && str.length() > 0) {
        str = this.signType;
        if (str != null && str.length() > 0) {
          str = this.cardSign;
          if (str != null && str.length() > 0)
            return true; 
        } 
      } 
      return false;
    }
    
    public int getType() {
      return 16;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_choose_card_from_wx_card_app_id", this.appId);
      param1Bundle.putString("_wxapi_choose_card_from_wx_card_location_id", this.locationId);
      param1Bundle.putString("_wxapi_choose_card_from_wx_card_sign_type", this.signType);
      param1Bundle.putString("_wxapi_choose_card_from_wx_card_card_sign", this.cardSign);
      param1Bundle.putString("_wxapi_choose_card_from_wx_card_time_stamp", this.timeStamp);
      param1Bundle.putString("_wxapi_choose_card_from_wx_card_nonce_str", this.nonceStr);
      param1Bundle.putString("_wxapi_choose_card_from_wx_card_card_id", this.cardId);
      param1Bundle.putString("_wxapi_choose_card_from_wx_card_card_type", this.cardType);
      param1Bundle.putString("_wxapi_choose_card_from_wx_card_can_multi_select", this.canMultiSelect);
    }
  }
  
  public static class Resp extends BaseResp {
    public String cardItemList;
    
    public Resp() {}
    
    public Resp(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      String str = this.cardItemList;
      return !(str == null || str.length() == 0);
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      String str = param1Bundle.getString("_wxapi_choose_card_from_wx_card_list");
      if (str != null && str.length() > 0) {
        this.cardItemList = str;
      } else {
        Log.i("MicroMsg.ChooseCardFromWXCardPackage", "cardItemList is empty!");
      } 
    }
    
    public int getType() {
      return 16;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_choose_card_from_wx_card_list", this.cardItemList);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\ChooseCardFromWXCardPackage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */