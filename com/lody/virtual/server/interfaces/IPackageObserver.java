package com.lody.virtual.server.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPackageObserver extends IInterface {
  void onPackageInstalled(String paramString) throws RemoteException;
  
  void onPackageInstalledAsUser(int paramInt, String paramString) throws RemoteException;
  
  void onPackageUninstalled(String paramString) throws RemoteException;
  
  void onPackageUninstalledAsUser(int paramInt, String paramString) throws RemoteException;
  
  public static class Default implements IPackageObserver {
    public IBinder asBinder() {
      return null;
    }
    
    public void onPackageInstalled(String param1String) throws RemoteException {}
    
    public void onPackageInstalledAsUser(int param1Int, String param1String) throws RemoteException {}
    
    public void onPackageUninstalled(String param1String) throws RemoteException {}
    
    public void onPackageUninstalledAsUser(int param1Int, String param1String) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IPackageObserver {
    private static final String DESCRIPTOR = "com.lody.virtual.server.interfaces.IPackageObserver";
    
    static final int TRANSACTION_onPackageInstalled = 1;
    
    static final int TRANSACTION_onPackageInstalledAsUser = 3;
    
    static final int TRANSACTION_onPackageUninstalled = 2;
    
    static final int TRANSACTION_onPackageUninstalledAsUser = 4;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.interfaces.IPackageObserver");
    }
    
    public static IPackageObserver asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.interfaces.IPackageObserver");
      return (iInterface != null && iInterface instanceof IPackageObserver) ? (IPackageObserver)iInterface : new Proxy(param1IBinder);
    }
    
    public static IPackageObserver getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IPackageObserver param1IPackageObserver) {
      if (Proxy.sDefaultImpl == null && param1IPackageObserver != null) {
        Proxy.sDefaultImpl = param1IPackageObserver;
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
            if (param1Int1 != 4) {
              if (param1Int1 != 1598968902)
                return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
              param1Parcel2.writeString("com.lody.virtual.server.interfaces.IPackageObserver");
              return true;
            } 
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IPackageObserver");
            onPackageUninstalledAsUser(param1Parcel1.readInt(), param1Parcel1.readString());
            param1Parcel2.writeNoException();
            return true;
          } 
          param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IPackageObserver");
          onPackageInstalledAsUser(param1Parcel1.readInt(), param1Parcel1.readString());
          param1Parcel2.writeNoException();
          return true;
        } 
        param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IPackageObserver");
        onPackageUninstalled(param1Parcel1.readString());
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IPackageObserver");
      onPackageInstalled(param1Parcel1.readString());
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements IPackageObserver {
      public static IPackageObserver sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.interfaces.IPackageObserver";
      }
      
      public void onPackageInstalled(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageObserver");
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IPackageObserver.Stub.getDefaultImpl() != null) {
            IPackageObserver.Stub.getDefaultImpl().onPackageInstalled(param2String);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void onPackageInstalledAsUser(int param2Int, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageObserver");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IPackageObserver.Stub.getDefaultImpl() != null) {
            IPackageObserver.Stub.getDefaultImpl().onPackageInstalledAsUser(param2Int, param2String);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void onPackageUninstalled(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageObserver");
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IPackageObserver.Stub.getDefaultImpl() != null) {
            IPackageObserver.Stub.getDefaultImpl().onPackageUninstalled(param2String);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void onPackageUninstalledAsUser(int param2Int, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageObserver");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IPackageObserver.Stub.getDefaultImpl() != null) {
            IPackageObserver.Stub.getDefaultImpl().onPackageUninstalledAsUser(param2Int, param2String);
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
  
  private static class Proxy implements IPackageObserver {
    public static IPackageObserver sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.interfaces.IPackageObserver";
    }
    
    public void onPackageInstalled(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageObserver");
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IPackageObserver.Stub.getDefaultImpl() != null) {
          IPackageObserver.Stub.getDefaultImpl().onPackageInstalled(param1String);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void onPackageInstalledAsUser(int param1Int, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageObserver");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IPackageObserver.Stub.getDefaultImpl() != null) {
          IPackageObserver.Stub.getDefaultImpl().onPackageInstalledAsUser(param1Int, param1String);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void onPackageUninstalled(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageObserver");
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IPackageObserver.Stub.getDefaultImpl() != null) {
          IPackageObserver.Stub.getDefaultImpl().onPackageUninstalled(param1String);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void onPackageUninstalledAsUser(int param1Int, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageObserver");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IPackageObserver.Stub.getDefaultImpl() != null) {
          IPackageObserver.Stub.getDefaultImpl().onPackageUninstalledAsUser(param1Int, param1String);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\interfaces\IPackageObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */