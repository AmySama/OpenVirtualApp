package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPackageInstallObserver extends IInterface {
  void packageInstalled(String paramString, int paramInt) throws RemoteException;
  
  public static class Default implements IPackageInstallObserver {
    public IBinder asBinder() {
      return null;
    }
    
    public void packageInstalled(String param1String, int param1Int) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IPackageInstallObserver {
    private static final String DESCRIPTOR = "android.content.pm.IPackageInstallObserver";
    
    static final int TRANSACTION_packageInstalled = 1;
    
    public Stub() {
      attachInterface(this, "android.content.pm.IPackageInstallObserver");
    }
    
    public static IPackageInstallObserver asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.content.pm.IPackageInstallObserver");
      return (iInterface != null && iInterface instanceof IPackageInstallObserver) ? (IPackageInstallObserver)iInterface : new Proxy(param1IBinder);
    }
    
    public static IPackageInstallObserver getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IPackageInstallObserver param1IPackageInstallObserver) {
      if (Proxy.sDefaultImpl == null && param1IPackageInstallObserver != null) {
        Proxy.sDefaultImpl = param1IPackageInstallObserver;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1) {
        if (param1Int1 != 1598968902)
          return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
        param1Parcel2.writeString("android.content.pm.IPackageInstallObserver");
        return true;
      } 
      param1Parcel1.enforceInterface("android.content.pm.IPackageInstallObserver");
      packageInstalled(param1Parcel1.readString(), param1Parcel1.readInt());
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements IPackageInstallObserver {
      public static IPackageInstallObserver sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "android.content.pm.IPackageInstallObserver";
      }
      
      public void packageInstalled(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.content.pm.IPackageInstallObserver");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IPackageInstallObserver.Stub.getDefaultImpl() != null) {
            IPackageInstallObserver.Stub.getDefaultImpl().packageInstalled(param2String, param2Int);
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
  
  private static class Proxy implements IPackageInstallObserver {
    public static IPackageInstallObserver sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "android.content.pm.IPackageInstallObserver";
    }
    
    public void packageInstalled(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.content.pm.IPackageInstallObserver");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IPackageInstallObserver.Stub.getDefaultImpl() != null) {
          IPackageInstallObserver.Stub.getDefaultImpl().packageInstalled(param1String, param1Int);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\content\pm\IPackageInstallObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */