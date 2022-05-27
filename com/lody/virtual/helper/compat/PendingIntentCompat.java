package com.lody.virtual.helper.compat;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;

public class PendingIntentCompat {
  public static PendingIntent readPendingIntent(IBinder paramIBinder) {
    Parcel parcel = Parcel.obtain();
    parcel.writeStrongBinder(paramIBinder);
    parcel.setDataPosition(0);
    try {
      return PendingIntent.readPendingIntentOrNullFromParcel(parcel);
    } finally {
      parcel.recycle();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\PendingIntentCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */