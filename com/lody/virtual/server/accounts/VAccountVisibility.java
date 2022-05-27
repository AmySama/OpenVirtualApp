package com.lody.virtual.server.accounts;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

public class VAccountVisibility implements Parcelable {
  public static final Parcelable.Creator<VAccountVisibility> CREATOR = new Parcelable.Creator<VAccountVisibility>() {
      public VAccountVisibility createFromParcel(Parcel param1Parcel) {
        return new VAccountVisibility(param1Parcel);
      }
      
      public VAccountVisibility[] newArray(int param1Int) {
        return new VAccountVisibility[param1Int];
      }
    };
  
  public String name;
  
  public String type;
  
  public int userId;
  
  public Map<String, Integer> visibility;
  
  public VAccountVisibility() {}
  
  public VAccountVisibility(int paramInt, Account paramAccount, Map<String, Integer> paramMap) {
    this.userId = paramInt;
    this.name = paramAccount.name;
    this.type = paramAccount.type;
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    this.visibility = (Map)hashMap;
    if (paramMap != null)
      hashMap.putAll(paramMap); 
  }
  
  protected VAccountVisibility(Parcel paramParcel) {
    this.name = paramParcel.readString();
    this.type = paramParcel.readString();
    this.userId = paramParcel.readInt();
    int i = paramParcel.readInt();
    this.visibility = new HashMap<String, Integer>(i);
    for (byte b = 0; b < i; b++) {
      String str = paramParcel.readString();
      Integer integer = (Integer)paramParcel.readValue(Integer.class.getClassLoader());
      this.visibility.put(str, integer);
    } 
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.name);
    paramParcel.writeString(this.type);
    paramParcel.writeInt(this.userId);
    paramParcel.writeInt(this.visibility.size());
    for (Map.Entry<String, Integer> entry : this.visibility.entrySet()) {
      paramParcel.writeString((String)entry.getKey());
      paramParcel.writeValue(entry.getValue());
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\accounts\VAccountVisibility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */