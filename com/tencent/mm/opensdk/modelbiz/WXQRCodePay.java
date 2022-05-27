package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;

public class WXQRCodePay {
  public static class Req extends BaseReq {
    private static final String TAG = "MicroMsg.SDK.WXQRCodePayReq.Req";
    
    public String codeContent;
    
    public String extraMsg;
    
    public boolean checkArgs() {
      return TextUtils.isEmpty(this.codeContent) ^ true;
    }
    
    public int getType() {
      return 38;
    }
  }
  
  public static final class Resp extends BaseResp {
    public Resp() {}
    
    public Resp(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
    }
    
    public int getType() {
      return 38;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\WXQRCodePay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */