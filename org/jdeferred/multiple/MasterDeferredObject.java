package org.jdeferred.multiple;

import java.util.concurrent.atomic.AtomicInteger;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

public class MasterDeferredObject extends DeferredObject<MultipleResults, OneReject, MasterProgress> implements Promise<MultipleResults, OneReject, MasterProgress> {
  private final AtomicInteger doneCount = new AtomicInteger();
  
  private final AtomicInteger failCount = new AtomicInteger();
  
  private final int numberOfPromises;
  
  private final MultipleResults results;
  
  public MasterDeferredObject(Promise... paramVarArgs) {
    if (paramVarArgs != null && paramVarArgs.length != 0) {
      final int index = paramVarArgs.length;
      this.numberOfPromises = i;
      this.results = new MultipleResults(i);
      int j = paramVarArgs.length;
      byte b = 0;
      for (i = 0; b < j; i++) {
        final Promise promise = paramVarArgs[b];
        promise.fail(new FailCallback<Object>() {
              public void onFail(Object param1Object) {
                synchronized (MasterDeferredObject.this) {
                  if (!MasterDeferredObject.this.isPending())
                    return; 
                  int i = MasterDeferredObject.this.failCount.incrementAndGet();
                  MasterDeferredObject masterDeferredObject = MasterDeferredObject.this;
                  MasterProgress masterProgress = new MasterProgress();
                  this(MasterDeferredObject.this.doneCount.get(), i, MasterDeferredObject.this.numberOfPromises);
                  masterDeferredObject.notify(masterProgress);
                  masterDeferredObject = MasterDeferredObject.this;
                  OneReject oneReject = new OneReject();
                  this(index, promise, param1Object);
                  masterDeferredObject.reject(oneReject);
                  return;
                } 
              }
            }).progress(new ProgressCallback() {
              public void onProgress(Object param1Object) {
                synchronized (MasterDeferredObject.this) {
                  if (!MasterDeferredObject.this.isPending())
                    return; 
                  MasterDeferredObject masterDeferredObject = MasterDeferredObject.this;
                  OneProgress oneProgress = new OneProgress();
                  this(MasterDeferredObject.this.doneCount.get(), MasterDeferredObject.this.failCount.get(), MasterDeferredObject.this.numberOfPromises, index, promise, param1Object);
                  masterDeferredObject.notify(oneProgress);
                  return;
                } 
              }
            }).done(new DoneCallback() {
              public void onDone(Object param1Object) {
                synchronized (MasterDeferredObject.this) {
                  if (!MasterDeferredObject.this.isPending())
                    return; 
                  MultipleResults multipleResults = MasterDeferredObject.this.results;
                  int i = index;
                  OneResult oneResult = new OneResult();
                  this(index, promise, param1Object);
                  multipleResults.set(i, oneResult);
                  i = MasterDeferredObject.this.doneCount.incrementAndGet();
                  MasterDeferredObject masterDeferredObject = MasterDeferredObject.this;
                  param1Object = new MasterProgress();
                  super(i, MasterDeferredObject.this.failCount.get(), MasterDeferredObject.this.numberOfPromises);
                  masterDeferredObject.notify(param1Object);
                  if (i == MasterDeferredObject.this.numberOfPromises)
                    MasterDeferredObject.this.resolve(MasterDeferredObject.this.results); 
                  return;
                } 
              }
            });
        b++;
      } 
      return;
    } 
    throw new IllegalArgumentException("Promises is null or empty");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\multiple\MasterDeferredObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */