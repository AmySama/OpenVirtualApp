package android.support.v7.recyclerview.extensions;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.util.DiffUtil;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class AsyncDifferConfig<T> {
  private final Executor mBackgroundThreadExecutor;
  
  private final DiffUtil.ItemCallback<T> mDiffCallback;
  
  private final Executor mMainThreadExecutor;
  
  private AsyncDifferConfig(Executor paramExecutor1, Executor paramExecutor2, DiffUtil.ItemCallback<T> paramItemCallback) {
    this.mMainThreadExecutor = paramExecutor1;
    this.mBackgroundThreadExecutor = paramExecutor2;
    this.mDiffCallback = paramItemCallback;
  }
  
  public Executor getBackgroundThreadExecutor() {
    return this.mBackgroundThreadExecutor;
  }
  
  public DiffUtil.ItemCallback<T> getDiffCallback() {
    return this.mDiffCallback;
  }
  
  public Executor getMainThreadExecutor() {
    return this.mMainThreadExecutor;
  }
  
  public static final class Builder<T> {
    private static Executor sDiffExecutor = null;
    
    private static final Object sExecutorLock = new Object();
    
    private static final Executor sMainThreadExecutor = new MainThreadExecutor();
    
    private Executor mBackgroundThreadExecutor;
    
    private final DiffUtil.ItemCallback<T> mDiffCallback;
    
    private Executor mMainThreadExecutor;
    
    static {
    
    }
    
    public Builder(DiffUtil.ItemCallback<T> param1ItemCallback) {
      this.mDiffCallback = param1ItemCallback;
    }
    
    public AsyncDifferConfig<T> build() {
      if (this.mMainThreadExecutor == null)
        this.mMainThreadExecutor = sMainThreadExecutor; 
      if (this.mBackgroundThreadExecutor == null)
        synchronized (sExecutorLock) {
          if (sDiffExecutor == null)
            sDiffExecutor = Executors.newFixedThreadPool(2); 
          this.mBackgroundThreadExecutor = sDiffExecutor;
        }  
      return new AsyncDifferConfig<T>(this.mMainThreadExecutor, this.mBackgroundThreadExecutor, this.mDiffCallback);
    }
    
    public Builder<T> setBackgroundThreadExecutor(Executor param1Executor) {
      this.mBackgroundThreadExecutor = param1Executor;
      return this;
    }
    
    public Builder<T> setMainThreadExecutor(Executor param1Executor) {
      this.mMainThreadExecutor = param1Executor;
      return this;
    }
    
    private static class MainThreadExecutor implements Executor {
      final Handler mHandler = new Handler(Looper.getMainLooper());
      
      private MainThreadExecutor() {}
      
      public void execute(Runnable param2Runnable) {
        this.mHandler.post(param2Runnable);
      }
    }
  }
  
  private static class MainThreadExecutor implements Executor {
    final Handler mHandler = new Handler(Looper.getMainLooper());
    
    private MainThreadExecutor() {}
    
    public void execute(Runnable param1Runnable) {
      this.mHandler.post(param1Runnable);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\recyclerview\extensions\AsyncDifferConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */