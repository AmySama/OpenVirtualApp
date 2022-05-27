package android.support.v7.app;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import java.util.Calendar;

class TwilightManager {
  private static final int SUNRISE = 6;
  
  private static final int SUNSET = 22;
  
  private static final String TAG = "TwilightManager";
  
  private static TwilightManager sInstance;
  
  private final Context mContext;
  
  private final LocationManager mLocationManager;
  
  private final TwilightState mTwilightState = new TwilightState();
  
  TwilightManager(Context paramContext, LocationManager paramLocationManager) {
    this.mContext = paramContext;
    this.mLocationManager = paramLocationManager;
  }
  
  static TwilightManager getInstance(Context paramContext) {
    if (sInstance == null) {
      paramContext = paramContext.getApplicationContext();
      sInstance = new TwilightManager(paramContext, (LocationManager)paramContext.getSystemService("location"));
    } 
    return sInstance;
  }
  
  private Location getLastKnownLocation() {
    Location location2;
    int i = PermissionChecker.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION");
    Location location1 = null;
    if (i == 0) {
      location2 = getLastKnownLocationForProvider("network");
    } else {
      location2 = null;
    } 
    if (PermissionChecker.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") == 0)
      location1 = getLastKnownLocationForProvider("gps"); 
    if (location1 != null && location2 != null) {
      Location location = location2;
      if (location1.getTime() > location2.getTime())
        location = location1; 
      return location;
    } 
    if (location1 != null)
      location2 = location1; 
    return location2;
  }
  
  private Location getLastKnownLocationForProvider(String paramString) {
    try {
      if (this.mLocationManager.isProviderEnabled(paramString))
        return this.mLocationManager.getLastKnownLocation(paramString); 
    } catch (Exception exception) {
      Log.d("TwilightManager", "Failed to get last known location", exception);
    } 
    return null;
  }
  
  private boolean isStateValid() {
    boolean bool;
    if (this.mTwilightState.nextUpdate > System.currentTimeMillis()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  static void setInstance(TwilightManager paramTwilightManager) {
    sInstance = paramTwilightManager;
  }
  
  private void updateState(Location paramLocation) {
    boolean bool;
    TwilightState twilightState = this.mTwilightState;
    long l1 = System.currentTimeMillis();
    TwilightCalculator twilightCalculator = TwilightCalculator.getInstance();
    twilightCalculator.calculateTwilight(l1 - 86400000L, paramLocation.getLatitude(), paramLocation.getLongitude());
    long l2 = twilightCalculator.sunset;
    twilightCalculator.calculateTwilight(l1, paramLocation.getLatitude(), paramLocation.getLongitude());
    if (twilightCalculator.state == 1) {
      bool = true;
    } else {
      bool = false;
    } 
    long l3 = twilightCalculator.sunrise;
    long l4 = twilightCalculator.sunset;
    twilightCalculator.calculateTwilight(86400000L + l1, paramLocation.getLatitude(), paramLocation.getLongitude());
    long l5 = twilightCalculator.sunrise;
    if (l3 == -1L || l4 == -1L) {
      l1 = 43200000L + l1;
    } else {
      if (l1 > l4) {
        l1 = 0L + l5;
      } else if (l1 > l3) {
        l1 = 0L + l4;
      } else {
        l1 = 0L + l3;
      } 
      l1 += 60000L;
    } 
    twilightState.isNight = bool;
    twilightState.yesterdaySunset = l2;
    twilightState.todaySunrise = l3;
    twilightState.todaySunset = l4;
    twilightState.tomorrowSunrise = l5;
    twilightState.nextUpdate = l1;
  }
  
  boolean isNight() {
    TwilightState twilightState = this.mTwilightState;
    if (isStateValid())
      return twilightState.isNight; 
    Location location = getLastKnownLocation();
    if (location != null) {
      updateState(location);
      return twilightState.isNight;
    } 
    Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
    int i = Calendar.getInstance().get(11);
    return (i < 6 || i >= 22);
  }
  
  private static class TwilightState {
    boolean isNight;
    
    long nextUpdate;
    
    long todaySunrise;
    
    long todaySunset;
    
    long tomorrowSunrise;
    
    long yesterdaySunset;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\app\TwilightManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */