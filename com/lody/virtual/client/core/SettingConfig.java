package com.lody.virtual.client.core;

import android.content.Intent;
import com.lody.virtual.client.env.SpecialComponentList;

public abstract class SettingConfig {
  public boolean disableSetScreenOrientation(String paramString) {
    return false;
  }
  
  public String getBinderProviderAuthority() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getMainPackageName());
    stringBuilder.append(".virtual.service.BinderProvider");
    return stringBuilder.toString();
  }
  
  public String getExtPackageHelperAuthority() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getExtPackageName());
    stringBuilder.append(".virtual.service.ext_helper");
    return stringBuilder.toString();
  }
  
  public abstract String getExtPackageName();
  
  public FakeWifiStatus getFakeWifiStatus() {
    return null;
  }
  
  public abstract String getMainPackageName();
  
  public String getVirtualSdcardAndroidDataName() {
    return "Android_va";
  }
  
  public boolean isAllowCreateShortcut() {
    return true;
  }
  
  public boolean isDisableDrawOverlays(String paramString) {
    return false;
  }
  
  public boolean isEnableIORedirect() {
    return true;
  }
  
  public boolean isEnableVirtualSdcardAndroidData() {
    return false;
  }
  
  public boolean isHideForegroundNotification() {
    return false;
  }
  
  public boolean isHostIntent(Intent paramIntent) {
    return false;
  }
  
  public boolean isOutsideAction(String paramString) {
    return ("android.media.action.IMAGE_CAPTURE".equals(paramString) || "android.media.action.VIDEO_CAPTURE".equals(paramString) || "android.intent.action.PICK".equals(paramString));
  }
  
  public abstract boolean isOutsidePackage(String paramString);
  
  public boolean isUnProtectAction(String paramString) {
    return SpecialComponentList.SYSTEM_BROADCAST_ACTION.contains(paramString);
  }
  
  public boolean isUseRealApkPath(String paramString) {
    return false;
  }
  
  public boolean isUseRealDataDir(String paramString) {
    return false;
  }
  
  public boolean isUseRealLibDir(String paramString) {
    return false;
  }
  
  public Intent onHandleLauncherIntent(Intent paramIntent) {
    return null;
  }
  
  public boolean resumeInstrumentationInMakeApplication(String paramString) {
    return false;
  }
  
  public static class FakeWifiStatus {
    public static String DEFAULT_BSSID = "66:55:44:33:22:11";
    
    public static String DEFAULT_MAC = "11:22:33:44:55:66";
    
    public static String DEFAULT_SSID = "VA_SSID";
    
    public String getBSSID() {
      return DEFAULT_BSSID;
    }
    
    public String getMAC() {
      return DEFAULT_MAC;
    }
    
    public String getSSID() {
      return DEFAULT_SSID;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\core\SettingConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */