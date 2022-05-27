package android.support.v4.media.session;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class PlaybackStateCompat implements Parcelable {
  public static final long ACTION_FAST_FORWARD = 64L;
  
  public static final long ACTION_PAUSE = 2L;
  
  public static final long ACTION_PLAY = 4L;
  
  public static final long ACTION_PLAY_FROM_MEDIA_ID = 1024L;
  
  public static final long ACTION_PLAY_FROM_SEARCH = 2048L;
  
  public static final long ACTION_PLAY_FROM_URI = 8192L;
  
  public static final long ACTION_PLAY_PAUSE = 512L;
  
  public static final long ACTION_PREPARE = 16384L;
  
  public static final long ACTION_PREPARE_FROM_MEDIA_ID = 32768L;
  
  public static final long ACTION_PREPARE_FROM_SEARCH = 65536L;
  
  public static final long ACTION_PREPARE_FROM_URI = 131072L;
  
  public static final long ACTION_REWIND = 8L;
  
  public static final long ACTION_SEEK_TO = 256L;
  
  public static final long ACTION_SET_CAPTIONING_ENABLED = 1048576L;
  
  public static final long ACTION_SET_RATING = 128L;
  
  public static final long ACTION_SET_REPEAT_MODE = 262144L;
  
  public static final long ACTION_SET_SHUFFLE_MODE = 2097152L;
  
  @Deprecated
  public static final long ACTION_SET_SHUFFLE_MODE_ENABLED = 524288L;
  
  public static final long ACTION_SKIP_TO_NEXT = 32L;
  
  public static final long ACTION_SKIP_TO_PREVIOUS = 16L;
  
  public static final long ACTION_SKIP_TO_QUEUE_ITEM = 4096L;
  
  public static final long ACTION_STOP = 1L;
  
  public static final Parcelable.Creator<PlaybackStateCompat> CREATOR = new Parcelable.Creator<PlaybackStateCompat>() {
      public PlaybackStateCompat createFromParcel(Parcel param1Parcel) {
        return new PlaybackStateCompat(param1Parcel);
      }
      
      public PlaybackStateCompat[] newArray(int param1Int) {
        return new PlaybackStateCompat[param1Int];
      }
    };
  
  public static final int ERROR_CODE_ACTION_ABORTED = 10;
  
  public static final int ERROR_CODE_APP_ERROR = 1;
  
  public static final int ERROR_CODE_AUTHENTICATION_EXPIRED = 3;
  
  public static final int ERROR_CODE_CONCURRENT_STREAM_LIMIT = 5;
  
  public static final int ERROR_CODE_CONTENT_ALREADY_PLAYING = 8;
  
  public static final int ERROR_CODE_END_OF_QUEUE = 11;
  
  public static final int ERROR_CODE_NOT_AVAILABLE_IN_REGION = 7;
  
  public static final int ERROR_CODE_NOT_SUPPORTED = 2;
  
  public static final int ERROR_CODE_PARENTAL_CONTROL_RESTRICTED = 6;
  
  public static final int ERROR_CODE_PREMIUM_ACCOUNT_REQUIRED = 4;
  
  public static final int ERROR_CODE_SKIP_LIMIT_REACHED = 9;
  
  public static final int ERROR_CODE_UNKNOWN_ERROR = 0;
  
  private static final int KEYCODE_MEDIA_PAUSE = 127;
  
  private static final int KEYCODE_MEDIA_PLAY = 126;
  
  public static final long PLAYBACK_POSITION_UNKNOWN = -1L;
  
  public static final int REPEAT_MODE_ALL = 2;
  
  public static final int REPEAT_MODE_GROUP = 3;
  
  public static final int REPEAT_MODE_INVALID = -1;
  
  public static final int REPEAT_MODE_NONE = 0;
  
  public static final int REPEAT_MODE_ONE = 1;
  
  public static final int SHUFFLE_MODE_ALL = 1;
  
  public static final int SHUFFLE_MODE_GROUP = 2;
  
  public static final int SHUFFLE_MODE_INVALID = -1;
  
  public static final int SHUFFLE_MODE_NONE = 0;
  
  public static final int STATE_BUFFERING = 6;
  
  public static final int STATE_CONNECTING = 8;
  
  public static final int STATE_ERROR = 7;
  
  public static final int STATE_FAST_FORWARDING = 4;
  
  public static final int STATE_NONE = 0;
  
  public static final int STATE_PAUSED = 2;
  
  public static final int STATE_PLAYING = 3;
  
  public static final int STATE_REWINDING = 5;
  
  public static final int STATE_SKIPPING_TO_NEXT = 10;
  
  public static final int STATE_SKIPPING_TO_PREVIOUS = 9;
  
  public static final int STATE_SKIPPING_TO_QUEUE_ITEM = 11;
  
  public static final int STATE_STOPPED = 1;
  
  final long mActions;
  
  final long mActiveItemId;
  
  final long mBufferedPosition;
  
  List<CustomAction> mCustomActions;
  
  final int mErrorCode;
  
  final CharSequence mErrorMessage;
  
  final Bundle mExtras;
  
  final long mPosition;
  
  final float mSpeed;
  
  final int mState;
  
  private Object mStateObj;
  
  final long mUpdateTime;
  
  PlaybackStateCompat(int paramInt1, long paramLong1, long paramLong2, float paramFloat, long paramLong3, int paramInt2, CharSequence paramCharSequence, long paramLong4, List<CustomAction> paramList, long paramLong5, Bundle paramBundle) {
    this.mState = paramInt1;
    this.mPosition = paramLong1;
    this.mBufferedPosition = paramLong2;
    this.mSpeed = paramFloat;
    this.mActions = paramLong3;
    this.mErrorCode = paramInt2;
    this.mErrorMessage = paramCharSequence;
    this.mUpdateTime = paramLong4;
    this.mCustomActions = new ArrayList<CustomAction>(paramList);
    this.mActiveItemId = paramLong5;
    this.mExtras = paramBundle;
  }
  
  PlaybackStateCompat(Parcel paramParcel) {
    this.mState = paramParcel.readInt();
    this.mPosition = paramParcel.readLong();
    this.mSpeed = paramParcel.readFloat();
    this.mUpdateTime = paramParcel.readLong();
    this.mBufferedPosition = paramParcel.readLong();
    this.mActions = paramParcel.readLong();
    this.mErrorMessage = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel);
    this.mCustomActions = paramParcel.createTypedArrayList(CustomAction.CREATOR);
    this.mActiveItemId = paramParcel.readLong();
    this.mExtras = paramParcel.readBundle();
    this.mErrorCode = paramParcel.readInt();
  }
  
  public static PlaybackStateCompat fromPlaybackState(Object paramObject) {
    PlaybackStateCompat playbackStateCompat;
    List<Object> list1 = null;
    Bundle bundle = null;
    List<Object> list2 = list1;
    if (paramObject != null) {
      list2 = list1;
      if (Build.VERSION.SDK_INT >= 21) {
        list1 = PlaybackStateCompatApi21.getCustomActions(paramObject);
        if (list1 != null) {
          list2 = new ArrayList(list1.size());
          Iterator iterator = list1.iterator();
          while (iterator.hasNext())
            list2.add(CustomAction.fromCustomAction(iterator.next())); 
        } else {
          list2 = null;
        } 
        if (Build.VERSION.SDK_INT >= 22)
          bundle = PlaybackStateCompatApi22.getExtras(paramObject); 
        playbackStateCompat = new PlaybackStateCompat(PlaybackStateCompatApi21.getState(paramObject), PlaybackStateCompatApi21.getPosition(paramObject), PlaybackStateCompatApi21.getBufferedPosition(paramObject), PlaybackStateCompatApi21.getPlaybackSpeed(paramObject), PlaybackStateCompatApi21.getActions(paramObject), 0, PlaybackStateCompatApi21.getErrorMessage(paramObject), PlaybackStateCompatApi21.getLastPositionUpdateTime(paramObject), (List)list2, PlaybackStateCompatApi21.getActiveQueueItemId(paramObject), bundle);
        playbackStateCompat.mStateObj = paramObject;
      } 
    } 
    return playbackStateCompat;
  }
  
  public static int toKeyCode(long paramLong) {
    return (paramLong == 4L) ? 126 : ((paramLong == 2L) ? 127 : ((paramLong == 32L) ? 87 : ((paramLong == 16L) ? 88 : ((paramLong == 1L) ? 86 : ((paramLong == 64L) ? 90 : ((paramLong == 8L) ? 89 : ((paramLong == 512L) ? 85 : 0)))))));
  }
  
  public int describeContents() {
    return 0;
  }
  
  public long getActions() {
    return this.mActions;
  }
  
  public long getActiveQueueItemId() {
    return this.mActiveItemId;
  }
  
  public long getBufferedPosition() {
    return this.mBufferedPosition;
  }
  
  public List<CustomAction> getCustomActions() {
    return this.mCustomActions;
  }
  
  public int getErrorCode() {
    return this.mErrorCode;
  }
  
  public CharSequence getErrorMessage() {
    return this.mErrorMessage;
  }
  
  public Bundle getExtras() {
    return this.mExtras;
  }
  
  public long getLastPositionUpdateTime() {
    return this.mUpdateTime;
  }
  
  public float getPlaybackSpeed() {
    return this.mSpeed;
  }
  
  public Object getPlaybackState() {
    if (this.mStateObj == null && Build.VERSION.SDK_INT >= 21) {
      ArrayList<Object> arrayList = null;
      if (this.mCustomActions != null) {
        ArrayList<Object> arrayList1 = new ArrayList(this.mCustomActions.size());
        Iterator<CustomAction> iterator = this.mCustomActions.iterator();
        while (true) {
          arrayList = arrayList1;
          if (iterator.hasNext()) {
            arrayList1.add(((CustomAction)iterator.next()).getCustomAction());
            continue;
          } 
          break;
        } 
      } 
      if (Build.VERSION.SDK_INT >= 22) {
        this.mStateObj = PlaybackStateCompatApi22.newInstance(this.mState, this.mPosition, this.mBufferedPosition, this.mSpeed, this.mActions, this.mErrorMessage, this.mUpdateTime, arrayList, this.mActiveItemId, this.mExtras);
      } else {
        this.mStateObj = PlaybackStateCompatApi21.newInstance(this.mState, this.mPosition, this.mBufferedPosition, this.mSpeed, this.mActions, this.mErrorMessage, this.mUpdateTime, arrayList, this.mActiveItemId);
      } 
    } 
    return this.mStateObj;
  }
  
  public long getPosition() {
    return this.mPosition;
  }
  
  public int getState() {
    return this.mState;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("PlaybackState {");
    stringBuilder.append("state=");
    stringBuilder.append(this.mState);
    stringBuilder.append(", position=");
    stringBuilder.append(this.mPosition);
    stringBuilder.append(", buffered position=");
    stringBuilder.append(this.mBufferedPosition);
    stringBuilder.append(", speed=");
    stringBuilder.append(this.mSpeed);
    stringBuilder.append(", updated=");
    stringBuilder.append(this.mUpdateTime);
    stringBuilder.append(", actions=");
    stringBuilder.append(this.mActions);
    stringBuilder.append(", error code=");
    stringBuilder.append(this.mErrorCode);
    stringBuilder.append(", error message=");
    stringBuilder.append(this.mErrorMessage);
    stringBuilder.append(", custom actions=");
    stringBuilder.append(this.mCustomActions);
    stringBuilder.append(", active item id=");
    stringBuilder.append(this.mActiveItemId);
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.mState);
    paramParcel.writeLong(this.mPosition);
    paramParcel.writeFloat(this.mSpeed);
    paramParcel.writeLong(this.mUpdateTime);
    paramParcel.writeLong(this.mBufferedPosition);
    paramParcel.writeLong(this.mActions);
    TextUtils.writeToParcel(this.mErrorMessage, paramParcel, paramInt);
    paramParcel.writeTypedList(this.mCustomActions);
    paramParcel.writeLong(this.mActiveItemId);
    paramParcel.writeBundle(this.mExtras);
    paramParcel.writeInt(this.mErrorCode);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Actions {}
  
  public static final class Builder {
    private long mActions;
    
    private long mActiveItemId = -1L;
    
    private long mBufferedPosition;
    
    private final List<PlaybackStateCompat.CustomAction> mCustomActions = new ArrayList<PlaybackStateCompat.CustomAction>();
    
    private int mErrorCode;
    
    private CharSequence mErrorMessage;
    
    private Bundle mExtras;
    
    private long mPosition;
    
    private float mRate;
    
    private int mState;
    
    private long mUpdateTime;
    
    public Builder() {}
    
    public Builder(PlaybackStateCompat param1PlaybackStateCompat) {
      this.mState = param1PlaybackStateCompat.mState;
      this.mPosition = param1PlaybackStateCompat.mPosition;
      this.mRate = param1PlaybackStateCompat.mSpeed;
      this.mUpdateTime = param1PlaybackStateCompat.mUpdateTime;
      this.mBufferedPosition = param1PlaybackStateCompat.mBufferedPosition;
      this.mActions = param1PlaybackStateCompat.mActions;
      this.mErrorCode = param1PlaybackStateCompat.mErrorCode;
      this.mErrorMessage = param1PlaybackStateCompat.mErrorMessage;
      if (param1PlaybackStateCompat.mCustomActions != null)
        this.mCustomActions.addAll(param1PlaybackStateCompat.mCustomActions); 
      this.mActiveItemId = param1PlaybackStateCompat.mActiveItemId;
      this.mExtras = param1PlaybackStateCompat.mExtras;
    }
    
    public Builder addCustomAction(PlaybackStateCompat.CustomAction param1CustomAction) {
      if (param1CustomAction != null) {
        this.mCustomActions.add(param1CustomAction);
        return this;
      } 
      throw new IllegalArgumentException("You may not add a null CustomAction to PlaybackStateCompat.");
    }
    
    public Builder addCustomAction(String param1String1, String param1String2, int param1Int) {
      return addCustomAction(new PlaybackStateCompat.CustomAction(param1String1, param1String2, param1Int, null));
    }
    
    public PlaybackStateCompat build() {
      return new PlaybackStateCompat(this.mState, this.mPosition, this.mBufferedPosition, this.mRate, this.mActions, this.mErrorCode, this.mErrorMessage, this.mUpdateTime, this.mCustomActions, this.mActiveItemId, this.mExtras);
    }
    
    public Builder setActions(long param1Long) {
      this.mActions = param1Long;
      return this;
    }
    
    public Builder setActiveQueueItemId(long param1Long) {
      this.mActiveItemId = param1Long;
      return this;
    }
    
    public Builder setBufferedPosition(long param1Long) {
      this.mBufferedPosition = param1Long;
      return this;
    }
    
    public Builder setErrorMessage(int param1Int, CharSequence param1CharSequence) {
      this.mErrorCode = param1Int;
      this.mErrorMessage = param1CharSequence;
      return this;
    }
    
    public Builder setErrorMessage(CharSequence param1CharSequence) {
      this.mErrorMessage = param1CharSequence;
      return this;
    }
    
    public Builder setExtras(Bundle param1Bundle) {
      this.mExtras = param1Bundle;
      return this;
    }
    
    public Builder setState(int param1Int, long param1Long, float param1Float) {
      return setState(param1Int, param1Long, param1Float, SystemClock.elapsedRealtime());
    }
    
    public Builder setState(int param1Int, long param1Long1, float param1Float, long param1Long2) {
      this.mState = param1Int;
      this.mPosition = param1Long1;
      this.mUpdateTime = param1Long2;
      this.mRate = param1Float;
      return this;
    }
  }
  
  public static final class CustomAction implements Parcelable {
    public static final Parcelable.Creator<CustomAction> CREATOR = new Parcelable.Creator<CustomAction>() {
        public PlaybackStateCompat.CustomAction createFromParcel(Parcel param2Parcel) {
          return new PlaybackStateCompat.CustomAction(param2Parcel);
        }
        
        public PlaybackStateCompat.CustomAction[] newArray(int param2Int) {
          return new PlaybackStateCompat.CustomAction[param2Int];
        }
      };
    
    private final String mAction;
    
    private Object mCustomActionObj;
    
    private final Bundle mExtras;
    
    private final int mIcon;
    
    private final CharSequence mName;
    
    CustomAction(Parcel param1Parcel) {
      this.mAction = param1Parcel.readString();
      this.mName = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(param1Parcel);
      this.mIcon = param1Parcel.readInt();
      this.mExtras = param1Parcel.readBundle();
    }
    
    CustomAction(String param1String, CharSequence param1CharSequence, int param1Int, Bundle param1Bundle) {
      this.mAction = param1String;
      this.mName = param1CharSequence;
      this.mIcon = param1Int;
      this.mExtras = param1Bundle;
    }
    
    public static CustomAction fromCustomAction(Object param1Object) {
      if (param1Object == null || Build.VERSION.SDK_INT < 21)
        return null; 
      CustomAction customAction = new CustomAction(PlaybackStateCompatApi21.CustomAction.getAction(param1Object), PlaybackStateCompatApi21.CustomAction.getName(param1Object), PlaybackStateCompatApi21.CustomAction.getIcon(param1Object), PlaybackStateCompatApi21.CustomAction.getExtras(param1Object));
      customAction.mCustomActionObj = param1Object;
      return customAction;
    }
    
    public int describeContents() {
      return 0;
    }
    
    public String getAction() {
      return this.mAction;
    }
    
    public Object getCustomAction() {
      if (this.mCustomActionObj != null || Build.VERSION.SDK_INT < 21)
        return this.mCustomActionObj; 
      Object object = PlaybackStateCompatApi21.CustomAction.newInstance(this.mAction, this.mName, this.mIcon, this.mExtras);
      this.mCustomActionObj = object;
      return object;
    }
    
    public Bundle getExtras() {
      return this.mExtras;
    }
    
    public int getIcon() {
      return this.mIcon;
    }
    
    public CharSequence getName() {
      return this.mName;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Action:mName='");
      stringBuilder.append(this.mName);
      stringBuilder.append(", mIcon=");
      stringBuilder.append(this.mIcon);
      stringBuilder.append(", mExtras=");
      stringBuilder.append(this.mExtras);
      return stringBuilder.toString();
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeString(this.mAction);
      TextUtils.writeToParcel(this.mName, param1Parcel, param1Int);
      param1Parcel.writeInt(this.mIcon);
      param1Parcel.writeBundle(this.mExtras);
    }
    
    public static final class Builder {
      private final String mAction;
      
      private Bundle mExtras;
      
      private final int mIcon;
      
      private final CharSequence mName;
      
      public Builder(String param2String, CharSequence param2CharSequence, int param2Int) {
        if (!TextUtils.isEmpty(param2String)) {
          if (!TextUtils.isEmpty(param2CharSequence)) {
            if (param2Int != 0) {
              this.mAction = param2String;
              this.mName = param2CharSequence;
              this.mIcon = param2Int;
              return;
            } 
            throw new IllegalArgumentException("You must specify an icon resource id to build a CustomAction.");
          } 
          throw new IllegalArgumentException("You must specify a name to build a CustomAction.");
        } 
        throw new IllegalArgumentException("You must specify an action to build a CustomAction.");
      }
      
      public PlaybackStateCompat.CustomAction build() {
        return new PlaybackStateCompat.CustomAction(this.mAction, this.mName, this.mIcon, this.mExtras);
      }
      
      public Builder setExtras(Bundle param2Bundle) {
        this.mExtras = param2Bundle;
        return this;
      }
    }
  }
  
  static final class null implements Parcelable.Creator<CustomAction> {
    public PlaybackStateCompat.CustomAction createFromParcel(Parcel param1Parcel) {
      return new PlaybackStateCompat.CustomAction(param1Parcel);
    }
    
    public PlaybackStateCompat.CustomAction[] newArray(int param1Int) {
      return new PlaybackStateCompat.CustomAction[param1Int];
    }
  }
  
  public static final class Builder {
    private final String mAction;
    
    private Bundle mExtras;
    
    private final int mIcon;
    
    private final CharSequence mName;
    
    public Builder(String param1String, CharSequence param1CharSequence, int param1Int) {
      if (!TextUtils.isEmpty(param1String)) {
        if (!TextUtils.isEmpty(param1CharSequence)) {
          if (param1Int != 0) {
            this.mAction = param1String;
            this.mName = param1CharSequence;
            this.mIcon = param1Int;
            return;
          } 
          throw new IllegalArgumentException("You must specify an icon resource id to build a CustomAction.");
        } 
        throw new IllegalArgumentException("You must specify a name to build a CustomAction.");
      } 
      throw new IllegalArgumentException("You must specify an action to build a CustomAction.");
    }
    
    public PlaybackStateCompat.CustomAction build() {
      return new PlaybackStateCompat.CustomAction(this.mAction, this.mName, this.mIcon, this.mExtras);
    }
    
    public Builder setExtras(Bundle param1Bundle) {
      this.mExtras = param1Bundle;
      return this;
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ErrorCode {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface MediaKeyAction {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface RepeatMode {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ShuffleMode {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface State {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\session\PlaybackStateCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */