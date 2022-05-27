package android.support.v4.media;

import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.service.media.MediaBrowserService;
import java.util.ArrayList;
import java.util.List;

class MediaBrowserServiceCompatApi21 {
  public static Object createService(Context paramContext, ServiceCompatProxy paramServiceCompatProxy) {
    return new MediaBrowserServiceAdaptor(paramContext, paramServiceCompatProxy);
  }
  
  public static void notifyChildrenChanged(Object paramObject, String paramString) {
    ((MediaBrowserService)paramObject).notifyChildrenChanged(paramString);
  }
  
  public static IBinder onBind(Object paramObject, Intent paramIntent) {
    return ((MediaBrowserService)paramObject).onBind(paramIntent);
  }
  
  public static void onCreate(Object paramObject) {
    ((MediaBrowserService)paramObject).onCreate();
  }
  
  public static void setSessionToken(Object paramObject1, Object paramObject2) {
    ((MediaBrowserService)paramObject1).setSessionToken((MediaSession.Token)paramObject2);
  }
  
  static class BrowserRoot {
    final Bundle mExtras;
    
    final String mRootId;
    
    BrowserRoot(String param1String, Bundle param1Bundle) {
      this.mRootId = param1String;
      this.mExtras = param1Bundle;
    }
  }
  
  static class MediaBrowserServiceAdaptor extends MediaBrowserService {
    final MediaBrowserServiceCompatApi21.ServiceCompatProxy mServiceProxy;
    
    MediaBrowserServiceAdaptor(Context param1Context, MediaBrowserServiceCompatApi21.ServiceCompatProxy param1ServiceCompatProxy) {
      attachBaseContext(param1Context);
      this.mServiceProxy = param1ServiceCompatProxy;
    }
    
    public MediaBrowserService.BrowserRoot onGetRoot(String param1String, int param1Int, Bundle param1Bundle) {
      MediaBrowserService.BrowserRoot browserRoot;
      MediaBrowserServiceCompatApi21.ServiceCompatProxy serviceCompatProxy = this.mServiceProxy;
      MediaBrowserServiceCompatApi21.BrowserRoot browserRoot2 = null;
      if (param1Bundle == null) {
        param1Bundle = null;
      } else {
        param1Bundle = new Bundle(param1Bundle);
      } 
      MediaBrowserServiceCompatApi21.BrowserRoot browserRoot1 = serviceCompatProxy.onGetRoot(param1String, param1Int, param1Bundle);
      if (browserRoot1 == null) {
        browserRoot1 = browserRoot2;
      } else {
        browserRoot = new MediaBrowserService.BrowserRoot(browserRoot1.mRootId, browserRoot1.mExtras);
      } 
      return browserRoot;
    }
    
    public void onLoadChildren(String param1String, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> param1Result) {
      this.mServiceProxy.onLoadChildren(param1String, new MediaBrowserServiceCompatApi21.ResultWrapper<List<Parcel>>(param1Result));
    }
  }
  
  static class ResultWrapper<T> {
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
    
    public void sendResult(T param1T) {
      if (param1T instanceof List) {
        this.mResultObj.sendResult(parcelListToItemList((List<Parcel>)param1T));
      } else if (param1T instanceof Parcel) {
        Parcel parcel = (Parcel)param1T;
        parcel.setDataPosition(0);
        this.mResultObj.sendResult(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
        parcel.recycle();
      } else {
        this.mResultObj.sendResult(null);
      } 
    }
  }
  
  public static interface ServiceCompatProxy {
    MediaBrowserServiceCompatApi21.BrowserRoot onGetRoot(String param1String, int param1Int, Bundle param1Bundle);
    
    void onLoadChildren(String param1String, MediaBrowserServiceCompatApi21.ResultWrapper<List<Parcel>> param1ResultWrapper);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\MediaBrowserServiceCompatApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */