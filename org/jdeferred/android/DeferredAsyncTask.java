package org.jdeferred.android;

import android.os.AsyncTask;
import java.util.concurrent.CancellationException;
import org.jdeferred.DeferredManager;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DeferredAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
  private final DeferredObject<Result, Throwable, Progress> deferred = new DeferredObject();
  
  protected final Logger log = LoggerFactory.getLogger(DeferredAsyncTask.class);
  
  private final DeferredManager.StartPolicy startPolicy = DeferredManager.StartPolicy.DEFAULT;
  
  private Throwable throwable;
  
  public DeferredAsyncTask() {}
  
  public DeferredAsyncTask(DeferredManager.StartPolicy paramStartPolicy) {}
  
  protected final Result doInBackground(Params... paramVarArgs) {
    try {
      return doInBackgroundSafe(paramVarArgs);
    } finally {
      paramVarArgs = null;
      this.throwable = (Throwable)paramVarArgs;
    } 
  }
  
  protected abstract Result doInBackgroundSafe(Params... paramVarArgs) throws Exception;
  
  public DeferredManager.StartPolicy getStartPolicy() {
    return this.startPolicy;
  }
  
  protected final void notify(Progress paramProgress) {
    publishProgress(new Object[] { paramProgress });
  }
  
  protected final void onCancelled() {
    this.deferred.reject(new CancellationException());
  }
  
  protected final void onCancelled(Result paramResult) {
    this.deferred.reject(new CancellationException());
  }
  
  protected final void onPostExecute(Result paramResult) {
    Throwable throwable = this.throwable;
    if (throwable != null) {
      this.deferred.reject(throwable);
    } else {
      this.deferred.resolve(paramResult);
    } 
  }
  
  protected final void onProgressUpdate(Progress... paramVarArgs) {
    if (paramVarArgs == null || paramVarArgs.length == 0) {
      this.deferred.notify(null);
      return;
    } 
    if (paramVarArgs.length > 0) {
      this.log.warn("There were multiple progress values.  Only the first one was used!");
      this.deferred.notify(paramVarArgs[0]);
    } 
  }
  
  public Promise<Result, Throwable, Progress> promise() {
    return this.deferred.promise();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\android\DeferredAsyncTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */