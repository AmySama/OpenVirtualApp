package com.tencent.lbssearch.object.result;

import com.tencent.lbssearch.object.Location;
import java.util.List;

public class DrivingResultObject extends RoutePlanningObject {
  public static final String EXPERIENCE = "EXPERIENCE";
  
  public static final String LEAST_DISTANCE = "LEAST_DISTANCE";
  
  public static final String LEAST_LIGHT = "LEAST_LIGHT";
  
  public static final String LEAST_TIME = "LEAST_TIME";
  
  public static final String RECOMMEND = "RECOMMEND";
  
  public Result result;
  
  public static final class Result {
    public List<DrivingResultObject.Route> routes;
  }
  
  public static final class Route {
    public String direction;
    
    public float distance;
    
    public float duration;
    
    public String mode;
    
    public List<Location> polyline;
    
    public List<RoutePlanningObject.Step> steps;
    
    public List<String> tags;
    
    public List<DrivingResultObject.WayPoint> waypoints;
  }
  
  public static final class WayPoint {
    public Location location;
    
    public String title;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\result\DrivingResultObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */