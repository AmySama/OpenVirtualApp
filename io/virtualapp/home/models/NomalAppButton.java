package io.virtualapp.home.models;

import android.graphics.drawable.Drawable;

public class NomalAppButton {
  public Drawable icon;
  
  public boolean isFirstOpen;
  
  public boolean isLoading;
  
  private boolean isSelect;
  
  public String name;
  
  public String packageName;
  
  public NomalAppButton(Drawable paramDrawable, String paramString1, String paramString2) {
    this.icon = paramDrawable;
    this.name = paramString1;
    this.packageName = paramString2;
  }
  
  public NomalAppButton(Drawable paramDrawable, String paramString1, String paramString2, boolean paramBoolean) {
    this.icon = paramDrawable;
    this.name = paramString1;
    this.packageName = paramString2;
    this.isSelect = paramBoolean;
  }
  
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
  
  public String getPkgName() {
    return this.packageName;
  }
  
  public int getUserId() {
    return 0;
  }
  
  public boolean isFirstOpen() {
    return this.isFirstOpen;
  }
  
  public boolean isLoading() {
    return this.isLoading;
  }
  
  public boolean isSelect() {
    return this.isSelect;
  }
  
  public void setSelect(boolean paramBoolean) {
    this.isSelect = paramBoolean;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\models\NomalAppButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */