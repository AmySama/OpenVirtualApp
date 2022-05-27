package org.jdeferred;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.jdeferred.multiple.MasterProgress;
import org.jdeferred.multiple.MultipleResults;
import org.jdeferred.multiple.OneReject;

public interface DeferredManager {
  Promise<Void, Throwable, Void> when(Runnable paramRunnable);
  
  <D> Promise<D, Throwable, Void> when(Callable<D> paramCallable);
  
  <D> Promise<D, Throwable, Void> when(Future<D> paramFuture);
  
  <D, P> Promise<D, Throwable, P> when(DeferredCallable<D, P> paramDeferredCallable);
  
  <D, P> Promise<D, Throwable, P> when(DeferredFutureTask<D, P> paramDeferredFutureTask);
  
  <P> Promise<Void, Throwable, P> when(DeferredRunnable<P> paramDeferredRunnable);
  
  <D, F, P> Promise<D, F, P> when(Promise<D, F, P> paramPromise);
  
  Promise<MultipleResults, OneReject, MasterProgress> when(Runnable... paramVarArgs);
  
  Promise<MultipleResults, OneReject, MasterProgress> when(Callable<?>... paramVarArgs);
  
  Promise<MultipleResults, OneReject, MasterProgress> when(Future<?>... paramVarArgs);
  
  Promise<MultipleResults, OneReject, MasterProgress> when(DeferredCallable<?, ?>... paramVarArgs);
  
  Promise<MultipleResults, OneReject, MasterProgress> when(DeferredFutureTask<?, ?>... paramVarArgs);
  
  Promise<MultipleResults, OneReject, MasterProgress> when(DeferredRunnable<?>... paramVarArgs);
  
  Promise<MultipleResults, OneReject, MasterProgress> when(Promise... paramVarArgs);
  
  public enum StartPolicy {
    AUTO, DEFAULT, MANAUL;
    
    static {
      StartPolicy startPolicy = new StartPolicy("MANAUL", 2);
      MANAUL = startPolicy;
      $VALUES = new StartPolicy[] { DEFAULT, AUTO, startPolicy };
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\DeferredManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */