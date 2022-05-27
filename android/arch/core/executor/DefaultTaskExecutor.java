package android.arch.core.executor;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultTaskExecutor extends TaskExecutor {
  private ExecutorService mDiskIO = Executors.newFixedThreadPool(2);
  
  private final Object mLock = new Object();
  
  private volatile Handler mMainHandler;
  
  public void executeOnDiskIO(Runnable paramRunnable) {
    this.mDiskIO.execute(paramRunnable);
  }
  
  public boolean isMainThread() {
    boolean bool;
    if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void postToMainThread(Runnable paramRunnable) {
    if (this.mMainHandler == null)
      synchronized (this.mLock) {
        if (this.mMainHandler == null) {
          Handler handler = new Handler();
          this(Looper.getMainLooper());
          this.mMainHandler = handler;
        } 
      }  
    this.mMainHandler.post(paramRunnable);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\core\executor\DefaultTaskExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */