package android.support.v4.os;

import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public final class ParcelableCompat {
  @Deprecated
  public static <T> Parcelable.Creator<T> newCreator(ParcelableCompatCreatorCallbacks<T> paramParcelableCompatCreatorCallbacks) {
    return (Parcelable.Creator<T>)new ParcelableCompatCreatorHoneycombMR2<T>(paramParcelableCompatCreatorCallbacks);
  }
  
  static class ParcelableCompatCreatorHoneycombMR2<T> implements Parcelable.ClassLoaderCreator<T> {
    private final ParcelableCompatCreatorCallbacks<T> mCallbacks;
    
    ParcelableCompatCreatorHoneycombMR2(ParcelableCompatCreatorCallbacks<T> param1ParcelableCompatCreatorCallbacks) {
      this.mCallbacks = param1ParcelableCompatCreatorCallbacks;
    }
    
    public T createFromParcel(Parcel param1Parcel) {
      return this.mCallbacks.createFromParcel(param1Parcel, null);
    }
    
    public T createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return this.mCallbacks.createFromParcel(param1Parcel, param1ClassLoader);
    }
    
    public T[] newArray(int param1Int) {
      return this.mCallbacks.newArray(param1Int);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\os\ParcelableCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */