package com.tencent.lbssearch.object.param;

import com.tencent.lbssearch.object.result.BicyclingResultObject;

public class BicyclingParam extends RoutePlanningParam {
  public Class<?> getResultClass() {
    return BicyclingResultObject.class;
  }
  
  public String getUrl() {
    return "https://apis.map.qq.com/ws/direction/v1/bicycling";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\param\BicyclingParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */