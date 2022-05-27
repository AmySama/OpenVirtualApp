package com.tencent.lbssearch.object.param;

import com.tencent.lbssearch.a.b.d;

public class DistrictChildrenParam implements ParamObject {
  private static final String ID = "id";
  
  private int id;
  
  public d buildParameters() {
    d d = new d();
    int i = this.id;
    if (i > 0)
      d.b("id", String.valueOf(i)); 
    return d;
  }
  
  public boolean checkParams() {
    return true;
  }
  
  public DistrictChildrenParam id(int paramInt) {
    this.id = paramInt;
    return this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\param\DistrictChildrenParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */