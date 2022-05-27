package com.tencent.lbssearch.object.result;

import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.object.Location;
import java.util.List;

public class SearchResultObject extends BaseObject {
  public int count;
  
  public List<SearchResultData> data;
  
  public class SearchResultData {
    public String address;
    
    public String category;
    
    public String id;
    
    public Location location;
    
    public Pano pano;
    
    public String tel;
    
    public String title;
    
    public String type;
    
    public class Pano {
      public int heading;
      
      public String id;
      
      public int pitch;
      
      public int zoom;
    }
  }
  
  public class Pano {
    public int heading;
    
    public String id;
    
    public int pitch;
    
    public int zoom;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\result\SearchResultObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */