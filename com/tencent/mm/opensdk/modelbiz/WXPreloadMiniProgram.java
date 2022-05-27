package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

public class WXPreloadMiniProgram {
  public static final class Req extends BaseReq {
    private static final String TAG = "MicroMsg.SDK.WXPreloadMiniProgram.Req";
    
    public String extData = "";
    
    public int miniprogramType = 0;
    
    public String path = "";
    
    public String userName;
    
    public boolean checkArgs() {
      if (b.b(this.userName)) {
        String str = "userName is null";
        Log.e("MicroMsg.SDK.WXPreloadMiniProgram.Req", str);
        return false;
      } 
      int i = this.miniprogramType;
      if (i < 0 || i > 2) {
        String str = "miniprogram type should between MINIPTOGRAM_TYPE_RELEASE and MINIPROGRAM_TYPE_PREVIEW";
        Log.e("MicroMsg.SDK.WXPreloadMiniProgram.Req", str);
        return false;
      } 
      return true;
    }
    
    public int getType() {
      return 28;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_launch_wxminiprogram_username", this.userName);
      param1Bundle.putString("_launch_wxminiprogram_path", this.path);
      param1Bundle.putString("_launch_wxminiprogram_extData", this.extData);
      param1Bundle.putInt("_launch_wxminiprogram_type", this.miniprogramType);
    }
  }
  
  public static final class Resp extends BaseResp {
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
      return 28;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_launch_wxminiprogram_ext_msg", this.extMsg);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\WXPreloadMiniProgram.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */