package com.lody.virtual.server.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IServiceFetcher extends IInterface {
  void addService(String paramString, IBinder paramIBinder) throws RemoteException;
  
  IBinder getService(String paramString) throws RemoteException;
  
  void removeService(String paramString) throws RemoteException;
  
  public static class Default implements IServiceFetcher {
    public void addService(String param1String, IBinder param1IBinder) throws RemoteException {}
    
    public IBinder asBinder() {
      return null;
    }
    
    public IBinder getService(String param1String) throws RemoteException {
      return null;
    }
    
    public void removeService(String param1String) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IServiceFetcher {
    private static final String DESCRIPTOR = "com.lody.virtual.server.interfaces.IServiceFetcher";
    
    static final int TRANSACTION_addService = 2;
    
    static final int TRANSACTION_getService = 1;
    
    static final int TRANSACTION_removeService = 3;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.interfaces.IServiceFetcher");
    }
    
    public static IServiceFetcher asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.interfaces.IServiceFetcher");
      return (iInterface != null && iInterface instanceof IServiceFetcher) ? (IServiceFetcher)iInterface : new Proxy(param1IBinder);
    }
    
    public static IServiceFetcher getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IServiceFetcher param1IServiceFetcher) {
      if (Proxy.sDefaultImpl == null && param1IServiceFetcher != null) {
        Proxy.sDefaultImpl = param1IServiceFetcher;
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
          if (param1Int1 != 3) {
            if (param1Int1 != 1598968902)
              return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
            param1Parcel2.writeString("com.lody.virtual.server.interfaces.IServiceFetcher");
            return true;
          } 
          param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IServiceFetcher");
          removeService(param1Parcel1.readString());
          param1Parcel2.writeNoException();
          return true;
        } 
        param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IServiceFetcher");
        addService(param1Parcel1.readString(), param1Parcel1.readStrongBinder());
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IServiceFetcher");
      IBinder iBinder = getService(param1Parcel1.readString());
      param1Parcel2.writeNoException();
      param1Parcel2.writeStrongBinder(iBinder);
      return true;
    }
    
    private static class Proxy implements IServiceFetcher {
      public static IServiceFetcher sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public void addService(String param2String, IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IServiceFetcher");
          parcel1.writeString(param2String);
          parcel1.writeStrongBinder(param2IBinder);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IServiceFetcher.Stub.getDefaultImpl() != null) {
            IServiceFetcher.Stub.getDefaultImpl().addService(param2String, param2IBinder);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.interfaces.IServiceFetcher";
      }
      
      public IBinder getService(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IServiceFetcher");
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IServiceFetcher.Stub.getDefaultImpl() != null)
            return IServiceFetcher.Stub.getDefaultImpl().getService(param2String); 
          parcel2.readException();
          return parcel2.readStrongBinder();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void removeService(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IServiceFetcher");
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IServiceFetcher.Stub.getDefaultImpl() != null) {
            IServiceFetcher.Stub.getDefaultImpl().removeService(param2String);
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
  
  private static class Proxy implements IServiceFetcher {
    public static IServiceFetcher sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public void addService(String param1String, IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IServiceFetcher");
        parcel1.writeString(param1String);
        parcel1.writeStrongBinder(param1IBinder);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IServiceFetcher.Stub.getDefaultImpl() != null) {
          IServiceFetcher.Stub.getDefaultImpl().addService(param1String, param1IBinder);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.interfaces.IServiceFetcher";
    }
    
    public IBinder getService(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IServiceFetcher");
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IServiceFetcher.Stub.getDefaultImpl() != null)
          return IServiceFetcher.Stub.getDefaultImpl().getService(param1String); 
        parcel2.readException();
        return parcel2.readStrongBinder();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void removeService(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IServiceFetcher");
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IServiceFetcher.Stub.getDefaultImpl() != null) {
          IServiceFetcher.Stub.getDefaultImpl().removeService(param1String);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\interfaces\IServiceFetcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */