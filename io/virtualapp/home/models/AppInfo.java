package io.virtualapp.home.models;

import android.graphics.drawable.Drawable;
import io.virtualapp.bean.AdBean;

public class AppInfo {
  public AdBean adBean;
  
  public Integer appCloneCount = Integer.valueOf(0);
  
  public String appName;
  
  public int cloneCount;
  
  public boolean cloneMode;
  
  public Drawable icon;
  
  public boolean isAd = false;
  
  public boolean isSelected;
  
  public boolean isUsual = false;
  
  public CharSequence name;
  
  public String packageName;
  
  public String path;
  
  public String[] requestedPermissions;
  
  public int targetSdkVersion;
  
  public AdBean getAdBean() {
    return this.adBean;
  }
  
  public Integer getAppCloneCount() {
    return this.appCloneCount;
  }
  
  public String getAppName() {
    return this.appName;
  }
  
  public int getCloneCount() {
    return this.cloneCount;
  }
  
  public Drawable getIcon() {
    return this.icon;
  }
  
  public CharSequence getName() {
    return this.name;
  }
  
  public String getPackageName() {
    return this.packageName;
  }
  
  public String getPath() {
    return this.path;
  }
  
  public String[] getRequestedPermissions() {
    return this.requestedPermissions;
  }
  
  public int getTargetSdkVersion() {
    return this.targetSdkVersion;
  }
  
  public boolean isAd() {
    return this.isAd;
  }
  
  public boolean isCloneMode() {
    return this.cloneMode;
  }
  
  public boolean isSelected() {
    return this.isSelected;
  }
  
  public boolean isUsual() {
    return this.isUsual;
  }
  
  public void setAd(boolean paramBoolean) {
    this.isAd = paramBoolean;
  }
  
  public void setAdBean(AdBean paramAdBean) {
    this.adBean = paramAdBean;
  }
  
  public void setAppCloneCount(Integer paramInteger) {
    this.appCloneCount = paramInteger;
  }
  
  public void setAppName(String paramString) {
    this.appName = paramString;
  }
  
  public void setCloneCount(int paramInt) {
    this.cloneCount = paramInt;
  }
  
  public void setCloneMode(boolean paramBoolean) {
    this.cloneMode = paramBoolean;
  }
  
  public void setIcon(Drawable paramDrawable) {
    this.icon = paramDrawable;
  }
  
  public void setName(CharSequence paramCharSequence) {
    this.name = paramCharSequence;
  }
  
  public void setPackageName(String paramString) {
    this.packageName = paramString;
  }
  
  public void setPath(String paramString) {
    this.path = paramString;
  }
  
  public void setRequestedPermissions(String[] paramArrayOfString) {
    this.requestedPermissions = paramArrayOfString;
  }
  
  public void setSelected(boolean paramBoolean) {
    this.isSelected = paramBoolean;
  }
  
  public void setTargetSdkVersion(int paramInt) {
    this.targetSdkVersion = paramInt;
  }
  
  public void setUsual(boolean paramBoolean) {
    this.isUsual = paramBoolean;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\models\AppInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */