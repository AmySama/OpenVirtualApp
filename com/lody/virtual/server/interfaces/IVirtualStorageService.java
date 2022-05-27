package com.lody.virtual.server.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IVirtualStorageService extends IInterface {
  String getVirtualStorage(String paramString, int paramInt) throws RemoteException;
  
  boolean isVirtualStorageEnable(String paramString, int paramInt) throws RemoteException;
  
  void setVirtualStorage(String paramString1, int paramInt, String paramString2) throws RemoteException;
  
  void setVirtualStorageState(String paramString, int paramInt, boolean paramBoolean) throws RemoteException;
  
  public static class Default implements IVirtualStorageService {
    public IBinder asBinder() {
      return null;
    }
    
    public String getVirtualStorage(String param1String, int param1Int) throws RemoteException {
      return null;
    }
    
    public boolean isVirtualStorageEnable(String param1String, int param1Int) throws RemoteException {
      return false;
    }
    
    public void setVirtualStorage(String param1String1, int param1Int, String param1String2) throws RemoteException {}
    
    public void setVirtualStorageState(String param1String, int param1Int, boolean param1Boolean) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IVirtualStorageService {
    private static final String DESCRIPTOR = "com.lody.virtual.server.interfaces.IVirtualStorageService";
    
    static final int TRANSACTION_getVirtualStorage = 2;
    
    static final int TRANSACTION_isVirtualStorageEnable = 4;
    
    static final int TRANSACTION_setVirtualStorage = 1;
    
    static final int TRANSACTION_setVirtualStorageState = 3;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.interfaces.IVirtualStorageService");
    }
    
    public static IVirtualStorageService asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.interfaces.IVirtualStorageService");
      return (iInterface != null && iInterface instanceof IVirtualStorageService) ? (IVirtualStorageService)iInterface : new Proxy(param1IBinder);
    }
    
    public static IVirtualStorageService getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IVirtualStorageService param1IVirtualStorageService) {
      if (Proxy.sDefaultImpl == null && param1IVirtualStorageService != null) {
        Proxy.sDefaultImpl = param1IVirtualStorageService;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      String str;
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          boolean bool;
          if (param1Int1 != 3) {
            if (param1Int1 != 4) {
              if (param1Int1 != 1598968902)
                return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
              param1Parcel2.writeString("com.lody.virtual.server.interfaces.IVirtualStorageService");
              return true;
            } 
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IVirtualStorageService");
            boolean bool1 = isVirtualStorageEnable(param1Parcel1.readString(), param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool1);
            return true;
          } 
          param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IVirtualStorageService");
          String str1 = param1Parcel1.readString();
          param1Int1 = param1Parcel1.readInt();
          if (param1Parcel1.readInt() != 0) {
            bool = true;
          } else {
            bool = false;
          } 
          setVirtualStorageState(str1, param1Int1, bool);
          param1Parcel2.writeNoException();
          return true;
        } 
        param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IVirtualStorageService");
        str = getVirtualStorage(param1Parcel1.readString(), param1Parcel1.readInt());
        param1Parcel2.writeNoException();
        param1Parcel2.writeString(str);
        return true;
      } 
      str.enforceInterface("com.lody.virtual.server.interfaces.IVirtualStorageService");
      setVirtualStorage(str.readString(), str.readInt(), str.readString());
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements IVirtualStorageService {
      public static IVirtualStorageService sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.interfaces.IVirtualStorageService";
      }
      
      public String getVirtualStorage(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualStorageService");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IVirtualStorageService.Stub.getDefaultImpl() != null) {
            param2String = IVirtualStorageService.Stub.getDefaultImpl().getVirtualStorage(param2String, param2Int);
            return param2String;
          } 
          parcel2.readException();
          param2String = parcel2.readString();
          return param2String;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean isVirtualStorageEnable(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualStorageService");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(4, parcel1, parcel2, 0) && IVirtualStorageService.Stub.getDefaultImpl() != null) {
            bool = IVirtualStorageService.Stub.getDefaultImpl().isVirtualStorageEnable(param2String, param2Int);
            return bool;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          if (param2Int != 0)
            bool = true; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setVirtualStorage(String param2String1, int param2Int, String param2String2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualStorageService");
          parcel1.writeString(param2String1);
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String2);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IVirtualStorageService.Stub.getDefaultImpl() != null) {
            IVirtualStorageService.Stub.getDefaultImpl().setVirtualStorage(param2String1, param2Int, param2String2);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setVirtualStorageState(String param2String, int param2Int, boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualStorageService");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IVirtualStorageService.Stub.getDefaultImpl() != null) {
            IVirtualStorageService.Stub.getDefaultImpl().setVirtualStorageState(param2String, param2Int, param2Boolean);
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
  
  private static class Proxy implements IVirtualStorageService {
    public static IVirtualStorageService sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.interfaces.IVirtualStorageService";
    }
    
    public String getVirtualStorage(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualStorageService");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IVirtualStorageService.Stub.getDefaultImpl() != null) {
          param1String = IVirtualStorageService.Stub.getDefaultImpl().getVirtualStorage(param1String, param1Int);
          return param1String;
        } 
        parcel2.readException();
        param1String = parcel2.readString();
        return param1String;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean isVirtualStorageEnable(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualStorageService");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(4, parcel1, parcel2, 0) && IVirtualStorageService.Stub.getDefaultImpl() != null) {
          bool = IVirtualStorageService.Stub.getDefaultImpl().isVirtualStorageEnable(param1String, param1Int);
          return bool;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        if (param1Int != 0)
          bool = true; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setVirtualStorage(String param1String1, int param1Int, String param1String2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualStorageService");
        parcel1.writeString(param1String1);
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String2);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IVirtualStorageService.Stub.getDefaultImpl() != null) {
          IVirtualStorageService.Stub.getDefaultImpl().setVirtualStorage(param1String1, param1Int, param1String2);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setVirtualStorageState(String param1String, int param1Int, boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IVirtualStorageService");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IVirtualStorageService.Stub.getDefaultImpl() != null) {
          IVirtualStorageService.Stub.getDefaultImpl().setVirtualStorageState(param1String, param1Int, param1Boolean);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\interfaces\IVirtualStorageService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */