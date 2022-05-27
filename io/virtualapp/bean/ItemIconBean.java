package io.virtualapp.bean;

import android.graphics.drawable.Drawable;
import java.io.Serializable;

public class ItemIconBean implements Serializable {
  private Drawable icon;
  
  private String iconName;
  
  private String iconPath;
  
  private String iconResource;
  
  private boolean isLocalIcon = false;
  
  private boolean isSelect;
  
  public Drawable getIcon() {
    return this.icon;
  }
  
  public String getIconName() {
    return this.iconName;
  }
  
  public String getIconPath() {
    return this.iconPath;
  }
  
  public String getIconResource() {
    return this.iconResource;
  }
  
  public boolean isLocalIcon() {
    return this.isLocalIcon;
  }
  
  public boolean isSelect() {
    return this.isSelect;
  }
  
  public void setIcon(Drawable paramDrawable) {
    this.icon = paramDrawable;
  }
  
  public void setIconName(String paramString) {
    this.iconName = paramString;
  }
  
  public void setIconPath(String paramString) {
    this.iconPath = paramString;
  }
  
  public void setIconResource(String paramString) {
    this.iconResource = paramString;
  }
  
  public void setLocalIcon(boolean paramBoolean) {
    this.isLocalIcon = paramBoolean;
  }
  
  public void setSelect(boolean paramBoolean) {
    this.isSelect = paramBoolean;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\bean\ItemIconBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */