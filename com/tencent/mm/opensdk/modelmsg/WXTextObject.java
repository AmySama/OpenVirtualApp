package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;

public class WXTextObject implements WXMediaMessage.IMediaObject {
  private static final int LENGTH_LIMIT = 10240;
  
  private static final String TAG = "MicroMsg.SDK.WXTextObject";
  
  public String text;
  
  public WXTextObject() {
    this(null);
  }
  
  public WXTextObject(String paramString) {
    this.text = paramString;
  }
  
  public boolean checkArgs() {
    String str = this.text;
    if (str == null || str.length() == 0 || this.text.length() > 10240) {
      Log.e("MicroMsg.SDK.WXTextObject", "checkArgs fail, text is invalid");
      return false;
    } 
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putString("_wxtextobject_text", this.text);
  }
  
  public int type() {
    return 1;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.text = paramBundle.getString("_wxtextobject_text");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXTextObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */