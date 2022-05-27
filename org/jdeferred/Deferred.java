package org.jdeferred;

public interface Deferred<D, F, P> extends Promise<D, F, P> {
  Deferred<D, F, P> notify(P paramP);
  
  Promise<D, F, P> promise();
  
  Deferred<D, F, P> reject(F paramF);
  
  Deferred<D, F, P> resolve(D paramD);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\Deferred.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */