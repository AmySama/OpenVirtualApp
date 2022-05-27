package com.lody.virtual.server.pm;

import android.util.ArrayMap;
import com.lody.virtual.server.pm.parser.PackageParserEx;
import com.lody.virtual.server.pm.parser.VPackage;

public class PackageCacheManager {
  static final ArrayMap<String, VPackage> PACKAGE_CACHE = new ArrayMap();
  
  public static boolean contain(String paramString) {
    synchronized (PACKAGE_CACHE) {
      return PACKAGE_CACHE.containsKey(paramString);
    } 
  }
  
  public static VPackage get(String paramString) {
    synchronized (PACKAGE_CACHE) {
      return (VPackage)PACKAGE_CACHE.get(paramString);
    } 
  }
  
  public static PackageSetting getSetting(String paramString) {
    synchronized (PACKAGE_CACHE) {
      VPackage vPackage = (VPackage)PACKAGE_CACHE.get(paramString);
      if (vPackage != null)
        return (PackageSetting)vPackage.mExtras; 
      return null;
    } 
  }
  
  public static PackageSetting getSettingLocked(String paramString) {
    VPackage vPackage = (VPackage)PACKAGE_CACHE.get(paramString);
    return (vPackage != null) ? (PackageSetting)vPackage.mExtras : null;
  }
  
  public static void put(VPackage paramVPackage, PackageSetting paramPackageSetting) {
    synchronized (PACKAGE_CACHE) {
      VPackage vPackage = (VPackage)PACKAGE_CACHE.remove(paramVPackage.packageName);
      if (vPackage != null)
        VPackageManagerService.get().deletePackageLocked(vPackage); 
      PackageParserEx.initApplicationInfoBase(paramPackageSetting, paramVPackage);
      PACKAGE_CACHE.put(paramVPackage.packageName, paramVPackage);
      paramVPackage.mExtras = paramPackageSetting;
      VPackageManagerService.get().analyzePackageLocked(paramVPackage);
      return;
    } 
  }
  
  public static VPackage remove(String paramString) {
    synchronized (PACKAGE_CACHE) {
      VPackage vPackage = (VPackage)PACKAGE_CACHE.remove(paramString);
      if (vPackage != null)
        VPackageManagerService.get().deletePackageLocked(vPackage); 
      return vPackage;
    } 
  }
  
  public static int size() {
    synchronized (PACKAGE_CACHE) {
      return PACKAGE_CACHE.size();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\PackageCacheManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */