package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPackageDataObserver extends IInterface {
  void onRemoveCompleted(String paramString, boolean paramBoolean) throws RemoteException;
  
  public static class Default implements IPackageDataObserver {
    public IBinder asBinder() {
      return null;
    }
    
    public void onRemoveCompleted(String param1String, boolean param1Boolean) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IPackageDataObserver {
    private static final String DESCRIPTOR = "android.content.pm.IPackageDataObserver";
    
    static final int TRANSACTION_onRemoveCompleted = 1;
    
    public Stub() {
      attachInterface(this, "android.content.pm.IPackageDataObserver");
    }
    
    public static IPackageDataObserver asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.content.pm.IPackageDataObserver");
      return (iInterface != null && iInterface instanceof IPackageDataObserver) ? (IPackageDataObserver)iInterface : new Proxy(param1IBinder);
    }
    
    public static IPackageDataObserver getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IPackageDataObserver param1IPackageDataObserver) {
      if (Proxy.sDefaultImpl == null && param1IPackageDataObserver != null) {
        Proxy.sDefaultImpl = param1IPackageDataObserver;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      boolean bool;
      if (param1Int1 != 1) {
        if (param1Int1 != 1598968902)
          return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
        param1Parcel2.writeString("android.content.pm.IPackageDataObserver");
        return true;
      } 
      param1Parcel1.enforceInterface("android.content.pm.IPackageDataObserver");
      String str = param1Parcel1.readString();
      if (param1Parcel1.readInt() != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      onRemoveCompleted(str, bool);
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements IPackageDataObserver {
      public static IPackageDataObserver sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "android.content.pm.IPackageDataObserver";
      }
      
      public void onRemoveCompleted(String param2String, boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("android.content.pm.IPackageDataObserver");
          parcel1.writeString(param2String);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IPackageDataObserver.Stub.getDefaultImpl() != null) {
            IPackageDataObserver.Stub.getDefaultImpl().onRemoveCompleted(param2String, param2Boolean);
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
  
  private static class Proxy implements IPackageDataObserver {
    public static IPackageDataObserver sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "android.content.pm.IPackageDataObserver";
    }
    
    public void onRemoveCompleted(String param1String, boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("android.content.pm.IPackageDataObserver");
        parcel1.writeString(param1String);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IPackageDataObserver.Stub.getDefaultImpl() != null) {
          IPackageDataObserver.Stub.getDefaultImpl().onRemoveCompleted(param1String, param1Boolean);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\content\pm\IPackageDataObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */