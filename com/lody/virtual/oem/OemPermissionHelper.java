package com.lody.virtual.oem;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import com.lody.virtual.helper.compat.BuildCompat;
import java.util.Arrays;
import java.util.List;

public class OemPermissionHelper {
  private static List<ComponentName> EMUI_AUTO_START_COMPONENTS = Arrays.asList(new ComponentName[] { new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"), new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.bootstart.BootStartActivity"), new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"), new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupAwakedAppListActivity") });
  
  private static List<ComponentName> FLYME_AUTO_START_COMPONENTS = Arrays.asList(new ComponentName[] { new ComponentName("com.meizu.safe", "com.meizu.safe.permission.SmartBGActivity"), new ComponentName("com.meizu.safe", "com.meizu.safe.security.HomeActivity") });
  
  private static List<ComponentName> VIVO_AUTO_START_COMPONENTS = Arrays.asList(new ComponentName[] { new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"), new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.PurviewTabActivity"), new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity"), new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.PurviewActivity") });
  
  public static Intent getPermissionActivityIntent(Context paramContext) {
    Intent intent;
    BuildCompat.ROMType rOMType = BuildCompat.getROMType();
    switch (rOMType) {
      default:
        return null;
      case null:
        intent = new Intent();
        intent.addFlags(268435456);
        intent.setClassName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        if (verifyIntent(paramContext, intent))
          return intent; 
      case null:
        for (ComponentName componentName : VIVO_AUTO_START_COMPONENTS) {
          intent = new Intent();
          intent.addFlags(268435456);
          intent.setComponent(componentName);
          if (verifyIntent(paramContext, intent))
            return intent; 
        } 
      case null:
        intent = new Intent();
        intent.addFlags(268435456);
        intent.setClassName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity");
        if (verifyIntent(paramContext, intent))
          return intent; 
      case null:
        intent = new Intent();
        intent.addFlags(268435456);
        intent.setClassName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity");
        if (verifyIntent(paramContext, intent))
          return intent; 
      case null:
        for (ComponentName componentName : FLYME_AUTO_START_COMPONENTS) {
          intent = new Intent();
          intent.addFlags(268435456);
          intent.setComponent(componentName);
          if (verifyIntent(paramContext, intent))
            return intent; 
        } 
      case null:
        intent = new Intent();
        intent.addFlags(268435456);
        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
        if (verifyIntent(paramContext, intent))
          return intent; 
      case null:
        break;
    } 
    for (ComponentName componentName : EMUI_AUTO_START_COMPONENTS) {
      Intent intent1 = new Intent();
      intent1.addFlags(268435456);
      intent1.setComponent(componentName);
      if (verifyIntent(paramContext, intent1))
        return intent1; 
    } 
  }
  
  private static boolean verifyIntent(Context paramContext, Intent paramIntent) {
    PackageManager packageManager = paramContext.getPackageManager();
    boolean bool1 = false;
    ResolveInfo resolveInfo = packageManager.resolveActivity(paramIntent, 0);
    boolean bool2 = bool1;
    if (resolveInfo != null) {
      bool2 = bool1;
      if (resolveInfo.activityInfo != null) {
        bool2 = bool1;
        if (resolveInfo.activityInfo.exported)
          bool2 = true; 
      } 
    } 
    return bool2;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\oem\OemPermissionHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */