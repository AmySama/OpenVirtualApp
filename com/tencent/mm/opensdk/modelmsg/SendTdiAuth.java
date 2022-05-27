package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;

public class SendTdiAuth {
  public static final class Resp extends SendAuth.Resp {
    private static final String KEY_AUTH_BUFFER = "_wxapi_sendauth_resp_tdi_buffer";
    
    private static final String TAG = "MicroMsg.SDK.SendTdiAuth.Resp";
    
    public byte[] tdiAuthBuffer;
    
    public Resp(Bundle param1Bundle) {
      super(param1Bundle);
    }
    
    public boolean checkArgs() {
      return true;
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.tdiAuthBuffer = param1Bundle.getByteArray("_wxapi_sendauth_resp_tdi_buffer");
    }
    
    public int getType() {
      return 31;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putByteArray("_wxapi_sendauth_resp_tdi_buffer", this.tdiAuthBuffer);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\SendTdiAuth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */