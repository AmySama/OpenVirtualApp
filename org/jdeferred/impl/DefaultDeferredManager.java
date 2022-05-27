package org.jdeferred.impl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DefaultDeferredManager extends AbstractDeferredManager {
  public static final boolean DEFAULT_AUTO_SUBMIT = true;
  
  private boolean autoSubmit = true;
  
  private final ExecutorService executorService = Executors.newCachedThreadPool();
  
  public DefaultDeferredManager() {}
  
  public DefaultDeferredManager(ExecutorService paramExecutorService) {}
  
  public boolean awaitTermination(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException {
    return this.executorService.awaitTermination(paramLong, paramTimeUnit);
  }
  
  public ExecutorService getExecutorService() {
    return this.executorService;
  }
  
  public boolean isAutoSubmit() {
    return this.autoSubmit;
  }
  
  public boolean isShutdown() {
    return this.executorService.isShutdown();
  }
  
  public boolean isTerminated() {
    return this.executorService.isTerminated();
  }
  
  public void setAutoSubmit(boolean paramBoolean) {
    this.autoSubmit = paramBoolean;
  }
  
  public void shutdown() {
    this.executorService.shutdown();
  }
  
  public List<Runnable> shutdownNow() {
    return this.executorService.shutdownNow();
  }
  
  protected void submit(Runnable paramRunnable) {
    this.executorService.submit(paramRunnable);
  }
  
  protected void submit(Callable<?> paramCallable) {
    this.executorService.submit(paramCallable);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\impl\DefaultDeferredManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */