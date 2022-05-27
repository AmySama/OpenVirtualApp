package com.lody.virtual.remote.vloc;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import com.lody.virtual.client.env.VirtualGPSSatalines;
import com.lody.virtual.helper.utils.Reflect;

public class VLocation implements Parcelable {
  public static final Parcelable.Creator<VLocation> CREATOR = new Parcelable.Creator<VLocation>() {
      public VLocation createFromParcel(Parcel param1Parcel) {
        return new VLocation(param1Parcel);
      }
      
      public VLocation[] newArray(int param1Int) {
        return new VLocation[param1Int];
      }
    };
  
  public float accuracy = 0.0F;
  
  public double altitude = 0.0D;
  
  public float bearing;
  
  public double latitude = 0.0D;
  
  public double longitude = 0.0D;
  
  public float speed;
  
  public VLocation() {}
  
  public VLocation(double paramDouble1, double paramDouble2) {
    this.latitude = paramDouble1;
    this.longitude = paramDouble2;
  }
  
  public VLocation(Parcel paramParcel) {
    this.latitude = paramParcel.readDouble();
    this.longitude = paramParcel.readDouble();
    this.altitude = paramParcel.readDouble();
    this.accuracy = paramParcel.readFloat();
    this.speed = paramParcel.readFloat();
    this.bearing = paramParcel.readFloat();
  }
  
  public int describeContents() {
    return 0;
  }
  
  public double getLatitude() {
    return this.latitude;
  }
  
  public double getLongitude() {
    return this.longitude;
  }
  
  public boolean isEmpty() {
    boolean bool;
    if (this.latitude == 0.0D && this.longitude == 0.0D) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("VLocation{latitude=");
    stringBuilder.append(this.latitude);
    stringBuilder.append(", longitude=");
    stringBuilder.append(this.longitude);
    stringBuilder.append(", altitude=");
    stringBuilder.append(this.altitude);
    stringBuilder.append(", accuracy=");
    stringBuilder.append(this.accuracy);
    stringBuilder.append(", speed=");
    stringBuilder.append(this.speed);
    stringBuilder.append(", bearing=");
    stringBuilder.append(this.bearing);
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public Location toSysLocation() {
    Location location = new Location("gps");
    location.setAccuracy(8.0F);
    Bundle bundle = new Bundle();
    location.setBearing(this.bearing);
    Reflect.on(location).call("setIsFromMockProvider", new Object[] { Boolean.valueOf(false) });
    location.setLatitude(this.latitude);
    location.setLongitude(this.longitude);
    location.setSpeed(this.speed);
    location.setTime(System.currentTimeMillis());
    int i = VirtualGPSSatalines.get().getSvCount();
    bundle.putInt("satellites", i);
    bundle.putInt("satellitesvalue", i);
    location.setExtras(bundle);
    if (Build.VERSION.SDK_INT >= 17)
      try {
        Reflect.on(location).call("makeComplete");
      } catch (Exception exception) {
        location.setTime(System.currentTimeMillis());
        location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
      }  
    return location;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeDouble(this.latitude);
    paramParcel.writeDouble(this.longitude);
    paramParcel.writeDouble(this.altitude);
    paramParcel.writeFloat(this.accuracy);
    paramParcel.writeFloat(this.speed);
    paramParcel.writeFloat(this.bearing);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\vloc\VLocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */