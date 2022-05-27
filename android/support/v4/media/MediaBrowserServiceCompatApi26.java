package android.support.v4.media;

import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.os.Parcel;
import android.service.media.MediaBrowserService;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class MediaBrowserServiceCompatApi26 {
  private static final String TAG = "MBSCompatApi26";
  
  private static Field sResultFlags;
  
  static {
    try {
      Field field = MediaBrowserService.Result.class.getDeclaredField("mFlags");
      sResultFlags = field;
      field.setAccessible(true);
    } catch (NoSuchFieldException noSuchFieldException) {
      Log.w("MBSCompatApi26", noSuchFieldException);
    } 
  }
  
  public static Object createService(Context paramContext, ServiceCompatProxy paramServiceCompatProxy) {
    return new MediaBrowserServiceAdaptor(paramContext, paramServiceCompatProxy);
  }
  
  public static Bundle getBrowserRootHints(Object paramObject) {
    return ((MediaBrowserService)paramObject).getBrowserRootHints();
  }
  
  public static void notifyChildrenChanged(Object paramObject, String paramString, Bundle paramBundle) {
    ((MediaBrowserService)paramObject).notifyChildrenChanged(paramString, paramBundle);
  }
  
  static class MediaBrowserServiceAdaptor extends MediaBrowserServiceCompatApi23.MediaBrowserServiceAdaptor {
    MediaBrowserServiceAdaptor(Context param1Context, MediaBrowserServiceCompatApi26.ServiceCompatProxy param1ServiceCompatProxy) {
      super(param1Context, param1ServiceCompatProxy);
    }
    
    public void onLoadChildren(String param1String, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> param1Result, Bundle param1Bundle) {
      ((MediaBrowserServiceCompatApi26.ServiceCompatProxy)this.mServiceProxy).onLoadChildren(param1String, new MediaBrowserServiceCompatApi26.ResultWrapper(param1Result), param1Bundle);
    }
  }
  
  static class ResultWrapper {
    MediaBrowserService.Result mResultObj;
    
    ResultWrapper(MediaBrowserService.Result param1Result) {
      this.mResultObj = param1Result;
    }
    
    public void detach() {
      this.mResultObj.detach();
    }
    
    List<MediaBrowser.MediaItem> parcelListToItemList(List<Parcel> param1List) {
      if (param1List == null)
        return null; 
      ArrayList<Object> arrayList = new ArrayList();
      for (Parcel parcel : param1List) {
        parcel.setDataPosition(0);
        arrayList.add(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
        parcel.recycle();
      } 
      return arrayList;
    }
    
    public void sendResult(List<Parcel> param1List, int param1Int) {
      try {
        MediaBrowserServiceCompatApi26.sResultFlags.setInt(this.mResultObj, param1Int);
      } catch (IllegalAccessException illegalAccessException) {
        Log.w("MBSCompatApi26", illegalAccessException);
      } 
      this.mResultObj.sendResult(parcelListToItemList(param1List));
    }
  }
  
  public static interface ServiceCompatProxy extends MediaBrowserServiceCompatApi23.ServiceCompatProxy {
    void onLoadChildren(String param1String, MediaBrowserServiceCompatApi26.ResultWrapper param1ResultWrapper, Bundle param1Bundle);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\MediaBrowserServiceCompatApi26.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */