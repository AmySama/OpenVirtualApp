package com.tencent.mm.opensdk.modelbiz;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.b;

public class JoinChatroom {
  public static class Req extends BaseReq {
    public String chatroomNickName;
    
    public String extMsg;
    
    public String groupId = "";
    
    public boolean checkArgs() {
      return !b.b(this.groupId);
    }
    
    public int getType() {
      return 15;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_join_chatroom_group_id", this.groupId);
      param1Bundle.putString("_wxapi_join_chatroom_chatroom_nickname", this.chatroomNickName);
      param1Bundle.putString("_wxapi_join_chatroom_ext_msg", this.extMsg);
      param1Bundle.putString("_wxapi_basereq_openid", this.openId);
    }
  }
  
  public static class Resp extends BaseResp {
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
      this.extMsg = param1Bundle.getString("_wxapi_join_chatroom_ext_msg");
    }
    
    public int getType() {
      return 15;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putString("_wxapi_join_chatroom_ext_msg", this.extMsg);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelbiz\JoinChatroom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */