package com.lody.virtual.remote;

import android.os.Parcel;
import android.os.Parcelable;

public class Problem implements Parcelable {
  public static final Parcelable.Creator<Problem> CREATOR = new Parcelable.Creator<Problem>() {
      public Problem createFromParcel(Parcel param1Parcel) {
        return new Problem(param1Parcel);
      }
      
      public Problem[] newArray(int param1Int) {
        return new Problem[param1Int];
      }
    };
  
  public Throwable e;
  
  protected Problem(Parcel paramParcel) {
    this.e = (Throwable)paramParcel.readSerializable();
  }
  
  public Problem(Throwable paramThrowable) {
    this.e = paramThrowable;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeSerializable(this.e);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\Problem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */