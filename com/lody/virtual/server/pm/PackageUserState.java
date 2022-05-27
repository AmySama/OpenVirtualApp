package com.lody.virtual.server.pm;

import android.os.Parcel;
import android.os.Parcelable;

public class PackageUserState implements Parcelable {
  public static final Parcelable.Creator<PackageUserState> CREATOR = new Parcelable.Creator<PackageUserState>() {
      public PackageUserState createFromParcel(Parcel param1Parcel) {
        return new PackageUserState(param1Parcel);
      }
      
      public PackageUserState[] newArray(int param1Int) {
        return new PackageUserState[param1Int];
      }
    };
  
  public boolean hidden;
  
  public boolean installed;
  
  public boolean launched;
  
  public PackageUserState() {
    this.installed = false;
    this.launched = true;
    this.hidden = false;
  }
  
  protected PackageUserState(Parcel paramParcel) {
    boolean bool2;
    byte b = paramParcel.readByte();
    boolean bool1 = true;
    if (b != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    this.launched = bool2;
    if (paramParcel.readByte() != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    this.hidden = bool2;
    if (paramParcel.readByte() != 0) {
      bool2 = bool1;
    } else {
      bool2 = false;
    } 
    this.installed = bool2;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeByte(this.launched);
    paramParcel.writeByte(this.hidden);
    paramParcel.writeByte(this.installed);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\PackageUserState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */