package com.tencent.lbssearch.httpresponse;

public class BaseObject {
  public String message;
  
  public int status;
  
  public boolean isStatusOk() {
    boolean bool;
    if (this.status == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\httpresponse\BaseObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */