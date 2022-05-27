package com.lody.virtual.client.hook.secondary;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.io.FileDescriptor;

public class ProxyBinder implements IBinder {
  private IBinder mBase;
  
  IInterface mProxyInterface;
  
  public ProxyBinder(IBinder paramIBinder, IInterface paramIInterface) {
    this.mBase = paramIBinder;
    this.mProxyInterface = paramIInterface;
  }
  
  public void dump(FileDescriptor paramFileDescriptor, String[] paramArrayOfString) throws RemoteException {
    this.mBase.dump(paramFileDescriptor, paramArrayOfString);
  }
  
  public void dumpAsync(FileDescriptor paramFileDescriptor, String[] paramArrayOfString) throws RemoteException {
    this.mBase.dumpAsync(paramFileDescriptor, paramArrayOfString);
  }
  
  public String getInterfaceDescriptor() throws RemoteException {
    return this.mBase.getInterfaceDescriptor();
  }
  
  public boolean isBinderAlive() {
    return this.mBase.isBinderAlive();
  }
  
  public void linkToDeath(IBinder.DeathRecipient paramDeathRecipient, int paramInt) throws RemoteException {
    this.mBase.linkToDeath(paramDeathRecipient, paramInt);
  }
  
  public boolean pingBinder() {
    return this.mBase.pingBinder();
  }
  
  public IInterface queryLocalInterface(String paramString) {
    return this.mProxyInterface;
  }
  
  public boolean transact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2) throws RemoteException {
    return this.mBase.transact(paramInt1, paramParcel1, paramParcel2, paramInt2);
  }
  
  public boolean unlinkToDeath(IBinder.DeathRecipient paramDeathRecipient, int paramInt) {
    return this.mBase.unlinkToDeath(paramDeathRecipient, paramInt);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\secondary\ProxyBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */