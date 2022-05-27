package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IStopUserCallback extends IInterface {
  void userStopAborted(int paramInt) throws RemoteException;
  
  void userStopped(int paramInt) throws RemoteException;
  
  public static class Default implements IStopUserCallback {
    public IBinder asBinder() {
      return null;
    }
    
    public void userStopAborted(int param1Int) throws RemoteException {}
    
    public void userStopped(int param1Int) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IStopUserCallback {
    private static final String DESCRIPTOR = "android.app.IStopUserCallback";
    
    static final int TRANSACTION_userStopAborted = 2;
    
    static final int TRANSACTION_userStopped = 1;
    
    public Stub() {
      attachInterface(this, "android.app.IStopUserCallback");
    }
    
    public static IStopUserCallback asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.app.IStopUserCallback");
      return (iInterface != null && iInterface instanceof IStopUserCallback) ? (IStopUserCallback)iInterface : new Proxy(param1IBinder);
    }
    
    public static IStopUserCallback getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IStopUserCallback param1IStopUserCallback) {
      if (Proxy.sDefaultImpl == null && param1IStopUserCallback != null) {
        Proxy.sDefaultImpl = param1IStopUserCallback;
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
          param1Parcel2.writeString("android.app.IStopUserCallback");
          return true;
        } 
        param1Parcel1.enforceInterface("android.app.IStopUserCallback");
        userStopAborted(param1Parcel1.readInt());
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel1.enforceInterface("android.app.IStopUserCallback");
      userStopped(param1Parcel1.readInt());
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements IStopUserCallback {
      public static IStopUserCallback sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "android.app.IStopUserCallback";
      }
      
      public void userStopAborted(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.app.IStopUserCallback");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IStopUserCallback.Stub.getDefaultImpl() != null) {
            IStopUserCallback.Stub.getDefaultImpl().userStopAborted(param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void userStopped(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.app.IStopUserCallback");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IStopUserCallback.Stub.getDefaultImpl() != null) {
            IStopUserCallback.Stub.getDefaultImpl().userStopped(param2Int);
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
  
  private static class Proxy implements IStopUserCallback {
    public static IStopUserCallback sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "android.app.IStopUserCallback";
    }
    
    public void userStopAborted(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.app.IStopUserCallback");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IStopUserCallback.Stub.getDefaultImpl() != null) {
          IStopUserCallback.Stub.getDefaultImpl().userStopAborted(param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void userStopped(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.app.IStopUserCallback");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IStopUserCallback.Stub.getDefaultImpl() != null) {
          IStopUserCallback.Stub.getDefaultImpl().userStopped(param1Int);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\app\IStopUserCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */