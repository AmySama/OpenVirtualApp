package io.virtualapp.home.models;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import com.lody.virtual.helper.InstalledInfoCache;
import com.lody.virtual.remote.InstalledAppInfo;

public class PackageAppData extends AppData {
  public Drawable icon;
  
  public boolean is64bit;
  
  public boolean isSys;
  
  public String name;
  
  public String packageName;
  
  public PackageAppData(Context paramContext, InstalledAppInfo paramInstalledAppInfo) {
    this.packageName = paramInstalledAppInfo.packageName;
    this.isFirstOpen = paramInstalledAppInfo.isLaunched(0) ^ true;
    this.is64bit = paramInstalledAppInfo.is64bit;
    loadData(paramContext, paramInstalledAppInfo.getApplicationInfo(paramInstalledAppInfo.getInstalledUsers()[0]));
  }
  
  public PackageAppData(String paramString1, String paramString2, Drawable paramDrawable, boolean paramBoolean) {
    this.packageName = paramString1;
    this.name = paramString2;
    this.icon = paramDrawable;
    this.isSys = paramBoolean;
  }
  
  private void loadData(Context paramContext, ApplicationInfo paramApplicationInfo) {
    if (paramApplicationInfo == null)
      return; 
    PackageManager packageManager = paramContext.getPackageManager();
    try {
      InstalledInfoCache.CacheItem cacheItem = InstalledInfoCache.get(paramApplicationInfo.packageName);
    } finally {
      paramContext = null;
    } 
  }
  
  public boolean canCreateShortcut() {
    return true;
  }
  
  public boolean canDelete() {
    return true;
  }
  
  public boolean canLaunch() {
    return true;
  }
  
  public boolean canReorder() {
    return true;
  }
  
  public Drawable getIcon() {
    return this.icon;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getPackageName() {
    return this.packageName;
  }
  
  public int getUserId() {
    return 0;
  }
  
  public boolean is64bit() {
    return this.is64bit;
  }
  
  public boolean isFirstOpen() {
    return this.isFirstOpen;
  }
  
  public boolean isIs64bit() {
    return this.is64bit;
  }
  
  public boolean isLoading() {
    return this.isLoading;
  }
  
  public boolean isSys() {
    return this.isSys;
  }
  
  public void setIcon(Drawable paramDrawable) {
    this.icon = paramDrawable;
  }
  
  public void setIs64bit(boolean paramBoolean) {
    this.is64bit = paramBoolean;
  }
  
  public void setName(String paramString) {
    this.name = paramString;
  }
  
  public void setPackageName(String paramString) {
    this.packageName = paramString;
  }
  
  public void setSys(boolean paramBoolean) {
    this.isSys = paramBoolean;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\models\PackageAppData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */