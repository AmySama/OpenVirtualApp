package org.jdeferred.impl;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;

public class DeferredObject<D, F, P> extends AbstractPromise<D, F, P> implements Deferred<D, F, P> {
  public Deferred<D, F, P> notify(P paramP) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual isPending : ()Z
    //   6: ifeq -> 18
    //   9: aload_0
    //   10: aload_1
    //   11: invokevirtual triggerProgress : (Ljava/lang/Object;)V
    //   14: aload_0
    //   15: monitorexit
    //   16: aload_0
    //   17: areturn
    //   18: new java/lang/IllegalStateException
    //   21: astore_1
    //   22: aload_1
    //   23: ldc 'Deferred object already finished, cannot notify progress'
    //   25: invokespecial <init> : (Ljava/lang/String;)V
    //   28: aload_1
    //   29: athrow
    //   30: astore_1
    //   31: aload_0
    //   32: monitorexit
    //   33: aload_1
    //   34: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	30	finally
    //   18	30	30	finally
    //   31	33	30	finally
  }
  
  public Promise<D, F, P> promise() {
    return this;
  }
  
  public Deferred<D, F, P> reject(F paramF) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual isPending : ()Z
    //   6: ifeq -> 53
    //   9: aload_0
    //   10: getstatic org/jdeferred/Promise$State.REJECTED : Lorg/jdeferred/Promise$State;
    //   13: putfield state : Lorg/jdeferred/Promise$State;
    //   16: aload_0
    //   17: aload_1
    //   18: putfield rejectResult : Ljava/lang/Object;
    //   21: aload_0
    //   22: aload_1
    //   23: invokevirtual triggerFail : (Ljava/lang/Object;)V
    //   26: aload_0
    //   27: aload_0
    //   28: getfield state : Lorg/jdeferred/Promise$State;
    //   31: aconst_null
    //   32: aload_1
    //   33: invokevirtual triggerAlways : (Lorg/jdeferred/Promise$State;Ljava/lang/Object;Ljava/lang/Object;)V
    //   36: aload_0
    //   37: monitorexit
    //   38: aload_0
    //   39: areturn
    //   40: astore_2
    //   41: aload_0
    //   42: aload_0
    //   43: getfield state : Lorg/jdeferred/Promise$State;
    //   46: aconst_null
    //   47: aload_1
    //   48: invokevirtual triggerAlways : (Lorg/jdeferred/Promise$State;Ljava/lang/Object;Ljava/lang/Object;)V
    //   51: aload_2
    //   52: athrow
    //   53: new java/lang/IllegalStateException
    //   56: astore_1
    //   57: aload_1
    //   58: ldc 'Deferred object already finished, cannot reject again'
    //   60: invokespecial <init> : (Ljava/lang/String;)V
    //   63: aload_1
    //   64: athrow
    //   65: astore_1
    //   66: aload_0
    //   67: monitorexit
    //   68: aload_1
    //   69: athrow
    // Exception table:
    //   from	to	target	type
    //   2	21	65	finally
    //   21	26	40	finally
    //   26	38	65	finally
    //   41	53	65	finally
    //   53	65	65	finally
    //   66	68	65	finally
  }
  
  public Deferred<D, F, P> resolve(D paramD) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual isPending : ()Z
    //   6: ifeq -> 53
    //   9: aload_0
    //   10: getstatic org/jdeferred/Promise$State.RESOLVED : Lorg/jdeferred/Promise$State;
    //   13: putfield state : Lorg/jdeferred/Promise$State;
    //   16: aload_0
    //   17: aload_1
    //   18: putfield resolveResult : Ljava/lang/Object;
    //   21: aload_0
    //   22: aload_1
    //   23: invokevirtual triggerDone : (Ljava/lang/Object;)V
    //   26: aload_0
    //   27: aload_0
    //   28: getfield state : Lorg/jdeferred/Promise$State;
    //   31: aload_1
    //   32: aconst_null
    //   33: invokevirtual triggerAlways : (Lorg/jdeferred/Promise$State;Ljava/lang/Object;Ljava/lang/Object;)V
    //   36: aload_0
    //   37: monitorexit
    //   38: aload_0
    //   39: areturn
    //   40: astore_2
    //   41: aload_0
    //   42: aload_0
    //   43: getfield state : Lorg/jdeferred/Promise$State;
    //   46: aload_1
    //   47: aconst_null
    //   48: invokevirtual triggerAlways : (Lorg/jdeferred/Promise$State;Ljava/lang/Object;Ljava/lang/Object;)V
    //   51: aload_2
    //   52: athrow
    //   53: new java/lang/IllegalStateException
    //   56: astore_1
    //   57: aload_1
    //   58: ldc 'Deferred object already finished, cannot resolve again'
    //   60: invokespecial <init> : (Ljava/lang/String;)V
    //   63: aload_1
    //   64: athrow
    //   65: astore_1
    //   66: aload_0
    //   67: monitorexit
    //   68: aload_1
    //   69: athrow
    // Exception table:
    //   from	to	target	type
    //   2	21	65	finally
    //   21	26	40	finally
    //   26	38	65	finally
    //   41	53	65	finally
    //   53	65	65	finally
    //   66	68	65	finally
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\impl\DeferredObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */