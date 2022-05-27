package org.jdeferred.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FutureCallable<V> implements Callable<V> {
  private final Future<V> future;
  
  public FutureCallable(Future<V> paramFuture) {
    this.future = paramFuture;
  }
  
  public V call() throws Exception {
    try {
      return this.future.get();
    } catch (InterruptedException interruptedException) {
      throw interruptedException;
    } catch (ExecutionException executionException) {
      if (executionException.getCause() instanceof Exception)
        throw (Exception)executionException.getCause(); 
      throw executionException;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\impl\FutureCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */