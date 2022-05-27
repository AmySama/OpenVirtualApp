package com.tencent.lbssearch.object.param;

import com.tencent.lbssearch.a.b.d;
import com.tencent.lbssearch.object.result.TransitResultObject;

public class TransitParam extends RoutePlanningParam {
  private RoutePlanningParam.TransitPolicy policy = RoutePlanningParam.TransitPolicy.LEAST_TIME;
  
  public d buildParameters() {
    d d = super.buildParameters();
    d.a("policy", this.policy.name());
    return d;
  }
  
  public Class<TransitResultObject> getResultClass() {
    return TransitResultObject.class;
  }
  
  public String getUrl() {
    return "https://apis.map.qq.com/ws/direction/v1/transit";
  }
  
  public TransitParam policy(RoutePlanningParam.TransitPolicy paramTransitPolicy) {
    this.policy = paramTransitPolicy;
    return this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\param\TransitParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */