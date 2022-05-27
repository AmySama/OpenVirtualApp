package com.tencent.mm.opensdk.modelbase;

import android.os.Bundle;
import com.tencent.mm.opensdk.channel.a.a;

public abstract class BaseReq {
  public String openId;
  
  public String transaction;
  
  public abstract boolean checkArgs();
  
  public void fromBundle(Bundle paramBundle) {
    this.transaction = a.a(paramBundle, "_wxapi_basereq_transaction");
    this.openId = a.a(paramBundle, "_wxapi_basereq_openid");
  }
  
  public abstract int getType();
  
  public void toBundle(Bundle paramBundle) {
    paramBundle.putInt("_wxapi_command_type", getType());
    paramBundle.putString("_wxapi_basereq_transaction", this.transaction);
    paramBundle.putString("_wxapi_basereq_openid", this.openId);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbase\BaseReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */