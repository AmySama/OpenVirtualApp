package io.virtualapp.home.models;

import java.io.Serializable;

public class AppInfoBean implements Serializable {
  public boolean isFirstOpen;
  
  public boolean isLoading;
  
  public String name;
  
  public String packageName;
  
  public int userId;
  
  public String getName() {
    return this.name;
  }
  
  public String getPackageName() {
    return this.packageName;
  }
  
  public int getUserId() {
    return this.userId;
  }
  
  public boolean isFirstOpen() {
    return this.isFirstOpen;
  }
  
  public boolean isLoading() {
    return this.isLoading;
  }
  
  public void setFirstOpen(boolean paramBoolean) {
    this.isFirstOpen = paramBoolean;
  }
  
  public void setLoading(boolean paramBoolean) {
    this.isLoading = paramBoolean;
  }
  
  public void setName(String paramString) {
    this.name = paramString;
  }
  
  public void setPackageName(String paramString) {
    this.packageName = paramString;
  }
  
  public void setUserId(int paramInt) {
    this.userId = paramInt;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\models\AppInfoBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */