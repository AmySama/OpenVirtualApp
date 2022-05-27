package org.jdeferred;

public interface Promise<D, F, P> {
  Promise<D, F, P> always(AlwaysCallback<D, F> paramAlwaysCallback);
  
  Promise<D, F, P> done(DoneCallback<D> paramDoneCallback);
  
  Promise<D, F, P> fail(FailCallback<F> paramFailCallback);
  
  boolean isPending();
  
  boolean isRejected();
  
  boolean isResolved();
  
  Promise<D, F, P> progress(ProgressCallback<P> paramProgressCallback);
  
  State state();
  
  Promise<D, F, P> then(DoneCallback<D> paramDoneCallback);
  
  Promise<D, F, P> then(DoneCallback<D> paramDoneCallback, FailCallback<F> paramFailCallback);
  
  Promise<D, F, P> then(DoneCallback<D> paramDoneCallback, FailCallback<F> paramFailCallback, ProgressCallback<P> paramProgressCallback);
  
  <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DoneFilter<D, D_OUT> paramDoneFilter);
  
  <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DoneFilter<D, D_OUT> paramDoneFilter, FailFilter<F, F_OUT> paramFailFilter);
  
  <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DoneFilter<D, D_OUT> paramDoneFilter, FailFilter<F, F_OUT> paramFailFilter, ProgressFilter<P, P_OUT> paramProgressFilter);
  
  <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DonePipe<D, D_OUT, F_OUT, P_OUT> paramDonePipe);
  
  <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DonePipe<D, D_OUT, F_OUT, P_OUT> paramDonePipe, FailPipe<F, D_OUT, F_OUT, P_OUT> paramFailPipe);
  
  <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DonePipe<D, D_OUT, F_OUT, P_OUT> paramDonePipe, FailPipe<F, D_OUT, F_OUT, P_OUT> paramFailPipe, ProgressPipe<P, D_OUT, F_OUT, P_OUT> paramProgressPipe);
  
  void waitSafely() throws InterruptedException;
  
  void waitSafely(long paramLong) throws InterruptedException;
  
  public enum State {
    PENDING, REJECTED, RESOLVED;
    
    static {
      State state = new State("RESOLVED", 2);
      RESOLVED = state;
      $VALUES = new State[] { PENDING, REJECTED, state };
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\Promise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */