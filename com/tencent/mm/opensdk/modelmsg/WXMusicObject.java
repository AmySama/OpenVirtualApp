package com.tencent.mm.opensdk.modelmsg;

import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;

public class WXMusicObject implements WXMediaMessage.IMediaObject {
  private static final int LENGTH_LIMIT = 10240;
  
  private static final int LYRIC_LENGTH_LIMIT = 32768;
  
  private static final String TAG = "MicroMsg.SDK.WXMusicObject";
  
  public String musicDataUrl;
  
  public String musicLowBandDataUrl;
  
  public String musicLowBandUrl;
  
  public String musicUrl;
  
  public String songAlbumUrl;
  
  public String songLyric;
  
  public boolean checkArgs() {
    String str = this.musicUrl;
    if (str == null || str.length() == 0) {
      str = this.musicLowBandUrl;
      if (str == null || str.length() == 0) {
        str = "both arguments are null";
        Log.e("MicroMsg.SDK.WXMusicObject", str);
        return false;
      } 
    } 
    str = this.musicUrl;
    if (str != null && str.length() > 10240) {
      str = "checkArgs fail, musicUrl is too long";
      Log.e("MicroMsg.SDK.WXMusicObject", str);
      return false;
    } 
    str = this.musicLowBandUrl;
    if (str != null && str.length() > 10240) {
      str = "checkArgs fail, musicLowBandUrl is too long";
      Log.e("MicroMsg.SDK.WXMusicObject", str);
      return false;
    } 
    str = this.songAlbumUrl;
    if (str != null && str.length() > 10240) {
      str = "checkArgs fail, songAlbumUrl is too long";
      Log.e("MicroMsg.SDK.WXMusicObject", str);
      return false;
    } 
    str = this.songLyric;
    if (str != null && str.length() > 32768) {
      str = "checkArgs fail, songLyric is too long";
      Log.e("MicroMsg.SDK.WXMusicObject", str);
      return false;
    } 
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putString("_wxmusicobject_musicUrl", this.musicUrl);
    paramBundle.putString("_wxmusicobject_musicLowBandUrl", this.musicLowBandUrl);
    paramBundle.putString("_wxmusicobject_musicDataUrl", this.musicDataUrl);
    paramBundle.putString("_wxmusicobject_musicLowBandDataUrl", this.musicLowBandDataUrl);
    paramBundle.putString("_wxmusicobject_musicAlbumUrl", this.songAlbumUrl);
    paramBundle.putString("_wxmusicobject_musicLyric", this.songLyric);
  }
  
  public int type() {
    return 3;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.musicUrl = paramBundle.getString("_wxmusicobject_musicUrl");
    this.musicLowBandUrl = paramBundle.getString("_wxmusicobject_musicLowBandUrl");
    this.musicDataUrl = paramBundle.getString("_wxmusicobject_musicDataUrl");
    this.musicLowBandDataUrl = paramBundle.getString("_wxmusicobject_musicLowBandDataUrl");
    this.songAlbumUrl = paramBundle.getString("_wxmusicobject_musicAlbumUrl");
    this.songLyric = paramBundle.getString("_wxmusicobject_musicLyric");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXMusicObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */