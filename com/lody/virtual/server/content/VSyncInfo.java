package com.lody.virtual.server.content;

import android.accounts.Account;
import android.content.SyncInfo;
import android.os.Parcel;
import android.os.Parcelable;
import mirror.android.content.SyncInfo;

public class VSyncInfo implements Parcelable {
  public static final Parcelable.Creator<VSyncInfo> CREATOR;
  
  private static final Account REDACTED_ACCOUNT = new Account("*****", "*****");
  
  public final Account account;
  
  public final String authority;
  
  public final int authorityId;
  
  public final long startTime;
  
  static {
    CREATOR = new Parcelable.Creator<VSyncInfo>() {
        public VSyncInfo createFromParcel(Parcel param1Parcel) {
          return new VSyncInfo(param1Parcel);
        }
        
        public VSyncInfo[] newArray(int param1Int) {
          return new VSyncInfo[param1Int];
        }
      };
  }
  
  public VSyncInfo(int paramInt, Account paramAccount, String paramString, long paramLong) {
    this.authorityId = paramInt;
    this.account = paramAccount;
    this.authority = paramString;
    this.startTime = paramLong;
  }
  
  VSyncInfo(Parcel paramParcel) {
    this.authorityId = paramParcel.readInt();
    this.account = (Account)paramParcel.readParcelable(Account.class.getClassLoader());
    this.authority = paramParcel.readString();
    this.startTime = paramParcel.readLong();
  }
  
  public VSyncInfo(VSyncInfo paramVSyncInfo) {
    this.authorityId = paramVSyncInfo.authorityId;
    this.account = new Account(paramVSyncInfo.account.name, paramVSyncInfo.account.type);
    this.authority = paramVSyncInfo.authority;
    this.startTime = paramVSyncInfo.startTime;
  }
  
  public static VSyncInfo createAccountRedacted(int paramInt, String paramString, long paramLong) {
    return new VSyncInfo(paramInt, REDACTED_ACCOUNT, paramString, paramLong);
  }
  
  public int describeContents() {
    return 0;
  }
  
  public SyncInfo toSyncInfo() {
    return (SyncInfo)SyncInfo.ctor.newInstance(new Object[] { Integer.valueOf(this.authorityId), this.account, this.authority, Long.valueOf(this.startTime) });
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.authorityId);
    paramParcel.writeParcelable((Parcelable)this.account, paramInt);
    paramParcel.writeString(this.authority);
    paramParcel.writeLong(this.startTime);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\content\VSyncInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */