package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public final class MediaDescriptionCompat implements Parcelable {
  public static final long BT_FOLDER_TYPE_ALBUMS = 2L;
  
  public static final long BT_FOLDER_TYPE_ARTISTS = 3L;
  
  public static final long BT_FOLDER_TYPE_GENRES = 4L;
  
  public static final long BT_FOLDER_TYPE_MIXED = 0L;
  
  public static final long BT_FOLDER_TYPE_PLAYLISTS = 5L;
  
  public static final long BT_FOLDER_TYPE_TITLES = 1L;
  
  public static final long BT_FOLDER_TYPE_YEARS = 6L;
  
  public static final Parcelable.Creator<MediaDescriptionCompat> CREATOR = new Parcelable.Creator<MediaDescriptionCompat>() {
      public MediaDescriptionCompat createFromParcel(Parcel param1Parcel) {
        return (Build.VERSION.SDK_INT < 21) ? new MediaDescriptionCompat(param1Parcel) : MediaDescriptionCompat.fromMediaDescription(MediaDescriptionCompatApi21.fromParcel(param1Parcel));
      }
      
      public MediaDescriptionCompat[] newArray(int param1Int) {
        return new MediaDescriptionCompat[param1Int];
      }
    };
  
  public static final String DESCRIPTION_KEY_MEDIA_URI = "android.support.v4.media.description.MEDIA_URI";
  
  public static final String DESCRIPTION_KEY_NULL_BUNDLE_FLAG = "android.support.v4.media.description.NULL_BUNDLE_FLAG";
  
  public static final String EXTRA_BT_FOLDER_TYPE = "android.media.extra.BT_FOLDER_TYPE";
  
  public static final String EXTRA_DOWNLOAD_STATUS = "android.media.extra.DOWNLOAD_STATUS";
  
  public static final long STATUS_DOWNLOADED = 2L;
  
  public static final long STATUS_DOWNLOADING = 1L;
  
  public static final long STATUS_NOT_DOWNLOADED = 0L;
  
  private final CharSequence mDescription;
  
  private Object mDescriptionObj;
  
  private final Bundle mExtras;
  
  private final Bitmap mIcon;
  
  private final Uri mIconUri;
  
  private final String mMediaId;
  
  private final Uri mMediaUri;
  
  private final CharSequence mSubtitle;
  
  private final CharSequence mTitle;
  
  MediaDescriptionCompat(Parcel paramParcel) {
    this.mMediaId = paramParcel.readString();
    this.mTitle = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel);
    this.mSubtitle = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel);
    this.mDescription = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(paramParcel);
    this.mIcon = (Bitmap)paramParcel.readParcelable(null);
    this.mIconUri = (Uri)paramParcel.readParcelable(null);
    this.mExtras = paramParcel.readBundle();
    this.mMediaUri = (Uri)paramParcel.readParcelable(null);
  }
  
  MediaDescriptionCompat(String paramString, CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3, Bitmap paramBitmap, Uri paramUri1, Bundle paramBundle, Uri paramUri2) {
    this.mMediaId = paramString;
    this.mTitle = paramCharSequence1;
    this.mSubtitle = paramCharSequence2;
    this.mDescription = paramCharSequence3;
    this.mIcon = paramBitmap;
    this.mIconUri = paramUri1;
    this.mExtras = paramBundle;
    this.mMediaUri = paramUri2;
  }
  
  public static MediaDescriptionCompat fromMediaDescription(Object paramObject) {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aconst_null
    //   3: astore_2
    //   4: aload_1
    //   5: astore_3
    //   6: aload_0
    //   7: ifnull -> 201
    //   10: aload_1
    //   11: astore_3
    //   12: getstatic android/os/Build$VERSION.SDK_INT : I
    //   15: bipush #21
    //   17: if_icmplt -> 201
    //   20: new android/support/v4/media/MediaDescriptionCompat$Builder
    //   23: dup
    //   24: invokespecial <init> : ()V
    //   27: astore #4
    //   29: aload #4
    //   31: aload_0
    //   32: invokestatic getMediaId : (Ljava/lang/Object;)Ljava/lang/String;
    //   35: invokevirtual setMediaId : (Ljava/lang/String;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   38: pop
    //   39: aload #4
    //   41: aload_0
    //   42: invokestatic getTitle : (Ljava/lang/Object;)Ljava/lang/CharSequence;
    //   45: invokevirtual setTitle : (Ljava/lang/CharSequence;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   48: pop
    //   49: aload #4
    //   51: aload_0
    //   52: invokestatic getSubtitle : (Ljava/lang/Object;)Ljava/lang/CharSequence;
    //   55: invokevirtual setSubtitle : (Ljava/lang/CharSequence;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   58: pop
    //   59: aload #4
    //   61: aload_0
    //   62: invokestatic getDescription : (Ljava/lang/Object;)Ljava/lang/CharSequence;
    //   65: invokevirtual setDescription : (Ljava/lang/CharSequence;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   68: pop
    //   69: aload #4
    //   71: aload_0
    //   72: invokestatic getIconBitmap : (Ljava/lang/Object;)Landroid/graphics/Bitmap;
    //   75: invokevirtual setIconBitmap : (Landroid/graphics/Bitmap;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   78: pop
    //   79: aload #4
    //   81: aload_0
    //   82: invokestatic getIconUri : (Ljava/lang/Object;)Landroid/net/Uri;
    //   85: invokevirtual setIconUri : (Landroid/net/Uri;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   88: pop
    //   89: aload_0
    //   90: invokestatic getExtras : (Ljava/lang/Object;)Landroid/os/Bundle;
    //   93: astore_1
    //   94: aload_1
    //   95: ifnonnull -> 103
    //   98: aconst_null
    //   99: astore_3
    //   100: goto -> 113
    //   103: aload_1
    //   104: ldc 'android.support.v4.media.description.MEDIA_URI'
    //   106: invokevirtual getParcelable : (Ljava/lang/String;)Landroid/os/Parcelable;
    //   109: checkcast android/net/Uri
    //   112: astore_3
    //   113: aload_3
    //   114: ifnull -> 149
    //   117: aload_1
    //   118: ldc 'android.support.v4.media.description.NULL_BUNDLE_FLAG'
    //   120: invokevirtual containsKey : (Ljava/lang/String;)Z
    //   123: ifeq -> 137
    //   126: aload_1
    //   127: invokevirtual size : ()I
    //   130: iconst_2
    //   131: if_icmpne -> 137
    //   134: goto -> 151
    //   137: aload_1
    //   138: ldc 'android.support.v4.media.description.MEDIA_URI'
    //   140: invokevirtual remove : (Ljava/lang/String;)V
    //   143: aload_1
    //   144: ldc 'android.support.v4.media.description.NULL_BUNDLE_FLAG'
    //   146: invokevirtual remove : (Ljava/lang/String;)V
    //   149: aload_1
    //   150: astore_2
    //   151: aload #4
    //   153: aload_2
    //   154: invokevirtual setExtras : (Landroid/os/Bundle;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   157: pop
    //   158: aload_3
    //   159: ifnull -> 172
    //   162: aload #4
    //   164: aload_3
    //   165: invokevirtual setMediaUri : (Landroid/net/Uri;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   168: pop
    //   169: goto -> 190
    //   172: getstatic android/os/Build$VERSION.SDK_INT : I
    //   175: bipush #23
    //   177: if_icmplt -> 190
    //   180: aload #4
    //   182: aload_0
    //   183: invokestatic getMediaUri : (Ljava/lang/Object;)Landroid/net/Uri;
    //   186: invokevirtual setMediaUri : (Landroid/net/Uri;)Landroid/support/v4/media/MediaDescriptionCompat$Builder;
    //   189: pop
    //   190: aload #4
    //   192: invokevirtual build : ()Landroid/support/v4/media/MediaDescriptionCompat;
    //   195: astore_3
    //   196: aload_3
    //   197: aload_0
    //   198: putfield mDescriptionObj : Ljava/lang/Object;
    //   201: aload_3
    //   202: areturn
  }
  
  public int describeContents() {
    return 0;
  }
  
  public CharSequence getDescription() {
    return this.mDescription;
  }
  
  public Bundle getExtras() {
    return this.mExtras;
  }
  
  public Bitmap getIconBitmap() {
    return this.mIcon;
  }
  
  public Uri getIconUri() {
    return this.mIconUri;
  }
  
  public Object getMediaDescription() {
    if (this.mDescriptionObj != null || Build.VERSION.SDK_INT < 21)
      return this.mDescriptionObj; 
    Object object1 = MediaDescriptionCompatApi21.Builder.newInstance();
    MediaDescriptionCompatApi21.Builder.setMediaId(object1, this.mMediaId);
    MediaDescriptionCompatApi21.Builder.setTitle(object1, this.mTitle);
    MediaDescriptionCompatApi21.Builder.setSubtitle(object1, this.mSubtitle);
    MediaDescriptionCompatApi21.Builder.setDescription(object1, this.mDescription);
    MediaDescriptionCompatApi21.Builder.setIconBitmap(object1, this.mIcon);
    MediaDescriptionCompatApi21.Builder.setIconUri(object1, this.mIconUri);
    Bundle bundle1 = this.mExtras;
    Bundle bundle2 = bundle1;
    if (Build.VERSION.SDK_INT < 23) {
      bundle2 = bundle1;
      if (this.mMediaUri != null) {
        bundle2 = bundle1;
        if (bundle1 == null) {
          bundle2 = new Bundle();
          bundle2.putBoolean("android.support.v4.media.description.NULL_BUNDLE_FLAG", true);
        } 
        bundle2.putParcelable("android.support.v4.media.description.MEDIA_URI", (Parcelable)this.mMediaUri);
      } 
    } 
    MediaDescriptionCompatApi21.Builder.setExtras(object1, bundle2);
    if (Build.VERSION.SDK_INT >= 23)
      MediaDescriptionCompatApi23.Builder.setMediaUri(object1, this.mMediaUri); 
    Object object2 = MediaDescriptionCompatApi21.Builder.build(object1);
    this.mDescriptionObj = object2;
    return object2;
  }
  
  public String getMediaId() {
    return this.mMediaId;
  }
  
  public Uri getMediaUri() {
    return this.mMediaUri;
  }
  
  public CharSequence getSubtitle() {
    return this.mSubtitle;
  }
  
  public CharSequence getTitle() {
    return this.mTitle;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.mTitle);
    stringBuilder.append(", ");
    stringBuilder.append(this.mSubtitle);
    stringBuilder.append(", ");
    stringBuilder.append(this.mDescription);
    return stringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    if (Build.VERSION.SDK_INT < 21) {
      paramParcel.writeString(this.mMediaId);
      TextUtils.writeToParcel(this.mTitle, paramParcel, paramInt);
      TextUtils.writeToParcel(this.mSubtitle, paramParcel, paramInt);
      TextUtils.writeToParcel(this.mDescription, paramParcel, paramInt);
      paramParcel.writeParcelable((Parcelable)this.mIcon, paramInt);
      paramParcel.writeParcelable((Parcelable)this.mIconUri, paramInt);
      paramParcel.writeBundle(this.mExtras);
      paramParcel.writeParcelable((Parcelable)this.mMediaUri, paramInt);
    } else {
      MediaDescriptionCompatApi21.writeToParcel(getMediaDescription(), paramParcel, paramInt);
    } 
  }
  
  public static final class Builder {
    private CharSequence mDescription;
    
    private Bundle mExtras;
    
    private Bitmap mIcon;
    
    private Uri mIconUri;
    
    private String mMediaId;
    
    private Uri mMediaUri;
    
    private CharSequence mSubtitle;
    
    private CharSequence mTitle;
    
    public MediaDescriptionCompat build() {
      return new MediaDescriptionCompat(this.mMediaId, this.mTitle, this.mSubtitle, this.mDescription, this.mIcon, this.mIconUri, this.mExtras, this.mMediaUri);
    }
    
    public Builder setDescription(CharSequence param1CharSequence) {
      this.mDescription = param1CharSequence;
      return this;
    }
    
    public Builder setExtras(Bundle param1Bundle) {
      this.mExtras = param1Bundle;
      return this;
    }
    
    public Builder setIconBitmap(Bitmap param1Bitmap) {
      this.mIcon = param1Bitmap;
      return this;
    }
    
    public Builder setIconUri(Uri param1Uri) {
      this.mIconUri = param1Uri;
      return this;
    }
    
    public Builder setMediaId(String param1String) {
      this.mMediaId = param1String;
      return this;
    }
    
    public Builder setMediaUri(Uri param1Uri) {
      this.mMediaUri = param1Uri;
      return this;
    }
    
    public Builder setSubtitle(CharSequence param1CharSequence) {
      this.mSubtitle = param1CharSequence;
      return this;
    }
    
    public Builder setTitle(CharSequence param1CharSequence) {
      this.mTitle = param1CharSequence;
      return this;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\MediaDescriptionCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */