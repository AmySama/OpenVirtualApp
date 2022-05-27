package io.virtualapp.home.models;

import android.graphics.drawable.Drawable;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.remote.InstalledAppInfo;

public class MultiplePackageAppData extends AppData {
  public InstalledAppInfo appInfo;
  
  public Drawable icon;
  
  public String name;
  
  public String packageName;
  
  public int userId;
  
  public MultiplePackageAppData(PackageAppData paramPackageAppData, int paramInt) {
    this.userId = paramInt;
    InstalledAppInfo installedAppInfo = VirtualCore.get().getInstalledAppInfo(paramPackageAppData.packageName, 0);
    this.appInfo = installedAppInfo;
    this.isFirstOpen = installedAppInfo.isLaunched(paramInt) ^ true;
    if (paramPackageAppData.icon != null) {
      Drawable.ConstantState constantState = paramPackageAppData.icon.getConstantState();
      if (constantState != null)
        this.icon = constantState.newDrawable(); 
    } 
    this.name = paramPackageAppData.name;
    this.packageName = paramPackageAppData.packageName;
  }
  
  public MultiplePackageAppData(PackageAppData paramPackageAppData, boolean paramBoolean) {
    this.isSys = paramBoolean;
    if (paramPackageAppData.icon != null) {
      Drawable.ConstantState constantState = paramPackageAppData.icon.getConstantState();
      if (constantState != null)
        this.icon = constantState.newDrawable(); 
    } 
    this.name = paramPackageAppData.name;
    this.packageName = paramPackageAppData.packageName;
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
    return this.userId;
  }
  
  public boolean is64bit() {
    return this.appInfo.is64bit;
  }
  
  public boolean isFirstOpen() {
    return this.isFirstOpen;
  }
  
  public boolean isLoading() {
    return this.isLoading;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\models\MultiplePackageAppData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */