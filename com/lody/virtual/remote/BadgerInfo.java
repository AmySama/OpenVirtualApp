package com.lody.virtual.remote;

import android.os.Parcel;
import android.os.Parcelable;
import com.lody.virtual.os.VUserHandle;

public class BadgerInfo implements Parcelable {
  public static final Parcelable.Creator<BadgerInfo> CREATOR = new Parcelable.Creator<BadgerInfo>() {
      public BadgerInfo createFromParcel(Parcel param1Parcel) {
        return new BadgerInfo(param1Parcel);
      }
      
      public BadgerInfo[] newArray(int param1Int) {
        return new BadgerInfo[param1Int];
      }
    };
  
  public int badgerCount;
  
  public String className;
  
  public String packageName;
  
  public int userId = VUserHandle.myUserId();
  
  public BadgerInfo() {}
  
  protected BadgerInfo(Parcel paramParcel) {
    this.packageName = paramParcel.readString();
    this.badgerCount = paramParcel.readInt();
    this.className = paramParcel.readString();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.userId);
    paramParcel.writeString(this.packageName);
    paramParcel.writeInt(this.badgerCount);
    paramParcel.writeString(this.className);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\BadgerInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */