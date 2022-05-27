package com.lody.virtual.client.hook.proxies.location;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

class GPSStatusListenerThread extends TimerTask {
  private static GPSStatusListenerThread INSTANCE = new GPSStatusListenerThread();
  
  private boolean isRunning = false;
  
  private Map<Object, Long> listeners = new HashMap<Object, Long>();
  
  private Timer timer = new Timer();
  
  public static GPSStatusListenerThread get() {
    return INSTANCE;
  }
  
  public void addListenerTransport(Object paramObject) {
    // Byte code:
    //   0: aload_0
    //   1: getfield isRunning : Z
    //   4: ifne -> 45
    //   7: aload_0
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield isRunning : Z
    //   13: ifne -> 35
    //   16: aload_0
    //   17: iconst_1
    //   18: putfield isRunning : Z
    //   21: aload_0
    //   22: getfield timer : Ljava/util/Timer;
    //   25: aload_0
    //   26: ldc2_w 100
    //   29: ldc2_w 800
    //   32: invokevirtual schedule : (Ljava/util/TimerTask;JJ)V
    //   35: aload_0
    //   36: monitorexit
    //   37: goto -> 45
    //   40: astore_1
    //   41: aload_0
    //   42: monitorexit
    //   43: aload_1
    //   44: athrow
    //   45: aload_0
    //   46: getfield listeners : Ljava/util/Map;
    //   49: aload_1
    //   50: invokestatic currentTimeMillis : ()J
    //   53: invokestatic valueOf : (J)Ljava/lang/Long;
    //   56: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   61: pop
    //   62: return
    // Exception table:
    //   from	to	target	type
    //   9	35	40	finally
    //   35	37	40	finally
    //   41	43	40	finally
  }
  
  public void removeListenerTransport(Object paramObject) {
    if (paramObject != null)
      this.listeners.remove(paramObject); 
  }
  
  public void run() {
    if (!this.listeners.isEmpty())
      for (Map.Entry<Object, Long> entry : this.listeners.entrySet()) {
        try {
          entry = (Map.Entry<Object, Long>)entry.getKey();
          MockLocationHelper.invokeSvStatusChanged(entry);
          MockLocationHelper.invokeNmeaReceived(entry);
        } finally {
          entry = null;
        } 
      }  
  }
  
  public void stop() {
    this.timer.cancel();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\location\GPSStatusListenerThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */