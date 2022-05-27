package com.swift.sandhook.xposedcompat.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Process;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public class ProcessUtils {
  private static volatile String processName;
  
  private static String doGetProcessName(Context paramContext) {
    List list = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses();
    if (list == null)
      return null; 
    for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : list) {
      if (runningAppProcessInfo.pid == Process.myPid() && runningAppProcessInfo.processName != null)
        return runningAppProcessInfo.processName; 
    } 
    return paramContext.getPackageName();
  }
  
  public static List<ResolveInfo> findActivitiesForPackage(Context paramContext, String paramString) {
    PackageManager packageManager = paramContext.getPackageManager();
    Intent intent = new Intent("android.intent.action.MAIN", null);
    intent.addCategory("android.intent.category.LAUNCHER");
    intent.setPackage(paramString);
    List<ResolveInfo> list = packageManager.queryIntentActivities(intent, 0);
    if (list == null)
      list = new ArrayList(); 
    return list;
  }
  
  public static String getProcessName(Context paramContext) {
    if (!TextUtils.isEmpty(processName))
      return processName; 
    processName = doGetProcessName(paramContext);
    return processName;
  }
  
  public static boolean isMainProcess(Context paramContext) {
    String str2 = getProcessName(paramContext);
    String str1 = paramContext.getPackageName();
    return !(!TextUtils.isEmpty(str2) && !TextUtils.equals(str2, str1));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\xposedcompa\\utils\ProcessUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */