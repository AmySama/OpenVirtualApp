package com.lody.virtual.client.stub;

import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.IAccountManagerResponse;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.helper.utils.VLog;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class AmsTask extends FutureTask<Bundle> implements AccountManagerFuture<Bundle> {
  final Activity mActivity;
  
  final AccountManagerCallback<Bundle> mCallback;
  
  final Handler mHandler;
  
  protected final IAccountManagerResponse mResponse;
  
  public AmsTask(Activity paramActivity, Handler paramHandler, AccountManagerCallback<Bundle> paramAccountManagerCallback) {
    super(new Callable<Bundle>() {
          public Bundle call() throws Exception {
            throw new IllegalStateException("this should never be called");
          }
        });
    this.mHandler = paramHandler;
    this.mCallback = paramAccountManagerCallback;
    this.mActivity = paramActivity;
    this.mResponse = (IAccountManagerResponse)new Response();
  }
  
  private Exception convertErrorToException(int paramInt, String paramString) {
    return (Exception)((paramInt == 3) ? new IOException(paramString) : ((paramInt == 6) ? new UnsupportedOperationException(paramString) : ((paramInt == 5) ? new AuthenticatorException(paramString) : ((paramInt == 7) ? new IllegalArgumentException(paramString) : new AuthenticatorException(paramString)))));
  }
  
  private Bundle internalGetResult(Long paramLong, TimeUnit paramTimeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
    Bundle bundle;
    AuthenticatorException authenticatorException;
    if (paramLong == null) {
      try {
        bundle = get();
        cancel(true);
        return bundle;
      } catch (CancellationException cancellationException) {
        OperationCanceledException operationCanceledException = new OperationCanceledException();
        this();
        throw operationCanceledException;
      } catch (TimeoutException|InterruptedException timeoutException) {
        cancel(true);
        throw new OperationCanceledException();
      } catch (ExecutionException executionException) {
        Throwable throwable = executionException.getCause();
        if (!(throwable instanceof IOException)) {
          if (!(throwable instanceof UnsupportedOperationException)) {
            if (!(throwable instanceof AuthenticatorException)) {
              if (!(throwable instanceof RuntimeException)) {
                if (throwable instanceof Error)
                  throw (Error)throwable; 
                IllegalStateException illegalStateException = new IllegalStateException();
                this(throwable);
                throw illegalStateException;
              } 
              throw (RuntimeException)throwable;
            } 
            throw (AuthenticatorException)throwable;
          } 
          authenticatorException = new AuthenticatorException();
          this(throwable);
          throw authenticatorException;
        } 
        throw (IOException)throwable;
      } finally {}
    } else {
      bundle = get(paramLong.longValue(), (TimeUnit)authenticatorException);
      cancel(true);
      return bundle;
    } 
    cancel(true);
    throw bundle;
  }
  
  private void postToHandler(Handler paramHandler, final AccountManagerCallback<Bundle> callback, final AccountManagerFuture<Bundle> future) {
    Handler handler = paramHandler;
    if (paramHandler == null)
      handler = VirtualRuntime.getUIHandler(); 
    handler.post(new Runnable() {
          public void run() {
            callback.run(future);
          }
        });
  }
  
  public abstract void doWork() throws RemoteException;
  
  protected void done() {
    AccountManagerCallback<Bundle> accountManagerCallback = this.mCallback;
    if (accountManagerCallback != null)
      postToHandler(this.mHandler, accountManagerCallback, this); 
  }
  
  public Bundle getResult() throws OperationCanceledException, IOException, AuthenticatorException {
    return internalGetResult((Long)null, (TimeUnit)null);
  }
  
  public Bundle getResult(long paramLong, TimeUnit paramTimeUnit) throws OperationCanceledException, IOException, AuthenticatorException {
    return internalGetResult(Long.valueOf(paramLong), paramTimeUnit);
  }
  
  protected void set(Bundle paramBundle) {
    if (paramBundle == null)
      VLog.e("AccountManager", "the bundle must not be null\n%s", new Object[] { new Exception() }); 
    super.set(paramBundle);
  }
  
  public final AccountManagerFuture<Bundle> start() {
    try {
      doWork();
    } catch (RemoteException remoteException) {
      setException((Throwable)remoteException);
    } 
    return this;
  }
  
  private class Response extends IAccountManagerResponse.Stub {
    private Response() {}
    
    public void onError(int param1Int, String param1String) {
      if (param1Int == 4 || param1Int == 100 || param1Int == 101) {
        AmsTask.this.cancel(true);
        return;
      } 
      AmsTask amsTask = AmsTask.this;
      amsTask.setException(amsTask.convertErrorToException(param1Int, param1String));
    }
    
    public void onResult(Bundle param1Bundle) {
      Intent intent = (Intent)param1Bundle.getParcelable("intent");
      if (intent != null && AmsTask.this.mActivity != null) {
        AmsTask.this.mActivity.startActivity(intent);
      } else if (param1Bundle.getBoolean("retry")) {
        try {
          AmsTask.this.doWork();
        } catch (RemoteException remoteException) {
          throw new RuntimeException(remoteException);
        } 
      } else {
        AmsTask.this.set((Bundle)remoteException);
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\AmsTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */