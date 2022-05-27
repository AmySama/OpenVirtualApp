package android.content;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;

public class SyncInfo implements Parcelable {
  public static final Parcelable.Creator<SyncInfo> CREATOR;
  
  private static final Account REDACTED_ACCOUNT = new Account("*****", "*****");
  
  public final Account account;
  
  public final String authority;
  
  public final int authorityId;
  
  public final long startTime;
  
  static {
    CREATOR = new Parcelable.Creator<SyncInfo>() {
        public SyncInfo createFromParcel(Parcel param1Parcel) {
          return new SyncInfo(param1Parcel);
        }
        
        public SyncInfo[] newArray(int param1Int) {
          return new SyncInfo[param1Int];
        }
      };
  }
  
  public SyncInfo(int paramInt, Account paramAccount, String paramString, long paramLong) {
    throw new RuntimeException("Stub!");
  }
  
  public SyncInfo(SyncInfo paramSyncInfo) {
    throw new RuntimeException("Stub!");
  }
  
  SyncInfo(Parcel paramParcel) {
    throw new RuntimeException("Stub!");
  }
  
  public static SyncInfo createAccountRedacted(int paramInt, String paramString, long paramLong) {
    throw new RuntimeException("Stub!");
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.authorityId);
    paramParcel.writeParcelable((Parcelable)this.account, paramInt);
    paramParcel.writeString(this.authority);
    paramParcel.writeLong(this.startTime);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\content\SyncInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */