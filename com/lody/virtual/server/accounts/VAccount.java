package com.lody.virtual.server.accounts;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

public class VAccount implements Parcelable {
  public static final Parcelable.Creator<VAccount> CREATOR = new Parcelable.Creator<VAccount>() {
      public VAccount createFromParcel(Parcel param1Parcel) {
        return new VAccount(param1Parcel);
      }
      
      public VAccount[] newArray(int param1Int) {
        return new VAccount[param1Int];
      }
    };
  
  public Map<String, String> authTokens;
  
  public long lastAuthenticatedTime;
  
  public String name;
  
  public String password;
  
  public String previousName;
  
  public String type;
  
  public Map<String, String> userDatas;
  
  public int userId;
  
  public VAccount(int paramInt, Account paramAccount) {
    this.userId = paramInt;
    this.name = paramAccount.name;
    this.type = paramAccount.type;
    this.authTokens = new HashMap<String, String>();
    this.userDatas = new HashMap<String, String>();
  }
  
  public VAccount(Parcel paramParcel) {
    this.userId = paramParcel.readInt();
    this.name = paramParcel.readString();
    this.previousName = paramParcel.readString();
    this.type = paramParcel.readString();
    this.password = paramParcel.readString();
    this.lastAuthenticatedTime = paramParcel.readLong();
    int i = paramParcel.readInt();
    this.authTokens = new HashMap<String, String>(i);
    boolean bool = false;
    byte b;
    for (b = 0; b < i; b++) {
      String str1 = paramParcel.readString();
      String str2 = paramParcel.readString();
      this.authTokens.put(str1, str2);
    } 
    i = paramParcel.readInt();
    this.userDatas = new HashMap<String, String>(i);
    for (b = bool; b < i; b++) {
      String str2 = paramParcel.readString();
      String str1 = paramParcel.readString();
      this.userDatas.put(str2, str1);
    } 
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.userId);
    paramParcel.writeString(this.name);
    paramParcel.writeString(this.previousName);
    paramParcel.writeString(this.type);
    paramParcel.writeString(this.password);
    paramParcel.writeLong(this.lastAuthenticatedTime);
    paramParcel.writeInt(this.authTokens.size());
    for (Map.Entry<String, String> entry : this.authTokens.entrySet()) {
      paramParcel.writeString((String)entry.getKey());
      paramParcel.writeString((String)entry.getValue());
    } 
    paramParcel.writeInt(this.userDatas.size());
    for (Map.Entry<String, String> entry : this.userDatas.entrySet()) {
      paramParcel.writeString((String)entry.getKey());
      paramParcel.writeString((String)entry.getValue());
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\accounts\VAccount.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */