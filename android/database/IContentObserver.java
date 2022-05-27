package android.database;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IContentObserver extends IInterface {
  void onChange(boolean paramBoolean, Uri paramUri, int paramInt) throws RemoteException;
  
  public static class Default implements IContentObserver {
    public IBinder asBinder() {
      return null;
    }
    
    public void onChange(boolean param1Boolean, Uri param1Uri, int param1Int) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IContentObserver {
    private static final String DESCRIPTOR = "android.database.IContentObserver";
    
    static final int TRANSACTION_onChange = 1;
    
    public Stub() {
      attachInterface(this, "android.database.IContentObserver");
    }
    
    public static IContentObserver asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.database.IContentObserver");
      return (iInterface != null && iInterface instanceof IContentObserver) ? (IContentObserver)iInterface : new Proxy(param1IBinder);
    }
    
    public static IContentObserver getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IContentObserver param1IContentObserver) {
      if (Proxy.sDefaultImpl == null && param1IContentObserver != null) {
        Proxy.sDefaultImpl = param1IContentObserver;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      boolean bool;
      Uri uri;
      if (param1Int1 != 1) {
        if (param1Int1 != 1598968902)
          return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
        param1Parcel2.writeString("android.database.IContentObserver");
        return true;
      } 
      param1Parcel1.enforceInterface("android.database.IContentObserver");
      if (param1Parcel1.readInt() != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      if (param1Parcel1.readInt() != 0) {
        uri = (Uri)Uri.CREATOR.createFromParcel(param1Parcel1);
      } else {
        uri = null;
      } 
      onChange(bool, uri, param1Parcel1.readInt());
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements IContentObserver {
      public static IContentObserver sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "android.database.IContentObserver";
      }
      
      public void onChange(boolean param2Boolean, Uri param2Uri, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("android.database.IContentObserver");
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (param2Uri != null) {
            parcel1.writeInt(1);
            param2Uri.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IContentObserver.Stub.getDefaultImpl() != null) {
            IContentObserver.Stub.getDefaultImpl().onChange(param2Boolean, param2Uri, param2Int);
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
  
  private static class Proxy implements IContentObserver {
    public static IContentObserver sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "android.database.IContentObserver";
    }
    
    public void onChange(boolean param1Boolean, Uri param1Uri, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("android.database.IContentObserver");
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (param1Uri != null) {
          parcel1.writeInt(1);
          param1Uri.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IContentObserver.Stub.getDefaultImpl() != null) {
          IContentObserver.Stub.getDefaultImpl().onChange(param1Boolean, param1Uri, param1Int);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\database\IContentObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */