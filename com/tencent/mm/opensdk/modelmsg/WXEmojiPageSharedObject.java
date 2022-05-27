package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

public class WXEmojiPageSharedObject implements WXMediaMessage.IMediaObject {
  private static final String TAG = "MicroMsg.SDK.WXEmojiSharedObject";
  
  public String desc;
  
  public String iconUrl;
  
  public int pageType;
  
  public String secondUrl;
  
  public int tid;
  
  public String title;
  
  public int type;
  
  public String url;
  
  public WXEmojiPageSharedObject() {}
  
  public WXEmojiPageSharedObject(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt3, String paramString5) {
    this.tid = paramInt2;
    this.title = paramString1;
    this.desc = paramString2;
    this.iconUrl = paramString3;
    this.secondUrl = paramString4;
    this.pageType = paramInt3;
    this.url = paramString5;
    this.type = paramInt1;
  }
  
  public boolean checkArgs() {
    if (b.b(this.title) || b.b(this.iconUrl)) {
      Log.e("MicroMsg.SDK.WXEmojiSharedObject", "checkArgs fail, title or iconUrl is invalid");
      return false;
    } 
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putInt("_wxemojisharedobject_tid", this.tid);
    paramBundle.putString("_wxemojisharedobject_title", this.title);
    paramBundle.putString("_wxemojisharedobject_desc", this.desc);
    paramBundle.putString("_wxemojisharedobject_iconurl", this.iconUrl);
    paramBundle.putString("_wxemojisharedobject_secondurl", this.secondUrl);
    paramBundle.putInt("_wxemojisharedobject_pagetype", this.pageType);
    paramBundle.putString("_wxwebpageobject_url", this.url);
  }
  
  public int type() {
    return this.type;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.tid = paramBundle.getInt("_wxemojisharedobject_tid");
    this.title = paramBundle.getString("_wxemojisharedobject_title");
    this.desc = paramBundle.getString("_wxemojisharedobject_desc");
    this.iconUrl = paramBundle.getString("_wxemojisharedobject_iconurl");
    this.secondUrl = paramBundle.getString("_wxemojisharedobject_secondurl");
    this.pageType = paramBundle.getInt("_wxemojisharedobject_pagetype");
    this.url = paramBundle.getString("_wxwebpageobject_url");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXEmojiPageSharedObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */