package org.jdeferred.impl;

import org.jdeferred.DoneCallback;
import org.jdeferred.DonePipe;
import org.jdeferred.FailCallback;
import org.jdeferred.FailPipe;
import org.jdeferred.ProgressCallback;
import org.jdeferred.ProgressPipe;
import org.jdeferred.Promise;

public class PipedPromise<D, F, P, D_OUT, F_OUT, P_OUT> extends DeferredObject<D_OUT, F_OUT, P_OUT> implements Promise<D_OUT, F_OUT, P_OUT> {
  public PipedPromise(Promise<D, F, P> paramPromise, final DonePipe<D, D_OUT, F_OUT, P_OUT> doneFilter, final FailPipe<F, D_OUT, F_OUT, P_OUT> failFilter, final ProgressPipe<P, D_OUT, F_OUT, P_OUT> progressFilter) {
    paramPromise.done(new DoneCallback<D>() {
          public void onDone(D param1D) {
            DonePipe donePipe = doneFilter;
            if (donePipe != null) {
              PipedPromise.this.pipe(donePipe.pipeDone(param1D));
            } else {
              PipedPromise.this.resolve(param1D);
            } 
          }
        }).fail(new FailCallback<F>() {
          public void onFail(F param1F) {
            FailPipe failPipe = failFilter;
            if (failPipe != null) {
              PipedPromise.this.pipe(failPipe.pipeFail(param1F));
            } else {
              PipedPromise.this.reject(param1F);
            } 
          }
        }).progress(new ProgressCallback<P>() {
          public void onProgress(P param1P) {
            ProgressPipe progressPipe = progressFilter;
            if (progressPipe != null) {
              PipedPromise.this.pipe(progressPipe.pipeProgress(param1P));
            } else {
              PipedPromise.this.notify(param1P);
            } 
          }
        });
  }
  
  protected Promise<D_OUT, F_OUT, P_OUT> pipe(Promise<D_OUT, F_OUT, P_OUT> paramPromise) {
    paramPromise.done(new DoneCallback<D_OUT>() {
          public void onDone(D_OUT param1D_OUT) {
            PipedPromise.this.resolve(param1D_OUT);
          }
        }).fail(new FailCallback<F_OUT>() {
          public void onFail(F_OUT param1F_OUT) {
            PipedPromise.this.reject(param1F_OUT);
          }
        }).progress(new ProgressCallback<P_OUT>() {
          public void onProgress(P_OUT param1P_OUT) {
            PipedPromise.this.notify(param1P_OUT);
          }
        });
    return paramPromise;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\impl\PipedPromise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */