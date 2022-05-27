package com.tencent.lbssearch.object.result;

import com.tencent.lbssearch.object.Location;
import java.util.List;

public class BicyclingResultObject extends RoutePlanningObject {
  public Result result;
  
  public static final class Result {
    public List<BicyclingResultObject.Route> routes;
  }
  
  public static final class Route {
    public String direction;
    
    public float distance;
    
    public float duration;
    
    public String mode;
    
    public List<Location> polyline;
    
    public List<RoutePlanningObject.Step> steps;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\result\BicyclingResultObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */