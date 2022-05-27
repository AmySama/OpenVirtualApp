package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;

public class WXEnterpriseCardObject implements WXMediaMessage.IMediaObject {
  private static final int LENGTH_LIMIT = 10240;
  
  private static final String TAG = "MicroMsg.SDK.WXEnterpriseCardObject";
  
  public String cardInfo;
  
  public int msgType;
  
  public boolean checkArgs() {
    String str = this.cardInfo;
    if (str == null || str.length() == 0) {
      Log.e("MicroMsg.SDK.WXEnterpriseCardObject", "checkArgs fail, cardInfo is invalid");
      return false;
    } 
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putInt("_wxenterprisecard_msgtype", this.msgType);
    paramBundle.putString("_wxenterprisecard_cardinfo", this.cardInfo);
  }
  
  public int type() {
    return 45;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.msgType = paramBundle.getInt("_wxenterprisecard_msgtype");
    this.cardInfo = paramBundle.getString("_wxenterprisecard_cardinfo");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXEnterpriseCardObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */