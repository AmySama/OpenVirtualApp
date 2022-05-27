package com.tencent.lbssearch.object.result;

import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.object.Location;
import java.util.List;

public abstract class RoutePlanningObject extends BaseObject {
  public static final class Step {
    public String accessorial_desc;
    
    public String act_desc;
    
    public String dir_desc;
    
    public float distance;
    
    public float duration;
    
    public String instruction;
    
    public List<Integer> polyline_idx;
    
    public String road_name;
  }
  
  public static final class WayPoint {
    public Location location;
    
    public String title;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\result\RoutePlanningObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */