package android.support.v4.media.session;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import java.util.List;

public interface IMediaControllerCallback extends IInterface {
  void onCaptioningEnabledChanged(boolean paramBoolean) throws RemoteException;
  
  void onEvent(String paramString, Bundle paramBundle) throws RemoteException;
  
  void onExtrasChanged(Bundle paramBundle) throws RemoteException;
  
  void onMetadataChanged(MediaMetadataCompat paramMediaMetadataCompat) throws RemoteException;
  
  void onPlaybackStateChanged(PlaybackStateCompat paramPlaybackStateCompat) throws RemoteException;
  
  void onQueueChanged(List<MediaSessionCompat.QueueItem> paramList) throws RemoteException;
  
  void onQueueTitleChanged(CharSequence paramCharSequence) throws RemoteException;
  
  void onRepeatModeChanged(int paramInt) throws RemoteException;
  
  void onSessionDestroyed() throws RemoteException;
  
  void onSessionReady() throws RemoteException;
  
  void onShuffleModeChanged(int paramInt) throws RemoteException;
  
  void onShuffleModeChangedRemoved(boolean paramBoolean) throws RemoteException;
  
  void onVolumeInfoChanged(ParcelableVolumeInfo paramParcelableVolumeInfo) throws RemoteException;
  
  public static abstract class Stub extends Binder implements IMediaControllerCallback {
    private static final String DESCRIPTOR = "android.support.v4.media.session.IMediaControllerCallback";
    
    static final int TRANSACTION_onCaptioningEnabledChanged = 11;
    
    static final int TRANSACTION_onEvent = 1;
    
    static final int TRANSACTION_onExtrasChanged = 7;
    
    static final int TRANSACTION_onMetadataChanged = 4;
    
    static final int TRANSACTION_onPlaybackStateChanged = 3;
    
    static final int TRANSACTION_onQueueChanged = 5;
    
    static final int TRANSACTION_onQueueTitleChanged = 6;
    
    static final int TRANSACTION_onRepeatModeChanged = 9;
    
    static final int TRANSACTION_onSessionDestroyed = 2;
    
    static final int TRANSACTION_onSessionReady = 13;
    
    static final int TRANSACTION_onShuffleModeChanged = 12;
    
    static final int TRANSACTION_onShuffleModeChangedRemoved = 10;
    
    static final int TRANSACTION_onVolumeInfoChanged = 8;
    
    public Stub() {
      attachInterface(this, "android.support.v4.media.session.IMediaControllerCallback");
    }
    
    public static IMediaControllerCallback asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.support.v4.media.session.IMediaControllerCallback");
      return (iInterface != null && iInterface instanceof IMediaControllerCallback) ? (IMediaControllerCallback)iInterface : new Proxy(param1IBinder);
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      Bundle bundle;
      if (param1Int1 != 1598968902) {
        ParcelableVolumeInfo parcelableVolumeInfo1;
        Bundle bundle1;
        CharSequence charSequence1;
        MediaMetadataCompat mediaMetadataCompat1;
        boolean bool1 = false;
        boolean bool2 = false;
        ParcelableVolumeInfo parcelableVolumeInfo2 = null;
        Bundle bundle2 = null;
        CharSequence charSequence2 = null;
        MediaMetadataCompat mediaMetadataCompat2 = null;
        PlaybackStateCompat playbackStateCompat2 = null;
        Parcel parcel = null;
        switch (param1Int1) {
          default:
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
          case 13:
            param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
            onSessionReady();
            return true;
          case 12:
            param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
            onShuffleModeChanged(param1Parcel1.readInt());
            return true;
          case 11:
            param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
            if (param1Parcel1.readInt() != 0)
              bool2 = true; 
            onCaptioningEnabledChanged(bool2);
            return true;
          case 10:
            param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
            bool2 = bool1;
            if (param1Parcel1.readInt() != 0)
              bool2 = true; 
            onShuffleModeChangedRemoved(bool2);
            return true;
          case 9:
            param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
            onRepeatModeChanged(param1Parcel1.readInt());
            return true;
          case 8:
            param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
            param1Parcel2 = parcel;
            if (param1Parcel1.readInt() != 0)
              parcelableVolumeInfo1 = (ParcelableVolumeInfo)ParcelableVolumeInfo.CREATOR.createFromParcel(param1Parcel1); 
            onVolumeInfoChanged(parcelableVolumeInfo1);
            return true;
          case 7:
            param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
            parcelableVolumeInfo1 = parcelableVolumeInfo2;
            if (param1Parcel1.readInt() != 0)
              bundle1 = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1); 
            onExtrasChanged(bundle1);
            return true;
          case 6:
            param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
            bundle1 = bundle2;
            if (param1Parcel1.readInt() != 0)
              charSequence1 = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(param1Parcel1); 
            onQueueTitleChanged(charSequence1);
            return true;
          case 5:
            param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
            onQueueChanged(param1Parcel1.createTypedArrayList(MediaSessionCompat.QueueItem.CREATOR));
            return true;
          case 4:
            param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
            charSequence1 = charSequence2;
            if (param1Parcel1.readInt() != 0)
              mediaMetadataCompat1 = (MediaMetadataCompat)MediaMetadataCompat.CREATOR.createFromParcel(param1Parcel1); 
            onMetadataChanged(mediaMetadataCompat1);
            return true;
          case 3:
            param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
            mediaMetadataCompat1 = mediaMetadataCompat2;
            if (param1Parcel1.readInt() != 0)
              playbackStateCompat1 = (PlaybackStateCompat)PlaybackStateCompat.CREATOR.createFromParcel(param1Parcel1); 
            onPlaybackStateChanged(playbackStateCompat1);
            return true;
          case 2:
            param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
            onSessionDestroyed();
            return true;
          case 1:
            break;
        } 
        param1Parcel1.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
        String str = param1Parcel1.readString();
        PlaybackStateCompat playbackStateCompat1 = playbackStateCompat2;
        if (param1Parcel1.readInt() != 0)
          bundle = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1); 
        onEvent(str, bundle);
        return true;
      } 
      bundle.writeString("android.support.v4.media.session.IMediaControllerCallback");
      return true;
    }
    
    private static class Proxy implements IMediaControllerCallback {
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "android.support.v4.media.session.IMediaControllerCallback";
      }
      
      public void onCaptioningEnabledChanged(boolean param2Boolean) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          boolean bool;
          parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel.writeInt(bool);
          this.mRemote.transact(11, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public void onEvent(String param2String, Bundle param2Bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
          parcel.writeString(param2String);
          if (param2Bundle != null) {
            parcel.writeInt(1);
            param2Bundle.writeToParcel(parcel, 0);
          } else {
            parcel.writeInt(0);
          } 
          this.mRemote.transact(1, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public void onExtrasChanged(Bundle param2Bundle) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
          if (param2Bundle != null) {
            parcel.writeInt(1);
            param2Bundle.writeToParcel(parcel, 0);
          } else {
            parcel.writeInt(0);
          } 
          this.mRemote.transact(7, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public void onMetadataChanged(MediaMetadataCompat param2MediaMetadataCompat) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
          if (param2MediaMetadataCompat != null) {
            parcel.writeInt(1);
            param2MediaMetadataCompat.writeToParcel(parcel, 0);
          } else {
            parcel.writeInt(0);
          } 
          this.mRemote.transact(4, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public void onPlaybackStateChanged(PlaybackStateCompat param2PlaybackStateCompat) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
          if (param2PlaybackStateCompat != null) {
            parcel.writeInt(1);
            param2PlaybackStateCompat.writeToParcel(parcel, 0);
          } else {
            parcel.writeInt(0);
          } 
          this.mRemote.transact(3, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public void onQueueChanged(List<MediaSessionCompat.QueueItem> param2List) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
          parcel.writeTypedList(param2List);
          this.mRemote.transact(5, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public void onQueueTitleChanged(CharSequence param2CharSequence) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
          if (param2CharSequence != null) {
            parcel.writeInt(1);
            TextUtils.writeToParcel(param2CharSequence, parcel, 0);
          } else {
            parcel.writeInt(0);
          } 
          this.mRemote.transact(6, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public void onRepeatModeChanged(int param2Int) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
          parcel.writeInt(param2Int);
          this.mRemote.transact(9, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public void onSessionDestroyed() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
          this.mRemote.transact(2, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public void onSessionReady() throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
          this.mRemote.transact(13, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public void onShuffleModeChanged(int param2Int) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
          parcel.writeInt(param2Int);
          this.mRemote.transact(12, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public void onShuffleModeChangedRemoved(boolean param2Boolean) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          boolean bool;
          parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel.writeInt(bool);
          this.mRemote.transact(10, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
      
      public void onVolumeInfoChanged(ParcelableVolumeInfo param2ParcelableVolumeInfo) throws RemoteException {
        Parcel parcel = Parcel.obtain();
        try {
          parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
          if (param2ParcelableVolumeInfo != null) {
            parcel.writeInt(1);
            param2ParcelableVolumeInfo.writeToParcel(parcel, 0);
          } else {
            parcel.writeInt(0);
          } 
          this.mRemote.transact(8, parcel, null, 1);
          return;
        } finally {
          parcel.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements IMediaControllerCallback {
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "android.support.v4.media.session.IMediaControllerCallback";
    }
    
    public void onCaptioningEnabledChanged(boolean param1Boolean) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        boolean bool;
        parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel.writeInt(bool);
        this.mRemote.transact(11, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public void onEvent(String param1String, Bundle param1Bundle) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
        parcel.writeString(param1String);
        if (param1Bundle != null) {
          parcel.writeInt(1);
          param1Bundle.writeToParcel(parcel, 0);
        } else {
          parcel.writeInt(0);
        } 
        this.mRemote.transact(1, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public void onExtrasChanged(Bundle param1Bundle) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
        if (param1Bundle != null) {
          parcel.writeInt(1);
          param1Bundle.writeToParcel(parcel, 0);
        } else {
          parcel.writeInt(0);
        } 
        this.mRemote.transact(7, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public void onMetadataChanged(MediaMetadataCompat param1MediaMetadataCompat) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
        if (param1MediaMetadataCompat != null) {
          parcel.writeInt(1);
          param1MediaMetadataCompat.writeToParcel(parcel, 0);
        } else {
          parcel.writeInt(0);
        } 
        this.mRemote.transact(4, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public void onPlaybackStateChanged(PlaybackStateCompat param1PlaybackStateCompat) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
        if (param1PlaybackStateCompat != null) {
          parcel.writeInt(1);
          param1PlaybackStateCompat.writeToParcel(parcel, 0);
        } else {
          parcel.writeInt(0);
        } 
        this.mRemote.transact(3, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public void onQueueChanged(List<MediaSessionCompat.QueueItem> param1List) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
        parcel.writeTypedList(param1List);
        this.mRemote.transact(5, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public void onQueueTitleChanged(CharSequence param1CharSequence) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
        if (param1CharSequence != null) {
          parcel.writeInt(1);
          TextUtils.writeToParcel(param1CharSequence, parcel, 0);
        } else {
          parcel.writeInt(0);
        } 
        this.mRemote.transact(6, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public void onRepeatModeChanged(int param1Int) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
        parcel.writeInt(param1Int);
        this.mRemote.transact(9, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public void onSessionDestroyed() throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
        this.mRemote.transact(2, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public void onSessionReady() throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
        this.mRemote.transact(13, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public void onShuffleModeChanged(int param1Int) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
        parcel.writeInt(param1Int);
        this.mRemote.transact(12, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public void onShuffleModeChangedRemoved(boolean param1Boolean) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        boolean bool;
        parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel.writeInt(bool);
        this.mRemote.transact(10, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
    
    public void onVolumeInfoChanged(ParcelableVolumeInfo param1ParcelableVolumeInfo) throws RemoteException {
      Parcel parcel = Parcel.obtain();
      try {
        parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
        if (param1ParcelableVolumeInfo != null) {
          parcel.writeInt(1);
          param1ParcelableVolumeInfo.writeToParcel(parcel, 0);
        } else {
          parcel.writeInt(0);
        } 
        this.mRemote.transact(8, parcel, null, 1);
        return;
      } finally {
        parcel.recycle();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\session\IMediaControllerCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */