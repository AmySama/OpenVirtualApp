package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import java.util.List;

class MediaBrowserCompatApi21 {
  static final String NULL_MEDIA_ITEM_ID = "android.support.v4.media.MediaBrowserCompat.NULL_MEDIA_ITEM";
  
  public static void connect(Object paramObject) {
    ((MediaBrowser)paramObject).connect();
  }
  
  public static Object createBrowser(Context paramContext, ComponentName paramComponentName, Object paramObject, Bundle paramBundle) {
    return new MediaBrowser(paramContext, paramComponentName, (MediaBrowser.ConnectionCallback)paramObject, paramBundle);
  }
  
  public static Object createConnectionCallback(ConnectionCallback paramConnectionCallback) {
    return new ConnectionCallbackProxy<ConnectionCallback>(paramConnectionCallback);
  }
  
  public static Object createSubscriptionCallback(SubscriptionCallback paramSubscriptionCallback) {
    return new SubscriptionCallbackProxy<SubscriptionCallback>(paramSubscriptionCallback);
  }
  
  public static void disconnect(Object paramObject) {
    ((MediaBrowser)paramObject).disconnect();
  }
  
  public static Bundle getExtras(Object paramObject) {
    return ((MediaBrowser)paramObject).getExtras();
  }
  
  public static String getRoot(Object paramObject) {
    return ((MediaBrowser)paramObject).getRoot();
  }
  
  public static ComponentName getServiceComponent(Object paramObject) {
    return ((MediaBrowser)paramObject).getServiceComponent();
  }
  
  public static Object getSessionToken(Object paramObject) {
    return ((MediaBrowser)paramObject).getSessionToken();
  }
  
  public static boolean isConnected(Object paramObject) {
    return ((MediaBrowser)paramObject).isConnected();
  }
  
  public static void subscribe(Object paramObject1, String paramString, Object paramObject2) {
    ((MediaBrowser)paramObject1).subscribe(paramString, (MediaBrowser.SubscriptionCallback)paramObject2);
  }
  
  public static void unsubscribe(Object paramObject, String paramString) {
    ((MediaBrowser)paramObject).unsubscribe(paramString);
  }
  
  static interface ConnectionCallback {
    void onConnected();
    
    void onConnectionFailed();
    
    void onConnectionSuspended();
  }
  
  static class ConnectionCallbackProxy<T extends ConnectionCallback> extends MediaBrowser.ConnectionCallback {
    protected final T mConnectionCallback;
    
    public ConnectionCallbackProxy(T param1T) {
      this.mConnectionCallback = param1T;
    }
    
    public void onConnected() {
      this.mConnectionCallback.onConnected();
    }
    
    public void onConnectionFailed() {
      this.mConnectionCallback.onConnectionFailed();
    }
    
    public void onConnectionSuspended() {
      this.mConnectionCallback.onConnectionSuspended();
    }
  }
  
  static class MediaItem {
    public static Object getDescription(Object param1Object) {
      return ((MediaBrowser.MediaItem)param1Object).getDescription();
    }
    
    public static int getFlags(Object param1Object) {
      return ((MediaBrowser.MediaItem)param1Object).getFlags();
    }
  }
  
  static interface SubscriptionCallback {
    void onChildrenLoaded(String param1String, List<?> param1List);
    
    void onError(String param1String);
  }
  
  static class SubscriptionCallbackProxy<T extends SubscriptionCallback> extends MediaBrowser.SubscriptionCallback {
    protected final T mSubscriptionCallback;
    
    public SubscriptionCallbackProxy(T param1T) {
      this.mSubscriptionCallback = param1T;
    }
    
    public void onChildrenLoaded(String param1String, List<MediaBrowser.MediaItem> param1List) {
      this.mSubscriptionCallback.onChildrenLoaded(param1String, param1List);
    }
    
    public void onError(String param1String) {
      this.mSubscriptionCallback.onError(param1String);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\MediaBrowserCompatApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */