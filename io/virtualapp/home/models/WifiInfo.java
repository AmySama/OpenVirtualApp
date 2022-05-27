package io.virtualapp.home.models;

public class WifiInfo {
  private int acc;
  
  private LocationBean location;
  
  private String mac;
  
  public int getAcc() {
    return this.acc;
  }
  
  public LocationBean getLocation() {
    return this.location;
  }
  
  public String getMac() {
    return this.mac;
  }
  
  public void setAcc(int paramInt) {
    this.acc = paramInt;
  }
  
  public void setLocation(LocationBean paramLocationBean) {
    this.location = paramLocationBean;
  }
  
  public void setMac(String paramString) {
    this.mac = paramString;
  }
  
  public static class LocationBean {
    private double lat;
    
    private double lon;
    
    public double getLat() {
      return this.lat;
    }
    
    public double getLon() {
      return this.lon;
    }
    
    public void setLat(double param1Double) {
      this.lat = param1Double;
    }
    
    public void setLon(double param1Double) {
      this.lon = param1Double;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\models\WifiInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */