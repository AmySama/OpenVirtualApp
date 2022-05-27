package com.lody.virtual.remote;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VPackageManager;
import com.lody.virtual.os.VEnvironment;
import java.io.File;
import java.util.List;
import mirror.dalvik.system.VMRuntime;

public final class InstalledAppInfo implements Parcelable {
  public static final Parcelable.Creator<InstalledAppInfo> CREATOR = new Parcelable.Creator<InstalledAppInfo>() {
      public InstalledAppInfo createFromParcel(Parcel param1Parcel) {
        return new InstalledAppInfo(param1Parcel);
      }
      
      public InstalledAppInfo[] newArray(int param1Int) {
        return new InstalledAppInfo[param1Int];
      }
    };
  
  public int appId;
  
  public boolean dynamic;
  
  public int flag;
  
  public boolean is64bit;
  
  public String packageName;
  
  public String primaryCpuAbi;
  
  public String secondaryCpuAbi;
  
  protected InstalledAppInfo(Parcel paramParcel) {
    boolean bool2;
    this.packageName = paramParcel.readString();
    byte b = paramParcel.readByte();
    boolean bool1 = true;
    if (b != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    this.dynamic = bool2;
    this.flag = paramParcel.readInt();
    this.appId = paramParcel.readInt();
    this.primaryCpuAbi = paramParcel.readString();
    this.secondaryCpuAbi = paramParcel.readString();
    if (paramParcel.readByte() != 0) {
      bool2 = bool1;
    } else {
      bool2 = false;
    } 
    this.is64bit = bool2;
  }
  
  public InstalledAppInfo(String paramString1, boolean paramBoolean1, int paramInt1, int paramInt2, String paramString2, String paramString3, boolean paramBoolean2) {
    this.packageName = paramString1;
    this.dynamic = paramBoolean1;
    this.flag = paramInt1;
    this.appId = paramInt2;
    this.primaryCpuAbi = paramString2;
    this.secondaryCpuAbi = paramString3;
    this.is64bit = paramBoolean2;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public String getApkPath() {
    return getApkPath(VirtualCore.get().isExtPackage());
  }
  
  public String getApkPath(boolean paramBoolean) {
    if (this.dynamic)
      try {
        return (VirtualCore.get().getHostPackageManager().getApplicationInfo(this.packageName, 0)).publicSourceDir;
      } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
        throw new IllegalStateException(nameNotFoundException);
      }  
    return paramBoolean ? VEnvironment.getPackageFileExt(this.packageName).getPath() : VEnvironment.getPackageFile(this.packageName).getPath();
  }
  
  public ApplicationInfo getApplicationInfo(int paramInt) {
    ApplicationInfo applicationInfo = VPackageManager.get().getApplicationInfo(this.packageName, 0, paramInt);
    if (applicationInfo != null && !VirtualCore.get().isVAppProcess() && !(new File(applicationInfo.sourceDir)).exists()) {
      applicationInfo.sourceDir = getApkPath();
      applicationInfo.publicSourceDir = applicationInfo.sourceDir;
    } 
    return applicationInfo;
  }
  
  public int[] getInstalledUsers() {
    return VirtualCore.get().getPackageInstalledUsers(this.packageName);
  }
  
  public File getOatFile() {
    return getOatFile(VirtualCore.get().isExtPackage(), (String)VMRuntime.getCurrentInstructionSet.call(new Object[0]));
  }
  
  public File getOatFile(boolean paramBoolean, String paramString) {
    File file;
    if (paramBoolean) {
      file = VEnvironment.getOatFile(this.packageName, paramString);
    } else {
      file = VEnvironment.getOatFileExt(this.packageName, (String)file);
    } 
    return file;
  }
  
  public String getOatPath() {
    return getOatFile().getPath();
  }
  
  public PackageInfo getPackageInfo(int paramInt) {
    return VPackageManager.get().getPackageInfo(this.packageName, 0, paramInt);
  }
  
  public List<String> getSplitNames() {
    return VirtualCore.get().getInstalledSplitNames(this.packageName);
  }
  
  public boolean isLaunched(int paramInt) {
    return VirtualCore.get().isPackageLaunched(paramInt, this.packageName);
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.packageName);
    paramParcel.writeByte(this.dynamic);
    paramParcel.writeInt(this.flag);
    paramParcel.writeInt(this.appId);
    paramParcel.writeString(this.primaryCpuAbi);
    paramParcel.writeString(this.secondaryCpuAbi);
    paramParcel.writeByte(this.is64bit);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\InstalledAppInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */