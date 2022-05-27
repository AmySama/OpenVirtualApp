package com.lody.virtual.client.ipc;

import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.proxies.location.MockLocationHelper;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.remote.vloc.VLocation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VLocationManager {
  private static VLocationManager sVLocationManager = new VLocationManager();
  
  private final List<Object> mGpsListeners = new ArrayList();
  
  private HandlerThread mHandlerThread;
  
  private final Map<Object, UpdateLocationTask> mLocationTaskMap = new HashMap<Object, UpdateLocationTask>();
  
  private Runnable mUpdateGpsStatusTask = new Runnable() {
      public void run() {
        synchronized (VLocationManager.this.mGpsListeners) {
          for (Object object : VLocationManager.this.mGpsListeners)
            VLocationManager.this.notifyGpsStatus(object); 
          VLocationManager.this.mWorkHandler.postDelayed(VLocationManager.this.mUpdateGpsStatusTask, 8000L);
          return;
        } 
      }
    };
  
  private Handler mWorkHandler;
  
  private VLocationManager() {
    MockLocationHelper.fakeGpsStatus((LocationManager)VirtualCore.get().getContext().getSystemService("location"));
  }
  
  private void checkWork() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mHandlerThread : Landroid/os/HandlerThread;
    //   4: ifnonnull -> 45
    //   7: aload_0
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield mHandlerThread : Landroid/os/HandlerThread;
    //   13: ifnonnull -> 35
    //   16: new android/os/HandlerThread
    //   19: astore_1
    //   20: aload_1
    //   21: ldc 'loc_thread'
    //   23: invokespecial <init> : (Ljava/lang/String;)V
    //   26: aload_0
    //   27: aload_1
    //   28: putfield mHandlerThread : Landroid/os/HandlerThread;
    //   31: aload_1
    //   32: invokevirtual start : ()V
    //   35: aload_0
    //   36: monitorexit
    //   37: goto -> 45
    //   40: astore_1
    //   41: aload_0
    //   42: monitorexit
    //   43: aload_1
    //   44: athrow
    //   45: aload_0
    //   46: getfield mWorkHandler : Landroid/os/Handler;
    //   49: ifnonnull -> 91
    //   52: aload_0
    //   53: monitorenter
    //   54: aload_0
    //   55: getfield mWorkHandler : Landroid/os/Handler;
    //   58: ifnonnull -> 81
    //   61: new android/os/Handler
    //   64: astore_1
    //   65: aload_1
    //   66: aload_0
    //   67: getfield mHandlerThread : Landroid/os/HandlerThread;
    //   70: invokevirtual getLooper : ()Landroid/os/Looper;
    //   73: invokespecial <init> : (Landroid/os/Looper;)V
    //   76: aload_0
    //   77: aload_1
    //   78: putfield mWorkHandler : Landroid/os/Handler;
    //   81: aload_0
    //   82: monitorexit
    //   83: goto -> 91
    //   86: astore_1
    //   87: aload_0
    //   88: monitorexit
    //   89: aload_1
    //   90: athrow
    //   91: return
    // Exception table:
    //   from	to	target	type
    //   9	35	40	finally
    //   35	37	40	finally
    //   41	43	40	finally
    //   54	81	86	finally
    //   81	83	86	finally
    //   87	89	86	finally
  }
  
  public static VLocationManager get() {
    return sVLocationManager;
  }
  
  private UpdateLocationTask getTask(Object paramObject) {
    synchronized (this.mLocationTaskMap) {
      paramObject = this.mLocationTaskMap.get(paramObject);
      return (UpdateLocationTask)paramObject;
    } 
  }
  
  private void notifyGpsStatus(final Object transport) {
    if (transport == null)
      return; 
    this.mWorkHandler.post(new Runnable() {
          public void run() {
            MockLocationHelper.invokeSvStatusChanged(transport);
            MockLocationHelper.invokeNmeaReceived(transport);
          }
        });
  }
  
  private boolean notifyLocation(final Object ListenerTransport, final Location location, boolean paramBoolean) {
    if (ListenerTransport == null)
      return false; 
    if (!paramBoolean)
      try {
        return true;
      } finally {
        ListenerTransport = null;
        ListenerTransport.printStackTrace();
      }  
    this.mWorkHandler.post(new Runnable() {
          public void run() {
            try {
            
            } finally {
              Exception exception = null;
            } 
          }
        });
    return true;
  }
  
  private void startGpsTask() {
    checkWork();
    stopGpsTask();
    this.mWorkHandler.postDelayed(this.mUpdateGpsStatusTask, 5000L);
  }
  
  private void stopGpsTask() {
    Handler handler = this.mWorkHandler;
    if (handler != null)
      handler.removeCallbacks(this.mUpdateGpsStatusTask); 
  }
  
  public void addGpsStatusListener(Object[] paramArrayOfObject) {
    Object object = paramArrayOfObject[0];
    MockLocationHelper.invokeSvStatusChanged(object);
    if (object != null)
      synchronized (this.mGpsListeners) {
        this.mGpsListeners.add(object);
      }  
    checkWork();
    notifyGpsStatus(object);
    startGpsTask();
  }
  
  public VLocation getCurAppLocation() {
    return getVirtualLocation(VClient.get().getCurrentPackage(), null, VUserHandle.myUserId());
  }
  
  public VLocation getLocation(String paramString, int paramInt) {
    return getVirtualLocation(paramString, null, paramInt);
  }
  
  public String getPackageName() {
    return VClient.get().getCurrentPackage();
  }
  
  public VLocation getVirtualLocation(String paramString, Location paramLocation, int paramInt) {
    try {
      return (VirtualLocationManager.get().getMode(paramInt, paramString) == 1) ? VirtualLocationManager.get().getGlobalLocation() : VirtualLocationManager.get().getLocation(paramInt, paramString);
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
  
  public boolean hasVirtualLocation(String paramString, int paramInt) {
    boolean bool = false;
    try {
      paramInt = VirtualLocationManager.get().getMode(paramInt, paramString);
      if (paramInt != 0)
        bool = true; 
      return bool;
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    } 
  }
  
  public boolean isProviderEnabled(String paramString) {
    return "gps".equals(paramString);
  }
  
  public void removeGpsStatusListener(Object[] paramArrayOfObject) {
    boolean bool = false;
    if (paramArrayOfObject[0] instanceof android.app.PendingIntent)
      return; 
    synchronized (this.mGpsListeners) {
      this.mGpsListeners.remove(paramArrayOfObject[0]);
      if (this.mGpsListeners.size() == 0)
        bool = true; 
      if (bool)
        stopGpsTask(); 
      return;
    } 
  }
  
  public void removeUpdates(Object[] paramArrayOfObject) {
    if (paramArrayOfObject[0] != null) {
      UpdateLocationTask updateLocationTask = getTask(paramArrayOfObject[0]);
      if (updateLocationTask != null)
        updateLocationTask.stop(); 
    } 
  }
  
  public void requestLocationUpdates(Object[] paramArrayOfObject) {
    int i;
    if (Build.VERSION.SDK_INT >= 17) {
      i = 1;
    } else {
      i = paramArrayOfObject.length - 1;
    } 
    Object object = paramArrayOfObject[i];
    if (object == null) {
      Log.e("VLoc", "ListenerTransport:null");
    } else {
      long l;
      if (Build.VERSION.SDK_INT >= 17) {
        try {
          l = ((Long)Reflect.on(paramArrayOfObject[0]).get("mInterval")).longValue();
        } finally {
          paramArrayOfObject = null;
        } 
      } else {
        l = ((Long)MethodParameterUtils.getFirstParam(paramArrayOfObject, Long.class)).longValue();
      } 
      VLocation vLocation = getCurAppLocation();
      checkWork();
      notifyLocation(object, vLocation.toSysLocation(), true);
      UpdateLocationTask updateLocationTask2 = getTask(object);
      UpdateLocationTask updateLocationTask1 = updateLocationTask2;
      if (updateLocationTask2 == null)
        synchronized (this.mLocationTaskMap) {
          updateLocationTask1 = new UpdateLocationTask();
          this(this, object, l);
          this.mLocationTaskMap.put(object, updateLocationTask1);
        }  
      updateLocationTask1.start();
    } 
  }
  
  private class UpdateLocationTask implements Runnable {
    private Object mListenerTransport;
    
    private volatile boolean mRunning;
    
    private long mTime;
    
    private UpdateLocationTask(Object param1Object, long param1Long) {
      this.mListenerTransport = param1Object;
      this.mTime = param1Long;
    }
    
    public void run() {
      if (this.mRunning) {
        VLocation vLocation = VLocationManager.this.getCurAppLocation();
        if (vLocation != null && VLocationManager.this.notifyLocation(this.mListenerTransport, vLocation.toSysLocation(), false))
          start(); 
      } 
    }
    
    public void start() {
      this.mRunning = true;
      VLocationManager.this.mWorkHandler.removeCallbacks(this);
      if (this.mTime > 0L) {
        VLocationManager.this.mWorkHandler.postDelayed(this, this.mTime);
      } else {
        VLocationManager.this.mWorkHandler.post(this);
      } 
    }
    
    public void stop() {
      this.mRunning = false;
      VLocationManager.this.mWorkHandler.removeCallbacks(this);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\ipc\VLocationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */