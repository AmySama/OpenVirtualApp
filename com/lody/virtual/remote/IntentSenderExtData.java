package com.lody.virtual.remote;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public class IntentSenderExtData implements Parcelable {
  public static final Parcelable.Creator<IntentSenderExtData> CREATOR;
  
  public static final IntentSenderExtData EMPTY = new IntentSenderExtData(null, null, null, null, 0, null, 0, 0);
  
  public Intent fillIn;
  
  public int flagsMask;
  
  public int flagsValues;
  
  public Bundle options;
  
  public int requestCode;
  
  public IBinder resultTo;
  
  public String resultWho;
  
  public IBinder sender;
  
  static {
    CREATOR = new Parcelable.Creator<IntentSenderExtData>() {
        public IntentSenderExtData createFromParcel(Parcel param1Parcel) {
          return new IntentSenderExtData(param1Parcel);
        }
        
        public IntentSenderExtData[] newArray(int param1Int) {
          return new IntentSenderExtData[param1Int];
        }
      };
  }
  
  public IntentSenderExtData(IBinder paramIBinder1, Intent paramIntent, IBinder paramIBinder2, String paramString, int paramInt1, Bundle paramBundle, int paramInt2, int paramInt3) {
    this.sender = paramIBinder1;
    this.fillIn = paramIntent;
    this.resultTo = paramIBinder2;
    this.resultWho = paramString;
    this.requestCode = paramInt1;
    this.options = paramBundle;
    this.flagsMask = paramInt2;
    this.flagsValues = paramInt3;
  }
  
  protected IntentSenderExtData(Parcel paramParcel) {
    this.sender = paramParcel.readStrongBinder();
    this.fillIn = (Intent)paramParcel.readParcelable(Intent.class.getClassLoader());
    this.resultTo = paramParcel.readStrongBinder();
    this.resultWho = paramParcel.readString();
    this.requestCode = paramParcel.readInt();
    this.options = paramParcel.readBundle();
    this.flagsMask = paramParcel.readInt();
    this.flagsValues = paramParcel.readInt();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeStrongBinder(this.sender);
    paramParcel.writeParcelable((Parcelable)this.fillIn, paramInt);
    paramParcel.writeStrongBinder(this.resultTo);
    paramParcel.writeString(this.resultWho);
    paramParcel.writeInt(this.requestCode);
    paramParcel.writeBundle(this.options);
    paramParcel.writeInt(this.flagsMask);
    paramParcel.writeInt(this.flagsValues);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\IntentSenderExtData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */