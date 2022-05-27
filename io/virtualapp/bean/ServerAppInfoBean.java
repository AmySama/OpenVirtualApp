package io.virtualapp.bean;

import java.io.Serializable;

public class ServerAppInfoBean implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String appname;
  
  private String black;
  
  private String packet;
  
  private String viewcount;
  
  public String getAppname() {
    return this.appname;
  }
  
  public String getBlack() {
    return this.black;
  }
  
  public String getPacket() {
    return this.packet;
  }
  
  public String getViewcount() {
    return this.viewcount;
  }
  
  public void setAppname(String paramString) {
    this.appname = paramString;
  }
  
  public void setBlack(String paramString) {
    this.black = paramString;
  }
  
  public void setPacket(String paramString) {
    this.packet = paramString;
  }
  
  public void setViewcount(String paramString) {
    this.viewcount = paramString;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\bean\ServerAppInfoBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */