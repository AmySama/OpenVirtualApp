package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPackageInstallerCallback extends IInterface {
  void onSessionActiveChanged(int paramInt, boolean paramBoolean) throws RemoteException;
  
  void onSessionBadgingChanged(int paramInt) throws RemoteException;
  
  void onSessionCreated(int paramInt) throws RemoteException;
  
  void onSessionFinished(int paramInt, boolean paramBoolean) throws RemoteException;
  
  void onSessionProgressChanged(int paramInt, float paramFloat) throws RemoteException;
  
  public static class Default implements IPackageInstallerCallback {
    public IBinder asBinder() {
      return null;
    }
    
    public void onSessionActiveChanged(int param1Int, boolean param1Boolean) throws RemoteException {}
    
    public void onSessionBadgingChanged(int param1Int) throws RemoteException {}
    
    public void onSessionCreated(int param1Int) throws RemoteException {}
    
    public void onSessionFinished(int param1Int, boolean param1Boolean) throws RemoteException {}
    
    public void onSessionProgressChanged(int param1Int, float param1Float) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IPackageInstallerCallback {
    private static final String DESCRIPTOR = "android.content.pm.IPackageInstallerCallback";
    
    static final int TRANSACTION_onSessionActiveChanged = 3;
    
    static final int TRANSACTION_onSessionBadgingChanged = 2;
    
    static final int TRANSACTION_onSessionCreated = 1;
    
    static final int TRANSACTION_onSessionFinished = 5;
    
    static final int TRANSACTION_onSessionProgressChanged = 4;
    
    public Stub() {
      attachInterface(this, "android.content.pm.IPackageInstallerCallback");
    }
    
    public static IPackageInstallerCallback asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.content.pm.IPackageInstallerCallback");
      return (iInterface != null && iInterface instanceof IPackageInstallerCallback) ? (IPackageInstallerCallback)iInterface : new Proxy(param1IBinder);
    }
    
    public static IPackageInstallerCallback getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IPackageInstallerCallback param1IPackageInstallerCallback) {
      if (Proxy.sDefaultImpl == null && param1IPackageInstallerCallback != null) {
        Proxy.sDefaultImpl = param1IPackageInstallerCallback;
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
          boolean bool1 = false;
          boolean bool2 = false;
          if (param1Int1 != 3) {
            if (param1Int1 != 4) {
              if (param1Int1 != 5) {
                if (param1Int1 != 1598968902)
                  return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2); 
                param1Parcel2.writeString("android.content.pm.IPackageInstallerCallback");
                return true;
              } 
              param1Parcel1.enforceInterface("android.content.pm.IPackageInstallerCallback");
              param1Int1 = param1Parcel1.readInt();
              if (param1Parcel1.readInt() != 0)
                bool2 = true; 
              onSessionFinished(param1Int1, bool2);
              param1Parcel2.writeNoException();
              return true;
            } 
            param1Parcel1.enforceInterface("android.content.pm.IPackageInstallerCallback");
            onSessionProgressChanged(param1Parcel1.readInt(), param1Parcel1.readFloat());
            param1Parcel2.writeNoException();
            return true;
          } 
          param1Parcel1.enforceInterface("android.content.pm.IPackageInstallerCallback");
          param1Int1 = param1Parcel1.readInt();
          bool2 = bool1;
          if (param1Parcel1.readInt() != 0)
            bool2 = true; 
          onSessionActiveChanged(param1Int1, bool2);
          param1Parcel2.writeNoException();
          return true;
        } 
        param1Parcel1.enforceInterface("android.content.pm.IPackageInstallerCallback");
        onSessionBadgingChanged(param1Parcel1.readInt());
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel1.enforceInterface("android.content.pm.IPackageInstallerCallback");
      onSessionCreated(param1Parcel1.readInt());
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements IPackageInstallerCallback {
      public static IPackageInstallerCallback sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "android.content.pm.IPackageInstallerCallback";
      }
      
      public void onSessionActiveChanged(int param2Int, boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("android.content.pm.IPackageInstallerCallback");
          parcel1.writeInt(param2Int);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IPackageInstallerCallback.Stub.getDefaultImpl() != null) {
            IPackageInstallerCallback.Stub.getDefaultImpl().onSessionActiveChanged(param2Int, param2Boolean);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void onSessionBadgingChanged(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.content.pm.IPackageInstallerCallback");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IPackageInstallerCallback.Stub.getDefaultImpl() != null) {
            IPackageInstallerCallback.Stub.getDefaultImpl().onSessionBadgingChanged(param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void onSessionCreated(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.content.pm.IPackageInstallerCallback");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IPackageInstallerCallback.Stub.getDefaultImpl() != null) {
            IPackageInstallerCallback.Stub.getDefaultImpl().onSessionCreated(param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void onSessionFinished(int param2Int, boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("android.content.pm.IPackageInstallerCallback");
          parcel1.writeInt(param2Int);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IPackageInstallerCallback.Stub.getDefaultImpl() != null) {
            IPackageInstallerCallback.Stub.getDefaultImpl().onSessionFinished(param2Int, param2Boolean);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void onSessionProgressChanged(int param2Int, float param2Float) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.content.pm.IPackageInstallerCallback");
          parcel1.writeInt(param2Int);
          parcel1.writeFloat(param2Float);
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IPackageInstallerCallback.Stub.getDefaultImpl() != null) {
            IPackageInstallerCallback.Stub.getDefaultImpl().onSessionProgressChanged(param2Int, param2Float);
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
  
  private static class Proxy implements IPackageInstallerCallback {
    public static IPackageInstallerCallback sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "android.content.pm.IPackageInstallerCallback";
    }
    
    public void onSessionActiveChanged(int param1Int, boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("android.content.pm.IPackageInstallerCallback");
        parcel1.writeInt(param1Int);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IPackageInstallerCallback.Stub.getDefaultImpl() != null) {
          IPackageInstallerCallback.Stub.getDefaultImpl().onSessionActiveChanged(param1Int, param1Boolean);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void onSessionBadgingChanged(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.content.pm.IPackageInstallerCallback");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IPackageInstallerCallback.Stub.getDefaultImpl() != null) {
          IPackageInstallerCallback.Stub.getDefaultImpl().onSessionBadgingChanged(param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void onSessionCreated(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.content.pm.IPackageInstallerCallback");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IPackageInstallerCallback.Stub.getDefaultImpl() != null) {
          IPackageInstallerCallback.Stub.getDefaultImpl().onSessionCreated(param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void onSessionFinished(int param1Int, boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("android.content.pm.IPackageInstallerCallback");
        parcel1.writeInt(param1Int);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IPackageInstallerCallback.Stub.getDefaultImpl() != null) {
          IPackageInstallerCallback.Stub.getDefaultImpl().onSessionFinished(param1Int, param1Boolean);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void onSessionProgressChanged(int param1Int, float param1Float) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.content.pm.IPackageInstallerCallback");
        parcel1.writeInt(param1Int);
        parcel1.writeFloat(param1Float);
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IPackageInstallerCallback.Stub.getDefaultImpl() != null) {
          IPackageInstallerCallback.Stub.getDefaultImpl().onSessionProgressChanged(param1Int, param1Float);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\content\pm\IPackageInstallerCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */