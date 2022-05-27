package com.tencent.lbssearch.object;

public class Location {
  public float lat;
  
  public float lng;
  
  public Location() {}
  
  public Location(float paramFloat1, float paramFloat2) {
    this.lat = paramFloat1;
    this.lng = paramFloat2;
  }
  
  public Location lat(float paramFloat) {
    this.lat = paramFloat;
    return this;
  }
  
  public Location lng(float paramFloat) {
    this.lng = paramFloat;
    return this;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("lat:");
    stringBuilder.append(String.valueOf(this.lat));
    stringBuilder.append("  lng:");
    stringBuilder.append(String.valueOf(this.lng));
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\object\Location.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */