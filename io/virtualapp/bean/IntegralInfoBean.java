package io.virtualapp.bean;

import java.io.Serializable;

public class IntegralInfoBean implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private String bizcode;
  
  private String bizid;
  
  private String biztext;
  
  private String id;
  
  private String integral;
  
  private boolean isSelect;
  
  private String memo;
  
  private String money;
  
  private int payType;
  
  private String value;
  
  public String getBizcode() {
    return this.bizcode;
  }
  
  public String getBizid() {
    return this.bizid;
  }
  
  public String getBiztext() {
    return this.biztext;
  }
  
  public String getId() {
    return this.id;
  }
  
  public String getIntegral() {
    return this.integral;
  }
  
  public String getMemo() {
    return this.memo;
  }
  
  public String getMoney() {
    return this.money;
  }
  
  public int getPayType() {
    return this.payType;
  }
  
  public String getValue() {
    return this.value;
  }
  
  public boolean isSelect() {
    return this.isSelect;
  }
  
  public void setBizcode(String paramString) {
    this.bizcode = paramString;
  }
  
  public void setBizid(String paramString) {
    this.bizid = paramString;
  }
  
  public void setBiztext(String paramString) {
    this.biztext = paramString;
  }
  
  public void setId(String paramString) {
    this.id = paramString;
  }
  
  public void setIntegral(String paramString) {
    this.integral = paramString;
  }
  
  public void setMemo(String paramString) {
    this.memo = paramString;
  }
  
  public void setMoney(String paramString) {
    this.money = paramString;
  }
  
  public void setPayType(int paramInt) {
    this.payType = paramInt;
  }
  
  public void setSelect(boolean paramBoolean) {
    this.isSelect = paramBoolean;
  }
  
  public void setValue(String paramString) {
    this.value = paramString;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\bean\IntegralInfoBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */