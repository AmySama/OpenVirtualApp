package com.lody.virtual.client.core;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.lody.virtual.client.env.VirtualRuntime;

public class ANRWatchDog extends Thread {
  private static final int ANR_TIMEOUT = 5000;
  
  private static final int MESSAGE_WATCHDOG_TIME_TICK = 0;
  
  private static int lastTimeTick = -1;
  
  private static int timeTick;
  
  private boolean makeCrash;
  
  private final Handler watchDogHandler = new Handler() {
      public void handleMessage(Message param1Message) {
        ANRWatchDog.access$008();
        ANRWatchDog.access$002(ANRWatchDog.timeTick % Integer.MAX_VALUE);
      }
    };
  
  public ANRWatchDog() {
    this(false);
  }
  
  public ANRWatchDog(boolean paramBoolean) {
    this.makeCrash = paramBoolean;
  }
  
  private void triggerANR() {
    if (this.makeCrash)
      throw new ANRException(); 
    try {
      ANRException aNRException = new ANRException();
      this();
      throw aNRException;
    } catch (ANRException aNRException) {
      aNRException.printStackTrace();
      return;
    } 
  }
  
  public void run() {
    while (true) {
      this.watchDogHandler.sendEmptyMessage(0);
      try {
        Thread.sleep(5000L);
      } catch (InterruptedException interruptedException) {
        interruptedException.printStackTrace();
      } 
      int i = timeTick;
      if (i == lastTimeTick) {
        triggerANR();
        continue;
      } 
      lastTimeTick = i;
    } 
  }
  
  public static class ANRException extends RuntimeException {
    public ANRException() {
      super(stringBuilder.toString());
      setStackTrace(Looper.getMainLooper().getThread().getStackTrace());
    }
    
    private static String getAnrDesc() {
      String str;
      if (VirtualCore.get().isVAppProcess()) {
        str = VirtualRuntime.getProcessName();
      } else {
        str = VirtualCore.get().getProcessName();
      } 
      return str;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\core\ANRWatchDog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */