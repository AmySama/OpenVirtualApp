package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import java.net.URLEncoder;

public class HandleScanResult {
  public static class Req extends BaseReq {
    private static final int MAX_URL_LENGHT = 10240;
    
    public String scanResult;
    
    public boolean checkArgs() {
      String str = this.scanResult;
      return (str == null || str.length() < 0) ? false : (!(this.scanResult.length() > 10240));
    }
    
    public int getType() {
      return 17;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_scan_qrcode_result", URLEncoder.encode(this.scanResult));
    }
  }
  
  public static class Resp extends BaseResp {
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
      return 17;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\HandleScanResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */