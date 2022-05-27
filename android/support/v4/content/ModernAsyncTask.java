package android.support.v4.content;

import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

abstract class ModernAsyncTask<Params, Progress, Result> {
  private static final int CORE_POOL_SIZE = 5;
  
  private static final int KEEP_ALIVE = 1;
  
  private static final String LOG_TAG = "AsyncTask";
  
  private static final int MAXIMUM_POOL_SIZE = 128;
  
  private static final int MESSAGE_POST_PROGRESS = 2;
  
  private static final int MESSAGE_POST_RESULT = 1;
  
  public static final Executor THREAD_POOL_EXECUTOR;
  
  private static volatile Executor sDefaultExecutor;
  
  private static InternalHandler sHandler;
  
  private static final BlockingQueue<Runnable> sPoolWorkQueue;
  
  private static final ThreadFactory sThreadFactory = new ThreadFactory() {
      private final AtomicInteger mCount = new AtomicInteger(1);
      
      public Thread newThread(Runnable param1Runnable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ModernAsyncTask #");
        stringBuilder.append(this.mCount.getAndIncrement());
        return new Thread(param1Runnable, stringBuilder.toString());
      }
    };
  
  private final AtomicBoolean mCancelled = new AtomicBoolean();
  
  private final FutureTask<Result> mFuture = new FutureTask<Result>(this.mWorker) {
      protected void done() {
        try {
          Result result = get();
          ModernAsyncTask.this.postResultIfNotInvoked(result);
        } catch (InterruptedException interruptedException) {
        
        } catch (ExecutionException executionException) {
          throw new RuntimeException("An error occurred while executing doInBackground()", executionException.getCause());
        } catch (CancellationException cancellationException) {
        
        } finally {
          Exception exception = null;
        } 
      }
    };
  
  private volatile Status mStatus = Status.PENDING;
  
  private final AtomicBoolean mTaskInvoked = new AtomicBoolean();
  
  private final WorkerRunnable<Params, Result> mWorker = new WorkerRunnable<Params, Result>() {
      public Result call() throws Exception {
        ModernAsyncTask.this.mTaskInvoked.set(true);
        Object object1 = null;
        Object object2 = object1;
        try {
          Process.setThreadPriority(10);
          object2 = object1;
          object1 = ModernAsyncTask.this.doInBackground((Object[])this.mParams);
          object2 = object1;
          Binder.flushPendingCommands();
          return (Result)object1;
        } finally {
          object1 = null;
        } 
      }
    };
  
  static {
    sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(10);
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 128, 1L, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
    THREAD_POOL_EXECUTOR = threadPoolExecutor;
    sDefaultExecutor = threadPoolExecutor;
  }
  
  public static void execute(Runnable paramRunnable) {
    sDefaultExecutor.execute(paramRunnable);
  }
  
  private static Handler getHandler() {
    // Byte code:
    //   0: ldc android/support/v4/content/ModernAsyncTask
    //   2: monitorenter
    //   3: getstatic android/support/v4/content/ModernAsyncTask.sHandler : Landroid/support/v4/content/ModernAsyncTask$InternalHandler;
    //   6: ifnonnull -> 21
    //   9: new android/support/v4/content/ModernAsyncTask$InternalHandler
    //   12: astore_0
    //   13: aload_0
    //   14: invokespecial <init> : ()V
    //   17: aload_0
    //   18: putstatic android/support/v4/content/ModernAsyncTask.sHandler : Landroid/support/v4/content/ModernAsyncTask$InternalHandler;
    //   21: getstatic android/support/v4/content/ModernAsyncTask.sHandler : Landroid/support/v4/content/ModernAsyncTask$InternalHandler;
    //   24: astore_0
    //   25: ldc android/support/v4/content/ModernAsyncTask
    //   27: monitorexit
    //   28: aload_0
    //   29: areturn
    //   30: astore_0
    //   31: ldc android/support/v4/content/ModernAsyncTask
    //   33: monitorexit
    //   34: aload_0
    //   35: athrow
    // Exception table:
    //   from	to	target	type
    //   3	21	30	finally
    //   21	28	30	finally
    //   31	34	30	finally
  }
  
  public static void setDefaultExecutor(Executor paramExecutor) {
    sDefaultExecutor = paramExecutor;
  }
  
  public final boolean cancel(boolean paramBoolean) {
    this.mCancelled.set(true);
    return this.mFuture.cancel(paramBoolean);
  }
  
  protected abstract Result doInBackground(Params... paramVarArgs);
  
  public final ModernAsyncTask<Params, Progress, Result> execute(Params... paramVarArgs) {
    return executeOnExecutor(sDefaultExecutor, paramVarArgs);
  }
  
  public final ModernAsyncTask<Params, Progress, Result> executeOnExecutor(Executor paramExecutor, Params... paramVarArgs) {
    if (this.mStatus != Status.PENDING) {
      int i = null.$SwitchMap$android$support$v4$content$ModernAsyncTask$Status[this.mStatus.ordinal()];
      if (i != 1) {
        if (i != 2)
          throw new IllegalStateException("We should never reach this state"); 
        throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
      } 
      throw new IllegalStateException("Cannot execute task: the task is already running.");
    } 
    this.mStatus = Status.RUNNING;
    onPreExecute();
    this.mWorker.mParams = paramVarArgs;
    paramExecutor.execute(this.mFuture);
    return this;
  }
  
  void finish(Result paramResult) {
    if (isCancelled()) {
      onCancelled(paramResult);
    } else {
      onPostExecute(paramResult);
    } 
    this.mStatus = Status.FINISHED;
  }
  
  public final Result get() throws InterruptedException, ExecutionException {
    return this.mFuture.get();
  }
  
  public final Result get(long paramLong, TimeUnit paramTimeUnit) throws InterruptedException, ExecutionException, TimeoutException {
    return this.mFuture.get(paramLong, paramTimeUnit);
  }
  
  public final Status getStatus() {
    return this.mStatus;
  }
  
  public final boolean isCancelled() {
    return this.mCancelled.get();
  }
  
  protected void onCancelled() {}
  
  protected void onCancelled(Result paramResult) {
    onCancelled();
  }
  
  protected void onPostExecute(Result paramResult) {}
  
  protected void onPreExecute() {}
  
  protected void onProgressUpdate(Progress... paramVarArgs) {}
  
  Result postResult(Result paramResult) {
    getHandler().obtainMessage(1, new AsyncTaskResult(this, new Object[] { paramResult })).sendToTarget();
    return paramResult;
  }
  
  void postResultIfNotInvoked(Result paramResult) {
    if (!this.mTaskInvoked.get())
      postResult(paramResult); 
  }
  
  protected final void publishProgress(Progress... paramVarArgs) {
    if (!isCancelled())
      getHandler().obtainMessage(2, new AsyncTaskResult<Progress>(this, paramVarArgs)).sendToTarget(); 
  }
  
  private static class AsyncTaskResult<Data> {
    final Data[] mData;
    
    final ModernAsyncTask mTask;
    
    AsyncTaskResult(ModernAsyncTask param1ModernAsyncTask, Data... param1VarArgs) {
      this.mTask = param1ModernAsyncTask;
      this.mData = param1VarArgs;
    }
  }
  
  private static class InternalHandler extends Handler {
    InternalHandler() {
      super(Looper.getMainLooper());
    }
    
    public void handleMessage(Message param1Message) {
      ModernAsyncTask.AsyncTaskResult asyncTaskResult = (ModernAsyncTask.AsyncTaskResult)param1Message.obj;
      int i = param1Message.what;
      if (i != 1) {
        if (i == 2)
          asyncTaskResult.mTask.onProgressUpdate((Object[])asyncTaskResult.mData); 
      } else {
        asyncTaskResult.mTask.finish(asyncTaskResult.mData[0]);
      } 
    }
  }
  
  public enum Status {
    FINISHED, PENDING, RUNNING;
    
    static {
      Status status = new Status("FINISHED", 2);
      FINISHED = status;
      $VALUES = new Status[] { PENDING, RUNNING, status };
    }
  }
  
  private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
    Params[] mParams;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\content\ModernAsyncTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */