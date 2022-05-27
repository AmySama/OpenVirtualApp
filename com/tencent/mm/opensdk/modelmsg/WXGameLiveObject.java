package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.b;
import java.util.HashMap;

public class WXGameLiveObject implements WXMediaMessage.IMediaObject {
  private static final String TAG = "MicroMsg.SDK.WXGameObject";
  
  public HashMap<String, String> extraInfoMap = new HashMap<String, String>();
  
  public boolean checkArgs() {
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
    HashMap<String, String> hashMap = this.extraInfoMap;
    if (hashMap != null)
      paramBundle.putSerializable("_wxgame_extrainfo", hashMap); 
  }
  
  public int type() {
    return 70;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.extraInfoMap = (HashMap<String, String>)paramBundle.getSerializable("_wxgame_extrainfo");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXGameLiveObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */