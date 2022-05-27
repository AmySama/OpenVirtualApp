package com.lody.virtual.helper.compat;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import mirror.android.os.BaseBundle;
import mirror.android.os.BundleICS;

public class BundleCompat {
  public static void clearParcelledData(Bundle paramBundle) {
    Parcel parcel = Parcel.obtain();
    parcel.writeInt(0);
    parcel.setDataPosition(0);
    if (BaseBundle.TYPE != null) {
      Parcel parcel1 = (Parcel)BaseBundle.mParcelledData.get(paramBundle);
      if (parcel1 != null)
        parcel1.recycle(); 
      BaseBundle.mParcelledData.set(paramBundle, parcel);
    } else if (BundleICS.TYPE != null) {
      Parcel parcel1 = (Parcel)BundleICS.mParcelledData.get(paramBundle);
      if (parcel1 != null)
        parcel1.recycle(); 
      BundleICS.mParcelledData.set(paramBundle, parcel);
    } 
  }
  
  public static IBinder getBinder(Intent paramIntent, String paramString) {
    Bundle bundle = paramIntent.getBundleExtra(paramString);
    return (bundle != null) ? getBinder(bundle, "binder") : null;
  }
  
  public static IBinder getBinder(Bundle paramBundle, String paramString) {
    return paramBundle.getBinder(paramString);
  }
  
  public static void putBinder(Intent paramIntent, String paramString, IBinder paramIBinder) {
    Bundle bundle = new Bundle();
    putBinder(bundle, "binder", paramIBinder);
    paramIntent.putExtra(paramString, bundle);
  }
  
  public static void putBinder(Bundle paramBundle, String paramString, IBinder paramIBinder) {
    paramBundle.putBinder(paramString, paramIBinder);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\BundleCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */