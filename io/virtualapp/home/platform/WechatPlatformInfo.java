package io.virtualapp.home.platform;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;

public class WechatPlatformInfo extends PlatformInfo {
  public WechatPlatformInfo() {
    super(new String[] { "com.tencent.mm" });
  }
  
  public boolean relyOnPlatform(PackageInfo paramPackageInfo) {
    if (paramPackageInfo.activities == null)
      return false; 
    ActivityInfo[] arrayOfActivityInfo = paramPackageInfo.activities;
    int i = arrayOfActivityInfo.length;
    for (byte b = 0; b < i; b++) {
      if ((arrayOfActivityInfo[b]).name.endsWith("WXEntryActivity"))
        return true; 
    } 
    return false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\platform\WechatPlatformInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */