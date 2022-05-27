package android.support.v4.media.session;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class MediaControllerCompatApi21 {
  public static void adjustVolume(Object paramObject, int paramInt1, int paramInt2) {
    ((MediaController)paramObject).adjustVolume(paramInt1, paramInt2);
  }
  
  public static Object createCallback(Callback paramCallback) {
    return new CallbackProxy<Callback>(paramCallback);
  }
  
  public static boolean dispatchMediaButtonEvent(Object paramObject, KeyEvent paramKeyEvent) {
    return ((MediaController)paramObject).dispatchMediaButtonEvent(paramKeyEvent);
  }
  
  public static Object fromToken(Context paramContext, Object paramObject) {
    return new MediaController(paramContext, (MediaSession.Token)paramObject);
  }
  
  public static Bundle getExtras(Object paramObject) {
    return ((MediaController)paramObject).getExtras();
  }
  
  public static long getFlags(Object paramObject) {
    return ((MediaController)paramObject).getFlags();
  }
  
  public static Object getMediaController(Activity paramActivity) {
    return paramActivity.getMediaController();
  }
  
  public static Object getMetadata(Object paramObject) {
    return ((MediaController)paramObject).getMetadata();
  }
  
  public static String getPackageName(Object paramObject) {
    return ((MediaController)paramObject).getPackageName();
  }
  
  public static Object getPlaybackInfo(Object paramObject) {
    return ((MediaController)paramObject).getPlaybackInfo();
  }
  
  public static Object getPlaybackState(Object paramObject) {
    return ((MediaController)paramObject).getPlaybackState();
  }
  
  public static List<Object> getQueue(Object paramObject) {
    paramObject = ((MediaController)paramObject).getQueue();
    return (paramObject == null) ? null : new ArrayList((Collection<?>)paramObject);
  }
  
  public static CharSequence getQueueTitle(Object paramObject) {
    return ((MediaController)paramObject).getQueueTitle();
  }
  
  public static int getRatingType(Object paramObject) {
    return ((MediaController)paramObject).getRatingType();
  }
  
  public static PendingIntent getSessionActivity(Object paramObject) {
    return ((MediaController)paramObject).getSessionActivity();
  }
  
  public static Object getSessionToken(Object paramObject) {
    return ((MediaController)paramObject).getSessionToken();
  }
  
  public static Object getTransportControls(Object paramObject) {
    return ((MediaController)paramObject).getTransportControls();
  }
  
  public static void registerCallback(Object paramObject1, Object paramObject2, Handler paramHandler) {
    ((MediaController)paramObject1).registerCallback((MediaController.Callback)paramObject2, paramHandler);
  }
  
  public static void sendCommand(Object paramObject, String paramString, Bundle paramBundle, ResultReceiver paramResultReceiver) {
    ((MediaController)paramObject).sendCommand(paramString, paramBundle, paramResultReceiver);
  }
  
  public static void setMediaController(Activity paramActivity, Object paramObject) {
    paramActivity.setMediaController((MediaController)paramObject);
  }
  
  public static void setVolumeTo(Object paramObject, int paramInt1, int paramInt2) {
    ((MediaController)paramObject).setVolumeTo(paramInt1, paramInt2);
  }
  
  public static void unregisterCallback(Object paramObject1, Object paramObject2) {
    ((MediaController)paramObject1).unregisterCallback((MediaController.Callback)paramObject2);
  }
  
  public static interface Callback {
    void onAudioInfoChanged(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5);
    
    void onExtrasChanged(Bundle param1Bundle);
    
    void onMetadataChanged(Object param1Object);
    
    void onPlaybackStateChanged(Object param1Object);
    
    void onQueueChanged(List<?> param1List);
    
    void onQueueTitleChanged(CharSequence param1CharSequence);
    
    void onSessionDestroyed();
    
    void onSessionEvent(String param1String, Bundle param1Bundle);
  }
  
  static class CallbackProxy<T extends Callback> extends MediaController.Callback {
    protected final T mCallback;
    
    public CallbackProxy(T param1T) {
      this.mCallback = param1T;
    }
    
    public void onAudioInfoChanged(MediaController.PlaybackInfo param1PlaybackInfo) {
      this.mCallback.onAudioInfoChanged(param1PlaybackInfo.getPlaybackType(), MediaControllerCompatApi21.PlaybackInfo.getLegacyAudioStream(param1PlaybackInfo), param1PlaybackInfo.getVolumeControl(), param1PlaybackInfo.getMaxVolume(), param1PlaybackInfo.getCurrentVolume());
    }
    
    public void onExtrasChanged(Bundle param1Bundle) {
      this.mCallback.onExtrasChanged(param1Bundle);
    }
    
    public void onMetadataChanged(MediaMetadata param1MediaMetadata) {
      this.mCallback.onMetadataChanged(param1MediaMetadata);
    }
    
    public void onPlaybackStateChanged(PlaybackState param1PlaybackState) {
      this.mCallback.onPlaybackStateChanged(param1PlaybackState);
    }
    
    public void onQueueChanged(List<MediaSession.QueueItem> param1List) {
      this.mCallback.onQueueChanged(param1List);
    }
    
    public void onQueueTitleChanged(CharSequence param1CharSequence) {
      this.mCallback.onQueueTitleChanged(param1CharSequence);
    }
    
    public void onSessionDestroyed() {
      this.mCallback.onSessionDestroyed();
    }
    
    public void onSessionEvent(String param1String, Bundle param1Bundle) {
      this.mCallback.onSessionEvent(param1String, param1Bundle);
    }
  }
  
  public static class PlaybackInfo {
    private static final int FLAG_SCO = 4;
    
    private static final int STREAM_BLUETOOTH_SCO = 6;
    
    private static final int STREAM_SYSTEM_ENFORCED = 7;
    
    public static AudioAttributes getAudioAttributes(Object param1Object) {
      return ((MediaController.PlaybackInfo)param1Object).getAudioAttributes();
    }
    
    public static int getCurrentVolume(Object param1Object) {
      return ((MediaController.PlaybackInfo)param1Object).getCurrentVolume();
    }
    
    public static int getLegacyAudioStream(Object param1Object) {
      return toLegacyStreamType(getAudioAttributes(param1Object));
    }
    
    public static int getMaxVolume(Object param1Object) {
      return ((MediaController.PlaybackInfo)param1Object).getMaxVolume();
    }
    
    public static int getPlaybackType(Object param1Object) {
      return ((MediaController.PlaybackInfo)param1Object).getPlaybackType();
    }
    
    public static int getVolumeControl(Object param1Object) {
      return ((MediaController.PlaybackInfo)param1Object).getVolumeControl();
    }
    
    private static int toLegacyStreamType(AudioAttributes param1AudioAttributes) {
      if ((param1AudioAttributes.getFlags() & 0x1) == 1)
        return 7; 
      if ((param1AudioAttributes.getFlags() & 0x4) == 4)
        return 6; 
      int i = param1AudioAttributes.getUsage();
      if (i != 13) {
        switch (i) {
          default:
            return 3;
          case 6:
            return 2;
          case 5:
          case 7:
          case 8:
          case 9:
          case 10:
            return 5;
          case 4:
            return 4;
          case 3:
            return 8;
          case 2:
            break;
        } 
        return 0;
      } 
      return 1;
    }
  }
  
  public static class TransportControls {
    public static void fastForward(Object param1Object) {
      ((MediaController.TransportControls)param1Object).fastForward();
    }
    
    public static void pause(Object param1Object) {
      ((MediaController.TransportControls)param1Object).pause();
    }
    
    public static void play(Object param1Object) {
      ((MediaController.TransportControls)param1Object).play();
    }
    
    public static void playFromMediaId(Object param1Object, String param1String, Bundle param1Bundle) {
      ((MediaController.TransportControls)param1Object).playFromMediaId(param1String, param1Bundle);
    }
    
    public static void playFromSearch(Object param1Object, String param1String, Bundle param1Bundle) {
      ((MediaController.TransportControls)param1Object).playFromSearch(param1String, param1Bundle);
    }
    
    public static void rewind(Object param1Object) {
      ((MediaController.TransportControls)param1Object).rewind();
    }
    
    public static void seekTo(Object param1Object, long param1Long) {
      ((MediaController.TransportControls)param1Object).seekTo(param1Long);
    }
    
    public static void sendCustomAction(Object param1Object, String param1String, Bundle param1Bundle) {
      ((MediaController.TransportControls)param1Object).sendCustomAction(param1String, param1Bundle);
    }
    
    public static void setRating(Object param1Object1, Object param1Object2) {
      ((MediaController.TransportControls)param1Object1).setRating((Rating)param1Object2);
    }
    
    public static void skipToNext(Object param1Object) {
      ((MediaController.TransportControls)param1Object).skipToNext();
    }
    
    public static void skipToPrevious(Object param1Object) {
      ((MediaController.TransportControls)param1Object).skipToPrevious();
    }
    
    public static void skipToQueueItem(Object param1Object, long param1Long) {
      ((MediaController.TransportControls)param1Object).skipToQueueItem(param1Long);
    }
    
    public static void stop(Object param1Object) {
      ((MediaController.TransportControls)param1Object).stop();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\session\MediaControllerCompatApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */