package com.lody.virtual.server.extension;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import com.lody.virtual.IExtHelperInterface;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.ProviderCall;
import com.lody.virtual.client.stub.StubManifest;
import com.lody.virtual.helper.IPCHelper;
import com.lody.virtual.helper.compat.BundleCompat;
import com.lody.virtual.helper.utils.VLog;
import java.util.Collections;
import java.util.List;

public class VExtPackageAccessor {
  private static final String TAG = "VExtPackageAccessor";
  
  private static IPCHelper<IExtHelperInterface> sHelper = new IPCHelper<IExtHelperInterface>() {
      public IExtHelperInterface getInterface() {
        Context context = VirtualCore.get().getContext();
        for (byte b = 0; b < 3; b++) {
          Bundle bundle = (new ProviderCall.Builder(context, VExtPackageAccessor.getAuthority())).methodName("connect").callSafely();
          if (bundle != null)
            return IExtHelperInterface.Stub.asInterface(BundleCompat.getBinder(bundle, "_VA_|_binder_")); 
          VExtPackageAccessor.tryPullUpExtProcess();
          SystemClock.sleep(200L);
        } 
        return null;
      }
    };
  
  public static boolean callHelper() {
    try {
      ProviderCall.Builder builder = new ProviderCall.Builder();
      this(VirtualCore.get().getContext(), getAuthority());
      builder.methodName("@").retry(0).call();
      try {
        builder = new ProviderCall.Builder();
        this(VirtualCore.get().getContext(), "com.smallyin.moreopen.no.virtual_stub_ext_0");
        builder.methodName("@").retry(0).call();
        return true;
      } catch (IllegalAccessException illegalAccessException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("callHelper virtual_stub_ext_0 failed...:");
        stringBuilder.append(illegalAccessException);
        Log.e("VExtPackageAccessor", stringBuilder.toString());
        return false;
      } 
    } catch (IllegalAccessException illegalAccessException) {
      VLog.e("VExtPackageAccessor", "callHelper virtual.service.ext_helper failed...");
      return false;
    } 
  }
  
  public static void cleanPackageData(final int[] userIds, final String packageName) {
    if (!VirtualCore.get().isExtPackageInstalled())
      return; 
    sHelper.callVoid(new IPCHelper.CallableVoid<IExtHelperInterface>() {
          public void call(IExtHelperInterface param1IExtHelperInterface) throws RemoteException {
            param1IExtHelperInterface.cleanPackageData(userIds, packageName);
          }
        });
  }
  
  public static void forceStop(final int[] pids) {
    sHelper.callVoid(new IPCHelper.CallableVoid<IExtHelperInterface>() {
          public void call(IExtHelperInterface param1IExtHelperInterface) throws RemoteException {
            param1IExtHelperInterface.forceStop(pids);
          }
        });
  }
  
  private static String getAuthority() {
    return VirtualCore.getConfig().getExtPackageHelperAuthority();
  }
  
  private static Intent getLaunchIntentForPackage(PackageManager paramPackageManager, String paramString) {
    Intent intent2 = new Intent("android.intent.action.LAUNCH_HELPER");
    intent2.setPackage(paramString);
    List list = paramPackageManager.queryIntentActivities(intent2, 0);
    if (list.size() <= 0)
      return null; 
    Intent intent1 = new Intent(intent2);
    intent1.setFlags(268435456);
    intent1.setFlags(65536);
    intent1.setClassName(((ResolveInfo)list.get(0)).activityInfo.packageName, ((ResolveInfo)list.get(0)).activityInfo.name);
    return intent1;
  }
  
  public static List<ActivityManager.RecentTaskInfo> getRecentTasks(final int maxNum, final int flags) {
    if (!VirtualCore.get().isExtPackageInstalled())
      return Collections.emptyList(); 
    List<?> list1 = (List)sHelper.call(new IPCHelper.Callable<IExtHelperInterface, List<ActivityManager.RecentTaskInfo>>() {
          public List<ActivityManager.RecentTaskInfo> call(IExtHelperInterface param1IExtHelperInterface) throws RemoteException {
            return param1IExtHelperInterface.getRecentTasks(maxNum, flags);
          }
        });
    List<?> list2 = list1;
    if (list1 == null)
      list2 = Collections.emptyList(); 
    return (List)list2;
  }
  
  public static List<ActivityManager.RunningAppProcessInfo> getRunningAppProcesses() {
    if (!VirtualCore.get().isExtPackageInstalled())
      return Collections.emptyList(); 
    List<?> list1 = (List)sHelper.call(new IPCHelper.Callable<IExtHelperInterface, List<ActivityManager.RunningAppProcessInfo>>() {
          public List<ActivityManager.RunningAppProcessInfo> call(IExtHelperInterface param1IExtHelperInterface) throws RemoteException {
            return param1IExtHelperInterface.getRunningAppProcesses();
          }
        });
    List<?> list2 = list1;
    if (list1 == null)
      list2 = Collections.emptyList(); 
    return (List)list2;
  }
  
  public static List<ActivityManager.RunningTaskInfo> getRunningTasks(final int maxNum) {
    if (!VirtualCore.get().isExtPackageInstalled())
      return Collections.emptyList(); 
    List<?> list1 = (List)sHelper.call(new IPCHelper.Callable<IExtHelperInterface, List<ActivityManager.RunningTaskInfo>>() {
          public List<ActivityManager.RunningTaskInfo> call(IExtHelperInterface param1IExtHelperInterface) throws RemoteException {
            return param1IExtHelperInterface.getRunningTasks(maxNum);
          }
        });
    List<?> list2 = list1;
    if (list1 == null)
      list2 = Collections.emptyList(); 
    return (List)list2;
  }
  
  public static boolean hasExtPackageBootPermission() {
    if (!VirtualCore.get().isExtPackageInstalled())
      return false; 
    if (callHelper())
      return true; 
    tryPullUpExtProcess();
    for (byte b = 0; b < 5; b++) {
      if (callHelper())
        return true; 
      SystemClock.sleep(200L);
    } 
    return false;
  }
  
  public static boolean isExternalStorageManager() {
    return !VirtualCore.get().isExtPackageInstalled() ? true : sHelper.callBoolean(new IPCHelper.Callable<IExtHelperInterface, Boolean>() {
          public Boolean call(IExtHelperInterface param1IExtHelperInterface) throws RemoteException {
            return Boolean.valueOf(param1IExtHelperInterface.isExternalStorageManager());
          }
        });
  }
  
  public static void syncPackages() {
    if (!VirtualCore.get().isExtPackageInstalled())
      return; 
    sHelper.callVoid(new IPCHelper.CallableVoid<IExtHelperInterface>() {
          public void call(IExtHelperInterface param1IExtHelperInterface) throws RemoteException {
            param1IExtHelperInterface.syncPackages();
          }
        });
  }
  
  private static void tryPullUpExtProcess() {
    Context context = VirtualCore.get().getContext();
    Intent intent = getLaunchIntentForPackage(context.getPackageManager(), StubManifest.EXT_PACKAGE_NAME);
    if (intent != null) {
      intent.addFlags(65536);
      intent.addFlags(268435456);
      context.startActivity(intent);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\extension\VExtPackageAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */