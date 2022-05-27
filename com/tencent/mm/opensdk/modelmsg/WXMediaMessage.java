package com.tencent.mm.opensdk.modelmsg;

import android.graphics.Bitmap;
import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;
import java.io.ByteArrayOutputStream;

public final class WXMediaMessage {
  public static final String ACTION_WXAPPMESSAGE = "com.tencent.mm.sdk.openapi.Intent.ACTION_WXAPPMESSAGE";
  
  public static final int DESCRIPTION_LENGTH_LIMIT = 1024;
  
  public static final int MEDIA_TAG_NAME_LENGTH_LIMIT = 64;
  
  public static final int MESSAGE_ACTION_LENGTH_LIMIT = 2048;
  
  public static final int MESSAGE_EXT_LENGTH_LIMIT = 2048;
  
  public static final int MINI_PROGRAM__THUMB_LENGHT = 131072;
  
  private static final String TAG = "MicroMsg.SDK.WXMediaMessage";
  
  public static final int THUMB_LENGTH_LIMIT = 65536;
  
  public static final int TITLE_LENGTH_LIMIT = 512;
  
  public String description;
  
  public IMediaObject mediaObject;
  
  public String mediaTagName;
  
  public String messageAction;
  
  public String messageExt;
  
  public int sdkVer;
  
  public byte[] thumbData;
  
  public String title;
  
  public WXMediaMessage() {
    this(null);
  }
  
  public WXMediaMessage(IMediaObject paramIMediaObject) {
    this.mediaObject = paramIMediaObject;
  }
  
  boolean checkArgs() {
    if (getType() == 8) {
      byte[] arrayOfByte = this.thumbData;
      if (arrayOfByte == null || arrayOfByte.length == 0) {
        String str1 = "checkArgs fail, thumbData should not be null when send emoji";
        Log.e("MicroMsg.SDK.WXMediaMessage", str1);
        return false;
      } 
    } 
    if (getType() == 76 && b.b(this.title)) {
      String str1 = "checkArgs fail, Type = Music Video, but title == null";
      Log.e("MicroMsg.SDK.WXMediaMessage", str1);
      return false;
    } 
    if (b.a(getType())) {
      byte[] arrayOfByte = this.thumbData;
      if (arrayOfByte == null || arrayOfByte.length > 131072) {
        String str1 = "checkArgs fail, thumbData should not be null or exceed 128kb";
        Log.e("MicroMsg.SDK.WXMediaMessage", str1);
        return false;
      } 
    } 
    if (!b.a(getType())) {
      byte[] arrayOfByte = this.thumbData;
      if (arrayOfByte != null && arrayOfByte.length > 65536) {
        String str1 = "checkArgs fail, thumbData is invalid";
        Log.e("MicroMsg.SDK.WXMediaMessage", str1);
        return false;
      } 
    } 
    String str = this.title;
    if (str != null && str.length() > 512) {
      str = "checkArgs fail, title is invalid";
      Log.e("MicroMsg.SDK.WXMediaMessage", str);
      return false;
    } 
    str = this.description;
    if (str != null && str.length() > 1024) {
      str = "checkArgs fail, description is invalid";
      Log.e("MicroMsg.SDK.WXMediaMessage", str);
      return false;
    } 
    if (this.mediaObject == null) {
      str = "checkArgs fail, mediaObject is null";
      Log.e("MicroMsg.SDK.WXMediaMessage", str);
      return false;
    } 
    str = this.mediaTagName;
    if (str != null && str.length() > 64) {
      str = "checkArgs fail, mediaTagName is too long";
      Log.e("MicroMsg.SDK.WXMediaMessage", str);
      return false;
    } 
    str = this.messageAction;
    if (str != null && str.length() > 2048) {
      str = "checkArgs fail, messageAction is too long";
      Log.e("MicroMsg.SDK.WXMediaMessage", str);
      return false;
    } 
    str = this.messageExt;
    if (str != null && str.length() > 2048) {
      str = "checkArgs fail, messageExt is too long";
      Log.e("MicroMsg.SDK.WXMediaMessage", str);
      return false;
    } 
    return this.mediaObject.checkArgs();
  }
  
  public int getType() {
    IMediaObject iMediaObject = this.mediaObject;
    return (iMediaObject == null) ? 0 : iMediaObject.type();
  }
  
  public void setThumbImage(Bitmap paramBitmap) {
    try {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      this();
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, 85, byteArrayOutputStream);
      this.thumbData = byteArrayOutputStream.toByteArray();
      byteArrayOutputStream.close();
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("setThumbImage exception:");
      stringBuilder.append(exception.getMessage());
      Log.e("MicroMsg.SDK.WXMediaMessage", stringBuilder.toString());
    } 
  }
  
  public static class Builder {
    public static final String KEY_IDENTIFIER = "_wxobject_identifier_";
    
    public static WXMediaMessage fromBundle(Bundle param1Bundle) {
      WXMediaMessage wXMediaMessage = new WXMediaMessage();
      wXMediaMessage.sdkVer = param1Bundle.getInt("_wxobject_sdkVer");
      wXMediaMessage.title = param1Bundle.getString("_wxobject_title");
      wXMediaMessage.description = param1Bundle.getString("_wxobject_description");
      wXMediaMessage.thumbData = param1Bundle.getByteArray("_wxobject_thumbdata");
      wXMediaMessage.mediaTagName = param1Bundle.getString("_wxobject_mediatagname");
      wXMediaMessage.messageAction = param1Bundle.getString("_wxobject_message_action");
      wXMediaMessage.messageExt = param1Bundle.getString("_wxobject_message_ext");
      String str = pathOldToNew(param1Bundle.getString("_wxobject_identifier_"));
      if (str != null && str.length() > 0)
        try {
          WXMediaMessage.IMediaObject iMediaObject = (WXMediaMessage.IMediaObject)Class.forName(str).newInstance();
          wXMediaMessage.mediaObject = iMediaObject;
          iMediaObject.unserialize(param1Bundle);
          return wXMediaMessage;
        } catch (Exception exception) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("get media object from bundle failed: unknown ident ");
          stringBuilder.append(str);
          stringBuilder.append(", ex = ");
          stringBuilder.append(exception.getMessage());
          Log.e("MicroMsg.SDK.WXMediaMessage", stringBuilder.toString());
        }  
      return wXMediaMessage;
    }
    
    private static String pathNewToOld(String param1String) {
      if (param1String == null || param1String.length() == 0) {
        Log.e("MicroMsg.SDK.WXMediaMessage", "pathNewToOld fail, newPath is null");
        return param1String;
      } 
      return param1String.replace("com.tencent.mm.opensdk.modelmsg", "com.tencent.mm.sdk.openapi");
    }
    
    private static String pathOldToNew(String param1String) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("pathOldToNew, oldPath = ");
      stringBuilder.append(param1String);
      Log.i("MicroMsg.SDK.WXMediaMessage", stringBuilder.toString());
      if (param1String == null || param1String.length() == 0) {
        Log.e("MicroMsg.SDK.WXMediaMessage", "pathOldToNew fail, oldPath is null");
        return param1String;
      } 
      int i = param1String.lastIndexOf('.');
      if (i == -1) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("pathOldToNew fail, invalid pos, oldPath = ");
        stringBuilder.append(param1String);
        Log.e("MicroMsg.SDK.WXMediaMessage", stringBuilder.toString());
        return param1String;
      } 
      stringBuilder = new StringBuilder();
      stringBuilder.append("com.tencent.mm.opensdk.modelmsg");
      stringBuilder.append(param1String.substring(i));
      return stringBuilder.toString();
    }
    
    public static Bundle toBundle(WXMediaMessage param1WXMediaMessage) {
      Bundle bundle = new Bundle();
      bundle.putInt("_wxobject_sdkVer", param1WXMediaMessage.sdkVer);
      bundle.putString("_wxobject_title", param1WXMediaMessage.title);
      bundle.putString("_wxobject_description", param1WXMediaMessage.description);
      bundle.putByteArray("_wxobject_thumbdata", param1WXMediaMessage.thumbData);
      WXMediaMessage.IMediaObject iMediaObject = param1WXMediaMessage.mediaObject;
      if (iMediaObject != null) {
        bundle.putString("_wxobject_identifier_", pathNewToOld(iMediaObject.getClass().getName()));
        param1WXMediaMessage.mediaObject.serialize(bundle);
      } 
      bundle.putString("_wxobject_mediatagname", param1WXMediaMessage.mediaTagName);
      bundle.putString("_wxobject_message_action", param1WXMediaMessage.messageAction);
      bundle.putString("_wxobject_message_ext", param1WXMediaMessage.messageExt);
      return bundle;
    }
  }
  
  public static interface IMediaObject {
    public static final int TYPE_APPBRAND = 33;
    
    public static final int TYPE_APPDATA = 7;
    
    public static final int TYPE_BUSINESS_CARD = 45;
    
    public static final int TYPE_CARD_SHARE = 16;
    
    public static final int TYPE_DESIGNER_SHARED = 25;
    
    public static final int TYPE_DEVICE_ACCESS = 12;
    
    public static final int TYPE_EMOJI = 8;
    
    public static final int TYPE_EMOJILIST_SHARED = 27;
    
    public static final int TYPE_EMOTICON_GIFT = 11;
    
    public static final int TYPE_EMOTICON_SHARED = 15;
    
    public static final int TYPE_EMOTIONLIST_SHARED = 26;
    
    public static final int TYPE_FILE = 6;
    
    public static final int TYPE_GAME_LIVE = 70;
    
    public static final int TYPE_GAME_VIDEO_FILE = 39;
    
    public static final int TYPE_GIFTCARD = 34;
    
    public static final int TYPE_IMAGE = 2;
    
    public static final int TYPE_LOCATION = 30;
    
    public static final int TYPE_LOCATION_SHARE = 17;
    
    public static final int TYPE_MALL_PRODUCT = 13;
    
    public static final int TYPE_MUSIC = 3;
    
    public static final int TYPE_MUSIC_VIDEO = 76;
    
    public static final int TYPE_NOTE = 24;
    
    public static final int TYPE_OLD_TV = 14;
    
    public static final int TYPE_OPENSDK_APPBRAND = 36;
    
    public static final int TYPE_OPENSDK_APPBRAND_WEISHIVIDEO = 46;
    
    public static final int TYPE_OPENSDK_LITEAPP = 68;
    
    public static final int TYPE_OPENSDK_WEWORK_OBJECT = 49;
    
    public static final int TYPE_PRODUCT = 10;
    
    public static final int TYPE_RECORD = 19;
    
    public static final int TYPE_TEXT = 1;
    
    public static final int TYPE_TV = 20;
    
    public static final int TYPE_UNKNOWN = 0;
    
    public static final int TYPE_URL = 5;
    
    public static final int TYPE_VIDEO = 4;
    
    public static final int TYPE_VIDEO_FILE = 38;
    
    boolean checkArgs();
    
    void serialize(Bundle param1Bundle);
    
    int type();
    
    void unserialize(Bundle param1Bundle);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXMediaMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */