package android.content;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISyncContext extends IInterface {
  void onFinished(SyncResult paramSyncResult) throws RemoteException;
  
  void sendHeartbeat() throws RemoteException;
  
  public static class Default implements ISyncContext {
    public IBinder asBinder() {
      return null;
    }
    
    public void onFinished(SyncResult param1SyncResult) throws RemoteException {}
    
    public void sendHeartbeat() throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements ISyncContext {
    private static final String DESCRIPTOR = "android.content.ISyncContext";
    
    static final int TRANSACTION_onFinished = 2;
    
    static final int TRANSACTION_sendHeartbeat = 1;
    
    public Stub() {
      attachInterface(this, "android.content.ISyncContext");
    }
    
    public static ISyncContext asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("android.content.ISyncContext");
      return (iInterface != null && iInterface instanceof ISyncContext) ? (ISyncContext)iInterface : new Proxy(param1IBinder);
    }
    
    public static ISyncContext getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(ISyncContext param1ISyncContext) {
      if (Proxy.sDefaultImpl == null && param1ISyncContext != null) {
        Proxy.sDefaultImpl = param1ISyncContext;
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
          param1Parcel2.writeString("android.content.ISyncContext");
          return true;
        } 
        param1Parcel1.enforceInterface("android.content.ISyncContext");
        if (param1Parcel1.readInt() != 0) {
          SyncResult syncResult = (SyncResult)SyncResult.CREATOR.createFromParcel(param1Parcel1);
        } else {
          param1Parcel1 = null;
        } 
        onFinished((SyncResult)param1Parcel1);
        param1Parcel2.writeNoException();
        return true;
      } 
      param1Parcel1.enforceInterface("android.content.ISyncContext");
      sendHeartbeat();
      param1Parcel2.writeNoException();
      return true;
    }
    
    private static class Proxy implements ISyncContext {
      public static ISyncContext sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public String getInterfaceDescriptor() {
        return "android.content.ISyncContext";
      }
      
      public void onFinished(SyncResult param2SyncResult) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.content.ISyncContext");
          if (param2SyncResult != null) {
            parcel1.writeInt(1);
            param2SyncResult.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && ISyncContext.Stub.getDefaultImpl() != null) {
            ISyncContext.Stub.getDefaultImpl().onFinished(param2SyncResult);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void sendHeartbeat() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("android.content.ISyncContext");
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && ISyncContext.Stub.getDefaultImpl() != null) {
            ISyncContext.Stub.getDefaultImpl().sendHeartbeat();
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
  
  private static class Proxy implements ISyncContext {
    public static ISyncContext sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public String getInterfaceDescriptor() {
      return "android.content.ISyncContext";
    }
    
    public void onFinished(SyncResult param1SyncResult) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.content.ISyncContext");
        if (param1SyncResult != null) {
          parcel1.writeInt(1);
          param1SyncResult.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && ISyncContext.Stub.getDefaultImpl() != null) {
          ISyncContext.Stub.getDefaultImpl().onFinished(param1SyncResult);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void sendHeartbeat() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("android.content.ISyncContext");
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && ISyncContext.Stub.getDefaultImpl() != null) {
          ISyncContext.Stub.getDefaultImpl().sendHeartbeat();
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\content\ISyncContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */