package com.lody.virtual.server.pm;

import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.remote.InstalledAppInfo;
import com.lody.virtual.server.pm.parser.PackageParserEx;

public class PackageSetting implements Parcelable {
  public static final Parcelable.Creator<PackageSetting> CREATOR;
  
  public static final int CURRENT_VERSION = 6;
  
  private static final PackageUserState DEFAULT_USER_STATE = new PackageUserState();
  
  public int appId;
  
  public boolean dynamic;
  
  public long firstInstallTime;
  
  public int flag;
  
  public boolean is64bitPackage;
  
  public long lastUpdateTime;
  
  public String packageName;
  
  public String primaryCpuAbi;
  
  public String secondaryCpuAbi;
  
  SparseArray<PackageUserState> userState;
  
  public int version;
  
  static {
    CREATOR = new Parcelable.Creator<PackageSetting>() {
        public PackageSetting createFromParcel(Parcel param1Parcel) {
          return new PackageSetting(6, param1Parcel);
        }
        
        public PackageSetting[] newArray(int param1Int) {
          return new PackageSetting[param1Int];
        }
      };
  }
  
  public PackageSetting() {
    this.userState = new SparseArray();
    this.version = 6;
  }
  
  PackageSetting(int paramInt, Parcel paramParcel) {
    boolean bool2;
    this.userState = new SparseArray();
    this.version = paramInt;
    this.packageName = paramParcel.readString();
    this.appId = paramParcel.readInt();
    this.primaryCpuAbi = paramParcel.readString();
    this.secondaryCpuAbi = paramParcel.readString();
    paramInt = paramParcel.readByte();
    boolean bool1 = true;
    if (paramInt != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    this.is64bitPackage = bool2;
    if (paramParcel.readByte() != 0) {
      bool2 = bool1;
    } else {
      bool2 = false;
    } 
    this.dynamic = bool2;
    this.userState = paramParcel.readSparseArray(PackageUserState.class.getClassLoader());
    this.flag = paramParcel.readInt();
    this.firstInstallTime = paramParcel.readLong();
    this.lastUpdateTime = paramParcel.readLong();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public InstalledAppInfo getAppInfo() {
    return new InstalledAppInfo(this.packageName, this.dynamic, this.flag, this.appId, this.primaryCpuAbi, this.secondaryCpuAbi, this.is64bitPackage);
  }
  
  public String getPackagePath() {
    if (this.dynamic)
      try {
        return (VirtualCore.get().getHostPackageManager().getApplicationInfo(this.packageName, 0)).publicSourceDir;
      } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
        nameNotFoundException.printStackTrace();
        return null;
      }  
    return VirtualCore.get().isExtPackage() ? VEnvironment.getPackageFileExt(this.packageName).getPath() : VEnvironment.getPackageFile(this.packageName).getPath();
  }
  
  public boolean is64bitPackage() {
    return this.is64bitPackage;
  }
  
  public boolean isEnabledAndMatchLPr(ComponentInfo paramComponentInfo, int paramInt1, int paramInt2) {
    return ((paramInt1 & 0x200) != 0) ? true : PackageParserEx.isEnabledLPr(paramComponentInfo, paramInt1, paramInt2);
  }
  
  public boolean isHidden(int paramInt) {
    return (readUserState(paramInt)).hidden;
  }
  
  public boolean isInstalled(int paramInt) {
    return (readUserState(paramInt)).installed;
  }
  
  public boolean isLaunched(int paramInt) {
    return (readUserState(paramInt)).launched;
  }
  
  public boolean isRunInExtProcess() {
    return is64bitPackage();
  }
  
  PackageUserState modifyUserState(int paramInt) {
    PackageUserState packageUserState1 = (PackageUserState)this.userState.get(paramInt);
    PackageUserState packageUserState2 = packageUserState1;
    if (packageUserState1 == null) {
      packageUserState2 = new PackageUserState();
      this.userState.put(paramInt, packageUserState2);
    } 
    return packageUserState2;
  }
  
  public PackageUserState readUserState(int paramInt) {
    PackageUserState packageUserState = (PackageUserState)this.userState.get(paramInt);
    return (packageUserState != null) ? packageUserState : DEFAULT_USER_STATE;
  }
  
  void removeUser(int paramInt) {
    this.userState.delete(paramInt);
  }
  
  public void setHidden(int paramInt, boolean paramBoolean) {
    (modifyUserState(paramInt)).hidden = paramBoolean;
  }
  
  public void setInstalled(int paramInt, boolean paramBoolean) {
    (modifyUserState(paramInt)).installed = paramBoolean;
  }
  
  public void setLaunched(int paramInt, boolean paramBoolean) {
    (modifyUserState(paramInt)).launched = paramBoolean;
  }
  
  void setUserState(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    PackageUserState packageUserState = modifyUserState(paramInt);
    packageUserState.launched = paramBoolean1;
    packageUserState.hidden = paramBoolean2;
    packageUserState.installed = paramBoolean3;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.packageName);
    paramParcel.writeInt(this.appId);
    paramParcel.writeString(this.primaryCpuAbi);
    paramParcel.writeString(this.secondaryCpuAbi);
    paramParcel.writeByte((byte)this.is64bitPackage);
    paramParcel.writeByte((byte)this.dynamic);
    paramParcel.writeSparseArray(this.userState);
    paramParcel.writeInt(this.flag);
    paramParcel.writeLong(this.firstInstallTime);
    paramParcel.writeLong(this.lastUpdateTime);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\PackageSetting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */