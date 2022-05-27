package io.virtualapp.bean;

import java.io.Serializable;

public class PayBean implements Serializable {
  private String amount;
  
  private String bizcode;
  
  private String token;
  
  public String getAmount() {
    return this.amount;
  }
  
  public String getBizcode() {
    return this.bizcode;
  }
  
  public String getToken() {
    return this.token;
  }
  
  public void setAmount(String paramString) {
    this.amount = paramString;
  }
  
  public void setBizcode(String paramString) {
    this.bizcode = paramString;
  }
  
  public void setToken(String paramString) {
    this.token = paramString;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\bean\PayBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */