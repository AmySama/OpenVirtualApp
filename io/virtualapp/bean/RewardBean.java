package io.virtualapp.bean;

import java.io.Serializable;

public class RewardBean implements Serializable {
  private String buttonDisabledStr;
  
  private String buttonStr;
  
  private String content;
  
  private boolean isDisabled;
  
  private String memo;
  
  private int method;
  
  private String rightTitle;
  
  private String title;
  
  public String getButtonDisabledStr() {
    return this.buttonDisabledStr;
  }
  
  public String getButtonStr() {
    return this.buttonStr;
  }
  
  public String getContent() {
    return this.content;
  }
  
  public String getMemo() {
    return this.memo;
  }
  
  public int getMethod() {
    return this.method;
  }
  
  public String getRightTitle() {
    return this.rightTitle;
  }
  
  public String getTitle() {
    return this.title;
  }
  
  public boolean isDisabled() {
    return this.isDisabled;
  }
  
  public void setButtonDisabledStr(String paramString) {
    this.buttonDisabledStr = paramString;
  }
  
  public void setButtonStr(String paramString) {
    this.buttonStr = paramString;
  }
  
  public void setContent(String paramString) {
    this.content = paramString;
  }
  
  public void setDisabled(boolean paramBoolean) {
    this.isDisabled = paramBoolean;
  }
  
  public void setMemo(String paramString) {
    this.memo = paramString;
  }
  
  public void setMethod(int paramInt) {
    this.method = paramInt;
  }
  
  public void setRightTitle(String paramString) {
    this.rightTitle = paramString;
  }
  
  public void setTitle(String paramString) {
    this.title = paramString;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\bean\RewardBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */