package org.jdeferred;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.jdeferred.impl.DeferredObject;

public class DeferredFutureTask<D, P> extends FutureTask<D> {
  protected final Deferred<D, Throwable, P> deferred = (Deferred<D, Throwable, P>)new DeferredObject();
  
  protected final DeferredManager.StartPolicy startPolicy = DeferredManager.StartPolicy.DEFAULT;
  
  public DeferredFutureTask(Runnable paramRunnable) {
    super(paramRunnable, null);
  }
  
  public DeferredFutureTask(Callable<D> paramCallable) {
    super(paramCallable);
  }
  
  public DeferredFutureTask(DeferredCallable<D, P> paramDeferredCallable) {
    super(paramDeferredCallable);
  }
  
  public DeferredFutureTask(DeferredRunnable<P> paramDeferredRunnable) {
    super(paramDeferredRunnable, null);
  }
  
  protected void done() {
    try {
      if (isCancelled()) {
        Deferred<D, Throwable, P> deferred = this.deferred;
        CancellationException cancellationException = new CancellationException();
        this();
        deferred.reject(cancellationException);
      } 
      D d = get();
      this.deferred.resolve(d);
    } catch (InterruptedException interruptedException) {
    
    } catch (ExecutionException executionException) {
      this.deferred.reject(executionException.getCause());
    } 
  }
  
  public DeferredManager.StartPolicy getStartPolicy() {
    return this.startPolicy;
  }
  
  public Promise<D, Throwable, P> promise() {
    return this.deferred.promise();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\DeferredFutureTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */