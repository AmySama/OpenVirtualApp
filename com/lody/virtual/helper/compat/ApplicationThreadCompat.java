package com.lody.virtual.helper.compat;

import android.os.IBinder;
import android.os.IInterface;
import mirror.android.app.ApplicationThreadNative;
import mirror.android.app.IApplicationThreadOreo;

public class ApplicationThreadCompat {
  public static IInterface asInterface(IBinder paramIBinder) {
    return BuildCompat.isOreo() ? (IInterface)IApplicationThreadOreo.Stub.asInterface.call(new Object[] { paramIBinder }) : (IInterface)ApplicationThreadNative.asInterface.call(new Object[] { paramIBinder });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\ApplicationThreadCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */