package com.tencent.lbssearch.object.param;

import com.tencent.lbssearch.a.b.d;
import com.tencent.lbssearch.object.Location;
import com.tencent.lbssearch.object.result.DrivingResultObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DrivingParam extends RoutePlanningParam {
  private static final int MAX_WAT_POINTS = 10;
  
  private static final String MULTIPLE_PLAN = "get_mp";
  
  private int multiPlan = 0;
  
  private RoutePlanningParam.DrivingPolicy policy = RoutePlanningParam.DrivingPolicy.LEAST_TIME;
  
  private List<Location> waypoints = new ArrayList<Location>();
  
  public DrivingParam addWayPoint(Location paramLocation) {
    if (this.waypoints.size() < 10)
      this.waypoints.add(paramLocation); 
    return this;
  }
  
  public DrivingParam addWayPoints(Iterable<Location> paramIterable) {
    if (paramIterable != null) {
      int i = 0;
      for (Location location : paramIterable) {
        this.waypoints.add(location);
        int j = i + 1;
        i = j;
        if (j > 10)
          break; 
      } 
    } 
    return this;
  }
  
  public d buildParameters() {
    d d = super.buildParameters();
    d.a("policy", this.policy.name());
    d.a("get_mp", this.multiPlan);
    if (this.waypoints.size() > 0) {
      StringBuilder stringBuilder = new StringBuilder();
      Iterator<Location> iterator = this.waypoints.iterator();
      while (iterator.hasNext()) {
        stringBuilder.append(locationToParamsString(iterator.next()));
        stringBuilder.append(";");
      } 
      stringBuilder.setLength(stringBuilder.length() - 1);
      d.a("waypoints", stringBuilder.toString());
    } 
    return d;
  }
  
  public Class<DrivingResultObject> getResultClass() {
    return DrivingResultObject.class;
  }
  
  public String getUrl() {
    return "https://apis.map.qq.com/ws/direction/v1/driving";
  }
  
  public DrivingParam policy(RoutePlanningParam.DrivingPolicy paramDrivingPolicy) {
    this.policy = paramDrivingPolicy;
    return this;
  }
  
  public void setMultyPlan(boolean paramBoolean) {
    if (paramBoolean) {
      this.multiPlan = 1;
    } else {
      this.multiPlan = 0;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\param\DrivingParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */