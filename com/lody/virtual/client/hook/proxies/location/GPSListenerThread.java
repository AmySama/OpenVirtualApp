package com.lody.virtual.client.hook.proxies.location;

import android.location.Location;
import android.os.Build;
import android.os.Handler;
import com.lody.virtual.client.ipc.VirtualLocationManager;
import com.lody.virtual.remote.vloc.VLocation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import mirror.android.location.LocationManager;

class GPSListenerThread extends TimerTask {
  private static GPSListenerThread INSTANCE = new GPSListenerThread();
  
  private Handler handler = new Handler();
  
  private boolean isRunning = false;
  
  private HashMap<Object, Long> listeners = new HashMap<Object, Long>();
  
  private Timer timer = new Timer();
  
  public static GPSListenerThread get() {
    return INSTANCE;
  }
  
  private void notifyGPSStatus(Map paramMap) {
    if (paramMap != null && !paramMap.isEmpty())
      for (Map.Entry entry : paramMap.entrySet()) {
        try {
          entry = (Map.Entry)entry.getValue();
        } finally {
          entry = null;
        } 
      }  
  }
  
  private void notifyLocation(Map paramMap) {
    if (paramMap != null)
      try {
        if (!paramMap.isEmpty()) {
          VLocation vLocation = VirtualLocationManager.get().getLocation();
          if (vLocation != null) {
            Location location = vLocation.toSysLocation();
            Iterator<Map.Entry> iterator = paramMap.entrySet().iterator();
            while (iterator.hasNext()) {
              Object object = ((Map.Entry)iterator.next()).getValue();
              if (object != null)
                try {
                  if (LocationManager.ListenerTransport.TYPE != null) {
                    LocationManager.ListenerTransport.onLocationChanged.call(object, new Object[] { location });
                    continue;
                  } 
                } finally {
                  object = null;
                }  
            } 
          } 
        } 
      } finally {
        paramMap = null;
      }  
  }
  
  private void notifyMNmeaListener(Map paramMap) {
    if (paramMap != null && !paramMap.isEmpty())
      for (Map.Entry entry : paramMap.entrySet()) {
        try {
          entry = (Map.Entry)entry.getValue();
          if (entry != null)
            MockLocationHelper.invokeNmeaReceived(entry); 
        } catch (Exception exception) {
          exception.printStackTrace();
        } 
      }  
  }
  
  public void addListenerTransport(Object paramObject) {
    // Byte code:
    //   0: aload_0
    //   1: getfield listeners : Ljava/util/HashMap;
    //   4: aload_1
    //   5: invokestatic currentTimeMillis : ()J
    //   8: invokestatic valueOf : (J)Ljava/lang/Long;
    //   11: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   14: pop
    //   15: aload_0
    //   16: getfield isRunning : Z
    //   19: ifne -> 60
    //   22: aload_0
    //   23: monitorenter
    //   24: aload_0
    //   25: getfield isRunning : Z
    //   28: ifne -> 50
    //   31: aload_0
    //   32: iconst_1
    //   33: putfield isRunning : Z
    //   36: aload_0
    //   37: getfield timer : Ljava/util/Timer;
    //   40: aload_0
    //   41: ldc2_w 1000
    //   44: ldc2_w 1000
    //   47: invokevirtual schedule : (Ljava/util/TimerTask;JJ)V
    //   50: aload_0
    //   51: monitorexit
    //   52: goto -> 60
    //   55: astore_1
    //   56: aload_0
    //   57: monitorexit
    //   58: aload_1
    //   59: athrow
    //   60: return
    // Exception table:
    //   from	to	target	type
    //   24	50	55	finally
    //   50	52	55	finally
    //   56	58	55	finally
  }
  
  public void removeListenerTransport(Object paramObject) {
    if (paramObject != null)
      this.listeners.remove(paramObject); 
  }
  
  public void run() {
    if (!this.listeners.isEmpty()) {
      if (VirtualLocationManager.get().getMode() == 0) {
        this.listeners.clear();
        return;
      } 
      for (Map.Entry<Object, Long> entry : this.listeners.entrySet()) {
        try {
          Map map;
          Object object = entry.getKey();
          if (Build.VERSION.SDK_INT >= 24) {
            map = (Map)LocationManager.mGnssNmeaListeners.get(object);
            notifyGPSStatus((Map)LocationManager.mGnssStatusListeners.get(object));
            notifyMNmeaListener(map);
            map = (Map)LocationManager.mGpsStatusListeners.get(object);
            notifyGPSStatus(map);
            notifyMNmeaListener((Map)LocationManager.mGpsNmeaListeners.get(object));
          } else {
            map = (Map)LocationManager.mGpsStatusListeners.get(object);
            notifyGPSStatus(map);
            notifyMNmeaListener((Map)LocationManager.mNmeaListeners.get(object));
          } 
          object = LocationManager.mListeners.get(object);
          if (map != null && !map.isEmpty()) {
            if (object == null || object.isEmpty()) {
              Handler handler = this.handler;
              Runnable runnable = new Runnable() {
                  public void run() {
                    GPSListenerThread.this.notifyLocation(listeners);
                  }
                };
              super(this, (Map)object);
              handler.postDelayed(runnable, 100L);
              continue;
            } 
            notifyLocation((Map)object);
          } 
        } catch (Exception exception) {
          exception.printStackTrace();
        } 
      } 
    } 
  }
  
  public void stop() {
    this.timer.cancel();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\location\GPSListenerThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */