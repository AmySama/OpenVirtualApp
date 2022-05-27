package com.lody.virtual.remote;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public class ClientConfig implements Parcelable {
  public static final Parcelable.Creator<ClientConfig> CREATOR = new Parcelable.Creator<ClientConfig>() {
      public ClientConfig createFromParcel(Parcel param1Parcel) {
        return new ClientConfig(param1Parcel);
      }
      
      public ClientConfig[] newArray(int param1Int) {
        return new ClientConfig[param1Int];
      }
    };
  
  public boolean isExt;
  
  public String packageName;
  
  public String processName;
  
  public IBinder token;
  
  public int vpid;
  
  public int vuid;
  
  public ClientConfig() {}
  
  protected ClientConfig(Parcel paramParcel) {
    boolean bool;
    if (paramParcel.readByte() != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.isExt = bool;
    this.vpid = paramParcel.readInt();
    this.vuid = paramParcel.readInt();
    this.processName = paramParcel.readString();
    this.packageName = paramParcel.readString();
    this.token = paramParcel.readStrongBinder();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeByte(this.isExt);
    paramParcel.writeInt(this.vpid);
    paramParcel.writeInt(this.vuid);
    paramParcel.writeString(this.processName);
    paramParcel.writeString(this.packageName);
    paramParcel.writeStrongBinder(this.token);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\ClientConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */