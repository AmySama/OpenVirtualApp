package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;
import java.io.Serializable;
import java.util.HashMap;

public class WXWebpageObject implements WXMediaMessage.IMediaObject {
  private static final int LENGTH_LIMIT = 10240;
  
  private static final String TAG = "MicroMsg.SDK.WXWebpageObject";
  
  public String canvasPageXml;
  
  public String extInfo;
  
  public HashMap<String, String> extraInfoMap = null;
  
  public boolean isSecretMessage = false;
  
  public String webpageUrl;
  
  public WXWebpageObject() {}
  
  public WXWebpageObject(String paramString) {
    this.webpageUrl = paramString;
  }
  
  public boolean checkArgs() {
    String str = this.webpageUrl;
    if (str == null || str.length() == 0 || this.webpageUrl.length() > 10240) {
      Log.e("MicroMsg.SDK.WXWebpageObject", "checkArgs fail, webpageUrl is invalid");
      return false;
    } 
    return true;
  }
  
  public String getExtra(String paramString1, String paramString2) {
    HashMap<String, String> hashMap = this.extraInfoMap;
    if (hashMap != null) {
      paramString1 = hashMap.get(paramString1);
      if (paramString1 != null)
        paramString2 = paramString1; 
      return paramString2;
    } 
    return null;
  }
  
  public void putExtra(String paramString1, String paramString2) {
    if (this.extraInfoMap == null)
      this.extraInfoMap = new HashMap<String, String>(); 
    if (!b.b(paramString1))
      this.extraInfoMap.put(paramString1, paramString2); 
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putString("_wxwebpageobject_extInfo", this.extInfo);
    paramBundle.putString("_wxwebpageobject_webpageUrl", this.webpageUrl);
    paramBundle.putString("_wxwebpageobject_canvaspagexml", this.canvasPageXml);
    paramBundle.putBoolean("_wxwebpageobject_issecretmsg", this.isSecretMessage);
    HashMap<String, String> hashMap = this.extraInfoMap;
    if (hashMap != null)
      paramBundle.putSerializable("_wxwebpageobject_extrainfo", hashMap); 
  }
  
  public int type() {
    return 5;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.extInfo = paramBundle.getString("_wxwebpageobject_extInfo");
    this.webpageUrl = paramBundle.getString("_wxwebpageobject_webpageUrl");
    this.canvasPageXml = paramBundle.getString("_wxwebpageobject_canvaspagexml");
    this.isSecretMessage = paramBundle.getBoolean("_wxwebpageobject_issecretmsg");
    Serializable serializable = paramBundle.getSerializable("_wxwebpageobject_extrainfo");
    if (serializable != null)
      this.extraInfoMap = (HashMap<String, String>)serializable; 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXWebpageObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */