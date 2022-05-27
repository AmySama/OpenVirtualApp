package com.lody.virtual.remote;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import mirror.android.content.BroadcastReceiver;

public class PendingResultData implements Parcelable {
  public static final Parcelable.Creator<PendingResultData> CREATOR = new Parcelable.Creator<PendingResultData>() {
      public PendingResultData createFromParcel(Parcel param1Parcel) {
        return new PendingResultData(param1Parcel);
      }
      
      public PendingResultData[] newArray(int param1Int) {
        return new PendingResultData[param1Int];
      }
    };
  
  public boolean mAbortBroadcast;
  
  public boolean mFinished;
  
  public int mFlags;
  
  public boolean mInitialStickyHint;
  
  public boolean mOrderedHint;
  
  public int mResultCode;
  
  public String mResultData;
  
  public Bundle mResultExtras;
  
  public int mSendingUser;
  
  public IBinder mToken;
  
  public int mType;
  
  public PendingResultData(BroadcastReceiver.PendingResult paramPendingResult) {
    if (BroadcastReceiver.PendingResultMNC.ctor != null) {
      this.mType = BroadcastReceiver.PendingResultMNC.mType.get(paramPendingResult);
      this.mOrderedHint = BroadcastReceiver.PendingResultMNC.mOrderedHint.get(paramPendingResult);
      this.mInitialStickyHint = BroadcastReceiver.PendingResultMNC.mInitialStickyHint.get(paramPendingResult);
      this.mToken = (IBinder)BroadcastReceiver.PendingResultMNC.mToken.get(paramPendingResult);
      this.mSendingUser = BroadcastReceiver.PendingResultMNC.mSendingUser.get(paramPendingResult);
      this.mFlags = BroadcastReceiver.PendingResultMNC.mFlags.get(paramPendingResult);
      this.mResultCode = BroadcastReceiver.PendingResultMNC.mResultCode.get(paramPendingResult);
      this.mResultData = (String)BroadcastReceiver.PendingResultMNC.mResultData.get(paramPendingResult);
      this.mResultExtras = (Bundle)BroadcastReceiver.PendingResultMNC.mResultExtras.get(paramPendingResult);
      this.mAbortBroadcast = BroadcastReceiver.PendingResultMNC.mAbortBroadcast.get(paramPendingResult);
      this.mFinished = BroadcastReceiver.PendingResultMNC.mFinished.get(paramPendingResult);
    } else if (BroadcastReceiver.PendingResultJBMR1.ctor != null) {
      this.mType = BroadcastReceiver.PendingResultJBMR1.mType.get(paramPendingResult);
      this.mOrderedHint = BroadcastReceiver.PendingResultJBMR1.mOrderedHint.get(paramPendingResult);
      this.mInitialStickyHint = BroadcastReceiver.PendingResultJBMR1.mInitialStickyHint.get(paramPendingResult);
      this.mToken = (IBinder)BroadcastReceiver.PendingResultJBMR1.mToken.get(paramPendingResult);
      this.mSendingUser = BroadcastReceiver.PendingResultJBMR1.mSendingUser.get(paramPendingResult);
      this.mResultCode = BroadcastReceiver.PendingResultJBMR1.mResultCode.get(paramPendingResult);
      this.mResultData = (String)BroadcastReceiver.PendingResultJBMR1.mResultData.get(paramPendingResult);
      this.mResultExtras = (Bundle)BroadcastReceiver.PendingResultJBMR1.mResultExtras.get(paramPendingResult);
      this.mAbortBroadcast = BroadcastReceiver.PendingResultJBMR1.mAbortBroadcast.get(paramPendingResult);
      this.mFinished = BroadcastReceiver.PendingResultJBMR1.mFinished.get(paramPendingResult);
    } else {
      this.mType = BroadcastReceiver.PendingResult.mType.get(paramPendingResult);
      this.mOrderedHint = BroadcastReceiver.PendingResult.mOrderedHint.get(paramPendingResult);
      this.mInitialStickyHint = BroadcastReceiver.PendingResult.mInitialStickyHint.get(paramPendingResult);
      this.mToken = (IBinder)BroadcastReceiver.PendingResult.mToken.get(paramPendingResult);
      this.mResultCode = BroadcastReceiver.PendingResult.mResultCode.get(paramPendingResult);
      this.mResultData = (String)BroadcastReceiver.PendingResult.mResultData.get(paramPendingResult);
      this.mResultExtras = (Bundle)BroadcastReceiver.PendingResult.mResultExtras.get(paramPendingResult);
      this.mAbortBroadcast = BroadcastReceiver.PendingResult.mAbortBroadcast.get(paramPendingResult);
      this.mFinished = BroadcastReceiver.PendingResult.mFinished.get(paramPendingResult);
    } 
  }
  
  protected PendingResultData(Parcel paramParcel) {
    boolean bool2;
    this.mType = paramParcel.readInt();
    byte b = paramParcel.readByte();
    boolean bool1 = true;
    if (b != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    this.mOrderedHint = bool2;
    if (paramParcel.readByte() != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    this.mInitialStickyHint = bool2;
    this.mToken = paramParcel.readStrongBinder();
    this.mSendingUser = paramParcel.readInt();
    this.mFlags = paramParcel.readInt();
    this.mResultCode = paramParcel.readInt();
    this.mResultData = paramParcel.readString();
    this.mResultExtras = paramParcel.readBundle();
    if (paramParcel.readByte() != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    this.mAbortBroadcast = bool2;
    if (paramParcel.readByte() != 0) {
      bool2 = bool1;
    } else {
      bool2 = false;
    } 
    this.mFinished = bool2;
  }
  
  public BroadcastReceiver.PendingResult build() {
    return (BroadcastReceiver.PendingResultMNC.ctor != null) ? (BroadcastReceiver.PendingResult)BroadcastReceiver.PendingResultMNC.ctor.newInstance(new Object[] { Integer.valueOf(this.mResultCode), this.mResultData, this.mResultExtras, Integer.valueOf(this.mType), Boolean.valueOf(this.mOrderedHint), Boolean.valueOf(this.mInitialStickyHint), this.mToken, Integer.valueOf(this.mSendingUser), Integer.valueOf(this.mFlags) }) : ((BroadcastReceiver.PendingResultJBMR1.ctor != null) ? (BroadcastReceiver.PendingResult)BroadcastReceiver.PendingResultJBMR1.ctor.newInstance(new Object[] { Integer.valueOf(this.mResultCode), this.mResultData, this.mResultExtras, Integer.valueOf(this.mType), Boolean.valueOf(this.mOrderedHint), Boolean.valueOf(this.mInitialStickyHint), this.mToken, Integer.valueOf(this.mSendingUser) }) : (BroadcastReceiver.PendingResult)BroadcastReceiver.PendingResult.ctor.newInstance(new Object[] { Integer.valueOf(this.mResultCode), this.mResultData, this.mResultExtras, Integer.valueOf(this.mType), Boolean.valueOf(this.mOrderedHint), Boolean.valueOf(this.mInitialStickyHint), this.mToken }));
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void finish() {
    try {
      build().finish();
    } finally {
      Exception exception = null;
    } 
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.mType);
    paramParcel.writeByte(this.mOrderedHint);
    paramParcel.writeByte(this.mInitialStickyHint);
    paramParcel.writeStrongBinder(this.mToken);
    paramParcel.writeInt(this.mSendingUser);
    paramParcel.writeInt(this.mFlags);
    paramParcel.writeInt(this.mResultCode);
    paramParcel.writeString(this.mResultData);
    paramParcel.writeBundle(this.mResultExtras);
    paramParcel.writeByte(this.mAbortBroadcast);
    paramParcel.writeByte(this.mFinished);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\PendingResultData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */