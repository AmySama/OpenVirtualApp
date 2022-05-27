package android.content;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISyncStatusObserver extends IInterface {
  void onStatusChanged(int paramInt) throws RemoteException;
  
  public static class Default implements ISyncStatusObserver {
    public IBinder asBinder() {
      return null;
    }
    
    public void onStatusChanged(int param1Int) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements ISyncStatusObserver {
    private static final String DESCRIPTOR = "android.content.ISyncStatusObserver";
    
    static final int TRANSACTION_onStatusChanged = 1;
    
    public Stub() {
      attachInterface(this, "android.content.ISyncStatusObserver");
    }
    
    public static ISyncStatusObserver asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.content.ISyncStatusObserver");
      return (iInterface != null && iInterface instanceof ISyncStatusObserver) ? (ISyncStatusObserver)iInterface : new Proxy(param1IBinder);
    }
    
    public static ISyncStatusObserver getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(ISyncStatusObserver param1ISyncStatusObserver) {
      if (Proxy.sDefaultImpl == null && param1ISyncStatusObserver != null) {
        Proxy.sDefaultImpl = param1ISyncStatusObserver;
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
        param1Parcel2.writeString("android.content.ISyncStatusObserver");
        return true;
      } 
      param1Parcel1.enforceInterface("android.content.ISyncStatusObserver");
      onStatusChanged(param1Parcel1.readInt());
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements ISyncStatusObserver {
      public static ISyncStatusObserver sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "android.content.ISyncStatusObserver";
      }
      
      public void onStatusChanged(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.content.ISyncStatusObserver");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && ISyncStatusObserver.Stub.getDefaultImpl() != null) {
            ISyncStatusObserver.Stub.getDefaultImpl().onStatusChanged(param2Int);
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
  
  private static class Proxy implements ISyncStatusObserver {
    public static ISyncStatusObserver sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "android.content.ISyncStatusObserver";
    }
    
    public void onStatusChanged(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.content.ISyncStatusObserver");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && ISyncStatusObserver.Stub.getDefaultImpl() != null) {
          ISyncStatusObserver.Stub.getDefaultImpl().onStatusChanged(param1Int);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\content\ISyncStatusObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */