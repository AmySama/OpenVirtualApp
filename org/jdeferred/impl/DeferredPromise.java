package org.jdeferred.impl;

import org.jdeferred.AlwaysCallback;
import org.jdeferred.Deferred;
import org.jdeferred.DoneCallback;
import org.jdeferred.DoneFilter;
import org.jdeferred.DonePipe;
import org.jdeferred.FailCallback;
import org.jdeferred.FailFilter;
import org.jdeferred.FailPipe;
import org.jdeferred.ProgressCallback;
import org.jdeferred.ProgressFilter;
import org.jdeferred.ProgressPipe;
import org.jdeferred.Promise;

public class DeferredPromise<D, F, P> implements Promise<D, F, P> {
  protected final Deferred<D, F, P> deferred;
  
  private final Promise<D, F, P> promise;
  
  public DeferredPromise(Deferred<D, F, P> paramDeferred) {
    this.deferred = paramDeferred;
    this.promise = paramDeferred.promise();
  }
  
  public Promise<D, F, P> always(AlwaysCallback<D, F> paramAlwaysCallback) {
    return this.promise.always(paramAlwaysCallback);
  }
  
  public Promise<D, F, P> done(DoneCallback<D> paramDoneCallback) {
    return this.promise.done(paramDoneCallback);
  }
  
  public Promise<D, F, P> fail(FailCallback<F> paramFailCallback) {
    return this.promise.fail(paramFailCallback);
  }
  
  public boolean isPending() {
    return this.promise.isPending();
  }
  
  public boolean isRejected() {
    return this.promise.isRejected();
  }
  
  public boolean isResolved() {
    return this.promise.isResolved();
  }
  
  public Promise<D, F, P> progress(ProgressCallback<P> paramProgressCallback) {
    return this.promise.progress(paramProgressCallback);
  }
  
  public Promise.State state() {
    return this.promise.state();
  }
  
  public Promise<D, F, P> then(DoneCallback<D> paramDoneCallback) {
    return this.promise.then(paramDoneCallback);
  }
  
  public Promise<D, F, P> then(DoneCallback<D> paramDoneCallback, FailCallback<F> paramFailCallback) {
    return this.promise.then(paramDoneCallback, paramFailCallback);
  }
  
  public Promise<D, F, P> then(DoneCallback<D> paramDoneCallback, FailCallback<F> paramFailCallback, ProgressCallback<P> paramProgressCallback) {
    return this.promise.then(paramDoneCallback, paramFailCallback, paramProgressCallback);
  }
  
  public <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DoneFilter<D, D_OUT> paramDoneFilter) {
    return this.promise.then(paramDoneFilter);
  }
  
  public <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DoneFilter<D, D_OUT> paramDoneFilter, FailFilter<F, F_OUT> paramFailFilter) {
    return this.promise.then(paramDoneFilter, paramFailFilter);
  }
  
  public <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DoneFilter<D, D_OUT> paramDoneFilter, FailFilter<F, F_OUT> paramFailFilter, ProgressFilter<P, P_OUT> paramProgressFilter) {
    return this.promise.then(paramDoneFilter, paramFailFilter, paramProgressFilter);
  }
  
  public <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DonePipe<D, D_OUT, F_OUT, P_OUT> paramDonePipe) {
    return this.promise.then(paramDonePipe);
  }
  
  public <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DonePipe<D, D_OUT, F_OUT, P_OUT> paramDonePipe, FailPipe<F, D_OUT, F_OUT, P_OUT> paramFailPipe) {
    return this.promise.then(paramDonePipe, paramFailPipe);
  }
  
  public <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DonePipe<D, D_OUT, F_OUT, P_OUT> paramDonePipe, FailPipe<F, D_OUT, F_OUT, P_OUT> paramFailPipe, ProgressPipe<P, D_OUT, F_OUT, P_OUT> paramProgressPipe) {
    return this.promise.then(paramDonePipe, paramFailPipe, paramProgressPipe);
  }
  
  public void waitSafely() throws InterruptedException {
    this.promise.waitSafely();
  }
  
  public void waitSafely(long paramLong) throws InterruptedException {
    this.promise.waitSafely(paramLong);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\impl\DeferredPromise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */