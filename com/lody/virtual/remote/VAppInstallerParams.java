package com.lody.virtual.remote;

import android.os.Parcel;
import android.os.Parcelable;

public class VAppInstallerParams implements Parcelable {
  public static final Parcelable.Creator<VAppInstallerParams> CREATOR = new Parcelable.Creator<VAppInstallerParams>() {
      public VAppInstallerParams createFromParcel(Parcel param1Parcel) {
        return new VAppInstallerParams(param1Parcel);
      }
      
      public VAppInstallerParams[] newArray(int param1Int) {
        return new VAppInstallerParams[param1Int];
      }
    };
  
  public static final int FLAG_INSTALL_NOTIFY = 1;
  
  public static final int FLAG_INSTALL_OVERRIDE_DONT_KILL_APP = 8;
  
  public static final int FLAG_INSTALL_OVERRIDE_FORBIDDEN = 4;
  
  public static final int FLAG_INSTALL_OVERRIDE_NO_CHECK = 2;
  
  public static final int FLAG_INSTALL_OVERRIDE_SKIP_ODEX = 16;
  
  public static final int MODE_FULL_INSTALL = 1;
  
  public static final int MODE_INHERIT_EXISTING = 2;
  
  private String cpuAbiOverride;
  
  private int installFlags = 0;
  
  private int mode = 1;
  
  public VAppInstallerParams() {}
  
  public VAppInstallerParams(int paramInt) {
    this.installFlags = paramInt;
  }
  
  public VAppInstallerParams(int paramInt1, int paramInt2) {
    this.installFlags = paramInt1;
    this.mode = paramInt2;
  }
  
  protected VAppInstallerParams(Parcel paramParcel) {
    this.installFlags = paramParcel.readInt();
    this.mode = paramParcel.readInt();
    this.cpuAbiOverride = paramParcel.readString();
  }
  
  public void addInstallFlags(int paramInt) {
    this.installFlags = paramInt | this.installFlags;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public String getCpuAbiOverride() {
    return this.cpuAbiOverride;
  }
  
  public int getInstallFlags() {
    return this.installFlags;
  }
  
  public int getMode() {
    return this.mode;
  }
  
  public void removeInstallFlags(int paramInt) {
    this.installFlags = paramInt & this.installFlags;
  }
  
  public void setCpuAbiOverride(String paramString) {
    this.cpuAbiOverride = paramString;
  }
  
  public void setInstallFlags(int paramInt) {
    this.installFlags = paramInt;
  }
  
  public void setMode(int paramInt) {
    this.mode = paramInt;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.installFlags);
    paramParcel.writeInt(this.mode);
    paramParcel.writeString(this.cpuAbiOverride);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\VAppInstallerParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */