package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

public class WXLaunchMiniProgramWithToken {
  public static final class Req extends BaseReq {
    private static final String TAG = "MicroMsg.SDK.WXLaunchMiniProgramWithToken.Req";
    
    public String token;
    
    public boolean checkArgs() {
      if (b.b(this.token)) {
        Log.e("MicroMsg.SDK.WXLaunchMiniProgramWithToken.Req", "token is null");
        return false;
      } 
      return true;
    }
    
    public int getType() {
      return 29;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_launch_wxminiprogram_token", this.token);
    }
  }
  
  public static final class Resp extends BaseResp {
    public static final int ERR_INVALID_TOKEN = -1000;
    
    public String extMsg;
    
    public Resp() {}
    
    public Resp(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.extMsg = param1Bundle.getString("_launch_wxminiprogram_ext_msg");
    }
    
    public int getType() {
      return 29;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_launch_wxminiprogram_ext_msg", this.extMsg);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\WXLaunchMiniProgramWithToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */