package com.lody.virtual.server.pm.installer;

import android.content.pm.PackageInstaller;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import mirror.android.content.pm.PackageInstaller;

public class SessionParams implements Parcelable {
  public static final Parcelable.Creator<SessionParams> CREATOR = new Parcelable.Creator<SessionParams>() {
      public SessionParams createFromParcel(Parcel param1Parcel) {
        return new SessionParams(param1Parcel);
      }
      
      public SessionParams[] newArray(int param1Int) {
        return new SessionParams[param1Int];
      }
    };
  
  public static final int MODE_FULL_INSTALL = 1;
  
  public static final int MODE_INHERIT_EXISTING = 2;
  
  public static final int MODE_INVALID = -1;
  
  public String abiOverride;
  
  public Bitmap appIcon;
  
  public long appIconLastModified = -1L;
  
  public String appLabel;
  
  public String appPackageName;
  
  public String[] grantedRuntimePermissions;
  
  public int installFlags;
  
  public int installLocation = 1;
  
  public int mode = -1;
  
  public Uri originatingUri;
  
  public Uri referrerUri;
  
  public long sizeBytes = -1L;
  
  public String volumeUuid;
  
  public SessionParams(int paramInt) {
    this.mode = paramInt;
  }
  
  protected SessionParams(Parcel paramParcel) {
    this.mode = paramParcel.readInt();
    this.installFlags = paramParcel.readInt();
    this.installLocation = paramParcel.readInt();
    this.sizeBytes = paramParcel.readLong();
    this.appPackageName = paramParcel.readString();
    this.appIcon = (Bitmap)paramParcel.readParcelable(Bitmap.class.getClassLoader());
    this.appLabel = paramParcel.readString();
    this.appIconLastModified = paramParcel.readLong();
    this.originatingUri = (Uri)paramParcel.readParcelable(Uri.class.getClassLoader());
    this.referrerUri = (Uri)paramParcel.readParcelable(Uri.class.getClassLoader());
    this.abiOverride = paramParcel.readString();
    this.volumeUuid = paramParcel.readString();
    this.grantedRuntimePermissions = paramParcel.createStringArray();
  }
  
  public static SessionParams create(PackageInstaller.SessionParams paramSessionParams) {
    if (Build.VERSION.SDK_INT >= 23) {
      SessionParams sessionParams1 = new SessionParams(PackageInstaller.SessionParamsMarshmallow.mode.get(paramSessionParams));
      sessionParams1.installFlags = PackageInstaller.SessionParamsMarshmallow.installFlags.get(paramSessionParams);
      sessionParams1.installLocation = PackageInstaller.SessionParamsMarshmallow.installLocation.get(paramSessionParams);
      sessionParams1.sizeBytes = PackageInstaller.SessionParamsMarshmallow.sizeBytes.get(paramSessionParams);
      sessionParams1.appPackageName = (String)PackageInstaller.SessionParamsMarshmallow.appPackageName.get(paramSessionParams);
      sessionParams1.appIcon = (Bitmap)PackageInstaller.SessionParamsMarshmallow.appIcon.get(paramSessionParams);
      sessionParams1.appLabel = (String)PackageInstaller.SessionParamsMarshmallow.appLabel.get(paramSessionParams);
      sessionParams1.appIconLastModified = PackageInstaller.SessionParamsMarshmallow.appIconLastModified.get(paramSessionParams);
      sessionParams1.originatingUri = (Uri)PackageInstaller.SessionParamsMarshmallow.originatingUri.get(paramSessionParams);
      sessionParams1.referrerUri = (Uri)PackageInstaller.SessionParamsMarshmallow.referrerUri.get(paramSessionParams);
      sessionParams1.abiOverride = (String)PackageInstaller.SessionParamsMarshmallow.abiOverride.get(paramSessionParams);
      sessionParams1.volumeUuid = (String)PackageInstaller.SessionParamsMarshmallow.volumeUuid.get(paramSessionParams);
      sessionParams1.grantedRuntimePermissions = (String[])PackageInstaller.SessionParamsMarshmallow.grantedRuntimePermissions.get(paramSessionParams);
      return sessionParams1;
    } 
    SessionParams sessionParams = new SessionParams(PackageInstaller.SessionParamsLOLLIPOP.mode.get(paramSessionParams));
    sessionParams.installFlags = PackageInstaller.SessionParamsLOLLIPOP.installFlags.get(paramSessionParams);
    sessionParams.installLocation = PackageInstaller.SessionParamsLOLLIPOP.installLocation.get(paramSessionParams);
    sessionParams.sizeBytes = PackageInstaller.SessionParamsLOLLIPOP.sizeBytes.get(paramSessionParams);
    sessionParams.appPackageName = (String)PackageInstaller.SessionParamsLOLLIPOP.appPackageName.get(paramSessionParams);
    sessionParams.appIcon = (Bitmap)PackageInstaller.SessionParamsLOLLIPOP.appIcon.get(paramSessionParams);
    sessionParams.appLabel = (String)PackageInstaller.SessionParamsLOLLIPOP.appLabel.get(paramSessionParams);
    sessionParams.appIconLastModified = PackageInstaller.SessionParamsLOLLIPOP.appIconLastModified.get(paramSessionParams);
    sessionParams.originatingUri = (Uri)PackageInstaller.SessionParamsLOLLIPOP.originatingUri.get(paramSessionParams);
    sessionParams.referrerUri = (Uri)PackageInstaller.SessionParamsLOLLIPOP.referrerUri.get(paramSessionParams);
    sessionParams.abiOverride = (String)PackageInstaller.SessionParamsLOLLIPOP.abiOverride.get(paramSessionParams);
    return sessionParams;
  }
  
  public PackageInstaller.SessionParams build() {
    if (Build.VERSION.SDK_INT >= 23) {
      PackageInstaller.SessionParams sessionParams1 = new PackageInstaller.SessionParams(this.mode);
      PackageInstaller.SessionParamsMarshmallow.installFlags.set(sessionParams1, this.installFlags);
      PackageInstaller.SessionParamsMarshmallow.installLocation.set(sessionParams1, this.installLocation);
      PackageInstaller.SessionParamsMarshmallow.sizeBytes.set(sessionParams1, this.sizeBytes);
      PackageInstaller.SessionParamsMarshmallow.appPackageName.set(sessionParams1, this.appPackageName);
      PackageInstaller.SessionParamsMarshmallow.appIcon.set(sessionParams1, this.appIcon);
      PackageInstaller.SessionParamsMarshmallow.appLabel.set(sessionParams1, this.appLabel);
      PackageInstaller.SessionParamsMarshmallow.appIconLastModified.set(sessionParams1, this.appIconLastModified);
      PackageInstaller.SessionParamsMarshmallow.originatingUri.set(sessionParams1, this.originatingUri);
      PackageInstaller.SessionParamsMarshmallow.referrerUri.set(sessionParams1, this.referrerUri);
      PackageInstaller.SessionParamsMarshmallow.abiOverride.set(sessionParams1, this.abiOverride);
      PackageInstaller.SessionParamsMarshmallow.volumeUuid.set(sessionParams1, this.volumeUuid);
      PackageInstaller.SessionParamsMarshmallow.grantedRuntimePermissions.set(sessionParams1, this.grantedRuntimePermissions);
      return sessionParams1;
    } 
    PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(this.mode);
    PackageInstaller.SessionParamsLOLLIPOP.installFlags.set(sessionParams, this.installFlags);
    PackageInstaller.SessionParamsLOLLIPOP.installLocation.set(sessionParams, this.installLocation);
    PackageInstaller.SessionParamsLOLLIPOP.sizeBytes.set(sessionParams, this.sizeBytes);
    PackageInstaller.SessionParamsLOLLIPOP.appPackageName.set(sessionParams, this.appPackageName);
    PackageInstaller.SessionParamsLOLLIPOP.appIcon.set(sessionParams, this.appIcon);
    PackageInstaller.SessionParamsLOLLIPOP.appLabel.set(sessionParams, this.appLabel);
    PackageInstaller.SessionParamsLOLLIPOP.appIconLastModified.set(sessionParams, this.appIconLastModified);
    PackageInstaller.SessionParamsLOLLIPOP.originatingUri.set(sessionParams, this.originatingUri);
    PackageInstaller.SessionParamsLOLLIPOP.referrerUri.set(sessionParams, this.referrerUri);
    PackageInstaller.SessionParamsLOLLIPOP.abiOverride.set(sessionParams, this.abiOverride);
    return sessionParams;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.mode);
    paramParcel.writeInt(this.installFlags);
    paramParcel.writeInt(this.installLocation);
    paramParcel.writeLong(this.sizeBytes);
    paramParcel.writeString(this.appPackageName);
    paramParcel.writeParcelable((Parcelable)this.appIcon, paramInt);
    paramParcel.writeString(this.appLabel);
    paramParcel.writeLong(this.appIconLastModified);
    paramParcel.writeParcelable((Parcelable)this.originatingUri, paramInt);
    paramParcel.writeParcelable((Parcelable)this.referrerUri, paramInt);
    paramParcel.writeString(this.abiOverride);
    paramParcel.writeString(this.volumeUuid);
    paramParcel.writeStringArray(this.grantedRuntimePermissions);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\installer\SessionParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */