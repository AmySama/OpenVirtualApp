package android.support.v4.media;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.Rating;
import android.os.Parcel;
import java.util.Set;

class MediaMetadataCompatApi21 {
  public static Object createFromParcel(Parcel paramParcel) {
    return MediaMetadata.CREATOR.createFromParcel(paramParcel);
  }
  
  public static Bitmap getBitmap(Object paramObject, String paramString) {
    return ((MediaMetadata)paramObject).getBitmap(paramString);
  }
  
  public static long getLong(Object paramObject, String paramString) {
    return ((MediaMetadata)paramObject).getLong(paramString);
  }
  
  public static Object getRating(Object paramObject, String paramString) {
    return ((MediaMetadata)paramObject).getRating(paramString);
  }
  
  public static CharSequence getText(Object paramObject, String paramString) {
    return ((MediaMetadata)paramObject).getText(paramString);
  }
  
  public static Set<String> keySet(Object paramObject) {
    return ((MediaMetadata)paramObject).keySet();
  }
  
  public static void writeToParcel(Object paramObject, Parcel paramParcel, int paramInt) {
    ((MediaMetadata)paramObject).writeToParcel(paramParcel, paramInt);
  }
  
  public static class Builder {
    public static Object build(Object param1Object) {
      return ((MediaMetadata.Builder)param1Object).build();
    }
    
    public static Object newInstance() {
      return new MediaMetadata.Builder();
    }
    
    public static void putBitmap(Object param1Object, String param1String, Bitmap param1Bitmap) {
      ((MediaMetadata.Builder)param1Object).putBitmap(param1String, param1Bitmap);
    }
    
    public static void putLong(Object param1Object, String param1String, long param1Long) {
      ((MediaMetadata.Builder)param1Object).putLong(param1String, param1Long);
    }
    
    public static void putRating(Object param1Object1, String param1String, Object param1Object2) {
      ((MediaMetadata.Builder)param1Object1).putRating(param1String, (Rating)param1Object2);
    }
    
    public static void putString(Object param1Object, String param1String1, String param1String2) {
      ((MediaMetadata.Builder)param1Object).putString(param1String1, param1String2);
    }
    
    public static void putText(Object param1Object, String param1String, CharSequence param1CharSequence) {
      ((MediaMetadata.Builder)param1Object).putText(param1String, param1CharSequence);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\MediaMetadataCompatApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */