package com.lody.virtual.server.interfaces;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IIntentFilterObserver extends IInterface {
  Intent filter(Intent paramIntent) throws RemoteException;
  
  IBinder getCallBack() throws RemoteException;
  
  void setCallBack(IBinder paramIBinder) throws RemoteException;
  
  public static class Default implements IIntentFilterObserver {
    public IBinder asBinder() {
      return null;
    }
    
    public Intent filter(Intent param1Intent) throws RemoteException {
      return null;
    }
    
    public IBinder getCallBack() throws RemoteException {
      return null;
    }
    
    public void setCallBack(IBinder param1IBinder) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IIntentFilterObserver {
    private static final String DESCRIPTOR = "com.lody.virtual.server.interfaces.IIntentFilterObserver";
    
    static final int TRANSACTION_filter = 1;
    
    static final int TRANSACTION_getCallBack = 3;
    
    static final int TRANSACTION_setCallBack = 2;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.interfaces.IIntentFilterObserver");
    }
    
    public static IIntentFilterObserver asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.interfaces.IIntentFilterObserver");
      return (iInterface != null && iInterface instanceof IIntentFilterObserver) ? (IIntentFilterObserver)iInterface : new Proxy(param1IBinder);
    }
    
    public static IIntentFilterObserver getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IIntentFilterObserver param1IIntentFilterObserver) {
      if (Proxy.sDefaultImpl == null && param1IIntentFilterObserver != null) {
        Proxy.sDefaultImpl = param1IIntentFilterObserver;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      IBinder iBinder;
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          if (param1Int1 != 3) {
            if (param1Int1 != 1598968902)
              return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
            param1Parcel2.writeString("com.lody.virtual.server.interfaces.IIntentFilterObserver");
            return true;
          } 
          param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IIntentFilterObserver");
          iBinder = getCallBack();
          param1Parcel2.writeNoException();
          param1Parcel2.writeStrongBinder(iBinder);
          return true;
        } 
        iBinder.enforceInterface("com.lody.virtual.server.interfaces.IIntentFilterObserver");
        setCallBack(iBinder.readStrongBinder());
        param1Parcel2.writeNoException();
        return true;
      } 
      iBinder.enforceInterface("com.lody.virtual.server.interfaces.IIntentFilterObserver");
      if (iBinder.readInt() != 0) {
        Intent intent1 = (Intent)Intent.CREATOR.createFromParcel((Parcel)iBinder);
      } else {
        iBinder = null;
      } 
      Intent intent = filter((Intent)iBinder);
      param1Parcel2.writeNoException();
      if (intent != null) {
        param1Parcel2.writeInt(1);
        intent.writeToParcel(param1Parcel2, 1);
      } else {
        param1Parcel2.writeInt(0);
      } 
      return true;
    }
    
    private static class Proxy implements IIntentFilterObserver {
      public static IIntentFilterObserver sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public Intent filter(Intent param2Intent) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IIntentFilterObserver");
          if (param2Intent != null) {
            parcel1.writeInt(1);
            param2Intent.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IIntentFilterObserver.Stub.getDefaultImpl() != null) {
            param2Intent = IIntentFilterObserver.Stub.getDefaultImpl().filter(param2Intent);
            return param2Intent;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            param2Intent = (Intent)Intent.CREATOR.createFromParcel(parcel2);
          } else {
            param2Intent = null;
          } 
          return param2Intent;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public IBinder getCallBack() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IIntentFilterObserver");
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IIntentFilterObserver.Stub.getDefaultImpl() != null)
            return IIntentFilterObserver.Stub.getDefaultImpl().getCallBack(); 
          parcel2.readException();
          return parcel2.readStrongBinder();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.interfaces.IIntentFilterObserver";
      }
      
      public void setCallBack(IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IIntentFilterObserver");
          parcel1.writeStrongBinder(param2IBinder);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IIntentFilterObserver.Stub.getDefaultImpl() != null) {
            IIntentFilterObserver.Stub.getDefaultImpl().setCallBack(param2IBinder);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements IIntentFilterObserver {
    public static IIntentFilterObserver sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public Intent filter(Intent param1Intent) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IIntentFilterObserver");
        if (param1Intent != null) {
          parcel1.writeInt(1);
          param1Intent.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IIntentFilterObserver.Stub.getDefaultImpl() != null) {
          param1Intent = IIntentFilterObserver.Stub.getDefaultImpl().filter(param1Intent);
          return param1Intent;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          param1Intent = (Intent)Intent.CREATOR.createFromParcel(parcel2);
        } else {
          param1Intent = null;
        } 
        return param1Intent;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public IBinder getCallBack() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IIntentFilterObserver");
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IIntentFilterObserver.Stub.getDefaultImpl() != null)
          return IIntentFilterObserver.Stub.getDefaultImpl().getCallBack(); 
        parcel2.readException();
        return parcel2.readStrongBinder();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.interfaces.IIntentFilterObserver";
    }
    
    public void setCallBack(IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IIntentFilterObserver");
        parcel1.writeStrongBinder(param1IBinder);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IIntentFilterObserver.Stub.getDefaultImpl() != null) {
          IIntentFilterObserver.Stub.getDefaultImpl().setCallBack(param1IBinder);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\interfaces\IIntentFilterObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */