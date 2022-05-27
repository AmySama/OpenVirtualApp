package com.tencent.lbssearch.object.result;

import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.object.Location;
import java.util.List;

public class SuggestionResultObject extends BaseObject {
  public int count;
  
  public List<SuggestionData> data;
  
  public class SuggestionData {
    public String adcode;
    
    public String address;
    
    public String city;
    
    public String district;
    
    public String id;
    
    public Location location;
    
    public String province;
    
    public String title;
    
    public int type;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\result\SuggestionResultObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */