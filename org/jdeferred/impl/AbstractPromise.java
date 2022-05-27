package org.jdeferred.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.jdeferred.AlwaysCallback;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractPromise<D, F, P> implements Promise<D, F, P> {
  protected final List<AlwaysCallback<D, F>> alwaysCallbacks = new CopyOnWriteArrayList<AlwaysCallback<D, F>>();
  
  protected final List<DoneCallback<D>> doneCallbacks = new CopyOnWriteArrayList<DoneCallback<D>>();
  
  protected final List<FailCallback<F>> failCallbacks = new CopyOnWriteArrayList<FailCallback<F>>();
  
  protected final Logger log = LoggerFactory.getLogger(AbstractPromise.class);
  
  protected final List<ProgressCallback<P>> progressCallbacks = new CopyOnWriteArrayList<ProgressCallback<P>>();
  
  protected F rejectResult;
  
  protected D resolveResult;
  
  protected volatile Promise.State state = Promise.State.PENDING;
  
  public Promise<D, F, P> always(AlwaysCallback<D, F> paramAlwaysCallback) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual isPending : ()Z
    //   6: ifeq -> 23
    //   9: aload_0
    //   10: getfield alwaysCallbacks : Ljava/util/List;
    //   13: aload_1
    //   14: invokeinterface add : (Ljava/lang/Object;)Z
    //   19: pop
    //   20: goto -> 40
    //   23: aload_0
    //   24: aload_1
    //   25: aload_0
    //   26: getfield state : Lorg/jdeferred/Promise$State;
    //   29: aload_0
    //   30: getfield resolveResult : Ljava/lang/Object;
    //   33: aload_0
    //   34: getfield rejectResult : Ljava/lang/Object;
    //   37: invokevirtual triggerAlways : (Lorg/jdeferred/AlwaysCallback;Lorg/jdeferred/Promise$State;Ljava/lang/Object;Ljava/lang/Object;)V
    //   40: aload_0
    //   41: monitorexit
    //   42: aload_0
    //   43: areturn
    //   44: astore_1
    //   45: aload_0
    //   46: monitorexit
    //   47: aload_1
    //   48: athrow
    // Exception table:
    //   from	to	target	type
    //   2	20	44	finally
    //   23	40	44	finally
    //   40	42	44	finally
    //   45	47	44	finally
  }
  
  public Promise<D, F, P> done(DoneCallback<D> paramDoneCallback) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual isResolved : ()Z
    //   6: ifeq -> 21
    //   9: aload_0
    //   10: aload_1
    //   11: aload_0
    //   12: getfield resolveResult : Ljava/lang/Object;
    //   15: invokevirtual triggerDone : (Lorg/jdeferred/DoneCallback;Ljava/lang/Object;)V
    //   18: goto -> 32
    //   21: aload_0
    //   22: getfield doneCallbacks : Ljava/util/List;
    //   25: aload_1
    //   26: invokeinterface add : (Ljava/lang/Object;)Z
    //   31: pop
    //   32: aload_0
    //   33: monitorexit
    //   34: aload_0
    //   35: areturn
    //   36: astore_1
    //   37: aload_0
    //   38: monitorexit
    //   39: aload_1
    //   40: athrow
    // Exception table:
    //   from	to	target	type
    //   2	18	36	finally
    //   21	32	36	finally
    //   32	34	36	finally
    //   37	39	36	finally
  }
  
  public Promise<D, F, P> fail(FailCallback<F> paramFailCallback) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual isRejected : ()Z
    //   6: ifeq -> 21
    //   9: aload_0
    //   10: aload_1
    //   11: aload_0
    //   12: getfield rejectResult : Ljava/lang/Object;
    //   15: invokevirtual triggerFail : (Lorg/jdeferred/FailCallback;Ljava/lang/Object;)V
    //   18: goto -> 32
    //   21: aload_0
    //   22: getfield failCallbacks : Ljava/util/List;
    //   25: aload_1
    //   26: invokeinterface add : (Ljava/lang/Object;)Z
    //   31: pop
    //   32: aload_0
    //   33: monitorexit
    //   34: aload_0
    //   35: areturn
    //   36: astore_1
    //   37: aload_0
    //   38: monitorexit
    //   39: aload_1
    //   40: athrow
    // Exception table:
    //   from	to	target	type
    //   2	18	36	finally
    //   21	32	36	finally
    //   32	34	36	finally
    //   37	39	36	finally
  }
  
  public boolean isPending() {
    boolean bool;
    if (this.state == Promise.State.PENDING) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isRejected() {
    boolean bool;
    if (this.state == Promise.State.REJECTED) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isResolved() {
    boolean bool;
    if (this.state == Promise.State.RESOLVED) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public Promise<D, F, P> progress(ProgressCallback<P> paramProgressCallback) {
    this.progressCallbacks.add(paramProgressCallback);
    return this;
  }
  
  public Promise.State state() {
    return this.state;
  }
  
  public Promise<D, F, P> then(DoneCallback<D> paramDoneCallback) {
    return done(paramDoneCallback);
  }
  
  public Promise<D, F, P> then(DoneCallback<D> paramDoneCallback, FailCallback<F> paramFailCallback) {
    done(paramDoneCallback);
    fail(paramFailCallback);
    return this;
  }
  
  public Promise<D, F, P> then(DoneCallback<D> paramDoneCallback, FailCallback<F> paramFailCallback, ProgressCallback<P> paramProgressCallback) {
    done(paramDoneCallback);
    fail(paramFailCallback);
    progress(paramProgressCallback);
    return this;
  }
  
  public <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DoneFilter<D, D_OUT> paramDoneFilter) {
    return new FilteredPromise<D, Object, Object, D_OUT, F_OUT, P_OUT>(this, paramDoneFilter, null, null);
  }
  
  public <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DoneFilter<D, D_OUT> paramDoneFilter, FailFilter<F, F_OUT> paramFailFilter) {
    return new FilteredPromise<D, F, Object, D_OUT, F_OUT, P_OUT>(this, paramDoneFilter, paramFailFilter, null);
  }
  
  public <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DoneFilter<D, D_OUT> paramDoneFilter, FailFilter<F, F_OUT> paramFailFilter, ProgressFilter<P, P_OUT> paramProgressFilter) {
    return new FilteredPromise<D, F, P, D_OUT, F_OUT, P_OUT>(this, paramDoneFilter, paramFailFilter, paramProgressFilter);
  }
  
  public <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DonePipe<D, D_OUT, F_OUT, P_OUT> paramDonePipe) {
    return new PipedPromise<D, Object, Object, D_OUT, F_OUT, P_OUT>(this, paramDonePipe, null, null);
  }
  
  public <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DonePipe<D, D_OUT, F_OUT, P_OUT> paramDonePipe, FailPipe<F, D_OUT, F_OUT, P_OUT> paramFailPipe) {
    return new PipedPromise<D, F, Object, D_OUT, F_OUT, P_OUT>(this, paramDonePipe, paramFailPipe, null);
  }
  
  public <D_OUT, F_OUT, P_OUT> Promise<D_OUT, F_OUT, P_OUT> then(DonePipe<D, D_OUT, F_OUT, P_OUT> paramDonePipe, FailPipe<F, D_OUT, F_OUT, P_OUT> paramFailPipe, ProgressPipe<P, D_OUT, F_OUT, P_OUT> paramProgressPipe) {
    return new PipedPromise<D, F, P, D_OUT, F_OUT, P_OUT>(this, paramDonePipe, paramFailPipe, paramProgressPipe);
  }
  
  protected void triggerAlways(AlwaysCallback<D, F> paramAlwaysCallback, Promise.State paramState, D paramD, F paramF) {
    paramAlwaysCallback.onAlways(paramState, paramD, paramF);
  }
  
  protected void triggerAlways(Promise.State paramState, D paramD, F paramF) {
    // Byte code:
    //   0: aload_0
    //   1: getfield alwaysCallbacks : Ljava/util/List;
    //   4: invokeinterface iterator : ()Ljava/util/Iterator;
    //   9: astore #4
    //   11: aload #4
    //   13: invokeinterface hasNext : ()Z
    //   18: ifeq -> 63
    //   21: aload #4
    //   23: invokeinterface next : ()Ljava/lang/Object;
    //   28: checkcast org/jdeferred/AlwaysCallback
    //   31: astore #5
    //   33: aload_0
    //   34: aload #5
    //   36: aload_1
    //   37: aload_2
    //   38: aload_3
    //   39: invokevirtual triggerAlways : (Lorg/jdeferred/AlwaysCallback;Lorg/jdeferred/Promise$State;Ljava/lang/Object;Ljava/lang/Object;)V
    //   42: goto -> 11
    //   45: astore #5
    //   47: aload_0
    //   48: getfield log : Lorg/slf4j/Logger;
    //   51: ldc 'an uncaught exception occured in a AlwaysCallback'
    //   53: aload #5
    //   55: invokeinterface error : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   60: goto -> 11
    //   63: aload_0
    //   64: getfield alwaysCallbacks : Ljava/util/List;
    //   67: invokeinterface clear : ()V
    //   72: aload_0
    //   73: monitorenter
    //   74: aload_0
    //   75: invokevirtual notifyAll : ()V
    //   78: aload_0
    //   79: monitorexit
    //   80: return
    //   81: astore_1
    //   82: aload_0
    //   83: monitorexit
    //   84: aload_1
    //   85: athrow
    // Exception table:
    //   from	to	target	type
    //   33	42	45	java/lang/Exception
    //   74	80	81	finally
    //   82	84	81	finally
  }
  
  protected void triggerDone(D paramD) {
    for (DoneCallback<D> doneCallback : this.doneCallbacks) {
      try {
        triggerDone(doneCallback, paramD);
      } catch (Exception exception) {
        this.log.error("an uncaught exception occured in a DoneCallback", exception);
      } 
    } 
    this.doneCallbacks.clear();
  }
  
  protected void triggerDone(DoneCallback<D> paramDoneCallback, D paramD) {
    paramDoneCallback.onDone(paramD);
  }
  
  protected void triggerFail(F paramF) {
    for (FailCallback<F> failCallback : this.failCallbacks) {
      try {
        triggerFail(failCallback, paramF);
      } catch (Exception exception) {
        this.log.error("an uncaught exception occured in a FailCallback", exception);
      } 
    } 
    this.failCallbacks.clear();
  }
  
  protected void triggerFail(FailCallback<F> paramFailCallback, F paramF) {
    paramFailCallback.onFail(paramF);
  }
  
  protected void triggerProgress(P paramP) {
    for (ProgressCallback<P> progressCallback : this.progressCallbacks) {
      try {
        triggerProgress(progressCallback, paramP);
      } catch (Exception exception) {
        this.log.error("an uncaught exception occured in a ProgressCallback", exception);
      } 
    } 
  }
  
  protected void triggerProgress(ProgressCallback<P> paramProgressCallback, P paramP) {
    paramProgressCallback.onProgress(paramP);
  }
  
  public void waitSafely() throws InterruptedException {
    waitSafely(-1L);
  }
  
  public void waitSafely(long paramLong) throws InterruptedException {
    // Byte code:
    //   0: invokestatic currentTimeMillis : ()J
    //   3: lstore_3
    //   4: aload_0
    //   5: monitorenter
    //   6: aload_0
    //   7: invokevirtual isPending : ()Z
    //   10: istore #5
    //   12: iload #5
    //   14: ifeq -> 74
    //   17: lload_1
    //   18: lconst_0
    //   19: lcmp
    //   20: istore #6
    //   22: iload #6
    //   24: ifgt -> 34
    //   27: aload_0
    //   28: invokevirtual wait : ()V
    //   31: goto -> 45
    //   34: aload_0
    //   35: lload_1
    //   36: invokestatic currentTimeMillis : ()J
    //   39: lload_3
    //   40: lsub
    //   41: lsub
    //   42: invokevirtual wait : (J)V
    //   45: iload #6
    //   47: ifle -> 6
    //   50: invokestatic currentTimeMillis : ()J
    //   53: lload_3
    //   54: lsub
    //   55: lload_1
    //   56: lcmp
    //   57: iflt -> 6
    //   60: aload_0
    //   61: monitorexit
    //   62: return
    //   63: astore #7
    //   65: invokestatic currentThread : ()Ljava/lang/Thread;
    //   68: invokevirtual interrupt : ()V
    //   71: aload #7
    //   73: athrow
    //   74: aload_0
    //   75: monitorexit
    //   76: return
    //   77: astore #7
    //   79: aload_0
    //   80: monitorexit
    //   81: aload #7
    //   83: athrow
    // Exception table:
    //   from	to	target	type
    //   6	12	77	finally
    //   27	31	63	java/lang/InterruptedException
    //   27	31	77	finally
    //   34	45	63	java/lang/InterruptedException
    //   34	45	77	finally
    //   50	62	77	finally
    //   65	74	77	finally
    //   74	76	77	finally
    //   79	81	77	finally
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\impl\AbstractPromise.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */