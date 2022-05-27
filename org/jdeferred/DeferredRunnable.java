package org.jdeferred;

import org.jdeferred.impl.DeferredObject;

public abstract class DeferredRunnable<P> implements Runnable {
  private final Deferred<Void, Throwable, P> deferred = (Deferred<Void, Throwable, P>)new DeferredObject();
  
  private final DeferredManager.StartPolicy startPolicy = DeferredManager.StartPolicy.DEFAULT;
  
  public DeferredRunnable() {}
  
  public DeferredRunnable(DeferredManager.StartPolicy paramStartPolicy) {}
  
  protected Deferred<Void, Throwable, P> getDeferred() {
    return this.deferred;
  }
  
  public DeferredManager.StartPolicy getStartPolicy() {
    return this.startPolicy;
  }
  
  protected void notify(P paramP) {
    this.deferred.notify(paramP);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\DeferredRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */