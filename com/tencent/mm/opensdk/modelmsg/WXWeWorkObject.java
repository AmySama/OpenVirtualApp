package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;

public class WXWeWorkObject implements WXMediaMessage.IMediaObject {
  public static final int SUB_TYPE_MSG_RECORD = 1;
  
  private static final String TAG = "MicroMsg.SDK.WXWeWorkObject";
  
  public byte[] data;
  
  public String extInfo;
  
  public int subType;
  
  public boolean checkArgs() {
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putByteArray("_wxenterprise_object_data", this.data);
    paramBundle.putString("_wxenterprise_object_extinfo", this.extInfo);
    paramBundle.putInt("_wxenterprise_object_subType", this.subType);
  }
  
  public int type() {
    return 49;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.data = paramBundle.getByteArray("_wxenterprise_object_data");
    this.extInfo = paramBundle.getString("_wxenterprise_object_extinfo");
    this.subType = paramBundle.getInt("_wxenterprise_object_subType");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXWeWorkObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */