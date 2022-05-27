package com.lody.virtual.server.pm.installer;

import android.content.pm.PackageInstaller;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import mirror.android.content.pm.PackageInstaller;

public class SessionInfo implements Parcelable {
  public static final Parcelable.Creator<SessionInfo> CREATOR = new Parcelable.Creator<SessionInfo>() {
      public SessionInfo createFromParcel(Parcel param1Parcel) {
        return new SessionInfo(param1Parcel);
      }
      
      public SessionInfo[] newArray(int param1Int) {
        return new SessionInfo[param1Int];
      }
    };
  
  public boolean active;
  
  public Bitmap appIcon;
  
  public CharSequence appLabel;
  
  public String appPackageName;
  
  public String installerPackageName;
  
  public int mode;
  
  public float progress;
  
  public String resolvedBaseCodePath;
  
  public boolean sealed;
  
  public int sessionId;
  
  public long sizeBytes;
  
  public SessionInfo() {}
  
  protected SessionInfo(Parcel paramParcel) {
    boolean bool2;
    this.sessionId = paramParcel.readInt();
    this.installerPackageName = paramParcel.readString();
    this.resolvedBaseCodePath = paramParcel.readString();
    this.progress = paramParcel.readFloat();
    byte b = paramParcel.readByte();
    boolean bool1 = true;
    if (b != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    this.sealed = bool2;
    if (paramParcel.readByte() != 0) {
      bool2 = bool1;
    } else {
      bool2 = false;
    } 
    this.active = bool2;
    this.mode = paramParcel.readInt();
    this.sizeBytes = paramParcel.readLong();
    this.appPackageName = paramParcel.readString();
    this.appIcon = (Bitmap)paramParcel.readParcelable(Bitmap.class.getClassLoader());
    this.appLabel = paramParcel.readString();
  }
  
  public static SessionInfo realloc(PackageInstaller.SessionInfo paramSessionInfo) {
    SessionInfo sessionInfo = new SessionInfo();
    sessionInfo.sessionId = PackageInstaller.SessionInfo.sessionId.get(paramSessionInfo);
    sessionInfo.installerPackageName = (String)PackageInstaller.SessionInfo.installerPackageName.get(paramSessionInfo);
    sessionInfo.resolvedBaseCodePath = (String)PackageInstaller.SessionInfo.resolvedBaseCodePath.get(paramSessionInfo);
    sessionInfo.progress = PackageInstaller.SessionInfo.progress.get(paramSessionInfo);
    sessionInfo.sealed = PackageInstaller.SessionInfo.sealed.get(paramSessionInfo);
    sessionInfo.active = PackageInstaller.SessionInfo.active.get(paramSessionInfo);
    sessionInfo.mode = PackageInstaller.SessionInfo.mode.get(paramSessionInfo);
    sessionInfo.sizeBytes = PackageInstaller.SessionInfo.sizeBytes.get(paramSessionInfo);
    sessionInfo.appPackageName = (String)PackageInstaller.SessionInfo.appPackageName.get(paramSessionInfo);
    sessionInfo.appIcon = (Bitmap)PackageInstaller.SessionInfo.appIcon.get(paramSessionInfo);
    sessionInfo.appLabel = (CharSequence)PackageInstaller.SessionInfo.appLabel.get(paramSessionInfo);
    return sessionInfo;
  }
  
  public PackageInstaller.SessionInfo alloc() {
    PackageInstaller.SessionInfo sessionInfo = (PackageInstaller.SessionInfo)PackageInstaller.SessionInfo.ctor.newInstance();
    PackageInstaller.SessionInfo.sessionId.set(sessionInfo, this.sessionId);
    PackageInstaller.SessionInfo.installerPackageName.set(sessionInfo, this.installerPackageName);
    PackageInstaller.SessionInfo.resolvedBaseCodePath.set(sessionInfo, this.resolvedBaseCodePath);
    PackageInstaller.SessionInfo.progress.set(sessionInfo, this.progress);
    PackageInstaller.SessionInfo.sealed.set(sessionInfo, this.sealed);
    PackageInstaller.SessionInfo.active.set(sessionInfo, this.active);
    PackageInstaller.SessionInfo.mode.set(sessionInfo, this.mode);
    PackageInstaller.SessionInfo.sizeBytes.set(sessionInfo, this.sizeBytes);
    PackageInstaller.SessionInfo.appPackageName.set(sessionInfo, this.appPackageName);
    PackageInstaller.SessionInfo.appIcon.set(sessionInfo, this.appIcon);
    PackageInstaller.SessionInfo.appLabel.set(sessionInfo, this.appLabel);
    return sessionInfo;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.sessionId);
    paramParcel.writeString(this.installerPackageName);
    paramParcel.writeString(this.resolvedBaseCodePath);
    paramParcel.writeFloat(this.progress);
    paramParcel.writeByte(this.sealed);
    paramParcel.writeByte(this.active);
    paramParcel.writeInt(this.mode);
    paramParcel.writeLong(this.sizeBytes);
    paramParcel.writeString(this.appPackageName);
    paramParcel.writeParcelable((Parcelable)this.appIcon, paramInt);
    CharSequence charSequence = this.appLabel;
    if (charSequence != null)
      paramParcel.writeString(charSequence.toString()); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\installer\SessionInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */