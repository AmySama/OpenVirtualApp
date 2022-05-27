package io.virtualapp.bean;

import java.io.Serializable;

public class PayTypeBean implements Serializable {
  private String alipaydiscount;
  
  private String paytype;
  
  public String getAlipaydiscount() {
    return this.alipaydiscount;
  }
  
  public String getPaytype() {
    return this.paytype;
  }
  
  public void setAlipaydiscount(String paramString) {
    this.alipaydiscount = paramString;
  }
  
  public void setPaytype(String paramString) {
    this.paytype = paramString;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\bean\PayTypeBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */