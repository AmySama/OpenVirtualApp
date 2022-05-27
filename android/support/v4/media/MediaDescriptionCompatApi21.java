package android.support.v4.media;

import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;

class MediaDescriptionCompatApi21 {
  public static Object fromParcel(Parcel paramParcel) {
    return MediaDescription.CREATOR.createFromParcel(paramParcel);
  }
  
  public static CharSequence getDescription(Object paramObject) {
    return ((MediaDescription)paramObject).getDescription();
  }
  
  public static Bundle getExtras(Object paramObject) {
    return ((MediaDescription)paramObject).getExtras();
  }
  
  public static Bitmap getIconBitmap(Object paramObject) {
    return ((MediaDescription)paramObject).getIconBitmap();
  }
  
  public static Uri getIconUri(Object paramObject) {
    return ((MediaDescription)paramObject).getIconUri();
  }
  
  public static String getMediaId(Object paramObject) {
    return ((MediaDescription)paramObject).getMediaId();
  }
  
  public static CharSequence getSubtitle(Object paramObject) {
    return ((MediaDescription)paramObject).getSubtitle();
  }
  
  public static CharSequence getTitle(Object paramObject) {
    return ((MediaDescription)paramObject).getTitle();
  }
  
  public static void writeToParcel(Object paramObject, Parcel paramParcel, int paramInt) {
    ((MediaDescription)paramObject).writeToParcel(paramParcel, paramInt);
  }
  
  static class Builder {
    public static Object build(Object param1Object) {
      return ((MediaDescription.Builder)param1Object).build();
    }
    
    public static Object newInstance() {
      return new MediaDescription.Builder();
    }
    
    public static void setDescription(Object param1Object, CharSequence param1CharSequence) {
      ((MediaDescription.Builder)param1Object).setDescription(param1CharSequence);
    }
    
    public static void setExtras(Object param1Object, Bundle param1Bundle) {
      ((MediaDescription.Builder)param1Object).setExtras(param1Bundle);
    }
    
    public static void setIconBitmap(Object param1Object, Bitmap param1Bitmap) {
      ((MediaDescription.Builder)param1Object).setIconBitmap(param1Bitmap);
    }
    
    public static void setIconUri(Object param1Object, Uri param1Uri) {
      ((MediaDescription.Builder)param1Object).setIconUri(param1Uri);
    }
    
    public static void setMediaId(Object param1Object, String param1String) {
      ((MediaDescription.Builder)param1Object).setMediaId(param1String);
    }
    
    public static void setSubtitle(Object param1Object, CharSequence param1CharSequence) {
      ((MediaDescription.Builder)param1Object).setSubtitle(param1CharSequence);
    }
    
    public static void setTitle(Object param1Object, CharSequence param1CharSequence) {
      ((MediaDescription.Builder)param1Object).setTitle(param1CharSequence);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\MediaDescriptionCompatApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */