package io.virtualapp.home.models;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.lody.virtual.remote.InstalledAppInfo;
import com.lody.virtual.remote.vloc.VLocation;

public class LocationData extends SettingsData {
  public VLocation location;
  
  public int mode;
  
  public LocationData() {}
  
  public LocationData(Context paramContext, InstalledAppInfo paramInstalledAppInfo, int paramInt) {
    this.packageName = paramInstalledAppInfo.packageName;
    this.userId = paramInt;
    loadData(paramContext, paramInstalledAppInfo.getApplicationInfo(paramInstalledAppInfo.getInstalledUsers()[0]));
  }
  
  private void loadData(Context paramContext, ApplicationInfo paramApplicationInfo) {
    if (paramApplicationInfo == null)
      return; 
    PackageManager packageManager = paramContext.getPackageManager();
    try {
      CharSequence charSequence = paramApplicationInfo.loadLabel(packageManager);
      if (charSequence != null)
        this.name = charSequence.toString(); 
    } finally {
      paramContext = null;
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("LocationData{packageName='");
    stringBuilder.append(this.packageName);
    stringBuilder.append('\'');
    stringBuilder.append(", userId=");
    stringBuilder.append(this.userId);
    stringBuilder.append(", location=");
    stringBuilder.append(this.location);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\models\LocationData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */