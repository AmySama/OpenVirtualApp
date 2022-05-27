package io.virtualapp.bean;

import java.io.Serializable;

public class UpdateBean implements Serializable {
  private String downloadurl;
  
  private String force;
  
  private String packetname;
  
  private String ver;
  
  private String verinfo;
  
  public String getDownloadurl() {
    return this.downloadurl;
  }
  
  public String getForce() {
    return this.force;
  }
  
  public String getPacketname() {
    return this.packetname;
  }
  
  public String getVer() {
    return this.ver;
  }
  
  public String getVerinfo() {
    return this.verinfo;
  }
  
  public void setDownloadurl(String paramString) {
    this.downloadurl = paramString;
  }
  
  public void setForce(String paramString) {
    this.force = paramString;
  }
  
  public void setPacketname(String paramString) {
    this.packetname = paramString;
  }
  
  public void setVer(String paramString) {
    this.ver = paramString;
  }
  
  public void setVerinfo(String paramString) {
    this.verinfo = paramString;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\bean\UpdateBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */