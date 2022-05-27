package android.app;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IServiceConnection extends IInterface {
  void connected(ComponentName paramComponentName, IBinder paramIBinder) throws RemoteException;
  
  public static class Default implements IServiceConnection {
    public IBinder asBinder() {
      return null;
    }
    
    public void connected(ComponentName param1ComponentName, IBinder param1IBinder) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IServiceConnection {
    private static final String DESCRIPTOR = "android.app.IServiceConnection";
    
    static final int TRANSACTION_connected = 1;
    
    public Stub() {
      attachInterface(this, "android.app.IServiceConnection");
    }
    
    public static IServiceConnection asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.app.IServiceConnection");
      return (iInterface != null && iInterface instanceof IServiceConnection) ? (IServiceConnection)iInterface : new Proxy(param1IBinder);
    }
    
    public static IServiceConnection getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IServiceConnection param1IServiceConnection) {
      if (Proxy.sDefaultImpl == null && param1IServiceConnection != null) {
        Proxy.sDefaultImpl = param1IServiceConnection;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      ComponentName componentName;
      if (param1Int1 != 1) {
        if (param1Int1 != 1598968902)
          return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
        param1Parcel2.writeString("android.app.IServiceConnection");
        return true;
      } 
      param1Parcel1.enforceInterface("android.app.IServiceConnection");
      if (param1Parcel1.readInt() != 0) {
        componentName = (ComponentName)ComponentName.CREATOR.createFromParcel(param1Parcel1);
      } else {
        componentName = null;
      } 
      connected(componentName, param1Parcel1.readStrongBinder());
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements IServiceConnection {
      public static IServiceConnection sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public void connected(ComponentName param2ComponentName, IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.app.IServiceConnection");
          if (param2ComponentName != null) {
            parcel1.writeInt(1);
            param2ComponentName.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeStrongBinder(param2IBinder);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IServiceConnection.Stub.getDefaultImpl() != null) {
            IServiceConnection.Stub.getDefaultImpl().connected(param2ComponentName, param2IBinder);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "android.app.IServiceConnection";
      }
    }
  }
  
  private static class Proxy implements IServiceConnection {
    public static IServiceConnection sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public void connected(ComponentName param1ComponentName, IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.app.IServiceConnection");
        if (param1ComponentName != null) {
          parcel1.writeInt(1);
          param1ComponentName.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeStrongBinder(param1IBinder);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IServiceConnection.Stub.getDefaultImpl() != null) {
          IServiceConnection.Stub.getDefaultImpl().connected(param1ComponentName, param1IBinder);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "android.app.IServiceConnection";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\app\IServiceConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */