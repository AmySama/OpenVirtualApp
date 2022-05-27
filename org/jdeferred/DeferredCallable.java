package org.jdeferred;

import java.util.concurrent.Callable;
import org.jdeferred.impl.DeferredObject;

public abstract class DeferredCallable<D, P> implements Callable<D> {
  private final Deferred<D, Throwable, P> deferred = (Deferred<D, Throwable, P>)new DeferredObject();
  
  private final DeferredManager.StartPolicy startPolicy = DeferredManager.StartPolicy.DEFAULT;
  
  public DeferredCallable() {}
  
  public DeferredCallable(DeferredManager.StartPolicy paramStartPolicy) {}
  
  protected Deferred<D, Throwable, P> getDeferred() {
    return this.deferred;
  }
  
  public DeferredManager.StartPolicy getStartPolicy() {
    return this.startPolicy;
  }
  
  protected void notify(P paramP) {
    this.deferred.notify(paramP);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\DeferredCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */