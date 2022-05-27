package com.lody.virtual.server.pm.legacy;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.lody.virtual.server.pm.PackageUserState;

@Deprecated
public class PackageSettingV5 implements Parcelable {
  public static final Parcelable.Creator<PackageSettingV5> CREATOR;
  
  public static final int CURRENT_VERSION = 5;
  
  private static final PackageUserState DEFAULT_USER_STATE = new PackageUserState();
  
  public static final int MODE_APP_COPY_APK = 0;
  
  public static final int MODE_APP_USE_OUTSIDE_APK = 1;
  
  public int appId;
  
  public int appMode;
  
  public long firstInstallTime;
  
  public int flag;
  
  public long lastUpdateTime;
  
  public String packageName;
  
  public SparseArray<PackageUserState> userState = new SparseArray();
  
  public int version = 5;
  
  static {
    CREATOR = new Parcelable.Creator<PackageSettingV5>() {
        public PackageSettingV5 createFromParcel(Parcel param1Parcel) {
          return new PackageSettingV5(5, param1Parcel);
        }
        
        public PackageSettingV5[] newArray(int param1Int) {
          return new PackageSettingV5[param1Int];
        }
      };
  }
  
  public PackageSettingV5() {}
  
  public PackageSettingV5(int paramInt, Parcel paramParcel) {
    this.packageName = paramParcel.readString();
    this.appId = paramParcel.readInt();
    this.appMode = paramParcel.readInt();
    this.userState = paramParcel.readSparseArray(PackageUserState.class.getClassLoader());
    this.flag = paramParcel.readInt();
    this.firstInstallTime = paramParcel.readLong();
    this.lastUpdateTime = paramParcel.readLong();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.packageName);
    paramParcel.writeInt(this.appId);
    paramParcel.writeInt(this.appMode);
    paramParcel.writeSparseArray(this.userState);
    paramParcel.writeInt(this.flag);
    paramParcel.writeLong(this.firstInstallTime);
    paramParcel.writeLong(this.lastUpdateTime);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\legacy\PackageSettingV5.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */