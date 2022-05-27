package org.jdeferred.android;

import android.os.Build;
import java.util.concurrent.ExecutorService;
import org.jdeferred.DeferredFutureTask;
import org.jdeferred.DeferredManager;
import org.jdeferred.Promise;
import org.jdeferred.impl.DefaultDeferredManager;
import org.jdeferred.multiple.MasterProgress;
import org.jdeferred.multiple.MultipleResults;
import org.jdeferred.multiple.OneReject;

public class AndroidDeferredManager extends DefaultDeferredManager {
  private static Void[] EMPTY_PARAMS = new Void[0];
  
  public AndroidDeferredManager() {}
  
  public AndroidDeferredManager(ExecutorService paramExecutorService) {
    super(paramExecutorService);
  }
  
  public <D, P> Promise<D, Throwable, P> when(DeferredFutureTask<D, P> paramDeferredFutureTask) {
    return (new AndroidDeferredObject<Object, Object, Object>(super.when(paramDeferredFutureTask))).promise();
  }
  
  public <D, F, P> Promise<D, F, P> when(Promise<D, F, P> paramPromise) {
    return (paramPromise instanceof AndroidDeferredObject) ? paramPromise : (new AndroidDeferredObject<Object, Object, Object>(paramPromise)).promise();
  }
  
  public <D, F, P> Promise<D, F, P> when(Promise<D, F, P> paramPromise, AndroidExecutionScope paramAndroidExecutionScope) {
    return (paramPromise instanceof AndroidDeferredObject) ? paramPromise : (new AndroidDeferredObject<Object, Object, Object>(paramPromise, paramAndroidExecutionScope)).promise();
  }
  
  public Promise<MultipleResults, OneReject, MasterProgress> when(AndroidExecutionScope paramAndroidExecutionScope, Promise... paramVarArgs) {
    return (new AndroidDeferredObject<Object, Object, Object>(super.when(paramVarArgs), paramAndroidExecutionScope)).promise();
  }
  
  public Promise<MultipleResults, OneReject, MasterProgress> when(AndroidExecutionScope paramAndroidExecutionScope, DeferredAsyncTask<Void, ?, ?>... paramVarArgs) {
    assertNotEmpty((Object[])paramVarArgs);
    Promise[] arrayOfPromise = new Promise[paramVarArgs.length];
    for (byte b = 0; b < paramVarArgs.length; b++)
      arrayOfPromise[b] = when(paramVarArgs[b]); 
    return when(paramAndroidExecutionScope, arrayOfPromise);
  }
  
  public <Progress, Result> Promise<Result, Throwable, Progress> when(DeferredAsyncTask<Void, Progress, Result> paramDeferredAsyncTask) {
    if (paramDeferredAsyncTask.getStartPolicy() == DeferredManager.StartPolicy.AUTO || (paramDeferredAsyncTask.getStartPolicy() == DeferredManager.StartPolicy.DEFAULT && isAutoSubmit()))
      if (Build.VERSION.SDK_INT >= 11) {
        paramDeferredAsyncTask.executeOnExecutor(getExecutorService(), (Object[])EMPTY_PARAMS);
      } else {
        paramDeferredAsyncTask.execute((Object[])EMPTY_PARAMS);
      }  
    return paramDeferredAsyncTask.promise();
  }
  
  public Promise<MultipleResults, OneReject, MasterProgress> when(Promise... paramVarArgs) {
    return (new AndroidDeferredObject<Object, Object, Object>(super.when(paramVarArgs))).promise();
  }
  
  public Promise<MultipleResults, OneReject, MasterProgress> when(DeferredAsyncTask<Void, ?, ?>... paramVarArgs) {
    assertNotEmpty((Object[])paramVarArgs);
    Promise[] arrayOfPromise = new Promise[paramVarArgs.length];
    for (byte b = 0; b < paramVarArgs.length; b++)
      arrayOfPromise[b] = when(paramVarArgs[b]); 
    return when(arrayOfPromise);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\android\AndroidDeferredManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */