package com.lody.virtual.client.env;

import android.content.pm.PackageManager;
import com.lody.virtual.client.core.SettingConfig;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.utils.ComponentUtils;
import java.util.HashMap;
import java.util.Map;

public class LocalPackageCache {
  private static final Map<String, Boolean> sSystemPackages = new HashMap<String, Boolean>();
  
  public static void init() {
    String[] arrayOfString = VirtualCore.get().getHostPackageManager().getPackagesForUid(1000);
    if (arrayOfString != null) {
      int i = arrayOfString.length;
      for (byte b = 0; b < i; b++) {
        String str = arrayOfString[b];
        sSystemPackages.put(str, Boolean.valueOf(VirtualCore.get().isAppInstalled(str) ^ true));
      } 
    } 
  }
  
  public static boolean isOutsideVisiblePackage(String paramString) {
    boolean bool = false;
    if (paramString == null)
      return false; 
    SettingConfig settingConfig = VirtualCore.getConfig();
    if (paramString.equals(settingConfig.getMainPackageName()) || paramString.equals(settingConfig.getExtPackageName()) || settingConfig.isOutsidePackage(paramString) || isSystemPackage(paramString))
      bool = true; 
    return bool;
  }
  
  public static boolean isSystemPackage(String paramString) {
    synchronized (sSystemPackages) {
      Boolean bool1 = sSystemPackages.get(paramString);
      Boolean bool2 = bool1;
      if (bool1 == null) {
        try {
          if (VirtualCore.get().isAppInstalled(paramString)) {
            bool2 = Boolean.valueOf(false);
          } else {
            bool2 = Boolean.valueOf(ComponentUtils.isSystemApp(VirtualCore.get().getHostPackageManager().getApplicationInfo(paramString, 0)));
          } 
        } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
          bool2 = Boolean.valueOf(false);
        } 
        sSystemPackages.put(paramString, bool2);
      } 
      return bool2.booleanValue();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\env\LocalPackageCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */