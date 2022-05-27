package com.lody.virtual.server.vs;

import android.os.Parcel;
import android.os.Parcelable;

public class VSConfig implements Parcelable {
  public static final Parcelable.Creator<VSConfig> CREATOR = new Parcelable.Creator<VSConfig>() {
      public VSConfig createFromParcel(Parcel param1Parcel) {
        return new VSConfig(param1Parcel);
      }
      
      public VSConfig[] newArray(int param1Int) {
        return new VSConfig[param1Int];
      }
    };
  
  public boolean enable;
  
  public String vsPath;
  
  public VSConfig() {}
  
  protected VSConfig(Parcel paramParcel) {
    boolean bool;
    if (paramParcel.readByte() != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.enable = bool;
    this.vsPath = paramParcel.readString();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeByte(this.enable);
    paramParcel.writeString(this.vsPath);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\vs\VSConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */