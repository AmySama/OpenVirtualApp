package io.virtualapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdBean implements Serializable {
  private String adid;
  
  private String apkurl;
  
  private String content;
  
  private String icon;
  
  private List<String> images = new ArrayList<String>();
  
  private String md5;
  
  private String opentype;
  
  private String packagename;
  
  private String showtype;
  
  private String subtitle;
  
  private String title;
  
  public String getAdid() {
    return this.adid;
  }
  
  public String getApkurl() {
    return this.apkurl;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public String getIcon() {
    return this.icon;
  }
  
  public List<String> getImages() {
    return this.images;
  }
  
  public String getMd5() {
    return this.md5;
  }
  
  public String getOpentype() {
    return this.opentype;
  }
  
  public String getPackagename() {
    return this.packagename;
  }
  
  public String getShowtype() {
    return this.showtype;
  }
  
  public String getSubtitle() {
    return this.subtitle;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public void setAdid(String paramString) {
    this.adid = paramString;
  }
  
  public void setApkurl(String paramString) {
    this.apkurl = paramString;
  }
  
  public void setContent(String paramString) {
    this.content = paramString;
  }
  
  public void setIcon(String paramString) {
    this.icon = paramString;
  }
  
  public void setImages(List<String> paramList) {
    this.images = paramList;
  }
  
  public void setMd5(String paramString) {
    this.md5 = paramString;
  }
  
  public void setOpentype(String paramString) {
    this.opentype = paramString;
  }
  
  public void setPackagename(String paramString) {
    this.packagename = paramString;
  }
  
  public void setShowtype(String paramString) {
    this.showtype = paramString;
  }
  
  public void setSubtitle(String paramString) {
    this.subtitle = paramString;
  }
  
  public void setTitle(String paramString) {
    this.title = paramString;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\bean\AdBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */