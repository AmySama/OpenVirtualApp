package android.support.v4.media;

import android.media.AudioAttributes;
import android.os.Build;
import android.util.SparseIntArray;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public class AudioAttributesCompat {
  public static final int CONTENT_TYPE_MOVIE = 3;
  
  public static final int CONTENT_TYPE_MUSIC = 2;
  
  public static final int CONTENT_TYPE_SONIFICATION = 4;
  
  public static final int CONTENT_TYPE_SPEECH = 1;
  
  public static final int CONTENT_TYPE_UNKNOWN = 0;
  
  private static final int FLAG_ALL = 1023;
  
  private static final int FLAG_ALL_PUBLIC = 273;
  
  public static final int FLAG_AUDIBILITY_ENFORCED = 1;
  
  private static final int FLAG_BEACON = 8;
  
  private static final int FLAG_BYPASS_INTERRUPTION_POLICY = 64;
  
  private static final int FLAG_BYPASS_MUTE = 128;
  
  private static final int FLAG_DEEP_BUFFER = 512;
  
  public static final int FLAG_HW_AV_SYNC = 16;
  
  private static final int FLAG_HW_HOTWORD = 32;
  
  private static final int FLAG_LOW_LATENCY = 256;
  
  private static final int FLAG_SCO = 4;
  
  private static final int FLAG_SECURE = 2;
  
  private static final int[] SDK_USAGES;
  
  private static final int SUPPRESSIBLE_CALL = 2;
  
  private static final int SUPPRESSIBLE_NOTIFICATION = 1;
  
  private static final SparseIntArray SUPPRESSIBLE_USAGES;
  
  private static final String TAG = "AudioAttributesCompat";
  
  public static final int USAGE_ALARM = 4;
  
  public static final int USAGE_ASSISTANCE_ACCESSIBILITY = 11;
  
  public static final int USAGE_ASSISTANCE_NAVIGATION_GUIDANCE = 12;
  
  public static final int USAGE_ASSISTANCE_SONIFICATION = 13;
  
  public static final int USAGE_ASSISTANT = 16;
  
  public static final int USAGE_GAME = 14;
  
  public static final int USAGE_MEDIA = 1;
  
  public static final int USAGE_NOTIFICATION = 5;
  
  public static final int USAGE_NOTIFICATION_COMMUNICATION_DELAYED = 9;
  
  public static final int USAGE_NOTIFICATION_COMMUNICATION_INSTANT = 8;
  
  public static final int USAGE_NOTIFICATION_COMMUNICATION_REQUEST = 7;
  
  public static final int USAGE_NOTIFICATION_EVENT = 10;
  
  public static final int USAGE_NOTIFICATION_RINGTONE = 6;
  
  public static final int USAGE_UNKNOWN = 0;
  
  private static final int USAGE_VIRTUAL_SOURCE = 15;
  
  public static final int USAGE_VOICE_COMMUNICATION = 2;
  
  public static final int USAGE_VOICE_COMMUNICATION_SIGNALLING = 3;
  
  private static boolean sForceLegacyBehavior;
  
  private AudioAttributesCompatApi21.Wrapper mAudioAttributesWrapper;
  
  int mContentType = 0;
  
  int mFlags = 0;
  
  Integer mLegacyStream;
  
  int mUsage = 0;
  
  static {
    SparseIntArray sparseIntArray = new SparseIntArray();
    SUPPRESSIBLE_USAGES = sparseIntArray;
    sparseIntArray.put(5, 1);
    SUPPRESSIBLE_USAGES.put(6, 2);
    SUPPRESSIBLE_USAGES.put(7, 2);
    SUPPRESSIBLE_USAGES.put(8, 1);
    SUPPRESSIBLE_USAGES.put(9, 1);
    SUPPRESSIBLE_USAGES.put(10, 1);
    SDK_USAGES = new int[] { 
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
        10, 11, 12, 13, 14, 16 };
  }
  
  private AudioAttributesCompat() {}
  
  public static void setForceLegacyBehavior(boolean paramBoolean) {
    sForceLegacyBehavior = paramBoolean;
  }
  
  static int toVolumeStreamType(boolean paramBoolean, int paramInt1, int paramInt2) {
    StringBuilder stringBuilder;
    boolean bool = true;
    if ((paramInt1 & 0x1) == 1) {
      if (paramBoolean) {
        paramInt1 = bool;
      } else {
        paramInt1 = 7;
      } 
      return paramInt1;
    } 
    bool = false;
    byte b = 0;
    if ((paramInt1 & 0x4) == 4) {
      if (paramBoolean) {
        paramInt1 = b;
      } else {
        paramInt1 = 6;
      } 
      return paramInt1;
    } 
    b = 3;
    paramInt1 = bool;
    switch (paramInt2) {
      default:
        if (!paramBoolean)
          return 3; 
        stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown usage value ");
        stringBuilder.append(paramInt2);
        stringBuilder.append(" in audio attributes");
        throw new IllegalArgumentException(stringBuilder.toString());
      case 13:
        return 1;
      case 11:
        return 10;
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
        if (paramBoolean) {
          paramInt1 = bool;
        } else {
          paramInt1 = 8;
        } 
      case 2:
        return paramInt1;
      case 1:
      case 12:
      case 14:
      case 16:
        return 3;
      case 0:
        break;
    } 
    paramInt1 = b;
    if (paramBoolean)
      paramInt1 = Integer.MIN_VALUE; 
    return paramInt1;
  }
  
  static int toVolumeStreamType(boolean paramBoolean, AudioAttributesCompat paramAudioAttributesCompat) {
    return toVolumeStreamType(paramBoolean, paramAudioAttributesCompat.getFlags(), paramAudioAttributesCompat.getUsage());
  }
  
  private static int usageForStreamType(int paramInt) {
    switch (paramInt) {
      default:
        return 0;
      case 10:
        return 11;
      case 8:
        return 3;
      case 6:
        return 2;
      case 5:
        return 5;
      case 4:
        return 4;
      case 3:
        return 1;
      case 2:
        return 6;
      case 1:
      case 7:
        return 13;
      case 0:
        break;
    } 
    return 2;
  }
  
  static String usageToString(int paramInt) {
    StringBuilder stringBuilder;
    switch (paramInt) {
      default:
        stringBuilder = new StringBuilder();
        stringBuilder.append("unknown usage ");
        stringBuilder.append(paramInt);
        return new String(stringBuilder.toString());
      case 16:
        return new String("USAGE_ASSISTANT");
      case 14:
        return new String("USAGE_GAME");
      case 13:
        return new String("USAGE_ASSISTANCE_SONIFICATION");
      case 12:
        return new String("USAGE_ASSISTANCE_NAVIGATION_GUIDANCE");
      case 11:
        return new String("USAGE_ASSISTANCE_ACCESSIBILITY");
      case 10:
        return new String("USAGE_NOTIFICATION_EVENT");
      case 9:
        return new String("USAGE_NOTIFICATION_COMMUNICATION_DELAYED");
      case 8:
        return new String("USAGE_NOTIFICATION_COMMUNICATION_INSTANT");
      case 7:
        return new String("USAGE_NOTIFICATION_COMMUNICATION_REQUEST");
      case 6:
        return new String("USAGE_NOTIFICATION_RINGTONE");
      case 5:
        return new String("USAGE_NOTIFICATION");
      case 4:
        return new String("USAGE_ALARM");
      case 3:
        return new String("USAGE_VOICE_COMMUNICATION_SIGNALLING");
      case 2:
        return new String("USAGE_VOICE_COMMUNICATION");
      case 1:
        return new String("USAGE_MEDIA");
      case 0:
        break;
    } 
    return new String("USAGE_UNKNOWN");
  }
  
  public static AudioAttributesCompat wrap(Object paramObject) {
    if (Build.VERSION.SDK_INT >= 21 && !sForceLegacyBehavior) {
      AudioAttributesCompat audioAttributesCompat = new AudioAttributesCompat();
      audioAttributesCompat.mAudioAttributesWrapper = AudioAttributesCompatApi21.Wrapper.wrap((AudioAttributes)paramObject);
      return audioAttributesCompat;
    } 
    return null;
  }
  
  public boolean equals(Object paramObject) {
    null = true;
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    paramObject = paramObject;
    if (Build.VERSION.SDK_INT >= 21 && !sForceLegacyBehavior) {
      AudioAttributesCompatApi21.Wrapper wrapper = this.mAudioAttributesWrapper;
      if (wrapper != null)
        return wrapper.unwrap().equals(paramObject.unwrap()); 
    } 
    if (this.mContentType == paramObject.getContentType() && this.mFlags == paramObject.getFlags() && this.mUsage == paramObject.getUsage()) {
      Integer integer = this.mLegacyStream;
      if ((integer != null) ? integer.equals(((AudioAttributesCompat)paramObject).mLegacyStream) : (((AudioAttributesCompat)paramObject).mLegacyStream == null))
        return null; 
    } 
    return false;
  }
  
  public int getContentType() {
    if (Build.VERSION.SDK_INT >= 21 && !sForceLegacyBehavior) {
      AudioAttributesCompatApi21.Wrapper wrapper = this.mAudioAttributesWrapper;
      if (wrapper != null)
        return wrapper.unwrap().getContentType(); 
    } 
    return this.mContentType;
  }
  
  public int getFlags() {
    int k;
    if (Build.VERSION.SDK_INT >= 21 && !sForceLegacyBehavior) {
      AudioAttributesCompatApi21.Wrapper wrapper = this.mAudioAttributesWrapper;
      if (wrapper != null)
        return wrapper.unwrap().getFlags(); 
    } 
    int i = this.mFlags;
    int j = getLegacyStreamType();
    if (j == 6) {
      k = i | 0x4;
    } else {
      k = i;
      if (j == 7)
        k = i | 0x1; 
    } 
    return k & 0x111;
  }
  
  public int getLegacyStreamType() {
    Integer integer = this.mLegacyStream;
    return (integer != null) ? integer.intValue() : ((Build.VERSION.SDK_INT >= 21 && !sForceLegacyBehavior) ? AudioAttributesCompatApi21.toLegacyStreamType(this.mAudioAttributesWrapper) : toVolumeStreamType(false, this.mFlags, this.mUsage));
  }
  
  public int getUsage() {
    if (Build.VERSION.SDK_INT >= 21 && !sForceLegacyBehavior) {
      AudioAttributesCompatApi21.Wrapper wrapper = this.mAudioAttributesWrapper;
      if (wrapper != null)
        return wrapper.unwrap().getUsage(); 
    } 
    return this.mUsage;
  }
  
  public int getVolumeControlStream() {
    return (Build.VERSION.SDK_INT >= 26 && !sForceLegacyBehavior && unwrap() != null) ? ((AudioAttributes)unwrap()).getVolumeControlStream() : toVolumeStreamType(true, this);
  }
  
  public int hashCode() {
    if (Build.VERSION.SDK_INT >= 21 && !sForceLegacyBehavior) {
      AudioAttributesCompatApi21.Wrapper wrapper = this.mAudioAttributesWrapper;
      if (wrapper != null)
        return wrapper.unwrap().hashCode(); 
    } 
    return Arrays.hashCode(new Object[] { Integer.valueOf(this.mContentType), Integer.valueOf(this.mFlags), Integer.valueOf(this.mUsage), this.mLegacyStream });
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("AudioAttributesCompat:");
    if (unwrap() != null) {
      stringBuilder.append(" audioattributes=");
      stringBuilder.append(unwrap());
    } else {
      if (this.mLegacyStream != null) {
        stringBuilder.append(" stream=");
        stringBuilder.append(this.mLegacyStream);
        stringBuilder.append(" derived");
      } 
      stringBuilder.append(" usage=");
      stringBuilder.append(usageToString());
      stringBuilder.append(" content=");
      stringBuilder.append(this.mContentType);
      stringBuilder.append(" flags=0x");
      stringBuilder.append(Integer.toHexString(this.mFlags).toUpperCase());
    } 
    return stringBuilder.toString();
  }
  
  public Object unwrap() {
    AudioAttributesCompatApi21.Wrapper wrapper = this.mAudioAttributesWrapper;
    return (wrapper != null) ? wrapper.unwrap() : null;
  }
  
  String usageToString() {
    return usageToString(this.mUsage);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface AttributeContentType {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface AttributeUsage {}
  
  private static abstract class AudioManagerHidden {
    public static final int STREAM_ACCESSIBILITY = 10;
    
    public static final int STREAM_BLUETOOTH_SCO = 6;
    
    public static final int STREAM_SYSTEM_ENFORCED = 7;
    
    public static final int STREAM_TTS = 9;
  }
  
  public static class Builder {
    private Object mAAObject;
    
    private int mContentType = 0;
    
    private int mFlags = 0;
    
    private Integer mLegacyStream;
    
    private int mUsage = 0;
    
    public Builder() {}
    
    public Builder(AudioAttributesCompat param1AudioAttributesCompat) {
      this.mUsage = param1AudioAttributesCompat.mUsage;
      this.mContentType = param1AudioAttributesCompat.mContentType;
      this.mFlags = param1AudioAttributesCompat.mFlags;
      this.mLegacyStream = param1AudioAttributesCompat.mLegacyStream;
      this.mAAObject = param1AudioAttributesCompat.unwrap();
    }
    
    public AudioAttributesCompat build() {
      if (!AudioAttributesCompat.sForceLegacyBehavior && Build.VERSION.SDK_INT >= 21) {
        Object object = this.mAAObject;
        if (object != null)
          return AudioAttributesCompat.wrap(object); 
        object = (new AudioAttributes.Builder()).setContentType(this.mContentType).setFlags(this.mFlags).setUsage(this.mUsage);
        Integer integer = this.mLegacyStream;
        if (integer != null)
          object.setLegacyStreamType(integer.intValue()); 
        return AudioAttributesCompat.wrap(object.build());
      } 
      AudioAttributesCompat audioAttributesCompat = new AudioAttributesCompat();
      audioAttributesCompat.mContentType = this.mContentType;
      audioAttributesCompat.mFlags = this.mFlags;
      audioAttributesCompat.mUsage = this.mUsage;
      audioAttributesCompat.mLegacyStream = this.mLegacyStream;
      AudioAttributesCompat.access$202(audioAttributesCompat, null);
      return audioAttributesCompat;
    }
    
    public Builder setContentType(int param1Int) {
      if (param1Int != 0 && param1Int != 1 && param1Int != 2 && param1Int != 3 && param1Int != 4) {
        this.mUsage = 0;
      } else {
        this.mContentType = param1Int;
      } 
      return this;
    }
    
    public Builder setFlags(int param1Int) {
      this.mFlags = param1Int & 0x3FF | this.mFlags;
      return this;
    }
    
    public Builder setLegacyStreamType(int param1Int) {
      if (param1Int != 10) {
        this.mLegacyStream = Integer.valueOf(param1Int);
        this.mUsage = AudioAttributesCompat.usageForStreamType(param1Int);
        return this;
      } 
      throw new IllegalArgumentException("STREAM_ACCESSIBILITY is not a legacy stream type that was used for audio playback");
    }
    
    public Builder setUsage(int param1Int) {
      switch (param1Int) {
        default:
          this.mUsage = 0;
          return this;
        case 16:
          if (!AudioAttributesCompat.sForceLegacyBehavior && Build.VERSION.SDK_INT > 25) {
            this.mUsage = param1Int;
          } else {
            this.mUsage = 12;
          } 
          return this;
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
          break;
      } 
      this.mUsage = param1Int;
      return this;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\AudioAttributesCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */