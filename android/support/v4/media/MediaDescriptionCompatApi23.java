package android.support.v4.media;

import android.media.MediaDescription;
import android.net.Uri;

class MediaDescriptionCompatApi23 extends MediaDescriptionCompatApi21 {
  public static Uri getMediaUri(Object paramObject) {
    return ((MediaDescription)paramObject).getMediaUri();
  }
  
  static class Builder extends MediaDescriptionCompatApi21.Builder {
    public static void setMediaUri(Object param1Object, Uri param1Uri) {
      ((MediaDescription.Builder)param1Object).setMediaUri(param1Uri);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\MediaDescriptionCompatApi23.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */