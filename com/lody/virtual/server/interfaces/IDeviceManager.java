package com.lody.virtual.server.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.lody.virtual.remote.VDeviceConfig;

public interface IDeviceManager extends IInterface {
  VDeviceConfig getDeviceConfig(int paramInt) throws RemoteException;
  
  boolean isEnable(int paramInt) throws RemoteException;
  
  void setEnable(int paramInt, boolean paramBoolean) throws RemoteException;
  
  void updateDeviceConfig(int paramInt, VDeviceConfig paramVDeviceConfig) throws RemoteException;
  
  public static class Default implements IDeviceManager {
    public IBinder asBinder() {
      return null;
    }
    
    public VDeviceConfig getDeviceConfig(int param1Int) throws RemoteException {
      return null;
    }
    
    public boolean isEnable(int param1Int) throws RemoteException {
      return false;
    }
    
    public void setEnable(int param1Int, boolean param1Boolean) throws RemoteException {}
    
    public void updateDeviceConfig(int param1Int, VDeviceConfig param1VDeviceConfig) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IDeviceManager {
    private static final String DESCRIPTOR = "com.lody.virtual.server.interfaces.IDeviceManager";
    
    static final int TRANSACTION_getDeviceConfig = 1;
    
    static final int TRANSACTION_isEnable = 3;
    
    static final int TRANSACTION_setEnable = 4;
    
    static final int TRANSACTION_updateDeviceConfig = 2;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.interfaces.IDeviceManager");
    }
    
    public static IDeviceManager asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.interfaces.IDeviceManager");
      return (iInterface != null && iInterface instanceof IDeviceManager) ? (IDeviceManager)iInterface : new Proxy(param1IBinder);
    }
    
    public static IDeviceManager getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IDeviceManager param1IDeviceManager) {
      if (Proxy.sDefaultImpl == null && param1IDeviceManager != null) {
        Proxy.sDefaultImpl = param1IDeviceManager;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      boolean bool = false;
      if (param1Int1 != 1) {
        if (param1Int1 != 2) {
          if (param1Int1 != 3) {
            if (param1Int1 != 4) {
              if (param1Int1 != 1598968902)
                return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
              param1Parcel2.writeString("com.lody.virtual.server.interfaces.IDeviceManager");
              return true;
            } 
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IDeviceManager");
            param1Int1 = param1Parcel1.readInt();
            if (param1Parcel1.readInt() != 0)
              bool = true; 
            setEnable(param1Int1, bool);
            param1Parcel2.writeNoException();
            return true;
          } 
          param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IDeviceManager");
          boolean bool1 = isEnable(param1Parcel1.readInt());
          param1Parcel2.writeNoException();
          param1Parcel2.writeInt(bool1);
          return true;
        } 
        param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IDeviceManager");
        param1Int1 = param1Parcel1.readInt();
        if (param1Parcel1.readInt() != 0) {
          VDeviceConfig vDeviceConfig1 = (VDeviceConfig)VDeviceConfig.CREATOR.createFromParcel(param1Parcel1);
        } else {
          param1Parcel1 = null;
        } 
        updateDeviceConfig(param1Int1, (VDeviceConfig)param1Parcel1);
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IDeviceManager");
      VDeviceConfig vDeviceConfig = getDeviceConfig(param1Parcel1.readInt());
      param1Parcel2.writeNoException();
      if (vDeviceConfig != null) {
        param1Parcel2.writeInt(1);
        vDeviceConfig.writeToParcel(param1Parcel2, 1);
      } else {
        param1Parcel2.writeInt(0);
      } 
      return true;
    }
    
    private static class Proxy implements IDeviceManager {
      public static IDeviceManager sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public VDeviceConfig getDeviceConfig(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          VDeviceConfig vDeviceConfig;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IDeviceManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IDeviceManager.Stub.getDefaultImpl() != null) {
            vDeviceConfig = IDeviceManager.Stub.getDefaultImpl().getDeviceConfig(param2Int);
            return vDeviceConfig;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            vDeviceConfig = (VDeviceConfig)VDeviceConfig.CREATOR.createFromParcel(parcel2);
          } else {
            vDeviceConfig = null;
          } 
          return vDeviceConfig;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.interfaces.IDeviceManager";
      }
      
      public boolean isEnable(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IDeviceManager");
          parcel1.writeInt(param2Int);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(3, parcel1, parcel2, 0) && IDeviceManager.Stub.getDefaultImpl() != null) {
            bool = IDeviceManager.Stub.getDefaultImpl().isEnable(param2Int);
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
      
      public void setEnable(int param2Int, boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IDeviceManager");
          parcel1.writeInt(param2Int);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IDeviceManager.Stub.getDefaultImpl() != null) {
            IDeviceManager.Stub.getDefaultImpl().setEnable(param2Int, param2Boolean);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void updateDeviceConfig(int param2Int, VDeviceConfig param2VDeviceConfig) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IDeviceManager");
          parcel1.writeInt(param2Int);
          if (param2VDeviceConfig != null) {
            parcel1.writeInt(1);
            param2VDeviceConfig.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IDeviceManager.Stub.getDefaultImpl() != null) {
            IDeviceManager.Stub.getDefaultImpl().updateDeviceConfig(param2Int, param2VDeviceConfig);
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
  
  private static class Proxy implements IDeviceManager {
    public static IDeviceManager sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public VDeviceConfig getDeviceConfig(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        VDeviceConfig vDeviceConfig;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IDeviceManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IDeviceManager.Stub.getDefaultImpl() != null) {
          vDeviceConfig = IDeviceManager.Stub.getDefaultImpl().getDeviceConfig(param1Int);
          return vDeviceConfig;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          vDeviceConfig = (VDeviceConfig)VDeviceConfig.CREATOR.createFromParcel(parcel2);
        } else {
          vDeviceConfig = null;
        } 
        return vDeviceConfig;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.interfaces.IDeviceManager";
    }
    
    public boolean isEnable(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IDeviceManager");
        parcel1.writeInt(param1Int);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(3, parcel1, parcel2, 0) && IDeviceManager.Stub.getDefaultImpl() != null) {
          bool = IDeviceManager.Stub.getDefaultImpl().isEnable(param1Int);
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
    
    public void setEnable(int param1Int, boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IDeviceManager");
        parcel1.writeInt(param1Int);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IDeviceManager.Stub.getDefaultImpl() != null) {
          IDeviceManager.Stub.getDefaultImpl().setEnable(param1Int, param1Boolean);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void updateDeviceConfig(int param1Int, VDeviceConfig param1VDeviceConfig) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IDeviceManager");
        parcel1.writeInt(param1Int);
        if (param1VDeviceConfig != null) {
          parcel1.writeInt(1);
          param1VDeviceConfig.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IDeviceManager.Stub.getDefaultImpl() != null) {
          IDeviceManager.Stub.getDefaultImpl().updateDeviceConfig(param1Int, param1VDeviceConfig);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\interfaces\IDeviceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */