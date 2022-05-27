package io.virtualapp.home.models;

public class CellInfo {
  private int acc;
  
  private int ci;
  
  private int lac;
  
  private LocationBean location;
  
  private int mnc;
  
  public int getAcc() {
    return this.acc;
  }
  
  public int getCi() {
    return this.ci;
  }
  
  public int getLac() {
    return this.lac;
  }
  
  public LocationBean getLocation() {
    return this.location;
  }
  
  public int getMnc() {
    return this.mnc;
  }
  
  public void setAcc(int paramInt) {
    this.acc = paramInt;
  }
  
  public void setCi(int paramInt) {
    this.ci = paramInt;
  }
  
  public void setLac(int paramInt) {
    this.lac = paramInt;
  }
  
  public void setLocation(LocationBean paramLocationBean) {
    this.location = paramLocationBean;
  }
  
  public void setMnc(int paramInt) {
    this.mnc = paramInt;
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\models\CellInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */