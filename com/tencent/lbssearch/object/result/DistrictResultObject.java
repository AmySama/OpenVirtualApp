package com.tencent.lbssearch.object.result;

import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.object.Location;
import java.util.List;

public class DistrictResultObject extends BaseObject {
  public List<List<DistrictResult>> result;
  
  public class DistrictResult {
    public String fullname;
    
    public int id;
    
    public Location location;
    
    public String name;
    
    public List<String> pinyin;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\result\DistrictResultObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */