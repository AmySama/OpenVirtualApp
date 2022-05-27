package android.arch.lifecycle;

import android.os.Handler;

public class ServiceLifecycleDispatcher {
  private final Handler mHandler;
  
  private DispatchRunnable mLastDispatchRunnable;
  
  private final LifecycleRegistry mRegistry;
  
  public ServiceLifecycleDispatcher(LifecycleOwner paramLifecycleOwner) {
    this.mRegistry = new LifecycleRegistry(paramLifecycleOwner);
    this.mHandler = new Handler();
  }
  
  private void postDispatchRunnable(Lifecycle.Event paramEvent) {
    DispatchRunnable dispatchRunnable2 = this.mLastDispatchRunnable;
    if (dispatchRunnable2 != null)
      dispatchRunnable2.run(); 
    DispatchRunnable dispatchRunnable1 = new DispatchRunnable(this.mRegistry, paramEvent);
    this.mLastDispatchRunnable = dispatchRunnable1;
    this.mHandler.postAtFrontOfQueue(dispatchRunnable1);
  }
  
  public Lifecycle getLifecycle() {
    return this.mRegistry;
  }
  
  public void onServicePreSuperOnBind() {
    postDispatchRunnable(Lifecycle.Event.ON_START);
  }
  
  public void onServicePreSuperOnCreate() {
    postDispatchRunnable(Lifecycle.Event.ON_CREATE);
  }
  
  public void onServicePreSuperOnDestroy() {
    postDispatchRunnable(Lifecycle.Event.ON_STOP);
    postDispatchRunnable(Lifecycle.Event.ON_DESTROY);
  }
  
  public void onServicePreSuperOnStart() {
    postDispatchRunnable(Lifecycle.Event.ON_START);
  }
  
  static class DispatchRunnable implements Runnable {
    final Lifecycle.Event mEvent;
    
    private final LifecycleRegistry mRegistry;
    
    private boolean mWasExecuted = false;
    
    DispatchRunnable(LifecycleRegistry param1LifecycleRegistry, Lifecycle.Event param1Event) {
      this.mRegistry = param1LifecycleRegistry;
      this.mEvent = param1Event;
    }
    
    public void run() {
      if (!this.mWasExecuted) {
        this.mRegistry.handleLifecycleEvent(this.mEvent);
        this.mWasExecuted = true;
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\ServiceLifecycleDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */