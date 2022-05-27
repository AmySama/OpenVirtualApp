package com.lody.virtual.remote;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public class AppTaskInfo implements Parcelable {
  public static final Parcelable.Creator<AppTaskInfo> CREATOR = new Parcelable.Creator<AppTaskInfo>() {
      public AppTaskInfo createFromParcel(Parcel param1Parcel) {
        return new AppTaskInfo(param1Parcel);
      }
      
      public AppTaskInfo[] newArray(int param1Int) {
        return new AppTaskInfo[param1Int];
      }
    };
  
  public ComponentName baseActivity;
  
  public Intent baseIntent;
  
  public int taskId;
  
  public ComponentName topActivity;
  
  public AppTaskInfo(int paramInt, Intent paramIntent, ComponentName paramComponentName1, ComponentName paramComponentName2) {
    this.taskId = paramInt;
    this.baseIntent = paramIntent;
    this.baseActivity = paramComponentName1;
    this.topActivity = paramComponentName2;
  }
  
  protected AppTaskInfo(Parcel paramParcel) {
    this.taskId = paramParcel.readInt();
    this.baseIntent = (Intent)paramParcel.readParcelable(Intent.class.getClassLoader());
    this.baseActivity = (ComponentName)paramParcel.readParcelable(ComponentName.class.getClassLoader());
    this.topActivity = (ComponentName)paramParcel.readParcelable(ComponentName.class.getClassLoader());
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.taskId);
    paramParcel.writeParcelable((Parcelable)this.baseIntent, paramInt);
    paramParcel.writeParcelable((Parcelable)this.baseActivity, paramInt);
    paramParcel.writeParcelable((Parcelable)this.topActivity, paramInt);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\AppTaskInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */