package com.lody.virtual.remote;

import android.os.Parcel;
import android.os.Parcelable;

public class VAppInstallerResult implements Parcelable {
  public static final Parcelable.Creator<VAppInstallerResult> CREATOR = new Parcelable.Creator<VAppInstallerResult>() {
      public VAppInstallerResult createFromParcel(Parcel param1Parcel) {
        return new VAppInstallerResult(param1Parcel);
      }
      
      public VAppInstallerResult[] newArray(int param1Int) {
        return new VAppInstallerResult[param1Int];
      }
    };
  
  public static final int FLAG_IS_SPLIT_PACKAGE = 1;
  
  public static final int FLAG_PACKAGE_UPDATED = 2;
  
  public static final int STATUS_FAILURE_ABORTED = 3;
  
  public static final int STATUS_FAILURE_BLOCKED = 2;
  
  public static final int STATUS_FAILURE_CONFLICT = 5;
  
  public static final int STATUS_FAILURE_INCOMPATIBLE = 7;
  
  public static final int STATUS_FAILURE_INVALID = 4;
  
  public static final int STATUS_FAILURE_NO_BASE_APK = 8;
  
  public static final int STATUS_FAILURE_STORAGE = 6;
  
  public static final int STATUS_SUCCESS = 0;
  
  public int flags;
  
  public String packageName;
  
  public int status = 0;
  
  public VAppInstallerResult() {}
  
  public VAppInstallerResult(int paramInt) {
    this.status = paramInt;
  }
  
  public VAppInstallerResult(int paramInt1, int paramInt2) {
    this.status = paramInt1;
    this.flags = paramInt2;
  }
  
  protected VAppInstallerResult(Parcel paramParcel) {
    this.packageName = paramParcel.readString();
    this.status = paramParcel.readInt();
    this.flags = paramParcel.readInt();
  }
  
  public VAppInstallerResult(String paramString, int paramInt) {
    this.packageName = paramString;
    this.status = paramInt;
  }
  
  public VAppInstallerResult(String paramString, int paramInt1, int paramInt2) {
    this.packageName = paramString;
    this.status = paramInt1;
    this.flags = paramInt2;
  }
  
  public static VAppInstallerResult create(int paramInt) {
    return new VAppInstallerResult(paramInt);
  }
  
  public static VAppInstallerResult create(String paramString, int paramInt) {
    return new VAppInstallerResult(paramString, paramInt);
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.packageName);
    paramParcel.writeInt(this.status);
    paramParcel.writeInt(this.flags);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\VAppInstallerResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */