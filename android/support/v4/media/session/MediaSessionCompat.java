package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.RemoteControlClient;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.VolumeProviderCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MediaSessionCompat {
  static final String ACTION_ARGUMENT_CAPTIONING_ENABLED = "android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED";
  
  static final String ACTION_ARGUMENT_EXTRAS = "android.support.v4.media.session.action.ARGUMENT_EXTRAS";
  
  static final String ACTION_ARGUMENT_MEDIA_ID = "android.support.v4.media.session.action.ARGUMENT_MEDIA_ID";
  
  static final String ACTION_ARGUMENT_QUERY = "android.support.v4.media.session.action.ARGUMENT_QUERY";
  
  static final String ACTION_ARGUMENT_RATING = "android.support.v4.media.session.action.ARGUMENT_RATING";
  
  static final String ACTION_ARGUMENT_REPEAT_MODE = "android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE";
  
  static final String ACTION_ARGUMENT_SHUFFLE_MODE = "android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE";
  
  static final String ACTION_ARGUMENT_URI = "android.support.v4.media.session.action.ARGUMENT_URI";
  
  public static final String ACTION_FLAG_AS_INAPPROPRIATE = "android.support.v4.media.session.action.FLAG_AS_INAPPROPRIATE";
  
  public static final String ACTION_FOLLOW = "android.support.v4.media.session.action.FOLLOW";
  
  static final String ACTION_PLAY_FROM_URI = "android.support.v4.media.session.action.PLAY_FROM_URI";
  
  static final String ACTION_PREPARE = "android.support.v4.media.session.action.PREPARE";
  
  static final String ACTION_PREPARE_FROM_MEDIA_ID = "android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID";
  
  static final String ACTION_PREPARE_FROM_SEARCH = "android.support.v4.media.session.action.PREPARE_FROM_SEARCH";
  
  static final String ACTION_PREPARE_FROM_URI = "android.support.v4.media.session.action.PREPARE_FROM_URI";
  
  static final String ACTION_SET_CAPTIONING_ENABLED = "android.support.v4.media.session.action.SET_CAPTIONING_ENABLED";
  
  static final String ACTION_SET_RATING = "android.support.v4.media.session.action.SET_RATING";
  
  static final String ACTION_SET_REPEAT_MODE = "android.support.v4.media.session.action.SET_REPEAT_MODE";
  
  static final String ACTION_SET_SHUFFLE_MODE = "android.support.v4.media.session.action.SET_SHUFFLE_MODE";
  
  public static final String ACTION_SKIP_AD = "android.support.v4.media.session.action.SKIP_AD";
  
  public static final String ACTION_UNFOLLOW = "android.support.v4.media.session.action.UNFOLLOW";
  
  public static final String ARGUMENT_MEDIA_ATTRIBUTE = "android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE";
  
  public static final String ARGUMENT_MEDIA_ATTRIBUTE_VALUE = "android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE_VALUE";
  
  static final String EXTRA_BINDER = "android.support.v4.media.session.EXTRA_BINDER";
  
  public static final int FLAG_HANDLES_MEDIA_BUTTONS = 1;
  
  public static final int FLAG_HANDLES_QUEUE_COMMANDS = 4;
  
  public static final int FLAG_HANDLES_TRANSPORT_CONTROLS = 2;
  
  private static final int MAX_BITMAP_SIZE_IN_DP = 320;
  
  public static final int MEDIA_ATTRIBUTE_ALBUM = 1;
  
  public static final int MEDIA_ATTRIBUTE_ARTIST = 0;
  
  public static final int MEDIA_ATTRIBUTE_PLAYLIST = 2;
  
  static final String TAG = "MediaSessionCompat";
  
  static int sMaxBitmapSize;
  
  private final ArrayList<OnActiveChangeListener> mActiveListeners = new ArrayList<OnActiveChangeListener>();
  
  private final MediaControllerCompat mController;
  
  private final MediaSessionImpl mImpl;
  
  private MediaSessionCompat(Context paramContext, MediaSessionImpl paramMediaSessionImpl) {
    this.mImpl = paramMediaSessionImpl;
    if (Build.VERSION.SDK_INT >= 21 && !MediaSessionCompatApi21.hasCallback(paramMediaSessionImpl.getMediaSession()))
      setCallback(new Callback() {
          
          }); 
    this.mController = new MediaControllerCompat(paramContext, this);
  }
  
  public MediaSessionCompat(Context paramContext, String paramString) {
    this(paramContext, paramString, null, null);
  }
  
  public MediaSessionCompat(Context paramContext, String paramString, ComponentName paramComponentName, PendingIntent paramPendingIntent) {
    if (paramContext != null) {
      if (!TextUtils.isEmpty(paramString)) {
        ComponentName componentName = paramComponentName;
        if (paramComponentName == null) {
          paramComponentName = MediaButtonReceiver.getMediaButtonReceiverComponent(paramContext);
          componentName = paramComponentName;
          if (paramComponentName == null) {
            Log.w("MediaSessionCompat", "Couldn't find a unique registered media button receiver in the given context.");
            componentName = paramComponentName;
          } 
        } 
        PendingIntent pendingIntent = paramPendingIntent;
        if (componentName != null) {
          pendingIntent = paramPendingIntent;
          if (paramPendingIntent == null) {
            Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
            intent.setComponent(componentName);
            pendingIntent = PendingIntent.getBroadcast(paramContext, 0, intent, 0);
          } 
        } 
        if (Build.VERSION.SDK_INT >= 21) {
          this.mImpl = new MediaSessionImplApi21(paramContext, paramString);
          setCallback(new Callback() {
              
              });
          this.mImpl.setMediaButtonReceiver(pendingIntent);
        } else if (Build.VERSION.SDK_INT >= 19) {
          this.mImpl = new MediaSessionImplApi19(paramContext, paramString, componentName, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= 18) {
          this.mImpl = new MediaSessionImplApi18(paramContext, paramString, componentName, pendingIntent);
        } else {
          this.mImpl = new MediaSessionImplBase(paramContext, paramString, componentName, pendingIntent);
        } 
        this.mController = new MediaControllerCompat(paramContext, this);
        if (sMaxBitmapSize == 0)
          sMaxBitmapSize = (int)TypedValue.applyDimension(1, 320.0F, paramContext.getResources().getDisplayMetrics()); 
        return;
      } 
      throw new IllegalArgumentException("tag must not be null or empty");
    } 
    throw new IllegalArgumentException("context must not be null");
  }
  
  public static MediaSessionCompat fromMediaSession(Context paramContext, Object paramObject) {
    return (paramContext != null && paramObject != null && Build.VERSION.SDK_INT >= 21) ? new MediaSessionCompat(paramContext, new MediaSessionImplApi21(paramObject)) : null;
  }
  
  private static PlaybackStateCompat getStateWithUpdatedPosition(PlaybackStateCompat paramPlaybackStateCompat, MediaMetadataCompat paramMediaMetadataCompat) {
    PlaybackStateCompat playbackStateCompat = paramPlaybackStateCompat;
    if (paramPlaybackStateCompat != null) {
      long l1 = paramPlaybackStateCompat.getPosition();
      long l2 = -1L;
      if (l1 == -1L) {
        playbackStateCompat = paramPlaybackStateCompat;
      } else {
        if (paramPlaybackStateCompat.getState() != 3 && paramPlaybackStateCompat.getState() != 4) {
          playbackStateCompat = paramPlaybackStateCompat;
          if (paramPlaybackStateCompat.getState() == 5) {
            l1 = paramPlaybackStateCompat.getLastPositionUpdateTime();
            playbackStateCompat = paramPlaybackStateCompat;
          } 
          return playbackStateCompat;
        } 
        l1 = paramPlaybackStateCompat.getLastPositionUpdateTime();
        playbackStateCompat = paramPlaybackStateCompat;
      } 
    } 
    return playbackStateCompat;
  }
  
  public void addOnActiveChangeListener(OnActiveChangeListener paramOnActiveChangeListener) {
    if (paramOnActiveChangeListener != null) {
      this.mActiveListeners.add(paramOnActiveChangeListener);
      return;
    } 
    throw new IllegalArgumentException("Listener may not be null");
  }
  
  public String getCallingPackage() {
    return this.mImpl.getCallingPackage();
  }
  
  public MediaControllerCompat getController() {
    return this.mController;
  }
  
  public Object getMediaSession() {
    return this.mImpl.getMediaSession();
  }
  
  public Object getRemoteControlClient() {
    return this.mImpl.getRemoteControlClient();
  }
  
  public Token getSessionToken() {
    return this.mImpl.getSessionToken();
  }
  
  public boolean isActive() {
    return this.mImpl.isActive();
  }
  
  public void release() {
    this.mImpl.release();
  }
  
  public void removeOnActiveChangeListener(OnActiveChangeListener paramOnActiveChangeListener) {
    if (paramOnActiveChangeListener != null) {
      this.mActiveListeners.remove(paramOnActiveChangeListener);
      return;
    } 
    throw new IllegalArgumentException("Listener may not be null");
  }
  
  public void sendSessionEvent(String paramString, Bundle paramBundle) {
    if (!TextUtils.isEmpty(paramString)) {
      this.mImpl.sendSessionEvent(paramString, paramBundle);
      return;
    } 
    throw new IllegalArgumentException("event cannot be null or empty");
  }
  
  public void setActive(boolean paramBoolean) {
    this.mImpl.setActive(paramBoolean);
    Iterator<OnActiveChangeListener> iterator = this.mActiveListeners.iterator();
    while (iterator.hasNext())
      ((OnActiveChangeListener)iterator.next()).onActiveChanged(); 
  }
  
  public void setCallback(Callback paramCallback) {
    setCallback(paramCallback, null);
  }
  
  public void setCallback(Callback paramCallback, Handler paramHandler) {
    MediaSessionImpl mediaSessionImpl = this.mImpl;
    if (paramHandler == null)
      paramHandler = new Handler(); 
    mediaSessionImpl.setCallback(paramCallback, paramHandler);
  }
  
  public void setCaptioningEnabled(boolean paramBoolean) {
    this.mImpl.setCaptioningEnabled(paramBoolean);
  }
  
  public void setExtras(Bundle paramBundle) {
    this.mImpl.setExtras(paramBundle);
  }
  
  public void setFlags(int paramInt) {
    this.mImpl.setFlags(paramInt);
  }
  
  public void setMediaButtonReceiver(PendingIntent paramPendingIntent) {
    this.mImpl.setMediaButtonReceiver(paramPendingIntent);
  }
  
  public void setMetadata(MediaMetadataCompat paramMediaMetadataCompat) {
    this.mImpl.setMetadata(paramMediaMetadataCompat);
  }
  
  public void setPlaybackState(PlaybackStateCompat paramPlaybackStateCompat) {
    this.mImpl.setPlaybackState(paramPlaybackStateCompat);
  }
  
  public void setPlaybackToLocal(int paramInt) {
    this.mImpl.setPlaybackToLocal(paramInt);
  }
  
  public void setPlaybackToRemote(VolumeProviderCompat paramVolumeProviderCompat) {
    if (paramVolumeProviderCompat != null) {
      this.mImpl.setPlaybackToRemote(paramVolumeProviderCompat);
      return;
    } 
    throw new IllegalArgumentException("volumeProvider may not be null!");
  }
  
  public void setQueue(List<QueueItem> paramList) {
    this.mImpl.setQueue(paramList);
  }
  
  public void setQueueTitle(CharSequence paramCharSequence) {
    this.mImpl.setQueueTitle(paramCharSequence);
  }
  
  public void setRatingType(int paramInt) {
    this.mImpl.setRatingType(paramInt);
  }
  
  public void setRepeatMode(int paramInt) {
    this.mImpl.setRepeatMode(paramInt);
  }
  
  public void setSessionActivity(PendingIntent paramPendingIntent) {
    this.mImpl.setSessionActivity(paramPendingIntent);
  }
  
  public void setShuffleMode(int paramInt) {
    this.mImpl.setShuffleMode(paramInt);
  }
  
  public static abstract class Callback {
    private CallbackHandler mCallbackHandler = null;
    
    final Object mCallbackObj;
    
    private boolean mMediaPlayPauseKeyPending;
    
    private WeakReference<MediaSessionCompat.MediaSessionImpl> mSessionImpl;
    
    public Callback() {
      if (Build.VERSION.SDK_INT >= 24) {
        this.mCallbackObj = MediaSessionCompatApi24.createCallback(new StubApi24());
      } else if (Build.VERSION.SDK_INT >= 23) {
        this.mCallbackObj = MediaSessionCompatApi23.createCallback(new StubApi23());
      } else if (Build.VERSION.SDK_INT >= 21) {
        this.mCallbackObj = MediaSessionCompatApi21.createCallback(new StubApi21());
      } else {
        this.mCallbackObj = null;
      } 
    }
    
    private void handleMediaPlayPauseKeySingleTapIfPending() {
      long l;
      boolean bool2;
      boolean bool3;
      if (!this.mMediaPlayPauseKeyPending)
        return; 
      boolean bool1 = false;
      this.mMediaPlayPauseKeyPending = false;
      this.mCallbackHandler.removeMessages(1);
      MediaSessionCompat.MediaSessionImpl mediaSessionImpl = this.mSessionImpl.get();
      if (mediaSessionImpl == null)
        return; 
      PlaybackStateCompat playbackStateCompat = mediaSessionImpl.getPlaybackState();
      if (playbackStateCompat == null) {
        l = 0L;
      } else {
        l = playbackStateCompat.getActions();
      } 
      if (playbackStateCompat != null && playbackStateCompat.getState() == 3) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      if ((0x204L & l) != 0L) {
        bool3 = true;
      } else {
        bool3 = false;
      } 
      if ((l & 0x202L) != 0L)
        bool1 = true; 
      if (bool2 && bool1) {
        onPause();
      } else if (!bool2 && bool3) {
        onPlay();
      } 
    }
    
    private void setSessionImpl(MediaSessionCompat.MediaSessionImpl param1MediaSessionImpl, Handler param1Handler) {
      this.mSessionImpl = new WeakReference<MediaSessionCompat.MediaSessionImpl>(param1MediaSessionImpl);
      CallbackHandler callbackHandler = this.mCallbackHandler;
      if (callbackHandler != null)
        callbackHandler.removeCallbacksAndMessages(null); 
      this.mCallbackHandler = new CallbackHandler(param1Handler.getLooper());
    }
    
    public void onAddQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat) {}
    
    public void onAddQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat, int param1Int) {}
    
    public void onCommand(String param1String, Bundle param1Bundle, ResultReceiver param1ResultReceiver) {}
    
    public void onCustomAction(String param1String, Bundle param1Bundle) {}
    
    public void onFastForward() {}
    
    public boolean onMediaButtonEvent(Intent param1Intent) {
      MediaSessionCompat.MediaSessionImpl mediaSessionImpl = this.mSessionImpl.get();
      if (mediaSessionImpl != null && this.mCallbackHandler != null) {
        KeyEvent keyEvent = (KeyEvent)param1Intent.getParcelableExtra("android.intent.extra.KEY_EVENT");
        if (keyEvent != null && keyEvent.getAction() == 0) {
          int i = keyEvent.getKeyCode();
          if (i != 79 && i != 85) {
            handleMediaPlayPauseKeySingleTapIfPending();
            return false;
          } 
          if (keyEvent.getRepeatCount() > 0) {
            handleMediaPlayPauseKeySingleTapIfPending();
          } else if (this.mMediaPlayPauseKeyPending) {
            long l;
            this.mCallbackHandler.removeMessages(1);
            this.mMediaPlayPauseKeyPending = false;
            PlaybackStateCompat playbackStateCompat = mediaSessionImpl.getPlaybackState();
            if (playbackStateCompat == null) {
              l = 0L;
            } else {
              l = playbackStateCompat.getActions();
            } 
            if ((l & 0x20L) != 0L)
              onSkipToNext(); 
          } else {
            this.mMediaPlayPauseKeyPending = true;
            this.mCallbackHandler.sendEmptyMessageDelayed(1, ViewConfiguration.getDoubleTapTimeout());
          } 
          return true;
        } 
      } 
      return false;
    }
    
    public void onPause() {}
    
    public void onPlay() {}
    
    public void onPlayFromMediaId(String param1String, Bundle param1Bundle) {}
    
    public void onPlayFromSearch(String param1String, Bundle param1Bundle) {}
    
    public void onPlayFromUri(Uri param1Uri, Bundle param1Bundle) {}
    
    public void onPrepare() {}
    
    public void onPrepareFromMediaId(String param1String, Bundle param1Bundle) {}
    
    public void onPrepareFromSearch(String param1String, Bundle param1Bundle) {}
    
    public void onPrepareFromUri(Uri param1Uri, Bundle param1Bundle) {}
    
    public void onRemoveQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat) {}
    
    @Deprecated
    public void onRemoveQueueItemAt(int param1Int) {}
    
    public void onRewind() {}
    
    public void onSeekTo(long param1Long) {}
    
    public void onSetCaptioningEnabled(boolean param1Boolean) {}
    
    public void onSetRating(RatingCompat param1RatingCompat) {}
    
    public void onSetRating(RatingCompat param1RatingCompat, Bundle param1Bundle) {}
    
    public void onSetRepeatMode(int param1Int) {}
    
    public void onSetShuffleMode(int param1Int) {}
    
    public void onSkipToNext() {}
    
    public void onSkipToPrevious() {}
    
    public void onSkipToQueueItem(long param1Long) {}
    
    public void onStop() {}
    
    private class CallbackHandler extends Handler {
      private static final int MSG_MEDIA_PLAY_PAUSE_KEY_DOUBLE_TAP_TIMEOUT = 1;
      
      CallbackHandler(Looper param2Looper) {
        super(param2Looper);
      }
      
      public void handleMessage(Message param2Message) {
        if (param2Message.what == 1)
          MediaSessionCompat.Callback.this.handleMediaPlayPauseKeySingleTapIfPending(); 
      }
    }
    
    private class StubApi21 implements MediaSessionCompatApi21.Callback {
      public void onCommand(String param2String, Bundle param2Bundle, ResultReceiver param2ResultReceiver) {
        try {
          IBinder iBinder1;
          boolean bool = param2String.equals("android.support.v4.media.session.command.GET_EXTRA_BINDER");
          IBinder iBinder2 = null;
          IMediaSession iMediaSession = null;
          if (bool) {
            MediaSessionCompat.MediaSessionImplApi21 mediaSessionImplApi21 = MediaSessionCompat.Callback.this.mSessionImpl.get();
            if (mediaSessionImplApi21 != null) {
              param2Bundle = new Bundle();
              this();
              IMediaSession iMediaSession1 = mediaSessionImplApi21.getSessionToken().getExtraBinder();
              if (iMediaSession1 == null) {
                iMediaSession1 = iMediaSession;
              } else {
                iBinder1 = iMediaSession1.asBinder();
              } 
              BundleCompat.putBinder(param2Bundle, "android.support.v4.media.session.EXTRA_BINDER", iBinder1);
              param2ResultReceiver.send(0, param2Bundle);
            } 
          } else {
            bool = iBinder1.equals("android.support.v4.media.session.command.ADD_QUEUE_ITEM");
            if (bool) {
              param2Bundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
              MediaSessionCompat.Callback.this.onAddQueueItem((MediaDescriptionCompat)param2Bundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"));
            } else {
              bool = iBinder1.equals("android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT");
              if (bool) {
                param2Bundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
                MediaSessionCompat.Callback.this.onAddQueueItem((MediaDescriptionCompat)param2Bundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"), param2Bundle.getInt("android.support.v4.media.session.command.ARGUMENT_INDEX"));
              } else if (iBinder1.equals("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM")) {
                param2Bundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
                MediaSessionCompat.Callback.this.onRemoveQueueItem((MediaDescriptionCompat)param2Bundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"));
              } else {
                MediaSessionCompat.QueueItem queueItem;
                MediaSessionCompat.MediaSessionImplApi21 mediaSessionImplApi21;
                if (iBinder1.equals("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT")) {
                  mediaSessionImplApi21 = MediaSessionCompat.Callback.this.mSessionImpl.get();
                  if (mediaSessionImplApi21 != null && mediaSessionImplApi21.mQueue != null) {
                    int i = param2Bundle.getInt("android.support.v4.media.session.command.ARGUMENT_INDEX", -1);
                    iBinder1 = iBinder2;
                    if (i >= 0) {
                      iBinder1 = iBinder2;
                      if (i < mediaSessionImplApi21.mQueue.size())
                        queueItem = mediaSessionImplApi21.mQueue.get(i); 
                    } 
                    if (queueItem != null)
                      MediaSessionCompat.Callback.this.onRemoveQueueItem(queueItem.getDescription()); 
                  } 
                } else {
                  MediaSessionCompat.Callback.this.onCommand((String)queueItem, param2Bundle, (ResultReceiver)mediaSessionImplApi21);
                } 
              } 
            } 
          } 
        } catch (BadParcelableException badParcelableException) {
          Log.e("MediaSessionCompat", "Could not unparcel the extra data.");
        } 
      }
      
      public void onCustomAction(String param2String, Bundle param2Bundle) {
        Uri uri;
        if (param2String.equals("android.support.v4.media.session.action.PLAY_FROM_URI")) {
          uri = (Uri)param2Bundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_URI");
          param2Bundle = (Bundle)param2Bundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS");
          MediaSessionCompat.Callback.this.onPlayFromUri(uri, param2Bundle);
        } else if (uri.equals("android.support.v4.media.session.action.PREPARE")) {
          MediaSessionCompat.Callback.this.onPrepare();
        } else {
          String str;
          if (uri.equals("android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID")) {
            str = param2Bundle.getString("android.support.v4.media.session.action.ARGUMENT_MEDIA_ID");
            param2Bundle = param2Bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS");
            MediaSessionCompat.Callback.this.onPrepareFromMediaId(str, param2Bundle);
          } else if (str.equals("android.support.v4.media.session.action.PREPARE_FROM_SEARCH")) {
            str = param2Bundle.getString("android.support.v4.media.session.action.ARGUMENT_QUERY");
            param2Bundle = param2Bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS");
            MediaSessionCompat.Callback.this.onPrepareFromSearch(str, param2Bundle);
          } else {
            Uri uri1;
            if (str.equals("android.support.v4.media.session.action.PREPARE_FROM_URI")) {
              uri1 = (Uri)param2Bundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_URI");
              param2Bundle = param2Bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS");
              MediaSessionCompat.Callback.this.onPrepareFromUri(uri1, param2Bundle);
            } else if (uri1.equals("android.support.v4.media.session.action.SET_CAPTIONING_ENABLED")) {
              boolean bool = param2Bundle.getBoolean("android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED");
              MediaSessionCompat.Callback.this.onSetCaptioningEnabled(bool);
            } else if (uri1.equals("android.support.v4.media.session.action.SET_REPEAT_MODE")) {
              int i = param2Bundle.getInt("android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE");
              MediaSessionCompat.Callback.this.onSetRepeatMode(i);
            } else if (uri1.equals("android.support.v4.media.session.action.SET_SHUFFLE_MODE")) {
              int i = param2Bundle.getInt("android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE");
              MediaSessionCompat.Callback.this.onSetShuffleMode(i);
            } else {
              RatingCompat ratingCompat;
              if (uri1.equals("android.support.v4.media.session.action.SET_RATING")) {
                param2Bundle.setClassLoader(RatingCompat.class.getClassLoader());
                ratingCompat = (RatingCompat)param2Bundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_RATING");
                param2Bundle = param2Bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS");
                MediaSessionCompat.Callback.this.onSetRating(ratingCompat, param2Bundle);
              } else {
                MediaSessionCompat.Callback.this.onCustomAction((String)ratingCompat, param2Bundle);
              } 
            } 
          } 
        } 
      }
      
      public void onFastForward() {
        MediaSessionCompat.Callback.this.onFastForward();
      }
      
      public boolean onMediaButtonEvent(Intent param2Intent) {
        return MediaSessionCompat.Callback.this.onMediaButtonEvent(param2Intent);
      }
      
      public void onPause() {
        MediaSessionCompat.Callback.this.onPause();
      }
      
      public void onPlay() {
        MediaSessionCompat.Callback.this.onPlay();
      }
      
      public void onPlayFromMediaId(String param2String, Bundle param2Bundle) {
        MediaSessionCompat.Callback.this.onPlayFromMediaId(param2String, param2Bundle);
      }
      
      public void onPlayFromSearch(String param2String, Bundle param2Bundle) {
        MediaSessionCompat.Callback.this.onPlayFromSearch(param2String, param2Bundle);
      }
      
      public void onRewind() {
        MediaSessionCompat.Callback.this.onRewind();
      }
      
      public void onSeekTo(long param2Long) {
        MediaSessionCompat.Callback.this.onSeekTo(param2Long);
      }
      
      public void onSetRating(Object param2Object) {
        MediaSessionCompat.Callback.this.onSetRating(RatingCompat.fromRating(param2Object));
      }
      
      public void onSetRating(Object param2Object, Bundle param2Bundle) {
        MediaSessionCompat.Callback.this.onSetRating(RatingCompat.fromRating(param2Object), param2Bundle);
      }
      
      public void onSkipToNext() {
        MediaSessionCompat.Callback.this.onSkipToNext();
      }
      
      public void onSkipToPrevious() {
        MediaSessionCompat.Callback.this.onSkipToPrevious();
      }
      
      public void onSkipToQueueItem(long param2Long) {
        MediaSessionCompat.Callback.this.onSkipToQueueItem(param2Long);
      }
      
      public void onStop() {
        MediaSessionCompat.Callback.this.onStop();
      }
    }
    
    private class StubApi23 extends StubApi21 implements MediaSessionCompatApi23.Callback {
      public void onPlayFromUri(Uri param2Uri, Bundle param2Bundle) {
        MediaSessionCompat.Callback.this.onPlayFromUri(param2Uri, param2Bundle);
      }
    }
    
    private class StubApi24 extends StubApi23 implements MediaSessionCompatApi24.Callback {
      public void onPrepare() {
        MediaSessionCompat.Callback.this.onPrepare();
      }
      
      public void onPrepareFromMediaId(String param2String, Bundle param2Bundle) {
        MediaSessionCompat.Callback.this.onPrepareFromMediaId(param2String, param2Bundle);
      }
      
      public void onPrepareFromSearch(String param2String, Bundle param2Bundle) {
        MediaSessionCompat.Callback.this.onPrepareFromSearch(param2String, param2Bundle);
      }
      
      public void onPrepareFromUri(Uri param2Uri, Bundle param2Bundle) {
        MediaSessionCompat.Callback.this.onPrepareFromUri(param2Uri, param2Bundle);
      }
    }
  }
  
  private class CallbackHandler extends Handler {
    private static final int MSG_MEDIA_PLAY_PAUSE_KEY_DOUBLE_TAP_TIMEOUT = 1;
    
    CallbackHandler(Looper param1Looper) {
      super(param1Looper);
    }
    
    public void handleMessage(Message param1Message) {
      if (param1Message.what == 1)
        this.this$0.handleMediaPlayPauseKeySingleTapIfPending(); 
    }
  }
  
  private class StubApi21 implements MediaSessionCompatApi21.Callback {
    public void onCommand(String param1String, Bundle param1Bundle, ResultReceiver param1ResultReceiver) {
      try {
        IBinder iBinder1;
        boolean bool = param1String.equals("android.support.v4.media.session.command.GET_EXTRA_BINDER");
        IBinder iBinder2 = null;
        IMediaSession iMediaSession = null;
        if (bool) {
          MediaSessionCompat.MediaSessionImplApi21 mediaSessionImplApi21 = this.this$0.mSessionImpl.get();
          if (mediaSessionImplApi21 != null) {
            param1Bundle = new Bundle();
            this();
            IMediaSession iMediaSession1 = mediaSessionImplApi21.getSessionToken().getExtraBinder();
            if (iMediaSession1 == null) {
              iMediaSession1 = iMediaSession;
            } else {
              iBinder1 = iMediaSession1.asBinder();
            } 
            BundleCompat.putBinder(param1Bundle, "android.support.v4.media.session.EXTRA_BINDER", iBinder1);
            param1ResultReceiver.send(0, param1Bundle);
          } 
        } else {
          bool = iBinder1.equals("android.support.v4.media.session.command.ADD_QUEUE_ITEM");
          if (bool) {
            param1Bundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
            this.this$0.onAddQueueItem((MediaDescriptionCompat)param1Bundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"));
          } else {
            bool = iBinder1.equals("android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT");
            if (bool) {
              param1Bundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
              this.this$0.onAddQueueItem((MediaDescriptionCompat)param1Bundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"), param1Bundle.getInt("android.support.v4.media.session.command.ARGUMENT_INDEX"));
            } else if (iBinder1.equals("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM")) {
              param1Bundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
              this.this$0.onRemoveQueueItem((MediaDescriptionCompat)param1Bundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"));
            } else {
              MediaSessionCompat.QueueItem queueItem;
              MediaSessionCompat.MediaSessionImplApi21 mediaSessionImplApi21;
              if (iBinder1.equals("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT")) {
                mediaSessionImplApi21 = this.this$0.mSessionImpl.get();
                if (mediaSessionImplApi21 != null && mediaSessionImplApi21.mQueue != null) {
                  int i = param1Bundle.getInt("android.support.v4.media.session.command.ARGUMENT_INDEX", -1);
                  iBinder1 = iBinder2;
                  if (i >= 0) {
                    iBinder1 = iBinder2;
                    if (i < mediaSessionImplApi21.mQueue.size())
                      queueItem = mediaSessionImplApi21.mQueue.get(i); 
                  } 
                  if (queueItem != null)
                    this.this$0.onRemoveQueueItem(queueItem.getDescription()); 
                } 
              } else {
                this.this$0.onCommand((String)queueItem, param1Bundle, (ResultReceiver)mediaSessionImplApi21);
              } 
            } 
          } 
        } 
      } catch (BadParcelableException badParcelableException) {
        Log.e("MediaSessionCompat", "Could not unparcel the extra data.");
      } 
    }
    
    public void onCustomAction(String param1String, Bundle param1Bundle) {
      Uri uri;
      if (param1String.equals("android.support.v4.media.session.action.PLAY_FROM_URI")) {
        uri = (Uri)param1Bundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_URI");
        param1Bundle = (Bundle)param1Bundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS");
        this.this$0.onPlayFromUri(uri, param1Bundle);
      } else if (uri.equals("android.support.v4.media.session.action.PREPARE")) {
        this.this$0.onPrepare();
      } else {
        String str;
        if (uri.equals("android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID")) {
          str = param1Bundle.getString("android.support.v4.media.session.action.ARGUMENT_MEDIA_ID");
          param1Bundle = param1Bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS");
          this.this$0.onPrepareFromMediaId(str, param1Bundle);
        } else if (str.equals("android.support.v4.media.session.action.PREPARE_FROM_SEARCH")) {
          str = param1Bundle.getString("android.support.v4.media.session.action.ARGUMENT_QUERY");
          param1Bundle = param1Bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS");
          this.this$0.onPrepareFromSearch(str, param1Bundle);
        } else {
          Uri uri1;
          if (str.equals("android.support.v4.media.session.action.PREPARE_FROM_URI")) {
            uri1 = (Uri)param1Bundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_URI");
            param1Bundle = param1Bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS");
            this.this$0.onPrepareFromUri(uri1, param1Bundle);
          } else if (uri1.equals("android.support.v4.media.session.action.SET_CAPTIONING_ENABLED")) {
            boolean bool = param1Bundle.getBoolean("android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED");
            this.this$0.onSetCaptioningEnabled(bool);
          } else if (uri1.equals("android.support.v4.media.session.action.SET_REPEAT_MODE")) {
            int i = param1Bundle.getInt("android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE");
            this.this$0.onSetRepeatMode(i);
          } else if (uri1.equals("android.support.v4.media.session.action.SET_SHUFFLE_MODE")) {
            int i = param1Bundle.getInt("android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE");
            this.this$0.onSetShuffleMode(i);
          } else {
            RatingCompat ratingCompat;
            if (uri1.equals("android.support.v4.media.session.action.SET_RATING")) {
              param1Bundle.setClassLoader(RatingCompat.class.getClassLoader());
              ratingCompat = (RatingCompat)param1Bundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_RATING");
              param1Bundle = param1Bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS");
              this.this$0.onSetRating(ratingCompat, param1Bundle);
            } else {
              this.this$0.onCustomAction((String)ratingCompat, param1Bundle);
            } 
          } 
        } 
      } 
    }
    
    public void onFastForward() {
      this.this$0.onFastForward();
    }
    
    public boolean onMediaButtonEvent(Intent param1Intent) {
      return this.this$0.onMediaButtonEvent(param1Intent);
    }
    
    public void onPause() {
      this.this$0.onPause();
    }
    
    public void onPlay() {
      this.this$0.onPlay();
    }
    
    public void onPlayFromMediaId(String param1String, Bundle param1Bundle) {
      this.this$0.onPlayFromMediaId(param1String, param1Bundle);
    }
    
    public void onPlayFromSearch(String param1String, Bundle param1Bundle) {
      this.this$0.onPlayFromSearch(param1String, param1Bundle);
    }
    
    public void onRewind() {
      this.this$0.onRewind();
    }
    
    public void onSeekTo(long param1Long) {
      this.this$0.onSeekTo(param1Long);
    }
    
    public void onSetRating(Object param1Object) {
      this.this$0.onSetRating(RatingCompat.fromRating(param1Object));
    }
    
    public void onSetRating(Object param1Object, Bundle param1Bundle) {
      this.this$0.onSetRating(RatingCompat.fromRating(param1Object), param1Bundle);
    }
    
    public void onSkipToNext() {
      this.this$0.onSkipToNext();
    }
    
    public void onSkipToPrevious() {
      this.this$0.onSkipToPrevious();
    }
    
    public void onSkipToQueueItem(long param1Long) {
      this.this$0.onSkipToQueueItem(param1Long);
    }
    
    public void onStop() {
      this.this$0.onStop();
    }
  }
  
  private class StubApi23 extends Callback.StubApi21 implements MediaSessionCompatApi23.Callback {
    StubApi23() {
      super((MediaSessionCompat.Callback)MediaSessionCompat.this);
    }
    
    public void onPlayFromUri(Uri param1Uri, Bundle param1Bundle) {
      this.this$0.onPlayFromUri(param1Uri, param1Bundle);
    }
  }
  
  private class StubApi24 extends Callback.StubApi23 implements MediaSessionCompatApi24.Callback {
    StubApi24() {
      super((MediaSessionCompat.Callback)MediaSessionCompat.this);
    }
    
    public void onPrepare() {
      this.this$0.onPrepare();
    }
    
    public void onPrepareFromMediaId(String param1String, Bundle param1Bundle) {
      this.this$0.onPrepareFromMediaId(param1String, param1Bundle);
    }
    
    public void onPrepareFromSearch(String param1String, Bundle param1Bundle) {
      this.this$0.onPrepareFromSearch(param1String, param1Bundle);
    }
    
    public void onPrepareFromUri(Uri param1Uri, Bundle param1Bundle) {
      this.this$0.onPrepareFromUri(param1Uri, param1Bundle);
    }
  }
  
  static interface MediaSessionImpl {
    String getCallingPackage();
    
    Object getMediaSession();
    
    PlaybackStateCompat getPlaybackState();
    
    Object getRemoteControlClient();
    
    MediaSessionCompat.Token getSessionToken();
    
    boolean isActive();
    
    void release();
    
    void sendSessionEvent(String param1String, Bundle param1Bundle);
    
    void setActive(boolean param1Boolean);
    
    void setCallback(MediaSessionCompat.Callback param1Callback, Handler param1Handler);
    
    void setCaptioningEnabled(boolean param1Boolean);
    
    void setExtras(Bundle param1Bundle);
    
    void setFlags(int param1Int);
    
    void setMediaButtonReceiver(PendingIntent param1PendingIntent);
    
    void setMetadata(MediaMetadataCompat param1MediaMetadataCompat);
    
    void setPlaybackState(PlaybackStateCompat param1PlaybackStateCompat);
    
    void setPlaybackToLocal(int param1Int);
    
    void setPlaybackToRemote(VolumeProviderCompat param1VolumeProviderCompat);
    
    void setQueue(List<MediaSessionCompat.QueueItem> param1List);
    
    void setQueueTitle(CharSequence param1CharSequence);
    
    void setRatingType(int param1Int);
    
    void setRepeatMode(int param1Int);
    
    void setSessionActivity(PendingIntent param1PendingIntent);
    
    void setShuffleMode(int param1Int);
  }
  
  static class MediaSessionImplApi18 extends MediaSessionImplBase {
    private static boolean sIsMbrPendingIntentSupported = true;
    
    MediaSessionImplApi18(Context param1Context, String param1String, ComponentName param1ComponentName, PendingIntent param1PendingIntent) {
      super(param1Context, param1String, param1ComponentName, param1PendingIntent);
    }
    
    int getRccTransportControlFlagsFromActions(long param1Long) {
      int i = super.getRccTransportControlFlagsFromActions(param1Long);
      int j = i;
      if ((param1Long & 0x100L) != 0L)
        j = i | 0x100; 
      return j;
    }
    
    void registerMediaButtonEventReceiver(PendingIntent param1PendingIntent, ComponentName param1ComponentName) {
      if (sIsMbrPendingIntentSupported)
        try {
          this.mAudioManager.registerMediaButtonEventReceiver(param1PendingIntent);
        } catch (NullPointerException nullPointerException) {
          Log.w("MediaSessionCompat", "Unable to register media button event receiver with PendingIntent, falling back to ComponentName.");
          sIsMbrPendingIntentSupported = false;
        }  
      if (!sIsMbrPendingIntentSupported)
        super.registerMediaButtonEventReceiver(param1PendingIntent, param1ComponentName); 
    }
    
    public void setCallback(MediaSessionCompat.Callback param1Callback, Handler param1Handler) {
      super.setCallback(param1Callback, param1Handler);
      if (param1Callback == null) {
        this.mRcc.setPlaybackPositionUpdateListener(null);
      } else {
        RemoteControlClient.OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener = new RemoteControlClient.OnPlaybackPositionUpdateListener() {
            public void onPlaybackPositionUpdate(long param2Long) {
              MediaSessionCompat.MediaSessionImplApi18.this.postToHandler(18, Long.valueOf(param2Long));
            }
          };
        this.mRcc.setPlaybackPositionUpdateListener(onPlaybackPositionUpdateListener);
      } 
    }
    
    void setRccState(PlaybackStateCompat param1PlaybackStateCompat) {
      long l1 = param1PlaybackStateCompat.getPosition();
      float f = param1PlaybackStateCompat.getPlaybackSpeed();
      long l2 = param1PlaybackStateCompat.getLastPositionUpdateTime();
      long l3 = SystemClock.elapsedRealtime();
      long l4 = l1;
      if (param1PlaybackStateCompat.getState() == 3) {
        long l = 0L;
        l4 = l1;
        if (l1 > 0L) {
          l4 = l;
          if (l2 > 0L) {
            l = l3 - l2;
            l4 = l;
            if (f > 0.0F) {
              l4 = l;
              if (f != 1.0F)
                l4 = (long)((float)l * f); 
            } 
          } 
          l4 = l1 + l4;
        } 
      } 
      this.mRcc.setPlaybackState(getRccStateFromState(param1PlaybackStateCompat.getState()), l4, f);
    }
    
    void unregisterMediaButtonEventReceiver(PendingIntent param1PendingIntent, ComponentName param1ComponentName) {
      if (sIsMbrPendingIntentSupported) {
        this.mAudioManager.unregisterMediaButtonEventReceiver(param1PendingIntent);
      } else {
        super.unregisterMediaButtonEventReceiver(param1PendingIntent, param1ComponentName);
      } 
    }
  }
  
  class null implements RemoteControlClient.OnPlaybackPositionUpdateListener {
    public void onPlaybackPositionUpdate(long param1Long) {
      this.this$0.postToHandler(18, Long.valueOf(param1Long));
    }
  }
  
  static class MediaSessionImplApi19 extends MediaSessionImplApi18 {
    MediaSessionImplApi19(Context param1Context, String param1String, ComponentName param1ComponentName, PendingIntent param1PendingIntent) {
      super(param1Context, param1String, param1ComponentName, param1PendingIntent);
    }
    
    RemoteControlClient.MetadataEditor buildRccMetadata(Bundle param1Bundle) {
      long l;
      RemoteControlClient.MetadataEditor metadataEditor = super.buildRccMetadata(param1Bundle);
      if (this.mState == null) {
        l = 0L;
      } else {
        l = this.mState.getActions();
      } 
      if ((l & 0x80L) != 0L)
        metadataEditor.addEditableKey(268435457); 
      if (param1Bundle == null)
        return metadataEditor; 
      if (param1Bundle.containsKey("android.media.metadata.YEAR"))
        metadataEditor.putLong(8, param1Bundle.getLong("android.media.metadata.YEAR")); 
      if (param1Bundle.containsKey("android.media.metadata.RATING"))
        metadataEditor.putObject(101, param1Bundle.getParcelable("android.media.metadata.RATING")); 
      if (param1Bundle.containsKey("android.media.metadata.USER_RATING"))
        metadataEditor.putObject(268435457, param1Bundle.getParcelable("android.media.metadata.USER_RATING")); 
      return metadataEditor;
    }
    
    int getRccTransportControlFlagsFromActions(long param1Long) {
      int i = super.getRccTransportControlFlagsFromActions(param1Long);
      int j = i;
      if ((param1Long & 0x80L) != 0L)
        j = i | 0x200; 
      return j;
    }
    
    public void setCallback(MediaSessionCompat.Callback param1Callback, Handler param1Handler) {
      super.setCallback(param1Callback, param1Handler);
      if (param1Callback == null) {
        this.mRcc.setMetadataUpdateListener(null);
      } else {
        RemoteControlClient.OnMetadataUpdateListener onMetadataUpdateListener = new RemoteControlClient.OnMetadataUpdateListener() {
            public void onMetadataUpdate(int param2Int, Object param2Object) {
              if (param2Int == 268435457 && param2Object instanceof android.media.Rating)
                MediaSessionCompat.MediaSessionImplApi19.this.postToHandler(19, RatingCompat.fromRating(param2Object)); 
            }
          };
        this.mRcc.setMetadataUpdateListener(onMetadataUpdateListener);
      } 
    }
  }
  
  class null implements RemoteControlClient.OnMetadataUpdateListener {
    public void onMetadataUpdate(int param1Int, Object param1Object) {
      if (param1Int == 268435457 && param1Object instanceof android.media.Rating)
        this.this$0.postToHandler(19, RatingCompat.fromRating(param1Object)); 
    }
  }
  
  static class MediaSessionImplApi21 implements MediaSessionImpl {
    boolean mCaptioningEnabled;
    
    private boolean mDestroyed = false;
    
    private final RemoteCallbackList<IMediaControllerCallback> mExtraControllerCallbacks = new RemoteCallbackList();
    
    private MediaMetadataCompat mMetadata;
    
    private PlaybackStateCompat mPlaybackState;
    
    private List<MediaSessionCompat.QueueItem> mQueue;
    
    int mRatingType;
    
    int mRepeatMode;
    
    private final Object mSessionObj;
    
    int mShuffleMode;
    
    private final MediaSessionCompat.Token mToken;
    
    public MediaSessionImplApi21(Context param1Context, String param1String) {
      this.mSessionObj = MediaSessionCompatApi21.createSession(param1Context, param1String);
      this.mToken = new MediaSessionCompat.Token(MediaSessionCompatApi21.getSessionToken(this.mSessionObj), new ExtraSession());
    }
    
    public MediaSessionImplApi21(Object param1Object) {
      this.mSessionObj = MediaSessionCompatApi21.verifySession(param1Object);
      this.mToken = new MediaSessionCompat.Token(MediaSessionCompatApi21.getSessionToken(this.mSessionObj), new ExtraSession());
    }
    
    public String getCallingPackage() {
      return (Build.VERSION.SDK_INT < 24) ? null : MediaSessionCompatApi24.getCallingPackage(this.mSessionObj);
    }
    
    public Object getMediaSession() {
      return this.mSessionObj;
    }
    
    public PlaybackStateCompat getPlaybackState() {
      return this.mPlaybackState;
    }
    
    public Object getRemoteControlClient() {
      return null;
    }
    
    public MediaSessionCompat.Token getSessionToken() {
      return this.mToken;
    }
    
    public boolean isActive() {
      return MediaSessionCompatApi21.isActive(this.mSessionObj);
    }
    
    public void release() {
      this.mDestroyed = true;
      MediaSessionCompatApi21.release(this.mSessionObj);
    }
    
    public void sendSessionEvent(String param1String, Bundle param1Bundle) {
      if (Build.VERSION.SDK_INT < 23) {
        int i = this.mExtraControllerCallbacks.beginBroadcast() - 1;
        while (true) {
          if (i >= 0) {
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mExtraControllerCallbacks.getBroadcastItem(i);
            try {
              iMediaControllerCallback.onEvent(param1String, param1Bundle);
            } catch (RemoteException remoteException) {}
            i--;
            continue;
          } 
          this.mExtraControllerCallbacks.finishBroadcast();
          MediaSessionCompatApi21.sendSessionEvent(this.mSessionObj, param1String, param1Bundle);
          return;
        } 
      } 
      MediaSessionCompatApi21.sendSessionEvent(this.mSessionObj, param1String, param1Bundle);
    }
    
    public void setActive(boolean param1Boolean) {
      MediaSessionCompatApi21.setActive(this.mSessionObj, param1Boolean);
    }
    
    public void setCallback(MediaSessionCompat.Callback param1Callback, Handler param1Handler) {
      Object object2;
      Object object1 = this.mSessionObj;
      if (param1Callback == null) {
        object2 = null;
      } else {
        object2 = param1Callback.mCallbackObj;
      } 
      MediaSessionCompatApi21.setCallback(object1, object2, param1Handler);
      if (param1Callback != null)
        param1Callback.setSessionImpl(this, param1Handler); 
    }
    
    public void setCaptioningEnabled(boolean param1Boolean) {
      if (this.mCaptioningEnabled != param1Boolean) {
        this.mCaptioningEnabled = param1Boolean;
        int i = this.mExtraControllerCallbacks.beginBroadcast() - 1;
        while (true) {
          if (i >= 0) {
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mExtraControllerCallbacks.getBroadcastItem(i);
            try {
              iMediaControllerCallback.onCaptioningEnabledChanged(param1Boolean);
            } catch (RemoteException remoteException) {}
            i--;
            continue;
          } 
          this.mExtraControllerCallbacks.finishBroadcast();
          return;
        } 
      } 
    }
    
    public void setExtras(Bundle param1Bundle) {
      MediaSessionCompatApi21.setExtras(this.mSessionObj, param1Bundle);
    }
    
    public void setFlags(int param1Int) {
      MediaSessionCompatApi21.setFlags(this.mSessionObj, param1Int);
    }
    
    public void setMediaButtonReceiver(PendingIntent param1PendingIntent) {
      MediaSessionCompatApi21.setMediaButtonReceiver(this.mSessionObj, param1PendingIntent);
    }
    
    public void setMetadata(MediaMetadataCompat param1MediaMetadataCompat) {
      Object object1;
      this.mMetadata = param1MediaMetadataCompat;
      Object object2 = this.mSessionObj;
      if (param1MediaMetadataCompat == null) {
        param1MediaMetadataCompat = null;
      } else {
        object1 = param1MediaMetadataCompat.getMediaMetadata();
      } 
      MediaSessionCompatApi21.setMetadata(object2, object1);
    }
    
    public void setPlaybackState(PlaybackStateCompat param1PlaybackStateCompat) {
      this.mPlaybackState = param1PlaybackStateCompat;
      int i = this.mExtraControllerCallbacks.beginBroadcast() - 1;
      while (true) {
        Object object1;
        if (i >= 0) {
          IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mExtraControllerCallbacks.getBroadcastItem(i);
          try {
            iMediaControllerCallback.onPlaybackStateChanged(param1PlaybackStateCompat);
          } catch (RemoteException remoteException) {}
          i--;
          continue;
        } 
        this.mExtraControllerCallbacks.finishBroadcast();
        Object object2 = this.mSessionObj;
        if (param1PlaybackStateCompat == null) {
          param1PlaybackStateCompat = null;
        } else {
          object1 = param1PlaybackStateCompat.getPlaybackState();
        } 
        MediaSessionCompatApi21.setPlaybackState(object2, object1);
        return;
      } 
    }
    
    public void setPlaybackToLocal(int param1Int) {
      MediaSessionCompatApi21.setPlaybackToLocal(this.mSessionObj, param1Int);
    }
    
    public void setPlaybackToRemote(VolumeProviderCompat param1VolumeProviderCompat) {
      MediaSessionCompatApi21.setPlaybackToRemote(this.mSessionObj, param1VolumeProviderCompat.getVolumeProvider());
    }
    
    public void setQueue(List<MediaSessionCompat.QueueItem> param1List) {
      this.mQueue = param1List;
      if (param1List != null) {
        ArrayList<MediaSessionCompat.QueueItem> arrayList = new ArrayList();
        Iterator<MediaSessionCompat.QueueItem> iterator = param1List.iterator();
        while (true) {
          param1List = arrayList;
          if (iterator.hasNext()) {
            arrayList.add(((MediaSessionCompat.QueueItem)iterator.next()).getQueueItem());
            continue;
          } 
          break;
        } 
      } else {
        param1List = null;
      } 
      MediaSessionCompatApi21.setQueue(this.mSessionObj, (List)param1List);
    }
    
    public void setQueueTitle(CharSequence param1CharSequence) {
      MediaSessionCompatApi21.setQueueTitle(this.mSessionObj, param1CharSequence);
    }
    
    public void setRatingType(int param1Int) {
      if (Build.VERSION.SDK_INT < 22) {
        this.mRatingType = param1Int;
      } else {
        MediaSessionCompatApi22.setRatingType(this.mSessionObj, param1Int);
      } 
    }
    
    public void setRepeatMode(int param1Int) {
      if (this.mRepeatMode != param1Int) {
        this.mRepeatMode = param1Int;
        int i = this.mExtraControllerCallbacks.beginBroadcast() - 1;
        while (true) {
          if (i >= 0) {
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mExtraControllerCallbacks.getBroadcastItem(i);
            try {
              iMediaControllerCallback.onRepeatModeChanged(param1Int);
            } catch (RemoteException remoteException) {}
            i--;
            continue;
          } 
          this.mExtraControllerCallbacks.finishBroadcast();
          return;
        } 
      } 
    }
    
    public void setSessionActivity(PendingIntent param1PendingIntent) {
      MediaSessionCompatApi21.setSessionActivity(this.mSessionObj, param1PendingIntent);
    }
    
    public void setShuffleMode(int param1Int) {
      if (this.mShuffleMode != param1Int) {
        this.mShuffleMode = param1Int;
        int i = this.mExtraControllerCallbacks.beginBroadcast() - 1;
        while (true) {
          if (i >= 0) {
            IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mExtraControllerCallbacks.getBroadcastItem(i);
            try {
              iMediaControllerCallback.onShuffleModeChanged(param1Int);
            } catch (RemoteException remoteException) {}
            i--;
            continue;
          } 
          this.mExtraControllerCallbacks.finishBroadcast();
          return;
        } 
      } 
    }
    
    class ExtraSession extends IMediaSession.Stub {
      public void addQueueItem(MediaDescriptionCompat param2MediaDescriptionCompat) {
        throw new AssertionError();
      }
      
      public void addQueueItemAt(MediaDescriptionCompat param2MediaDescriptionCompat, int param2Int) {
        throw new AssertionError();
      }
      
      public void adjustVolume(int param2Int1, int param2Int2, String param2String) {
        throw new AssertionError();
      }
      
      public void fastForward() throws RemoteException {
        throw new AssertionError();
      }
      
      public Bundle getExtras() {
        throw new AssertionError();
      }
      
      public long getFlags() {
        throw new AssertionError();
      }
      
      public PendingIntent getLaunchPendingIntent() {
        throw new AssertionError();
      }
      
      public MediaMetadataCompat getMetadata() {
        throw new AssertionError();
      }
      
      public String getPackageName() {
        throw new AssertionError();
      }
      
      public PlaybackStateCompat getPlaybackState() {
        return MediaSessionCompat.getStateWithUpdatedPosition(MediaSessionCompat.MediaSessionImplApi21.this.mPlaybackState, MediaSessionCompat.MediaSessionImplApi21.this.mMetadata);
      }
      
      public List<MediaSessionCompat.QueueItem> getQueue() {
        return null;
      }
      
      public CharSequence getQueueTitle() {
        throw new AssertionError();
      }
      
      public int getRatingType() {
        return MediaSessionCompat.MediaSessionImplApi21.this.mRatingType;
      }
      
      public int getRepeatMode() {
        return MediaSessionCompat.MediaSessionImplApi21.this.mRepeatMode;
      }
      
      public int getShuffleMode() {
        return MediaSessionCompat.MediaSessionImplApi21.this.mShuffleMode;
      }
      
      public String getTag() {
        throw new AssertionError();
      }
      
      public ParcelableVolumeInfo getVolumeAttributes() {
        throw new AssertionError();
      }
      
      public boolean isCaptioningEnabled() {
        return MediaSessionCompat.MediaSessionImplApi21.this.mCaptioningEnabled;
      }
      
      public boolean isShuffleModeEnabledRemoved() {
        return false;
      }
      
      public boolean isTransportControlEnabled() {
        throw new AssertionError();
      }
      
      public void next() throws RemoteException {
        throw new AssertionError();
      }
      
      public void pause() throws RemoteException {
        throw new AssertionError();
      }
      
      public void play() throws RemoteException {
        throw new AssertionError();
      }
      
      public void playFromMediaId(String param2String, Bundle param2Bundle) throws RemoteException {
        throw new AssertionError();
      }
      
      public void playFromSearch(String param2String, Bundle param2Bundle) throws RemoteException {
        throw new AssertionError();
      }
      
      public void playFromUri(Uri param2Uri, Bundle param2Bundle) throws RemoteException {
        throw new AssertionError();
      }
      
      public void prepare() throws RemoteException {
        throw new AssertionError();
      }
      
      public void prepareFromMediaId(String param2String, Bundle param2Bundle) throws RemoteException {
        throw new AssertionError();
      }
      
      public void prepareFromSearch(String param2String, Bundle param2Bundle) throws RemoteException {
        throw new AssertionError();
      }
      
      public void prepareFromUri(Uri param2Uri, Bundle param2Bundle) throws RemoteException {
        throw new AssertionError();
      }
      
      public void previous() throws RemoteException {
        throw new AssertionError();
      }
      
      public void rate(RatingCompat param2RatingCompat) throws RemoteException {
        throw new AssertionError();
      }
      
      public void rateWithExtras(RatingCompat param2RatingCompat, Bundle param2Bundle) throws RemoteException {
        throw new AssertionError();
      }
      
      public void registerCallbackListener(IMediaControllerCallback param2IMediaControllerCallback) {
        if (!MediaSessionCompat.MediaSessionImplApi21.this.mDestroyed)
          MediaSessionCompat.MediaSessionImplApi21.this.mExtraControllerCallbacks.register(param2IMediaControllerCallback); 
      }
      
      public void removeQueueItem(MediaDescriptionCompat param2MediaDescriptionCompat) {
        throw new AssertionError();
      }
      
      public void removeQueueItemAt(int param2Int) {
        throw new AssertionError();
      }
      
      public void rewind() throws RemoteException {
        throw new AssertionError();
      }
      
      public void seekTo(long param2Long) throws RemoteException {
        throw new AssertionError();
      }
      
      public void sendCommand(String param2String, Bundle param2Bundle, MediaSessionCompat.ResultReceiverWrapper param2ResultReceiverWrapper) {
        throw new AssertionError();
      }
      
      public void sendCustomAction(String param2String, Bundle param2Bundle) throws RemoteException {
        throw new AssertionError();
      }
      
      public boolean sendMediaButton(KeyEvent param2KeyEvent) {
        throw new AssertionError();
      }
      
      public void setCaptioningEnabled(boolean param2Boolean) throws RemoteException {
        throw new AssertionError();
      }
      
      public void setRepeatMode(int param2Int) throws RemoteException {
        throw new AssertionError();
      }
      
      public void setShuffleMode(int param2Int) throws RemoteException {
        throw new AssertionError();
      }
      
      public void setShuffleModeEnabledRemoved(boolean param2Boolean) throws RemoteException {}
      
      public void setVolumeTo(int param2Int1, int param2Int2, String param2String) {
        throw new AssertionError();
      }
      
      public void skipToQueueItem(long param2Long) {
        throw new AssertionError();
      }
      
      public void stop() throws RemoteException {
        throw new AssertionError();
      }
      
      public void unregisterCallbackListener(IMediaControllerCallback param2IMediaControllerCallback) {
        MediaSessionCompat.MediaSessionImplApi21.this.mExtraControllerCallbacks.unregister(param2IMediaControllerCallback);
      }
    }
  }
  
  class ExtraSession extends IMediaSession.Stub {
    public void addQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat) {
      throw new AssertionError();
    }
    
    public void addQueueItemAt(MediaDescriptionCompat param1MediaDescriptionCompat, int param1Int) {
      throw new AssertionError();
    }
    
    public void adjustVolume(int param1Int1, int param1Int2, String param1String) {
      throw new AssertionError();
    }
    
    public void fastForward() throws RemoteException {
      throw new AssertionError();
    }
    
    public Bundle getExtras() {
      throw new AssertionError();
    }
    
    public long getFlags() {
      throw new AssertionError();
    }
    
    public PendingIntent getLaunchPendingIntent() {
      throw new AssertionError();
    }
    
    public MediaMetadataCompat getMetadata() {
      throw new AssertionError();
    }
    
    public String getPackageName() {
      throw new AssertionError();
    }
    
    public PlaybackStateCompat getPlaybackState() {
      return MediaSessionCompat.getStateWithUpdatedPosition(this.this$0.mPlaybackState, this.this$0.mMetadata);
    }
    
    public List<MediaSessionCompat.QueueItem> getQueue() {
      return null;
    }
    
    public CharSequence getQueueTitle() {
      throw new AssertionError();
    }
    
    public int getRatingType() {
      return this.this$0.mRatingType;
    }
    
    public int getRepeatMode() {
      return this.this$0.mRepeatMode;
    }
    
    public int getShuffleMode() {
      return this.this$0.mShuffleMode;
    }
    
    public String getTag() {
      throw new AssertionError();
    }
    
    public ParcelableVolumeInfo getVolumeAttributes() {
      throw new AssertionError();
    }
    
    public boolean isCaptioningEnabled() {
      return this.this$0.mCaptioningEnabled;
    }
    
    public boolean isShuffleModeEnabledRemoved() {
      return false;
    }
    
    public boolean isTransportControlEnabled() {
      throw new AssertionError();
    }
    
    public void next() throws RemoteException {
      throw new AssertionError();
    }
    
    public void pause() throws RemoteException {
      throw new AssertionError();
    }
    
    public void play() throws RemoteException {
      throw new AssertionError();
    }
    
    public void playFromMediaId(String param1String, Bundle param1Bundle) throws RemoteException {
      throw new AssertionError();
    }
    
    public void playFromSearch(String param1String, Bundle param1Bundle) throws RemoteException {
      throw new AssertionError();
    }
    
    public void playFromUri(Uri param1Uri, Bundle param1Bundle) throws RemoteException {
      throw new AssertionError();
    }
    
    public void prepare() throws RemoteException {
      throw new AssertionError();
    }
    
    public void prepareFromMediaId(String param1String, Bundle param1Bundle) throws RemoteException {
      throw new AssertionError();
    }
    
    public void prepareFromSearch(String param1String, Bundle param1Bundle) throws RemoteException {
      throw new AssertionError();
    }
    
    public void prepareFromUri(Uri param1Uri, Bundle param1Bundle) throws RemoteException {
      throw new AssertionError();
    }
    
    public void previous() throws RemoteException {
      throw new AssertionError();
    }
    
    public void rate(RatingCompat param1RatingCompat) throws RemoteException {
      throw new AssertionError();
    }
    
    public void rateWithExtras(RatingCompat param1RatingCompat, Bundle param1Bundle) throws RemoteException {
      throw new AssertionError();
    }
    
    public void registerCallbackListener(IMediaControllerCallback param1IMediaControllerCallback) {
      if (!this.this$0.mDestroyed)
        this.this$0.mExtraControllerCallbacks.register(param1IMediaControllerCallback); 
    }
    
    public void removeQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat) {
      throw new AssertionError();
    }
    
    public void removeQueueItemAt(int param1Int) {
      throw new AssertionError();
    }
    
    public void rewind() throws RemoteException {
      throw new AssertionError();
    }
    
    public void seekTo(long param1Long) throws RemoteException {
      throw new AssertionError();
    }
    
    public void sendCommand(String param1String, Bundle param1Bundle, MediaSessionCompat.ResultReceiverWrapper param1ResultReceiverWrapper) {
      throw new AssertionError();
    }
    
    public void sendCustomAction(String param1String, Bundle param1Bundle) throws RemoteException {
      throw new AssertionError();
    }
    
    public boolean sendMediaButton(KeyEvent param1KeyEvent) {
      throw new AssertionError();
    }
    
    public void setCaptioningEnabled(boolean param1Boolean) throws RemoteException {
      throw new AssertionError();
    }
    
    public void setRepeatMode(int param1Int) throws RemoteException {
      throw new AssertionError();
    }
    
    public void setShuffleMode(int param1Int) throws RemoteException {
      throw new AssertionError();
    }
    
    public void setShuffleModeEnabledRemoved(boolean param1Boolean) throws RemoteException {}
    
    public void setVolumeTo(int param1Int1, int param1Int2, String param1String) {
      throw new AssertionError();
    }
    
    public void skipToQueueItem(long param1Long) {
      throw new AssertionError();
    }
    
    public void stop() throws RemoteException {
      throw new AssertionError();
    }
    
    public void unregisterCallbackListener(IMediaControllerCallback param1IMediaControllerCallback) {
      this.this$0.mExtraControllerCallbacks.unregister(param1IMediaControllerCallback);
    }
  }
  
  static class MediaSessionImplBase implements MediaSessionImpl {
    static final int RCC_PLAYSTATE_NONE = 0;
    
    final AudioManager mAudioManager;
    
    volatile MediaSessionCompat.Callback mCallback;
    
    boolean mCaptioningEnabled;
    
    private final Context mContext;
    
    final RemoteCallbackList<IMediaControllerCallback> mControllerCallbacks = new RemoteCallbackList();
    
    boolean mDestroyed = false;
    
    Bundle mExtras;
    
    int mFlags;
    
    private MessageHandler mHandler;
    
    boolean mIsActive = false;
    
    private boolean mIsMbrRegistered = false;
    
    private boolean mIsRccRegistered = false;
    
    int mLocalStream;
    
    final Object mLock = new Object();
    
    private final ComponentName mMediaButtonReceiverComponentName;
    
    private final PendingIntent mMediaButtonReceiverIntent;
    
    MediaMetadataCompat mMetadata;
    
    final String mPackageName;
    
    List<MediaSessionCompat.QueueItem> mQueue;
    
    CharSequence mQueueTitle;
    
    int mRatingType;
    
    final RemoteControlClient mRcc;
    
    int mRepeatMode;
    
    PendingIntent mSessionActivity;
    
    int mShuffleMode;
    
    PlaybackStateCompat mState;
    
    private final MediaSessionStub mStub;
    
    final String mTag;
    
    private final MediaSessionCompat.Token mToken;
    
    private VolumeProviderCompat.Callback mVolumeCallback = new VolumeProviderCompat.Callback() {
        public void onVolumeChanged(VolumeProviderCompat param2VolumeProviderCompat) {
          if (MediaSessionCompat.MediaSessionImplBase.this.mVolumeProvider != param2VolumeProviderCompat)
            return; 
          ParcelableVolumeInfo parcelableVolumeInfo = new ParcelableVolumeInfo(MediaSessionCompat.MediaSessionImplBase.this.mVolumeType, MediaSessionCompat.MediaSessionImplBase.this.mLocalStream, param2VolumeProviderCompat.getVolumeControl(), param2VolumeProviderCompat.getMaxVolume(), param2VolumeProviderCompat.getCurrentVolume());
          MediaSessionCompat.MediaSessionImplBase.this.sendVolumeInfoChanged(parcelableVolumeInfo);
        }
      };
    
    VolumeProviderCompat mVolumeProvider;
    
    int mVolumeType;
    
    public MediaSessionImplBase(Context param1Context, String param1String, ComponentName param1ComponentName, PendingIntent param1PendingIntent) {
      if (param1ComponentName != null) {
        this.mContext = param1Context;
        this.mPackageName = param1Context.getPackageName();
        this.mAudioManager = (AudioManager)param1Context.getSystemService("audio");
        this.mTag = param1String;
        this.mMediaButtonReceiverComponentName = param1ComponentName;
        this.mMediaButtonReceiverIntent = param1PendingIntent;
        this.mStub = new MediaSessionStub();
        this.mToken = new MediaSessionCompat.Token(this.mStub);
        this.mRatingType = 0;
        this.mVolumeType = 1;
        this.mLocalStream = 3;
        this.mRcc = new RemoteControlClient(param1PendingIntent);
        return;
      } 
      throw new IllegalArgumentException("MediaButtonReceiver component may not be null.");
    }
    
    private void sendCaptioningEnabled(boolean param1Boolean) {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      while (true) {
        if (i >= 0) {
          IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
          try {
            iMediaControllerCallback.onCaptioningEnabledChanged(param1Boolean);
          } catch (RemoteException remoteException) {}
          i--;
          continue;
        } 
        this.mControllerCallbacks.finishBroadcast();
        return;
      } 
    }
    
    private void sendEvent(String param1String, Bundle param1Bundle) {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      while (true) {
        if (i >= 0) {
          IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
          try {
            iMediaControllerCallback.onEvent(param1String, param1Bundle);
          } catch (RemoteException remoteException) {}
          i--;
          continue;
        } 
        this.mControllerCallbacks.finishBroadcast();
        return;
      } 
    }
    
    private void sendExtras(Bundle param1Bundle) {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      while (true) {
        if (i >= 0) {
          IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
          try {
            iMediaControllerCallback.onExtrasChanged(param1Bundle);
          } catch (RemoteException remoteException) {}
          i--;
          continue;
        } 
        this.mControllerCallbacks.finishBroadcast();
        return;
      } 
    }
    
    private void sendMetadata(MediaMetadataCompat param1MediaMetadataCompat) {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      while (true) {
        if (i >= 0) {
          IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
          try {
            iMediaControllerCallback.onMetadataChanged(param1MediaMetadataCompat);
          } catch (RemoteException remoteException) {}
          i--;
          continue;
        } 
        this.mControllerCallbacks.finishBroadcast();
        return;
      } 
    }
    
    private void sendQueue(List<MediaSessionCompat.QueueItem> param1List) {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      while (true) {
        if (i >= 0) {
          IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
          try {
            iMediaControllerCallback.onQueueChanged(param1List);
          } catch (RemoteException remoteException) {}
          i--;
          continue;
        } 
        this.mControllerCallbacks.finishBroadcast();
        return;
      } 
    }
    
    private void sendQueueTitle(CharSequence param1CharSequence) {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      while (true) {
        if (i >= 0) {
          IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
          try {
            iMediaControllerCallback.onQueueTitleChanged(param1CharSequence);
          } catch (RemoteException remoteException) {}
          i--;
          continue;
        } 
        this.mControllerCallbacks.finishBroadcast();
        return;
      } 
    }
    
    private void sendRepeatMode(int param1Int) {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      while (true) {
        if (i >= 0) {
          IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
          try {
            iMediaControllerCallback.onRepeatModeChanged(param1Int);
          } catch (RemoteException remoteException) {}
          i--;
          continue;
        } 
        this.mControllerCallbacks.finishBroadcast();
        return;
      } 
    }
    
    private void sendSessionDestroyed() {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      while (true) {
        if (i >= 0) {
          IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
          try {
            iMediaControllerCallback.onSessionDestroyed();
          } catch (RemoteException remoteException) {}
          i--;
          continue;
        } 
        this.mControllerCallbacks.finishBroadcast();
        this.mControllerCallbacks.kill();
        return;
      } 
    }
    
    private void sendShuffleMode(int param1Int) {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      while (true) {
        if (i >= 0) {
          IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
          try {
            iMediaControllerCallback.onShuffleModeChanged(param1Int);
          } catch (RemoteException remoteException) {}
          i--;
          continue;
        } 
        this.mControllerCallbacks.finishBroadcast();
        return;
      } 
    }
    
    private void sendState(PlaybackStateCompat param1PlaybackStateCompat) {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      while (true) {
        if (i >= 0) {
          IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
          try {
            iMediaControllerCallback.onPlaybackStateChanged(param1PlaybackStateCompat);
          } catch (RemoteException remoteException) {}
          i--;
          continue;
        } 
        this.mControllerCallbacks.finishBroadcast();
        return;
      } 
    }
    
    void adjustVolume(int param1Int1, int param1Int2) {
      if (this.mVolumeType == 2) {
        VolumeProviderCompat volumeProviderCompat = this.mVolumeProvider;
        if (volumeProviderCompat != null)
          volumeProviderCompat.onAdjustVolume(param1Int1); 
      } else {
        this.mAudioManager.adjustStreamVolume(this.mLocalStream, param1Int1, param1Int2);
      } 
    }
    
    RemoteControlClient.MetadataEditor buildRccMetadata(Bundle param1Bundle) {
      RemoteControlClient.MetadataEditor metadataEditor = this.mRcc.editMetadata(true);
      if (param1Bundle == null)
        return metadataEditor; 
      if (param1Bundle.containsKey("android.media.metadata.ART")) {
        Bitmap bitmap1 = (Bitmap)param1Bundle.getParcelable("android.media.metadata.ART");
        Bitmap bitmap2 = bitmap1;
        if (bitmap1 != null)
          bitmap2 = bitmap1.copy(bitmap1.getConfig(), false); 
        metadataEditor.putBitmap(100, bitmap2);
      } else if (param1Bundle.containsKey("android.media.metadata.ALBUM_ART")) {
        Bitmap bitmap1 = (Bitmap)param1Bundle.getParcelable("android.media.metadata.ALBUM_ART");
        Bitmap bitmap2 = bitmap1;
        if (bitmap1 != null)
          bitmap2 = bitmap1.copy(bitmap1.getConfig(), false); 
        metadataEditor.putBitmap(100, bitmap2);
      } 
      if (param1Bundle.containsKey("android.media.metadata.ALBUM"))
        metadataEditor.putString(1, param1Bundle.getString("android.media.metadata.ALBUM")); 
      if (param1Bundle.containsKey("android.media.metadata.ALBUM_ARTIST"))
        metadataEditor.putString(13, param1Bundle.getString("android.media.metadata.ALBUM_ARTIST")); 
      if (param1Bundle.containsKey("android.media.metadata.ARTIST"))
        metadataEditor.putString(2, param1Bundle.getString("android.media.metadata.ARTIST")); 
      if (param1Bundle.containsKey("android.media.metadata.AUTHOR"))
        metadataEditor.putString(3, param1Bundle.getString("android.media.metadata.AUTHOR")); 
      if (param1Bundle.containsKey("android.media.metadata.COMPILATION"))
        metadataEditor.putString(15, param1Bundle.getString("android.media.metadata.COMPILATION")); 
      if (param1Bundle.containsKey("android.media.metadata.COMPOSER"))
        metadataEditor.putString(4, param1Bundle.getString("android.media.metadata.COMPOSER")); 
      if (param1Bundle.containsKey("android.media.metadata.DATE"))
        metadataEditor.putString(5, param1Bundle.getString("android.media.metadata.DATE")); 
      if (param1Bundle.containsKey("android.media.metadata.DISC_NUMBER"))
        metadataEditor.putLong(14, param1Bundle.getLong("android.media.metadata.DISC_NUMBER")); 
      if (param1Bundle.containsKey("android.media.metadata.DURATION"))
        metadataEditor.putLong(9, param1Bundle.getLong("android.media.metadata.DURATION")); 
      if (param1Bundle.containsKey("android.media.metadata.GENRE"))
        metadataEditor.putString(6, param1Bundle.getString("android.media.metadata.GENRE")); 
      if (param1Bundle.containsKey("android.media.metadata.TITLE"))
        metadataEditor.putString(7, param1Bundle.getString("android.media.metadata.TITLE")); 
      if (param1Bundle.containsKey("android.media.metadata.TRACK_NUMBER"))
        metadataEditor.putLong(0, param1Bundle.getLong("android.media.metadata.TRACK_NUMBER")); 
      if (param1Bundle.containsKey("android.media.metadata.WRITER"))
        metadataEditor.putString(11, param1Bundle.getString("android.media.metadata.WRITER")); 
      return metadataEditor;
    }
    
    public String getCallingPackage() {
      return null;
    }
    
    public Object getMediaSession() {
      return null;
    }
    
    public PlaybackStateCompat getPlaybackState() {
      synchronized (this.mLock) {
        return this.mState;
      } 
    }
    
    int getRccStateFromState(int param1Int) {
      switch (param1Int) {
        default:
          return -1;
        case 10:
        case 11:
          return 6;
        case 9:
          return 7;
        case 7:
          return 9;
        case 6:
        case 8:
          return 8;
        case 5:
          return 5;
        case 4:
          return 4;
        case 3:
          return 3;
        case 2:
          return 2;
        case 1:
          return 1;
        case 0:
          break;
      } 
      return 0;
    }
    
    int getRccTransportControlFlagsFromActions(long param1Long) {
      if ((0x1L & param1Long) != 0L) {
        i = 32;
      } else {
        i = 0;
      } 
      int j = i;
      if ((0x2L & param1Long) != 0L)
        j = i | 0x10; 
      int i = j;
      if ((0x4L & param1Long) != 0L)
        i = j | 0x4; 
      j = i;
      if ((0x8L & param1Long) != 0L)
        j = i | 0x2; 
      i = j;
      if ((0x10L & param1Long) != 0L)
        i = j | 0x1; 
      j = i;
      if ((0x20L & param1Long) != 0L)
        j = i | 0x80; 
      i = j;
      if ((0x40L & param1Long) != 0L)
        i = j | 0x40; 
      j = i;
      if ((param1Long & 0x200L) != 0L)
        j = i | 0x8; 
      return j;
    }
    
    public Object getRemoteControlClient() {
      return null;
    }
    
    public MediaSessionCompat.Token getSessionToken() {
      return this.mToken;
    }
    
    public boolean isActive() {
      return this.mIsActive;
    }
    
    void postToHandler(int param1Int) {
      postToHandler(param1Int, (Object)null);
    }
    
    void postToHandler(int param1Int1, int param1Int2) {
      postToHandler(param1Int1, (Object)null, param1Int2);
    }
    
    void postToHandler(int param1Int, Object param1Object) {
      postToHandler(param1Int, param1Object, (Bundle)null);
    }
    
    void postToHandler(int param1Int1, Object param1Object, int param1Int2) {
      synchronized (this.mLock) {
        if (this.mHandler != null)
          this.mHandler.post(param1Int1, param1Object, param1Int2); 
        return;
      } 
    }
    
    void postToHandler(int param1Int, Object param1Object, Bundle param1Bundle) {
      synchronized (this.mLock) {
        if (this.mHandler != null)
          this.mHandler.post(param1Int, param1Object, param1Bundle); 
        return;
      } 
    }
    
    void registerMediaButtonEventReceiver(PendingIntent param1PendingIntent, ComponentName param1ComponentName) {
      this.mAudioManager.registerMediaButtonEventReceiver(param1ComponentName);
    }
    
    public void release() {
      this.mIsActive = false;
      this.mDestroyed = true;
      update();
      sendSessionDestroyed();
    }
    
    public void sendSessionEvent(String param1String, Bundle param1Bundle) {
      sendEvent(param1String, param1Bundle);
    }
    
    void sendVolumeInfoChanged(ParcelableVolumeInfo param1ParcelableVolumeInfo) {
      int i = this.mControllerCallbacks.beginBroadcast() - 1;
      while (true) {
        if (i >= 0) {
          IMediaControllerCallback iMediaControllerCallback = (IMediaControllerCallback)this.mControllerCallbacks.getBroadcastItem(i);
          try {
            iMediaControllerCallback.onVolumeInfoChanged(param1ParcelableVolumeInfo);
          } catch (RemoteException remoteException) {}
          i--;
          continue;
        } 
        this.mControllerCallbacks.finishBroadcast();
        return;
      } 
    }
    
    public void setActive(boolean param1Boolean) {
      if (param1Boolean == this.mIsActive)
        return; 
      this.mIsActive = param1Boolean;
      if (update()) {
        setMetadata(this.mMetadata);
        setPlaybackState(this.mState);
      } 
    }
    
    public void setCallback(MediaSessionCompat.Callback param1Callback, Handler param1Handler) {
      this.mCallback = param1Callback;
      if (param1Callback != null) {
        null = param1Handler;
        if (param1Handler == null)
          null = new Handler(); 
        synchronized (this.mLock) {
          if (this.mHandler != null)
            this.mHandler.removeCallbacksAndMessages(null); 
          MessageHandler messageHandler = new MessageHandler();
          this(this, null.getLooper());
          this.mHandler = messageHandler;
          this.mCallback.setSessionImpl(this, null);
        } 
      } 
    }
    
    public void setCaptioningEnabled(boolean param1Boolean) {
      if (this.mCaptioningEnabled != param1Boolean) {
        this.mCaptioningEnabled = param1Boolean;
        sendCaptioningEnabled(param1Boolean);
      } 
    }
    
    public void setExtras(Bundle param1Bundle) {
      this.mExtras = param1Bundle;
      sendExtras(param1Bundle);
    }
    
    public void setFlags(int param1Int) {
      synchronized (this.mLock) {
        this.mFlags = param1Int;
        update();
        return;
      } 
    }
    
    public void setMediaButtonReceiver(PendingIntent param1PendingIntent) {}
    
    public void setMetadata(MediaMetadataCompat param1MediaMetadataCompat) {
      null = param1MediaMetadataCompat;
      if (param1MediaMetadataCompat != null)
        null = (new MediaMetadataCompat.Builder(param1MediaMetadataCompat, MediaSessionCompat.sMaxBitmapSize)).build(); 
      synchronized (this.mLock) {
        this.mMetadata = null;
        sendMetadata(null);
        if (!this.mIsActive)
          return; 
        if (null == null) {
          null = null;
        } else {
          null = null.getBundle();
        } 
        buildRccMetadata((Bundle)null).apply();
        return;
      } 
    }
    
    public void setPlaybackState(PlaybackStateCompat param1PlaybackStateCompat) {
      synchronized (this.mLock) {
        this.mState = param1PlaybackStateCompat;
        sendState(param1PlaybackStateCompat);
        if (!this.mIsActive)
          return; 
        if (param1PlaybackStateCompat == null) {
          this.mRcc.setPlaybackState(0);
          this.mRcc.setTransportControlFlags(0);
        } else {
          setRccState(param1PlaybackStateCompat);
          this.mRcc.setTransportControlFlags(getRccTransportControlFlagsFromActions(param1PlaybackStateCompat.getActions()));
        } 
        return;
      } 
    }
    
    public void setPlaybackToLocal(int param1Int) {
      VolumeProviderCompat volumeProviderCompat = this.mVolumeProvider;
      if (volumeProviderCompat != null)
        volumeProviderCompat.setCallback(null); 
      this.mVolumeType = 1;
      param1Int = this.mVolumeType;
      int i = this.mLocalStream;
      sendVolumeInfoChanged(new ParcelableVolumeInfo(param1Int, i, 2, this.mAudioManager.getStreamMaxVolume(i), this.mAudioManager.getStreamVolume(this.mLocalStream)));
    }
    
    public void setPlaybackToRemote(VolumeProviderCompat param1VolumeProviderCompat) {
      if (param1VolumeProviderCompat != null) {
        VolumeProviderCompat volumeProviderCompat = this.mVolumeProvider;
        if (volumeProviderCompat != null)
          volumeProviderCompat.setCallback(null); 
        this.mVolumeType = 2;
        this.mVolumeProvider = param1VolumeProviderCompat;
        sendVolumeInfoChanged(new ParcelableVolumeInfo(this.mVolumeType, this.mLocalStream, this.mVolumeProvider.getVolumeControl(), this.mVolumeProvider.getMaxVolume(), this.mVolumeProvider.getCurrentVolume()));
        param1VolumeProviderCompat.setCallback(this.mVolumeCallback);
        return;
      } 
      throw new IllegalArgumentException("volumeProvider may not be null");
    }
    
    public void setQueue(List<MediaSessionCompat.QueueItem> param1List) {
      this.mQueue = param1List;
      sendQueue(param1List);
    }
    
    public void setQueueTitle(CharSequence param1CharSequence) {
      this.mQueueTitle = param1CharSequence;
      sendQueueTitle(param1CharSequence);
    }
    
    public void setRatingType(int param1Int) {
      this.mRatingType = param1Int;
    }
    
    void setRccState(PlaybackStateCompat param1PlaybackStateCompat) {
      this.mRcc.setPlaybackState(getRccStateFromState(param1PlaybackStateCompat.getState()));
    }
    
    public void setRepeatMode(int param1Int) {
      if (this.mRepeatMode != param1Int) {
        this.mRepeatMode = param1Int;
        sendRepeatMode(param1Int);
      } 
    }
    
    public void setSessionActivity(PendingIntent param1PendingIntent) {
      synchronized (this.mLock) {
        this.mSessionActivity = param1PendingIntent;
        return;
      } 
    }
    
    public void setShuffleMode(int param1Int) {
      if (this.mShuffleMode != param1Int) {
        this.mShuffleMode = param1Int;
        sendShuffleMode(param1Int);
      } 
    }
    
    void setVolumeTo(int param1Int1, int param1Int2) {
      if (this.mVolumeType == 2) {
        VolumeProviderCompat volumeProviderCompat = this.mVolumeProvider;
        if (volumeProviderCompat != null)
          volumeProviderCompat.onSetVolumeTo(param1Int1); 
      } else {
        this.mAudioManager.setStreamVolume(this.mLocalStream, param1Int1, param1Int2);
      } 
    }
    
    void unregisterMediaButtonEventReceiver(PendingIntent param1PendingIntent, ComponentName param1ComponentName) {
      this.mAudioManager.unregisterMediaButtonEventReceiver(param1ComponentName);
    }
    
    boolean update() {
      boolean bool = this.mIsActive;
      boolean bool1 = true;
      if (bool) {
        if (!this.mIsMbrRegistered && (this.mFlags & 0x1) != 0) {
          registerMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
          this.mIsMbrRegistered = true;
        } else if (this.mIsMbrRegistered && (this.mFlags & 0x1) == 0) {
          unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
          this.mIsMbrRegistered = false;
        } 
        if (!this.mIsRccRegistered && (this.mFlags & 0x2) != 0) {
          this.mAudioManager.registerRemoteControlClient(this.mRcc);
          this.mIsRccRegistered = true;
        } else {
          if (this.mIsRccRegistered && (this.mFlags & 0x2) == 0) {
            this.mRcc.setPlaybackState(0);
            this.mAudioManager.unregisterRemoteControlClient(this.mRcc);
            this.mIsRccRegistered = false;
          } 
          bool1 = false;
        } 
      } else {
        if (this.mIsMbrRegistered) {
          unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
          this.mIsMbrRegistered = false;
        } 
        if (this.mIsRccRegistered) {
          this.mRcc.setPlaybackState(0);
          this.mAudioManager.unregisterRemoteControlClient(this.mRcc);
          this.mIsRccRegistered = false;
        } 
        bool1 = false;
      } 
      return bool1;
    }
    
    private static final class Command {
      public final String command;
      
      public final Bundle extras;
      
      public final ResultReceiver stub;
      
      public Command(String param2String, Bundle param2Bundle, ResultReceiver param2ResultReceiver) {
        this.command = param2String;
        this.extras = param2Bundle;
        this.stub = param2ResultReceiver;
      }
    }
    
    class MediaSessionStub extends IMediaSession.Stub {
      public void addQueueItem(MediaDescriptionCompat param2MediaDescriptionCompat) {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(25, param2MediaDescriptionCompat);
      }
      
      public void addQueueItemAt(MediaDescriptionCompat param2MediaDescriptionCompat, int param2Int) {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(26, param2MediaDescriptionCompat, param2Int);
      }
      
      public void adjustVolume(int param2Int1, int param2Int2, String param2String) {
        MediaSessionCompat.MediaSessionImplBase.this.adjustVolume(param2Int1, param2Int2);
      }
      
      public void fastForward() throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(16);
      }
      
      public Bundle getExtras() {
        synchronized (MediaSessionCompat.MediaSessionImplBase.this.mLock) {
          return MediaSessionCompat.MediaSessionImplBase.this.mExtras;
        } 
      }
      
      public long getFlags() {
        synchronized (MediaSessionCompat.MediaSessionImplBase.this.mLock) {
          return MediaSessionCompat.MediaSessionImplBase.this.mFlags;
        } 
      }
      
      public PendingIntent getLaunchPendingIntent() {
        synchronized (MediaSessionCompat.MediaSessionImplBase.this.mLock) {
          return MediaSessionCompat.MediaSessionImplBase.this.mSessionActivity;
        } 
      }
      
      public MediaMetadataCompat getMetadata() {
        return MediaSessionCompat.MediaSessionImplBase.this.mMetadata;
      }
      
      public String getPackageName() {
        return MediaSessionCompat.MediaSessionImplBase.this.mPackageName;
      }
      
      public PlaybackStateCompat getPlaybackState() {
        synchronized (MediaSessionCompat.MediaSessionImplBase.this.mLock) {
          PlaybackStateCompat playbackStateCompat = MediaSessionCompat.MediaSessionImplBase.this.mState;
          MediaMetadataCompat mediaMetadataCompat = MediaSessionCompat.MediaSessionImplBase.this.mMetadata;
          return MediaSessionCompat.getStateWithUpdatedPosition(playbackStateCompat, mediaMetadataCompat);
        } 
      }
      
      public List<MediaSessionCompat.QueueItem> getQueue() {
        synchronized (MediaSessionCompat.MediaSessionImplBase.this.mLock) {
          return MediaSessionCompat.MediaSessionImplBase.this.mQueue;
        } 
      }
      
      public CharSequence getQueueTitle() {
        return MediaSessionCompat.MediaSessionImplBase.this.mQueueTitle;
      }
      
      public int getRatingType() {
        return MediaSessionCompat.MediaSessionImplBase.this.mRatingType;
      }
      
      public int getRepeatMode() {
        return MediaSessionCompat.MediaSessionImplBase.this.mRepeatMode;
      }
      
      public int getShuffleMode() {
        return MediaSessionCompat.MediaSessionImplBase.this.mShuffleMode;
      }
      
      public String getTag() {
        return MediaSessionCompat.MediaSessionImplBase.this.mTag;
      }
      
      public ParcelableVolumeInfo getVolumeAttributes() {
        synchronized (MediaSessionCompat.MediaSessionImplBase.this.mLock) {
          int k;
          int m;
          int n;
          int i = MediaSessionCompat.MediaSessionImplBase.this.mVolumeType;
          int j = MediaSessionCompat.MediaSessionImplBase.this.mLocalStream;
          VolumeProviderCompat volumeProviderCompat = MediaSessionCompat.MediaSessionImplBase.this.mVolumeProvider;
          if (i == 2) {
            k = volumeProviderCompat.getVolumeControl();
            m = volumeProviderCompat.getMaxVolume();
            int i1 = volumeProviderCompat.getCurrentVolume();
            n = m;
            m = i1;
          } else {
            k = MediaSessionCompat.MediaSessionImplBase.this.mAudioManager.getStreamMaxVolume(j);
            m = MediaSessionCompat.MediaSessionImplBase.this.mAudioManager.getStreamVolume(j);
            byte b = 2;
            n = k;
            k = b;
          } 
          return new ParcelableVolumeInfo(i, j, k, n, m);
        } 
      }
      
      public boolean isCaptioningEnabled() {
        return MediaSessionCompat.MediaSessionImplBase.this.mCaptioningEnabled;
      }
      
      public boolean isShuffleModeEnabledRemoved() {
        return false;
      }
      
      public boolean isTransportControlEnabled() {
        boolean bool;
        if ((MediaSessionCompat.MediaSessionImplBase.this.mFlags & 0x2) != 0) {
          bool = true;
        } else {
          bool = false;
        } 
        return bool;
      }
      
      public void next() throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(14);
      }
      
      public void pause() throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(12);
      }
      
      public void play() throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(7);
      }
      
      public void playFromMediaId(String param2String, Bundle param2Bundle) throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(8, param2String, param2Bundle);
      }
      
      public void playFromSearch(String param2String, Bundle param2Bundle) throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(9, param2String, param2Bundle);
      }
      
      public void playFromUri(Uri param2Uri, Bundle param2Bundle) throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(10, param2Uri, param2Bundle);
      }
      
      public void prepare() throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(3);
      }
      
      public void prepareFromMediaId(String param2String, Bundle param2Bundle) throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(4, param2String, param2Bundle);
      }
      
      public void prepareFromSearch(String param2String, Bundle param2Bundle) throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(5, param2String, param2Bundle);
      }
      
      public void prepareFromUri(Uri param2Uri, Bundle param2Bundle) throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(6, param2Uri, param2Bundle);
      }
      
      public void previous() throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(15);
      }
      
      public void rate(RatingCompat param2RatingCompat) throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(19, param2RatingCompat);
      }
      
      public void rateWithExtras(RatingCompat param2RatingCompat, Bundle param2Bundle) throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(31, param2RatingCompat, param2Bundle);
      }
      
      public void registerCallbackListener(IMediaControllerCallback param2IMediaControllerCallback) {
        if (MediaSessionCompat.MediaSessionImplBase.this.mDestroyed) {
          try {
            param2IMediaControllerCallback.onSessionDestroyed();
          } catch (Exception exception) {}
          return;
        } 
        MediaSessionCompat.MediaSessionImplBase.this.mControllerCallbacks.register((IInterface)exception);
      }
      
      public void removeQueueItem(MediaDescriptionCompat param2MediaDescriptionCompat) {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(27, param2MediaDescriptionCompat);
      }
      
      public void removeQueueItemAt(int param2Int) {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(28, param2Int);
      }
      
      public void rewind() throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(17);
      }
      
      public void seekTo(long param2Long) throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(18, Long.valueOf(param2Long));
      }
      
      public void sendCommand(String param2String, Bundle param2Bundle, MediaSessionCompat.ResultReceiverWrapper param2ResultReceiverWrapper) {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(1, new MediaSessionCompat.MediaSessionImplBase.Command(param2String, param2Bundle, param2ResultReceiverWrapper.mResultReceiver));
      }
      
      public void sendCustomAction(String param2String, Bundle param2Bundle) throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(20, param2String, param2Bundle);
      }
      
      public boolean sendMediaButton(KeyEvent param2KeyEvent) {
        int i = MediaSessionCompat.MediaSessionImplBase.this.mFlags;
        boolean bool = true;
        if ((i & 0x1) == 0)
          bool = false; 
        if (bool)
          MediaSessionCompat.MediaSessionImplBase.this.postToHandler(21, param2KeyEvent); 
        return bool;
      }
      
      public void setCaptioningEnabled(boolean param2Boolean) throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(29, Boolean.valueOf(param2Boolean));
      }
      
      public void setRepeatMode(int param2Int) throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(23, param2Int);
      }
      
      public void setShuffleMode(int param2Int) throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(30, param2Int);
      }
      
      public void setShuffleModeEnabledRemoved(boolean param2Boolean) throws RemoteException {}
      
      public void setVolumeTo(int param2Int1, int param2Int2, String param2String) {
        MediaSessionCompat.MediaSessionImplBase.this.setVolumeTo(param2Int1, param2Int2);
      }
      
      public void skipToQueueItem(long param2Long) {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(11, Long.valueOf(param2Long));
      }
      
      public void stop() throws RemoteException {
        MediaSessionCompat.MediaSessionImplBase.this.postToHandler(13);
      }
      
      public void unregisterCallbackListener(IMediaControllerCallback param2IMediaControllerCallback) {
        MediaSessionCompat.MediaSessionImplBase.this.mControllerCallbacks.unregister(param2IMediaControllerCallback);
      }
    }
    
    class MessageHandler extends Handler {
      private static final int KEYCODE_MEDIA_PAUSE = 127;
      
      private static final int KEYCODE_MEDIA_PLAY = 126;
      
      private static final int MSG_ADD_QUEUE_ITEM = 25;
      
      private static final int MSG_ADD_QUEUE_ITEM_AT = 26;
      
      private static final int MSG_ADJUST_VOLUME = 2;
      
      private static final int MSG_COMMAND = 1;
      
      private static final int MSG_CUSTOM_ACTION = 20;
      
      private static final int MSG_FAST_FORWARD = 16;
      
      private static final int MSG_MEDIA_BUTTON = 21;
      
      private static final int MSG_NEXT = 14;
      
      private static final int MSG_PAUSE = 12;
      
      private static final int MSG_PLAY = 7;
      
      private static final int MSG_PLAY_MEDIA_ID = 8;
      
      private static final int MSG_PLAY_SEARCH = 9;
      
      private static final int MSG_PLAY_URI = 10;
      
      private static final int MSG_PREPARE = 3;
      
      private static final int MSG_PREPARE_MEDIA_ID = 4;
      
      private static final int MSG_PREPARE_SEARCH = 5;
      
      private static final int MSG_PREPARE_URI = 6;
      
      private static final int MSG_PREVIOUS = 15;
      
      private static final int MSG_RATE = 19;
      
      private static final int MSG_RATE_EXTRA = 31;
      
      private static final int MSG_REMOVE_QUEUE_ITEM = 27;
      
      private static final int MSG_REMOVE_QUEUE_ITEM_AT = 28;
      
      private static final int MSG_REWIND = 17;
      
      private static final int MSG_SEEK_TO = 18;
      
      private static final int MSG_SET_CAPTIONING_ENABLED = 29;
      
      private static final int MSG_SET_REPEAT_MODE = 23;
      
      private static final int MSG_SET_SHUFFLE_MODE = 30;
      
      private static final int MSG_SET_VOLUME = 22;
      
      private static final int MSG_SKIP_TO_ITEM = 11;
      
      private static final int MSG_STOP = 13;
      
      public MessageHandler(Looper param2Looper) {
        super(param2Looper);
      }
      
      private void onMediaButtonEvent(KeyEvent param2KeyEvent, MediaSessionCompat.Callback param2Callback) {
        long l;
        if (param2KeyEvent == null || param2KeyEvent.getAction() != 0);
        if (MediaSessionCompat.MediaSessionImplBase.this.mState == null) {
          l = 0L;
        } else {
          l = MediaSessionCompat.MediaSessionImplBase.this.mState.getActions();
        } 
        int i = param2KeyEvent.getKeyCode();
        if (i != 79)
          if (i != 126) {
            if (i != 127) {
              switch (i) {
                default:
                  return;
                case 90:
                  if ((l & 0x40L) != 0L)
                    param2Callback.onFastForward(); 
                case 89:
                  if ((l & 0x8L) != 0L)
                    param2Callback.onRewind(); 
                case 88:
                  if ((l & 0x10L) != 0L)
                    param2Callback.onSkipToPrevious(); 
                case 87:
                  if ((l & 0x20L) != 0L)
                    param2Callback.onSkipToNext(); 
                case 86:
                  if ((l & 0x1L) != 0L)
                    param2Callback.onStop(); 
                case 85:
                  break;
              } 
            } else if ((l & 0x2L) != 0L) {
              param2Callback.onPause();
            } 
          } else if ((l & 0x4L) != 0L) {
            param2Callback.onPlay();
          }  
        Log.w("MediaSessionCompat", "KEYCODE_MEDIA_PLAY_PAUSE and KEYCODE_HEADSETHOOK are handled already");
      }
      
      public void handleMessage(Message param2Message) {
        Intent intent;
        KeyEvent keyEvent;
        MediaSessionCompat.Callback callback = MediaSessionCompat.MediaSessionImplBase.this.mCallback;
        if (callback == null)
          return; 
        switch (param2Message.what) {
          default:
            return;
          case 31:
            callback.onSetRating((RatingCompat)param2Message.obj, param2Message.getData());
          case 30:
            callback.onSetShuffleMode(param2Message.arg1);
          case 29:
            callback.onSetCaptioningEnabled(((Boolean)param2Message.obj).booleanValue());
          case 28:
            if (MediaSessionCompat.MediaSessionImplBase.this.mQueue != null) {
              if (param2Message.arg1 >= 0 && param2Message.arg1 < MediaSessionCompat.MediaSessionImplBase.this.mQueue.size()) {
                MediaSessionCompat.QueueItem queueItem = MediaSessionCompat.MediaSessionImplBase.this.mQueue.get(param2Message.arg1);
              } else {
                param2Message = null;
              } 
              if (param2Message != null)
                callback.onRemoveQueueItem(param2Message.getDescription()); 
            } 
          case 27:
            callback.onRemoveQueueItem((MediaDescriptionCompat)param2Message.obj);
          case 26:
            callback.onAddQueueItem((MediaDescriptionCompat)param2Message.obj, param2Message.arg1);
          case 25:
            callback.onAddQueueItem((MediaDescriptionCompat)param2Message.obj);
          case 23:
            callback.onSetRepeatMode(param2Message.arg1);
          case 22:
            MediaSessionCompat.MediaSessionImplBase.this.setVolumeTo(param2Message.arg1, 0);
          case 21:
            keyEvent = (KeyEvent)param2Message.obj;
            intent = new Intent("android.intent.action.MEDIA_BUTTON");
            intent.putExtra("android.intent.extra.KEY_EVENT", (Parcelable)keyEvent);
            if (!callback.onMediaButtonEvent(intent))
              onMediaButtonEvent(keyEvent, callback); 
          case 20:
            callback.onCustomAction((String)((Message)intent).obj, intent.getData());
          case 19:
            callback.onSetRating((RatingCompat)((Message)intent).obj);
          case 18:
            callback.onSeekTo(((Long)((Message)intent).obj).longValue());
          case 17:
            callback.onRewind();
          case 16:
            callback.onFastForward();
          case 15:
            callback.onSkipToPrevious();
          case 14:
            callback.onSkipToNext();
          case 13:
            callback.onStop();
          case 12:
            callback.onPause();
          case 11:
            callback.onSkipToQueueItem(((Long)((Message)intent).obj).longValue());
          case 10:
            callback.onPlayFromUri((Uri)((Message)intent).obj, intent.getData());
          case 9:
            callback.onPlayFromSearch((String)((Message)intent).obj, intent.getData());
          case 8:
            callback.onPlayFromMediaId((String)((Message)intent).obj, intent.getData());
          case 7:
            callback.onPlay();
          case 6:
            callback.onPrepareFromUri((Uri)((Message)intent).obj, intent.getData());
          case 5:
            callback.onPrepareFromSearch((String)((Message)intent).obj, intent.getData());
          case 4:
            callback.onPrepareFromMediaId((String)((Message)intent).obj, intent.getData());
          case 3:
            callback.onPrepare();
          case 2:
            MediaSessionCompat.MediaSessionImplBase.this.adjustVolume(((Message)intent).arg1, 0);
          case 1:
            break;
        } 
        MediaSessionCompat.MediaSessionImplBase.Command command = (MediaSessionCompat.MediaSessionImplBase.Command)((Message)intent).obj;
        callback.onCommand(command.command, command.extras, command.stub);
      }
      
      public void post(int param2Int) {
        post(param2Int, (Object)null);
      }
      
      public void post(int param2Int, Object param2Object) {
        obtainMessage(param2Int, param2Object).sendToTarget();
      }
      
      public void post(int param2Int1, Object param2Object, int param2Int2) {
        obtainMessage(param2Int1, param2Int2, 0, param2Object).sendToTarget();
      }
      
      public void post(int param2Int, Object param2Object, Bundle param2Bundle) {
        param2Object = obtainMessage(param2Int, param2Object);
        param2Object.setData(param2Bundle);
        param2Object.sendToTarget();
      }
    }
  }
  
  class null extends VolumeProviderCompat.Callback {
    public void onVolumeChanged(VolumeProviderCompat param1VolumeProviderCompat) {
      if (this.this$0.mVolumeProvider != param1VolumeProviderCompat)
        return; 
      ParcelableVolumeInfo parcelableVolumeInfo = new ParcelableVolumeInfo(this.this$0.mVolumeType, this.this$0.mLocalStream, param1VolumeProviderCompat.getVolumeControl(), param1VolumeProviderCompat.getMaxVolume(), param1VolumeProviderCompat.getCurrentVolume());
      this.this$0.sendVolumeInfoChanged(parcelableVolumeInfo);
    }
  }
  
  private static final class Command {
    public final String command;
    
    public final Bundle extras;
    
    public final ResultReceiver stub;
    
    public Command(String param1String, Bundle param1Bundle, ResultReceiver param1ResultReceiver) {
      this.command = param1String;
      this.extras = param1Bundle;
      this.stub = param1ResultReceiver;
    }
  }
  
  class MediaSessionStub extends IMediaSession.Stub {
    public void addQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat) {
      this.this$0.postToHandler(25, param1MediaDescriptionCompat);
    }
    
    public void addQueueItemAt(MediaDescriptionCompat param1MediaDescriptionCompat, int param1Int) {
      this.this$0.postToHandler(26, param1MediaDescriptionCompat, param1Int);
    }
    
    public void adjustVolume(int param1Int1, int param1Int2, String param1String) {
      this.this$0.adjustVolume(param1Int1, param1Int2);
    }
    
    public void fastForward() throws RemoteException {
      this.this$0.postToHandler(16);
    }
    
    public Bundle getExtras() {
      synchronized (this.this$0.mLock) {
        return this.this$0.mExtras;
      } 
    }
    
    public long getFlags() {
      synchronized (this.this$0.mLock) {
        return this.this$0.mFlags;
      } 
    }
    
    public PendingIntent getLaunchPendingIntent() {
      synchronized (this.this$0.mLock) {
        return this.this$0.mSessionActivity;
      } 
    }
    
    public MediaMetadataCompat getMetadata() {
      return this.this$0.mMetadata;
    }
    
    public String getPackageName() {
      return this.this$0.mPackageName;
    }
    
    public PlaybackStateCompat getPlaybackState() {
      synchronized (this.this$0.mLock) {
        PlaybackStateCompat playbackStateCompat = this.this$0.mState;
        MediaMetadataCompat mediaMetadataCompat = this.this$0.mMetadata;
        return MediaSessionCompat.getStateWithUpdatedPosition(playbackStateCompat, mediaMetadataCompat);
      } 
    }
    
    public List<MediaSessionCompat.QueueItem> getQueue() {
      synchronized (this.this$0.mLock) {
        return this.this$0.mQueue;
      } 
    }
    
    public CharSequence getQueueTitle() {
      return this.this$0.mQueueTitle;
    }
    
    public int getRatingType() {
      return this.this$0.mRatingType;
    }
    
    public int getRepeatMode() {
      return this.this$0.mRepeatMode;
    }
    
    public int getShuffleMode() {
      return this.this$0.mShuffleMode;
    }
    
    public String getTag() {
      return this.this$0.mTag;
    }
    
    public ParcelableVolumeInfo getVolumeAttributes() {
      synchronized (this.this$0.mLock) {
        int k;
        int m;
        int n;
        int i = this.this$0.mVolumeType;
        int j = this.this$0.mLocalStream;
        VolumeProviderCompat volumeProviderCompat = this.this$0.mVolumeProvider;
        if (i == 2) {
          k = volumeProviderCompat.getVolumeControl();
          m = volumeProviderCompat.getMaxVolume();
          int i1 = volumeProviderCompat.getCurrentVolume();
          n = m;
          m = i1;
        } else {
          k = this.this$0.mAudioManager.getStreamMaxVolume(j);
          m = this.this$0.mAudioManager.getStreamVolume(j);
          byte b = 2;
          n = k;
          k = b;
        } 
        return new ParcelableVolumeInfo(i, j, k, n, m);
      } 
    }
    
    public boolean isCaptioningEnabled() {
      return this.this$0.mCaptioningEnabled;
    }
    
    public boolean isShuffleModeEnabledRemoved() {
      return false;
    }
    
    public boolean isTransportControlEnabled() {
      boolean bool;
      if ((this.this$0.mFlags & 0x2) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void next() throws RemoteException {
      this.this$0.postToHandler(14);
    }
    
    public void pause() throws RemoteException {
      this.this$0.postToHandler(12);
    }
    
    public void play() throws RemoteException {
      this.this$0.postToHandler(7);
    }
    
    public void playFromMediaId(String param1String, Bundle param1Bundle) throws RemoteException {
      this.this$0.postToHandler(8, param1String, param1Bundle);
    }
    
    public void playFromSearch(String param1String, Bundle param1Bundle) throws RemoteException {
      this.this$0.postToHandler(9, param1String, param1Bundle);
    }
    
    public void playFromUri(Uri param1Uri, Bundle param1Bundle) throws RemoteException {
      this.this$0.postToHandler(10, param1Uri, param1Bundle);
    }
    
    public void prepare() throws RemoteException {
      this.this$0.postToHandler(3);
    }
    
    public void prepareFromMediaId(String param1String, Bundle param1Bundle) throws RemoteException {
      this.this$0.postToHandler(4, param1String, param1Bundle);
    }
    
    public void prepareFromSearch(String param1String, Bundle param1Bundle) throws RemoteException {
      this.this$0.postToHandler(5, param1String, param1Bundle);
    }
    
    public void prepareFromUri(Uri param1Uri, Bundle param1Bundle) throws RemoteException {
      this.this$0.postToHandler(6, param1Uri, param1Bundle);
    }
    
    public void previous() throws RemoteException {
      this.this$0.postToHandler(15);
    }
    
    public void rate(RatingCompat param1RatingCompat) throws RemoteException {
      this.this$0.postToHandler(19, param1RatingCompat);
    }
    
    public void rateWithExtras(RatingCompat param1RatingCompat, Bundle param1Bundle) throws RemoteException {
      this.this$0.postToHandler(31, param1RatingCompat, param1Bundle);
    }
    
    public void registerCallbackListener(IMediaControllerCallback param1IMediaControllerCallback) {
      if (this.this$0.mDestroyed) {
        try {
          param1IMediaControllerCallback.onSessionDestroyed();
        } catch (Exception exception) {}
        return;
      } 
      this.this$0.mControllerCallbacks.register((IInterface)exception);
    }
    
    public void removeQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat) {
      this.this$0.postToHandler(27, param1MediaDescriptionCompat);
    }
    
    public void removeQueueItemAt(int param1Int) {
      this.this$0.postToHandler(28, param1Int);
    }
    
    public void rewind() throws RemoteException {
      this.this$0.postToHandler(17);
    }
    
    public void seekTo(long param1Long) throws RemoteException {
      this.this$0.postToHandler(18, Long.valueOf(param1Long));
    }
    
    public void sendCommand(String param1String, Bundle param1Bundle, MediaSessionCompat.ResultReceiverWrapper param1ResultReceiverWrapper) {
      this.this$0.postToHandler(1, new MediaSessionCompat.MediaSessionImplBase.Command(param1String, param1Bundle, param1ResultReceiverWrapper.mResultReceiver));
    }
    
    public void sendCustomAction(String param1String, Bundle param1Bundle) throws RemoteException {
      this.this$0.postToHandler(20, param1String, param1Bundle);
    }
    
    public boolean sendMediaButton(KeyEvent param1KeyEvent) {
      int i = this.this$0.mFlags;
      boolean bool = true;
      if ((i & 0x1) == 0)
        bool = false; 
      if (bool)
        this.this$0.postToHandler(21, param1KeyEvent); 
      return bool;
    }
    
    public void setCaptioningEnabled(boolean param1Boolean) throws RemoteException {
      this.this$0.postToHandler(29, Boolean.valueOf(param1Boolean));
    }
    
    public void setRepeatMode(int param1Int) throws RemoteException {
      this.this$0.postToHandler(23, param1Int);
    }
    
    public void setShuffleMode(int param1Int) throws RemoteException {
      this.this$0.postToHandler(30, param1Int);
    }
    
    public void setShuffleModeEnabledRemoved(boolean param1Boolean) throws RemoteException {}
    
    public void setVolumeTo(int param1Int1, int param1Int2, String param1String) {
      this.this$0.setVolumeTo(param1Int1, param1Int2);
    }
    
    public void skipToQueueItem(long param1Long) {
      this.this$0.postToHandler(11, Long.valueOf(param1Long));
    }
    
    public void stop() throws RemoteException {
      this.this$0.postToHandler(13);
    }
    
    public void unregisterCallbackListener(IMediaControllerCallback param1IMediaControllerCallback) {
      this.this$0.mControllerCallbacks.unregister(param1IMediaControllerCallback);
    }
  }
  
  class MessageHandler extends Handler {
    private static final int KEYCODE_MEDIA_PAUSE = 127;
    
    private static final int KEYCODE_MEDIA_PLAY = 126;
    
    private static final int MSG_ADD_QUEUE_ITEM = 25;
    
    private static final int MSG_ADD_QUEUE_ITEM_AT = 26;
    
    private static final int MSG_ADJUST_VOLUME = 2;
    
    private static final int MSG_COMMAND = 1;
    
    private static final int MSG_CUSTOM_ACTION = 20;
    
    private static final int MSG_FAST_FORWARD = 16;
    
    private static final int MSG_MEDIA_BUTTON = 21;
    
    private static final int MSG_NEXT = 14;
    
    private static final int MSG_PAUSE = 12;
    
    private static final int MSG_PLAY = 7;
    
    private static final int MSG_PLAY_MEDIA_ID = 8;
    
    private static final int MSG_PLAY_SEARCH = 9;
    
    private static final int MSG_PLAY_URI = 10;
    
    private static final int MSG_PREPARE = 3;
    
    private static final int MSG_PREPARE_MEDIA_ID = 4;
    
    private static final int MSG_PREPARE_SEARCH = 5;
    
    private static final int MSG_PREPARE_URI = 6;
    
    private static final int MSG_PREVIOUS = 15;
    
    private static final int MSG_RATE = 19;
    
    private static final int MSG_RATE_EXTRA = 31;
    
    private static final int MSG_REMOVE_QUEUE_ITEM = 27;
    
    private static final int MSG_REMOVE_QUEUE_ITEM_AT = 28;
    
    private static final int MSG_REWIND = 17;
    
    private static final int MSG_SEEK_TO = 18;
    
    private static final int MSG_SET_CAPTIONING_ENABLED = 29;
    
    private static final int MSG_SET_REPEAT_MODE = 23;
    
    private static final int MSG_SET_SHUFFLE_MODE = 30;
    
    private static final int MSG_SET_VOLUME = 22;
    
    private static final int MSG_SKIP_TO_ITEM = 11;
    
    private static final int MSG_STOP = 13;
    
    public MessageHandler(Looper param1Looper) {
      super(param1Looper);
    }
    
    private void onMediaButtonEvent(KeyEvent param1KeyEvent, MediaSessionCompat.Callback param1Callback) {
      long l;
      if (param1KeyEvent == null || param1KeyEvent.getAction() != 0);
      if (this.this$0.mState == null) {
        l = 0L;
      } else {
        l = this.this$0.mState.getActions();
      } 
      int i = param1KeyEvent.getKeyCode();
      if (i != 79)
        if (i != 126) {
          if (i != 127) {
            switch (i) {
              default:
                return;
              case 90:
                if ((l & 0x40L) != 0L)
                  param1Callback.onFastForward(); 
              case 89:
                if ((l & 0x8L) != 0L)
                  param1Callback.onRewind(); 
              case 88:
                if ((l & 0x10L) != 0L)
                  param1Callback.onSkipToPrevious(); 
              case 87:
                if ((l & 0x20L) != 0L)
                  param1Callback.onSkipToNext(); 
              case 86:
                if ((l & 0x1L) != 0L)
                  param1Callback.onStop(); 
              case 85:
                break;
            } 
          } else if ((l & 0x2L) != 0L) {
            param1Callback.onPause();
          } 
        } else if ((l & 0x4L) != 0L) {
          param1Callback.onPlay();
        }  
      Log.w("MediaSessionCompat", "KEYCODE_MEDIA_PLAY_PAUSE and KEYCODE_HEADSETHOOK are handled already");
    }
    
    public void handleMessage(Message param1Message) {
      Intent intent;
      KeyEvent keyEvent;
      MediaSessionCompat.Callback callback = this.this$0.mCallback;
      if (callback == null)
        return; 
      switch (param1Message.what) {
        default:
          return;
        case 31:
          callback.onSetRating((RatingCompat)param1Message.obj, param1Message.getData());
        case 30:
          callback.onSetShuffleMode(param1Message.arg1);
        case 29:
          callback.onSetCaptioningEnabled(((Boolean)param1Message.obj).booleanValue());
        case 28:
          if (this.this$0.mQueue != null) {
            if (param1Message.arg1 >= 0 && param1Message.arg1 < this.this$0.mQueue.size()) {
              MediaSessionCompat.QueueItem queueItem = this.this$0.mQueue.get(param1Message.arg1);
            } else {
              param1Message = null;
            } 
            if (param1Message != null)
              callback.onRemoveQueueItem(param1Message.getDescription()); 
          } 
        case 27:
          callback.onRemoveQueueItem((MediaDescriptionCompat)param1Message.obj);
        case 26:
          callback.onAddQueueItem((MediaDescriptionCompat)param1Message.obj, param1Message.arg1);
        case 25:
          callback.onAddQueueItem((MediaDescriptionCompat)param1Message.obj);
        case 23:
          callback.onSetRepeatMode(param1Message.arg1);
        case 22:
          this.this$0.setVolumeTo(param1Message.arg1, 0);
        case 21:
          keyEvent = (KeyEvent)param1Message.obj;
          intent = new Intent("android.intent.action.MEDIA_BUTTON");
          intent.putExtra("android.intent.extra.KEY_EVENT", (Parcelable)keyEvent);
          if (!callback.onMediaButtonEvent(intent))
            onMediaButtonEvent(keyEvent, callback); 
        case 20:
          callback.onCustomAction((String)((Message)intent).obj, intent.getData());
        case 19:
          callback.onSetRating((RatingCompat)((Message)intent).obj);
        case 18:
          callback.onSeekTo(((Long)((Message)intent).obj).longValue());
        case 17:
          callback.onRewind();
        case 16:
          callback.onFastForward();
        case 15:
          callback.onSkipToPrevious();
        case 14:
          callback.onSkipToNext();
        case 13:
          callback.onStop();
        case 12:
          callback.onPause();
        case 11:
          callback.onSkipToQueueItem(((Long)((Message)intent).obj).longValue());
        case 10:
          callback.onPlayFromUri((Uri)((Message)intent).obj, intent.getData());
        case 9:
          callback.onPlayFromSearch((String)((Message)intent).obj, intent.getData());
        case 8:
          callback.onPlayFromMediaId((String)((Message)intent).obj, intent.getData());
        case 7:
          callback.onPlay();
        case 6:
          callback.onPrepareFromUri((Uri)((Message)intent).obj, intent.getData());
        case 5:
          callback.onPrepareFromSearch((String)((Message)intent).obj, intent.getData());
        case 4:
          callback.onPrepareFromMediaId((String)((Message)intent).obj, intent.getData());
        case 3:
          callback.onPrepare();
        case 2:
          this.this$0.adjustVolume(((Message)intent).arg1, 0);
        case 1:
          break;
      } 
      MediaSessionCompat.MediaSessionImplBase.Command command = (MediaSessionCompat.MediaSessionImplBase.Command)((Message)intent).obj;
      callback.onCommand(command.command, command.extras, command.stub);
    }
    
    public void post(int param1Int) {
      post(param1Int, (Object)null);
    }
    
    public void post(int param1Int, Object param1Object) {
      obtainMessage(param1Int, param1Object).sendToTarget();
    }
    
    public void post(int param1Int1, Object param1Object, int param1Int2) {
      obtainMessage(param1Int1, param1Int2, 0, param1Object).sendToTarget();
    }
    
    public void post(int param1Int, Object param1Object, Bundle param1Bundle) {
      param1Object = obtainMessage(param1Int, param1Object);
      param1Object.setData(param1Bundle);
      param1Object.sendToTarget();
    }
  }
  
  public static interface OnActiveChangeListener {
    void onActiveChanged();
  }
  
  public static final class QueueItem implements Parcelable {
    public static final Parcelable.Creator<QueueItem> CREATOR = new Parcelable.Creator<QueueItem>() {
        public MediaSessionCompat.QueueItem createFromParcel(Parcel param2Parcel) {
          return new MediaSessionCompat.QueueItem(param2Parcel);
        }
        
        public MediaSessionCompat.QueueItem[] newArray(int param2Int) {
          return new MediaSessionCompat.QueueItem[param2Int];
        }
      };
    
    public static final int UNKNOWN_ID = -1;
    
    private final MediaDescriptionCompat mDescription;
    
    private final long mId;
    
    private Object mItem;
    
    QueueItem(Parcel param1Parcel) {
      this.mDescription = (MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel(param1Parcel);
      this.mId = param1Parcel.readLong();
    }
    
    public QueueItem(MediaDescriptionCompat param1MediaDescriptionCompat, long param1Long) {
      this(null, param1MediaDescriptionCompat, param1Long);
    }
    
    private QueueItem(Object param1Object, MediaDescriptionCompat param1MediaDescriptionCompat, long param1Long) {
      if (param1MediaDescriptionCompat != null) {
        if (param1Long != -1L) {
          this.mDescription = param1MediaDescriptionCompat;
          this.mId = param1Long;
          this.mItem = param1Object;
          return;
        } 
        throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
      } 
      throw new IllegalArgumentException("Description cannot be null.");
    }
    
    public static QueueItem fromQueueItem(Object param1Object) {
      return (param1Object == null || Build.VERSION.SDK_INT < 21) ? null : new QueueItem(param1Object, MediaDescriptionCompat.fromMediaDescription(MediaSessionCompatApi21.QueueItem.getDescription(param1Object)), MediaSessionCompatApi21.QueueItem.getQueueId(param1Object));
    }
    
    public static List<QueueItem> fromQueueItemList(List<?> param1List) {
      if (param1List == null || Build.VERSION.SDK_INT < 21)
        return null; 
      ArrayList<QueueItem> arrayList = new ArrayList();
      Iterator<?> iterator = param1List.iterator();
      while (iterator.hasNext())
        arrayList.add(fromQueueItem(iterator.next())); 
      return arrayList;
    }
    
    public int describeContents() {
      return 0;
    }
    
    public MediaDescriptionCompat getDescription() {
      return this.mDescription;
    }
    
    public long getQueueId() {
      return this.mId;
    }
    
    public Object getQueueItem() {
      if (this.mItem != null || Build.VERSION.SDK_INT < 21)
        return this.mItem; 
      Object object = MediaSessionCompatApi21.QueueItem.createItem(this.mDescription.getMediaDescription(), this.mId);
      this.mItem = object;
      return object;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("MediaSession.QueueItem {Description=");
      stringBuilder.append(this.mDescription);
      stringBuilder.append(", Id=");
      stringBuilder.append(this.mId);
      stringBuilder.append(" }");
      return stringBuilder.toString();
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      this.mDescription.writeToParcel(param1Parcel, param1Int);
      param1Parcel.writeLong(this.mId);
    }
  }
  
  static final class null implements Parcelable.Creator<QueueItem> {
    public MediaSessionCompat.QueueItem createFromParcel(Parcel param1Parcel) {
      return new MediaSessionCompat.QueueItem(param1Parcel);
    }
    
    public MediaSessionCompat.QueueItem[] newArray(int param1Int) {
      return new MediaSessionCompat.QueueItem[param1Int];
    }
  }
  
  static final class ResultReceiverWrapper implements Parcelable {
    public static final Parcelable.Creator<ResultReceiverWrapper> CREATOR = new Parcelable.Creator<ResultReceiverWrapper>() {
        public MediaSessionCompat.ResultReceiverWrapper createFromParcel(Parcel param2Parcel) {
          return new MediaSessionCompat.ResultReceiverWrapper(param2Parcel);
        }
        
        public MediaSessionCompat.ResultReceiverWrapper[] newArray(int param2Int) {
          return new MediaSessionCompat.ResultReceiverWrapper[param2Int];
        }
      };
    
    private ResultReceiver mResultReceiver;
    
    ResultReceiverWrapper(Parcel param1Parcel) {
      this.mResultReceiver = (ResultReceiver)ResultReceiver.CREATOR.createFromParcel(param1Parcel);
    }
    
    public ResultReceiverWrapper(ResultReceiver param1ResultReceiver) {
      this.mResultReceiver = param1ResultReceiver;
    }
    
    public int describeContents() {
      return 0;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      this.mResultReceiver.writeToParcel(param1Parcel, param1Int);
    }
  }
  
  static final class null implements Parcelable.Creator<ResultReceiverWrapper> {
    public MediaSessionCompat.ResultReceiverWrapper createFromParcel(Parcel param1Parcel) {
      return new MediaSessionCompat.ResultReceiverWrapper(param1Parcel);
    }
    
    public MediaSessionCompat.ResultReceiverWrapper[] newArray(int param1Int) {
      return new MediaSessionCompat.ResultReceiverWrapper[param1Int];
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface SessionFlags {}
  
  public static final class Token implements Parcelable {
    public static final Parcelable.Creator<Token> CREATOR = new Parcelable.Creator<Token>() {
        public MediaSessionCompat.Token createFromParcel(Parcel param2Parcel) {
          Parcelable parcelable;
          IBinder iBinder;
          if (Build.VERSION.SDK_INT >= 21) {
            parcelable = param2Parcel.readParcelable(null);
          } else {
            iBinder = parcelable.readStrongBinder();
          } 
          return new MediaSessionCompat.Token(iBinder);
        }
        
        public MediaSessionCompat.Token[] newArray(int param2Int) {
          return new MediaSessionCompat.Token[param2Int];
        }
      };
    
    private final IMediaSession mExtraBinder;
    
    private final Object mInner;
    
    Token(Object param1Object) {
      this(param1Object, null);
    }
    
    Token(Object param1Object, IMediaSession param1IMediaSession) {
      this.mInner = param1Object;
      this.mExtraBinder = param1IMediaSession;
    }
    
    public static Token fromToken(Object param1Object) {
      return fromToken(param1Object, null);
    }
    
    public static Token fromToken(Object param1Object, IMediaSession param1IMediaSession) {
      return (param1Object != null && Build.VERSION.SDK_INT >= 21) ? new Token(MediaSessionCompatApi21.verifyToken(param1Object), param1IMediaSession) : null;
    }
    
    public int describeContents() {
      return 0;
    }
    
    public boolean equals(Object param1Object) {
      boolean bool = true;
      if (this == param1Object)
        return true; 
      if (!(param1Object instanceof Token))
        return false; 
      Token token = (Token)param1Object;
      param1Object = this.mInner;
      if (param1Object == null) {
        if (token.mInner != null)
          bool = false; 
        return bool;
      } 
      Object object = token.mInner;
      return (object == null) ? false : param1Object.equals(object);
    }
    
    public IMediaSession getExtraBinder() {
      return this.mExtraBinder;
    }
    
    public Object getToken() {
      return this.mInner;
    }
    
    public int hashCode() {
      Object object = this.mInner;
      return (object == null) ? 0 : object.hashCode();
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      if (Build.VERSION.SDK_INT >= 21) {
        param1Parcel.writeParcelable((Parcelable)this.mInner, param1Int);
      } else {
        param1Parcel.writeStrongBinder((IBinder)this.mInner);
      } 
    }
  }
  
  static final class null implements Parcelable.Creator<Token> {
    public MediaSessionCompat.Token createFromParcel(Parcel param1Parcel) {
      Parcelable parcelable;
      IBinder iBinder;
      if (Build.VERSION.SDK_INT >= 21) {
        parcelable = param1Parcel.readParcelable(null);
      } else {
        iBinder = parcelable.readStrongBinder();
      } 
      return new MediaSessionCompat.Token(iBinder);
    }
    
    public MediaSessionCompat.Token[] newArray(int param1Int) {
      return new MediaSessionCompat.Token[param1Int];
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\session\MediaSessionCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */