package com.lody.virtual.client.env;

class GPSStateline {
  private double mAzimuth;
  
  private double mCarrierFrequencyHz;
  
  private double mElevation;
  
  private boolean mHasAlmanac;
  
  private boolean mHasEphemeris;
  
  private int mPnr;
  
  private double mSnr;
  
  private boolean mUseInFix;
  
  public GPSStateline(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, double paramDouble4) {
    this.mAzimuth = paramDouble3;
    this.mElevation = paramDouble2;
    this.mHasAlmanac = paramBoolean2;
    this.mHasEphemeris = paramBoolean3;
    this.mCarrierFrequencyHz = paramDouble4;
    this.mPnr = paramInt;
    this.mSnr = paramDouble1;
    this.mUseInFix = paramBoolean1;
  }
  
  public double getAzimuth() {
    return this.mAzimuth;
  }
  
  public double getCarrierFrequencyHz() {
    return this.mCarrierFrequencyHz;
  }
  
  public double getElevation() {
    return this.mElevation;
  }
  
  public int getPnr() {
    return this.mPnr;
  }
  
  public double getSnr() {
    return this.mSnr;
  }
  
  public boolean hasCarrierFrequencyHz() {
    boolean bool;
    if (this.mCarrierFrequencyHz > 0.0D) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isHasAlmanac() {
    return this.mHasAlmanac;
  }
  
  public boolean isHasEphemeris() {
    return this.mHasEphemeris;
  }
  
  public boolean isUseInFix() {
    return this.mUseInFix;
  }
  
  public void setAzimuth(double paramDouble) {
    this.mAzimuth = paramDouble;
  }
  
  public void setElevation(double paramDouble) {
    this.mElevation = paramDouble;
  }
  
  public void setHasAlmanac(boolean paramBoolean) {
    this.mHasAlmanac = paramBoolean;
  }
  
  public void setHasEphemeris(boolean paramBoolean) {
    this.mHasEphemeris = paramBoolean;
  }
  
  public void setPnr(int paramInt) {
    this.mPnr = paramInt;
  }
  
  public void setSnr(double paramDouble) {
    this.mSnr = paramDouble;
  }
  
  public void setUseInFix(boolean paramBoolean) {
    this.mUseInFix = paramBoolean;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\env\GPSStateline.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */