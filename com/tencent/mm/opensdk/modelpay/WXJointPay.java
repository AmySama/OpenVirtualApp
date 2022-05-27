package com.tencent.mm.opensdk.modelpay;

import android.os.Bundle;

public class WXJointPay {
  public static class JointPayReq extends PayReq {
    public boolean checkArgs() {
      return super.checkArgs();
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
    }
    
    public int getType() {
      return 27;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
    }
  }
  
  public static class JointPayResp extends PayResp {
    public JointPayResp() {}
    
    public JointPayResp(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      return super.checkArgs();
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
    }
    
    public int getType() {
      return 27;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelpay\WXJointPay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */