package android.support.v4.media;

import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Parcel;
import android.service.media.MediaBrowserService;

class MediaBrowserServiceCompatApi23 {
  public static Object createService(Context paramContext, ServiceCompatProxy paramServiceCompatProxy) {
    return new MediaBrowserServiceAdaptor(paramContext, paramServiceCompatProxy);
  }
  
  static class MediaBrowserServiceAdaptor extends MediaBrowserServiceCompatApi21.MediaBrowserServiceAdaptor {
    MediaBrowserServiceAdaptor(Context param1Context, MediaBrowserServiceCompatApi23.ServiceCompatProxy param1ServiceCompatProxy) {
      super(param1Context, param1ServiceCompatProxy);
    }
    
    public void onLoadItem(String param1String, MediaBrowserService.Result<MediaBrowser.MediaItem> param1Result) {
      ((MediaBrowserServiceCompatApi23.ServiceCompatProxy)this.mServiceProxy).onLoadItem(param1String, new MediaBrowserServiceCompatApi21.ResultWrapper<Parcel>(param1Result));
    }
  }
  
  public static interface ServiceCompatProxy extends MediaBrowserServiceCompatApi21.ServiceCompatProxy {
    void onLoadItem(String param1String, MediaBrowserServiceCompatApi21.ResultWrapper<Parcel> param1ResultWrapper);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\MediaBrowserServiceCompatApi23.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */