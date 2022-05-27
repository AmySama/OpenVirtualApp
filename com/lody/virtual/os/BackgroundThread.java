package com.lody.virtual.os;

import android.os.Handler;
import android.os.HandlerThread;

public final class BackgroundThread extends HandlerThread {
  private static Handler sHandler;
  
  private static BackgroundThread sInstance;
  
  private BackgroundThread() {
    super("va.android.bg", 10);
  }
  
  private static void ensureThreadLocked() {
    if (sInstance == null) {
      BackgroundThread backgroundThread = new BackgroundThread();
      sInstance = backgroundThread;
      backgroundThread.start();
      sHandler = new Handler(sInstance.getLooper());
    } 
  }
  
  public static BackgroundThread get() {
    // Byte code:
    //   0: ldc com/lody/virtual/os/BackgroundThread
    //   2: monitorenter
    //   3: invokestatic ensureThreadLocked : ()V
    //   6: getstatic com/lody/virtual/os/BackgroundThread.sInstance : Lcom/lody/virtual/os/BackgroundThread;
    //   9: astore_0
    //   10: ldc com/lody/virtual/os/BackgroundThread
    //   12: monitorexit
    //   13: aload_0
    //   14: areturn
    //   15: astore_0
    //   16: ldc com/lody/virtual/os/BackgroundThread
    //   18: monitorexit
    //   19: aload_0
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   3	13	15	finally
    //   16	19	15	finally
  }
  
  public static Handler getHandler() {
    // Byte code:
    //   0: ldc com/lody/virtual/os/BackgroundThread
    //   2: monitorenter
    //   3: invokestatic ensureThreadLocked : ()V
    //   6: getstatic com/lody/virtual/os/BackgroundThread.sHandler : Landroid/os/Handler;
    //   9: astore_0
    //   10: ldc com/lody/virtual/os/BackgroundThread
    //   12: monitorexit
    //   13: aload_0
    //   14: areturn
    //   15: astore_0
    //   16: ldc com/lody/virtual/os/BackgroundThread
    //   18: monitorexit
    //   19: aload_0
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   3	13	15	finally
    //   16	19	15	finally
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\os\BackgroundThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */