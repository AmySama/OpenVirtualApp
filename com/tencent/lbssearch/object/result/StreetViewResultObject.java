package com.tencent.lbssearch.object.result;

import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.object.Location;

public class StreetViewResultObject extends BaseObject {
  public Details detail;
  
  public class Details {
    public String description;
    
    public String id;
    
    public Location location;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\result\StreetViewResultObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */