package com.lody.virtual.server;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBinderProxyService extends IInterface {
  ComponentName getComponent() throws RemoteException;
  
  IBinder getService() throws RemoteException;
  
  public static class Default implements IBinderProxyService {
    public IBinder asBinder() {
      return null;
    }
    
    public ComponentName getComponent() throws RemoteException {
      return null;
    }
    
    public IBinder getService() throws RemoteException {
      return null;
    }
  }
  
  public static abstract class Stub extends Binder implements IBinderProxyService {
    private static final String DESCRIPTOR = "com.lody.virtual.server.IBinderProxyService";
    
    static final int TRANSACTION_getComponent = 1;
    
    static final int TRANSACTION_getService = 2;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.IBinderProxyService");
    }
    
    public static IBinderProxyService asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.IBinderProxyService");
      return (iInterface != null && iInterface instanceof IBinderProxyService) ? (IBinderProxyService)iInterface : new Proxy(param1IBinder);
    }
    
    public static IBinderProxyService getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IBinderProxyService param1IBinderProxyService) {
      if (Proxy.sDefaultImpl == null && param1IBinderProxyService != null) {
        Proxy.sDefaultImpl = param1IBinderProxyService;
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
          if (param1Int1 != 1598968902)
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
          param1Parcel2.writeString("com.lody.virtual.server.IBinderProxyService");
          return true;
        } 
        param1Parcel1.enforceInterface("com.lody.virtual.server.IBinderProxyService");
        iBinder = getService();
        param1Parcel2.writeNoException();
        param1Parcel2.writeStrongBinder(iBinder);
        return true;
      } 
      iBinder.enforceInterface("com.lody.virtual.server.IBinderProxyService");
      ComponentName componentName = getComponent();
      param1Parcel2.writeNoException();
      if (componentName != null) {
        param1Parcel2.writeInt(1);
        componentName.writeToParcel(param1Parcel2, 1);
      } else {
        param1Parcel2.writeInt(0);
      } 
      return true;
    }
    
    private static class Proxy implements IBinderProxyService {
      public static IBinderProxyService sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public ComponentName getComponent() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          ComponentName componentName;
          parcel1.writeInterfaceToken("com.lody.virtual.server.IBinderProxyService");
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IBinderProxyService.Stub.getDefaultImpl() != null) {
            componentName = IBinderProxyService.Stub.getDefaultImpl().getComponent();
            return componentName;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            componentName = (ComponentName)ComponentName.CREATOR.createFromParcel(parcel2);
          } else {
            componentName = null;
          } 
          return componentName;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.IBinderProxyService";
      }
      
      public IBinder getService() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.IBinderProxyService");
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IBinderProxyService.Stub.getDefaultImpl() != null)
            return IBinderProxyService.Stub.getDefaultImpl().getService(); 
          parcel2.readException();
          return parcel2.readStrongBinder();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements IBinderProxyService {
    public static IBinderProxyService sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public ComponentName getComponent() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        ComponentName componentName;
        parcel1.writeInterfaceToken("com.lody.virtual.server.IBinderProxyService");
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IBinderProxyService.Stub.getDefaultImpl() != null) {
          componentName = IBinderProxyService.Stub.getDefaultImpl().getComponent();
          return componentName;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          componentName = (ComponentName)ComponentName.CREATOR.createFromParcel(parcel2);
        } else {
          componentName = null;
        } 
        return componentName;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.IBinderProxyService";
    }
    
    public IBinder getService() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.IBinderProxyService");
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IBinderProxyService.Stub.getDefaultImpl() != null)
          return IBinderProxyService.Stub.getDefaultImpl().getService(); 
        parcel2.readException();
        return parcel2.readStrongBinder();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\IBinderProxyService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */