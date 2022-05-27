package com.lody.virtual.remote;

import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class ReceiverInfo implements Parcelable {
  public static final Parcelable.Creator<ReceiverInfo> CREATOR = new Parcelable.Creator<ReceiverInfo>() {
      public ReceiverInfo createFromParcel(Parcel param1Parcel) {
        return new ReceiverInfo(param1Parcel);
      }
      
      public ReceiverInfo[] newArray(int param1Int) {
        return new ReceiverInfo[param1Int];
      }
    };
  
  public List<IntentFilter> filters;
  
  public ActivityInfo info;
  
  public ReceiverInfo(ActivityInfo paramActivityInfo, List<IntentFilter> paramList) {
    this.info = paramActivityInfo;
    this.filters = paramList;
  }
  
  protected ReceiverInfo(Parcel paramParcel) {
    this.info = (ActivityInfo)paramParcel.readParcelable(ActivityInfo.class.getClassLoader());
    this.filters = paramParcel.createTypedArrayList(IntentFilter.CREATOR);
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeParcelable((Parcelable)this.info, paramInt);
    paramParcel.writeTypedList(this.filters);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\ReceiverInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */