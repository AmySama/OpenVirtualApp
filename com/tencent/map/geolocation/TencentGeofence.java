package com.tencent.map.geolocation;

import android.os.SystemClock;
import android.text.TextUtils;
import java.util.Locale;

public class TencentGeofence {
  private final double a;
  
  private final double b;
  
  private final float c;
  
  private final long d;
  
  private final String e;
  
  private final long f;
  
  private TencentGeofence(double paramDouble1, double paramDouble2, float paramFloat, long paramLong, String paramString) {
    this.a = paramDouble1;
    this.b = paramDouble2;
    this.c = paramFloat;
    this.f = paramLong;
    this.d = SystemClock.elapsedRealtime() + paramLong;
    this.e = paramString;
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null)
      return false; 
    if (getClass() != paramObject.getClass())
      return false; 
    TencentGeofence tencentGeofence = (TencentGeofence)paramObject;
    if (Double.doubleToLongBits(this.a) != Double.doubleToLongBits(tencentGeofence.a))
      return false; 
    if (Double.doubleToLongBits(this.b) != Double.doubleToLongBits(tencentGeofence.b))
      return false; 
    paramObject = this.e;
    if (paramObject == null) {
      if (tencentGeofence.e != null)
        return false; 
    } else if (!paramObject.equals(tencentGeofence.e)) {
      return false;
    } 
    return true;
  }
  
  public long getDuration() {
    return this.f;
  }
  
  public long getExpireAt() {
    return this.d;
  }
  
  public double getLatitude() {
    return this.a;
  }
  
  public double getLongitude() {
    return this.b;
  }
  
  public float getRadius() {
    return this.c;
  }
  
  public String getTag() {
    return this.e;
  }
  
  public int hashCode() {
    int k;
    long l = Double.doubleToLongBits(this.a);
    int i = (int)(l ^ l >>> 32L);
    l = Double.doubleToLongBits(this.b);
    int j = (int)(l ^ l >>> 32L);
    String str = this.e;
    if (str == null) {
      k = 0;
    } else {
      k = str.hashCode();
    } 
    return ((i + 31) * 31 + j) * 31 + k;
  }
  
  public String toString() {
    return String.format(Locale.US, "TencentGeofence[tag=%s, type=%s, loc=(%.6f, %.6f), radius=%.2fm life=%.2fs]", new Object[] { this.e, "CIRCLE", Double.valueOf(this.a), Double.valueOf(this.b), Float.valueOf(this.c), Double.valueOf((this.d - SystemClock.elapsedRealtime()) / 1000.0D) });
  }
  
  public static class Builder {
    private double a;
    
    private double b;
    
    private float c;
    
    private long d;
    
    private String e;
    
    public TencentGeofence build() {
      return new TencentGeofence(this.a, this.b, this.c, this.d, this.e, (byte)0);
    }
    
    public Builder setCircularRegion(double param1Double1, double param1Double2, float param1Float) {
      if (param1Float > 0.0F) {
        if (param1Double1 <= 90.0D && param1Double1 >= -90.0D) {
          if (param1Double2 <= 180.0D && param1Double2 >= -180.0D) {
            this.a = param1Double1;
            this.b = param1Double2;
            this.c = param1Float;
            return this;
          } 
          StringBuilder stringBuilder2 = new StringBuilder("invalid longitude: ");
          stringBuilder2.append(param1Double2);
          throw new IllegalArgumentException(stringBuilder2.toString());
        } 
        StringBuilder stringBuilder1 = new StringBuilder("invalid latitude: ");
        stringBuilder1.append(param1Double1);
        throw new IllegalArgumentException(stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder("invalid radius: ");
      stringBuilder.append(param1Float);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    public Builder setExpirationDuration(long param1Long) {
      if (param1Long >= 0L) {
        this.d = param1Long;
        return this;
      } 
      StringBuilder stringBuilder = new StringBuilder("invalid duration: ");
      stringBuilder.append(param1Long);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    public Builder setTag(String param1String) {
      if (!TextUtils.isEmpty(param1String)) {
        this.e = param1String;
        return this;
      } 
      throw null;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\map\geolocation\TencentGeofence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */