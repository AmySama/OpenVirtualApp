package android.support.v4.media;

import android.media.Rating;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class RatingCompat implements Parcelable {
  public static final Parcelable.Creator<RatingCompat> CREATOR = new Parcelable.Creator<RatingCompat>() {
      public RatingCompat createFromParcel(Parcel param1Parcel) {
        return new RatingCompat(param1Parcel.readInt(), param1Parcel.readFloat());
      }
      
      public RatingCompat[] newArray(int param1Int) {
        return new RatingCompat[param1Int];
      }
    };
  
  public static final int RATING_3_STARS = 3;
  
  public static final int RATING_4_STARS = 4;
  
  public static final int RATING_5_STARS = 5;
  
  public static final int RATING_HEART = 1;
  
  public static final int RATING_NONE = 0;
  
  private static final float RATING_NOT_RATED = -1.0F;
  
  public static final int RATING_PERCENTAGE = 6;
  
  public static final int RATING_THUMB_UP_DOWN = 2;
  
  private static final String TAG = "Rating";
  
  private Object mRatingObj;
  
  private final int mRatingStyle;
  
  private final float mRatingValue;
  
  RatingCompat(int paramInt, float paramFloat) {
    this.mRatingStyle = paramInt;
    this.mRatingValue = paramFloat;
  }
  
  public static RatingCompat fromRating(Object paramObject) {
    RatingCompat ratingCompat;
    Rating rating1 = null;
    Rating rating2 = rating1;
    if (paramObject != null) {
      rating2 = rating1;
      if (Build.VERSION.SDK_INT >= 19) {
        rating2 = (Rating)paramObject;
        int i = rating2.getRatingStyle();
        if (rating2.isRated()) {
          switch (i) {
            default:
              return null;
            case 6:
              ratingCompat = newPercentageRating(rating2.getPercentRating());
              break;
            case 3:
            case 4:
            case 5:
              ratingCompat = newStarRating(i, ratingCompat.getStarRating());
              break;
            case 2:
              ratingCompat = newThumbRating(ratingCompat.isThumbUp());
              break;
            case 1:
              ratingCompat = newHeartRating(ratingCompat.hasHeart());
              break;
          } 
        } else {
          ratingCompat = newUnratedRating(i);
        } 
        ratingCompat.mRatingObj = paramObject;
      } 
    } 
    return ratingCompat;
  }
  
  public static RatingCompat newHeartRating(boolean paramBoolean) {
    float f;
    if (paramBoolean) {
      f = 1.0F;
    } else {
      f = 0.0F;
    } 
    return new RatingCompat(1, f);
  }
  
  public static RatingCompat newPercentageRating(float paramFloat) {
    if (paramFloat < 0.0F || paramFloat > 100.0F) {
      Log.e("Rating", "Invalid percentage-based rating value");
      return null;
    } 
    return new RatingCompat(6, paramFloat);
  }
  
  public static RatingCompat newStarRating(int paramInt, float paramFloat) {
    float f;
    if (paramInt != 3) {
      if (paramInt != 4) {
        if (paramInt != 5) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Invalid rating style (");
          stringBuilder.append(paramInt);
          stringBuilder.append(") for a star rating");
          Log.e("Rating", stringBuilder.toString());
          return null;
        } 
        f = 5.0F;
      } else {
        f = 4.0F;
      } 
    } else {
      f = 3.0F;
    } 
    if (paramFloat < 0.0F || paramFloat > f) {
      Log.e("Rating", "Trying to set out of range star-based rating");
      return null;
    } 
    return new RatingCompat(paramInt, paramFloat);
  }
  
  public static RatingCompat newThumbRating(boolean paramBoolean) {
    float f;
    if (paramBoolean) {
      f = 1.0F;
    } else {
      f = 0.0F;
    } 
    return new RatingCompat(2, f);
  }
  
  public static RatingCompat newUnratedRating(int paramInt) {
    switch (paramInt) {
      default:
        return null;
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
        break;
    } 
    return new RatingCompat(paramInt, -1.0F);
  }
  
  public int describeContents() {
    return this.mRatingStyle;
  }
  
  public float getPercentRating() {
    return (this.mRatingStyle != 6 || !isRated()) ? -1.0F : this.mRatingValue;
  }
  
  public Object getRating() {
    if (this.mRatingObj == null && Build.VERSION.SDK_INT >= 19)
      if (isRated()) {
        int i = this.mRatingStyle;
        switch (i) {
          default:
            return null;
          case 6:
            this.mRatingObj = Rating.newPercentageRating(getPercentRating());
            return this.mRatingObj;
          case 3:
          case 4:
          case 5:
            this.mRatingObj = Rating.newStarRating(i, getStarRating());
            return this.mRatingObj;
          case 2:
            this.mRatingObj = Rating.newThumbRating(isThumbUp());
            return this.mRatingObj;
          case 1:
            break;
        } 
        this.mRatingObj = Rating.newHeartRating(hasHeart());
      } else {
        this.mRatingObj = Rating.newUnratedRating(this.mRatingStyle);
      }  
    return this.mRatingObj;
  }
  
  public int getRatingStyle() {
    return this.mRatingStyle;
  }
  
  public float getStarRating() {
    int i = this.mRatingStyle;
    return ((i == 3 || i == 4 || i == 5) && isRated()) ? this.mRatingValue : -1.0F;
  }
  
  public boolean hasHeart() {
    int i = this.mRatingStyle;
    boolean bool = false;
    if (i != 1)
      return false; 
    if (this.mRatingValue == 1.0F)
      bool = true; 
    return bool;
  }
  
  public boolean isRated() {
    boolean bool;
    if (this.mRatingValue >= 0.0F) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isThumbUp() {
    int i = this.mRatingStyle;
    boolean bool = false;
    if (i != 2)
      return false; 
    if (this.mRatingValue == 1.0F)
      bool = true; 
    return bool;
  }
  
  public String toString() {
    String str;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Rating:style=");
    stringBuilder.append(this.mRatingStyle);
    stringBuilder.append(" rating=");
    float f = this.mRatingValue;
    if (f < 0.0F) {
      str = "unrated";
    } else {
      str = String.valueOf(f);
    } 
    stringBuilder.append(str);
    return stringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.mRatingStyle);
    paramParcel.writeFloat(this.mRatingValue);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface StarStyle {}
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Style {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\RatingCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */