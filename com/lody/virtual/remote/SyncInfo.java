package com.lody.virtual.remote;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;

public class SyncInfo {
  public static final Parcelable.Creator<SyncInfo> CREATOR = new Parcelable.Creator<SyncInfo>() {
      public SyncInfo createFromParcel(Parcel param1Parcel) {
        return new SyncInfo(param1Parcel);
      }
      
      public SyncInfo[] newArray(int param1Int) {
        return new SyncInfo[param1Int];
      }
    };
  
  public final Account account;
  
  public final String authority;
  
  public final int authorityId;
  
  public final long startTime;
  
  public SyncInfo(int paramInt, Account paramAccount, String paramString, long paramLong) {
    this.authorityId = paramInt;
    this.account = paramAccount;
    this.authority = paramString;
    this.startTime = paramLong;
  }
  
  SyncInfo(Parcel paramParcel) {
    this.authorityId = paramParcel.readInt();
    this.account = new Account(paramParcel);
    this.authority = paramParcel.readString();
    this.startTime = paramParcel.readLong();
  }
  
  public android.content.SyncInfo create() {
    return (android.content.SyncInfo)mirror.android.content.SyncInfo.ctor.newInstance(new Object[] { Integer.valueOf(this.authorityId), this.account, this.authority, Long.valueOf(this.startTime) });
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.authorityId);
    this.account.writeToParcel(paramParcel, 0);
    paramParcel.writeString(this.authority);
    paramParcel.writeLong(this.startTime);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\SyncInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */