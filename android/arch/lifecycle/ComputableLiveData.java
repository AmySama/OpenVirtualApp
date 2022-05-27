package android.arch.lifecycle;

import android.arch.core.executor.ArchTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ComputableLiveData<T> {
  private AtomicBoolean mComputing = new AtomicBoolean(false);
  
  private final Executor mExecutor;
  
  private AtomicBoolean mInvalid = new AtomicBoolean(true);
  
  final Runnable mInvalidationRunnable = new Runnable() {
      public void run() {
        boolean bool = ComputableLiveData.this.mLiveData.hasActiveObservers();
        if (ComputableLiveData.this.mInvalid.compareAndSet(false, true) && bool)
          ComputableLiveData.this.mExecutor.execute(ComputableLiveData.this.mRefreshRunnable); 
      }
    };
  
  private final LiveData<T> mLiveData;
  
  final Runnable mRefreshRunnable = new Runnable() {
      public void run() {
        boolean bool;
        do {
          null = ComputableLiveData.this.mComputing;
          bool = false;
          if (!null.compareAndSet(false, true))
            continue; 
          null = null;
          bool = false;
          try {
            while (ComputableLiveData.this.mInvalid.compareAndSet(true, false)) {
              null = ComputableLiveData.this.compute();
              bool = true;
            } 
            if (bool)
              ComputableLiveData.this.mLiveData.postValue(null); 
          } finally {
            ComputableLiveData.this.mComputing.set(false);
          } 
        } while (bool && ComputableLiveData.this.mInvalid.get());
      }
    };
  
  public ComputableLiveData() {
    this(ArchTaskExecutor.getIOThreadExecutor());
  }
  
  public ComputableLiveData(Executor paramExecutor) {
    this.mExecutor = paramExecutor;
    this.mLiveData = new LiveData<T>() {
        protected void onActive() {
          ComputableLiveData.this.mExecutor.execute(ComputableLiveData.this.mRefreshRunnable);
        }
      };
  }
  
  protected abstract T compute();
  
  public LiveData<T> getLiveData() {
    return this.mLiveData;
  }
  
  public void invalidate() {
    ArchTaskExecutor.getInstance().executeOnMainThread(this.mInvalidationRunnable);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\ComputableLiveData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */