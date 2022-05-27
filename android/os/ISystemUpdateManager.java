package android.os;

public interface ISystemUpdateManager extends IInterface {
  Bundle retrieveSystemUpdateInfo() throws RemoteException;
  
  void updateSystemUpdateInfo(PersistableBundle paramPersistableBundle) throws RemoteException;
  
  public static class Default implements ISystemUpdateManager {
    public IBinder asBinder() {
      return null;
    }
    
    public Bundle retrieveSystemUpdateInfo() throws RemoteException {
      return null;
    }
    
    public void updateSystemUpdateInfo(PersistableBundle param1PersistableBundle) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements ISystemUpdateManager {
    private static final String DESCRIPTOR = "android.os.ISystemUpdateManager";
    
    static final int TRANSACTION_retrieveSystemUpdateInfo = 1;
    
    static final int TRANSACTION_updateSystemUpdateInfo = 2;
    
    public Stub() {
      attachInterface(this, "android.os.ISystemUpdateManager");
    }
    
    public static ISystemUpdateManager asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.os.ISystemUpdateManager");
      return (iInterface != null && iInterface instanceof ISystemUpdateManager) ? (ISystemUpdateManager)iInterface : new Proxy(param1IBinder);
    }
    
    public static ISystemUpdateManager getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(ISystemUpdateManager param1ISystemUpdateManager) {
      if (Proxy.sDefaultImpl == null && param1ISystemUpdateManager != null) {
        Proxy.sDefaultImpl = param1ISystemUpdateManager;
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
          param1Parcel2.writeString("android.os.ISystemUpdateManager");
          return true;
        } 
        param1Parcel1.enforceInterface("android.os.ISystemUpdateManager");
        if (param1Parcel1.readInt() != 0) {
          PersistableBundle persistableBundle = (PersistableBundle)PersistableBundle.CREATOR.createFromParcel(param1Parcel1);
        } else {
          param1Parcel1 = null;
        } 
        updateSystemUpdateInfo((PersistableBundle)param1Parcel1);
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel1.enforceInterface("android.os.ISystemUpdateManager");
      Bundle bundle = retrieveSystemUpdateInfo();
      param1Parcel2.writeNoException();
      if (bundle != null) {
        param1Parcel2.writeInt(1);
        bundle.writeToParcel(param1Parcel2, 1);
      } else {
        param1Parcel2.writeInt(0);
      } 
      return true;
    }
    
    private static class Proxy implements ISystemUpdateManager {
      public static ISystemUpdateManager sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "android.os.ISystemUpdateManager";
      }
      
      public Bundle retrieveSystemUpdateInfo() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          Bundle bundle;
          parcel1.writeInterfaceToken("android.os.ISystemUpdateManager");
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && ISystemUpdateManager.Stub.getDefaultImpl() != null) {
            bundle = ISystemUpdateManager.Stub.getDefaultImpl().retrieveSystemUpdateInfo();
            return bundle;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            bundle = (Bundle)Bundle.CREATOR.createFromParcel(parcel2);
          } else {
            bundle = null;
          } 
          return bundle;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void updateSystemUpdateInfo(PersistableBundle param2PersistableBundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.os.ISystemUpdateManager");
          if (param2PersistableBundle != null) {
            parcel1.writeInt(1);
            param2PersistableBundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && ISystemUpdateManager.Stub.getDefaultImpl() != null) {
            ISystemUpdateManager.Stub.getDefaultImpl().updateSystemUpdateInfo(param2PersistableBundle);
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
  
  private static class Proxy implements ISystemUpdateManager {
    public static ISystemUpdateManager sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "android.os.ISystemUpdateManager";
    }
    
    public Bundle retrieveSystemUpdateInfo() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        Bundle bundle;
        parcel1.writeInterfaceToken("android.os.ISystemUpdateManager");
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && ISystemUpdateManager.Stub.getDefaultImpl() != null) {
          bundle = ISystemUpdateManager.Stub.getDefaultImpl().retrieveSystemUpdateInfo();
          return bundle;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          bundle = (Bundle)Bundle.CREATOR.createFromParcel(parcel2);
        } else {
          bundle = null;
        } 
        return bundle;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void updateSystemUpdateInfo(PersistableBundle param1PersistableBundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.os.ISystemUpdateManager");
        if (param1PersistableBundle != null) {
          parcel1.writeInt(1);
          param1PersistableBundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && ISystemUpdateManager.Stub.getDefaultImpl() != null) {
          ISystemUpdateManager.Stub.getDefaultImpl().updateSystemUpdateInfo(param1PersistableBundle);
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\os\ISystemUpdateManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */