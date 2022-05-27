package com.tencent.mm.opensdk.modelpay;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseResp;

public class PayResp extends BaseResp {
  public String extData;
  
  public String prepayId;
  
  public String returnKey;
  
  public PayResp() {}
  
  public PayResp(Bundle paramBundle) {
    fromBundle(paramBundle);
  }
  
  public boolean checkArgs() {
    return true;
  }
  
  public void fromBundle(Bundle paramBundle) {
    super.fromBundle(paramBundle);
    this.prepayId = paramBundle.getString("_wxapi_payresp_prepayid");
    this.returnKey = paramBundle.getString("_wxapi_payresp_returnkey");
    this.extData = paramBundle.getString("_wxapi_payresp_extdata");
  }
  
  public int getType() {
    return 5;
  }
  
  public void toBundle(Bundle paramBundle) {
    super.toBundle(paramBundle);
    paramBundle.putString("_wxapi_payresp_prepayid", this.prepayId);
    paramBundle.putString("_wxapi_payresp_returnkey", this.returnKey);
    paramBundle.putString("_wxapi_payresp_extdata", this.extData);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelpay\PayResp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */