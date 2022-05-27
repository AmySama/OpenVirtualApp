package com.lody.virtual.server.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IProcessObserver extends IInterface {
  void onProcessCreated(String paramString1, String paramString2) throws RemoteException;
  
  void onProcessDied(String paramString1, String paramString2) throws RemoteException;
  
  public static class Default implements IProcessObserver {
    public IBinder asBinder() {
      return null;
    }
    
    public void onProcessCreated(String param1String1, String param1String2) throws RemoteException {}
    
    public void onProcessDied(String param1String1, String param1String2) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IProcessObserver {
    private static final String DESCRIPTOR = "com.lody.virtual.server.interfaces.IProcessObserver";
    
    static final int TRANSACTION_onProcessCreated = 1;
    
    static final int TRANSACTION_onProcessDied = 2;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.interfaces.IProcessObserver");
    }
    
    public static IProcessObserver asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.interfaces.IProcessObserver");
      return (iInterface != null && iInterface instanceof IProcessObserver) ? (IProcessObserver)iInterface : new Proxy(param1IBinder);
    }
    
    public static IProcessObserver getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IProcessObserver param1IProcessObserver) {
      if (Proxy.sDefaultImpl == null && param1IProcessObserver != null) {
        Proxy.sDefaultImpl = param1IProcessObserver;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          if (param1Int1 != 1598968902)
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
          param1Parcel2.writeString("com.lody.virtual.server.interfaces.IProcessObserver");
          return true;
        } 
        param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IProcessObserver");
        onProcessDied(param1Parcel1.readString(), param1Parcel1.readString());
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IProcessObserver");
      onProcessCreated(param1Parcel1.readString(), param1Parcel1.readString());
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements IProcessObserver {
      public static IProcessObserver sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.interfaces.IProcessObserver";
      }
      
      public void onProcessCreated(String param2String1, String param2String2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IProcessObserver");
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IProcessObserver.Stub.getDefaultImpl() != null) {
            IProcessObserver.Stub.getDefaultImpl().onProcessCreated(param2String1, param2String2);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void onProcessDied(String param2String1, String param2String2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IProcessObserver");
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IProcessObserver.Stub.getDefaultImpl() != null) {
            IProcessObserver.Stub.getDefaultImpl().onProcessDied(param2String1, param2String2);
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
  
  private static class Proxy implements IProcessObserver {
    public static IProcessObserver sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.interfaces.IProcessObserver";
    }
    
    public void onProcessCreated(String param1String1, String param1String2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IProcessObserver");
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IProcessObserver.Stub.getDefaultImpl() != null) {
          IProcessObserver.Stub.getDefaultImpl().onProcessCreated(param1String1, param1String2);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void onProcessDied(String param1String1, String param1String2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IProcessObserver");
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IProcessObserver.Stub.getDefaultImpl() != null) {
          IProcessObserver.Stub.getDefaultImpl().onProcessDied(param1String1, param1String2);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\interfaces\IProcessObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */