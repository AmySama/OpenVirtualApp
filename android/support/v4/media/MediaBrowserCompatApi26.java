package android.support.v4.media;

import android.media.browse.MediaBrowser;
import android.os.Bundle;
import java.util.List;

class MediaBrowserCompatApi26 {
  static Object createSubscriptionCallback(SubscriptionCallback paramSubscriptionCallback) {
    return new SubscriptionCallbackProxy<SubscriptionCallback>(paramSubscriptionCallback);
  }
  
  public static void subscribe(Object paramObject1, String paramString, Bundle paramBundle, Object paramObject2) {
    ((MediaBrowser)paramObject1).subscribe(paramString, paramBundle, (MediaBrowser.SubscriptionCallback)paramObject2);
  }
  
  public static void unsubscribe(Object paramObject1, String paramString, Object paramObject2) {
    ((MediaBrowser)paramObject1).unsubscribe(paramString, (MediaBrowser.SubscriptionCallback)paramObject2);
  }
  
  static interface SubscriptionCallback extends MediaBrowserCompatApi21.SubscriptionCallback {
    void onChildrenLoaded(String param1String, List<?> param1List, Bundle param1Bundle);
    
    void onError(String param1String, Bundle param1Bundle);
  }
  
  static class SubscriptionCallbackProxy<T extends SubscriptionCallback> extends MediaBrowserCompatApi21.SubscriptionCallbackProxy<T> {
    SubscriptionCallbackProxy(T param1T) {
      super(param1T);
    }
    
    public void onChildrenLoaded(String param1String, List<MediaBrowser.MediaItem> param1List, Bundle param1Bundle) {
      ((MediaBrowserCompatApi26.SubscriptionCallback)this.mSubscriptionCallback).onChildrenLoaded(param1String, param1List, param1Bundle);
    }
    
    public void onError(String param1String, Bundle param1Bundle) {
      ((MediaBrowserCompatApi26.SubscriptionCallback)this.mSubscriptionCallback).onError(param1String, param1Bundle);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\MediaBrowserCompatApi26.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */