package com.lody.virtual.client.env;

import java.util.ArrayList;

public class VirtualGPSSatalines {
  private static final int BEIDOU_SVID_OFFSET = 200;
  
  private static final int CONSTELLATION_BEIDOU = 5;
  
  private static final int CONSTELLATION_GALILEO = 6;
  
  private static final int CONSTELLATION_GLONASS = 3;
  
  private static final int CONSTELLATION_GPS = 1;
  
  private static final int CONSTELLATION_QZSS = 4;
  
  private static final int CONSTELLATION_SBAS = 2;
  
  private static final int CONSTELLATION_TYPE_MASK = 15;
  
  private static final int CONSTELLATION_TYPE_SHIFT_WIDTH = 3;
  
  private static final int CONSTELLATION_UNKNOWN = 0;
  
  private static final int GLONASS_SVID_OFFSET = 64;
  
  private static final int GNSS_SV_FLAGS_HAS_ALMANAC_DATA = 2;
  
  private static final int GNSS_SV_FLAGS_HAS_EPHEMERIS_DATA = 1;
  
  private static final int GNSS_SV_FLAGS_NONE = 0;
  
  private static final int GNSS_SV_FLAGS_USED_IN_FIX = 4;
  
  private static VirtualGPSSatalines INSTANCE = new VirtualGPSSatalines();
  
  private static final int SBAS_SVID_OFFSET = -87;
  
  private static final int SVID_SHIFT_WIDTH = 7;
  
  private float[] carrierFreqs;
  
  private int mAlmanacMask;
  
  private float[] mAzimuths;
  
  private float[] mElevations;
  
  private int mEphemerisMask;
  
  private float[] mSnrs;
  
  private int mUsedInFixMask;
  
  private int[] pnrs;
  
  private int[] prnWithFlags;
  
  private int svCount;
  
  private VirtualGPSSatalines() {
    ArrayList<GPSStateline> arrayList = new ArrayList();
    arrayList.add(new GPSStateline(5, 1.0D, 5.0D, 112.0D, false, true, true, 0.0D));
    arrayList.add(new GPSStateline(13, 13.5D, 23.0D, 53.0D, true, true, true, 0.0D));
    arrayList.add(new GPSStateline(14, 19.1D, 6.0D, 247.0D, true, true, true, 0.0D));
    arrayList.add(new GPSStateline(15, 31.0D, 58.0D, 45.0D, true, true, true, 0.0D));
    arrayList.add(new GPSStateline(18, 0.0D, 52.0D, 309.0D, false, true, true, 0.0D));
    arrayList.add(new GPSStateline(20, 30.1D, 54.0D, 105.0D, true, true, true, 0.0D));
    arrayList.add(new GPSStateline(21, 33.2D, 56.0D, 251.0D, true, true, true, 0.0D));
    arrayList.add(new GPSStateline(22, 0.0D, 14.0D, 299.0D, false, true, true, 0.0D));
    arrayList.add(new GPSStateline(24, 25.9D, 57.0D, 157.0D, true, true, true, 0.0D));
    arrayList.add(new GPSStateline(27, 18.0D, 3.0D, 309.0D, true, true, true, 0.0D));
    arrayList.add(new GPSStateline(28, 18.2D, 3.0D, 42.0D, true, true, true, 0.0D));
    arrayList.add(new GPSStateline(41, 28.8D, 0.0D, 0.0D, false, false, false, 0.0D));
    arrayList.add(new GPSStateline(50, 29.2D, 0.0D, 0.0D, false, true, true, 0.0D));
    arrayList.add(new GPSStateline(67, 14.4D, 2.0D, 92.0D, false, false, false, 0.0D));
    arrayList.add(new GPSStateline(68, 21.2D, 45.0D, 60.0D, false, false, false, 0.0D));
    arrayList.add(new GPSStateline(69, 17.5D, 50.0D, 330.0D, false, true, true, 0.0D));
    arrayList.add(new GPSStateline(70, 22.4D, 7.0D, 291.0D, false, false, false, 0.0D));
    arrayList.add(new GPSStateline(77, 23.8D, 10.0D, 23.0D, true, true, true, 0.0D));
    arrayList.add(new GPSStateline(78, 18.0D, 47.0D, 70.0D, true, true, true, 0.0D));
    arrayList.add(new GPSStateline(79, 22.8D, 41.0D, 142.0D, true, true, true, 0.0D));
    arrayList.add(new GPSStateline(83, 0.2D, 9.0D, 212.0D, false, false, false, 0.0D));
    arrayList.add(new GPSStateline(84, 16.7D, 30.0D, 264.0D, true, true, true, 0.0D));
    arrayList.add(new GPSStateline(85, 12.1D, 20.0D, 317.0D, true, true, true, 0.0D));
    this.svCount = arrayList.size();
    this.pnrs = new int[arrayList.size()];
    boolean bool = false;
    byte b;
    for (b = 0; b < arrayList.size(); b++)
      this.pnrs[b] = ((GPSStateline)arrayList.get(b)).getPnr(); 
    this.mSnrs = new float[arrayList.size()];
    for (b = 0; b < arrayList.size(); b++)
      this.mSnrs[b] = (float)((GPSStateline)arrayList.get(b)).getSnr(); 
    this.mElevations = new float[arrayList.size()];
    for (b = 0; b < arrayList.size(); b++)
      this.mElevations[b] = (float)((GPSStateline)arrayList.get(b)).getElevation(); 
    this.mAzimuths = new float[arrayList.size()];
    for (b = 0; b < arrayList.size(); b++)
      this.mAzimuths[b] = (float)((GPSStateline)arrayList.get(b)).getAzimuth(); 
    this.carrierFreqs = new float[arrayList.size()];
    for (b = 0; b < arrayList.size(); b++)
      this.carrierFreqs[b] = (float)((GPSStateline)arrayList.get(b)).getCarrierFrequencyHz(); 
    this.prnWithFlags = new int[arrayList.size()];
    for (b = 0; b < arrayList.size(); b++) {
      GPSStateline gPSStateline = arrayList.get(b);
      this.prnWithFlags[b] = gPSStateline.getPnr() << 71 | 0x18;
    } 
    this.mEphemerisMask = 0;
    for (b = 0; b < arrayList.size(); b++) {
      if (((GPSStateline)arrayList.get(b)).isHasEphemeris()) {
        this.mEphemerisMask |= 1 << ((GPSStateline)arrayList.get(b)).getPnr() - 1;
        int[] arrayOfInt = this.prnWithFlags;
        arrayOfInt[b] = 0x1 | arrayOfInt[b];
      } 
    } 
    this.mAlmanacMask = 0;
    for (b = 0; b < arrayList.size(); b++) {
      if (((GPSStateline)arrayList.get(b)).isHasAlmanac()) {
        this.mAlmanacMask |= 1 << ((GPSStateline)arrayList.get(b)).getPnr() - 1;
        int[] arrayOfInt = this.prnWithFlags;
        arrayOfInt[b] = arrayOfInt[b] | 0x2;
      } 
    } 
    this.mUsedInFixMask = 0;
    for (b = bool; arrayList.size() > b; b++) {
      if (((GPSStateline)arrayList.get(b)).isUseInFix()) {
        this.mUsedInFixMask |= 1 << ((GPSStateline)arrayList.get(b)).getPnr() - 1;
        int[] arrayOfInt = this.prnWithFlags;
        arrayOfInt[b] = arrayOfInt[b] | 0x4;
      } 
    } 
  }
  
  public static VirtualGPSSatalines get() {
    return INSTANCE;
  }
  
  public int getAlmanacMask() {
    return this.mAlmanacMask;
  }
  
  public float[] getAzimuths() {
    return this.mAzimuths;
  }
  
  public float[] getCarrierFreqs() {
    return this.carrierFreqs;
  }
  
  public float[] getElevations() {
    return this.mElevations;
  }
  
  public int getEphemerisMask() {
    return this.mEphemerisMask;
  }
  
  public int[] getPrnWithFlags() {
    return this.prnWithFlags;
  }
  
  public int[] getPrns() {
    return this.pnrs;
  }
  
  public float[] getSnrs() {
    return this.mSnrs;
  }
  
  public int getSvCount() {
    return this.svCount;
  }
  
  public int getUsedInFixMask() {
    return this.mUsedInFixMask;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\env\VirtualGPSSatalines.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */