package io.virtualapp.home.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.File;

public class AppInfoLite implements Parcelable {
  public static final Parcelable.Creator<AppInfoLite> CREATOR = new Parcelable.Creator<AppInfoLite>() {
      public AppInfoLite createFromParcel(Parcel param1Parcel) {
        return new AppInfoLite(param1Parcel);
      }
      
      public AppInfoLite[] newArray(int param1Int) {
        return new AppInfoLite[param1Int];
      }
    };
  
  public boolean dynamic;
  
  public String label;
  
  public String packageName;
  
  public String path;
  
  public String[] requestedPermissions;
  
  public int targetSdkVersion;
  
  protected AppInfoLite(Parcel paramParcel) {
    boolean bool;
    this.packageName = paramParcel.readString();
    this.path = paramParcel.readString();
    this.label = paramParcel.readString();
    if (paramParcel.readByte() != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.dynamic = bool;
    this.targetSdkVersion = paramParcel.readInt();
    this.requestedPermissions = paramParcel.createStringArray();
  }
  
  public AppInfoLite(AppInfo paramAppInfo) {
    this(paramAppInfo.packageName, paramAppInfo.path, String.valueOf(paramAppInfo.name), paramAppInfo.cloneMode, paramAppInfo.targetSdkVersion, paramAppInfo.requestedPermissions);
  }
  
  public AppInfoLite(String paramString1, String paramString2, String paramString3, boolean paramBoolean, int paramInt, String[] paramArrayOfString) {
    this.packageName = paramString1;
    this.path = paramString2;
    this.label = paramString3;
    this.dynamic = paramBoolean;
    this.targetSdkVersion = paramInt;
    this.requestedPermissions = paramArrayOfString;
  }
  
  public AppInfoLite(String paramString1, String paramString2, String paramString3, boolean paramBoolean, String[] paramArrayOfString) {
    this.packageName = paramString1;
    this.path = paramString2;
    this.label = paramString3;
    this.dynamic = paramBoolean;
    this.requestedPermissions = paramArrayOfString;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public Uri getUri() {
    if (this.dynamic) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("package:");
      stringBuilder.append(this.packageName);
      return Uri.parse(stringBuilder.toString());
    } 
    return Uri.fromFile(new File(this.path));
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.packageName);
    paramParcel.writeString(this.path);
    paramParcel.writeString(this.label);
    paramParcel.writeByte(this.dynamic);
    paramParcel.writeInt(this.targetSdkVersion);
    paramParcel.writeStringArray(this.requestedPermissions);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\models\AppInfoLite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */