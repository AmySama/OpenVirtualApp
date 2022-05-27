package com.tencent.lbssearch.object.result;

import com.tencent.lbssearch.object.Location;
import java.util.List;

public class TransitResultObject extends RoutePlanningObject {
  public Result result;
  
  public static final class Destination {
    public String id;
    
    public String title;
  }
  
  public static final class GetOnOrOff {
    public String id;
    
    public Location location;
    
    public String title;
  }
  
  public static final class LatLngBounds {
    public Location northeast;
    
    public Location southwest;
  }
  
  public static final class Line {
    public TransitResultObject.Destination destination;
    
    public float distance;
    
    public float duration;
    
    public TransitResultObject.GetOnOrOff getoff;
    
    public TransitResultObject.GetOnOrOff geton;
    
    public String id;
    
    public List<Location> polyline;
    
    public int station_count;
    
    public String title;
    
    public String vehicle;
  }
  
  public static final class Result {
    public List<TransitResultObject.Route> routes;
  }
  
  public static final class Route {
    public TransitResultObject.LatLngBounds bounds;
    
    public float distance;
    
    public float duration;
    
    public List<TransitResultObject.Segment> steps;
  }
  
  public static abstract class Segment {
    public String mode;
  }
  
  public static final class Transit extends Segment {
    public List<TransitResultObject.Line> lines;
  }
  
  public static final class Walking extends Segment {
    public String accessorial_desc;
    
    public String direction;
    
    public float distance;
    
    public float duration;
    
    public List<Location> polyline;
    
    public List<RoutePlanningObject.Step> steps;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\result\TransitResultObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */