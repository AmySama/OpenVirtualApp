package android.os;

public final class ParcelableException extends RuntimeException implements Parcelable {
  public static final Parcelable.Creator<ParcelableException> CREATOR = new Parcelable.Creator<ParcelableException>() {
      public ParcelableException createFromParcel(Parcel param1Parcel) {
        return new ParcelableException(ParcelableException.readFromParcel(param1Parcel));
      }
      
      public ParcelableException[] newArray(int param1Int) {
        return new ParcelableException[param1Int];
      }
    };
  
  public ParcelableException(Throwable paramThrowable) {
    super(paramThrowable);
  }
  
  public static Throwable readFromParcel(Parcel paramParcel) {
    throw new RuntimeException("Stub!");
  }
  
  public static void writeToParcel(Parcel paramParcel, Throwable paramThrowable) {
    throw new RuntimeException("Stub!");
  }
  
  public int describeContents() {
    return 0;
  }
  
  public <T extends Throwable> void maybeRethrow(Class<T> paramClass) throws T {
    throw (T)new RuntimeException("Stub!");
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    throw new RuntimeException("Stub!");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\os\ParcelableException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */