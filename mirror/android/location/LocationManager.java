package mirror.android.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IInterface;
import java.util.HashMap;
import java.util.Map;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;
import mirror.RefObject;

public class LocationManager {
  public static Class<?> TYPE = RefClass.load(LocationManager.class, "android.location.LocationManager");
  
  public static RefObject<Map> mGnssNmeaListeners;
  
  public static RefObject<Map> mGnssStatusListeners;
  
  public static RefObject<Map> mGpsNmeaListeners;
  
  public static RefObject<Map> mGpsStatusListeners;
  
  public static RefObject<HashMap> mListeners;
  
  public static RefObject<HashMap> mNmeaListeners;
  
  public static RefObject<IInterface> mService;
  
  public static class GnssStatusListenerTransport {
    public static Class<?> TYPE = RefClass.load(GnssStatusListenerTransport.class, "android.location.LocationManager$GnssStatusListenerTransport");
    
    public static RefObject<Object> mGpsListener;
    
    public static RefObject<Object> mGpsNmeaListener;
    
    @MethodParams({int.class})
    public static RefMethod<Void> onFirstFix;
    
    public static RefMethod<Void> onGnssStarted;
    
    @MethodParams({long.class, String.class})
    public static RefMethod<Void> onNmeaReceived;
    
    @MethodParams({int.class, int[].class, float[].class, float[].class, float[].class})
    public static RefMethod<Void> onSvStatusChanged;
    
    public static RefObject<Object> this$0;
  }
  
  public static class GnssStatusListenerTransportO {
    public static Class<?> TYPE = RefClass.load(GnssStatusListenerTransportO.class, "android.location.LocationManager$GnssStatusListenerTransport");
    
    @MethodParams({int.class, int[].class, float[].class, float[].class, float[].class, float[].class})
    public static RefMethod<Void> onSvStatusChanged;
  }
  
  public static class GpsStatusListenerTransport {
    public static Class<?> TYPE = RefClass.load(GpsStatusListenerTransport.class, "android.location.LocationManager$GpsStatusListenerTransport");
    
    public static RefObject<Object> mListener;
    
    public static RefObject<Object> mNmeaListener;
    
    @MethodParams({int.class})
    public static RefMethod<Void> onFirstFix;
    
    public static RefMethod<Void> onGpsStarted;
    
    @MethodParams({long.class, String.class})
    public static RefMethod<Void> onNmeaReceived;
    
    @MethodParams({int.class, int[].class, float[].class, float[].class, float[].class, int.class, int.class, int.class})
    public static RefMethod<Void> onSvStatusChanged;
    
    public static RefObject<Object> this$0;
  }
  
  public static class GpsStatusListenerTransportOPPO_R815T {
    public static Class<?> TYPE = RefClass.load(GpsStatusListenerTransportOPPO_R815T.class, "android.location.LocationManager$GpsStatusListenerTransport");
    
    @MethodParams({int.class, int[].class, float[].class, float[].class, float[].class, int[].class, int[].class, int[].class, int.class})
    public static RefMethod<Void> onSvStatusChanged;
  }
  
  public static class GpsStatusListenerTransportSumsungS5 {
    public static Class<?> TYPE = RefClass.load(GpsStatusListenerTransportSumsungS5.class, "android.location.LocationManager$GpsStatusListenerTransport");
    
    @MethodParams({int.class, int[].class, float[].class, float[].class, float[].class, int.class, int.class, int.class, int[].class})
    public static RefMethod<Void> onSvStatusChanged;
  }
  
  public static class GpsStatusListenerTransportVIVO {
    public static Class<?> TYPE = RefClass.load(GpsStatusListenerTransportVIVO.class, "android.location.LocationManager$GpsStatusListenerTransport");
    
    @MethodParams({int.class, int[].class, float[].class, float[].class, float[].class, int.class, int.class, int.class, long[].class})
    public static RefMethod<Void> onSvStatusChanged;
  }
  
  public static class ListenerTransport {
    public static Class<?> TYPE = RefClass.load(ListenerTransport.class, "android.location.LocationManager$ListenerTransport");
    
    public static RefObject<LocationListener> mListener;
    
    @MethodParams({Location.class})
    public static RefMethod<Void> onLocationChanged;
    
    @MethodParams({String.class})
    public static RefMethod<Void> onProviderDisabled;
    
    @MethodParams({String.class})
    public static RefMethod<Void> onProviderEnabled;
    
    @MethodParams({String.class, int.class, Bundle.class})
    public static RefMethod<Void> onStatusChanged;
    
    public static RefObject<Object> this$0;
  }
  
  public static class LocationListenerTransport {
    public static Class<?> TYPE = RefClass.load(LocationListenerTransport.class, "android.location.LocationManager$LocationListenerTransport");
    
    public static RefObject<LocationListenerTransport> mListener;
    
    @MethodParams({Location.class})
    public static RefMethod<Void> onLocationChanged;
    
    @MethodParams({String.class})
    public static RefMethod<Void> onProviderDisabled;
    
    @MethodParams({String.class})
    public static RefMethod<Void> onProviderEnabled;
    
    @MethodParams({String.class, int.class, Bundle.class})
    public static RefMethod<Void> onStatusChanged;
    
    public static RefObject<Object> this$0;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\location\LocationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */