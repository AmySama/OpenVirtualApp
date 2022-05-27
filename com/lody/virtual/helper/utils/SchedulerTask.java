package com.lody.virtual.helper.utils;

import android.os.Handler;

public abstract class SchedulerTask implements Runnable {
  private long mDelay;
  
  private Handler mHandler;
  
  private final Runnable mInnerRunnable = new Runnable() {
      public void run() {
        SchedulerTask.this.run();
        if (SchedulerTask.this.mDelay > 0L)
          SchedulerTask.this.mHandler.postDelayed(this, SchedulerTask.this.mDelay); 
      }
    };
  
  public SchedulerTask(Handler paramHandler, long paramLong) {
    this.mHandler = paramHandler;
    this.mDelay = paramLong;
  }
  
  public void cancel() {
    this.mHandler.removeCallbacks(this.mInnerRunnable);
  }
  
  public void schedule() {
    this.mHandler.post(this.mInnerRunnable);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\SchedulerTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */