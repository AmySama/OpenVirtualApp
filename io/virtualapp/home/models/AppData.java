package io.virtualapp.home.models;

import android.graphics.drawable.Drawable;

public abstract class AppData {
  public boolean isFirstOpen;
  
  public boolean isLoading;
  
  public boolean isSys;
  
  public boolean canCreateShortcut() {
    return false;
  }
  
  public boolean canDelete() {
    return false;
  }
  
  public boolean canLaunch() {
    return false;
  }
  
  public boolean canReorder() {
    return false;
  }
  
  public Drawable getIcon() {
    return null;
  }
  
  public String getName() {
    return null;
  }
  
  public String getPackageName() {
    return null;
  }
  
  public int getUserId() {
    return 0;
  }
  
  public boolean is64bit() {
    return false;
  }
  
  public boolean isFirstOpen() {
    return this.isFirstOpen;
  }
  
  public boolean isLoading() {
    return this.isLoading;
  }
  
  public boolean isSys() {
    return this.isSys;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\models\AppData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */