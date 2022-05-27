package android.support.v4.os;

import android.os.Parcel;

@Deprecated
public interface ParcelableCompatCreatorCallbacks<T> {
  T createFromParcel(Parcel paramParcel, ClassLoader paramClassLoader);
  
  T[] newArray(int paramInt);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\os\ParcelableCompatCreatorCallbacks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */