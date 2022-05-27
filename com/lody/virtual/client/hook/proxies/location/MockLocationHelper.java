package com.lody.virtual.client.hook.proxies.location;

import android.location.GpsStatus;
import android.location.LocationManager;
import android.os.Build;
import com.lody.virtual.client.env.VirtualGPSSatalines;
import com.lody.virtual.client.ipc.VLocationManager;
import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.remote.vloc.VLocation;
import java.util.Iterator;
import java.util.Map;
import mirror.android.location.GpsStatus;
import mirror.android.location.GpsStatusL;
import mirror.android.location.LocationManager;

public class MockLocationHelper {
  public static String checksum(String paramString) {
    String str;
    if (paramString.startsWith("$")) {
      str = paramString.substring(1);
    } else {
      str = paramString;
    } 
    byte b = 0;
    int i = 0;
    while (b < str.length()) {
      i ^= (byte)str.charAt(b);
      b++;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("*");
    stringBuilder.append(String.format("%02X", new Object[] { Integer.valueOf(i) }).toLowerCase());
    return stringBuilder.toString();
  }
  
  public static void fakeGpsStatus(LocationManager paramLocationManager) {
    if (Build.VERSION.SDK_INT >= 24) {
      fakeGpsStatusN(paramLocationManager);
      return;
    } 
    LocationManager locationManager = null;
    try {
      GpsStatus gpsStatus = (GpsStatus)Reflect.on(paramLocationManager).get("mGpsStatus");
    } finally {
      paramLocationManager = null;
    } 
    VirtualGPSSatalines virtualGPSSatalines = VirtualGPSSatalines.get();
    int i = virtualGPSSatalines.getSvCount();
    float[] arrayOfFloat1 = virtualGPSSatalines.getSnrs();
    int[] arrayOfInt = virtualGPSSatalines.getPrns();
    float[] arrayOfFloat2 = virtualGPSSatalines.getElevations();
    float[] arrayOfFloat3 = virtualGPSSatalines.getAzimuths();
    try {
      int[] arrayOfInt1;
      int[] arrayOfInt2;
      if (GpsStatusL.setStatus != null) {
        int j = virtualGPSSatalines.getSvCount();
        int k = (virtualGPSSatalines.getPrns()).length;
        float[] arrayOfFloat4 = virtualGPSSatalines.getElevations();
        float[] arrayOfFloat5 = virtualGPSSatalines.getAzimuths();
        arrayOfInt1 = new int[k];
        byte b;
        for (b = 0; b < k; b++)
          arrayOfInt1[b] = virtualGPSSatalines.getEphemerisMask(); 
        arrayOfInt2 = new int[k];
        for (b = 0; b < k; b++)
          arrayOfInt2[b] = virtualGPSSatalines.getAlmanacMask(); 
        int[] arrayOfInt3 = new int[k];
        for (b = 0; b < k; b++)
          arrayOfInt3[b] = virtualGPSSatalines.getUsedInFixMask(); 
        GpsStatusL.setStatus.call(paramLocationManager, new Object[] { Integer.valueOf(j), arrayOfInt, arrayOfFloat1, arrayOfFloat4, arrayOfFloat5, arrayOfInt1, arrayOfInt2, arrayOfInt3 });
      } else if (GpsStatus.setStatus != null) {
        int j = virtualGPSSatalines.getEphemerisMask();
        int m = virtualGPSSatalines.getAlmanacMask();
        int k = virtualGPSSatalines.getUsedInFixMask();
        GpsStatus.setStatus.call(paramLocationManager, new Object[] { Integer.valueOf(i), arrayOfInt, arrayOfFloat1, arrayOfInt1, arrayOfInt2, Integer.valueOf(j), Integer.valueOf(m), Integer.valueOf(k) });
      } 
    } catch (Exception exception) {}
  }
  
  public static void fakeGpsStatusN(LocationManager paramLocationManager) {
    if (LocationManager.mGpsStatusListeners == null)
      return; 
    Iterator iterator = ((Map)LocationManager.mGpsStatusListeners.get(paramLocationManager)).values().iterator();
    if (iterator.hasNext())
      invokeSvStatusChanged(iterator.next()); 
  }
  
  public static String getGPSLat(double paramDouble) {
    int i = (int)paramDouble;
    paramDouble = (paramDouble - i) * 60.0D;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(i);
    stringBuilder.append(leftZeroPad((int)paramDouble, 2));
    stringBuilder.append(":");
    stringBuilder.append(String.valueOf(paramDouble).substring(2));
    return stringBuilder.toString();
  }
  
  private static String getNorthWest(VLocation paramVLocation) {
    return (paramVLocation.getLatitude() > 0.0D) ? "N" : "S";
  }
  
  private static String getSouthEast(VLocation paramVLocation) {
    return (paramVLocation.getLongitude() > 0.0D) ? "E" : "W";
  }
  
  public static void invokeNmeaReceived(Object paramObject) {
    if (paramObject != null) {
      VirtualGPSSatalines virtualGPSSatalines = VirtualGPSSatalines.get();
      try {
        VLocation vLocation = VLocationManager.get().getCurAppLocation();
      } finally {
        paramObject = null;
      } 
    } 
  }
  
  public static void invokeSvStatusChanged(Object paramObject) {
    if (paramObject != null) {
      VirtualGPSSatalines virtualGPSSatalines = VirtualGPSSatalines.get();
      try {
        Class<?> clazz = paramObject.getClass();
      } finally {
        paramObject = null;
      } 
    } 
  }
  
  private static String leftZeroPad(int paramInt1, int paramInt2) {
    return leftZeroPad(String.valueOf(paramInt1), paramInt2);
  }
  
  private static String leftZeroPad(String paramString, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder(paramInt);
    byte b1 = 0;
    byte b2 = 0;
    if (paramString == null) {
      for (b1 = b2; b1 < paramInt; b1++)
        stringBuilder.append('0'); 
    } else {
      while (b1 < paramInt - paramString.length()) {
        stringBuilder.append('0');
        b1++;
      } 
      stringBuilder.append(paramString);
    } 
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\location\MockLocationHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */