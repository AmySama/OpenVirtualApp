package org.jdeferred.impl;

import org.jdeferred.DoneCallback;
import org.jdeferred.DoneFilter;
import org.jdeferred.FailCallback;
import org.jdeferred.FailFilter;
import org.jdeferred.ProgressCallback;
import org.jdeferred.ProgressFilter;
import org.jdeferred.Promise;

public class FilteredPromise<D, F, P, D_OUT, F_OUT, P_OUT> extends DeferredObject<D_OUT, F_OUT, P_OUT> implements Promise<D_OUT, F_OUT, P_OUT> {
  protected static final NoOpDoneFilter NO_OP_DONE_FILTER = new NoOpDoneFilter();
  
  protected static final NoOpFailFilter NO_OP_FAIL_FILTER = new NoOpFailFilter();
  
  protected static final NoOpProgressFilter NO_OP_PROGRESS_FILTER = new NoOpProgressFilter();
  
  private final DoneFilter<D, D_OUT> doneFilter;
  
  private final FailFilter<F, F_OUT> failFilter;
  
  private final ProgressFilter<P, P_OUT> progressFilter;
  
  public FilteredPromise(Promise<D, F, P> paramPromise, DoneFilter<D, D_OUT> paramDoneFilter, FailFilter<F, F_OUT> paramFailFilter, ProgressFilter<P, P_OUT> paramProgressFilter) {
    DoneFilter<D, D_OUT> doneFilter = paramDoneFilter;
    if (paramDoneFilter == null)
      doneFilter = NO_OP_DONE_FILTER; 
    this.doneFilter = doneFilter;
    FailFilter<F, F_OUT> failFilter = paramFailFilter;
    if (paramFailFilter == null)
      failFilter = NO_OP_FAIL_FILTER; 
    this.failFilter = failFilter;
    ProgressFilter<P, P_OUT> progressFilter = paramProgressFilter;
    if (paramProgressFilter == null)
      progressFilter = NO_OP_PROGRESS_FILTER; 
    this.progressFilter = progressFilter;
    paramPromise.done(new DoneCallback<D>() {
          public void onDone(D param1D) {
            FilteredPromise filteredPromise = FilteredPromise.this;
            filteredPromise.resolve(filteredPromise.doneFilter.filterDone(param1D));
          }
        }).fail(new FailCallback<F>() {
          public void onFail(F param1F) {
            FilteredPromise filteredPromise = FilteredPromise.this;
            filteredPromise.reject(filteredPromise.failFilter.filterFail(param1F));
          }
        }).progress(new ProgressCallback<P>() {
          public void onProgress(P param1P) {
            FilteredPromise filteredPromise = FilteredPromise.this;
            filteredPromise.notify(filteredPromise.progressFilter.filterProgress(param1P));
          }
        });
  }
  
  public static final class NoOpDoneFilter<D> implements DoneFilter<D, D> {
    public D filterDone(D param1D) {
      return param1D;
    }
  }
  
  public static final class NoOpFailFilter<F> implements FailFilter<F, F> {
    public F filterFail(F param1F) {
      return param1F;
    }
  }
  
  public static final class NoOpProgressFilter<P> implements ProgressFilter<P, P> {
    public P filterProgress(P param1P) {
      return param1P;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\impl\FilteredPromise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */