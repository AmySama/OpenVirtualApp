package io.virtualapp.bean;

import java.io.Serializable;

public class PluginBean implements Serializable {
  private String downloadPath;
  
  private String filename;
  
  private String iconPath;
  
  private String memo;
  
  private String pluginName;
  
  private String size;
  
  public String getDownloadPath() {
    return this.downloadPath;
  }
  
  public String getFilename() {
    return this.filename;
  }
  
  public String getIconPath() {
    return this.iconPath;
  }
  
  public String getMemo() {
    return this.memo;
  }
  
  public String getPluginName() {
    return this.pluginName;
  }
  
  public String getSize() {
    return this.size;
  }
  
  public void setDownloadPath(String paramString) {
    this.downloadPath = paramString;
  }
  
  public void setFilename(String paramString) {
    this.filename = paramString;
  }
  
  public void setIconPath(String paramString) {
    this.iconPath = paramString;
  }
  
  public void setMemo(String paramString) {
    this.memo = paramString;
  }
  
  public void setPluginName(String paramString) {
    this.pluginName = paramString;
  }
  
  public void setSize(String paramString) {
    this.size = paramString;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\bean\PluginBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */