package com.lody.virtual.remote;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.lody.virtual.helper.compat.PendingIntentCompat;

public class IntentSenderData implements Parcelable {
  public static final Parcelable.Creator<IntentSenderData> CREATOR = new Parcelable.Creator<IntentSenderData>() {
      public IntentSenderData createFromParcel(Parcel param1Parcel) {
        return new IntentSenderData(param1Parcel);
      }
      
      public IntentSenderData[] newArray(int param1Int) {
        return new IntentSenderData[param1Int];
      }
    };
  
  public String targetPkg;
  
  public IBinder token;
  
  public int type;
  
  public int userId;
  
  protected IntentSenderData(Parcel paramParcel) {
    this.targetPkg = paramParcel.readString();
    this.token = paramParcel.readStrongBinder();
    this.type = paramParcel.readInt();
    this.userId = paramParcel.readInt();
  }
  
  public IntentSenderData(String paramString, IBinder paramIBinder, int paramInt1, int paramInt2) {
    this.targetPkg = paramString;
    this.token = paramIBinder;
    this.type = paramInt1;
    this.userId = paramInt2;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public PendingIntent getPendingIntent() {
    return PendingIntentCompat.readPendingIntent(this.token);
  }
  
  public void update(IntentSenderData paramIntentSenderData) {
    this.targetPkg = paramIntentSenderData.targetPkg;
    this.type = paramIntentSenderData.type;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeString(this.targetPkg);
    paramParcel.writeStrongBinder(this.token);
    paramParcel.writeInt(this.type);
    paramParcel.writeInt(this.userId);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\IntentSenderData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */