package com.tencent.lbssearch.object.param;

import com.tencent.lbssearch.a.b.d;
import com.tencent.lbssearch.object.Location;

public abstract class RoutePlanningParam implements ParamObject {
  protected final String POLICY = "policy";
  
  private Location from;
  
  private Location to;
  
  public d buildParameters() {
    d d = new d();
    d.a("from", locationToParamsString(this.from));
    d.a("to", locationToParamsString(this.to));
    return d;
  }
  
  public boolean checkParams() {
    boolean bool;
    if (this.from != null && this.to != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public RoutePlanningParam from(Location paramLocation) {
    this.from = paramLocation;
    return this;
  }
  
  public abstract Class<?> getResultClass();
  
  public abstract String getUrl();
  
  protected String locationToParamsString(Location paramLocation) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramLocation.lat);
    stringBuilder.append(",");
    stringBuilder.append(paramLocation.lng);
    return stringBuilder.toString();
  }
  
  public RoutePlanningParam to(Location paramLocation) {
    this.to = paramLocation;
    return this;
  }
  
  public enum DrivingPolicy {
    LEAST_DISTANCE, LEAST_FEE, LEAST_TIME, REAL_TRAFFIC;
    
    static {
      LEAST_DISTANCE = new DrivingPolicy("LEAST_DISTANCE", 2);
      DrivingPolicy drivingPolicy = new DrivingPolicy("REAL_TRAFFIC", 3);
      REAL_TRAFFIC = drivingPolicy;
      $VALUES = new DrivingPolicy[] { LEAST_TIME, LEAST_FEE, LEAST_DISTANCE, drivingPolicy };
    }
  }
  
  public enum TransitPolicy {
    LEAST_TIME, LEAST_TRANSFER, LEAST_WALKING;
    
    static {
      TransitPolicy transitPolicy = new TransitPolicy("LEAST_WALKING", 2);
      LEAST_WALKING = transitPolicy;
      $VALUES = new TransitPolicy[] { LEAST_TIME, LEAST_TRANSFER, transitPolicy };
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\param\RoutePlanningParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */