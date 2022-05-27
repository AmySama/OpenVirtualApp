package io.virtualapp.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class PackageUtils {
  public static PackageInfo getApkPackageInfo(PackageManager paramPackageManager, String paramString, int paramInt) {
    try {
      return paramPackageManager.getPackageArchiveInfo(paramString, paramInt);
    } finally {
      paramPackageManager = null;
    } 
  }
  
  public static int getApkVersion(Context paramContext, String paramString) {
    PackageInfo packageInfo = getApkPackageInfo(paramContext.getPackageManager(), paramString, 0);
    return (packageInfo == null) ? -1 : packageInfo.versionCode;
  }
  
  public static int getApkVersion(PackageManager paramPackageManager, String paramString) {
    PackageInfo packageInfo = getApkPackageInfo(paramPackageManager, paramString, 0);
    return (packageInfo == null) ? -1 : packageInfo.versionCode;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\PackageUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */