package com.lody.virtual.helper;

import android.os.IInterface;
import android.os.RemoteException;

public abstract class IPCHelper<S extends IInterface> {
  private S mInterface;
  
  public <R> R call(Callable<S, R> paramCallable) {
    byte b = 0;
    while (b <= 2) {
      S s = this.mInterface;
      if (s == null || s.asBinder().isBinderAlive())
        this.mInterface = getInterface(); 
      try {
        return paramCallable.call(this.mInterface);
      } catch (RemoteException remoteException) {
        remoteException.printStackTrace();
        b++;
      } 
    } 
    return null;
  }
  
  public boolean callBoolean(Callable<S, Boolean> paramCallable) {
    Boolean bool = call(paramCallable);
    return (bool == null) ? false : bool.booleanValue();
  }
  
  public void callVoid(final CallableVoid<S> callable) {
    call(new Callable<S, Void>() {
          public Void call(S param1S) throws RemoteException {
            callable.call(param1S);
            return null;
          }
        });
  }
  
  public abstract S getInterface();
  
  public static interface Callable<S, R> {
    R call(S param1S) throws RemoteException;
  }
  
  public static interface CallableVoid<S> {
    void call(S param1S) throws RemoteException;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\IPCHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */