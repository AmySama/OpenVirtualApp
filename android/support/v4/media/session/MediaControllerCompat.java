package android.support.v4.media.session;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.SupportActivity;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final class MediaControllerCompat {
  static final String COMMAND_ADD_QUEUE_ITEM = "android.support.v4.media.session.command.ADD_QUEUE_ITEM";
  
  static final String COMMAND_ADD_QUEUE_ITEM_AT = "android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT";
  
  static final String COMMAND_ARGUMENT_INDEX = "android.support.v4.media.session.command.ARGUMENT_INDEX";
  
  static final String COMMAND_ARGUMENT_MEDIA_DESCRIPTION = "android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION";
  
  static final String COMMAND_GET_EXTRA_BINDER = "android.support.v4.media.session.command.GET_EXTRA_BINDER";
  
  static final String COMMAND_REMOVE_QUEUE_ITEM = "android.support.v4.media.session.command.REMOVE_QUEUE_ITEM";
  
  static final String COMMAND_REMOVE_QUEUE_ITEM_AT = "android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT";
  
  static final String TAG = "MediaControllerCompat";
  
  private final MediaControllerImpl mImpl;
  
  private final HashSet<Callback> mRegisteredCallbacks = new HashSet<Callback>();
  
  private final MediaSessionCompat.Token mToken;
  
  public MediaControllerCompat(Context paramContext, MediaSessionCompat.Token paramToken) throws RemoteException {
    if (paramToken != null) {
      this.mToken = paramToken;
      if (Build.VERSION.SDK_INT >= 24) {
        this.mImpl = new MediaControllerImplApi24(paramContext, paramToken);
      } else if (Build.VERSION.SDK_INT >= 23) {
        this.mImpl = new MediaControllerImplApi23(paramContext, paramToken);
      } else if (Build.VERSION.SDK_INT >= 21) {
        this.mImpl = new MediaControllerImplApi21(paramContext, paramToken);
      } else {
        this.mImpl = new MediaControllerImplBase(this.mToken);
      } 
      return;
    } 
    throw new IllegalArgumentException("sessionToken must not be null");
  }
  
  public MediaControllerCompat(Context paramContext, MediaSessionCompat paramMediaSessionCompat) {
    if (paramMediaSessionCompat != null) {
      this.mToken = paramMediaSessionCompat.getSessionToken();
      if (Build.VERSION.SDK_INT >= 24) {
        this.mImpl = new MediaControllerImplApi24(paramContext, paramMediaSessionCompat);
      } else if (Build.VERSION.SDK_INT >= 23) {
        this.mImpl = new MediaControllerImplApi23(paramContext, paramMediaSessionCompat);
      } else if (Build.VERSION.SDK_INT >= 21) {
        this.mImpl = new MediaControllerImplApi21(paramContext, paramMediaSessionCompat);
      } else {
        this.mImpl = new MediaControllerImplBase(this.mToken);
      } 
      return;
    } 
    throw new IllegalArgumentException("session must not be null");
  }
  
  public static MediaControllerCompat getMediaController(Activity paramActivity) {
    MediaControllerCompat mediaControllerCompat;
    boolean bool = paramActivity instanceof SupportActivity;
    Activity activity = null;
    if (bool) {
      MediaControllerExtraData mediaControllerExtraData = (MediaControllerExtraData)((SupportActivity)paramActivity).getExtraData(MediaControllerExtraData.class);
      paramActivity = activity;
      if (mediaControllerExtraData != null)
        mediaControllerCompat = mediaControllerExtraData.getMediaController(); 
      return mediaControllerCompat;
    } 
    if (Build.VERSION.SDK_INT >= 21) {
      Object object = MediaControllerCompatApi21.getMediaController((Activity)mediaControllerCompat);
      if (object == null)
        return null; 
      object = MediaControllerCompatApi21.getSessionToken(object);
      try {
        return new MediaControllerCompat((Context)mediaControllerCompat, MediaSessionCompat.Token.fromToken(object));
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getMediaController.", (Throwable)remoteException);
      } 
    } 
    return null;
  }
  
  public static void setMediaController(Activity paramActivity, MediaControllerCompat paramMediaControllerCompat) {
    if (paramActivity instanceof SupportActivity)
      ((SupportActivity)paramActivity).putExtraData(new MediaControllerExtraData(paramMediaControllerCompat)); 
    if (Build.VERSION.SDK_INT >= 21) {
      Object object = null;
      if (paramMediaControllerCompat != null)
        object = MediaControllerCompatApi21.fromToken((Context)paramActivity, paramMediaControllerCompat.getSessionToken().getToken()); 
      MediaControllerCompatApi21.setMediaController(paramActivity, object);
    } 
  }
  
  private static void validateCustomAction(String paramString, Bundle paramBundle) {
    if (paramString == null)
      return; 
    byte b = -1;
    int i = paramString.hashCode();
    if (i != -1348483723) {
      if (i == 503011406 && paramString.equals("android.support.v4.media.session.action.UNFOLLOW"))
        b = 1; 
    } else if (paramString.equals("android.support.v4.media.session.action.FOLLOW")) {
      b = 0;
    } 
    if ((b != 0 && b != 1) || (paramBundle != null && paramBundle.containsKey("android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE")))
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("An extra field android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE is required for this action ");
    stringBuilder.append(paramString);
    stringBuilder.append(".");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void addQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat) {
    this.mImpl.addQueueItem(paramMediaDescriptionCompat);
  }
  
  public void addQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat, int paramInt) {
    this.mImpl.addQueueItem(paramMediaDescriptionCompat, paramInt);
  }
  
  public void adjustVolume(int paramInt1, int paramInt2) {
    this.mImpl.adjustVolume(paramInt1, paramInt2);
  }
  
  public boolean dispatchMediaButtonEvent(KeyEvent paramKeyEvent) {
    if (paramKeyEvent != null)
      return this.mImpl.dispatchMediaButtonEvent(paramKeyEvent); 
    throw new IllegalArgumentException("KeyEvent may not be null");
  }
  
  public Bundle getExtras() {
    return this.mImpl.getExtras();
  }
  
  public long getFlags() {
    return this.mImpl.getFlags();
  }
  
  public Object getMediaController() {
    return this.mImpl.getMediaController();
  }
  
  public MediaMetadataCompat getMetadata() {
    return this.mImpl.getMetadata();
  }
  
  public String getPackageName() {
    return this.mImpl.getPackageName();
  }
  
  public PlaybackInfo getPlaybackInfo() {
    return this.mImpl.getPlaybackInfo();
  }
  
  public PlaybackStateCompat getPlaybackState() {
    return this.mImpl.getPlaybackState();
  }
  
  public List<MediaSessionCompat.QueueItem> getQueue() {
    return this.mImpl.getQueue();
  }
  
  public CharSequence getQueueTitle() {
    return this.mImpl.getQueueTitle();
  }
  
  public int getRatingType() {
    return this.mImpl.getRatingType();
  }
  
  public int getRepeatMode() {
    return this.mImpl.getRepeatMode();
  }
  
  public PendingIntent getSessionActivity() {
    return this.mImpl.getSessionActivity();
  }
  
  public MediaSessionCompat.Token getSessionToken() {
    return this.mToken;
  }
  
  public int getShuffleMode() {
    return this.mImpl.getShuffleMode();
  }
  
  public TransportControls getTransportControls() {
    return this.mImpl.getTransportControls();
  }
  
  public boolean isCaptioningEnabled() {
    return this.mImpl.isCaptioningEnabled();
  }
  
  public boolean isSessionReady() {
    return this.mImpl.isSessionReady();
  }
  
  public void registerCallback(Callback paramCallback) {
    registerCallback(paramCallback, null);
  }
  
  public void registerCallback(Callback paramCallback, Handler paramHandler) {
    if (paramCallback != null) {
      Handler handler = paramHandler;
      if (paramHandler == null)
        handler = new Handler(); 
      paramCallback.setHandler(handler);
      this.mImpl.registerCallback(paramCallback, handler);
      this.mRegisteredCallbacks.add(paramCallback);
      return;
    } 
    throw new IllegalArgumentException("callback must not be null");
  }
  
  public void removeQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat) {
    this.mImpl.removeQueueItem(paramMediaDescriptionCompat);
  }
  
  @Deprecated
  public void removeQueueItemAt(int paramInt) {
    List<MediaSessionCompat.QueueItem> list = getQueue();
    if (list != null && paramInt >= 0 && paramInt < list.size()) {
      MediaSessionCompat.QueueItem queueItem = list.get(paramInt);
      if (queueItem != null)
        removeQueueItem(queueItem.getDescription()); 
    } 
  }
  
  public void sendCommand(String paramString, Bundle paramBundle, ResultReceiver paramResultReceiver) {
    if (!TextUtils.isEmpty(paramString)) {
      this.mImpl.sendCommand(paramString, paramBundle, paramResultReceiver);
      return;
    } 
    throw new IllegalArgumentException("command must neither be null nor empty");
  }
  
  public void setVolumeTo(int paramInt1, int paramInt2) {
    this.mImpl.setVolumeTo(paramInt1, paramInt2);
  }
  
  public void unregisterCallback(Callback paramCallback) {
    if (paramCallback != null)
      try {
        this.mRegisteredCallbacks.remove(paramCallback);
        this.mImpl.unregisterCallback(paramCallback);
        return;
      } finally {
        paramCallback.setHandler(null);
      }  
    throw new IllegalArgumentException("callback must not be null");
  }
  
  public static abstract class Callback implements IBinder.DeathRecipient {
    private final Object mCallbackObj;
    
    MessageHandler mHandler;
    
    boolean mHasExtraCallback;
    
    public Callback() {
      if (Build.VERSION.SDK_INT >= 21) {
        this.mCallbackObj = MediaControllerCompatApi21.createCallback(new StubApi21(this));
      } else {
        this.mCallbackObj = new StubCompat(this);
      } 
    }
    
    public void binderDied() {
      onSessionDestroyed();
    }
    
    public void onAudioInfoChanged(MediaControllerCompat.PlaybackInfo param1PlaybackInfo) {}
    
    public void onCaptioningEnabledChanged(boolean param1Boolean) {}
    
    public void onExtrasChanged(Bundle param1Bundle) {}
    
    public void onMetadataChanged(MediaMetadataCompat param1MediaMetadataCompat) {}
    
    public void onPlaybackStateChanged(PlaybackStateCompat param1PlaybackStateCompat) {}
    
    public void onQueueChanged(List<MediaSessionCompat.QueueItem> param1List) {}
    
    public void onQueueTitleChanged(CharSequence param1CharSequence) {}
    
    public void onRepeatModeChanged(int param1Int) {}
    
    public void onSessionDestroyed() {}
    
    public void onSessionEvent(String param1String, Bundle param1Bundle) {}
    
    public void onSessionReady() {}
    
    public void onShuffleModeChanged(int param1Int) {}
    
    void postToHandler(int param1Int, Object param1Object, Bundle param1Bundle) {
      MessageHandler messageHandler = this.mHandler;
      if (messageHandler != null) {
        param1Object = messageHandler.obtainMessage(param1Int, param1Object);
        param1Object.setData(param1Bundle);
        param1Object.sendToTarget();
      } 
    }
    
    void setHandler(Handler param1Handler) {
      if (param1Handler == null) {
        param1Handler = this.mHandler;
        if (param1Handler != null) {
          ((MessageHandler)param1Handler).mRegistered = false;
          this.mHandler.removeCallbacksAndMessages(null);
          this.mHandler = null;
        } 
      } else {
        param1Handler = new MessageHandler(param1Handler.getLooper());
        this.mHandler = (MessageHandler)param1Handler;
        ((MessageHandler)param1Handler).mRegistered = true;
      } 
    }
    
    private class MessageHandler extends Handler {
      private static final int MSG_DESTROYED = 8;
      
      private static final int MSG_EVENT = 1;
      
      private static final int MSG_SESSION_READY = 13;
      
      private static final int MSG_UPDATE_CAPTIONING_ENABLED = 11;
      
      private static final int MSG_UPDATE_EXTRAS = 7;
      
      private static final int MSG_UPDATE_METADATA = 3;
      
      private static final int MSG_UPDATE_PLAYBACK_STATE = 2;
      
      private static final int MSG_UPDATE_QUEUE = 5;
      
      private static final int MSG_UPDATE_QUEUE_TITLE = 6;
      
      private static final int MSG_UPDATE_REPEAT_MODE = 9;
      
      private static final int MSG_UPDATE_SHUFFLE_MODE = 12;
      
      private static final int MSG_UPDATE_VOLUME = 4;
      
      boolean mRegistered = false;
      
      MessageHandler(Looper param2Looper) {
        super(param2Looper);
      }
      
      public void handleMessage(Message param2Message) {
        if (!this.mRegistered)
          return; 
        switch (param2Message.what) {
          default:
            return;
          case 13:
            MediaControllerCompat.Callback.this.onSessionReady();
          case 12:
            MediaControllerCompat.Callback.this.onShuffleModeChanged(((Integer)param2Message.obj).intValue());
          case 11:
            MediaControllerCompat.Callback.this.onCaptioningEnabledChanged(((Boolean)param2Message.obj).booleanValue());
          case 9:
            MediaControllerCompat.Callback.this.onRepeatModeChanged(((Integer)param2Message.obj).intValue());
          case 8:
            MediaControllerCompat.Callback.this.onSessionDestroyed();
          case 7:
            MediaControllerCompat.Callback.this.onExtrasChanged((Bundle)param2Message.obj);
          case 6:
            MediaControllerCompat.Callback.this.onQueueTitleChanged((CharSequence)param2Message.obj);
          case 5:
            MediaControllerCompat.Callback.this.onQueueChanged((List<MediaSessionCompat.QueueItem>)param2Message.obj);
          case 4:
            MediaControllerCompat.Callback.this.onAudioInfoChanged((MediaControllerCompat.PlaybackInfo)param2Message.obj);
          case 3:
            MediaControllerCompat.Callback.this.onMetadataChanged((MediaMetadataCompat)param2Message.obj);
          case 2:
            MediaControllerCompat.Callback.this.onPlaybackStateChanged((PlaybackStateCompat)param2Message.obj);
          case 1:
            break;
        } 
        MediaControllerCompat.Callback.this.onSessionEvent((String)param2Message.obj, param2Message.getData());
      }
    }
    
    private static class StubApi21 implements MediaControllerCompatApi21.Callback {
      private final WeakReference<MediaControllerCompat.Callback> mCallback;
      
      StubApi21(MediaControllerCompat.Callback param2Callback) {
        this.mCallback = new WeakReference<MediaControllerCompat.Callback>(param2Callback);
      }
      
      public void onAudioInfoChanged(int param2Int1, int param2Int2, int param2Int3, int param2Int4, int param2Int5) {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.onAudioInfoChanged(new MediaControllerCompat.PlaybackInfo(param2Int1, param2Int2, param2Int3, param2Int4, param2Int5)); 
      }
      
      public void onExtrasChanged(Bundle param2Bundle) {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.onExtrasChanged(param2Bundle); 
      }
      
      public void onMetadataChanged(Object param2Object) {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(param2Object)); 
      }
      
      public void onPlaybackStateChanged(Object param2Object) {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null && !callback.mHasExtraCallback)
          callback.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(param2Object)); 
      }
      
      public void onQueueChanged(List<?> param2List) {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.onQueueChanged(MediaSessionCompat.QueueItem.fromQueueItemList(param2List)); 
      }
      
      public void onQueueTitleChanged(CharSequence param2CharSequence) {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.onQueueTitleChanged(param2CharSequence); 
      }
      
      public void onSessionDestroyed() {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.onSessionDestroyed(); 
      }
      
      public void onSessionEvent(String param2String, Bundle param2Bundle) {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null && (!callback.mHasExtraCallback || Build.VERSION.SDK_INT >= 23))
          callback.onSessionEvent(param2String, param2Bundle); 
      }
    }
    
    private static class StubCompat extends IMediaControllerCallback.Stub {
      private final WeakReference<MediaControllerCompat.Callback> mCallback;
      
      StubCompat(MediaControllerCompat.Callback param2Callback) {
        this.mCallback = new WeakReference<MediaControllerCompat.Callback>(param2Callback);
      }
      
      public void onCaptioningEnabledChanged(boolean param2Boolean) throws RemoteException {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.postToHandler(11, Boolean.valueOf(param2Boolean), null); 
      }
      
      public void onEvent(String param2String, Bundle param2Bundle) throws RemoteException {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.postToHandler(1, param2String, param2Bundle); 
      }
      
      public void onExtrasChanged(Bundle param2Bundle) throws RemoteException {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.postToHandler(7, param2Bundle, null); 
      }
      
      public void onMetadataChanged(MediaMetadataCompat param2MediaMetadataCompat) throws RemoteException {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.postToHandler(3, param2MediaMetadataCompat, null); 
      }
      
      public void onPlaybackStateChanged(PlaybackStateCompat param2PlaybackStateCompat) throws RemoteException {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.postToHandler(2, param2PlaybackStateCompat, null); 
      }
      
      public void onQueueChanged(List<MediaSessionCompat.QueueItem> param2List) throws RemoteException {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.postToHandler(5, param2List, null); 
      }
      
      public void onQueueTitleChanged(CharSequence param2CharSequence) throws RemoteException {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.postToHandler(6, param2CharSequence, null); 
      }
      
      public void onRepeatModeChanged(int param2Int) throws RemoteException {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.postToHandler(9, Integer.valueOf(param2Int), null); 
      }
      
      public void onSessionDestroyed() throws RemoteException {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.postToHandler(8, null, null); 
      }
      
      public void onSessionReady() throws RemoteException {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.postToHandler(13, null, null); 
      }
      
      public void onShuffleModeChanged(int param2Int) throws RemoteException {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null)
          callback.postToHandler(12, Integer.valueOf(param2Int), null); 
      }
      
      public void onShuffleModeChangedRemoved(boolean param2Boolean) throws RemoteException {}
      
      public void onVolumeInfoChanged(ParcelableVolumeInfo param2ParcelableVolumeInfo) throws RemoteException {
        MediaControllerCompat.Callback callback = this.mCallback.get();
        if (callback != null) {
          if (param2ParcelableVolumeInfo != null) {
            MediaControllerCompat.PlaybackInfo playbackInfo = new MediaControllerCompat.PlaybackInfo(param2ParcelableVolumeInfo.volumeType, param2ParcelableVolumeInfo.audioStream, param2ParcelableVolumeInfo.controlType, param2ParcelableVolumeInfo.maxVolume, param2ParcelableVolumeInfo.currentVolume);
          } else {
            param2ParcelableVolumeInfo = null;
          } 
          callback.postToHandler(4, param2ParcelableVolumeInfo, null);
        } 
      }
    }
  }
  
  private class MessageHandler extends Handler {
    private static final int MSG_DESTROYED = 8;
    
    private static final int MSG_EVENT = 1;
    
    private static final int MSG_SESSION_READY = 13;
    
    private static final int MSG_UPDATE_CAPTIONING_ENABLED = 11;
    
    private static final int MSG_UPDATE_EXTRAS = 7;
    
    private static final int MSG_UPDATE_METADATA = 3;
    
    private static final int MSG_UPDATE_PLAYBACK_STATE = 2;
    
    private static final int MSG_UPDATE_QUEUE = 5;
    
    private static final int MSG_UPDATE_QUEUE_TITLE = 6;
    
    private static final int MSG_UPDATE_REPEAT_MODE = 9;
    
    private static final int MSG_UPDATE_SHUFFLE_MODE = 12;
    
    private static final int MSG_UPDATE_VOLUME = 4;
    
    boolean mRegistered = false;
    
    MessageHandler(Looper param1Looper) {
      super(param1Looper);
    }
    
    public void handleMessage(Message param1Message) {
      if (!this.mRegistered)
        return; 
      switch (param1Message.what) {
        default:
          return;
        case 13:
          this.this$0.onSessionReady();
        case 12:
          this.this$0.onShuffleModeChanged(((Integer)param1Message.obj).intValue());
        case 11:
          this.this$0.onCaptioningEnabledChanged(((Boolean)param1Message.obj).booleanValue());
        case 9:
          this.this$0.onRepeatModeChanged(((Integer)param1Message.obj).intValue());
        case 8:
          this.this$0.onSessionDestroyed();
        case 7:
          this.this$0.onExtrasChanged((Bundle)param1Message.obj);
        case 6:
          this.this$0.onQueueTitleChanged((CharSequence)param1Message.obj);
        case 5:
          this.this$0.onQueueChanged((List<MediaSessionCompat.QueueItem>)param1Message.obj);
        case 4:
          this.this$0.onAudioInfoChanged((MediaControllerCompat.PlaybackInfo)param1Message.obj);
        case 3:
          this.this$0.onMetadataChanged((MediaMetadataCompat)param1Message.obj);
        case 2:
          this.this$0.onPlaybackStateChanged((PlaybackStateCompat)param1Message.obj);
        case 1:
          break;
      } 
      this.this$0.onSessionEvent((String)param1Message.obj, param1Message.getData());
    }
  }
  
  private static class StubApi21 implements MediaControllerCompatApi21.Callback {
    private final WeakReference<MediaControllerCompat.Callback> mCallback;
    
    StubApi21(MediaControllerCompat.Callback param1Callback) {
      this.mCallback = new WeakReference<MediaControllerCompat.Callback>(param1Callback);
    }
    
    public void onAudioInfoChanged(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.onAudioInfoChanged(new MediaControllerCompat.PlaybackInfo(param1Int1, param1Int2, param1Int3, param1Int4, param1Int5)); 
    }
    
    public void onExtrasChanged(Bundle param1Bundle) {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.onExtrasChanged(param1Bundle); 
    }
    
    public void onMetadataChanged(Object param1Object) {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(param1Object)); 
    }
    
    public void onPlaybackStateChanged(Object param1Object) {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null && !callback.mHasExtraCallback)
        callback.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(param1Object)); 
    }
    
    public void onQueueChanged(List<?> param1List) {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.onQueueChanged(MediaSessionCompat.QueueItem.fromQueueItemList(param1List)); 
    }
    
    public void onQueueTitleChanged(CharSequence param1CharSequence) {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.onQueueTitleChanged(param1CharSequence); 
    }
    
    public void onSessionDestroyed() {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.onSessionDestroyed(); 
    }
    
    public void onSessionEvent(String param1String, Bundle param1Bundle) {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null && (!callback.mHasExtraCallback || Build.VERSION.SDK_INT >= 23))
        callback.onSessionEvent(param1String, param1Bundle); 
    }
  }
  
  private static class StubCompat extends IMediaControllerCallback.Stub {
    private final WeakReference<MediaControllerCompat.Callback> mCallback;
    
    StubCompat(MediaControllerCompat.Callback param1Callback) {
      this.mCallback = new WeakReference<MediaControllerCompat.Callback>(param1Callback);
    }
    
    public void onCaptioningEnabledChanged(boolean param1Boolean) throws RemoteException {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.postToHandler(11, Boolean.valueOf(param1Boolean), null); 
    }
    
    public void onEvent(String param1String, Bundle param1Bundle) throws RemoteException {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.postToHandler(1, param1String, param1Bundle); 
    }
    
    public void onExtrasChanged(Bundle param1Bundle) throws RemoteException {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.postToHandler(7, param1Bundle, null); 
    }
    
    public void onMetadataChanged(MediaMetadataCompat param1MediaMetadataCompat) throws RemoteException {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.postToHandler(3, param1MediaMetadataCompat, null); 
    }
    
    public void onPlaybackStateChanged(PlaybackStateCompat param1PlaybackStateCompat) throws RemoteException {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.postToHandler(2, param1PlaybackStateCompat, null); 
    }
    
    public void onQueueChanged(List<MediaSessionCompat.QueueItem> param1List) throws RemoteException {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.postToHandler(5, param1List, null); 
    }
    
    public void onQueueTitleChanged(CharSequence param1CharSequence) throws RemoteException {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.postToHandler(6, param1CharSequence, null); 
    }
    
    public void onRepeatModeChanged(int param1Int) throws RemoteException {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.postToHandler(9, Integer.valueOf(param1Int), null); 
    }
    
    public void onSessionDestroyed() throws RemoteException {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.postToHandler(8, null, null); 
    }
    
    public void onSessionReady() throws RemoteException {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.postToHandler(13, null, null); 
    }
    
    public void onShuffleModeChanged(int param1Int) throws RemoteException {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null)
        callback.postToHandler(12, Integer.valueOf(param1Int), null); 
    }
    
    public void onShuffleModeChangedRemoved(boolean param1Boolean) throws RemoteException {}
    
    public void onVolumeInfoChanged(ParcelableVolumeInfo param1ParcelableVolumeInfo) throws RemoteException {
      MediaControllerCompat.Callback callback = this.mCallback.get();
      if (callback != null) {
        if (param1ParcelableVolumeInfo != null) {
          MediaControllerCompat.PlaybackInfo playbackInfo = new MediaControllerCompat.PlaybackInfo(param1ParcelableVolumeInfo.volumeType, param1ParcelableVolumeInfo.audioStream, param1ParcelableVolumeInfo.controlType, param1ParcelableVolumeInfo.maxVolume, param1ParcelableVolumeInfo.currentVolume);
        } else {
          param1ParcelableVolumeInfo = null;
        } 
        callback.postToHandler(4, param1ParcelableVolumeInfo, null);
      } 
    }
  }
  
  private static class MediaControllerExtraData extends SupportActivity.ExtraData {
    private final MediaControllerCompat mMediaController;
    
    MediaControllerExtraData(MediaControllerCompat param1MediaControllerCompat) {
      this.mMediaController = param1MediaControllerCompat;
    }
    
    MediaControllerCompat getMediaController() {
      return this.mMediaController;
    }
  }
  
  static interface MediaControllerImpl {
    void addQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat);
    
    void addQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat, int param1Int);
    
    void adjustVolume(int param1Int1, int param1Int2);
    
    boolean dispatchMediaButtonEvent(KeyEvent param1KeyEvent);
    
    Bundle getExtras();
    
    long getFlags();
    
    Object getMediaController();
    
    MediaMetadataCompat getMetadata();
    
    String getPackageName();
    
    MediaControllerCompat.PlaybackInfo getPlaybackInfo();
    
    PlaybackStateCompat getPlaybackState();
    
    List<MediaSessionCompat.QueueItem> getQueue();
    
    CharSequence getQueueTitle();
    
    int getRatingType();
    
    int getRepeatMode();
    
    PendingIntent getSessionActivity();
    
    int getShuffleMode();
    
    MediaControllerCompat.TransportControls getTransportControls();
    
    boolean isCaptioningEnabled();
    
    boolean isSessionReady();
    
    void registerCallback(MediaControllerCompat.Callback param1Callback, Handler param1Handler);
    
    void removeQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat);
    
    void sendCommand(String param1String, Bundle param1Bundle, ResultReceiver param1ResultReceiver);
    
    void setVolumeTo(int param1Int1, int param1Int2);
    
    void unregisterCallback(MediaControllerCompat.Callback param1Callback);
  }
  
  static class MediaControllerImplApi21 implements MediaControllerImpl {
    private HashMap<MediaControllerCompat.Callback, ExtraCallback> mCallbackMap = new HashMap<MediaControllerCompat.Callback, ExtraCallback>();
    
    protected final Object mControllerObj;
    
    private IMediaSession mExtraBinder;
    
    private final List<MediaControllerCompat.Callback> mPendingCallbacks = new ArrayList<MediaControllerCompat.Callback>();
    
    public MediaControllerImplApi21(Context param1Context, MediaSessionCompat.Token param1Token) throws RemoteException {
      Object object = MediaControllerCompatApi21.fromToken(param1Context, param1Token.getToken());
      this.mControllerObj = object;
      if (object != null) {
        object = param1Token.getExtraBinder();
        this.mExtraBinder = (IMediaSession)object;
        if (object == null)
          requestExtraBinder(); 
        return;
      } 
      throw new RemoteException();
    }
    
    public MediaControllerImplApi21(Context param1Context, MediaSessionCompat param1MediaSessionCompat) {
      this.mControllerObj = MediaControllerCompatApi21.fromToken(param1Context, param1MediaSessionCompat.getSessionToken().getToken());
      IMediaSession iMediaSession = param1MediaSessionCompat.getSessionToken().getExtraBinder();
      this.mExtraBinder = iMediaSession;
      if (iMediaSession == null)
        requestExtraBinder(); 
    }
    
    private void processPendingCallbacks() {
      if (this.mExtraBinder == null)
        return; 
      synchronized (this.mPendingCallbacks) {
        Iterator<MediaControllerCompat.Callback> iterator = this.mPendingCallbacks.iterator();
        while (true) {
          if (iterator.hasNext()) {
            MediaControllerCompat.Callback callback = iterator.next();
            ExtraCallback extraCallback = new ExtraCallback();
            this(callback);
            this.mCallbackMap.put(callback, extraCallback);
            callback.mHasExtraCallback = true;
            try {
              this.mExtraBinder.registerCallbackListener(extraCallback);
              callback.onSessionReady();
              continue;
            } catch (RemoteException remoteException) {
              Log.e("MediaControllerCompat", "Dead object in registerCallback.", (Throwable)remoteException);
            } 
          } else {
            break;
          } 
          this.mPendingCallbacks.clear();
          return;
        } 
        this.mPendingCallbacks.clear();
        return;
      } 
    }
    
    private void requestExtraBinder() {
      sendCommand("android.support.v4.media.session.command.GET_EXTRA_BINDER", null, new ExtraBinderRequestResultReceiver(this, new Handler()));
    }
    
    public void addQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat) {
      if ((getFlags() & 0x4L) != 0L) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION", (Parcelable)param1MediaDescriptionCompat);
        sendCommand("android.support.v4.media.session.command.ADD_QUEUE_ITEM", bundle, null);
        return;
      } 
      throw new UnsupportedOperationException("This session doesn't support queue management operations");
    }
    
    public void addQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat, int param1Int) {
      if ((getFlags() & 0x4L) != 0L) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION", (Parcelable)param1MediaDescriptionCompat);
        bundle.putInt("android.support.v4.media.session.command.ARGUMENT_INDEX", param1Int);
        sendCommand("android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT", bundle, null);
        return;
      } 
      throw new UnsupportedOperationException("This session doesn't support queue management operations");
    }
    
    public void adjustVolume(int param1Int1, int param1Int2) {
      MediaControllerCompatApi21.adjustVolume(this.mControllerObj, param1Int1, param1Int2);
    }
    
    public boolean dispatchMediaButtonEvent(KeyEvent param1KeyEvent) {
      return MediaControllerCompatApi21.dispatchMediaButtonEvent(this.mControllerObj, param1KeyEvent);
    }
    
    public Bundle getExtras() {
      return MediaControllerCompatApi21.getExtras(this.mControllerObj);
    }
    
    public long getFlags() {
      return MediaControllerCompatApi21.getFlags(this.mControllerObj);
    }
    
    public Object getMediaController() {
      return this.mControllerObj;
    }
    
    public MediaMetadataCompat getMetadata() {
      Object object = MediaControllerCompatApi21.getMetadata(this.mControllerObj);
      if (object != null) {
        object = MediaMetadataCompat.fromMediaMetadata(object);
      } else {
        object = null;
      } 
      return (MediaMetadataCompat)object;
    }
    
    public String getPackageName() {
      return MediaControllerCompatApi21.getPackageName(this.mControllerObj);
    }
    
    public MediaControllerCompat.PlaybackInfo getPlaybackInfo() {
      Object object = MediaControllerCompatApi21.getPlaybackInfo(this.mControllerObj);
      if (object != null) {
        object = new MediaControllerCompat.PlaybackInfo(MediaControllerCompatApi21.PlaybackInfo.getPlaybackType(object), MediaControllerCompatApi21.PlaybackInfo.getLegacyAudioStream(object), MediaControllerCompatApi21.PlaybackInfo.getVolumeControl(object), MediaControllerCompatApi21.PlaybackInfo.getMaxVolume(object), MediaControllerCompatApi21.PlaybackInfo.getCurrentVolume(object));
      } else {
        object = null;
      } 
      return (MediaControllerCompat.PlaybackInfo)object;
    }
    
    public PlaybackStateCompat getPlaybackState() {
      IMediaSession iMediaSession = this.mExtraBinder;
      if (iMediaSession != null)
        try {
          return iMediaSession.getPlaybackState();
        } catch (RemoteException remoteException) {
          Log.e("MediaControllerCompat", "Dead object in getPlaybackState.", (Throwable)remoteException);
        }  
      Object object = MediaControllerCompatApi21.getPlaybackState(this.mControllerObj);
      if (object != null) {
        object = PlaybackStateCompat.fromPlaybackState(object);
      } else {
        object = null;
      } 
      return (PlaybackStateCompat)object;
    }
    
    public List<MediaSessionCompat.QueueItem> getQueue() {
      List<Object> list = MediaControllerCompatApi21.getQueue(this.mControllerObj);
      if (list != null) {
        list = (List)MediaSessionCompat.QueueItem.fromQueueItemList(list);
      } else {
        list = null;
      } 
      return (List)list;
    }
    
    public CharSequence getQueueTitle() {
      return MediaControllerCompatApi21.getQueueTitle(this.mControllerObj);
    }
    
    public int getRatingType() {
      if (Build.VERSION.SDK_INT < 22) {
        IMediaSession iMediaSession = this.mExtraBinder;
        if (iMediaSession != null)
          try {
            return iMediaSession.getRatingType();
          } catch (RemoteException remoteException) {
            Log.e("MediaControllerCompat", "Dead object in getRatingType.", (Throwable)remoteException);
          }  
      } 
      return MediaControllerCompatApi21.getRatingType(this.mControllerObj);
    }
    
    public int getRepeatMode() {
      IMediaSession iMediaSession = this.mExtraBinder;
      if (iMediaSession != null)
        try {
          return iMediaSession.getRepeatMode();
        } catch (RemoteException remoteException) {
          Log.e("MediaControllerCompat", "Dead object in getRepeatMode.", (Throwable)remoteException);
        }  
      return -1;
    }
    
    public PendingIntent getSessionActivity() {
      return MediaControllerCompatApi21.getSessionActivity(this.mControllerObj);
    }
    
    public int getShuffleMode() {
      IMediaSession iMediaSession = this.mExtraBinder;
      if (iMediaSession != null)
        try {
          return iMediaSession.getShuffleMode();
        } catch (RemoteException remoteException) {
          Log.e("MediaControllerCompat", "Dead object in getShuffleMode.", (Throwable)remoteException);
        }  
      return -1;
    }
    
    public MediaControllerCompat.TransportControls getTransportControls() {
      Object object = MediaControllerCompatApi21.getTransportControls(this.mControllerObj);
      if (object != null) {
        object = new MediaControllerCompat.TransportControlsApi21(object);
      } else {
        object = null;
      } 
      return (MediaControllerCompat.TransportControls)object;
    }
    
    public boolean isCaptioningEnabled() {
      IMediaSession iMediaSession = this.mExtraBinder;
      if (iMediaSession != null)
        try {
          return iMediaSession.isCaptioningEnabled();
        } catch (RemoteException remoteException) {
          Log.e("MediaControllerCompat", "Dead object in isCaptioningEnabled.", (Throwable)remoteException);
        }  
      return false;
    }
    
    public boolean isSessionReady() {
      boolean bool;
      if (this.mExtraBinder != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public final void registerCallback(MediaControllerCompat.Callback param1Callback, Handler param1Handler) {
      MediaControllerCompatApi21.registerCallback(this.mControllerObj, param1Callback.mCallbackObj, param1Handler);
      if (this.mExtraBinder != null) {
        ExtraCallback extraCallback = new ExtraCallback(param1Callback);
        this.mCallbackMap.put(param1Callback, extraCallback);
        param1Callback.mHasExtraCallback = true;
        try {
          this.mExtraBinder.registerCallbackListener(extraCallback);
        } catch (RemoteException remoteException) {
          Log.e("MediaControllerCompat", "Dead object in registerCallback.", (Throwable)remoteException);
        } 
      } else {
        synchronized (this.mPendingCallbacks) {
          ((MediaControllerCompat.Callback)remoteException).mHasExtraCallback = false;
          this.mPendingCallbacks.add(remoteException);
          return;
        } 
      } 
    }
    
    public void removeQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat) {
      if ((getFlags() & 0x4L) != 0L) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION", (Parcelable)param1MediaDescriptionCompat);
        sendCommand("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM", bundle, null);
        return;
      } 
      throw new UnsupportedOperationException("This session doesn't support queue management operations");
    }
    
    public void sendCommand(String param1String, Bundle param1Bundle, ResultReceiver param1ResultReceiver) {
      MediaControllerCompatApi21.sendCommand(this.mControllerObj, param1String, param1Bundle, param1ResultReceiver);
    }
    
    public void setVolumeTo(int param1Int1, int param1Int2) {
      MediaControllerCompatApi21.setVolumeTo(this.mControllerObj, param1Int1, param1Int2);
    }
    
    public final void unregisterCallback(MediaControllerCompat.Callback param1Callback) {
      MediaControllerCompatApi21.unregisterCallback(this.mControllerObj, param1Callback.mCallbackObj);
      if (this.mExtraBinder != null) {
        try {
          ExtraCallback extraCallback = this.mCallbackMap.remove(param1Callback);
          if (extraCallback != null) {
            param1Callback.mHasExtraCallback = false;
            this.mExtraBinder.unregisterCallbackListener(extraCallback);
          } 
        } catch (RemoteException remoteException) {
          Log.e("MediaControllerCompat", "Dead object in unregisterCallback.", (Throwable)remoteException);
        } 
      } else {
        synchronized (this.mPendingCallbacks) {
          this.mPendingCallbacks.remove(remoteException);
          return;
        } 
      } 
    }
    
    private static class ExtraBinderRequestResultReceiver extends ResultReceiver {
      private WeakReference<MediaControllerCompat.MediaControllerImplApi21> mMediaControllerImpl;
      
      public ExtraBinderRequestResultReceiver(MediaControllerCompat.MediaControllerImplApi21 param2MediaControllerImplApi21, Handler param2Handler) {
        super(param2Handler);
        this.mMediaControllerImpl = new WeakReference<MediaControllerCompat.MediaControllerImplApi21>(param2MediaControllerImplApi21);
      }
      
      protected void onReceiveResult(int param2Int, Bundle param2Bundle) {
        MediaControllerCompat.MediaControllerImplApi21 mediaControllerImplApi21 = this.mMediaControllerImpl.get();
        if (mediaControllerImplApi21 != null && param2Bundle != null) {
          MediaControllerCompat.MediaControllerImplApi21.access$202(mediaControllerImplApi21, IMediaSession.Stub.asInterface(BundleCompat.getBinder(param2Bundle, "android.support.v4.media.session.EXTRA_BINDER")));
          mediaControllerImplApi21.processPendingCallbacks();
        } 
      }
    }
    
    private static class ExtraCallback extends MediaControllerCompat.Callback.StubCompat {
      ExtraCallback(MediaControllerCompat.Callback param2Callback) {
        super(param2Callback);
      }
      
      public void onExtrasChanged(Bundle param2Bundle) throws RemoteException {
        throw new AssertionError();
      }
      
      public void onMetadataChanged(MediaMetadataCompat param2MediaMetadataCompat) throws RemoteException {
        throw new AssertionError();
      }
      
      public void onQueueChanged(List<MediaSessionCompat.QueueItem> param2List) throws RemoteException {
        throw new AssertionError();
      }
      
      public void onQueueTitleChanged(CharSequence param2CharSequence) throws RemoteException {
        throw new AssertionError();
      }
      
      public void onSessionDestroyed() throws RemoteException {
        throw new AssertionError();
      }
      
      public void onVolumeInfoChanged(ParcelableVolumeInfo param2ParcelableVolumeInfo) throws RemoteException {
        throw new AssertionError();
      }
    }
  }
  
  private static class ExtraBinderRequestResultReceiver extends ResultReceiver {
    private WeakReference<MediaControllerCompat.MediaControllerImplApi21> mMediaControllerImpl;
    
    public ExtraBinderRequestResultReceiver(MediaControllerCompat.MediaControllerImplApi21 param1MediaControllerImplApi21, Handler param1Handler) {
      super(param1Handler);
      this.mMediaControllerImpl = new WeakReference<MediaControllerCompat.MediaControllerImplApi21>(param1MediaControllerImplApi21);
    }
    
    protected void onReceiveResult(int param1Int, Bundle param1Bundle) {
      MediaControllerCompat.MediaControllerImplApi21 mediaControllerImplApi21 = this.mMediaControllerImpl.get();
      if (mediaControllerImplApi21 != null && param1Bundle != null) {
        MediaControllerCompat.MediaControllerImplApi21.access$202(mediaControllerImplApi21, IMediaSession.Stub.asInterface(BundleCompat.getBinder(param1Bundle, "android.support.v4.media.session.EXTRA_BINDER")));
        mediaControllerImplApi21.processPendingCallbacks();
      } 
    }
  }
  
  private static class ExtraCallback extends Callback.StubCompat {
    ExtraCallback(MediaControllerCompat.Callback param1Callback) {
      super(param1Callback);
    }
    
    public void onExtrasChanged(Bundle param1Bundle) throws RemoteException {
      throw new AssertionError();
    }
    
    public void onMetadataChanged(MediaMetadataCompat param1MediaMetadataCompat) throws RemoteException {
      throw new AssertionError();
    }
    
    public void onQueueChanged(List<MediaSessionCompat.QueueItem> param1List) throws RemoteException {
      throw new AssertionError();
    }
    
    public void onQueueTitleChanged(CharSequence param1CharSequence) throws RemoteException {
      throw new AssertionError();
    }
    
    public void onSessionDestroyed() throws RemoteException {
      throw new AssertionError();
    }
    
    public void onVolumeInfoChanged(ParcelableVolumeInfo param1ParcelableVolumeInfo) throws RemoteException {
      throw new AssertionError();
    }
  }
  
  static class MediaControllerImplApi23 extends MediaControllerImplApi21 {
    public MediaControllerImplApi23(Context param1Context, MediaSessionCompat.Token param1Token) throws RemoteException {
      super(param1Context, param1Token);
    }
    
    public MediaControllerImplApi23(Context param1Context, MediaSessionCompat param1MediaSessionCompat) {
      super(param1Context, param1MediaSessionCompat);
    }
    
    public MediaControllerCompat.TransportControls getTransportControls() {
      Object object = MediaControllerCompatApi21.getTransportControls(this.mControllerObj);
      if (object != null) {
        object = new MediaControllerCompat.TransportControlsApi23(object);
      } else {
        object = null;
      } 
      return (MediaControllerCompat.TransportControls)object;
    }
  }
  
  static class MediaControllerImplApi24 extends MediaControllerImplApi23 {
    public MediaControllerImplApi24(Context param1Context, MediaSessionCompat.Token param1Token) throws RemoteException {
      super(param1Context, param1Token);
    }
    
    public MediaControllerImplApi24(Context param1Context, MediaSessionCompat param1MediaSessionCompat) {
      super(param1Context, param1MediaSessionCompat);
    }
    
    public MediaControllerCompat.TransportControls getTransportControls() {
      Object object = MediaControllerCompatApi21.getTransportControls(this.mControllerObj);
      if (object != null) {
        object = new MediaControllerCompat.TransportControlsApi24(object);
      } else {
        object = null;
      } 
      return (MediaControllerCompat.TransportControls)object;
    }
  }
  
  static class MediaControllerImplBase implements MediaControllerImpl {
    private IMediaSession mBinder;
    
    private MediaControllerCompat.TransportControls mTransportControls;
    
    public MediaControllerImplBase(MediaSessionCompat.Token param1Token) {
      this.mBinder = IMediaSession.Stub.asInterface((IBinder)param1Token.getToken());
    }
    
    public void addQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat) {
      try {
        if ((this.mBinder.getFlags() & 0x4L) != 0L) {
          this.mBinder.addQueueItem(param1MediaDescriptionCompat);
        } else {
          UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
          this("This session doesn't support queue management operations");
          throw unsupportedOperationException;
        } 
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in addQueueItem.", (Throwable)remoteException);
      } 
    }
    
    public void addQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat, int param1Int) {
      try {
        if ((this.mBinder.getFlags() & 0x4L) != 0L) {
          this.mBinder.addQueueItemAt(param1MediaDescriptionCompat, param1Int);
        } else {
          UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
          this("This session doesn't support queue management operations");
          throw unsupportedOperationException;
        } 
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in addQueueItemAt.", (Throwable)remoteException);
      } 
    }
    
    public void adjustVolume(int param1Int1, int param1Int2) {
      try {
        this.mBinder.adjustVolume(param1Int1, param1Int2, (String)null);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in adjustVolume.", (Throwable)remoteException);
      } 
    }
    
    public boolean dispatchMediaButtonEvent(KeyEvent param1KeyEvent) {
      if (param1KeyEvent != null) {
        try {
          this.mBinder.sendMediaButton(param1KeyEvent);
        } catch (RemoteException remoteException) {
          Log.e("MediaControllerCompat", "Dead object in dispatchMediaButtonEvent.", (Throwable)remoteException);
        } 
        return false;
      } 
      throw new IllegalArgumentException("event may not be null.");
    }
    
    public Bundle getExtras() {
      try {
        return this.mBinder.getExtras();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getExtras.", (Throwable)remoteException);
        return null;
      } 
    }
    
    public long getFlags() {
      try {
        return this.mBinder.getFlags();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getFlags.", (Throwable)remoteException);
        return 0L;
      } 
    }
    
    public Object getMediaController() {
      return null;
    }
    
    public MediaMetadataCompat getMetadata() {
      try {
        return this.mBinder.getMetadata();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getMetadata.", (Throwable)remoteException);
        return null;
      } 
    }
    
    public String getPackageName() {
      try {
        return this.mBinder.getPackageName();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getPackageName.", (Throwable)remoteException);
        return null;
      } 
    }
    
    public MediaControllerCompat.PlaybackInfo getPlaybackInfo() {
      try {
        ParcelableVolumeInfo parcelableVolumeInfo = this.mBinder.getVolumeAttributes();
        return new MediaControllerCompat.PlaybackInfo(parcelableVolumeInfo.volumeType, parcelableVolumeInfo.audioStream, parcelableVolumeInfo.controlType, parcelableVolumeInfo.maxVolume, parcelableVolumeInfo.currentVolume);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getPlaybackInfo.", (Throwable)remoteException);
        return null;
      } 
    }
    
    public PlaybackStateCompat getPlaybackState() {
      try {
        return this.mBinder.getPlaybackState();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getPlaybackState.", (Throwable)remoteException);
        return null;
      } 
    }
    
    public List<MediaSessionCompat.QueueItem> getQueue() {
      try {
        return this.mBinder.getQueue();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getQueue.", (Throwable)remoteException);
        return null;
      } 
    }
    
    public CharSequence getQueueTitle() {
      try {
        return this.mBinder.getQueueTitle();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getQueueTitle.", (Throwable)remoteException);
        return null;
      } 
    }
    
    public int getRatingType() {
      try {
        return this.mBinder.getRatingType();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getRatingType.", (Throwable)remoteException);
        return 0;
      } 
    }
    
    public int getRepeatMode() {
      try {
        return this.mBinder.getRepeatMode();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getRepeatMode.", (Throwable)remoteException);
        return -1;
      } 
    }
    
    public PendingIntent getSessionActivity() {
      try {
        return this.mBinder.getLaunchPendingIntent();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getSessionActivity.", (Throwable)remoteException);
        return null;
      } 
    }
    
    public int getShuffleMode() {
      try {
        return this.mBinder.getShuffleMode();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in getShuffleMode.", (Throwable)remoteException);
        return -1;
      } 
    }
    
    public MediaControllerCompat.TransportControls getTransportControls() {
      if (this.mTransportControls == null)
        this.mTransportControls = new MediaControllerCompat.TransportControlsBase(this.mBinder); 
      return this.mTransportControls;
    }
    
    public boolean isCaptioningEnabled() {
      try {
        return this.mBinder.isCaptioningEnabled();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in isCaptioningEnabled.", (Throwable)remoteException);
        return false;
      } 
    }
    
    public boolean isSessionReady() {
      return true;
    }
    
    public void registerCallback(MediaControllerCompat.Callback param1Callback, Handler param1Handler) {
      if (param1Callback != null) {
        try {
          this.mBinder.asBinder().linkToDeath(param1Callback, 0);
          this.mBinder.registerCallbackListener((IMediaControllerCallback)param1Callback.mCallbackObj);
        } catch (RemoteException remoteException) {
          Log.e("MediaControllerCompat", "Dead object in registerCallback.", (Throwable)remoteException);
          param1Callback.onSessionDestroyed();
        } 
        return;
      } 
      throw new IllegalArgumentException("callback may not be null.");
    }
    
    public void removeQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat) {
      try {
        if ((this.mBinder.getFlags() & 0x4L) != 0L) {
          this.mBinder.removeQueueItem(param1MediaDescriptionCompat);
        } else {
          UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
          this("This session doesn't support queue management operations");
          throw unsupportedOperationException;
        } 
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in removeQueueItem.", (Throwable)remoteException);
      } 
    }
    
    public void sendCommand(String param1String, Bundle param1Bundle, ResultReceiver param1ResultReceiver) {
      try {
        IMediaSession iMediaSession = this.mBinder;
        MediaSessionCompat.ResultReceiverWrapper resultReceiverWrapper = new MediaSessionCompat.ResultReceiverWrapper();
        this(param1ResultReceiver);
        iMediaSession.sendCommand(param1String, param1Bundle, resultReceiverWrapper);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in sendCommand.", (Throwable)remoteException);
      } 
    }
    
    public void setVolumeTo(int param1Int1, int param1Int2) {
      try {
        this.mBinder.setVolumeTo(param1Int1, param1Int2, (String)null);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in setVolumeTo.", (Throwable)remoteException);
      } 
    }
    
    public void unregisterCallback(MediaControllerCompat.Callback param1Callback) {
      if (param1Callback != null) {
        try {
          this.mBinder.unregisterCallbackListener((IMediaControllerCallback)param1Callback.mCallbackObj);
          this.mBinder.asBinder().unlinkToDeath(param1Callback, 0);
        } catch (RemoteException remoteException) {
          Log.e("MediaControllerCompat", "Dead object in unregisterCallback.", (Throwable)remoteException);
        } 
        return;
      } 
      throw new IllegalArgumentException("callback may not be null.");
    }
  }
  
  public static final class PlaybackInfo {
    public static final int PLAYBACK_TYPE_LOCAL = 1;
    
    public static final int PLAYBACK_TYPE_REMOTE = 2;
    
    private final int mAudioStream;
    
    private final int mCurrentVolume;
    
    private final int mMaxVolume;
    
    private final int mPlaybackType;
    
    private final int mVolumeControl;
    
    PlaybackInfo(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
      this.mPlaybackType = param1Int1;
      this.mAudioStream = param1Int2;
      this.mVolumeControl = param1Int3;
      this.mMaxVolume = param1Int4;
      this.mCurrentVolume = param1Int5;
    }
    
    public int getAudioStream() {
      return this.mAudioStream;
    }
    
    public int getCurrentVolume() {
      return this.mCurrentVolume;
    }
    
    public int getMaxVolume() {
      return this.mMaxVolume;
    }
    
    public int getPlaybackType() {
      return this.mPlaybackType;
    }
    
    public int getVolumeControl() {
      return this.mVolumeControl;
    }
  }
  
  public static abstract class TransportControls {
    public static final String EXTRA_LEGACY_STREAM_TYPE = "android.media.session.extra.LEGACY_STREAM_TYPE";
    
    public abstract void fastForward();
    
    public abstract void pause();
    
    public abstract void play();
    
    public abstract void playFromMediaId(String param1String, Bundle param1Bundle);
    
    public abstract void playFromSearch(String param1String, Bundle param1Bundle);
    
    public abstract void playFromUri(Uri param1Uri, Bundle param1Bundle);
    
    public abstract void prepare();
    
    public abstract void prepareFromMediaId(String param1String, Bundle param1Bundle);
    
    public abstract void prepareFromSearch(String param1String, Bundle param1Bundle);
    
    public abstract void prepareFromUri(Uri param1Uri, Bundle param1Bundle);
    
    public abstract void rewind();
    
    public abstract void seekTo(long param1Long);
    
    public abstract void sendCustomAction(PlaybackStateCompat.CustomAction param1CustomAction, Bundle param1Bundle);
    
    public abstract void sendCustomAction(String param1String, Bundle param1Bundle);
    
    public abstract void setCaptioningEnabled(boolean param1Boolean);
    
    public abstract void setRating(RatingCompat param1RatingCompat);
    
    public abstract void setRating(RatingCompat param1RatingCompat, Bundle param1Bundle);
    
    public abstract void setRepeatMode(int param1Int);
    
    public abstract void setShuffleMode(int param1Int);
    
    public abstract void skipToNext();
    
    public abstract void skipToPrevious();
    
    public abstract void skipToQueueItem(long param1Long);
    
    public abstract void stop();
  }
  
  static class TransportControlsApi21 extends TransportControls {
    protected final Object mControlsObj;
    
    public TransportControlsApi21(Object param1Object) {
      this.mControlsObj = param1Object;
    }
    
    public void fastForward() {
      MediaControllerCompatApi21.TransportControls.fastForward(this.mControlsObj);
    }
    
    public void pause() {
      MediaControllerCompatApi21.TransportControls.pause(this.mControlsObj);
    }
    
    public void play() {
      MediaControllerCompatApi21.TransportControls.play(this.mControlsObj);
    }
    
    public void playFromMediaId(String param1String, Bundle param1Bundle) {
      MediaControllerCompatApi21.TransportControls.playFromMediaId(this.mControlsObj, param1String, param1Bundle);
    }
    
    public void playFromSearch(String param1String, Bundle param1Bundle) {
      MediaControllerCompatApi21.TransportControls.playFromSearch(this.mControlsObj, param1String, param1Bundle);
    }
    
    public void playFromUri(Uri param1Uri, Bundle param1Bundle) {
      if (param1Uri != null && !Uri.EMPTY.equals(param1Uri)) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("android.support.v4.media.session.action.ARGUMENT_URI", (Parcelable)param1Uri);
        bundle.putParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS", (Parcelable)param1Bundle);
        sendCustomAction("android.support.v4.media.session.action.PLAY_FROM_URI", bundle);
        return;
      } 
      throw new IllegalArgumentException("You must specify a non-empty Uri for playFromUri.");
    }
    
    public void prepare() {
      sendCustomAction("android.support.v4.media.session.action.PREPARE", (Bundle)null);
    }
    
    public void prepareFromMediaId(String param1String, Bundle param1Bundle) {
      Bundle bundle = new Bundle();
      bundle.putString("android.support.v4.media.session.action.ARGUMENT_MEDIA_ID", param1String);
      bundle.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", param1Bundle);
      sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID", bundle);
    }
    
    public void prepareFromSearch(String param1String, Bundle param1Bundle) {
      Bundle bundle = new Bundle();
      bundle.putString("android.support.v4.media.session.action.ARGUMENT_QUERY", param1String);
      bundle.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", param1Bundle);
      sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_SEARCH", bundle);
    }
    
    public void prepareFromUri(Uri param1Uri, Bundle param1Bundle) {
      Bundle bundle = new Bundle();
      bundle.putParcelable("android.support.v4.media.session.action.ARGUMENT_URI", (Parcelable)param1Uri);
      bundle.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", param1Bundle);
      sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_URI", bundle);
    }
    
    public void rewind() {
      MediaControllerCompatApi21.TransportControls.rewind(this.mControlsObj);
    }
    
    public void seekTo(long param1Long) {
      MediaControllerCompatApi21.TransportControls.seekTo(this.mControlsObj, param1Long);
    }
    
    public void sendCustomAction(PlaybackStateCompat.CustomAction param1CustomAction, Bundle param1Bundle) {
      MediaControllerCompat.validateCustomAction(param1CustomAction.getAction(), param1Bundle);
      MediaControllerCompatApi21.TransportControls.sendCustomAction(this.mControlsObj, param1CustomAction.getAction(), param1Bundle);
    }
    
    public void sendCustomAction(String param1String, Bundle param1Bundle) {
      MediaControllerCompat.validateCustomAction(param1String, param1Bundle);
      MediaControllerCompatApi21.TransportControls.sendCustomAction(this.mControlsObj, param1String, param1Bundle);
    }
    
    public void setCaptioningEnabled(boolean param1Boolean) {
      Bundle bundle = new Bundle();
      bundle.putBoolean("android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED", param1Boolean);
      sendCustomAction("android.support.v4.media.session.action.SET_CAPTIONING_ENABLED", bundle);
    }
    
    public void setRating(RatingCompat param1RatingCompat) {
      Object object = this.mControlsObj;
      if (param1RatingCompat != null) {
        Object object1 = param1RatingCompat.getRating();
      } else {
        param1RatingCompat = null;
      } 
      MediaControllerCompatApi21.TransportControls.setRating(object, param1RatingCompat);
    }
    
    public void setRating(RatingCompat param1RatingCompat, Bundle param1Bundle) {
      Bundle bundle = new Bundle();
      bundle.putParcelable("android.support.v4.media.session.action.ARGUMENT_RATING", (Parcelable)param1RatingCompat);
      bundle.putParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS", (Parcelable)param1Bundle);
      sendCustomAction("android.support.v4.media.session.action.SET_RATING", bundle);
    }
    
    public void setRepeatMode(int param1Int) {
      Bundle bundle = new Bundle();
      bundle.putInt("android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE", param1Int);
      sendCustomAction("android.support.v4.media.session.action.SET_REPEAT_MODE", bundle);
    }
    
    public void setShuffleMode(int param1Int) {
      Bundle bundle = new Bundle();
      bundle.putInt("android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE", param1Int);
      sendCustomAction("android.support.v4.media.session.action.SET_SHUFFLE_MODE", bundle);
    }
    
    public void skipToNext() {
      MediaControllerCompatApi21.TransportControls.skipToNext(this.mControlsObj);
    }
    
    public void skipToPrevious() {
      MediaControllerCompatApi21.TransportControls.skipToPrevious(this.mControlsObj);
    }
    
    public void skipToQueueItem(long param1Long) {
      MediaControllerCompatApi21.TransportControls.skipToQueueItem(this.mControlsObj, param1Long);
    }
    
    public void stop() {
      MediaControllerCompatApi21.TransportControls.stop(this.mControlsObj);
    }
  }
  
  static class TransportControlsApi23 extends TransportControlsApi21 {
    public TransportControlsApi23(Object param1Object) {
      super(param1Object);
    }
    
    public void playFromUri(Uri param1Uri, Bundle param1Bundle) {
      MediaControllerCompatApi23.TransportControls.playFromUri(this.mControlsObj, param1Uri, param1Bundle);
    }
  }
  
  static class TransportControlsApi24 extends TransportControlsApi23 {
    public TransportControlsApi24(Object param1Object) {
      super(param1Object);
    }
    
    public void prepare() {
      MediaControllerCompatApi24.TransportControls.prepare(this.mControlsObj);
    }
    
    public void prepareFromMediaId(String param1String, Bundle param1Bundle) {
      MediaControllerCompatApi24.TransportControls.prepareFromMediaId(this.mControlsObj, param1String, param1Bundle);
    }
    
    public void prepareFromSearch(String param1String, Bundle param1Bundle) {
      MediaControllerCompatApi24.TransportControls.prepareFromSearch(this.mControlsObj, param1String, param1Bundle);
    }
    
    public void prepareFromUri(Uri param1Uri, Bundle param1Bundle) {
      MediaControllerCompatApi24.TransportControls.prepareFromUri(this.mControlsObj, param1Uri, param1Bundle);
    }
  }
  
  static class TransportControlsBase extends TransportControls {
    private IMediaSession mBinder;
    
    public TransportControlsBase(IMediaSession param1IMediaSession) {
      this.mBinder = param1IMediaSession;
    }
    
    public void fastForward() {
      try {
        this.mBinder.fastForward();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in fastForward.", (Throwable)remoteException);
      } 
    }
    
    public void pause() {
      try {
        this.mBinder.pause();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in pause.", (Throwable)remoteException);
      } 
    }
    
    public void play() {
      try {
        this.mBinder.play();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in play.", (Throwable)remoteException);
      } 
    }
    
    public void playFromMediaId(String param1String, Bundle param1Bundle) {
      try {
        this.mBinder.playFromMediaId(param1String, param1Bundle);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in playFromMediaId.", (Throwable)remoteException);
      } 
    }
    
    public void playFromSearch(String param1String, Bundle param1Bundle) {
      try {
        this.mBinder.playFromSearch(param1String, param1Bundle);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in playFromSearch.", (Throwable)remoteException);
      } 
    }
    
    public void playFromUri(Uri param1Uri, Bundle param1Bundle) {
      try {
        this.mBinder.playFromUri(param1Uri, param1Bundle);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in playFromUri.", (Throwable)remoteException);
      } 
    }
    
    public void prepare() {
      try {
        this.mBinder.prepare();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in prepare.", (Throwable)remoteException);
      } 
    }
    
    public void prepareFromMediaId(String param1String, Bundle param1Bundle) {
      try {
        this.mBinder.prepareFromMediaId(param1String, param1Bundle);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in prepareFromMediaId.", (Throwable)remoteException);
      } 
    }
    
    public void prepareFromSearch(String param1String, Bundle param1Bundle) {
      try {
        this.mBinder.prepareFromSearch(param1String, param1Bundle);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in prepareFromSearch.", (Throwable)remoteException);
      } 
    }
    
    public void prepareFromUri(Uri param1Uri, Bundle param1Bundle) {
      try {
        this.mBinder.prepareFromUri(param1Uri, param1Bundle);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in prepareFromUri.", (Throwable)remoteException);
      } 
    }
    
    public void rewind() {
      try {
        this.mBinder.rewind();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in rewind.", (Throwable)remoteException);
      } 
    }
    
    public void seekTo(long param1Long) {
      try {
        this.mBinder.seekTo(param1Long);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in seekTo.", (Throwable)remoteException);
      } 
    }
    
    public void sendCustomAction(PlaybackStateCompat.CustomAction param1CustomAction, Bundle param1Bundle) {
      sendCustomAction(param1CustomAction.getAction(), param1Bundle);
    }
    
    public void sendCustomAction(String param1String, Bundle param1Bundle) {
      MediaControllerCompat.validateCustomAction(param1String, param1Bundle);
      try {
        this.mBinder.sendCustomAction(param1String, param1Bundle);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in sendCustomAction.", (Throwable)remoteException);
      } 
    }
    
    public void setCaptioningEnabled(boolean param1Boolean) {
      try {
        this.mBinder.setCaptioningEnabled(param1Boolean);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in setCaptioningEnabled.", (Throwable)remoteException);
      } 
    }
    
    public void setRating(RatingCompat param1RatingCompat) {
      try {
        this.mBinder.rate(param1RatingCompat);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in setRating.", (Throwable)remoteException);
      } 
    }
    
    public void setRating(RatingCompat param1RatingCompat, Bundle param1Bundle) {
      try {
        this.mBinder.rateWithExtras(param1RatingCompat, param1Bundle);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in setRating.", (Throwable)remoteException);
      } 
    }
    
    public void setRepeatMode(int param1Int) {
      try {
        this.mBinder.setRepeatMode(param1Int);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in setRepeatMode.", (Throwable)remoteException);
      } 
    }
    
    public void setShuffleMode(int param1Int) {
      try {
        this.mBinder.setShuffleMode(param1Int);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in setShuffleMode.", (Throwable)remoteException);
      } 
    }
    
    public void skipToNext() {
      try {
        this.mBinder.next();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in skipToNext.", (Throwable)remoteException);
      } 
    }
    
    public void skipToPrevious() {
      try {
        this.mBinder.previous();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in skipToPrevious.", (Throwable)remoteException);
      } 
    }
    
    public void skipToQueueItem(long param1Long) {
      try {
        this.mBinder.skipToQueueItem(param1Long);
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in skipToQueueItem.", (Throwable)remoteException);
      } 
    }
    
    public void stop() {
      try {
        this.mBinder.stop();
      } catch (RemoteException remoteException) {
        Log.e("MediaControllerCompat", "Dead object in stop.", (Throwable)remoteException);
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\session\MediaControllerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */