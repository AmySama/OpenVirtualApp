package io.virtualapp.home.models;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import com.lody.virtual.remote.InstalledAppInfo;

public class SettingsData {
  public Drawable icon;
  
  public String name;
  
  public String packageName;
  
  public int userId;
  
  public SettingsData() {}
  
  public SettingsData(Context paramContext, InstalledAppInfo paramInstalledAppInfo, int paramInt) {
    String str;
    if (paramInstalledAppInfo == null) {
      str = null;
    } else {
      str = paramInstalledAppInfo.packageName;
    } 
    this.packageName = str;
    this.userId = paramInt;
    if (paramInstalledAppInfo != null)
      loadData(paramContext, paramInstalledAppInfo.getApplicationInfo(paramInstalledAppInfo.getInstalledUsers()[0])); 
  }
  
  private void loadData(Context paramContext, ApplicationInfo paramApplicationInfo) {
    if (paramApplicationInfo == null)
      return; 
    PackageManager packageManager = paramContext.getPackageManager();
    try {
      this.name = paramApplicationInfo.loadLabel(packageManager).toString();
      this.icon = paramApplicationInfo.loadIcon(packageManager);
    } finally {
      packageManager = null;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\models\SettingsData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */