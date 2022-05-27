package com.tencent.lbssearch.object.param;

import android.text.TextUtils;
import com.tencent.lbssearch.a.b.d;
import com.tencent.lbssearch.object.Location;

public class StreetViewParam implements ParamObject {
  private static final String ID = "id";
  
  private static final String LOCATION = "location";
  
  private static final String RADIUS = "radius";
  
  private String id;
  
  private Location location;
  
  private int radius;
  
  public d buildParameters() {
    d d = new d();
    if (this.location != null && TextUtils.isEmpty(this.id)) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(String.valueOf(this.location.lat));
      stringBuilder.append(",");
      stringBuilder.append(String.valueOf(this.location.lng));
      d.b("location", stringBuilder.toString());
    } else {
      d.b("id", String.valueOf(this.id));
    } 
    d.b("radius", String.valueOf(this.radius));
    return d;
  }
  
  public boolean checkParams() {
    return !(TextUtils.isEmpty(this.id) && this.location == null);
  }
  
  public StreetViewParam id(String paramString) {
    this.id = paramString;
    return this;
  }
  
  public StreetViewParam location(Location paramLocation) {
    this.location = paramLocation;
    return this;
  }
  
  public StreetViewParam radius(int paramInt) {
    this.radius = paramInt;
    return this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\param\StreetViewParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */