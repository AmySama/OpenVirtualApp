package com.lody.virtual.remote.vloc;

import android.os.Parcel;
import android.os.Parcelable;

public class VCell implements Parcelable {
  public static final Parcelable.Creator<VCell> CREATOR = new Parcelable.Creator<VCell>() {
      public VCell createFromParcel(Parcel param1Parcel) {
        return new VCell(param1Parcel);
      }
      
      public VCell[] newArray(int param1Int) {
        return new VCell[param1Int];
      }
    };
  
  public int baseStationId;
  
  public int cid;
  
  public int lac;
  
  public int mcc;
  
  public int mnc;
  
  public int networkId;
  
  public int psc;
  
  public int systemId;
  
  public int type;
  
  public VCell() {}
  
  public VCell(Parcel paramParcel) {
    this.type = paramParcel.readInt();
    this.mcc = paramParcel.readInt();
    this.mnc = paramParcel.readInt();
    this.psc = paramParcel.readInt();
    this.lac = paramParcel.readInt();
    this.cid = paramParcel.readInt();
    this.baseStationId = paramParcel.readInt();
    this.systemId = paramParcel.readInt();
    this.networkId = paramParcel.readInt();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.type);
    paramParcel.writeInt(this.mcc);
    paramParcel.writeInt(this.mnc);
    paramParcel.writeInt(this.psc);
    paramParcel.writeInt(this.lac);
    paramParcel.writeInt(this.cid);
    paramParcel.writeInt(this.baseStationId);
    paramParcel.writeInt(this.systemId);
    paramParcel.writeInt(this.networkId);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\vloc\VCell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */