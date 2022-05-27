package com.lody.virtual.server.pm.legacy;

import android.os.Parcel;
import android.util.SparseArray;
import com.lody.virtual.server.pm.PackageUserState;

@Deprecated
public class PackageSettingV1 {
  public int appId;
  
  public int flag;
  
  public boolean notCopyApk;
  
  public String packageName;
  
  public SparseArray<PackageUserState> userState;
  
  public void readFromParcel(Parcel paramParcel, int paramInt) {
    boolean bool;
    this.packageName = paramParcel.readString();
    paramParcel.readString();
    paramParcel.readString();
    if (paramParcel.readByte() != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.notCopyApk = bool;
    this.appId = paramParcel.readInt();
    this.userState = paramParcel.readSparseArray(PackageUserState.class.getClassLoader());
    paramParcel.readByte();
    if (paramInt > 3)
      this.flag = paramParcel.readInt(); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\legacy\PackageSettingV1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */