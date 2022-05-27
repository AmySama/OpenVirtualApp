package com.lody.virtual.remote.vloc;

import android.os.Parcel;
import android.os.Parcelable;

public class VWifi implements Parcelable {
  public static final Parcelable.Creator<VWifi> CREATOR = new Parcelable.Creator<VWifi>() {
      public VWifi createFromParcel(Parcel param1Parcel) {
        return new VWifi(param1Parcel);
      }
      
      public VWifi[] newArray(int param1Int) {
        return new VWifi[param1Int];
      }
    };
  
  public String bssid;
  
  public String capabilities;
  
  public int frequency;
  
  public int level;
  
  public String ssid;
  
  public long timestamp;
  
  public VWifi() {}
  
  public VWifi(Parcel paramParcel) {
    this.ssid = paramParcel.readString();
    this.bssid = paramParcel.readString();
    this.capabilities = paramParcel.readString();
    this.level = paramParcel.readInt();
    this.frequency = paramParcel.readInt();
    this.timestamp = paramParcel.readLong();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.ssid);
    paramParcel.writeString(this.bssid);
    paramParcel.writeString(this.capabilities);
    paramParcel.writeInt(this.level);
    paramParcel.writeInt(this.frequency);
    paramParcel.writeLong(this.timestamp);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\vloc\VWifi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */