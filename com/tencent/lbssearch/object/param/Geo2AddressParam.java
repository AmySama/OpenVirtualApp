package com.tencent.lbssearch.object.param;

import com.tencent.lbssearch.a.b.d;
import com.tencent.lbssearch.object.Location;

public class Geo2AddressParam implements ParamObject {
  private static final String COORD_TYPE = "coord_type";
  
  private static final String GET_POI = "get_poi";
  
  private static final String LOCATION = "location";
  
  private CoordTypeEnum coordType = CoordTypeEnum.DEFAULT;
  
  private boolean isGetPoi = false;
  
  private Location location;
  
  public d buildParameters() {
    d d = new d();
    if (this.location != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(String.valueOf(this.location.lat));
      stringBuilder.append(",");
      stringBuilder.append(String.valueOf(this.location.lng));
      d.b("location", stringBuilder.toString());
    } 
    int i = null.$SwitchMap$com$tencent$lbssearch$object$param$CoordTypeEnum[this.coordType.ordinal()];
    String str = "1";
    switch (i) {
      case 6:
        d.b("coord_type", "6");
        break;
      case 5:
        d.b("coord_type", "5");
        break;
      case 4:
        d.b("coord_type", "4");
        break;
      case 3:
        d.b("coord_type", "3");
        break;
      case 2:
        d.b("coord_type", "2");
        break;
      case 1:
        d.b("coord_type", "1");
        break;
    } 
    if (!this.isGetPoi)
      str = "0"; 
    d.b("get_poi", str);
    return d;
  }
  
  public boolean checkParams() {
    return !(this.location == null);
  }
  
  public Geo2AddressParam coord_type(CoordTypeEnum paramCoordTypeEnum) {
    this.coordType = paramCoordTypeEnum;
    return this;
  }
  
  public Geo2AddressParam get_poi(boolean paramBoolean) {
    this.isGetPoi = paramBoolean;
    return this;
  }
  
  public Geo2AddressParam location(Location paramLocation) {
    this.location = paramLocation;
    return this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\param\Geo2AddressParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */