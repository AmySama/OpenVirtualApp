package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.VolumeProvider;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class MediaSessionCompatApi21 {
  static final String TAG = "MediaSessionCompatApi21";
  
  public static Object createCallback(Callback paramCallback) {
    return new CallbackProxy<Callback>(paramCallback);
  }
  
  public static Object createSession(Context paramContext, String paramString) {
    return new MediaSession(paramContext, paramString);
  }
  
  public static Parcelable getSessionToken(Object paramObject) {
    return (Parcelable)((MediaSession)paramObject).getSessionToken();
  }
  
  public static boolean hasCallback(Object paramObject) {
    boolean bool = false;
    try {
      Field field = paramObject.getClass().getDeclaredField("mCallback");
      if (field != null) {
        field.setAccessible(true);
        paramObject = field.get(paramObject);
        if (paramObject != null)
          bool = true; 
        return bool;
      } 
    } catch (NoSuchFieldException|IllegalAccessException noSuchFieldException) {
      Log.w("MediaSessionCompatApi21", "Failed to get mCallback object.");
    } 
    return false;
  }
  
  public static boolean isActive(Object paramObject) {
    return ((MediaSession)paramObject).isActive();
  }
  
  public static void release(Object paramObject) {
    ((MediaSession)paramObject).release();
  }
  
  public static void sendSessionEvent(Object paramObject, String paramString, Bundle paramBundle) {
    ((MediaSession)paramObject).sendSessionEvent(paramString, paramBundle);
  }
  
  public static void setActive(Object paramObject, boolean paramBoolean) {
    ((MediaSession)paramObject).setActive(paramBoolean);
  }
  
  public static void setCallback(Object paramObject1, Object paramObject2, Handler paramHandler) {
    ((MediaSession)paramObject1).setCallback((MediaSession.Callback)paramObject2, paramHandler);
  }
  
  public static void setExtras(Object paramObject, Bundle paramBundle) {
    ((MediaSession)paramObject).setExtras(paramBundle);
  }
  
  public static void setFlags(Object paramObject, int paramInt) {
    ((MediaSession)paramObject).setFlags(paramInt);
  }
  
  public static void setMediaButtonReceiver(Object paramObject, PendingIntent paramPendingIntent) {
    ((MediaSession)paramObject).setMediaButtonReceiver(paramPendingIntent);
  }
  
  public static void setMetadata(Object paramObject1, Object paramObject2) {
    ((MediaSession)paramObject1).setMetadata((MediaMetadata)paramObject2);
  }
  
  public static void setPlaybackState(Object paramObject1, Object paramObject2) {
    ((MediaSession)paramObject1).setPlaybackState((PlaybackState)paramObject2);
  }
  
  public static void setPlaybackToLocal(Object paramObject, int paramInt) {
    AudioAttributes.Builder builder = new AudioAttributes.Builder();
    builder.setLegacyStreamType(paramInt);
    ((MediaSession)paramObject).setPlaybackToLocal(builder.build());
  }
  
  public static void setPlaybackToRemote(Object paramObject1, Object paramObject2) {
    ((MediaSession)paramObject1).setPlaybackToRemote((VolumeProvider)paramObject2);
  }
  
  public static void setQueue(Object paramObject, List<Object> paramList) {
    if (paramList == null) {
      ((MediaSession)paramObject).setQueue(null);
      return;
    } 
    ArrayList<MediaSession.QueueItem> arrayList = new ArrayList();
    Iterator<MediaSession.QueueItem> iterator = paramList.iterator();
    while (iterator.hasNext())
      arrayList.add(iterator.next()); 
    ((MediaSession)paramObject).setQueue(arrayList);
  }
  
  public static void setQueueTitle(Object paramObject, CharSequence paramCharSequence) {
    ((MediaSession)paramObject).setQueueTitle(paramCharSequence);
  }
  
  public static void setSessionActivity(Object paramObject, PendingIntent paramPendingIntent) {
    ((MediaSession)paramObject).setSessionActivity(paramPendingIntent);
  }
  
  public static Object verifySession(Object paramObject) {
    if (paramObject instanceof MediaSession)
      return paramObject; 
    throw new IllegalArgumentException("mediaSession is not a valid MediaSession object");
  }
  
  public static Object verifyToken(Object paramObject) {
    if (paramObject instanceof MediaSession.Token)
      return paramObject; 
    throw new IllegalArgumentException("token is not a valid MediaSession.Token object");
  }
  
  static interface Callback {
    void onCommand(String param1String, Bundle param1Bundle, ResultReceiver param1ResultReceiver);
    
    void onCustomAction(String param1String, Bundle param1Bundle);
    
    void onFastForward();
    
    boolean onMediaButtonEvent(Intent param1Intent);
    
    void onPause();
    
    void onPlay();
    
    void onPlayFromMediaId(String param1String, Bundle param1Bundle);
    
    void onPlayFromSearch(String param1String, Bundle param1Bundle);
    
    void onRewind();
    
    void onSeekTo(long param1Long);
    
    void onSetRating(Object param1Object);
    
    void onSetRating(Object param1Object, Bundle param1Bundle);
    
    void onSkipToNext();
    
    void onSkipToPrevious();
    
    void onSkipToQueueItem(long param1Long);
    
    void onStop();
  }
  
  static class CallbackProxy<T extends Callback> extends MediaSession.Callback {
    protected final T mCallback;
    
    public CallbackProxy(T param1T) {
      this.mCallback = param1T;
    }
    
    public void onCommand(String param1String, Bundle param1Bundle, ResultReceiver param1ResultReceiver) {
      this.mCallback.onCommand(param1String, param1Bundle, param1ResultReceiver);
    }
    
    public void onCustomAction(String param1String, Bundle param1Bundle) {
      this.mCallback.onCustomAction(param1String, param1Bundle);
    }
    
    public void onFastForward() {
      this.mCallback.onFastForward();
    }
    
    public boolean onMediaButtonEvent(Intent param1Intent) {
      return (this.mCallback.onMediaButtonEvent(param1Intent) || super.onMediaButtonEvent(param1Intent));
    }
    
    public void onPause() {
      this.mCallback.onPause();
    }
    
    public void onPlay() {
      this.mCallback.onPlay();
    }
    
    public void onPlayFromMediaId(String param1String, Bundle param1Bundle) {
      this.mCallback.onPlayFromMediaId(param1String, param1Bundle);
    }
    
    public void onPlayFromSearch(String param1String, Bundle param1Bundle) {
      this.mCallback.onPlayFromSearch(param1String, param1Bundle);
    }
    
    public void onRewind() {
      this.mCallback.onRewind();
    }
    
    public void onSeekTo(long param1Long) {
      this.mCallback.onSeekTo(param1Long);
    }
    
    public void onSetRating(Rating param1Rating) {
      this.mCallback.onSetRating(param1Rating);
    }
    
    public void onSkipToNext() {
      this.mCallback.onSkipToNext();
    }
    
    public void onSkipToPrevious() {
      this.mCallback.onSkipToPrevious();
    }
    
    public void onSkipToQueueItem(long param1Long) {
      this.mCallback.onSkipToQueueItem(param1Long);
    }
    
    public void onStop() {
      this.mCallback.onStop();
    }
  }
  
  static class QueueItem {
    public static Object createItem(Object param1Object, long param1Long) {
      return new MediaSession.QueueItem((MediaDescription)param1Object, param1Long);
    }
    
    public static Object getDescription(Object param1Object) {
      return ((MediaSession.QueueItem)param1Object).getDescription();
    }
    
    public static long getQueueId(Object param1Object) {
      return ((MediaSession.QueueItem)param1Object).getQueueId();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\session\MediaSessionCompatApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */