package com.lody.virtual.helper.compat;

import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import java.util.ArrayList;
import mirror.android.app.IApplicationThread;
import mirror.android.app.IApplicationThreadICSMR1;
import mirror.android.app.IApplicationThreadKitkat;
import mirror.android.app.IApplicationThreadOreo;
import mirror.android.app.ServiceStartArgs;
import mirror.android.content.res.CompatibilityInfo;

public class IApplicationThreadCompat {
  public static void scheduleBindService(IInterface paramIInterface, IBinder paramIBinder, Intent paramIntent, boolean paramBoolean) throws RemoteException {
    if (Build.VERSION.SDK_INT >= 19) {
      IApplicationThreadKitkat.scheduleBindService.call(paramIInterface, new Object[] { paramIBinder, paramIntent, Boolean.valueOf(paramBoolean), Integer.valueOf(0) });
    } else {
      IApplicationThread.scheduleBindService.call(paramIInterface, new Object[] { paramIBinder, paramIntent, Boolean.valueOf(paramBoolean) });
    } 
  }
  
  public static void scheduleCreateService(IInterface paramIInterface, IBinder paramIBinder, ServiceInfo paramServiceInfo) throws RemoteException {
    if (Build.VERSION.SDK_INT >= 19) {
      IApplicationThreadKitkat.scheduleCreateService.call(paramIInterface, new Object[] { paramIBinder, paramServiceInfo, CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO.get(), Integer.valueOf(0) });
    } else if (Build.VERSION.SDK_INT >= 15) {
      IApplicationThreadICSMR1.scheduleCreateService.call(paramIInterface, new Object[] { paramIBinder, paramServiceInfo, CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO.get() });
    } else {
      IApplicationThread.scheduleCreateService.call(paramIInterface, new Object[] { paramIBinder, paramServiceInfo });
    } 
  }
  
  public static void scheduleServiceArgs(IInterface paramIInterface, IBinder paramIBinder, int paramInt, Intent paramIntent) throws RemoteException {
    boolean bool = BuildCompat.isOreo();
    Boolean bool1 = Boolean.valueOf(false);
    Integer integer = Integer.valueOf(0);
    if (bool) {
      ArrayList<Object> arrayList = new ArrayList(1);
      arrayList.add(ServiceStartArgs.ctor.newInstance(new Object[] { bool1, Integer.valueOf(paramInt), integer, paramIntent }));
      IApplicationThreadOreo.scheduleServiceArgs.call(paramIInterface, new Object[] { paramIBinder, ParceledListSliceCompat.create(arrayList) });
    } else if (Build.VERSION.SDK_INT >= 15) {
      IApplicationThreadICSMR1.scheduleServiceArgs.call(paramIInterface, new Object[] { paramIBinder, bool1, Integer.valueOf(paramInt), integer, paramIntent });
    } else {
      IApplicationThread.scheduleServiceArgs.call(paramIInterface, new Object[] { paramIBinder, Integer.valueOf(paramInt), integer, paramIntent });
    } 
  }
  
  public static void scheduleStopService(IInterface paramIInterface, IBinder paramIBinder) throws RemoteException {
    IApplicationThread.scheduleStopService.call(paramIInterface, new Object[] { paramIBinder });
  }
  
  public static void scheduleUnbindService(IInterface paramIInterface, IBinder paramIBinder, Intent paramIntent) throws RemoteException {
    IApplicationThread.scheduleUnbindService.call(paramIInterface, new Object[] { paramIBinder, paramIntent });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\compat\IApplicationThreadCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */