package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

public class WXOpenBusinessView {
  public static final class Req extends BaseReq {
    private static final String TAG = "MicroMsg.SDK.WXOpenBusinessView.Req";
    
    public String businessType;
    
    public String extInfo;
    
    public String query;
    
    public boolean checkArgs() {
      if (b.b(this.businessType)) {
        Log.e("MicroMsg.SDK.WXOpenBusinessView.Req", "businessType is null");
        return false;
      } 
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.businessType = param1Bundle.getString("_openbusinessview_businessType");
      this.query = param1Bundle.getString("_openbusinessview__query_info");
      this.extInfo = param1Bundle.getString("_openbusinessview_extInfo");
    }
    
    public int getType() {
      return 26;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_openbusinessview_businessType", this.businessType);
      param1Bundle.putString("_openbusinessview__query_info", this.query);
      param1Bundle.putString("_openbusinessview_extInfo", this.extInfo);
    }
  }
  
  public static final class Resp extends BaseResp {
    public String businessType;
    
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
      this.extMsg = param1Bundle.getString("_openbusinessview_ext_msg");
      this.businessType = param1Bundle.getString("_openbusinessview_business_type");
    }
    
    public int getType() {
      return 26;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_openbusinessview_ext_msg", this.extMsg);
      param1Bundle.putString("_openbusinessview_business_type", this.businessType);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\WXOpenBusinessView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */