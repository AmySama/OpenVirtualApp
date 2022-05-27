package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.Log;

public class SendMessageToWX {
  public static interface IWXSceneDataObject {
    boolean checkArgs();
    
    int getJumpType();
    
    void serialize(Bundle param1Bundle);
    
    void unserialize(Bundle param1Bundle);
  }
  
  public static class Req extends BaseReq {
    private static final int FAV_CONTENT_LENGTH_LIMIT = 26214400;
    
    private static final String SCENE_DATA_OBJECT_KEY_IDENTIFIER = "_scene_data_object_identifier";
    
    private static final String TAG = "MicroMsg.SDK.SendMessageToWX.Req";
    
    public static final int WXSceneFavorite = 2;
    
    public static final int WXSceneSession = 0;
    
    public static final int WXSceneSpecifiedContact = 3;
    
    public static final int WXSceneStatus = 4;
    
    public static final int WXSceneTimeline = 1;
    
    public WXMediaMessage message;
    
    public int scene;
    
    public SendMessageToWX.IWXSceneDataObject sceneDataObject;
    
    public String userOpenId;
    
    public Req() {}
    
    public Req(Bundle param1Bundle) {
      fromBundle(param1Bundle);
    }
    
    public boolean checkArgs() {
      String str;
      WXMediaMessage wXMediaMessage = this.message;
      boolean bool = false;
      if (wXMediaMessage == null) {
        str = "checkArgs fail ,message is null";
        Log.e("MicroMsg.SDK.SendMessageToWX.Req", str);
        return false;
      } 
      if (((WXMediaMessage)str).mediaObject.type() == 6 && this.scene == 2)
        ((WXFileObject)this.message.mediaObject).setContentLengthLimit(26214400); 
      if (this.scene == 3 && this.userOpenId == null) {
        str = "Send specifiedContact userOpenId can not be null.";
        Log.e("MicroMsg.SDK.SendMessageToWX.Req", str);
        return false;
      } 
      if (this.scene == 3 && this.openId == null) {
        str = "Send specifiedContact openid can not be null.";
        Log.e("MicroMsg.SDK.SendMessageToWX.Req", str);
        return false;
      } 
      if (this.scene == 4) {
        if (this.sceneDataObject == null) {
          str = "checkArgs fail, sceneDataObject is null";
          Log.e("MicroMsg.SDK.SendMessageToWX.Req", str);
          return false;
        } 
        if (this.message.getType() == 1)
          return this.sceneDataObject.checkArgs(); 
        boolean bool1 = bool;
        if (this.message.checkArgs()) {
          bool1 = bool;
          if (this.sceneDataObject.checkArgs())
            bool1 = true; 
        } 
        return bool1;
      } 
      return this.message.checkArgs();
    }
    
    public void fromBundle(Bundle param1Bundle) {
      super.fromBundle(param1Bundle);
      this.message = WXMediaMessage.Builder.fromBundle(param1Bundle);
      this.scene = param1Bundle.getInt("_wxapi_sendmessagetowx_req_scene");
      this.userOpenId = param1Bundle.getString("_wxapi_sendmessagetowx_req_use_open_id");
      String str = param1Bundle.getString("_scene_data_object_identifier");
      if (str != null)
        try {
          SendMessageToWX.IWXSceneDataObject iWXSceneDataObject = (SendMessageToWX.IWXSceneDataObject)Class.forName(str).newInstance();
          this.sceneDataObject = iWXSceneDataObject;
          iWXSceneDataObject.unserialize(param1Bundle);
        } catch (Exception exception) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("get WXSceneDataObject from bundle failed: unknown ident ");
          stringBuilder.append(str);
          stringBuilder.append(", ex = ");
          stringBuilder.append(exception.getMessage());
          Log.e("MicroMsg.SDK.SendMessageToWX.Req", stringBuilder.toString());
        }  
    }
    
    public int getType() {
      return 2;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
      param1Bundle.putAll(WXMediaMessage.Builder.toBundle(this.message));
      param1Bundle.putInt("_wxapi_sendmessagetowx_req_scene", this.scene);
      param1Bundle.putInt("_wxapi_sendmessagetowx_req_media_type", this.message.getType());
      param1Bundle.putString("_wxapi_sendmessagetowx_req_use_open_id", this.userOpenId);
      SendMessageToWX.IWXSceneDataObject iWXSceneDataObject = this.sceneDataObject;
      if (iWXSceneDataObject != null) {
        param1Bundle.putString("_scene_data_object_identifier", iWXSceneDataObject.getClass().getName());
        this.sceneDataObject.serialize(param1Bundle);
      } 
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
      return 2;
    }
    
    public void toBundle(Bundle param1Bundle) {
      super.toBundle(param1Bundle);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\SendMessageToWX.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */