package org.jdeferred.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.jdeferred.DeferredCallable;
import org.jdeferred.DeferredFutureTask;
import org.jdeferred.DeferredManager;
import org.jdeferred.DeferredRunnable;
import org.jdeferred.Promise;
import org.jdeferred.multiple.MasterDeferredObject;
import org.jdeferred.multiple.MasterProgress;
import org.jdeferred.multiple.MultipleResults;
import org.jdeferred.multiple.OneReject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDeferredManager implements DeferredManager {
  protected final Logger log = LoggerFactory.getLogger(AbstractDeferredManager.class);
  
  protected void assertNotEmpty(Object[] paramArrayOfObject) {
    if (paramArrayOfObject != null && paramArrayOfObject.length != 0)
      return; 
    throw new IllegalArgumentException("Arguments is null or its length is empty");
  }
  
  public abstract boolean isAutoSubmit();
  
  protected abstract void submit(Runnable paramRunnable);
  
  protected abstract void submit(Callable paramCallable);
  
  public Promise<Void, Throwable, Void> when(Runnable paramRunnable) {
    return when(new DeferredFutureTask(paramRunnable));
  }
  
  public <D> Promise<D, Throwable, Void> when(Callable<D> paramCallable) {
    return when(new DeferredFutureTask(paramCallable));
  }
  
  public <D> Promise<D, Throwable, Void> when(final Future<D> future) {
    return when(new DeferredCallable<D, Void>(DeferredManager.StartPolicy.AUTO) {
          public D call() throws Exception {
            try {
              return (D)future.get();
            } catch (InterruptedException interruptedException) {
              throw interruptedException;
            } catch (ExecutionException executionException) {
              if (executionException.getCause() instanceof Exception)
                throw (Exception)executionException.getCause(); 
              throw executionException;
            } 
          }
        });
  }
  
  public <D, P> Promise<D, Throwable, P> when(DeferredCallable<D, P> paramDeferredCallable) {
    return when(new DeferredFutureTask(paramDeferredCallable));
  }
  
  public <D, P> Promise<D, Throwable, P> when(DeferredFutureTask<D, P> paramDeferredFutureTask) {
    if (paramDeferredFutureTask.getStartPolicy() == DeferredManager.StartPolicy.AUTO || (paramDeferredFutureTask.getStartPolicy() == DeferredManager.StartPolicy.DEFAULT && isAutoSubmit()))
      submit((Runnable)paramDeferredFutureTask); 
    return paramDeferredFutureTask.promise();
  }
  
  public <P> Promise<Void, Throwable, P> when(DeferredRunnable<P> paramDeferredRunnable) {
    return when(new DeferredFutureTask(paramDeferredRunnable));
  }
  
  public <D, F, P> Promise<D, F, P> when(Promise<D, F, P> paramPromise) {
    return paramPromise;
  }
  
  public Promise<MultipleResults, OneReject, MasterProgress> when(Runnable... paramVarArgs) {
    assertNotEmpty((Object[])paramVarArgs);
    Promise[] arrayOfPromise = new Promise[paramVarArgs.length];
    for (byte b = 0; b < paramVarArgs.length; b++) {
      if (paramVarArgs[b] instanceof DeferredRunnable) {
        arrayOfPromise[b] = when((DeferredRunnable)paramVarArgs[b]);
      } else {
        arrayOfPromise[b] = when(paramVarArgs[b]);
      } 
    } 
    return when(arrayOfPromise);
  }
  
  public Promise<MultipleResults, OneReject, MasterProgress> when(Callable<?>... paramVarArgs) {
    assertNotEmpty((Object[])paramVarArgs);
    Promise[] arrayOfPromise = new Promise[paramVarArgs.length];
    for (byte b = 0; b < paramVarArgs.length; b++) {
      if (paramVarArgs[b] instanceof DeferredCallable) {
        arrayOfPromise[b] = when((DeferredCallable)paramVarArgs[b]);
      } else {
        arrayOfPromise[b] = when(paramVarArgs[b]);
      } 
    } 
    return when(arrayOfPromise);
  }
  
  public Promise<MultipleResults, OneReject, MasterProgress> when(Future<?>... paramVarArgs) {
    assertNotEmpty((Object[])paramVarArgs);
    Promise[] arrayOfPromise = new Promise[paramVarArgs.length];
    for (byte b = 0; b < paramVarArgs.length; b++)
      arrayOfPromise[b] = when(paramVarArgs[b]); 
    return when(arrayOfPromise);
  }
  
  public Promise<MultipleResults, OneReject, MasterProgress> when(DeferredCallable<?, ?>... paramVarArgs) {
    assertNotEmpty((Object[])paramVarArgs);
    Promise[] arrayOfPromise = new Promise[paramVarArgs.length];
    for (byte b = 0; b < paramVarArgs.length; b++)
      arrayOfPromise[b] = when(paramVarArgs[b]); 
    return when(arrayOfPromise);
  }
  
  public Promise<MultipleResults, OneReject, MasterProgress> when(DeferredFutureTask<?, ?>... paramVarArgs) {
    assertNotEmpty((Object[])paramVarArgs);
    Promise[] arrayOfPromise = new Promise[paramVarArgs.length];
    for (byte b = 0; b < paramVarArgs.length; b++)
      arrayOfPromise[b] = when(paramVarArgs[b]); 
    return when(arrayOfPromise);
  }
  
  public Promise<MultipleResults, OneReject, MasterProgress> when(DeferredRunnable<?>... paramVarArgs) {
    assertNotEmpty((Object[])paramVarArgs);
    Promise[] arrayOfPromise = new Promise[paramVarArgs.length];
    for (byte b = 0; b < paramVarArgs.length; b++)
      arrayOfPromise[b] = when(paramVarArgs[b]); 
    return when(arrayOfPromise);
  }
  
  public Promise<MultipleResults, OneReject, MasterProgress> when(Promise... paramVarArgs) {
    assertNotEmpty((Object[])paramVarArgs);
    return (new MasterDeferredObject(paramVarArgs)).promise();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\impl\AbstractDeferredManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */