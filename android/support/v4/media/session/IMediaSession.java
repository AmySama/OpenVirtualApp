package android.support.v4.media.session;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import java.util.List;

public interface IMediaSession extends IInterface {
  void addQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat) throws RemoteException;
  
  void addQueueItemAt(MediaDescriptionCompat paramMediaDescriptionCompat, int paramInt) throws RemoteException;
  
  void adjustVolume(int paramInt1, int paramInt2, String paramString) throws RemoteException;
  
  void fastForward() throws RemoteException;
  
  Bundle getExtras() throws RemoteException;
  
  long getFlags() throws RemoteException;
  
  PendingIntent getLaunchPendingIntent() throws RemoteException;
  
  MediaMetadataCompat getMetadata() throws RemoteException;
  
  String getPackageName() throws RemoteException;
  
  PlaybackStateCompat getPlaybackState() throws RemoteException;
  
  List<MediaSessionCompat.QueueItem> getQueue() throws RemoteException;
  
  CharSequence getQueueTitle() throws RemoteException;
  
  int getRatingType() throws RemoteException;
  
  int getRepeatMode() throws RemoteException;
  
  int getShuffleMode() throws RemoteException;
  
  String getTag() throws RemoteException;
  
  ParcelableVolumeInfo getVolumeAttributes() throws RemoteException;
  
  boolean isCaptioningEnabled() throws RemoteException;
  
  boolean isShuffleModeEnabledRemoved() throws RemoteException;
  
  boolean isTransportControlEnabled() throws RemoteException;
  
  void next() throws RemoteException;
  
  void pause() throws RemoteException;
  
  void play() throws RemoteException;
  
  void playFromMediaId(String paramString, Bundle paramBundle) throws RemoteException;
  
  void playFromSearch(String paramString, Bundle paramBundle) throws RemoteException;
  
  void playFromUri(Uri paramUri, Bundle paramBundle) throws RemoteException;
  
  void prepare() throws RemoteException;
  
  void prepareFromMediaId(String paramString, Bundle paramBundle) throws RemoteException;
  
  void prepareFromSearch(String paramString, Bundle paramBundle) throws RemoteException;
  
  void prepareFromUri(Uri paramUri, Bundle paramBundle) throws RemoteException;
  
  void previous() throws RemoteException;
  
  void rate(RatingCompat paramRatingCompat) throws RemoteException;
  
  void rateWithExtras(RatingCompat paramRatingCompat, Bundle paramBundle) throws RemoteException;
  
  void registerCallbackListener(IMediaControllerCallback paramIMediaControllerCallback) throws RemoteException;
  
  void removeQueueItem(MediaDescriptionCompat paramMediaDescriptionCompat) throws RemoteException;
  
  void removeQueueItemAt(int paramInt) throws RemoteException;
  
  void rewind() throws RemoteException;
  
  void seekTo(long paramLong) throws RemoteException;
  
  void sendCommand(String paramString, Bundle paramBundle, MediaSessionCompat.ResultReceiverWrapper paramResultReceiverWrapper) throws RemoteException;
  
  void sendCustomAction(String paramString, Bundle paramBundle) throws RemoteException;
  
  boolean sendMediaButton(KeyEvent paramKeyEvent) throws RemoteException;
  
  void setCaptioningEnabled(boolean paramBoolean) throws RemoteException;
  
  void setRepeatMode(int paramInt) throws RemoteException;
  
  void setShuffleMode(int paramInt) throws RemoteException;
  
  void setShuffleModeEnabledRemoved(boolean paramBoolean) throws RemoteException;
  
  void setVolumeTo(int paramInt1, int paramInt2, String paramString) throws RemoteException;
  
  void skipToQueueItem(long paramLong) throws RemoteException;
  
  void stop() throws RemoteException;
  
  void unregisterCallbackListener(IMediaControllerCallback paramIMediaControllerCallback) throws RemoteException;
  
  public static abstract class Stub extends Binder implements IMediaSession {
    private static final String DESCRIPTOR = "android.support.v4.media.session.IMediaSession";
    
    static final int TRANSACTION_addQueueItem = 41;
    
    static final int TRANSACTION_addQueueItemAt = 42;
    
    static final int TRANSACTION_adjustVolume = 11;
    
    static final int TRANSACTION_fastForward = 22;
    
    static final int TRANSACTION_getExtras = 31;
    
    static final int TRANSACTION_getFlags = 9;
    
    static final int TRANSACTION_getLaunchPendingIntent = 8;
    
    static final int TRANSACTION_getMetadata = 27;
    
    static final int TRANSACTION_getPackageName = 6;
    
    static final int TRANSACTION_getPlaybackState = 28;
    
    static final int TRANSACTION_getQueue = 29;
    
    static final int TRANSACTION_getQueueTitle = 30;
    
    static final int TRANSACTION_getRatingType = 32;
    
    static final int TRANSACTION_getRepeatMode = 37;
    
    static final int TRANSACTION_getShuffleMode = 47;
    
    static final int TRANSACTION_getTag = 7;
    
    static final int TRANSACTION_getVolumeAttributes = 10;
    
    static final int TRANSACTION_isCaptioningEnabled = 45;
    
    static final int TRANSACTION_isShuffleModeEnabledRemoved = 38;
    
    static final int TRANSACTION_isTransportControlEnabled = 5;
    
    static final int TRANSACTION_next = 20;
    
    static final int TRANSACTION_pause = 18;
    
    static final int TRANSACTION_play = 13;
    
    static final int TRANSACTION_playFromMediaId = 14;
    
    static final int TRANSACTION_playFromSearch = 15;
    
    static final int TRANSACTION_playFromUri = 16;
    
    static final int TRANSACTION_prepare = 33;
    
    static final int TRANSACTION_prepareFromMediaId = 34;
    
    static final int TRANSACTION_prepareFromSearch = 35;
    
    static final int TRANSACTION_prepareFromUri = 36;
    
    static final int TRANSACTION_previous = 21;
    
    static final int TRANSACTION_rate = 25;
    
    static final int TRANSACTION_rateWithExtras = 51;
    
    static final int TRANSACTION_registerCallbackListener = 3;
    
    static final int TRANSACTION_removeQueueItem = 43;
    
    static final int TRANSACTION_removeQueueItemAt = 44;
    
    static final int TRANSACTION_rewind = 23;
    
    static final int TRANSACTION_seekTo = 24;
    
    static final int TRANSACTION_sendCommand = 1;
    
    static final int TRANSACTION_sendCustomAction = 26;
    
    static final int TRANSACTION_sendMediaButton = 2;
    
    static final int TRANSACTION_setCaptioningEnabled = 46;
    
    static final int TRANSACTION_setRepeatMode = 39;
    
    static final int TRANSACTION_setShuffleMode = 48;
    
    static final int TRANSACTION_setShuffleModeEnabledRemoved = 40;
    
    static final int TRANSACTION_setVolumeTo = 12;
    
    static final int TRANSACTION_skipToQueueItem = 17;
    
    static final int TRANSACTION_stop = 19;
    
    static final int TRANSACTION_unregisterCallbackListener = 4;
    
    public Stub() {
      attachInterface(this, "android.support.v4.media.session.IMediaSession");
    }
    
    public static IMediaSession asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.support.v4.media.session.IMediaSession");
      return (iInterface != null && iInterface instanceof IMediaSession) ? (IMediaSession)iInterface : new Proxy(param1IBinder);
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      String str1;
      MediaDescriptionCompat mediaDescriptionCompat1 = null;
      MediaDescriptionCompat mediaDescriptionCompat2 = null;
      Bundle bundle1 = null;
      MediaDescriptionCompat mediaDescriptionCompat3 = null;
      Bundle bundle2 = null;
      Bundle bundle3 = null;
      Bundle bundle4 = null;
      String str2 = null;
      RatingCompat ratingCompat = null;
      Bundle bundle5 = null;
      Bundle bundle6 = null;
      Bundle bundle7 = null;
      Bundle bundle8 = null;
      MediaDescriptionCompat mediaDescriptionCompat4 = null;
      if (param1Int1 != 51) {
        if (param1Int1 != 1598968902) {
          boolean bool2;
          int i;
          boolean bool1;
          Bundle bundle9;
          CharSequence charSequence;
          List<MediaSessionCompat.QueueItem> list;
          PlaybackStateCompat playbackStateCompat;
          MediaMetadataCompat mediaMetadataCompat;
          ParcelableVolumeInfo parcelableVolumeInfo;
          PendingIntent pendingIntent;
          Bundle bundle11;
          RatingCompat ratingCompat1;
          Bundle bundle10;
          KeyEvent keyEvent;
          String str5;
          Bundle bundle12;
          String str4;
          MediaSessionCompat.ResultReceiverWrapper resultReceiverWrapper;
          long l;
          boolean bool3 = false;
          boolean bool4 = false;
          switch (param1Int1) {
            default:
              return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
            case 48:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              setShuffleMode(param1Parcel1.readInt());
              param1Parcel2.writeNoException();
              return true;
            case 47:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              param1Int1 = getShuffleMode();
              param1Parcel2.writeNoException();
              param1Parcel2.writeInt(param1Int1);
              return true;
            case 46:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              if (param1Parcel1.readInt() != 0)
                bool4 = true; 
              setCaptioningEnabled(bool4);
              param1Parcel2.writeNoException();
              return true;
            case 45:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              bool2 = isCaptioningEnabled();
              param1Parcel2.writeNoException();
              param1Parcel2.writeInt(bool2);
              return true;
            case 44:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              removeQueueItemAt(param1Parcel1.readInt());
              param1Parcel2.writeNoException();
              return true;
            case 43:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              mediaDescriptionCompat3 = mediaDescriptionCompat4;
              if (param1Parcel1.readInt() != 0)
                mediaDescriptionCompat3 = (MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel(param1Parcel1); 
              removeQueueItem(mediaDescriptionCompat3);
              param1Parcel2.writeNoException();
              return true;
            case 42:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              mediaDescriptionCompat3 = mediaDescriptionCompat1;
              if (param1Parcel1.readInt() != 0)
                mediaDescriptionCompat3 = (MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel(param1Parcel1); 
              addQueueItemAt(mediaDescriptionCompat3, param1Parcel1.readInt());
              param1Parcel2.writeNoException();
              return true;
            case 41:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              mediaDescriptionCompat3 = mediaDescriptionCompat2;
              if (param1Parcel1.readInt() != 0)
                mediaDescriptionCompat3 = (MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel(param1Parcel1); 
              addQueueItem(mediaDescriptionCompat3);
              param1Parcel2.writeNoException();
              return true;
            case 40:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              bool4 = bool3;
              if (param1Parcel1.readInt() != 0)
                bool4 = true; 
              setShuffleModeEnabledRemoved(bool4);
              param1Parcel2.writeNoException();
              return true;
            case 39:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              setRepeatMode(param1Parcel1.readInt());
              param1Parcel2.writeNoException();
              return true;
            case 38:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              bool2 = isShuffleModeEnabledRemoved();
              param1Parcel2.writeNoException();
              param1Parcel2.writeInt(bool2);
              return true;
            case 37:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              i = getRepeatMode();
              param1Parcel2.writeNoException();
              param1Parcel2.writeInt(i);
              return true;
            case 36:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              if (param1Parcel1.readInt() != 0) {
                Uri uri = (Uri)Uri.CREATOR.createFromParcel(param1Parcel1);
              } else {
                mediaDescriptionCompat3 = null;
              } 
              bundle7 = bundle1;
              if (param1Parcel1.readInt() != 0)
                bundle7 = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1); 
              prepareFromUri((Uri)mediaDescriptionCompat3, bundle7);
              param1Parcel2.writeNoException();
              return true;
            case 35:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              str5 = param1Parcel1.readString();
              if (param1Parcel1.readInt() != 0)
                bundle11 = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1); 
              prepareFromSearch(str5, bundle11);
              param1Parcel2.writeNoException();
              return true;
            case 34:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              str5 = param1Parcel1.readString();
              bundle11 = bundle2;
              if (param1Parcel1.readInt() != 0)
                bundle11 = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1); 
              prepareFromMediaId(str5, bundle11);
              param1Parcel2.writeNoException();
              return true;
            case 33:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              prepare();
              param1Parcel2.writeNoException();
              return true;
            case 32:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              i = getRatingType();
              param1Parcel2.writeNoException();
              param1Parcel2.writeInt(i);
              return true;
            case 31:
              param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaSession");
              bundle9 = getExtras();
              param1Parcel2.writeNoException();
              if (bundle9 != null) {
                param1Parcel2.writeInt(1);
                bundle9.writeToParcel(param1Parcel2, 1);
              } else {
                param1Parcel2.writeInt(0);
              } 
              return true;
            case 30:
              bundle9.enforceInterface("android.support.v4.media.session.IMediaSession");
              charSequence = getQueueTitle();
              param1Parcel2.writeNoException();
              if (charSequence != null) {
                param1Parcel2.writeInt(1);
                TextUtils.writeToParcel(charSequence, param1Parcel2, 1);
              } else {
                param1Parcel2.writeInt(0);
              } 
              return true;
            case 29:
              charSequence.enforceInterface("android.support.v4.media.session.IMediaSession");
              list = getQueue();
              param1Parcel2.writeNoException();
              param1Parcel2.writeTypedList(list);
              return true;
            case 28:
              list.enforceInterface("android.support.v4.media.session.IMediaSession");
              playbackStateCompat = getPlaybackState();
              param1Parcel2.writeNoException();
              if (playbackStateCompat != null) {
                param1Parcel2.writeInt(1);
                playbackStateCompat.writeToParcel(param1Parcel2, 1);
              } else {
                param1Parcel2.writeInt(0);
              } 
              return true;
            case 27:
              playbackStateCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              mediaMetadataCompat = getMetadata();
              param1Parcel2.writeNoException();
              if (mediaMetadataCompat != null) {
                param1Parcel2.writeInt(1);
                mediaMetadataCompat.writeToParcel(param1Parcel2, 1);
              } else {
                param1Parcel2.writeInt(0);
              } 
              return true;
            case 26:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              str5 = mediaMetadataCompat.readString();
              bundle11 = bundle3;
              if (mediaMetadataCompat.readInt() != 0)
                bundle11 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)mediaMetadataCompat); 
              sendCustomAction(str5, bundle11);
              param1Parcel2.writeNoException();
              return true;
            case 25:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              bundle11 = bundle4;
              if (mediaMetadataCompat.readInt() != 0)
                ratingCompat1 = (RatingCompat)RatingCompat.CREATOR.createFromParcel((Parcel)mediaMetadataCompat); 
              rate(ratingCompat1);
              param1Parcel2.writeNoException();
              return true;
            case 24:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              seekTo(mediaMetadataCompat.readLong());
              param1Parcel2.writeNoException();
              return true;
            case 23:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              rewind();
              param1Parcel2.writeNoException();
              return true;
            case 22:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              fastForward();
              param1Parcel2.writeNoException();
              return true;
            case 21:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              previous();
              param1Parcel2.writeNoException();
              return true;
            case 20:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              next();
              param1Parcel2.writeNoException();
              return true;
            case 19:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              stop();
              param1Parcel2.writeNoException();
              return true;
            case 18:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              pause();
              param1Parcel2.writeNoException();
              return true;
            case 17:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              skipToQueueItem(mediaMetadataCompat.readLong());
              param1Parcel2.writeNoException();
              return true;
            case 16:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              if (mediaMetadataCompat.readInt() != 0) {
                Uri uri = (Uri)Uri.CREATOR.createFromParcel((Parcel)mediaMetadataCompat);
              } else {
                ratingCompat1 = null;
              } 
              str5 = str2;
              if (mediaMetadataCompat.readInt() != 0)
                bundle12 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)mediaMetadataCompat); 
              playFromUri((Uri)ratingCompat1, bundle12);
              param1Parcel2.writeNoException();
              return true;
            case 15:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              str4 = mediaMetadataCompat.readString();
              ratingCompat1 = ratingCompat;
              if (mediaMetadataCompat.readInt() != 0)
                bundle10 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)mediaMetadataCompat); 
              playFromSearch(str4, bundle10);
              param1Parcel2.writeNoException();
              return true;
            case 14:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              str4 = mediaMetadataCompat.readString();
              bundle10 = bundle5;
              if (mediaMetadataCompat.readInt() != 0)
                bundle10 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)mediaMetadataCompat); 
              playFromMediaId(str4, bundle10);
              param1Parcel2.writeNoException();
              return true;
            case 13:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              play();
              param1Parcel2.writeNoException();
              return true;
            case 12:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              setVolumeTo(mediaMetadataCompat.readInt(), mediaMetadataCompat.readInt(), mediaMetadataCompat.readString());
              param1Parcel2.writeNoException();
              return true;
            case 11:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              adjustVolume(mediaMetadataCompat.readInt(), mediaMetadataCompat.readInt(), mediaMetadataCompat.readString());
              param1Parcel2.writeNoException();
              return true;
            case 10:
              mediaMetadataCompat.enforceInterface("android.support.v4.media.session.IMediaSession");
              parcelableVolumeInfo = getVolumeAttributes();
              param1Parcel2.writeNoException();
              if (parcelableVolumeInfo != null) {
                param1Parcel2.writeInt(1);
                parcelableVolumeInfo.writeToParcel(param1Parcel2, 1);
              } else {
                param1Parcel2.writeInt(0);
              } 
              return true;
            case 9:
              parcelableVolumeInfo.enforceInterface("android.support.v4.media.session.IMediaSession");
              l = getFlags();
              param1Parcel2.writeNoException();
              param1Parcel2.writeLong(l);
              return true;
            case 8:
              parcelableVolumeInfo.enforceInterface("android.support.v4.media.session.IMediaSession");
              pendingIntent = getLaunchPendingIntent();
              param1Parcel2.writeNoException();
              if (pendingIntent != null) {
                param1Parcel2.writeInt(1);
                pendingIntent.writeToParcel(param1Parcel2, 1);
              } else {
                param1Parcel2.writeInt(0);
              } 
              return true;
            case 7:
              pendingIntent.enforceInterface("android.support.v4.media.session.IMediaSession");
              str1 = getTag();
              param1Parcel2.writeNoException();
              param1Parcel2.writeString(str1);
              return true;
            case 6:
              str1.enforceInterface("android.support.v4.media.session.IMediaSession");
              str1 = getPackageName();
              param1Parcel2.writeNoException();
              param1Parcel2.writeString(str1);
              return true;
            case 5:
              str1.enforceInterface("android.support.v4.media.session.IMediaSession");
              bool1 = isTransportControlEnabled();
              param1Parcel2.writeNoException();
              param1Parcel2.writeInt(bool1);
              return true;
            case 4:
              str1.enforceInterface("android.support.v4.media.session.IMediaSession");
              unregisterCallbackListener(IMediaControllerCallback.Stub.asInterface(str1.readStrongBinder()));
              param1Parcel2.writeNoException();
              return true;
            case 3:
              str1.enforceInterface("android.support.v4.media.session.IMediaSession");
              registerCallbackListener(IMediaControllerCallback.Stub.asInterface(str1.readStrongBinder()));
              param1Parcel2.writeNoException();
              return true;
            case 2:
              str1.enforceInterface("android.support.v4.media.session.IMediaSession");
              bundle10 = bundle6;
              if (str1.readInt() != 0)
                keyEvent = (KeyEvent)KeyEvent.CREATOR.createFromParcel((Parcel)str1); 
              bool1 = sendMediaButton(keyEvent);
              param1Parcel2.writeNoException();
              param1Parcel2.writeInt(bool1);
              return true;
            case 1:
              break;
          } 
          str1.enforceInterface("android.support.v4.media.session.IMediaSession");
          String str3 = str1.readString();
          if (str1.readInt() != 0) {
            Bundle bundle = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)str1);
          } else {
            keyEvent = null;
          } 
          if (str1.readInt() != 0)
            resultReceiverWrapper = (MediaSessionCompat.ResultReceiverWrapper)MediaSessionCompat.ResultReceiverWrapper.CREATOR.createFromParcel((Parcel)str1); 
          sendCommand(str3, (Bundle)keyEvent, resultReceiverWrapper);
          param1Parcel2.writeNoException();
          return true;
        } 
        param1Parcel2.writeString("android.support.v4.media.session.IMediaSession");
        return true;
      } 
      str1.enforceInterface("android.support.v4.media.session.IMediaSession");
      if (str1.readInt() != 0) {
        RatingCompat ratingCompat1 = (RatingCompat)RatingCompat.CREATOR.createFromParcel((Parcel)str1);
      } else {
        mediaDescriptionCompat3 = null;
      } 
      bundle7 = bundle8;
      if (str1.readInt() != 0)
        bundle7 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)str1); 
      rateWithExtras((RatingCompat)mediaDescriptionCompat3, bundle7);
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements IMediaSession {
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public void addQueueItem(MediaDescriptionCompat param2MediaDescriptionCompat) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (param2MediaDescriptionCompat != null) {
            parcel1.writeInt(1);
            param2MediaDescriptionCompat.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          this.mRemote.transact(41, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void addQueueItemAt(MediaDescriptionCompat param2MediaDescriptionCompat, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (param2MediaDescriptionCompat != null) {
            parcel1.writeInt(1);
            param2MediaDescriptionCompat.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeInt(param2Int);
          this.mRemote.transact(42, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void adjustVolume(int param2Int1, int param2Int2, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          parcel1.writeString(param2String);
          this.mRemote.transact(11, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public void fastForward() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(22, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public Bundle getExtras() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          Bundle bundle;
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(31, parcel1, parcel2, 0);
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            bundle = (Bundle)Bundle.CREATOR.createFromParcel(parcel2);
          } else {
            bundle = null;
          } 
          return bundle;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public long getFlags() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(9, parcel1, parcel2, 0);
          parcel2.readException();
          return parcel2.readLong();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "android.support.v4.media.session.IMediaSession";
      }
      
      public PendingIntent getLaunchPendingIntent() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          PendingIntent pendingIntent;
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(8, parcel1, parcel2, 0);
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            pendingIntent = (PendingIntent)PendingIntent.CREATOR.createFromParcel(parcel2);
          } else {
            pendingIntent = null;
          } 
          return pendingIntent;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public MediaMetadataCompat getMetadata() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          MediaMetadataCompat mediaMetadataCompat;
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(27, parcel1, parcel2, 0);
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            mediaMetadataCompat = (MediaMetadataCompat)MediaMetadataCompat.CREATOR.createFromParcel(parcel2);
          } else {
            mediaMetadataCompat = null;
          } 
          return mediaMetadataCompat;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getPackageName() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(6, parcel1, parcel2, 0);
          parcel2.readException();
          return parcel2.readString();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public PlaybackStateCompat getPlaybackState() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          PlaybackStateCompat playbackStateCompat;
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(28, parcel1, parcel2, 0);
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            playbackStateCompat = (PlaybackStateCompat)PlaybackStateCompat.CREATOR.createFromParcel(parcel2);
          } else {
            playbackStateCompat = null;
          } 
          return playbackStateCompat;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<MediaSessionCompat.QueueItem> getQueue() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(29, parcel1, parcel2, 0);
          parcel2.readException();
          return parcel2.createTypedArrayList(MediaSessionCompat.QueueItem.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public CharSequence getQueueTitle() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          CharSequence charSequence;
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(30, parcel1, parcel2, 0);
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            charSequence = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel2);
          } else {
            charSequence = null;
          } 
          return charSequence;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int getRatingType() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(32, parcel1, parcel2, 0);
          parcel2.readException();
          return parcel2.readInt();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int getRepeatMode() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(37, parcel1, parcel2, 0);
          parcel2.readException();
          return parcel2.readInt();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int getShuffleMode() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(47, parcel1, parcel2, 0);
          parcel2.readException();
          return parcel2.readInt();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getTag() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(7, parcel1, parcel2, 0);
          parcel2.readException();
          return parcel2.readString();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public ParcelableVolumeInfo getVolumeAttributes() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          ParcelableVolumeInfo parcelableVolumeInfo;
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(10, parcel1, parcel2, 0);
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            parcelableVolumeInfo = (ParcelableVolumeInfo)ParcelableVolumeInfo.CREATOR.createFromParcel(parcel2);
          } else {
            parcelableVolumeInfo = null;
          } 
          return parcelableVolumeInfo;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean isCaptioningEnabled() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          iBinder.transact(45, parcel1, parcel2, 0);
          parcel2.readException();
          int i = parcel2.readInt();
          if (i != 0)
            bool = true; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean isShuffleModeEnabledRemoved() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          iBinder.transact(38, parcel1, parcel2, 0);
          parcel2.readException();
          int i = parcel2.readInt();
          if (i != 0)
            bool = true; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean isTransportControlEnabled() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          iBinder.transact(5, parcel1, parcel2, 0);
          parcel2.readException();
          int i = parcel2.readInt();
          if (i != 0)
            bool = true; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void next() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(20, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void pause() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(18, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void play() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(13, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void playFromMediaId(String param2String, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          parcel1.writeString(param2String);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          this.mRemote.transact(14, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void playFromSearch(String param2String, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          parcel1.writeString(param2String);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          this.mRemote.transact(15, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void playFromUri(Uri param2Uri, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (param2Uri != null) {
            parcel1.writeInt(1);
            param2Uri.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          this.mRemote.transact(16, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void prepare() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(33, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void prepareFromMediaId(String param2String, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          parcel1.writeString(param2String);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          this.mRemote.transact(34, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void prepareFromSearch(String param2String, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          parcel1.writeString(param2String);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          this.mRemote.transact(35, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void prepareFromUri(Uri param2Uri, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (param2Uri != null) {
            parcel1.writeInt(1);
            param2Uri.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          this.mRemote.transact(36, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void previous() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(21, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void rate(RatingCompat param2RatingCompat) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (param2RatingCompat != null) {
            parcel1.writeInt(1);
            param2RatingCompat.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          this.mRemote.transact(25, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void rateWithExtras(RatingCompat param2RatingCompat, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (param2RatingCompat != null) {
            parcel1.writeInt(1);
            param2RatingCompat.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          this.mRemote.transact(51, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void registerCallbackListener(IMediaControllerCallback param2IMediaControllerCallback) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (param2IMediaControllerCallback != null) {
            IBinder iBinder = param2IMediaControllerCallback.asBinder();
          } else {
            param2IMediaControllerCallback = null;
          } 
          parcel1.writeStrongBinder((IBinder)param2IMediaControllerCallback);
          this.mRemote.transact(3, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void removeQueueItem(MediaDescriptionCompat param2MediaDescriptionCompat) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (param2MediaDescriptionCompat != null) {
            parcel1.writeInt(1);
            param2MediaDescriptionCompat.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          this.mRemote.transact(43, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void removeQueueItemAt(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          parcel1.writeInt(param2Int);
          this.mRemote.transact(44, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void rewind() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(23, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void seekTo(long param2Long) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          parcel1.writeLong(param2Long);
          this.mRemote.transact(24, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void sendCommand(String param2String, Bundle param2Bundle, MediaSessionCompat.ResultReceiverWrapper param2ResultReceiverWrapper) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          parcel1.writeString(param2String);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (param2ResultReceiverWrapper != null) {
            parcel1.writeInt(1);
            param2ResultReceiverWrapper.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          this.mRemote.transact(1, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void sendCustomAction(String param2String, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          parcel1.writeString(param2String);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          this.mRemote.transact(26, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean sendMediaButton(KeyEvent param2KeyEvent) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          boolean bool = true;
          if (param2KeyEvent != null) {
            parcel1.writeInt(1);
            param2KeyEvent.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          this.mRemote.transact(2, parcel1, parcel2, 0);
          parcel2.readException();
          int i = parcel2.readInt();
          if (i == 0)
            bool = false; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setCaptioningEnabled(boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          this.mRemote.transact(46, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setRepeatMode(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          parcel1.writeInt(param2Int);
          this.mRemote.transact(39, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setShuffleMode(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          parcel1.writeInt(param2Int);
          this.mRemote.transact(48, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setShuffleModeEnabledRemoved(boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          this.mRemote.transact(40, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setVolumeTo(int param2Int1, int param2Int2, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          parcel1.writeString(param2String);
          this.mRemote.transact(12, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void skipToQueueItem(long param2Long) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          parcel1.writeLong(param2Long);
          this.mRemote.transact(17, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void stop() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          this.mRemote.transact(19, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void unregisterCallbackListener(IMediaControllerCallback param2IMediaControllerCallback) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
          if (param2IMediaControllerCallback != null) {
            IBinder iBinder = param2IMediaControllerCallback.asBinder();
          } else {
            param2IMediaControllerCallback = null;
          } 
          parcel1.writeStrongBinder((IBinder)param2IMediaControllerCallback);
          this.mRemote.transact(4, parcel1, parcel2, 0);
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements IMediaSession {
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public void addQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        if (param1MediaDescriptionCompat != null) {
          parcel1.writeInt(1);
          param1MediaDescriptionCompat.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        this.mRemote.transact(41, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void addQueueItemAt(MediaDescriptionCompat param1MediaDescriptionCompat, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        if (param1MediaDescriptionCompat != null) {
          parcel1.writeInt(1);
          param1MediaDescriptionCompat.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeInt(param1Int);
        this.mRemote.transact(42, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void adjustVolume(int param1Int1, int param1Int2, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        parcel1.writeString(param1String);
        this.mRemote.transact(11, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public void fastForward() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(22, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public Bundle getExtras() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        Bundle bundle;
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(31, parcel1, parcel2, 0);
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          bundle = (Bundle)Bundle.CREATOR.createFromParcel(parcel2);
        } else {
          bundle = null;
        } 
        return bundle;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public long getFlags() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(9, parcel1, parcel2, 0);
        parcel2.readException();
        return parcel2.readLong();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "android.support.v4.media.session.IMediaSession";
    }
    
    public PendingIntent getLaunchPendingIntent() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        PendingIntent pendingIntent;
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(8, parcel1, parcel2, 0);
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          pendingIntent = (PendingIntent)PendingIntent.CREATOR.createFromParcel(parcel2);
        } else {
          pendingIntent = null;
        } 
        return pendingIntent;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public MediaMetadataCompat getMetadata() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        MediaMetadataCompat mediaMetadataCompat;
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(27, parcel1, parcel2, 0);
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          mediaMetadataCompat = (MediaMetadataCompat)MediaMetadataCompat.CREATOR.createFromParcel(parcel2);
        } else {
          mediaMetadataCompat = null;
        } 
        return mediaMetadataCompat;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getPackageName() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(6, parcel1, parcel2, 0);
        parcel2.readException();
        return parcel2.readString();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public PlaybackStateCompat getPlaybackState() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        PlaybackStateCompat playbackStateCompat;
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(28, parcel1, parcel2, 0);
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          playbackStateCompat = (PlaybackStateCompat)PlaybackStateCompat.CREATOR.createFromParcel(parcel2);
        } else {
          playbackStateCompat = null;
        } 
        return playbackStateCompat;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<MediaSessionCompat.QueueItem> getQueue() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(29, parcel1, parcel2, 0);
        parcel2.readException();
        return parcel2.createTypedArrayList(MediaSessionCompat.QueueItem.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public CharSequence getQueueTitle() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        CharSequence charSequence;
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(30, parcel1, parcel2, 0);
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          charSequence = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel2);
        } else {
          charSequence = null;
        } 
        return charSequence;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int getRatingType() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(32, parcel1, parcel2, 0);
        parcel2.readException();
        return parcel2.readInt();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int getRepeatMode() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(37, parcel1, parcel2, 0);
        parcel2.readException();
        return parcel2.readInt();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int getShuffleMode() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(47, parcel1, parcel2, 0);
        parcel2.readException();
        return parcel2.readInt();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getTag() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(7, parcel1, parcel2, 0);
        parcel2.readException();
        return parcel2.readString();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public ParcelableVolumeInfo getVolumeAttributes() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        ParcelableVolumeInfo parcelableVolumeInfo;
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(10, parcel1, parcel2, 0);
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          parcelableVolumeInfo = (ParcelableVolumeInfo)ParcelableVolumeInfo.CREATOR.createFromParcel(parcel2);
        } else {
          parcelableVolumeInfo = null;
        } 
        return parcelableVolumeInfo;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean isCaptioningEnabled() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        iBinder.transact(45, parcel1, parcel2, 0);
        parcel2.readException();
        int i = parcel2.readInt();
        if (i != 0)
          bool = true; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean isShuffleModeEnabledRemoved() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        iBinder.transact(38, parcel1, parcel2, 0);
        parcel2.readException();
        int i = parcel2.readInt();
        if (i != 0)
          bool = true; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean isTransportControlEnabled() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        iBinder.transact(5, parcel1, parcel2, 0);
        parcel2.readException();
        int i = parcel2.readInt();
        if (i != 0)
          bool = true; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void next() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(20, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void pause() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(18, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void play() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(13, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void playFromMediaId(String param1String, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        parcel1.writeString(param1String);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        this.mRemote.transact(14, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void playFromSearch(String param1String, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        parcel1.writeString(param1String);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        this.mRemote.transact(15, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void playFromUri(Uri param1Uri, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        if (param1Uri != null) {
          parcel1.writeInt(1);
          param1Uri.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        this.mRemote.transact(16, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void prepare() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(33, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void prepareFromMediaId(String param1String, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        parcel1.writeString(param1String);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        this.mRemote.transact(34, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void prepareFromSearch(String param1String, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        parcel1.writeString(param1String);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        this.mRemote.transact(35, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void prepareFromUri(Uri param1Uri, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        if (param1Uri != null) {
          parcel1.writeInt(1);
          param1Uri.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        this.mRemote.transact(36, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void previous() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(21, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void rate(RatingCompat param1RatingCompat) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        if (param1RatingCompat != null) {
          parcel1.writeInt(1);
          param1RatingCompat.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        this.mRemote.transact(25, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void rateWithExtras(RatingCompat param1RatingCompat, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        if (param1RatingCompat != null) {
          parcel1.writeInt(1);
          param1RatingCompat.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        this.mRemote.transact(51, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void registerCallbackListener(IMediaControllerCallback param1IMediaControllerCallback) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        if (param1IMediaControllerCallback != null) {
          IBinder iBinder = param1IMediaControllerCallback.asBinder();
        } else {
          param1IMediaControllerCallback = null;
        } 
        parcel1.writeStrongBinder((IBinder)param1IMediaControllerCallback);
        this.mRemote.transact(3, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void removeQueueItem(MediaDescriptionCompat param1MediaDescriptionCompat) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        if (param1MediaDescriptionCompat != null) {
          parcel1.writeInt(1);
          param1MediaDescriptionCompat.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        this.mRemote.transact(43, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void removeQueueItemAt(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        parcel1.writeInt(param1Int);
        this.mRemote.transact(44, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void rewind() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(23, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void seekTo(long param1Long) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        parcel1.writeLong(param1Long);
        this.mRemote.transact(24, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void sendCommand(String param1String, Bundle param1Bundle, MediaSessionCompat.ResultReceiverWrapper param1ResultReceiverWrapper) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        parcel1.writeString(param1String);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (param1ResultReceiverWrapper != null) {
          parcel1.writeInt(1);
          param1ResultReceiverWrapper.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        this.mRemote.transact(1, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void sendCustomAction(String param1String, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        parcel1.writeString(param1String);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        this.mRemote.transact(26, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean sendMediaButton(KeyEvent param1KeyEvent) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        boolean bool = true;
        if (param1KeyEvent != null) {
          parcel1.writeInt(1);
          param1KeyEvent.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        this.mRemote.transact(2, parcel1, parcel2, 0);
        parcel2.readException();
        int i = parcel2.readInt();
        if (i == 0)
          bool = false; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setCaptioningEnabled(boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        this.mRemote.transact(46, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setRepeatMode(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        parcel1.writeInt(param1Int);
        this.mRemote.transact(39, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setShuffleMode(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        parcel1.writeInt(param1Int);
        this.mRemote.transact(48, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setShuffleModeEnabledRemoved(boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        this.mRemote.transact(40, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setVolumeTo(int param1Int1, int param1Int2, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        parcel1.writeString(param1String);
        this.mRemote.transact(12, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void skipToQueueItem(long param1Long) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        parcel1.writeLong(param1Long);
        this.mRemote.transact(17, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void stop() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        this.mRemote.transact(19, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void unregisterCallbackListener(IMediaControllerCallback param1IMediaControllerCallback) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
        if (param1IMediaControllerCallback != null) {
          IBinder iBinder = param1IMediaControllerCallback.asBinder();
        } else {
          param1IMediaControllerCallback = null;
        } 
        parcel1.writeStrongBinder((IBinder)param1IMediaControllerCallback);
        this.mRemote.transact(4, parcel1, parcel2, 0);
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\session\IMediaSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */