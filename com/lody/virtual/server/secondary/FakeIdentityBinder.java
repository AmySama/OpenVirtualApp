package com.lody.virtual.server.secondary;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteException;

public class FakeIdentityBinder extends Binder {
  protected Binder mBase;
  
  protected int mFakePid;
  
  protected int mFakeUid;
  
  public FakeIdentityBinder(Binder paramBinder, int paramInt1, int paramInt2) {
    this.mBase = paramBinder;
    this.mFakeUid = paramInt1;
    this.mFakePid = paramInt2;
  }
  
  public static long getIdentity(int paramInt1, int paramInt2) {
    long l = paramInt1;
    return paramInt2 | l << 32L;
  }
  
  public static void setIdentity(int paramInt1, int paramInt2) {
    Binder.restoreCallingIdentity(getIdentity(paramInt1, paramInt2));
  }
  
  public static void setSystemIdentity() {
    Binder.restoreCallingIdentity(getIdentity(1000, Process.myPid()));
  }
  
  public void attachInterface(IInterface paramIInterface, String paramString) {
    this.mBase.attachInterface(paramIInterface, paramString);
  }
  
  protected long getFakeIdentity() {
    return getIdentity(getFakeUid(), getFakePid());
  }
  
  protected int getFakePid() {
    return this.mFakePid;
  }
  
  protected int getFakeUid() {
    return this.mFakeUid;
  }
  
  public String getInterfaceDescriptor() {
    return this.mBase.getInterfaceDescriptor();
  }
  
  public boolean isBinderAlive() {
    return this.mBase.isBinderAlive();
  }
  
  public void linkToDeath(IBinder.DeathRecipient paramDeathRecipient, int paramInt) {
    this.mBase.linkToDeath(paramDeathRecipient, paramInt);
  }
  
  public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2) throws RemoteException {
    long l = Binder.clearCallingIdentity();
    try {
      Binder.restoreCallingIdentity(getFakeIdentity());
      return this.mBase.transact(paramInt1, paramParcel1, paramParcel2, paramInt2);
    } finally {
      Binder.restoreCallingIdentity(l);
    } 
  }
  
  public boolean pingBinder() {
    return this.mBase.pingBinder();
  }
  
  public final IInterface queryLocalInterface(String paramString) {
    return this.mBase.queryLocalInterface(paramString);
  }
  
  public boolean unlinkToDeath(IBinder.DeathRecipient paramDeathRecipient, int paramInt) {
    return this.mBase.unlinkToDeath(paramDeathRecipient, paramInt);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\secondary\FakeIdentityBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */