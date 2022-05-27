package com.tencent.lbssearch.object.param;

import android.text.TextUtils;
import com.tencent.lbssearch.a.b.d;

public class Address2GeoParam implements ParamObject {
  private static final String ADDRESS = "address";
  
  private static final String REGION = "region";
  
  private String address;
  
  private String region;
  
  public Address2GeoParam address(String paramString) {
    this.address = paramString;
    return this;
  }
  
  public d buildParameters() {
    d d = new d();
    if (!TextUtils.isEmpty(this.address))
      d.b("address", this.address); 
    if (!TextUtils.isEmpty(this.region))
      d.b("region", this.region); 
    return d;
  }
  
  public boolean checkParams() {
    return !TextUtils.isEmpty(this.address);
  }
  
  public Address2GeoParam region(String paramString) {
    this.region = paramString;
    return this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\param\Address2GeoParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */