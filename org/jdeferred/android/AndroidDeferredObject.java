package org.jdeferred.android;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import org.jdeferred.AlwaysCallback;
import org.jdeferred.Deferred;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AndroidDeferredObject<D, F, P> extends DeferredObject<D, F, P> {
  private static final int MESSAGE_POST_ALWAYS = 4;
  
  private static final int MESSAGE_POST_DONE = 1;
  
  private static final int MESSAGE_POST_FAIL = 3;
  
  private static final int MESSAGE_POST_PROGRESS = 2;
  
  private static final InternalHandler sHandler = new InternalHandler();
  
  private final AndroidExecutionScope defaultAndroidExecutionScope;
  
  protected final Logger log = LoggerFactory.getLogger(AndroidDeferredObject.class);
  
  public AndroidDeferredObject(Promise<D, F, P> paramPromise) {
    this(paramPromise, AndroidExecutionScope.UI);
  }
  
  public AndroidDeferredObject(Promise<D, F, P> paramPromise, AndroidExecutionScope paramAndroidExecutionScope) {
    this.defaultAndroidExecutionScope = paramAndroidExecutionScope;
    paramPromise.done(new DoneCallback<D>() {
          public void onDone(D param1D) {
            AndroidDeferredObject.this.resolve(param1D);
          }
        }).progress(new ProgressCallback<P>() {
          public void onProgress(P param1P) {
            AndroidDeferredObject.this.notify(param1P);
          }
        }).fail(new FailCallback<F>() {
          public void onFail(F param1F) {
            AndroidDeferredObject.this.reject(param1F);
          }
        });
  }
  
  protected AndroidExecutionScope determineAndroidExecutionScope(Object paramObject) {
    if (paramObject instanceof AndroidExecutionScopeable) {
      paramObject = ((AndroidExecutionScopeable)paramObject).getExecutionScope();
    } else {
      paramObject = null;
    } 
    Object object = paramObject;
    if (paramObject == null)
      object = this.defaultAndroidExecutionScope; 
    return (AndroidExecutionScope)object;
  }
  
  protected <Callback> void executeInUiThread(int paramInt, Callback paramCallback, Promise.State paramState, D paramD, F paramF, P paramP) {
    sHandler.obtainMessage(paramInt, new CallbackMessage<Callback, D, F, P>((Deferred)this, paramCallback, paramState, paramD, paramF, paramP)).sendToTarget();
  }
  
  protected void triggerAlways(AlwaysCallback<D, F> paramAlwaysCallback, Promise.State paramState, D paramD, F paramF) {
    if (determineAndroidExecutionScope(paramAlwaysCallback) == AndroidExecutionScope.UI) {
      executeInUiThread(4, paramAlwaysCallback, paramState, paramD, paramF, (Object)null);
    } else {
      super.triggerAlways(paramAlwaysCallback, paramState, paramD, paramF);
    } 
  }
  
  protected void triggerDone(DoneCallback<D> paramDoneCallback, D paramD) {
    if (determineAndroidExecutionScope(paramDoneCallback) == AndroidExecutionScope.UI) {
      executeInUiThread(1, paramDoneCallback, Promise.State.RESOLVED, paramD, (Object)null, (Object)null);
    } else {
      super.triggerDone(paramDoneCallback, paramD);
    } 
  }
  
  protected void triggerFail(FailCallback<F> paramFailCallback, F paramF) {
    if (determineAndroidExecutionScope(paramFailCallback) == AndroidExecutionScope.UI) {
      executeInUiThread(3, paramFailCallback, Promise.State.REJECTED, (Object)null, paramF, (Object)null);
    } else {
      super.triggerFail(paramFailCallback, paramF);
    } 
  }
  
  protected void triggerProgress(ProgressCallback<P> paramProgressCallback, P paramP) {
    if (determineAndroidExecutionScope(paramProgressCallback) == AndroidExecutionScope.UI) {
      executeInUiThread(2, paramProgressCallback, Promise.State.PENDING, (Object)null, (Object)null, paramP);
    } else {
      super.triggerProgress(paramProgressCallback, paramP);
    } 
  }
  
  private static class CallbackMessage<Callback, D, F, P> {
    final Callback callback;
    
    final Deferred deferred;
    
    final P progress;
    
    final F rejected;
    
    final D resolved;
    
    final Promise.State state;
    
    CallbackMessage(Deferred param1Deferred, Callback param1Callback, Promise.State param1State, D param1D, F param1F, P param1P) {
      this.deferred = param1Deferred;
      this.callback = param1Callback;
      this.state = param1State;
      this.resolved = param1D;
      this.rejected = param1F;
      this.progress = param1P;
    }
  }
  
  private static class InternalHandler extends Handler {
    public InternalHandler() {
      super(Looper.getMainLooper());
    }
    
    public void handleMessage(Message param1Message) {
      AndroidDeferredObject.CallbackMessage callbackMessage = (AndroidDeferredObject.CallbackMessage)param1Message.obj;
      int i = param1Message.what;
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i == 4)
              ((AlwaysCallback)callbackMessage.callback).onAlways(callbackMessage.state, callbackMessage.resolved, callbackMessage.rejected); 
          } else {
            ((FailCallback)callbackMessage.callback).onFail(callbackMessage.rejected);
          } 
        } else {
          ((ProgressCallback)callbackMessage.callback).onProgress(callbackMessage.progress);
        } 
      } else {
        ((DoneCallback)callbackMessage.callback).onDone(callbackMessage.resolved);
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\android\AndroidDeferredObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */