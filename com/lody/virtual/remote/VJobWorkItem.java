package com.lody.virtual.remote;

import android.app.job.JobWorkItem;
import android.os.Parcel;
import android.os.Parcelable;

public class VJobWorkItem implements Parcelable {
  public static final Parcelable.Creator<VJobWorkItem> CREATOR = new Parcelable.Creator<VJobWorkItem>() {
      public VJobWorkItem createFromParcel(Parcel param1Parcel) {
        return new VJobWorkItem(param1Parcel);
      }
      
      public VJobWorkItem[] newArray(int param1Int) {
        return new VJobWorkItem[param1Int];
      }
    };
  
  private JobWorkItem item;
  
  public VJobWorkItem() {}
  
  public VJobWorkItem(JobWorkItem paramJobWorkItem) {
    this.item = paramJobWorkItem;
  }
  
  protected VJobWorkItem(Parcel paramParcel) {
    this.item = (JobWorkItem)paramParcel.readParcelable(JobWorkItem.class.getClassLoader());
  }
  
  public int describeContents() {
    return 0;
  }
  
  public JobWorkItem get() {
    return this.item;
  }
  
  public void set(JobWorkItem paramJobWorkItem) {
    this.item = paramJobWorkItem;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeParcelable((Parcelable)this.item, paramInt);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\VJobWorkItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */