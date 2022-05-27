package com.lody.virtual.os;

import android.os.Parcel;
import android.os.Parcelable;

public class VUserInfo implements Parcelable {
  public static final Parcelable.Creator<VUserInfo> CREATOR = new Parcelable.Creator<VUserInfo>() {
      public VUserInfo createFromParcel(Parcel param1Parcel) {
        return new VUserInfo(param1Parcel);
      }
      
      public VUserInfo[] newArray(int param1Int) {
        return new VUserInfo[param1Int];
      }
    };
  
  public static final int FLAG_ADMIN = 2;
  
  public static final int FLAG_DISABLED = 64;
  
  public static final int FLAG_GUEST = 4;
  
  public static final int FLAG_INITIALIZED = 16;
  
  public static final int FLAG_MANAGED_PROFILE = 32;
  
  public static final int FLAG_MASK_USER_TYPE = 255;
  
  public static final int FLAG_PRIMARY = 1;
  
  public static final int FLAG_RESTRICTED = 8;
  
  public static final int NO_PROFILE_GROUP_ID = -1;
  
  public long creationTime;
  
  public int flags;
  
  public boolean guestToRemove;
  
  public String iconPath;
  
  public int id;
  
  public long lastLoggedInTime;
  
  public String name;
  
  public boolean partial;
  
  public int profileGroupId;
  
  public int serialNumber;
  
  public VUserInfo() {}
  
  public VUserInfo(int paramInt) {
    this.id = paramInt;
  }
  
  public VUserInfo(int paramInt1, String paramString, int paramInt2) {
    this(paramInt1, paramString, null, paramInt2);
  }
  
  public VUserInfo(int paramInt1, String paramString1, String paramString2, int paramInt2) {
    this.id = paramInt1;
    this.name = paramString1;
    this.flags = paramInt2;
    this.iconPath = paramString2;
    this.profileGroupId = -1;
  }
  
  private VUserInfo(Parcel paramParcel) {
    boolean bool2;
    this.id = paramParcel.readInt();
    this.name = paramParcel.readString();
    this.iconPath = paramParcel.readString();
    this.flags = paramParcel.readInt();
    this.serialNumber = paramParcel.readInt();
    this.creationTime = paramParcel.readLong();
    this.lastLoggedInTime = paramParcel.readLong();
    int i = paramParcel.readInt();
    boolean bool1 = true;
    if (i != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    this.partial = bool2;
    this.profileGroupId = paramParcel.readInt();
    if (paramParcel.readInt() != 0) {
      bool2 = bool1;
    } else {
      bool2 = false;
    } 
    this.guestToRemove = bool2;
  }
  
  public VUserInfo(VUserInfo paramVUserInfo) {
    this.name = paramVUserInfo.name;
    this.iconPath = paramVUserInfo.iconPath;
    this.id = paramVUserInfo.id;
    this.flags = paramVUserInfo.flags;
    this.serialNumber = paramVUserInfo.serialNumber;
    this.creationTime = paramVUserInfo.creationTime;
    this.lastLoggedInTime = paramVUserInfo.lastLoggedInTime;
    this.partial = paramVUserInfo.partial;
    this.profileGroupId = paramVUserInfo.profileGroupId;
    this.guestToRemove = paramVUserInfo.guestToRemove;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public boolean isAdmin() {
    boolean bool;
    if ((this.flags & 0x2) == 2) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isEnabled() {
    boolean bool;
    if ((this.flags & 0x40) != 64) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isGuest() {
    boolean bool;
    if ((this.flags & 0x4) == 4) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isManagedProfile() {
    boolean bool;
    if ((this.flags & 0x20) == 32) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isPrimary() {
    int i = this.flags;
    boolean bool = true;
    if ((i & 0x1) != 1)
      bool = false; 
    return bool;
  }
  
  public boolean isRestricted() {
    boolean bool;
    if ((this.flags & 0x8) == 8) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("UserInfo{");
    stringBuilder.append(this.id);
    stringBuilder.append(":");
    stringBuilder.append(this.name);
    stringBuilder.append(":");
    stringBuilder.append(Integer.toHexString(this.flags));
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.id);
    paramParcel.writeString(this.name);
    paramParcel.writeString(this.iconPath);
    paramParcel.writeInt(this.flags);
    paramParcel.writeInt(this.serialNumber);
    paramParcel.writeLong(this.creationTime);
    paramParcel.writeLong(this.lastLoggedInTime);
    paramParcel.writeInt(this.partial);
    paramParcel.writeInt(this.profileGroupId);
    paramParcel.writeInt(this.guestToRemove);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\os\VUserInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */